package dsi.utn.ppai.controlador;

import dsi.utn.ppai.dominio.Bodega;
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
        }
    }

    private LocalDate getDate() {
        return LocalDate.now();
    }

    public void tomarSeleccionBodega(String nombre) {
        List<VinoDataHolder> vinosTemp = interfazAPI.consultarNovedades(nombre);
        // Aquí puedes agregar la lógica para manejar los vinos obtenidos
    }
}
