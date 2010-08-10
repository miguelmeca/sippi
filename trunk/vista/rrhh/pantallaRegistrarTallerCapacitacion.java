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

import com.toedter.calendar.JDateChooser;
import controlador.rrhh.GestorRegistrarTallerDeCapacitacion;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import modelo.TallerCapacitacion;
import util.FechaUtil;
import util.NTupla;
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
        initTablaEmpleado();
        initTablaEmpleadoAsiste();
        mostrarTiposCapacitacion();
        mostrarLugaresCapacitacion();

        txtFechaCurso.setText(FechaUtil.getFechaActual());

        initHorario();
    }

    private void initHorario()
    {
        txtFechaCurso.setText(FechaUtil.getFechaActual());
        txtFinH.setText("10");
        txtFinM.setText("00");
        txtIniH.setText("09");
        txtIniM.setText("00");
    }

    private void initTablaEmpleadoAsiste()
    {

        DefaultTableModel modelo = new DefaultTableModel();

        Object[] nombreColumnas = {"Nombre Completo"};
        for (int i = 0; i < nombreColumnas.length; i++)
        {
           modelo.addColumn(nombreColumnas[i]);
        }

        tblEmpleadosQueAsistiran.setModel(modelo);
    }

    private void initTablaEmpleado()
    {
        
        DefaultTableModel modelo = new DefaultTableModel();

        Object[] nombreColumnas = {"Nombre","Vencimiento","Realizada"};
        for (int i = 0; i < nombreColumnas.length; i++)
        {
           modelo.addColumn(nombreColumnas[i]);
        }

        tblEmpleados.setModel(modelo);
    }

    private void mostrarEmpleados()
    {
        initTablaEmpleado();
        DefaultTableModel modelo =  (DefaultTableModel) tblEmpleados.getModel();

        // Busco el tipo de capacitacion seleccionada
        Tupla tp = (Tupla)cmbTipoCapacitacion.getSelectedItem();
        // Si es cero es que no selecciono xq no hay
        if(tp.getId()!=0)
        {
            ArrayList<NTupla> lista = gestor.buscarEmpleadosActivosConFechaVencimiento(tp.getId());
            Iterator it = lista.iterator();
            while (it.hasNext())
            {
                Object[] fila = new Object[3];
                NTupla obj = (NTupla)it.next();
                System.out.println("$ "+obj.getId()+" - "+obj.getNombre());
                fila[0] = obj;
                String[] data = (String[])obj.getData();
                
                fila[1] = data[0];

                if(data[1].equals("TRUE"))
                { fila[2] = "Si";}
                else
                { fila[2] = "No"; }

                modelo.addRow(fila);
            }
        }
    }

    public void mostrarTiposCapacitacion()
    {
        ArrayList<Tupla> lista = gestor.buscarTiposDeCapacitacion();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No hay Tipos de Capacitación"));
        }

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

        if(lista.size()==0)
        {
             valores.addElement(new Tupla(0,"No hay Lugares Cargados"));
        }
        
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

            if(lista.size()==0)
            {
                valores.addElement(new Tupla(0,"No hay Capacitadores para éste Tipo de Capacitación"));
            }
            
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
        jPanel5 = new javax.swing.JPanel();
        txtFinH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnAgregarHorario = new javax.swing.JButton();
        txtFinM = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtIniH = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtFechaCurso = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIniM = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
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
        jPanel1 = new javax.swing.JPanel();
        btnAgregarEmpleado = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleadosQueAsistiran = new javax.swing.JTable();
        btnNuevoCapacitador = new javax.swing.JButton();

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
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Registrar el nuevo Taller de Capacitación");
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

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtFinH.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinH.setText("11");

        jLabel5.setText(":");

        btnAgregarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnAgregarHorario.setText("Agregar");
        btnAgregarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHorarioActionPerformed(evt);
            }
        });

        txtFinM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinM.setText("00");

        jLabel2.setText("Hora de Inicio:");

        txtIniH.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIniH.setText("10");

        jLabel1.setText("Fecha de dictado:");

        txtFechaCurso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaCurso.setText("__ / __  / ____");
        txtFechaCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaCursoActionPerformed(evt);
            }
        });

        jLabel3.setText(":");

        jLabel4.setText("Hora de Fin:");

        txtIniM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIniM.setText("00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregarHorario, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtFechaCurso, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(txtIniH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtIniM))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtFinH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFinM, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFechaCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIniH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtIniM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFinH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtFinM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(btnAgregarHorario)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lblDescripcion.setText("Descripción:");

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
                    .addComponent(btnNuevoLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(txtDireccionLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
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
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnQuitar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnQuitar)
                .addGap(10, 10, 10))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
            .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
        );

        btnNuevoCapacitador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoCapacitador.setText("Nuevo Capacitador");
        btnNuevoCapacitador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCapacitadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(lblTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnNuevoTipoCapacitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(63, 63, 63))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(lblNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(cmbNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnNuevoCapacitador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoCapacitacion)
                    .addComponent(cmbTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoTipoCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescripcion)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCapacitador)
                    .addComponent(cmbNombreCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed

        if(tblEmpleados.getSelectedRow()!=-1)
        {
            // Agrego la fila
            DefaultTableModel modelo = (DefaultTableModel) tblEmpleadosQueAsistiran.getModel();
            Object[] item = new Object[1];

            DefaultTableModel modeloEm = (DefaultTableModel) tblEmpleados.getModel();
            NTupla tp = (NTupla) modeloEm.getValueAt(tblEmpleados.getSelectedRow(),0);
            item[0] = tp;


            // Veo que no hay duplicados
            boolean existe = false;
            for (int i = 0; i < modelo.getRowCount(); i++)
            {
                NTupla tpaux = (NTupla) modelo.getValueAt(i,0);
                if(tpaux.getId() == tp.getId())
                {
                    existe = true;
                }
            }

            if(!existe)
            {
                modelo.addRow(item);
            }
            else
            {
                JOptionPane.showMessageDialog(this.getParent(),"El empleado ya está asignado a este Taller","Cuidado",JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        
        boolean valido = true;
        
        // Valido que hayan elegido un tipo de capacitacion
        Tupla tp = (Tupla)cmbTipoCapacitacion.getSelectedItem();
        if(tp.getId()==0)
        {
             valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Seleccione un Tipo de Capacitación","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }

        // La descripción no puede estar vacia
        if(txtDescripcion.getText().isEmpty())
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Ingrese una descripción para el Taller","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }

        // TIENE QUE ELEGIR UN CAPACITADOR
        Tupla tpc = (Tupla)cmbNombreCapacitador.getSelectedItem();
        if(tp.getId()==0)
        {
             valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Seleccione el Capacitador que dicatará el curso","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }

        // TODO: VALIDAR LOS HORARIOS Y LAS FECHAS ...

        if(tblHorarios.getRowCount()==0)
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Ingrese al menos un horario para realizar el Taller","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //TODO: Ver que ho haya fechas y horarios repetidos
        }
        if(tblEmpleadosQueAsistiran.getRowCount()==0)
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Ingrese al menos un Empleado para que asista al Taller","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }
        if(cmbNombreLugarCapacitacion.getSelectedItem()==null)
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Seleccione el Lugar donde se dictará el Taller","Faltan Datos Requeridos",JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(valido)
        {
            confirmarTaller();
        }
        

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHorarioActionPerformed

        DefaultTableModel modelo = (DefaultTableModel) tblHorarios.getModel();
        Object[] fila = new Object[3];
        fila[0] = txtFechaCurso.getText();
        fila[1] = txtIniH.getText()+":"+txtIniM.getText();
        fila[2] = txtFinH.getText()+":"+txtFinM.getText();
        modelo.addRow(fila);

        initHorario();

//        DefaultTableModel modelo = (DefaultTableModel) tblHorarios.getModel();
//        Object[] fila = new Object[3];
//        fila[0] = "00/00/"+FechaUtil.getYear();
//        fila[1] = "00:00";
//        fila[2] = "00:00";
//        modelo.addRow(fila);

//        pantallaRegistrarTallerCapacitacion_horarios horario = new pantallaRegistrarTallerCapacitacion_horarios();
//        SwingPanel.getInstance().addWindow(horario);
//        horario.setVisible(true);

    }//GEN-LAST:event_btnAgregarHorarioActionPerformed

    private void btnNuevoLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoLugarActionPerformed

        pantallaRegistrarLugarCapacitacion win = new pantallaRegistrarLugarCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setPantalla(this);
        win.setVisible(true);

    }//GEN-LAST:event_btnNuevoLugarActionPerformed

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
            if(t.getId()!=0)
            {
                txtDireccionLugar.setText(gestor.buscarDomicilioDelLugarSeleccionado(t.getId()));
            }
        }
    }

    private void cmbNombreLugarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreLugarCapacitacionActionPerformed

        mostrarDomicilioLugarSeleccionado();

    }//GEN-LAST:event_cmbNombreLugarCapacitacionActionPerformed

    private void cmbTipoCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoCapacitacionActionPerformed

        // ELIGIO UN TIPO DE CAPACITACION ASI QUE CARGO LOS CAPACITADORES
        mostrarCapacitadores();
        mostrarEmpleados();

    }//GEN-LAST:event_cmbTipoCapacitacionActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed

        if((tblEmpleadosQueAsistiran.getSelectedRowCount())==1)
        {
            DefaultTableModel modelo = (DefaultTableModel) tblEmpleadosQueAsistiran.getModel();
            modelo.removeRow(tblEmpleadosQueAsistiran.getSelectedRow());
        }

    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnNuevoCapacitadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCapacitadorActionPerformed

        pantallaRegistrarCapacitador prc = new pantallaRegistrarCapacitador();
        SwingPanel.getInstance().addWindow(prc);
        prc.setVisible(true); 

    }//GEN-LAST:event_btnNuevoCapacitadorActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtFechaCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaCursoActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDieccionLugar;
    private javax.swing.JLabel lblNombreCapacitador;
    private javax.swing.JLabel lblNombreLugarCapacitacion;
    private javax.swing.JLabel lblTipoCapacitacion;
    private javax.swing.JMenuItem menuBorrar;
    private javax.swing.JPopupMenu popupTablaHorarios;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTable tblEmpleadosQueAsistiran;
    private javax.swing.JTable tblHorarios;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDireccionLugar;
    private javax.swing.JFormattedTextField txtFechaCurso;
    private javax.swing.JTextField txtFinH;
    private javax.swing.JTextField txtFinM;
    private javax.swing.JTextField txtIniH;
    private javax.swing.JTextField txtIniM;
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

    public void confirmarTaller()
    {
        // BUENO BUENO, AHORA TENGO QUE GUARDAR TODA LA BATATOLA DE DATOS
        // 1) Paso la descripcion del taller
        gestor.descripcionTaller(txtDescripcion.getText());
        // 2) Paso el Tipo de Capacitacion
        gestor.tipoCapacitacion((Tupla)cmbTipoCapacitacion.getSelectedItem());
        // 3) Paso el Capacitador
        gestor.capacitador((Tupla)cmbNombreCapacitador.getSelectedItem());
        // 4) Paso el lugar de la capacitacion
        gestor.lugarCapacitacion((Tupla) cmbNombreLugarCapacitacion.getSelectedItem());
        // 5) Paso el detalle de los horarios
            ArrayList<String[]> lista = new ArrayList<String[]>();
            DefaultTableModel modelo = (DefaultTableModel)tblHorarios.getModel();
            for (int i = 0; i < tblHorarios.getRowCount(); i++)
            {
                String[] fila = new String[3];
                fila[0] = (String) modelo.getValueAt(i, 0);
                fila[1] = (String) modelo.getValueAt(i, 1);
                fila[2] = (String) modelo.getValueAt(i, 2);
                lista.add(fila);
            }
            gestor.horariosYFechas(lista);
        // 6) Paso el detalle de los que van a asistir al taller
            ArrayList<NTupla> listaEmpleados = new ArrayList<NTupla>();
            DefaultTableModel modelox = (DefaultTableModel)tblEmpleadosQueAsistiran.getModel();
            for (int i = 0; i < tblEmpleadosQueAsistiran.getRowCount(); i++)
            {
                NTupla taux = (NTupla)modelox.getValueAt(i,0);
                listaEmpleados.add(taux);
            }
            gestor.empleadosQueAsistiran(listaEmpleados);

        // 7) Creo que ya estaría todo ... probemos

            if(gestor.confirmacionRegistro())
            {
                // Se guardó en orden
                JOptionPane.showMessageDialog(this.getParent(),"Se registró correctamente el Taller de Capacitación","Carga Exitosa",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                // Error al guardar
                JOptionPane.showMessageDialog(this.getParent(),"Se detecto un error al cargar el Taller de Capacitación","Error en la Carga",JOptionPane.INFORMATION_MESSAGE);
            }
    }

}
