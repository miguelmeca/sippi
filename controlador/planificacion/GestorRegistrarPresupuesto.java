/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import modelo.Etapa;
import modelo.PedidoObra;
import modelo.Presupuesto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FechaUtil;
import util.HibernateUtil;
import util.LogUtil;
import util.NTupla;
import vista.planificacion.pantallaRegistrarPresupuesto;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorRegistrarPresupuesto {

    private pantallaRegistrarPresupuesto pantalla;
    private Presupuesto presupuesto;
    private PedidoObra obra;

    public GestorRegistrarPresupuesto(pantallaRegistrarPresupuesto pantalla) {
        this.pantalla = pantalla;

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
               pantalla.MostrarMensaje("ME-0001");
           }
    }

    public void cargarObra(int idObra)
    {
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                this.obra = (PedidoObra)sesion.load(PedidoObra.class,idObra);
                pantalla.mostrarDatosObra(obra.getId(),obra.getNombre(),obra.getPlanta().getRazonSocial());
                pantalla.mostrarFechaInicioYFin(FechaUtil.getFecha(obra.getFechaInicio()),FechaUtil.getFecha(obra.getFechaFin()));
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo cargar la Obra");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0004");
           }
    }

    public void buscarObraPorPresupuesto(int idPresupuesto) {

        ArrayList<PedidoObra> lista = new ArrayList<PedidoObra>();

        try
        {
            Iterator<PedidoObra> it = HibernateUtil.getSession().createQuery("FROM PedidoObra").iterate();
            while (it.hasNext())
            {
                PedidoObra po = it.next();
                Iterator<Presupuesto> itp = po.getPresupuestos().iterator();
                while (itp.hasNext())
                {
                    Presupuesto pres = itp.next();
                    if(pres.getId()==idPresupuesto)
                    {
                        this.obra = po;
                        pantalla.mostrarDatosObra(obra.getId(),obra.getNombre(),obra.getPlanta().getRazonSocial());
                        pantalla.mostrarFechaInicioYFin(FechaUtil.getFecha(obra.getFechaInicio()),FechaUtil.getFecha(obra.getFechaFin()));
                        return;
                    }
                }
            }
        }catch (Exception ex)
        {
            pantalla.MostrarMensaje("ME-0002");
        }

    }

    public ArrayList<NTupla> cargarEtapas()
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        // POR SI NO TENGO ETAPAS
        if(presupuesto.getEtapas()!=null)
        {
            Iterator<Etapa> it = presupuesto.getEtapas().iterator();
            while (it.hasNext())
            {
                Etapa et= it.next();
                NTupla nt = new NTupla(et.getId());
                nt.setNombre(et.getNombre());

                    Object[] data = new Object[2];
                    data[0] = et.getFechaInicio();
                    data[1] = et.getFechaFin();

                 nt.setData(data);
                 lista.add(nt);
            }
        }

        return lista;


    }


    public int crearEtapa(String nombre, Date fechaInicio, Date FechaFin)
    {
        Etapa e = new Etapa();
        e.setNombre(nombre);
        e.setFechaInicio(fechaInicio);
        e.setFechaFin(FechaFin);
        this.presupuesto.addEtapa(e);
        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().save(e);
            HibernateUtil.getSession().saveOrUpdate(this.presupuesto);
            HibernateUtil.commitTransaction();
            return e.getId();

        }catch (Exception ex)
        {
            pantalla.MostrarMensaje("ME-0003");
        }

        return 0;

    }

    public void cambiarFechaInicioEtapa(int idEtapa, Date fechaInicio)
    {
        System.out.println("SE CAMBIO LA FECHA DE INICIO DE LA ETAPA: "+idEtapa+" A "+FechaUtil.getFecha(fechaInicio));
    }

    public void muevoTarea(int idEtapa, Date fechaInicio)
    {
        System.out.println("SE MOVIO LA ETAPA: "+idEtapa+" A "+FechaUtil.getFecha(fechaInicio));
    }
   
    public void cambiarFechaFinEtapa(int idEtapa, Date fechaFinNueva)
    {
        System.out.println("SE CAMBIO LA FECHA DE FIN DE LA ETAPA: "+idEtapa+" A "+FechaUtil.getFecha(fechaFinNueva));
    }

}