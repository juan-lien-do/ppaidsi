package dsi.utn.ppai.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "varietales")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VarietalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVarietal")
    private Integer idVarietal;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "porcentajeComposicion")
    private Integer porcentajeComposicion;

    @ManyToOne
    @JoinColumn(name = "tipoUvaId")
    private TipoUvaEntity tipoUvaEntity;

    @ManyToOne
    @JoinColumn(name = "vinoId")
    private VinoEntity vinoEntity;
}
