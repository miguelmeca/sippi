package modelo;

import java.util.List;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class RecursoEspecifico {

    private int id;
    private String nombre;
    private List<RecursoXProveedor> proveedores;

    public RecursoEspecifico() {
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

    public List<RecursoXProveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<RecursoXProveedor> proveedores) {
        this.proveedores = proveedores;
    }


}
