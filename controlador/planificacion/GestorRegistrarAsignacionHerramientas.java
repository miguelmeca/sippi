/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.Herramienta;
import modelo.HerramientaDeEmpresa;
import modelo.Tarea;
import util.HibernateUtil;
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

    public ArrayList<Tupla> getHerramientasDeEmpresaDisponibles() {
        ArrayList<Tupla> herramientas = new ArrayList<Tupla>();
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from HerramientaDeEmpresa").iterate();
            while(it.hasNext()){
                Tupla t = new Tupla();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)it.next();
                t.setId(hde.getId());
                t.setNombre(hde.getRecursoEsp().getNombre()+" - NS: "+hde.getNroSerie());
                herramientas.add(t);
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

    public ArrayList<Tupla> getHerramientasAUtilizar() {
        ArrayList<Tupla> mau = new ArrayList<Tupla>();
        try {
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            Iterator<HerramientaDeEmpresa> it = t.getHerramientas().iterator();
            HerramientaDeEmpresa he = null;
            Tupla nt = new Tupla();
            while(it.hasNext()){
                he = it.next();
                nt.setId(he.getId());
                nt.setNombre(he.getRecursoEsp().getNombre()+" - NS: "+he.getNroSerie());
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
