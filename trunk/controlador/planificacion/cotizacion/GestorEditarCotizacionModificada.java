/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import org.hibernate.Session;
import util.HibernateUtil;
import vista.cotizacion.EditarCotizacion;

/**
 * @author Administrador
 */
public class GestorEditarCotizacionModificada extends GestorEditarCotizacion{

    // DE ACA SACO TODOS LOS DATOS DE LA PLANIFICACION Y DE LA COTIZACION INTERMEDIA
    private PlanificacionXXX planificacion;
    private int hashSubObra = -1;
     
    public GestorEditarCotizacionModificada(GestorExplorarSubObras gestor, PlanificacionXXX plan, int hashSubObra) {
        super(gestor);
        
        this.planificacion = plan;
        this.hashSubObra = hashSubObra;
        for (int i = 0; i < planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = (SubObra)planificacion.getCotizacion().getSubObras().get(i);
            if(so.hashCode()==hashSubObra)
            {
                super.setSubObra(so);
                break;
            }
        }
        super.gestorMateriales = new GestorCotizacionMateriales(this);
        super.gestorHerramientas = new GestorCotizacionHerramientas(this);
        super.gestorAlquileresCompras = new GestorCotizacionAlquileresCompras(this);
        super.gestorAdicionales = new GestorCotizacionAdicionales(this);
    }

    @Override
    public double calcularSubtotalSubObra() {
        return -1;
    }

    @Override
    public Cotizacion getCotizacion() {
        // Tengo q retornar la cotizacion intermedia
        return this.planificacion.getCotizacion().getCotizacionOriginal();
    }
/*
    @Override
    public GestorCotizacionAdicionales getGestorAdicionales() {
        return super.gestorAdicionales;
    }

    @Override
    public GestorCotizacionAlquileresCompras getGestorAlquileresCompras() {
        return super.gestorAlquileresCompras;
    }

    @Override
    public GestorCotizacionBeneficios getGestorBeneficios() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionDescripcion getGestorDescripcion() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionHerramientas getGestorHerramientas() {
        return super.gestorHerramientas;
    }

    @Override
    public GestorCotizacionManoDeObra getGestorManoObra() {
        // No deberia pasar
        return null;
    }

    @Override
    public GestorCotizacionMateriales getGestorMateriales() {
        return super.gestorMateriales;
    }*/

    @Override
    public SubObra getSubObraActual() {
        return super.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
        super.pantalla.actualizar();
    }

    @Override
    public void seleccionarSubObra(int idSubObra)
    {
        for (int i = 0; i < planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = (SubObra)planificacion.getCotizacion().getSubObras().get(i);
            if(so.getId()==idSubObra)
            {
                super.setSubObra(so);
                break;
            }
        }
    }

     public static void guardarCotizacion(CotizacionModificada cot, Session sesion) throws Exception
    {
         List<SubObra> listaOriginal = (List)cot.getSubObras();
         for (SubObra so: listaOriginal) 
         {
             //Guardo SubObrasXTareas
            List<SubObraXTarea> lisaTareas=so.getTareas();
            for (SubObraXTarea soxt: lisaTareas)
            {
              List<DetalleSubObraXTarea> dsoxt = soxt.getDetalles();
              for(DetalleSubObraXTarea detalle: dsoxt)
              {
                  sesion.saveOrUpdate(detalle);
              }
              sesion.saveOrUpdate(soxt);
            }
            //Guardo SubObrasXMaterial
            List<SubObraXMaterial> lisaMateriales=so.getMateriales();
            for (SubObraXMaterial soxm: lisaMateriales)
            {
               sesion.saveOrUpdate(soxm);
            }
            //Guardo SubObrasXAdicional
             List<SubObraXAdicional> lisaAdicionales=so.getAdicionales();
            for (SubObraXAdicional soxa: lisaAdicionales) 
            {
               sesion.saveOrUpdate(soxa);
            }
            //Guardo SubObrasXAlquilerCompra
            List<SubObraXAlquilerCompra> lisaAlquilerCompra=so.getAlquileresCompras();
            for (SubObraXAlquilerCompra soxac: lisaAlquilerCompra) 
            {
               sesion.saveOrUpdate(soxac);
            }
            //Guardo SubObrasXHerramienta
            List<SubObraXHerramienta> lisaHerramientas=so.getHerramientas();
            for (SubObraXHerramienta soxh: lisaHerramientas) 
            {
                sesion.saveOrUpdate(soxh);
            }
            
            sesion.saveOrUpdate(so);             
         }
         sesion.saveOrUpdate(cot);
    }

    public PlanificacionXXX getPlanificacion() {
        return planificacion;
    }
     
     
}