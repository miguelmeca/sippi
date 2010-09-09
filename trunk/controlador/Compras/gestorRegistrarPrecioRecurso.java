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
import util.NTupla;
import util.RubroUtil;
import util.Tupla;
import vista.compras.pantallaRegistrarPrecioRecurso;
import vista.compras.pantallaRegistrarPrecioRecursoNueva;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class gestorRegistrarPrecioRecurso {

    private pantallaRegistrarPrecioRecursoNueva pantalla;
    private List<Recurso> listaRecurso;

    public gestorRegistrarPrecioRecurso(pantallaRegistrarPrecioRecursoNueva pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarProveedores()
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
                   Tupla tp = new Tupla(rxp.getId(),rxp.getRazonSocial());
                   lista.add(tp);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("EG-0003");
           }

        return lista;
    }

    public ArrayList<NTupla> mostrarRecursosEspecificos(int idProveedor)
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        // TENGO Q MOSTRAR TODOS LOS RECURSOS ESPECIFICOS, PERO SOLO LOS DEL RUBRO DEL PROVEEDOR

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                // CARGO EL PROVEEDOR
                Proveedor pv = (Proveedor) sesion.load(Proveedor.class,idProveedor);

                // VEO SUS RECURSOS
                Iterator<Rubro> itrub = pv.getRubros().iterator();
                while (itrub.hasNext())
                {
                   Rubro rub = itrub.next();
                   // CARGO SOLO LOS RECURSOS DE ESE RUBRO
                   // PODRE?
                   List<Recurso> listaRec = sesion.createQuery("FROM "+rub.getNombre()+" AS re").list();
                   Iterator<Recurso> itrec = listaRec.iterator();
                   while (itrec.hasNext())
                   {
                        // TENGO LOS RECURSOS, BAJO DE NUEVO A LOS RECURSOS ESPECIFICOS
                        Recurso rec = itrec.next();
                        List<RecursoEspecifico> listaRecEsp = rec.getRecursosEspecificos();
                        Iterator<RecursoEspecifico> itrecesp = listaRecEsp.iterator();
                        while (itrecesp.hasNext())
                        {
                           RecursoEspecifico recesp = itrecesp.next();

                                // YA TENGO EL RECURSO, CARGO
                                NTupla nt = new NTupla();
                                nt.setId(recesp.getId());
                                // RUBRO
                                nt.setNombre(rub.getNombre());

                                    String[] datos = new String[4];
                                    // NOMBRE DEL RECURSO
                                    datos[0] = rec.getNombre();
                                    datos[1] = recesp.getNombre();
                                    datos[2] = "<HTML><BODY>";
                                    datos[3] = rec.getUnidadDeMedida().getAbreviatura();

                                        // BUSCO LOS PRECIOS !!!! Y SI TIENE ALGUNO
                                        List<RecursoXProveedor> listaRXP  = recesp.getRecursosXProveedor();
                                        Iterator<RecursoXProveedor> itrxp = listaRXP.iterator();
                                        while (itrxp.hasNext())
                                        {
                                            RecursoXProveedor rxp = itrxp.next();
                                            // VEO QUE SEA SOLO DE MI PROVEEDOR
                                            if(rxp.getProveedor().getId() == idProveedor)
                                            {
                                                // ES MI PROVEEDOR, CARGO EL ULTIMO PRECIO
                                                List<PrecioSegunCantidad> listaPsc = rxp.getListaUltimosPrecios();
                                                Iterator<PrecioSegunCantidad> itPsc = listaPsc.iterator();
                                                while (itPsc.hasNext())
                                                {
                                                    PrecioSegunCantidad psc = itPsc.next();
                                                    // CARGO LOS DATOS
                                                    datos[2] = datos[2] + psc.getCantidad()+rec.getUnidadDeMedida().getAbreviatura()+" <b>x</b> "+psc.getPrecio()+"<br>";
                                                }

                                            }
                                        }
                                  // AGREGO ALA LISTA DE NTUPLA
                                  if(datos[2].equals("<HTML><BODY>"))
                                  {
                                      datos[2] = "";
                                  }

                                  nt.setData(datos);
                                  lista.add(nt);
                        }
                    }
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("EG-0002");
           }


        return lista;
    }

    public String mostrarUnidadDeMedida(int idRecEsp)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                RecursoEspecifico re = (RecursoEspecifico)sesion.load(RecursoEspecifico.class,idRecEsp);

                Recurso rec = re.getRecurso();
                if(rec!=null)
                {
                    return rec.getUnidadDeMedida().getAbreviatura();
                }
                return "";

               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                ex.printStackTrace();
                pantalla.MostrarMensaje("ME-0001");
           }
           return "";
    }


    @Deprecated
    public ArrayList<Tupla> mostrarRubros()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//            ArrayList<Rubro> listaRubros = RubroUtil.getRubros();
//            Iterator<Rubro> itr = listaRubros.iterator();
//            while (itr.hasNext())
//            {
//                Rubro rubro = itr.next();
//                Tupla tp = new Tupla(rubro.getId(),rubro.getNombre());
//                lista.add(tp);
//            }
        return lista;
    }

    @Deprecated
    public boolean actualizarPrecio(Tupla recurso, Tupla proveedor,Date vigencia, int cantidad,double precio)
    {
//        Session sesion;
//        try
//        {
//            sesion = HibernateUtil.getSession();
//
//            // CARGO LOS DATOS
//            RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,recurso.getId());
//            Proveedor pr         = (Proveedor)sesion.load(Proveedor.class,proveedor.getId());
//
//                // CARGO LOS PRECIOS QUE YA TENGO, SI ES QUE TENGO ALGUNO
//                if(re.getProveedores().isEmpty())
//                {
//                    //NO TENGO CARGADO NINGUN RXP con PRECIOS PARA ESTA DUPLA
//                    // LA CREO Y GUARDO DE UNA
//                    PrecioSegunCantidad psc = new PrecioSegunCantidad();
//                    psc.setCantidad(cantidad);
//                    psc.setFecha(new Date());
//                    psc.setPrecio(precio);
//                    psc.setFechaVigencia(vigencia);
//                    RecursoXProveedor rxp = new RecursoXProveedor();
//                    rxp.setProveedor(pr);
//                    rxp.addPrecioSegunCantidad(psc);
//
//                    // AHORA AGREGO EL RE AL PROVEEDOR
//                    pr.getListaArticulos().add(re);
//                    // Y AGREGO EL PSC AL RECURSOESPECIFICO
//                    re.getProveedores().add(rxp);
//
//                    HibernateUtil.beginTransaction();
//                    sesion.save(psc);
//                    sesion.save(rxp);
//                    sesion.update(pr);
//                    sesion.update(re);
//                    HibernateUtil.commitTransaction();
//
//                    return true;
//
//                }
//                else
//                {
//                    // TENGO CARGADO UN RXP, LOS ANALIZO
//                    Iterator<RecursoXProveedor> itp = re.getProveedores().iterator();
//                    while (itp.hasNext())
//                    {
//                        RecursoXProveedor rxp = itp.next();
//                        // ES DEL PROVEEDOR AL QUE LE ESTOY CARGANDO ?
//                        if(rxp.getProveedor().getId() == pr.getId())
//                        {
//                            // ES EL PROVEEDOR ME FIJO EN LAS FECHAS SI HAY ALGUNA DE HOY Y CON ESA CANTIDAD
//                            Iterator<PrecioSegunCantidad> itf = rxp.getListaPrecios().iterator();
//                            while (itf.hasNext())
//                            {
//                                PrecioSegunCantidad psc = itf.next();
//                                Date hoy = new Date();
//                                if(FechaUtil.getFecha(hoy).equals(FechaUtil.getFecha(psc.getFecha())))
//                                {
//                                    // ES DE HOY !! ENTONCES ACTUALIZO EL PRECIO
//                                    // SE DA SI CARGA EN UN MISMO DÍA DOS VECES UN
//                                    // PRECIO PARA UN REC y PROV
//                                    if(psc.getCantidad()==cantidad)
//                                    {
//                                        // TIENE LA MISMA CANTIDAD, ACTUALIZO
//                                        psc.setPrecio(precio);
//                                        psc.setFechaVigencia(vigencia);
//
//                                        HibernateUtil.beginTransaction();
//                                        sesion.update(psc);
//                                        HibernateUtil.commitTransaction();
//                                        return true;
//                                    }
//                                    else
//                                    {
//                                        // NO ES LA MISMA CANTIDAD, CREO OTRO PSC
//                                        // TENGO QUE BUSCAR SI ESA CANTIDAD NO ESTA YA CARGADA
//                                         Iterator<PrecioSegunCantidad> itf2 = rxp.getListaPrecios().iterator();
//                                         boolean esta = false;
//                                         while (itf2.hasNext())
//                                         {
//                                            PrecioSegunCantidad pscAux = itf2.next();
//                                            if(pscAux.getCantidad()==cantidad)
//                                            {
//                                                // ES LA MISMA CANTIDAD !!!
//                                                // ENTONCES ESTA MAS ADELANTE
//                                                esta = true;
//                                                // SE LA DEJO A LA ITER DE ARRIBA
//                                            }
//                                        }
//
//                                         // NO ESTA ESA CANTIDAD CARGADA, LA CREO
//                                        if(!esta)
//                                        {
//                                            PrecioSegunCantidad pscNuevo = new PrecioSegunCantidad();
//                                            pscNuevo.setCantidad(cantidad);
//                                            pscNuevo.setFecha(hoy);
//                                            pscNuevo.setFechaVigencia(vigencia);
//                                            pscNuevo.setPrecio(precio);
//                                            rxp.addPrecioSegunCantidad(pscNuevo);
//                                            // Y AGREGO EL PSC AL RECURSOESPECIFICO
//                                            re.getProveedores().add(rxp);
//
//                                            HibernateUtil.beginTransaction();
//                                            sesion.save(pscNuevo);
//                                            sesion.update(rxp);
//                                            sesion.update(re);
//                                            HibernateUtil.commitTransaction();
//                                            return true;
//                                        }
//                                         else
//                                        {
//                                             // SE USARA??
//                                             LogUtil.addDebug("LA CANTIDAD YA ESTA CARGADA !!");
//                                        }
//
//                                    }
//
//                                }
//                                else
//                                {
//                                    // NO ES DE HOY , ES UNO VIEJO, NO HAGO NADA
//                                }
//                            }
//                        }
//                        else
//                        {
//                            // NO ES DE ESTE PROVEEDOR - VEO SI NO ES MAS ADELANTE
//                            Iterator<RecursoXProveedor> itp2 = re.getProveedores().iterator();
//                            boolean esta = false;
//                            while (itp2.hasNext())
//                            {
//                                RecursoXProveedor rxp2 = itp2.next();
//                                if(pr.getId()==rxp2.getProveedor().getId())
//                                {
//                                    esta = true;
//                                }
//                            }
//                            if(!esta) // Si no esta mas adelante creo
//                            {
//                                //NO TENGO CARGADO NINGUN RXP con PRECIOS PARA ESTE PROVEEDOR
//                                // LA CREO Y GUARDO DE UNA
//                                PrecioSegunCantidad psc = new PrecioSegunCantidad();
//                                psc.setCantidad(cantidad);
//                                psc.setFecha(new Date());
//                                psc.setPrecio(precio);
//                                psc.setFechaVigencia(vigencia);
//                                RecursoXProveedor rxpn = new RecursoXProveedor();
//                                rxpn.setProveedor(pr);
//                                rxpn.addPrecioSegunCantidad(psc);
//
//                                // SI AGREGO EL RE AL PROVEEDOR (CREO)
//                                 pr.getListaArticulos().add(re);
//                                // Y AGREGO EL PSC AL RECURSOESPECIFICO
//                                re.getProveedores().add(rxpn);
//
//                                HibernateUtil.beginTransaction();
//                                sesion.save(psc);
//                                sesion.save(rxpn);
//                                sesion.update(pr);
//                                sesion.update(re);
//                                HibernateUtil.commitTransaction();
//
//                                return true;
//                            }
//                        }
//                    }
//                }
//        }
//        catch (Exception ex)
//        {
//            LogUtil.addError("No se pudo comenzar la transacción en la actualizacion de precios");
//            HibernateUtil.rollbackTransaction();
//            pantalla.MostrarMensaje("ME-0025");
//            return false;
//        }
        return false;
    }

    @Deprecated
    public ArrayList<Tupla> mostrarRecursosPorTipoRecurso(int idRubro)
    {
//        Rubro rubro = RubroUtil.getRubro(idRubro); // Tipo de Recurso
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                // HAGO UN CACHE DE LA BUSQUEDA !!!
//                List<Recurso> listaRec;
//                if(this.listaRecurso==null)
//                {
//                    listaRec = sesion.createQuery("from Recurso").list();
//                    this.listaRecurso = listaRec;
//                }
//                else
//                {
//                    listaRec = this.listaRecurso;
//                }
//
//                Iterator<Recurso> iter = listaRec.iterator();
//                while (iter.hasNext())
//                {
//                   Recurso rec = iter.next();
//                   String tipoClase = rubro.getNombre();
//                   if(rec.esRubro(rubro.getId()))
//                   {
//                       Tupla t = new Tupla();
//                       t.setId(rec.getId());
//                       t.setNombre(rec.getNombre());
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
//
        return lista;
    }

    @Deprecated
    public ArrayList<Tupla> mostrarRecursosEspecificos2(int idRec)
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                Recurso r = (Recurso) sesion.load(Recurso.class,idRec);
//
//                    // MUESTRO LA UNIDAD DE MEDIDA
//                    pantalla.setUnidadDeMedida(r.getUnidadDeMedida().getAbreviatura());
//
//                Iterator<RecursoEspecifico> it = r.getRecursos().iterator();
//                while (it.hasNext())
//                {
//                    RecursoEspecifico re = it.next();
//
//                        Tupla tpla = new Tupla(re.getId(),re.getNombre());
//                        lista.add(tpla);
//               }
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0021");
//           }
//
        return lista;
    }

    @Deprecated
    public void mostrarInfoDeRecursoEspecifico(int idRecurso)
    {
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,idRecurso);
//
//                pantalla.mostrarDescipcionRecursoEspecifico(re.getDescipcion());
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0021");
//           }
    }

    @Deprecated
    public ArrayList<Tupla> cargarProveedoresXTipoRecurso(int idRubro)
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                List<Proveedor> listaProv = sesion.createQuery("FROM Proveedor AS pv").list();
//
//                Iterator<Proveedor> it = listaProv.iterator();
//                while (it.hasNext())
//                {
//                   Proveedor rxp = it.next();
//                   // TENGO EL PROVEEDOR, VEO QUE VENDA EL TIPO DE RECURSO
//                   Iterator<Rubro> itr = rxp.getRubros().iterator();
//                    while (itr.hasNext())
//                    {
//                        Rubro recu = itr.next();
//                        if(recu.getId()==idRubro)
//                        {
//                            Tupla tp = new Tupla(rxp.getId(),rxp.getRazonSocial());
//
//                            // VEO SI YA NO ESTA AGREGADO !!!
//                            Iterator<Tupla> itp = lista.iterator();
//                            boolean esta = false;
//                            while (itp.hasNext())
//                            {
//                                Tupla tpp = itp.next();
//                                if(tpp.getId()==tp.getId())
//                                {
//                                    esta = true;
//                                }
//                            }
//                            if(!esta)
//                            {
//                                lista.add(tp);
//                            }
//                        }
//                    }
//                }
//
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0022");
//           }
//
        return lista;
    }

    @Deprecated
    public ArrayList<Tupla> mostrarUltimoPrecioXProveedor(int idProv, int idRecEsp)
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
//
//           Session sesion;
//           try {
//                sesion = HibernateUtil.getSession();
//
//                RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,idRecEsp);
//
//                RecursoXProveedor rxp = re.getUltimoRecursoXProveedor(idProv);
//                if(rxp!=null)
//                {
//                       Iterator<PrecioSegunCantidad> itp = rxp.getListaPrecios().iterator();
//                       while (itp.hasNext())
//                       {
//                           PrecioSegunCantidad psc = itp.next();
//                           Tupla tp = new Tupla(psc.getId(),"["+FechaUtil.getFecha(psc.getFecha())+"] "+" $"+psc.getPrecio()+" x "+psc.getCantidad());
//                           lista.add(tp);
//                       }
//               }
//               else
//               {
//                    return lista;
//               }
//               }catch(Exception ex)
//           {
//                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
//                ex.printStackTrace();
//                pantalla.MostrarMensaje("ME-0023");
//           }
//
//
        return lista;
    }

    public void registrarPrecio(double cantidad, double precio, int idProv, int idRecEsp, Date vigencia)
    {

        // TENGO QUE:
        // VERIFICAR SI NO EXISTE LA CARGA DE ESTE PRECIO (HOY), CON ESTA CANTIDAD, CON ESTE PRECIO
        // SI NO EXISTE
        //      CREO UNO NUEVO
        // SINO
        //      MODIFICO EL ACTUAL
        // Y AGREGO EL RECURSO AL PROVEEDOR

        //1) VEO SI NO CARGO EL PRECIO
        Session sesion;
        try
        {
            sesion = HibernateUtil.getSession();

            // CARGO LOS DATOS
            RecursoEspecifico re = (RecursoEspecifico) sesion.load(RecursoEspecifico.class,idRecEsp);
            Proveedor pr         = (Proveedor)sesion.load(Proveedor.class,idProv);

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

                    pantalla.MostrarMensaje("MI-0001");return;

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

                                        pantalla.MostrarMensaje("MI-0001");return;

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
                                            // Y AGREGO EL PSC AL RECURSOESPECIFICO
                                            rxp.addPrecioSegunCantidad(pscNuevo);
                                            //re.getProveedores().add(rxp);

                                            HibernateUtil.beginTransaction();
                                            sesion.save(pscNuevo);
                                            sesion.update(rxp);
                                            sesion.update(re);
                                            HibernateUtil.commitTransaction();

                                            pantalla.MostrarMensaje("MI-0001");return;
                                        }
                                         else
                                        {
                                             // SE USARA, NO, LA AGARRA EL IF DE ARRIBA
                                             LogUtil.addDebug("ATENCION ATENCION: LA CANTIDAD YA ESTA CARGADA !! (ESTO NO DEBERIA VERSE)");
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
                            // NO ES DE ESTE PROVEEDOR - VEO SI NO ES MAS ADELANTE
                            Iterator<RecursoXProveedor> itp2 = re.getProveedores().iterator();
                            boolean esta = false;
                            while (itp2.hasNext())
                            {
                                RecursoXProveedor rxp2 = itp2.next();
                                if(pr.getId()==rxp2.getProveedor().getId())
                                {
                                    esta = true;
                                }
                            }
                            // Si no esta mas adelante creo
                            if(!esta) 
                            {
                                //NO TENGO CARGADO NINGUN RXP con PRECIOS PARA ESTE PROVEEDOR
                                // LA CREO Y GUARDO DE UNA
                                PrecioSegunCantidad psc = new PrecioSegunCantidad();
                                psc.setCantidad(cantidad);
                                psc.setFecha(new Date());
                                psc.setPrecio(precio);
                                psc.setFechaVigencia(vigencia);
                                RecursoXProveedor rxpn = new RecursoXProveedor();
                                rxpn.setProveedor(pr);
                                rxpn.addPrecioSegunCantidad(psc);

                                // SI AGREGO EL RE AL PROVEEDOR (CREO)
                                 pr.getListaArticulos().add(re);
                                // Y AGREGO EL PSC AL RECURSOESPECIFICO
                                re.getProveedores().add(rxpn);

                                HibernateUtil.beginTransaction();
                                sesion.save(psc);
                                sesion.save(rxpn);
                                sesion.update(pr);
                                sesion.update(re);
                                HibernateUtil.commitTransaction();

                                pantalla.MostrarMensaje("MI-0001");return;
                            }
                        }
                    }
                }
        }
        catch (Exception ex)
        {
            LogUtil.addError("No se pudo comenzar la transacción en la actualizacion de precios");
            HibernateUtil.rollbackTransaction();
            pantalla.MostrarMensaje("EG-0001");return;
        }

    }

}
