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
import util.LogUtil;
import util.NTupla;
import vista.planificacion.EditarTareaDetalles;
import vista.planificacion.EditarTareaDetallesABM;
import vista.planificacion.EditarTareaDetallesABM_SeleccionDetallePadre;

/**
 *
 * @author Fran
 */
public class GestorEditarTareaDetalles implements IGestorPlanificacion{
    private EditarTareaDetalles pantallaLista;
    private EditarTareaDetallesABM pantallaABM;
    private EditarTareaDetallesABM_SeleccionDetallePadre pantallaABM_SeleccionPadre;
    private GestorEditarTarea gestorPadre;
    private DetalleTareaPlanificacion detalleActual;  //SOLO LECTURA MIENTRAS NO SE PRESIONE ACEPTAR
    private DetalleTareaPlanificacion copiaDetalleActual;
    private DetalleTareaPlanificacion detallePadre;
    private DetalleTareaPlanificacion copiaDetallePadre;    
    private DetalleTareaPlanificacion copiaDetallePadre_tio;
    private DetalleTareaPlanificacion detalleConCotizacion;
    private DetalleTareaPlanificacion copiaDetalleConCotizacion;  
    
    
    private TareaPlanificacion copiaTareaConCotizacion;
    private TareaPlanificacion tareaConCotizacion;
    
    
    private TareaPlanificacion tareaActual;
    private TareaPlanificacion copiaTareaActual;
    private SubObraXTareaModif cotizacionTareaCotizada;
    private SubObraXTareaModif copiaCotizacionTareaCotizada;
    
    private DetalleSubObraXTareaModif detalleCotizacionTareaCotizada;
    private DetalleSubObraXTareaModif copiaDetalleCotizacionTareaCotizada;
    private TareaPlanificacion copiaTareaConDetallePadre;
    private TareaPlanificacion tareaConDetallePadre;
    private List<Empleado> listaEmpleadosAsignados;
    //private SubObraXTarea copiaTareaCotizadaOriginal;
    private boolean modificacion=false;
    private boolean tareaHijaDePlanificacion=false;
    private boolean detalleNoCotizado=false;
    private boolean devolverHorasATareaSuperior=true;

   // private List<TareaPlanificacion> caminoTareas; 
    
    private int cantPersonas;
    private double cantHsNormales;
    private double cantHs100;
    private double cantHs50;
    private Especialidad especialidad;
    private double costoDetalle;
    
    
    //Solo para modificacion:
    private int cantPersonasOriginal;
    private double cantHsNormalesOriginal;
    private double cantHs100Original;
    private double cantHs50Original;
    private Especialidad especialidadOriginal;
    private double costoDetalleOriginal;
    private List<TareaPlanificacion> caminoTareas;
    private List<TareaPlanificacion> copiaCaminoTareas;

    public GestorEditarTareaDetalles(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
        tareaActual=gestorPadre.getTareaActual();
        ///Para la parte que muestra el impacto en la tarea de cotizacion
        caminoTareas=tareaActual.buscarCaminoHastaTareaConCotizacion(gestorPadre.getPlanificacion(), true, true);
        tareaConCotizacion=caminoTareas.get(0);
        if(tareaConCotizacion.equals(tareaActual))
        {tareaHijaDePlanificacion=true;}
        else
        {tareaHijaDePlanificacion=false;}
        
        /*
       //Realizo las copias de trabajo de la tarea que tiene cotizacion (puede ser la que tenemos o una superior)
        copiaTareaConCotizacion=new TareaPlanificacion(getTareaConCotizacion());
        cotizacionTareaCotizada=tareaConCotizacion.getTareaCotizada();
        copiaCotizacionTareaCotizada=new SubObraXTareaModif(cotizacionTareaCotizada);
        copiaTareaConCotizacion.setTareaCotizada(copiaCotizacionTareaCotizada);
       
       */
       
        
    }
    
    public DetalleTareaPlanificacion getDetalleActual()
    {        
        return detalleActual;
    }
    
    public DetalleTareaPlanificacion getDetallePadre()
    {        
        return detallePadre;
    }
  
    
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
        return copiaDetallePadre_tio;
    }
     
    
    public void setPantallaLista(EditarTareaDetalles pantallaLista) {
        this.pantallaLista = pantallaLista;
    }

    public void setPantallaABM(EditarTareaDetallesABM pantallaABM) {
        this.pantallaABM = pantallaABM;
    }
    
    public void setPantallaABM_SeleccionPadre(EditarTareaDetallesABM_SeleccionDetallePadre pantallaABM) {
        this.pantallaABM_SeleccionPadre = pantallaABM;
    }
    
    
    //Solo si se trata de una modificacion de detalle y no una creacion
    public void setDetalleAcutal(DetalleTareaPlanificacion detalleActual, boolean tareaHijaDePlanificacion){
        limpiarDatosCopia();
        this.setTareaHijaDePlanificacion(tareaHijaDePlanificacion);      
               this.setDetalleNoCotizado(false);
       // copiaTareaActual=new TareaPlanificacion(gestorPadre.getTareaActual());
        modificacion=true;        
        this.detallePadre=detalleActual.getPadre();        
        this.detalleActual = detalleActual;
        
        cantPersonasOriginal=detalleActual.getCantidadPersonas();
        cantHsNormalesOriginal=detalleActual.getCantHorasNormales();
        cantHs100Original=detalleActual.getCantHorasAl100();
        cantHs50Original=detalleActual.getCantHorasAl50();
        especialidadOriginal=detalleActual.getEspecialidad();
        costoDetalleOriginal=detalleActual.getCostoXHoraNormal();
        
        cantPersonas=cantPersonasOriginal;
        cantHsNormales=cantHsNormalesOriginal;
        cantHs100=cantHs100Original;
        cantHs50=cantHs50Original;
        especialidad=especialidadOriginal;
        costoDetalle=costoDetalleOriginal;
        setListaEmpleadosAsignados(detalleActual.getEmpleados());
        //pantallaABM.tomarDatosDetalleModificado(gestorPadre.getPlanificacion().getTareaDeDetalle(detallePadre), detallePadre, detalleActual);
        if(!tareaHijaDePlanificacion)
        {
            armarCopiaDeEstructura(detalleActual);        
        }
        else
        {
            armarCopiaDeEstructuraTareaCotizada(detalleActual);
        }
        
    }
    
    public void crearNuevoDetalleAcutal(DetalleTareaPlanificacion detallePadre ) {
        limpiarDatosCopia();
        setTareaHijaDePlanificacion(false);
       // copiaTareaActual=new TareaPlanificacion(gestorPadre.getTareaActual());
        this.setDetalleNoCotizado(false);
        modificacion = false;
        this.detallePadre = detallePadre;
        detalleActual = new DetalleTareaPlanificacion();
        detalleActual.setearPadre(detallePadre);
        
        detalleActual.setCostoXHoraNormal(detallePadre.getCostoXHoraNormal());
        detalleActual.setCantidadPersonas(detallePadre.getCantidadPersonas());
        detalleActual.setEspecialidad(detallePadre.getEspecialidad());
        setListaEmpleadosAsignados(detalleActual.getEmpleados());
        armarCopiaDeEstructura(detalleActual);
    } 
    
    public void crearNuevoDetalleNoCotizado()
    {
        this.setDetalleNoCotizado(true);
        modificacion = false;
        this.detallePadre = null;
        detalleActual = new DetalleTareaPlanificacion();
        if(this.isTareaHijaDePlanificacion())
        {armarCopiaDeEstructuraTareaCotizada(detalleActual);}
        else
        {armarCopiaDeEstructura(detalleActual);}
        setListaEmpleadosAsignados(detalleActual.getEmpleados());        
    }
    
   
    
    
     private void armarCopiaDeEstructura(DetalleTareaPlanificacion detalleActual)
    {
        
        copiaTareaConCotizacion=new TareaPlanificacion(tareaConCotizacion);
        tareaActual=gestorPadre.getTareaActual();
        copiaTareaActual=tareaActual.getTareaCopia();
        if(gestorPadre.getTareaActual().equals(getTareaConCotizacion()))
        {
            copiaTareaConCotizacion=copiaTareaActual;
        }
        else
        {
            copiaTareaConCotizacion=getTareaConCotizacion().getTareaCopia();
        }
        cotizacionTareaCotizada=getTareaConCotizacion().getTareaCotizada();
        copiaCotizacionTareaCotizada=cotizacionTareaCotizada.getTareaCopia();
        
        if(detallePadre!=null)
        {
            copiaDetallePadre= detallePadre.getDetalleCopia();
            copiaDetallePadre_tio=null;
            tareaConDetallePadre=gestorPadre.getPlanificacion().getTareaDeDetalle(detallePadre);
            
            this.copiaTareaConDetallePadre= tareaConDetallePadre.getTareaCopia();
            
            /*
            int indiceDetalleActual_=tareaConDetallePadre.getDetalles().indexOf(detallePadre);
                if(indiceDetalleActual_!=-1)
                {
                    //copiaTareaConCotizacion.getDetalles().remove(indiceDetalleActual);
                    copiaTareaConDetallePadre.getDetalles().remove(indiceDetalleActual_);
                }
                else
                {
                    pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
                    Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle padre");
                }
            copiaTareaConDetallePadre.agreagarDetalle(copiaDetallePadre, indiceDetalleActual_);*/
        }
        else
        {
            copiaDetallePadre= null;
            copiaDetallePadre_tio=null;
            tareaConDetallePadre= null;
            
            this.copiaTareaConDetallePadre= null;
        }
        //////////////////////////////////////
        
        //Reinicio el detalle copia
        if(this.copiaDetalleActual !=null && isModificacion())
        {
            int indiceDetalleActual=gestorPadre.getTareaActual().getDetalles().indexOf(detalleActual);
            if(indiceDetalleActual!=-1)
            {
                //copiaTareaConCotizacion.getDetalles().remove(indiceDetalleActual);
                copiaTareaActual.getDetalles().remove(indiceDetalleActual);
            }
            else
            {
                pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
                Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle actual en tarea");
            }
         //   this.copiaDetalleActual = null;
        }
        
        copiaDetalleActual= new DetalleTareaPlanificacion(detalleActual);
        copiaDetalleActual.setearPadre(copiaDetallePadre);
        //copiaTareaConCotizacion.agreagarDetalle(copiaDetalleAcutal); 
        copiaTareaActual.agreagarDetalle(copiaDetalleActual); 
        
        //////////////////////////////////////
        if(detallePadre!=null)
        {detalleConCotizacion=getDetalleConCotizacion(getTareaConCotizacion(), detallePadre);
        
        copiaDetalleConCotizacion=detalleConCotizacion.getDetalleCopia();
        
        int indiceDetalleActual_1=getTareaConCotizacion().getDetalles().indexOf(detalleConCotizacion);
            if(indiceDetalleActual_1!=-1)
            {
               copiaTareaConCotizacion.getDetalles().remove(indiceDetalleActual_1);
               copiaTareaConCotizacion.agreagarDetalle(copiaDetalleConCotizacion, indiceDetalleActual_1);
            }
            else
            {
                pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
                Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle de tarea con cotizacion");
            }        
        
        //////////////FALTA REVISAR
        ///////////////////////////////////////////////////////////////
        detalleCotizacionTareaCotizada=detalleConCotizacion.getCotizado();
        
        
        }
        //COPIACAMINOTAREAS
        copiaCaminoTareas = obtenerCopiaCaminoDeTareas();
        
    }
    
    private List<TareaPlanificacion> obtenerCopiaCaminoDeTareas()
    {
        ArrayList<TareaPlanificacion> copiaCaminoTareasX = new ArrayList<TareaPlanificacion>();
       
        for (int i = 0; i < caminoTareas.size(); i++) 
        {
            copiaCaminoTareasX.add(caminoTareas.get(i).getTareaCopia());
        }
       return copiaCaminoTareasX;
    }
   
    private void armarCopiaDeEstructuraTareaCotizada(DetalleTareaPlanificacion detalleActual)
    {
        
        /////////////////////////////TODO: Verificar si esta bien esto aca
        copiaTareaActual=new TareaPlanificacion(gestorPadre.getTareaActual());
        
        copiaTareaConCotizacion=copiaTareaActual;
       
        
        cotizacionTareaCotizada=getTareaConCotizacion().getTareaCotizada();
        copiaCotizacionTareaCotizada=cotizacionTareaCotizada.getTareaCopia();
        
        //Reinicio el detalle copia
        if(this.copiaDetalleActual !=null && isModificacion())
        {
            int indiceDetalleActual=gestorPadre.getTareaActual().getDetalles().indexOf(detalleActual);
            if(indiceDetalleActual!=-1)
            {
                //copiaTareaConCotizacion.getDetalles().remove(indiceDetalleActual);
                copiaTareaActual.getDetalles().remove(indiceDetalleActual);
            }
            else
            {
                pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
                Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle actual en tarea");
            }
         //   this.copiaDetalleActual = null;
        }        
        copiaDetalleActual= new DetalleTareaPlanificacion(detalleActual);        
        copiaTareaActual.agreagarDetalle(copiaDetalleActual); 
        copiaCaminoTareas = obtenerCopiaCaminoDeTareas();
    }
     private void limpiarDatosCopia()
    {
        /////////////////////////////TODO: Verificar si esta bien esto aca
        copiaTareaActual=null;
        copiaDetallePadre=null;
        copiaTareaConCotizacion=null;
       
        
        cotizacionTareaCotizada=null;
        copiaCotizacionTareaCotizada=null;
        
        //Reinicio el detalle copia
        copiaDetalleActual =null;
        
        copiaDetalleActual=null;    
        copiaCaminoTareas =null;
        
        
        copiaDetallePadre_tio=null;
        copiaDetalleConCotizacion=null; 
        copiaDetalleCotizacionTareaCotizada=null;
        copiaTareaConDetallePadre=null;
    }
    
    
    private DetalleTareaPlanificacion getDetalleConCotizacion(TareaPlanificacion tareaCotizada, DetalleTareaPlanificacion detalleDescendiente)
    {
        DetalleTareaPlanificacion detalleSuperior=detalleDescendiente;
        if(tareaCotizada.tieneDetalle(detalleSuperior))
        {return detalleSuperior;}
        else
        {
            detalleSuperior=detalleDescendiente.getPadre();
            if(detalleSuperior!=null)
            {return getDetalleConCotizacion(tareaCotizada, detalleSuperior);}
            else
            {return null;}
            
        }
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
       
        return tuplas;           
      }
 
public ArrayList<NTupla> mostrarRangos(TipoEspecialidad te)
        { Session sesion;

        
            gestorBDvarios gestorBD = new gestorBDvarios();
            List<Especialidad> rangos=gestorBD.getEspecialidades( te);
            
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
    
    
    public Planificacion getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    public TareaPlanificacion getTareaActual() {
        return this.gestorPadre.getTareaActual();
    }

    public void refrescarPantallas() {
        this.gestorPadre.refrescarPantallas();
    }
    
    public void tomarCambios(int cantidadPersonas, double cantHorasNormales,  double cantHorasAl50,  double cantHorasAl100,  double costoXHoraNormal, int idEspecialidad)
    {
        
        this.cantPersonas=cantidadPersonas;
        this.cantHsNormales=cantHorasNormales;
        this.cantHs50=cantHorasAl50;
        this.cantHs100=cantHorasAl100;
        gestorBDvarios gestorBD = new gestorBDvarios();
        this.especialidad=gestorBD.getEspecialidad(idEspecialidad);
        this.costoDetalle=costoXHoraNormal;
        
        datosEnDetalleActual(copiaDetalleActual, cantHorasNormales, cantHorasAl50, cantHorasAl100, costoXHoraNormal, cantidadPersonas, especialidad);
        if(isTareaHijaDePlanificacion())
        {
            armarCopiaDeEstructuraTareaCotizada(detalleActual);
        }
        else
        {
          armarCopiaDeEstructura(detalleActual);
        }
        try
        {
            if(isTareaHijaDePlanificacion() ||  this.isDetalleNoCotizado())
            {
                impactarDatosDetalleNoIncluidosEnCotizacion(copiaCaminoTareas, cantidadPersonas, cantHorasNormales, cantHorasAl50, cantHorasAl100, costoXHoraNormal, especialidad, copiaTareaConCotizacion, copiaDetalleActual);
            }
            else
            {
                impactarDatos(copiaCaminoTareas, cantidadPersonas, cantHorasNormales, cantHorasAl50, cantHorasAl100, costoXHoraNormal, especialidad, copiaTareaConCotizacion, copiaDetalleConCotizacion, copiaTareaConDetallePadre, copiaDetalleActual);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
           pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Error interno");
           Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR en tomar cambios en GestotEditarTareaDetalles");
           return;
        }
        
        pantallaABM.actualizar();
    }

    
    public void datosEnDetalleActual(DetalleTareaPlanificacion detalle, double cantHorasNormales, double cantHorasAl50, double cantHorasAl100, double costoXHoraNormal, int cantidadPersonas, Especialidad especialidad){
        detalle.setCantHorasNormales(cantHorasNormales);
        detalle.setCantHorasAl50(cantHorasAl50);
        detalle.setCantHorasAl100(cantHorasAl100);
        detalle.setCostoXHoraNormal(costoXHoraNormal);
        detalle.setCantidadPersonas(cantidadPersonas);
        detalle.setEspecialidad(especialidad);
    }

   /* private void guardar()
    {
       if(modificacion)
        {
          int indiceDetalleActual=gestorPadre.getTareaActual().getDetalles().indexOf(detalleActual);
          
          if(indiceDetalleActual!=-1)
          {
              gestorPadre.getTareaActual().getDetalles().remove(indiceDetalleActual);
              gestorPadre.getTareaActual().getDetalles().add(indiceDetalleActual, copiaDetalleActual);
              
          }
          else
          {
              pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Ocurrio un error interno");
              Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR obteniendo indice de detalle actual en tarea");
          }
            
            
        }
        else
        {
            gestorPadre.getTareaActual().agreagarDetalle(copiaDetalleActual);
            
        }
        pantallaLista.actualizar(0, null, true, null);
    }*/

     public void impactarDatos( List<TareaPlanificacion> caminoDeTareas, int cantidadPersonas,double cantHorasNormales, double cantHorasAl50, double cantHorasAl100, double costoXHoraNormal, Especialidad especialidad, TareaPlanificacion tareaConCotizacion, DetalleTareaPlanificacion detalleCotizado, TareaPlanificacion tareaConDetallePadre, DetalleTareaPlanificacion detalleActual) throws Exception
    {
        DetalleTareaPlanificacion padreOriginal=detalleActual.getPadre();
        //DetalleTareaPlanificacion padrefinal=padreOriginal;
        DetalleSubObraXTareaModif detalleCotizacion=detalleCotizado.getCotizado();
        if(tareaConCotizacion.getDetalles().indexOf(detalleCotizado)==-1)
        {
            throw new Exception("El detalle no pertenece a la tarea pasada por parametro");
        }
         if(tareaConCotizacion.getTareaCotizada().getDetallesMod().indexOf(detalleCotizado.getCotizado())==-1)
        {
            throw new Exception("El detalle cotizado no pertenece a la tarea pasada por parametro");
        }
        
        if(tareaConDetallePadre.getDetalles().indexOf(detalleActual.getPadre())==-1)
        {
            throw new Exception("El detalle no pertenece a la tarea pasada por parametro");
        }       
        
        
        int cant_aux;
        //if(copiaDetallePadre_tio==null)
        //{
            cant_aux=padreOriginal.getCantidadPersonas();
            
        //}
        /*else
        {   
            cant_aux=padreOriginal.getCantidadPersonas()+copiaDetallePadre_tio.getCantidadPersonas();
        }*/
        if(cant_aux<cantidadPersonas)
        {
                throw new Exception("La cantidad de personas no puede ser mayor a la actual");
        }
        if(especialidad==null)
        {
            throw new Exception("La la especialidad no puede ser null");
        }
        
        boolean altoImpactoMasHoras=false;//=true  cambios en detalle en cotizacion modificada (agregar horas) - restar las horas al padre.
        boolean altoImpactoMenosPersonas=false;//=true  dividir detalle en tarea padre. usar horas de uno de ellos.
        boolean altoImpactoCosto=false; //=true  crear nuevo detalle en cotizacion modificada
        
        //Si todos son false, simplemente restar las horas al padre.
        
        if( (especialidad.getId()!=(padreOriginal.getEspecialidad().getId())) || (padreOriginal.getCostoXHoraNormal() !=costoXHoraNormal))
        {
            altoImpactoCosto=true;
        }
        
        if(padreOriginal.getCantidadPersonas()!=cantidadPersonas)
        {
            altoImpactoMenosPersonas=true;
        }       
        
        int indiceTareaConDetallePadre;
        indiceTareaConDetallePadre=caminoDeTareas.indexOf(tareaConDetallePadre);
        if(altoImpactoMenosPersonas || altoImpactoCosto)
        {
            //divido al padre cotizado en 3:  padre original (puede estar siendo padre de otro detalle)
            //                       padre nuevo (el q sera padre de este) - tiene detalle cotizado propio (nuevo)
            //                       tio (division del padre con el sobrante de personas)  - tiene detalle cotizado propio (nuevo)
            
            
            
            boolean creaTio=false;
            //Creo las 2 divisiones nuevas del detalle cotizado (cot_padre nuevo y cot_tio)    //TODO: SOLO CREAR TIO EN CASO NECESARIO
            
            DetalleTareaPlanificacion padreNuevo= new DetalleTareaPlanificacion(padreOriginal);
           //padrefinal=padreNuevo;
            DetalleTareaPlanificacion tio=null;
            if(altoImpactoMenosPersonas)
            //if((cantidadPersonas-detalleCotizacion.getCantidadPersonas())!=0)
            {
                creaTio=true;
                tio= new DetalleTareaPlanificacion(padreOriginal);            
                tio.setCantidadPersonas( tio.getCantidadPersonas() - cantidadPersonas); //tio.cantidadPersonas  -= cantidadPersonas; 
            }
           /* if(altoImpactoCosto)
            {*/
                padreNuevo.setCantHorasNormales(cantHorasNormales);
                padreNuevo.setCantHorasAl50(cantHorasAl50);
                padreNuevo.setCantHorasAl100(cantHorasAl100);
                if(creaTio)
                {
                    tio.setCantHorasNormales(cantHorasNormales);
                    tio.setCantHorasAl50(cantHorasAl50);
                    tio.setCantHorasAl100(cantHorasAl100);
                    tio.setEspecialidad(especialidad);
                    tio.setCostoXHoraNormal(costoXHoraNormal);
                }
           /* }*/
            padreNuevo.setCantidadPersonas(cantidadPersonas);
            padreNuevo.setEspecialidad(especialidad);
            padreNuevo.setCostoXHoraNormal(costoXHoraNormal);
            DetalleSubObraXTareaModif tioCotizado= null;
            DetalleSubObraXTareaModif padreNuevoCotizado=null;
            
            //Parte cotizacionMod
            //if(altoImpactoCosto)
            {
                
                //Creo las cotizaciones de tio y padre nuevo            
                padreNuevoCotizado= new DetalleSubObraXTareaModif((DetalleSubObraXTareaModif)detalleCotizacion);
                padreNuevoCotizado.setCantidadPersonas(cantidadPersonas);
                padreNuevoCotizado.setEspecialidad(especialidad);                
                padreNuevoCotizado.setCostoXHoraNormal(costoXHoraNormal);
                padreNuevoCotizado.setCantHorasNormales(cantHorasNormales);
                padreNuevoCotizado.setCantHorasAl50(cantHorasAl50);
                padreNuevoCotizado.setCantHorasAl100(cantHorasAl100);
                      
                if(creaTio)
                {
                    tioCotizado= new DetalleSubObraXTareaModif(detalleCotizacion);
                    tioCotizado.setCantidadPersonas(tioCotizado.getCantidadPersonas() - cantidadPersonas); 
                    tioCotizado.setCantHorasNormales(cantHorasNormales);
                    tioCotizado.setCantHorasAl50(cantHorasAl50);
                    tioCotizado.setCantHorasAl100(cantHorasAl100);
                    tioCotizado.setEspecialidad(especialidad);                
                    tioCotizado.setCostoXHoraNormal(costoXHoraNormal);
                }

                int indiceDetalleCotizadoPadre=tareaConCotizacion.getTareaCotizada().getDetallesMod().indexOf(detalleCotizacion);
                if(indiceDetalleCotizadoPadre!=-1)            
                {
                    if(creaTio)
                    {
                        tareaConCotizacion.getTareaCotizada().agreagarDetalle(tioCotizado, (indiceDetalleCotizadoPadre+1));
                    }       
                    tareaConCotizacion.getTareaCotizada().agreagarDetalle(padreNuevoCotizado, (indiceDetalleCotizadoPadre+1) );
                }
                else
                {
                    throw new Exception("Error en el indice de tareas - indiceDetalleCotizadoPadre");
                }
                //Resto a lo cotizado las horas usadas para los padres nuevos
                detalleCotizacion.setCantHorasNormales(detalleCotizacion.getCantHorasNormales() - padreNuevo.getCantHorasNormales());
                detalleCotizacion.setCantHorasAl50(detalleCotizacion.getCantHorasAl50() - padreNuevo.getCantHorasAl50());
                detalleCotizacion.setCantHorasAl100(detalleCotizacion.getCantHorasAl100() - padreNuevo.getCantHorasAl100());
                if(detalleCotizacion.getCantHorasNormales()<0.0)
                {detalleCotizacion.setCantHorasNormales(0.0);}
                if(detalleCotizacion.getCantHorasAl50()<0.0)
                {detalleCotizacion.setCantHorasAl50(0.0);}
                if(detalleCotizacion.getCantHorasAl100()<0.0)
                {detalleCotizacion.setCantHorasAl100(0.0);}               
                
                
                detalleCotizacion=padreNuevoCotizado;
            }
            //Resto las horas sobrantes al padre
            padreOriginal.setCantHorasNormales(padreOriginal.getCantHorasNormales() - padreNuevo.getCantHorasNormales());
            padreOriginal.setCantHorasAl50(padreOriginal.getCantHorasAl50() - padreNuevo.getCantHorasAl50());
            padreOriginal.setCantHorasAl100(padreOriginal.getCantHorasAl100() - padreNuevo.getCantHorasAl100());          
            if(padreOriginal.getCantHorasNormales()<0.0)
            {padreOriginal.setCantHorasNormales(0.0);}
            if(padreOriginal.getCantHorasAl50()<0.0)
            {padreOriginal.setCantHorasAl50(0.0);}
            if(padreOriginal.getCantHorasAl100()<0.0)
            {padreOriginal.setCantHorasAl100(0.0);} 
            
            
            int indiceDetallePadre=tareaConDetallePadre.getDetalles().indexOf(padreOriginal);
            
            if(padreOriginal.getCantidadHijos()==0 && padreOriginal.getCantHorasNormales()==0.0 && padreOriginal.getCantHorasAl50()==0.0 && padreOriginal.getCantHorasAl100()==0.0)
            {
                tareaConDetallePadre.getDetalles().remove(padreOriginal);
            }
            if(indiceDetallePadre!=-1)            
            {
                tareaConDetallePadre.agreagarDetalle(padreNuevo, indiceDetallePadre);
                
                if(creaTio)
                {
                    tareaConDetallePadre.agreagarDetalle(tio, indiceDetallePadre);
                }
            }
            else
            {
                throw new Exception("Error en el indice de tareas - tareaConDetallePadre");
            }
            
            
            
            //indiceTareaConDetallePadre=caminoDeTareas.indexOf(tareaConDetallePadre);
             
            if (indiceTareaConDetallePadre == -1) {
                throw new Exception("Error en el indice de tareas - 1");
            } else if (!(caminoDeTareas.get(0).equals(tareaConCotizacion))) {
                throw new Exception("Error en el indice de tareas - 2");
            }
            DetalleTareaPlanificacion tioAncestroViejo = null;
            DetalleTareaPlanificacion padreNuevoAncestroViejo = null;
            //DetalleTareaPlanificacion padreAncestroViejo=padreOriginal;
            //if (caminoTareas.size()>1) 
            //{
            for (int i = 0; i < indiceTareaConDetallePadre; i++) {

                DetalleTareaPlanificacion padreNuevoAncestro = new DetalleTareaPlanificacion(padreNuevo);
                padreNuevoAncestro.setCantHorasNormales(0.0);
                padreNuevoAncestro.setCantHorasAl50(0.0);
                padreNuevoAncestro.setCantHorasAl100(0.0);

                caminoDeTareas.get(i).addDetalle(padreNuevoAncestro);

                DetalleTareaPlanificacion tioAncestro = null;
                if (creaTio) {
                    tioAncestro = new DetalleTareaPlanificacion(tio);
                    tioAncestro.setCantHorasNormales(0.0);
                    tioAncestro.setCantHorasAl50(0.0);
                    tioAncestro.setCantHorasAl100(0.0);

                    caminoDeTareas.get(i).addDetalle(tioAncestro);
                }

                if (i == 0) {
                    if (caminoDeTareas.get(i).equals(tareaConCotizacion)) {

                        /*
                         * if(altoImpactoCosto) {
                         * padreNuevoAncestro.setCotizado(padreNuevoCotizado);
                         * if(creaTio) { tioAncestro.setCotizado(tioCotizado); }
                         * } else
                                {
                         */
                        padreNuevoAncestro.setCotizado(detalleCotizacion);
                        if (creaTio) {
                            tioAncestro.setCotizado(detalleCotizacion);
                        }
                        /*
                         * }
                         */
                    } else {
                        throw new Exception("Error en el indice de tareas - tareaConCotizacion");
                    }
                }

                padreNuevoAncestro.setearPadre(padreNuevoAncestroViejo);
                padreNuevoAncestroViejo = padreNuevoAncestro;
                if (creaTio) {
                    tioAncestro.setearPadre(tioAncestroViejo);
                    tioAncestroViejo = tioAncestro;
                }
            }
            //}
            if (creaTio) {
                tio.setearPadre(tioAncestroViejo);
            }
            padreNuevo.setearPadre(padreNuevoAncestroViejo);

            detalleActual.setearPadre(padreNuevo);


            //////////////
            
            
            
            if(altoImpactoMenosPersonas)
            {
                //copiaDetallePadre=padreNuevo;
                copiaDetallePadre_tio=tio;
            }
            
            
           padreOriginal=padreNuevo;
        }
        
        if ((!devolverHorasATareaSuperior) && isModificacion())
        {
            detalleCotizacion.setCantHorasNormales(detalleCotizado.getCantHorasNormales()+((cantHorasNormales-cantHsNormalesOriginal)));
            detalleCotizacion.setCantHorasAl50(detalleCotizado.getCantHorasAl50()+((cantHorasAl50-cantHs50Original)));
            detalleCotizacion.setCantHorasAl100(detalleCotizado.getCantHorasAl100()+((cantHorasAl100-cantHs100Original)));
        }
        else
        {
            if( padreOriginal.getCantHorasNormales() <(cantHorasNormales-cantHsNormalesOriginal))
            {
                detalleCotizacion.setCantHorasNormales(detalleCotizado.getCantHorasNormales()+((cantHorasNormales-cantHsNormalesOriginal)-padreOriginal.getCantHorasNormales()));
                padreOriginal.setCantHorasNormales(0.0);
            }
            else
            {
                padreOriginal.setCantHorasNormales(padreOriginal.getCantHorasNormales() - (cantHorasNormales-cantHsNormalesOriginal)); 
            }

            if( padreOriginal.getCantHorasAl50() <(cantHorasAl50-cantHs50Original)) 
            {
                detalleCotizacion.setCantHorasAl50(detalleCotizado.getCantHorasAl50()+((cantHorasAl50-cantHs50Original)-padreOriginal.getCantHorasAl50()));
                padreOriginal.setCantHorasAl50(0.0);
            }
            else
            {
                padreOriginal.setCantHorasAl50(padreOriginal.getCantHorasAl50() - (cantHorasAl50-cantHs50Original));
            }

            if( padreOriginal.getCantHorasAl100() < (cantHorasAl100-cantHs100Original))
            {
                detalleCotizacion.setCantHorasAl100(detalleCotizado.getCantHorasAl100()+((cantHorasAl100-cantHs100Original)-padreOriginal.getCantHorasAl100()));
                padreOriginal.setCantHorasAl100(0.0);
            }
            else
            {
            padreOriginal.setCantHorasAl100(padreOriginal.getCantHorasAl100() - (cantHorasAl100-cantHs100Original));
            }
          
        }
        
        ////////////////////////////////////////////
        DetalleTareaPlanificacion padreNuevoAncestroViejo2=padreOriginal;
        for (int i = indiceTareaConDetallePadre+1; i < caminoDeTareas.size(); i++) 
        {
            if(i!=caminoDeTareas.size()-1)
            {  DetalleTareaPlanificacion padreNuevoAncestro2 = new DetalleTareaPlanificacion(padreOriginal);
                padreNuevoAncestro2.setCantHorasNormales(0.0);
                padreNuevoAncestro2.setCantHorasAl50(0.0);
                padreNuevoAncestro2.setCantHorasAl100(0.0);

                caminoDeTareas.get(i).addDetalle(padreNuevoAncestro2);


                padreNuevoAncestro2.setearPadre(padreNuevoAncestroViejo2);
                padreNuevoAncestroViejo2 = padreNuevoAncestro2;
            }
            else
            {
               detalleActual.setearPadre(padreNuevoAncestroViejo2); 
            }
        }
        
        ////////////////////////////////////////////
        
        detalleActual.setCantHorasNormales(cantHorasNormales);
        detalleActual.setCantHorasAl50(cantHorasAl50);
        detalleActual.setCantHorasAl100(cantHorasAl100);
        detalleActual.setCostoXHoraNormal(costoXHoraNormal);
        detalleActual.setCantidadPersonas(cantidadPersonas);
        detalleActual.setEspecialidad(especialidad);
       
    }
     
     public void impactarDatosDetalleNoIncluidosEnCotizacion(List<TareaPlanificacion> caminoDeTareas, int cantidadPersonas,double cantHorasNormales, double cantHorasAl50, double cantHorasAl100, double costoXHoraNormal, Especialidad especialidad, TareaPlanificacion tareaConCotizacion, DetalleTareaPlanificacion detalleActual) throws Exception
    {
        if(especialidad==null)
        {
            throw new Exception("La la especialidad no puede ser null");
        }
        DetalleSubObraXTareaModif detalleCotizacion=detalleActual.getCotizado();
        
        //
               
        if(!isModificacion() || detalleActual.getCantidadHijos()==0)
        { 
           if(detalleCotizacion==null)
            {
                detalleCotizacion=estructuraNuevoDetalleNoCotizadoSubtarea(caminoDeTareas,  detalleActual, tareaConCotizacion);
                ///tareaConCotizacion.getTareaCotizada().agreagarDetalle(detalleCotizacion);
            }
            if(this.isTareaHijaDePlanificacion())
            {
            int indicedetalle=tareaConCotizacion.getDetalles().indexOf(detalleActual);
                if(indicedetalle!=-1)
                {
                   // tareaConCotizacion.getDetalles().remove(indicedetalle);
                    //tareaConCotizacion.getDetalles().add(indicedetalle, detalleActual);
                }
                else
                {
                    tareaConCotizacion.addDetalle(detalleActual);
                }
            }
            //SubTAREA
            else
            {
                detalleActual.setCantHorasNormales(cantHorasNormales);
                detalleActual.setCantHorasAl50(cantHorasAl50);
                detalleActual.setCantHorasAl100(cantHorasAl100);
                detalleActual.setCostoXHoraNormal(costoXHoraNormal);
                detalleActual.setCantidadPersonas(cantidadPersonas);
                detalleActual.setEspecialidad(especialidad);
                
               // detalleCotizacion=estructuraNuevoDetalleNoCotizadoSubtarea(caminoDeTareas,  detalleActual, tareaConCotizacion);
               
            }
            ////////////////////// 
        }
        else
        {
            //Detalles viejos
            detalleCotizacion.setCantHorasNormales(detalleCotizacion.getCantHorasNormales() - cantHorasNormales);
            detalleCotizacion.setCantHorasAl50(detalleCotizacion.getCantHorasAl50()-cantHorasAl50);
            detalleCotizacion.setCantHorasAl100(detalleCotizacion.getCantHorasAl100()-cantHorasAl100);
            if(detalleCotizacion.getCantHorasNormales()<0.0)
            {detalleCotizacion.setCantHorasNormales(0.0);}
            if(detalleCotizacion.getCantHorasAl50()<0.0)
            {detalleCotizacion.setCantHorasAl50(0.0);}
            if(detalleCotizacion.getCantHorasAl100()<0.0)
            {detalleCotizacion.setCantHorasAl100(0.0);} 
            
            detalleActual.setCantHorasNormales(detalleActual.getCantHorasNormales() - cantHorasNormales);
            detalleActual.setCantHorasAl50(detalleActual.getCantHorasAl50()-cantHorasAl50);
            detalleActual.setCantHorasAl100(detalleActual.getCantHorasAl100()-cantHorasAl100);
            if(detalleActual.getCantHorasNormales()<0.0)
            {detalleActual.setCantHorasNormales(0.0);}
            if(detalleActual.getCantHorasAl50()<0.0)
            {detalleActual.setCantHorasAl50(0.0);}
            if(detalleActual.getCantHorasAl100()<0.0)
            {detalleActual.setCantHorasAl100(0.0);}
            
            
            //Detalle nuevo
            detalleCotizacion =new DetalleSubObraXTareaModif();
            tareaConCotizacion.getTareaCotizada().agreagarDetalle(detalleCotizacion);
            
            DetalleTareaPlanificacion nuevoDetalleActual=new DetalleTareaPlanificacion(detalleActual);
            nuevoDetalleActual.setCotizado(detalleCotizacion);
            int indicedetalle=tareaConCotizacion.getDetalles().indexOf(detalleActual);
                if(indicedetalle!=-1)
                {
                   tareaConCotizacion.getDetalles().add(indicedetalle, nuevoDetalleActual);
                }
                else
                {
                    tareaConCotizacion.addDetalle(nuevoDetalleActual);
                }
                detalleActual=nuevoDetalleActual;                
        }
        
        
        detalleCotizacion.setCantidadPersonas(cantidadPersonas);
        detalleCotizacion.setEspecialidad(especialidad);                
        detalleCotizacion.setCostoXHoraNormal(costoXHoraNormal);
        detalleCotizacion.setCantHorasNormales(cantHorasNormales);
        detalleCotizacion.setCantHorasAl50(cantHorasAl50);
        detalleCotizacion.setCantHorasAl100(cantHorasAl100); 

        detalleActual.setCantHorasNormales(cantHorasNormales);
        detalleActual.setCantHorasAl50(cantHorasAl50);
        detalleActual.setCantHorasAl100(cantHorasAl100);
        detalleActual.setCostoXHoraNormal(costoXHoraNormal);
        detalleActual.setCantidadPersonas(cantidadPersonas);
        detalleActual.setEspecialidad(especialidad);
        
    }
     
     
     public DetalleSubObraXTareaModif estructuraNuevoDetalleNoCotizadoSubtarea(List<TareaPlanificacion> caminoDeTareas,  DetalleTareaPlanificacion detalleActual, TareaPlanificacion tareaConCotizacion) throws Exception
     {
         
       
            DetalleSubObraXTareaModif detalleCotizacion=new DetalleSubObraXTareaModif();
            tareaConCotizacion.getTareaCotizada().agreagarDetalle(detalleCotizacion);
            DetalleTareaPlanificacion padreNuevoAncestroViejo=null;
            if(tareaHijaDePlanificacion){
                detalleActual.setCotizado(detalleCotizacion);
            }
            else{
                for (int i = 0; i < caminoDeTareas.size()-1; i++) 
                {
                   DetalleTareaPlanificacion padreNuevoAncestro = new DetalleTareaPlanificacion(detalleActual);
                   padreNuevoAncestro.setCantHorasNormales(0.0);
                   padreNuevoAncestro.setCantHorasAl50(0.0);
                   padreNuevoAncestro.setCantHorasAl100(0.0);

                   caminoDeTareas.get(i).addDetalle(padreNuevoAncestro);


                   if (i == 0) {
                       if (caminoDeTareas.get(i).equals(tareaConCotizacion)) {

                           padreNuevoAncestro.setCotizado(detalleCotizacion);

                           /*
                            * }
                            */
                       } else {
                           throw new Exception("Error en el indice de tareas - tareaConCotizacion");
                       }
                   }

                   padreNuevoAncestro.setearPadre(padreNuevoAncestroViejo);
                   padreNuevoAncestroViejo = padreNuevoAncestro;                
               }
             }
            detalleActual.setearPadre(padreNuevoAncestroViejo); 
            return detalleCotizacion;
        
     }
     //////////////////////////////////////////
     
    public void guardarCambios()
    {
        
        if(!isModificacion())
        {
            gestorPadre.getTareaActual().addDetalle(detalleActual);
        }
        
        datosEnDetalleActual(detalleActual, cantHsNormales, cantHs50, cantHs100, costoDetalle, cantPersonas, especialidad);
        if(isTareaHijaDePlanificacion())
        {armarCopiaDeEstructuraTareaCotizada(detalleActual);}
        else
        {
          armarCopiaDeEstructura(detalleActual);
        }
        try
        {
            if(isTareaHijaDePlanificacion() ||  this.isDetalleNoCotizado())
            {
                impactarDatosDetalleNoIncluidosEnCotizacion(caminoTareas, cantPersonas, cantHsNormales, cantHs50, cantHs100, costoDetalle, especialidad, getTareaConCotizacion(), detalleActual);
            }
            else
            {
                impactarDatos(caminoTareas, cantPersonas, cantHsNormales, cantHs50, cantHs100, costoDetalle, especialidad, getTareaConCotizacion(), detalleConCotizacion, tareaConDetallePadre, detalleActual);
            }
            detalleActual.setEmpleados(listaEmpleadosAsignados);
        }
        catch(Exception e)
        {
            System.out.println(e);
           pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "Error interno");
           Logger.getLogger(GestorEditarTareaDetalles.class.getName()).log(Level.SEVERE, "ERROR en guardar cambios en GestotEditarTareaDetalles");
           return;
        }
        pantallaLista.inicializarVentana();
        
    }
     public boolean setearNuevoCostoPorDefectoEnRolEmpleado(int idEspecialidad, double nuevoCosto)
        {
            Session sesion;
            try
            {
                sesion = HibernateUtil.getSession();                
                HibernateUtil.beginTransaction();
                Especialidad re = (Especialidad) sesion.load(Especialidad.class,idEspecialidad);
                re.setPrecioHoraNormal(nuevoCosto);
                sesion.update(re);
                HibernateUtil.commitTransaction();
                return true;
            }
            catch (Exception ex)
            {
                LogUtil.addError("No se pudo realizar la transacciÃ³n en la actualizacion de precios");
                HibernateUtil.rollbackTransaction();
                return false;
            }
            
        }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////

    /**
     * @return the listaEmpleadosAsignados
     */
    public List<Empleado> getListaEmpleadosAsignados() {
        return listaEmpleadosAsignados;
    }

    /**
     * @param listaEmpleadosAsignados the listaEmpleadosAsignados to set
     */
    public void setListaEmpleadosAsignados(List<Empleado> listaEmpleadosAsignados) 
    {        
        this.listaEmpleadosAsignados = listaEmpleadosAsignados;
        //this.cantPersonas=listaEmpleadosAsignados.size();sdgdfgdfg
        
    }

    /**
     * @param devolverHorasATareaSuperior the devolverHorasATareaSuperior to set
     */
    public void setDevolverHorasATareaSuperior(boolean devolverHorasATareaSuperior) {
        this.devolverHorasATareaSuperior = devolverHorasATareaSuperior;
    }

    /**
     * @return the modificacion
     */
    public boolean isModificacion() {
        return modificacion;
    }

    /**
     * @return the tareaHijaDePlanificacion
     */
    public boolean isTareaHijaDePlanificacion() {
        return tareaHijaDePlanificacion;
    }

    /**
     * @param tareaHijaDePlanificacion the tareaHijaDePlanificacion to set
     */
    public void setTareaHijaDePlanificacion(boolean tareaHijaDePlanificacion) {
        this.tareaHijaDePlanificacion = tareaHijaDePlanificacion;
    }

    /**
     * @return the detalleNoCotizado
     */
    public boolean isDetalleNoCotizado() {
        return detalleNoCotizado;
    }

    /**
     * @param detalleNoCotizado the detalleNoCotizado to set
     */
    public void setDetalleNoCotizado(boolean detalleNoCotizado) {
        this.detalleNoCotizado = detalleNoCotizado;
    }

    /**
     * @return the tareaConCotizacion
     */
    public TareaPlanificacion getTareaConCotizacion() {
        return tareaConCotizacion;
    }
    
    public void eliminarDetalle(DetalleTareaPlanificacion detalle, boolean pasarHorasAlPadre) throws Exception
    {
        if(detalle.getPadre()==null&&pasarHorasAlPadre==true)
        {
            throw new Exception("ERROR: No se puede pasar horas al padre, padre inexistente");
        }
        
        if(pasarHorasAlPadre)
        {
            detalle.getPadre().setCantHorasNormales(detalle.getPadre().getCantHorasNormales()+detalle.getCantHorasNormales());
            detalle.getPadre().setCantHorasAl50(detalle.getPadre().getCantHorasAl50()+detalle.getCantHorasAl50());
            detalle.getPadre().setCantHorasAl100(detalle.getPadre().getCantHorasAl100()+detalle.getCantHorasAl100());
        }
        else
        {
            DetalleSubObraXTareaModif detalleCotizado=detalle.buscarCotizado();
            
            detalleCotizado.setCantHorasNormales(detalleCotizado.getCantHorasNormales()-detalle.getCantHorasNormales());
            detalleCotizado.setCantHorasAl50(detalleCotizado.getCantHorasAl50()-detalle.getCantHorasAl50());
            detalleCotizado.setCantHorasAl100(detalleCotizado.getCantHorasAl100()-detalle.getCantHorasAl100());
        }
        
        detalle.setCantHorasNormales(0.0);
        detalle.setCantHorasAl50(0.0);
        detalle.setCantHorasAl100(0.0);
        
        if(detalle.getCantidadHijos()==0)
        {
            detalle.borrar(caminoTareas);            
        }
    }

    
}
