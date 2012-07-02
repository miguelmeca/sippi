/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import org.hibernate.Session;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.planificacion.EditarTareaDetalles;
import vista.planificacion.EditarTareaDetallesABM;

/**
 *
 * @author Fran
 */
public class GestorEditarTareaDetalles implements IGestorPlanificacion{
    private EditarTareaDetalles pantallaLista;
    private EditarTareaDetallesABM pantallaABM;
    private GestorEditarTarea gestorPadre;
    private DetalleTareaPlanificacion detalleAcutal;  //SOLO LECTURA
    private DetalleTareaPlanificacion copiaDetalleAcutal;
    private DetalleTareaPlanificacion detallePadre;
    private DetalleTareaPlanificacion copiaDetallePadre;
    
    private DetalleTareaPlanificacion copiaDetallePadre_nva;
    
    private TareaPlanificacion copiaTareaConCotizacion;
    private TareaPlanificacion tareaConCotizacion;
    private SubObraXTareaModif cotizacionTareaConCotizacion;
    private SubObraXTareaModif copiaCotizacionTareaConCotizacion;
    //private SubObraXTarea copiaTareaCotizadaOriginal;
    private boolean modificacion=false;

    public GestorEditarTareaDetalles(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
       if(gestorPadre.getTareaActual().getTareaCotizada()!=null)
       {
           tareaConCotizacion=gestorPadre.getTareaActual();
       }  
       else
       {
           tareaConCotizacion=gestorPadre.getTareaActual().buscarCaminoHastaTareaConCotizacion(gestorPadre.getPlanificacion(), false, true).get(0);
       }
         copiaTareaConCotizacion=new TareaPlanificacion(tareaConCotizacion);
         cotizacionTareaConCotizacion=tareaConCotizacion.getTareaCotizada();
        copiaCotizacionTareaConCotizacion=new SubObraXTareaModif(cotizacionTareaConCotizacion);
        //copiaTareaCotizadaOriginal=new SubObraXTarea(gestorPadre.getTareaActual().buscarCaminoHastaTareaConCotizacion(gestorPadre.getPlanificacion(), false, true).get(0).getTareaCotizada().getOriginal());
        
        copiaTareaConCotizacion.setTareaCotizada(copiaCotizacionTareaConCotizacion);
    }
    
   /* public SubObraXTarea getCopiaTareaCotizadaOriginal()
    {        
        return copiaTareaCotizadaOriginal;
    }*/
    
    public TareaPlanificacion getCopiaTareaConConCotizacion()
    {        
        return copiaTareaConCotizacion;
    }
    
    public DetalleTareaPlanificacion getCopiaDetallePadre()
    {        
        return copiaDetallePadre;
    }
    
    public DetalleTareaPlanificacion getCopiaDetallePadre_nvo()
    {        
        return copiaDetallePadre_nva;
    }
    
   /* public TareaPlanificacion getCopiaTareaAcutal()
    {        
        return copiaTareaAcutal;
    }*/
    
    public void setPantallaLista(EditarTareaDetalles pantallaLista) {
        this.pantallaLista = pantallaLista;
    }

    public void setPantallaABM(EditarTareaDetallesABM pantallaABM) {
        this.pantallaABM = pantallaABM;
    }
    
    
    //Solo si se trata de una modificacion de detalle y no una creacion
    public void setDetalleAcutal(DetalleTareaPlanificacion detalleAcutal) {
        modificacion=true;
        this.detalleAcutal = detalleAcutal;
        copiaDetalleAcutal= new DetalleTareaPlanificacion(detalleAcutal);
        
        int indiceDetalleActual=gestorPadre.getTareaActual().getDetalles().indexOf(detalleAcutal);
          
        this.detallePadre=detalleAcutal.getPadre();
        copiaDetallePadre= new DetalleTareaPlanificacion(detallePadre);
        copiaDetalleAcutal.setPadre(copiaDetallePadre);
       if(indiceDetalleActual!=-1)
       {
           copiaTareaConCotizacion.getDetalles().remove(indiceDetalleActual);
           copiaTareaConCotizacion.getDetalles().add(indiceDetalleActual, copiaDetalleAcutal);
              
       }
       else
       {
           pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
           Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle actual en tarea");
       }
       copiaDetallePadre_nva=null;
       
    }
    
    public void crearNuevoDetalleAcutal(DetalleTareaPlanificacion detallePadre) {
        this.detallePadre=detallePadre;
        copiaDetallePadre= new DetalleTareaPlanificacion(detallePadre);
        modificacion=false;
        this.detalleAcutal = null;
        copiaDetalleAcutal= new DetalleTareaPlanificacion();
        copiaDetalleAcutal.setPadre(copiaDetallePadre);
        copiaTareaConCotizacion.agreagarDetalle(copiaDetalleAcutal);   
        copiaDetallePadre_nva=null;
    } 
        
    
    public ArrayList<NTupla> mostrarTareasSuperiores()
    { 
        //List<TareaPlanificacion> tareas=new ArrayList<TareaPlanificacion>();
        List<TareaPlanificacion> tareasSuperiores=new ArrayList<TareaPlanificacion>();
        try{            
            tareasSuperiores= gestorPadre.getTareaActual().buscarCaminoHastaTareaConCotizacion(gestorPadre.getPlanificacion(),false,true);
            
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            if(tareasSuperiores.isEmpty())
            {
                NTupla tuplaV = new NTupla(-1);
                tuplaV.setNombre("No hay tareas de nivel superior");
                tuplas.add(tuplaV);
                return tuplas;
            }            
            
            for (int i = 0; i < tareasSuperiores.size(); i++)
            {
                TareaPlanificacion tt = (TareaPlanificacion)tareasSuperiores.get(i);
                
                NTupla tuplaT = new NTupla(tt.hashCode());
                tuplaT.setNombre(tt.getNombre());
                tuplaT.setData(tt);
                tuplas.add(tuplaT);
             }
            return tuplas;    
           }
           catch (Exception ex){
                System.out.println("No se ejecutar la consulta para cargar el combo de tareas de nivel superior :"+ex);
                pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista Tareas de nivel superior");
                return null;
           }       
        }
    
     public ArrayList<NTupla> mostrarTiposEspecialidad()
    { //Session sesion;
        ArrayList<NTupla> tuplas;
        gestorBDvarios gestorBD = new gestorBDvarios();
        tuplas=gestorBD.getNTuplasTipoEspecialidades();
        /*//List<TipoEspecialidad> tiposEspecialidad=null;
        try{
            //sesion= HibernateUtil.getSession();
            
            //HibernateUtil.beginTransaction();
           //tiposEspecialidad=HibernateUtil.getSession().createQuery("from TipoEspecialidad").list();
           
            if(tiposEspecialidad==null)
            {return null;}           
            tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < tiposEspecialidad.size(); i++)
            {
                TipoEspecialidad te = (TipoEspecialidad)tiposEspecialidad.get(i);

                NTupla tupla = new NTupla(te.getId());
                tupla.setNombre(te.getNombre());
                tupla.setData(te);

                tuplas.add(tupla);
            } 
            //HibernateUtil.commitTransaction();
           
        }
        catch (Exception ex)
        {System.out.println("No se ejecutar la consulta en mostrarTiposEspecialidad()");
       // HibernateUtil.rollbackTransaction();
        return null;}*/
        return tuplas;           
      }
 
public ArrayList<NTupla> mostrarRangos(TipoEspecialidad te)
        { Session sesion;

        
            gestorBDvarios gestorBD = new gestorBDvarios();
            List<Especialidad> rangos=gestorBD.getEspecialidades( te);
            /*try{
                sesion= HibernateUtil.getSession();
                sesion.beginTransaction();
                rangos=sesion.createQuery("from Especialidad").list();
                sesion.getTransaction().commit();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarRangos()");
            return null;}*/
            if(rangos==null)
            {return null;}           
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < rangos.size(); i++)
            {
                Especialidad re = (Especialidad)rangos.get(i);
                
                NTupla nTupla = new NTupla(re.getId());
                nTupla.setNombre(re.getRango().getNombre());
                nTupla.setData(re.getPrecioHoraNormal());
                    tuplas.add(nTupla);
             }
            return tuplas;           
        }
    
    /*public DetalleTareaPlanificacion getDetalleTareaActual() {
        return this.detalleAcutal;
    }*/

    public PlanificacionXXX getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    public TareaPlanificacion getTareaActual() {
        return this.gestorPadre.getTareaActual();
    }

    public void refrescarPantallas() {
        this.gestorPadre.refrescarPantallas();
    }
    
    public void tomarCambios(int cantidadPersonas, double cantHorasNormales,  double cantHorasAl50,  double cantHorasAl100,  double costoXHoraNormal, Especialidad especialidad)
    {
        impactarDatos(cantidadPersonas, cantHorasNormales, cantHorasAl50, cantHorasAl100, costoXHoraNormal, especialidad, copiaTareaConCotizacion, copiaDetalleAcutal);
        
        
        pantallaABM.actualizar();
    }


    private void guardar()
    {
       if(modificacion)
        {
          int indiceDetalleActual=gestorPadre.getTareaActual().getDetalles().indexOf(detalleAcutal);
          
          if(indiceDetalleActual!=-1)
          {
              gestorPadre.getTareaActual().getDetalles().remove(indiceDetalleActual);
              gestorPadre.getTareaActual().getDetalles().add(indiceDetalleActual, copiaDetalleAcutal);
              
          }
          else
          {
              pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
              Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle actual en tarea");
          }
            
            
        }
        else
        {
            gestorPadre.getTareaActual().agreagarDetalle(copiaDetalleAcutal);
            
        }
        pantallaLista.actualizar(0, null, true, null);
    }

     public void impactarDatos( int cantidadPersonas,double cantHorasNormales, double cantHorasAl50, double cantHorasAl100, double costoXHoraNormal, Especialidad especialidad, TareaPlanificacion tarea, DetalleTareaPlanificacion detalle) throws RuntimeException
    {
        if(tarea.getDetalles().indexOf(detalle)==-1)
        {
            throw new RuntimeException("El detalle no pertenece a la tarea pasada por parametro");
        }
        int cant_aux;
        if(copiaDetallePadre_nva==null)
        {
            cant_aux=detalle.getPadre().getCantidadPersonas();
            
        }
        else
        {   
            cant_aux=detalle.getPadre().getCantidadPersonas()+copiaDetallePadre_nva.getCantidadPersonas();
        }
        if(cant_aux<cantidadPersonas)
        {
                throw new RuntimeException("La cantidad de personas no puede ser mayor a la actual");
        }
        if(especialidad==null)
        {
            throw new RuntimeException("La la especialidad no puede ser null");
        }
        
        boolean altoImpactoMasHoras=false;//=true  cambios en detalle en cotizacion modificada (agregar horas) - restar las horas al padre.
        boolean altoImpactoMenosPersonas=false;//=true  dividir detalle en tarea padre. usar horas de uno de ellos.
        boolean altoImpactoCosto=false; //=true  crear nuevo detalle en cotizacion modificada
        
        //Si todos son false, simplemente restar las horas al padre.
        
        if( !especialidad.equals(detalle.getPadre().getEspecialidad()) || detalle.getPadre().getCostoXHoraNormal() !=costoXHoraNormal)
        {
            altoImpactoCosto=true;
        }
        
        if(detalle.getPadre().getCantidadPersonas()!=cantidadPersonas)
        {
            altoImpactoMenosPersonas=true;
        }       
        
        
        if(altoImpactoMenosPersonas)
        {
            //divido al padre en 3:  padre original (puede estar siendo padre de otro detalle)
            //                       padre nuevo (el q sera padre de este) - tiene detalle cotizado propio (nuevo)
            //                       tio (division del padre con el sobrante de personas)  - tiene detalle cotizado propio (nuevo)
            
            //Resto a lo cotizado las horas del padre
            detalle.getPadre().getCotizado().setCantHorasNormales(detalle.getPadre().getCotizado().getCantHorasNormales() - detalle.getPadre().getCantHorasNormales());
            detalle.getPadre().getCotizado().setCantHorasAl50(detalle.getPadre().getCotizado().getCantHorasAl50() - detalle.getPadre().getCantHorasAl50());
            detalle.getPadre().getCotizado().setCantHorasAl100(detalle.getPadre().getCotizado().getCantHorasAl100() - detalle.getPadre().getCantHorasAl100());
            
            //Creo las 2 divisiones nuevas del padre (padre nuevo y tio)
            DetalleTareaPlanificacion tio= new DetalleTareaPlanificacion(detalle.getPadre());
            DetalleTareaPlanificacion padreNuevo= new DetalleTareaPlanificacion(detalle.getPadre());
            tio.setCantidadPersonas( tio.getCantidadPersonas() - cantidadPersonas); //tio.cantidadPersonas  -= cantidadPersonas; 
            padreNuevo.setCantidadPersonas(cantidadPersonas);
            
            
            //Creo las cotizaciones de tio y padre nuevo
            DetalleSubObraXTareaModif tioCotizado= new DetalleSubObraXTareaModif((DetalleSubObraXTareaModif)detalle.getPadre().getCotizado());
            DetalleSubObraXTareaModif padreNuevoCotizado= new DetalleSubObraXTareaModif((DetalleSubObraXTareaModif)detalle.getPadre().getCotizado());
            tioCotizado.setCantidadPersonas(detalle.getPadre().getCotizado().getCantidadPersonas() - cantidadPersonas); 
            padreNuevoCotizado.setCantidadPersonas(cantidadPersonas);
            tarea.getTareaCotizada().agreagarDetalle(tioCotizado);
            tarea.getTareaCotizada().agreagarDetalle(padreNuevoCotizado);
            
            //Resto las horas sobrantes al padre
            detalle.getPadre().setCantHorasNormales(detalle.getPadre().getCantHorasNormales() - padreNuevo.getCantHorasNormales());
            detalle.getPadre().setCantHorasAl50(detalle.getPadre().getCantHorasAl50() - padreNuevo.getCantHorasAl50());
            detalle.getPadre().setCantHorasAl100(detalle.getPadre().getCantHorasAl100() - padreNuevo.getCantHorasAl100());
            
            tio.setCotizado(tioCotizado);
            padreNuevo.setCotizado(padreNuevoCotizado);
            detalle.setPadre(padreNuevo);
            copiaDetallePadre_nva=tio;
            copiaDetallePadre=padreNuevo;
        }
        
        if(altoImpactoCosto)
        {
            //divido al padre en 2:  padre original (puede estar siendo padre de otro detalle)
            //                       padre nuevo (el q sera padre de este) - tiene detalle cotizado propio (nuevo)
            
            //Resto a lo cotizado las horas que necesito
            detalle.getPadre().getCotizado().setCantHorasNormales(detalle.getPadre().getCotizado().getCantHorasNormales() - cantHorasNormales);
            detalle.getPadre().getCotizado().setCantHorasAl50(detalle.getPadre().getCotizado().getCantHorasAl50() - cantHorasAl50);
            detalle.getPadre().getCotizado().setCantHorasAl100(detalle.getPadre().getCotizado().getCantHorasAl100() - cantHorasAl100);
            
            //Creo la division nueva del padre (padre nuevo)
            DetalleTareaPlanificacion padreNuevo= new DetalleTareaPlanificacion(detalle.getPadre());
            padreNuevo.setCostoXHoraNormal(costoXHoraNormal);
            padreNuevo.setEspecialidad(especialidad);
            padreNuevo.setCantHorasNormales(cantHorasNormales);
            padreNuevo.setCantHorasAl50(cantHorasAl50);
            padreNuevo.setCantHorasAl100(cantHorasAl100);
            
            //Creo la cotizacion de  padre nuevo
            DetalleSubObraXTareaModif padreNuevoCotizado= new DetalleSubObraXTareaModif((DetalleSubObraXTareaModif)detalle.getPadre().getCotizado());
            tarea.getTareaCotizada().agreagarDetalle(padreNuevoCotizado);
            padreNuevoCotizado.setCostoXHoraNormal(costoXHoraNormal);
            padreNuevoCotizado.setEspecialidad(especialidad);
            padreNuevoCotizado.setCantHorasNormales(cantHorasNormales);
            padreNuevoCotizado.setCantHorasAl50(cantHorasAl50);
            padreNuevoCotizado.setCantHorasAl100(cantHorasAl100);
            
            padreNuevo.setCotizado(padreNuevoCotizado);
            
            //Resto las horas sobrantes al padre
            detalle.getPadre().setCantHorasNormales(detalle.getPadre().getCantHorasNormales() - padreNuevo.getCantHorasNormales());
            detalle.getPadre().setCantHorasAl50(detalle.getPadre().getCantHorasAl50() - padreNuevo.getCantHorasAl50());
            detalle.getPadre().setCantHorasAl100(detalle.getPadre().getCantHorasAl100() - padreNuevo.getCantHorasAl100());
                        
            
            detalle.setPadre(padreNuevo);
        }
        
        
        
        if( detalle.getPadre().getCantHorasNormales() <cantHorasNormales)
        {
            detalle.getPadre().getCotizado().setCantHorasNormales(detalle.getPadre().getCotizado().getCantHorasNormales()+(cantHorasNormales-detalle.getPadre().getCantHorasNormales()));
            detalle.getPadre().setCantHorasNormales(0.0);
        }
        else
        {
           detalle.getPadre().setCantHorasNormales(detalle.getPadre().getCantHorasNormales() - cantHorasNormales); 
        }
        
        if( detalle.getPadre().getCantHorasAl50() <cantHorasAl50) 
        {
            detalle.getPadre().getCotizado().setCantHorasAl50(detalle.getPadre().getCotizado().getCantHorasAl50()+(cantHorasAl50-detalle.getPadre().getCantHorasAl50()));
            detalle.getPadre().setCantHorasAl50(0.0);
        }
        else
        {
            detalle.getPadre().setCantHorasAl50(detalle.getPadre().getCantHorasAl50() -cantHorasAl50);
        }
        
        if( detalle.getPadre().getCantHorasAl100() <cantHorasAl100)
        {
            detalle.getPadre().getCotizado().setCantHorasAl100(detalle.getPadre().getCotizado().getCantHorasAl100()+(cantHorasAl100-detalle.getPadre().getCantHorasAl100()));
            detalle.getPadre().setCantHorasAl100(0.0);
        }
        else
        {
           detalle.getPadre().setCantHorasAl100(detalle.getPadre().getCantHorasAl100() - cantHorasAl100);
        }
        ///////////////////////////////////////////////////
       
        
        //Devuelvo el sobrante al padre:        
       if( detalle.getCantHorasNormales() >cantHorasNormales)
        {
            detalle.getPadre().setCantHorasNormales(detalle.getPadre().getCantHorasNormales()+(detalle.getCantHorasNormales() - cantHorasNormales));
            //padre.setCantHorasNormales(0.0);
        }
        
        if( detalle.getCantHorasAl50() >cantHorasAl50) 
        {
            detalle.getPadre().setCantHorasAl50(detalle.getPadre().getCantHorasAl50()+(detalle.getCantHorasAl50()- cantHorasAl50));
            ///padre.setCantHorasAl50(0.0);
        }
        
        if( detalle.getCantHorasAl100() >cantHorasAl100)
        {
            detalle.getPadre().setCantHorasAl100(detalle.getPadre().getCantHorasAl100()+(detalle.getCantHorasAl100() - cantHorasAl100));
            //padre.setCantHorasAl100(0.0);
        }
        
       
    }
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////

    
}
