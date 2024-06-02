package dsi.utn.ppai.servicios;

import dsi.utn.ppai.utilidades.VinoDataHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@Service
public class InterfazAPI {
    public List<VinoDataHolder> consultarNovedades(String nombre) {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<VinoDataHolder[]> res = template.getForEntity(
                    "http://localhost:8082/bodegas/".concat(nombre),
                    VinoDataHolder[].class

            );
            VinoDataHolder[] vinitos = res.getBody();

            for (VinoDataHolder vin : vinitos) {
                System.out.println(vin);
            }

            List<VinoDataHolder> vinos = Arrays.stream(res.getBody()).toList();

            return vinos;

        } catch (HttpClientErrorException errorException) {
            System.out.println("Error en la petición :(");
            return null;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;

        }


    }
}
