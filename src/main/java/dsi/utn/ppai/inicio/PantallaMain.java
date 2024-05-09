package dsi.utn.ppai.inicio;

import dsi.utn.ppai.PpaiApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// la idea de esto es que controle la plantilla main
// es la que luego nos lleva a la pantalla importar novedades
@Component
public class PantallaMain implements Initializable {
    @FXML
    private Button botonCTA;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.applicationContext = new SpringApplicationBuilder(PpaiApplication.class).run();


    }

    @FXML
    private void opcionImportarNovedades(){
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/importarNovedades.fxml"));

        loader.setControllerFactory(applicationContext::getBean);
        Scene escena = null;

        try {
            escena = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newStage.setScene(escena);
        newStage.show();
    }

    //una funcion para levantar los otros casos de uso del administrador
    public void opcionNoImplementada(){
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/CUNoImplementado.fxml"));

        loader.setControllerFactory(applicationContext::getBean);
        Scene escena = null;

        try {
            escena = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newStage.setScene(escena);
        newStage.show();
    }
    // otra funcion para salir de la pantalla principal y los casos de uso no implementados
    public void cerrarVentana(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
