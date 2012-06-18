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
import modelo.Empleado;
import modelo.TallerCapacitacion;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.rrhh.pantallaConsultarTallerCapacitacion;
import vista.rrhh.pantallaVerTallerCapacitacion;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorVerTallerCapacitacion {

    private pantallaVerTallerCapacitacion pantalla;

    private TallerCapacitacion taller;

    public GestorVerTallerCapacitacion(pantallaVerTallerCapacitacion pantalla) {
        this.pantalla = pantalla;
    }

    public void cargarDatosTaller(int id)
    {
               Session sesion;
               try
               {
                    sesion = HibernateUtil.getSession();
                    HibernateUtil.beginTransaction();

                    this.taller = (TallerCapacitacion) sesion.load(TallerCapacitacion.class,id);
                    HibernateUtil.commitTransaction();

               }catch(Exception ex)
               {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    HibernateUtil.rollbackTransaction();
                    pantalla.MostrarMensaje("EG-0017");
               }

               // Paso los datos a la pantalla
               String nombre = this.taller.getCapacitador().getApellido()+", "+this.taller.getCapacitador().getNombre();
               pantalla.setDatosGenerales(this.taller.getTipoCapacitacion().getNombre(), taller.getDescripcion(),nombre);

               // Paso los cursos
               ArrayList<NTupla> lista = new ArrayList<NTupla>();
               Iterator it = this.taller.getDetalle().iterator();
               while (it.hasNext())
               {
                    DetalleHorarioTaller dht = (DetalleHorarioTaller)it.next();

                    NTupla nt = new NTupla();
                    nt.setId(dht.getId());
                    nt.setNombre(FechaUtil.getFecha(dht.getFecha()));

                        String[] horas = new String[2];
                        horas[0] = dht.getHoraInicio();
                        horas[1] = dht.getHoraFin();

                    nt.setData(horas);
                    lista.add(nt);
                }
               pantalla.setHorarios(lista);

               // Paso el lugar
               pantalla.setLugar(taller.getLugar().getNombre(),taller.getLugar().getDomicilio().toString());

               // Paso los empleados
               ArrayList<NTupla> listaEmpleados = new ArrayList<NTupla>();
               Iterator i2 = taller.getEmpleados().iterator();
               while (i2.hasNext())
               {
                    Empleado emp = (Empleado)i2.next();
                    NTupla nt = new NTupla();
                    nt.setId(emp.getOid());
                    nt.setNombre(String.valueOf(emp.getLegajo()));
                        String[] dato = new String[2];
                        dato[0] = emp.getApellido();
                        dato[1] = emp.getNombre();
                    nt.setData(dato);
                    listaEmpleados.add(nt);
                }
               pantalla.setEmpleados(listaEmpleados);
    }

}
