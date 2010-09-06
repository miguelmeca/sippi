/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class EstadoOrdenDeCompraRecibidaTotal extends EstadoOrdenDeCompra{

    public EstadoOrdenDeCompraRecibidaTotal() {
        super();
        this.setNombre("Recibida Total");
    }

    @Override
    public boolean esAnulada() {
        return false;
    }

    @Override
    public boolean esCancelada() {
        return false;
    }

    @Override
    public boolean esGenerada() {
        return false;
    }

    @Override
    public boolean esPendiente() {
        return false;
    }

    @Override
    public boolean esRecibidaParcial() {
        return false;
    }

    @Override
    public boolean esRecibidaTotal() {
        return true;
    }
}
