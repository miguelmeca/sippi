/*            NTupla nt1 = new NTupla(1);
            nt1.setNombre("TestSubTarea1");
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import controlador.GestorAbstracto;
import java.util.ArrayList;
import java.util.List;
import util.NTupla;
import vista.planificacion.EditarPlanificacion;

/**
 *
 * @author Administrador
 */
public class GestorEditarPlanificacion extends GestorAbstracto {

    private EditarPlanificacion _pantalla;
    private GestorArbolDeRecursos _gestorArbolRecursos;

    public GestorEditarPlanificacion(EditarPlanificacion _pantalla) {
        this._pantalla = _pantalla;
        this._gestorArbolRecursos = new GestorArbolDeRecursos();
    }

    public List getListaSubObras() {
        //TODO: Cambiar por la referencia 
        //int idPlanificacion = 1;
        //return _gestorArbolRecursos.getListaSubObras(idPlanificacion);

        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            NTupla nt1 = new NTupla(1);
            nt1.setNombre("TestSubTarea1:"+(Math.random()*100));

            NTupla nt2 = new NTupla(1);
            nt2.setNombre("TestSubTarea2:"+(Math.random()*100));
            
            NTupla nt3 = new NTupla(1);
            nt3.setNombre("TestSubTarea3:"+(Math.random()*100));
            
            lista.add(nt1);
            lista.add(nt2);
            lista.add(nt3);
            
        return lista;
    }
    
    public List getListaTareasXSubObra(int idSubObra) {
        //TODO: Cambiar por la referencia 
        //int idPlanificacion = 1;
        //return _gestorArbolRecursos.getListaSubObras(idPlanificacion);

        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            NTupla nt1 = new NTupla(1);
            nt1.setNombre("Tarea 1:"+(Math.random()*100));

            NTupla nt2 = new NTupla(1);
            nt2.setNombre("Tarea 2:"+(Math.random()*100));
            
            NTupla nt3 = new NTupla(1);
            nt3.setNombre("Tarea 3:"+(Math.random()*100));
            
            lista.add(nt1);
            lista.add(nt2);
            lista.add(nt3);
            
        return lista;
    }    
    
}
