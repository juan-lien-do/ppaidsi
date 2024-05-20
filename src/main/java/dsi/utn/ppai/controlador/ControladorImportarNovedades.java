package dsi.utn.ppai.controlador;

import dsi.utn.ppai.pantalla.PantallaImportarNovedades;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ControladorImportarNovedades {
    private List<String> nombres;
    private PantallaImportarNovedades pantallaImportarNovedades;

    public ControladorImportarNovedades(PantallaImportarNovedades pantallaImportarNovedades) {
        this.pantallaImportarNovedades = pantallaImportarNovedades;
    }
}
