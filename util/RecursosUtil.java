/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

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
            re = (RecursoEspecifico) HibernateUtil.getSession().createQuery("FROM RecursoEspecifico re WHERE re.precios=:rxp").setParameter("%rxp%", rxp);
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
