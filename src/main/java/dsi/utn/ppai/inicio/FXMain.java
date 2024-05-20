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


//
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FXMain extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Getter
    private static Stage stage;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(PpaiApplication.class).run();
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
        stage.show();



        /*
        Button btn2 = new Button("Escena 2");
        btn2.setTranslateX(10);
        btn2.setOnAction(event -> {
            System.out.println("lol");
            StackPane root2 = new StackPane();
            root2.getChildren().add(new Button("Soy la escena 2"));
            Scene scene2 = new Scene(root2, 300, 200);
            stage.setScene(scene2);
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn2);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Cambio de Escenas");
        stage.setScene(scene);
        stage.show();*/
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
