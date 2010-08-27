package modelo;

//

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : EmpresaCliente.java
//  @ Date : 14/06/2010
//  @ Author : 
//
//




public class EmpresaCliente extends Empresa{
    private List plantas;

    public EmpresaCliente() {
    }

    public List getPlantas() {
        return plantas;
    }

    public void setPlantas(List plantas) {
        this.plantas = plantas;
    }

    // Sería el constructor...
    public void crear() {

    }

    public void mostrarLocalidad() {

    }

    public void mostrarPlanta(String nombre) {

    }

    public void getDatosEmpresaCliente() {

    }

    public void getEmpresa(Planta planta) {

    }

    public void mostrarDatosRestantes() {

    }

    public void getEmpresaCliente(String nombre) {

    }

    public void getRazonSocialPlantas() {

    }

    public void esEstadoAlta() {

    }

    public void mostrarDatos() {

    }

    public void addPlanta(Planta p){
        this.plantas.add(p);
    }

    public boolean delPlanta(Planta p){
        return this.plantas.remove(p);
    }

    public boolean esMiPlanta(int id){
        Iterator it = plantas.iterator();
        Planta p = null;
        while(it.hasNext()){
            p = (Planta) it.next();
            if(p.getId() == id)
                return true;
        }
        return false;
    }
}
