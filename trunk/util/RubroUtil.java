/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Alojamiento;
import modelo.Consumible;
import modelo.Herramienta;
import modelo.Indumentaria;
import modelo.Material;
import modelo.Rubro;
import modelo.Transporte;
import modelo.Viatico;
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

    public static Rubro getRubro(int id)
    {
          Rubro rb = new Rubro();
          Session sesion;
          try
          {
                sesion = HibernateUtil.getSession();
                rb = (Rubro)sesion.load(Rubro.class,id);

          }catch(Exception e)
          {
              LogUtil.addError("Error al cargar los rubros");
          }
          return rb;
    }
}
