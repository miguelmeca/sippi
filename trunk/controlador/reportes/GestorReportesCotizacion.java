
package controlador.reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.SubObra;
import org.hibernate.Hibernate;
import util.HibernateUtil;
import util.ReporteUtil;
import vista.reportes.sources.CotizacionInterna;

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
            
            List listaSubObras = new ArrayList();
            for (int i = 0; i < cot.getSubObras().size(); i++) 
            {
               SubObra so = cot.getSubObras().get(i);
               listaSubObras.add(so);
            }

            try
            {
                CotizacionInterna ci = new CotizacionInterna(id_presupuesto);
                ci.setNombreReporte("Cotizacion Interna");
                ci.makeAndShow();
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
