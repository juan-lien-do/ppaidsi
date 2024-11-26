package dsi.utn.ppai.modelo;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
//@ToString
@Builder
public class Vino {
    private Integer idVino;
    private String imagenEtiqueta;
    private int anada;
    private LocalDate fechaActualizacion;
    private String nombre;
    private String notaDeCataBodega;
    private float precioARS;
    private List<Varietal> varietales;
    private Bodega bodega;
    private List<Maridaje> maridajes;

    public boolean existeVino(String xNombre, int xAnada) {
        return (Objects.equals(this.getNombre(), xNombre) && this.getAnada() == xAnada);
    }

    //refactor constructor
    public Vino(LocalDate fechaActual, String vinoNombre, int vinoAnada,
                float vinoPrecioARS, String vinoNotaCata, List<String> vinoDescripcionesVarietal,
                List<Integer> vinoPorcentajesVarietales , List<TipoUva> tipoUvaParticular,
                List<Maridaje> maridajeParticular, Bodega bodega) {
        this.nombre = vinoNombre;
        this.anada = vinoAnada;
        this.precioARS = vinoPrecioARS;
        this.fechaActualizacion = fechaActual;
        this.notaDeCataBodega = vinoNotaCata;
        List<Varietal> varietalTemp = new ArrayList<>();
        for (int i = 0; i < tipoUvaParticular.size(); i++) {
            varietalTemp.add(crearVarietal(vinoDescripcionesVarietal.get(i),
                    vinoPorcentajesVarietales.get(i),
                    tipoUvaParticular.get(i)));
        }
        this.varietales = varietalTemp;
        this.maridajes = maridajeParticular;
        this.bodega = bodega;
        bodega.agregarVino(this);
    }

    private Varietal crearVarietal(String descrip, int porcentaje, TipoUva tipoUvaParticular) {
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

    @Override
    public String toString() {
        return "Vino{" +
                "idVino=" + idVino +
                ", imagenEtiqueta='" + imagenEtiqueta + '\'' +
                ", anada=" + anada +
                ", fechaActualizacion=" + fechaActualizacion +
                ", nombre='" + nombre + '\'' +
                ", notaDeCataBodega='" + notaDeCataBodega + '\'' +
                ", precioARS=" + precioARS +
                ", varietales=" + varietales +
                ", maridajes=" + maridajes +
                '}';
    }

    public boolean esReciente(LocalDate fechaActual) {
        return fechaActual == fechaActualizacion;
    }
}
