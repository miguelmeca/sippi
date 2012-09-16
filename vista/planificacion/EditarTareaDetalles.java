/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * editarCotizacion_Descripcion.java
 *
 * Created on 06-jun-2011, 10:22:15
 */

package vista.planificacion;


import com.itextpdf.text.log.SysoLogger;
import controlador.planificacion.GestorEditarTareaDetalles;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.DetalleTareaPlanificacion;
import modelo.TareaPlanificacion;
import util.NTupla;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.ICallBack_v3;

/**
 *
 * @author Fran
 */
public class EditarTareaDetalles extends javax.swing.JPanel implements ICallBack_v3 {

    private GestorEditarTareaDetalles gestor;
    boolean tareaHijaDePlanificacion=false; 
    
    public EditarTareaDetalles(GestorEditarTareaDetalles gestor) {
        initComponents();        
        this.gestor = gestor;
        gestor.setPantallaLista(this);
        inicializarVentana();
        cambiarTamCabeceraTablas();
    }
    public void inicializarVentana()
    {
      tblDetalles.getTableHeader().setPreferredSize(new Dimension(tblDetalles.getColumnModel().getTotalColumnWidth(), 34));
      inicializarTablaDetalles();
      tareaHijaDePlanificacion=gestor.getTareaActual().esCotizada(); 
      if(tareaHijaDePlanificacion)
      {
          btnAgregarDetalle.setVisible(false);
      }
      else
      {
          btnAgregarDetalle.setVisible(true);
      }
      cargarLabelsTotalesTarea();
      cargarLabelsTotalesTareaYSubtareas();
      
    }
    private void cambiarTamCabeceraTablas()
    {
        Font fuente = new Font("Verdana", Font.PLAIN, 9);
        
        JTableHeader th2;
        th2 = tblDetalles.getTableHeader();
        th2.setFont(fuente);        
    }
    
    private void inicializarTablaDetalles()
    {
        llenarTablaDetalles(gestor.getTareaActual());
    }
    private void llenarTablaDetalles(TareaPlanificacion tarea)
    {
       ((DefaultTableModel)tblDetalles.getModel()).setRowCount(0);
       for (DetalleTareaPlanificacion detalle:tarea.getDetallesSinDetallesVacios()) {
       //for (DetalleTareaPlanificacion detalle:tarea.getDetalles()) {
            agregarDetalleTareaATabla(detalle);            
        }       
       
        habilitarBotonesParaTablaDetalle(false);
    }
    
    private void vaciarTablaDetalles()
    {
        ((DefaultTableModel)tblDetalles.getModel()).setRowCount(0);
         habilitarBotonesParaTablaDetalle(false);
    }
    
    private void agregarDetalleTareaATabla(DetalleTareaPlanificacion detalleTarea) //throws Exception
    {
       Object[] datos=new Object[6];   
       NTupla nombreTipoEspecialidad=new NTupla();
       nombreTipoEspecialidad.setNombre(detalleTarea.getEspecialidad().getTipo().getNombre());
       nombreTipoEspecialidad.setData(detalleTarea);
       datos[0]=nombreTipoEspecialidad; 
       Tupla detalleYNombreRango=new Tupla();
       detalleYNombreRango.setNombre(detalleTarea.getEspecialidad().getRango().getNombre());       
       datos[1]=detalleYNombreRango;      
       datos[2]=detalleTarea.getCantidadPersonasAsignadas()+"/"+detalleTarea.getCantidadPersonas();
       datos[3]=detalleTarea.getCantHorasNormales();
       datos[4]=detalleTarea.getCantHorasAl50();
       datos[5]=detalleTarea.getCantHorasAl100();
       
       DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
       
           modelo.addRow(datos);
       
    }
    
   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        lblTotalHsNormalesConSubtareas = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblTotalHs50ConSubtareas = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblTotalHs100ConSubtareas = new javax.swing.JLabel();
        btnAgregarDetalle = new javax.swing.JButton();
        btnEditarDetalle = new javax.swing.JButton();
        btnQuitarDetalle = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        lblTotalHsNormales = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblTotalHs50 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblTotalHs100 = new javax.swing.JLabel();
        btnAgregarDetalleNoCotizado = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        tblDetalles.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Especialidad", "Rango", "<html>Personas<br> Asignadas</html>", "Hs Normales", "Hs 50%", "Hs 100%"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetallesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDetallesMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblDetallesMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblDetalles);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Total de horas en esta tarea y sus subtareas"));

        jLabel24.setText("Hs Normales");

        lblTotalHsNormalesConSubtareas.setText("--");

        jLabel26.setText("Horas 50%");

        lblTotalHs50ConSubtareas.setText("--");

        jLabel28.setText("Horas 100%");

        lblTotalHs100ConSubtareas.setText("--");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHsNormalesConSubtareas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHs50ConSubtareas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHs100ConSubtareas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel24)
                .addComponent(lblTotalHsNormalesConSubtareas)
                .addComponent(jLabel26)
                .addComponent(lblTotalHs50ConSubtareas)
                .addComponent(jLabel28)
                .addComponent(lblTotalHs100ConSubtareas))
        );

        btnAgregarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarDetalle.setText("Agregar detalle desde tarea contenedora");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        btnEditarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png"))); // NOI18N
        btnEditarDetalle.setText("Editar");
        btnEditarDetalle.setEnabled(false);
        btnEditarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDetalleActionPerformed(evt);
            }
        });

        btnQuitarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.setEnabled(false);
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        jLabel5.setText("Detalles de la tarea actual");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Total de horas en esta tarea (sin incluir sus subtareas)"));

        jLabel30.setText("Hs Normales");

        lblTotalHsNormales.setText("--");

        jLabel31.setText("Horas 50%");

        lblTotalHs50.setText("--");

        jLabel32.setText("Horas 100%");

        lblTotalHs100.setText("--");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHs50, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHs100, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel30)
                .addComponent(lblTotalHsNormales)
                .addComponent(jLabel31)
                .addComponent(lblTotalHs50)
                .addComponent(jLabel32)
                .addComponent(lblTotalHs100))
        );

        btnAgregarDetalleNoCotizado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarDetalleNoCotizado.setText("Agregar detalle no cotizado");
        btnAgregarDetalleNoCotizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleNoCotizadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnAgregarDetalle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarDetalleNoCotizado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnQuitarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarDetalleNoCotizado)
                    .addComponent(btnQuitarDetalle)
                    .addComponent(btnEditarDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarDetalle)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed
      
        EditarTareaDetallesABM_SeleccionDetallePadre at = new EditarTareaDetallesABM_SeleccionDetallePadre(this, gestor);
        SwingPanel.getInstance().addWindow(at);
       at.setVisible(true);
    }//GEN-LAST:event_btnAgregarDetalleActionPerformed

    private void btnEditarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDetalleActionPerformed
      modificarDetalle();
        
    }//GEN-LAST:event_btnEditarDetalleActionPerformed

    private void modificarDetalle()
    {
         if (tblDetalles.getSelectedRow() != -1) {
            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            DetalleTareaPlanificacion detalleTarea = (DetalleTareaPlanificacion) ((NTupla) modelo.getValueAt(tblDetalles.getSelectedRow(), 0)).getData();    
            gestor.setDetalleAcutal(detalleTarea,gestor.getTareaActual().esCotizada());
            EditarTareaDetallesABM at = new EditarTareaDetallesABM(this, gestor, true, tareaHijaDePlanificacion);
            
            SwingPanel.getInstance().addWindow(at);
            at.setVisible(true);
           
        }
    }
    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
        if (tblDetalles.getSelectedRow() != -1) {
            int respuesta = JOptionPane.showOptionDialog(this, "¿Mover las horas a la tarea superior?", "Eliminar detalle", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) ;
            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            DetalleTareaPlanificacion detalleTarea = (DetalleTareaPlanificacion) ((NTupla) modelo.getValueAt(tblDetalles.getSelectedRow(), 0)).getData();    
            
            boolean pasarHorasAlPadre=false;
            if ( respuesta== JOptionPane.YES_OPTION)
            {
                pasarHorasAlPadre=true;                
            }
            else if(respuesta== JOptionPane.NO_OPTION)
            {
                pasarHorasAlPadre=false;
            }
            
            if(respuesta!=JOptionPane.CANCEL_OPTION)
            {
                try{
                gestor.eliminarDetalle(detalleTarea, pasarHorasAlPadre);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                inicializarTablaDetalles();
                habilitarBotonesParaTablaDetalle(false);
            }
        }
    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

    private void tblDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesMouseClicked
        clickEnFilaTablaDetalles(evt);
    }//GEN-LAST:event_tblDetallesMouseClicked

    private void tblDetallesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesMousePressed
       clickEnFilaTablaDetalles(evt);
    }//GEN-LAST:event_tblDetallesMousePressed

    private void tblDetallesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesMouseReleased
        clickEnFilaTablaDetalles(evt);
    }//GEN-LAST:event_tblDetallesMouseReleased

    private void btnAgregarDetalleNoCotizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleNoCotizadoActionPerformed
        
        
        gestor.crearNuevoDetalleNoCotizado();
        EditarTareaDetallesABM at = new EditarTareaDetallesABM(this, gestor,false, tareaHijaDePlanificacion);
       
        SwingPanel.getInstance().addWindow(at);
       at.setVisible(true);
    }//GEN-LAST:event_btnAgregarDetalleNoCotizadoActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        inicializarVentana();
    }//GEN-LAST:event_formFocusGained
    
    private void clickEnFilaTablaDetalles(java.awt.event.MouseEvent evt)
    {
        int selectedRow=tblDetalles.getSelectedRow();
   
           if(evt.getClickCount()==1)
           {
               boolean clickEnFila=false;
               if(selectedRow!=-1)
             {
                clickEnFila=true;                
             }
             habilitarBotonesParaTablaDetalle(clickEnFila);
           }
           if(evt.getClickCount()==2)
           {
            modificarDetalle();
           }
        
        
        evt.consume();
    }
    
    public void MostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }
    
    private void habilitarBotonesParaTablaDetalle(boolean habilitar)
    {
       btnEditarDetalle.setEnabled(habilitar);
       btnQuitarDetalle.setEnabled(habilitar);
    }
    
    private void cargarLabelsTotalesTarea()
    {
        lblTotalHsNormales.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasNormalesSinSubtareas()));    
        lblTotalHs50.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasAl50SinSubtareas()));
        lblTotalHs100.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasAl100SinSubtareas()));
    }
    
    private void cargarLabelsTotalesTareaYSubtareas()
    {
        lblTotalHsNormalesConSubtareas.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasNormalesConSubtareas()));    
        lblTotalHs50ConSubtareas.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasAl50ConSubtareas()));
        lblTotalHs100ConSubtareas.setText(String.valueOf(gestor.getTareaActual().obtenerTotalDeHorasAl100ConSubtareas()));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnAgregarDetalleNoCotizado;
    private javax.swing.JButton btnEditarDetalle;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblTotalHs100;
    private javax.swing.JLabel lblTotalHs100ConSubtareas;
    private javax.swing.JLabel lblTotalHs50;
    private javax.swing.JLabel lblTotalHs50ConSubtareas;
    private javax.swing.JLabel lblTotalHsNormales;
    private javax.swing.JLabel lblTotalHsNormalesConSubtareas;
    private javax.swing.JTable tblDetalles;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actualizar(int id, String flag, boolean exito, Object[] data) {
       inicializarVentana();
    }
    

   
}
