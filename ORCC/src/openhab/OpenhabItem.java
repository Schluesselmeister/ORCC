/**
 * 
 */
package openhab;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * An Openhab item usable in a TreeTableView.
 * 
 * @author Sven Rehfu�
 *
 */
public class OpenhabItem {

	private RestItem restItem;
	private SimpleStringProperty name;
	private ReadOnlyStringWrapper id;
	private SimpleStringProperty type;
	private SimpleStringProperty category;
	private SimpleStringProperty state;
	
	public OpenhabItem(RestItem restItem) {
		if (restItem != null) {
			this.restItem = restItem;
			name = new SimpleStringProperty(this, "name", restItem.getLabel());
			id = new ReadOnlyStringWrapper(this, "id", restItem.getName());
			type = new SimpleStringProperty(this, "type", restItem.getType());
			category = new SimpleStringProperty(this, "category", restItem.getCategory());
			if (!restItem.getState().equals("NULL")) {
				state  = new SimpleStringProperty(this, "state", restItem.getState());
			} else {
				state = new SimpleStringProperty(this, "state", "");
			}
		}
	}
	
	
	public SimpleStringProperty nameProperty() {
		return name;
	}
	public SimpleStringProperty idProperty() {
		return id;
	}
	public SimpleStringProperty typeProperty() {
		return type;
	}
	public SimpleStringProperty categoryProperty() {
		return category;
	}
	public SimpleStringProperty stateProperty() {
		return state;
	}
	
	public RestItem getRestItem() {
		return restItem;
	}
	
}
