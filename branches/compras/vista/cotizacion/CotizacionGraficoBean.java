/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.cotizacion;

/**
 *
 * @author Iuga
 */
public class CotizacionGraficoBean {
   
    private String nombre;
    private double value;

    public CotizacionGraficoBean(String nombre, double value) {
        this.nombre = nombre;
        this.value = value;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
    
}
