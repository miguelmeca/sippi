package modelo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : Indumentaria.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//

public class Indumentaria {
	
        private int id;
        private String descripcion;
	private Talle talle;
	private TipoIndumentaria tipo;

    public Indumentaria() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Talle getTalle() {
        return talle;
    }

    public void setTalle(Talle talle) {
        this.talle = talle;
    }

    public TipoIndumentaria getTipo() {
        return tipo;
    }

    public void setTipo(TipoIndumentaria tipo) {
        this.tipo = tipo;
    }

    

	public void crear() {
	
	}
	
	public void mostrarIndumentaria() {
	
	}
	
	public void mostrarTipoIndumentaria() {
	
	}
}
