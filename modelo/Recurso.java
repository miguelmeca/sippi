package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import util.RubroUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public abstract class Recurso {
    
    private int id;
    private String nombre;
    private List<RecursoEspecifico> recursos;
    private UnidadDeMedida unidadDeMedida;

    public Recurso() {
        recursos = new ArrayList<RecursoEspecifico>();
    }

    public boolean esRubro(int idRubro)
    {
         Rubro r = RubroUtil.getRubro(idRubro);
         if(r.getNombreClase().equals(this.toString()))
         {
             return true;
         }
         return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getRecursos() {
        return recursos;
    }

    public List getRecursosEspecificos() {
        return recursos;
    }

    public void setRecursos(List recursos) {
        this.recursos = recursos;
    }

    public UnidadDeMedida getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
}
