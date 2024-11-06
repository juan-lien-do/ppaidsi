package dsi.utn.ppai;

import dsi.utn.ppai.inicio.FXMain;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class PpaiApplication {

	public static void main(String[] args) {
		Application.launch(FXMain.class);
	}

}
