/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.utiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.TipoDocumento;
import modelo.TipoTelefono;
import modelo.RangoEspecialidad;
import modelo.TipoEspecialidad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;

/**
 *
 * @author Fran
 */
public class gestorBDvarios
{

    private Session sesion;

    public gestorBDvarios()
    {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        sesion = sf.openSession();
    }


     public ArrayList<Tupla> getTiposDeDocumento()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoDocumento").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                TipoDocumento td = (TipoDocumento)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(td.getId(),td.getNombre());
                    tuplas.add(tupla);
            }

            return tuplas;
    }

     public  TipoDocumento getTipoDeDocumento(int idTipoDoc)
    {
        TipoDocumento td = (TipoDocumento)sesion.load(TipoDocumento.class,idTipoDoc);
        return td;
    }

      public  TipoTelefono getTipoDeTelefono(int idTipoTel)
    {
        TipoTelefono tt = (TipoTelefono)sesion.load(TipoTelefono.class,idTipoTel);
        return tt;
    }

     public ArrayList<Tupla> getTiposDeTelefono()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoTelefono").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                TipoTelefono tt = (TipoTelefono)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(tt.getId(),tt.getNombre());
                    tuplas.add(tupla);
            }

            return tuplas;
    }


       public  TipoEspecialidad getTipoEspecialidad(int idTipoEspecialidad)
    {
        TipoEspecialidad esp = (TipoEspecialidad)sesion.load(TipoEspecialidad.class,idTipoEspecialidad);
        return esp;
    }

     public ArrayList<Tupla> getTipoEspecialidades()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoEspecialidad").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                TipoEspecialidad te = (TipoEspecialidad)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(te.getId(),te.getNombre());
                    tuplas.add(tupla);
            }

            return tuplas;
    }

     public  RangoEspecialidad getRangoEspecialidad(int idRango)
    {
        RangoEspecialidad rng = (RangoEspecialidad)sesion.load(RangoEspecialidad.class,idRango);
        return rng;
    }

     public ArrayList<Tupla> getRangosEspecialidad()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from RangoEspecialidad").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                RangoEspecialidad rng = (RangoEspecialidad)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(rng.getId(),rng.getNombre());
                    tuplas.add(tupla);
            }

            return tuplas;
    }



}
