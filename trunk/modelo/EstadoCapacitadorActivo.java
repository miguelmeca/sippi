/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * @author Iuga
 */
public class EstadoCapacitadorActivo extends EstadoCapacitador
{
    @Override
    public boolean esActivo()
    {
	return true;
    }
    public EstadoCapacitadorActivo()
    {super.setNombre("Activo");}

}
