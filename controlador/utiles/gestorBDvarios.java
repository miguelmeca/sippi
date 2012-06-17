/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.utiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.ContactoResponsable;
import modelo.TipoDocumento;
import modelo.TipoTelefono;
import modelo.EmpresaCliente;
import modelo.Planta;
import modelo.RangoEspecialidad;
import modelo.TipoEspecialidad;
import modelo.Especialidad;
import modelo.TipoCapacitacion;
//import modelo.Criticidad;
import org.hibernate.EntityMode;
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
        try{
        //SessionFactory sf = HibernateUtil.getSessionFactory();
        //sesion = sf.openSession();
        sesion= HibernateUtil.getSession();
        } catch (Exception ex)////////////
            {//////////////////////////////////////////
                System.out.println("No se pudo abrir la sesion");//////////
                
            }
    }


     public ArrayList<Tupla> getTiposDeDocumento()
    {
         //sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoDocumento").list();
            //sesion.getTransaction().commit();

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
         //sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoTelefono").list();
           // sesion.getTransaction().commit();

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

    public Especialidad getEspecialidad(TipoEspecialidad te,RangoEspecialidad re )
    {
         //TipoEspecialidad te=(TipoEspecialidad) sesion.createQuery("from TipoEspecialidad where id ="+listaRolTipoEspecialidad.get(i).getId()).uniqueResult();
         //RangoEspecialidad re=(RangoEspecialidad) sesion.createQuery("from RangoEspecialidad where id ="+listaRolRangoEspecialidad.get(i).getId()).uniqueResult();
         Especialidad esp=(Especialidad) sesion.createQuery("from Especialidad where tipo = (:tipoEsp) and rango = (:rangoEsp)").setParameter("tipoEsp", te).setParameter("rangoEsp", re).uniqueResult();
         return esp;
    }
    
    public ArrayList<Especialidad> getEspecialidades(TipoEspecialidad te)
    {
         //TipoEspecialidad te=(TipoEspecialidad) sesion.createQuery("from TipoEspecialidad where id ="+listaRolTipoEspecialidad.get(i).getId()).uniqueResult();
         //RangoEspecialidad re=(RangoEspecialidad) sesion.createQuery("from RangoEspecialidad where id ="+listaRolRangoEspecialidad.get(i).getId()).uniqueResult();
        sesion.beginTransaction();
         List lista = sesion.createQuery("from Especialidad where tipo = (:tipoEsp)").setParameter("tipoEsp", te).list();
          sesion.getTransaction().commit();
         ArrayList<Especialidad> especialidades = new ArrayList<Especialidad>();
            for (int i = 0; i < lista.size(); i++) {
                Especialidad e = (Especialidad)lista.get(i);
               // listaNombres.add(td.getNombre());
              especialidades.add(e);
            }

            return especialidades;
    }

    public Especialidad getEspecialidad(int idTipoE ,int idRangoE )
    {
         TipoEspecialidad te=getTipoEspecialidad(idTipoE);
         RangoEspecialidad re=getRangoEspecialidad(idRangoE);
         Especialidad esp=getEspecialidad( te, re );
         return esp;
    }

       public  TipoEspecialidad getTipoEspecialidad(int idTipoEspecialidad)
    {
        TipoEspecialidad esp = (TipoEspecialidad)sesion.load(TipoEspecialidad.class,idTipoEspecialidad);
        return esp;
    }

     public ArrayList<Tupla> getTipoEspecialidades()
    {
         //sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoEspecialidad").list();
           // sesion.getTransaction().commit();

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
         //sesion.beginTransaction();
            List lista = sesion.createQuery("from RangoEspecialidad").list();
          //  sesion.getTransaction().commit();

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
         //sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoCapacitacion").list();
          //  sesion.getTransaction().commit();

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

//    public ArrayList<Tupla> getCriticidad()
//    {
//
//            List lista = sesion.createQuery("from Criticidad c order by c.nivel").list();
//            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
//            for (int i = 0; i < lista.size(); i++)
//            {
//                Criticidad tc = (Criticidad)lista.get(i);
//               // listaNombres.add(td.getNombre());
//                Tupla tupla = new Tupla(tc.getId(),tc.getNombre());
//                    tuplas.add(tupla);
//            }
//            return tuplas;
//    }


     /////////////////////////////////////////////////////////
     public ArrayList<Tupla> getEmpresas()
    {
        // sesion.beginTransaction();
            List lista = sesion.createQuery("from EmpresaCliente ec order by ec.razonSocial").list();
          //  sesion.getTransaction().commit();

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
        EmpresaCliente emp = (EmpresaCliente)sesion.load(EmpresaCliente.class,idEmpresa);
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

    	public ArrayList<Tupla> mostrarContactos() {

            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            //SessionFactory sf = HibernateUtil.getSessionFactory();
            //Session sesion = sf.openSession();


            //sesion.beginTransaction();
            Iterator iter = sesion.createQuery("from ContactoResponsable ec order by ec.id DESC").iterate();
            while ( iter.hasNext() )
            {
                ContactoResponsable cr = (ContactoResponsable)iter.next();
//                Tupla tupla = new Tupla(cr.getId(),cr.getApellido()+", "+cr.getNombre());
//                tuplas.add(tupla);
            }
            return tuplas;

	}

}
