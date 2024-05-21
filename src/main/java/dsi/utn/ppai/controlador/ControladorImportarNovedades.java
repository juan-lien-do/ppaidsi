package dsi.utn.ppai.controlador;

import dsi.utn.ppai.dominio.Bodega;
import dsi.utn.ppai.dominio.Maridaje;
import dsi.utn.ppai.dominio.TipoUva;
import dsi.utn.ppai.dominio.Vino;
import dsi.utn.ppai.inicio.PantallaMain;
import dsi.utn.ppai.pantalla.PantallaImportarNovedades;
import dsi.utn.ppai.servicios.InterfazAPI;
import dsi.utn.ppai.utilidades.FalsaBaseDeDatos;
import dsi.utn.ppai.utilidades.VinoDataHolder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ControladorImportarNovedades {
    private PantallaImportarNovedades pantallaImportarNovedades;
    private List<String> nombresBodegasTemp;
    private final InterfazAPI interfazAPI;
    private Bodega bodegaSeleccionada;
    private List<VinoDataHolder> vinoDataHolders;
    private List<Bodega> bodegas;

    public ControladorImportarNovedades(PantallaImportarNovedades pantallaImportarNovedades) {
        this.pantallaImportarNovedades = pantallaImportarNovedades;
        this.interfazAPI = PantallaMain.getApplicationContext().getBean(InterfazAPI.class);
    }

    public void importarVinos() {
        buscarBodegas();
    }

    private void buscarBodegas() {
        List<Bodega> bodegasTemp = FalsaBaseDeDatos.getInstance().getBodegas();
        LocalDate fechaActual = getDate();
        List<String> nombresBodegasTemp = new ArrayList<>();
        for (Bodega x : bodegasTemp) {
            if (x.esActualizable(fechaActual)) {
                nombresBodegasTemp.add(x.getNombre());
            }
        }
        if (nombresBodegasTemp.isEmpty()) {
            pantallaImportarNovedades.noHayBodegas();
        } else {
            pantallaImportarNovedades.mostrarBodegas(nombresBodegasTemp);
            this.bodegas = bodegasTemp;
        }

    }

    private LocalDate getDate() {
        return LocalDate.now();
    }

    public void obtenerActualizaciones(String nombre) {
        this.vinoDataHolders = interfazAPI.consultarNovedades(nombre);


    }

    private void actualizarVinos() {
        //this.vinoDataHolders;
        this.bodegaSeleccionada.actualizarVinos(vinoDataHolders);
    }

    public void tomarSeleccionBodega(String nombre) {
        this.obtenerActualizaciones(nombre);

        //ahora hay q setear la bodega seleccionada
        this.validarBodega(nombre);
        // y hacer cosas con los vinos
        this.actualizarVinos();
        this.crearVinosNuevos();
        // act fecha bodega
        this.bodegaSeleccionada.actualizarFechaBodega(LocalDate.now());
        //mostrar cambios

        pantallaImportarNovedades.mostrarResumenDeVinos(bodegaSeleccionada.getNombre(),
                this.vinoDataHolders.stream().filter(x-> x.isActualizable()).map(x -> x.getNombre()).toList(),
                this.vinoDataHolders.stream().filter(x-> !x.isActualizable()).map(x -> x.getNombre()).toList());

        // aca va lo que falta
    }

    private void crearVinosNuevos() {
        for(VinoDataHolder x : this.vinoDataHolders){
            if (!x.isActualizable()){
                List<Maridaje> maridajeParticular = this.buscarMaridaje(x.getMaridajes());
                TipoUva tipoUvaParticular = this.buscarTipoDeUva(x.getNombreUva());
                crearVino(x, tipoUvaParticular, maridajeParticular);
            }
        }
    }

    private void crearVino(VinoDataHolder x, TipoUva tipoUvaParticular, List<Maridaje> maridajeParticular) {
        Vino vinoParticular = new Vino(x, tipoUvaParticular, maridajeParticular, this.bodegaSeleccionada);
        System.out.println(vinoParticular);
        FalsaBaseDeDatos.getInstance().agregarNuevoVino(vinoParticular);
    }

    private TipoUva buscarTipoDeUva(String nombreUva) {
        for(TipoUva x : FalsaBaseDeDatos.getInstance().getTiposUva()){
            if(x.sosTipoDeUva(nombreUva)){
                return x;
            }
        }
        return null;
    }

    private List<Maridaje> buscarMaridaje(List<Maridaje> maridajesP) {
        if(maridajesP == null) return null;
        List<Maridaje> maridajesFinal = new ArrayList<>();
        for( Maridaje x : FalsaBaseDeDatos.getInstance().getMaridajes()){
            for(Maridaje y : maridajesP){
                if (x.sosMaridaje(y.getNombre())){
                    maridajesFinal.add(y);
                }
            }
        }
        return maridajesFinal;
    }

    private void validarBodega(String nombre) {
        for (Bodega x : this.bodegas) {
            if (x.existe(nombre)) {
                this.bodegaSeleccionada = x;
            }
        }

    }
}
