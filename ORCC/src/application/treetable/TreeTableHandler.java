/**
 * 
 */
package application.treetable;

import java.util.Observable;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import openhab.OpenhabItem;

/**
 * Populate and handle the tree view showing the Openhab items.
 * 
 * @author Sven Rehfuﬂ
 *
 */
public class TreeTableHandler implements ListChangeListener<OpenhabItem> {

	public final static int GROUP = 0;
	public final static int TYPE = 1;

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
		this.allItems = new FilteredList<OpenhabItem>(allItemsObservable);

		navTree.editableProperty().set(true);

		TreeTableColumn<OpenhabItem, String> nameCol = new TreeTableColumn<>("Name");
		nameCol.editableProperty().set(true);

		nameCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		TreeTableColumn<OpenhabItem, String> idCol = new TreeTableColumn<>("ID");
		idCol.editableProperty().set(false);

		TreeTableColumn<OpenhabItem, String> typeCol = new TreeTableColumn<>("Type");

		TreeTableColumn<OpenhabItem, String> categoryCol = new TreeTableColumn<>("Category");
		categoryCol.editableProperty().set(true);
		categoryCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

		TreeTableColumn<OpenhabItem, String> stateCol = new TreeTableColumn<>("State");

		createTree();

		nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<OpenhabItem, String>("name"));
		idCol.setCellValueFactory(new TreeItemPropertyValueFactory<OpenhabItem, String>("id"));
		typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<OpenhabItem, String>("type"));
		categoryCol.setCellValueFactory(new TreeItemPropertyValueFactory<OpenhabItem, String>("category"));
		stateCol.setCellValueFactory(new TreeItemPropertyValueFactory<OpenhabItem, String>("state"));

		navTree.getColumns().setAll(nameCol, idCol, typeCol, categoryCol, stateCol);
	}

	public void setTextFilter(String localTextFilter) {
		textFilter = localTextFilter.toLowerCase();
		refreshFilter();
	}

	private void createTree() {
		TreeItem<OpenhabItem> root = new TreeItem<>(new OpenhabItem(null));

		for (OpenhabItem oneItem : allItems) {
			TreeItem<OpenhabItem> oneTreeItem = new TreeItem<OpenhabItem>(oneItem);
			root.getChildren().add(oneTreeItem);
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				navTree.setRoot(root);
			}
		});
	}

	private void refreshFilter() {
		allItems.setPredicate(item -> {
			boolean retVal = false;

			if (!textFilter.isEmpty()) {
				// text filter has been set.
				if (item.nameProperty().getValueSafe().toLowerCase().contains(textFilter)
						|| item.idProperty().getValueSafe().toLowerCase().contains(textFilter)
						|| item.categoryProperty().getValueSafe().toLowerCase().contains(textFilter)) {
					retVal = true;
				}
			} else {
				// no text filter
				retVal = true;
			}

			return retVal;
		});

		createTree();
	}

	@Override
	public void onChanged(Change<? extends OpenhabItem> c) {
		// TODO Auto-generated method stub
		while (c.next()) {
			if (c.wasUpdated()) {

			} else if (c.wasPermutated()) {
				// nothing to do
			} else {
				createTree();
			}
		}
	}
}
