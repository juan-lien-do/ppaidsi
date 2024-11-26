package dsi.utn.ppai.notificacion;

import java.util.ArrayList;
import java.util.List;

public interface ISujetoNotificacion {
    List<IObserverNotificacion> observadores = new ArrayList<>();

    void notificar();
    void quitar(IObserverNotificacion o);
    void suscribir(IObserverNotificacion o);
}
