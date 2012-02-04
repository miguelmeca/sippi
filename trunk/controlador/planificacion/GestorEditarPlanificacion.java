/*            NTupla nt1 = new NTupla(1);
            nt1.setNombre("TestSubTarea1");
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import controlador.GestorAbstracto;
import java.util.ArrayList;
import java.util.List;
import modelo.PedidoObra;
import modelo.PlanificacionXXX;
import modelo.SubObraModificada;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.planificacion.EditarPlanificacion;

/**
 *
 * @author Administrador
 */
public class GestorEditarPlanificacion extends GestorAbstracto {

    private EditarPlanificacion _pantalla;
    private GestorArbolDeRecursos _gestorArbolRecursos;
    private Session sesion;
    private PedidoObra pedidoDeObra;
    private PlanificacionXXX planificacion;

    public GestorEditarPlanificacion(EditarPlanificacion _pantalla, int idPedidoDeObra) {
        this._pantalla = _pantalla;
        this._gestorArbolRecursos = new GestorArbolDeRecursos();
        
        try
        {
            sesion= HibernateUtil.getSession();
            
            this.pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class,idPedidoDeObra);
            this.planificacion = pedidoDeObra.getPlanificacion();              
            
        }catch(Exception e)
        {
            mostrarMensajeError("No se pudo cargar el Pedido ni la planificación asociada");
        }        
        
    }

    public List getListaSubObras() {
          
        ArrayList<NTupla> lista = new ArrayList<NTupla>();
        
        try
        {
            
             if(this.planificacion!=null && this.planificacion.getId()!=0)
             {
                 ArrayList<SubObraModificada> listaSO = (ArrayList) this.planificacion.getCotizacion().getSubObra();
                 for (int i = 0; i < listaSO.size(); i++) 
                 {
                     SubObraModificada som = listaSO.get(i);
                     NTupla nt1 = new NTupla(som.getId());
                     nt1.setNombre("xxx"+1+"xxx");
                 }
             }
               
            
        }catch(Exception e)
        {
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        }
        
        //return lista;
        return lista;

    }
    
    public List getListaTareasXSubObra(int idSubObra) {
        
        return getListaMock("Tarea");
        
    }    
    
    public List getListaMock(String tipo) {
   
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            NTupla nt1 = new NTupla(1);
            nt1.setNombre(tipo+" 1:"+(Math.random()*100));

            NTupla nt2 = new NTupla(2);
            nt2.setNombre(tipo+" 2:"+(Math.random()*100));
            
            NTupla nt3 = new NTupla(3);
            nt3.setNombre(tipo+" 3:"+(Math.random()*100));
            
            NTupla nt4 = new NTupla(4);
            nt4.setNombre(tipo+" 4:"+(Math.random()*100));            
            
            lista.add(nt1);
            lista.add(nt2);
            lista.add(nt3);
            lista.add(nt4);
            
        return lista;        
        
    }

    public void mostrarDatosGenerales(int idPedidoDeObra) {
        
        try
        {
            sesion= HibernateUtil.getSession();
            pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class,idPedidoDeObra);
            
            _pantalla.setLblObraNombre(pedidoDeObra.getNombre().substring(0,60)+"..."); 
            _pantalla.setLblObraFechaFin(FechaUtil.getFecha(pedidoDeObra.getFechaFin()));
            _pantalla.setLblObraFechaInicio(FechaUtil.getFecha(pedidoDeObra.getFechaInicio()));
            _pantalla.setLblObraLugar(pedidoDeObra.getPlanta().getDomicilio().toString());
            _pantalla.setLblObraPlanta(pedidoDeObra.getPlanta().getRazonSocial());

            if(this.planificacion!=null && this.planificacion.getCotizacion()!=null)
            {
                _pantalla.setNroPlanificacion(String.valueOf(this.planificacion.getNumeroPlanificacion()));
                _pantalla.setFechaInicioPlanif(this.planificacion.getFechaInicio());
                _pantalla.setFechaFinPlanif(this.planificacion.getFechaFin());
            }            
            
            if(this.planificacion!=null && this.planificacion.getCotizacion()!=null && this.planificacion.getCotizacion().getCotizacionOriginal()!=null)
            {
            _pantalla.setTxtNroCotizacion(this.planificacion.getCotizacion().getCotizacionOriginal().getNroCotizacion());
            _pantalla.setCotizacionMontoTotal(String.valueOf(this.planificacion.getCotizacion().getCotizacionOriginal().CalcularTotal()));
            }
            
            
        }catch(Exception e)
        {
            mostrarMensajeError("No se pudo cargar los datos generales de la cotizacion");
        }  
        
    }
    
    private void mostrarMensajeError(String msg)
    {
        System.err.println("[ERROR] "+msg);
    }
    
}
