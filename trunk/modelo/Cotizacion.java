package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.HibernateUtil;


public class Cotizacion {
    
    public final static String ESTADO_EN_CREACION          = "En Creación";
    public final static String ESTADO_PENDIENTE_ACEPTACION = "Pendiente de Aceptación";
    public final static String ESTADO_ACEPTADO             = "Aceptado";
    public final static String ESTADO_RECHAZADO            = "Rechazado";
    public final static String ESTADO_DESCARTADO           = "Descartado";
    
    private int id;
    private String nroCotizacion;
    private int nroRevision;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String plazoEntrega;
    private String lugarEntrega;
    private Date validezOferta;
    private String descripcion;
    private Date fechaLimiteEntrega;
    protected List<SubObra> subObras;
    
    private String estado;

    public Cotizacion() 
    {
        subObras = new ArrayList<SubObra>();
        // Estado por default
        estado = Cotizacion.ESTADO_EN_CREACION;
        // nro de Cotizacion por default EMPTY
        nroCotizacion = new String();
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLimiteEntrega() {
        return fechaLimiteEntrega;
    }

    public void setFechaLimiteEntrega(Date fechaLimiteEntrega) {
        this.fechaLimiteEntrega = fechaLimiteEntrega;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public int getNroRevision() {
        return nroRevision;
    }

    public void setNroRevision(int nroRevision) {
        this.nroRevision = nroRevision;
    }
    public String getNroCotizacion() {
        return nroCotizacion;
    }

    public void setNroCotizacion(String nroCotizacion) {
        this.nroCotizacion = nroCotizacion;
    }

    public String getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(String plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public List<SubObra> getSubObras() {
        return subObras;
    }

    public void setSubObras(List<SubObra> subObras) {
        this.subObras = subObras;
    }

    public Date getValidezOferta() {
        return validezOferta;
    }

    public void setValidezOferta(Date validezOferta) {
        this.validezOferta = validezOferta;
    }

    public void addSubObra(SubObra so){
        this.subObras.add(so);
    }
    
    public PedidoObra buscarPedidoObra()
    {
        PedidoObra PO=null;
        try{            
            //PO= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in indices(PO.cotizaciones)").setParameter("cID", this.id).uniqueResult(); //No se pq mierda no funciona
            PO= (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in elements(PO.cotizaciones)").setParameter("cID", this).uniqueResult();
        }
        catch(Exception e){
            System.out.println("ERROR:"+e.getMessage()+"|");
            e.printStackTrace();
       }
        return PO;
        
    }   
    
    public String getCalcularTotal()
    {
        return "$ "+String.valueOf(CalcularTotal());
    }
    
    public double CalcularTotal()
    {
        double monto = 0;
        for (int i = 0; i < subObras.size(); i++) 
        {
            SubObra so = subObras.get(i);
            monto += so.calcularSubtotal();
        }
        return Math.rint(monto*100)/100; // Redondeo
    }
    
    public double CalcularMontoBase()
    {
        return 0;
    }    
    
    public String toString()
    {
       String aux= buscarPedidoObra().getNombre()+" Cotizacion N° "+nroCotizacion+"-Revisión N°"+nroRevision;
    
        return aux;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean setEstadoDescartado()
    {
        if(this.estado.equals(Cotizacion.ESTADO_EN_CREACION))
        {
            this.estado = Cotizacion.ESTADO_DESCARTADO;
            return true;
        }
        return false;
    }
  
    public boolean setEstadoPendienteAceptacion()
    {
        if(this.estado.equals(Cotizacion.ESTADO_PENDIENTE_ACEPTACION))
        {
            return true;
        }       
        if(this.estado.equals(Cotizacion.ESTADO_EN_CREACION))
        {
            this.estado = Cotizacion.ESTADO_PENDIENTE_ACEPTACION;
            return true;
        }
        return false;        
    }    
    
    public boolean setEstadoAceptado()
    {
        if(this.estado.equals(Cotizacion.ESTADO_PENDIENTE_ACEPTACION))
        {
            this.estado = Cotizacion.ESTADO_ACEPTADO;
            return true;
        }
        return false;         
    }
    
    public boolean setEstadoRechazado()
    {
        if(this.estado.equals(Cotizacion.ESTADO_PENDIENTE_ACEPTACION))
        {
            this.estado = Cotizacion.ESTADO_RECHAZADO;
            return true;
        }
        return false;         
    }    
    
    
    public SubObraXMaterial getSubObraXMaterialPorHash(int hashCode)
    {
        for (int i = 0; i < subObras.size(); i++) {
            
            SubObraXMaterial soxm =subObras.get(i).getSubObraXMaterialPorHash(hashCode);
            if(soxm!=null)
            { return soxm;}            
        }
        return null;
    }
    public SubObraXAdicional getSubObraXAdicionalPorHash(int hashCode)
    {
        for (int i = 0; i < subObras.size(); i++) {
            
            SubObraXAdicional soxa =subObras.get(i).getSubObraXAdicionalPorHash(hashCode);
            if(soxa!=null)
            { return soxa;}            
        }
        return null;
    }
    public SubObraXHerramienta getSubObraXHerramientaPorHash(int hashCode)
    {
        for (int i = 0; i < subObras.size(); i++) {
            
            SubObraXHerramienta soxh =subObras.get(i).getSubObraXHerramientaPorHash(hashCode);
            if(soxh!=null)
            { return soxh;}            
        }
        return null;
    }
    public SubObraXTarea getSubObraXTareaPorHash(int hashCode)
    {
        for (int i = 0; i < subObras.size(); i++) {
            
            SubObraXTarea soxt =subObras.get(i).getSubObraXTareaPorHash(hashCode);
            if(soxt!=null)
            { return soxt;}            
        }
        return null;
    }
    public SubObraXAlquilerCompra getSubObraXAlquilerCompraPorHash(int hashCode)
    {
        for (int i = 0; i < subObras.size(); i++) {
            
            SubObraXAlquilerCompra soxac =subObras.get(i).getSubObraXAlquilerCompraPorHash(hashCode);
            if(soxac!=null)
            { return soxac;}            
        }
        return null;
    }
    
    
}