package modelo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : DetalleMaterial.java
//  @ Date : 08/10/2010
//  @ Author : 
//
//

public class DetalleMaterial {
    private int id;
    private String descripcion;
    private int cantidad;
    private RecursoXProveedor material;
    public void crear() {

    }

    public void mostrarRecurso() {

    }

    public DetalleMaterial() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public RecursoXProveedor getMaterial() {
        return material;
    }

    public void setMaterial(RecursoXProveedor material) {
        this.material = material;
    }
}