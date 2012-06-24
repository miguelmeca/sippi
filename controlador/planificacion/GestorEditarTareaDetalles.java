/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
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
       
    }
    
    public void crearNuevoDetalleAcutal(DetalleTareaPlanificacion detallePadre) {
        this.detallePadre=detallePadre;
        copiaDetallePadre= new DetalleTareaPlanificacion(detallePadre);
        modificacion=false;
        this.detalleAcutal = null;
        copiaDetalleAcutal= new DetalleTareaPlanificacion();
        copiaDetalleAcutal.setPadre(copiaDetallePadre);
        copiaTareaConCotizacion.agreagarDetalle(detalleAcutal);
        
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
    { Session sesion;

        List<TipoEspecialidad> tiposEspecialidad=null;
        try{
            sesion= HibernateUtil.getSession();
            sesion.beginTransaction();
            tiposEspecialidad=sesion.createQuery("from TipoEspecialidad").list();
            sesion.getTransaction().commit();
        }
        catch (Exception ex)
        {System.out.println("No se ejecutar la consulta en mostrarTiposEspecialidad()");
        return null;}
        if(tiposEspecialidad==null)
        {return null;}           
        ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
        for (int i = 0; i < tiposEspecialidad.size(); i++)
        {
           TipoEspecialidad te = (TipoEspecialidad)tiposEspecialidad.get(i);
                
           NTupla tupla = new NTupla(te.getId());
           tupla.setNombre(te.getNombre());
           tupla.setData(te);
           
           tuplas.add(tupla);
        }
        return tuplas;           
      }
 
public ArrayList<NTupla> mostrarRangos(TipoEspecialidad te)
        { Session sesion;

        
            gestorBDvarios gestorBD = new gestorBDvarios();
            List<Especialidad> rangos=gestorBD.getEspecialidades( te);
            try{
                sesion= HibernateUtil.getSession();
                sesion.beginTransaction();
                rangos=sesion.createQuery("from Especialidad").list();
                sesion.getTransaction().commit();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarRangos()");
            return null;}
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
        copiaDetalleAcutal.impactarDatos(cantidadPersonas, cantHorasNormales, cantHorasAl50, cantHorasAl100, costoXHoraNormal, especialidad, copiaTareaConCotizacion);
        
        
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


    
}
