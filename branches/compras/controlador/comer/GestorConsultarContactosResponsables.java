/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;
import controlador.rrhh.*;
import vista.comer.pantallaConsultarContactosResponsables;
import util.NTupla;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import modelo.ContactoResponsable;

/**
 *
 * @author Administrador
 */
public class GestorConsultarContactosResponsables
{

    private pantallaConsultarContactosResponsables pantalla;
    private Session sesion;





    public GestorConsultarContactosResponsables(pantallaConsultarContactosResponsables pantalla)
    {
        this.pantalla = pantalla;
        //listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
       // listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
       // listaNroTel= new  ArrayList<String>();
       // listaTipoTel= new ArrayList<TipoTelefono>();
       // listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
       //  listaVencimientoCapacitaciones=new ArrayList<Date>();
    }

    public List listaContactos()
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
            List lista = sesion.createQuery("from ContactoResponsable order by apellido").list();
            //sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<NTupla> listaContactos = new ArrayList<NTupla>();
            for (int i = 0; i < lista.size(); i++) {
                ContactoResponsable cap = (ContactoResponsable)lista.get(i);
               // listaNombres.add(td.getNombre());
                NTupla tupla = new NTupla(cap.getId());
//                tupla.setNombre(String.valueOf(cap.getCuil()));
                String[] datos=new String[2];
                //datos[0]=String.valueOf(cap.getCuil());
                datos[0]=cap.getNombre();
//                datos[1]=cap.getApellido();
                /*if(cap..getEstado()!=null)
                {datos[3]=cap.getEstado().getNombre();}*/
                tupla.setData(datos);
                    listaContactos.add(tupla);
            }

            return listaContactos;

    }

}
