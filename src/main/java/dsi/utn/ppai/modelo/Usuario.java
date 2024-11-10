package dsi.utn.ppai.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    private int idUsuario;
    private String contrasena;
    private String nombre;
    private boolean premium;

    public Usuario(String contrasena, String nombre, boolean premium) {
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.premium = premium;
    }
}
