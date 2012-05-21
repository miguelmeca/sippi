/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import org.hibernate.Session;
import util.HibernateUtil;
import util.NTupla;
import vista.planificacion.EditarTareaEsfuerzo;

/**
 *
 * @author Fran
 */
public class GestorEditarTareaEsfuerzo implements IGestorPlanificacion{
    private EditarTareaEsfuerzo pantalla;
    private GestorEditarTarea gestorPadre;

    public GestorEditarTareaEsfuerzo(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(EditarTareaEsfuerzo pantalla) {
        this.pantalla = pantalla;
    }
        
    
    public ArrayList<NTupla> mostrarTareasSuperiores()
    { 
        //List<TareaPlanificacion> tareas=new ArrayList<TareaPlanificacion>();
        List<TareaPlanificacion> tareasSuperiores=new ArrayList<TareaPlanificacion>();
        try{            
            tareasSuperiores= gestorPadre.getTareaActual().buscarCaminoHastaTareaConCotizacion(gestorPadre.getPlanificacion(),false,true);
            
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            if(tareasSuperiores.isEmpty())
            {
                NTupla tuplaV = new NTupla(-1);
                tuplaV.setNombre("No hay tareas de nivel superior");
                tuplas.add(tuplaV);
                return tuplas;
            }            
            
            for (int i = 0; i < tareasSuperiores.size(); i++)
            {
                TareaPlanificacion tt = (TareaPlanificacion)tareasSuperiores.get(i);
                
                NTupla tuplaT = new NTupla(tt.hashCode());
                tuplaT.setNombre(tt.getNombre());
                tuplaT.setData(tt);
                tuplas.add(tuplaT);
             }
            return tuplas;    
           }
           catch (Exception ex){
                System.out.println("No se ejecutar la consulta para cargar el combo de tareas de nivel superior :"+ex);
                pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista Tareas de nivel superior");
                return null;
           }       
        }

    public PlanificacionXXX getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    public TareaPlanificacion getTareaActual() {
        return this.gestorPadre.getTareaActual();
    }

    public void refrescarPantallas() {
        this.gestorPadre.refrescarPantallas();
    }


    


    
}
