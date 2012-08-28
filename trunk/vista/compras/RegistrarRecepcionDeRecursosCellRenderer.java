/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.RecepcionOrdenDeCompra;

/**
 *
 * @author Administrador
 */
public class RegistrarRecepcionDeRecursosCellRenderer extends DefaultTableCellRenderer{
   public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column)
   {
      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      
      // La tercer columna es la que se edita, sale color blanco
      if (column==3){
         this.setOpaque(true);
         this.setBackground(Color.WHITE);
         this.setForeground(Color.BLACK);
         return this;
      }
      
      // El resto, depende de los valores ingresados
      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
      
        double tOrde = (Double) modelo.getValueAt(row, 1);
        double tReci = (Double) modelo.getValueAt(row, 2);
        double aReci = (Double) modelo.getValueAt(row, 3);
      
        if(tReci==0 && aReci == 0){
            // No se recibio
            this.setOpaque(true);
            this.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_ANULADA);
            this.setForeground(Color.BLACK);
            return this;
        }
        
        if(tOrde > (tReci+aReci)){
            // Recepcion Parcial
            this.setOpaque(true);
            this.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_RECIBIDA_PARCIALMENTE);
            this.setForeground(Color.BLACK);
            return this;
        }else{
            // Recepcion Total
            this.setOpaque(true);
            this.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_RECIBIDA_TOTALMENTE);
            this.setForeground(Color.BLACK);
            return this;
        }

   }
}