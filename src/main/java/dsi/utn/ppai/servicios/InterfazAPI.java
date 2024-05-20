package dsi.utn.ppai.servicios;

import dsi.utn.ppai.dominio.Vino;
import dsi.utn.ppai.utilidades.VinoDataHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class InterfazAPI {
    public List<VinoDataHolder> consultarNovedades(String nombre){
        try{
            RestTemplate template = new RestTemplate();
            ResponseEntity<List<VinoDataHolder>> res = template.exchange(
                    "https://localhost:8082/bodegas/{nombre}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<VinoDataHolder>>() {},
                    nombre
            );
            List<VinoDataHolder> vinos = res.getBody();
            System.out.println(vinos);
            return vinos;

        }catch (HttpClientErrorException errorException){
            System.out.println("Error en la petición :(");
            return null;
        }

    }
}
