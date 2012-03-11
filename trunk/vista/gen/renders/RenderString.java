/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gen.renders;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.jdom.Element;

/**
 *
 * @author Administrador
 */
public class RenderString extends RenderAbstracto{

    public RenderString(String name, boolean notNull, boolean unique, String defaultVaule) {
        super(name, notNull, unique, defaultVaule);
    }

    public RenderString(Element e) {
        super(e);
    }

    @Override
    public JComponent render() {
        super.componente = new JTextField();
        super.componente.setMinimumSize(new Dimension(50, 28)); 
        super.componente.setPreferredSize(new Dimension(300, 28)); 
        super.componente.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));    
        super.componente.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        super.componente.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        return super.componente;
    }




    
}
