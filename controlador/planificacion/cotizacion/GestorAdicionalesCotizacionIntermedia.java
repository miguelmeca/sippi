/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionAdicionales;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.Cotizacion;
import modelo.SubObra;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionAdicionales;

/**
 * @author Administrador
 */
public class GestorAdicionalesCotizacionIntermedia extends GestorCotizacionAdicionales {

    public GestorAdicionalesCotizacionIntermedia(GestorEditarCotizacion gestorPadre) {
        super(gestorPadre);
    }

    @Override
    public void AgregarAdicional(Tupla tipo, String descripcion, int cantidadOperarios, int cantidadDias, double precio) {
        super.AgregarAdicional(tipo, descripcion, cantidadOperarios, cantidadDias, precio);
    }

    @Override
    public Cotizacion getCotizacion() {
        return super.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual() {
        return super.getSubObraActual();
    }

    @Override
    public void initVentana() {
        super.initVentana();
    }

    @Override
    public void quitarAlquilerCompra(NTupla ntp) {
        super.quitarAlquilerCompra(ntp);
    }

    @Override
    public void refrescarPantallas() {
        super.refrescarPantallas();
    }

    @Override
    public void setPantalla(CotizacionAdicionales pantalla) {
        super.setPantalla(pantalla);
    }
    
    
    
}
