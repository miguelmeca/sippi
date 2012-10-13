/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXMaterial;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import util.NTupla;
import vista.planificacion.PlanificacionAlquileresCompras;

/**
 *
 * @author Emmanuel
 */
public class GestorPlanificacionAlquileresCompras implements IGestorPlanificacion{

    private GestorEditarTarea gestor;
    private PlanificacionAlquileresCompras pantalla;
    
    GestorPlanificacionAlquileresCompras(GestorEditarTarea gestor) {
        this.gestor = gestor;
    }

    @Override
    public PlanificacionXXX getPlanificacion() {
        return this.gestor.getPlanificacion();
    }

    @Override
    public TareaPlanificacion getTareaActual() {
        return this.gestor.getTareaActual();
    }

    @Override
    public void refrescarPantallas() {
        this.gestor.refrescarPantallas();
    }

    public void setPantalla(PlanificacionAlquileresCompras pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> getAlquileresComprasAsociadas() {
        TareaPlanificacion tarea = this.getTareaActual();
        Iterator<PlanificacionXAlquilerCompra> itAC = tarea.getAlquilerCompras().iterator();
        ArrayList<NTupla> ma = new ArrayList<NTupla>();
        while(itAC.hasNext())
        {
            PlanificacionXAlquilerCompra pxac = itAC.next();           
            if(pxac.getAlquilerCompraCotizacion() != null && pxac.getAlquilerCompraCotizacion().getTipoAlquilerCompra() != null)
            {
                NTupla nt = new NTupla();
                nt.setId(pxac.hashCode());
                nt.setNombre(pxac.getAlquilerCompraCotizacion().getTipoAlquilerCompra().getNombre());
                Object[] o = new Object[2];
                o[0]= pxac.getCantidad();
                o[1]= pxac.getAlquilerCompraCotizacion().getPrecioUnitario();
                nt.setData(o);
                ma.add(nt);
            }
        }
        return ma;
    }

    public boolean quitarAlquilerCompra(int id) {
        boolean borrado = false;
        Iterator<PlanificacionXAlquilerCompra> it = this.getTareaActual().getAlquilerCompras().iterator();
        while(it.hasNext())
        {
            PlanificacionXAlquilerCompra pxac = it.next();
            if(pxac.hashCode() == id)
            {
                // Es mi asignacion
                this.getTareaActual().getAlquilerCompras().remove(pxac);
                borrado = true;
                break;
            }
        }
        return borrado;
    }
    
    @Deprecated
    public boolean quitarAlquilerCompra(int id, boolean eliminarGastos) {       
        boolean borrado = false;
        Iterator<PlanificacionXAlquilerCompra> it = this.getTareaActual().getAlquilerCompras().iterator();
        while(it.hasNext())
        {
            PlanificacionXAlquilerCompra pxac = it.next();
            if(pxac.hashCode() == id)
            {
                // Es mi asignacion
                if(eliminarGastos)
                {
                    // Elimino los gastos
                    // Esto es un borrador del algoritmo, no se me ocurre otra cosa
                    int horasBaul = pxac.getAlquilerCompraCotizacion().getCantidad();
                    horasBaul = horasBaul - pxac.getCantidad();
                    pxac.getAlquilerCompraCotizacion().setCantidad(horasBaul);
                }
                this.getTareaActual().getAlquilerCompras().remove(pxac);
                borrado = true;
                break;
            }
        }
        return borrado;
    }
}