package controlador.comer;

import controlador.utiles.gestorBDvarios;
import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import modelo.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.comer.pantallaRegistrarNuevaPlanta;

/**
 * @version 1.0
 * @author Iuga
 */

public class GestorRegistrarNuevaPlanta {

        private pantallaRegistrarNuevaPlanta pantalla;
	private ArrayList<Empresa> listaEmpresas;
	private String nombrePlanta;
	private String calle;
	private int altura;
	private int piso;
	private String depto;
        private String codigoPostal;
	private ArrayList<Pais> listaPais;
	private ArrayList<Provincia> listaProvincias;
	private ArrayList<Localidad> listaLocalidad;
	private ArrayList<Barrio> listaBarrio;
        private HashSet<Telefono> listaTelefonos;
	private Pais pais;
	private Provincia provincia;
	private Localidad localidad;
	private Barrio barrio;

        private EmpresaCliente empresa = null;

        public GestorRegistrarNuevaPlanta(pantallaRegistrarNuevaPlanta pantalla) {
            this.pantalla = pantalla;
            listaPais = new ArrayList<Pais>();
            listaBarrio = new ArrayList<Barrio>();
            listaLocalidad = new ArrayList<Localidad>();
            listaProvincias = new ArrayList<Provincia>();
        }
 
	public ArrayList<Tupla> mostrarContactos() {

            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();

            sesion.beginTransaction();
            Iterator iter = sesion.createQuery("from ContactoResponsable ec order by ec.id DESC").iterate();
            while ( iter.hasNext() )
            {
                ContactoResponsable cr = (ContactoResponsable)iter.next();
                Tupla tupla = new Tupla(cr.getId(),cr.getApellido()+", "+cr.getNombre());
                tuplas.add(tupla);
            }
            return tuplas;

	}

	public ArrayList<Tupla> mostrarEmpresasCliente() {

            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();

            sesion.beginTransaction();
            Iterator iter = sesion.createQuery("from EmpresaCliente ec order by ec.razonSocial").iterate();
            while ( iter.hasNext() )
            {
                EmpresaCliente ec = (EmpresaCliente)iter.next();
                Tupla tupla = new Tupla(ec.getId(),ec.getRazonSocial());
                tuplas.add(tupla);
            }
            return tuplas;

	}

        public ArrayList<Tupla> mostrarTiposTelefono()
        {
            gestorBDvarios gaux = new gestorBDvarios();
            return gaux.getTiposDeTelefono();
        }

	public void empresaCliente(Tupla ec)
        {
                    SessionFactory sf = HibernateUtil.getSessionFactory();
                    Session sesion;
                    try {
                    sesion = HibernateUtil.getSession();
                    try{
                    HibernateUtil.beginTransaction();

                        this.empresa = (EmpresaCliente) sesion.load(EmpresaCliente.class,ec.getId());

                        HibernateUtil.commitTransaction();
                    }catch(Exception e) {
                        System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                }
            } catch (Exception ex) { System.out.println("No se pudo abrir la sesion");  }
	
	}
	
	public void nombrePlanta(String nombre)
        {
            this.nombrePlanta = nombre;
	}
	
	public void telefonoPlanta(HashSet<NTupla> listaTel) {

            HashSet<Telefono> lista = new HashSet();
            // Convierto el NTupla en Telefono
            Iterator it = listaTel.iterator();
            while (it.hasNext())
            {
                NTupla nt = (NTupla)it.next();
                Telefono tel = new Telefono();
                tel.setNumero(nt.getNombre());

                TipoTelefono ttel = new TipoTelefono();
                Tupla aux = (Tupla)nt.getData();
                ttel.setId(aux.getId());
                ttel.setNombre(aux.getNombre());
                tel.setTipo(ttel);
                lista.add(tel);

            }

            this.listaTelefonos = lista;
	}
	
	public void DomicilioPlanta(String calle, int altura, int piso, String depto, String codigoPostal)
        {
            this.calle = calle;
            this.altura = altura;
            this.piso = piso;
            this.depto = depto;
            this.codigoPostal = codigoPostal;
	}
	
	public ArrayList<Tupla> mostrarPaises() {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getPaises();

	}
	
	public ArrayList<Tupla> mostrarProvincias(int idPais)
        {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();

            // GUARDO EL PAIS PARA CUANDO CREE
            this.pais = ggl.getPais(idPais);

           return ggl.getProvincias(idPais);
	}

        public ArrayList<Tupla> mostrarLocalidades(int idProvincia) {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();

            // GUARDO LA PROVINCIA PARA CUANDO CREE
            this.provincia = ggl.getProvincia(idProvincia);

           return ggl.getLocalidades(idProvincia);

	}

	public ArrayList<Tupla> mostrarBarrios(int idLocalidad) {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getBarrios(idLocalidad);

	}

        /*
         *  SETERS DE LA GEOPOSICION ---------------------------------------
         */

	public void paisPlanta(int idPais) {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            this.pais = ggl.getPais(idPais);
	}

	public void provinciaPlanta(int idProvincia) {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            this.provincia = ggl.getProvincia(idProvincia);
	}
	
	public void LocalidadPlanta(int idLocalidad) {
            gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            this.localidad = ggl.getLocalidad(idLocalidad);
	}
	
	public void barrioPlanta(int idBarrio) {
            gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
            this.barrio = (Barrio)ggl.getBarrio(idBarrio);
	}
	
        public int PlantaConfirmada()
        {
            // GUARDO LA PLANTA, SIN LA EMPRESA
            Planta p = new Planta();
            p.setRazonSocial(this.nombrePlanta);
            p.setTelefonos(this.listaTelefonos);

            Domicilio d = new Domicilio();
            d.setBarrio((Barrio)barrio);
            d.setCalle(calle);
            d.setCodigoPostal(codigoPostal);
            d.setDepto(depto);
            d.setNumero(altura);
            d.setPiso(piso);
              p.setDomicilio(d);
            
                    SessionFactory sf = HibernateUtil.getSessionFactory();
                    Session sesion;
                    try {
                    sesion = HibernateUtil.getSession();
                    try{
                    HibernateUtil.beginTransaction();

                        if(this.empresa == null)
                        {
                            for (Telefono tell : listaTelefonos) 
                            {
                                sesion.save(tell);
                            }
                            sesion.save(d);
                            sesion.save(p);
                        }
                        else
                        {
                            empresa.addPlanta(p);
                            //sesion.flush();
                            //sesion.saveOrUpdate(empresa);
                        }

                        HibernateUtil.commitTransaction();
                    }catch(Exception e) { 
                        System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
            }
            return p.getId();

        }
	
	public void CrearPlanta() {
	
	}
	
	public void noConfirmacionRegistrarContacto() {
	
	}
}
