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
public class OLD_pantallaConsultarOC extends OLD_pantallaRegistrarRecepcionOrdenCompra{

    public OLD_pantallaConsultarOC() {
        super();
        super.ocultarBotonesRecepcion();
        super.borrarColumnaSeleccion();
        super.setTitle("Consultar Orden de Compra");
    }

//    @Override
//    protected ArrayList<NTupla> buscarOCPorFechaEmision(Date fecha) {
//        return super.getGestor().buscarOCPorFechaEmision(fecha);
//    }
//
//    @Override
//    protected ArrayList<NTupla> buscarOCPorNro(int nro) {
//        return super.getGestor().buscarOCPorNro(nro);
//    }
//
//    @Override
//    protected ArrayList<NTupla> buscarOCPorProveedor(int id) {
//        return super.getGestor().buscarOCProveedor(id);
//    }
//
//    @Override
//    protected ArrayList<NTupla> mostrarProveedores() {
//        return super.getGestor().mostrarProveedoresConOC();
//    }
}
