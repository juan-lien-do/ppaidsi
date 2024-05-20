package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    private String contrasena;
    private String nombre;
    private boolean premium;
}
