/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class EstadoTallerCapacitacionBaja extends EstadoTallerCapacitacion
{

    public EstadoTallerCapacitacionBaja()
    {
        super();
        super.setNombre("Baja");
    }
    

    @Override
    public boolean esBaja()
    {
        return true;
    }
}
