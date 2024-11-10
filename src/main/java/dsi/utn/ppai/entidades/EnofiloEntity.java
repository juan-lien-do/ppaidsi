package dsi.utn.ppai.entidades;

import dsi.utn.ppai.modelo.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "enofilos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnofiloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEnofilo")
    private Integer idEnofilo;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(name = "usuarioId")
    private UsuarioEntity usuarioEntities;

    @OneToMany
    @JoinColumn(name = "enofiloSigueId")
    private List<SiguiendoEntity> siguiendoEntities;
}
