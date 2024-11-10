package dsi.utn.ppai.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "precioARS")
    private Float precioARS;

    @OneToMany(mappedBy = "vinoEntity")
    private List<VarietalEntity> varietalEntities;

    @ManyToMany
    @JoinTable(
            name = "maridajeXVino",
            joinColumns = @JoinColumn(name = "vinoId"),
            inverseJoinColumns = @JoinColumn(name = "maridajeId")
    )
    private List<MaridajeEntity> maridajeEntities;
}
