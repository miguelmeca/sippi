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
    private GestorCotizacionManoDeObra gestorManoObra;
    private GestorCotizacionHerramientas gestorHerramientas;
    private GestorCotizacionMateriales gestorMateriales;
    private GestorCotizacionAlquileresCompras gestorAlquileresCompras;
    private GestorCotizacionBeneficios gestorBeneficios;
    private GestorCotizacionAdicionales gestorAdicionales;
    private GestorCotizacionDescripcion gestorDescripcion;    
    
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
                this.subObra = so;
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
        return this.subObra;
    }

    @Override
    public void refrescarPantallas() {
        gestorPadre.refrescarPantallas();
    }
    
}
