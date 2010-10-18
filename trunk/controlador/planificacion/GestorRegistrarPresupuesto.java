/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import modelo.DetalleMaterial;
import modelo.Etapa;
import modelo.Material;
import modelo.PedidoObra;
import modelo.Presupuesto;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.Tarea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FechaUtil;
import util.HibernateUtil;
import util.LogUtil;
import util.NTupla;
import util.RecursosUtil;
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
                pantalla.mostrarGananciaEmpresa(String.valueOf(this.presupuesto.getGanancia()));
                pantalla.mostrarPorcentajeConsumible(String.valueOf(this.presupuesto.getConsumibles()));
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
        // CAMBIO LA FECHA DE INICIO DE LA ETAPA
        // CARGO LA ETAPA
        Etapa e = cargarEtapa(idEtapa);
        if(e!=null)
        {
            e.setFechaInicio(fechaInicio);
            try
            {
                HibernateUtil.beginTransaction();
                HibernateUtil.getSession().saveOrUpdate(e);
                HibernateUtil.commitTransaction();
            }
            catch(Exception ex)
            {
                pantalla.MostrarMensaje("ME-0005");
            }
        }


    }

    public void muevoTarea(int idEtapa, Date fechaInicio)
    {
        // CAMBIO DE LUGAR LA ETAPA
        // CARGO LA ETAPA
        Etapa e = cargarEtapa(idEtapa);

        if(e!=null)
        {
            // VEO ACTUALMENTE CUANTOS DIAS DURA
            int dias = FechaUtil.diasDiferencia(e.getFechaInicio(),e.getFechaFin());
            e.setFechaInicio(fechaInicio);

            Date finNueva = FechaUtil.fechaMas(fechaInicio,dias);
            e.setFechaFin(finNueva);

            try
            {
                HibernateUtil.beginTransaction();
                HibernateUtil.getSession().saveOrUpdate(e);
                HibernateUtil.commitTransaction();
            }
            catch(Exception ex)
            {
                pantalla.MostrarMensaje("ME-0005");
            }
        }
    }
   
    public void cambiarFechaFinEtapa(int idEtapa, Date fechaFinNueva)
    {
        // CAMBIO LA FECHA DE FIN DE LA ETAPA
        // CARGO LA ETAPA
        Etapa e = cargarEtapa(idEtapa);
        if(e!=null)
        {
            e.setFechaFin(fechaFinNueva);
            try
            {
                HibernateUtil.beginTransaction();
                HibernateUtil.getSession().saveOrUpdate(e);
                HibernateUtil.commitTransaction();
            }
            catch(Exception ex)
            {
                pantalla.MostrarMensaje("ME-0005");
            }
        }
    }

    private Etapa cargarEtapa(int idEtapa)
    {
        try
        {
            return (Etapa) HibernateUtil.getSession().load(Etapa.class,idEtapa);
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0004");
        }
        return null;
    }

    public void mostrarDatosEtapa(int idEtapa)
    {
        Etapa e = cargarEtapa(idEtapa);
        if(e!=null)
        {
            pantalla.mostrarDatosEtapa(e.getNombre(),e.getFechaInicio(),e.getFechaFin());
            pantalla.mostrarSubTotales(e.calcularSubTotalMateriales(),
                                       e.calcularSubTotalTranporteMateriales(),
                                       e.calcularSubTotalTrasladoPersonas(),
                                       e.calcularSubTotalHsHombre(),
                                       e.calcularSubTotalAlojamiento());
        }
    }

    public void cambiarGananciaEmpresa(double ganancia)
    {
        presupuesto.setGanancia(ganancia);
        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(presupuesto);
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0006");
        }
    }

    public void cambiarPorcentajeConsumible(float porce)
    {
        presupuesto.setConsumibles(porce);
        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(presupuesto);
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0007");
        }
    }

    public void modificarViaticos(int cantEmp, int cantDias, double monto) throws Exception
    {

        presupuesto.setCantDiasViaticos(cantDias);
        presupuesto.setCantEmpleadosViaticos(cantEmp);
        presupuesto.setMontoViaticos(monto);

        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(presupuesto);
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0007");
        }
        
    }

    public int getViaticosDias()
    {
        return presupuesto.getCantDiasViaticos();
    }

    public int getViaticosEmpleados()
    {
        return presupuesto.getCantEmpleadosViaticos();
    }

    public double getViaticosMonto()
    {
        return presupuesto.getMontoViaticos();
    }

    public void mostrarTotales() {

        pantalla.mostrarMontosTotales(presupuesto.CalcularCostoBase(),presupuesto.CalcularTotal());

    }

    public ArrayList<NTupla> getListadoTareas(int idEtapa)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        try
        {
            Etapa e = cargarEtapa(idEtapa);
            Iterator<Tarea> it = e.getTareas().iterator();
            while (it.hasNext())
            {
                Tarea tar = it.next();
                NTupla nt = new NTupla(tar.getId());
                nt.setNombre(tar.getDescripcion());
                lista.add(nt);
            }
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0009");
        }
        return lista;
    }

    public ArrayList<NTupla> getListadoMateriales(int idEtapa)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        try
        {
            Etapa e = cargarEtapa(idEtapa);
            Iterator<Tarea> it = e.getTareas().iterator();
            while (it.hasNext())
            {
                Tarea tar = it.next();
                Iterator<DetalleMaterial> itdm = tar.getDetallesMaterial().iterator();
                while (itdm.hasNext())
                {
                    DetalleMaterial dm = itdm.next();
                    
                        RecursoXProveedor rxp = dm.getMaterial();
                        RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
                        Material m = (Material)RecursosUtil.getMaterial(re);

                    NTupla nt = new NTupla(dm.getId());
                    nt.setNombre(m.getNombre());
                    String cant = String.valueOf(dm.getCantidad());
                    nt.setData(cant);

                   lista.add(nt);

                }

                
            }
        }
        catch(Exception e)
        {
            pantalla.MostrarMensaje("ME-0009");
        }
        return lista;
    }

}
