package modelo;

import java.util.HashSet;
import java.util.Set;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : Planta.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//


public class Planta {

        private int id;
	private String razonSocial;
	private Set telefonos;
	private Domicilio domicilio;
	private ContactoResponsable contacto;

    public Planta() {

        telefonos = new HashSet();

    }



    public Domicilio getDomicilio() {
        return domicilio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public ContactoResponsable getContacto() {
        return contacto;
    }

    public void setContacto(ContactoResponsable contacto) {
        this.contacto = contacto;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Set getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Set telefono) {
        this.telefonos = telefono;
    }

    public void addTelefono(Telefono t)
    {
        this.telefonos.add(t);
    }

}