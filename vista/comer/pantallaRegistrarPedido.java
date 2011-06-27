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
import javax.swing.JOptionPane;
import modelo.EmpresaCliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
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
        mostrarContactos();
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
//        if(((JDateChooser)cmbLEP).getDate() == null){
//            mensaje+="- Fecha Límite de Entrega de Presupuesto\n";
//            ban=false;
//        }
//        if(((JDateChooser)cmbLVP).getDate() == null){
//            mensaje+="- Fecha Límite de Validez del Presupuesto\n";
//            ban=false;
//        }
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

        jLabel1 = new javax.swing.JLabel();
        txtNroPedido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreObra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbPlanta = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAgregarEmpresaCliente = new javax.swing.JButton();
        btnAgregarPlanta = new javax.swing.JButton();
        cmbfechaInicio = new com.toedter.calendar.JDateChooser();
        cmbfechaFin = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        btnAgregarCR = new javax.swing.JButton();
        cmbContactos = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar Pedido");

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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Empresa Cliente:");

        cmbEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpresaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Planta:");

        cmbPlanta.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Fecha de Inicio:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Fecha de Fin:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Presupuesto Máximo ($):");

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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

        btnAgregarEmpresaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarEmpresaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpresaClienteActionPerformed(evt);
            }
        });

        btnAgregarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarPlanta.setEnabled(false);
        btnAgregarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlantaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto Responsable"));

        btnAgregarCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarCR.setText("Agregar Contacto");
        btnAgregarCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(cmbContactos, 0, 296, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnAgregarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAgregarCR)
                .addComponent(cmbContactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
                        .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                            .addComponent(txtNombreObra, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbfechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarEmpresaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if(ValidarDatos()){
            gestor.nombreObra(this.txtNombreObra.getText());
            gestor.descripcionObra(this.txtDescripcion.getText());
            gestor.montoMaximo(Long.valueOf(this.txtMonto.getText()));

            
            //VALIDO LAS FECHAS
            // HACER
            //PASO LAS FECHAS AL GESTOR
            Date fechaI = ((JDateChooser) cmbfechaInicio).getDate();
            Date fechaF = ((JDateChooser) cmbfechaFin).getDate();
            gestor.fechaInicioYFin(fechaI, fechaF);
//            Date fechaLVP = ((JDateChooser) cmbLVP).getDate();
//            gestor.fechaLVP(fechaLVP);
//            Date fechaLEP = ((JDateChooser) cmbLEP).getDate();
//            gestor.fechaLEP(fechaLEP);

            if(cmbContactos.getSelectedItem() instanceof Tupla)
            {
                Tupla tp = (Tupla) cmbContactos.getSelectedItem();
                if(tp.getId()!=0)
                {
                    gestor.contactoResponsable(tp.getId());
                }
            }
            // LANZO EL CREAR
            gestor.seleccionPlanta((Tupla)cmbPlanta.getSelectedItem());
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
        pantallaRegistrarContactoResponsable p = new pantallaRegistrarContactoResponsable(this);
        SwingPanel.getInstance().addWindow(p);
        p.setVisible(true);
    }//GEN-LAST:event_btnAgregarCRActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCR;
    private javax.swing.JButton btnAgregarEmpresaCliente;
    private javax.swing.JButton btnAgregarPlanta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cmbContactos;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbPlanta;
    private com.toedter.calendar.JDateChooser cmbfechaFin;
    private com.toedter.calendar.JDateChooser cmbfechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombreObra;
    private javax.swing.JTextField txtNroPedido;
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
}
