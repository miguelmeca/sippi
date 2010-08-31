package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Indumentaria extends RecursoEspecifico{

    private Talle talle;
    private EstadoIndumentaria estado;
    private String hib_flag_estado;

    public Talle getTalle() {
        return talle;
    }

    public void setTalle(Talle talle) {
        this.talle = talle;
    }

    public EstadoIndumentaria getEstado() {
        return estado;
    }

    public void setEstado(EstadoIndumentaria estado) {
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
        return "Indumentaria";
    }




}
