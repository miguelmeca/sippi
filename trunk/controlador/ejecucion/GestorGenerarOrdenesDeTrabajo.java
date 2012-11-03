/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ejecucion;

import java.util.HashMap;
import modelo.PedidoObra;
import modelo.TareaEjecucion;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.OrdenDeTrabajo;

/**
 * Gestor que se encarga de emitir ordenes de trabajo.
 * @author Iuga
 */
public class GestorGenerarOrdenesDeTrabajo {

    /**
     * Emite la orden de trabajo seleccionada
     * @param po
     * @param te 
     */
    public void emitirOrdenDeTrabajo(PedidoObra po, TareaEjecucion te) {
        try {
            OrdenDeTrabajo re = new OrdenDeTrabajo(po,te);
            re.setNombreReporte("Orden de Trabajo");
            re.setNombreArchivo("OrdenDeTrabajo-"+EjecucionUtils.getNumeroOrdenDeTrabajo(po.getNumero(),te.getId()),ReportDesigner.REPORTE_TIPO_OTROS);
            re.makeAndShow(new HashMap<String,Object>());
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
        }
    }
    
    
}
