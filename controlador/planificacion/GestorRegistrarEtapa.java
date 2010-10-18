/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.Date;
import modelo.Etapa;
import util.HibernateUtil;
import vista.planificacion.pantallaRegistrarEtapa;


/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorRegistrarEtapa {

    private pantallaRegistrarEtapa pantalla;
    private Etapa etapa;


    public GestorRegistrarEtapa(pantallaRegistrarEtapa pantalla) {
        this.pantalla = pantalla;
    }

    public void cargarEtapa(int idEtapa)
    {
        this.etapa = getEtapa(idEtapa);
    }

    private Etapa getEtapa(int idEtapa)
    {
        try
        {
            return (Etapa) HibernateUtil.getSession().load(Etapa.class,idEtapa);
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0001");
        }
        return null;
    }

    public void mostrarDatosEtapa()
    {
        pantalla.mostrarDatosEtapa(etapa.getNombre(),etapa.getFechaInicio(),etapa.getFechaFin(),etapa.getDescripcion());
    }

    public void guardarCambiosBaseEtapa(String nombre, Date fechaInicio, Date fechaFin, String obs)
    {
        etapa.setNombre(nombre);
        etapa.setFechaInicio(fechaInicio);
        etapa.setFechaFin(fechaFin);
        etapa.setDescripcion(obs);

            try
            {
                HibernateUtil.beginTransaction();
                HibernateUtil.getSession().saveOrUpdate(etapa);
                HibernateUtil.commitTransaction();
            }
            catch(Exception ex)
            {
                pantalla.MostrarMensaje("ME-0002");
            }

    }

}
