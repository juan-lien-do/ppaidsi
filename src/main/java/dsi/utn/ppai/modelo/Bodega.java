package dsi.utn.ppai.modelo;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Bodega {
    private int idBodega;
    private float[] coordenadasUbicacion;
    private String apiUrl;
    private String descripcion;
    private String historia;
    private String nombre;
    private int periodoActualizacion; // expresado en dias
    private LocalDate ultimaActualizacion;
    private List<Vino> vinos;

    public void agregarVino(Vino vino) {
        System.out.println(vino);
        System.out.println(vinos.getClass());
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

    // TODO: DESACOPLAR EL DTO DEL GESTOR DE BODEGA
    // devuelve true si encontró un vino para actualizar con los datos del DTO

    /**
     * Actualiza un vino a la vez, si es que lo encuentra
     * @params datos de un vino nuevo y la fecha actual
     * @return TRUE si es que encontró un vino con esas características y lo actualizó.
     * FALSE si es que no encontró un vino con esos datos.
     */
    public boolean actualizarVino(String nombreVino, int anada, String imagenEtiqueta, float precioARS, String notaCataBodega, LocalDate fechaActual){
        for (Vino vino : this.vinos){
            if (vino.existeVino(nombreVino, anada)){

                vino.setImagenEtiqueta(imagenEtiqueta);
                vino.setPrecioARS(precioARS);
                vino.setNotaDeCataBodega(notaCataBodega);
                vino.setFechaActualizacion(fechaActual);
                return true;
            }
        }
        return false;
    }

    public void actualizarFechaBodega(LocalDate now) {
        this.setUltimaActualizacion(now);
    }
}
