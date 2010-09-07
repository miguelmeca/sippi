/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.TipoEspecialidad;
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

public class GestorABMTipoEspecialidad {

    public GestorABMTipoEspecialidad() {
    }

    public boolean crear(String nombre, String descripcion)
    {
        TipoEspecialidad nuevo = new TipoEspecialidad();
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
                        return false;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
                return false;
            }

        return true;
    }

    public ArrayList<NTupla> mostrarNombreTiposEspecialidad()
    {
           ArrayList<NTupla> lista = new ArrayList<NTupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoEspecialidad tc order by tc.nombre").iterate();
                while (iter.hasNext())
                {
                   TipoEspecialidad tc = (TipoEspecialidad)iter.next();
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
            TipoEspecialidad tc = (TipoEspecialidad)sesion.load(TipoEspecialidad.class,oid);
            tc.setNombre(nombre);
            tc.setDescripcion(desc);

            try
            {
                sesion.saveOrUpdate(tc);
            }catch(Exception e)
            {
                System.out.println("Error al modificar un TipoEspecialidad");
                return false;
            }

        } catch (Exception ex) {
             System.out.println("No se pudo abrir la sesion");
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
            TipoEspecialidad tc = (TipoEspecialidad)sesion.load(TipoEspecialidad.class,oid);

            try
            {
                sesion.delete(tc);

            }catch(Exception e)
            {
                System.out.println("Error al modificar un TipoEspecialidad");
                return 0;
            }

        } catch (Exception ex) {
             System.out.println("No se pudo abrir la sesion");
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
                long cant = ( (Long) sesion.createQuery("select count(*) from TipoEspecialidad tc WHERE tc.nombre = :tcname ").setParameter("tcname",nombre).iterate().next() ).intValue();;
           
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
