/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;
import java.util.List;
import java.util.ArrayList;
import modelo.Tarea;
import modelo.Etapa;
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
import vista.planificacion.pantallaRegistrarTarea;
import controlador.utiles.gestorBDvarios;
import util.Tupla;
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
    private Tarea tarea;
        
        //private HashSet<String> HlistaNroTel;
        //private HashSet<TipoTelefono> HlistaTipoTel;


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
        this.listaRolTipoEspecialidad=listaRolTipoEspecialidad;
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
                       RolEmpleado re=irpt.getRol();
                       sesion.delete(irpt);
                       /*if(re no esta siendo usado en otra tarea) //TODO:
                       {sesion.delete(re);}*/
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
                   TipoEspecialidad te=(TipoEspecialidad) sesion.createQuery("from TipoEspecialidad where id ="+listaRolTipoEspecialidad.get(i).getId()).uniqueResult();
                   RangoEspecialidad re=(RangoEspecialidad) sesion.createQuery("from RangoEspecialidad where id ="+listaRolRangoEspecialidad.get(i).getId()).uniqueResult();
                    rE= crearRolEmpleado( te, re);
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
   private RolEmpleado crearRolEmpleado(TipoEspecialidad te, RangoEspecialidad re)
    {
       Especialidad esp=new Especialidad();
       esp.setRango(re);
       esp.setTipo(te);
       RolEmpleado r=new RolEmpleado();
       r.setEspecialidad(esp);
       return r;
       
    }


}




