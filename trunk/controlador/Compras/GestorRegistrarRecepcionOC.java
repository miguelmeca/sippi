/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
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

    /***************************************************************************
     *                LOAD CON PROVEEDORES CON ORDENES DE COMPRA 
     ***************************************************************************
     */

    public ArrayList<NTupla> mostrarProveedoresConOCPendientes(){
        ArrayList<NTupla> provs = new ArrayList<NTupla>();
        NTupla nt = new NTupla();
        Proveedor pr = null;
        Iterator it = null;
        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecepcionParcial' AND oc.proveedor = p)";
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

    public ArrayList<NTupla> mostrarProveedoresConOCGeneradas(){
        ArrayList<NTupla> provs = new ArrayList<NTupla>();
        NTupla nt = new NTupla();
        Proveedor pr = null;
        Iterator it = null;
        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada' AND oc.proveedor = p)";
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

    public ArrayList<NTupla> mostrarProveedoresConOC(){
        ArrayList<NTupla> provs = new ArrayList<NTupla>();
        NTupla nt = new NTupla();
        Proveedor pr = null;
        Iterator it = null;
        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.proveedor = p)";
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

    /***************************************************************************
     *                       BUSQUEDA POR PROVEEDOR
     ***************************************************************************
     */

    public ArrayList<NTupla> buscarOCPendientesProveedor(int idProv){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecepcionParcial'").setParameter("idProveedor",idProv).iterate();
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

    public ArrayList<NTupla> buscarOCGeneradasProveedor(int idProv){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("idProveedor",idProv).iterate();
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

    public ArrayList<NTupla> buscarOCProveedor(int idProv){
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

    /***************************************************************************
     *                   BUSQUEDA POR NUMERO DE ORDEN DE COMPRA
     ***************************************************************************
     */

    public ArrayList<NTupla> buscarOCPendientesPorNro(int nro){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecepcionParcial'").setParameter("idOC",nro).iterate();
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

    public ArrayList<NTupla> buscarOCGeneradasPorNro(int nro){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("idOC",nro).iterate();
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

    /***************************************************************************
     *                    BUSQUEDA POR FECHA DE EMISION
     ***************************************************************************
     */

    public ArrayList<NTupla> buscarOCPendientesPorFechaEmision(Date fecha){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecepcionParcial'").setParameter("fecha",fecha).iterate();
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

    public ArrayList<NTupla> buscarOCGeneradasPorFechaEmision(Date fecha){
        OrdenDeCompra oc=null;
        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
        NTupla nt = null;
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("fecha",fecha).iterate();
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

    /***************************************************************************
     *                       MOSTRAR DETALLE <GENERICO>
     ***************************************************************************
     */

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

    /***************************************************************************
     *                       METODOS DE RECEPCION
     ***************************************************************************
     */

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
            String query="FROM Remito WHERE orden.id=:idOC";
            OrdenDeCompra orden = (OrdenDeCompra)HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
            Iterator<Remito> it = (Iterator<Remito>)HibernateUtil.getSession().createQuery(query).setParameter("idOC", idOC).iterate();

            for(int i = 0;i<model.getRowCount();i++){
                boolean esta = false;
                if((Boolean)model.getValueAt(i, 0)){
                    NTupla nt = (NTupla)model.getValueAt(i,2);
                    while(it.hasNext()){
                        Remito re = (Remito)it.next();
                        Iterator itRE = (Iterator)re.getDetalle().iterator();
                        while(itRE.hasNext()){
                            DetalleRemito dr = (DetalleRemito)itRE.next();
                            if(nt.getId() == dr.getDetalleOC().getId()){
                                esta = true;
                            }
                        }
                    }
                    if(!esta){

                    }
                }
            }

            while(it.hasNext()){
                Remito re = (Remito)it.next();
                Iterator itRE = (Iterator)re.getDetalle().iterator();
                while(itRE.hasNext()){
                    DetalleRemito dr = (DetalleRemito)itRE.next();
                    boolean esta = false;
                    for(int i = 0;i<model.getRowCount();i++){
                        if((Boolean)model.getValueAt(i, 0)){
                            NTupla nt = (NTupla)model.getValueAt(i,2);
                            if(nt.getId() == dr.getDetalleOC().getId()){
                                esta = true;
                            }
                        }
                    }
                }


            }



            DetalleOrdenDeCompra doc = null;
            Remito re = new Remito();
            ArrayList<DetalleRemito> listaDetalles = new ArrayList<DetalleRemito>();
            while(it.hasNext()){
//                doc = (DetalleOrdenDeCompra)it.next();
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

//    public boolean verificarRecepcionParcial(int id) {
//        boolean respuesta = false;
//        try {
//            ArrayList<Remito> remitos = (ArrayList<Remito>) HibernateUtil.getSession().createQuery("from Remito where orden.id=:idOC").setParameter("idOC", id).list();
//            if(!remitos.isEmpty()){
//                respuesta=true;
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return respuesta;
//    }

    public Tupla getEstadoOCSeleccionada(int id) {
        Tupla estado = new Tupla();
        try {
            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
            estado.setId(oc.getId());
            estado.setNombre(oc.getEstado().getNombre());
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

    public boolean verificarRecibidaParcial(int id){
        Boolean estado = false;
        try {
            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
            estado = oc.getEstado().esRecibidaParcial();
        } catch (Exception ex) {
            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
}
