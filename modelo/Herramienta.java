package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Herramienta extends Recurso{

    private EstadoHerramienta estado;
    private String hib_flag_estado;

    public EstadoHerramienta getEstado() {
        return estado;
    }

    public void setEstado(EstadoHerramienta estado) {
        this.estado = estado;
    }

    public String getHib_flag_estado() {
        return hib_flag_estado;
    }

    public void setHib_flag_estado(String hib_flag_estado) {
        this.hib_flag_estado = hib_flag_estado;
    }

    @Override
    public String toString()
    {
        return "Herramienta";
    }


  
}
