/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.gui.sidebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author iuga
 */
public class IconTreeRenderer extends DefaultTreeCellRenderer {

    private final JLabel label;
    private final Font orgFont;
    private final Font boldFont;

     private TreeEntry te;

    public IconTreeRenderer()
    {
        label = new JLabel();
        label.setBackground(null);

        orgFont = label.getFont();
        boldFont = label.getFont().deriveFont(label.getFont().getStyle() ^ Font.BOLD);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        te = (TreeEntry) value;

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
        
        // TIENE PERMISO PERMISOS
        if(!te.isPermiso()){
            label.setForeground(Color.LIGHT_GRAY);
        }else{
            label.setForeground(Color.DARK_GRAY);
        }
        
        // Si tiene hijos es un padre
        if(te.getEntries()!=null && !te.getEntries().isEmpty()){
            label.setForeground(Color.BLACK);
        }  
       
        
        label.setPreferredSize(new Dimension(200, 20));
        return label;
    }



}
