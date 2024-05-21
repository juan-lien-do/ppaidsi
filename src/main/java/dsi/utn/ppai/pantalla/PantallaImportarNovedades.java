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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
@Component
public class PantallaImportarNovedades implements Initializable {
    private ControladorImportarNovedades controladorImportarNovedades;
    private static Stage stage;
    private ConfigurableApplicationContext applicationContext;

    @FXML
    private AnchorPane pantallaNoHayBodegas;
    @FXML
    private AnchorPane pantallaEspera;
    @FXML
    private AnchorPane pantallaSeleccionBodega;
    @FXML
    private ListView<String> listView;
    @FXML
    private AnchorPane pantallaResumen;
    @FXML
    private Label textoNovedad;
    @FXML
    private ListView<String> listViewVinosNuevos;
    @FXML
    private ListView<String> listViewVinosCambiados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        habilitarPantalla();
    }

    public static void opcionImportarVino(Stage stage) throws IOException {
        PantallaImportarNovedades.stage = stage;
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/importarNovedades.fxml"));
        loader.setControllerFactory(PantallaMain.getApplicationContext()::getBean);
        Scene escena = new Scene(loader.load());
        FXMain.getStage().setScene(escena);
        stage.show();
    }

    public void habilitarPantalla() {
        this.controladorImportarNovedades = new ControladorImportarNovedades(this);
        controladorImportarNovedades.importarVinos();
    }

    public void mostrarBodegas(List<String> nombres) {
        pantallaEspera.setVisible(false);
        pantallaSeleccionBodega.setVisible(true);
        listView.getItems().addAll(nombres);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void tomarSeleccionBodega() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            controladorImportarNovedades.tomarSeleccionBodega(selectedItem);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor elija una bodega.");
            alert.showAndWait();
        }
    }

    public void noHayBodegas() {
        pantallaResumen.setVisible(false);
        pantallaSeleccionBodega.setVisible(false);
        pantallaEspera.setVisible(false);
        pantallaNoHayBodegas.setVisible(true);

    }

    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void volverAtras(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        PantallaMain.volverAInicio(stage);
    }

    public void mostrarResumenDeVinos(String nombre, List<String> actualizados, List<String> nuevos) {
        this.pantallaEspera.setVisible(false);
        this.pantallaSeleccionBodega.setVisible(false);
        this.pantallaResumen.setVisible(true);
        this.textoNovedad.setText(new StringBuilder().append("En la bodega ").append(nombre).append(" hay ").append(String.valueOf((actualizados.size() + nuevos.size()))).append(" vinos nuevos.").toString());
        listViewVinosCambiados.getItems().addAll(actualizados);
        listViewVinosNuevos.getItems().addAll(nuevos);
    }
}
