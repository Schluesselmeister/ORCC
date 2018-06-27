/**
 * 
 */
package application.treetable;

import java.util.ArrayList;

import application.Constants;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;
import openhab.OpenhabItem;

/**
 * @author Sven Rehfuﬂ
 *
 */
public class ControlTreeHandler implements ListChangeListener<OpenhabItem> {

	/**
	 * The tree view to be controlled by this handler.
	 */
	private TreeView<String> controllTree;
	
	/*
	 * The list of groups
	 */
	private ArrayList<String> groupList;
	
	public ControlTreeHandler(TreeView localControllTree) {
		controllTree = localControllTree;
		CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("");
		CheckBoxTreeItem<String> groups = new CheckBoxTreeItem<String>("Group");
		groups.setSelected(true);
		root.getChildren().add(groups);
		controllTree.setRoot(root);
		
		controllTree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
 
                return new CheckBoxTreeCell<String>();
            }
        });
	}

	@Override
	public void onChanged(Change<? extends OpenhabItem> change) {
		while (change.next()) {
			CheckBoxTreeItem<String> root = (CheckBoxTreeItem<String>) controllTree.getRoot();
			CheckBoxTreeItem<String> groups = (CheckBoxTreeItem<String>) root.getChildren().get(0);
			
            if (change.wasPermutated()) {

            } else if (change.wasUpdated()) {
            	System.out.println("List update : " + change.toString());
            
            } else {
                    for (OpenhabItem remitem : change.getRemoved()) {
                    	if (remitem.typeProperty().getValueSafe().equals(Constants.OPENHAB_GROUP_TYPE)) {
                    		if (!remitem.nameProperty().getValueSafe().isEmpty()) {
                        		groups.getChildren().remove(remitem.nameProperty().getValue());
                    		} else {
                        		groups.getChildren().remove(remitem.idProperty().getValue());
                    		}
                    	}
                    }
                    for (OpenhabItem additem : change.getAddedSubList()) {
                    	if (additem.typeProperty().getValueSafe().equals(Constants.OPENHAB_GROUP_TYPE)) {
                    		if (!additem.nameProperty().getValueSafe().isEmpty()) {
                    			CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(additem.nameProperty().getValue());
                    			item.setSelected(true);
                        		groups.getChildren().add(item);
                    		} else {
                    			CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(additem.idProperty().getValue());
                    			item.setSelected(true);
                        		groups.getChildren().add(item);
                    		}
                    	}
                    }
                    controllTree.setRoot(root);
                }
            }
	}
	
}
