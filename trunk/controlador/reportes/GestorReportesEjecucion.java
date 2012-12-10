
package controlador.reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.*;
import util.FechaUtil;
import util.HibernateUtil;
import util.ReporteUtil;
import vista.reportes.sources.ResumenPlanificacion;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.ResumenEjecucion;

/**
 * @author Iuga
 */
public class GestorReportesEjecucion {
    
    private ReporteUtil reporteUtil;
    
    public static final String REPORTE_PATH  = "/vista/planificacion/reportes/";

    public GestorReportesEjecucion() 
    {
        reporteUtil = new ReporteUtil();
    }
    
    public void emitirResumenEjecucion(Ejecucion plan)
    {
         try
        {                  
            PedidoObra po= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID = PO.ejecucion").setParameter("cID", plan).uniqueResult(); 
            EmpresaCliente empresa = (EmpresaCliente)HibernateUtil.getSession().createQuery("from EmpresaCliente EC where :cID in elements(EC.plantas)").setParameter("cID", po.getPlanta()).uniqueResult();

            List listaTareas = new ArrayList();
            for (int i = 0; i < plan.getTareas().size(); i++)
            {
               TareaPlanificacion tp = plan.getTareas().get(i);
               listaTareas.add(tp);
            }
            
            List listaAdicionales = new ArrayList();
            
                List<EjecucionXAdicional>  adicionales= plan.getAdicionales();
                for (int j = 0; j < adicionales.size(); j++)
                {
                    EjecucionXAdicional tp = adicionales.get(j);
                    listaAdicionales.add(tp);
                }
            HashMap<String,Object> params = new HashMap<String, Object>();

                params.put("PLANIFICACION_NRO",plan.getId());
                params.put("PLANIFICACION_EMPRESA_CLIENTE", empresa.getRazonSocial() );
                params.put("PLANIFICACION_PEDIDO_OBRA", po.getNombre() );
                params.put("PLANIFICACION_FECHA_INICIO", FechaUtil.getFecha(plan.getFechaInicio()) );
                params.put("PLANIFICACION_FECHA_FIN", FechaUtil.getFecha(plan.getFechaFin()) );
                params.put("PLANIFICACION_TAREAS", listaTareas );
                params.put("PLANIFICACION_ADICIONALES", listaAdicionales );

            ResumenEjecucion re = new ResumenEjecucion(plan.getId());
            re.setNombreReporte("Resumen Ejecucion");
            re.setNombreArchivo("ResumenEjecucion-"+plan.getId(),ReportDesigner.REPORTE_TIPO_EJECUCION);
            re.makeAndShow(params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
