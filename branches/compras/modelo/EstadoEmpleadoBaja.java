/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * @author Iuga
 */
public class EstadoEmpleadoBaja extends EstadoEmpleado
{
    @Override
    public boolean esBaja()
    {
	return true;
    }
    
    public EstadoEmpleadoBaja()
    {super.setNombre("Dado de baja");}
}
     
        
        
