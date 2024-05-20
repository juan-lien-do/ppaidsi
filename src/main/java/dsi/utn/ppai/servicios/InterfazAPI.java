package dsi.utn.ppai.servicios;

import dsi.utn.ppai.dominio.Vino;
import dsi.utn.ppai.utilidades.VinoDataHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InterfazAPI {
    public List<VinoDataHolder> consultarNovedades(String nombre) {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<VinoDataHolder[]> res = template.getForEntity(
                    "http://localhost:8082/bodegas/{nombre}",
                    VinoDataHolder[].class,
                    nombre
            );
            VinoDataHolder[] vinitos = res.getBody();

            for (VinoDataHolder x : vinitos) {
                System.out.println(x);
            }

            List<VinoDataHolder> vinos = Arrays.stream(res.getBody()).toList();

            //vinos.stream().map(x -> System.out.println(x));
            //System.out.println(vinos);
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
