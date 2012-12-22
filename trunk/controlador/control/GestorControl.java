package controlador.control;

import controlador.informes.GestorInformesGenerales;
import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import util.HibernateUtil;
import util.Tupla;
import vista.control.VentanaControl;

/**
 *
 * @author Iuga
 */
public class GestorControl {

    private PedidoObra pedidoObra;

    public GestorControl(int idPedidoObra) {
        cargarPedidoObra(idPedidoObra);
    }

    /**
     * Cargo el pedido de Obra a controlar
     *
     * @param idPedidoObra
     * @return
     */
    private void cargarPedidoObra(int idPedidoObra) {
        try {
            HibernateUtil.beginTransaction();
            this.pedidoObra = (PedidoObra) HibernateUtil.getSession().load(PedidoObra.class, idPedidoObra);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
        }
    }

    public String getNombreObra() {
        if (this.pedidoObra != null) {
            return this.pedidoObra.getNombre();
        }
        return "";
    }

    public String getNombreClienteObra() {
        if (this.pedidoObra != null) {
            if (this.pedidoObra.getPlanta() != null) {
                gestorBDvarios gbd = new gestorBDvarios();
                EmpresaCliente cliente = gbd.buscarEmpresaCliente(this.pedidoObra.getPlanta());
                if (cliente != null) {
                    return cliente.getRazonSocial() + " - " + this.pedidoObra.getPlanta().getRazonSocial();
                }
                return "";
            }
            return "";
        }
        return "";
    }

    public String getNumeroObra() {
        if (this.pedidoObra != null) {
            return "" + this.pedidoObra.getNumero();
        }
        return "";
    }

    public int getIDObra() {
        if (this.pedidoObra != null) {
            return this.pedidoObra.getNumero();
        }
        return 0;
    }

    public String getEstadoPedidoObra() {
        if (this.pedidoObra != null) {
            return this.pedidoObra.getEstado();
        }
        return PedidoObra.ESTADO_SOLICITADO;
    }

    public String getEstadoCotizacionObra() {
        // Veo si tiene cotizaciones
        if (this.pedidoObra != null) {
            if (this.pedidoObra.getCotizaciones() != null && this.pedidoObra.getCotizaciones().size() > 0) {
                // Tengo Cotizaciones
                // Si tengo planificacion, una esta aceptada, sino, solo las cuento
                if (this.pedidoObra.getPlanificacion() != null) {
                    // Tengo planificacion, una Cot es la que vale
                    if (this.pedidoObra.getPlanificacion().getCotizacion() != null
                            && this.pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal() != null) {
                        return this.pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal().getEstado();
                    }
                }
            }

            if (this.pedidoObra.getCotizaciones() != null && this.pedidoObra.getCotizaciones().size() > 0) {
                return Cotizacion.ESTADO_EN_CREACION;
            }

        }
        return VentanaControl.MENSAJE_NO_ESTA_COTIZADO;
    }

    public String getEstadoPlanificacionObra() {
        // Veo si tiene cotizaciones
        if (this.pedidoObra != null) {
            if (this.pedidoObra.getPlanificacion() != null) {
                return this.pedidoObra.getPlanificacion().getEstado();
            }
        }
        return VentanaControl.MENSAJE_NO_ESTA_PLANIFICADO;
    }

    public String getEstadoEjecucionObra() {
        // Veo si tiene cotizaciones
        if (this.pedidoObra != null) {
            if (this.pedidoObra.getEjecucion() != null) {
                return this.pedidoObra.getEjecucion().getEstado();
            }
        }
        return VentanaControl.MENSAJE_NO_ESTA_EJECUTADO;
    }

    public void emitirInformeControl() throws Exception {
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    GestorInformesGenerales gestorInformes = new GestorInformesGenerales(new Date(), new Date());
                    gestorInformes.generarInformeControlObras(pedidoObra);

                } catch (Exception e) {
                    System.err.println("ERROR:" + e.getMessage());
                }
            }
        });
        thread5.start();
    }

    public PedidoObra getPedidoObraActual() {
        return pedidoObra;
    }
}
