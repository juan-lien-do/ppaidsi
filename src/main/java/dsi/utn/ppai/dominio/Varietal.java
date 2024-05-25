package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Varietal {
    private String descripcion;
    private int porcentajeComposicion;
    private TipoUva tipoUva;
}
