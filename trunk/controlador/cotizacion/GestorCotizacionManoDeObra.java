/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.cotizacion;


import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;
import modelo.Cotizacion;
import modelo.SubObra;
import modelo.SubObraXTarea;
import modelo.TipoTarea;
import modelo.RangoEmpleado;
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
//import java.util.logging.Level;
//import java.util.logging.Logger;


/**
 *
 * @author Emmanuel
 */
public class GestorCotizacionManoDeObra implements IGestorCotizacion
{
    private GestorEditarCotizacion gestorPadre;
    private CotizacionManoDeObraGeneral pantallaGeneral;
    private CotizacionManoDeObraAgregarMO pantallaAgregarMO;
    private SubObra subObra;
    //ArrayList<PrecioSegunCantidad> listaPrecios;

    public GestorCotizacionManoDeObra(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
        subObra=getSubObraActual();
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
            tareas=sesion.createQuery("from TipoTarea").list();
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
                Object[] datos=new Object[3];
                datos[0]=Double.toString(tareas.get(i).getCantHorasPredeterminada());                
                datos[1]=Integer.toString(tareas.get(i).getCantOperariosPredeterminada());
                
                    RangoEmpleado rangoEmp=tareas.get(i).getRangoEmpleadoPredeterminado();
                    /*=NTupla tuplaR = new NTupla(rangoEmp.getId());
                    tuplaR.setNombre(rangoEmp.getNombre());
                    tuplaR.setData(rangoEmp.getCostoXHora());                    
                datos[2]=tuplaR;*/
                datos[2]=rangoEmp.getId();
                tuplaT.setData(datos);
                tuplas.add(tuplaT);
             }
            return tuplas;           
        }
 public ArrayList<NTupla> mostrarRangos()
        { Session sesion;

            List<RangoEmpleado> rangos=null;
            try{
                sesion= HibernateUtil.getSession();
                rangos=sesion.createQuery("from RangoEmpleado").list();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarRangos()");
            return null;}
            if(rangos==null)
            {return null;}           
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < rangos.size(); i++)
            {
                RangoEmpleado re = (RangoEmpleado)rangos.get(i);
                
                NTupla nTupla = new NTupla(re.getId());
                nTupla.setNombre(re.getNombre());
                nTupla.setData(re.getCostoXHora());
                    tuplas.add(nTupla);
             }
            return tuplas;           
        }
        public boolean setearNuevoCostoPorDefectoEnRolEmpleado(int idRangoEmpleado, double nuevoCosto)
        {
            Session sesion;
            try
            {
                sesion = HibernateUtil.getSession();                
                HibernateUtil.beginTransaction();
                RangoEmpleado re = (RangoEmpleado) sesion.load(RangoEmpleado.class,idRangoEmpleado);
                re.setCostoXHora(nuevoCosto);
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
        for (int j = 0; j < subObra.getTareas().size(); j++) 
        {
             monto+= subObra.getTareas().get(j).calcularSubtotal();
        }
        return monto;
    }
    public void getTareasDeSubObra()
    {
        List<SubObraXTarea> tareas= subObra.getTareas();
        
        for (SubObraXTarea soxt : tareas) 
        {
           Object[] datos=new Object[7];           
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
           pantallaGeneral.agregarTarea(datos, false, false); 
        }
    }
    public void eliminarTarea(int id)
    {
        subObra.eliminarTarea(id);
        refrescarPantallas();
    }
    
    public boolean agregarTarea(Object[] datos)
    {
        Session sesion;
        SubObraXTarea soxt;
        boolean tareaNueva;
        try
        {
            sesion = HibernateUtil.getSession();
            soxt=new SubObraXTarea();
            if(((NTupla)datos[0]).getId()>0)
            {
               tareaNueva=false;
               List<SubObraXTarea> tareas= subObra.getTareas();
        
               
                boolean codigoInalzanzableAlcanzado=true;
                for (SubObraXTarea soxtAux : tareas) 
                {
                    if(soxtAux.getId()==((NTupla)datos[0]).getId())
                    {
                        soxt=soxtAux;
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
            {tareaNueva=true;}
                      
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
            
            if(tareaNueva)
            {
               subObra.addTarea(soxt); 
            }
            
            
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