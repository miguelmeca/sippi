/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.List;
import java.util.ArrayList;
import modelo.Domicilio;
import modelo.Rubro;
import modelo.Barrio;
import modelo.Alojamiento;
import modelo.Tarea;
import modelo.Etapa;
import modelo.TransporteDeMaterialesYHerramientas;
import modelo.TransporteDePasajeros;
import modelo.HerramientaDeEmpresa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import modelo.Proveedor;
import vista.planificacion.pantallaRegistrarEtapa;
import controlador.utiles.gestorGeoLocalicacion;
import controlador.utiles.gestorBDvarios;
import util.Tupla;
import util.NTupla;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.Set;
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : MetAR
//  @ File Name : GestorOtrosDatosEtapa.java
//  @ Date : 11/10/2010
//  @ Author : Fran
//
//




public class GestorOtrosDatosEtapa   {

        private pantallaRegistrarEtapa pantalla;
	private Domicilio domicilioOrigenTransporteMH;
	private Domicilio domicilioDestinoTransporteMH;
        private String calleD;
        private int nmroD;
        private int pisoD;
        private String departamentoD;
        private String codigoPostalD;
        private Barrio barrioD;
        private List listaTareas;
        //private HashSet<String> HlistaNroTel;
        //private HashSet<TipoTelefono> HlistaTipoTel;


    public GestorOtrosDatosEtapa(pantallaRegistrarEtapa pantalla)
    {
        this.pantalla = pantalla;
       
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

        public ArrayList<Tupla> mostrarHerramientasDisponibles() 
        {   Session sesion;
            try{
            sesion= HibernateUtil.getSession();
            //Rubro rubro=(Rubro)sesion.createQuery("from Rubro r where nombre ="+ Alojamiento.class.toString()).uniqueResult();
            List lista = sesion.createQuery("from HerramientaDeEmpresa HE ").list();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++)
            {
                HerramientaDeEmpresa he = (HerramientaDeEmpresa)lista.get(i);
                if(he.getEstado().esDisponible()) //TODO: Analizar bien esto, podria querer asignar una herramienta que todavia no esta disponible pero q se esta arreglando en este momento
                {
                Tupla tupla = new Tupla(he.getId(),he.getNroSerie());
                    tuplas.add(tupla);
                }
            }

            return tuplas;
            }
         catch (Exception ex)////////////
         {System.out.println("No se pudo abrir la sesion en MostrarEmpresa");
         return null;}
        }

        public ArrayList<Tupla> mostrarEmpresasTransporteMH() 
        { Session sesion;
            Rubro rubro=null;
            try{
            sesion= HibernateUtil.getSession();
            rubro=(Rubro)sesion.createQuery("from Rubro r where nombreClase = 'TransporteDeMaterialesYHerramientas'").uniqueResult();
            }
            catch (Exception ex)
            {System.out.println("No se pudo abrir la sesion en MostrarEmpresaTransporteDeMaterialesYHerramientas");
            return null;}
            return mostrarEmpresas(rubro);
        }

        public ArrayList<Tupla> mostrarEmpresasTransportePasajeros()
        {    Session sesion;
            Rubro rubro=null;
            try{
            sesion= HibernateUtil.getSession();
            rubro=(Rubro)sesion.createQuery("from Rubro r where nombreClase ='TransporteDePasajeros'").uniqueResult();
            }
            catch (Exception ex)////////////
            {System.out.println("No se pudo abrir la sesion en MostrarEmpresaTransporteDePasajeros");
            return null;}
            return mostrarEmpresas(rubro);
        }
        public ArrayList<Tupla> mostrarEmpresasAlojamiento()
        {
            Session sesion;
            Rubro rubro=null;
            try{
            sesion= HibernateUtil.getSession();
            rubro=(Rubro)sesion.createQuery("from Rubro r where nombreClase = 'Alojamiento' ").uniqueResult();
            }
            catch (Exception ex)////////////
            {System.out.println("No se pudo abrir la sesion en MostrarEmpresaAlojamiento");
            return null;}
            return mostrarEmpresas(rubro);

        }

        public ArrayList<Tupla> mostrarEmpresas(Rubro rubro)
        {   Session sesion;
            if(rubro==null)
            {return null;}
            try{
            sesion= HibernateUtil.getSession();
            //Rubro rubro=(Rubro)sesion.createQuery("from Rubro r where nombre ="+ Alojamiento.class.toString()).uniqueResult();
            List lista = sesion.createQuery("from Proveedor pr order by pr.razonSocial").list();
            ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
            for (int i = 0; i < lista.size(); i++)
            {
                Proveedor emp = (Proveedor)lista.get(i);
                if(emp.tieneRubro(rubro))
                {
                Tupla tupla = new Tupla(emp.getId(),emp.getRazonSocial());
                    tuplas.add(tupla);
                }
            }

            return tuplas;
            }
         catch (Exception ex)////////////
         {System.out.println("No se pudo abrir la sesion en MostrarEmpresa");
         return null;}
        }
        public List listaTareas( int idEtapa)
        {
            Session sesion;
            ArrayList<NTupla> listaNTuplaTareas;
            try{

            sesion= HibernateUtil.getSession();

             Etapa etapa= (Etapa)sesion.createQuery("from Etapa e where e.id="+idEtapa+" order by e.orden").uniqueResult();

             listaTareas=etapa.getTareas();
            listaNTuplaTareas = new ArrayList<NTupla>();
            for (int i = 0; i < listaTareas.size(); i++)
                {
                    Tarea tar = (Tarea)listaTareas.get(i);
                    NTupla tupla = new NTupla(tar.getId());
                    tupla.setNombre(String.valueOf(tar.getDescripcion()));
                    String[] datos=new String[1];
                    datos[0]=String.valueOf(tar.CalcularMontoTotal());
                    tupla.setData(datos);
                    listaNTuplaTareas.add(tupla);
                }

            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
                return null;
            }

            return listaNTuplaTareas;
        }

	

	public void finCU() {

	}
	

}
