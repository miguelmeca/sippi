/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DetalleOrdenDeCompra;
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

    public ArrayList<NTupla> mostrarProveedoresConOCPendientes(){
        ArrayList<NTupla> provs = new ArrayList<NTupla>();
        NTupla nt = new NTupla();
        Proveedor pr = null;
        Iterator it = null;
        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' AND oc.proveedor = p)";
        try {
            it = HibernateUtil.getSession().createQuery(consulta).iterate();
            while(it.hasNext()){
                pr = (Proveedor)it.next();
                nt = new NTupla();
                nt.setId(pr.getId());
                nt.setNombre(pr.getRazonSocial());
                provs.add(nt);
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provs;
    }

    public ArrayList<NTupla> buscarOCPendientesProveedor(int idProv){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor").setParameter("idProveedor",idProv).iterate();
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

    public ArrayList<NTupla> buscarOCPorNro(int nro){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC").setParameter("idOC",nro).iterate();
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

        public ArrayList<NTupla> buscarOCPorFechaEmision(Date fecha){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha").setParameter("fecha",fecha).iterate();
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

    public ArrayList<NTupla> mostrarDetalleOC(int idOC){
        DetalleOrdenDeCompra doc=null;
        ArrayList<NTupla> detalles = new ArrayList<NTupla>();
        NTupla nt = null;
        String[] datos = new String[3];
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from DetalleOrdenDeCompra doc WHERE (FROM OrdenDeCompra oc WHERE oc=:idOC AND oc.detalle=doc.id)").setParameter("idOC",idOC).iterate();
            while(it.hasNext()){
                doc = (DetalleOrdenDeCompra)it.next();
                nt = new NTupla();
                nt.setId(doc.getId());
                nt.setNombre(doc.getRecurso().getNombre());
                datos[0] = new String(String.valueOf(doc.getCantidad()));
                datos[1] = new String(String.valueOf(doc.getRecurso().getDescipcion()));
                datos[2] = new String(String.valueOf(doc.getPrecio()));
                nt.setData(datos);
                detalles.add(nt);
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalles;
    }
}
