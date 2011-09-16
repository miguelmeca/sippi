package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.HibernateUtil;


public class Cotizacion {
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
    private List<SubObra> subObras;

    public void crear() {
    }

    public Cotizacion() {
        subObras = new ArrayList<SubObra>();
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
    
            
    
    public double CalcularTotal()
    {
        double monto = 0;
        for (int i = 0; i < subObras.size(); i++) 
        {
            SubObra so = subObras.get(i);
            monto += so.calcularSubtotal();
        }
        return monto;
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
    
}