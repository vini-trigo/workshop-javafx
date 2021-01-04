package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem miSeller;
	@FXML
	private MenuItem miDepartment;
	@FXML
	private MenuItem miAbout;
	
	@FXML
	public void menuItemSellerAction() {
		loadview("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void menuItemAboutAction() {
		loadview("/gui/About.fxml");
	}
	
	@FXML
	public void menuItemDepartmentAction() {
		loadview("/gui/DepartmentList.fxml");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	@SuppressWarnings("unused")
	private synchronized void loadview(String name) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
		try {
			VBox nb = loader.load();
			
			Scene mainSene = Main.sc();
			VBox mainVb = (VBox) ((ScrollPane) mainSene.getRoot()).getContent();
			
			Node mainMenu = mainVb.getChildren().get(0);
			mainVb.getChildren().clear();
			mainVb.getChildren().add(mainMenu);
			mainVb.getChildren().addAll(nb.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading View", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
