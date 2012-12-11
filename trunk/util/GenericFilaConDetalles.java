/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class GenericFilaConDetalles<T,U,V extends HashMap> {
    
    private T entidad;
    private U dato;
    private V detalles;

    public GenericFilaConDetalles() {
        detalles = (V) new HashMap<Object,Object>();
    }
    
    public T getEntidad() {
        return entidad;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }

    public U getDato() {
        return dato;
    }

    public void setDato(U dato) {
        this.dato = dato;
    }

    public V getDetalles() {
        return detalles;
    }

    public void setDetalles(V detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(Object key,Object obj)
    {
        this.detalles.put(key, obj);
    }
    
    public Object quitarDetalle(Object key){
        return this.detalles.remove(key);
    }
    
    public Object getDetalle(Object key)
    {
        return this.detalles.get(key);
    }
}
