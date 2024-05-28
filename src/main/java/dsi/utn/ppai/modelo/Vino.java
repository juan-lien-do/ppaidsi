package dsi.utn.ppai.modelo;

import dsi.utn.ppai.utilidades.VinoDataHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Vino {
    private String imagenEtiqueta;
    private int anada;
    private LocalDate fechaActualizacion;
    private String nombre;
    private String notaDeCataBodega;
    private float precioARS;
    private List<Varietal> varietales;
    private Bodega bodega;
    private List<Maridaje> maridajes;

    public boolean existeVino(String xNombre, int xAnada){
        return (Objects.equals(this.getNombre(), xNombre) && this.getAnada() == xAnada);
    }
    public Vino(VinoDataHolder x, List<TipoUva> tipoUvaParticular, List<Maridaje> maridajeParticular, Bodega bodega){
        this.nombre = x.getNombre();
        this.anada = x.getAnada();
        this.precioARS = x.getPrecioARS();
        this.fechaActualizacion = LocalDate.now();
        this.notaDeCataBodega = x.getNotaDeCataBodega();
        List<Varietal> varietalTemp = new ArrayList<>();
        for (int i = 0; i < tipoUvaParticular.size(); i++) {
            varietalTemp.add(crearVarietal(x.getDescripcionesVarietal().get(i),
                    x.getPorcentajesComposicionVarietal().get(i),
                    tipoUvaParticular.get(i)));
        }
        this.varietales = varietalTemp;
        this.maridajes = maridajeParticular;
        this.bodega = bodega;
        bodega.agregarVino(this);
    }

    private Varietal crearVarietal(String descrip, int porcentaje, TipoUva tipoUvaParticular){
        return new Varietal(descrip, porcentaje, tipoUvaParticular);
    }

    public Vino(int anada, LocalDate fechaActualizacion, String nombre, String notaDeCataBodega, float precioARS, List<Varietal> varietales, Bodega bodega) {
        this.anada = anada;
        this.fechaActualizacion = fechaActualizacion;
        this.nombre = nombre;
        this.notaDeCataBodega = notaDeCataBodega;
        this.precioARS = precioARS;
        this.varietales = varietales;
        this.bodega = bodega;
    }
}
