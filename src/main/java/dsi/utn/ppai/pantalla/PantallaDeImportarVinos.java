package dsi.utn.ppai.pantalla;

import dsi.utn.ppai.PpaiApplication;
import dsi.utn.ppai.controlador.GestorImportacionVinos;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
@Component
public class PantallaDeImportarVinos implements Initializable {
    private GestorImportacionVinos gestorImportacionVinos;

    @FXML
    private AnchorPane divNoHayBodegas;
    @FXML
    private AnchorPane divEspera;
    @FXML
    private AnchorPane divSeleccionBodega;
    @FXML
    private ListView<String> listViewBodegas;
    @FXML
    private AnchorPane divResumen;
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
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/importarNovedades.fxml"));
        loader.setControllerFactory(PantallaMain.getApplicationContext()::getBean);
        Scene escena = new Scene(loader.load());
        FXMain.getStage().setScene(escena);
        stage.show();
    }

    public void habilitarPantalla() {
        this.gestorImportacionVinos = new GestorImportacionVinos(this);
        gestorImportacionVinos.importarVinos();
    }

    public void mostrarBodegas(List<String> nombres) {
        divEspera.setVisible(false);
        divSeleccionBodega.setVisible(true);
        listViewBodegas.getItems().addAll(nombres);
        listViewBodegas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void tomarSeleccionBodega() {
        String selectedItem = listViewBodegas.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            gestorImportacionVinos.tomarSeleccionBodega(selectedItem);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor elija una bodega.");
            alert.showAndWait();
        }
    }

    public void noHayBodegas() {
        divResumen.setVisible(false);
        divSeleccionBodega.setVisible(false);
        divEspera.setVisible(false);
        divNoHayBodegas.setVisible(true);

    }


    public void volverAtras(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        PantallaMain.volverAInicio(stage);
    }

    public void mostrarResumenDeVinos(String nombre, List<String> actualizados, List<String> nuevos) {
        this.divEspera.setVisible(false);
        this.divSeleccionBodega.setVisible(false);
        this.divResumen.setVisible(true);
        this.textoNovedad.setText(new StringBuilder().append("En la bodega ")
                .append(nombre).append(" hay ").append(String.valueOf((actualizados.size() + nuevos.size())))
                .append(" vinos actualizados.").toString());
        listViewVinosCambiados.getItems().addAll(actualizados);
        listViewVinosNuevos.getItems().addAll(nuevos);
    }
}
