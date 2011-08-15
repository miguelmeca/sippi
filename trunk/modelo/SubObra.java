/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class SubObra implements ISubtotal
{
    
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
        adicionales = new ArrayList<SubObraXAdicional>();
        alquileresCompras = new ArrayList<SubObraXAlquilerCompra>();
        tareas = new ArrayList<SubObraXTarea>();
        herramientas = new ArrayList<SubObraXHerramienta>();
        materiales = new ArrayList<SubObraXMaterial>();
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

    public void addAdicional(SubObraXAdicional soxa){
        this.adicionales.add(soxa);
    }
    public void addAlquilerCompra(SubObraXAlquilerCompra soxac){
        this.alquileresCompras.add(soxac);
    }
    public void addTarea(SubObraXTarea soxt){
        this.tareas.add(soxt);
    }
    public void addHerramienta(SubObraXHerramienta soxh){
        this.herramientas.add(soxh);
    }
    public void addMaterial(SubObraXMaterial soxm){
        this.materiales.add(soxm);
    }

    @Override
    public double calcularSubtotal() 
    {
        double monto = 0;
        // 1-Material 
        // 2-Tareas
        // 3-Herramientas
        // 4-Adicionales
        // 5-Compras
                // Materiales
                for (int j = 0; j < getMateriales().size(); j++) 
                {
                    monto += getMateriales().get(j).calcularSubtotal();
                }
                // Tareas
                for (int j = 0; j < getTareas().size(); j++) 
                {
                    monto += getTareas().get(j).calcularSubtotal();
                }
                // Herramientas
                for (int j = 0; j < getHerramientas().size(); j++) 
                {
                    monto += getHerramientas().get(j).calcularSubtotal();
                }
                // Adicionales
                for (int j = 0; j < getAdicionales().size(); j++) 
                {
                    monto += getAdicionales().get(j).calcularSubtotal();
                } 
                // Compras
                for (int j = 0; j < getAlquileresCompras().size(); j++) 
                {
                    monto += getAlquileresCompras().get(j).calcularSubtotal();
                }    
        return monto;
    }
}
