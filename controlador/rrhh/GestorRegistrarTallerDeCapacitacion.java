package controlador.rrhh;

//

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Capacitador;
import modelo.Domicilio;
import modelo.LugardeCapacitacion;
import modelo.TipoCapacitacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;
import vista.rrhh.pantallaRegistrarTallerCapacitacion;

//
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : GestorRegistrarTallerDeCapacitacion.java
//  @ Date : 24/06/2010
//  @ Author : Iuga
//
//




public class GestorRegistrarTallerDeCapacitacion {


	public ArrayList<String> idNombreEmpleados;
	public ArrayList<String> fechasVencimientos;
        private pantallaRegistrarTallerCapacitacion pantalla;

        public GestorRegistrarTallerDeCapacitacion(pantallaRegistrarTallerCapacitacion pantalla) {
            this.pantalla = pantalla;
        }

	public void registrarTallerDeCapacitacionParaUnaObra() {
	
	}
	
	public void ingresoNombreTaller() {
	
	}
	
	public void ingresoDescripcionTaller() {
	
	}
	
	public void buscarObrasActivas() {
	
	}
	
	public void seleccionPedidoObra() {
	
	}
	
	public void buscarObraSeleccionada() {
	
	}
	
	public void buscarTipoDeCapacitacionDeObra() {
	
	}
	
	public void seleccionTipoCapacitacion() {
	
	}
	
	public void ingresoDeHorariosYFechas() {
	
	}
	
	public  ArrayList<Tupla> buscarLugarDeTaller() {

           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from LugardeCapacitacion tc order by tc.id DESC").iterate();
                while (iter.hasNext())
                {
                   LugardeCapacitacion lc = (LugardeCapacitacion)iter.next();
                   Tupla t = new Tupla();
                   t.setId(lc.getId());
                   t.setNombre(lc.getNombre());
                   lista.add(t);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
           }

           return lista;
	}
	
	public void seleccionLugarTaller() {
	
	}
	
	public String buscarDomicilioDelLugarSeleccionado(int idLugar)
        {
            String dire = "x";

                  SessionFactory sf = HibernateUtil.getSessionFactory();
                   Session sesion;
                   try
                   {
                        sesion = HibernateUtil.getSession();

                        LugardeCapacitacion lugar = (LugardeCapacitacion) sesion.load(LugardeCapacitacion.class,idLugar);
                        dire = lugar.getDomicilio().toString();

                   }catch(Exception ex)
                   {
                        System.out.println("No se pudo abrir la sesion: "+ex.getMessage());
                   }

             return dire;

	}

	public ArrayList<Tupla> buscarCapacitadoresParaTipoCapacitacion(int idTipoCapacitacion)
        {
            // ALTO BARDO, BUSCO EN LOS CAPACITADORES cuales tienen ese tipo de capacitacion
            // Guardo los capacitadores listos
            ArrayList<Capacitador> listaCap = new ArrayList<Capacitador>();

                // LOGICA LOGICA LOGICA
                try
                {
                    TipoCapacitacion tc = (TipoCapacitacion) HibernateUtil.getSession().load(TipoCapacitacion.class, idTipoCapacitacion);


                    Iterator it = HibernateUtil.getSession().createQuery("from Capacitador").iterate();

                        while (it.hasNext())
                        {
                            Capacitador cp = (Capacitador)it.next();
                            // Ahora verifico si tiene ese tipo de capacitacion
                            if(cp.tieneTipoCapacitacion(tc))
                            {
                                listaCap.add(cp);
                            }
                        }

                }
                catch (Exception ex) {

                }



            // Ya tengo los capacitadores, los paso a tuplas y los devuelvo
            ArrayList<Tupla> listaCapTuplas = new ArrayList<Tupla>();
            Iterator it = listaCap.iterator();
            while (it.hasNext())
            {
                Capacitador cp = (Capacitador)it.next();
                Tupla tp = new Tupla(cp.getoId(),cp.getApellido()+", "+cp.getNombre());
                listaCapTuplas.add(tp);
            }
            return listaCapTuplas;
	
	}
	
	public void seleccionCapacitador() {
	
	}
	
	public void buscarEmpleadosDeObraSeleccionadaDeEseTipoYMaxFechaVencimiento() {
	
	}
	
	public void seleccionEmpleados() {
	
	}
	
	public void confirmacionRegistro() {
	
	}
	
	public void validarDatos() {
	
	}
	
	public void registrarTallerDeCapacitacionParaObra() {
	
	}
	
	public void finCU() {
	
	}
	
	public void registrarTallerCapacitacionParaLosEmpleadosDeLaEmpresa() {
	
	}
	
	public ArrayList<Tupla> buscarTiposDeCapacitacion() {

           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoCapacitacion tc order by tc.id DESC").iterate();
                while (iter.hasNext())
                {
                   TipoCapacitacion tc = (TipoCapacitacion)iter.next();
                   Tupla t = new Tupla();
                   t.setId(tc.getId());
                   t.setNombre(tc.getNombre());
                   lista.add(t);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion");
           }

           return lista;

	}
	
	public void buscarNombresEmpleadosActivos() {
	
	}
	
	public void buscarEmpleadosActivosConFechaVencimiento() {
	
	}
	
	public void registrarTallerDeCapacitacionParaLosEmpleadosDeLaEmpresa() {
	
	}
}
