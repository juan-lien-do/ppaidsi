package dsi.utn.ppai.notificacion;

import dsi.utn.ppai.notificacion.IObserverNotificacion;
import dsi.utn.ppai.notificacion.ImprimirDatosAPantalla;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InterfazNotificacionPush implements IObserverNotificacion {
    private StringBuilder stringBuilder;
    private String[] nombresUsuarios;

    public void enviarNotificacionPush() {
        //System.out.println("Mensaje enviado al usuario: ".concat(nombre));
        int longMensaje = stringBuilder.length();

        for (String nombre : nombresUsuarios) {
            stringBuilder.append(nombre);
            ImprimirDatosAPantalla.imprimir(stringBuilder.toString());
            stringBuilder.setLength(longMensaje);
        }

    }

    // por ahí es _overkill_ utilizar un string builder para enviar 3 notificaciones, pero
    // se supone que esto es escalable.
    @Override
    public void enviarNotificacion(String nombreBodega, String[] nombresUsuarios, String textoNotificacion,
                                   LocalDate fechaActual, String[] vinosActualizados, String[] vinosNuevos) {
        this.nombresUsuarios = nombresUsuarios;
        stringBuilder = new StringBuilder();
        stringBuilder.append("La bodega ");
        stringBuilder.append(nombreBodega);
        if (vinosNuevos.length>0) stringBuilder.append(" incorporó los vinos: ");
        for (String nomVino :
                vinosNuevos) {
            stringBuilder.append(nomVino);
            stringBuilder.append(", ");
        }
        if (vinosActualizados.length>0) stringBuilder.append("; actualizó los vinos: ");
        for (String nomVino :
                vinosActualizados) {
            stringBuilder.append(nomVino);
            stringBuilder.append(", ");
        }
        stringBuilder.append(textoNotificacion);

        enviarNotificacionPush();
    }
}
