package controlador.ejecucion;

import vista.ejecucion.VentanaEjecucion;

/**
 *
 * @author Iuga
 */
public class GestorVentanaEjecucion {

    private VentanaEjecucion vista;
    private int idObra;

    public GestorVentanaEjecucion(VentanaEjecucion vista, int idObra) {
        this.vista = vista;
        this.idObra = idObra;
    }
    
    public int getIdObraActual(){
        return idObra;
    }
    
    
}
