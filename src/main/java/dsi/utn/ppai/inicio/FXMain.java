package dsi.utn.ppai.inicio;

import dsi.utn.ppai.PpaiApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


//
@ComponentScan("dsi.utn.ppai.repositorios")
@SpringBootApplication//(exclude = { DataSourceAutoConfiguration.class })
public class FXMain extends Application {

    @Getter
    private static ConfigurableApplicationContext applicationContext;

    @Getter
    @Setter
    private static Stage stage;

    @Override
    public void init(){
        FXMain.applicationContext = new SpringApplicationBuilder(PpaiApplication.class).run();
    }

    // Un stage tiene adentro muchas Scenes
    @Override
    public void start(Stage stage) throws Exception {
        FXMain.stage = stage;
        // cargamos la pantalla principal
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/main.fxml"));

        loader.setControllerFactory(applicationContext::getBean);
        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.setTitle("Aplicacion Bonvino");
        stage.show();

    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
