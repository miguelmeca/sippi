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
    private int id = 0;
    
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
    
    private List<RecepcionOrdenDeCompra> recepciones;

    public OrdenDeCompra(){
        detalle = new ArrayList<DetalleOrdenDeCompra>();
        recepciones = new ArrayList<RecepcionOrdenDeCompra>();
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

    public List<RecepcionOrdenDeCompra> getRecepciones() {
        return recepciones;
    }

    public void setRecepciones(List<RecepcionOrdenDeCompra> recepciones) {
        this.recepciones = recepciones;
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

    /**
     * Para un detalle de esta orden de compra, ve cuantas unidades se recibieron y
     * están registradas en las recepciones parciales.
     * Por cada una de las recepciones, recorro su detalle y veo si linkean a la que se 
     * pasa por parametro, si es asi, sumo a la cantidad.
     * @param doc
     * @return 
     */
    public double getCantidadTotalRecibida(DetalleOrdenDeCompra doc)
    {
        double cantidad = 0;
        
        for (int i = 0; i < this.recepciones.size(); i++) {
            RecepcionOrdenDeCompra roc = this.recepciones.get(i);
            for (int j = 0; j < roc.getRecepcionesParciales().size(); j++) {
                DetalleRecepcionOrdenDeCompra droc = roc.getRecepcionesParciales().get(j);
                if(droc.getDetalleOrdenDeCompra().getId()==doc.getId())
                {
                    cantidad += droc.getCantidad();
                }                    
            }
        }
        
        return cantidad;
    }

    /**
     * Recorre las recepciones y calcula el estado de la recepcion en General
     * @return 
     */
    public String getEstadoRecepciones() {
        
        if(recepciones.isEmpty())
        {
            return RecepcionOrdenDeCompra.ESTADO_PENDIENTE;
        }
        
        boolean esTotal = true;
        
        for (int i = 0; i < detalle.size(); i++) {
            DetalleOrdenDeCompra doc = detalle.get(i);
            double cantidadRecibida = getCantidadTotalRecibida(doc);
            double cantitadTotal    = doc.getCantidad();
            if(cantidadRecibida<cantitadTotal)
            {
                esTotal = false;
            }
        }
        
        if(esTotal)
        {
            return RecepcionOrdenDeCompra.ESTADO_RECIBIDA_TOTALMENTE;
        }
        return RecepcionOrdenDeCompra.ESTADO_RECIBIDA_PARCIALMENTE;
    }
    
}
