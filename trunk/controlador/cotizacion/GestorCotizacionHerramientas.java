/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import controlador.planificacion.PlanificacionUtils;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.*;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.CotizacionHerramientas;
import vista.planificacion.cotizacion.EditarCotizacionModificada;

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
                    
                    String nombreAmostrar = hde.getRecursoEsp().getRecurso().getNombre()+ " " + hde.getRecursoEsp().getNombre()+" (Nro. "+hde.getNroSerie()+")";
                    
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
    
    public void AgregarHerramienta(Tupla tph, int cantHoras, double costo, String observaciones)
    {
        SubObraXHerramienta detalle =this.nuevaSubObraXHerramienta();
        
        detalle.setCantHoras(cantHoras);
        detalle.setCostoXHora(costo);
        detalle.setObservaciones(observaciones);
        
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
    
    public void AgregarHerramienta(SubObraXHerramienta herramientaEditando, Tupla tpitem, int cantHoras, double costoHora, String observaciones) {
        
        for (int i = 0; i < getSubObraActual().getHerramientas().size(); i++) {
            SubObraXHerramienta soxhde = getSubObraActual().getHerramientas().get(i);
            if(soxhde.hashCode()==herramientaEditando.hashCode())
            {
                soxhde.setCantHoras(cantHoras);
                soxhde.setCostoXHora(costoHora);
                for (int j = 0; j < bufferHerramientas.size(); j++) {
                    HerramientaDeEmpresa hde = bufferHerramientas.get(j);
                    if(hde.getId()==tpitem.getId()){
                        soxhde.setHerramienta(hde);
                    }
                }
                soxhde.setObservaciones(observaciones);
                
                llenarTablaHerramientas();
                refrescarPantallas();
            }
        }
    }    
    
    public void quitarHerramienta(NTupla ntp) 
    {
        for (int i = 0; i < getSubObraActual().getHerramientas().size(); i++) 
        {
            if(i==ntp.getId())
            {
                if(gestorPadre.pantalla.getClass().equals(EditarCotizacionModificada.class))
                {
                    GestorEditarCotizacionModificada gestor = (GestorEditarCotizacionModificada)gestorPadre;
                    
                    //obtengo la herramienta y pregunto si se puede eliminar
                    SubObraModificada subMod=((SubObraModificada)gestorPadre.getSubObraActual());
                    SubObraXHerramienta soxhm=(SubObraXHerramienta)subMod.getHerramientas().get(i);
                    
                    if(gestor.getPlanificacion()!=null && soxhm!=null)
                    {
                        boolean estaEnUso = PlanificacionUtils.estaSubObraXHerramientaEnUso(gestor.getPlanificacion(),soxhm);
                        if(estaEnUso)
                        {
                            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","<HTML><b>No</b> se puede eliminar la herramienta, está asignada a una tarea de la planificacion"); 
                            return;                            
                        }
                    }
                }                
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
                tp.setNombre(detalle.getHerramienta().getRecursoEsp().getRecurso().getNombre() + " " + detalle.getHerramienta().getRecursoEsp().getNombre()+" (Nro. "+detalle.getHerramienta().getNroSerie()+")");
                String[] data = new String[3];
//                    data[0] =  String.valueOf(detalle.getCantDias());
                    data[0] =  String.valueOf(detalle.getCantHoras());
                    data[1] =  String.valueOf(detalle.getCostoXHora());
                    DecimalFormat df = new DecimalFormat("#.00");
                    data[2] =  df.format(detalle.calcularSubtotal()).replaceAll(",",".");
                    
                tp.setData(data);
                listaFilas.add(tp);
        }
        pantalla.llenarTabla(listaFilas);        
    }

    public SubObraXHerramienta getHerramientaAgregada(NTupla ntp) {
        // Retorna por posicion en el Array, no por el Hash
        return getSubObraActual().getHerramientas().get(ntp.getId());       
    }
    
    //Tiene que ser publico para que java permita sobreescribirlo
    public SubObraXHerramienta nuevaSubObraXHerramienta()
    {
        SubObraXHerramienta soxh = new SubObraXHerramienta();
        return soxh;
    }
    
}
