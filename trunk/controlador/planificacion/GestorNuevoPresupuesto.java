/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.PedidoObra;
import modelo.Presupuesto;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.LogUtil;
import util.NTupla;
import vista.planificacion.pantallaNuevoPresupuesto;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class GestorNuevoPresupuesto {

    private pantallaNuevoPresupuesto pantalla;

    public GestorNuevoPresupuesto(pantallaNuevoPresupuesto pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<NTupla> getObrasPorPresupuestar()
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                Iterator<PedidoObra> iter = sesion.createQuery("from PedidoObra").iterate();
                while (iter.hasNext())
                {
                   PedidoObra po = iter.next();
                   NTupla nt = new NTupla(po.getId());
                   nt.setNombre(po.getNombre());
                   String[] data = new String[3];
                    data[0] = po.getPlanta().getRazonSocial();
                    data[1] = FechaUtil.getFecha(po.getFechaInicio());
                    data[2] = FechaUtil.getFecha(po.getFechaFin());
                   nt.setData(data);
                   lista.add(nt);
               }

           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo cargar la lista de Obras");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0001");
           }
        

        return lista;

    }

    public String getDescripcionPlantilla(int id)
    {
        return "Crea un nuevo presupuesto en blanco sin ningún tipo de elementos";
    }

    public void crearPresupuesto(int idObra, String plantilla)
    {
        // 3 PASOS, CREO EL PRESUPUESTO, SE LO PASO A LA OBRA Y ABRO EL EDITOR
        // PASO 1 ) CREO EL PRESUPUESTO
        Presupuesto pre = new Presupuesto();
        pre.setVersion("1");

           // LO GUARDO
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                HibernateUtil.beginTransaction();
                sesion.saveOrUpdate(pre);
                HibernateUtil.commitTransaction();
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo crear el presupuesto");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0003");
               return;
           }

      // PASO 2 ) SE LO PASO AL PEDIDO DE OBRA
      PedidoObra obra = cargarObra(idObra);
      if(obra!=null)
      {
          obra.addPresupuesto(pre);

           try
           {
                HibernateUtil.beginTransaction();
                sesion.saveOrUpdate(obra);
                HibernateUtil.commitTransaction();
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo agregar el presupuesto al Pedido de Obra");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0005");
               return;
           }
      }
      else
      {
          pantalla.MostrarMensaje("ME-0004");
          return;
      }

      // PASO 3 ) LANZO LA PANTALLA DE EDICION (DESDE LA INTERFAZ)
      pantalla.abrirEditorPresupuesto(pre.getId(),idObra);

      // ESCONDO LA PANTALLA
      pantalla.dispose();
    }

    public PedidoObra cargarObra(int idObra)
    {
           PedidoObra obra = null;
           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                obra = (PedidoObra)sesion.load(PedidoObra.class,idObra);
           }
           catch(Exception e)
           {
               LogUtil.addError("No se pudo cargar la Obra");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0004");
           }
           return obra;
    }

}
