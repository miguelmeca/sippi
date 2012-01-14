/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditarPlanificacion.java
 *
 * Created on 08-ene-2012, 17:13:17
 */
package vista.planificacion;

import vista.gui.dnd.PanelDropTarget;
import com.hackelare.coolgantt.*;
import com.hackelare.coolgantt.demo.demoEvents;
import com.hackelare.coolgantt.demo.demoTypes;
import com.hackelare.coolgantt.legacy.model.ColorLabel;
import controlador.planificacion.GestorEditarPlanificacion;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.NTupla;
import vista.gui.dnd.IDropEvent;

/**
 *
 * @author Administrador
 */
public class EditarPlanificacion extends javax.swing.JInternalFrame {

    private static final String iconoSubObra = "/res/iconos/var/16x16/Application.png";
    private static final String iconoTarea = "/res/iconos/var/16x16/calendar.png";
    
    
    private GestorEditarPlanificacion _gestor;
    ICoolGantt graph;
    JComponent grafico;
   

    /**
     * Creates new form EditarPlanificacion
     */
    public EditarPlanificacion(int idObra) {
        _gestor = new GestorEditarPlanificacion(this);
        initComponents();

        tblSubObras.setDefaultRenderer(Object.class, new ArbolDeTareasRender());
        tblTareas.setDefaultRenderer(Object.class, new ArbolDeTareasRender());

        initArbolRecursos();
        initGraph();

        //TODO: Sacar esto, test de DnD
        PanelDropTarget target = new PanelDropTarget(jPanel2, new GanttDropEvent());
        
    }

    private void initArbolRecursos() {

        initTablaSubObras();
        initTablaTareas(1);
        initArbolRecursosCotizados();
    }

    private void initGraph() {
        graph = CoolGanttFactory.create();
        graph.setEventHandler(new demoEvents());

        initTypeModel();
        initModel();

        grafico = graph.getComponent();
        
        // Soporte para Drop
        PanelDropTarget target = new PanelDropTarget(grafico, new GanttDropEvent());
        
        panelLineaDeTiempo.add(grafico, BorderLayout.CENTER);
        pack();
        setVisible(true);
        
        // Agrego el menu del boton derecho
        grafico.setComponentPopupMenu(jpm);
        
        // Le doy el zoom que quiero
         graph.setZoomOut();
          graph.setZoomOut();
           graph.setZoomOut();
            graph.setZoomOut();
             graph.setZoomOut();
         
        graph.setEventHandler(new GanttEventMouse());
    }

    private void updateGantt()
    {
        panelLineaDeTiempo.remove(grafico);
        pack();
        grafico = graph.getComponent();
        panelLineaDeTiempo.add(grafico, BorderLayout.CENTER);
        pack();
        // Soporte para Drop
        PanelDropTarget target = new PanelDropTarget(grafico, new GanttDropEvent());
    }
    
    private void initModel() {

        // Create a new Phrase
        CoolGanttPhase p1 = new CoolGanttPhase();
        p1.setEditable(true);
        p1.setId(1);
        p1.setNombre("Phase Test 1");
        p1.setType(demoTypes.TYPE_NORMAL);
        p1.setStartDate(new Date());
        p1.setEndDate(fechaMas(new Date(), 5));

        // Create a new Phrase
        CoolGanttPhase p2 = new CoolGanttPhase();
        p2.setEditable(true);
        p2.setId(2);
        p2.setNombre("Phase Test 2");
        p2.setType(demoTypes.TYPE_TRANSPORTE);
        p2.setStartDate(fechaMas(new Date(), 5));
        p2.setEndDate(fechaMas(new Date(), 10));

        // Create a new Phrase
        CoolGanttPhase p3 = new CoolGanttPhase();
        p3.setEditable(false);
        p3.setId(3);
        p3.setNombre("Phase Test 3");
        p3.setStartDate(fechaMas(new Date(), 10));
        p3.setEndDate(fechaMas(new Date(), 20));

        // Create a new Phrase
        CoolGanttPhase p4 = new CoolGanttPhase();
        p4.setId(4);
        p4.setNombre("Phase Test 4");
        p4.setStartDate(fechaMas(new Date(), 20));
        p4.setEndDate(fechaMas(new Date(), 30));

        // Create a new Phrase
        CoolGanttPhase p5 = new CoolGanttPhase();
        p5.setEditable(true);
        p5.setId(5);
        p5.setNombre("Phase Test 5");
        p5.setStartDate(fechaMas(new Date(), 10));
        p5.setEndDate(fechaMas(new Date(), 20));

        // Set dependencies
        p2.addSubsequentPhase(p1);
        p4.addSubsequentPhase(p2);
        p5.addSubsequentPhase(p1);

        graph.addPhase(p1);
        graph.addPhase(p2);
        graph.addPhase(p3);
        graph.addPhase(p4);
        graph.addPhase(p5);

        graph.refreshModel();
        
        //graph.refresh();
        //pack();
    }

    private Date fechaMas(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new Date(cal.getTimeInMillis());
    }

    private void initTypeModel() {
        CoolGanttTypeModel typeModel = new CoolGanttTypeModel();
        typeModel.addType(demoTypes.TYPE_NORMAL, ColorLabel.COLOR_LABELS[0]);
        typeModel.addType(demoTypes.TYPE_TRANSPORTE, ColorLabel.COLOR_LABELS[1]);
        typeModel.addType(demoTypes.TYPE_MONTAJE, ColorLabel.COLOR_LABELS[2]);
        graph.getModel().setTypeModel(typeModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpm = new javax.swing.JPopupMenu();
        menuNuevaEtapa = new javax.swing.JMenuItem();
        menuZoomMas = new javax.swing.JMenuItem();
        menuZoomMenos = new javax.swing.JMenuItem();
        menuDerecha = new javax.swing.JMenuItem();
        menuIzquierda = new javax.swing.JMenuItem();
        menuFechaActual = new javax.swing.JMenuItem();
        menuVistaAnual = new javax.swing.JMenuItem();
        menuVistaSemanal = new javax.swing.JMenuItem();
        panelBarraIzquierda = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTareas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSubObras = new javax.swing.JTable();
        btnEmitirInforme = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panelCentral = new javax.swing.JTabbedPane();
        panelLineaDeTiempo = new javax.swing.JPanel();
        panelArbolTareas = new javax.swing.JPanel();
        panelDatosGenerales = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        menuNuevaEtapa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        menuNuevaEtapa.setText("Nueva Tarea");
        menuNuevaEtapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevaEtapaActionPerformed(evt);
            }
        });
        jpm.add(menuNuevaEtapa);

        menuZoomMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        menuZoomMas.setText("Zoom +");
        menuZoomMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomMasActionPerformed(evt);
            }
        });
        jpm.add(menuZoomMas);

        menuZoomMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        menuZoomMenos.setText("Zoom -");
        menuZoomMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomMenosActionPerformed(evt);
            }
        });
        jpm.add(menuZoomMenos);

        menuDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        menuDerecha.setText("Mover a la Derecha");
        menuDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDerechaActionPerformed(evt);
            }
        });
        jpm.add(menuDerecha);

        menuIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        menuIzquierda.setText("Mover a la Izquierda");
        menuIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIzquierdaActionPerformed(evt);
            }
        });
        jpm.add(menuIzquierda);

        menuFechaActual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuFechaActual.setText("Ir a Fecha Actual");
        menuFechaActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFechaActualActionPerformed(evt);
            }
        });
        jpm.add(menuFechaActual);

        menuVistaAnual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuVistaAnual.setText("Vista Anual");
        menuVistaAnual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVistaAnualActionPerformed(evt);
            }
        });
        jpm.add(menuVistaAnual);

        menuVistaSemanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuVistaSemanal.setText("Vista Semanal");
        menuVistaSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVistaSemanalActionPerformed(evt);
            }
        });
        jpm.add(menuVistaSemanal);

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Editar Planificación");

        panelBarraIzquierda.setBorder(javax.swing.BorderFactory.createTitledBorder("Sub Obras Cotizadas"));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles de la SubObra"));

        tblTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tareas Cotizadas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTareas.setCellSelectionEnabled(true);
        tblTareas.setDragEnabled(true);
        tblTareas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblTareas);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Herramientas");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("AutoElevadora");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Alquileres / Compras");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Galpon");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Materiales");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Plancha 3' DXA x5");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setRootVisible(false);
        jScrollPane3.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        tblSubObras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la SubObra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSubObras.setCellSelectionEnabled(true);
        tblSubObras.setDragEnabled(true);
        tblSubObras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblSubObras);

        javax.swing.GroupLayout panelBarraIzquierdaLayout = new javax.swing.GroupLayout(panelBarraIzquierda);
        panelBarraIzquierda.setLayout(panelBarraIzquierdaLayout);
        panelBarraIzquierdaLayout.setHorizontalGroup(
            panelBarraIzquierdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBarraIzquierdaLayout.setVerticalGroup(
            panelBarraIzquierdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarraIzquierdaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEmitirInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/List.png"))); // NOI18N
        btnEmitirInforme.setText("Generar Cotización");
        btnEmitirInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmitirInformeActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Save.png"))); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles Generales"));

        panelLineaDeTiempo.setLayout(new java.awt.BorderLayout());
        panelCentral.addTab("Línea de Tiempo", panelLineaDeTiempo);

        javax.swing.GroupLayout panelArbolTareasLayout = new javax.swing.GroupLayout(panelArbolTareas);
        panelArbolTareas.setLayout(panelArbolTareasLayout);
        panelArbolTareasLayout.setHorizontalGroup(
            panelArbolTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );
        panelArbolTareasLayout.setVerticalGroup(
            panelArbolTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );

        panelCentral.addTab("Árbol de Tareas", panelArbolTareas);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDatosGeneralesLayout = new javax.swing.GroupLayout(panelDatosGenerales);
        panelDatosGenerales.setLayout(panelDatosGeneralesLayout);
        panelDatosGeneralesLayout.setHorizontalGroup(
            panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDatosGeneralesLayout.setVerticalGroup(
            panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        panelCentral.addTab("Test de Drag&Drop DnD", panelDatosGenerales);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBarraIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEmitirInforme)
                        .addGap(127, 127, 127)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelBarraIzquierda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnSave)
                            .addComponent(btnEmitirInforme))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmitirInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirInformeActionPerformed
    }//GEN-LAST:event_btnEmitirInformeActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    }//GEN-LAST:event_btnSaveActionPerformed

    private void menuNuevaEtapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevaEtapaActionPerformed

//        pantallaRegistrarEtapaRapida pre = new pantallaRegistrarEtapaRapida(this);
//        SwingPanel.getInstance().addWindow(pre);
//        pre.setVisible(true);
        
    }//GEN-LAST:event_menuNuevaEtapaActionPerformed

    private void menuZoomMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomMasActionPerformed
        graph.setZoomIn();
    }//GEN-LAST:event_menuZoomMasActionPerformed

    private void menuZoomMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomMenosActionPerformed
        graph.setZoomOut();
    }//GEN-LAST:event_menuZoomMenosActionPerformed

    private void menuDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDerechaActionPerformed
        graph.moveRight();
    }//GEN-LAST:event_menuDerechaActionPerformed

    private void menuIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIzquierdaActionPerformed
        graph.moveLeft();
    }//GEN-LAST:event_menuIzquierdaActionPerformed

    private void menuFechaActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFechaActualActionPerformed
        graph.setFocusToday();
    }//GEN-LAST:event_menuFechaActualActionPerformed

    private void menuVistaAnualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVistaAnualActionPerformed
        graph.setYearView();
    }//GEN-LAST:event_menuVistaAnualActionPerformed

    private void menuVistaSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVistaSemanalActionPerformed
        graph.setWeeklyView();
    }//GEN-LAST:event_menuVistaSemanalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEmitirInforme;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    private javax.swing.JPopupMenu jpm;
    private javax.swing.JMenuItem menuDerecha;
    private javax.swing.JMenuItem menuFechaActual;
    private javax.swing.JMenuItem menuIzquierda;
    private javax.swing.JMenuItem menuNuevaEtapa;
    private javax.swing.JMenuItem menuVistaAnual;
    private javax.swing.JMenuItem menuVistaSemanal;
    private javax.swing.JMenuItem menuZoomMas;
    private javax.swing.JMenuItem menuZoomMenos;
    private javax.swing.JPanel panelArbolTareas;
    private javax.swing.JPanel panelBarraIzquierda;
    private javax.swing.JTabbedPane panelCentral;
    private javax.swing.JPanel panelDatosGenerales;
    private javax.swing.JPanel panelLineaDeTiempo;
    private javax.swing.JTable tblSubObras;
    private javax.swing.JTable tblTareas;
    // End of variables declaration//GEN-END:variables

    private void initTablaSubObras() {


        ArrayList<NTupla> lista = (ArrayList<NTupla>) _gestor.getListaSubObras();

        for (int i = 0; i < lista.size(); i++) {
            NTupla nt = lista.get(i);
                   
            ArbolDeTareasCelda item = new ArbolDeTareasCelda(ArbolDeTareasTipos.TIPO_SUBOBRA,String.valueOf(nt.getId()));
            item.setItemData(iconoSubObra, nt.getNombre());

            DefaultTableModel modelo = (DefaultTableModel) tblSubObras.getModel();
            Object[] fila = new Object[1];
            fila[0] = item;
            modelo.addRow(fila);
        }
    }

    private void initTablaTareas(int idSubObra) {
        
        ArrayList<NTupla> lista = (ArrayList<NTupla>) _gestor.getListaTareasXSubObra(idSubObra);

        for (int i = 0; i < lista.size(); i++) {
            NTupla nt = lista.get(i);
            ArbolDeTareasCelda item = new ArbolDeTareasCelda(ArbolDeTareasTipos.TIPO_TAREA,String.valueOf(nt.getId()));
            item.setItemData(iconoTarea, nt.getNombre());

            DefaultTableModel modelo = (DefaultTableModel) tblTareas.getModel();
            Object[] fila = new Object[1];
            fila[0] = item;
            modelo.addRow(fila);

        }
        
    }

    private void initArbolRecursosCotizados() {
    }
    
    private void AgregarNuevaTarea(int id,String nombre)
    {
        // Create a new Phrase
        CoolGanttPhase p5 = new CoolGanttPhase();
        p5.setEditable(true);
        p5.setId(id);
        p5.setNombre(nombre);
        p5.setStartDate(fechaMas(new Date(), 10));
        p5.setEndDate(fechaMas(new Date(), 20));
        
        graph.addPhase(p5);

        graph.refreshModel();     
        updateGantt();
    }
    
    
    /**
     * InnerClass para manerja el disparo de evento Drag&Drop
     */
    public class GanttDropEvent implements IDropEvent{

        @Override
        public void dropEvent(String data) {

            if(data!=null && !data.isEmpty())
            {
                String[] dataTrigger = data.split(";");
                if(dataTrigger.length==3)
                {

                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_SUBOBRA))
                    {
                        // Por ahora no hago nada
                        JOptionPane.showMessageDialog(new JFrame(),"ESTO ES UNA SUBOBRA IDIOTA \n Disparador del Drop > Tipo:"+ dataTrigger[0]+"  ID: "+dataTrigger[1]);
                    }
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_TAREA))
                    {
                        // Agrego una nueva Tarea
                        AgregarNuevaTarea(Integer.parseInt(dataTrigger[1]),dataTrigger[2]);
                    }
                }
            }   
        }
    }
    
}
