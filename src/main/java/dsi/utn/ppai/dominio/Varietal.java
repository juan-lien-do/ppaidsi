package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Varietal {
    private String descripcion;
    private int porcentajeComposicion;
    private TipoUva tipoUva;
}
