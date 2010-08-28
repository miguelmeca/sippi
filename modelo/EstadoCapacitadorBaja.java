/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * @author Iuga
 */
public class EstadoCapacitadorBaja extends EstadoCapacitador
{
    @Override
    public boolean esBaja()
    {
	return true;
    }
    public EstadoCapacitadorBaja()
    {super.setNombre("Baja");}

}
