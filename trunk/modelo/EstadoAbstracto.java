package modelo;

/**
 * Descripción: Clase que define la interfaz de los estados del sistema
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public abstract class EstadoAbstracto
{
    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
