/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gen.renders;

import javax.swing.JComponent;
import org.jdom.Element;

/**
 * @author Iuga
 */
public abstract class RenderAbstracto {

    protected JComponent componente;
    
    private String name = "";
    private boolean notNull = false;
    private boolean unique = false;
    private String defaultVaule = "";
    
    public RenderAbstracto(String name,boolean notNull, boolean unique, String defaultVaule) {
        this.name = name;
        this.notNull = notNull;
        this.unique = unique;
        this.defaultVaule = defaultVaule;
    }
    
    public RenderAbstracto(Element e,String nombreCampo)
    {
        if(e.getAttributeValue("name")!=null)
        {
            this.name = e.getAttributeValue("name");
            System.out.println("[DEBUG] Render: Name="+this.name);
        }
        if(e.getAttributeValue("not-null")!=null)
        {
            if(e.getAttributeValue("not-null").equals("true"))
            {
                this.notNull = true;
            }
            else
            {
                this.notNull = false;
            }
            System.out.println("[DEBUG] Render: Not-Null="+this.notNull);
        }
        if(e.getAttributeValue("default")!=null)
        {
            this.defaultVaule = e.getAttributeValue("default");
            System.out.println("[DEBUG] Render: Default="+this.name);
        }
        if(e.getAttributeValue("unique")!=null)
        {
            if(e.getAttributeValue("unique").equals("true"))
            {
                this.unique = true;
            }
            else
            {
                this.unique = false;
            }
            System.out.println("[DEBUG] Render: Unique="+this.unique);
        }
    }
    
    public JComponent render()
    {
        return null;
    }
    
}
