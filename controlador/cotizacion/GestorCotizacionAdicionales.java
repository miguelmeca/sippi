/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.*;
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
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista de Gastos Varios");
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
                String[] data = new String[3];
                    data[0] =  String.valueOf(detalle.getCantidad());
                    data[1] =  String.valueOf(detalle.getPrecioUnitario());
                    data[2] =  String.valueOf(detalle.calcularSubtotal());
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
        pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo eliminar de la lista el Gasto General");
    }

    public void AgregarAdicional(Tupla tipo, String descripcion, int cantidad, double precio) 
    {
        SubObraXAdicional detalle = this.nuevaSubObraXAdicional();
        detalle.setDescripcion(descripcion);
        detalle.setCantidad(cantidad);
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
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo agregar el Gasto General");
        }        
    }
    
    public void AgregarAdicional(SubObraXAdicional editando, Tupla tipo, String descripcion, int cantidad, double precio) {
        for (int i = 0; i < getSubObraActual().getAdicionales().size(); i++) {
            SubObraXAdicional soxhde = getSubObraActual().getAdicionales().get(i);
            if(soxhde.hashCode()==editando.hashCode())
            {
                soxhde.setCantidad(cantidad);
                soxhde.setDescripcion(descripcion);
                soxhde.setPrecioUnitario(precio);

                for (int j = 0; j < bufferTipos.size(); j++) {
                    TipoAdicional ta = bufferTipos.get(j);
                    if(ta.getId()==tipo.getId()){
                        soxhde.setTipoAdicional(ta);
                    }
                }
                
                llenarTablaAdicionales();
                refrescarPantallas();
            }
        }
    }    

    public SubObraXAdicional getAdicionalAgregada(NTupla ntp) {
        // Retorna por posicion en el Array, no por el Hash
        return getSubObraActual().getAdicionales().get(ntp.getId());   
    }

//Tiene que ser publico para que java permita sobreescribirlo
    public SubObraXAdicional nuevaSubObraXAdicional()
    {
        SubObraXAdicional soxa = new SubObraXAdicional();
        return soxa;
    }
    
}
