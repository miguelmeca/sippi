/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaModificarPedido.java
 *
 * Created on 10/08/2010, 14:06:38
 */

package vista.comer;

import com.toedter.calendar.JDateChooser;
import controlador.comer.GestorRegistrarPedido;
import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.PedidoObra;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;
import vista.interfaces.IPantallaPedidoABM;

/**
 *
 * @author Emmanuel
 */
@Deprecated
public final class pantallaModificarPedido extends javax.swing.JInternalFrame implements IPantallaPedidoABM, IAyuda, ICallBack {
    private GestorRegistrarPedido gestor;
    private int idPedido;
    private pantallaBuscarPedido pBuscar;

    /** Creates new form pantallaModificarPedido */
    public pantallaModificarPedido(pantallaBuscarPedido p) {
        this.pBuscar = p;
        this.idPedido = p.getIdPedidoSeleccionado();
        gestor = new GestorRegistrarPedido(this);
        initComponents();
        habilitarVentana();
    }

    private void habilitarVentana()
    {
        mostrarEmpresasCliente();
        mostrarContactos();
        gestor.seleccionPedido(this.idPedido);
    }

    public void mostrarEmpresasCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarEmpresasCliente();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        //int idEmpresaCliente = gestor.buscarEmpresaCliente();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
            //if (tu.getId() == idEmpresaCliente)
            //    valores.setSelectedItem(tu);
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
            if (tu.getId() == gestor.getIdPlantaPedido())
                valores.setSelectedItem(tu);
        }
        cmbPlanta.setModel(valores);
    }

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
        if(((JDateChooser)cmbLEP).getDate() == null){
            mensaje+="- Fecha Límite de Entrega de Presupuesto\n";
            ban=false;
        }
        if(((JDateChooser)cmbLVP).getDate() == null){
            mensaje+="- Fecha Límite de Validez del Presupuesto\n";
            ban=false;
        }
        if(!ban){
            JOptionPane.showMessageDialog(this.getParent(),mensaje,"ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        }
        return ban;
    }

    private void mostrarContactos()
    {
        Tupla noAsigna = new Tupla(0,"Ninguno");

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        valores.addElement(noAsigna);

        gestorBDvarios gBD = new gestorBDvarios();

        ArrayList<Tupla> lista = gBD.mostrarContactos();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbContactos.setModel(valores);
    }

    public void setNumeroPedido(String nro) {
        this.txtNroPedido.setText(nro);
    }

    public void setNombreObra(String nombre) {
        this.txtNombreObra.setText(nombre);
    }

    public void setDescripcionObra(String desc) {
        this.txtDescripcion.setText(desc);
    }

    public void setEmpresaCliente(int id) {
        //for(cmbEmpresa.:)
        Tupla t=null;
        for(int i=0;i<cmbEmpresa.getModel().getSize();i++){
            t = (Tupla)cmbEmpresa.getModel().getElementAt(i);
            if(id==t.getId()){
                cmbEmpresa.setSelectedItem(t);
                break;
            }
        }
        mostrarPlantasEmpresaCliente();
    }

    public void setPlanta(int id) {
        Tupla t=null;
        for(int i=0;i<cmbPlanta.getModel().getSize();i++){
            t = (Tupla)cmbPlanta.getModel().getElementAt(i);
            if(id==t.getId()){
                cmbPlanta.setSelectedItem(t);
                break;
            }
        }
    }

    public void setFechaInicio(Date fInicio) {
        this.cmbfechaInicio.setDate(fInicio);
    }

    public void setFechaFin(Date fFin) {
        this.cmbfechaFin.setDate(fFin);
    }

    public void setFechaLEP(Date fLEP) {
        this.cmbLEP.setDate(fLEP);
    }

    public void setMontoPedido(String monto) {
        this.txtMonto.setText(monto);
    }

    public void setFechaLVP(Date fLVP) {
        this.cmbLVP.setDate(fLVP);
    }

    public void setContactoResponsable(int idContacto) {
        Tupla t=null;
        for(int i=0;i<cmbContactos.getModel().getSize();i++){
            t = (Tupla)cmbContactos.getModel().getElementAt(i);
            if(idContacto==t.getId()){
                cmbContactos.setSelectedItem(t);
                break;
            }
        }
    }

    public void setEstadoPedidoObra(String nombre) {
        this.txtEstadoPedido.setText(nombre);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbPlanta = new javax.swing.JComboBox();
        btnAgregarPlanta = new javax.swing.JButton();
        txtNroPedido = new javax.swing.JTextField();
        btnAgregarEmpresaCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtNombreObra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbfechaInicio = new com.toedter.calendar.JDateChooser();
        cmbfechaFin = new com.toedter.calendar.JDateChooser();
        cmbLEP = new com.toedter.calendar.JDateChooser();
        cmbLVP = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        btnAgregarCR = new javax.swing.JButton();
        cmbContactos = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        txtEstadoPedido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Fecha Límite de Entrega del Presupuesto:");

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnConfirmar.setText("Aceptar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Presupuesto Máximo ($):");

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Fecha Límite de Validez del Presupuesto:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Fecha de Inicio:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Fecha de Fin:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Planta:");

        cmbPlanta.setEnabled(false);
        cmbPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPlantaActionPerformed(evt);
            }
        });

        btnAgregarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarPlanta.setEnabled(false);
        btnAgregarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlantaActionPerformed(evt);
            }
        });

        txtNroPedido.setEditable(false);
        txtNroPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAgregarEmpresaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarEmpresaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpresaClienteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Número de Pedido:");

        cmbEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpresaActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Descripción:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Nombre de la Obra:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Empresa Cliente:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto Responsable"));

        btnAgregarCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarCR.setText("Agregar Contacto");
        btnAgregarCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(cmbContactos, 0, 275, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnAgregarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAgregarCR)
                .addComponent(cmbContactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Estado:");

        txtEstadoPedido.setEditable(false);
        txtEstadoPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstadoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel8)
                                .addGap(16, 16, 16))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbLVP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbLEP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                            .addComponent(txtNombreObra, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbfechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbfechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtEstadoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cmbfechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbfechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cmbLEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(cmbLVP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if(ValidarDatos()){
            //VALIDO LAS FECHAS
            // HACER
            //PASO LAS FECHAS AL GESTOR
            gestor.nombreObra(txtNombreObra.getText());
            gestor.descripcionObra(txtDescripcion.getText());
            gestor.montoMaximo(Double.parseDouble(txtMonto.getText()));

            Date fechaI = ((JDateChooser) cmbfechaInicio).getDate();
            Date fechaF = ((JDateChooser) cmbfechaFin).getDate();
            gestor.fechaInicioYFin(fechaI, fechaF);
//            Date fechaLVP = ((JDateChooser) cmbLVP).getDate();
//            gestor.fechaLVP(fechaLVP);
//            Date fechaLEP = ((JDateChooser) cmbLEP).getDate();
//            gestor.fechaLEP(fechaLEP);
            // LANZO EL CREAR

            if(cmbContactos.getSelectedItem() instanceof Tupla)
            {
                Tupla tp = (Tupla) cmbContactos.getSelectedItem();
                if(tp.getId()!=0)
                {
//                    gestor.contactoResponsable(tp.getId());
                }
            }

            gestor.seleccionPlanta((Tupla)cmbPlanta.getSelectedItem());
            int id = gestor.confirmacionRegistro(Integer.parseInt(this.txtNroPedido.getText()));

            JOptionPane.showMessageDialog(this.getParent(),"Se modificó con éxito el pedido número "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(pBuscar != null){
                pBuscar.actualizar(1, true);
            }
            this.dispose();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void cmbPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPlantaActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cmbPlantaActionPerformed

    private void btnAgregarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPlantaActionPerformed
        gestor.llamarCURegistrarNuevaPlanta(((Tupla)cmbEmpresa.getSelectedItem()));
}//GEN-LAST:event_btnAgregarPlantaActionPerformed

    private void btnAgregarEmpresaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpresaClienteActionPerformed
        gestor.llamarCURegistrarNuevaEmpresaCliente();
}//GEN-LAST:event_btnAgregarEmpresaClienteActionPerformed

    private void cmbEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpresaActionPerformed
        this.mostrarPlantasEmpresaCliente();
        if(cmbEmpresa.getSelectedIndex() != -1){
            btnAgregarPlanta.setEnabled(true);
            cmbPlanta.setEnabled(true);} else{
            btnAgregarPlanta.setEnabled(false);
            cmbPlanta.setEnabled(false);}
}//GEN-LAST:event_cmbEmpresaActionPerformed

    private void btnAgregarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCRActionPerformed
        pantallaRegistrarContactoResponsable p = new pantallaRegistrarContactoResponsable(this);
        SwingPanel.getInstance().addWindow(p);
        p.setVisible(true);
}//GEN-LAST:event_btnAgregarCRActionPerformed

    /**
    * @param args the command line arguments
    *//*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaModificarPedido().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCR;
    private javax.swing.JButton btnAgregarEmpresaCliente;
    private javax.swing.JButton btnAgregarPlanta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cmbContactos;
    private javax.swing.JComboBox cmbEmpresa;
    private com.toedter.calendar.JDateChooser cmbLEP;
    private com.toedter.calendar.JDateChooser cmbLVP;
    private javax.swing.JComboBox cmbPlanta;
    private com.toedter.calendar.JDateChooser cmbfechaFin;
    private com.toedter.calendar.JDateChooser cmbfechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEstadoPedido;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombreObra;
    private javax.swing.JTextField txtNroPedido;
    // End of variables declaration//GEN-END:variables

    public String getTituloAyuda() {
        return "Modificar Pedido Existente";
    }

    public String getResumenAyuda() {
        return "Usted podrá modificar los datos de la empresa seleccionada";
    }

    public int getIdAyuda() {
        return 1;
    }

    public void actualizar(int flag,boolean exito) {
        // 1: VIENE DEL UC Registrar Contacto Responsable
        switch(flag)
        {
            case 1:
                if(exito==true)
                {
                    mostrarContactos();
                }
                else
                {
                    JOptionPane.showMessageDialog(this.getParent(),"No se registro correctamente el Contacto Responsable\nIntentelo Nuevamente","Error",JOptionPane.INFORMATION_MESSAGE);
                }
                break;
        }
    }

    public void setPliegosPedido(String pliegos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPlanosPedido(String pedidos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizarListaContactosResponsables() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFormaDePago(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }
