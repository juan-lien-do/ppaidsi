package dsi.utn.ppai.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maridajes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaridajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMaridaje")
    private Integer idMaridaje;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;


}
