/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import modelo.OrdenDeCompra;
import vista.reportes.ReportDesigner;

/**
 * @author Iuga
 */
public class EmitirOrdenDeCompra extends ReportDesigner  {
    
    private OrdenDeCompra odc;

    public EmitirOrdenDeCompra(OrdenDeCompra odc) {
        this.odc = odc;
    }
    
    
    
}
