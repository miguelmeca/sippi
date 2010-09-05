/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.PrecioSegunCantidad;
import modelo.Proveedor;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.Rubro;
import org.hibernate.Session;
import util.HibernateUtil;
import util.LogUtil;
import util.NTupla;
import util.RubroUtil;
import util.Tupla;
import vista.compras.pantallaConsultarPrecioXProveedor;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class GestorConsultarPrecioXProveedor {

    private pantallaConsultarPrecioXProveedor pantalla;

    public GestorConsultarPrecioXProveedor(pantallaConsultarPrecioXProveedor pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarRubrosPorProveedor(int idProv)
    {

        ArrayList<Tupla> lista = new ArrayList<Tupla>();

         Session sesion;
           try 
           {
                sesion = HibernateUtil.getSession();
                Proveedor p = (Proveedor)sesion.load(Proveedor.class,idProv);
                
                Iterator<Rubro> it = p.getRubros().iterator();
                while (it.hasNext()) 
                {
                    Rubro rb = (Rubro)it.next();
                    Tupla tp = new Tupla(rb.getId(),rb.getNombre());
                    lista.add(tp);
                }
           }
           catch(Exception e)
           {
               LogUtil.addError("ConsultarPrecioXProveedor: No se pudo cargar la lista de Rubros");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0002");
           }
        return lista;

    }

    public ArrayList<Tupla> mostrarProveedores()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try 
           {
                sesion = HibernateUtil.getSession();

                List<Proveedor> listaProv = sesion.createQuery("FROM Proveedor").list();

                Iterator<Proveedor> it = listaProv.iterator();
                while (it.hasNext()) 
                {

                   Proveedor pv = it.next();
                       Tupla tp = new Tupla(pv.getId(),pv.getRazonSocial());
                       lista.add(tp);
                }
                
           }
           catch(Exception e)
           {
               LogUtil.addError("ConsultarPrecioXProveedor: No se pudo cargar la lista de Proveedores");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0001");
           }
        return lista;
    }

     public ArrayList<NTupla> mostrarPreciosPorProveedor(int idProv, int idRubro)
     {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

           Session sesion;
           try
           {
                sesion = HibernateUtil.getSession();
                Proveedor p = (Proveedor)sesion.load(Proveedor.class,idProv);
                Rubro r = RubroUtil.getRubro(idRubro);

                // Veo en la lista de Recursos que tiene el proveedor
                Iterator<RecursoEspecifico> itre = p.getListaArticulos().iterator();
                while (itre.hasNext())
                {
                   RecursoEspecifico re = itre.next();

                   NTupla ntp = new NTupla();
                   ntp.setId(ntp.getId());
                   ntp.setNombre(re.getNombre());

                   String[] data = new String[3];
                   data[0] = re.getDescipcion();
                   data[1] = "<HTML><BODY> ";
                   // Tengo los datso del recurso, ahora a los bifes, a los precios
                   RecursoXProveedor urxp = re.getUltimoRecursoXProveedor();
                   Iterator<PrecioSegunCantidad> itpsc = urxp.getListaPrecios().iterator();

                   while (itpsc.hasNext())
                    {
                        PrecioSegunCantidad psc = itpsc.next();
                        data[1] = data[1]+psc.getCantidad()+" <b>x</b> $"+psc.getPrecio()+"<br>";
                    }

                   ntp.setData(data);
                   lista.add(ntp);

                }


           }
           catch(Exception e)
           {
               LogUtil.addError("ConsultarPrecioXProveedor: No se pudo cargar la tabla de precios");
               e.printStackTrace();
               pantalla.MostrarMensaje("ME-0003");
           }

        return lista;
     }


}
