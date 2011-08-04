/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class SubObra {
    private int id;
    private String nombre;
    private double gananciaMonto;
    private int gananciaPorcentaje;
    private String descripcion;
    private List<SubObraXAdicional> adicionales;
    private List<SubObraXAlquilerCompra> alquileresCompras;
    private List<SubObraXTarea> tareas;
    private List<SubObraXHerramienta> herramientas;
    private List<SubObraXMaterial> materiales;

    public SubObra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SubObraXAdicional> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<SubObraXAdicional> adicionales) {
        this.adicionales = adicionales;
    }

    public List<SubObraXAlquilerCompra> getAlquileresCompras() {
        return alquileresCompras;
    }

    public void setAlquileresCompras(List<SubObraXAlquilerCompra> alquileresCompras) {
        this.alquileresCompras = alquileresCompras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getGananciaMonto() {
        return gananciaMonto;
    }

    public void setGananciaMonto(double gananciaMonto) {
        this.gananciaMonto = gananciaMonto;
    }

    public int getGananciaPorcentaje() {
        return gananciaPorcentaje;
    }

    public void setGananciaPorcentaje(int gananciaPorcentaje) {
        this.gananciaPorcentaje = gananciaPorcentaje;
    }

    public List<SubObraXHerramienta> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<SubObraXHerramienta> herramientas) {
        this.herramientas = herramientas;
    }

    public List<SubObraXMaterial> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<SubObraXMaterial> materiales) {
        this.materiales = materiales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<SubObraXTarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<SubObraXTarea> tareas) {
        this.tareas = tareas;
    }
}
