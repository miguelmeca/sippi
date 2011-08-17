/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.cotizacion;

import controlador.Compras.*;
import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;
import modelo.TipoTarea;
import modelo.RangoEmpleado;
import util.RubroUtil;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import util.NTupla;
import util.FechaUtil;
import vista.cotizacion.CotizacionManoDeObraGeneral;
import vista.cotizacion.CotizacionManoDeObraAgregarMO;
import org.hibernate.Session;
import util.HibernateUtil;
//import java.util.logging.Level;
//import java.util.logging.Logger;


/**
 *
 * @author Emmanuel
 */
public class GestorCotizacionManoDeObra
{
  private CotizacionManoDeObraGeneral pantallaGeneral;
  private CotizacionManoDeObraAgregarMO pantallaAgregarMO;
  //ArrayList<PrecioSegunCantidad> listaPrecios;

public GestorCotizacionManoDeObra( CotizacionManoDeObraGeneral pantalla)
    {
        this.pantallaGeneral = pantalla;
       // listaPrecios= new ArrayList<PrecioSegunCantidad>();

    }


 /*public ArrayList<Tupla> mostrarRubrosCompras()
 {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();
        lista = RubroUtil.getTuplasRubros();
        mostroPrecios=false;
        return lista;

 }/////////////////////////////*/
 public ArrayList<NTupla> mostrarNombresTareas()
        { Session sesion;

            List<TipoTarea> tareas=null;
            try{
                sesion= HibernateUtil.getSession();
            tareas=sesion.createQuery("from TipoTarea").list();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarNombresTareas");
            return null;}
            if(tareas==null)
            {return null;}            
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < tareas.size(); i++)
            {
                TipoTarea tt = (TipoTarea)tareas.get(i);
                
                NTupla tuplaT = new NTupla(tt.getId());
                tuplaT.setNombre(tt.getNombre());
                Object[] datos=new Object[3];
                datos[0]=Double.toString(tareas.get(i).getCantHorasPredeterminada());                
                datos[1]=Integer.toString(tareas.get(i).getCantOperariosPredeterminada());
                
                    RangoEmpleado rangoEmp=tareas.get(i).getRangoEmpleadoPredeterminado();
                    /*=NTupla tuplaR = new NTupla(rangoEmp.getId());
                    tuplaR.setNombre(rangoEmp.getNombre());
                    tuplaR.setData(rangoEmp.getCostoXHora());                    
                datos[2]=tuplaR;*/
                datos[2]=rangoEmp.getId();
                tuplaT.setData(datos);
                tuplas.add(tuplaT);
             }
            return tuplas;           
        }
 public ArrayList<NTupla> mostrarRangos()
        { Session sesion;

            List<RangoEmpleado> rangos=null;
            try{
                sesion= HibernateUtil.getSession();
                rangos=sesion.createQuery("from RangoEmpleado").list();
            }
            catch (Exception ex)
            {System.out.println("No se ejecutar la consulta en mostrarRangos()");
            return null;}
            if(rangos==null)
            {return null;}           
            ArrayList<NTupla> tuplas = new ArrayList<NTupla>();
            for (int i = 0; i < rangos.size(); i++)
            {
                RangoEmpleado re = (RangoEmpleado)rangos.get(i);
                
                NTupla nTupla = new NTupla(re.getId());
                nTupla.setNombre(re.getNombre());
                nTupla.setData(re.getCostoXHora());
                    tuplas.add(nTupla);
             }
            return tuplas;           
        }
////////////////////////////

    /**
     * @return the pantallaGeneral
     */
    public CotizacionManoDeObraGeneral getPantallaGeneral() {
        return pantallaGeneral;
    }

    /**
     * @param pantallaGeneral the pantallaGeneral to set
     */
    public void setPantallaGeneral(CotizacionManoDeObraGeneral pantallaGeneral) {
        this.pantallaGeneral = pantallaGeneral;
    }

    /**
     * @return the pantallaAgregarMO
     */
    public CotizacionManoDeObraAgregarMO getPantallaAgregarMO() {
        return pantallaAgregarMO;
    }

    /**
     * @param pantallaAgregarMO the pantallaAgregarMO to set
     */
    public void setPantallaAgregarMO(CotizacionManoDeObraAgregarMO pantallaAgregarMO) {
        this.pantallaAgregarMO = pantallaAgregarMO;
    }
 
}