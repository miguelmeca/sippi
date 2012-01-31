/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import controlador.GestorAbstracto;
import java.util.List;
import modelo.CotizacionModificada;
import modelo.PlanificacionXXX;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Administrador
 */
public class GestorArbolDeRecursos extends GestorAbstracto{

    private Session sesion;
    private List lista;
    
    public List getListaSubObras(int idPlanificacion) throws Exception{
        
        sesion= HibernateUtil.getSession();
        
        PlanificacionXXX pl = (PlanificacionXXX) sesion.load(PlanificacionXXX.class,idPlanificacion);
        
        if(pl==null)
        {
            throw new Exception("No se pudo cargar la planificacion");
        }
        
        CotizacionModificada cm = pl.getCotizacion();
        
        if(cm==null)
        {
            throw new Exception("No se pudo cargar la cotizacion de esta planificacion");
        }        
        
        //cm.getSubObra()
        
        return null; 
        
    }
    
    public List getListaTareasXSubObra(int idSubObra) {
        
        return null; 
        
    }
    
    
    
}
