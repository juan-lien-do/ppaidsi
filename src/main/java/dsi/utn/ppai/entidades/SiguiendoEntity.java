package dsi.utn.ppai.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate fechaFin;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @ManyToOne
    @JoinColumn(name = "bodegaSeguidaId")
    private BodegaEntity bodegaEntity;

    @ManyToOne
    @JoinColumn(name = "enofiloSeguidoId")
    private EnofiloEntity enofiloEntity; // este seria el enofilo al que estan siguiendo
}
