/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion_old;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import modelo.Etapa;
import modelo.Presupuesto;
import org.hibernate.Session;
import util.HibernateUtil;
import util.LogUtil;
import util.NTupla;
import vista.planificacion_old.pantallaRegistrarEtapa;


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
    private Presupuesto presupuesto;


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

    public ArrayList<NTupla> getListaEtapasRelacionadas(int idPresupuesto,int idEtapa)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        // CArgo el presupuesto y lo dejo en el gestor
        cargarPresupuesto(idPresupuesto);
        Iterator<Etapa> ite = this.presupuesto.getEtapas().iterator();
        while (ite.hasNext()) 
        {
            Etapa et = ite.next();

            if(et.getId() != idEtapa)
            {
                NTupla nt = new NTupla(et.getId());
                nt.setNombre(et.getNombre());

                   // VEO SI ESTA EN LA ETAPA ACTUAL, Y LA MARCO
                   Etapa etActual = getEtapa(idEtapa);
                   Iterator<Etapa> itPred = etActual.getCpm().iterator();
                   Boolean esRelacion = false;
                   while (itPred.hasNext())
                   {
                        Etapa etapaPred = itPred.next();
                        if(etapaPred.getId() == et.getId())
                        {
                            esRelacion = true;
                        }
                   }
                   nt.setData(esRelacion);

                lista.add(nt);
            }
        }

        return lista;
    }


    public void cargarPresupuesto(int id)
    {
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                this.presupuesto = (Presupuesto)sesion.load(Presupuesto.class,id);
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo el presupuesto");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0003");
           }
    }

    public void modificarRelacionEtapa(int idEtapaActual, int idEtapa, boolean relacion) {

        // CARGO LA ETAPA
        Etapa eActual = getEtapa(idEtapaActual);

        // VEO SI TIENE LA ETAPA
        boolean laTiene = false;
        Iterator<Etapa> ite = eActual.getCpm().iterator();
        while (ite.hasNext())
        {
            Etapa e = ite.next();
            if(e.getId()==idEtapa)
            {
                laTiene = true;
            }
        }


        // MODDIFICO
        if(laTiene&&relacion)
        {
            // NO HAGO NADA
        }
        if(!laTiene&&!relacion)
        {
            // NO HAGO NADA
        }
        if(laTiene&&!relacion)
        {
            // ELIMINO
            Etapa etapaAEliminar=null;
            Iterator<Etapa> itex = eActual.getCpm().iterator();
            while (itex.hasNext())
            {
                Etapa etDel = itex.next();
                if(etDel.getId()==idEtapa)
                {
                    etapaAEliminar = etDel;
                }
            }
                    if(etapaAEliminar!=null)
                    {
                        eActual.getCpm().remove(etapaAEliminar);
                        try
                        {
                            HibernateUtil.beginTransaction();
                            HibernateUtil.getSession().saveOrUpdate(eActual);
                            HibernateUtil.commitTransaction();
                        }
                        catch(Exception e)
                        {
                            pantalla.MostrarMensaje("ME-0007");
                        }
                    }
        }
        if(!laTiene&&relacion)
        {
            // LA AGREGO
            Etapa etAdd = getEtapa(idEtapa);
            eActual.getCpm().add(etAdd);

                        try
                        {
                            HibernateUtil.beginTransaction();
                            HibernateUtil.getSession().saveOrUpdate(eActual);
                            HibernateUtil.commitTransaction();
                        }
                        catch(Exception e)
                        {
                            pantalla.MostrarMensaje("ME-0007");
                        }

        }

    }

}
