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
import java.sql.Blob;
import util.Tupla;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import util.imagenes.GestorImagenes;
//import java.util.Set;
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : GestorModificarNuevoEmpleado.java
//  @ Date : 10/08/2010
//  @ Author : Fran
//
//


//Esta clase actua como mediador entre la interfaz de usuario y el gestor, conteniendo en si misma el archivo binario de la imagen.

public class GestorModificarEmpleado   implements IGestorEmpleado {

        private pantallaRegistrarEmpleado pantalla;
        private TipoDocumento tipoDocumentoEmpleado;
	private String nroDocumento;
        private String nombreEmpleado;
        private String apellidoEmpleado;
        private String emailEmpleado;
        private String cuilEmpleado;
        private Date fechaNacimientoEmpleado;
        private Date fechaIngresoEmpleado;
	private Domicilio domicilioEmpleado;
	private Object listaPaises;
	private Object listaProvincias;
	private Object listaLocalidades;
	private Object listaBarrios;
        //private ArrayList<TipoEspecialidad> listaTipoEspecialidades;
        //private ArrayList<RangoEspecialidad> listaRangoEspecialidades;
        private ArrayList<Especialidad> listaEspecialidades;
        private ArrayList<TipoCapacitacion> listaTipoCapacitaciones;
        private ArrayList<Date> listaVencimientoCapacitaciones;
        private ArrayList<String> listaNroTel;
        private ArrayList<TipoTelefono> listaTipoTel;
	private Object especialidadesYRangos;
	private Object fechaActual;
	private int legajoEmpleado;
        private String calleD;
        private int nmroD;
        private int pisoD;
        private String departamentoD;
        private String codigoPostalD;
        private Barrio barrioD;
        private Empleado empleadoModif;
        private int legajoEmpleadoOriginal;
        private String nroDocumentoOriginal;
        private String cuilEmpleadoOriginal;
        private GestorImagenes gestorImagenes;


    public GestorModificarEmpleado(pantallaRegistrarEmpleado pantalla)
    {
        this.pantalla = pantalla;
        //listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
        //listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
        listaEspecialidades= new ArrayList<Especialidad>();
        listaNroTel= new  ArrayList<String>();
        listaTipoTel= new ArrayList<TipoTelefono>();
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
         listaVencimientoCapacitaciones=new ArrayList<Date>();
         gestorImagenes=new GestorImagenes();

    }


        public ArrayList<Tupla> mostrarTiposDeDocumento() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeDocumento();

	}


	public void verificarExistenciaEmpleado() {

	}
        public boolean ValidarDocumento(String nroDoc, Tupla td)
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
                {
                    aprobado=false;
                    if(nroDocumentoOriginal.equals(nroDoc))
                    {aprobado=true;
                     break;}
                }
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
                {
                    aprobado=false;
                    if(nleg==legajoEmpleadoOriginal)
                    {aprobado=true;
                     break;}
                }
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
                {
                    aprobado=false;
                    if(cuilEmpleadoOriginal.equals(cuil))
                    {aprobado=true;
                     break;}
                }
            }
            return aprobado;
        }
	

        public ArrayList<Tupla> mostrarTiposDeTelefono() {

           gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTiposDeTelefono();

	}
	public ArrayList<Tupla> mostrarPaises() {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getPaises();

	}



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


	public ArrayList<Tupla> mostrarTipoEspecialidad()
        {
            gestorBDvarios bdv = new gestorBDvarios();
           return bdv.getTuplasTipoEspecialidades();

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
        public boolean levantarEmpleado(int id)
        {
            Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
            //sesion.beginTransaction();
            empleadoModif = (Empleado) sesion.createQuery("from Empleado where id ="+id).uniqueResult();
              } catch (Exception ex)
            {
                System.out.println("Error levantando el empleado");
                return false;
            }
            legajoEmpleadoOriginal=empleadoModif.getLegajo();
            nroDocumentoOriginal=empleadoModif.getNroDoc();
            cuilEmpleadoOriginal=empleadoModif.getCuil();
            //Envio a la pantalla los datos personales del empleado levantado
            pantalla.datosPersonalesEmpleado(String.valueOf(empleadoModif.getLegajo()), empleadoModif.getCuil(), empleadoModif.getNroDoc(), empleadoModif.getTipoDoc().getId(),empleadoModif.getNombre(), empleadoModif.getApellido(), empleadoModif.getFechadeNac(),empleadoModif.getFechaIngreso(), empleadoModif.getEmail());
            
            gestorImagenes.setImagenBlob(empleadoModif.getImagen());
            pantalla.mostrarImagenEmpleado(gestorImagenes);
            //Envio a la pantalla los telefonos del empleado levantado
            Telefono[] tel=empleadoModif.getTelefonos().toArray(new Telefono[0]);
            ArrayList listaNro=new ArrayList<String>();
            ArrayList listaTipoT=new ArrayList<Tupla>();
            for(int i=0; i<tel.length;i++)
            {
               TipoTelefono td= tel[i].getTipo();
               Tupla tup=new Tupla();
               tup.setId(td.getId());
               tup.setNombre(td.getNombre());
               listaTipoT.add(tup);
               listaNro.add(tel[i].getNumero());
            }
            pantalla.telefonosEmpleado( listaNro , listaTipoT);
            //Envio a la pantalla los datos del domicilio del empleado levantado
            if(empleadoModif.getDomicilio().getBarrio()!=null)
            {
            gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            int idLocalidad=ggl.getLocalidadDeBarrio(empleadoModif.getDomicilio().getBarrio().getId()).getId();
            int idProvincia=ggl.getProvinciaDeLocalidad(idLocalidad).getId();
            int idPais=ggl.getPaisDeProvincia(idProvincia).getId();            
            pantalla.datosDomicilioEmpleado(empleadoModif.getDomicilio().getCalle(),String.valueOf(empleadoModif.getDomicilio().getNumero()), empleadoModif.getDomicilio().getDepto(),  String.valueOf(empleadoModif.getDomicilio().getPiso()), empleadoModif.getDomicilio().getCodigoPostal(), idPais, idProvincia, idLocalidad,empleadoModif.getDomicilio().getBarrio().getId());
            }
            else
            {pantalla.datosDomicilioEmpleado(empleadoModif.getDomicilio().getCalle(),String.valueOf(empleadoModif.getDomicilio().getNumero()), empleadoModif.getDomicilio().getDepto(),  String.valueOf(empleadoModif.getDomicilio().getPiso()), empleadoModif.getDomicilio().getCodigoPostal());}
            //Envio a la pantalla las capacitaciones del empleado levantado
            Capacitacion[] cap= (Capacitacion[])empleadoModif.getCapacitaciones().toArray(new Capacitacion[0]);//¿¿?? no se pq en este tengo q castear y en los otros no hace falta...
             //Capacitacion[] cap= empleadoModif.getCapacitaciones().toArray(new Capacitacion[0]);
            ArrayList listaFechaVen=new ArrayList<Date>();
            ArrayList listaTipoC=new ArrayList<Tupla>();
            for(int i=0; i<cap.length;i++)
            {
               TipoCapacitacion tc= cap[i].getTipoCapacitacion();
               Tupla tup=new Tupla();
               tup.setId(tc.getId());
               tup.setNombre(tc.getNombre());
               listaTipoC.add(tup);
               listaFechaVen.add(cap[i].getFechaVencimiento());
            }
            pantalla.capacitacionesEmpleado( listaTipoC,listaFechaVen);
            //Envio a la pantalla las especialidades del empleado levantado
            Especialidad[] esp= empleadoModif.getEspecialidades().toArray(new Especialidad[0]);

            ArrayList listaRango=new ArrayList<Tupla>();
            ArrayList listaTipoE=new ArrayList<Tupla>();
            for(int i=0; i<esp.length;i++)
            {
               TipoEspecialidad te= esp[i].getTipo();
               RangoEspecialidad re=esp[i].getRango();
               Tupla tupE=new Tupla();
               tupE.setId(te.getId());
               tupE.setNombre(te.getNombre());
               listaTipoE.add(tupE);
               Tupla tupR=new Tupla();
               tupR.setId(re.getId());
               tupR.setNombre(re.getNombre());
               listaRango.add(tupR);
            }

            pantalla.especialidadesEmpleado(listaTipoE, listaRango);
            ////
            return true;
           /* } catch (Exception ex)
            {
                System.out.println("Error levantando el empleado");
                return false;
            }*/
        }
        
        
        
        public void datosPersonalesEmpleado(int leg,String cuil, String nmroDoc, Tupla tipoDocumento, String nombre, String apellido, Date fechaNac,Date fechaIng, String email)
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
            fechaIngresoEmpleado=fechaIng;

	}

	public void datosDomicilioEmpleado(String calle, String nro, String depto, String piso, String cp, Tupla tBarrio)
        {
            gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            if(tBarrio.getId()<=0)
            {barrioD=null;}
            else
            {barrioD =ggl.getBarrio(tBarrio.getId());}
            if(nro.equals(""))
            {nmroD=0;}
            else{
            nmroD=Integer.parseInt(nro);}
            if(piso.equals(""))
            {pisoD=0;}
            else{
            pisoD =Integer.parseInt(piso);}
            calleD=calle;
            departamentoD=depto;
            codigoPostalD=cp;

	}
        public void imagenEmpleado(GestorImagenes ge)
        {this.gestorImagenes=ge;}
        
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
	public void tipoEspecialidadYRango(ArrayList<Tupla> lstTipoEspecialidad, ArrayList<Tupla> lstRangoEspecialidad)
        {
            listaEspecialidades= new ArrayList<Especialidad>();
            //listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
            gestorBDvarios bdv = new gestorBDvarios();
            for(int i=0; i<lstTipoEspecialidad.size();i++)
            {
                listaEspecialidades.add(bdv.getEspecialidad(lstTipoEspecialidad.get(i).getId(), lstRangoEspecialidad.get(i).getId()));
                //listaTipoEspecialidades.add(bdv.getTipoEspecialidad(lstTipoEspecialidad.get(i).getId()));
                //listaRangoEspecialidades.add(bdv.getRangoEspecialidad(lstRangoEspecialidad.get(i).getId()));
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
                listaVencimientoCapacitaciones=lstVencimientosCapacitaciones;
            }

	}

	public boolean empleadoModificado()
        {
           // Empleado empleado;
            
            Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
                    
                    try{
                    HibernateUtil.beginTransaction();
                    //sesion.beginTransaction();
                    ////////////////////////////////
                    Iterator ittv=empleadoModif.getTelefonos().iterator();
                    while(ittv.hasNext())
                    {
                        Telefono tel=(Telefono)ittv.next();
                        sesion.delete(tel);
                    }
                    /*Iterator itEspv=empleadoModif.getEspecialidades().iterator();
                    while(itEspv.hasNext())
                    {
                        Especialidad esp=(Especialidad)itEspv.next();
                        sesion.delete(esp);
                    }*/
                   
                   Iterator itCapv=empleadoModif.getCapacitaciones().iterator();
                    while(itCapv.hasNext())
                    {
                        Capacitacion cap=(Capacitacion)itCapv.next();
                        sesion.delete(cap);
                    }
                    if(empleadoModif.getDomicilio()!=null)
                    {sesion.delete(empleadoModif.getDomicilio());}
                    //sesion.delete(empleadoModif);
                    ////////////////////////////
                    ////////////////////////////////
                   try{
                    //empleado=modificarEmpleado();
                       modificarEmpleado();
                   }
                    catch (Exception ex)
                    {
                    System.out.println("No se pudo modificar el empleado");
                    return false;
                    }
                    Iterator itt=empleadoModif.getTelefonos().iterator();
                    while(itt.hasNext())
                    {
                        Telefono tel=(Telefono)itt.next();
                        sesion.save(tel);
                    }
                    /*Iterator itEsp=empleadoModif.getEspecialidades().iterator();
                    while(itEsp.hasNext())
                    {
                        Especialidad esp=(Especialidad)itEsp.next();
                        sesion.save(esp);
                    }*/

                   Iterator itCap=empleadoModif.getCapacitaciones().iterator();
                    while(itCap.hasNext())
                    {
                        Capacitacion cap=(Capacitacion)itCap.next();
                        sesion.save(cap);
                    }
                    if(empleadoModif.getDomicilio()!=null)
                    {
                        sesion.save(empleadoModif.getDomicilio());
                        //sesion.update(empleado.getDomicilio());
                    }
                    sesion.saveOrUpdate(empleadoModif);
                    //sesion.update(empleado);
                    ////////////////////////////

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

	

	public void buscarUltimoLegajoEmpleado() {

	}
        public void vaciarDatos()
        {
        tipoDocumentoEmpleado=null;
	nroDocumento=null;
        nombreEmpleado=null;
        apellidoEmpleado=null;
        emailEmpleado=null;
        cuilEmpleado=null;
        fechaNacimientoEmpleado=null;
        fechaIngresoEmpleado=null;
        legajoEmpleado=0;
        calleD=null;
        nmroD=0;
        pisoD=0;
        departamentoD=null;
        codigoPostalD=null;
        barrioD=null;
        gestorImagenes=new GestorImagenes();//imagen=null;
        }
	public void modificarEmpleado()
        {

            //Date fechaAltaActual=new Date();
           // fecha_Alta=System
           empleadoModif.setLegajo(legajoEmpleado);
           empleadoModif.setNombre(nombreEmpleado);
           empleadoModif.setApellido(apellidoEmpleado);
           empleadoModif.setFechadeNac(fechaNacimientoEmpleado);
           empleadoModif.setFechaIngreso(fechaIngresoEmpleado);
           empleadoModif.setTipoDoc(tipoDocumentoEmpleado);
           empleadoModif.setNroDoc(nroDocumento);
           empleadoModif.setCuil(cuilEmpleado);
           empleadoModif.setEmail(emailEmpleado);
           // dom=new Domicilio( calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD);
           empleadoModif.setDomicilio(calleD, nmroD, pisoD, calleD, codigoPostalD, barrioD);
           //empleadoModif.setEspecialidades(listaTipoEspecialidades);
           empleadoModif.setEspecialidades(listaEspecialidades);
           empleadoModif.setTelefonos(listaNroTel, listaTipoTel);
           empleadoModif.setCapacitaciones(listaTipoCapacitaciones, listaVencimientoCapacitaciones);
           empleadoModif.setImagen(gestorImagenes.getImagenBlob());
          
            //Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado,fechaIngresoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,listaNroTel, listaTipoTel, listaTipoCapacitaciones, listaVencimientoCapacitaciones, empleadoModif.getFechaAlta());
            //Empleado emp=new Empleado(legajoEmpleado,nombreEmpleado, apellidoEmpleado,fechaNacimientoEmpleado, tipoDocumentoEmpleado ,nroDocumento, cuilEmpleado,  emailEmpleado,  calleD,  nmroD,  pisoD,  departamentoD,  codigoPostalD,  barrioD , listaTipoEspecialidades, listaRangoEspecialidades ,HlistaNroTel, HlistaTipoTel, fechaAltaActual);
            
           // return emp;

	}

	public void finCU() {

	}

	

	public void seleccionTipoDocumento() {

	}

	public void documentoEmpleado() {

	}

}
