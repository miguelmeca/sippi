package modelo;

import java.util.Date;

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
    private EstadoOrdenDeCompra estado;
    private String hib_flag_estado;

    public OrdenDeCompra() {
    }

    public EstadoOrdenDeCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenDeCompra estado) {
        this.estado = estado;
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



}
