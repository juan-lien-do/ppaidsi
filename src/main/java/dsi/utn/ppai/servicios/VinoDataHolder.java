package dsi.utn.ppai.servicios;

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
    private String imagenEtiqueta;
    private String notaDeCataBodega;
    private float precioARS;
    private List<String> maridajes;
    private boolean actualizable;
    //Esto se va a volver una serie de listas
    private List<String> descripcionesVarietal;
    private List<Integer> porcentajesComposicionVarietal;
    private List<String> nombresUva;
}
