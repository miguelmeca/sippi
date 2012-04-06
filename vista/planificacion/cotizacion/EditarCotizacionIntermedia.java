/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorEditarCotizacion;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.TablaUtil;
import vista.cotizacion.*;



/**
 *
 * @author Administrador
 */
public class EditarCotizacionIntermedia extends EditarCotizacion{

    // No van mas
    protected static final int OPTN_DESCRIPCION           = -1;
    protected static final int OPTN_RRHH                  = -2;
    protected static final int OPTN_BENEFICIOS            = -3;
    
    // Nuevos
    protected static final int OPTN_HERRAMIENTAS          = 0;
    protected static final int OPTN_MATERIALES            = 1;
    protected static final int OPTN_ALQUILERES_COMPRAS    = 2;
    protected static final int OPTN_ADICIONALES           = 3;
    
    
    public EditarCotizacionIntermedia(GestorEditarCotizacion gestor) {
        super(gestor);
        editarMenuLateral();
    }

    @Override
    protected void initVentanaDefecto(String nombre) {
        super.setNombrePanel(nombre);
//        CotizacionDescripcion ecd = new CotizacionDescripcion(super.gestor.getGestorDescripcion());
//        getPanel().setViewportView(ecd);
//        ecd.setVisible(true);
    }

    @Override
    public void actualizar() {
        setMontoTotal("XXX");
    }

    private void editarMenuLateral() {
       
        DefaultTableModel modelo = (DefaultTableModel)getMenuLateral().getModel();
        TablaUtil.vaciarDefaultTableModel(modelo);
        
        Object[] fila = new String[1];
        fila[0] = "Herramientas";
        modelo.addRow(fila);
        
        fila[0] = "Materiales";
        modelo.addRow(fila);
        
        fila[0] = "Alquileres / Compras";
        modelo.addRow(fila);
        
        fila[0] = "Adicionales";
        modelo.addRow(fila);
        
    }

    @Override
    protected void clickMenuLateral(DefaultTableModel modelo) {
        switch(getMenuLateral().getSelectedRow())
        {
            case OPTN_BENEFICIOS:
            case OPTN_RRHH:
            case OPTN_DESCRIPCION:
                System.err.println("Error! Pero no debería pasar");
                break;
            case OPTN_MATERIALES:
                  JOptionPane.showMessageDialog(new JFrame(),"En construcción ...","Atencion!",JOptionPane.INFORMATION_MESSAGE);
//                  setNombrePanel(modelo.getValueAt(OPTN_MATERIALES,0).toString());
//                  CotizacionMateriales cm = new CotizacionMateriales(gestor.getGestorMateriales());
//                  getPanel().setViewportView(cm);
//                  cm.setVisible(true);
                break;
            case OPTN_ALQUILERES_COMPRAS:
                  JOptionPane.showMessageDialog(new JFrame(),"En construcción ...","Atencion!",JOptionPane.INFORMATION_MESSAGE);
                  setNombrePanel(modelo.getValueAt(OPTN_ALQUILERES_COMPRAS,0).toString());
//                CotizacionAlquileresCompras cc = new CotizacionAlquileresCompras(gestor.getGestorAlquileresCompras(),0);
//                panel.setViewportView(cc);
//                cc.setVisible(true);
                break;
            case OPTN_ADICIONALES:
                  JOptionPane.showMessageDialog(new JFrame(),"En construcción ...","Atencion!",JOptionPane.INFORMATION_MESSAGE);
                  setNombrePanel(modelo.getValueAt(OPTN_ADICIONALES,0).toString());
//                CotizacionAdicionales ca = new CotizacionAdicionales(gestor.getGestorAdicionales());
//                panel.setViewportView(ca);
//                ca.setVisible(true);
                break;
            
            case OPTN_HERRAMIENTAS:
                  JOptionPane.showMessageDialog(new JFrame(),"En construcción ...","Atencion!",JOptionPane.INFORMATION_MESSAGE);
                  setNombrePanel(modelo.getValueAt(OPTN_HERRAMIENTAS, 0).toString());
                  CotizacionHerramientas h = new CotizacionHerramientas(gestor.getGestorHerramientas());
                  getPanel().setViewportView(h);
                  h.setVisible(true);
                break;
            default:
                  JOptionPane.showMessageDialog(new JFrame(),"En construcción ...","Atencion!",JOptionPane.INFORMATION_MESSAGE);
//                setNombrePanel(modelo.getValueAt(OPTN_DESCRIPCION, 0).toString());
//                CotizacionDescripcion ecd2 = new CotizacionDescripcion(gestor.getGestorDescripcion());
//                panel.setViewportView(ecd2);
//                ecd2.setVisible(true);
        }        
    }
    
    
    
     
    
    
}
