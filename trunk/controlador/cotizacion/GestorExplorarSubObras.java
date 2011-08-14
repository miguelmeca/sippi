/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.Iterator;
import modelo.Cotizacion;
import modelo.PedidoObra;
import modelo.SubObra;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.cotizacion.ExplorarSubObras;

/**
 *
 * @author Iuga
 */
public class GestorExplorarSubObras {
    
    private ExplorarSubObras pantalla;
    private Session sesion;
    
    private PedidoObra obra;
    private Cotizacion cot;

    public GestorExplorarSubObras(ExplorarSubObras pantalla) 
    {
        this.pantalla = pantalla;
        try 
        {    
            this.sesion = HibernateUtil.getSession();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }        
    }
    
    public void cargarCotizacion(int id_cot)
    {
        this.cot  = (Cotizacion) sesion.load(Cotizacion.class,id_cot);
        this.obra = cot.buscarPedidoObra();   
        
        cargarDescripcionObra();
        
        refrescarVentana();
    }

    private void refrescarVentana() 
    {
        // Cargo en el menú las SubObras
        cargarSubObras();
        // Cargo los datos de la Obra
        cargarDatosGeneralesObra();
        // Actualizo el monto Total
        cargarMontoTotal();
        // Mostrar Barra de Monto Actual VS Maximo
        cargarMontoActualVSMaximo();
    }    
    
    private void cargarSubObras()
    {
        this.pantalla.clearSubObrasList();
        Iterator<SubObra> it = this.cot.getSubObras().iterator();
        while(it.hasNext())
        {
            SubObra sb = (SubObra) it.next();
            Tupla nt = new Tupla(sb.getId(),sb.getNombre());
            this.pantalla.cargarSubObras(nt);
        }     
    }
    
    private void cargarDatosGeneralesObra()
    {
        String lbl_obra_nombre = this.obra.getNombre();
        String lbl_obra_planta = this.obra.getPlanta().getRazonSocial();
        String lbl_obra_lugar  = this.obra.getPlanta().getDomicilio().toString();
        String lbl_obra_montomax = "$"+String.valueOf(this.obra.getPresupuestoMaximo());
        String lbl_obra_fechaini = FechaUtil.getFecha(this.obra.getFechaInicio());
        String lbl_obra_fechafin = FechaUtil.getFecha(this.obra.getFechaFin());
        
        pantalla.llenarDatosGeneralesObra(lbl_obra_nombre, lbl_obra_planta, lbl_obra_lugar, lbl_obra_montomax, lbl_obra_fechaini, lbl_obra_fechafin);
    }
    
    private double getMontoMaximo()
    {
        if(this.cot!=null)
        {
            return this.obra.getPresupuestoMaximo();
        }
        return -1;
    }
    
    public String getMontoTotal()
    {
        return "$"+this.cot.CalcularTotal()+" (Max: $"+getMontoMaximo()+")";
    }
    
    private void cargarMontoTotal()
    {
        pantalla.setMontoTotal(getMontoTotal());
    }

    private void cargarMontoActualVSMaximo() {
        
        //pantalla.setMontoActualVSMaximo(this.cot.CalcularTotal(),getMontoMaximo());
        pantalla.setMontoActualVSMaximo(1000,getMontoMaximo());
    }

    private void cargarDescripcionObra() 
    {
        pantalla.setDescripcionObra(this.cot.getDescripcion());
    }
    
    public void crearSubObra(String nombre)
    {
        // VEO QUE NO REPITA EL NOMBRE
        boolean _used_name = false;
        for (int i = 0; i < this.cot.getSubObras().size(); i++) 
        {
            SubObra so = this.cot.getSubObras().get(i);
            if(so.getNombre().equals(nombre))
            {
                _used_name=true;
            }
        }
        
        if(_used_name==false)
        {
            SubObra nuevaso = new SubObra();
            nuevaso.setNombre(nombre);
            this.cot.getSubObras().add(nuevaso);
            refrescarVentana();
        }
    }

            
    
    
}
