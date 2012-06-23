/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.cotizacion;


import controlador.planificacion.PlanificacionUtils;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import controlador.utiles.gestorBDvarios;
import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.*;
import util.RubroUtil;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import util.NTupla;
import util.FechaUtil;
import vista.cotizacion.CotizacionManoDeObraGeneral;
import vista.cotizacion.CotizacionManoDeObraAgregarMO;
import org.hibernate.Session;
import util.HibernateUtil;
import util.LogUtil;
import vista.planificacion.cotizacion.EditarCotizacionModificada;
//import java.util.logging.Level;
//import java.util.logging.Logger;


/**
 *
 * @author Fran
 */
public class GestorCotizacionManoDeObra implements IGestorCotizacion
{
    private GestorEditarCotizacion gestorPadre;
    private CotizacionManoDeObraGeneral pantallaGeneral;
    private CotizacionManoDeObraAgregarMO pantallaAgregarMO;
    private SubObra subObra;
    List<DetalleSubObraXTarea> cacheDetalles;

    public GestorCotizacionManoDeObra(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
        subObra=this.getSubObraActual();
    }
    

 /*public ArrayList<Tupla> mostrarRubrosCompras()
 {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        lista = RubroUtil.getTuplasRubros();
        mostroPrecios=false;
        return lista;

 }/////////////////////////////*/
 public ArrayList<NTupla> mostrarNombresTareas()
        { Session sesion;

            List<TipoTarea> tareas=null;
            try{
                sesion= HibernateUtil.getSession();
                sesion.beginTransaction();
            tareas=sesion.createQuery("from TipoTarea").list();
            sesion.getTransaction().commit();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarNombresTareas");
            return null;}
            if(tareas==null)
            {return null;}            
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < tareas.size(); i++)
            {
                TipoTarea tt = (TipoTarea)tareas.get(i);
                
                NTupla tuplaT = new NTupla(tt.getId());
                tuplaT.setNombre(tt.getNombre());
                tuplas.add(tuplaT);
             }
            return tuplas;           
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
                LogUtil.addError("No se pudo realizar la transacción en la actualizacion de precios");
                HibernateUtil.rollbackTransaction();
                return false;
            }
            
        }
        
    public double calcularSubtotal()
    {
        double monto=0.0;
        for (int j = 0; j < getSubObraActual().getTareas().size(); j++) 
        {
             monto+= getSubObraActual().getTareas().get(j).calcularSubtotal();
        }
        return monto;
    }
    
    public void getTareasDeSubObra()
    {
        List<SubObraXTarea> tareas= getSubObraActual().getTareas();
        
        for (SubObraXTarea soxt : tareas) 
        {
           pantallaGeneral.agregarTareaTabla(soxt, false, false); 
           /* Object[] datos=new Object[7];           
           NTupla tar=new NTupla(soxt.getId());
           tar.setNombre(soxt.getTipoTarea().getNombre());
           Object[] datosTarea=new Object[2];       
               Tupla tipoTar=new Tupla(soxt.getTipoTarea().getId(), soxt.getTipoTarea().getNombre());
               datosTarea[0]=tipoTar;
               datosTarea[1]=soxt.getObservaciones();//Mierda q quilombo!!!
           tar.setData(datosTarea);       
           datos[0]=(tar); 
           datos[1]=soxt.getCantOperarios(); 
           NTupla nvoRango=new NTupla(soxt.getRangoEmpleado().getId());
           nvoRango.setNombre(soxt.getRangoEmpleado().getNombre());
           nvoRango.setData(soxt.getCostoXHora());
           datos[2]=nvoRango;
           datos[3]=soxt.getCantHoras(); 
           NTupla tFI=new NTupla(0);
           tFI.setNombre(FechaUtil.getFecha(soxt.getFechaInicio()));
           tFI.setData(soxt.getFechaInicio());
           datos[4]=tFI;
           NTupla tFF=new NTupla(0);
           tFF.setNombre(FechaUtil.getFecha(soxt.getFechaFin()));
           tFF.setData(soxt.getFechaFin());
           datos[5]=tFF;
           datos[6]=soxt.calcularSubtotal();
           pantallaGeneral.agregarTareaTabla(datos, false, false); */
        }
    }
    public void eliminarTarea(int id)
    {
        if(gestorPadre.pantalla.getClass().equals(EditarCotizacionModificada.class))
        {
            GestorEditarCotizacionModificada gestor = (GestorEditarCotizacionModificada)gestorPadre;

            //obtengo la herramienta y pregunto si se puede eliminar
            SubObraModificada subMod=((SubObraModificada)gestorPadre.getSubObraActual());
            SubObraXTareaModif soxtm=(SubObraXTareaModif)subMod.getSubObraXTareaPorHash(id);
            System.out.println("asd");
            
            if(gestor.getPlanificacion()!=null && soxtm!=null){
                boolean estaEnUso = PlanificacionUtils.estaSubObraXTareaEnUso(gestor.getPlanificacion(),soxtm);
                if(estaEnUso){
                    pantallaGeneral.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","<HTML><b>No</b> se puede eliminar la Tarea, está asignada a una tarea de la planificacion"); 
                }
                else{
                    getSubObraActual().eliminarTarea(id);
                    refrescarPantallas();
                }
            }
        } 
    }
    
    public boolean agregarTarea(SubObraXTarea tarea)
    {
        Session sesion;
        
       // boolean esTareaNueva;
        try
        {
            sesion = HibernateUtil.getSession();
            
            if(tarea.getId()>0)
            {//Es una tarea modificada
              // esTareaNueva=false;
               List<SubObraXTarea> tareas= getSubObraActual().getTareas();        
               
                boolean codigoInalzanzableAlcanzado=true;
                for (SubObraXTarea soxtAux : tareas) 
                {
                    if(soxtAux.getId()==tarea.getId())
                    {
                        soxtAux=tarea;
                        codigoInalzanzableAlcanzado=false;
                        break;
                    }
                }
                if(codigoInalzanzableAlcanzado)
                {
                    Exception eCIA=new Exception("ERROR:Id de tarea inexistente");
                    throw eCIA; 
                }
            }
            else
            {
               // esTareaNueva=true;
                /*SubObraXTarea soxt;
                soxt=new SubObraXTarea();
                TipoTarea tt=(TipoTarea) sesion.load(TipoTarea.class, ((Tupla)((Object[])((NTupla)datos[0]).getData())[0]).getId()  );
                soxt.setTipoTarea(tt);
                soxt.setObservaciones(  (String)(((Object[])((NTupla)datos[0]).getData())[1])  );
                soxt.setCantOperarios((Integer)datos[1]);
                RangoEmpleado re = (RangoEmpleado) sesion.load(RangoEmpleado.class, ((NTupla)datos[2]).getId());
                soxt.setRangoEmpleado(re);
                soxt.setCantHoras((Double)datos[3]);
                soxt.setCostoXHora((Double)((NTupla)datos[2]).getData());
                soxt.setFechaInicio((Date)((NTupla)datos[4]).getData());
                soxt.setFechaFin((Date)((NTupla)datos[5]).getData());
                */
                getSubObraActual().addTarea(tarea);
            }
           /** if(esTareaNueva)
            {
              getSubObraActual().addTarea(soxt); 
            }*/
            
            
            refrescarPantallas();
            //return soxt.getId();
            return true;
        }
        catch (Exception ex)
            {
                LogUtil.addError("ERROR abriendo sesion en gestor.agregarTarea: "+ex);
                System.out.println("ERROR abriendo sesion en gestor.agregarTarea: "+ex);
                HibernateUtil.rollbackTransaction();
                //return -1;
                return false;
            }
    }
    
    public DetalleSubObraXTarea crearDetalleTarea(double hsNormales, double hs50,double hs100, int cantidadPersonas, double costoNormal, int idEspecialidad)
    {
       DetalleSubObraXTarea detalleNuevo=crearDetalleVacio();
        detalleNuevo.setCantHorasNormales(hsNormales);
        detalleNuevo.setCantHorasAl50(hs50);
        detalleNuevo.setCantHorasAl50(hs50);
        detalleNuevo.setCantHorasAl100(hs100);
        detalleNuevo.setCantidadPersonas(cantidadPersonas );
        detalleNuevo.setCostoXHoraNormal(costoNormal);
        /*RangoEspecialidad rangoEmpleado=levantarRangoEmpleado(idRangoEmpleado);        
        detalleNuevo.setRangoEmpleado(rangoEmpleado); 
        TipoEspecialidad tipoEspecialidad=levantarTipoEspecialidad(idTipoEspecialidad);        
        detalleNuevo.setTipoEspecialidad(tipoEspecialidad); */
        Especialidad especialidad=levantarEspecialidad(idEspecialidad);  
        detalleNuevo.setEspecialidad(especialidad);
        
        return detalleNuevo;    
    }
    private DetalleSubObraXTarea crearDetalleVacio()
    {
        return new DetalleSubObraXTarea();
    }
    
    public Especialidad levantarEspecialidad(int idEspecialidad)
    {
       Session sesion;
        Especialidad te=null;
        try
        {
            sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            te = (Especialidad) sesion.load(Especialidad.class, (idEspecialidad));
            HibernateUtil.commitTransaction();
            
        }
        catch (Exception ex)            
        {   System.out.println("No se ejecutar la consulta en levantarEspecialidad(id)"); 
            HibernateUtil.rollbackTransaction();
        }
        return te;
    }
   /* public RangoEspecialidad levantarRangoEmpleado(int idRango)
    {
       Session sesion;
        RangoEspecialidad re=null;
        try
        {
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            re = (RangoEspecialidad) sesion.load(RangoEspecialidad.class, (idRango));
            sesion.getTransaction().commit();
            
        }
        catch (Exception ex)            
        {   System.out.println("No se ejecutar la consulta en levantarRangoEmpleado(id)"); 
        }
        return re;
    }
    
    public TipoEspecialidad levantarTipoEspecialidad(int idTipoEspecialidad)
    {
       Session sesion;
        TipoEspecialidad te=null;
        try
        {
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            te = (TipoEspecialidad) sesion.load(TipoEspecialidad.class, (idTipoEspecialidad));
            sesion.getTransaction().commit();
            
        }
        catch (Exception ex)            
        {   System.out.println("No se ejecutar la consulta en levantarRangoEmpleado(id)"); 
        }
        return te;
    }*/
    
    public TipoTarea levantarTipoTarea(int idTipoTarea)
    {
       Session sesion;
        TipoTarea tt=null;
        try
        {
            sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            tt = (TipoTarea) sesion.load(TipoTarea.class, (idTipoTarea));
            HibernateUtil.commitTransaction();
            
        }
        catch (Exception ex)
        {   System.out.println("No se ejecutar la consulta en levantarTipoTarea(id)");
            HibernateUtil.rollbackTransaction();
        }
        return tt;
    }
////////////////////////////

    /**
     * @return the pantallaGeneral
     */
    public CotizacionManoDeObraGeneral getPantallaGeneral() {
        return pantallaGeneral;
    }

    /**
     * @param pantallaGeneral the pantallaGeneral to set
     */
    public void setPantalla(CotizacionManoDeObraGeneral pantallaGeneral) {
        this.pantallaGeneral = pantallaGeneral;
    }

    /**
     * @return the pantallaAgregarMO
     */
    public CotizacionManoDeObraAgregarMO getPantallaAgregarMO() {
        return pantallaAgregarMO;
    }

    /**
     * @param pantallaAgregarMO the pantallaAgregarMO to set
     */
    public void setPantallaAgregarMO(CotizacionManoDeObraAgregarMO pantallaAgregarMO) {
        this.pantallaAgregarMO = pantallaAgregarMO;
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
    
 
}