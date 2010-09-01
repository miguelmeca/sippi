/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.compras;

/**
 *
 * @author Emmanuel
 */
public class pantallaConsultarOC extends pantallaRegistrarRecepcionOrdenCompra{

    public pantallaConsultarOC() {
        super();
        super.ocultarBotonesRecepcion();
        super.borrarColumnaSeleccion();
    }

}
