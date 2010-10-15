<<<<<<< .mine
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;

/**
 *
 * @author dell
 */
public class RecursosUtil {
    public static RecursoEspecifico getRecursoEspecifico(RecursoXProveedor rxp){
        RecursoEspecifico re=null;
        try {
            Iterator<RecursoEspecifico> it = HibernateUtil.getSession().createQuery("FROM RecursoEspecifico re WHERE re.proveedores = :rxp").setParameter("rxp", rxp).iterate();
            while(it.hasNext()){
                re=it.next();
                break;
            }
        } catch (Exception ex) {
            Logger.getLogger(RecursosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return re;
    }

    public static Recurso getRecurso(RecursoEspecifico re){
        Recurso r=null;
        try {
            r = (Recurso) HibernateUtil.getSession().createQuery("FROM Recurso r WHERE re.recursos=:re").setParameter("%re%", re);
        } catch (Exception ex) {
            Logger.getLogger(RecursosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
}
