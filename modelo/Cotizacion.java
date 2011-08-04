package modelo;

import java.util.Date;
import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : Cotizacion.java
//  @ Date : 08/10/2010
//  @ Author : 
//
//




public class Cotizacion {
    private int id;
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
}