/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarCursoCapacitacion.java
 *
 * Created on 08-may-2010, 19:48:51
 */

package vista.rrhh;

import controlador.rrhh.GestorRegistrarTallerDeCapacitacion;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import util.FechaUtil;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.ICallBack;

/**
 *
 * @author Iuga
 */
public class pantallaRegistrarTallerCapacitacion extends javax.swing.JInternalFrame implements ICallBack {

    private GestorRegistrarTallerDeCapacitacion gestor;

    private int FLAG_TIPO_CAPACITACION = 0;
    private int FLAG_LUGAR_CAPACITACION = 1;

    /** Creates new form frmRegistrarCursoCapacitacion */
    public pantallaRegistrarTallerCapacitacion() {
        initComponents();

        gestor = new GestorRegistrarTallerDeCapacitacion(this);

        habilitarVentana();

    }

    private void habilitarVentana()
    {
        mostrarTiposCapacitacion();
        mostrarLugaresCapacitacion();
    }

    public void mostrarTiposCapacitacion()
    {
        ArrayList<Tupla> lista = gestor.buscarTiposDeCapacitacion();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        
        // Si la lista no tiene elementos cargo una por defecto
        if(lista.size()==0)
        {
            Tupla tpn = new Tupla();
            tpn.setId(0);
            tpn.setNombre("Seleccione un Tipo de Capacitación...");
            valores.addElement(tpn);
        }

        cmbTipoCapacitacion.setModel(valores);
    }

    public void mostrarLugaresCapacitacion()
    {
        ArrayList<Tupla> lista = gestor.buscarLugarDeTaller();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbNombreLugarCapacitacion.setModel(valores);

        mostrarDomicilioLugarSeleccionado();
    }

    public void mostrarCapacitadores()
    {
        // Busco el tipo de capacitacion seleccionada
        Tupla tp = (Tupla)cmbTipoCapacitacion.getSelectedItem();

        // Si es cero es que no selecciono xq no hay
        if(tp.getId()!=0)
        {
            ArrayList<Tupla> lista = gestor.buscarCapacitadoresParaTipoCapacitacion(tp.getId());

            DefaultComboBoxModel valores = new DefaultComboBoxModel();
            Iterator<Tupla> it = lista.iterator();
            while(it.hasNext()){
                Tupla tu = it.next();
                valores.addElement(tu);
            }
            cmbNombreCapacitador.setModel(valores);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupTablaHorarios = new javax.swing.JPopupMenu();
        menuBorrar = new javax.swing.JMenuItem();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();
        btnAgregarHorario = new javax.swing.JButton();
        lblNombreTaller = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        txtNombreTaller = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        lblTipoCapacitacion = new javax.swing.JLabel();
        cmbTipoCapacitacion = new javax.swing.JComboBox();
        btnNuevoTipoCapacitacion = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblNombreLugarCapacitacion = new javax.swing.JLabel();
        cmbNombreLugarCapacitacion = new javax.swing.JComboBox();
        lblDieccionLugar = new javax.swing.JLabel();
        txtDireccionLugar = new javax.swing.JTextField();
        btnNuevoLugar = new javax.swing.JButton();
        lblNombreCapacitador = new javax.swing.JLabel();
        cmbNombreCapacitador = new javax.swing.JComboBox();
        btnNuevoCapacitador = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAgregarEmpleado = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleadosQueAsistiran = new javax.swing.JTable();

        menuBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        menuBorrar.setText("Quitar");
        menuBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBorrarActionPerformed(evt);
            }
        });
        popupTablaHorarios.add(menuBorrar);

        setClosable(true);
        setForeground(java.awt.Color.white);
        setIconifiable(true);
        setTitle("Registrar Taller de Capacitación para los empleados");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Taller"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Horarios del Taller"));

        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Hora de Inicio", "Hora de Fin"
            }
        ));
        tblHorarios.setComponentPopupMenu(popupTablaHorarios);
        jScrollPane3.setViewportView(tblHorarios);

        btnAgregarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarHorario.setText("Agregar");
        btnAgregarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHorarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarHorario))
        );

        lblNombreTaller.setText("Nombre: ");

        lblDescripcion.setText("Descripción:");

        txtNombreTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreTallerActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane4.setViewportView(txtDescripcion);

        lblTipoCapacitacion.setText("Tipo de Capacitación:");

        cmbTipoCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoCapacitacionActionPerformed(evt);
            }
        });

        btnNuevoTipoCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoTipoCapacitacion.setText("Nuevo Tipo de Capacitacion");
        btnNuevoTipoCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoCapacitacionActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lugar de la Capacitación"));

        lblNombreLugarCapacitacion.setText("Nombre:");

        cmbNombreLugarCapacitacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elija un Lugar ..." }));
        cmbNombreLugarCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNombreLugarCapacitacionActionPerformed(evt);
            }
        });

        lblDieccionLugar.setText("Dirección:");

        txtDireccionLugar.setEditable(false);

        btnNuevoLugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoLugar.setText("Nuevo Lugar");
        btnNuevoLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoLugarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreLugarCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbNombreLugarCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDieccionLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                    .addComponent(txtDireccionLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreLugarCapacitacion)
                    .addComponent(cmbNombreLugarCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDieccionLugar)
                    .addComponent(txtDireccionLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevoLugar))
        );

        lblNombreCapacitador.setText("Nombre del Capacitador: ");

        cmbNombreCapacitador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un Tipo de Capacitación..." }));

        btnNuevoCapacitador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoCapacitador.setText("Nuevo Capacitador");
        btnNuevoCapacitador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCapacitadorActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleados que asistirán"));

        btnAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        btnAgregarEmpleado.setText("Agregar");
        btnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpleadoActionPerformed(evt);
            }
        });

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnQuitar.setText("Quitar");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Campos, Oscar", "20/05/2010", new Boolean(true)},
                {"Castro, Raul", "10/12/2010", new Boolean(true)},
                {"Oviedo, Fernando", "-", null}
            },
            new String [] {
                "Nombre", "Vencimiento", "Realizada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEmpleados);

        tblEmpleadosQueAsistiran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Stark, Antoni"}
            },
            new String [] {
                "Lista de Empleados"
            }
        ));
        jScrollPane2.setViewportView(tblEmpleadosQueAsistiran);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(btnQuitar, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnAgregarEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuitar))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
            .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblNombreTaller, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDescripcion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                            .addComponent(lblTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                            .addComponent(txtNombreTaller, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbTipoCapacitacion, 0, 321, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNuevoTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevoCapacitador, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreTaller)
                    .addComponent(txtNombreTaller, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescripcion)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoCapacitacion)
                    .addComponent(cmbTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoTipoCapacitacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCapacitador)
                    .addComponent(cmbNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoCapacitador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHorarioActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tblHorarios.getModel();
        Object[] fila = new Object[3];
        fila[0] = "00/00/"+FechaUtil.getYear();
        fila[1] = "00:00";
        fila[2] = "00:00";
        modelo.addRow(fila);
    }//GEN-LAST:event_btnAgregarHorarioActionPerformed

    private void txtNombreTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreTallerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreTallerActionPerformed

    private void btnNuevoLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoLugarActionPerformed

        pantallaRegistrarLugarCapacitacion win = new pantallaRegistrarLugarCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setPantalla(this);
        win.setVisible(true);

    }//GEN-LAST:event_btnNuevoLugarActionPerformed

    private void btnNuevoCapacitadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCapacitadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoCapacitadorActionPerformed

    private void btnNuevoTipoCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoCapacitacionActionPerformed

        pantallaGestionarTipoCapacitacion win = new pantallaGestionarTipoCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setPantalla(this);
        win.setVisible(true);

    }//GEN-LAST:event_btnNuevoTipoCapacitacionActionPerformed

    private void menuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBorrarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tblHorarios.getModel();
        modelo.removeRow(tblHorarios.getSelectedRow());
    }//GEN-LAST:event_menuBorrarActionPerformed

    private void mostrarDomicilioLugarSeleccionado()
    {
        Tupla t = (Tupla)cmbNombreLugarCapacitacion.getSelectedItem();
        if(t!=null)
        {
            txtDireccionLugar.setText(gestor.buscarDomicilioDelLugarSeleccionado(t.getId()));
        }
    }

    private void cmbNombreLugarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreLugarCapacitacionActionPerformed

        mostrarDomicilioLugarSeleccionado();

    }//GEN-LAST:event_cmbNombreLugarCapacitacionActionPerformed

    private void cmbTipoCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoCapacitacionActionPerformed

        // ELIGIO UN TIPO DE CAPACITACION ASI QUE CARGO LOS CAPACITADORES
        this.mostrarCapacitadores();

    }//GEN-LAST:event_cmbTipoCapacitacionActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaRegistrarTallerCapacitacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarEmpleado;
    private javax.swing.JButton btnAgregarHorario;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNuevoCapacitador;
    private javax.swing.JButton btnNuevoLugar;
    private javax.swing.JButton btnNuevoTipoCapacitacion;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox cmbNombreCapacitador;
    private javax.swing.JComboBox cmbNombreLugarCapacitacion;
    private javax.swing.JComboBox cmbTipoCapacitacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDieccionLugar;
    private javax.swing.JLabel lblNombreCapacitador;
    private javax.swing.JLabel lblNombreLugarCapacitacion;
    private javax.swing.JLabel lblNombreTaller;
    private javax.swing.JLabel lblTipoCapacitacion;
    private javax.swing.JMenuItem menuBorrar;
    private javax.swing.JPopupMenu popupTablaHorarios;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTable tblEmpleadosQueAsistiran;
    private javax.swing.JTable tblHorarios;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDireccionLugar;
    private javax.swing.JTextField txtNombreTaller;
    // End of variables declaration//GEN-END:variables



    public void actualizar(int flag,boolean exito)
    {
        if(flag == FLAG_TIPO_CAPACITACION)
        {
            mostrarTiposCapacitacion();
        }
        if(flag == FLAG_LUGAR_CAPACITACION)
        {
            mostrarLugaresCapacitacion();
        }

    }

}
