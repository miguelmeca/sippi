package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.FechaUtil;

public class PedidoObra{
    
    public final static String ESTADO_CANCELADO              = "Cancelado";
    public final static String ESTADO_CONFIRMADO             = "Confirmado";
    public final static String ESTADO_EN_EJECUCION           = "En Ejecución";
    public final static String ESTADO_PENDIENTE              = "Pendiente";
    public final static String ESTADO_PLANIFICADO            = "Planificado";
    public final static String ESTADO_PRESUPUESTADO          = "Presupuestado";
    public final static String ESTADO_SOLICITADO             = "Solicitado";
    public final static String ESTADO_SUSPENDIDO             = "Suspendido";
    public final static String ESTADO_TERMINADO              = "Terminado";
    
    private int id; // Tb guarda el numero del pedido
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private double presupuestoMaximo;
    private FormaDePago formaPago;
    private List<Cotizacion> cotizaciones;
    private List<ContactoResponsable> contactos;
    private Planta planta;
    private Date fechaDeRegistro;
    private Planificacion planificacion;
    private Ejecucion ejecucion;

    private String estado;

    public PedidoObra() 
    {
        this.estado = PedidoObra.ESTADO_SOLICITADO;
        
        this.contactos = new ArrayList<ContactoResponsable>();

        if(cotizaciones==null)
        {
            cotizaciones = new ArrayList<Cotizacion>();
        }

    }

    public List<Cotizacion> getCotizaciones()
    {
        return cotizaciones;
    }

    public void addCotizaciones(Cotizacion c)
    {
        cotizaciones.add(c);
    }

    // ESTE LO PIDE HIBERNATE
    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public void addContacto(ContactoResponsable c){
        this.contactos.add(c);
    }

    public List<ContactoResponsable> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoResponsable> contacto) {
        this.contactos = contacto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public void setFechaDeRegistro(Date fechaDeRegistro) {
        this.fechaDeRegistro = fechaDeRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return id;
    }

    public void setNumero(int numero) {
        this.id = numero;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public Date getFechaFin() {
        return fechaFin;
    }
    
    public String mostrarFechaFin() {
        return FechaUtil.getFecha(fechaFin);
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public String mostrarFechaInicio() {
        return FechaUtil.getFecha(fechaInicio);
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public FormaDePago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaDePago formaPago) {
        this.formaPago = formaPago;
    }

    public double getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public void setPresupuestoMaximo(double presupuestoMaximo) {
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public String mostrarPlanta()
    {
        if(this.planta!=null)
        {
            return this.planta.getRazonSocial();
        }
        return "";
    }

    public Ejecucion getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(Ejecucion ejecucion) {
        this.ejecucion = ejecucion;
    }

/*************************************************************
 *                    MANEJO DE ESTADOS                      *
 * ***********************************************************
 */
    
    public String getEstado()
    {
        return this.estado;
    }

    public boolean setEstadoPresupuestado()
    {
        if(this.estado.equals(PedidoObra.ESTADO_SOLICITADO))
        {
            this.estado = PedidoObra.ESTADO_PRESUPUESTADO;
            return true;
        }
        return false;
    }

    public boolean setEstadoPendiente()
    {
        if(this.estado.equals(PedidoObra.ESTADO_PRESUPUESTADO))
        {
            this.estado = PedidoObra.ESTADO_PENDIENTE;
            return true;
        }
        return false;           
    }

    public boolean setEstadoConfirmado()
    {
        if(this.estado.equals(PedidoObra.ESTADO_PENDIENTE))
        {
            this.estado = PedidoObra.ESTADO_CONFIRMADO;
            return true;
        }
        return false;           
    }

    public boolean setEstadoPlanificado()
    {
        if(this.estado.equals(PedidoObra.ESTADO_CONFIRMADO))
        {
            this.estado = PedidoObra.ESTADO_PLANIFICADO;
            return true;
        }
        return false;               
    }

    public boolean setEstadoEnEjecucion()
    {
        this.estado = PedidoObra.ESTADO_EN_EJECUCION;
        return true;
    }

    public boolean setEstadoSuspendido()
    {
        if(this.estado.equals(PedidoObra.ESTADO_EN_EJECUCION))
        {
            this.estado = PedidoObra.ESTADO_SUSPENDIDO;
            return true;
        }
        return false;  
    }

    public boolean setEstadoTerminado()
    {
        if(this.estado.equals(PedidoObra.ESTADO_EN_EJECUCION))
        {
            this.estado = PedidoObra.ESTADO_TERMINADO;
            return true;
        }
        return false;          
    }

    public boolean setEstadoCancelado()
    {
        if(!this.estado.equals(PedidoObra.ESTADO_TERMINADO))
        {
            this.estado = PedidoObra.ESTADO_CANCELADO;
            return true;
        }
        return false;           
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Planificacion getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(Planificacion planificacion) {
        this.planificacion = planificacion;
    }
}