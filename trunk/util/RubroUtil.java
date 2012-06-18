/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Rubro;
import org.hibernate.Session;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class RubroUtil {

    public static ArrayList<Rubro> getRubros()
    {
        ArrayList<Rubro> lista = new ArrayList<Rubro>();
          Session sesion;
          try 
          {
                sesion = HibernateUtil.getSession();
                List<Rubro> listaRubro = sesion.createQuery("from Rubro").list();
                Iterator<Rubro> itr = listaRubro.iterator();
                while (itr.hasNext()) 
                {
                  Rubro rubro = itr.next();
                  lista.add(rubro);
                }
                
          }catch(Exception e)
          {
              LogUtil.addError("Error al cargar los rubros");
          }
        return lista;
    }

    public static ArrayList<Tupla> getTuplasRubros()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
          Session sesion;
          try
          {
                sesion = HibernateUtil.getSession();
                List<Rubro> listaRubro = sesion.createQuery("from Rubro").list();
                Iterator<Rubro> itr = listaRubro.iterator();
                while (itr.hasNext())
                {
                  Tupla tu= new Tupla();
                  Rubro rubro = itr.next();
                  tu.setId(rubro.getId());
                  tu.setNombre(rubro.getNombre());
                  lista.add(tu);
                }

          }catch(Exception e)
          {
              LogUtil.addError("Error al cargar los rubros");
          }
        return lista;
    }

    public static Rubro getRubro(int id)
    {
          Rubro rb = new Rubro();
          Session sesion;
          try
          {
                sesion = HibernateUtil.getSession();
                HibernateUtil.beginTransaction();
                rb = (Rubro)sesion.load(Rubro.class,id);
                HibernateUtil.commitTransaction();

          }catch(Exception e)
          {
              LogUtil.addError("Error al cargar los rubros");
              HibernateUtil.rollbackTransaction();
          }
          return rb;
    }
}
