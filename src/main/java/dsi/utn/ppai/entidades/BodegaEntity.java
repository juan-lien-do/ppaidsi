package dsi.utn.ppai.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "bodegas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodegaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBodega")
    private Integer idBodega;
    @Column(name = "apiUrl")
    private String apiUrl;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "historia")
    private String historia;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "periodoActualizacion")
    private Integer periodoActualizacion;

    @OneToMany
    @JoinColumn(name = "bodegaId")
    private List<VinoEntity> vinoEntities;
}
