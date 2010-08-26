/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.rrhh;

import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.List;
import modelo.Barrio;
import modelo.Domicilio;
import modelo.EstadoLugarCapacitacionAlta;
import modelo.Localidad;
import modelo.LugardeCapacitacion;
import modelo.Pais;
import modelo.Provincia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;
import vista.rrhh.pantallaModificarLugarCapacitacion;
import vista.rrhh.pantallaRegistrarLugarCapacitacion;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorModificarLugarCapacitacion {

    private pantallaModificarLugarCapacitacion pantalla;
    private Pais pais;
    private Provincia provincia;
    private String nombre;
    private String calle;
    private int altura;
    private int piso;
    private String depto;
    private String codigoPostal;
    private Barrio barrio;

    public GestorModificarLugarCapacitacion(pantallaModificarLugarCapacitacion pantalla) {
        this.pantalla = pantalla;
    }

    	public ArrayList<Tupla> mostrarPaises() {

           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getPaises();

	}

        public boolean existeLugarCapacitacion(String nombreLugar)
        {
            Session sesion;
            try 
            {
                    sesion = HibernateUtil.getSession();
                    //List lista = sesion.createQuery("from LugardeCapacitacion lc WHERE lc.nombre = :nom").setParameter("nom","'"+nombreLugar+"'").list();
                    List lista = sesion.createQuery("from LugardeCapacitacion lc WHERE lc.nombre LIKE :nam").setParameter("nam",nombreLugar).list();
                    if(lista.size()>0)
                    {
                        return true;
                    }
                    else   
                    {
                        return false;
                    }
                    
            }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("No se pudo conectar con la BD");
                pantalla.MostrarMensaje("EG-0014");
                return false;
            }
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

        public void nombreLugarCapacitacion(String nombre)
        {
            this.nombre = nombre;
        }

        public void DomicilioPlanta(String calle, int altura, int piso, String depto, String codigoPostal)
        {
            this.calle = calle;
            this.altura = altura;
            this.piso = piso;
            this.depto = depto;
            this.codigoPostal = codigoPostal;
	}

        public void barrioPlanta(int idBarrio) {

                gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
                this.barrio = (Barrio)ggl.getBarrio(idBarrio);

        }

    public boolean confirmarLugarCapacitacion(int id) {

        Session sesion;
        try {
            sesion = HibernateUtil.getSession();



            LugardeCapacitacion lugar = (LugardeCapacitacion)sesion.load(LugardeCapacitacion.class,id);
            lugar.setNombre(nombre);

                Domicilio d = lugar.getDomicilio();
                d.setBarrio((Barrio)barrio);
                d.setCalle(calle);
                d.setCodigoPostal(codigoPostal);
                d.setDepto(depto);
                d.setNumero(altura);
                d.setPiso(piso);

                lugar.setEstado(new EstadoLugarCapacitacionAlta());
            
                    HibernateUtil.beginTransaction();

                     
                            sesion.update(d);
                            sesion.update(lugar);


                        HibernateUtil.commitTransaction();

                    }catch(Exception e)
                    {
                        System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                        return false;
                    }
            return true;

    }

    public void mostrarLugarCapacitacion(int idLugar)
    {

            Session sesion;
            try
            {
                    sesion = HibernateUtil.getSession();
                    //List lista = sesion.createQuery("from LugardeCapacitacion lc WHERE lc.nombre = :nom").setParameter("nom","'"+nombreLugar+"'").list();
                    LugardeCapacitacion lc = (LugardeCapacitacion)sesion.load(LugardeCapacitacion.class,idLugar);

                    pantalla.mostrarNombre(lc.getNombre());
                    pantalla.mostrarDomicilio(lc.getDomicilio().getCalle(),lc.getDomicilio().getNumero(),lc.getDomicilio().getPiso(),lc.getDomicilio().getDepto(),lc.getDomicilio().getCodigoPostal());

                    Tupla tBarrio = new Tupla(lc.getDomicilio().getBarrio().getId(),lc.getDomicilio().getBarrio().getNombre());

                    gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
                    Localidad l = ggl.getLocalidadDeBarrio(tBarrio.getId());
                    Tupla tLocalidad = new Tupla(l.getId(),l.getNombre());

                    Provincia p = ggl.getProvinciaDeLocalidad(l.getId());
                    Tupla tProvincia = new Tupla(p.getId(),p.getNombre());

                    Pais pa = ggl.getPaisDeProvincia(p.getId());
                    Tupla tPais = new Tupla(pa.getId(),pa.getNombre());


                    pantalla.mostrarGeo(tPais, tProvincia, tLocalidad, tBarrio);

            }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("No se pudo conectar con la BD");
                pantalla.MostrarMensaje("EG-0014");
            }

    }

}
