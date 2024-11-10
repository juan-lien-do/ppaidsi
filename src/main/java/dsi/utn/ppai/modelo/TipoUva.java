package dsi.utn.ppai.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TipoUva {
    private int idTipoUva;
    private String descripcion;
    private String nombre;
    public boolean sosTipoDeUva(String nombre){
        return (Objects.equals(nombre, this.nombre));
    }

    public TipoUva(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
}
