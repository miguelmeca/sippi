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
public class GestorEditarCotizacionIntermedia extends GestorEditarCotizacion{

    // DE ACA SACO TODOS LOS DATOS DE LA PLANIFICACION Y DE LA COTIZACION INTERMEDIA
    private PlanificacionXXX planificacion;
    private int idSubObra = -1;
    
    public GestorEditarCotizacionIntermedia(GestorExplorarSubObras gestor, PlanificacionXXX plan, int idSubObra) {
        super(gestor);
        
        this.planificacion = plan;
        this.idSubObra = idSubObra;
        
        super.gestorMateriales = new GestorMaterialesCotizacionIntermedia();
        super.gestorHerramientas = new GestorHerramientasCotizacionIntermedia(this);
        super.gestorAlquileresCompras = new GestorAlquileresComprasCotizacionIntermedia(this);
        super.gestorAdicionales = new GestorAdicionalesCotizacionIntermedia(this);
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
    }

    @Override
    public SubObra getSubObraActual() {
        //return this.planificacion.getCotizacion().buscarSubObra(idSubObra);
        return null;
    }

    @Override
    public void refrescarPantallas() {
        super.refrescarPantallas();
    }

    @Override
    public void seleccionarSubObra(int idSubObra) {
        super.seleccionarSubObra(idSubObra);
    }

    @Override
    public void setPantalla(EditarCotizacion pantalla) {
        super.setPantalla(pantalla);
    }
}