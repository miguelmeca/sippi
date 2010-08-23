/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

import java.util.Date;

/**
 *
 * @author Emmanuel
 */
public interface IPantallaPedidoABM {

    public void setNumeroPedido(String nro);
    
    public void setNombreObra(String nombre);
    
    public void setDescripcionObra(String desc);
    
    public void setEmpresaCliente(int id);
    
    public void setPlanta(int id);
    
    public void setFechaInicio(Date fInicio);
    
    public void setFechaFin(Date fFin);
    
    public void setMontoPedido(String monto);

    public void setFechaLEP(Date fLEP);
    
    public void setFechaLVP(Date fLVP);

    public void setPliegosPedido(String pliegos);

    public void setPlanosPedido(String pedidos);

    public void setContactoResponsable(int idContacto);

    public void mostrarEmpresasCliente();

    public void mostrarPlantasEmpresaCliente();

    public void setEstadoPedidoObra(String nombre);
}
