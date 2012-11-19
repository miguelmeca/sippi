/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ejecucion;

import controlador.ejecucion.GestorEjecucion;
import vista.ejecucion.*;
import controlador.planificacion.GestorEditarTareaDetalles;
import controlador.rrhh.GestorConsultarEmpleado;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import modelo.*;
import util.NTupla;
import vista.interfaces.ICallBack_v3;
import vista.rrhh.EmpleadoActivoRowFilter;
import vista.rrhh.ExplorarEmpleados_RenderCeldas;
import vista.rrhh.ExplorarEmpleados_celdaDatos;
import vista.rrhh.ExplorarEmpleados_celdaFoto;

/**
 *
 * @author Fran
 */
public class EdicionDetalleRRHH extends javax.swing.JInternalFrame {

    private GestorEjecucion gestor;
    private ICallBack_v3 pantallaPrincipal;
    private boolean modificacion; //modificacion = false entonces es detalle nuevo
    
    double costoEnDetalleSuperior;
    boolean tareaHijaDePlanificacion=false; 
    
    
    int cantPersonasAsignadas;
    int cantPersonas;
    double cantHsNormales;
    double cantHs100;
    double cantHs50;
    Especialidad especialidadPadre;
    int idEspecialidad;
    double costoDetalle;
    
    boolean enProceso=false;
    private boolean filtroBuscarActivado;
    private GestorConsultarEmpleado gestorConsultarEmpleado;
    private List<Empleado> listaEmpleadosDisponibles;
    private List<Empleado> listaEmpleadosAsignados;
    private DefaultTableModel modeloTablaEmpleadosDisponibles;
    private DefaultTableModel modeloTablaEmpleadosAsignados;
    
    
    DetalleTareaEjecucionXDia detalleRRHHXDiaSeleccionado;
    DetalleTareaEjecucion detalleRRHHSeleccionado;
    
    public EdicionDetalleRRHH(ICallBack_v3 pantalla, GestorEjecucion gestor) {
        cantPersonas=1;
        gestorConsultarEmpleado = new GestorConsultarEmpleado();
        this.tareaHijaDePlanificacion=tareaHijaDePlanificacion;
        initComponents();        
        this.gestor = gestor;
        detalleRRHHXDiaSeleccionado = gestor.getDetalleRRHHXDiaSeleccionado();
        detalleRRHHSeleccionado = gestor.getDetalleRRHHSeleccionado();
        
        listaEmpleadosAsignados= new ArrayList<Empleado>();
        //gestor.setPantallaABM(this);     
        inicializarVentana();
        
        pantallaPrincipal=pantalla;
        
       
        
        
    }
    
    
    /*public void tomarDatosDetalleModificado(TareaPlanificacion tareaDeDetallePadre, DetalleTareaPlanificacion detallePadre, DetalleTareaPlanificacion detalleAcutal)
    {
        tomarDatosDetalleModificado(detalleAcutal);
        detalleSuperiorSeleccionado=detallePadre;
        clicEnTablaDetallesTareaSuperior(false);
    }*/

    
    private void inicializarVentana()
    {
        enProceso=true;
     filtroBuscarActivado=false;
     
     rbFiltroTodos.setSelected(true);
     rbFiltroActivos.setSelected(false);    
     
        ////////////////////////////////////
        tblEmpleadosDisponibles.setDefaultRenderer(Object.class,new ExplorarEmpleados_RenderCeldas());
        tblEmpleadosDisponibles.setRowHeight(72);
        tblEmpleadosDisponibles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEmpleadosDisponibles.getColumnModel().getColumn(0).setPreferredWidth(72);
        tblEmpleadosDisponibles.getColumnModel().getColumn(1).setPreferredWidth(465);
        
        tblEmpleadosAsignados.setDefaultRenderer(Object.class,new ExplorarEmpleados_RenderCeldas());
        tblEmpleadosAsignados.setRowHeight(72);
        tblEmpleadosAsignados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEmpleadosAsignados.getColumnModel().getColumn(0).setPreferredWidth(72);
        tblEmpleadosAsignados.getColumnModel().getColumn(1).setPreferredWidth(465);
        
      enProceso=false;
      cargarEmpleadosDisponibles();
      cargarEmpleadosAsignados();
      limpiarSobranteTablaEmpleadosDisponibles();
      
       this.setTitle("Detalle de Recursos Humanos en tarea '"+gestor.getTareaSeleccionada().getNombre()+"'");
      
    }
    
  
    /*
    public void cargarCboRangos(TipoEspecialidad te)
    {
        ArrayList<NTupla> listaRangos = gestor.mostrarRangos(te);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaRangos!=null && !listaRangos.isEmpty())
        for (NTupla rango : listaRangos)
        {
            model.addElement(rango);
        }
        cboRango.setModel(model);
        NTupla t0 = new NTupla(-1);
        t0.setNombre("Seleccione un rango..."); 
        cboRango.insertItemAt(t0, 0);
        cboRango.setSelectedIndex(0);   
    }
    public void cargarCboTipoEspecialidad()
    {
        ArrayList<NTupla> listaTipoEspecialidad = gestor.mostrarTiposEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaTipoEspecialidad!=null && !listaTipoEspecialidad.isEmpty())
        for (NTupla te : listaTipoEspecialidad)
        {
            model.addElement(te);
        }
        cboTipoEspecialidad.setModel(model);
        NTupla t0 = new NTupla(-1);
        t0.setNombre("Seleccione una especialidad...");
        cboTipoEspecialidad.insertItemAt(t0, 0);
        cboTipoEspecialidad.setSelectedIndex(0);  
        
        cboRango.setModel(new DefaultComboBoxModel());        
    }*/
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        rbFiltroTodos = new javax.swing.JRadioButton();
        rbFiltroActivos = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleadosDisponibles = new javax.swing.JTable();
        btnAgregarEmpleado = new javax.swing.JButton();
        btnQuitarEmpleado = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleadosAsignados = new javax.swing.JTable();

        setTitle("Detalle de Recursos Humanos");
        setOpaque(true);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setEnabled(false);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        rbFiltroTodos.setText("Mostrar Todos");
        rbFiltroTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroTodosActionPerformed(evt);
            }
        });

        rbFiltroActivos.setText("Mostrar sólo empleados en estado \"Activo\"");
        rbFiltroActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroActivosActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Empleados registrados:");

        tblEmpleadosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Foto", "Datos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleadosDisponibles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleadosDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEmpleadosDisponiblesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleadosDisponibles);

        btnAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarEmpleado.setText("Agregar");
        btnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpleadoActionPerformed(evt);
            }
        });

        btnQuitarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Erase.png"))); // NOI18N
        btnQuitarEmpleado.setText("Quitar");
        btnQuitarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarEmpleadoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Empleados asignados:");

        tblEmpleadosAsignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Foto", "Datos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleadosAsignados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleadosAsignados.setTableHeader(null);
        tblEmpleadosAsignados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEmpleadosAsignadosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmpleadosAsignados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(jLabel19)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(rbFiltroTodos)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rbFiltroActivos)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbFiltroTodos)
                        .addComponent(rbFiltroActivos))
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarEmpleado)
                    .addComponent(btnQuitarEmpleado))
                .addGap(8, 8, 8)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        //gestor.impactarCambiosEnModeloDeDetalleTareaEjecucionXDia();
        
        detalleRRHHSeleccionado.setEmpleados(listaEmpleadosAsignados);
        this.actualizarPantallas();
        cerrar();
        
    }//GEN-LAST:event_btnAceptarActionPerformed
     
    public void cerrar() 
    {
      gestor.setDetalleRRHHXDiaSeleccionado(null);
      gestor.setDetalleRRHHSeleccionado(null);
      this.dispose();  
    }
    
    public void actualizarPantallas()
    {
        pantallaPrincipal.actualizar(0, "", true, null);
    }
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cerrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void rbFiltroTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroTodosActionPerformed
        rbFiltroTodos.setSelected(true);
        rbFiltroActivos.setSelected(false);
        activarFiltrosTabla();
    }//GEN-LAST:event_rbFiltroTodosActionPerformed

    private void rbFiltroActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroActivosActionPerformed
        rbFiltroTodos.setSelected(false);
        rbFiltroActivos.setSelected(true);
        activarFiltrosTabla();
    }//GEN-LAST:event_rbFiltroActivosActionPerformed

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked

        if (txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained

        if (txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
            filtroBuscarActivado = true;
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (txtBuscar.getText().equals("")) {
            txtBuscar.setText("Buscar...");
            txtBuscar.setForeground(Color.GRAY);
            filtroBuscarActivado = false;
        } else {
            filtroBuscarActivado = true;
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        activarFiltrosTabla();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

   }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed
        int selectedRow=tblEmpleadosDisponibles.getSelectedRow();
       
       if(tblEmpleadosAsignados.getRowCount()<cantPersonas)
       {
           if(selectedRow!=-1 )
            {
                DefaultTableModel modeloDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();           
                DefaultTableModel modeloAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
                int altura = tblEmpleadosDisponibles.getRowHeight(tblEmpleadosDisponibles.getSelectedRow());
                modeloAsignados.addRow((Vector)modeloDisponibles.getDataVector().elementAt(tblEmpleadosDisponibles.getSelectedRow()));
                tblEmpleadosAsignados.setRowHeight(tblEmpleadosAsignados.getRowCount()-1, altura);
                modeloDisponibles.removeRow(tblEmpleadosDisponibles.getSelectedRow());  
            }
       }
       else {
            JOptionPane.showMessageDialog(this.getParent(), "Todos los empleados ya se encuentran asignados a este detalle", "Asignados todos los empelados", JOptionPane.INFORMATION_MESSAGE);          
       }
       btnAgregarEmpleado.setEnabled(false);
       calcularListaEmpleadosAsignados();
       btnAceptar.setEnabled(true);
    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void btnQuitarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarEmpleadoActionPerformed
      int selectedRow=tblEmpleadosAsignados.getSelectedRow();
      if(selectedRow!=-1)
       {
           /*int id;
           id=((ExplorarEmpleados_celdaDatos)(tblEmpleadosAsignados.getModel().getValueAt(tblEmpleadosAsignados.getSelectedRow(), 1))).getId();
           */
           DefaultTableModel modeloDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();           
           DefaultTableModel modeloAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
           int altura = tblEmpleadosAsignados.getRowHeight(tblEmpleadosAsignados.getSelectedRow());
           modeloDisponibles.addRow((Vector)modeloAsignados.getDataVector().elementAt(tblEmpleadosAsignados.getSelectedRow()));
           tblEmpleadosDisponibles.setRowHeight(tblEmpleadosDisponibles.getRowCount()-1, altura);
           modeloAsignados.removeRow(tblEmpleadosAsignados.getSelectedRow()); 
           
       }
      btnQuitarEmpleado.setEnabled(false);
      calcularListaEmpleadosAsignados();
      btnAceptar.setEnabled(true);
    }//GEN-LAST:event_btnQuitarEmpleadoActionPerformed

    private void tblEmpleadosDisponiblesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosDisponiblesMousePressed
        clicEnTablaEmpleadosDisponibles();
         evt.consume();
    }//GEN-LAST:event_tblEmpleadosDisponiblesMousePressed

    private void tblEmpleadosAsignadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosAsignadosMousePressed
        clicEnTablaEmpleadosAsignados();
        evt.consume();        
    }//GEN-LAST:event_tblEmpleadosAsignadosMousePressed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        inicializarVentana();
    }//GEN-LAST:event_formFocusGained
           
    private void clicEnTablaEmpleadosDisponibles()
    {
       int selectedRow=tblEmpleadosDisponibles.getSelectedRow();
       if(selectedRow!=-1)
       {
              btnAgregarEmpleado.setEnabled(true);
              btnQuitarEmpleado.setEnabled(false);
              tblEmpleadosAsignados.clearSelection();
       }
       else
       {
           btnAgregarEmpleado.setEnabled(false);
       }
         
    }
    
     private void clicEnTablaEmpleadosAsignados()
    {
       int selectedRow=tblEmpleadosAsignados.getSelectedRow();
       if(selectedRow!=-1)
       {
              btnQuitarEmpleado.setEnabled(true);
              btnAgregarEmpleado.setEnabled(false);
              tblEmpleadosDisponibles.clearSelection();
              
       }
       else
       {
           btnQuitarEmpleado.setEnabled(false);
       }     
         
    }
    
    
   
    
    
    
    
    public void MostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarEmpleado;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnQuitarEmpleado;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbFiltroActivos;
    private javax.swing.JRadioButton rbFiltroTodos;
    private javax.swing.JTable tblEmpleadosAsignados;
    private javax.swing.JTable tblEmpleadosDisponibles;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

     /*private void tomarDatosDetalleNuevo() {
        especialidadPadre = detalleSuperiorSeleccionado.getEspecialidad();
        //gestor.crearNuevoDetalleAcutal(detalleSuperiorSeleccionado);
        SpinnerModel modelPersonas =
                new SpinnerNumberModel(1, //initial value
                1, //min
                detalleSuperiorSeleccionado.getCantidadPersonas(), //max
                1);//step
        spnPersonas.setModel(modelPersonas);
        spnPersonas.setValue(detalleSuperiorSeleccionado.getCantidadPersonas());
        cantPersonas = detalleSuperiorSeleccionado.getCantidadPersonas();
        spnHsNormales.setValue(0.0);
        spnHs50.setValue(0.0);
        spnHs100.setValue(0.0);
        for (int i = 0; i < cboTipoEspecialidad.getItemCount(); i++) {
            if ((((NTupla) cboTipoEspecialidad.getItemAt(i)).getId()) == especialidadPadre.getTipo().getId()) {
                cboTipoEspecialidad.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cboRango.getItemCount(); i++) {
            if ((((NTupla) cboRango.getItemAt(i)).getId()) == especialidadPadre.getId()) {
                cboRango.setSelectedIndex(i);
                break;
            }
        }
        costoDetalle = detalleSuperiorSeleccionado.getCostoXHoraNormal();
        txtCosto.setText(String.valueOf(costoDetalle));
    }*/

    
    
    private void cargarEmpleadosDisponibles()
    {
        listaEmpleadosDisponibles=gestorConsultarEmpleado.listaEmpleados();
       
        modeloTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();
        int fil=modeloTablaEmpleadosDisponibles.getRowCount();
        for (int i = 0; i < fil; i++) {
            modeloTablaEmpleadosDisponibles.removeRow(0);
        }
        
        
        
        int rowNumber = 0;
        for (Empleado empleado : listaEmpleadosDisponibles)
        {
             Object[] filaTabla=new Object[2];
           
            ExplorarEmpleados_celdaDatos celdaDatos = new ExplorarEmpleados_celdaDatos();            
            celdaDatos.setEmpleado(empleado);  
            ExplorarEmpleados_celdaFoto celdaFoto = new ExplorarEmpleados_celdaFoto();
            celdaFoto.setEmpleado(empleado);
            
            filaTabla[0] = celdaFoto;
            filaTabla[1] = celdaDatos;
            modeloTablaEmpleadosDisponibles.addRow(filaTabla);
            tblEmpleadosDisponibles.setRowHeight(rowNumber, celdaDatos.getAltura());
            
            rowNumber++;
            ////////////////////////
        }
       
        tblEmpleadosDisponibles.setModel(modeloTablaEmpleadosDisponibles);
    }
    
    private void cargarEmpleadosAsignados()
    {
        for (int i = 0; i < detalleRRHHSeleccionado.getEmpleados().size(); i++) {
            listaEmpleadosAsignados.add(detalleRRHHSeleccionado.getEmpleados().get(i));
        }
       
        modeloTablaEmpleadosAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
        int fil=modeloTablaEmpleadosAsignados.getRowCount();
        for (int i = 0; i < fil; i++) {
            modeloTablaEmpleadosAsignados.removeRow(0);
        }
        
        int rowNumber = 0;
        for (Empleado empleado : listaEmpleadosAsignados)
        {
             Object[] filaTabla=new Object[2];
           
            ExplorarEmpleados_celdaDatos celdaDatos = new ExplorarEmpleados_celdaDatos();            
            celdaDatos.setEmpleado(empleado);  
            ExplorarEmpleados_celdaFoto celdaFoto = new ExplorarEmpleados_celdaFoto();
            celdaFoto.setEmpleado(empleado);
            
            filaTabla[0] = celdaFoto;
            filaTabla[1] = celdaDatos;
            modeloTablaEmpleadosAsignados.addRow(filaTabla);
            tblEmpleadosAsignados.setRowHeight(rowNumber, celdaDatos.getAltura());
            
            rowNumber++;
            ////////////////////////
        }
       
        tblEmpleadosAsignados.setModel(modeloTablaEmpleadosAsignados);
    }
    
    private void limpiarSobranteTablaEmpleadosDisponibles()
    {
        //DefaultTableModel modeloXTablaEmpleadosAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
        int filAsig=modeloTablaEmpleadosAsignados.getRowCount();
        
        //DefaultTableModel modeloXTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();
       
        for (int i = filAsig-1; i >=0 ; i--) {
            for (int j = modeloTablaEmpleadosDisponibles.getRowCount()-1; j >=0 ; j--) 
            {
                if(((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosAsignados.getValueAt(i, 1)).getEmpleado().getLegajo()  ==  ((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosDisponibles.getValueAt(j, 1)).getEmpleado().getLegajo())
                {modeloTablaEmpleadosDisponibles.removeRow(j);}
            }
            
        }
        
    }
    
    private void calcularListaEmpleadosAsignados()
    {
        listaEmpleadosAsignados.clear();
        int filAsig=modeloTablaEmpleadosAsignados.getRowCount();        
        //DefaultTableModel modeloXTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();       
        for (int i = filAsig-1; i >=0 ; i--) {
            
            listaEmpleadosAsignados.add(((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosAsignados.getValueAt(i, 1)).getEmpleado());
        }
        cantPersonasAsignadas=listaEmpleadosAsignados.size();
        
       
    }
    
    private void activarFiltrosTabla()
    {
         TableRowSorter<TableModel> modeloOrdenado;
            modeloOrdenado = new TableRowSorter<TableModel>(modeloTablaEmpleadosDisponibles);
            tblEmpleadosDisponibles.setRowSorter(modeloOrdenado);
        

        if(filtroBuscarActivado)
        {
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i],1));
           }
            if(rbFiltroActivos.isSelected())
           {
              filters.add(RowFilter.regexFilter(gestorConsultarEmpleado.nombreEstadoEmpleadoActivo(), 3));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);

        }
        else
        {
             if(rbFiltroActivos.isSelected())
           {
              modeloOrdenado.setRowFilter(new EmpleadoActivoRowFilter());
           }
        }


    }
}
