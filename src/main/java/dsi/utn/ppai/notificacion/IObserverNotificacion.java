package dsi.utn.ppai.notificacion;

import java.time.LocalDate;

public interface IObserverNotificacion {
    public void enviarNotificacion(String nombreBodega, String[] nombresUsuarios, String textoNotificacion,
                                   LocalDate fechaActual, String[] vinosActualizados, String[] vinosNuevos);
}
