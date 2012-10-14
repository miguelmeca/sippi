/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ejecucion.lanzamiento;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import util.Tupla;

/**
 *
 * @author Emmanuel
 */
public class tablaItemOrdenesDeCompraCellEditor implements TableCellEditor {

    protected Hashtable editors;

    protected TableCellEditor editor, defaultEditor;
    
    protected ArrayList<Tupla> valores;
  
    JTable table;
    
    public tablaItemOrdenesDeCompraCellEditor(JTable table, ArrayList<Tupla> valores) {
        this.table = table;
        this.valores = valores;
        editors = new Hashtable();
        JComboBox combo = new JComboBox();
        Iterator<Tupla> itValores = valores.iterator();
        while(itValores.hasNext())
        {
            Tupla tupla = itValores.next();
            combo.addItem(tupla);
        }
        defaultEditor = new DefaultCellEditor(combo);
    }
    
    /**
    * @param row
    *            table row
    * @param editor
    *            table cell editor
    */
    public void setEditorAt(int row, TableCellEditor editor) {
        editors.put(new Integer(row), editor);
    }

    protected void selectEditor(MouseEvent e) {
        int row;
        if (e == null) {
            row = table.getSelectionModel().getAnchorSelectionIndex();
        } else {
            row = table.rowAtPoint(e.getPoint());
        }
        editor = (TableCellEditor) editors.get(new Integer(row));
        if (editor == null) {
            editor = defaultEditor;
        }
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //editor = (TableCellEditor)editors.get(new Integer(row));
        //if (editor == null) {
        //  editor = defaultEditor;
        //}
        return editor.getTableCellEditorComponent(table, value, isSelected,row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getCellEditorValue();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        selectEditor((MouseEvent) anEvent);
        return editor.isCellEditable(anEvent);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        selectEditor((MouseEvent) anEvent);
        return editor.shouldSelectCell(anEvent);
    }

    @Override
    public boolean stopCellEditing() {
        return editor.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        editor.cancelCellEditing();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        editor.addCellEditorListener(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        editor.removeCellEditorListener(l);
    }
    
}
