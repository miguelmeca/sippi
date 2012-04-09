/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;
import vista.cotizacion.EditarCotizacion;

/**
 *
 * @author Iuga
 */
public class GestorEditarCotizacion implements IGestorCotizacion{
    
    private EditarCotizacion pantalla;

    private SubObra subObra;
    
    // GESTOR PADRE
    private GestorExplorarSubObras gestorPadre;
    
    // LISTA DE GESTORES
    protected GestorCotizacionManoDeObra gestorManoObra;
    protected GestorCotizacionHerramientas gestorHerramientas;
    protected GestorCotizacionMateriales gestorMateriales;
    protected GestorCotizacionAlquileresCompras gestorAlquileresCompras;
    protected GestorCotizacionBeneficios gestorBeneficios;
    protected GestorCotizacionAdicionales gestorAdicionales;
    protected GestorCotizacionDescripcion gestorDescripcion;    
    
    public GestorEditarCotizacion(GestorExplorarSubObras gestor) 
    {
        this.gestorPadre = gestor;
    }
    
    public void seleccionarSubObra(int idSubObra)
    {
        for (int i = 0; i < getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = (SubObra)getCotizacion().getSubObras().get(i);
            if(so.hashCode()==idSubObra)
            {
                this.setSubObra(so);
                break;
            }
        }
    }
    

    public void setPantalla(EditarCotizacion pantalla) {
        this.pantalla = pantalla;
    }
    
    public GestorCotizacionAdicionales getGestorAdicionales() 
    {
        if(this.gestorAdicionales==null)
        {
            this.gestorAdicionales = new GestorCotizacionAdicionales(this);
        }
        return gestorAdicionales;
    }

    public GestorCotizacionAlquileresCompras getGestorAlquileresCompras() 
    {
        if(this.gestorAlquileresCompras==null)
        {
            this.gestorAlquileresCompras = new GestorCotizacionAlquileresCompras(this);
        }
        return gestorAlquileresCompras;
    }

    public GestorCotizacionBeneficios getGestorBeneficios() 
    {
        if(this.gestorBeneficios==null)
        {
            this.gestorBeneficios = new GestorCotizacionBeneficios(this);
        }
        return gestorBeneficios;
    }

    public GestorCotizacionDescripcion getGestorDescripcion() 
    {
        if(this.gestorDescripcion==null)
        {
            this.gestorDescripcion = new GestorCotizacionDescripcion(this);
        }
        return gestorDescripcion;
    }

    public GestorCotizacionHerramientas getGestorHerramientas() 
    {
        if(this.gestorHerramientas==null)
        {
            this.gestorHerramientas = new GestorCotizacionHerramientas(this);
        }
        return gestorHerramientas;
    }

    public GestorCotizacionManoDeObra getGestorManoObra() 
    {
        if(this.gestorManoObra==null)
        {
            this.gestorManoObra = new GestorCotizacionManoDeObra(this);
        }
        return gestorManoObra;
    }

    public GestorCotizacionMateriales getGestorMateriales() 
    {
        if(this.gestorMateriales==null)
        {
            this.gestorMateriales = new GestorCotizacionMateriales(this);
        }
        return gestorMateriales;
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual() {
        return this.getSubObra();
    }

    @Override
    public void refrescarPantallas() {
        // Paso el mensaje
        gestorPadre.refrescarPantallas();
        pantalla.actualizar();
        // Me actualizo (SubTotal)
        //....
    }
    public double calcularSubtotalSubObra()
    {
        return getSubObra().calcularSubtotal();
    }

    
    
    /**
     * @return the subObra
     */
    public SubObra getSubObra() {
        return subObra;
    }

    /**
     * @param subObra the subObra to set
     */
    public void setSubObra(SubObra subObra) {
        this.subObra = subObra;
    }
    
}
