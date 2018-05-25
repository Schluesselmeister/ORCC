/**
 * 
 */
package application.treetable;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import openhab.OpenhabItem;
import openhab.RestItem;

/**
 * Populate and handle the tree view showing the Openhab items.
 * @author Sven Rehfuﬂ
 *
 */
public class TreeTableHandler {
	/**
	 * The tree view.
	 */
	private TreeTableView<OpenhabItem> navTree;
	
	/**
	 * Observable list of all Openhab items.
	 */
	private ObservableList<OpenhabItem> allItems;
	
	
	
	public TreeTableHandler(TreeTableView<OpenhabItem> navTree, ObservableList<OpenhabItem> allItems) {
		this.navTree = navTree;
		this.allItems = allItems;

		TreeTableColumn<OpenhabItem,String> nameCol = new TreeTableColumn<>("Name");
		TreeTableColumn<OpenhabItem,String> idCol = new TreeTableColumn<>("ID");
		TreeTableColumn<OpenhabItem,String> typeCol = new TreeTableColumn<>("Type");
		TreeTableColumn<OpenhabItem,String> categoryCol = new TreeTableColumn<>("Category");

		TreeItem<OpenhabItem> root = new TreeItem<>(new OpenhabItem(null));
		navTree.setRoot(root);
		
		
		for (OpenhabItem oneItem : allItems) {
			root.getChildren().add(new TreeItem<OpenhabItem>(oneItem));
		}

		nameCol.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
		idCol.setCellValueFactory(new TreeItemPropertyValueFactory("id"));
		typeCol.setCellValueFactory(new TreeItemPropertyValueFactory("type"));
		categoryCol.setCellValueFactory(new TreeItemPropertyValueFactory("category"));

		navTree.getColumns().setAll(nameCol, idCol, typeCol, categoryCol);
		
	}
}
