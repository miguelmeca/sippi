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
    
    private int id = 0;
    private Class tipoComprable;
    private int idComprable;
    private Object item;

    public ItemComprable(Class tipoComprable, int id) {
        this.tipoComprable = tipoComprable;
        this.idComprable = id;
    }

    public ItemComprable() {
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
                retorno += re.getRecurso().getNombre()+" "+re.getNombre();
                return retorno;
            }
            if(item instanceof TipoAlquilerCompra)
            {
                String retorno = "";
                TipoAlquilerCompra tac = (TipoAlquilerCompra)item;
                retorno += tac.getNombre();
                return retorno;
            }
            if(item instanceof TipoAdicional)
            {
                String retorno = "";
                TipoAdicional tad = (TipoAdicional)item;
                retorno += tad.getNombre();
                return retorno;                
            }
            return "???";
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
            this.item = HibernateUtil.getSession().load(this.tipoComprable, this.idComprable);
            HibernateUtil.commitTransaction();
            
        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            e.printStackTrace();
            return;
        } 
    }

    public Object getItem() {
        if(item == null)
            {loadItem();}
        return item;
    }
    
    public String getUnidadDeMedida()
    {
        if(this.item==null)
        {
            loadItem();
        }    
        if(this.item!=null)
        {
            if(item instanceof RecursoEspecifico)
            {
                RecursoEspecifico re = (RecursoEspecifico)item;
                return re.getRecurso().getUnidadDeMedida().getAbreviatura();
            }
        }
        return UnidadDeMedida.UNIDAD_BASE_ABREVIATURA;     
    }
    
}
