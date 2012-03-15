/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;


import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import util.HibernateUtil;
import vista.planificacion.PantallaEditarTarea;

/**
 *
 * @author Emmanuel
 */
public class GestorEditarTarea implements IGestorPlanificacion{
    private PantallaEditarTarea pantalla;

    private TareaPlanificacion tarea;

    // GESTOR PADRE
    private GestorEditarPlanificacion gestorPadre;

    // LISTA DE GESTORES
    private GestorPlanificacionDatosGenerales gestorDatosGenerales;
    private GestorPlanificacionSubTareas gestorSubTareas;
    private GestorPlanificacionAsigEmpleados gestorAsigEmpleados;
    private GestorPlanificacionHerramientas gestorHerramientas;
    private GestorPlanificacionMateriales gestorMateriales;
    private GestorPlanificacionAlquileresCompras gestorAlquileresCompras;
    

    public GestorEditarTarea(GestorEditarPlanificacion gestor)
    {
        this.gestorPadre = gestor;
    }

    public void setPantalla(PantallaEditarTarea pantalla) {
        this.pantalla = pantalla;
    }

    public void seleccionarTarea(int idTarea)
    {
        try {
            //        for (int i = 0; i < getPlanificacion().getTareas().size(); i++)
            //        {
            //            TareaPlanificacion tp = (TareaPlanificacion)getPlanificacion().getTareas().get(i);
            //            if(tp.getId()==idTarea)
            //            {
            //                this.tarea = tp;
            //                break;
            //            }
            //        }
            this.tarea = (TareaPlanificacion) HibernateUtil.getSession().load(TareaPlanificacion.class,idTarea);
        } catch (Exception ex) {
            Logger.getLogger(GestorEditarTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GestorPlanificacionDatosGenerales getGestorDatosGenerales()
    {
        if(this.gestorDatosGenerales==null)
        {
            this.gestorDatosGenerales = new GestorPlanificacionDatosGenerales(this);
        }
        return gestorDatosGenerales;
    }

    public GestorPlanificacionSubTareas getGestorSubTareas()
    {
        if(this.gestorSubTareas==null)
        {
            this.gestorSubTareas = new GestorPlanificacionSubTareas(this);
        }
        return gestorSubTareas;
    }

    public GestorPlanificacionAsigEmpleados getGestorAsigEmpleados()
    {
        if(this.gestorAsigEmpleados==null)
        {
            this.gestorAsigEmpleados = new GestorPlanificacionAsigEmpleados(this);
        }
        return gestorAsigEmpleados;
    }

    public GestorPlanificacionHerramientas getGestorHerramientas()
    {
        if(this.gestorHerramientas==null)
        {
            this.gestorHerramientas = new GestorPlanificacionHerramientas(this);
        }
        return gestorHerramientas;
    }

    public GestorPlanificacionMateriales getGestorMateriales()
    {
        if(this.gestorMateriales==null)
        {
            this.gestorMateriales = new GestorPlanificacionMateriales(this);
        }
        return gestorMateriales;
    }

    public GestorPlanificacionAlquileresCompras getGestorAlquileresCompras()
    {
        if(this.gestorAlquileresCompras==null)
        {
            this.gestorAlquileresCompras = new GestorPlanificacionAlquileresCompras(this);
        }
        return gestorAlquileresCompras;
    }

    @Override
    public void refrescarPantallas() {
        gestorPadre.refrescarPantallas();
        pantalla.actualizar();
    }

    @Override
    public PlanificacionXXX getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    @Override
    public TareaPlanificacion getTareaActual() {
        return this.tarea;
    }
}
