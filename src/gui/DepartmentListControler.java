package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNewAction() {
		System.out.println("Ontio");
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
	
}
