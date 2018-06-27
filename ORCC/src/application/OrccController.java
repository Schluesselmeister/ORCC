package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.treetable.ControlTreeHandler;
import application.treetable.TreeTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import nz.net.ultraq.preferences.xml.XmlPreferences;
import nz.net.ultraq.preferences.xml.XmlPreferencesFile;
import openhab.OpenhabItem;
import openhab.RestHandler;
import openhab.RestItem;

public class OrccController implements Initializable {
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private TreeView controlTree;
	
	@FXML
	private TreeTableView<OpenhabItem> navTree;
	
	@FXML
	private TextField filterTextField;
	
	@FXML
	private Button filterActionButton;
	@FXML
	private Button filterClearButton;
	
	@FXML
	private Text connectedInfoField;
	
	/**
	 * The Openhab server address.
	 */
	private String serverUrl;
	
	/**
	 * The preferences like server URL.
	 */
	private XmlPreferences preferences;
	
	/**
	 * Connect to the Openhab server?
	 */
	private boolean isConnected = false;
	
	/**
	 * The worker thread for long operations.
	 */
	Thread workerThread;
	
	/**
	 * The rest handler to get the rest objects as POJO.
	 */
	private RestHandler restHandler;
	
	/**
	 * Handler for the tree table view.
	 */
	private TreeTableHandler treeTableHandler;
	
	/**
	 * Control tree handler
	 */
	private ControlTreeHandler controlTreeHandler;
	
	/**
	 * The list of all Openhab items.
	 */
	ObservableList<OpenhabItem> allItems = FXCollections.observableArrayList();
	
	/**
	 * Enter the Openhab server URL in a modal dialog
	 * @param event
	 */
	@FXML
	private void enterServerUrl(ActionEvent event) {
		TextInputDialog serverUrlDialog = new TextInputDialog();
		serverUrlDialog.setTitle("Openhab server URL");
		serverUrlDialog.setHeaderText(null);
		serverUrlDialog.initModality(Modality.WINDOW_MODAL);
		serverUrlDialog.setContentText("Please enter the Openhab server URL.");
		serverUrlDialog.getEditor().setText(this.serverUrl.toString());
		serverUrlDialog.getEditor().setMinWidth(300);

		Optional<String> local_serverUrl = serverUrlDialog.showAndWait();
		if (local_serverUrl.isPresent()) {
			preferences.put(Constants.SERVER_URL_PREF, local_serverUrl.get());
			preferences.flush();
			serverUrl = local_serverUrl.get();
		}
	}
	
	/**
	 * Handler for connect button. Connect to the server and download the list of all Openhab items.
	 */
	@FXML
	private void connectToServer() {
		mainPane.setCursor(Cursor.WAIT);

		Task<Void> myTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (restHandler.canConnect() == false) {
					isConnected = false;
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							connectedInfoField.setText("DISCONNECTED");
							mainPane.setCursor(Cursor.DEFAULT);
							Alert alertDialog = new Alert(AlertType.ERROR);
							alertDialog.setHeaderText(null);
							alertDialog.setTitle("Connection Error");
							alertDialog.setContentText("Cannot connect to the server \n\r" + serverUrl);
							alertDialog.initModality(Modality.WINDOW_MODAL);
							alertDialog.showAndWait();
						}
					});
				} else {
					isConnected = true;

					try {
						connectedInfoField.setText("CONNECTED");
						allItems.addAll(restHandler.getAllItems());
//						restHandler.subscribeToSSE();
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								mainPane.setCursor(Cursor.DEFAULT);
							}
						});
					} catch (Exception ex) {
						isConnected = false;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								connectedInfoField.setText("DISCONNECTED");
								mainPane.setCursor(Cursor.DEFAULT);
								Alert alertDialog = new Alert(AlertType.ERROR);
								alertDialog.setHeaderText(null);
								alertDialog.setTitle("Connection Error");
								alertDialog.setContentText("Cannot download the list of items. \r\n" + ex.getMessage());
								alertDialog.initModality(Modality.WINDOW_MODAL);
								alertDialog.showAndWait();
							}
						});
					}
				}
				return null;
			}
		};
		
		workerThread = new Thread(myTask);
		workerThread.start();
	}
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		menuBar.setFocusTraversable(true);
		preferences = new XmlPreferences(new XmlPreferencesFile("settings"));
		String serverUrl = preferences.get(Constants.SERVER_URL_PREF,"http://localhost:8080/rest/");
		this.serverUrl = serverUrl;
		restHandler = new RestHandler(this.serverUrl);
		treeTableHandler = new TreeTableHandler(navTree, allItems); 
		controlTreeHandler = new ControlTreeHandler(controlTree);
		allItems.addListener(treeTableHandler);
		allItems.addListener(controlTreeHandler);
		filterTextField.textProperty().addListener((obj, oldVal, newVal) -> {
			handleFilterButton();
		});
	}   

	
	/*
	 * Handle the filter button CLEAR
	 */
	@FXML
	private void handleFilterClearButton() {
		filterTextField.setText("");
		treeTableHandler.setTextFilter("");
	}

	/*
	 * Handle the filter button FILTER
	 */
	@FXML
	private void handleFilterButton() {
		String localString = filterTextField.getText();
		if (localString != null ) {
			treeTableHandler.setTextFilter(localString);
		}
	}
}
