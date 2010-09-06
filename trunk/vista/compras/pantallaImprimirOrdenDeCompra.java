/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.compras;

/**
 *
 * @author Emmanuel
 */
public class pantallaImprimirOrdenDeCompra extends pantallaConsultarOC{
    public pantallaImprimirOrdenDeCompra() {
        super();
        super.ocultarBotonesRecepcion();
        super.borrarColumnaSeleccion();
        super.setTitle("Imprimir Orden De Compra Generada");
    }
}
