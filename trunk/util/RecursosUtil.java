/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Material;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;

/**
 *
 * @author dell
 */
public class RecursosUtil {
    public static RecursoEspecifico getRecursoEspecifico(RecursoXProveedor rxp){
        RecursoEspecifico reEncontrado=null;
        RecursoEspecifico re=null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("FROM RecursoEspecifico").iterate();
            while(it.hasNext()){
                re=(RecursoEspecifico)it.next();
                Iterator<RecursoXProveedor> itre = re.getRecursosXProveedor().iterator();
                RecursoXProveedor rx = null;
                while(itre.hasNext()){
                    rx = itre.next();
                    if(rx.getId()==rxp.getId()){
                        reEncontrado= re;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RecursosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reEncontrado;
    }

//    public static Recurso getRecurso(RecursoEspecifico re){
//        Recurso rEncontrado = null;
//        Recurso r=null;
//        try {
//            //r = (Recurso) HibernateUtil.getSession().createQuery("FROM Recurso r WHERE re.recursos IN(:re)").setParameter("%re%", re);
//            Iterator it = HibernateUtil.getSession().createQuery("FROM Recurso").iterate();
//            while(it.hasNext()){
//                r=(Recurso)it.next();
//                Iterator<RecursoEspecifico> itr = r.getRecursos().iterator();
//                RecursoEspecifico resp = null;
//                while(itr.hasNext()){
//                    resp = itr.next();
//                    if(resp.equals(re)){
//                        rEncontrado=r;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(RecursosUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return rEncontrado;
//    }

        public static Material getMaterial(RecursoEspecifico re){
        Material rEncontrado = null;
        Material r=null;
        try {
            //r = (Recurso) HibernateUtil.getSession().createQuery("FROM Recurso r WHERE re.recursos IN(:re)").setParameter("%re%", re);
            Iterator it = HibernateUtil.getSession().createQuery("FROM Material").iterate();
            while(it.hasNext()){
                r=(Material)it.next();
                Iterator<RecursoEspecifico> itr = r.getRecursos().iterator();
                RecursoEspecifico resp = null;
                while(itr.hasNext()){
                    resp = itr.next();
                    if(resp.getId()==re.getId()){
                        rEncontrado=r;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RecursosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rEncontrado;
    }
}
