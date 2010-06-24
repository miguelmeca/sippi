/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.TipoCapacitacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorABMTipoCapacitacion {

    public GestorABMTipoCapacitacion() {
    }

    public TipoCapacitacion crear(String nombre, String descripcion)
    {
        TipoCapacitacion nuevo = new TipoCapacitacion();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(descripcion);

         SessionFactory sf = HibernateUtil.getSessionFactory();
                    Session sesion;
                    try {
                    sesion = HibernateUtil.getSession();
                    try{
                    HibernateUtil.beginTransaction();

                            sesion.save(nuevo);

                        HibernateUtil.commitTransaction();
                    }catch(Exception e) {
                        System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                        return null;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
            }

        return nuevo;
    }

    public ArrayList<Tupla> mostrarNombreTiposCapacitacion()
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoCapacitacion tc order by tc.nombre").iterate();
                while (iter.hasNext())
                {
                   TipoCapacitacion tc = (TipoCapacitacion)iter.next();
                   Tupla t = new Tupla();
                   t.setId(tc.getId());
                   t.setNombre(tc.getNombre());
                   lista.add(t);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion");
           }

           return lista;
    }

}
