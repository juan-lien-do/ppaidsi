package dsi.utn.ppai.controlador;

import dsi.utn.ppai.dominio.*;
import dsi.utn.ppai.inicio.PantallaMain;
import dsi.utn.ppai.pantalla.PantallaDeImportarVinos;
import dsi.utn.ppai.servicios.InterfazAPI;
import dsi.utn.ppai.servicios.InterfazNotificacionPush;
import dsi.utn.ppai.utilidades.FalsaBaseDeDatos;
import dsi.utn.ppai.utilidades.VinoDataHolder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GestorImportacionVinos {
    private PantallaDeImportarVinos pantallaDeImportarVinos;
    private List<String> nombresBodegasTemp;
    private final InterfazAPI interfazAPI;
    private Bodega bodegaSeleccionada;
    private List<VinoDataHolder> vinoDataHolders;
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
        List<Bodega> bodegasTemp = FalsaBaseDeDatos.getInstance().getBodegas();
        getDate();
        List<String> nombresBodegasTemp = new ArrayList<>();
        for (Bodega x : bodegasTemp) {
            if (x.esActualizable(this.fechaActual)) {
                nombresBodegasTemp.add(x.getNombre());
            }
        }
        if (nombresBodegasTemp.isEmpty()) {
            pantallaDeImportarVinos.noHayBodegas();
        } else {
            pantallaDeImportarVinos.mostrarBodegas(nombresBodegasTemp);
            this.bodegas = bodegasTemp;
        }

    }

    private void getDate() {
        this.fechaActual = LocalDate.now();
    }

    public void obtenerActualizaciones() {
        String url = this.bodegaSeleccionada.getApiUrl();
        this.vinoDataHolders = interfazAPI.consultarNovedades(url);


    }

    private void actualizarVinos() {
        //this.vinoDataHolders;
        this.bodegaSeleccionada.actualizarVinos(vinoDataHolders, this.fechaActual);
    }

    public void tomarSeleccionBodega(String nombre) {
        //setear la bodega
        this.buscarBodegaPorNombre(nombre);
        //obtener actualizaciones
        this.obtenerActualizaciones();
        // y hacer cosas con los vinos
        this.actualizarVinos();
        this.crearVinosNuevos();
        // act fecha bodega
        this.bodegaSeleccionada.actualizarFechaBodega(fechaActual);
        //mostrar cambios

        pantallaDeImportarVinos.mostrarResumenDeVinos(bodegaSeleccionada.getNombre(),
                this.vinoDataHolders.stream().filter(x -> x.isActualizable()).map(x -> x.getNombre()).toList(),
                this.vinoDataHolders.stream().filter(x -> !x.isActualizable()).map(x -> x.getNombre()).toList());

        // aca va lo que falta
        buscarSeguidoresDeBodega();
    }

    private void buscarSeguidoresDeBodega() {
        List<Enofilo> enofilos = FalsaBaseDeDatos.getInstance().getEnofilos();
        List<String> nomEnofilos = new ArrayList<>();
        for (Enofilo en : enofilos) {
            if (en.seguisABodega(bodegaSeleccionada)) {
                nomEnofilos.add(en.getNombreUsuario());
            }
        }
        for (String nombre : nomEnofilos) {
            this.interfazNotificacionPush.enviarNotificacion(nombre);
        }
        ;

    }

    private void crearVinosNuevos() {
        for (VinoDataHolder x : this.vinoDataHolders) {
            if (!x.isActualizable()) {
                List<Maridaje> maridajeParticular = this.buscarMaridaje(x.getMaridajes());
                List<TipoUva> tiposUvaParticular = this.buscarTipoDeUva(x.getNombresUva());
                crearVino(x, tiposUvaParticular, maridajeParticular);
            }
        }
    }

    private void crearVino(VinoDataHolder x, List<TipoUva> tipoUvaParticular, List<Maridaje> maridajeParticular) {
        Vino vinoParticular = new Vino(x, tipoUvaParticular, maridajeParticular, this.bodegaSeleccionada);
        System.out.println(vinoParticular);
        FalsaBaseDeDatos.getInstance().agregarNuevoVino(vinoParticular);
    }

    private List<TipoUva> buscarTipoDeUva(List<String> nombreUva) {
        if (nombreUva == null) return null;
        List<TipoUva> tiposUva = new ArrayList<>();

        for (TipoUva x : FalsaBaseDeDatos.getInstance().getTiposUva()) {
            for (String y : nombreUva)
                if (x.sosTipoDeUva(y)) {
                    tiposUva.add(x);
                }
        }
        return tiposUva;
    }

    private List<Maridaje> buscarMaridaje(List<String> maridajesP) {
        if (maridajesP == null) return null;
        List<Maridaje> maridajesFinal = new ArrayList<>();
        for (Maridaje x : FalsaBaseDeDatos.getInstance().getMaridajes()) {
            for (String y : maridajesP) {
                if (x.sosMaridaje(y)) {
                    maridajesFinal.add(x);
                }
            }
        }
        return maridajesFinal;
    }

    private void buscarBodegaPorNombre(String nombre) {
        for (Bodega x : this.bodegas) {
            if (x.existe(nombre)) {
                this.bodegaSeleccionada = x;
            }
        }

    }
}
