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
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.Proveedor;
import modelo.RecursoXProveedor;
import modelo.Rubro;
import util.RubroUtil;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import util.NTupla;
import vista.compras.pantallaGenerarOrdenCompra;
import org.hibernate.Session;
import util.HibernateUtil;


/**
 *
 * @author Emmanuel
 */
public class GestorGenerarOrdenCompra
{
  private pantallaGenerarOrdenCompra pantalla;

public GestorGenerarOrdenCompra(pantallaGenerarOrdenCompra pantalla)
    {
        this.pantalla = pantalla;
        
    }


 public ArrayList<Tupla> mostrarRubros()
 {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        lista = RubroUtil.getTuplasRubros();
        return lista;

 }



 public ArrayList<Tupla> mostrarRecursos(int idTipoRec)
 {
  ArrayList<Tupla> lista = new ArrayList<Tupla>();
  Rubro rub= RubroUtil.getRubro(idTipoRec);

  Session sesion;
  try {
      sesion = HibernateUtil.getSession();

      List<Recurso> listaRecursos = sesion.createQuery("from "+rub.getNombre()).list();
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

 public String mostrarUnidadMedida(int idR)
 {
     Session sesion;
     String um;
     um=null;
  try {
      sesion = HibernateUtil.getSession();
      Recurso rec= (Recurso)sesion.createQuery("from Recurso where id = "+idR).uniqueResult();
      um=rec.getUnidadDeMedida().getAbreviatura();

   }

   catch(Exception ex)
   {
      System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
      ex.printStackTrace();

   }
     return um;
 }

 public ArrayList<NTupla> mostrarProveedores(int idRE)
 {
    Session sesion;
    ArrayList<NTupla> listaProveedores=new ArrayList<NTupla>();
  try {
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
    return new ArrayList<NTupla>();
 }




}