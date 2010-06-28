package modelo;

//

import java.util.Date;
import java.util.ArrayList;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : Persona.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//

public class Persona {
	
        private int oid;
        private String nombre;
	private String apellido;
	private Date fechadeNac;
	private TipoDocumento tipoDoc;
	private String nroDoc;
	private ArrayList<Telefono> telefonos;
	private String email;
	private String cuil;
	private Domicilio domicilio;
	//private Object estado; COMO ES ESTO??? PERSONA TIENE ESTADO??? O EMPLEADO Y CLIENTE??? ESTA MAL MODELADO EN NUESTRO MODELOADO!!!
	public void mostrarPersona() {
	
	}
	
	public void tomarTipoDocumento() {
	
	}
	
	public void tomarDomicilo() {
	
	}

    public Persona() {

    }

    public int getOid() {
        return oid;
    }

    public int getoId() {
        return oid;
    }

    public int getoid() {
        return oid;
    }

    public int getOID() {
        return oid;
    }

    public void setOid(int id) {
        this.oid = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
    public void setDomicilio(String calleD, int numeroD, int pisoD, String deptoD, String codigoPostalD, Barrio barrioD) {
        domicilio=new Domicilio(calleD,  numeroD, pisoD,  deptoD,  codigoPostalD, barrioD);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechadeNac() {
        return fechadeNac;
    }

    public void setFechadeNac(Date fechadeNac) {
        this.fechadeNac = fechadeNac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<Telefono> telefono) {
        this.telefonos = telefono;
    }

    public void setTelefonos(ArrayList<String> nroTelefono, ArrayList<TipoTelefono> tipoTelefono)
    {
       for(int i=0; i<nroTelefono.size();i++)
       {
           Telefono tel=new Telefono(nroTelefono.get(i) , tipoTelefono.get(i));
           this.telefonos.add(tel);
       }

    }

    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

        
}
