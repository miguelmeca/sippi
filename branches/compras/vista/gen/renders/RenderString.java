/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gen.renders;

import java.awt.Dimension;
import java.awt.Label;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdom.Element;
import vista.gen.PantallaABMGenerica;

/**
 *
 * @author Administrador
 */
public class RenderString extends RenderAbstracto{

    private JTextField txtData;
    
    public RenderString(String name, boolean notNull, boolean unique, String defaultVaule) {
        super(name, notNull, unique, defaultVaule);
    }

    public RenderString(Element e,String nombre) {
        super(e,nombre);
    }

    @Override
    protected JComponent renderAlta() {
        // PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        
        // TITULO
        JLabel label = new JLabel(super.nombreMostrar+":");
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setMaximumSize(new Dimension(Short.MAX_VALUE, 25)); 
        label.setHorizontalAlignment(Label.RIGHT);
        label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        label.setOpaque(true);
        label.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);

        // Texto
        JTextField texto = new JTextField();
        texto.setMinimumSize(new Dimension(50, 28)); 
        texto.setPreferredSize(new Dimension(300, 28)); 
        texto.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));    
        texto.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        texto.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        this.txtData = texto;
        panel.add(label);
        panel.add(texto);
        
        super.componente=panel;
        return panel;
    }

    @Override
    protected JComponent renderVerDetalles() {
        // PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        
        // TITULO
        JLabel label = new JLabel(super.nombreMostrar+":");
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setMaximumSize(new Dimension(Short.MAX_VALUE, 25)); 
        label.setHorizontalAlignment(Label.RIGHT);
        label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        label.setOpaque(true);
        label.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);

        // Texto
        JTextField texto = new JTextField();
        texto.setMinimumSize(new Dimension(50, 28)); 
        texto.setPreferredSize(new Dimension(300, 28)); 
        texto.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));    
        texto.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        texto.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        texto.setEnabled(false);
        this.txtData = texto;
        panel.add(label);
        panel.add(texto);
        
        super.componente=panel;
        return panel;
    }

    @Override
    protected JComponent renderBaja() {
        return renderVerDetalles();
    }

    @Override
    protected JComponent renderModificacion() {
        // PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        
        // TITULO
        JLabel label = new JLabel(super.nombreMostrar+":");
        label.setFont(new java.awt.Font("Tahoma", 1, 11));
        label.setMaximumSize(new Dimension(Short.MAX_VALUE, 25)); 
        label.setHorizontalAlignment(Label.RIGHT);
        label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        label.setOpaque(true);
        label.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);

        // Texto
        JTextField texto = new JTextField();
        texto.setMinimumSize(new Dimension(50, 28)); 
        texto.setPreferredSize(new Dimension(300, 28)); 
        texto.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));    
        texto.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        texto.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        this.txtData = texto;
        panel.add(label);
        panel.add(texto);
        
        super.componente=panel;
        return panel;
    }

  
    
    @Override
    public String getData() {
        if(this.txtData!=null)
        {
            return txtData.getText();
        }
        return "";
    }

    @Override
    public void setData(String data) {
        if(this.txtData!=null)
        {
            txtData.setText(data);
        }
    }
    
    


    
}
