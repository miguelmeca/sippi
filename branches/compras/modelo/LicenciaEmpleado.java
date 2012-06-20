/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.Date;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class LicenciaEmpleado {

    private int oid;
    private Date fechaInicio;
    private Date fechaFin;
    private String motivo;
    private String estado;
    private Empleado empleado;
    private TipoLicenciaEmpleado tipoLicencia;

    public LicenciaEmpleado() {
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public TipoLicenciaEmpleado getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(TipoLicenciaEmpleado tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

}
