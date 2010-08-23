/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import modelo.Empleado;
import modelo.LicenciaEmpleado;
import modelo.TipoLicenciaEmpleado;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.rrhh.pantallaGestionLicenciasEmpleado;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class gestorGestionarLicenciasEmpleado {

    private pantallaGestionLicenciasEmpleado pantalla;

    private Empleado             n_empleado;
    private Date                 n_fechaInicio;
    private Date                 n_fechaFin;
    private TipoLicenciaEmpleado n_tipoLicencia;
    private String               n_motivo;

    public gestorGestionarLicenciasEmpleado(pantallaGestionLicenciasEmpleado pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarTiposLicencia()
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoLicenciaEmpleado tle order by tle.nombre").iterate();
                while (iter.hasNext())
                {
                  TipoLicenciaEmpleado tle = (TipoLicenciaEmpleado) iter.next();
                  lista.add(new Tupla(tle.getOid(),tle.getNombre()));
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0007");
           }
           return lista;
    }

    public ArrayList<Tupla> mostrarEmpleados()
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from Empleado e order by e.apellido").iterate();
                while (iter.hasNext())
                {
                  Empleado e = (Empleado) iter.next();
                  lista.add(new Tupla(e.getOid(),"Legajo: "+e.getLegajo()+" - "+e.getApellido()+", "+e.getNombre()));
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0008");
           }
           return lista;
    }

    public void setEmpleado(Tupla tp) 
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Empleado e = (Empleado)sesion.load(Empleado.class,tp.getId());

                this.n_empleado = e;

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0009");
           }

    }

    public void setFechaFin(Date n_fechaFin) {
        this.n_fechaFin = n_fechaFin;
    }

    public void setFechaInicio(Date n_fechaInicio) {
        this.n_fechaInicio = n_fechaInicio;
    }

    public void setMotivo(String n_motivo) {
        this.n_motivo = n_motivo;
    }

    public void setTipoLicencia(Tupla tp) {

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                TipoLicenciaEmpleado tl = (TipoLicenciaEmpleado)sesion.load(TipoLicenciaEmpleado.class,tp.getId());

                this.n_tipoLicencia = tl;

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0010");
           }
    }

    public void confirmarNuevaLicencia()
    {
        if(this.n_empleado!=null && this.n_tipoLicencia!= null)
        {
            if(!existeLicenciaEnFecha(n_empleado, n_fechaInicio, n_fechaFin))
            {
                LicenciaEmpleado le = new LicenciaEmpleado();
                le.setEmpleado(n_empleado);
                le.setEstado("Programada");
                le.setFechaFin(n_fechaFin);
                le.setFechaInicio(n_fechaInicio);
                le.setMotivo(n_motivo);
                le.setTipoLicencia(n_tipoLicencia);


                Session sesion;
                try
                {
                    sesion = HibernateUtil.getSession();
                    HibernateUtil.beginTransaction();
                    sesion.save(le);
                    HibernateUtil.commitTransaction();
                    
                }catch(Exception ex)
                {
                    HibernateUtil.rollbackTransaction();
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    pantalla.MostrarMensaje("EG-0012");
                }

                pantalla.MostrarMensaje("MI-0004");

            }
            else
            {
                pantalla.MostrarMensaje("MI-0005");
            }
        }
        else
        {
                System.out.println("Faltan Datos");
                pantalla.MostrarMensaje("EG-0011");
        }
    }

    public void setPantalla(pantallaGestionLicenciasEmpleado pantalla) {
        this.pantalla = pantalla;
    }

    private boolean existeLicenciaEnFecha(Empleado e, Date inicio, Date fin)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from LicenciaEmpleado le WHERE le.empleado.legajo = :legEmp").setParameter("legEmp",e.getLegajo()).iterate();
                while (iter.hasNext())
                {
                  LicenciaEmpleado le = (LicenciaEmpleado) iter.next();

                  if(FechaUtil.fechaEnRango(inicio,le.getFechaInicio(),le.getFechaFin()) || FechaUtil.fechaEnRango(fin,le.getFechaInicio(),le.getFechaFin()))
                  {
                    return true;
                  }

                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0008");
           }
        return false;
    }

    private boolean existeLicenciaEnFechaIgnorarActual(Empleado e, Date inicio, Date fin,int id)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from LicenciaEmpleado le WHERE le.empleado.legajo = :legEmp").setParameter("legEmp",e.getLegajo()).iterate();
                while (iter.hasNext())
                {
                  LicenciaEmpleado le = (LicenciaEmpleado) iter.next();

                  if(le.getOid() != id)
                  {
                      if(FechaUtil.fechaEnRango(inicio,le.getFechaInicio(),le.getFechaFin()) || FechaUtil.fechaEnRango(fin,le.getFechaInicio(),le.getFechaFin()))
                      {
                        return true;
                      }
                  }

                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0008");
           }
        return false;
    }

    public void mostrarLicencia(int oid)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                LicenciaEmpleado tl = (LicenciaEmpleado)sesion.load(LicenciaEmpleado.class,oid);

                pantalla.mostrarFechas(tl.getFechaInicio(),tl.getFechaFin());
                pantalla.mostrarMotivo(tl.getMotivo());
                pantalla.seleccionarEmpleadoLicencia(tl.getEmpleado().getoId());
                pantalla.seleccionarTipoLicencia(tl.getTipoLicencia().getOid());


               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                pantalla.MostrarMensaje("EG-0010");
           }

    }

    public void modificarLicencia(int id) {

        if(this.n_empleado!=null && this.n_tipoLicencia!= null)
        {
            if(!existeLicenciaEnFechaIgnorarActual(n_empleado, n_fechaInicio, n_fechaFin,id))
            {
                Session sesion;
                try {
                       sesion = HibernateUtil.getSession();

                       LicenciaEmpleado le = (LicenciaEmpleado)sesion.load(LicenciaEmpleado.class,id);

                        le.setEmpleado(n_empleado);
                        le.setEstado("Programada");
                        le.setFechaFin(n_fechaFin);
                        le.setFechaInicio(n_fechaInicio);
                        le.setMotivo(n_motivo);
                        le.setTipoLicencia(n_tipoLicencia);

                        HibernateUtil.beginTransaction();
                        sesion.update(le);
                        HibernateUtil.commitTransaction();

                }catch(Exception ex)
                {
                    HibernateUtil.rollbackTransaction();
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    pantalla.MostrarMensaje("EG-0012");
                }

                pantalla.MostrarMensaje("MI-0013");

            }
            else
            {
                pantalla.MostrarMensaje("MI-0005");
            }
        }
        else
        {
                System.out.println("Faltan Datos");
                pantalla.MostrarMensaje("EG-0011");
        }


    }


    
}
