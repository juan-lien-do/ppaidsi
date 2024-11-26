package dsi.utn.ppai.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Siguiendo {
    private int idSiguiendo;
    private LocalDate fechaFin;
    private LocalDate fechaInicio;
    private Bodega bodega;
    private Enofilo enofilo;

    public boolean sosDeBodega(Bodega bod) {
        if (this.bodega == null) return false;
        return bodega.getIdBodega() == bod.getIdBodega();
    }

    public boolean sosDeAmigo(Enofilo enof) {
        return (enofilo == enof);
    }

    public Siguiendo(LocalDate fechaFin, LocalDate fechaInicio, Bodega bodega) {
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.bodega = bodega;
    }

    public Siguiendo(LocalDate fechaFin, LocalDate fechaInicio, Enofilo enofilo) {
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.enofilo = enofilo;
    }

    @Override
    public String toString() {
        return "Siguiendo{" +
                "idSiguiendo=" + idSiguiendo +
                ", fechaFin=" + fechaFin +
                ", fechaInicio=" + fechaInicio +
                (bodega != null ? (", bodega=" + bodega.getIdBodega()) : "") +
                (enofilo != null ? (", enofilo=" + enofilo.getIdEnofilo()) : "") +
                '}';
    }
}
