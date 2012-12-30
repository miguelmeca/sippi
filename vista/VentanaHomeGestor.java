/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import modelo.Cotizacion;
import modelo.Ejecucion;
import modelo.Planificacion;
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
        int res = 0;
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

    public static int getPlanificacionesEnCreacion() {
        return getHSQL("SELECT P.id FROM Planificacion P WHERE P.estado LIKE '"+Planificacion.ESTADO_CREADA+"'");
    }

    static int getPlanificacionesFinalizadas() {
        return getHSQL("SELECT P.id FROM Planificacion P WHERE P.estado LIKE '"+Planificacion.ESTADO_FINALIZADA+"'");
    }

    static int getEjecucionEnEjecucion() {
        return getHSQL("SELECT E.id FROM Ejecucion E WHERE E.estado LIKE '"+Ejecucion.ESTADO_CREADA+"'");
    }

    static int getEjecucionFinalizada() {
        return getHSQL("SELECT E.id FROM Ejecucion E WHERE E.estado LIKE '"+Ejecucion.ESTADO_FINALIZADA+"'");
    }
    
}
