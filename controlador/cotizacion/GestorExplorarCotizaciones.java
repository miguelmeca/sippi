/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.cotizacion;
import vista.cotizacion.ExplorarCotizaciones;
import util.NTupla;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import modelo.Cotizacion;
import util.FechaUtil;
/**
 *
 * @author Administrador
 */
public class GestorExplorarCotizaciones
{

    private ExplorarCotizaciones pantalla;
    private Session sesion;
    private List lista;





    public GestorExplorarCotizaciones(ExplorarCotizaciones pantalla)
    {
        this.pantalla = pantalla;
        //listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
       // listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
       // listaNroTel= new  ArrayList<String>();
       // listaTipoTel= new ArrayList<TipoTelefono>();
       // listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
       //  listaVencimientoCapacitaciones=new ArrayList<Date>();
    }

    /*public boolean esActivo(int id)
    {
        for (int i = 0; i < lista.size(); i++) {
                Empleado emp = (Empleado)lista.get(i);
                if(emp.getOID()==id)
               // listaNombres.add(td.getNombre());
               return emp.estaActivo();
                
            }
         return false;
    }*/
    /*public boolean esBaja(int id)
    {
        for (int i = 0; i < lista.size(); i++) {
                Empleado emp = (Empleado)lista.get(i);
                if(emp.getOID()==id)
               // listaNombres.add(td.getNombre());
               return emp.estaBaja();
                
            }
         return false;
    }*/
    /*public String nombreEstadoEmpleadoActivo()
    {
        EstadoEmpleadoActivo eca=new EstadoEmpleadoActivo();
        return eca.getNombre();
    }*/
    public List listaCotizaciones()
    {


        try{
        //SessionFactory sf = HibernateUtil.getSessionFactory();
        //sesion = sf.openSession();
        sesion= HibernateUtil.getSession();
        } catch (Exception ex)////////////
            {//////////////////////////////////////////
                System.out.println("No se pudo abrir la sesion");//////////

            }

            lista = sesion.createQuery("from Cotizacion").list();


            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<NTupla> listaCotizaciones = new ArrayList<NTupla>();
            for (int i = 0; i < lista.size(); i++) {
                Cotizacion cot = (Cotizacion)lista.get(i);
                String nombrePedidoObra=cot.buscarPedidoObra().getNombre();
               // listaNombres.add(td.getNombre());
                NTupla tupla = new NTupla(cot.getId());
                tupla.setNombre(nombrePedidoObra);//No me gusta como queda esto... Pero es la 1er columna...
                String[] datos=new String[5];
                //datos[0]=String.valueOf(emp.getLegajo());
                datos[0]=String.valueOf(cot.getNroCotizacion());
                datos[1]=String.valueOf(cot.getNroRevision());
                datos[2]=String.valueOf(FechaUtil.getFecha(cot.getFechaCreacion()));                
                datos[3]=String.valueOf(FechaUtil.getFecha(cot.getFechaModificacion()));
                datos[4]=String.valueOf(cot.CalcularTotal());
                //if(emp.getEstado()!=null)
               // {datos[2]=cot.getEstado().getNombre();}
                tupla.setData(datos);
                    listaCotizaciones.add(tupla);
            }

            return listaCotizaciones;

    }
    
     

}
