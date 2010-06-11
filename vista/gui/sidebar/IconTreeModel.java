/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.gui.sidebar;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author iuga
 */
public class IconTreeModel implements TreeModel {

    private TreeEntry root;
    protected EventListenerList listenerList = new EventListenerList();

    public IconTreeModel()
    {
    }


    public void RellenarArbol(TreeEntry te)
    {
        root = te;
    }

    public Object getRoot() {
       return root;
    }

    public Object getChild(Object parent, int index) {
        return ((TreeEntry) parent).get(index);
    }

    public int getChildCount(Object parent) {
         return ((TreeEntry) parent).size();
    }

    public boolean isLeaf(Object node) {
        return ((TreeEntry) node).size() == 0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported");
    }

    public int getIndexOfChild(Object parent, Object child) {
       return ((TreeEntry) parent).indexOf(child);
    }

    public void addTreeModelListener(TreeModelListener l) {
       listenerList.add(TreeModelListener.class, l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
       listenerList.remove(TreeModelListener.class, l);
    }

}
