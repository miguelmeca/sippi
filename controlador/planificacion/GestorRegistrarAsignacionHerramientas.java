/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import modelo.Etapa;
import modelo.DetalleEtapa;
import modelo.Herramienta;
import modelo.HerramientaDeEmpresa;
import modelo.PedidoObra;
import modelo.Presupuesto;
import modelo.Tarea;
import util.HibernateUtil;
import util.NTupla;
import util.RecursosUtil;
import util.Tupla;
import vista.planificacion.pantallaRegistrarTarea;

/**
 *
 * @author dell
 */
public class GestorRegistrarAsignacionHerramientas {
    private pantallaRegistrarTarea pantalla;
    private int idTarea;

    public GestorRegistrarAsignacionHerramientas(pantallaRegistrarTarea aThis) {
        this.pantalla=aThis;
        this.idTarea=0;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public HashMap<Integer,NTupla> getHerramientasDeEmpresa(int idP){
        HashMap<Integer,NTupla> hDisponibles = this.getHerramientasDeEmpresaDisponibles();
        HashMap<Integer,NTupla> hPresupuesto = this.getHerramientasPresupuesto(idP);
        HashMap<Integer,NTupla> hAUtilizar = this.getHerramientasAUtilizar();

        ArrayList<NTupla> hs = new ArrayList<NTupla>();

        Iterator itHD = hDisponibles.entrySet().iterator();
        while(itHD.hasNext()){
            NTupla ntHD = (NTupla)((Map.Entry)itHD.next()).getValue();
            Iterator itHP = hPresupuesto.entrySet().iterator();
            while(itHP.hasNext()){
                NTupla ntHP = (NTupla)((Map.Entry)itHP.next()).getValue();
                hDisponibles.put(ntHP.getId(), ntHP);
            }
        }
        Iterator itHAU = hAUtilizar.entrySet().iterator();
        while(itHAU.hasNext()){
            NTupla ntHAU = (NTupla)((Map.Entry)itHAU.next()).getValue();
            hDisponibles.remove(ntHAU.getId());
        }
        return hDisponibles;
    }

//    public ArrayList<NTupla> getHerramientasDeEmpresaDisponibles() {
    public HashMap<Integer,NTupla> getHerramientasDeEmpresaDisponibles() {
//        ArrayList<NTupla> herramientas = new ArrayList<NTupla>();
        HashMap<Integer,NTupla> hMap = new HashMap<Integer, NTupla>();
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from HerramientaDeEmpresa").iterate();
            while(it.hasNext()){
                NTupla nt = new NTupla();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)it.next();
                nt.setId(hde.getId());
                Herramienta h = RecursosUtil.getHerramienta(hde.getRecursoEsp());
                nt.setNombre(h.getNombre()+" "+hde.getRecursoEsp().getNombre()+" - NS: "+hde.getNroSerie());
                nt.setData(0);
//                herramientas.add(nt);
                hMap.put(hde.getId(), nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0001");
        }
//        return herramientas;
        return hMap;
    }

//    public ArrayList<NTupla> getHerramientasPresupuesto(int idP){
    public HashMap<Integer,NTupla> getHerramientasPresupuesto(int idP){
        //TODO: Arreglar para que busque sólo en la etapa en la que estamos trabajando!!!
//        ArrayList<NTupla> herramientas = new ArrayList<NTupla>();
        HashMap<Integer,NTupla> hMap = new HashMap<Integer, NTupla>();
        try {
            Presupuesto p = (Presupuesto)HibernateUtil.getSession().load(Presupuesto.class, idP);
            Iterator<Etapa> itP = p.getEtapas().iterator();
            while(itP.hasNext()){
                Etapa e = itP.next();
                Iterator<DetalleEtapa> itE = e.getTareas().iterator();
                while(itE.hasNext()){
                    DetalleEtapa t = itE.next();
                    if(t instanceof Tarea)
                    {
                        Iterator itHP = t.getHerramientas().iterator();
                        while(itHP.hasNext()){
                            NTupla nt = new NTupla();
                            HerramientaDeEmpresa hde = (HerramientaDeEmpresa)itHP.next();
                            nt.setId(hde.getId());
                            nt.setNombre(hde.getRecursoEsp().getNombre()+" - NS: "+hde.getNroSerie()+" ("+t.getDescripcion()+")");
                            nt.setData(t.getId());
    //                        herramientas.add(nt);
                            hMap.put(hde.getId(), nt);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0001");
        }
//        return herramientas;
        return hMap;
    }

//    public ArrayList<NTupla> getHerramientasAUtilizar() {
    public HashMap<Integer,NTupla> getHerramientasAUtilizar() {
//        ArrayList<NTupla> mau = new ArrayList<NTupla>();
        HashMap<Integer,NTupla> hMap = new HashMap<Integer, NTupla>();
        try {
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            Iterator<HerramientaDeEmpresa> it = t.getHerramientas().iterator();
            HerramientaDeEmpresa he = null;
            while(it.hasNext()){
                NTupla nt = new NTupla();
                he = it.next();
                nt.setId(he.getId());
                Herramienta h = RecursosUtil.getHerramienta(he.getRecursoEsp());
                nt.setNombre(h.getNombre()+" "+he.getRecursoEsp().getNombre()+" - NS: "+he.getNroSerie());
                nt.setData(0);
//                mau.add(nt);
                hMap.put(he.getId(), nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0002");
        }
//        return mau;
        return hMap;
    }

    public void agregarHerramienta(int idH) {
        try {
            HibernateUtil.beginTransaction();
            //HerramientaDeEmpresa he = (HerramientaDeEmpresa)HibernateUtil.getSession().load(HerramientaDeEmpresa.class, idH);
            HerramientaDeEmpresa he = (HerramientaDeEmpresa)HibernateUtil.getSession().createQuery("FROM HerramientaDeEmpresa where id=:idH").setParameter("idH", idH).uniqueResult();
            Tarea t = (Tarea)HibernateUtil.getSession().load(Tarea.class, this.idTarea);
            t.agregarHerramienta(he);
            HibernateUtil.getSession().saveOrUpdate(t);
            HibernateUtil.commitTransaction();

        } catch (Exception ex) {
            this.pantalla.MostrarMensaje("AH-0002");
            HibernateUtil.rollbackTransaction();
        }
    }

    public boolean quitarHerramienta(int idH) {
        boolean respuesta=true;
        try {
            HibernateUtil.beginTransaction();
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            HerramientaDeEmpresa he = (HerramientaDeEmpresa) HibernateUtil.getSession().load(HerramientaDeEmpresa.class, idH);
            t.getHerramientas().remove(he);
            HibernateUtil.getSession().saveOrUpdate(t);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            respuesta=false;
            pantalla.MostrarMensaje("AH-0003");
        }
        return respuesta;
    }
}
