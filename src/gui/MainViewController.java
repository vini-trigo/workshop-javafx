package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem miSeller;
	@FXML
	private MenuItem miDepartment;
	@FXML
	private MenuItem miAbout;
	
	@FXML
	public void menuItemSellerAction() {
		System.out.println("alo");
	}
	
	@FXML
	public void menuItemAboutAction() {
		System.out.println("cobalt");
	}
	
	@FXML
	public void menuItemDepartmentAction() {
		System.out.println("ola");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		
	}

	
}
