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

public class EstadoLugarCapacitacionBaja extends EstadoLugarCapacitacion {

    public EstadoLugarCapacitacionBaja()
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
