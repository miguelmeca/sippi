/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.TipoAdicional;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionAdicionales;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionAdicionales implements IGestorCotizacion {

    private GestorEditarCotizacion gestorPadre;
    private CotizacionAdicionales pantalla;
    private ArrayList<TipoAdicional> bufferTipos;

    public GestorCotizacionAdicionales(GestorEditarCotizacion gestorPadre) 
    {
        this.gestorPadre = gestorPadre;
        bufferTipos = new ArrayList<TipoAdicional>();
    }

    public void setPantalla(CotizacionAdicionales pantalla) {
        this.pantalla = pantalla;
    }

    public void initVentana() 
    {
        llenarComboTipoAdicionales();
        llenarTablaAdicionales();
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
    public void refrescarPantallas() 
    {
        gestorPadre.refrescarPantallas();
    }

    private void llenarComboTipoAdicionales() 
    {
        // Vacio el buffer
        bufferTipos.clear();
        
        ArrayList<Tupla> listaTipos = new ArrayList<Tupla>();
        try 
        {
            Iterator<TipoAdicional> iter = HibernateUtil.getSession().createQuery("from TipoAdicional tac order by tac.nombre").iterate();
            while (iter.hasNext())
            {
                TipoAdicional tac = iter.next(); 
                    
                    // Lo agrego al buffer
                    bufferTipos.add(tac);
                    
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
    
    private void llenarTablaAdicionales()        
    {
         ArrayList<NTupla> listaFilas = new ArrayList<NTupla>();
         
         for (int i = 0; i < getSubObraActual().getAdicionales().size(); i++) 
         {
            SubObraXAdicional detalle = getSubObraActual().getAdicionales().get(i);
            
                NTupla tp = new NTupla(i);
                tp.setNombre(detalle.getTipoAdicional().getNombre()+"-"+detalle.getDescripcion());
                String[] data = new String[4];
                    data[0] =  String.valueOf(detalle.getCantOperarios());
                    data[1] =  String.valueOf(detalle.getCantDias());
                    data[2] =  String.valueOf(detalle.getPrecioUnitario());
                    data[3] =  String.valueOf(detalle.calcularSubtotal());
                tp.setData(data);
                listaFilas.add(tp);
        }
        pantalla.llenarTabla(listaFilas);
    }

    public void quitarAlquilerCompra(NTupla ntp) 
    {
        for (int i = 0; i < getSubObraActual().getAdicionales().size(); i++) 
        {
            if(i==ntp.getId())
            {
                getSubObraActual().getAdicionales().remove(i);
                llenarTablaAdicionales();
                refrescarPantallas();
                return;
            }
        }
        pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo eliminar de la lista el Alquiler/Compra");
    }

    public void AgregarAdicional(Tupla tipo, String descripcion, int cantidadOperarios, int cantidadDias, double precio) 
    {
        SubObraXAdicional detalle = new SubObraXAdicional();
        detalle.setDescripcion(descripcion);
        detalle.setCantDias(cantidadDias);
        detalle.setCantOperarios(cantidadOperarios);
        detalle.setPrecioUnitario(precio);
        
        // Busco el Tipo Alquiler Compra
        for (int i = 0; i < bufferTipos.size(); i++) 
        {
            TipoAdicional ta = bufferTipos.get(i);
            if(ta.getId()==tipo.getId())
            {
                detalle.setTipoAdicional(ta);
            }
        }
        // lo agrego
        if(detalle.getTipoAdicional()!=null)
        {
            getSubObraActual().getAdicionales().add(detalle);
            llenarTablaAdicionales();
            refrescarPantallas();
        }
        else
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo agregar el Adicional");
        }        
    }
    
    
}
