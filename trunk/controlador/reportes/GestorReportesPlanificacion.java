
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

/**
 * @author Iuga
 */
public class GestorReportesPlanificacion {
    
    private ReporteUtil reporteUtil;
    
    public static final String REPORTE_PATH  = "/vista/planificacion/reportes/";

    public GestorReportesPlanificacion() 
    {
        reporteUtil = new ReporteUtil();
    }
    
    public void emitirResumenPlanificacion(Planificacion plan)
    {
         try
        {                  
            PedidoObra po= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID = PO.planificacion").setParameter("cID", plan).uniqueResult(); 
            EmpresaCliente empresa = (EmpresaCliente)HibernateUtil.getSession().createQuery("from EmpresaCliente EC where :cID in elements(EC.plantas)").setParameter("cID", po.getPlanta()).uniqueResult();

            List listaTareas = new ArrayList();
            for (int i = 0; i < plan.getTareas().size(); i++)
            {
               TareaPlanificacion tp = plan.getTareas().get(i);
               listaTareas.add(tp);
            }
            HashMap<String,Object> params = new HashMap<String, Object>();

                params.put("PLANIFICACION_NRO",plan.getId());
                params.put("PLANIFICACION_EMPRESA_CLIENTE", empresa.getRazonSocial() );
                params.put("PLANIFICACION_PEDIDO_OBRA", po.getNombre() );
                params.put("PLANIFICACION_FECHA_INICIO", FechaUtil.getFecha(plan.getFechaInicio()) );
                params.put("PLANIFICACION_FECHA_FIN", FechaUtil.getFecha(plan.getFechaFin()) );
                params.put("PLANIFICACION_TAREAS", listaTareas );

            ResumenPlanificacion re = new ResumenPlanificacion(plan.getId());
            re.setNombreReporte("Resumen Planificación");
            re.setNombreArchivo("ResumenPlanificacion-"+plan.getId(),ReportDesigner.REPORTE_TIPO_PLANIFICACION);
            re.makeAndShow(params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
