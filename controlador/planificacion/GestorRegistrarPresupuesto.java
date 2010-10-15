/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import modelo.PedidoObra;
import modelo.Presupuesto;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.LogUtil;
import vista.planificacion.pantallaRegistrarPresupuesto;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorRegistrarPresupuesto {

    private pantallaRegistrarPresupuesto pantalla;
    private Presupuesto presupuesto;
    private PedidoObra obra;

    public GestorRegistrarPresupuesto(pantallaRegistrarPresupuesto pantalla) {
        this.pantalla = pantalla;

    }

    public void cargarPresupuesto(int id)
    {
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                this.presupuesto = (Presupuesto)sesion.load(Presupuesto.class,id);
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo el presupuesto");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0001");
           }
    }

    public void cargarObra(int idObra)
    {
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                this.obra = (PedidoObra)sesion.load(PedidoObra.class,idObra);
                pantalla.mostrarDatosObra(obra.getId(),obra.getNombre(),obra.getPlanta().getRazonSocial());
                pantalla.mostrarFechaInicioYFin(FechaUtil.getFecha(obra.getFechaInicio()),FechaUtil.getFecha(obra.getFechaFin()));
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo cargar la Obra");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0004");
           }
    }

    

}
