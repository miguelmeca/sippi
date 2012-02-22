package vista.rrhh;

import javax.swing.JTable;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @todo Hacer q cambie de color un JPane cuando le pasas el mouse por arriba
 */

public class ExplorarEmpleados_RenderCeldas extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1)
    {

        if((o instanceof ExplorarEmpleados_celdaDatos) || (o instanceof ExplorarEmpleados_celdaFoto))
        {
            JPanel panel = (JPanel)o;
            fillColor(jtable, o, bln);
            return panel;
        }
        else 
        {
           return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);           
        }

    }


    public void fillColor(JTable t,Object obj,boolean isSelected )
    {
        //setting the background and foreground when JLabel is selected
        if(obj instanceof ExplorarEmpleados_celdaDatos)
        {
            ExplorarEmpleados_celdaDatos panel = (ExplorarEmpleados_celdaDatos)obj;
            panel.setSelected(isSelected);
        }
        if(obj instanceof ExplorarEmpleados_celdaFoto)
        {
            ExplorarEmpleados_celdaFoto panel = (ExplorarEmpleados_celdaFoto)obj;
            panel.setSelected(isSelected);
        }
        
    }

}
