/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import modelo.ContactoResponsable;
import modelo.Cotizacion;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Administrador
 */
public class VentanaHomeGestor {
 
    public static int getCotizacionesEnCreacion()
    {
        return getHSQL("SELECT C.id FROM Cotizacion C WHERE C.estado LIKE '"+Cotizacion.ESTADO_EN_CREACION+"'");
    }
    
    public static int getCotizacionesPendientes()
    {
        return getHSQL("SELECT C.id FROM Cotizacion C WHERE C.estado LIKE '"+Cotizacion.ESTADO_PENDIENTE_ACEPTACION+"'");
    }    
    
    public static int getCotizacionesAceptadas()
    {
        return getHSQL("SELECT C.id FROM Cotizacion C WHERE C.estado LIKE '"+Cotizacion.ESTADO_ACEPTADO+"'");
    }       
    
    private static int getHSQL(String hsql)
    {
        int res = -1;
        try
        {
            Session ses = HibernateUtil.getSession();
            HibernateUtil.getSession().beginTransaction();
            res = (Integer) ses.createQuery(hsql).list().size();
        }catch(Exception e)
        {
            System.err.println("No se pudo hacer la consulta para la HomeScreen");
        }
        return res;
    }
    
}
