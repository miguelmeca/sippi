/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.DetalleSubObraXTarea;
import modelo.PedidoObra;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXHerramienta;
import modelo.SubObraXMaterial;
import modelo.SubObraXTarea;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;

import vista.interfaces.ICallBack_v2;

/**
 *
 * @author Iuga
 */
public class GestorRegistrarCotizacion {
    
    private ICallBack_v2 pantalla;
    private Session sesion;
    private PedidoObra obra;
    private Cotizacion cot;
    private Cotizacion cotOriginal;
    private PedidoObra obraOriginal;
    private Date fechaInicioObra;
    private Date fechaFinObra;
    int diasDiferencia;
    boolean fechasFueraDeRango;
    

    public GestorRegistrarCotizacion(ICallBack_v2 pantalla, int po_id) 
    {
        fechasFueraDeRango=false;
        this.pantalla = pantalla;
        try 
        {    
            HibernateUtil.beginTransaction();
            this.sesion = HibernateUtil.getSession();  
            //sesion.clear();
            obra=(PedidoObra)sesion.load(PedidoObra.class, po_id);
            HibernateUtil.commitTransaction();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } 
    }
    
    
    public int crearCotizacionNueva()
    {
        cot=new Cotizacion();     
        cot.setDescripcion("");        
        if(obra.getCotizaciones().isEmpty())
        {
            cot.setNroCotizacion("");
            cot.setNroRevision(0);
        }
        else
        {
             cot.setNroCotizacion(obra.getCotizaciones().get(0).getNroCotizacion());
             List<Cotizacion> listCotX= obra.getCotizaciones();
             int maxNroRevision=0;
             for (Cotizacion cotX : listCotX) 
             {
                if(cotX.getNroRevision()>maxNroRevision) 
                    maxNroRevision=cotX.getNroRevision();                
             }         
             cot.setNroRevision(maxNroRevision+1);
        }
        
        obra.addCotizaciones(cot);
        
        
        try 
        {    
            HibernateUtil.beginTransaction();
            sesion.saveOrUpdate(cot);
            sesion.saveOrUpdate(obra);
            HibernateUtil.commitTransaction();
            return cot.getId();
        } 
        catch (Exception ex) 
        {
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            return -1;
        }
        
    }
    
    public int crearCotizacionAPartirExistente(int id_cot)
    {
        try
        {
            HibernateUtil.beginTransaction();
            cotOriginal = (Cotizacion) sesion.load(Cotizacion.class,id_cot);
            HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        
        cot=copiarCotizacion(cotOriginal);        
        
        //Si se trata de una recotizacion, acÃ¡ siempre va entrar por el camino del else.
        if(obra.getCotizaciones().isEmpty())
        {
            cot.setNroCotizacion("");
            cot.setNroRevision(0);
        }
        else
        {
             cot.setNroCotizacion(obra.getCotizaciones().get(0).getNroCotizacion());
             List<Cotizacion> listCotX= obra.getCotizaciones();
             /*int maxNroRevision=(Integer)sesion.createQuery("select max(cot.nroRevision) from Cotizacion cot where cot.nroCotizacion=:nroCot").setParameter("nroCot", cotOriginal.getNroCotizacion()).uniqueResult();
                cot.setNroRevision(maxNroRevision+1);*/
             int maxNroRevision=0;
             for (Cotizacion cotX : listCotX) 
             {
                if(cotX.getNroRevision()>maxNroRevision) 
                    maxNroRevision=cotX.getNroRevision();                
             }         
             cot.setNroRevision(maxNroRevision+1);
        }        
        cot.setDescripcion("");
        obra.addCotizaciones(cot);
        if(fechasFueraDeRango)
        {
            pantalla.actualizar(0, "CopiaCotizacionCambioFechas", false);
        }
        return guardarCotizacion();        
    }
    private Cotizacion copiarCotizacion(Cotizacion cotOriginal)
    {
       obraOriginal=cotOriginal.buscarPedidoObra();
       Date fechaInicioObraOriginal=obraOriginal.getFechaInicio();
       fechaInicioObra=obra.getFechaInicio(); 
       fechaFinObra=obra.getFechaFin();
       diasDiferencia=FechaUtil.diasDiferencia(fechaInicioObraOriginal, fechaInicioObra);
       
       Cotizacion cotiz=new Cotizacion();
       //cotiz.setNroCotizacion(cotOriginal.getNroCotizacion());            
       
       cotiz.setFechaCreacion(new Date()); 
       cotiz.setFechaModificacion(new Date());//TODO:revisar
       cotiz.setDescripcion("");
       //Copio SubObras
       Iterator<SubObra> itso = cotOriginal.getSubObras().iterator();
        while (itso.hasNext()) 
        {
            SubObra so = itso.next();
       //ArrayList<SubObra> listaOriginal = (ArrayList)cotOriginal.getSubObras();
        //for (SubObra so: listaOriginal) 
        //{
            SubObra nuevaSO=new SubObra();
            nuevaSO.setDescripcion(so.getDescripcion());
            if(so.isFlagGananciaPorcentaje())
            {nuevaSO.setGananciaPorcentaje(so.getGananciaPorcentaje());}
            else
            {nuevaSO.setGananciaMonto(so.getGananciaMonto());}            
            nuevaSO.setNombre(so.getNombre());
            //Copio SubObrasXTareas
            Iterator<SubObraXTarea> it = so.getTareas().iterator();
            while (it.hasNext()) 
            {
               SubObraXTarea soxt = it.next();
               SubObraXTarea nuevaSoxt =new SubObraXTarea(soxt);
               
               
               nuevaSO.addTarea(nuevaSoxt);
            }
            //Copio SubObrasXMaterial
            //List<SubObraXMaterial> lisaMateriales=so.getMateriales();
           // for (SubObraXMaterial soxm: lisaMateriales)
            //{
            Iterator<SubObraXMaterial> im = so.getMateriales().iterator();
            while (im.hasNext()) 
            {
               SubObraXMaterial soxm = im.next();
               SubObraXMaterial nuevaSoxm =new SubObraXMaterial();
               nuevaSoxm.setCantidad(soxm.getCantidad());
               nuevaSoxm.setDescripcion(soxm.getDescripcion());
               nuevaSoxm.setMaterial(soxm.getMaterial());//TODO: El modelo estÃ¡ mal, acÃ¡ no se esta teniendo en cuetna el precio histÃ³rico
               nuevaSO.addMaterial(nuevaSoxm);
            }
            //Copio SubObrasXAdicional
            Iterator<SubObraXAdicional> ia = so.getAdicionales().iterator();
            while (ia.hasNext()) 
            {
               SubObraXAdicional soxa = ia.next();
            // List<SubObraXAdicional> lisaAdicionales=so.getAdicionales();
           // for (SubObraXAdicional soxa: lisaAdicionales) 
           // {
                SubObraXAdicional nuevaSoxa =new SubObraXAdicional();
                nuevaSoxa.setCantDias(soxa.getCantDias());
                nuevaSoxa.setCantidad(soxa.getCantidad());
                nuevaSoxa.setDescripcion(soxa.getDescripcion());
                nuevaSoxa.setPrecioUnitario(soxa.getPrecioUnitario());
                nuevaSoxa.setTipoAdicional(soxa.getTipoAdicional());
                nuevaSO.addAdicional(nuevaSoxa);
                
            }
            //Copio SubObrasXAlquilerCompra
            Iterator<SubObraXAlquilerCompra> iac = so.getAlquileresCompras().iterator();
            while (iac.hasNext()) 
            {
               SubObraXAlquilerCompra soxac = iac.next();
            //List<SubObraXAlquilerCompra> lisaAlquilerCompra=so.getAlquileresCompras();
           // for (SubObraXAlquilerCompra soxac: lisaAlquilerCompra) 
            //{
                SubObraXAlquilerCompra nuevaSoxac =new SubObraXAlquilerCompra();
                nuevaSoxac.setCantidad(soxac.getCantidad());
                nuevaSoxac.setDescripcion(soxac.getDescripcion());
                nuevaSoxac.setPrecioUnitario(soxac.getPrecioUnitario());
                nuevaSoxac.setTipoAlquilerCompra(soxac.getTipoAlquilerCompra());
                nuevaSO.addAlquilerCompra(nuevaSoxac);
                
            }
            //Copio SubObrasXHerramienta
            Iterator<SubObraXHerramienta> ih = so.getHerramientas().iterator();
            while (ih.hasNext()) 
            {
               SubObraXHerramienta soxh = ih.next();
            //List<SubObraXHerramienta> lisaHerramientas=so.getHerramientas();
            //for (SubObraXHerramienta soxh: lisaHerramientas) 
            //{
                SubObraXHerramienta nuevaSoxh =new SubObraXHerramienta();
//                nuevaSoxh.setCantDias(soxh.getCantDias());
                nuevaSoxh.setCantHoras(soxh.getCantHoras());
                nuevaSoxh.setCostoXHora(soxh.getCostoXHora());
                nuevaSoxh.setObservaciones(soxh.getObservaciones());
                nuevaSoxh.setHerramienta(soxh.getHerramienta());
                nuevaSO.addHerramienta(nuevaSoxh);
            }
            
            cotiz.addSubObra(nuevaSO);
        }
       
       
       
       
        return cotiz;
    }
    
    private int guardarCotizacion()
    {
        try 
        {    
           
         HibernateUtil.beginTransaction();
         ArrayList<SubObra> listaOriginal = (ArrayList)cot.getSubObras();
         for (SubObra so: listaOriginal) 
         {
             //Guardo SubObrasXTareas
            List<SubObraXTarea> lisaTareas=so.getTareas();
            for (SubObraXTarea soxt: lisaTareas)
            {
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
         sesion.saveOrUpdate(obra);
         HibernateUtil.commitTransaction();
         return cot.getId();
        } 
        catch (Exception ex) 
        {
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            return -1;
        } 
    }
    
    public String getNombreCotizacion(int id_cot)
    {
        try
        {
            HibernateUtil.beginTransaction();
            cotOriginal = (Cotizacion) sesion.load(Cotizacion.class,id_cot);
            HibernateUtil.beginTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return cotOriginal.toString();
    }
    
  
    public void updateLEP(Date date) {
        this.cot.setFechaLimiteEntrega(date);
    }

    public void updateLVP(Date date) {
        this.cot.setValidezOferta(date);
    }

    public void updatePlazoEntrega(String text) {
        this.cot.setPlazoEntrega(text);
    }

    public void updateLugarEntrega(String text) {
        this.cot.setLugarEntrega(text);
    }

    public void updateDescripcion(String text) {
        this.cot.setDescripcion(text);
    }

    
    
}
