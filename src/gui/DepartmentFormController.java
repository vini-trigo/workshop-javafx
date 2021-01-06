package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Contraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department depart;

	private DepartmentService service;

	private List<DataChangeListener> datacl = new ArrayList<>();
	
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

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	public void SubscribeData(DataChangeListener listener) {
		datacl.add(listener);
	}
	
	
	
	@FXML
	public void onBtSave(ActionEvent event) {
		if (depart == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Entity was null");
		}
		try {
			depart = getFormD();
			service.SaveOrupadte(depart);
			notifyData();
			Utils.currentStage(event).close();
			
		} 
		catch(ValidationException d) {
			setErrormsg(d.getError());
		}
		catch (DbException a) {
			Alerts.showAlert("Error save objet", null, a.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyData() {
		for(DataChangeListener listener : datacl){
			listener.onDataChange();
		}
	}

	private Department getFormD() {
		Department obj = new Department();
		
		ValidationException exce = new ValidationException("Validation Error");
		
		obj.setId(Utils.TryparseInt(txtid.getText()));
		
		if(txtname.getText() == null || txtname.getText().trim().equals("")) {
			exce.addError("Name", "O campo não pode ser vazio");
		}
		obj.setName(txtname.getText());

		if(exce.getError().size() > 0) {
			throw exce;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancel(ActionEvent event) {
		Utils.currentStage(event).close();
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
		if (depart == null) {
			throw new IllegalStateException("Entity was null.");
		}
		txtid.setText(String.valueOf(depart.getId()));
		txtname.setText(depart.getName());

	}
	
	private void setErrormsg(Map<String, String> error) {
		Set<String> fields = error.keySet();
		
		if(fields.contains("Name")) {
			labelerror.setText(error.get("Name"));
		}
	}

}
