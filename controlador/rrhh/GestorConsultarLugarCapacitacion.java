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
import modelo.TipoLicenciaEmpleado;
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
                        nt.setData(lc.getDomicilio().toString());

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


    
}
