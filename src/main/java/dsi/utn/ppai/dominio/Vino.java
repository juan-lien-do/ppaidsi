package dsi.utn.ppai.dominio;

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
    private int anada;
    private LocalDate fechaActualizacion;
    private String nombre;
    private String notaDeCataBodega;
    private float precioARS;
    private List<Varietal> varietales;
    private Bodega bodega;
    private List<Maridaje> maridajes;

    public boolean existeVino(VinoDataHolder x){
        return (Objects.equals(this.getNombre(), x.getNombre()) && this.getAnada() == x.getAnada());
    }
    public Vino(VinoDataHolder x, TipoUva tipoUvaParticular, List<Maridaje> maridajeParticular, Bodega bodega){
        this.nombre = x.getNombre();
        this.anada = x.getAnada();
        this.precioARS = x.getPrecioARS();
        this.fechaActualizacion = LocalDate.now();
        this.notaDeCataBodega = x.getNotaDeCataBodega();
        this.varietales = crearVarietal(null, x.getPorcentajeComposicionVarietal(), tipoUvaParticular);
        this.maridajes = maridajeParticular;
        this.bodega = bodega;
        bodega.agregarVino(this);
    }

    private List<Varietal> crearVarietal(String descrip, int porcentaje, TipoUva tipoUvaParticular){
        List<Varietal> varietals = new ArrayList<>();
        varietals.add(new Varietal(descrip, porcentaje, tipoUvaParticular));
        return varietals;
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
