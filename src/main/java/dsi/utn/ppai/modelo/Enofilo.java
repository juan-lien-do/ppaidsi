package dsi.utn.ppai.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Enofilo {
    private int idEnofilo;
    private String apellido;
    private String nombre;
    private Usuario usuario;
    private List<Siguiendo> siguiendos;

    public Enofilo(String apellido, String nombre, Usuario usuario) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.usuario = usuario;
        this.siguiendos = new ArrayList<>();
    }

    public void agregarSiguiendoEnofilo(Enofilo enofilo) {
        siguiendos.add(new Siguiendo(null, LocalDate.now().minusDays(20), enofilo));
    }

    public void agregarSiguiendoBodega(Bodega bodega) {
        siguiendos.add(new Siguiendo(null, LocalDate.now().minusDays(20), bodega));
    }

    public String getNombreUsuario(){
        return this.usuario.getNombre();
    }

    public boolean seguisABodega(Bodega bod) {
        for (Siguiendo sig : siguiendos) {
            if (sig.sosDeBodega(bod)){
                return true;
            }
        }
        return false;
    }
}
