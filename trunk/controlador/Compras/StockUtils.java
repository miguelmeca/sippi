package controlador.Compras;

import java.util.List;
import modelo.ItemComprable;
import modelo.ItemStock;
import util.HibernateUtil;

/**
 *
 * @author Iuga
 */
public class StockUtils {
    
    /**
     * Dado una Clase Stockeable y un ID, retorna el Stock disponible en este momento.
     * @param tipo
     * @param idItemComprable
     * @return 
     */
    public double calcularStockDeRecursoespecifico(Class tipo,int idItemComprable) {
        double stock = 0;
        try
        {
            HibernateUtil.beginTransaction();
            List<ItemStock> listado = HibernateUtil.getSession().createQuery("from ItemStock").list();
            HibernateUtil.commitTransaction();

            for (int i = 0; i < listado.size(); i++) {
                ItemStock is = listado.get(i);
                
                ItemComprable ic = is.getItem();
                if(is.getItem()!=null)
                {
                    if(ic.getTipoComprable()==tipo && idItemComprable==ic.getId()){
                        stock += is.getCantidad();
                    }
                }
            }

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            e.printStackTrace();
            return -1;
        } 
        return stock;
    }    
    
}
