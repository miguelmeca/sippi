package controlador.Compras;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.PrecioSegunCantidad;
import modelo.RecursoXProveedor;
import modelo.Proveedor;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.Rubro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FechaUtil;
import util.HibernateUtil;
import util.LogUtil;
import util.RubroUtil;
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

    public ArrayList<Tupla> mostrarRubros()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

            ArrayList<Rubro> listaRubros = RubroUtil.getRubros();
            Iterator<Rubro> itr = listaRubros.iterator();
            while (itr.hasNext())
            {
                Rubro rubro = itr.next();
                Tupla tp = new Tupla(rubro.getId(),rubro.getNombre());
                lista.add(tp);
            }
        return lista;
    }

    // tp_re,tp_pr,vigencia,cantidad,precio
    public boolean actualizarPrecio(Tupla recurso, Tupla proveedor,Date vigencia, int cantidad,double precio)
    {
        Session sesion;
        try
        {
            sesion = HibernateUtil.getSession();

            // CARGO LOS DATOS
            RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,recurso.getId());
            Proveedor pr         = (Proveedor)sesion.load(Proveedor.class,proveedor.getId());

                // CARGO LOS PRECIOS QUE YA TENGO, SI ES QUE TENGO ALGUNO
                if(re.getProveedores().isEmpty())
                {
                    //NO TENGO CARGADO NINGUN RXP con PRECIOS PARA ESTA DUPLA
                    // LA CREO Y GUARDO DE UNA
                    PrecioSegunCantidad psc = new PrecioSegunCantidad();
                    psc.setCantidad(cantidad);
                    psc.setFecha(new Date());
                    psc.setPrecio(precio);
                    psc.setFechaVigencia(vigencia);
                    RecursoXProveedor rxp = new RecursoXProveedor();
                    rxp.setProveedor(pr);
                    rxp.addPrecioSegunCantidad(psc);

                    // AHORA AGREGO EL RE AL PROVEEDOR
                    pr.getListaArticulos().add(re);
                    // Y AGREGO EL PSC AL RECURSOESPECIFICO
                    re.getProveedores().add(rxp);

                    HibernateUtil.beginTransaction();
                    sesion.save(psc);
                    sesion.save(rxp);
                    sesion.update(pr);
                    sesion.update(re);
                    HibernateUtil.commitTransaction();



                    return true;

                }
                else
                {
                    // TENGO CARGADO UN RXP, LOS ANALIZO
                    Iterator<RecursoXProveedor> itp = re.getProveedores().iterator();
                    while (itp.hasNext())
                    {
                        RecursoXProveedor rxp = itp.next();
                        // ES DEL PROVEEDOR AL QUE LE ESTOY CARGANDO ?
                        if(rxp.getProveedor().getId() == pr.getId())
                        {
                            // ES EL PROVEEDOR ME FIJO EN LAS FECHAS SI HAY ALGUNA DE HOY Y CON ESA CANTIDAD
                            Iterator<PrecioSegunCantidad> itf = rxp.getListaPrecios().iterator();
                            while (itf.hasNext())
                            {
                                PrecioSegunCantidad psc = itf.next();
                                Date hoy = new Date();
                                if(FechaUtil.getFecha(hoy).equals(FechaUtil.getFecha(psc.getFecha())))
                                {
                                    // ES DE HOY !! ENTONCES ACTUALIZO EL PRECIO
                                    // SE DA SI CARGA EN UN MISMO DÍA DOS VECES UN
                                    // PRECIO PARA UN REC y PROV
                                    if(psc.getCantidad()==cantidad)
                                    {
                                        // TIENE LA MISMA CANTIDAD, ACTUALIZO
                                        psc.setPrecio(precio);
                                        psc.setFechaVigencia(vigencia);

                                        HibernateUtil.beginTransaction();
                                        sesion.update(psc);
                                        HibernateUtil.commitTransaction();
                                        return true;
                                    }
                                    else
                                    {
                                        // NO ES LA MISMA CANTIDAD, CREO OTRO PSC
                                        // TENGO QUE BUSCAR SI ESA CANTIDAD NO ESTA YA CARGADA
                                         Iterator<PrecioSegunCantidad> itf2 = rxp.getListaPrecios().iterator();
                                         boolean esta = false;
                                         while (itf2.hasNext())
                                         {
                                            PrecioSegunCantidad pscAux = itf2.next();
                                            if(pscAux.getCantidad()==cantidad)
                                            {
                                                // ES LA MISMA CANTIDAD !!!
                                                // ENTONCES ESTA MAS ADELANTE
                                                esta = true;
                                                // SE LA DEJO A LA ITER DE ARRIBA
                                            }
                                        }

                                         // NO ESTA ESA CANTIDAD CARGADA, LA CREO
                                        if(!esta)
                                        {
                                            PrecioSegunCantidad pscNuevo = new PrecioSegunCantidad();
                                            pscNuevo.setCantidad(cantidad);
                                            pscNuevo.setFecha(hoy);
                                            pscNuevo.setFechaVigencia(vigencia);
                                            pscNuevo.setPrecio(precio);
                                            rxp.addPrecioSegunCantidad(pscNuevo);
                                            // Y AGREGO EL PSC AL RECURSOESPECIFICO
                                            re.getProveedores().add(rxp);

                                            HibernateUtil.beginTransaction();
                                            sesion.save(pscNuevo);
                                            sesion.update(rxp);
                                            sesion.update(re);
                                            HibernateUtil.commitTransaction();
                                            return true;
                                        }
                                         else
                                        {
                                             // SE USARA??
                                             LogUtil.addDebug("LA CANTIDAD YA ESTA CARGADA !!");
                                        }

                                    }

                                }
                                else
                                {
                                    // NO ES DE HOY , ES UNO VIEJO, NO HAGO NADA
                                }
                            }
                        }
                        else
                        {
                            // NO ES DE ESTE PROVEEDOR
                        }
                    }
                }
        }
        catch (Exception ex)
        {
            LogUtil.addError("No se pudo comenzar la transacción en la actualizacion de precios");
            HibernateUtil.rollbackTransaction();
            pantalla.MostrarMensaje("ME-0025");
            return false;
        }
        return false;
    }

    public ArrayList<Tupla> mostrarRecursosPorTipoRecurso(int idRubro)
    {
        Rubro rubro = RubroUtil.getRubro(idRubro); // Tipo de Recurso
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
                   String tipoClase = rubro.getNombre();
                   if(rec.esRubro(rubro.getId()))
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

    public ArrayList<Tupla> mostrarRecursosEspecificos(int idRec)
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Recurso r = (Recurso) sesion.load(Recurso.class,idRec);

                    // MUESTRO LA UNIDAD DE MEDIDA
                    pantalla.setUnidadDeMedida(r.getUnidadDeMedida().getAbreviatura());

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

    public ArrayList<Tupla> cargarProveedoresXTipoRecurso(int idRubro)
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                List<Proveedor> listaProv = sesion.createQuery("FROM Proveedor AS pv").list();

                Iterator<Proveedor> it = listaProv.iterator();
                while (it.hasNext())
                {
                   Proveedor rxp = it.next();
                   // TENGO EL PROVEEDOR, VEO QUE VENDA EL TIPO DE RECURSO
                   Iterator<Rubro> itr = rxp.getRubros().iterator();
                    while (itr.hasNext())
                    {
                        Rubro recu = itr.next();
                        if(recu.getId()==idRubro)
                        {
                            Tupla tp = new Tupla(rxp.getId(),rxp.getRazonSocial());

                            // VEO SI YA NO ESTA AGREGADO !!!
                            Iterator<Tupla> itp = lista.iterator();
                            boolean esta = false;
                            while (itp.hasNext())
                            {
                                Tupla tpp = itp.next();
                                if(tpp.getId()==tp.getId())
                                {
                                    esta = true;
                                }
                            }
                            if(!esta)
                            {
                                lista.add(tp);
                            }
                        }
                    }
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0022");
           }

        return lista;
    }

    public ArrayList<Tupla> mostrarUltimoPrecioXProveedor(int idProv, int idRecEsp)
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,idRecEsp);

                RecursoXProveedor rxp = re.getUltimoRecursoXProveedor();
                if(rxp!=null)
                {
                       Iterator<PrecioSegunCantidad> itp = rxp.getListaPrecios().iterator();
                       while (itp.hasNext())
                       {
                           PrecioSegunCantidad psc = itp.next();
                           Tupla tp = new Tupla(psc.getId(),"["+FechaUtil.getFecha(psc.getFecha())+"] "+" $"+psc.getPrecio()+" x "+psc.getCantidad());
                           lista.add(tp);
                       }
               }
               else
               {
                    return lista;
               }
               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0023");
           }


        return lista;
    }

}
