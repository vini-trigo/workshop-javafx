package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListControler implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableview;

	@FXML
	private TableColumn<Seller, Integer> tableid;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEmail;

	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;

	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;

	@FXML
	private TableColumn<Seller, String> tablename;

	@FXML
	TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btnew;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage pt = Utils.currentStage(event);
		Seller obj = new Seller();
		CreateDialogForm(obj, "/gui/SellerForm.fxml", pt);
		;
	}

	private ObservableList<Seller> obslist;

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableid.setCellValueFactory(new PropertyValueFactory<>("id"));
		tablename.setCellValueFactory(new PropertyValueFactory<>("Name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		Stage stage = (Stage) Main.mainSecne.getWindow();
		tableview.prefHeightProperty().bind(stage.heightProperty());
	}

	public void UpdateView() {
		if (service == null) {
			throw new IllegalStateException("Service estava nulo.");
		}
		List<Seller> list = service.findAll();
		obslist = FXCollections.observableArrayList(list);
		tableview.setItems(obslist);
		initEditButtons();
		initRemoveButtons();
	}

	private void CreateDialogForm(Seller obj, String name, Stage pt) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
//			Pane pane = loader.load();
//
//			SellerFormController contrel = loader.getController();
//			contrel.setSeller(obj);
//			contrel.setSellerService(new SellerService());
//			contrel.SubscribeData(this);
//			contrel.updateFormData();
//
//			Stage dialog = new Stage();
//			dialog.setTitle("Enter department data");
//			dialog.setScene(new Scene(pane));
//			dialog.setResizable(false);
//			dialog.initOwner(pt);
//			dialog.initModality(Modality.WINDOW_MODAL);
//			dialog.showAndWait();
//
//		} catch (IOException s) {
//			Alerts.showAlert("IO Execpition", "Error loading view", s.getMessage(), AlertType.ERROR);
//		}
	}

	@Override
	public void onDataChange() {
		UpdateView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> CreateDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "You are sure to deletd");
		
		if(result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
			service.remove(obj);
			UpdateView();
			}
			catch(DbIntegrityException s) {
				Alerts.showAlert("Error remove object", null, s.getMessage(), AlertType.ERROR);
			}
		}
	}

}
