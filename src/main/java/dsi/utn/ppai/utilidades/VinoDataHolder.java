package dsi.utn.ppai.utilidades;

import dsi.utn.ppai.dominio.Bodega;
import dsi.utn.ppai.dominio.Maridaje;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class VinoDataHolder {
    private int anada;
    private LocalDate fechaActualizacion;
    private String nombre;
    private String notaDeCataBodega;
    private float precioARS;
    private List<Maridaje> maridajes;
    private String descripcionVarietal;
    private int porcentajeComposicionVarietal;
    private String nombreUva;
    private boolean actualizable;
}
