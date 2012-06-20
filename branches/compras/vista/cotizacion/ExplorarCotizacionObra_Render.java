package vista.cotizacion;

import javax.swing.JTable;
import vista.gui.*;
import de.javasoft.plaf.synthetica.simple2D.DefaultComboListCellRenderer;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @todo Hacer q cambie de color un JPane cuando le pasas el mouse por arriba
 */

public class ExplorarCotizacionObra_Render extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1)
    {

        if(o instanceof ExplorarCotizacionObra_celda)
        {
            JPanel panel = (JPanel)o;
            fillColor(jtable, o, bln1);
            return panel;
        }
        else 
        {
            if(o instanceof ExplorarCotizacionObra_celdaObras)
            {
                JPanel panel = (JPanel)o;
                fillColor(jtable, o, bln1);
                return panel;           
            }
            else
            {   
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        }

    }


    public void fillColor(JTable t,Object obj,boolean isSelected )
    {
        //setting the background and foreground when JLabel is selected
        if(obj instanceof ExplorarCotizacionObra_celda)
        {
            ExplorarCotizacionObra_celda panel = (ExplorarCotizacionObra_celda)obj;
            panel.setSelected(isSelected);
        }
        
        if(obj instanceof ExplorarCotizacionObra_celdaObras)
        {
            ExplorarCotizacionObra_celdaObras panel = (ExplorarCotizacionObra_celdaObras)obj;
            panel.setSelected(isSelected);            
        }
    }

}
