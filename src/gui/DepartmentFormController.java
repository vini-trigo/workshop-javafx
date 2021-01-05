package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Contraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	private Department depart;
	
	@FXML
	private TextField txtid;
	
	@FXML
	private TextField txtname;
	
	@FXML
	private Label labelerror;
	
	@FXML
	private Button btsave;

	@FXML
	private Button btcancel;
	
	public void setDepartment(Department depart) {
		this.depart = depart;
	}
	
	@FXML
	public void onBtSave() {
		System.out.println("Sarve");
	}
	
	@FXML
	public void onBtCancel() {
		System.out.println("irra");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initialize();
	}

	private void initialize() {
		Contraints.setTextFieldDouble(txtid);
		Contraints.setTextFieldMaxLength(txtname, 30);
	}
	
	public void updateFormData() {
		if(depart == null) {
			throw new IllegalStateException("Entity was null.");
		}
		txtid.setText(String.valueOf(depart.getId()));
		txtname.setText(depart.getName());
		
	}
	
}
