package dsi.utn.ppai.modelo;

import dsi.utn.ppai.utilidades.VinoDataHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Bodega {
    private float[] coordenadasUbicacion;
    private String apiUrl;
    private String descripcion;
    private String historia;
    private String nombre;
    private int periodoActualizacion; // expresado en dias
    private LocalDate ultimaActualizacion;
    private List<Vino> vinos;

    public void agregarVino(Vino vino) {
        vinos.add(vino);
    }


    public Bodega(float[] coordenadasUbicacion, String apiUrl, String descripcion, String historia, String nombre, int periodoActualizacion, LocalDate ultimaActualizacion) {
        this.coordenadasUbicacion = coordenadasUbicacion;
        this.apiUrl = apiUrl;
        this.descripcion = descripcion;
        this.historia = historia;
        this.nombre = nombre;
        this.periodoActualizacion = periodoActualizacion;
        this.ultimaActualizacion = ultimaActualizacion;
        this.vinos = new ArrayList<>();
    }

    public Bodega(float[] coordenadasUbicacion, String descripcion, String historia, String nombre, int periodoActualizacion, LocalDate ultimaActualizacion) {
        this.coordenadasUbicacion = coordenadasUbicacion;
        this.descripcion = descripcion;
        this.historia = historia;
        this.nombre = nombre;
        this.periodoActualizacion = periodoActualizacion;
        this.ultimaActualizacion = ultimaActualizacion;
        this.vinos = new ArrayList<>();
    }

    public boolean esActualizable(LocalDate fechaActual) {
        return this.validarFechasAct(fechaActual);
    }

    public boolean validarFechasAct(LocalDate fechaActual) {
        return this.getUltimaActualizacion().plusDays(this.getPeriodoActualizacion()).isBefore(fechaActual);
    }

    public boolean existe(String nombre) {
        return (Objects.equals(nombre, this.nombre));
    }

    public void actualizarVinos(List<VinoDataHolder> vinoDataHolders, LocalDate fechaActual) {
        for (Vino vino : this.vinos) {
            for (VinoDataHolder vinoDTO : vinoDataHolders) {
                if (vino.existeVino(vinoDTO.getNombre(), vinoDTO.getAnada())) {
                    vinoDTO.setActualizable(true);

                    vino.setImagenEtiqueta(vinoDTO.getImagenEtiqueta());
                    vino.setPrecioARS(vinoDTO.getPrecioARS());
                    vino.setNotaDeCataBodega(vinoDTO.getNotaDeCataBodega());
                    vino.setFechaActualizacion(fechaActual);
                }
            }
        }
    }

    public void actualizarFechaBodega(LocalDate now) {
        this.setUltimaActualizacion(now);
    }
}
