/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.*;
import util.NTupla;
import util.RecursosUtil;
import vista.planificacion.PlanificacionMateriales;

/**
 *
 * @author Emmanuel
 */
public class GestorPlanificacionMateriales implements IGestorPlanificacion{

    private GestorEditarTarea gestor;
    private PlanificacionMateriales pantalla;
    
    GestorPlanificacionMateriales(GestorEditarTarea gestor) {
        this.gestor = gestor;
    }

    public PlanificacionXXX getPlanificacion() {
        return this.gestor.getPlanificacion();
    }

    public TareaPlanificacion getTareaActual() {
        return this.gestor.getTareaActual();
    }

    public void refrescarPantallas() {
        this.gestor.refrescarPantallas();
    }

    public void setPantalla(PlanificacionMateriales pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> getMaterialesAsociados() {
        TareaPlanificacion tarea = this.getTareaActual();
        Iterator<PlanificacionXMaterial> itM = tarea.getMateriales().iterator();
        ArrayList<NTupla> ma = new ArrayList<NTupla>();
        while(itM.hasNext())
        {
            PlanificacionXMaterial pxm = itM.next();           
            NTupla nt = new NTupla();
            nt.setId(pxm.hashCode());
            RecursoXProveedor rxp = pxm.getMaterialCotizacion().getMaterial();
            RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
            Material m = (Material)RecursosUtil.getMaterial(re);
            nt.setNombre(m.getNombre());
            Object[] o = new Object[4];
            o[0]= re.getNombre();
            o[1]= pxm.getCantidad();
            o[2]= pxm.getMaterialCotizacion().getPrecioUnitario();
            o[3] = m.getUnidadDeMedida().getAbreviatura();
            nt.setData(o);
            ma.add(nt);
        }
        return ma;
    }

    public boolean quitarMaterial(int id) {
        boolean borrado = false;
        Iterator<PlanificacionXMaterial> it = this.getTareaActual().getMateriales().iterator();
        while(it.hasNext())
        {
            PlanificacionXMaterial pxm = it.next();
            if(pxm.hashCode() == id)
            {
                // Es mi asignacion
                this.getTareaActual().getMateriales().remove(pxm);
                borrado = true;
                break;
            }
        }
        return borrado;
    }
    
    @Deprecated
    public boolean quitarMaterial(int id, boolean eliminarGastos) {
        boolean borrado = false;
        Iterator<PlanificacionXMaterial> it = this.getTareaActual().getMateriales().iterator();
        while(it.hasNext())
        {
            PlanificacionXMaterial pxm = it.next();
            if(pxm.hashCode() == id)
            {
                // Es mi asignacion
                if(eliminarGastos)
                {
                    // Elimino los gastos
                    // Esto es un borrador del algoritmo, no se me ocurre otra cosa
                    int horasBaul = pxm.getMaterialCotizacion().getCantidadDisponible();
                    horasBaul = horasBaul - pxm.getCantidad();
                    pxm.getMaterialCotizacion().setCantidad(horasBaul);
                }
                this.getTareaActual().getMateriales().remove(pxm);
                borrado = true;
                break;
            }
        }
        return borrado;
    }
}