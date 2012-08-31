/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Compras;

import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Date;
import modelo.*;
import util.RubroUtil;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import util.NTupla;
import util.FechaUtil;
import vista.compras.pantallaConsultarPrecioXRecurso;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Fran
 */
public class GestorConsultarPrecioXRecurso {

    private pantallaConsultarPrecioXRecurso pantalla;
    ArrayList<PrecioSegunCantidad> listaPrecios;
    private boolean mostroPrecios = false;
    // NO CAMBIAR EL ORDEN !!
    private String[] conceptos = {"Materiales", "Herramientas", "Alquileres y Compras", "Adicionales"};

    public GestorConsultarPrecioXRecurso(pantallaConsultarPrecioXRecurso pantalla) {
        this.pantalla = pantalla;
        listaPrecios = new ArrayList<PrecioSegunCantidad>();

    }

    public ArrayList<Tupla> mostrarRubros() {
//   ArrayList<Tupla> lista = RubroUtil.getTuplasRubros();
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        
        for (int i = 0; i < conceptos.length; i++) {
            Tupla t = new Tupla();
            t.setId(i);
            t.setNombre(conceptos[i]);
            lista.add(t);
        }
        mostroPrecios = false;
        return lista;
    }

    public ArrayList<Tupla> mostrarRecursos(int idTipoRec) {
        mostroPrecios = false;
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        Session sesion;
        try {
            sesion = HibernateUtil.getSession();

            List<IComprable> listaRecursos = null;
            switch (idTipoRec) {
                case 0: // Materiales
                { listaRecursos = sesion.createQuery("from Material").list(); break; }
                case 1: // Herramientas
                { listaRecursos = sesion.createQuery("from Herramienta").list(); break; }
                case 2: // Alquileres y Compras
                { listaRecursos = sesion.createQuery("from TipoAlquilerCompra").list(); break; }
                case 3: // Adicionales
                { listaRecursos = sesion.createQuery("from TipoAdicional").list(); break; }
            }
            
            Iterator<IComprable> it = listaRecursos.iterator();
            while (it.hasNext()) {
                IComprable c = it.next();
                int id = 0;
                switch(idTipoRec)
                {
                    case 0: {id = ((Material)c).getId(); break;}
                    case 1: {id = ((Herramienta)c).getId(); break;}
                    case 2: {id = ((TipoAlquilerCompra)c).getId(); break;}
                    case 3: {id = ((TipoAdicional)c).getId(); break;}
                }
                Tupla tupla = new Tupla(id, c.getNombre());
                lista.add(tupla);
            }
        } catch (Exception ex) {
            System.out.println("No se pudo cargar el objeto: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return lista;
    }

    public ArrayList<Tupla> mostrarRecursosEspecificos(int idRec) {
        mostroPrecios = false;
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        Session sesion;
        try {
            sesion = HibernateUtil.getSession();
            Recurso rec = (Recurso) sesion.createQuery("from Recurso re where re.id = " + idRec).uniqueResult();
            List<RecursoEspecifico> listaRecursosE = rec.getRecursos();
            Iterator<RecursoEspecifico> it = listaRecursosE.iterator();
            while (it.hasNext()) {
                RecursoEspecifico re = it.next();
                Tupla tpla = new Tupla(re.getId(), re.getNombre());
                lista.add(tpla);
            }
        } catch (Exception ex) {
            System.out.println("No se pudo cargar el objeto: " + ex.getMessage());
            ex.printStackTrace();
        }

        return lista;
    }

    public ArrayList<NTupla> mostrarProveedores(int idRE) {
        mostroPrecios = false;
        Session sesion;
        ArrayList<NTupla> listaProveedores = new ArrayList<NTupla>();
        try {
            sesion = HibernateUtil.getSession();
            RecursoEspecifico recE = (RecursoEspecifico) sesion.createQuery("from RecursoEspecifico where id = " + idRE).uniqueResult();
            for (int i = 0; i < recE.getProveedores().size(); i++) {
                NTupla nt = new NTupla();
                Proveedor prov = recE.getProveedores().get(i).getProveedor();
                nt.setId(prov.getId());
                nt.setNombre(prov.getRazonSocial());
                nt.setData(prov.getConfiabilidad());
                listaProveedores.add(nt);
            }

        } catch (Exception ex) {
            System.out.println("No se pudo cargar el objeto: " + ex.getMessage());
            ex.printStackTrace();

        }
        return listaProveedores;
    }
    
    /**
     * Método que muestra los proveedores de Un Alquiler/Compra o un Adicional
     * @param rubro El nombre del Rubro
     * @return lista de Proveedores
     */
    public ArrayList<NTupla> mostrarProveedores(String rubro) { // Sólo para Alquiler/Compras y Adicionales
        mostroPrecios = false;
        Session sesion;
        ArrayList<NTupla> listaProveedores = new ArrayList<NTupla>();
        try {
            sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            List<Proveedor> proveedores = sesion.createQuery("FROM Proveedor").list();
            Iterator<Proveedor> itProv = proveedores.iterator();
            while(itProv.hasNext())
            {
                Proveedor proveedor = itProv.next();
//                Iterator<Rubro> itRubros = proveedor.getRubros().iterator();
//                while(itRubros.hasNext())
//                {
//                    Rubro rubroAux = itRubros.next();
//                    if(rubroAux.getNombre().equals(rubro))
//                    {
                        NTupla nt = new NTupla();
                        nt.setId(proveedor.getId());
                        nt.setNombre(proveedor.getRazonSocial());
                        nt.setData(proveedor.getConfiabilidad());
                        listaProveedores.add(nt);
                        break;
//                    }
//                }
            }
            HibernateUtil.commitTransaction();

        } catch (Exception ex) {
            System.out.println("No se pudo cargar el objeto: " + ex.getMessage());
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
        }
        return listaProveedores;
    }

    public ArrayList<NTupla> mostrarPrecios(int idProv, int idRE) {
        mostroPrecios = true;
        Session sesion;
        ArrayList<NTupla> listaTuplaPrecios = new ArrayList<NTupla>();
        listaPrecios = new ArrayList<PrecioSegunCantidad>();
        List<RecursoXProveedor> listaRxP;
        RecursoXProveedor rxP = new RecursoXProveedor();
        try {
            sesion = HibernateUtil.getSession();
            RecursoEspecifico recE = (RecursoEspecifico) sesion.createQuery("from RecursoEspecifico where id = " + idRE).uniqueResult();
            Proveedor prov = (Proveedor) sesion.createQuery("from Proveedor where id = " + idProv).uniqueResult();
            //listaPrecios=(ArrayList<PrecioSegunCantidad>)sesion.createQuery("select max(fechaVigencia) from PrecioSegunCantidad where id = "+idProv+" and group by cantidad").list();
            listaRxP = recE.getProveedores();
            boolean seguro = false;
            for (int i = 0; i < listaRxP.size(); i++) {
                if (listaRxP.get(i).getProveedor() == prov) {
                    rxP = listaRxP.get(i);
                    seguro = true;
                    break;
                }
            }
            if (seguro) {
                listaPrecios = rxP.getListaUltimosPrecios();
                //TODO: Ordenar listaPrecios segun la cantidad
                for (int i = 0; i < listaPrecios.size(); i++) {
                    NTupla nt = new NTupla();
                    nt.setId(listaPrecios.get(i).getId());
                    nt.setNombre(Double.toString(listaPrecios.get(i).getCantidad()));
                    String[] datos = new String[3];

                    datos[0] = Double.toString(listaPrecios.get(i).getPrecio());
                    datos[1] = listaPrecios.get(i).getFechaVigencia().toString();
                    nt.setData(datos);
                    listaTuplaPrecios.add(nt);
                }
            } else {
                return null;
            }

        } catch (Exception ex) {
            System.out.println("No se pudo cargar el objeto: " + ex.getMessage());
            ex.printStackTrace();

        }
        return listaTuplaPrecios;
    }
}