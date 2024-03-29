/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;
import vista.cotizacion.CotizacionBeneficios;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionBeneficios implements IGestorCotizacion {
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionBeneficios pantalla;
    private double porcentageGanancia;
    private double montoGanancia;
    private boolean flagGananciaPorcentaje;
    private SubObra subObraActual;

    public GestorCotizacionBeneficios(GestorEditarCotizacion gestorPadre) 
    {
        this.gestorPadre = gestorPadre;
        subObraActual=getSubObraActual();
        montoGanancia=subObraActual.getGananciaMonto();
        porcentageGanancia=subObraActual.getGananciaPorcentaje();
        flagGananciaPorcentaje=subObraActual.isFlagGananciaPorcentaje();
        
    }
    public void reiniciarGestor() 
    {
        subObraActual=getSubObraActual();
        montoGanancia=subObraActual.getGananciaMonto();
        porcentageGanancia=subObraActual.getGananciaPorcentaje();
        flagGananciaPorcentaje=subObraActual.isFlagGananciaPorcentaje();
        
    }

    public void setPantalla(CotizacionBeneficios pantalla) 
    {
        this.pantalla = pantalla;
    }
    
    public double calcularGananciaSubObra(boolean flagGananciaPorcentaj,double ganancia)
    {
        
        
        subObraActual=getSubObraActual();
        flagGananciaPorcentaje=flagGananciaPorcentaj;
       // subObraActual.setFlagGananciaPorcentaje(flagGananciaPorcentaje);
        if(flagGananciaPorcentaje)
        {
            porcentageGanancia=ganancia;            
            subObraActual.setearGananciaPorcentaje(ganancia);
            montoGanancia=subObraActual.getGananciaMonto();
            
        }
        else
        {
            montoGanancia=ganancia;            
            subObraActual.setearGananciaMonto(ganancia);
            porcentageGanancia=subObraActual.getGananciaPorcentaje();
        }
        
        refrescarPantallas();
        return subObraActual.getGananciaMonto();        
    }
    
    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual()
    {
        return this.gestorPadre.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
       gestorPadre.refrescarPantallas();
    }

    /**
     * @return the MontoOPorcentageGanancia
     */
    public double getGananciaMonto() {
      
           return montoGanancia;
       
    }
    public double getGananciaPorcentaje() 
    {
        return porcentageGanancia;       
    }

    /**
     * @return the flagGananciaPorcentaje
     */
    public boolean isFlagGananciaPorcentaje() {
        return flagGananciaPorcentaje;
    }
    
    
}
