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
import modelo.EmpresaCliente;
import modelo.Planta;
import modelo.RangoEspecialidad;
import modelo.TipoEspecialidad;
import modelo.TipoCapacitacion;
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

    public  TipoCapacitacion getTipoCapacitacion(int idTipoCapacitacion)
    {
        TipoCapacitacion cap = (TipoCapacitacion)sesion.load(TipoCapacitacion.class,idTipoCapacitacion);
        return cap;
    }

     public ArrayList<Tupla> getTipoCapacitacion()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoCapacitacion").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                TipoCapacitacion tc = (TipoCapacitacion)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(tc.getId(),tc.getNombre());
                    tuplas.add(tupla);
            }

            return tuplas;
    }




     /////////////////////////////////////////////////////////
     public ArrayList<Tupla> getEmpresas()
    {
         sesion.beginTransaction();
            List lista = sesion.createQuery("from EmpresaCliente ec order by ec.razonSocial").list();
            sesion.getTransaction().commit();

            //ArrayList<String> listaNombres = new ArrayList<String>();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++) {
                EmpresaCliente emp = (EmpresaCliente)lista.get(i);
               // listaNombres.add(td.getNombre());
                Tupla tupla = new Tupla(emp.getId(),emp.getRazonSocial());
                    tuplas.add(tupla);
            }

            return tuplas;
    }

     public  EmpresaCliente getEmpresa(int idEmpresa)
    {
        EmpresaCliente emp = (EmpresaCliente)sesion.load(TipoDocumento.class,idEmpresa);
        return emp;
    }



    public  ArrayList<Tupla> getPlantas(int idEmpresa)
    {
        // Tengo solo el ID, así que busco el objeto
        EmpresaCliente ec = getEmpresa(idEmpresa);
        return this.getPlantas(ec);
    }
    public  ArrayList<Tupla> getPlantas(EmpresaCliente ec)
    {
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();

            List<Planta> lista = ec.getPlantas();
            Iterator i = lista.iterator();
            while (i.hasNext())
            {
                Planta planta = (Planta)i.next();
                Tupla tupla = new Tupla(planta.getId(),planta.getRazonSocial());
                tuplas.add(tupla);
            }
            return tuplas;
    }

    public Planta getPlanta(int idPlanta)
    {
        return (Planta) sesion.load(Planta.class,idPlanta);
    }

}
