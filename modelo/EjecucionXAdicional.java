package modelo;

/**
 *
 * @author Iuga
 */
public class EjecucionXAdicional {
 
    private int id;
    private SubObraXAdicionalModif adicionalPlanificado;
    private int cantidad;
    private double precioUnitario;

    public EjecucionXAdicional() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubObraXAdicionalModif getAdicionalPlanificado() {
        return adicionalPlanificado;
    }

    public void setAdicionalPlanificado(SubObraXAdicionalModif adicional) {
        this.adicionalPlanificado = adicional;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precioUnitario
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public double calcularSubtotal() {
        return precioUnitario*cantidad;
    }
    
}
