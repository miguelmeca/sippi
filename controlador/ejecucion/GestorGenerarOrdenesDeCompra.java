/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ejecucion;

import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Hibernate;
import util.HibernateUtil;
import util.NTupla;
import util.RecursosUtil;

/**
 *
 * @author Emmanuel
 */
public class GestorGenerarOrdenesDeCompra {

    ArrayList<NTupla> items;
    
    public GestorGenerarOrdenesDeCompra(ArrayList<NTupla> items) {
        this.items = items;
    }

    public ArrayList<NTupla> getItemsAComprar() {
        try
        {
            ArrayList<NTupla> items = new ArrayList<NTupla>();
            HibernateUtil.beginTransaction();
            
            Iterator<NTupla> itItems = this.items.iterator();
            while(itItems.hasNext())
            {
//                NTupla nTupla = itItems.next();
//                nTupla
//                RecursoEspecifico re = 
//                RecursosUtil.getMaterial(null)
            }
            
            
            HibernateUtil.commitTransaction();
            return items;
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
            return null;
        }
    }
    
}
