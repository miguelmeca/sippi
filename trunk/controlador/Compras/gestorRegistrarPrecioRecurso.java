package controlador.Compras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Proveedor;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import vista.compras.pantallaRegistrarPrecioRecurso;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class gestorRegistrarPrecioRecurso {

    private pantallaRegistrarPrecioRecurso pantalla;

    public gestorRegistrarPrecioRecurso(pantallaRegistrarPrecioRecurso pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarProveedores()
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                List<Proveedor> listaProv = sesion.createQuery("from Proveedor").list();
                Iterator<Proveedor> iter = listaProv.iterator();
                while (iter.hasNext())
                {
                   Proveedor pro = iter.next();
                   if(pro.getEstado().esAlta())
                   {
                       Tupla t = new Tupla();
                       t.setId(pro.getId());
                       t.setNombre(pro.getRazonSocial());
                       lista.add(t);
                   }
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0020");
           }

           return lista;
    }

    public ArrayList<Tupla> buscarRecursoPorProveedor(int idProv)
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Proveedor prov = (Proveedor)sesion.load(Proveedor.class,idProv);

//                while (iter.hasNext())
//                {
//                   Proveedor pro = iter.next();
//                   if(pro.getEstado().esAlta())
//                   {
//                       Tupla t = new Tupla();
//                       t.setId(pro.getId());
//                       t.setNombre(pro.getRazonSocial());
//                       lista.add(t);
//                   }
//                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0020");
           }

           return lista;
    }

}
