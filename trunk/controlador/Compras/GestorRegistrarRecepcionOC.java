/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.OrdenDeCompra;
import modelo.Proveedor;
import util.HibernateUtil;
import util.NTupla;
import vista.compras.pantallaConsultarOC;
import vista.compras.pantallaRegistrarRecepcionOrdenCompra;

/**
 *
 * @author Emmanuel
 */
public class GestorRegistrarRecepcionOC {
    private pantallaRegistrarRecepcionOrdenCompra pantalla;

    public GestorRegistrarRecepcionOC(pantallaRegistrarRecepcionOrdenCompra p) {
        this.pantalla = p;
    }

    public ArrayList<NTupla> mostrarProveedoresConOrdenesPendientes(){
        /**
         * LA PAPA DE HIBERNATE
         */
        ArrayList<NTupla> provs = new ArrayList<NTupla>();
        NTupla nt = new NTupla();
        Proveedor pr = null;
        Iterator it = null;
        String consulta = "SELECT p FROM modelo.Proveedor p WHERE EXIST(SELECT oc FROM OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendiente' AND oc.proveedor = p)";
        try {
            it = HibernateUtil.getSession().createQuery(consulta).iterate();
            while(it.hasNext()){
                pr = (Proveedor)it.next();
                nt = new NTupla();
                nt.setId(pr.getId());
                nt.setNombre(consulta);
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList<NTupla> buscarOCPendientes(){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompras").iterate();
            while(it.hasNext()){
                oc = (OrdenDeCompra)it.next();
                nt = new NTupla();
                nt.setId(oc.getId());
                nt.setNombre(oc.getProveedor().getRazonSocial());
                nt.setData(oc.getFechaDePedido());
                ordenes.add(nt);
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordenes;
    }
}
