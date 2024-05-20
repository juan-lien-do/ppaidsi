package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Bodega {
    private float[] coordenadasUbicacion;
    private String descripcion;
    private String historia;
    private String nombre;
    private int periodoActualizacion; // expresado en dias
    private LocalDate ultimaActualizacion;
    private List<Vino> vinos;
    public void agregarVino(Vino vino){
        vinos.add(vino);
    };

    public Bodega(float[] coordenadasUbicacion, String descripcion, String historia, String nombre, int periodoActualizacion, LocalDate ultimaActualizacion) {
        this.coordenadasUbicacion = coordenadasUbicacion;
        this.descripcion = descripcion;
        this.historia = historia;
        this.nombre = nombre;
        this.periodoActualizacion = periodoActualizacion;
        this.ultimaActualizacion = ultimaActualizacion;
        this.vinos = new ArrayList<>();
    }

    public boolean esActualizable(LocalDate fechaActual){
        return this.getUltimaActualizacion().plusDays(this.getPeriodoActualizacion()).isBefore(fechaActual);
    }
}
