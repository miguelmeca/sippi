/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;
import java.util.ArrayList;
import util.Tupla;
import java.util.Date;
import util.imagenes.GestorImagenes;
/**
 *
 * @author Administrador
 */
public interface IGestorEmpleado
{

    
    public ArrayList<Tupla> mostrarTiposDeDocumento();
    public boolean ValidarDocumento(String nroDoc, Tupla td);
    public boolean ValidarLegajo(String leg);
    public boolean ValidarCuil(String cuil);
    public void datosPersonalesEmpleado(int leg,String cuil, String nmroDoc, Tupla tipoDocumento, String nombre, String apellido, Date fechaNac, Date fechaIngreso, String email);
    public void datosDomicilioEmpleado(String calle, String nro, String depto, String piso, String cp, Tupla tBarrio);
    public ArrayList<Tupla> mostrarTiposDeTelefono();
    public ArrayList<Tupla> mostrarPaises();
    public ArrayList<Tupla> mostrarProvincias(int idPais);
    public ArrayList<Tupla> mostrarLocalidades(int idProvincia);
    public ArrayList<Tupla> mostrarBarrios(int idLocalidad);
    public ArrayList<Tupla> mostrarTipoEspecialidad();
    public ArrayList<Tupla> mostrarRangoEspecialidad();
    public ArrayList<Tupla> mostrarTipoCapacitacion();
    public void tipoEspecialidadYRango(ArrayList<Tupla> lstTipoEspecialidad, ArrayList<Tupla> lstRangoEspecialidad);
    public void capacitaciones(ArrayList<Tupla> lstTipoCapacitaciones, ArrayList<Date> lstVencimientosCapacitaciones);
    public void telefonosEmpleado(ArrayList<String> numero,ArrayList<Tupla> tipo );
    public void vaciarDatos();
    public void imagenEmpleado(GestorImagenes ge);
}
