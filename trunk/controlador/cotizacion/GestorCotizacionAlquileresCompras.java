/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import controlador.planificacion.PlanificacionUtils;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import org.hibernate.Hibernate;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionAlquileresCompras;
import vista.planificacion.cotizacion.EditarCotizacionModificada;

/**
 * @author Iuga
 */
public class GestorCotizacionAlquileresCompras implements IGestorCotizacion {
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionAlquileresCompras pantalla;
    
    private ArrayList<TipoAlquilerCompra> bufferTiposAlquileresCompras;

    public GestorCotizacionAlquileresCompras(GestorEditarCotizacion gestorPadre) 
    {
        this.gestorPadre = gestorPadre;
        bufferTiposAlquileresCompras = new ArrayList<TipoAlquilerCompra>();
    }

    public void setPantalla(CotizacionAlquileresCompras pantalla) {
        this.pantalla = pantalla;
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

    public void initVentana() 
    {
        llenarComboTipoAlquilerCompra();
        llenarTablaAlquilerCompra();    
    }

    private void llenarComboTipoAlquilerCompra() 
    {
        // Vacio el buffer
        bufferTiposAlquileresCompras.clear();
        
        ArrayList<Tupla> listaTipos = new ArrayList<Tupla>();
        try 
        {
            Iterator<TipoAlquilerCompra> iter = HibernateUtil.getSession().createQuery("from TipoAlquilerCompra tac order by tac.nombre").iterate();
            while (iter.hasNext())
            {
                TipoAlquilerCompra tac = iter.next(); 
                    
                    // Lo agrego al buffer
                    bufferTiposAlquileresCompras.add(tac);
                    
                    // lo agrego a la lista
                    Tupla tp = new Tupla(tac.getId(),tac.getNombre());
                    listaTipos.add(tp);
            }
            pantalla.llenarComboTipos(listaTipos);
        } 
        catch (Exception ex) 
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista de Tipos de Alquileres / Compras");
        }
        
    }
    
    private void llenarTablaAlquilerCompra()        
    {
         ArrayList<NTupla> listaFilas = new ArrayList<NTupla>();
         
         for (int i = 0; i < getSubObraActual().getAlquileresCompras().size(); i++) 
         {
            SubObraXAlquilerCompra detalle = getSubObraActual().getAlquileresCompras().get(i);
            
                NTupla tp = new NTupla(i);
                tp.setNombre(detalle.getTipoAlquilerCompra().getNombre()+"-"+detalle.getDescripcion());
                String[] data = new String[3];
                    data[0] = String.valueOf(detalle.getCantidad());
                    data[1] =  String.valueOf(detalle.getPrecioUnitario());
                    data[2] =  String.valueOf(detalle.calcularSubtotal());
                tp.setData(data);
                listaFilas.add(tp);
        }
        pantalla.llenarTabla(listaFilas);
    }

    public void quitarAlquilerCompra(NTupla ntp) 
    {
        for (int i = 0; i < getSubObraActual().getAlquileresCompras().size(); i++) 
        {
            if(i==ntp.getId())
            {
                if(gestorPadre.pantalla.getClass().equals(EditarCotizacionModificada.class))
                {
                    GestorEditarCotizacionModificada gestor = (GestorEditarCotizacionModificada)gestorPadre;
                    
                    //obtengo el alquiler/compra y pregunto si se puede eliminar
                    SubObraModificada subMod =((SubObraModificada)gestorPadre.getSubObraActual());
                    SubObraXAlquilerCompra soxac =(SubObraXAlquilerCompra)subMod.getAlquileresCompras().get(i);
                    
                    if(gestor.getPlanificacion()!=null && soxac!=null)
                    {
                        boolean estaEnUso = PlanificacionUtils.estaSubObraXAlquilerCompraEnUso(gestor.getPlanificacion(),soxac);
                        if(estaEnUso)
                        {
                            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","<HTML><b>No</b> se puede eliminar el alquiler/compra, está asignado a una tarea de la planificacion"); 
                            return;
                        }
                    }
                } 
                
                getSubObraActual().getAlquileresCompras().remove(i);
                llenarTablaAlquilerCompra();
                refrescarPantallas();
                return;
            }
        }
        pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo eliminar de la lista el Alquiler/Compra");
    }

    public void AgregarCompraAlquiler(Tupla tipo, String descripcion, int cantidad, double precio) 
    {
        // Creo el objeto
        SubObraXAlquilerCompra detalle = this.nuevaSubObraXAlquilerCompra();    
        detalle.setCantidad(cantidad);
        detalle.setDescripcion(descripcion);
        detalle.setPrecioUnitario(precio);        
        // Busco el Tipo Alquiler Compra
        for (int i = 0; i < bufferTiposAlquileresCompras.size(); i++) 
        {
            TipoAlquilerCompra tac = bufferTiposAlquileresCompras.get(i);
            if(tac.getId()==tipo.getId())
            {
                detalle.setTipoAlquilerCompra(tac);
            }
        }
        
        // lo agrego
        if(detalle.getTipoAlquilerCompra()!=null)
        {
            getSubObraActual().getAlquileresCompras().add(detalle);
            llenarTablaAlquilerCompra();
            refrescarPantallas();
        }
        else
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo agregar el Alquiler/Compra a la lista");
        }
      
        
    }

    @Override
    public void refrescarPantallas() 
    {
        gestorPadre.refrescarPantallas();
    }

    public SubObraXAlquilerCompra getAlquilerCompraAgregada(NTupla ntp) {
        // Retorna por posicion en el Array, no por el Hash
        return getSubObraActual().getAlquileresCompras().get(ntp.getId()); 
    }

    public void AgregarCompraAlquiler(SubObraXAlquilerCompra editando, Tupla tipo, String descripcion, int cantidad, double precio) {
        for (int i = 0; i < getSubObraActual().getAlquileresCompras().size(); i++) {
            SubObraXAlquilerCompra soxhde = getSubObraActual().getAlquileresCompras().get(i);
            if(soxhde.hashCode()==editando.hashCode())
            {
                soxhde.setCantidad(cantidad);
                soxhde.setDescripcion(descripcion);
                soxhde.setPrecioUnitario(precio);
                
                for (int j = 0; j < bufferTiposAlquileresCompras.size(); j++) {
                    TipoAlquilerCompra hde = bufferTiposAlquileresCompras.get(j);
                    if(hde.getId()==tipo.getId()){
                        soxhde.setTipoAlquilerCompra(hde);
                    }
                }
                
                llenarTablaAlquilerCompra();
                refrescarPantallas();
            }
        }
    }
    
    //Tiene que ser publico para que java permita sobreescribirlo
    public SubObraXAlquilerCompra nuevaSubObraXAlquilerCompra()
    {
        SubObraXAlquilerCompra soxac = new SubObraXAlquilerCompra();
        return soxac;
    }
    
}
