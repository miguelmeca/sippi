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
        Pais p = (Pais) sesion.load(Pais.class,idPais);
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
        Provincia p = (Provincia) sesion.load(Provincia.class,idProvincia);
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
        Localidad l = (Localidad)sesion.load(Localidad.class,idLocalidad);
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
        return (Pais) sesion.load(Pais.class,idPais);
    }

    /**
     * Me devuelve la provincia al que refiere el idProvincia
     * @param idProvincia
     * @return Provincia
     */
    public Provincia getProvincia(int idProvincia)
    {
        return (Provincia) sesion.load(Provincia.class,idProvincia);
    }

    /**
     * Me devuelve la Localidad al que refiere el idLocalidad
     * @param idLocalidad
     * @return Localidad
     */
    public Localidad getLocalidad(int idLocalidad)
    {
        return (Localidad) sesion.load(Localidad.class,idLocalidad);
    }

    /**
     * Me devuelve el Barrio al que refiere el idBarrio
     * @param idBarrio
     * @return Barrio
     */
    public Barrio getBarrio(int idBarrio)
    {
        return (Barrio) sesion.load(Barrio.class,idBarrio);
    }

}
