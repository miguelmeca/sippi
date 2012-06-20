/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class EstadoLugarCapacitacionAlta extends EstadoLugarCapacitacion
{
    public EstadoLugarCapacitacionAlta()
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
