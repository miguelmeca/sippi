/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;
import vista.rrhh.pantallaConsultarCapacitadores;
import util.NTupla;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import modelo.Capacitador;
import modelo.EstadoCapacitadorActivo;

/**
 *
 * @author Administrador
 */
public class GestorConsultarCapacitadores
{

    private pantallaConsultarCapacitadores pantalla;
    private Session sesion;
    private List lista;





    public GestorConsultarCapacitadores(pantallaConsultarCapacitadores pantalla)
    {
        this.pantalla = pantalla;
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
                Capacitador cap = (Capacitador)lista.get(i);
                if(cap.getOID()==id)
               // listaNombres.add(td.getNombre());
               return cap.estaActivo();

            }
         return false;
    }
    public String nombreEstadoCapacitadorActivo()
    {
        EstadoCapacitadorActivo eca=new EstadoCapacitadorActivo();
        return eca.getNombre();
    }
    public boolean esBaja(int id)
    {
        for (int i = 0; i < lista.size(); i++) {
                Capacitador cap = (Capacitador)lista.get(i);
                if(cap.getOID()==id)
               // listaNombres.add(td.getNombre());
               return cap.estaBaja();

            }
         return false;
    }
    public List listaCapacitadores()
    {


        try{
        //SessionFactory sf = HibernateUtil.getSessionFactory();
        //sesion = sf.openSession();
        sesion= HibernateUtil.getSession();
        } catch (Exception ex)////////////
            {//////////////////////////////////////////
                System.out.println("No se pudo abrir la sesion");//////////

            }




        // sesion.beginTransaction();
            lista = sesion.createQuery("from Capacitador order by apellido").list();
            //sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<NTupla> listaCapacitadores = new ArrayList<NTupla>();
            for (int i = 0; i < lista.size(); i++) {
                Capacitador cap = (Capacitador)lista.get(i);
               // listaNombres.add(td.getNombre());
                NTupla tupla = new NTupla(cap.getOID());
                tupla.setNombre(String.valueOf(cap.getCuil()));
                String[] datos=new String[3];
                //datos[0]=String.valueOf(cap.getCuil());
                datos[0]=cap.getNombre();
                datos[1]=cap.getApellido();
                //if(cap.getEstado()!=null)
                //{
                    datos[2]=cap.getEstado().getNombre();
                //}
                tupla.setData(datos);
                    listaCapacitadores.add(tupla);
            }

            return listaCapacitadores;

    }

}
