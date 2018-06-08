/**
 * 
 */
package application.treetable;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeView;
import openhab.OpenhabItem;

/**
 * @author Sven Rehfuﬂ
 *
 */
public class ControlTreeHandler implements ListChangeListener<OpenhabItem> {

	/**
	 * The tree view to be controlled by this handler.
	 */
	private TreeView<?> controllTree;
	
	
	public ControlTreeHandler(TreeView localControllTree) {
		controllTree = localControllTree;
	}

	@Override
	public void onChanged(Change<? extends OpenhabItem> change) {
		while (change.next()) {
            if (change.wasPermutated()) {

            } else if (change.wasUpdated()) {
                         //update item
                } else {
                    for (OpenhabItem remitem : change.getRemoved()) {
 //                       remitem.remove(Outer.this);
                    }
                    for (OpenhabItem additem : change.getAddedSubList()) {
 //                       additem.add(Outer.this);
                    }
                }
            }
	}
	
}
