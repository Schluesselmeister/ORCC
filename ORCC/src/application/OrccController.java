package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nz.net.ultraq.preferences.xml.XmlPreferences;

public class OrccController implements Initializable {
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private BorderPane mainPane;
	
	/**
	 * The Openhab server address.
	 */
	private URL serverUrl;
	
	/**
	 * The preferences like server URL.
	 */
	private XmlPreferences preferences;
	
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

		Optional<String> local_serverUrl = serverUrlDialog.showAndWait();
		if (local_serverUrl.isPresent()) {
		}
	}
	
	
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		menuBar.setFocusTraversable(true);

	}   
}
