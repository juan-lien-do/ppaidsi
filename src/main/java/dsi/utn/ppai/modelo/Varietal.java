package dsi.utn.ppai.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Varietal {
    private int idVarietal;
    private String descripcion;
    private int porcentajeComposicion;
    private TipoUva tipoUva;

    public Varietal(String descripcion, int porcentajeComposicion, TipoUva tipoUva) {
        this.descripcion = descripcion;
        this.porcentajeComposicion = porcentajeComposicion;
        this.tipoUva = tipoUva;
    }
}
