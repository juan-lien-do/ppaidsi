package dsi.utn.ppai.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class TipoUva {
    private String descripcion;
    private String nombre;
    public boolean sosTipoDeUva(String nombre){
        return (Objects.equals(nombre, this.nombre));
    }
}
