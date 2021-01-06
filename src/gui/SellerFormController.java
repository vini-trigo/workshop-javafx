package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller depart;

	private SellerService service;

	private List<DataChangeListener> datacl = new ArrayList<>();
	
	@FXML
	private TextField txtid;

	@FXML
	private TextField txtname;
	
	@FXML
	private TextField txtemail;
	
	@FXML
	private DatePicker dpBirtDate;
	
	@FXML
	private TextField txtBaseSalary;

	@FXML
	private Label labelerror;
	
	@FXML
	private Label labelerroremail;
	
	@FXML
	private Label labelerrorBirthDate;
	
	@FXML
	private Label labelerrorBaseSalary;

	@FXML
	private Button btsave;

	@FXML
	private Button btcancel;

	public void setSeller(Seller depart) {
		this.depart = depart;
	}

	public void setSellerService(SellerService service) {
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

	private Seller getFormD() {
		Seller obj = new Seller();
		
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
		Contraints.setTextFieldInteger(txtid);
		Contraints.setTextFieldMaxLength(txtname, 50);
		Contraints.setTextFieldDouble(txtBaseSalary);
		Contraints.setTextFieldMaxLength(txtemail, 60);
		Utils.formatDatePicker(dpBirtDate, "dd/MM/yyyy");
	}

	public void updateFormData() {
		if (depart == null) {
			throw new IllegalStateException("Entity was null.");
		}
		txtid.setText(String.valueOf(depart.getId()));
		txtname.setText(depart.getName());
		txtemail.setText(depart.getEmail());
		txtBaseSalary.setText(String.format("%.2f", depart.getBaseSalary()));
		if(depart.getBirthDate() != null) {
			dpBirtDate.setValue(LocalDate.ofInstant(depart.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
	}
	
	private void setErrormsg(Map<String, String> error) {
		Set<String> fields = error.keySet();
		
		if(fields.contains("Name")) {
			labelerror.setText(error.get("Name"));
		}
	}

}
