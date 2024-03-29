package modelo;

//

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : TallerCapacitacion.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//




public class TallerCapacitacion {

        private int id;
        private String descripcion;
	private LugardeCapacitacion lugar;
	private Capacitador capacitador;
	private TipoCapacitacion tipoCapacitacion;
	private Set<DetalleHorarioTaller> detalle;
	private EstadoTallerCapacitacion estado;
        private String hib_flag_estado;

        public TallerCapacitacion() {

            this.detalle = new HashSet<DetalleHorarioTaller>();
            this.hib_flag_estado = "modelo.EstadoTallerCapacitacionAlta";

        }

        public EstadoTallerCapacitacion getEstado()
        {
            if(this.id!=0) // Objeto no cargado
            {
                if(this.estado==null)
                {
                    try {
                            //Class estadoAux = Class.forName(this.hib_flag_estado);
                            EstadoTallerCapacitacion estadoAux = (EstadoTallerCapacitacion) Class.forName(this.hib_flag_estado).newInstance();
                            this.estado = estadoAux;
                            return estado;
                        }
                        catch (Exception ex)
                        {
                            System.out.println("No se encontro la clase Estado Concreto");
                        }
                }
                else
                {
                    return this.estado;
                }
                
            }
            else
            {
                System.out.println("Carga el objeto antes de usarlo");
                return null;
            }
            return null;
        }

        public void darDeBaja()
        {
            if(this.id!=0) // Objeto no cargado
            {
                if(this.estado.esAlta())
                {
                    this.estado.darBaja(this);
                }
            }
        }

        public void darDeAlta()
        {
            if(this.id!=0) // Objeto no cargado
            {
                if(this.estado.esBaja())
                {
                    this.estado.darAlta(this);
                }
            }
        }

        public void setEstado(EstadoTallerCapacitacion estado)
        {
            this.estado = estado;
        }

        public Capacitador getCapacitador() {
            return capacitador;
        }

        public void setCapacitador(Capacitador capacitador) {
            this.capacitador = capacitador;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Set<DetalleHorarioTaller> getDetalle() {
            return detalle;
        }

        public void addDetalleHorarioTaller(DetalleHorarioTaller dht)
        {
            this.detalle.add(dht);
        }

        public void setDetalle(Set<DetalleHorarioTaller> detalle) {
            this.detalle = detalle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LugardeCapacitacion getLugar() {
            return lugar;
        }

        public void setLugar(LugardeCapacitacion lugar) {
            this.lugar = lugar;
        }

        public TipoCapacitacion getTipoCapacitacion() {
            return tipoCapacitacion;
        }

        public void setTipoCapacitacion(TipoCapacitacion tipoCapacitacion) {
            this.tipoCapacitacion = tipoCapacitacion;
        }



	
	public void mostrarTallerCapacitacion() {
	
	}
	
	public void mostrarCapacitador() {
	
	}
	
	public void mostrarTipoCapacitacion() {
	
	}
	
	public void getNombre() {
	
	}
	
	public void mostrarNombresPendientes() {
	
	}
	
	public void getTaller() {
	
	}

        /**
         * HECHO A OJO, REVISAR Y REFACTORIZAR
         * @return
         */
	public ArrayList<Empleado> getEmpleados() {

            ArrayList<Empleado> lista = new ArrayList<Empleado>();
            Iterator ir = detalle.iterator();
            if (ir.hasNext())
            {
                DetalleHorarioTaller dht = (DetalleHorarioTaller)ir.next();

                    Iterator it = dht.getAsistencias().iterator();
                    while(it.hasNext())
                    {
                        AsistenciaTallerCapacitacion atc = (AsistenciaTallerCapacitacion)it.next();
                        lista.add(atc.getEmpleado());
                    }
            }
            return lista;
	}
	
	public void buscarApellidoYNombre() {
	
	}
	
	public void crearDetalle() {
	
	}
	
	public boolean tieneConflictoFecha() {
            return true;
	}
	
	public void getDetalleHorarioTaller() {
	
	}

        public String getHib_flag_estado() {
            return hib_flag_estado;
        }

        public void setHib_flag_estado(String hib_flag_estado) {
            this.hib_flag_estado = hib_flag_estado;
        }



}
