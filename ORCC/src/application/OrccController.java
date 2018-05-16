package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import nz.net.ultraq.preferences.xml.XmlPreferences;
import nz.net.ultraq.preferences.xml.XmlPreferencesFile;
import openhab.RestHandler;

public class OrccController implements Initializable {
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private BorderPane mainPane;
	
	/**
	 * The Openhab server address.
	 */
	private URL serverUrl = null;
	
	/**
	 * The preferences like server URL.
	 */
	private XmlPreferences preferences;
	
	/**
	 * Connect to the Openhab server?
	 */
	private boolean isConnected = false;
	
	Thread workerThread;
	
	private RestHandler restHandler;
	
	
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
			preferences.put(Constants.ServerUrlPref, local_serverUrl.get());	
		}
	}
	
	@FXML
	private void connectToServer() {
		mainPane.setCursor(Cursor.WAIT);

		Task myTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (restHandler.canConnect() == false) {
					isConnected = false;
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
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
					mainPane.setCursor(Cursor.DEFAULT);
					isConnected = true;
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
		preferences = new XmlPreferences(new XmlPreferencesFile("settings.xml"));
		String serverUrl = preferences.get(Constants.ServerUrlPref,"http://localhost:8080/rest/");
		try {
			this.serverUrl = new URL(serverUrl);
			restHandler = new RestHandler(this.serverUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.serverUrl = null;
		}
		
	}   
}
