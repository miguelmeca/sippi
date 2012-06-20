package controlador.rrhh;

//


//import java.util.List;

import modelo.Empleado;
import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import util.HibernateUtil;
import vista.rrhh.pantallaRegistrarAltaEmpleado;

//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : GestorModificarNuevoEmpleado.java
//  @ Date : 10/08/2010
//  @ Author : Fran
//
//




public class GestorRegistrarAltaEmpleado    {

        private pantallaRegistrarAltaEmpleado pantalla;

        private Empleado empleadoModif;



    public GestorRegistrarAltaEmpleado(pantallaRegistrarAltaEmpleado pantalla)
    {
        this.pantalla = pantalla;
        /*listaTipoEspecialidades= new ArrayList<TipoEspecialidad>();
        listaRangoEspecialidades= new ArrayList<RangoEspecialidad>();
        listaNroTel= new  ArrayList<String>();
        listaTipoTel= new ArrayList<TipoTelefono>();
        listaTipoCapacitaciones=new ArrayList<TipoCapacitacion>();
         listaVencimientoCapacitaciones=new ArrayList<Date>();*/

    }



        public boolean levantarEmpleado(int id)//public boolean levantarEmpleado(int leg)
        {
            Session sesion;
            ///////////////////////////////////
             try {
                    sesion = HibernateUtil.getSession();
            //sesion.beginTransaction();
            empleadoModif = (Empleado) sesion.createQuery("from Empleado where id ="+id).uniqueResult();


            //Envio a la pantalla los datos personales del empleado levantado

            pantalla.datosPersonalesEmpleado(String.valueOf(empleadoModif.getLegajo()), empleadoModif.getCuil(), empleadoModif.getNroDoc(), empleadoModif.getTipoDoc().getNombre(),empleadoModif.getNombre(), empleadoModif.getApellido(), empleadoModif.getFechadeNac(),empleadoModif.getFechaIngreso(), empleadoModif.getFechaAlta(), empleadoModif.getEmail(), empleadoModif.getMotivoBaja(), empleadoModif.getFechaBaja());
            //Envio a la pantalla los telefonos del empleado levantado

            ////
            return true;
            } catch (Exception ex)
            {
                System.out.println("Error levantando el empleado");
                return false;
            }
        }

        public boolean altaEmpleado()
        {
            Session sesion;
           try {
                    sesion = HibernateUtil.getSession();
                    HibernateUtil.beginTransaction();
            

            empleadoModif.darDeAlta();
            empleadoModif.setMotivoBaja("");

            sesion.update(empleadoModif);

            HibernateUtil.commitTransaction();

            ////
            return true;
            } catch (Exception ex)
            {
                System.out.println("Error registrando alta del empleado");
                HibernateUtil.rollbackTransaction();
                return false;
            }
        }



}
