package dsi.utn.ppai.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
    private LocalDate fechaActualizacion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "notaDeCataBodega")
    private String notaDeCataBodega;
    @Column(name = "precioArs")
    private Float precioARS;


    @OneToMany(mappedBy = "vinoEntity", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<VarietalEntity> varietalEntities;

    // perdon por usar fetchType Eager es que tenemos poco tiempo
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "maridajeXVino", // Nombre de la tabla intermedia
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


/*
@ManyToMany
    @JoinTable(
            name = "maridajeXVino",
            joinColumns = @JoinColumn(name = "vinoId"),
            inverseJoinColumns = @JoinColumn(name = "maridajeId")
    )

@OneToMany(mappedBy = "vinoEntity")
* */