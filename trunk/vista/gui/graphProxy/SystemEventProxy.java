package vista.gui.graphProxy;

import vista.planificacion.pantallaRegistrarPresupuesto;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class SystemEventProxy {

    private static SystemEventProxy instance = null;

    private pantallaRegistrarPresupuesto pantalla;

    private SystemEventProxy(){}

    public static SystemEventProxy getInstance()
    {
        if(instance==null)
        {
            instance = new SystemEventProxy();
        }
        return instance;
    }

    public pantallaRegistrarPresupuesto getPantalla()
    {
        return pantalla;
    }

    public void setPantalla(pantallaRegistrarPresupuesto pantalla)
    {
        this.pantalla = pantalla;
    }



    
}
