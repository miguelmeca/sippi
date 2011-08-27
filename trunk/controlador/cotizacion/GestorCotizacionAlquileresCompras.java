/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.SubObra;
import modelo.SubObraXAlquilerCompra;
import modelo.TipoAlquilerCompra;
import org.hibernate.Hibernate;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionAlquileresCompras;

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
                tp.setNombre(String.valueOf(detalle.getCantidad()));
                String[] data = new String[3];
                    data[0] = detalle.getTipoAlquilerCompra().getNombre()+"-"+detalle.getDescripcion();
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
        SubObraXAlquilerCompra detalle = new SubObraXAlquilerCompra();    
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
    
}
