package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListControler implements Initializable{

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableview;
	
	@FXML
	private TableColumn<Department, Integer> tableid;
	
	@FXML
	private TableColumn<Department, String> tablename;
	
	@FXML
	private Button btnew;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage pt = Utils.currentStage(event);
		Department obj = new Department();
		CreateDialogForm(obj, "/gui/DepartmentForm.fxml", pt);;
	}
	
	private ObservableList<Department> obslist;
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tablename.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Stage stage = (Stage) Main.mainSecne.getWindow();
		tableview.prefHeightProperty().bind(stage.heightProperty());
	}

	public void UpdateView() {
		if(service == null) {
			throw new IllegalStateException("Service estava nulo.");
		}
		List<Department> list = service.findAll();
		obslist = FXCollections.observableArrayList(list);
		tableview.setItems(obslist);
	}
	
	private void CreateDialogForm(Department obj,String name, Stage pt) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
			Pane pane = loader.load();
			
			DepartmentFormController contrel = loader.getController();
			contrel.setDepartment(obj);
			contrel.updateFormData();
			
			Stage dialog = new Stage();
			dialog.setTitle("Enter department data");
			dialog.setScene(new Scene(pane));
			dialog.setResizable(false);
			dialog.initOwner(pt);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.showAndWait();
			
		}catch(IOException s) {
			Alerts.showAlert("IO Execpition", "Error loading view", s.getMessage(), AlertType.ERROR);
		}
	}
	
}
