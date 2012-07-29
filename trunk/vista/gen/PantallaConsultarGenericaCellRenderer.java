package vista.gen;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class PantallaConsultarGenericaCellRenderer extends DefaultTableCellRenderer {

    private ArrayList<String[]> colorCriteria;
    private ArrayList<String[]> columnas;
    
    public PantallaConsultarGenericaCellRenderer(ArrayList<String[]> colorCriteria,ArrayList<String[]> columnas) {
        this.colorCriteria = colorCriteria;
        this.columnas = columnas;
    }

    
    
    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
        if(value instanceof Tupla)
        {
            Tupla tp = (Tupla) value;
            JLabel label = new JLabel(tp.getNombre());
            label.setOpaque(true);
            fillColor(table,label,tp.getNombre(),isSelected,row,column);
            return label;
        }
        else
        {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * Este método es para que pinte el fondo del JLabel cuando
     * lo seleccionamos para que no quede en blanco, desentonando
     * con el resto de las celdas que no son JLabel
     */
    public void fillColor(JTable t,JLabel l,String data,boolean isSelected,int row, int column){

        String[] col = this.columnas.get(column);
        String nombreColumna = col[1];
        
        for (int i = 0; i < colorCriteria.size(); i++) {
            String[] colores = colorCriteria.get(i);
            if(colores[0].equals(nombreColumna))
            {
                if(data.equals(colores[1]))
                {
                    // ES LA COLUMNA Y MACHEA CON EL CONTENIDO
                    if(isSelected){
                        l.setBackground(new Color(153,153,255));
                        l.setForeground(t.getSelectionForeground());
                    }
                    else{
                        int r = Integer.parseInt(colores[2]);
                        int g = Integer.parseInt(colores[3]);
                        int b = Integer.parseInt(colores[4]);
                        l.setBackground(new Color(r, g, b));
                        l.setForeground(t.getForeground());
                    }
                }
            }
            else
            {
                if(isSelected){
                    l.setBackground(new Color(153,153,255));
                    l.setForeground(t.getSelectionForeground());
                }
            }
        }
        
        // Si no hay criteria de color, pinto la fila de otro color cuando se selecciona
        if(colorCriteria.isEmpty())
        {
            if(isSelected){
                l.setBackground(new Color(153,153,255));
                l.setForeground(t.getSelectionForeground());
            }
        }
        
    }

}
