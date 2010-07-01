package controlador.rrhh;

//

import java.util.ArrayList;
import java.util.List;
import modelo.TipoDocumento;
import modelo.TipoTelefono;
import modelo.Telefono;
import modelo.Domicilio;
import modelo.TipoEspecialidad;
import modelo.RangoEspecialidad;
import modelo.TipoCapacitacion;
import modelo.Capacitacion;
import modelo.Especialidad;
import modelo.Barrio;
import modelo.Empleado;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import vista.rrhh.pantallaRegistrarEmpleado;
import controlador.utiles.gestorGeoLocalicacion;
import controlador.utiles.gestorBDvarios;
import util.Tupla;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.Set;
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : GestorRegistrarNuevoEmpleado.java
//  @ Date : 10/06/2010
//  @ Author : Fran
//
//




public class GestorRegistrarNuevoEmpleado {

        private pantallaRegistrarEmpleado pantalla;
        private TipoDocumento tipoDocumentoEmpleado;
	private String nroDocumento;
        private String nombreEmpleado;
        private String apellidoEmpleado;
        private String emailEmpleado;
        private String cuilEmpleado;
        private Date fechaNacimientoEmpleado;
	private Domicilio domicilioEmpleado;
	private Object listaPaises;
	private Object listaProvincias;
	private Object listaLocalidades;
	private Object listaBarrios;
        private ArrayList<TipoEspecialidad> listaTipoEspecialidades;
        private ArrayList<RangoEspecialidad> listaRangoEspecialidades;
        private ArrayList<TipoCapacitacion> listaTipoCapacitaciones;
        private ArrayList<Date> listaVencimientoCapacitaciones;
        private ArrayList<String> listaNroTel;
        private ArrayList<TipoTelefono> listaTipoTel;
	//private Object listaRangos;
	private Object especialidadesYRangos;
	private Object fechaActual;
	private int legajoEmpleado;
        private String calleD;
        private int nmroD;
        private int pisoD;
        private String departamentoD;
        private String codigoPostalD;
        private Barrio barrioD;
        //private HashSet<String> HlistaNroTel;
        //private HashSet<TipoTelefono> HlistaTipoTel;


    public GestorRegistrarNuevoEmpleado(pantallaRegistrarEmpleado pantalla)
    {
        this.pantalla = pantalla;
        listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
        listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
        listaNroTel= new  ArrayList<String>();
        listaTipoTel= new ArrayList<TipoTelefono>();
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
         listaVencimientoCapacitaciones=new ArrayList<Date>();
    }
        

        public ArrayList<Tupla> mostrarTiposDeDocumento() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeDocumento();

	}
	/*public ArrayList<String> mostrarTiposDeDocumento()
        {

            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            sesion.beginTransaction();
            List lista = sesion.createQuery("from TipoDocumento").list();
            sesion.getTransaction().commit();

            ArrayList<String> listaNombres = new ArrayList<String>();
            for (int i = 0; i < lista.size(); i++) {
                TipoDocumento td = (TipoDocumento)lista.get(i);
                listaNombres.add(td.getNombre());
            }

            return listaNombres;

	}*/
	
	public void verificarExistenciaEmpleado() {
	
	}
        public boolean ValidarDocumento(String nroDoc)
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            List listNroDoc=new ArrayList();
            boolean aprobado=true;
            listNroDoc =sesion.createQuery("Select nroDoc from modelo.Empleado").list();//
            String n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(String)listNroDoc.get(i);
                if(n.equals(nroDoc))
                {aprobado=false;}
            }
            return aprobado;
        }
	public boolean ValidarLegajo(String leg)
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            List listNroDoc=new ArrayList();
            boolean aprobado=true;
            listNroDoc =sesion.createQuery("Select legajo from modelo.Empleado").list();//
            int nleg;
            nleg=Integer.parseInt(leg);
            int n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(Integer)listNroDoc.get(i);
                if(n==(nleg))
                {aprobado=false;}
            }
            return aprobado;
        }
        public boolean ValidarCuil(String cuil)
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            List listNroDoc=new ArrayList();
            boolean aprobado=true;
            listNroDoc =sesion.createQuery("Select cuil from modelo.Empleado").list();//
            String n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(String)listNroDoc.get(i);
                if(n.equals(cuil))
                {aprobado=false;}
            }
            return aprobado;
        }
	public void datosPersonalesEmpleado(int leg,String cuil, String nmroDoc, Tupla tipoDocumento, String nombre, String apellido, Date fechaNac, String email)
        {
            legajoEmpleado=leg;
            nroDocumento=nmroDoc;
            gestorBDvarios bdv = new gestorBDvarios();                        
            tipoDocumentoEmpleado=bdv.getTipoDeDocumento(tipoDocumento.getId());
            nombreEmpleado=nombre;
            apellidoEmpleado=apellido;
            emailEmpleado=email;
            fechaNacimientoEmpleado=fechaNac;
            cuilEmpleado=cuil;
	
	}
	
	public void datosDomicilioEmpleado(String calle, String nro, String depto, String piso, String cp, Tupla tBarrio)
        {
            gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();                        
            barrioD =ggl.getBarrio(tBarrio.getId());
            if(nro.equals(""))
            {nmroD=0;}
            else{
            nmroD=Integer.parseInt(nro);}
            if(nro.equals(""))
            {pisoD=0;}
            else{
            pisoD =Integer.parseInt(piso);}
            calleD=calle;
            departamentoD=depto;
            codigoPostalD=cp;
	
	}

        public ArrayList<Tupla> mostrarTiposDeTelefono() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeTelefono();

	}
	public ArrayList<Tupla> mostrarPaises() {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getPaises();

	}
	
	/*public void paisEmpleado() {
	
	}*/
	
	public ArrayList<Tupla> mostrarProvincias(int idPais)
        {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getProvincias(idPais);
	}

        public ArrayList<Tupla> mostrarLocalidades(int idProvincia) {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getLocalidades(idProvincia);

	}

	public ArrayList<Tupla> mostrarBarrios(int idLocalidad) {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getBarrios(idLocalidad);

	}
	
	/*public void provinciaEmpleado() {
	
	}		
	
	public void localidadEmpleado() {
	
	}
		
	public void barrioEmpleado() {
	
	}
	*/
	public ArrayList<Tupla> mostrarTipoEspecialidad() 
        {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTipoEspecialidades();
	
	}
	
	public ArrayList<Tupla> mostrarRangoEspecialidad()
        {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getRangosEspecialidad();
	
	}
	public ArrayList<Tupla> mostrarTipoCapacitacion()
        {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTipoCapacitacion();

	}
	public void tipoEspecialidadYRango(ArrayList<Tupla> lstTipoEspecialidad, ArrayList<Tupla> lstRangoEspecialidad)
        {
            listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
        listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<lstTipoEspecialidad.size();i++)
            {
                listaTipoEspecialidades.add(bdv.getTipoEspecialidad(lstTipoEspecialidad.get(i).getId()));                
                listaRangoEspecialidades.add(bdv.getRangoEspecialidad(lstRangoEspecialidad.get(i).getId()));
            }
	
	}
	
        public void capacitaciones(ArrayList<Tupla> lstTipoCapacitaciones, ArrayList<Date> lstVencimientosCapacitaciones)
        {
           
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
         listaVencimientoCapacitaciones=new ArrayList<Date>();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<lstTipoCapacitaciones.size();i++)
            {
                listaTipoCapacitaciones.add(bdv.getTipoCapacitacion(lstTipoCapacitaciones.get(i).getId()));                
                //listaRangoEspecialidades.add(bdv.getRangoEspecialidad(lstRangoEspecialidad.get(i).getId()));
                listaVencimientoCapacitaciones=lstVencimientosCapacitaciones;
            }
	
	}
        
	public boolean empleadoConfirmado()
        {
            Empleado empleado;
            try{
            empleado=crearEmpleado();}
            catch (Exception ex)
            {
                System.out.println("No se pudo crear el empleado");
                return false;
            }
            Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
                     //SessionFactory sf = HibernateUtil.getSessionFactory();
                    //sesion = sf.openSession();
                    try{
                    HibernateUtil.beginTransaction();
                    //sesion.beginTransaction();

                    Iterator itt=empleado.getTelefonos().iterator();
                    while(itt.hasNext())
                    {
                        Telefono tel=(Telefono)itt.next();
                        sesion.save(tel);
                    }
                    Iterator itEsp=empleado.getEspecialidades().iterator();
                    while(itEsp.hasNext())
                    {
                        Especialidad esp=(Especialidad)itEsp.next();
                        sesion.save(esp);
                    }
                   // Set CaPP=empleado.getCapacitaciones();

                   // Iterator itCap=CaPP.iterator();
                   Iterator itCap=empleado.getCapacitaciones().iterator();
                    while(itCap.hasNext())
                    {
                        Capacitacion cap=(Capacitacion)itCap.next();
                        sesion.save(cap);
                    }
                    if(empleado.getDomicilio()!=null)
                    {sesion.save(empleado.getDomicilio());}
                    sesion.save(empleado);


                     //sesion.getTransaction().commit();
                   HibernateUtil.commitTransaction();
                   
                    return true;
                    }catch(Exception e) {
                        System.out.println("No se pudo realizar la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                        
                        return false;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
                return false;
            }
            
	}
	
	public int generarLegajoEmpleado()
        {
            int mayorLegajo;
            Session sesion;
            try{
             //SessionFactory sf = HibernateUtil.getSessionFactory();
            //Session sesion = sf.openSession();
            sesion = HibernateUtil.getSession();
            
            
            //sesion.beginTransaction();
            
            //try{

            Object ob =sesion.createQuery("Select MAX(legajo) from Empleado").uniqueResult();
            if(ob!=null)
            {mayorLegajo=(Integer)ob;}
            else{mayorLegajo=0;}
            //sesion.getTransaction().commit();
            //}
            }
            catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
                mayorLegajo=0;
            }

            return (mayorLegajo+1);
	
	}
	
	public void buscarUltimoLegajoEmpleado() {
	
	}
	
	public Empleado crearEmpleado()
        {

            Date fechaAltaActual=new Date();
           // fecha_Alta=System
            
            Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,listaNroTel, listaTipoTel, listaTipoCapacitaciones, listaVencimientoCapacitaciones, fechaAltaActual);
            //Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,HlistaNroTel, HlistaTipoTel, fechaAltaActual);
            
            return emp;

	}
	
	public void finCU() {
	
	}
	
	public void telefonosEmpleado(ArrayList<String> numero,ArrayList<Tupla> tipo )
        {
           

            listaNroTel=numero;
            listaTipoTel=new ArrayList();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<tipo.size();i++)
            {
               TipoTelefono td= bdv.getTipoDeTelefono(tipo.get(i).getId());
               listaTipoTel.add(td); 
            }            
	}


	/*public void telefonosEmpleado(ArrayList<String> numero,ArrayList<Tupla> tipo )
        {
            HlistaNroTel=new HashSet();
            for(int i=0; i<tipo.size();i++)
            {

               HlistaNroTel.add(numero.get(i));
            }
            HlistaTipoTel=new HashSet();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<tipo.size();i++)
            {
               TipoTelefono td= bdv.getTipoDeTelefono(tipo.get(i).getId());
               HlistaTipoTel.add(td);
            }
	}*/
	public void seleccionTipoDocumento() {
	
	}
	
	public void documentoEmpleado() {
	
	}
		
}
