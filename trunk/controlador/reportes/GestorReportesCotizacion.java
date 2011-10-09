
package controlador.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.PedidoObra;
import modelo.SubObra;
import org.hibernate.Hibernate;
import util.FechaUtil;
import util.HibernateUtil;
import util.ReporteUtil;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.CotizacionExternaGeneral;
import vista.reportes.sources.CotizacionExternaXRecurso;
import vista.reportes.sources.CotizacionInterna;
import vista.reportes.sources.CotizacionExternaSubObras;

/**
 * @author Iuga
 */
public class GestorReportesCotizacion {
    
    private ReporteUtil reporteUtil;
    
    public static final String REPORTE_PATH  = "/vista/reportes/";
    public static final String REPORTE_COTIZACION_INTERNO = "CotizacionInterna.jasper";

    public GestorReportesCotizacion() 
    {
        reporteUtil = new ReporteUtil();
    }
    
    public void emitirPresupuestoInterno(int id_presupuesto)
    {
         try
        {                  
            // Listado de SubObras
            Cotizacion cot =(Cotizacion) HibernateUtil.getSession().load(Cotizacion.class,id_presupuesto);
            
            PedidoObra po= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in elements(PO.cotizaciones)").setParameter("cID", cot).uniqueResult(); 
            
            List listaSubObras = new ArrayList();
            for (int i = 0; i < cot.getSubObras().size(); i++) 
            {
               SubObra so = cot.getSubObras().get(i);
               listaSubObras.add(so);
            }

            try
            {
                HashMap<String,Object> params = new HashMap<String, Object>();
                
                    params.put("COTIZACION_NRO",cot.getNroCotizacion()+" Rev:"+cot.getNroRevision());
                    params.put("COTIZACION_MEMDESC",cot.getDescripcion());
                    params.put("LISTA_SUB_OBRAS",listaSubObras);
                    params.put("COTIZACION_TOTAL","$"+cot.CalcularTotal());
                    
                    params.put("FORMA_DE_PAGO",po.getFormaPago().getNombre());
                    params.put("PLAZO_ENTREGA",cot.getPlazoEntrega());
                    params.put("LUGAR_ENTREGA",cot.getLugarEntrega());
                    
                    String valof = FechaUtil.diasDiferencia(new Date(),cot.getValidezOferta())+" días ( desde el "+FechaUtil.getFechaActual()+")";
                    params.put("VALIDEZ_OFERTA",valof);
                    
                    
                CotizacionInterna ci = new CotizacionInterna(id_presupuesto);
                ci.setNombreReporte("Cotización Interna");
                ci.setNombreArchivo("CotizacionInterna-"+cot.getNroCotizacion()+"Rev."+cot.getNroRevision(),ReportDesigner.REPORTE_TIPO_COTIZACION);
                ci.makeAndShow(params);
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

        public void emitirPresupuestoExternoGeneral(int id_presupuesto)
    {
         try
        {
            Cotizacion cot =(Cotizacion) HibernateUtil.getSession().load(Cotizacion.class,id_presupuesto);

            PedidoObra po= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in elements(PO.cotizaciones)").setParameter("cID", cot).uniqueResult();

            try
            {
                HashMap<String,Object> params = new HashMap<String, Object>();

                    params.put("COTIZACION_NRO",cot.getNroCotizacion()+" Rev:"+cot.getNroRevision());
                    params.put("COTIZACION_MEMDESC",cot.getDescripcion());
                    params.put("COTIZACION_TOTAL","$"+cot.CalcularTotal());

                    params.put("FORMA_DE_PAGO",po.getFormaPago().getNombre());
                    params.put("PLAZO_ENTREGA",cot.getPlazoEntrega());
                    params.put("LUGAR_ENTREGA",cot.getLugarEntrega());

                    String valof = FechaUtil.diasDiferencia(new Date(),cot.getValidezOferta())+" días ( desde el "+FechaUtil.getFechaActual()+")";
                    params.put("VALIDEZ_OFERTA",valof);


                CotizacionExternaGeneral ceg = new CotizacionExternaGeneral(id_presupuesto);
                ceg.setNombreReporte("Cotización Externa");
                ceg.setNombreArchivo("CotizacionExterna-"+cot.getNroCotizacion()+"Rev."+cot.getNroRevision(),ReportDesigner.REPORTE_TIPO_COTIZACION);
                ceg.makeAndShow(params);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void emitirPresupuestoExternoXRecurso(int cotizacionId) {
         try
        {
            // Listado de SubObras
            Cotizacion cot =(Cotizacion) HibernateUtil.getSession().load(Cotizacion.class,cotizacionId);

            PedidoObra po= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in elements(PO.cotizaciones)").setParameter("cID", cot).uniqueResult();

            List listaSubObras = new ArrayList();
            for (int i = 0; i < cot.getSubObras().size(); i++)
            {
               SubObra so = cot.getSubObras().get(i);
               listaSubObras.add(so);
            }

            try
            {
                HashMap<String,Object> params = new HashMap<String, Object>();

                    params.put("COTIZACION_NRO",cot.getNroCotizacion()+" Rev:"+cot.getNroRevision());
                    params.put("COTIZACION_MEMDESC",cot.getDescripcion());
                    params.put("LISTA_SUB_OBRAS",listaSubObras);
                    params.put("COTIZACION_TOTAL","$"+cot.CalcularTotal());

                    params.put("FORMA_DE_PAGO",po.getFormaPago().getNombre());
                    params.put("PLAZO_ENTREGA",cot.getPlazoEntrega());
                    params.put("LUGAR_ENTREGA",cot.getLugarEntrega());

                    String valof = FechaUtil.diasDiferencia(new Date(),cot.getValidezOferta())+" días ( desde el "+FechaUtil.getFechaActual()+")";
                    params.put("VALIDEZ_OFERTA",valof);


                CotizacionExternaXRecurso cexr = new CotizacionExternaXRecurso(cotizacionId);
                cexr.setNombreReporte("Cotización Externa");
                cexr.setNombreArchivo("CotizacionExternaRecursos-"+cot.getNroCotizacion()+"Rev."+cot.getNroRevision(),ReportDesigner.REPORTE_TIPO_COTIZACION);
                cexr.makeAndShow(params);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void emitirPresupuestoExternoSubObras(int id_presupuesto)
    {
         try
        {                  
            // Listado de SubObras
            Cotizacion cot =(Cotizacion) HibernateUtil.getSession().load(Cotizacion.class,id_presupuesto);
            
            List listaSubObras = new ArrayList();
            for (int i = 0; i < cot.getSubObras().size(); i++) 
            {
               SubObra so = cot.getSubObras().get(i);
               listaSubObras.add(so);
            }

            try
            {
                HashMap<String,Object> params = new HashMap<String, Object>();
                
                    params.put("COTIZACION_NRO",cot.getNroCotizacion()+" Rev:"+cot.getNroRevision());
                    params.put("COTIZACION_MEMDESC",cot.getDescripcion());
                    params.put("LISTA_SUB_OBRAS",listaSubObras);
                    params.put("COTIZACION_TOTAL","$"+cot.CalcularTotal());
                    
                CotizacionExternaSubObras ceso = new CotizacionExternaSubObras(id_presupuesto);
                ceso.setNombreReporte("Cotización Externa por Items de la Obra");
                ceso.setNombreArchivo("CotizacionExternaItemsDeObra-"+cot.getNroCotizacion()+"Rev."+cot.getNroRevision(),ReportDesigner.REPORTE_TIPO_COTIZACION);
                ceso.makeAndShow(params);
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
