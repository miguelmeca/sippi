/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion_old;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelo.Tarea;
import modelo.DetalleEtapa;
import modelo.Etapa;
import modelo.Presupuesto;
import modelo.GrupoDeTrabajo;
import modelo.RolEmpleado;
import modelo.Criticidad;
import modelo.Especialidad;
import modelo.InstanciaDeRolPorTarea;
import modelo.TipoEspecialidad;
import modelo.RangoEspecialidad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import vista.planificacion_old.pantallaRegistrarTarea;
import controlador.utiles.gestorBDvarios;
import util.Tupla;
import util.NTupla;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
/**
 *
 * @author Administrador
 */
public class GestorRegistrarTarea
{
    private pantallaRegistrarTarea pantalla;
	
    private String nombreT;
    private String ubicacionT;
    private String observacionesT;
    private Tupla tCriticidad;
    private Criticidad criticicidad;
    private Date fechaInicio;
    private Date fechaFin;
    private ArrayList<Tupla> listaRolNombre;
    private ArrayList<Tupla> listaRolTipoEspecialidad;
    private ArrayList<Tupla> listaRolRangoEspecialidad;
    private ArrayList<Double> listaRolHsNormales;
    private ArrayList<Double> listaRolHs50;
    private ArrayList<Double> listaRolHs100;
    private HashMap mapaRolesCreados;
    private ArrayList<NTupla> listaRolesCreados;
    private Tarea tarea;
        
        //private HashSet<String> HlistaNroTel;
        //private HashSet<TipoTelefono> HlistaTipoTel;

//TODO: BOTON CANCELAR
    //TODO: CODIGO EN EL EVENTO DISPOSE (en este momento si se sale haciendo clic en la crucecita, la tarea se registra sin nombre)
    public GestorRegistrarTarea(pantallaRegistrarTarea pantalla)
    {
        this.pantalla = pantalla;
        listaRolNombre= new ArrayList<Tupla>();
        listaRolTipoEspecialidad= new  ArrayList<Tupla>();
        listaRolRangoEspecialidad = new ArrayList<Tupla>();
        listaRolHsNormales = new ArrayList<Double>();
        listaRolHs50 = new ArrayList<Double>();
        listaRolHs100 = new ArrayList<Double>();
    }
    public int crearTarea(int idEtapa)
    {
            tarea= new Tarea();
            int mayorIdTarea;

            Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
                    Object ob =sesion.createQuery("Select MAX(id) from Tarea").uniqueResult();
                    if(ob!=null)
                    {mayorIdTarea=(Integer)ob;}
                    else{mayorIdTarea=0;}
                    tarea.setId(mayorIdTarea+1);
                    try{
                    HibernateUtil.beginTransaction();
                    sesion.save(tarea);
                    Etapa etapa = (Etapa) sesion.createQuery("from Etapa where id ="+idEtapa).uniqueResult();
                    etapa.getTareas().add(tarea);
                    sesion.saveOrUpdate(etapa);
                   HibernateUtil.commitTransaction();

                    return tarea.getId();
                    }catch(Exception e) {
                        System.out.println("No se pudo realizar la transaccion creando la tarea\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();

                        return -1;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion creando la tarea");
                return -1;
            }

	}

    public void datosGenerales(String nombre, String ubicacion ,String observaciones ,Tupla criticidadT, Date fechaI, Date fechaF )
    {
        this.nombreT=nombre;
        this.ubicacionT=ubicacion;
        this.observacionesT=observaciones;
        this.tCriticidad=criticidadT;
        this.fechaInicio=fechaI;
        this.fechaFin=fechaF;
    }
    public void datosGrupoRoles(ArrayList<Tupla> listaRolNombre, ArrayList<Tupla> listaRolTipoEspecialidadR, ArrayList<Tupla> listaRolRangoEspecialidad, ArrayList<Double> listaRolHsNormales, ArrayList<Double> listaRolHs50, ArrayList<Double> listaRolHs100)
    {
       this.listaRolNombre = listaRolNombre;
        this.listaRolTipoEspecialidad=listaRolTipoEspecialidadR;
        this.listaRolRangoEspecialidad = listaRolRangoEspecialidad;
        this.listaRolHsNormales = listaRolHsNormales;
        this.listaRolHs50 = listaRolHs50;
        this.listaRolHs100 = listaRolHs100;

    }

    public void vaciarDatos()
    {
        this.nombreT="";
        this.ubicacionT="";
        this.observacionesT="";
        this.tCriticidad=null;
        this.fechaInicio=null;
        this.fechaFin=null;
        listaRolNombre= new ArrayList<Tupla>();
        listaRolTipoEspecialidad= new  ArrayList<Tupla>();
        listaRolRangoEspecialidad = new ArrayList<Tupla>();
        listaRolHsNormales = new ArrayList<Double>();
        listaRolHs50 = new ArrayList<Double>();
        listaRolHs100 = new ArrayList<Double>();
    }

    public ArrayList<Tupla> mostrarCriticidad()
    {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getCriticidad();

    }
    public ArrayList<Tupla> mostrarTiposEspecialidad()
    {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTipoEspecialidades();

    }

    public ArrayList<Tupla> mostrarRangosEspecialidad()
    {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getRangosEspecialidad();
    }

    public ArrayList<NTupla> mostrarRolesCreados(int idPresupuesto)
    {
         Session sesion;
         mapaRolesCreados = new HashMap();
         listaRolesCreados=new ArrayList<NTupla>();

            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
                    DetalleEtapa dt = (DetalleEtapa) sesion.createQuery("from DetalleEtapa where id ="+idPresupuesto).uniqueResult();
                    /*for (Etapa et : presupuesto.getEtapas())
                    {
                        for (DetalleEtapa t : et.getTareas())
                        {
                            if(t instanceof Tarea)
                            {
                                for(InstanciaDeRolPorTarea ins : t.getListaInstRolXTarea())
                                {
                                    mapaRolesCreados.put(ins.getRol().getId(), ins.getRol()) ;
                                }
                            }
                        }
                    }***/
                    /*Iterator it = mapaRolesCreados.entrySet().iterator();

                    while (it.hasNext()) {
                    Map.Entry e = (Map.Entry)it.next();
                    RolEmpleado r = (RolEmpleado)e.getValue();
                    NTupla nomT=new NTupla();
                    nomT.setId(r.getId());
                    nomT.setNombre(r.getNombre());
                    Tupla[] datos=new Tupla[2];
                    datos[0]=new Tupla();
                    datos[1]=new Tupla();
                    datos[0].setId(r.getEspecialidad().getTipo().getId());
                    datos[0].setNombre(r.getEspecialidad().getTipo().getNombre());
                    datos[1].setId(r.getEspecialidad().getRango().getId());
                    datos[1].setNombre(r.getEspecialidad().getRango().getNombre());
                    nomT.setData(datos);
                    listaRolesCreados.add(nomT);
                    }
*/

                    return listaRolesCreados;

            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion creando la tarea");
                return new ArrayList<NTupla>();
            }
    }


   public boolean tareaModificada()
   {
       Session sesion;
            //////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();

                    try{
                    HibernateUtil.beginTransaction();
                   /* if(tarea.getGrupo()==null)
                    {tarea.setGrupo(new GrupoDeTrabajo());}
                    Iterator irgt =tarea.getGrupo().getInstanciasRoles().iterator();*/

                    Iterator irgt =tarea.getListaInstRolXTarea().iterator();
                    while(irgt.hasNext())
                    {
                       InstanciaDeRolPorTarea irpt=(InstanciaDeRolPorTarea)irgt.next();
                       
                       sesion.delete(irpt);
                       RolEmpleado re=(RolEmpleado)mapaRolesCreados.get(irpt.getRol().getId());
                       if( re==null) //Si el Rol no esta siendo usado por ninguna otra tarea, lo borro (por si el usaurio lo borro)
                       {sesion.delete(irpt.getRol());
                        
                        }
                        
                    }
                   criticicidad = (Criticidad) sesion.createQuery("from Criticidad where id ="+tCriticidad.getId()).uniqueResult();
                   try{
                    modificarTarea(sesion);
                   }
                    catch (Exception ex)
                    {
                    System.out.println("No se pudo modificar la tarea");
                    return false;
                    }

                    //Iterator irgtg =tarea.getGrupo().getInstanciasRoles().iterator();
                    Iterator irgtg =tarea.getListaInstRolXTarea().iterator();
                    while(irgtg.hasNext())
                    {
                        InstanciaDeRolPorTarea reg=(InstanciaDeRolPorTarea)irgtg.next();
                        sesion.save(reg);
                    }
                   // sesion.saveOrUpdate(tarea.getGrupo()); //
                    sesion.saveOrUpdate(tarea);
                    
                   HibernateUtil.commitTransaction();

                    return true;
                    }catch(Exception e) {
                        System.out.println("No se pudo realizar la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();

                        return false;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion modificando la tarea");
                return false;
            }

   }

   public void modificarTarea(Session sesion)
        {
           tarea.setCriticidad(criticicidad);
           tarea.setDescripcion(nombreT);
           tarea.setUbicacion(ubicacionT);
           tarea.setObservaciones(observacionesT);
           tarea.setFechaInicio(fechaInicio);
           tarea.setFechaFin(fechaFin);
           //tarea.setGrupo(new GrupoDeTrabajo());
           RolEmpleado rE;
            for (int i = 0; i < listaRolNombre.size(); i++)
            {
                if(listaRolNombre.get(i).getId()<0) //si el rol es nuevo
                {
                   /*TipoEspecialidad te=(TipoEspecialidad) sesion.createQuery("from TipoEspecialidad where id ="+listaRolTipoEspecialidad.get(i).getId()).uniqueResult();
                   RangoEspecialidad re=(RangoEspecialidad) sesion.createQuery("from RangoEspecialidad where id ="+listaRolRangoEspecialidad.get(i).getId()).uniqueResult();
                   Especialidad esp=(Especialidad) sesion.createQuery("from Especialidad where tipo = (:tipoEsp) and rango = (:rangoEsp)").setParameter("tipoEsp", te).setParameter("rangoEsp", re).uniqueResult();*/
                   gestorBDvarios bdv = new gestorBDvarios();
                   Especialidad esp= bdv.getEspecialidad(listaRolTipoEspecialidad.get(i).getId(), listaRolRangoEspecialidad.get(i).getId());
                   rE= crearRolEmpleado(listaRolNombre.get(i).getNombre(), esp);
                    sesion.saveOrUpdate(rE);
                }
                else // el rol es d los q ya existen para otra tarea, entonces levanto el rol
                {
                    rE=(RolEmpleado) sesion.createQuery("from RolEmpleado where id ="+listaRolNombre.get(i).getId()).uniqueResult();
                }
                InstanciaDeRolPorTarea irpt=new InstanciaDeRolPorTarea(rE, listaRolHsNormales.get(i), listaRolHs50.get(i),listaRolHs100.get(i));

                //tarea.getGrupo().agregarInstanciasRoles(irpt);
                tarea.agregarRolAGrupo(irpt);


            }
         }
   private RolEmpleado crearRolEmpleado(String nomR, Especialidad esp)
    {
       RolEmpleado r=new RolEmpleado();
       r.setEspecialidad(esp);
       r.setNombre(nomR);
       return r;
       
    }


}




