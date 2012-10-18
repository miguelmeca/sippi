/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import modelo.Planificacion;
import modelo.TareaPlanificacion;

/**
 * @author Iuga
 */
public interface IGestorPlanificacion {
 
    /**
     * Obtiene la cotizacion con la que se esta trabajando
     * @return 
     */
    public Planificacion getPlanificacion();
    
    
    /**
     * Obtiene la SubObra Actual que se está editando
     * @return 
     */
    public TareaPlanificacion getTareaActual();
    
    /**
     * Envia un pedido para que se refresquen el resto de las pantallas
     */
    public void refrescarPantallas();
    
}
