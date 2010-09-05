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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.DetalleOrdenDeCompra;
import modelo.DetalleRemito;
import modelo.OrdenDeCompra;
import modelo.Proveedor;
import modelo.Remito;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
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
        ArrayList<NTupla> detalles = new ArrayList<NTupla>();
        NTupla nt = null;
        String[] datos = new String[3];
        try {
//            HibernateUtil.getSession().createQuery("from DetalleOrdenDeCompra doc WHERE EXISTS(FROM OrdenDeCompra oc WHERE oc=:idOC AND oc.detalle.id = doc.id) AND EXISTS(FROM DetalleRemito WHERE detalleOC.id=doc.id)");
            OrdenDeCompra orden = (OrdenDeCompra)HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
            for(DetalleOrdenDeCompra doc : orden.getDetalle()){
                nt = new NTupla();
                nt.setId(doc.getId());
                nt.setNombre(doc.getRecurso().getNombre());
                datos[0] = String.valueOf(doc.getCantidad());
                datos[1] = String.valueOf(doc.getRecurso().getDescipcion());
                datos[2] = String.valueOf(doc.getPrecio());
                nt.setData(datos);
                detalles.add(nt);
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalles;
    }

    public int registrarRecepcionTotal(int idOC) {
        int idRemito = 0;
        try {
            HibernateUtil.beginTransaction();
            OrdenDeCompra orden = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
            Iterator it = orden.getDetalle().iterator();
            DetalleOrdenDeCompra doc = null;
            Remito re = new Remito();
            ArrayList<DetalleRemito> listaDetalles = new ArrayList<DetalleRemito>();
            while(it.hasNext()){
                doc = (DetalleOrdenDeCompra)it.next();
                DetalleRemito dre = new DetalleRemito();
                dre.setCantidad(doc.getCantidad());// ACA ESTA MAL XQ EL USUARIO DEBERIA INGRESAR LO QUE REALMENTE RECIBIO... MOCO...
//                re.setDescripcion(doc.get); TAMBIEN NOS ESTA FALTANDO LA DESCRICION... TODO MAL..
                dre.setDetalleOC(doc);
                HibernateUtil.getSession().save(dre);
                listaDetalles.add(dre);
            }
            re.setDetalle(listaDetalles);
            re.setFechaEntrega(new Date());
            re.setOrden(orden);
            HibernateUtil.getSession().save(re);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
        }
        return idRemito;
    }

    public int registrarRecepcionParcial(Integer idOC, TableModel model) {
        DefaultTableModel dtm = (DefaultTableModel)model;
        int idRemito = 0;
        try {
            HibernateUtil.beginTransaction();
            OrdenDeCompra orden = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
            Iterator it = orden.getDetalle().iterator();
            for (int i = 0;i<dtm.getRowCount();i++){
                boolean selec = (Boolean)dtm.getValueAt(i, 0);
                if(selec){
                    Tupla t = (Tupla)dtm.getValueAt(i, 2);

                }
            }
            DetalleOrdenDeCompra doc = null;
            Remito re = new Remito();
            ArrayList<DetalleRemito> listaDetalles = new ArrayList<DetalleRemito>();
            while(it.hasNext()){
                doc = (DetalleOrdenDeCompra)it.next();
                DetalleRemito dre = new DetalleRemito();
                dre.setCantidad(doc.getCantidad());// ACA ESTA MAL XQ EL USUARIO DEBERIA INGRESAR LO QUE REALMENTE RECIBIO... MOCO...
//                re.setDescripcion(doc.get); TAMBIEN NOS ESTA FALTANDO LA DESCRICION... TODO MAL..
                dre.setDetalleOC(doc);
                HibernateUtil.getSession().save(dre);
                listaDetalles.add(dre);
            }
            re.setDetalle(listaDetalles);
            re.setFechaEntrega(new Date());
            re.setOrden(orden);
            HibernateUtil.getSession().save(re);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
        }
        return idRemito;
    }

    public boolean verificarRecepcionParcial(int id) {
        boolean respuesta = false;
        try {
            ArrayList<Remito> remitos = (ArrayList<Remito>) HibernateUtil.getSession().createQuery("from Remito where orden.id=:idOC").setParameter("idOC", id).list();
            if(!remitos.isEmpty()){
                respuesta=true;
            }

        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
}
