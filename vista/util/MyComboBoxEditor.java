package vista.util;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * @author Iuga
 */
public class MyComboBoxEditor extends DefaultCellEditor {

    public MyComboBoxEditor(String[] items) {
        super(new JComboBox(items));
    }
}
