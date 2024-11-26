package dsi.utn.ppai.controlador;

import dsi.utn.ppai.modelo.*;
import dsi.utn.ppai.inicio.PantallaMain;
import dsi.utn.ppai.notificacion.IObserverNotificacion;
import dsi.utn.ppai.notificacion.ISujetoNotificacion;
import dsi.utn.ppai.pantalla.PantallaDeImportarVinos;
import dsi.utn.ppai.servicios.ServicioAPIBodega;
import dsi.utn.ppai.notificacion.InterfazNotificacionPush;
import dsi.utn.ppai.persistencia.AdministradorBaseDeDatos;
import dsi.utn.ppai.servicios.VinoDataHolder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class GestorImportacionVinos implements ISujetoNotificacion {
    private PantallaDeImportarVinos pantallaDeImportarVinos;
    private List<String> nombresBodegasTemp;
    private final ServicioAPIBodega interfazAPI;
    private Bodega bodegaSeleccionada;
    private List<Bodega> bodegas;
    //private final InterfazNotificacionPush interfazNotificacionPush;
    private LocalDate fechaActual;
    private String[] nombresUsuario;
    private HashMap<String, Float> vinosActualizados;
    private HashMap<String, Float> vinosNuevos;

    public GestorImportacionVinos(PantallaDeImportarVinos pantallaDeImportarVinos) {
        this.pantallaDeImportarVinos = pantallaDeImportarVinos;
        this.interfazAPI = PantallaMain.getApplicationContext().getBean(ServicioAPIBodega.class);
        //this.interfazNotificacionPush = PantallaMain.getApplicationContext().getBean(InterfazNotificacionPush.class);
    }

    public void importarVinos() {
        buscarBodegas();
    }

    private void buscarBodegas() {
        List<Bodega> bodegasTemp = AdministradorBaseDeDatos.getInstance().getBodegas();

        this.fechaActual = getDate();

        List<String> nombresBodegasTemp = new ArrayList<>();

        for (Bodega bodegaAnalizar : bodegasTemp) {
            if (bodegaAnalizar.esActualizable(this.fechaActual)) {
                nombresBodegasTemp.add(bodegaAnalizar.getNombre());
            }
        }
        if (nombresBodegasTemp.isEmpty()) {
            pantallaDeImportarVinos.noHayBodegas();
        } else {
            pantallaDeImportarVinos.mostrarBodegas(nombresBodegasTemp);
            this.bodegas = bodegasTemp;
        }

    }

    private LocalDate getDate() {
        return LocalDate.now();
    }

    public List<VinoDataHolder> obtenerActualizaciones() {
        String url = this.bodegaSeleccionada.getApiUrl();
        List<VinoDataHolder> novedades = new ArrayList<>();
        try {
            novedades = interfazAPI.consultarNovedades(url);
        } catch (ResourceAccessException resourceAccessException) {
            throw resourceAccessException;
            //this.pantallaDeImportarVinos.noFuncionaSistemaBodega();
        }
        return novedades;
    }

    private void actualizarVinos(List<VinoDataHolder> vinosDTO) {

        for (VinoDataHolder vino : vinosDTO) {
            // Bodega seleccionada devuelve true si encontró un vino con ese nombre y añada
            // y actualiza los datos
            boolean esActualizable = this.bodegaSeleccionada.actualizarVino(vino.getNombre(), vino.getAnada(), vino.getImagenEtiqueta(), vino.getPrecioARS(), vino.getNotaDeCataBodega(), this.fechaActual);

            vino.setActualizable(esActualizable);
        }

    }

    public void tomarSeleccionBodega(String nombre) {

        List<VinoDataHolder> vinosDTO = null;
        //setear la bodega
        this.bodegaSeleccionada = this.buscarBodegaPorNombre(nombre);
        try {
            vinosDTO = this.obtenerActualizaciones();
            // y hacer cosas con los vinosDTO
            this.actualizarVinos(vinosDTO);
            this.crearVinosNuevos(vinosDTO);
            // act fecha bodega
            this.bodegaSeleccionada.actualizarFechaBodega(fechaActual);

            // persistencia
            guardarCambiosBodegaYVinos();

            // dividir vinos
            this.vinosNuevos = new HashMap<String, Float>();
            this.vinosActualizados = new HashMap<String, Float>();
            for (Vino vino : bodegaSeleccionada.getVinos()){
                if (vino.esReciente(this.fechaActual)){
                    if (null == vino.getIdVino()){// significa que no fue persistido
                        vinosNuevos.put(vino.getNombre(), vino.getPrecioARS());
                    } else {
                        vinosActualizados.put(vino.getNombre(), vino.getPrecioARS());
                    }
                }
            }

            for (VinoDataHolder vinoDataHolder: vinosDTO){
                if (vinoDataHolder.isActualizable()){

                } else {

                };
            }



            //mostrar cambios

            pantallaDeImportarVinos.mostrarResumenDeVinos(bodegaSeleccionada.getNombre(),
                    vinosActualizados.entrySet().stream().map(x -> x.getKey() + " - " + x.getValue() + " ARS").toList(),
                    vinosNuevos.entrySet().stream().map(x -> x.getKey() + " - " + x.getValue() + " ARS").toList());

            // TODO ESPERAR CORRECCIÓN DEL PROFESOR
            this.nombresUsuario = buscarSeguidoresDeBodega(this.bodegaSeleccionada);

            notificarEnofilos();

        } catch (ResourceAccessException resourceAccessException) {
            this.pantallaDeImportarVinos.noFuncionaSistemaBodega();
        }

    }


    private String[] buscarSeguidoresDeBodega(Bodega bodSel) {
        List<Enofilo> enofilos = AdministradorBaseDeDatos.getInstance().getEnofilos();

        List<String> nomEnofilos = new ArrayList<>();
        for (Enofilo en : enofilos) {
            if (en.seguisABodega(bodSel)) {
                nomEnofilos.add(en.getNombre());
            }
        }

        return nomEnofilos.toArray(new String[0]);
    }

    private void notificarEnofilos() {
        List<IObserverNotificacion> notificadores = new ArrayList<>();
        InterfazNotificacionPush interfazNotificacionPush =
                PantallaMain.getApplicationContext().getBean(InterfazNotificacionPush.class);
        // siempre se van a suscribir las notificaciones push en este caso de uso
        notificadores.add(interfazNotificacionPush);

        for (IObserverNotificacion iObserverNotificacion : notificadores) {
            suscribir(iObserverNotificacion);
        }

        notificar();
    }

    private void guardarCambiosBodegaYVinos() {
        AdministradorBaseDeDatos.getInstance().persistirCambiosBodegaYVinos(this.bodegaSeleccionada);
    }

    private void crearVinosNuevos(List<VinoDataHolder> vinosDTO) {
        for (VinoDataHolder vinoDto : vinosDTO) {
            if (!vinoDto.isActualizable()) {
                List<Maridaje> maridajeParticular = this.buscarMaridaje(vinoDto.getMaridajes());
                List<TipoUva> tiposUvaParticular = this.buscarTipoDeUva(vinoDto.getNombresUva());
                //refactor desde aca
                crearVino(vinoDto, tiposUvaParticular, maridajeParticular);
            }
        }
    }

    private void crearVino(VinoDataHolder vinoDTO, List<TipoUva> tipoUvaParticular, List<Maridaje> maridajeParticular) {
        // partir el dto antes de mandarselo al constructorr

        // acá establecemos la doble dependencia vino-bodega
        Vino vinoParticular = new Vino(this.fechaActual, vinoDTO.getNombre(), vinoDTO.getAnada(), vinoDTO.getPrecioARS(), vinoDTO.getNotaDeCataBodega(), vinoDTO.getDescripcionesVarietal(), vinoDTO.getPorcentajesComposicionVarietal(), tipoUvaParticular, maridajeParticular, this.bodegaSeleccionada);
    }

    private List<TipoUva> buscarTipoDeUva(List<String> nombreUva) {
        if (nombreUva == null) return null;
        List<TipoUva> tiposUva = new ArrayList<>();

        for (TipoUva tUva : AdministradorBaseDeDatos.getInstance().getTiposUva()) {
            for (String nombreTUva : nombreUva)
                if (tUva.sosTipoDeUva(nombreTUva)) {
                    tiposUva.add(tUva);
                }
        }
        return tiposUva;
    }

    private List<Maridaje> buscarMaridaje(List<String> maridajesP) {
        if (maridajesP == null) return null;
        List<Maridaje> maridajesFinal = new ArrayList<>();
        for (Maridaje maridaje : AdministradorBaseDeDatos.getInstance().getMaridajes()) {
            for (String nombreMaridaje : maridajesP) {
                if (maridaje.sosMaridaje(nombreMaridaje)) {
                    maridajesFinal.add(maridaje);
                }
            }
        }
        return maridajesFinal;
    }

    private Bodega buscarBodegaPorNombre(String nombre) {
        for (Bodega bodega : this.bodegas) {
            if (bodega.existe(nombre)) {
                return bodega;
            }
        }
        return null;
    }

    @Override
    public void notificar() {
        // "...No te lo podés perder por nada del mundo, ..."
        String nombreBodega = this.bodegaSeleccionada.getNombre();
        String[] nombresVinosNuevos = this.vinosNuevos.keySet().toArray(new String[0]);
        String[] nombresVinosActualizados = this.vinosActualizados.keySet().toArray(new String[0]);
        String textoNotificacion = " no te lo podés perder por nada del mundo, ";
        // ya tenemos la LocalDate

        for (IObserverNotificacion iObserverNotificacion : this.observadores) {
            iObserverNotificacion.enviarNotificacion(nombreBodega,
                    this.nombresUsuario, textoNotificacion,
                    this.fechaActual, nombresVinosActualizados, nombresVinosNuevos);
        }
    }

    @Override
    public void quitar(IObserverNotificacion o) {
        observadores.remove(o);
    }

    @Override
    public void suscribir(IObserverNotificacion o) {
        observadores.add(o);
    }
}
