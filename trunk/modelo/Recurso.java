package modelo;

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
    private String descipcion;
    private RecursoEspecifico recursos;
    private UnidadDeMedida unidadDeMedida;

    public Recurso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RecursoEspecifico getRecursos() {
        return recursos;
    }

    public void setRecursos(RecursoEspecifico recurso) {
        this.recursos = recurso;
    }

    public UnidadDeMedida getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }


 
}
