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
  ArrayList<PrecioSegunCantidad> listaPrecios;
  private boolean mostroPrecios=false;

public GestorGenerarOrdenCompra(pantallaGenerarOrdenCompra pantalla)
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
              nt.setNombre(Integer.toString(listaPrecios.get(i).getCantidad()));
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

public double[] precioParcial(double cant)
{
    double precioUnit;
    double cantidad;
    double[] precios=new double[2];
    precios[0]=0.0;
    precios[1]=0.0;
    if(!mostroPrecios)
    {return precios;}
    if(listaPrecios.isEmpty())
    {return precios;}
    else
    {cantidad=listaPrecios.get(0).getCantidad();
    precioUnit=listaPrecios.get(0).getPrecio();}
    for (int i= 0; i < listaPrecios.size(); i++)
    {
        if(cant>=listaPrecios.get(i).getCantidad() && listaPrecios.get(i).getCantidad()>=cantidad)
        { cantidad=listaPrecios.get(i).getCantidad();
          precioUnit=listaPrecios.get(i).getPrecio();
        }

    }
    
    
    precioUnit=Math.rint(precioUnit*100)/100;
    double precioParcial=Math.rint((precioUnit*cant)*100)/100;

    precios[0]=precioUnit;
    precios[1]=precioParcial;

    return precios;
}

public NTupla fechaActual()
{
     NTupla ntFG=new NTupla();
            ntFG.setData(new Date());
            ntFG.setNombre( FechaUtil.getFechaYHoraActual());
         return ntFG;
}
public int generarNuevoNmroOC()
{
    int mayorNmro;
            Session sesion;
            try{
             
            sesion = HibernateUtil.getSession();

            Object ob =sesion.createQuery("Select MAX(id) from OrdenDeCompra").uniqueResult();
            if(ob!=null)
            {mayorNmro=(Integer)ob;}
            else{mayorNmro=0;}
            //sesion.getTransaction().commit();
            //}
            }
            catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
                return 0;
            }

            return (mayorNmro+1);
}

public int[] registrar(ArrayList<Object[]> daktos)
{
   /* try {
                    sesion = HibernateUtil.getSession();
            //sesion.beginTransaction();
            empleadoModif = (Empleado) sesion.createQuery("from Empleado where id ="+id).uniqueResult();
    */

    for (int i= 0; i < daktos.size(); i++)
       {
           for (int j= 0; j <((ArrayList<NTupla>) daktos.get(i)[0]).size(); j++) // Detalles OC
           {
               

           }
        //OrdenDeCompra OC=new OrdenDeCompra()
          Object[] fila=new Object[4];
           fila[0]=daktos.get(i)[5];
           fila[1]=daktos.get(i)[3];
           fila[2]=daktos.get(i)[6];
         //  modelo.addRow(fila);




       }

return int[]=new int[1];
}

}