package dsi.utn.ppai.pantalla;

import dsi.utn.ppai.PpaiApplication;
import dsi.utn.ppai.controlador.ControladorImportarNovedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Esta clase controla los elementos de los archivos fxml, es la pantalla.
// IDEA:
// 1 Primero la pantalla de bienvenida
// luego busca las bodegas, si no hay tira el error.
// 2 si hay bodegas entonces tira una lista de bodegas y pide que seleccione una
// 3 tira una pantallita de cargando
// 4 tira un resumen
//
@Component
public class PantallaImportarNovedades implements Initializable {
    private ControladorImportarNovedades controladorImportarNovedades;

    private ConfigurableApplicationContext applicationContext;
    @FXML
    private TableView<String> bodegasTabla;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controladorImportarNovedades = new ControladorImportarNovedades(this);
    }

    public void opcionImportarNovedades() throws IOException {

    }

    public void habilitarPantalla(){

    }

    public void noHayBodegas(){
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/noHayBodegas.fxml"));

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

    public void cerrarVentana(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}