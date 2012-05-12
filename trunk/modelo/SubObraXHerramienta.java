/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class SubObraXHerramienta implements ISubtotal{
    private int id;
    private HerramientaDeEmpresa herramienta;
    private String observaciones;
    private int cantDias;
    private int cantHoras;
    private double costoXHora;

    public SubObraXHerramienta() {
    }

    public int getCantDias() {
        return cantDias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCantDias(int cantDias) {
        this.cantDias = cantDias;
    }

    public int getCantHoras() {
        return cantHoras;
    }

    public void setCantHoras(int cantHoras) {
        this.cantHoras = cantHoras;
    }

    public double getCostoXHora() {
        return costoXHora;
    }

    public void setCostoXHora(double costoXHora) {
        this.costoXHora = costoXHora;
    }

    public HerramientaDeEmpresa getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(HerramientaDeEmpresa herramienta) {
        this.herramienta = herramienta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public double calcularSubtotal() 
    {
        return costoXHora*cantDias*cantHoras;
    }
    
    public int getHorasDisponibles()
    {
        return cantDias*cantHoras;
    }
}
