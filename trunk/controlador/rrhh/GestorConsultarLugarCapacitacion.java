/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import modelo.Empleado;
import modelo.LicenciaEmpleado;
import modelo.LugardeCapacitacion;
import modelo.EstadoLugarCapacitacionAlta;
import modelo.EstadoLugarCapacitacionBaja;
import modelo.EstadoLugarCapacitacion;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.rrhh.pantallaConsultarLugarCapacitacion;
import vista.rrhh.pantallaGestionLicenciasEmpleado;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class GestorConsultarLugarCapacitacion {

    private pantallaConsultarLugarCapacitacion pantalla;

    public GestorConsultarLugarCapacitacion(pantallaConsultarLugarCapacitacion pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> mostrarLugaresCapacitacion()
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from LugardeCapacitacion lc order by lc.nombre").list();
                    Iterator<LugardeCapacitacion> iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        LugardeCapacitacion lc = iter.next();

                        NTupla nt = new NTupla();
                        nt.setId(lc.getId());
                        nt.setNombre(lc.getNombre());
                        String[] aux = new String[2];
                            aux[0] = lc.getDomicilio().toString();
                            aux[1] = lc.getEstado().getNombre();

                        nt.setData(aux);

                        lista.add(nt);
                    }
                }
                catch(Exception e)
                {
                    System.out.println("No se pudo abrir la sesion: "+e.getMessage());
                    e.printStackTrace();
                    pantalla.MostrarMensaje("EG-0015");
                }
        
        return lista;
    }

    public ArrayList<NTupla> buscarLugaresCapacitacionPorNombre(String nombre)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    List listaIt = sesion.createQuery("from LugardeCapacitacion lc WHERE lc.nombre LIKE :nom order by lc.nombre").setParameter("nom","%"+nombre+"%").list();
                    Iterator<LugardeCapacitacion> iter = listaIt.iterator();
                    while (iter.hasNext())
                    {
                        LugardeCapacitacion lc = iter.next();

                        NTupla nt = new NTupla();
                        nt.setId(lc.getId());
                        nt.setNombre(lc.getNombre());

                        String[] aux = new String[2];
                            aux[0] = lc.getDomicilio().toString();
                            aux[1] = lc.getEstado().getNombre();

                        nt.setData(aux);

                        lista.add(nt);
                    }
                }
                catch(Exception e)
                {
                    System.out.println("No se pudo abrir la sesion: "+e.getMessage());
                    e.printStackTrace();
                    pantalla.MostrarMensaje("EG-0015");
                }

        return lista;
    }

    public void darBajaLugarCapacitacion(int id)
    {
        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    LugardeCapacitacion tc = (LugardeCapacitacion)sesion.load(LugardeCapacitacion.class,id);

                        tc.darDeBaja(); // Ya di de baja , ahora actualizo

                        sesion.update(tc);
                        pantalla.MostrarMensaje("MI-0008");

                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0019");
                }
    }

    public void darAltaLugarCapacitacion(int id)
    {
        Session sesion;
            try {
                    sesion = HibernateUtil.getSession();
                    LugardeCapacitacion tc = (LugardeCapacitacion)sesion.load(LugardeCapacitacion.class,id);

                        tc.darDeAlta(); // Ya di de baja , ahora actualizo

                        sesion.update(tc);
                        pantalla.MostrarMensaje("MI-0009");

                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                    pantalla.MostrarMensaje("EG-0019");
                }
    }

    
}
