package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Herramienta extends Recurso{

    private String estado;

    public Herramienta() {
        estado = "Alta";
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString()
    {
        return "Herramienta";
    }
}
