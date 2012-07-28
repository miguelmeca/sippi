package modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class OrdenDeCompra {

    private int id;
    private int numero;
    
    private Date fechaDeGeneracion;
    private Date fechaUltimaModificacion;
    
    private Proveedor proveedor;
    
    private FormaDePago formaDePago;
    
    private String estado;
    public static final String ESTADO_EN_CREACION  = "En Creación"; //Estado Falso, nunca lo tiene, solo es para mostrar mientras se crea !!!
    public static final String ESTADO_PENDIENTE  = "Pendiente";
    public static final String ESTADO_EMITIDA    = "Emitida";
    public static final String ESTADO_ANULADA    = "Anulada";
    
    private String formaDeEntrega;
    public static final String[] FORMAS_DE_ENTREGA  = { "En la empresa",
                                                        "En el cliente",
                                                        "Envío a domicilio",
                                                        "A definir"};      
    
    private List<DetalleOrdenDeCompra> detalle;
    
    private RecepcionOrdenDeCompra recepcion;

    public OrdenDeCompra(){
    }

    public List<DetalleOrdenDeCompra> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleOrdenDeCompra> detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDeGeneracion() {
        return fechaDeGeneracion;
    }

    public void setFechaDeGeneracion(Date fechaDeGeneracion) {
        this.fechaDeGeneracion = fechaDeGeneracion;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getFormaDeEntrega() {
        return formaDeEntrega;
    }

    public void setFormaDeEntrega(String formaDeEntrega) {
        this.formaDeEntrega = formaDeEntrega;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(FormaDePago formaDePago) {
        this.formaDePago = formaDePago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public RecepcionOrdenDeCompra getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(RecepcionOrdenDeCompra recepcion) {
        this.recepcion = recepcion;
    }

    
}
