/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import util.HibernateUtil;

/**
 * @author Iuga
 */
public class ItemComprable {
    
    private int id;
    private Class tipoComprable;
    private int idComprable;
    private IComprable item;

    public ItemComprable(Class tipoComprable, int id) {
        this.tipoComprable = tipoComprable;
        this.idComprable = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdComprable() {
        return idComprable;
    }

    public void setIdComprable(int id) {
        this.idComprable = id;
    }

    public Class getTipoComprable() {
        return tipoComprable;
    }

    public void setTipoComprable(Class tipoComprable) {
        this.tipoComprable = tipoComprable;
    }
    
    /**
     * Reflexiono el objeto al que hace referencia y retorno su nombre a mostrar
     * @return 
     */
    public String getNombre()
    {
        if(this.item==null)
        {
            loadItem();
        }
        
        if(this.item!=null)
        {
            if(item instanceof RecursoEspecifico)
            {
                String retorno = "";
                RecursoEspecifico re = (RecursoEspecifico)item;
                retorno += re.getRecurso().getNombre()+" "+item.getNombre();
                return retorno;
            }
            else
            {    
                return item.getNombre();
            }
        }
        else
        {
            return "???";
        }
    }

    private void loadItem() {
        try
        {
            
            HibernateUtil.beginTransaction();
            this.item = (IComprable)HibernateUtil.getSession().load(this.tipoComprable, this.idComprable);
            HibernateUtil.commitTransaction();
            
        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            e.printStackTrace();
            return;
        } 
    }
    
    
}
