/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionAlquileresCompras;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.Cotizacion;
import modelo.SubObra;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionAlquileresCompras;

/**
 *
 * @author Administrador
 */
public class GestorAlquileresComprasCotizacionIntermedia extends GestorCotizacionAlquileresCompras {

    public GestorAlquileresComprasCotizacionIntermedia(GestorEditarCotizacion gestorPadre) {
        super(gestorPadre);
    }

    @Override
    public void AgregarCompraAlquiler(Tupla tipo, String descripcion, int cantidad, double precio) {
        super.AgregarCompraAlquiler(tipo, descripcion, cantidad, precio);
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
    public void setPantalla(CotizacionAlquileresCompras pantalla) {
        super.setPantalla(pantalla);
    }
    
    
    
}
