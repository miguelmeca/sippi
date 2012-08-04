package modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class OrdenDeCompra {

    // EL NUMERO TAMBIEN ES EL ID !!!
    private int id;
    
    private Date fechaDeGeneracion;
    private Date fechaUltimaModificacion;
    
    private Proveedor proveedor;
    
    private FormaDePago formaDePago;
    
    private String estado;
    /**
     * En Creacion: Estado Falso, nunca lo tiene, solo es para mostrar mientras se crea !!!
     */
    public static final String ESTADO_EN_CREACION  = "En Creación";

    public static final String ESTADO_PENDIENTE  = "Pendiente";
    public static final String ESTADO_EMITIDA    = "Emitida";
    public static final String ESTADO_ANULADA    = "Anulada";
    
    public static final Color COLOR_ESTADO_PENDIENTE  = new Color(214,227,188);
    public static final Color COLOR_ESTADO_EMITIDA    = new Color(184,204,240);
    public static final Color COLOR_ESTADO_ANULADA    = new Color(229,184,183);    
    
    private String formaDeEntrega;
    public static final String[] FORMAS_DE_ENTREGA  = { "En la empresa",
                                                        "En el cliente",
                                                        "Envío a domicilio",
                                                        "A definir"};      
    
    private List<DetalleOrdenDeCompra> detalle;
    
    private RecepcionOrdenDeCompra recepcion;

    public OrdenDeCompra(){
        detalle = new ArrayList<DetalleOrdenDeCompra>();
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    public String getNombreProveedor()
    {
        if(this.proveedor!=null)
        {
            return this.proveedor.toString();
        }
        return "";
    }
            

    public RecepcionOrdenDeCompra getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(RecepcionOrdenDeCompra recepcion) {
        this.recepcion = recepcion;
    }
    
    public void addDetalleOrdenDeCompra(DetalleOrdenDeCompra doc) {
        detalle.add(doc);
    }
    
    /**
     * Calcula el Total de la Compra
     * @return 
     */
    public double calcularTotal()
    {
        double total = 0;
        for (int i = 0; i < detalle.size(); i++) {
            DetalleOrdenDeCompra doc = detalle.get(i);
            total += doc.calcularSubTotal();
        }
        return total;
    }
    
    /**
     * Calcula el Total de la Compra
     * @return 
     */
    public String mostrarCalcularTotal()
    {
        return "$ "+calcularTotal();
    }    

    
}
