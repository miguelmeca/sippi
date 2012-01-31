/*            NTupla nt1 = new NTupla(1);
            nt1.setNombre("TestSubTarea1");
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import controlador.GestorAbstracto;
import java.util.ArrayList;
import java.util.List;
import modelo.PedidoObra;
import org.hibernate.Session;
import util.HibernateUtil;
import util.NTupla;
import vista.planificacion.EditarPlanificacion;

/**
 *
 * @author Administrador
 */
public class GestorEditarPlanificacion extends GestorAbstracto {

    private EditarPlanificacion _pantalla;
    private GestorArbolDeRecursos _gestorArbolRecursos;
    private Session sesion;
    private PedidoObra pedidoDeObra;

    public GestorEditarPlanificacion(EditarPlanificacion _pantalla) {
        this._pantalla = _pantalla;
        this._gestorArbolRecursos = new GestorArbolDeRecursos();
    }

    public List getListaSubObras(int idPedidoDeObra) {
          
        ArrayList<NTupla> lista = new ArrayList<NTupla>();
        
        try
        {
            sesion= HibernateUtil.getSession();
            pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class,idPedidoDeObra);
            
//            pedidoDeObra.ge
//            NTupla nt1 = new NTupla(1);
//            nt1.setNombre("TestSubTarea1:"+(Math.random()*100));    
            
        }catch(Exception e)
        {
            System.err.println("No se pudo cargar las subobras del pedido");
        }
        
        //return lista;
        return getListaMock("Sub Obra");

    }
    
    public List getListaTareasXSubObra(int idSubObra) {
        
        return getListaMock("Tarea");
        
    }    
    
    public List getListaMock(String tipo) {
   
        ArrayList<NTupla> lista = new ArrayList<NTupla>();

            NTupla nt1 = new NTupla(1);
            nt1.setNombre(tipo+" 1:"+(Math.random()*100));

            NTupla nt2 = new NTupla(2);
            nt2.setNombre(tipo+" 2:"+(Math.random()*100));
            
            NTupla nt3 = new NTupla(3);
            nt3.setNombre(tipo+" 3:"+(Math.random()*100));
            
            NTupla nt4 = new NTupla(4);
            nt4.setNombre(tipo+" 4:"+(Math.random()*100));            
            
            lista.add(nt1);
            lista.add(nt2);
            lista.add(nt3);
            lista.add(nt4);
            
        return lista;        
        
    }
    
}
