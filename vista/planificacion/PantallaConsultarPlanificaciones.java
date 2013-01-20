/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.PedidoObra;
import modelo.Planificacion;
import modelo.TipoTarea;
import util.HibernateUtil;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;
import vista.gui.IFavorito;

/**
 *
 * @author Administrador
 */
public class PantallaConsultarPlanificaciones extends PantallaConsultarGenerica implements IFavorito{

    public PantallaConsultarPlanificaciones(Class entidad) {
        super(entidad);
    }

    public PantallaConsultarPlanificaciones() {
        super(Planificacion.class);
    }
    
    public PantallaConsultarPlanificaciones(String filtro) {
        super(Planificacion.class,filtro);
    }        
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"mostrarPedidoObra","Obra"});
            columnas.add(new String[]{"getNroCotizacionPlanificada","Cotización"});
            columnas.add(new String[]{"mostrarFechaInicio","Fecha de Inicio"});
            columnas.add(new String[]{"mostrarFechaFin","Fecha de Fin"});
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }

    @Override
    protected String getNombreVentana() {
        return "Listado: Planificaciones de Obra";
    }

    @Override
    protected ArrayList<String[]> getColumnColorCriteria() {
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();
        
            colorCriteria.add(new String[]{"Estado",Planificacion.ESTADO_CREADA,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",Planificacion.ESTADO_CANCELADA,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",Planificacion.ESTADO_FINALIZADA,"184","204","240"});
        
        return colorCriteria;
    }

    @Override
    protected void abrirEntidad(int id) {
        
        // Necesita el ID de la Obra, no el de la Ejecución
        PedidoObra obra = null;
        try
        {
            HibernateUtil.beginTransaction();
            List<PedidoObra> obras = HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
            HibernateUtil.commitTransaction();
            
            for (int i = 0; i < obras.size(); i++) {
                PedidoObra po = obras.get(i);
                if(po.getPlanificacion()!=null && po.getPlanificacion().getId()==id){
                    obra = po;
                }
            }
        }
        catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:"+e.getMessage());
        } 
        
        if(obra!=null){
            EditarPlanificacion win = new EditarPlanificacion(obra.getId());
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }else{
            super.mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","La Ejecución no está asociada a ninguna Obra");
        }
        
    }
    
    

    @Override
    public boolean isFavorito() {
        return true;
    }

    @Override
    public String getIconoFavorito() {
        return "/res/iconos/var/16x16/List.png";
    }
    
}
