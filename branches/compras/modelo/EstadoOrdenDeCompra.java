package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public abstract class EstadoOrdenDeCompra extends EstadoAbstracto {
    public abstract boolean esAnulada();

    public abstract boolean esCancelada();

    public abstract boolean esGenerada();

    public abstract boolean esPendiente();

    public abstract boolean esRecibidaParcial();

    public abstract boolean esRecibidaTotal();
}
