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

                            Iterator iter2 = tc.getDetalle().iterator();
                            while (iter2.hasNext())
                            {
                                DetalleHorarioTaller dht = (DetalleHorarioTaller)iter2.next();

                                NTupla nt = new NTupla();
                                nt.setId(tc.getId());
                                nt.setNombre(tc.getTipoCapacitacion().getNombre());

                                    String[] datos = new String[3];
                                    datos[0] = FechaUtil.getFecha(dht.getFecha());
                                    datos[1] = dht.getHoraInicio()+" - "+dht.getHoraFin();
                                    datos[2] = tc.getCapacitador().getApellido()+", "+tc.getCapacitador().getNombre();
                                    nt.setData(datos);

                                lista.add(nt);

                            }
                    }

                }
                catch(Exception ex)
                {
                    System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                    ex.printStackTrace();
                }
        
        return lista;
    }



}
