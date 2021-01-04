package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Scene mainSecne;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrolparent = loader.load();
			
			scrolparent.setFitToHeight(true);
			scrolparent.setFitToWidth(true);
			
			mainSecne = new Scene(scrolparent);
			primaryStage.setScene(mainSecne);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene sc() {
		return mainSecne;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
