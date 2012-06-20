/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.TipoCapacitacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;
import util.NTupla;
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
                return null;
            }

        return nuevo;
    }

    public ArrayList<NTupla> mostrarNombreTiposCapacitacion()
    {
           ArrayList<NTupla> lista = new ArrayList<NTupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoCapacitacion tc order by tc.nombre").iterate();
                while (iter.hasNext())
                {
                   TipoCapacitacion tc = (TipoCapacitacion)iter.next();
                   NTupla t = new NTupla();
                   t.setId(tc.getId());
                   t.setNombre(tc.getNombre());
                   t.setData(tc.getDescripcion());
                   lista.add(t);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion");
           }

           return lista;
    }

    public boolean modificar(int oid, String nombre, String desc)
    {
        try
        {
            Session sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            TipoCapacitacion tc = (TipoCapacitacion)sesion.load(TipoCapacitacion.class,oid);
            tc.setNombre(nombre);
            tc.setDescripcion(desc);

            try
            {
                sesion.saveOrUpdate(tc);
            }catch(Exception e)
            {
                System.out.println("Error al modificar un TipoCapacitacion");
                return false;
            }
            HibernateUtil.commitTransaction();

        } catch (Exception ex) {
             System.out.println("No se pudo abrir la sesion");
             HibernateUtil.rollbackTransaction();
             return false;
        }

        return true;
    }

    /**
     * 1 - Correcta eliminacion
     * 2 - Se esta usando
     * 0 - Error desconocido
     * @param oid
     * @return
     */
    public int eliminar(int oid)
    {
         try
        {
            Session sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            TipoCapacitacion tc = (TipoCapacitacion)sesion.load(TipoCapacitacion.class,oid);

            try
            {
                sesion.delete(tc);

            }catch(Exception e)
            {
                System.out.println("Error al modificar un TipoCapacitacion");
                return 0;
            }
            HibernateUtil.commitTransaction();

        } catch (Exception ex) {
             System.out.println("No se pudo abrir la sesion");
             HibernateUtil.rollbackTransaction();
             return 0;
        }
        return 1;
    }

    public boolean existeTipo(String nombre)
    {
        SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();
                long cant = ( (Long) sesion.createQuery("select count(*) from TipoCapacitacion tc WHERE tc.nombre = :tcname ").setParameter("tcname",nombre).iterate().next() ).intValue();;
           
                if(cant > 0)
                {
                    return true;
                }

        }catch (Exception ex) {
             System.out.println("No se pudo abrir la sesion");
        }
        return false;
    }

}
