/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import util.NTupla;
import vista.planificacion.EditarTareaDetalles;
import vista.planificacion.EditarTareaDetallesABM;

/**
 *
 * @author Fran
 */
public class GestorEditarTareaDetalles implements IGestorPlanificacion{
    private EditarTareaDetalles pantallaLista;
    private EditarTareaDetallesABM pantallaABM;
    private GestorEditarTarea gestorPadre;

    public GestorEditarTareaDetalles(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
    }

    public void setPantallaLista(EditarTareaDetalles pantallaLista) {
        this.pantallaLista = pantallaLista;
    }
    
    public void setPantallaABM(EditarTareaDetallesABM pantallaABM) {
        this.pantallaABM = pantallaABM;
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
                pantallaABM.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista Tareas de nivel superior");
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
