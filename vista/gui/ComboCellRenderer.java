package vista.gui;

import de.javasoft.plaf.synthetica.simple2D.DefaultComboListCellRenderer;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @todo Hacer q cambie de color un JPane cuando le pasas el mouse por arriba
 */

public class ComboCellRenderer extends DefaultComboListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(JList jlist, Object value, int i, boolean bln, boolean bln1) {

        if(value instanceof JPanel)
        {
            JPanel panel = (JPanel)value;
            return panel;
        }
        else
        {
            return super.getListCellRendererComponent(jlist, value, i, bln, bln1);
        }

    }
}
