package dsi.utn.ppai.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiposUva")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoUvaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoUva")
    private Integer idTipoUva;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
}
