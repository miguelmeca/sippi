package vista.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Iuga
 */
public class EditableCellTableRenderer extends DefaultTableCellRenderer{
   
    public static final Color COLOR_CELDA_EDITABE = new Color(217, 255, 179);
    
    public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column)
   {
      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      
      //veo cada celda si es editable, si lo es, la pinto...
      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
      if(modelo.isCellEditable(row, column)){
          this.setBackground(COLOR_CELDA_EDITABE);
          this.setForeground(Color.BLACK);
      }else{
          this.setBackground(Color.WHITE);
          this.setForeground(Color.BLACK);
      }
      return this;
   }
   
   
}
