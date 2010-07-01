package controlador.rrhh;

//

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Capacitacion;
import modelo.Capacitador;
import modelo.Domicilio;
import modelo.Empleado;
import modelo.LugardeCapacitacion;
import modelo.TipoCapacitacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
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
	
	public ArrayList<NTupla>  buscarEmpleadosActivosConFechaVencimiento(int idTipoCapacitacion)
        {
            ArrayList<NTupla> lista = new ArrayList<NTupla>();

                try
                {
                    TipoCapacitacion tcx = (TipoCapacitacion) HibernateUtil.getSession().load(TipoCapacitacion.class, idTipoCapacitacion);

                    Iterator it = HibernateUtil.getSession().createQuery("from Empleado").iterate();

                    while (it.hasNext())
                    {
                        NTupla tuplaEmpleado = new NTupla();
                        Empleado e = (Empleado)it.next();

                        tuplaEmpleado.setId(e.getoId());
                        tuplaEmpleado.setNombre(e.getApellido()+", "+e.getNombre());
                        String[] params = new String[2];


                        HashSet listaCaps = new HashSet(e.getCapacitaciones());

                        Iterator itc = listaCaps.iterator();
                        params[0] = "";
                        params[1] = "FALSE";
                        while (itc.hasNext())
                        {
                            Capacitacion cpc = (Capacitacion)itc.next();
                            if(cpc.getTipoCapacitacion().getId() == tcx.getId())
                            {
                                
                                params[0] = FechaUtil.getFecha(cpc.getFechaVencimiento());
                                params[1] = "TRUE";
                            }
                        }

                        tuplaEmpleado.setData(params);
                        lista.add(tuplaEmpleado);
                    }

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            return lista;
	}
	
	public void registrarTallerDeCapacitacionParaLosEmpleadosDeLaEmpresa() {
	
	}
}
