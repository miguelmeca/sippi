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
import controlador.comer.GestorRegistrarPedido;
import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.EmpresaCliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;
import vista.interfaces.IPantallaPedidoABM;

/**
 *
 * @author Administrador
 */
public class pantallaRegistrarPedido extends javax.swing.JInternalFrame implements IAyuda, IPantallaPedidoABM, ICallBack{

    private GestorRegistrarPedido gestor;
    private pantallaBuscarPedido pBuscar;

    /** Creates new form frmRegistrarPedido */
    public pantallaRegistrarPedido(pantallaBuscarPedido p) {
        this.pBuscar = p;
        gestor = new GestorRegistrarPedido(this);
        initComponents();
        habilitarVentana();
        txtNroPedido.setText(String.valueOf(gestor.generarNumeroPedido()));
    }

    public pantallaRegistrarPedido() {
        this.pBuscar = null;
        gestor = new GestorRegistrarPedido(this);
        initComponents();
        habilitarVentana();
        txtNroPedido.setText(String.valueOf(gestor.generarNumeroPedido()));
    }

    private void habilitarVentana(){
        mostrarEmpresasCliente();
        mostrarFormasDePago();
        mostrarRoles();
        mostrarTiposTelefono();
    }

    public void mostrarEmpresasCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarEmpresasCliente();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbEmpresa.setModel(valores);
    }

    public void mostrarPlantasEmpresaCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarPlantasEmpresaCliente(((Tupla)cmbEmpresa.getSelectedItem()).getId());

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
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

    private void registrarPedido(){}

    private boolean ValidarDatos()
    {
        boolean ban=true;
        String mensaje="Faltan ingresar/seleccionar los siguientes campos:\n";
        if(txtNombreObra.getText().equals("")){
            mensaje+="- Nombre de la Obra\n";
            ban=false;
        }
        if(cmbEmpresa.getSelectedIndex() == -1){
            mensaje+="- Empresa\n";
            ban=false;
        }
        if(cmbPlanta.getSelectedIndex() == -1){
            mensaje+="- Planta\n";
            ban=false;
        }
        if(((JDateChooser)cmbfechaInicio).getDate() == null){
            mensaje+="- Fecha de Inicio\n";
            ban=false;
        }
        if(((JDateChooser)cmbfechaFin).getDate() == null){
            mensaje+="- Fecha de Fin\n";
            ban=false;
        }
        if(txtMonto.getText().equals("")){
            mensaje+="- Monto de la Obra\n";
            ban=false;
        }
        if(cmbFormaDePago.getSelectedIndex() == -1){
            mensaje+="- Forma de Pago\n";
            ban=false;
        }
        try{
            Double.parseDouble(txtMonto.getText());
        }
        catch(Exception e){
            mensaje+="- Formato no válido Presupuesto Máximo\n";
            ban=false;
        }
        if(!ban){
            JOptionPane.showMessageDialog(this.getParent(),mensaje,"ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        }
        return ban;
    }


//    private void actualizarListaContactosResponsables() {
//        ArrayList<NTupla> cr = gestor.mostrarContactosResponsables();
//        DefaultTableModel dtm = (DefaultTableModel) this.tablaCR.getModel();
//        for(int i=0; i< dtm.getRowCount(); i++) {dtm.removeRow(0);} // Limpia la tabla
//        Iterator it = cr.iterator();
//        while(it.hasNext()){
//            NTupla nt = (NTupla)it.next();
//            Object datos[] = { nt.getNombre() , ((Object[])nt.getData())[0] , ((Object[])nt.getData())[1] , ((Object[])nt.getData())[2]};
//            dtm.addRow(datos);
//        }
//    }

    public void setNumeroPedido(String nro) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNombreObra(String nombre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDescripcionObra(String desc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEmpresaCliente(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPlanta(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFechaInicio(Date fInicio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFechaFin(Date fFin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMontoPedido(String monto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFechaLEP(Date fLEP) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFechaLVP(Date fLVP) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPliegosPedido(String pliegos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPlanosPedido(String pedidos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContactoResponsable(int idContacto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEstadoPedidoObra(String nombre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNroPedido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreObra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        cmbfechaInicio = new com.toedter.calendar.JDateChooser();
        btnAgregarEmpresaCliente = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbfechaFin = new com.toedter.calendar.JDateChooser();
        cmbPlanta = new javax.swing.JComboBox();
        btnAgregarPlanta = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbFormaDePago = new javax.swing.JComboBox();
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

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar Pedido");

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnConfirmar.setText("Aceptar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Obra"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Número de Pedido:");

        txtNroPedido.setEditable(false);
        txtNroPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Nombre de la Obra:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Descripción:");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addComponent(txtNombreObra, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Empresa Cliente:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Fecha de Inicio:");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Planta:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Fecha de Fin:");

        cmbPlanta.setEnabled(false);

        btnAgregarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarPlanta.setEnabled(false);
        btnAgregarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlantaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Presupuesto Máximo ($):");

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Forma de Pago:");

        cmbFormaDePago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Cheque" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addGap(2, 2, 2)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbfechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel5)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbFormaDePago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cmbPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbfechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cmbfechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbfechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Rol:");

        cmbRolCR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrador", "Guardia", "Gerente", "Abogado", "Contratista" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitarCR, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreCR, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                        .addComponent(txtTelCR, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAgregarRol, 0, 0, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(213, 213, 213))
        );

        jTabbedPane1.addTab("Contactos Responsables", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(338, Short.MAX_VALUE)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Contactos Responsables");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if(ValidarDatos()){
            gestor.nombreObra(this.txtNombreObra.getText());
            gestor.descripcionObra(this.txtDescripcion.getText());
            gestor.montoMaximo(Long.valueOf(this.txtMonto.getText()));
            Date fechaI = ((JDateChooser) cmbfechaInicio).getDate();
            Date fechaF = ((JDateChooser) cmbfechaFin).getDate();
            gestor.fechaInicioYFin(fechaI, fechaF);
            gestor.seleccionPlanta((Tupla)cmbPlanta.getSelectedItem());
            gestor.presupuestoMaximo(txtMonto.getText());
            gestor.formaDePago((Tupla)cmbFormaDePago.getSelectedItem());

            // Los contactos responsables ya están en el gestor

            int id = gestor.confirmacionRegistro(0);

            JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito el pedido número "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(pBuscar != null){
                pBuscar.actualizar(1, true);
            }
            this.dispose();
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void cmbEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpresaActionPerformed
        this.mostrarPlantasEmpresaCliente();
        if(cmbEmpresa.getSelectedIndex() != -1){
            btnAgregarPlanta.setEnabled(true);
            cmbPlanta.setEnabled(true);}
        else{
            btnAgregarPlanta.setEnabled(false);
            cmbPlanta.setEnabled(false);}
    }//GEN-LAST:event_cmbEmpresaActionPerformed

    private void btnAgregarEmpresaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpresaClienteActionPerformed
        gestor.llamarCURegistrarNuevaEmpresaCliente();
    }//GEN-LAST:event_btnAgregarEmpresaClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPlantaActionPerformed
        gestor.llamarCURegistrarNuevaPlanta(((Tupla)cmbEmpresa.getSelectedItem()));
    }//GEN-LAST:event_btnAgregarPlantaActionPerformed

    private void btnAgregarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCRActionPerformed
        String msj="Han ocurrido los siguientes errores al momento de la carga:\n";
        boolean b=true;
        if(this.txtNombreCR.getText().equals("")){
            msj += "- Nombre de Contacto vacío\n";
            b = false;
        }
        if(this.cmbRolCR.getSelectedIndex()== 0){
            msj += "- No ha seleccionado un Rol para el contacto\n";
            b = false;
        }
        if(this.cmbTipoTelefono.getSelectedIndex()== 0){
            msj += "- No ha seleccionado un Tipo de Teléfono\n";
            b = false;
        }
        if(this.txtTelCR.getText().equals("")){
            msj += "- Teléfono vacío\n";
            b = false;
        }
        if(b){
            Tupla tRol = (Tupla)this.cmbRolCR.getItemAt(this.cmbRolCR.getSelectedIndex());
            Tupla tTipo = (Tupla)this.cmbTipoTelefono.getItemAt(this.cmbTipoTelefono.getSelectedIndex());
            Object o = gestor.agregarContactoResponsable(this.txtNombreCR.getText(),tRol.getId(),tTipo.getId(),txtTelCR.getText());
            if(o!=null){
                DefaultTableModel dtm = (DefaultTableModel) tablaCR.getModel();
                Object []datos = {this.txtNombreCR.getText() , tRol.getNombre(),tTipo.getNombre()+": "+txtTelCR.getText(), o};
                dtm.addRow(datos);
                this.txtNombreCR.setText("");
                this.txtTelCR.setText("");
                this.cmbRolCR.setSelectedIndex(0);
                this.cmbTipoTelefono.setSelectedIndex(0);
//              this.actualizarListaContactosResponsables();
            }
        }else{
            JOptionPane.showMessageDialog(this, msj,"Alerta",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarCRActionPerformed

    private void btnQuitarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCRActionPerformed
        if(tablaCR.getSelectedRow()!= -1){
            DefaultTableModel dtm = (DefaultTableModel)tablaCR.getModel();
            Object o = dtm.getValueAt(tablaCR.getSelectedRow(), 3);
            if(gestor.quitarContactoResponsable(o))
                dtm.removeRow(tablaCR.getSelectedRow());
//              this.actualizarListaContactosResponsables();
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCR;
    private javax.swing.JButton btnAgregarEmpresaCliente;
    private javax.swing.JButton btnAgregarPlanta;
    private javax.swing.JButton btnAgregarRol;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnQuitarCR;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbFormaDePago;
    private javax.swing.JComboBox cmbPlanta;
    private javax.swing.JComboBox cmbRolCR;
    private javax.swing.JComboBox cmbTipoTelefono;
    private com.toedter.calendar.JDateChooser cmbfechaFin;
    private com.toedter.calendar.JDateChooser cmbfechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaCR;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombreCR;
    private javax.swing.JTextField txtNombreObra;
    private javax.swing.JTextField txtNroPedido;
    private javax.swing.JTextField txtTelCR;
    // End of variables declaration//GEN-END:variables

    public String getTituloAyuda() {
        return "Opción: Nuevo Pedido";
    }

    public String getResumenAyuda() {
        return "Ingrese los datos del Pedido a cargar.";
    }

    public int getIdAyuda() {
        return 0;
    }

    public void actualizar(int flag, boolean exito) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
