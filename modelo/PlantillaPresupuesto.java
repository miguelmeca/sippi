package modelo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : PlantillaPresupuesto.java
//  @ Date : 08/10/2010
//  @ Author : 
//
//

public class PlantillaPresupuesto extends PresupuestoBase {
    private int id;
    private String nombre;
    private String descripcion;
    // private Usuario esDeUsuario; // NO SE QUE TIPO DE DATOS ES ESTE

    public PlantillaPresupuesto() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
