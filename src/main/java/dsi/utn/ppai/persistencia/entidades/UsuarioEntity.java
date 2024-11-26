package dsi.utn.ppai.persistencia.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "premium")
    private Boolean premium;
}
