package controlador.Compras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.RecursoXProveedor;
import modelo.Proveedor;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import org.hibernate.Session;
import util.HibernateUtil;
import util.LogUtil;
import util.TipoRecursoUtil;
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
    private List<Recurso> listaRecurso;

    public gestorRegistrarPrecioRecurso(pantallaRegistrarPrecioRecurso pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarTipoRecursos()
    {
        return TipoRecursoUtil.getTiposDeRecurso();
    }

    public ArrayList<Tupla> mostrarRecursosPorTipoRecurso(int idTipoRec)
    {
        Tupla tipo = TipoRecursoUtil.TipoRecurso(idTipoRec); // Tipo de Recurso
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                // HAGO UN CACHE DE LA BUSQUEDA !!!
                List<Recurso> listaRec;
                if(this.listaRecurso==null)
                {
                    listaRec = sesion.createQuery("from Recurso").list();
                    this.listaRecurso = listaRec;
                }
                else
                {
                    listaRec = this.listaRecurso;
                }

                Iterator<Recurso> iter = listaRec.iterator();
                while (iter.hasNext())
                {
                   Recurso rec = iter.next();
                   String tipoClase = tipo.getNombre();
                   if(rec.esTipoRecurso(tipo.getId()))
                   {
                       Tupla t = new Tupla();
                       t.setId(rec.getId());
                       t.setNombre(rec.getNombre());
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

//    public ArrayList<Tupla> mostrarProveedores()
//    {
//           ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                List<Proveedor> listaProv = sesion.createQuery("from Proveedor").list();
//                Iterator<Proveedor> iter = listaProv.iterator();
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
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0020");
//           }
//
//           return lista;
//    }
//
//    public ArrayList<Tupla> buscarRecursoPorProveedor(int idProv)
//    {
//           ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                Proveedor prov = (Proveedor)sesion.load(Proveedor.class,idProv);
//
//                return prov.buscarTipoDeRecursosDisponibles();
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0021");
//           }
//
//           return lista;
//    }
//
//    public ArrayList<Tupla> mostrarTipoDeRecursos()
//    {
//        return TipoRecursoUtil.getTiposDeRecurso();
//    }
//
//    public ArrayList<Tupla> mostrarRecursos(int idTipoRec)
//    {
//        ArrayList<Tupla> lista = new ArrayList<Tupla>();
//        Tupla tipo = TipoRecursoUtil.TipoRecurso(idTipoRec);
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                List<RecursoEspecifico> listaProv = sesion.createQuery("from RecursoEspecifico").list();
//                Iterator<RecursoEspecifico> it = listaProv.iterator();
//                while (it.hasNext())
//                {
//                    RecursoEspecifico r = it.next();
//                    if(r.toString().equals(tipo.getNombre()))
//                    {
//                        Tupla tpla = new Tupla(r.getRecurso().getId(),r.getRecurso().getNombre());
//                        // ES UN RECURSO DEL TIPO
//
//                        // SI NO LO TENGO EN LA LISTA LO AGREGO
//                        boolean esta = false;
//                        Iterator<Tupla> ite = lista.iterator();
//                        while (ite.hasNext())
//                        {
//                            Tupla aux = ite.next();
//                            if(aux.getId() == tpla.getId())
//                            {
//                                esta = true;
//                            }
//                        }
//                        if(!esta)
//                        {
//                            lista.add(tpla);
//                        }
//                    }
//               }
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0021");
//           }
//
//        return lista;
//    }
//
//
//
    public ArrayList<Tupla> mostrarRecursosEspecificos(int idRec)
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Recurso r = (Recurso) sesion.load(Recurso.class,idRec);

                Iterator<RecursoEspecifico> it = r.getRecursos().iterator();
                while (it.hasNext())
                {
                    RecursoEspecifico re = it.next();

                        Tupla tpla = new Tupla(re.getId(),re.getNombre());
                        lista.add(tpla);
               }

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0021");
           }

        return lista;
    }

    public void mostrarInfoDeRecursoEspecifico(int idRecurso)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,idRecurso);

                pantalla.mostrarDescipcionRecursoEspecifico(re.getDescipcion());

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0021");
           }
    }

}
