/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarPedido.java
 *
 * Created on 08-may-2010, 18:53:08
 */

package vista.comer;

import com.toedter.calendar.JDateChooser;
import controlador.comer.GestorABMPedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ContactoResponsable;
import modelo.PedidoObra;
import util.FechaUtil;
import util.NTupla;
import util.TablaUtil;
import util.Tupla;
import vista.gui.IFavorito;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;
import vista.interfaces.IPantallaPedidoABM;

/**
 *
 * @author Administrador
 */
public class ABMPedidoObra extends javax.swing.JInternalFrame implements IAyuda, IPantallaPedidoABM, ICallBack,IFavorito{

    private GestorABMPedido gestor;
    private int idPedidoObra = -1;

    public ABMPedidoObra() {
        gestor = new GestorABMPedido(this);
        initComponents();
        habilitarVentana();
    }
    
    public ABMPedidoObra(int idPedidoObra){
        gestor = new GestorABMPedido(this);
        this.idPedidoObra = idPedidoObra;
        initComponents();
        habilitarVentana();
        gestor.seleccionPedido(idPedidoObra);
        actualizarListaContactosResponsables();
    }

    private void habilitarVentana(){
        mostrarEmpresasCliente();
        mostrarFormasDePago();
        mostrarRoles();
        mostrarTiposTelefono();
    }

    @Override
    public void mostrarEmpresasCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarEmpresasCliente();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        valores.addElement("");
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbEmpresa.setModel(valores);
    }

    @Override
    public void mostrarPlantasEmpresaCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarPlantasEmpresaCliente(((Tupla)cmbEmpresa.getSelectedItem()).getId());

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        valores.addElement("");
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbPlanta.setModel(valores);
    }

    private void mostrarFormasDePago() {
        ArrayList<Tupla> lista = gestor.mostrarFormasDePago();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbFormaDePago.setModel(valores);
    }

    private void mostrarRoles() {
        ArrayList<Tupla> lista = gestor.mostrarRoles();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla vacia = new Tupla(-1, "");
        valores.addElement(vacia);

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbRolCR.setModel(valores);
    }

    private void mostrarTiposTelefono() {
        ArrayList<Tupla> lista = gestor.mostrarTiposTelefono();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla vacia = new Tupla(-1, "");
        valores.addElement(vacia);

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbTipoTelefono.setModel(valores);
    }

    private boolean ValidarDatos()
    {
        boolean ban=true;
        boolean banFechas=true;
        String mensaje="Faltan ingresar/seleccionar los siguientes campos:\n";
        if(txtNombreObra.getText().equals("")){
            mensaje+="- Nombre de la Obra\n";
            ban=false;
        }
        if(cmbEmpresa.getSelectedIndex()<=0){
            mensaje+="- Empresa\n";
            ban=false;
        }
        if(cmbPlanta.getSelectedIndex()<=0){
            mensaje+="- Planta\n";
            ban=false;
        }
        if(((JDateChooser)dpfechaInicio).getDate() == null){
            mensaje+="- Fecha de Inicio\n";
            ban=false;
            banFechas=false;
        }
        if(((JDateChooser)dpfechaFin).getDate() == null){
            mensaje+="- Fecha de Fin\n";
            ban=false;
            banFechas=false;
        }
        if(txtMonto.getText().equals("")){
            mensaje+="- Monto de la Obra\n";
            ban=false;
        }
        if(cmbFormaDePago.getSelectedIndex() == -1){
            mensaje+="- Forma de Pago\n";
            ban=false;
        }
        if(txtMonto.getText().equals("")){
            mensaje+="- Presupuesto Máximo\n";
            ban=false;
        }else{
            try{
                Double.parseDouble(txtMonto.getText());
            }
            catch(Exception e){
                mensaje+="- Formato no válido Presupuesto Máximo\n";
                ban=false;
            }
        }
        if(banFechas && !FechaUtil.fechaMayorQue(dpfechaFin.getDate(),dpfechaInicio.getDate())){
            mensaje+="- La Fecha de Fin debe ser posterior a la fecha de Inicio\n";
            ban=false;
        }
        if(txtPliego.getText().equals(""))
        {
            mensaje+="- Los datos del Pliego\n";
            ban=false;
        }
        if(!ban){
            JOptionPane.showMessageDialog(this.getParent(),mensaje,"ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        }
        return ban;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreObra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtEstadoPedido = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        dpfechaInicio = new com.toedter.calendar.JDateChooser();
        btnAgregarEmpresaCliente = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dpfechaFin = new com.toedter.calendar.JDateChooser();
        cmbPlanta = new javax.swing.JComboBox();
        btnAgregarPlanta = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbFormaDePago = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtPliego = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnAgregarCR = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtNombreCR = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbRolCR = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtTelCR = new javax.swing.JTextField();
        btnQuitarCR = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCR = new javax.swing.JTable();
        btnAgregarRol = new javax.swing.JButton();
        cmbTipoTelefono = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtApellidoCR = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnDarDeBaja = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Administración de Pedidos de Obra");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Obra"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombre de la Obra: *");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Descripción:");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Estado:");

        txtEstadoPedido.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreObra)
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstadoPedido)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtEstadoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Empresa Cliente *:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Fecha de Inicio: *");

        cmbEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpresaActionPerformed(evt);
            }
        });

        btnAgregarEmpresaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarEmpresaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpresaClienteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Planta: *");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Fecha de Fin: *");

        cmbPlanta.setEnabled(false);

        btnAgregarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarPlanta.setEnabled(false);
        btnAgregarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlantaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Presupuesto Máximo ($): *");

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Forma de Pago: *");

        cmbFormaDePago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Cheque" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Datos Pliego:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dpfechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbPlanta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbFormaDePago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMonto)
                            .addComponent(dpfechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPliego)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPlanta)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(dpfechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dpfechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPliego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("General", jPanel5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto Responsable"));

        btnAgregarCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarCR.setText("Agregar Contacto");
        btnAgregarCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCRActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Rol:");

        cmbRolCR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrador", "Guardia", "Gerente", "Abogado", "Contratista" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Tel.:");

        btnQuitarCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarCR.setText("Quitar");
        btnQuitarCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCRActionPerformed(evt);
            }
        });

        tablaCR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Rol", "Teléfono", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCR.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablaCR);
        tablaCR.getColumnModel().getColumn(0).setMinWidth(160);
        tablaCR.getColumnModel().getColumn(0).setPreferredWidth(160);
        tablaCR.getColumnModel().getColumn(0).setMaxWidth(160);
        tablaCR.getColumnModel().getColumn(1).setMinWidth(160);
        tablaCR.getColumnModel().getColumn(1).setPreferredWidth(160);
        tablaCR.getColumnModel().getColumn(1).setMaxWidth(160);
        tablaCR.getColumnModel().getColumn(2).setMinWidth(160);
        tablaCR.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablaCR.getColumnModel().getColumn(2).setMaxWidth(160);
        tablaCR.getColumnModel().getColumn(3).setResizable(false);
        tablaCR.getColumnModel().getColumn(3).setPreferredWidth(0);

        btnAgregarRol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRolActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Apellido:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitarCR, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreCR, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoCR))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbRolCR, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarRol, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelCR, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtApellidoCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAgregarRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbRolCR)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel13)
                        .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTelCR)))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCR)
                    .addComponent(btnQuitarCR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contactos Responsables", jPanel6);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnDarDeBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnDarDeBaja.setText("Cancelar Pedido");
        btnDarDeBaja.setEnabled(false);
        btnDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarDeBajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnDarDeBaja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnGuardar)
                    .addComponent(btnDarDeBaja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Contactos Responsables");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(ValidarDatos()){
            gestor.nombreObra(this.txtNombreObra.getText());
            gestor.descripcionObra(this.txtDescripcion.getText());
            gestor.montoMaximo(Double.valueOf(this.txtMonto.getText()));
            Date fechaI = ((JDateChooser) dpfechaInicio).getDate();
            Date fechaF = ((JDateChooser) dpfechaFin).getDate();
            gestor.fechaInicioYFin(fechaI, fechaF);
            gestor.seleccionPlanta((Tupla)cmbPlanta.getSelectedItem());
            gestor.presupuestoMaximo(txtMonto.getText());
            gestor.formaDePago((Tupla)cmbFormaDePago.getSelectedItem());
            
            gestor.pliego(txtPliego.getText());

            // Los contactos responsables ya estan en el gestor

            int id = gestor.confirmacionRegistro(this.idPedidoObra);

            JOptionPane.showMessageDialog(this.getParent(),"<HTML>Se registró con <b>éxito</b> el pedido número : <b>"+id+"</b>","Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cmbEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpresaActionPerformed
        if(cmbEmpresa.getSelectedIndex() > 0){
            this.mostrarPlantasEmpresaCliente();
            btnAgregarPlanta.setEnabled(true);
            cmbPlanta.setEnabled(true);}
        else{
            btnAgregarPlanta.setEnabled(false);
            cmbPlanta.setEnabled(false);}
    }//GEN-LAST:event_cmbEmpresaActionPerformed

    private void btnAgregarEmpresaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpresaClienteActionPerformed
        gestor.llamarCURegistrarNuevaEmpresaCliente();
    }//GEN-LAST:event_btnAgregarEmpresaClienteActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAgregarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPlantaActionPerformed
        gestor.llamarCURegistrarNuevaPlanta(((Tupla)cmbEmpresa.getSelectedItem()));
    }//GEN-LAST:event_btnAgregarPlantaActionPerformed

    private void btnAgregarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCRActionPerformed
        String msj="<HTML>Han ocurrido los siguientes errores al momento de la carga:\n";
        boolean b=true;
        if(this.txtNombreCR.getText().equals("")){
            msj += "- El <b>Nombre</b> de Contacto no puede ser vacio\n";
            b = false;
        }
        if(this.txtApellidoCR.getText().equals("")){
            msj += "- El <b>Apellido</b> de Contacto no puede ser vacio\n";
            b = false;
        }
        if(this.cmbRolCR.getSelectedIndex()== 0){
            msj += "- No ha seleccionado un <b>Rol</b> para el contacto\n";
            b = false;
        }
        if(this.cmbTipoTelefono.getSelectedIndex()== 0){
            msj += "- No ha seleccionado un <b>Tipo de Teléfono</b>\n";
            b = false;
        }
        if(this.txtTelCR.getText().equals("")){
            msj += "- <b>Teléfono</b> vacio\n";
            b = false;
        }
        if(b){
            Tupla tRol = (Tupla)this.cmbRolCR.getItemAt(this.cmbRolCR.getSelectedIndex());
            Tupla tTipo = (Tupla)this.cmbTipoTelefono.getItemAt(this.cmbTipoTelefono.getSelectedIndex());
            Object o = gestor.agregarContactoResponsable(this.txtNombreCR.getText(),this.txtApellidoCR.getText(), tRol.getId(),tTipo.getId(),txtTelCR.getText());
            if(o!=null){
                DefaultTableModel dtm = (DefaultTableModel) tablaCR.getModel();
                Object []datos = {this.txtNombreCR.getText() , tRol.getNombre(),tTipo.getNombre()+": "+txtTelCR.getText(), o};
                dtm.addRow(datos);
                this.txtNombreCR.setText("");
                this.txtApellidoCR.setText("");
                this.txtTelCR.setText("");
                this.cmbRolCR.setSelectedIndex(0);
                this.cmbTipoTelefono.setSelectedIndex(0);
                this.actualizarListaContactosResponsables();
            }
        }else{
            JOptionPane.showMessageDialog(this, msj,"Alerta",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarCRActionPerformed

    private void btnQuitarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCRActionPerformed
        if(tablaCR.getSelectedRow()!= -1){
            DefaultTableModel dtm = (DefaultTableModel)tablaCR.getModel();
            ContactoResponsable o = (ContactoResponsable) dtm.getValueAt(tablaCR.getSelectedRow(), 0);
            if(gestor.quitarContactoResponsable(o))
            {
                dtm.removeRow(tablaCR.getSelectedRow());
                this.actualizarListaContactosResponsables();
            }
            else
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar\n quitar un contacto del pedido","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar un contacto","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnQuitarCRActionPerformed

    private void btnAgregarRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRolActionPerformed
        String n = JOptionPane.showInputDialog(this, "Ingrese el Rol de Contacto Responsable que desea agregar: ", "Agregar Rol", JOptionPane.OK_CANCEL_OPTION);
        if(n!=null && !n.equals(""));
        {
            gestor.agregarRolCR(n);
            this.mostrarRoles();
        }
    }//GEN-LAST:event_btnAgregarRolActionPerformed

    private void btnDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarDeBajaActionPerformed
        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Está seguro que desea cancelar el pedido?","Dar de Baja el Pedido",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            
            String msg = "<HTML><b>Dar de Baja</b> un pedido anulará todas las:<br>"
                    + "- Cotizaciones asociadas<br>"
                    + "- Planificación de la Obra<br>"
                    + "- Ejecución de la Obra<br>"
                    + "y estos cambios <b>no pueden revertirse</b>.<br>"
                    + "¿Está seguro que desea dar de Baja el Pedido?";
            
            // Otro mensaje (Esta opcion es demoledora)
            int seleccion = JOptionPane.showOptionDialog(
                this, // Componente padre
                msg, //Mensaje
                "Atencion!", // Título
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,    // null para icono por defecto.
                new Object[] { "Si, entiendo los riesgos, Dar de Baja", "Cancelar"},    // null para YES, NO y CANCEL
                "Si");

            if (seleccion != -1)
            {
               if((seleccion + 1)==1)
               {
                  if(gestor.cancelarPedido(idPedidoObra)){
                    JOptionPane.showMessageDialog(this, "El Pedido Nro. "+idPedidoObra+" fue dado de Baja con éxito.", "Pedido Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                  }else{
                    JOptionPane.showMessageDialog(this, "Se produjo un error al intentar dar de baja el pedido\nIntentelo nuevamente más tarde!", "Error!", JOptionPane.ERROR_MESSAGE);
                  }
               }
            }
        }
    }//GEN-LAST:event_btnDarDeBajaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCR;
    private javax.swing.JButton btnAgregarEmpresaCliente;
    private javax.swing.JButton btnAgregarPlanta;
    private javax.swing.JButton btnAgregarRol;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnDarDeBaja;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitarCR;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbFormaDePago;
    private javax.swing.JComboBox cmbPlanta;
    private javax.swing.JComboBox cmbRolCR;
    private javax.swing.JComboBox cmbTipoTelefono;
    private com.toedter.calendar.JDateChooser dpfechaFin;
    private com.toedter.calendar.JDateChooser dpfechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaCR;
    private javax.swing.JTextField txtApellidoCR;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEstadoPedido;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombreCR;
    private javax.swing.JTextField txtNombreObra;
    private javax.swing.JTextField txtPliego;
    private javax.swing.JTextField txtTelCR;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isFavorito() {
        return true;
    }

    @Override
    public String getIconoFavorito() 
    {
        return "/res/iconos/var/16x16/add.png";
    }
    
    private void seleccionarEnCombo(JComboBox cb, int id) {
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel)cb.getModel();
        for(int i = 0 ; i < dcbm.getSize() ; i++)
        {
            if(dcbm.getElementAt(i) instanceof Tupla)
            {
                Tupla tupla = (Tupla) dcbm.getElementAt(i);
                if(tupla.getId() == id)
                {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @Override
    public void setNumeroPedido(String nro) {
        this.idPedidoObra = Integer.valueOf(nro);
    }

    @Override
    public void setNombreObra(String nombre) {
        this.txtNombreObra.setText(nombre);
    }

    @Override
    public void setDescripcionObra(String desc) {
        this.txtDescripcion.setText(desc);
    }

    @Override
    public void setEmpresaCliente(int id) {
        this.seleccionarEnCombo(cmbEmpresa, id);
    }

    @Override
    public void setPlanta(int id) {
        this.seleccionarEnCombo(cmbPlanta, id);
    }

    @Override
    public void setFechaInicio(Date fInicio) {
        this.dpfechaInicio.setDate(fInicio);
    }

    @Override
    public void setFechaFin(Date fFin) {
        this.dpfechaFin.setDate(fFin);
    }

    @Override
    public void setMontoPedido(String monto) {
        this.txtMonto.setText(monto);
    }

    @Override
    public void setFechaLEP(Date fLEP) {
        
    }

    @Override
    public void setFechaLVP(Date fLVP) {
        
    }

    @Override
    public void setPliegosPedido(String pliegos) {
        this.txtPliego.setText(pliegos);
    }

    @Override
    public void setPlanosPedido(String pedidos) {
        
    }

    @Override
    public void setEstadoPedidoObra(String nombre) {
        this.txtEstadoPedido.setText(nombre);
        if(nombre.equals(PedidoObra.ESTADO_CANCELADO))
        {
            txtNombreObra.setEnabled(false);
            txtDescripcion.setEnabled(false);
            cmbEmpresa.setEnabled(false);
            btnAgregarEmpresaCliente.setEnabled(false);
            cmbPlanta.setEnabled(false);
            btnAgregarPlanta.setEnabled(false);
            dpfechaInicio.setEnabled(false);
            dpfechaFin.setEnabled(false);
            txtMonto.setEnabled(false);
            cmbFormaDePago.setEnabled(false);
            txtPliego.setEnabled(false);
            txtNombreCR.setEnabled(false);
            txtApellidoCR.setEnabled(false);
            cmbRolCR.setEnabled(false);
            btnAgregarRol.setEnabled(false);
            cmbTipoTelefono.setEnabled(false);
            txtTelCR.setEnabled(false);
            btnAgregarCR.setEnabled(false);
            btnQuitarCR.setEnabled(false);
            tablaCR.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnDarDeBaja.setEnabled(false);
        }
        else
        {
            btnDarDeBaja.setEnabled(true);
        }
    }

    @Override
    public void actualizarListaContactosResponsables() {
        Iterator<NTupla> itCR = this.gestor.mostrarContactosResponsables().iterator();
        DefaultTableModel dtm = (DefaultTableModel)this.tablaCR.getModel();
        TablaUtil.vaciarDefaultTableModel(dtm);
        while(itCR.hasNext())
        {
            NTupla nTupla = itCR.next();
            Object data[] = (Object[])nTupla.getData();
            ContactoResponsable contacto = (ContactoResponsable) data[2];
            Object fila[] = {contacto,data[0],data[1]};
            dtm.addRow(fila);
        }
    }

    @Override
    public void setFormaDePago(int id) {
        this.seleccionarEnCombo(cmbFormaDePago, id);
    }

    @Override
    public void setContactoResponsable(int idContacto) {
        
    }

    @Override
    public String getTituloAyuda() {
        return "";
    }

    @Override
    public String getResumenAyuda() {
        return "";
    }

    @Override
    public int getIdAyuda() {
        return 0;
    }

    @Override
    public void actualizar(int flag, boolean exito) {
        
    }
}
