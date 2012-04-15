/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.*;
import modelo.Cotizacion;
import modelo.PlanificacionXXX;
import modelo.SubObra;
import vista.cotizacion.EditarCotizacion;

/**
 * @author Administrador
 */
public class GestorEditarCotizacionModificada extends GestorEditarCotizacion{

    // DE ACA SACO TODOS LOS DATOS DE LA PLANIFICACION Y DE LA COTIZACION INTERMEDIA
    private PlanificacionXXX planificacion;
    private int hashSubObra = -1;
     
    public GestorEditarCotizacionModificada(GestorExplorarSubObras gestor, PlanificacionXXX plan, int hashSubObra) {
        super(gestor);
        
        this.planificacion = plan;
        this.hashSubObra = hashSubObra;
        for (int i = 0; i < planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = (SubObra)planificacion.getCotizacion().getSubObras().get(i);
            if(so.hashCode()==hashSubObra)
            {
                super.setSubObra(so);
                break;
            }
        }
        super.gestorMateriales = new GestorCotizacionMateriales(this);
        super.gestorHerramientas = new GestorCotizacionHerramientas(this);
        super.gestorAlquileresCompras = new GestorCotizacionAlquileresCompras(this);
        super.gestorAdicionales = new GestorCotizacionAdicionales(this);
    }

    @Override
    public double calcularSubtotalSubObra() {
        return -1;
    }

    @Override
    public Cotizacion getCotizacion() {
        // Tengo q retornar la cotizacion intermedia
        return this.planificacion.getCotizacion().getCotizacionOriginal();
    }
/*
    @Override
    public GestorCotizacionAdicionales getGestorAdicionales() {
        return super.gestorAdicionales;
    }

    @Override
    public GestorCotizacionAlquileresCompras getGestorAlquileresCompras() {
        return super.gestorAlquileresCompras;
    }

    @Override
    public GestorCotizacionBeneficios getGestorBeneficios() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionDescripcion getGestorDescripcion() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionHerramientas getGestorHerramientas() {
        return super.gestorHerramientas;
    }

    @Override
    public GestorCotizacionManoDeObra getGestorManoObra() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionMateriales getGestorMateriales() {
        return super.gestorMateriales;
    }*/

    @Override
    public SubObra getSubObraActual() {
        return super.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
        super.pantalla.actualizar();
    }

    @Override
    public void seleccionarSubObra(int idSubObra)
    {
        for (int i = 0; i < planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = (SubObra)planificacion.getCotizacion().getSubObras().get(i);
            if(so.getId()==idSubObra)
            {
                super.setSubObra(so);
                break;
            }
        }
    }

    
}