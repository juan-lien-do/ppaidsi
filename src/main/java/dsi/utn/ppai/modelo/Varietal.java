package dsi.utn.ppai.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
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
