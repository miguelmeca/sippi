/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.HerramientaDeEmpresa;
import modelo.SubObra;
import modelo.SubObraXHerramienta;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionHerramientas;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionHerramientas implements IGestorCotizacion{
    
    protected GestorEditarCotizacion gestorPadre;
    protected CotizacionHerramientas pantalla;
    protected ArrayList<HerramientaDeEmpresa> bufferHerramientas;

    public GestorCotizacionHerramientas(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
        bufferHerramientas = new ArrayList<HerramientaDeEmpresa>();
    }

    public void setPantalla(CotizacionHerramientas pantalla) {
        this.pantalla = pantalla;
        llenarComboHerramientas();
        llenarTablaHerramientas();
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

    private void llenarComboHerramientas() 
    {
        // Vacio el buffer
        bufferHerramientas.clear();
        
        ArrayList<Tupla> listaTipos = new ArrayList<Tupla>();
        try 
        {
            Iterator<HerramientaDeEmpresa> iter = HibernateUtil.getSession().createQuery("from HerramientaDeEmpresa hde order by hde.id").iterate();
            while (iter.hasNext())
            {
                HerramientaDeEmpresa hde = iter.next(); 
                    
                    // Lo agrego al buffer
                    bufferHerramientas.add(hde);
                    
                    String nombreAmostrar = hde.getRecursoEsp().getNombre()+" "+hde.getRecursoEsp().getRecurso().getNombre()+" ("+hde.getNroSerie()+")";
                    
                    // lo agrego a la lista
                    Tupla tp = new Tupla(hde.getId(),nombreAmostrar);
                    listaTipos.add(tp);
            }
            pantalla.llenarComboHerramientas(listaTipos);
        } 
        catch (Exception ex) 
        {
           ex.printStackTrace();
           pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista de Tipos de Alquileres / Compras");
        }
    }
    
    public void AgregarHerramienta(Tupla tph, int cantDias, int cantHoras, double costo)
    {
        SubObraXHerramienta detalle = new SubObraXHerramienta();
        
        detalle.setCantDias(cantDias);
        detalle.setCantHoras(cantHoras);
        detalle.setCostoXHora(costo);
        
        for (int i = 0; i < bufferHerramientas.size(); i++) 
        {
            HerramientaDeEmpresa hde = bufferHerramientas.get(i);
            if(hde.getId()==tph.getId())
            {
                detalle.setHerramienta(hde);
            }
        }
        
        if(detalle.getHerramienta()!=null)
        {
            getSubObraActual().getHerramientas().add(detalle);
            llenarTablaHerramientas();
            refrescarPantallas();
        }
        else
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo agregar la Herramienta");
        }          
    }
    
    public void quitarHerramienta(NTupla ntp) 
    {
        for (int i = 0; i < getSubObraActual().getHerramientas().size(); i++) 
        {
            if(i==ntp.getId())
            {
                getSubObraActual().getHerramientas().remove(i);
                llenarTablaHerramientas();
                refrescarPantallas();
                return;
            }
        }
        pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo eliminar de la lista la herramienta");
    }

    private void llenarTablaHerramientas() 
    {
         ArrayList<NTupla> listaFilas = new ArrayList<NTupla>();
         
         for (int i = 0; i < getSubObraActual().getHerramientas().size(); i++) 
         {
            SubObraXHerramienta detalle = getSubObraActual().getHerramientas().get(i);
            
                NTupla tp = new NTupla(i);
                tp.setNombre(detalle.getHerramienta().getRecursoEsp().getNombre()+" "+detalle.getHerramienta().getRecursoEsp().getRecurso().getNombre()+" ("+detalle.getHerramienta().getNroSerie()+")");
                String[] data = new String[4];
                    data[0] =  String.valueOf(detalle.getCantDias());
                    data[1] =  String.valueOf(detalle.getCantHoras());
                    data[2] =  String.valueOf(detalle.getCostoXHora());
                    data[3] =  String.valueOf(detalle.calcularSubtotal());
                tp.setData(data);
                listaFilas.add(tp);
        }
        pantalla.llenarTabla(listaFilas);        
    }
    
}
