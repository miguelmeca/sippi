/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.Etapa;
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

    public ArrayList<NTupla> getHerramientasDeEmpresa(int idP){
        ArrayList<NTupla> hDisponibles = this.getHerramientasDeEmpresaDisponibles();
        ArrayList<NTupla> hPresupuesto = this.getHerramientasPresupuesto(idP);

        Iterator<NTupla> itHD = hDisponibles.iterator();
        while(itHD.hasNext()){
            NTupla ntHD = itHD.next();
            Iterator<NTupla> itHP = hPresupuesto.iterator();
            while(itHP.hasNext()){
                NTupla ntHP = itHP.next();
                if(ntHD.getId()==ntHP.getId()){
                   hDisponibles.remove(ntHP);
                }
            }
        }
        return hDisponibles;
    }

    public ArrayList<NTupla> getHerramientasDeEmpresaDisponibles() {
        ArrayList<NTupla> herramientas = new ArrayList<NTupla>();
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from HerramientaDeEmpresa").iterate();
            while(it.hasNext()){
                NTupla nt = new NTupla();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)it.next();
                nt.setId(hde.getId());
                Herramienta h = RecursosUtil.getHerramienta(hde.getRecursoEsp());
                nt.setNombre(h.getNombre()+" "+hde.getRecursoEsp().getNombre()+" - NS: "+hde.getNroSerie());
                nt.setData(0);
                herramientas.add(nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0001");
        }
        return herramientas;
    }

    public ArrayList<NTupla> getHerramientasPresupuesto(int idP){
        ArrayList<NTupla> herramientas = new ArrayList<NTupla>();
        try {
            Presupuesto p = (Presupuesto)HibernateUtil.getSession().load(Presupuesto.class, idP);
            Iterator<Etapa> itP = p.getEtapas().iterator();
            while(itP.hasNext()){
                Etapa e = itP.next();
                Iterator<Tarea> itE = e.getTareas().iterator();
                while(itE.hasNext()){
                    Tarea t = itE.next();
                    Iterator itHP = t.getHerramientas().iterator();
                    while(itHP.hasNext()){
                        NTupla nt = new NTupla();
                        HerramientaDeEmpresa hde = (HerramientaDeEmpresa)itHP.next();
                        nt.setId(hde.getId());
                        nt.setNombre(hde.getRecursoEsp().getNombre()+" - NS: "+hde.getNroSerie()+" ("+t.getDescripcion()+")");
                        nt.setData(t.getId());
                        herramientas.add(nt);
                    }
                }
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0001");
        }
        return herramientas;
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

    public ArrayList<NTupla> getHerramientasAUtilizar() {
        ArrayList<NTupla> mau = new ArrayList<NTupla>();
        try {
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            Iterator<HerramientaDeEmpresa> it = t.getHerramientas().iterator();
            HerramientaDeEmpresa he = null;
            NTupla nt = new NTupla();
            while(it.hasNext()){
                he = it.next();
                nt.setId(he.getId());
                Herramienta h = RecursosUtil.getHerramienta(he.getRecursoEsp());
                nt.setNombre(h.getNombre()+" "+he.getRecursoEsp().getNombre()+" - NS: "+he.getNroSerie());
                mau.add(nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AH-0002");
        }
        return mau;
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
