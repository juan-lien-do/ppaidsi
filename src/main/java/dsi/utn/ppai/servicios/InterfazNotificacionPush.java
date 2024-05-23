package dsi.utn.ppai.servicios;

import org.springframework.stereotype.Service;

@Service
public class InterfazNotificacionPush {

    public void enviarNotificacion(String nombre) {
        System.out.println("Mensaje enviado al usuario: ".concat(nombre));
    }
}
