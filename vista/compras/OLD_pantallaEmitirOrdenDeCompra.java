/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.compras;

import java.util.ArrayList;
import java.util.Date;
import util.NTupla;

/**
 *
 * @author Emmanuel
 */
@Deprecated
public class OLD_pantallaEmitirOrdenDeCompra extends OLD_pantallaConsultarOC{
    public OLD_pantallaEmitirOrdenDeCompra() {
        super();
        super.ocultarBotonesRecepcion();
        super.borrarColumnaSeleccion();
        super.setTitle("Imprimir Orden De Compra Generada");
        super.visualizarBotonImprimir();
    }

//    @Override
//    protected ArrayList<NTupla> buscarOCPorFechaEmision(Date fecha) {
//        return super.getGestor().buscarOCGeneradasPorFechaEmision(fecha);
//    }
//
//    @Override
//    protected ArrayList<NTupla> buscarOCPorNro(int nro) {
//        return super.getGestor().buscarOCGeneradasPorNro(nro);
//    }
//
//    @Override
//    protected ArrayList<NTupla> buscarOCPorProveedor(int id) {
//        return super.getGestor().buscarOCGeneradasProveedor(id);
//    }
//
//    @Override
//    protected ArrayList<NTupla> mostrarProveedores() {
//        return super.getGestor().mostrarProveedoresConOCGeneradas();
//    }
}
