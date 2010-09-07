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
    private Date fechaDePedido;
    private Date fechaDeRecepcion;
    private Proveedor proveedor;
    private List<DetalleOrdenDeCompra> detalle;
    private FormaDePago formaDePago;
    private EstadoOrdenDeCompra estado;
    private String hib_flag_estado;

    public OrdenDeCompra()
    {
        this.hib_flag_estado = "modelo.EstadoOrdenDeCompraGenerada";
    }

    public OrdenDeCompra(List<RecursoEspecifico> lstRec, String[] lstDescrip, double[] lstCantidades, double[] lstPrecios, Proveedor p)
    {
        this.hib_flag_estado = "modelo.EstadoOrdenDeCompraGenerada";
        estado=this.getEstado();
        proveedor=p;
        detalle=new ArrayList<DetalleOrdenDeCompra>();
        try
        {
            for (int i = 0; i < lstRec.size(); i++)
            {
               DetalleOrdenDeCompra doc=new DetalleOrdenDeCompra(lstCantidades[i], lstPrecios[i],lstDescrip[i], lstRec.get(i));

                detalle.add(doc);

            }
        }
        catch(Exception e)
        {
            System.err.println("ERORRRRRR:  Lista para detalles pasadas a OrdenDeCompra mal cargadas");
        }


    }

    public Date getFechaDePedido() {
        return fechaDePedido;
    }

    public void setFechaDePedido(Date fechaDePedido) {
        this.fechaDePedido = fechaDePedido;
    }

    public Date getFechaDeRecepcion() {
        return fechaDeRecepcion;
    }

    public void setFechaDeRecepcion(Date fechaDeRecepcion) {
        this.fechaDeRecepcion = fechaDeRecepcion;
    }

    public String getHib_flag_estado() {
        return hib_flag_estado;
    }

    public void setHib_flag_estado(String hib_flag_estado) {
        this.hib_flag_estado = hib_flag_estado;
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

    public List<DetalleOrdenDeCompra> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleOrdenDeCompra> detalle) {
        this.detalle = detalle;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(FormaDePago formaDePago) {
        this.formaDePago = formaDePago;
    }

/*************************************************************
 *                    MANEJO DE ESTADOS                      *
 * ***********************************************************
 */
    
    public EstadoOrdenDeCompra getEstado()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado==null)
            {
                try {
                        //Class estadoAux = Class.forName(this.hib_flag_estado);
                        EstadoOrdenDeCompra estadoAux = (EstadoOrdenDeCompra) Class.forName(this.hib_flag_estado).newInstance();
                        this.estado = estadoAux;
                        return estado;
                    }
                    catch (Exception ex)
                    {
                        System.out.println("No se encontro la clase Estado Concreto");
                    }
            }
            else
            {
                return this.estado;
            }

        }
        else
        {
            System.out.println("Carga el objeto antes de usarlo");
            return null;
        }
        return null;
    }

    public void setEstadoPendienteDeRecepcion()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado.esGenerada())
            {
                ((EstadoOrdenDeCompraGenerada)this.estado).setPendiente(this);
            }
        }
    }

    public void setEstadoRecibidaParcial()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado.esPendiente())
            {
                ((EstadoOrdenDeCompraPendienteDeRecepcion)this.estado).setRecibidaParcial(this);
            }else{
                if(this.estado.esRecibidaParcial()){
                    ((EstadoOrdenDeCompraRecibidaParcial)this.estado).setRecibidaParcial(this);
                }
            }
        }
    }

    public void setEstadoRecibidaTotal()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado.esPendiente())
            {
                ((EstadoOrdenDeCompraPendienteDeRecepcion)this.estado).setRecibidaTotal(this);
            }else{
                if(this.estado.esRecibidaParcial()){
                    ((EstadoOrdenDeCompraRecibidaParcial)this.estado).setRecibidaTotal(this);
                }
            }
        }
    }


    public void setEstadoCancelado()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado.esGenerada()){
                ((EstadoOrdenDeCompraGenerada)this.estado).setCancelado(this);
            }
        }
    }

    public void setEstadoAnulada()
    {
        if(this.id!=0) // Objeto no cargado
        {
            if(this.estado.esPendiente()){
                ((EstadoOrdenDeCompraPendienteDeRecepcion)this.estado).setAnulada(this);
            }
        }
    }

    public void setEstado(EstadoOrdenDeCompra estado) {
        this.estado = estado;
    }

}
