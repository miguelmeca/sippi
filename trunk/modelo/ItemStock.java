package modelo;

import java.util.Date;
import javax.swing.JOptionPane;
import util.HibernateUtil;

/**
 * @author Iuga
 */
public class ItemStock {
    
    private int id = 0;
    private ItemComprable item;
    private double cantidad;
    private Date fechaModificacion;

    public ItemStock() {
    }
    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemComprable getItem() {
        return item;
    }

    public void setItem(ItemComprable item) {
        this.item = item;
    }
    
    /**
     * Tengo un item comprable, pero necesito un Item Stock.
     * Lo cargo. Si no existe creo uno y lo guardo y devuelvo
     * @param ic
     * @return 
     */
    public static ItemStock getItemStockFromItemComprable(ItemComprable ic)
    {
        ItemStock is = null;
        // Lo cargo
        try
        {
            HibernateUtil.beginTransaction();
            is = (ItemStock) HibernateUtil.getSession().createQuery("FROM ItemStock WHERE :cID = item").setParameter("cID", ic).uniqueResult();
            HibernateUtil.commitTransaction();

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            e.printStackTrace();
        } 
        
        if(is==null)
        {
            // Creo el nuevo Item Stock
            is = new ItemStock();
            is.setCantidad(0);
            is.setFechaModificacion(new Date());
            is.setItem(ic);
            
                // Lo guardo
                try
                {
                    HibernateUtil.beginTransaction();
                    HibernateUtil.getSession().saveOrUpdate(is);
                    HibernateUtil.commitTransaction();

                }catch(Exception e)
                {
                    HibernateUtil.rollbackTransaction();
                    e.printStackTrace();
                }             
        }
        
        return is;
    }    
    
}
