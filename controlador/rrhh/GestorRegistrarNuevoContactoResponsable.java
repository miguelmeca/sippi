package controlador.rrhh;

//

import java.util.ArrayList;
import java.util.List;
import modelo.TipoDocumento;
import modelo.TipoTelefono;
import modelo.Telefono;
import modelo.ContactoResponsable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import vista.rrhh.pantallaRegistrarContactoResponsable;
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
//  @ Author : 
//
//




public class GestorRegistrarNuevoContactoResponsable {

        private pantallaRegistrarContactoResponsable pantalla;
        
        private String nombreContactoResponsable;
        private String apellidoContactoResponsable;
        private String emailContactoResponsable;
        private String cuilContactoResponsable;
        private String cargoContactoResponsable;
        private ArrayList<String> listaNroTel;
        private ArrayList<TipoTelefono> listaTipoTel;
	//private Object listaRangos;
	private Date fechaActual;
        


    public GestorRegistrarNuevoContactoResponsable(pantallaRegistrarContactoResponsable pantalla)
    {
        this.pantalla = pantalla;
        listaNroTel= new  ArrayList<String>();
        listaTipoTel= new ArrayList<TipoTelefono>();
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
            listNroDoc =sesion.createQuery("Select nroDoc from modelo.ContactoResponsable").list();//
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
            listNroDoc =sesion.createQuery("Select cuil from modelo.ContactoResponsable").list();//
            String n;
            for(int i=0; i<listNroDoc.size();i++)
            {
                n=(String)listNroDoc.get(i);
                if(n.equals(cuil))
                {aprobado=false;}
            }
            return aprobado;
        }
	public void datosPersonalesContactoResponsable(String cuil, String nombre, String apellido, String email, String cargo)
        {
            
            //gestorBDvarios bdv = new gestorBDvarios();
            //tipoDocumentoCapacitador=bdv.getTipoDeDocumento(tipoDocumento.getId());
            nombreContactoResponsable=nombre;
            apellidoContactoResponsable=apellido;
            emailContactoResponsable=email;
            cuilContactoResponsable=cuil;
            cargoContactoResponsable=cargo;
	
	}
	
        public ArrayList<Tupla> mostrarTiposDeTelefono() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeTelefono();

	}
	
	public boolean capacitadorConfirmado()
        {
            ContactoResponsable contacto;
            try{
            contacto=crearContactoResponsable();}
            catch (Exception ex)
            {
                System.out.println("No se pudo crear el contacto responsable");
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

                    Iterator itt=contacto.getTelefonos().iterator();
                    while(itt.hasNext())
                    {
                        Telefono tel=(Telefono)itt.next();
                        sesion.save(tel);
                    }
                   
                    sesion.save(contacto);

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
		
	public ContactoResponsable crearContactoResponsable()
        {

            Date fechaAltaActual=fechaActual;
           // fecha_Alta=System
            
            ContactoResponsable cr=new ContactoResponsable(nombreContactoResponsable, apellidoContactoResponsable, cuilContactoResponsable,  emailContactoResponsable,  listaNroTel, listaTipoTel, fechaAltaActual);
            //Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,HlistaNroTel, HlistaTipoTel, fechaAltaActual);
            
            return cr;

	}
	
	public void finCU() {
	
	}
	
	public void telefonosContactoResponsable(ArrayList<String> numero,ArrayList<Tupla> tipo )
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
