package dsi.utn.ppai.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "vinos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VinoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVino")
    private Integer idVino;
    @Column(name = "imagenEtiqueta")
    private String imagenEtiqueta;
    @Column(name = "anada")
    private Integer anada;
    @Column(name = "fechaActualizacion")
    private Long fechaActualizacion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "notaCataBodega")
    private String notaDeCataBodega;
    @Column(name = "precio_ars")
    private Float precioARS;


    @OneToMany(mappedBy = "vinoEntity", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<VarietalEntity> varietalEntities;

    // perdon por usar fetchType Eager es que tenemos poco tiempo
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "maridaje_x_vino", // creo que es un bug pero no funciona el parseo de las mayúsculas a guiones bajos
            joinColumns = @JoinColumn(name = "vinoId"),
            inverseJoinColumns = @JoinColumn(name = "maridajeId")
    )
    private List<MaridajeEntity> maridajeEntities;

    @ManyToOne
    @JoinColumn(name = "bodega_id") // FK a la bodega
    private BodegaEntity bodega;

    @Override
    public String toString() {
        return "VinoEntity{" +
                "idVino=" + idVino +
                ", imagenEtiqueta='" + imagenEtiqueta + '\'' +
                ", anada=" + anada +
                ", fechaActualizacion=" + fechaActualizacion +
                ", nombre='" + nombre + '\'' +
                ", notaDeCataBodega='" + notaDeCataBodega + '\'' +
                ", precioARS=" + precioARS +
                ", varietalEntities=" + varietalEntities +
                ", maridajeEntities=" + maridajeEntities +
                '}';
    }
}