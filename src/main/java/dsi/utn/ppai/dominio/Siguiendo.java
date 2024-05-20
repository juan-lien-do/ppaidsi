package dsi.utn.ppai.dominio;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Siguiendo {
    private LocalDate fechaFin;
    private LocalDate fechaInicio;
    private Bodega bodega;
    private Enofilo enofilo;

    private boolean sosDeBodega() {
        return (bodega != null);
    }

    private boolean sosDeAmigo() {
        return (enofilo != null);
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
}
