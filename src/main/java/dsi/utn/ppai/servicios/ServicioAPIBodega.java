package dsi.utn.ppai.servicios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que se encarga de hacer peticiones a los sistemas de bodega.
 * <b>En realidad le estamos haciendo todas las peticiones a un mismo sistema porque no nos dá
 * la memoria RAM para simularlos a todos.</b>
 * <p>Es un servicio inyectable pero se puede instanciar así:</p>
 * <pre>
 *     InterfazAPI interfazAPI = new InterfazAPI();
 * </pre>
 * <p>Gracias a Jackson puede parsear los arrays</p>
 */
@Service
public class ServicioAPIBodega {
    /**
     *
     * @param urlAPI el urlAPI de la bodega, al cual vamos a hacer la petición.
     * @return una lista de vinosDTO, parseados
     * @throws ResourceAccessException si el sistema de bodega está apagado.
     */
    public List<VinoDataHolder> consultarNovedades(String urlAPI) throws ResourceAccessException {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<VinoDataHolder[]> res = template.getForEntity(
                    "http://localhost:8082/bodegas/".concat(urlAPI),
                    VinoDataHolder[].class

            );
            VinoDataHolder[] vinitos = res.getBody();

            /*for (VinoDataHolder vin : vinitos) {
                System.out.println(vin);
            }*/

            return Arrays.stream(res.getBody()).toList();

        } catch (ResourceAccessException errorException) {
            System.out.println("Error en la petición. Compruebe que el sistema de bodega no esté apagado.");
            throw errorException;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception.getClass());
            return null;

        }


    }
}
