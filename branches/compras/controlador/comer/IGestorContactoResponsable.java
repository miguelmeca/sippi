/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;
import controlador.rrhh.*;
import java.util.ArrayList;
import util.Tupla;
import java.util.Date;
/**
 *
 * @author Administrador
 */
public interface IGestorContactoResponsable
{

    
    //public ArrayList<Tupla> mostrarTiposDeDocumento();
    //public boolean ValidarDocumento(String nroDoc, Tupla td);
   // public boolean ValidarLegajo(String leg);
    //public boolean ValidarCuil(String cuil);
    public void datosPersonalesContactoResponsable(String cuil, String nombre, String apellido, String email, String cargo, Tupla empr, Tupla plant );
    public void datosPersonalesContactoResponsable(String cuil, String nombre, String apellido, String email, String cargo);
    //public void datosDomicilioContacto(String calle, String nro, String depto, String piso, String cp, Tupla tBarrio);
    public ArrayList<Tupla> mostrarTiposDeTelefono();
    public boolean validarPlantaSinContacto();
    public void telefonosContactoResponsable(ArrayList<String> numero,ArrayList<Tupla> tipo );
    //public void vaciarDatos();
     public boolean ValidarCuil(String cuil);
    public ArrayList<Tupla> mostrarEmpresas();
    public ArrayList<Tupla> mostrarPlantas(int idEmpresa);
}
