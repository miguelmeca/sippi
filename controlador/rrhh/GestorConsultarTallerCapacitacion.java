/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import modelo.DetalleHorarioTaller;
import modelo.TallerCapacitacion;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.rrhh.pantallaConsultarTallerCapacitacion;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorConsultarTallerCapacitacion {

    private pantallaConsultarTallerCapacitacion pantalla;

    public GestorConsultarTallerCapacitacion(pantallaConsultarTallerCapacitacion pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> buscarTallerCapacitacionPorNombre(String nombre)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc WHERE tc.descripcion LIKE :nom").setParameter("nom","%"+nombre+"%").list();
                    Iterator<TallerCapacitacion> iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        String[] datos = new String[4];
                        NTupla nt = new NTupla();
                        nt.setId(tc.getId());
                        nt.setNombre(tc.getTipoCapacitacion().getNombre());

                                    datos[0] = "";
                                    datos[1] = tc.getDescripcion();
                                    datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                                    datos[3] = tc.getEstado().getNombre();

                            Iterator iter2 = tc.getDetalle().iterator();
                            while (iter2.hasNext())
                            {
                                DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                    if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                    {
                                        datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                    }

                                nt.setData(datos);
                            }

                            lista.add(nt);
                    }
                }
                catch(Exception e)
                {
                    System.out.println("No se pudo abrir la sesion: "+e.getMessage());
                    e.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }

        return lista;
    }

    public ArrayList<NTupla> consultarTalleres()
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc order by tc.id DESC").list();
                    Iterator iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        String[] datos = new String[4];
                        NTupla nt = new NTupla();
                        nt.setId(tc.getId());
                        nt.setNombre(tc.getTipoCapacitacion().getNombre());
                        datos[0] = "";
                        datos[1] = tc.getDescripcion();
                        datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                        datos[3] = tc.getEstado().getNombre();

                            Iterator iter2 = tc.getDetalle().iterator();
                            while (iter2.hasNext())
                            {
                                DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                    if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                    {
                                        datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                    }

                                nt.setData(datos);
                            }

                            lista.add(nt);
                    }
                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }
        
        return lista;
    }

    public void darBajaTallerCapacitacion(int id)
    {
        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    TallerCapacitacion tc = (TallerCapacitacion)sesion.load(TallerCapacitacion.class,id);

                        tc.darDeBaja(); // Ya di de baja , ahora actualizo

                        sesion.update(tc);
                        pantalla.MostrarMensaje("ME-0006");

                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0018");
                }
    }

    public void darAltaTallerCapacitacion(int id)
    {
        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    TallerCapacitacion tc = (TallerCapacitacion)sesion.load(TallerCapacitacion.class,id);

                        tc.darDeAlta(); // Ya di de baja , ahora actualizo

                        sesion.update(tc);
                        pantalla.MostrarMensaje("ME-0007");

                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0018");
                }
    }

    public ArrayList<NTupla> consultarTalleresEnAlta() {

       ArrayList<NTupla> lista = new ArrayList<NTupla>();

        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc order by tc.id DESC").list();
                    Iterator iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        if(tc.getEstado().esAlta())
                        {
                            String[] datos = new String[4];
                            NTupla nt = new NTupla();
                            nt.setId(tc.getId());
                            nt.setNombre(tc.getTipoCapacitacion().getNombre());
                            datos[0] = "";
                            datos[1] = tc.getDescripcion();
                            datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                            datos[3] = tc.getEstado().getNombre();

                                Iterator iter2 = tc.getDetalle().iterator();
                                while (iter2.hasNext())
                                {
                                    DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                        if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                        {
                                            datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                        }

                                    nt.setData(datos);
                                }

                                lista.add(nt);
                        }
                    }
                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }

        return lista;

    }

    public ArrayList<NTupla> consultarTalleresEnBaja() {

       ArrayList<NTupla> lista = new ArrayList<NTupla>();

        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc order by tc.id DESC").list();
                    Iterator iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        if(tc.getEstado().esBaja())
                        {
                            String[] datos = new String[4];
                            NTupla nt = new NTupla();
                            nt.setId(tc.getId());
                            nt.setNombre(tc.getTipoCapacitacion().getNombre());
                            datos[0] = "";
                            datos[1] = tc.getDescripcion();
                            datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                            datos[3] = tc.getEstado().getNombre();

                                Iterator iter2 = tc.getDetalle().iterator();
                                while (iter2.hasNext())
                                {
                                    DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                        if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                        {
                                            datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                        }

                                    nt.setData(datos);
                                }

                                lista.add(nt);
                        }
                    }
                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }

        return lista;
    }


    public ArrayList<NTupla> buscarTallerCapacitacionPorNombreEnAlta(String nombre)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc WHERE tc.descripcion LIKE :nom").setParameter("nom","%"+nombre+"%").list();
                    Iterator<TallerCapacitacion> iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        if(tc.getEstado().esAlta())
                        {
                            String[] datos = new String[4];
                            NTupla nt = new NTupla();
                            nt.setId(tc.getId());
                            nt.setNombre(tc.getTipoCapacitacion().getNombre());

                                        datos[0] = "";
                                        datos[1] = tc.getDescripcion();
                                        datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                                        datos[3] = tc.getEstado().getNombre();

                                Iterator iter2 = tc.getDetalle().iterator();
                                while (iter2.hasNext())
                                {
                                    DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                        if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                        {
                                            datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                        }

                                    nt.setData(datos);
                                }

                                lista.add(nt);
                        }
                    }
                }
                catch(Exception e)
                {
                    System.out.println("No se pudo abrir la sesion: "+e.getMessage());
                    e.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }

        return lista;
    }

    public ArrayList<NTupla> buscarTallerCapacitacionPorNombreEnBaja(String nombre)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from TallerCapacitacion tc WHERE tc.descripcion LIKE :nom").setParameter("nom","%"+nombre+"%").list();
                    Iterator<TallerCapacitacion> iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        TallerCapacitacion tc = (TallerCapacitacion)iter.next();
                        if(tc.getEstado().esBaja())
                        {
                            String[] datos = new String[4];
                            NTupla nt = new NTupla();
                            nt.setId(tc.getId());
                            nt.setNombre(tc.getTipoCapacitacion().getNombre());

                                        datos[0] = "";
                                        datos[1] = tc.getDescripcion();
                                        datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                                        datos[3] = tc.getEstado().getNombre();

                                Iterator iter2 = tc.getDetalle().iterator();
                                while (iter2.hasNext())
                                {
                                    DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                        if(datos[0].indexOf(FechaUtil.getFecha(dht.getFecha()))<0)
                                        {
                                            datos[0] = datos[0] + " " +FechaUtil.getFecha(dht.getFecha());
                                        }

                                    nt.setData(datos);
                                }

                                lista.add(nt);
                        }
                    }
                }
                catch(Exception e)
                {
                    System.out.println("No se pudo abrir la sesion: "+e.getMessage());
                    e.printStackTrace();
                    pantalla.MostrarMensaje("EG-0016");
                }

        return lista;
    }

}
