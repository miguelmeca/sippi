package modelo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : LugardeCapacitacion.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//




public class LugardeCapacitacion {

        private int id;
	private String nombre;
	private Domicilio domicilio;
	private EstadoLugarCapacitacion estado;
        private String hib_flag_estado;

        public LugardeCapacitacion() {

            this.hib_flag_estado = "modelo.EstadoLugarCapacitacionAlta";

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Domicilio getDomicilio() {
            return domicilio;
        }

        public void setDomicilio(Domicilio domicilio) {
            this.domicilio = domicilio;
        }

        public EstadoLugarCapacitacion getEstado()
        {

            if(this.id!=0) // Objeto no cargado
            {
                if(this.estado==null)
                {
                    try {
                            //Class estadoAux = Class.forName(this.hib_flag_estado);
                            EstadoLugarCapacitacion estadoAux = (EstadoLugarCapacitacion) Class.forName(this.hib_flag_estado).newInstance();
                            this.estado = estadoAux;
                            return estado;
                        }
                        catch (Exception ex)
                        {
                            System.out.println("No se encontro la clase Estado Concreto");
                            ex.getStackTrace();
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


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setEstado(EstadoLugarCapacitacion estado) {
            this.estado = estado;
            this.hib_flag_estado = estado.getClass().getName();
        }



	public void mostrarLugaresDisponibles() {
	
	}
	
	public void buscarDomicilio() {
	
	}

    public String getHib_flag_estado() {
        return hib_flag_estado;
    }

    public void setHib_flag_estado(String hib_flag_estado) {
        this.hib_flag_estado = hib_flag_estado;
    }

        

}
