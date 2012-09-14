package modelo;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Herramienta extends Recurso{

    public Herramienta() {
        
    }

    @Override
    public String getEstado() {
        return super.getEstado();
    }

    @Override
    public void setEstado(String estado) {
        super.setEstado(estado);
    }
    
    @Override
    public String toString()
    {
        return "Herramienta";
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public String mostrarUnidadDeMedida() {
        return super.mostrarUnidadDeMedida();
    }
}
