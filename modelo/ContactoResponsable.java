package modelo;

//

import java.util.List;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : ContactoResponsable.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//

public class ContactoResponsable {
	private int id;
        private String cargo;
	private String nombre;
	private String apellido;
	private String email;
	private String cuil;
	private List telefonos;

    public ContactoResponsable() {
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List telefonos) {
        this.telefonos = telefonos;
    }

    public void crear() {

    }

    public void mostrarContactoEmpresa() {

    }

    public void getContacto() {

    }


}
