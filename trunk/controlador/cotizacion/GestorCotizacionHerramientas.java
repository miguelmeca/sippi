/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import modelo.Cotizacion;
import modelo.HerramientaDeEmpresa;
import modelo.SubObra;
import util.HibernateUtil;
import util.Tupla;
import vista.cotizacion.CotizacionHerramientas;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionHerramientas implements IGestorCotizacion{
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionHerramientas pantalla;
    private ArrayList<HerramientaDeEmpresa> bufferHerramientas;

    public GestorCotizacionHerramientas(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
        bufferHerramientas = new ArrayList<HerramientaDeEmpresa>();
    }

    public void setPantalla(CotizacionHerramientas pantalla) {
        this.pantalla = pantalla;
        llenarComboHerramientas();
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual()
    {
        return this.gestorPadre.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void llenarComboHerramientas() 
    {
        // Vacio el buffer
        bufferHerramientas.clear();
        
        ArrayList<Tupla> listaTipos = new ArrayList<Tupla>();
        try 
        {
            Iterator<HerramientaDeEmpresa> iter = HibernateUtil.getSession().createQuery("from HerramientaDeEmpresa hde order by hde.id").iterate();
            while (iter.hasNext())
            {
                HerramientaDeEmpresa hde = iter.next(); 
                    
                    // Lo agrego al buffer
                    bufferHerramientas.add(hde);
                    
                    String nombreAmostrar = hde.getRecursoEsp().getNombre()+" "+hde.getRecursoEsp().getRecurso().getNombre()+" ("+hde.getNroSerie()+")";
                    
                    // lo agrego a la lista
                    Tupla tp = new Tupla(hde.getId(),nombreAmostrar);
                    listaTipos.add(tp);
            }
            pantalla.llenarComboHerramientas(listaTipos);
        } 
        catch (Exception ex) 
        {
           ex.printStackTrace();
           pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la lista de Tipos de Alquileres / Compras");
        }
    }
    
}
