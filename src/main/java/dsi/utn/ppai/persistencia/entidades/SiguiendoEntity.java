package dsi.utn.ppai.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "siguiendos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiguiendoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSiguiendo")
    private Integer idSiguiendo;
    @Column(name = "fechaFin")
    private Long fechaFin;
    @Column(name = "fechaInicio")
    private Long fechaInicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bodegaSeguidaId")
    private BodegaEntity bodegaEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enofiloSeguidoId")
    private EnofiloEntity enofiloEntity; // este seria el enofilo al que estan siguiendo
}
