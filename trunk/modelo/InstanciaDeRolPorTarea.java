/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Fran
 */
public class InstanciaDeRolPorTarea
{
    private int id;
    private RolEmpleado rol;
    private double hsNormales;
    private double hs50;
    private double hs100;

    public InstanciaDeRolPorTarea()
    {}

    public InstanciaDeRolPorTarea(RolEmpleado rol, double hsNormales, double hs50,double hs100 )
    {
        this.rol=rol;
        this.hsNormales=hsNormales;
        this.hs50=hs50;
        this.hs100=hs100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRol(RolEmpleado rol)
    {
        this.rol=rol;
    }
    public RolEmpleado getRol()
    {
        return rol;
    }

     public void setHsNormales(double hs)
    {
        this.hsNormales=hs;
    }
    public double getHsNormales()
    {
        return hsNormales;
    }

     public void setHs50(double hs)
    {
        this.hs50=hs;
    }
    public double getHs50()
    {
        return hs50;
    }

     public void setHs100(double hs)
    {
        this.hs100=hs;
    }
    public double getHs100()
    {
        return hs100;
    }
    public double getSubtotal(double  multiplicadorHora50,double multiplicadorHora100)
    {
        double montoHsNormal=hsNormales*rol.getEspecialidad().getPrecioHoraNormal();
        double montoHs50=hs50*rol.getEspecialidad().getPrecioHoraNormal()*multiplicadorHora50;
        double montoHs100=hs100*rol.getEspecialidad().getPrecioHoraNormal()*multiplicadorHora100;

        return (montoHsNormal+montoHs50+montoHs100);
    }

}
