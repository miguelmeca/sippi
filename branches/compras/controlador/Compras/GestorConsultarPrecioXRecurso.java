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
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.Proveedor;
import modelo.RecursoXProveedor;
import modelo.PrecioSegunCantidad;
import modelo.Rubro;
import modelo.OrdenDeCompra;
import modelo.DetalleOrdenDeCompra;
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
public class GestorConsultarPrecioXRecurso
{
  private pantallaConsultarPrecioXRecurso pantalla;
  ArrayList<PrecioSegunCantidad> listaPrecios;
  private boolean mostroPrecios=false;

public GestorConsultarPrecioXRecurso(pantallaConsultarPrecioXRecurso pantalla)
    {
        this.pantalla = pantalla;
        listaPrecios= new ArrayList<PrecioSegunCantidad>();

    }


 public ArrayList<Tupla> mostrarRubros()
 {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        lista = RubroUtil.getTuplasRubros();
        mostroPrecios=false;
        return lista;

 }



 public ArrayList<Tupla> mostrarRecursos(int idTipoRec)
 {
  mostroPrecios=false;
  ArrayList<Tupla> lista = new ArrayList<Tupla>();
  Rubro rub= RubroUtil.getRubro(idTipoRec);

  Session sesion;
  try {
      sesion = HibernateUtil.getSession();

      List<Recurso> listaRecursos = sesion.createQuery("from "+rub.getNombreClase()).list();
      Iterator<Recurso> it = listaRecursos.iterator();
      while (it.hasNext())
      {
        Recurso r = it.next();
        Tupla tpla = new Tupla(r.getId(),r.getNombre());
        lista.add(tpla);
      }
   }

   catch(Exception ex)
   {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();

   }

   return lista;
 }


 


 public ArrayList<Tupla> mostrarRecursosEspecificos(int idRec)
 {
  mostroPrecios=false;
     ArrayList<Tupla> lista = new ArrayList<Tupla>();
  Session sesion;
  try {
      sesion = HibernateUtil.getSession();
      Recurso rec= (Recurso)sesion.createQuery("from Recurso re where re.id = "+idRec).uniqueResult();
      List<RecursoEspecifico> listaRecursosE = rec.getRecursos();
      Iterator<RecursoEspecifico> it = listaRecursosE.iterator();
      while (it.hasNext())
      {
        RecursoEspecifico re = it.next();
        Tupla tpla = new Tupla(re.getId(),re.getNombre());
        lista.add(tpla);
      }
   }

   catch(Exception ex)
   {
      System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
      ex.printStackTrace();

   }

   return lista;
 }

 

 public ArrayList<NTupla> mostrarProveedores(int idRE)
 {
    mostroPrecios=false;
     Session sesion;
    ArrayList<NTupla> listaProveedores=new ArrayList<NTupla>();
  try
  {
      sesion = HibernateUtil.getSession();
      RecursoEspecifico recE= (RecursoEspecifico)sesion.createQuery("from RecursoEspecifico where id = "+idRE).uniqueResult();
      for (int i= 0; i < recE.getProveedores().size(); i++) 
      {
        NTupla nt=new NTupla();
        Proveedor prov=recE.getProveedores().get(i).getProveedor();
        nt.setId(prov.getId());
        nt.setNombre(prov.getRazonSocial());
        nt.setData(prov.getConfiabilidad());
        listaProveedores.add(nt);
      }

   }

   catch(Exception ex)
   {
      System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
      ex.printStackTrace();

   }
     return listaProveedores;
 }

 public ArrayList<NTupla> mostrarPrecios(int idProv,int idRE)
 {
    mostroPrecios=true;
     Session sesion;
    ArrayList<NTupla> listaTuplaPrecios=new ArrayList<NTupla>();
    listaPrecios=new ArrayList<PrecioSegunCantidad>();
    List<RecursoXProveedor> listaRxP;
    RecursoXProveedor rxP=new RecursoXProveedor();
    try
    {
      sesion = HibernateUtil.getSession();
      RecursoEspecifico recE= (RecursoEspecifico)sesion.createQuery("from RecursoEspecifico where id = "+idRE).uniqueResult();
      Proveedor prov=(Proveedor)sesion.createQuery("from Proveedor where id = "+idProv).uniqueResult();
      //listaPrecios=(ArrayList<PrecioSegunCantidad>)sesion.createQuery("select max(fechaVigencia) from PrecioSegunCantidad where id = "+idProv+" and group by cantidad").list();
      listaRxP=recE.getProveedores();
      boolean seguro=false;
        for (int i= 0; i < listaRxP.size(); i++)
        {
            if(listaRxP.get(i).getProveedor()==prov)
            {rxP=listaRxP.get(i);
             seguro=true;
             break;
            }
        }
      if(seguro)
      {
          listaPrecios=rxP.getListaUltimosPrecios();
          //TODO: Ordenar listaPrecios segun la cantidad
          for (int i= 0; i < listaPrecios.size(); i++)
          {
              NTupla nt=new NTupla();
              nt.setId(listaPrecios.get(i).getId());
              nt.setNombre(Double.toString(listaPrecios.get(i).getCantidad()));
              String[] datos=new String[3];

              datos[0]=Double.toString(listaPrecios.get(i).getPrecio());
              datos[1]=listaPrecios.get(i).getFechaVigencia().toString();
              nt.setData(datos);
             listaTuplaPrecios.add(nt);
          }
      }
      else
      {return null;}

   }

   catch(Exception ex)
   {
      System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
      ex.printStackTrace();

   }
     return listaTuplaPrecios;
 }




}