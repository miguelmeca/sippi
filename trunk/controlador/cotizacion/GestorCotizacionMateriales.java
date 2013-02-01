/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import controlador.planificacion.PlanificacionUtils;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import org.hibernate.Session;
import util.*;
import vista.cotizacion.AgregarNuevoPrecio;
import vista.cotizacion.CotizacionMateriales;
import vista.cotizacion.SeleccionProveedorCotizacion;
import vista.planificacion.cotizacion.EditarCotizacionModificada;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionMateriales implements IGestorCotizacion{
    
    private GestorEditarCotizacion gestorPadre;
//    private SubObra subObra;
    private CotizacionMateriales pantalla;
    private SeleccionProveedorCotizacion pantallaSeleccion;

    
    
    public GestorCotizacionMateriales(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
//        this.subObra = gestorPadre.getSubObraActual();
        pantallaSeleccion = null;
    }

    public GestorCotizacionMateriales() {
        this.gestorPadre = null;
//        this.subObra = null;
        pantallaSeleccion = null;
    }

    public void setPantalla(CotizacionMateriales pantalla) {
        this.pantalla = pantalla;
    }

    public void setPantallaSeleccion(SeleccionProveedorCotizacion pantallaSeleccion) {
        this.pantallaSeleccion = pantallaSeleccion;
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }

    public ArrayList<NTupla> getMaterialesDisponibles(){
        ArrayList<NTupla> materiales = new ArrayList<NTupla>();
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from Material").iterate();
            while(it.hasNext()){
                NTupla t = new NTupla();
                Material m = (Material)it.next();
                t.setId(m.getId());
                t.setNombre(m.getNombre());
                materiales.add(t);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar cargar los materiales disponibles: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return materiales;
    }

    public ArrayList<Tupla> getEspecificacionMaterial(int id) {
        ArrayList<Tupla> esps = new ArrayList<Tupla>();
        try {
            HibernateUtil.beginTransaction();
            Material m = (Material)HibernateUtil.getSession().load(Material.class, id);
            Iterator it = m.getRecursosEspecificos().iterator();
            while(it.hasNext()){
                Tupla t = new Tupla();
                RecursoEspecifico re = (RecursoEspecifico)it.next();
                t.setId(re.getId());
                t.setNombre(re.getNombre());
                esps.add(t);
            }
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            System.out.println("Ocurrió un problema: "+ex.getMessage());
            HibernateUtil.rollbackTransaction();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar cargar las especificaciones de una material: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return esps;
    }

    public ArrayList<NTupla> getMaterialesAUtilizar() {
        ArrayList<NTupla> mau = new ArrayList<NTupla>();
        try {
            Iterator<SubObraXMaterial> it = getSubObraActual().getMateriales().iterator();
            SubObraXMaterial soxm = null;
            while(it.hasNext()){
                NTupla nt = new NTupla();
                soxm = it.next();
                nt.setId(soxm.hashCode());
                RecursoXProveedor rxp = soxm.getMaterial();
                RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
                Material m = (Material)RecursosUtil.getMaterial(re);
                nt.setNombre(m.getNombre());
                Object[] o = new Object[3];
                o[0]= re.getNombre();
                o[1]= soxm.getCantidad();
                o[2]= soxm.getPrecioUnitario();
                nt.setData(o);
                mau.add(nt);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar cargar los materiales a utilizar: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return mau;
    }

    public ArrayList<NTupla> mostrarRecursosEspecificosXProveedor(int idR, int idRE) {
        ArrayList<NTupla> esps = new ArrayList<NTupla>();
        try {
            HibernateUtil.beginTransaction();
            RecursoEspecifico re = (RecursoEspecifico)HibernateUtil.getSession().load(RecursoEspecifico.class, idRE);
            Iterator it = re.getRecursosXProveedor().iterator();

            Material m = (Material)HibernateUtil.getSession().load(Material.class, idR);

            while(it.hasNext()){
                NTupla nt = new NTupla();
                RecursoXProveedor rxp = (RecursoXProveedor)it.next();
                nt.setId(rxp.getId());
                nt.setNombre(rxp.getProveedor().getRazonSocial());
                String[] precios= new String[2];
                precios[0]= "<HTML><BODY>";
                precios[1]= "<HTML><BODY>";
                Iterator itPSC = rxp.getListaUltimosPrecios().iterator();
                while(itPSC.hasNext()){
                    PrecioSegunCantidad psc = (PrecioSegunCantidad)itPSC.next();
                    precios[0]+= psc.getCantidad()+m.getUnidadDeMedida().getAbreviatura()+" <b>x</b> "+psc.getPrecio()+"<br>";
                    precios[1]+=FechaUtil.getFecha(psc.getFechaVigencia())+"<br>";
                }
                nt.setData(precios);
                esps.add(nt);
            }
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar mostrar los precios del material por proveedor: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return esps;
    }

    public String getNombreRecurso(int idR, int idRE) {
        String nombre="";
        try {
            HibernateUtil.beginTransaction();
            Material m = (Material) HibernateUtil.getSession().load(Material.class, idR);
            nombre=m.getNombre();
            RecursoEspecifico re = (RecursoEspecifico)HibernateUtil.getSession().load(RecursoEspecifico.class, idRE);
            nombre+=(" "+re.getNombre());
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar mostrar el nombre del recurso: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return nombre;
    }

    public double getSubtotal(int idRXP, int cantidad) {
        double subtotal=0;
        try {
            RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
            Iterator it = rxp.getListaUltimosPrecios().iterator();
            double precio=0;
            double cantMayor=0;
            while(it.hasNext()){
                PrecioSegunCantidad psc = (PrecioSegunCantidad)it.next();
                if(cantidad >= psc.getCantidad() && psc.getCantidad() > cantMayor){
                    precio=psc.getPrecio();
                    cantMayor=psc.getCantidad();
                }
            }
            subtotal = precio*cantidad;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar calcular el subtotal: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return subtotal;
    }

    public boolean agregarMaterial(int idRXP, int cantidad, String desc,double precio) {
        boolean hayPrecio = true;
        try {
            if(precio == 0)
            {
                hayPrecio = false;
                int respuesta = JOptionPane.showOptionDialog(pantalla, "¿Desea registrar un nuevo precio para dicha cantidad?", "No existe precio para la cantidad requerida", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,  null,new Object[]{"Registrar", "Cancelar"}, "Registrar");
                if(respuesta == 0)
                {
                    RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
                    RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
                    Material r = RecursosUtil.getMaterial(re);
                    AgregarNuevoPrecio anp = new AgregarNuevoPrecio(this.gestorPadre.getGestorMateriales(),r.getId(),re.getId());
                    SwingPanel.getInstance().addWindow(anp);
                    anp.setVisible(true);
                }
            }
            else
            {
                HibernateUtil.beginTransaction();
                SubObraXMaterial soxm = this.nuevaSubObraXMaterial();
                RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
                soxm.setMaterial(rxp);
                soxm.setCantidad(cantidad);
                soxm.setDescripcion(desc);
                soxm.setPrecioUnitario(precio);
//                subObra.getMateriales().add(soxm);
                getSubObraActual().getMateriales().add(soxm);
                refrescarPantallas();
                HibernateUtil.commitTransaction();
            }

        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar agregar un material: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        pantalla.actualizar(1, "...", true);
        return hayPrecio;
    }

    public boolean modificarMaterial(int idRXP, int cantidad, String desc,double precio, SubObraXMaterial soxm) {
        boolean hayPrecio = true;
        try {
            if(precio == 0)
            {
                hayPrecio = false;
                int respuesta = JOptionPane.showOptionDialog(pantalla, "¿Desea registrar un nuevo precio para dicha cantidad?", "No existe precio para la cantidad requerida", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,  null,new Object[]{"Registrar", "Cancelar"}, "Registrar");
                if(respuesta == 0)
                {
                    RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
                    RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
                    Material r = RecursosUtil.getMaterial(re);
                    AgregarNuevoPrecio anp = new AgregarNuevoPrecio(this.gestorPadre.getGestorMateriales(),r.getId(),re.getId());
                    SwingPanel.getInstance().addWindow(anp);
                    anp.setVisible(true);
                }
            }
            else
            {
                HibernateUtil.beginTransaction();
                RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
                soxm.setMaterial(rxp);
                soxm.setCantidad(cantidad);
                soxm.setDescripcion(desc);
                soxm.setPrecioUnitario(precio);
                refrescarPantallas();
                HibernateUtil.commitTransaction();
            }

        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar agregar un material: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        pantalla.actualizar(1, "...", true);
        return hayPrecio;
    }
    
    public boolean quitarMaterial(int idDM) {
        boolean respuesta = false;
        for (int i = 0; i < getSubObraActual().getMateriales().size(); i++)
        {
            int hc = getSubObraActual().getMateriales().get(i).hashCode();
            if(hc==idDM)
            {
                if(gestorPadre.pantalla.getClass().equals(EditarCotizacionModificada.class))
                {
                    GestorEditarCotizacionModificada gestor = (GestorEditarCotizacionModificada)gestorPadre;
                    
                    //obtengo un material y pregunto si se puede eliminar
                    SubObraModificada subMod =((SubObraModificada)gestorPadre.getSubObraActual());
                    SubObraXMaterial soxmm =(SubObraXMaterial)subMod.getMateriales().get(i);
                    
                    if(gestor.getPlanificacion()!=null && soxmm!=null)
                    {
                        boolean estaEnUso = PlanificacionUtils.estaSubObraXMaterialEnUso(gestor.getPlanificacion(),soxmm);
                        if(estaEnUso)
                        {
                            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","<HTML><b>No</b> se puede eliminar el material, está asignado a una tarea de la planificacion"); 
                            return false;                            
                        }
                    }
                }   
                
                getSubObraActual().getMateriales().remove(i);
                return true;
            }
        }
        if(!respuesta){
            JOptionPane.showMessageDialog(this.pantalla, "Ha ocurrido un error al intentar quitar un material: \n", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return respuesta;
    }
    
    public SubObraXMaterial getSubObraXMaterialByHash(int hashDM)
    {
        for (int i = 0; i < getSubObraActual().getMateriales().size(); i++)
        {
            int hc = getSubObraActual().getMateriales().get(i).hashCode();
            if(hc==hashDM)
            {
                return getSubObraActual().getMateriales().get(i);
            }
        }
        return null;
    }

    public ArrayList<Tupla> mostrarProveedores()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Rubro r = (Rubro)sesion.createQuery("from Rubro r where r.nombre=:name").setParameter("name", "Material").uniqueResult();
                List<Proveedor> listaProv = sesion.createQuery("FROM Proveedor AS pv WHERE :rub in elements(pv.rubros)").setParameter("rub", r).list();

                Iterator<Proveedor> it = listaProv.iterator();
                while (it.hasNext())
                {
                   Proveedor rxp = it.next();
                   Tupla tp = new Tupla(rxp.getId(),rxp.getRazonSocial());
                   lista.add(tp);
                }

           }catch(Exception ex){
               ex.printStackTrace();
               JOptionPane.showMessageDialog(this.pantalla, "No se pudo cargar el objeto: \n"+ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
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
                HibernateUtil.beginTransaction();
                // CARGO EL PROVEEDOR
                Proveedor pv = (Proveedor) sesion.load(Proveedor.class,idProveedor);

                // VEO SUS RECURSOS
                Iterator<Rubro> itrub = pv.getRubros().iterator();
                while (itrub.hasNext())
                {
                   Rubro rub = itrub.next();
                   // CARGO SOLO LOS RECURSOS DE ESE RUBRO
                   // PODRE?
                   List<Recurso> listaRec = sesion.createQuery("FROM "+rub.getNombreClase()+" AS re").list();
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
                HibernateUtil.commitTransaction();
               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                HibernateUtil.rollbackTransaction();
                ex.printStackTrace();
                //pantalla.MostrarMensaje("EG-0002");
           }

        return lista;
    }

    public ArrayList<Tupla> mostrarProveedoresMaterial(int idRe){
        ArrayList<Tupla> provs = null;
        RecursoEspecifico re;
        try {
            provs = new ArrayList<Tupla>();
//            HibernateUtil.getSession().createQuery("select * from Rubro where nombre=:nombre").setParameter("", re)
//            HibernateUtil.getSession().createQuery("select * from Provedor")

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(pantalla.getParent(), "A ocurrido un error al intentar mostrar los proveedores del material seleccionado: \n"+ ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        return provs;
    }

    public String mostrarUnidadDeMedida(int idRecEsp)
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();
                HibernateUtil.beginTransaction();
                
                RecursoEspecifico re = (RecursoEspecifico)sesion.load(RecursoEspecifico.class,idRecEsp);

                Recurso rec = re.getRecurso();
                if(rec!=null)
                {
                    return rec.getUnidadDeMedida().getAbreviatura();
                }
                HibernateUtil.commitTransaction();
                return "";
                
               }catch(Exception ex)
           {
                System.out.println("No se pudo cargar el objeto: "+ex.getMessage());
                HibernateUtil.rollbackTransaction();
                ex.printStackTrace();
                //pantalla.MostrarMensaje("ME-0001");
           }
           return "";
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
            HibernateUtil.beginTransaction();
            
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

                   // pantalla.MostrarMensaje("MI-0001");return;

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
//                            Iterator<PrecioSegunCantidad> itf = rxp.getListaPrecios().iterator();
                            // http://www.journaldev.com/378/how-to-avoid-concurrentmodificationexception-when-using-an-iterator
                            CopyOnWriteArrayList<PrecioSegunCantidad> listaSincronizada = new CopyOnWriteArrayList<PrecioSegunCantidad>(rxp.getListaPrecios());
                            
                            // CON EL NUEVO GESTOR DE RECURSOS, SE CREA UN RXP CON UNA LISTA DE PRECIOS VACIA
                            // POR TAL MOTIVO, TUVE QUE AGREGAR ESTA OPCION NO CONTEMPLADA.
                            if(listaSincronizada.size() > 0 )
                            {
                                Iterator<PrecioSegunCantidad> itf = listaSincronizada.iterator();
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

                                            //pantalla.MostrarMensaje("MI-0001");return;

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

                                                //pantalla.MostrarMensaje("MI-0001");return;
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
                                // TENGO UN RXP CARGADO SIN LISTA DE PRECIOS
                                // LA CREO Y GUARDO DE UNA
                                PrecioSegunCantidad psc = new PrecioSegunCantidad();
                                psc.setCantidad(cantidad);
                                psc.setFecha(new Date());
                                psc.setPrecio(precio);
                                psc.setFechaVigencia(vigencia);
                                rxp.addPrecioSegunCantidad(psc);

                                // AHORA AGREGO EL RE AL PROVEEDOR
//                                pr.getListaArticulos().add(re);
                                // Y AGREGO EL PSC AL RECURSOESPECIFICO
//                                re.getProveedores().add(rxp);

                                HibernateUtil.beginTransaction();
                                sesion.save(psc);
                                sesion.save(rxp);
//                                sesion.update(pr);
//                                sesion.update(re);
                                HibernateUtil.commitTransaction();
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

                                //pantalla.MostrarMensaje("MI-0001");return;
                            }
                        }
                    }
                }
            HibernateUtil.commitTransaction();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LogUtil.addError("No se pudo comenzar la transacción en la actualizacion de precios");
            HibernateUtil.rollbackTransaction();
            //pantalla.MostrarMensaje("EG-0001");return;
        }

    }



    @Override
    public SubObra getSubObraActual()
    {
        return this.gestorPadre.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
        gestorPadre.refrescarPantallas();
    }

    public String mostrarMaterial(int idR) {
        String name = "";
        try {
            HibernateUtil.beginTransaction();
            Material m = (Material) HibernateUtil.getSession().load(Material.class, idR);
            name = m.getNombre();
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            LogUtil.addError("No se pudo mostrar el material: "+ex.getMessage());
        }
        return name;
    }

    public ArrayList<NTupla> mostrarPreciosVigentes(int idProv, int idRe) {
            ArrayList<NTupla> provs = new ArrayList<NTupla>();
        try {
            HibernateUtil.beginTransaction();
            RecursoEspecifico re = (RecursoEspecifico) HibernateUtil.getSession().load(RecursoEspecifico.class, idRe);
            Iterator<RecursoXProveedor> it= re.getRecursosXProveedor().iterator();
            while(it.hasNext()){
                RecursoXProveedor rxp = it.next();
                int id = rxp.getProveedor().getId();
                if(id == idProv){
                    Iterator<PrecioSegunCantidad> itPrecios = rxp.getListaUltimosPrecios().iterator();
                    while(itPrecios.hasNext()){
                        PrecioSegunCantidad psc = itPrecios.next();
                        Object o[] = {psc.getCantidad(),psc.getPrecio(),FechaUtil.getFecha(psc.getFechaVigencia())};
                        NTupla nt = new NTupla();
                        nt.setId(psc.getId());
                        nt.setData(o);
                        provs.add(nt);
                    }
                    break;
                }
            }
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            Logger.getLogger(GestorCotizacionMateriales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provs;
    }

    public String mostrarRE(int idRe) {
        String name = "";
        try {
            HibernateUtil.beginTransaction();
            RecursoEspecifico re = (RecursoEspecifico) HibernateUtil.getSession().load(RecursoEspecifico.class, idRe);
            name = re.getNombre();
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            LogUtil.addError("No se pudo mostrar el material: "+ex.getMessage());
        }
        return name;
    }

    public boolean quitarPrecioVigente(int idPrecio) {
        try {
            HibernateUtil.beginTransaction();
            PrecioSegunCantidad psc = (PrecioSegunCantidad) HibernateUtil.getSession().get(PrecioSegunCantidad.class, idPrecio);
            RecursoXProveedor rxp = (RecursoXProveedor)HibernateUtil.getSession().createQuery("from RecursoXProveedor rxp where :cID in elements(rxp.listaPrecios)").setParameter("cID", psc).uniqueResult();
            rxp.getListaPrecios().remove(psc);
            HibernateUtil.getSession().saveOrUpdate(rxp);
            HibernateUtil.getSession().saveOrUpdate(psc);
            HibernateUtil.getSession().delete(psc);
            HibernateUtil.commitTransaction();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(GestorCotizacionMateriales.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
            return false;
        }
    }

    public double getPrecioMaterial(int idRXP,int cantidad) {
        double precio = 0;
        try {
            HibernateUtil.beginTransaction();
            RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
            Iterator it = rxp.getListaUltimosPrecios().iterator();
            while(it.hasNext()){
                PrecioSegunCantidad psc = (PrecioSegunCantidad)it.next();
                if(cantidad >= psc.getCantidad())
                    precio=psc.getPrecio();
            }

            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            Logger.getLogger(GestorCotizacionMateriales.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
        }
        return precio;
    }
    
    //Tiene que ser publico para que java permita sobreescribirlo
    public SubObraXMaterial nuevaSubObraXMaterial()
    {
        SubObraXMaterial soxm = new SubObraXMaterial();
        return soxm;
    }

    public void actualizarPantallaSeleccion() {
        if(pantallaSeleccion != null)
        {
            pantallaSeleccion.mostrarRecursosEspecificosXProveedor();
        }
    }
}