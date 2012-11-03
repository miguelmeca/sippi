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
import vista.rrhh.pantallaConsultarLicenciasEmpleado;
import vista.rrhh.pantallaGestionLicenciasEmpleado;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class gestorConsultarLicenciasEmpleado {

    private pantallaConsultarLicenciasEmpleado pantalla;

    private Empleado             n_empleado;
    private Date                 n_fechaInicio;
    private Date                 n_fechaFin;
    private TipoLicenciaEmpleado n_tipoLicencia;
    private String               n_motivo;
    
    private String  nombreEmpleado;

    public gestorConsultarLicenciasEmpleado(pantallaConsultarLicenciasEmpleado pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> mostrarLicenciasActivas(int id)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                if(id!=0)
                {
                    sesion.enableFilter("filtroLicenciaEmpleado").setParameter("idEmpleado",id);
                }
                Iterator iter = sesion.createQuery("from LicenciaEmpleado le order by le.fechaInicio DESC").iterate();
                while (iter.hasNext())
                {
                  LicenciaEmpleado le = (LicenciaEmpleado) iter.next();

                    NTupla nt = new NTupla();
                    nt.setId(le.getOid());
                    nt.setNombre(String.valueOf(le.getEmpleado().getLegajo()));

                        Object[] datos = new Object[4];
                        datos[0] = le.getEmpleado().getApellido()+", "+le.getEmpleado().getNombre();

                            String fechaIni = FechaUtil.getFecha(le.getFechaInicio());
                            String fechaFin = FechaUtil.getFecha(le.getFechaFin());

                            if(fechaFin.equals(fechaIni))
                            {
                                datos[1] = fechaIni;
                            }
                            else
                            {
                                datos[1] = fechaIni+" - "+fechaFin;
                            }
                        datos[2] = le.getEstado();
                        datos[3] = le.getTipoLicencia().getNombre();
                    nt.setData(datos);

                  lista.add(nt);
                  if(id!=0)
                  {
                      setNombreEmpleado(le.getEmpleado().getApellido()+", "+le.getEmpleado().getNombre()+ " Legajo Nº:"+String.valueOf(le.getEmpleado().getLegajo()));
                        sesion.enableFilter("filtroLicenciaEmpleado").setParameter("idEmpleado",id);
                  }
                }

               }
               catch(Exception ex)
               {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    pantalla.MostrarMensaje("EG-0013");
               }

        return lista;
    }

    /**
     * @return the nombreEmpleado
     */
    public String getNombreEmpleado(int id) {
        Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
            //sesion.beginTransaction();
            Empleado empleado = (Empleado) sesion.createQuery("from Empleado where id ="+id).uniqueResult();
            return empleado.getApellido()+", "+empleado.getNombre()+ " Legajo Nº:"+String.valueOf(empleado.getLegajo());
              } catch (Exception ex)
            {
                System.out.println("Error levantando el empleado");
                return null;
            }        
    }

    /**
     * @param nombreEmpleado the nombreEmpleado to set
     */
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

}
