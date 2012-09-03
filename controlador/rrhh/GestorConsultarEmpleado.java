/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;
import vista.rrhh.PantallaConsultarEmpleado;
import util.NTupla;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import modelo.Empleado;
import modelo.EstadoEmpleadoActivo;

/**
 *
 * @author Administrador
 */
public class GestorConsultarEmpleado
{

    //private PantallaConsultarEmpleado pantalla;
    private Session sesion;
    private List lista;





    public GestorConsultarEmpleado()
    {
       // this.pantalla = pantalla;
        //listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
       // listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
       // listaNroTel= new  ArrayList<String>();
       // listaTipoTel= new ArrayList<TipoTelefono>();
       // listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
       //  listaVencimientoCapacitaciones=new ArrayList<Date>();
    }

    public boolean esActivo(int id)
    {
        for (int i = 0; i < lista.size(); i++) {
                Empleado emp = (Empleado)lista.get(i);
                if(emp.getOID()==id)
               // listaNombres.add(td.getNombre());
               return emp.estaActivo();
                
            }
         return false;
    }
    public boolean esBaja(int id)
    {
        for (int i = 0; i < lista.size(); i++) {
                Empleado emp = (Empleado)lista.get(i);
                if(emp.getOID()==id)
               // listaNombres.add(td.getNombre());
               return emp.estaBaja();
                
            }
         return false;
    }
    public String nombreEstadoEmpleadoActivo()
    {
        EstadoEmpleadoActivo eca=new EstadoEmpleadoActivo();
        return eca.getNombre();
    }
    public List listaEmpleados()
    {


        try{
        //SessionFactory sf = HibernateUtil.getSessionFactory();
        //sesion = sf.openSession();
        sesion= HibernateUtil.getSession();
        } catch (Exception ex)////////////
            {//////////////////////////////////////////
                System.out.println("No se pudo abrir la sesion");//////////

            }

            lista = sesion.createQuery("from Empleado order by legajo").list();
            
            ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
            for (int i = 0; i < lista.size(); i++) {
                Empleado emp = (Empleado)lista.get(i);
                //NTupla tupla = new NTupla(emp.getOID());
               // tupla.setNombre(String.valueOf(emp.getLegajo()));
                
                /*String[] datos=new String[3];                
                datos[0]=emp.getNombre();
                datos[1]=emp.getApellido();               
                {datos[2]=emp.getEstado().getNombre();}
                tupla.setData(datos);*/
                //tupla.setData(emp);
                listaEmpleados.add(emp);
            }

            return listaEmpleados;

    }

}
