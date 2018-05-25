/**
 * 
 */
package application.treetable;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
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
	private FilteredList<OpenhabItem> allItems;

	/**
	 * The text filter to be applied.
	 */
	private String textFilter = "";
	
	/**
	 * 
	 * @param navTree
	 * @param allItemsObservable
	 */
	public TreeTableHandler(TreeTableView<OpenhabItem> navTree, ObservableList<OpenhabItem> allItemsObservable) {
		this.navTree = navTree;
		this.allItems = new FilteredList<OpenhabItem>(allItemsObservable,null);

		navTree.editableProperty().set(true);
		
		TreeTableColumn<OpenhabItem,String> nameCol = new TreeTableColumn<>("Name");
		nameCol.editableProperty().set(true);

		nameCol.setCellFactory( TextFieldTreeTableCell.forTreeTableColumn() ); 
		TreeTableColumn<OpenhabItem,String> idCol = new TreeTableColumn<>("ID");
		idCol.editableProperty().set(false);

		TreeTableColumn<OpenhabItem,String> typeCol = new TreeTableColumn<>("Type");

		TreeTableColumn<OpenhabItem,String> categoryCol = new TreeTableColumn<>("Category");
		categoryCol.editableProperty().set(true);
		categoryCol.setCellFactory( TextFieldTreeTableCell.forTreeTableColumn() ); 

		TreeTableColumn<OpenhabItem,String> stateCol = new TreeTableColumn<>("State");
		
		TreeItem<OpenhabItem> root = new TreeItem<>(new OpenhabItem(null));
		navTree.setRoot(root);
		
		for (OpenhabItem oneItem : allItems) {
			TreeItem<OpenhabItem> oneTreeItem = new TreeItem<OpenhabItem>(oneItem);
			root.getChildren().add(oneTreeItem);
		}

		nameCol.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
		idCol.setCellValueFactory(new TreeItemPropertyValueFactory("id"));
		typeCol.setCellValueFactory(new TreeItemPropertyValueFactory("type"));
		categoryCol.setCellValueFactory(new TreeItemPropertyValueFactory("category"));
		stateCol.setCellValueFactory(new TreeItemPropertyValueFactory("state"));

		navTree.getColumns().setAll(nameCol, idCol, typeCol, categoryCol, stateCol);
		
	}
	
	
	public void setTextFilter(String filterText) {
		// TODO: add free text filtering
	}
	
}
