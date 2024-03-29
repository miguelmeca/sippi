/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.planificacion.arbolTareas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author iuga/fran
 */
public class ArbolIconoRenderer extends DefaultTreeCellRenderer 
{

    private final JLabel label;
    private final Font orgFont;
    private final Font boldFont;

     private ArbolIconoNodo te;
     JPopupMenu menu;

    public ArbolIconoRenderer()
    {
        label = new JLabel();
        label.setBackground(null);
        //this.menu=menu;
        //label.add(menu);
        this.setFocusable(false);
        label.setFocusable(false);
        orgFont = label.getFont();
        boldFont = label.getFont().deriveFont(label.getFont().getStyle() ^ Font.BOLD);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) 
    {
        te = (ArbolIconoNodo) value;

        label.setText(te.getTitulo());
        if (sel)
        {
            label.setFont(boldFont);
            label.setIcon(te.getIcono());
        }
        else
        {
            label.setFont(orgFont);
            label.setIcon(te.getIcono());
        }
        label.setPreferredSize(new Dimension(200, 20));
        return label;
    }
    
    public JLabel getLabel()
    {  return label;  }



}
