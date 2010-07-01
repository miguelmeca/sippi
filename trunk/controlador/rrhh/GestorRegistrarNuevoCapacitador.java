package controlador.rrhh;

//

import java.util.ArrayList;
import java.util.List;
import modelo.TipoDocumento;
import modelo.TipoTelefono;
import modelo.Telefono;
import modelo.Domicilio;
import modelo.TipoCapacitacion;
import modelo.Capacitacion;
import modelo.Barrio;
import modelo.Capacitador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import vista.rrhh.pantallaRegistrarCapacitador;
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




public class GestorRegistrarNuevoCapacitador {

        private pantallaRegistrarCapacitador pantalla;
        private TipoDocumento tipoDocumentoCapacitador;
	private String nroDocumento;
        private String nombreCapacitador;
        private String apellidoCapacitador;
        private String emailCapacitador;
        private String cuilCapacitador;
        private Date fechaNacimientoCapacitador;
	private Domicilio domicilioCapacitador;
	private Object listaPaises;
	private Object listaProvincias;
	private Object listaLocalidades;
	private Object listaBarrios;
        
        private ArrayList<TipoCapacitacion> listaTipoCapacitaciones;
        private ArrayList<String> listaNroTel;
        private ArrayList<TipoTelefono> listaTipoTel;
	//private Object listaRangos;
	private Date fechaActual;
        private String calleD;
        private int nmroD;
        private int pisoD;
        private String departamentoD;
        private String codigoPostalD;
        private Barrio barrioD;
        //private HashSet<String> HlistaNroTel;
        //private HashSet<TipoTelefono> HlistaTipoTel;


    public GestorRegistrarNuevoCapacitador(pantallaRegistrarCapacitador pantalla)
    {
        this.pantalla = pantalla;
        listaNroTel= new  ArrayList<String>();
        listaTipoTel= new ArrayList<TipoTelefono>();
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
         fechaActual=new Date();
    }
        

        public ArrayList<Tupla> mostrarTiposDeDocumento() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeDocumento();

	}
	
        public boolean ValidarDocumento(String nroDoc)
        {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            List listNroDoc=new ArrayList();
            boolean aprobado=true;
            listNroDoc =sesion.createQuery("Select nroDoc from modelo.Capacitador").list();//
            String n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(String)listNroDoc.get(i);
                if(n.equals(nroDoc))
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
            listNroDoc =sesion.createQuery("Select cuil from modelo.Capacitador").list();//
            String n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(String)listNroDoc.get(i);
                if(n.equals(cuil))
                {aprobado=false;}
            }
            return aprobado;
        }
	public void datosPersonalesCapacitador(String cuil, String nmroDoc, Tupla tipoDocumento, String nombre, String apellido, Date fechaNac, String email)
        {
            nroDocumento=nmroDoc;
            gestorBDvarios bdv = new gestorBDvarios();                        
            tipoDocumentoCapacitador=bdv.getTipoDeDocumento(tipoDocumento.getId());
            nombreCapacitador=nombre;
            apellidoCapacitador=apellido;
            emailCapacitador=email;
            fechaNacimientoCapacitador=fechaNac;
            cuilCapacitador=cuil;
	
	}
	
	public void datosDomicilioCapacitador(String calle, String nro, String depto, String piso, String cp, Tupla tBarrio)
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
	
	public ArrayList<Tupla> mostrarTipoCapacitacion()
        {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTipoCapacitacion();

	}
		
        public void tiposCapacitacion(ArrayList<Tupla> lstTipoCapacitaciones)
        {
           
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<lstTipoCapacitaciones.size();i++)
            {
                listaTipoCapacitaciones.add(bdv.getTipoCapacitacion(lstTipoCapacitaciones.get(i).getId()));                
                
            }
	
	}
        
	public boolean capacitadorConfirmado()
        {
            Capacitador capacitador;
            try{
            capacitador=crearCapacitador();}
            catch (Exception ex)
            {
                System.out.println("No se pudo crear el capacitador");
                return false;
            }
            Session sesion;
            ///////////////////////////////////
             try {
                    //sesion = HibernateUtil.getSession();
                     SessionFactory sf = HibernateUtil.getSessionFactory();


                    sesion = sf.openSession();
                    try{
                    //HibernateUtil.beginTransaction();
                    sesion.beginTransaction();

                    Iterator itt=capacitador.getTelefonos().iterator();
                    while(itt.hasNext())
                    {
                        Telefono tel=(Telefono)itt.next();
                        sesion.save(tel);
                    }
                   
                   /*Esto no va....
                    Iterator itCap=capacitador.getTiposCapacitacion().iterator();
                    while(itCap.hasNext())
                    {
                        TipoCapacitacion tcap=(TipoCapacitacion)itCap.next();
                        sesion.save(tcap);
                    }*/
                    if(capacitador.getDomicilio()!=null)
                    {sesion.save(capacitador.getDomicilio());}
                    sesion.save(capacitador);


                    sesion.getTransaction().commit();
                    //HibernateUtil.commitTransaction();
                    
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
		
	public Capacitador crearCapacitador()
        {

            Date fechaAltaActual=fechaActual;
           // fecha_Alta=System
            
            Capacitador cap=new Capacitador(nombreCapacitador, apellidoCapacitador,fechaNacimientoCapacitador, tipoDocumentoCapacitador ,nroDocumento, cuilCapacitador,  emailCapacitador,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD  ,listaNroTel, listaTipoTel, listaTipoCapacitaciones,  fechaAltaActual);
            //Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,HlistaNroTel, HlistaTipoTel, fechaAltaActual);
            
            return cap;

	}
	
	public void finCU() {
	
	}
	
	public void telefonosCapacitador(ArrayList<String> numero,ArrayList<Tupla> tipo )
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
