
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
        
       try
       {
            HibernateUtil.beginTransaction();
            Iterator iter = sesion.createQuery("from PedidoObra p order by p.nombre").iterate();
            while (iter.hasNext())
            {
                PedidoObra p = (PedidoObra)iter.next();
                if(p.getEstado().equals(PedidoObra.ESTADO_SOLICITADO) ||
                   p.getEstado().equals(PedidoObra.ESTADO_COTIZADO) )
                {
                    NTupla nt = new NTupla(p.getId());
                        String[] datos = new String[4];
                            datos[0] = p.getNombre();
                            datos[1] = p.getPlanta().getRazonSocial();
                            datos[2] = FechaUtil.getFecha(p.getFechaInicio());
                            datos[3] = FechaUtil.getFecha(p.getFechaFin());
                        nt.setData(datos);
                    listaObras.add(nt);
                }
            }
            HibernateUtil.commitTransaction();
       }
       catch(Exception ex){
           HibernateUtil.rollbackTransaction();
       }
        return listaObras;
    }

    public ArrayList<NTupla> getCotizaciones(int idObra)
    {
        ArrayList<NTupla> listaCotizaciones = new ArrayList<NTupla>();
        try
        {
            HibernateUtil.beginTransaction();
            PedidoObra p = (PedidoObra) sesion.load(PedidoObra.class,idObra);
            Iterator it = p.getCotizaciones().iterator();
            while(it.hasNext())
            {
                Cotizacion cot = (Cotizacion)it.next();

                // Solo agrego las cotizaciones que se estén creando o esperando aceptacion !!
                if(cot.getEstado().equals(Cotizacion.ESTADO_EN_CREACION) )
                {
                    NTupla nt = new NTupla(cot.getId());

                        String[] data = new String[6];
                        data[0] = String.valueOf(cot.getNroCotizacion());
                        data[1] = String.valueOf(cot.getNroRevision());
                        data[2] = FechaUtil.getFecha(cot.getFechaModificacion());
                        data[3] = FechaUtil.getFecha(cot.getFechaCreacion());

                            data[4] = String.valueOf(p.getId());
                            data[5] = cot.getEstado();

                        nt.setData(data);
                    listaCotizaciones.add(nt);
                }
            }
            HibernateUtil.commitTransaction();
        }
        catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        
        return listaCotizaciones;
    }
}
