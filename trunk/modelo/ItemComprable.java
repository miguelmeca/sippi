/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * @author Iuga
 */
public class ItemComprable {
    
    private Class tipoComprable;
    private int id;

    public ItemComprable(Class tipoComprable, int id) {
        this.tipoComprable = tipoComprable;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class getTipoComprable() {
        return tipoComprable;
    }

    public void setTipoComprable(Class tipoComprable) {
        this.tipoComprable = tipoComprable;
    }
    
    
    
}
