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
            HerramientaDeEmpresa he = (HerramientaDeEmpresa)HibernateUtil.getSession().load(HerramientaDeEmpresa.class, idH);
            Tarea t = (Tarea)HibernateUtil.getSession().load(Tarea.class, this.idTarea);
            t.getHerramientas().add(he);
            HibernateUtil.getSession().saveOrUpdate(t);
            //pantalla.actualizar(dm.getId(), "...", true);

        } catch (Exception ex) {
            this.pantalla.MostrarMensaje("AM-0006");
            this.pantalla.actualizar(-1, "NO IMPLEMENTADO AUN", false);
        }
    }
}
