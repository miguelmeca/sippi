/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionHerramientas;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.Cotizacion;
import modelo.SubObra;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionHerramientas;

/**
 *
 * @author Administrador
 */
public class GestorHerramientasCotizacionIntermedia extends GestorCotizacionHerramientas {

    public GestorHerramientasCotizacionIntermedia(GestorEditarCotizacion gestorPadre) {
        super(gestorPadre);
    }

    @Override
    public void AgregarHerramienta(Tupla tph, int cantDias, int cantHoras, double costo) {
        // Va a agregar una herramienta ala cotizacion intermedia
        super.AgregarHerramienta(tph, cantDias, cantHoras, costo);
    }

    @Override
    public Cotizacion getCotizacion() {
        // Va a devolver la cotizacion Original (No tenemos una cotizacion Intermedia/Modificada )
        return super.gestorPadre.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual() {
        // Retorna la SubObra intermedia Actual
        return super.getSubObraActual();
    }

    @Override
    public void quitarHerramienta(NTupla ntp) {
        // Lo mismo, creo
        super.quitarHerramienta(ntp);
    }

    @Override
    public void refrescarPantallas() {
        // Lo mismo
        super.refrescarPantallas();
    }

    @Override
    public void setPantalla(CotizacionHerramientas pantalla) {
        // Lo mismo
        super.setPantalla(pantalla);
    }
    
    
    
}
