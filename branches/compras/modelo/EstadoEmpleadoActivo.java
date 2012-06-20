/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * @author Iuga
 */
public class EstadoEmpleadoActivo extends EstadoEmpleado
{
    @Override
    public boolean esActivo()
    {
	return true;
    }
    
    
    public EstadoEmpleadoActivo()
    {super.setNombre("Activo");}

}
     
        
        
