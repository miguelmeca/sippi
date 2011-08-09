
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cotizacion;
import modelo.PedidoObra;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.cotizacion.ExplorarCotizacionObra;

/*
 * @author Iuga
 */
public class GestorExplorarCotizacionesObra {
    
    private ExplorarCotizacionObra vista;
    private Session sesion;

    public GestorExplorarCotizacionesObra(ExplorarCotizacionObra vista) 
    {
        this.vista = vista;
        try 
        {    
            this.sesion = HibernateUtil.getSession();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<NTupla> getObras()
    {
        ArrayList<NTupla> listaObras = new ArrayList<NTupla>();
        
            Iterator iter = sesion.createQuery("from PedidoObra p order by p.nombre").iterate();
            while (iter.hasNext())
            {
                PedidoObra p = (PedidoObra)iter.next();

                NTupla nt = new NTupla(p.getId());
                    String[] datos = new String[4];
                        datos[0] = p.getNombre();
                        datos[1] = p.getPlanta().getRazonSocial();
                        datos[2] = FechaUtil.getFecha(p.getFechaInicio());
                        datos[3] = FechaUtil.getFecha(p.getFechaFin());
                    nt.setData(datos);
                listaObras.add(nt);
            }
        
        return listaObras;
    }

    public ArrayList<NTupla> getCotizaciones(int idObra)
    {
        ArrayList<NTupla> listaCotizaciones = new ArrayList<NTupla>();
        
            PedidoObra p = (PedidoObra) sesion.load(PedidoObra.class,idObra);
            Iterator it = p.getCotizaciones().iterator();
            while(it.hasNext())
            {
                Cotizacion cot = (Cotizacion)it.next();
                NTupla nt = new NTupla(cot.getId());
                    
                    String[] data = new String[4];
                    data[0] = String.valueOf(cot.getNroCotizacion());
                    data[1] = String.valueOf(cot.getNroRevision());
                    data[2] = FechaUtil.getFecha(cot.getFechaModificacion());
                    data[3] = FechaUtil.getFecha(cot.getFechaCreacion());
                    nt.setData(data);
                listaCotizaciones.add(nt);
            }
        
        return listaCotizaciones;
    }
    
    
    
}
