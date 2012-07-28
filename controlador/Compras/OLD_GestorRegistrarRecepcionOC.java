/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import vista.compras.OLD_pantallaConsultarOC;
import vista.compras.OLD_pantallaRegistrarRecepcionOrdenCompra;
import vista.interfaces.IPantallaOrdenDeCompra;

/**
 *
 * @author Emmanuel
 */
public class OLD_GestorRegistrarRecepcionOC {
//    private pantallaRegistrarRecepcionOrdenCompra pantalla;
    private IPantallaOrdenDeCompra pantalla;

//    public GestorRegistrarRecepcionOC(pantallaRegistrarRecepcionOrdenCompra p) {
//        this.pantalla = p;
//    }

    public OLD_GestorRegistrarRecepcionOC(IPantallaOrdenDeCompra p) {
        this.pantalla = p;
    }

    /***************************************************************************
     *                LOAD CON PROVEEDORES CON ORDENES DE COMPRA 
     ***************************************************************************
     */

//    public ArrayList<NTupla> mostrarProveedoresConOCPendientes(){
//        ArrayList<NTupla> provs = new ArrayList<NTupla>();
//        NTupla nt = new NTupla();
//        Proveedor pr = null;
//        Iterator it = null;
//        String consulta = "FROM modelo.Proveedor p WHERE p IN (SELECT oc.proveedor FROM modelo.OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecibidaParcial' AND oc.proveedor = p GROUP BY oc.proveedor)";
//        try {
//            it = HibernateUtil.getSession().createQuery(consulta).iterate();
//            while(it.hasNext()){
//                pr = (Proveedor)it.next();
//                nt = new NTupla();
//                nt.setId(pr.getId());
//                nt.setNombre(pr.getRazonSocial());
//                provs.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return provs;
//    }
//
//    public ArrayList<NTupla> mostrarProveedoresConOCGeneradas(){
//        ArrayList<NTupla> provs = new ArrayList<NTupla>();
//        NTupla nt = new NTupla();
//        Proveedor pr = null;
//        Iterator it = null;
//        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada' AND oc.proveedor = p)";
//        try {
//            it = HibernateUtil.getSession().createQuery(consulta).iterate();
//            while(it.hasNext()){
//                pr = (Proveedor)it.next();
//                nt = new NTupla();
//                nt.setId(pr.getId());
//                nt.setNombre(pr.getRazonSocial());
//                provs.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return provs;
//    }
//
//    public ArrayList<NTupla> mostrarProveedoresConOC(){
//        ArrayList<NTupla> provs = new ArrayList<NTupla>();
//        NTupla nt = new NTupla();
//        Proveedor pr = null;
//        Iterator it = null;
//        String consulta = "FROM modelo.Proveedor p WHERE EXISTS(FROM modelo.OrdenDeCompra oc WHERE oc.proveedor = p)";
//        try {
//            it = HibernateUtil.getSession().createQuery(consulta).iterate();
//            while(it.hasNext()){
//                pr = (Proveedor)it.next();
//                nt = new NTupla();
//                nt.setId(pr.getId());
//                nt.setNombre(pr.getRazonSocial());
//                provs.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return provs;
//    }
//
//    /***************************************************************************
//     *                       BUSQUEDA POR PROVEEDOR
//     ***************************************************************************
//     */
//
//    public ArrayList<NTupla> buscarOCPendientesProveedor(int idProv){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecibidaParcial'").setParameter("idProveedor",idProv).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCGeneradasProveedor(int idProv){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("idProveedor",idProv).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCProveedor(int idProv){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.proveedor.id=:idProveedor").setParameter("idProveedor",idProv).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    /***************************************************************************
//     *                   BUSQUEDA POR NUMERO DE ORDEN DE COMPRA
//     ***************************************************************************
//     */
//
//    public ArrayList<NTupla> buscarOCPendientesPorNro(int nro){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecibidaParcial'").setParameter("idOC",nro).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCGeneradasPorNro(int nro){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("idOC",nro).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCPorNro(int nro){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.id=:idOC").setParameter("idOC",nro).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    /***************************************************************************
//     *                    BUSQUEDA POR FECHA DE EMISION
//     ***************************************************************************
//     */
//
//    public ArrayList<NTupla> buscarOCPendientesPorFechaEmision(Date fecha){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraPendienteDeRecepcion' OR oc.hib_flag_estado='modelo.EstadoOrdenDeCompraRecibidaParcial'").setParameter("fecha",fecha).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCGeneradasPorFechaEmision(Date fecha){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha AND oc.hib_flag_estado='modelo.EstadoOrdenDeCompraGenerada'").setParameter("fecha",fecha).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    public ArrayList<NTupla> buscarOCPorFechaEmision(Date fecha){
//        OrdenDeCompra oc=null;
//        ArrayList<NTupla> ordenes = new ArrayList<NTupla>();
//        NTupla nt = null;
//        try {
//            Iterator it = HibernateUtil.getSession().createQuery("from OrdenDeCompra oc where oc.fechaDePedido=:fecha").setParameter("fecha",fecha).iterate();
//            while(it.hasNext()){
//                oc = (OrdenDeCompra)it.next();
//                nt = new NTupla();
//                nt.setId(oc.getId());
//                nt.setNombre(oc.getProveedor().getRazonSocial());
//                nt.setData(oc.getFechaDePedido());
//                ordenes.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ordenes;
//    }
//
//    /***************************************************************************
//     *                       MOSTRAR DETALLE <GENERICO>
//     ***************************************************************************
//     */
//
//    public ArrayList<NTupla> mostrarDetalleOC1(int idOC){
//        ArrayList<NTupla> detalles = new ArrayList<NTupla>();
//        NTupla nt = null;
//        String[] datos = new String[3];
//        try {
//            OrdenDeCompra orden = (OrdenDeCompra)HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
//            for(DetalleOrdenDeCompra doc : orden.getDetalle()){
//                nt = new NTupla();
//                nt.setId(doc.getId());
//                nt.setNombre(doc.getRecurso().getNombre());
//                datos[0] = String.valueOf(doc.getCantidad());
//                datos[1] = String.valueOf(doc.getRecurso().getDescipcion());
//                datos[2] = String.valueOf(doc.getPrecio());
//                nt.setData(datos);
//                detalles.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return detalles;
//    }
//
//    public ArrayList<NTupla> mostrarDetalleOC(int idOC){
//        ArrayList<NTupla> detalles = new ArrayList<NTupla>();
//        //NTupla nt = null;
////        String[] datos = new String[4];
//        try {
////            HibernateUtil.getSession().createQuery("from DetalleOrdenDeCompra doc WHERE EXISTS(FROM OrdenDeCompra oc WHERE oc=:idOC AND oc.detalle.id = doc.id) AND EXISTS(FROM DetalleRemito WHERE detalleOC.id=doc.id)");
//            OrdenDeCompra orden = (OrdenDeCompra)HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
//            for(DetalleOrdenDeCompra doc : orden.getDetalle()){
//                String[] datos = new String[4];
//                NTupla nt = new NTupla();
//                nt.setId(doc.getId());
//                nt.setNombre(doc.getRecurso().getNombre());
//                datos[0] = String.valueOf(doc.getCantidad());
//                datos[1] = String.valueOf(doc.getRecurso().getDescipcion());
//                datos[2] = String.valueOf(doc.getPrecio());
//
//                String query="SELECT COUNT(doc) FROM DetalleOrdenDeCompra doc WHERE doc.id=:idDOC AND doc.id IN(SELECT dre.detalleOC.id FROM DetalleRemito dre)";
//                long cantidad = (Long)HibernateUtil.getSession().createQuery(query).setParameter("idDOC", doc.getId()).uniqueResult();
//                if(cantidad>0)
//                    datos[3] = "true";
//                else
//                    datos[3] = "false";
//
//                nt.setData(datos);
//                detalles.add(nt);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return detalles;
//    }
//
//    /***************************************************************************
//     *                       METODOS DE RECEPCION
//     ***************************************************************************
//     */
//
//    public int registrarRecepcionTotal(int idOC) {
//        int idRemito = 0;
//        try {
//            HibernateUtil.beginTransaction();
//            OrdenDeCompra orden = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
//            Iterator it = orden.getDetalle().iterator();
//            DetalleOrdenDeCompra doc = null;
//            Remito re = new Remito();
//            ArrayList<DetalleRemito> listaDetalles = new ArrayList<DetalleRemito>();
//            while(it.hasNext()){
//                doc = (DetalleOrdenDeCompra)it.next();
//                DetalleRemito dre = new DetalleRemito();
//                dre.setCantidad(doc.getCantidad());// ACA ESTA MAL XQ EL USUARIO DEBERIA INGRESAR LO QUE REALMENTE RECIBIO... MOCO...
////                re.setDescripcion(doc.get); TAMBIEN NOS ESTA FALTANDO LA DESCRICION... TODO MAL..
//                dre.setDetalleOC(doc);
//                HibernateUtil.getSession().save(dre);
//                listaDetalles.add(dre);
//            }
//            re.setDetalle(listaDetalles);
//            re.setFechaEntrega(new Date());
//            re.setOrden(orden);
//            HibernateUtil.getSession().save(re);
//            orden.setEstadoRecibidaTotal();
//            HibernateUtil.getSession().update(orden);
//            idRemito = re.getId();
//            HibernateUtil.commitTransaction();
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//            HibernateUtil.rollbackTransaction();
//        }
//        return idRemito;
//    }
//
//    public int registrarRecepcionParcial(Integer idOC, TableModel model) {
//        DefaultTableModel dtm = (DefaultTableModel)model;
//        int idRemito = 0;
//        try {
//            HibernateUtil.beginTransaction();
//            ArrayList<DetalleRemito> listaDRE = new ArrayList<DetalleRemito>();
//            String query="FROM Remito WHERE orden.id=:idOC";
//            
//            OrdenDeCompra orden = (OrdenDeCompra)HibernateUtil.getSession().get(OrdenDeCompra.class, idOC);
//
//            int contadorRegistrados=0;
//            for(int i = 0;i<model.getRowCount();i++){
//                boolean esta = false;
//                if((Boolean)model.getValueAt(i, 0)){
//                    NTupla nt = (NTupla)model.getValueAt(i,2);
//                    Iterator<Remito> itRE = (Iterator<Remito>)HibernateUtil.getSession().createQuery(query).setParameter("idOC", idOC).iterate();
//                    while(itRE.hasNext()){
//                        Remito re = (Remito)itRE.next();
//                        Iterator itDRE = (Iterator)re.getDetalle().iterator();
//                        while(itDRE.hasNext()){
//                            DetalleRemito dr = (DetalleRemito)itDRE.next();
//                            if(nt.getId() == dr.getDetalleOC().getId()){
//                                esta = true;
//                                contadorRegistrados++;
//                            }
//                        }
//                    }
//                    if(!esta){
//                        DetalleRemito dre = new DetalleRemito();
//                        dre.setCantidad(Double.parseDouble(((String[])nt.getData())[0]));
//                        dre.setDescripcion(((String[])nt.getData())[1]);
//                        DetalleOrdenDeCompra doc = (DetalleOrdenDeCompra)HibernateUtil.getSession().load(DetalleOrdenDeCompra.class, nt.getId());
//                        dre.setDetalleOC(doc);
//                        HibernateUtil.getSession().save(dre);
//                        listaDRE.add(dre);
//                    }
//                }
//                 else{
//                    NTupla nt = (NTupla)model.getValueAt(i,2);
//                    Iterator<Remito> itRE = (Iterator<Remito>)HibernateUtil.getSession().createQuery(query).setParameter("idOC", idOC).iterate();
//                    while(itRE.hasNext()){
//                        Remito re = (Remito)itRE.next();
//                        Iterator itDRE = (Iterator)re.getDetalle().iterator();
//                        while(itDRE.hasNext()){
//                            DetalleRemito dr = (DetalleRemito)itDRE.next();
//                            if(nt.getId() == dr.getDetalleOC().getId()){
//                                contadorRegistrados++;
//                            }
//                        }
//                    }
//                 }
//            }
//            int total = listaDRE.size() + contadorRegistrados;
//            if(total == model.getRowCount()){
//                orden.setEstadoRecibidaTotal();
//            }else{
//                orden.setEstadoRecibidaParcial();
//            }
//            HibernateUtil.getSession().update(orden);
//            Remito re = new Remito();
//            re.setDetalle(listaDRE);
//            re.setFechaEntrega(new Date());
//            re.setOrden(orden);
//            HibernateUtil.getSession().save(re);
//            idRemito = re.getId();
//            HibernateUtil.commitTransaction();
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//            HibernateUtil.rollbackTransaction();
//        }
//        return idRemito;
//    }
//
//    public Tupla getEstadoOCSeleccionada(int id) {
//        Tupla estado = new Tupla();
//        try {
//            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
//            estado.setId(oc.getId());
//            estado.setNombre(oc.getEstado().getNombre());
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return estado;
//    }
//
//    public boolean verificarRecibidaParcial(int id){
//        Boolean estado = false;
//        try {
//            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
//            estado = oc.getEstado().esRecibidaParcial();
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return estado;
//    }
//
//    /***************************************************************************
//     *                 METODOS DE EMITIR ORDEN DE COMPRA
//     ***************************************************************************
//     */
//
//    public void emitirOrdenDeCompra(int id) {
//        try {
//            HibernateUtil.beginTransaction();
//            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
//            oc.setEstadoPendienteDeRecepcion();
//            oc.setFechaDePedido(new Date());
//            HibernateUtil.getSession().update(oc);
//            HibernateUtil.commitTransaction();
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//            HibernateUtil.rollbackTransaction();
//        }
//    }
//
//    public Map parametrosAImprimir(int id) {
//        Map params = new HashMap();
//        try {
//            OrdenDeCompra oc = (OrdenDeCompra) HibernateUtil.getSession().get(OrdenDeCompra.class, id);
//            params.put("idOC",id);
//            params.put("PROVEEDOR", oc.getProveedor().getRazonSocial());
//            params.put("CUIT", oc.getProveedor().getCuit());
//            params.put("DIRECCION", oc.getProveedor().getDomicilio().toString());
//        } catch (Exception ex) {
//            Logger.getLogger(GestorRegistrarRecepcionOC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return params;
//    }
}
