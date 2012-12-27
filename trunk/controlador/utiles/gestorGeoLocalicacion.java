/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.utiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Barrio;
import modelo.Localidad;
import modelo.Pais;
import modelo.Provincia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;

/**
 * Clase que se encarga de manejar la carga de los paises, provincias, localidades y
 * barrios. Es un componente para poder reutilizar constantemente
 * @version 1.0
 * @author iuga
 */
public class gestorGeoLocalicacion {

    private Session sesion;

    public gestorGeoLocalicacion() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        sesion = sf.openSession();

    }

    /**
     * @author: Iuga
     * Retorna una Lista de Tuplas que contienen los nombre de todos los paises
     * y los id asociados a cada uno.
     * @return ArrayList<Tupla> listaPaises
     */
    public ArrayList<Tupla> getPaises()
    {
         ArrayList<Tupla> tuplas = new ArrayList<Tupla>();

            sesion.beginTransaction();

                Iterator iter = sesion.createQuery("from Pais q order by q.nombre").iterate();
                while ( iter.hasNext() )
                {
                    Pais p = (Pais) iter.next();
                    Tupla tupla = new Tupla(p.getId(),p.getNombre());
                    tuplas.add(tupla);
                }

        return tuplas;
    }

    /**
     * @author: Iuga
     * Retorna una Lista de todas las provincias correspondientes a un pais
     * que se pasa por parametro
     * @return ArrayList<Tupla> listaProvincias
     */
    public  ArrayList<Tupla> getProvincias(Pais p)
    {
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();

            List<Provincia> lista = p.getProvincias();
            Iterator i = lista.iterator();
            while (i.hasNext())
            {
                Provincia provincia = (Provincia)i.next();
                Tupla tupla = new Tupla(provincia.getId(),provincia.getNombre());
                tuplas.add(tupla);
            }
            return tuplas;
    }

     /**
     * @author: Iuga
     * Retorna una Lista de todas las provincias correspondientes a un pais
     * que se pasa por parametro su id
     * @return ArrayList<Tupla> listaProvincias
     */
    public  ArrayList<Tupla> getProvincias(int idPais)
    {
        // Tengo solo el ID, así que busco el objeto
        Pais p = null;
        try
        {
             HibernateUtil.beginTransaction();
             p = (Pais) sesion.load(Pais.class,idPais);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return this.getProvincias(p);
    }

    /**
     * @author: Iuga
     * Retorna una Lista de todas las localidades que tiene una provincia, se pasa
     * el id de la provincia.
     * @return ArrayList<Tupla> listaLocalidades
     */
    public  ArrayList<Tupla> getLocalidades(int idProvincia)
    {
        // Tengo solo el ID, así que busco el objeto
        Provincia p = null;
        try
        {
             HibernateUtil.beginTransaction();
             p = (Provincia) sesion.load(Provincia.class,idProvincia);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return this.getLocalidades(p);
    }

    /**
     * @author: Iuga
     * Retorna una Lista de todas las localidades que tiene una provincia, se pasa
     * el objeto provincia.
     * @return ArrayList<Tupla> listaLocalidades
     */
    public  ArrayList<Tupla> getLocalidades(Provincia p)
    {
           ArrayList<Tupla> tuplas = new ArrayList<Tupla>();

            List<Localidad> lista = p.getLocalidades();
            Iterator i = lista.iterator();
            while (i.hasNext())
            {
                Localidad l = (Localidad)i.next();
                Tupla tupla = new Tupla(l.getId(),l.getNombre());
                tuplas.add(tupla);
            }
            return tuplas;
    }

    /**
     * @author: Iuga
     * Retorna una Lista de todos los barrios que pertenezcan a la localidad que
     * pertenezca al id de Localidad que se le pasa
     * @return ArrayList<Tupla> listaBarrios
     */
    public  ArrayList<Tupla> getBarrios(int idLocalidad)
    {
        Localidad l = null;
        try
        {
             HibernateUtil.beginTransaction();
             l = (Localidad)sesion.load(Localidad.class,idLocalidad);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return this.getBarrios(l);
    }

    /**
     * @author: Iuga
     * Retorna una Lista de todos los barrios que pertenezcan a la localidad que
     * se le pasa.
     * @return ArrayList<Tupla> listaBarrios
     */
    public  ArrayList<Tupla> getBarrios(Localidad l)
    {
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();

            List<Barrio> lista = l.getBarrios();
            Iterator i = lista.iterator();
            while (i.hasNext())
            {
                Barrio b = (Barrio)i.next();
                Tupla tupla = new Tupla(b.getId(),b.getNombre());
                tuplas.add(tupla);
            }
            return tuplas;
    }

    /**
     * Me devuelve el Pais al que refiere el idPais
     * @param idPais
     * @return Pais
     */
    public Pais getPais(int idPais)
    {
        Pais pais = null;
        try
        {
             HibernateUtil.beginTransaction();
             pais = (Pais) sesion.load(Pais.class,idPais);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return pais;
    }

    /**
     * Me devuelve la provincia al que refiere el idProvincia
     * @param idProvincia
     * @return Provincia
     */
    public Provincia getProvincia(int idProvincia)
    {
        Provincia provincia = null;
        try
        {
             HibernateUtil.beginTransaction();
             provincia = (Provincia) sesion.load(Provincia.class,idProvincia);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return provincia;
    }

    /**
     * Me devuelve la Localidad al que refiere el idLocalidad
     * @param idLocalidad
     * @return Localidad
     */
    public Localidad getLocalidad(int idLocalidad)
    {
        Localidad localidad = null;
        try
        {
             HibernateUtil.beginTransaction();
             localidad = (Localidad) sesion.load(Localidad.class,idLocalidad);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return localidad;
    }

    /**
     * Me devuelve el Barrio al que refiere el idBarrio
     * @param idBarrio
     * @return Barrio
     */
    public Barrio getBarrio(int idBarrio)
    {
        Barrio barrio = null;
        try
        {
             HibernateUtil.beginTransaction();
             barrio = (Barrio) sesion.load(Barrio.class,idBarrio);
             HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return barrio;
    }

    /**
     * Se le pasa el ID de un Barrio por parametro y devuelve la localidad donde
     * se encuentra ese barrio.
     * @param idBarrio
     * @return Localidad
     */
    public Localidad getLocalidadDeBarrio(int idBarrio)
    {
          sesion.beginTransaction();
          Localidad l = null;
          Iterator it = sesion.createQuery("from Localidad as loc left join loc.barrios as barrio where barrio.id = :pid").setParameter("pid",idBarrio).list().iterator();
          while (it.hasNext())
          {
            Object[] row = (Object[])it.next();
            l = (Localidad) row[0];
          }
          return l;
    }


     /**
     * Se le pasa el ID de una Localidad por parametro y devuelve la Provincia donde
     * se encuentra esa Localidad.
     * @param idLocalidad
     * @return Provincia
     */
    public Provincia getProvinciaDeLocalidad(int idLocalidad)
    {
          sesion.beginTransaction();
          Provincia p = null;
          Iterator it = sesion.createQuery("from Provincia as prov left join prov.localidades as loc where loc.id = :pid").setParameter("pid",idLocalidad).list().iterator();
          while (it.hasNext())
          {
            Object[] row = (Object[])it.next();
            p = (Provincia) row[0];
          }
          return p;
    }

     /**
     * Se le pasa el ID de una Provincia por parametro y devuelve el Pais donde
     * se encuentra esa Provincia.
     * @param idProvincia
     * @return Pais
     */
    public Pais getPaisDeProvincia(int idProvincia)
    {
          sesion.beginTransaction();
          Pais p = null;
          Iterator it = sesion.createQuery("from Pais as p left join p.provincias as prov where prov.id = :pid").setParameter("pid",idProvincia).list().iterator();
          while (it.hasNext())
          {
            Object[] row = (Object[])it.next();
            p = (Pais) row[0];
          }
          return p;
    }

    /**
     * Este método me permite verificar si el Barrio con id = idBarrio
     * está siendo utilizado en algun domicilio.
     * @param idBarrio El id del barrio a analizar
     * @return true si el barrio es utilizado por algun domicilio y
     * false si no lo es.
     */
    public static boolean verificarExistenciaBarrioEnDomicilios(int idBarrio)
    {
        boolean existe = false;
        try
        {
            HibernateUtil.beginTransaction();
            
            List domicilios = HibernateUtil.getSession()
                    .createQuery("From Domicilio d where d.barrio.id=:idBarrio")
                    .setInteger("idBarrio", idBarrio)
                    .list();

            if(domicilios!= null && domicilios.size() >0)
            {
                existe = true;
            }
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            existe = false;
        }
        return existe;
    }
    
    /**
     * Este método me permite verificar si la localidad con id = idLocalidad
     * está siendo utilizada en algun domicilio.
     * 
     * * Lugar de Capacitación
     * @param idLocalidad El id de la localidad a analizar
     * @return true si el barrio es utilizado por algun domicilio y
     * false si no lo es.
     */
    public static boolean verificarExistenciaLocalidadEnDomicilios(int idLocalidad)
    {
        boolean existe = false;
        try
        {
            HibernateUtil.beginTransaction();
            
            List domicilios = HibernateUtil.getSession()
            .createQuery("From Domicilio d where d.barrio.id in"
            + " (select b.id FROM Localidad as l left join l.barrios as b WHERE l.id =:idLocalidad)")
            .setInteger("idLocalidad", idLocalidad)
            .list();
            
            if(domicilios!= null && domicilios.size() >0)
            {
                existe = true;
            }
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            existe = false;
            HibernateUtil.rollbackTransaction();
        }
        return existe;
    }
    
    
    /**
     * Este método me permite verificar si la provincia con id = idProvincia
     * está siendo utilizada en algun domicilio.
     * 
     * @param idProvincia El id de la provincia a analizar
     * @return true si el barrio es utilizado por algun domicilio y
     * false si no lo es.
     */
    public static boolean verificarExistenciaProvinciaEnDomicilios(int idProvincia)
    {
        boolean existe = false;
        try
        {
            HibernateUtil.beginTransaction();
            
            List domicilios = HibernateUtil.getSession()
            .createQuery("From Domicilio d where d.barrio.id in"
            + " (select b.id FROM Localidad as l left join l.barrios as b WHERE l.id in"
            + " (select locs.id from Provincia as pr left join pr.localidades as locs where pr.id=:idProvincia))")
            .setInteger("idProvincia", idProvincia)
            .list();
            
            if(domicilios!= null && domicilios.size() >0)
            {
                existe = true;
            }
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            existe = false;
            HibernateUtil.rollbackTransaction();
        }
        return existe;
    }
    
    /**
     * Este método me permite verificar si el pais con id = idPais
     * está siendo utilizado en algun domicilio.
     * 
     * @param idPais El id del pais a analizar
     * @return true si el barrio es utilizado por algun domicilio y
     * false si no lo es.
     */
    public static boolean verificarExistenciaPaisEnDomicilios(int idPais)
    {
        boolean existe = false;
        try
        {
            HibernateUtil.beginTransaction();
            
            List domicilios = HibernateUtil.getSession()
                    .createQuery("From Domicilio d where d.barrio.id in"
                    + " (select b.id FROM Localidad as l left join l.barrios as b WHERE l.id in"
                    + " (select locs.id from Provincia as pr left join pr.localidades as locs where pr.id in"
                    + " (select provs.id from Pais as pa left join pa.provincias as provs where pa.id=:idPais)))")
                    .setInteger("idPais", idPais)
                    .list();
            
            if(domicilios!= null && domicilios.size() >0)
            {
                existe = true;
            }
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            existe = false;
            HibernateUtil.rollbackTransaction();
        }
        return existe;
    }
}
