package dsi.utn.ppai.controlador;

import dsi.utn.ppai.modelo.*;
import dsi.utn.ppai.inicio.PantallaMain;
import dsi.utn.ppai.pantalla.PantallaDeImportarVinos;
import dsi.utn.ppai.servicios.InterfazAPI;
import dsi.utn.ppai.servicios.InterfazNotificacionPush;
import dsi.utn.ppai.utilidades.AdministradorBaseDeDatos;
import dsi.utn.ppai.utilidades.VinoDataHolder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class GestorImportacionVinos {
    private PantallaDeImportarVinos pantallaDeImportarVinos;
    private List<String> nombresBodegasTemp;
    private final InterfazAPI interfazAPI;
    private Bodega bodegaSeleccionada;
    private List<Bodega> bodegas;
    private final InterfazNotificacionPush interfazNotificacionPush;
    private LocalDate fechaActual;

    public GestorImportacionVinos(PantallaDeImportarVinos pantallaDeImportarVinos) {
        this.pantallaDeImportarVinos = pantallaDeImportarVinos;
        this.interfazAPI = PantallaMain.getApplicationContext().getBean(InterfazAPI.class);
        this.interfazNotificacionPush = PantallaMain.getApplicationContext().getBean(InterfazNotificacionPush.class);
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
        try{
            novedades = interfazAPI.consultarNovedades(url);
        } catch (ResourceAccessException resourceAccessException){
            throw resourceAccessException;
            //this.pantallaDeImportarVinos.noFuncionaSistemaBodega();
        }
        return novedades;
    }

    private void actualizarVinos(List<VinoDataHolder> vinosDTO) {

        for (VinoDataHolder vino : vinosDTO){
            // Bodega seleccionada devuelve true si encontró un vino con ese nombre y añada
            // y actualiza los datos
            boolean esActualizable = this.bodegaSeleccionada.actualizarVino(vino.getNombre(),
                    vino.getAnada(), vino.getImagenEtiqueta(), vino.getPrecioARS(),
                    vino.getNotaDeCataBodega(), this.fechaActual);

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
            guardarCambiosBodega();


            //mostrar cambios

            pantallaDeImportarVinos.mostrarResumenDeVinos(bodegaSeleccionada.getNombre(),
                    vinosDTO.stream().filter(x -> x.isActualizable()).map(x -> x.getNombre()).toList(),
                    vinosDTO.stream().filter(x -> !x.isActualizable()).map(x -> x.getNombre()).toList());

            // aca va lo que falta
            buscarSeguidoresDeBodega();

        } catch (ResourceAccessException resourceAccessException){
            this.pantallaDeImportarVinos.noFuncionaSistemaBodega();
        }

    }

    private void guardarCambiosBodega(){
        AdministradorBaseDeDatos.getInstance().persistirCambiosBodegaYVinos(this.bodegaSeleccionada);
    }

    private void buscarSeguidoresDeBodega() {
        List<Enofilo> enofilos = AdministradorBaseDeDatos.getInstance().getEnofilos();
        System.out.println(enofilos.toString());
        List<String> nomEnofilos = new ArrayList<>();
        for (Enofilo en : enofilos) {
            if (en.seguisABodega(bodegaSeleccionada)) {
                nomEnofilos.add(en.getNombreUsuario());
            }
        }
        for (String nombre : nomEnofilos) {
            this.interfazNotificacionPush.enviarNotificacion(nombre);
        }

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
        Vino vinoParticular = new Vino(this.fechaActual, vinoDTO.getNombre(), vinoDTO.getAnada(), vinoDTO.getPrecioARS(),
                vinoDTO.getNotaDeCataBodega(), vinoDTO.getDescripcionesVarietal(), vinoDTO.getPorcentajesComposicionVarietal(),
                tipoUvaParticular, maridajeParticular, this.bodegaSeleccionada);
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
}
