package dsi.utn.ppai.inicio;

import dsi.utn.ppai.PpaiApplication;
import dsi.utn.ppai.pantalla.PantallaImportarNovedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PantallaMain implements Initializable {
    @Getter
    private static Stage stage;
    @Getter
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PantallaMain.applicationContext = new SpringApplicationBuilder(PpaiApplication.class).run();
    }

    public static void volverAInicio(Stage stage) throws IOException {
        PantallaMain.stage = stage;
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/main.fxml"));
        loader.setControllerFactory(PantallaMain.getApplicationContext()::getBean);
        Scene escena = new Scene(loader.load());
        FXMain.getStage().setScene(escena);
        stage.show();
    }

    @FXML
    public void opcionImportarNovedades(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        PantallaImportarNovedades.opcionImportarVino(stage);
    }

    public void opcionNoImplementada() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No disponible");
        alert.setHeaderText(null);
        alert.setContentText("Caso de uso no implementado.");
        alert.showAndWait();

    }

    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
