package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Maridaje {
    private String descripcion;
    private String nombre;
    public boolean sosMaridaje(String nombre){
        return (Objects.equals(nombre, this.nombre));
    }
}
