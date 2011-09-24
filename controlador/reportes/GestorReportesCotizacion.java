
package controlador.reportes;

import java.util.HashMap;
import util.ReporteUtil;

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
        String reporte = GestorReportesCotizacion.REPORTE_PATH+GestorReportesCotizacion.REPORTE_COTIZACION_INTERNO;

            // Busco los datos
            HashMap params = new HashMap();
            params.put("ID_PRESUPUESTO",id_presupuesto);
            
        try
        {
            reporteUtil.mostrarReporte(reporte,params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
