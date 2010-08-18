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

public class EstadoTallerCapacitacionAlta extends EstadoTallerCapacitacion
{

    public EstadoTallerCapacitacionAlta()
    {
        super();
        super.setNombre("Alta");
    }


    @Override
    public boolean esAlta()
    {
        return true;
    }
}
