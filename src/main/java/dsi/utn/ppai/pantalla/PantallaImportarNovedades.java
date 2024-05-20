package dsi.utn.ppai.pantalla;

import dsi.utn.ppai.PpaiApplication;
import dsi.utn.ppai.controlador.ControladorImportarNovedades;
import dsi.utn.ppai.inicio.FXMain;
import dsi.utn.ppai.inicio.PantallaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
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

@Getter
@Setter
@Component
public class PantallaImportarNovedades implements Initializable {
    private ControladorImportarNovedades controladorImportarNovedades;
    private static Stage stage;
    private ConfigurableApplicationContext applicationContext;
    @FXML
    private TableView<String> bodegasTabla;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        habilitarPantalla();
    }

    public static void opcionImportarVino(Stage stage) throws IOException {

        PantallaImportarNovedades.stage = stage;
        // cargamos la pantalla principal
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/importarNovedades.fxml"));

        loader.setControllerFactory(PantallaMain.getApplicationContext()::getBean);
        Scene escena = new Scene(loader.load());
        FXMain.getStage().setScene(escena);
        stage.show();

    }

    // habilita el funcionamiento de la pantalla incorporando un controlador
    public void habilitarPantalla(){
        this.controladorImportarNovedades = new ControladorImportarNovedades(this);
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

    public void volverAtras(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        PantallaMain.volverAInicio(stage);
    }
}
