package controlador.rrhh;

//

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.AsistenciaTallerCapacitacion;
import modelo.Capacitacion;
import modelo.Capacitador;
import modelo.DetalleHorarioTaller;
import modelo.Domicilio;
import modelo.Empleado;
import modelo.LugardeCapacitacion;
import modelo.TallerCapacitacion;
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

        private String descripcion;
        private TipoCapacitacion tipoCapacitacion;
        private Capacitador capacitador;
        private LugardeCapacitacion lugar;
        private Set detalleHorarios;
        private Set listaAsistencia;

        public void descripcionTaller(String descripcion) {
            this.descripcion = descripcion;
        }

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
	

        public void tipoCapacitacion(Tupla tipo)
        {
            try
            {
                TipoCapacitacion tc = (TipoCapacitacion)HibernateUtil.getSession().get(TipoCapacitacion.class,tipo.getId());
                this.tipoCapacitacion = tc;
            }
            catch(Exception e)
            {
                System.out.println("ERROR: "+e.getMessage());
            }
        }

        public void capacitador(Tupla tipo)
        {
            try
            {
                Capacitador c = (Capacitador)HibernateUtil.getSession().get(Capacitador.class,tipo.getId());
                this.capacitador = c;
            }
            catch(Exception e)
            {
                System.out.println("ERROR: "+e.getMessage());
            }
        }

        public void lugarCapacitacion(Tupla tipo)
        {
            try
            {
                LugardeCapacitacion lc = (LugardeCapacitacion)HibernateUtil.getSession().get(LugardeCapacitacion.class,tipo.getId());
                this.lugar = lc;
            }
            catch(Exception e)
            {
                System.out.println("ERROR: "+e.getMessage());
            }
        }

        public void horariosYFechas(ArrayList<String[]> lista)
        {
            HashSet listaDetalle = new HashSet();
            Iterator it = lista.iterator();
            while (it.hasNext())
            {
                String[] fila = (String[])it.next();
                DetalleHorarioTaller dht = new DetalleHorarioTaller();
                try {
                dht.setFecha(FechaUtil.getDate(fila[0]));
                } catch (ParseException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
                dht.setHoraInicio(fila[1]);
                dht.setHoraFin(fila[2]);
                listaDetalle.add(dht);
            }
            this.detalleHorarios = listaDetalle;
	}

        public void empleadosQueAsistiran(ArrayList<NTupla> lista)
        {
            listaAsistencia = new HashSet();
            Iterator it = lista.iterator();
            while (it.hasNext())
            {
                NTupla fila = (NTupla)it.next();
                Empleado eqa = null;
                 try
                    {
                        eqa = (Empleado)HibernateUtil.getSession().get(Empleado.class,fila.getId());
                    }
                    catch(Exception e)
                    {
                        System.out.println("ERROR: "+e.getMessage());
                    }

                AsistenciaTallerCapacitacion atc = new AsistenciaTallerCapacitacion();
                atc.setAsistio(Boolean.FALSE);
                atc.setEmpleado(eqa);

                listaAsistencia.add(atc);
            }
        }


        private void asignarAsistenciaADetalle()
        {
            // SON MUCHOS Q SE RELACIONAN CON MUCHOS TODOS IGUALES .... COPIO VARIAS VECES TODO
            Iterator it = detalleHorarios.iterator();
            while (it.hasNext())
            {
                HashSet items = new HashSet(listaAsistencia);
                DetalleHorarioTaller dht = (DetalleHorarioTaller)it.next();

                    // TENGO QUE DUPLICAR
                    HashSet itemsCopia = new HashSet();
                    Iterator<AsistenciaTallerCapacitacion> itx = items.iterator();
                    while (itx.hasNext())
                    {
                        AsistenciaTallerCapacitacion ac = itx.next();
                        AsistenciaTallerCapacitacion atcn = new AsistenciaTallerCapacitacion();

                        atcn.setEmpleado(ac.getEmpleado());
                        atcn.setAsistio(ac.getAsistio());
                        itemsCopia.add(atcn);
                    }
                dht.setAsistencias(itemsCopia);
            }
        }

        public void confirmacionRegistro()
        {
            // GUARDO:
            // 1) Creo el Taller con sus objetos (YA CREADOS)
            TallerCapacitacion tc = new TallerCapacitacion();
            tc.setDescripcion(descripcion);
            tc.setTipoCapacitacion(tipoCapacitacion);
            tc.setCapacitador(capacitador);
            tc.setLugar(lugar);
            tc.setDetalle(detalleHorarios);
            // 2) Creo el detalle con la asistencia
            asignarAsistenciaADetalle();
            // 3) Me la mando a guardar =)
            try
                {
                    HibernateUtil.beginTransaction();

                    // GUARDO TODAS LAS ASISTENCIAS PRIMERO Y DESPUES EL DETALLE
                    for (DetalleHorarioTaller det : tc.getDetalle())
                    {
                        for(AsistenciaTallerCapacitacion as : det.getAsistencias())
                        {
                            HibernateUtil.getSession().save(as);
                        }
                        HibernateUtil.getSession().save(det);
                    }

                    HibernateUtil.getSession().save(tc);

                    HibernateUtil.commitTransaction();

                }
                catch(Exception e)
                {
                    HibernateUtil.rollbackTransaction();
                    System.out.println("EG-0001 : "+e.getMessage());
                    pantalla.MostrarMensaje("EG-0001");
                }


            pantalla.MostrarMensaje("MI-0001");

	}

}
