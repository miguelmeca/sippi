/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaRegistrarRecepcionOrdenCompra.java
 *
 * Created on 27/08/2010, 10:02:21
 */

package vista.compras;

import controlador.Compras.GestorRegistrarRecepcionOC;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.NTupla;
import util.Tupla;

/**
 *
 * @author Emmanuel
 */
public class pantallaRegistrarRecepcionOrdenCompra extends javax.swing.JInternalFrame {
    private GestorRegistrarRecepcionOC gestor;


    /** Creates new form pantallaRegistrarRecepcionOrdenCompra */
    public pantallaRegistrarRecepcionOrdenCompra() {
        initComponents();
        gestor = new GestorRegistrarRecepcionOC(this);
        habilitarVentana();
        

    }

    public void habilitarVentana(){
        this.setAnchoColumnas();
        llenarComboProveedores();
    }

    public void llenarComboProveedores(){
        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<NTupla> lista = null;
        lista = gestor.mostrarProveedoresConOrdenesPendientes();
        if(lista != null){
            Iterator it = lista.iterator();
            while(it.hasNext()){
                NTupla tu = (NTupla)it.next();
                valores.addElement(tu);
            }
            cmbProveedor.setModel(valores);
        }
    }

    protected void ocultarBotonesRecepcion(){
        this.btnRecepcionParcial.setVisible(false);
        this.btnRecepcionTotal.setVisible(false);
    }

    protected void borrarColumnaSeleccion(){
        TableColumnModel tcm = tablaDetalle.getColumnModel();
        TableColumn columna = tcm.getColumn( 0 );

        /** La columna que deseo eliminar se elimina de la vista pero no del mo-
         *  delo y se puede recuperar por medio de:
         *
         *   tablaDetalle.getColumnModel().addColumn(tablaDetalle.getColumnModel().getColumn(0));
         *
         *  Cabe aclarar que es de vital importancia que cuando nosotros invoca-
         *  mos los métodos:
         *       tablaDetalle.getColumnCount() o
         *       tableDetalle.getColumnModel().getColumnCount()
         *
         *  estos nos devuelven la cantidad de COLUMNAS VISIBLES.
         *
         *  FUENTE: http://200.80.28.188/jforum/posts/list/24.page
         */
        tcm.removeColumn( columna );

    }

    protected void setAnchoColumnas(){
        JViewport scroll =  (JViewport) tablaDetalle.getParent();
        //HARDCORE!!!
        int ancho = 535;
        // NO FUNCIONA:
        //int ancho = (int)tablaDetalle.getSize().getWidth();
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tablaDetalle.getColumnModel();
        TableColumn columnaTabla;
        if(tablaDetalle.getColumnCount()>6){
            for (int i = 0; i < tablaDetalle.getColumnCount(); i++) {
                columnaTabla = modeloColumna.getColumn(i);
                switch(i){
                    case 0: anchoColumna = (3*ancho)/100;
                            break;
                    case 1: anchoColumna = (10*ancho)/100;
                            break;
                    case 2: anchoColumna = (25*ancho)/100;
                            break;
                    case 3: anchoColumna = (22*ancho)/100;
                            break;
                    case 4: anchoColumna = (20*ancho)/100;
                            break;
                    case 5: anchoColumna = (10*ancho)/100;
                            break;
                    case 6: anchoColumna = (10*ancho)/100;
                            break;
                }
                columnaTabla.setPreferredWidth(anchoColumna);
            }
        }
        else{
                for (int i = 0; i < tablaDetalle.getColumnCount(); i++) {
                columnaTabla = modeloColumna.getColumn(i);
                switch(i){
                    case 0: anchoColumna = (10*ancho)/100;
                            break;
                    case 1: anchoColumna = (20*ancho)/100;
                            break;
                    case 2: anchoColumna = (20*ancho)/100;
                            break;
                    case 3: anchoColumna = (20*ancho)/100;
                            break;
                    case 4: anchoColumna = (15*ancho)/100;
                            break;
                    case 5: anchoColumna = (15*ancho)/100;
                            break;
                }
                columnaTabla.setPreferredWidth(anchoColumna);
            }
        }
    }

    public void llenarTablaOC(ArrayList<NTupla> ordenes){
        ArrayList<NTupla> pls = ordenes;
        DefaultTableModel modelo = (DefaultTableModel) tablaOrdenesCompra.getModel();
        for(NTupla nt : pls){
            Object[] item = new Object[3];
            item[0] = nt.getId();
            item[1] = nt.getNombre();
            item[2] = nt.getData();
            modelo.addRow(item);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        txtTotalDetalle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnRecepcionTotal = new javax.swing.JButton();
        btnRecepcionParcial = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaOrdenesCompra = new javax.swing.JTable();
        pestanias = new javax.swing.JTabbedPane();
        panelProveedor = new javax.swing.JPanel();
        cmbProveedor = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        panelNumeroOrden = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNumeroOrden = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        panelFechaEmision = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnMostrarDetalle = new javax.swing.JButton();

        setTitle("Registrar Recepción de Orden de Compra");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Detalle"));

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Cant.", "Nombre", "Descripción", "Proveedor", "P. U.", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tablaDetalle);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Total:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)))
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnRecepcionTotal.setText("Recepción Total");
        btnRecepcionTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecepcionTotalActionPerformed(evt);
            }
        });

        btnRecepcionParcial.setText("Recepción Parcial");
        btnRecepcionParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecepcionParcialActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenes de Compra"));

        tablaOrdenesCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Orden", "Proveedor", "Fecha de Emisión"
            }
        ));
        jScrollPane2.setViewportView(tablaOrdenesCompra);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pestanias.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar por"));

        cmbProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProveedorActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Proveedor:");

        javax.swing.GroupLayout panelProveedorLayout = new javax.swing.GroupLayout(panelProveedor);
        panelProveedor.setLayout(panelProveedorLayout);
        panelProveedorLayout.setHorizontalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProveedor, 0, 190, Short.MAX_VALUE)
                .addGap(271, 271, 271))
        );
        panelProveedorLayout.setVerticalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pestanias.addTab("Proveedor", panelProveedor);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Número:");

        txtNumeroOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroOrdenActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        javax.swing.GroupLayout panelNumeroOrdenLayout = new javax.swing.GroupLayout(panelNumeroOrden);
        panelNumeroOrden.setLayout(panelNumeroOrdenLayout);
        panelNumeroOrdenLayout.setHorizontalGroup(
            panelNumeroOrdenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumeroOrdenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumeroOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        panelNumeroOrdenLayout.setVerticalGroup(
            panelNumeroOrdenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumeroOrdenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNumeroOrdenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(panelNumeroOrdenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtNumeroOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pestanias.addTab(" Número de Orden", panelNumeroOrden);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Fecha de Emisión:");

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout panelFechaEmisionLayout = new javax.swing.GroupLayout(panelFechaEmision);
        panelFechaEmision.setLayout(panelFechaEmisionLayout);
        panelFechaEmisionLayout.setHorizontalGroup(
            panelFechaEmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFechaEmisionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );
        panelFechaEmisionLayout.setVerticalGroup(
            panelFechaEmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFechaEmisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFechaEmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pestanias.addTab("Fecha de Emisión", panelFechaEmision);

        btnMostrarDetalle.setText("Mostrar Detalle");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pestanias, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(421, Short.MAX_VALUE)
                        .addComponent(btnMostrarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(198, Short.MAX_VALUE)
                        .addComponent(btnRecepcionParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRecepcionTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pestanias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnMostrarDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnRecepcionTotal)
                    .addComponent(btnRecepcionParcial))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecepcionTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecepcionTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRecepcionTotalActionPerformed

    private void btnRecepcionParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecepcionParcialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRecepcionParcialActionPerformed

    private void txtNumeroOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroOrdenActionPerformed
        System.out.println("Pulsado enter");
        System.out.println(txtNumeroOrden.getText());

}//GEN-LAST:event_txtNumeroOrdenActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedorActionPerformed
        llenarTablaOC(gestor.buscarOCPendientesProveedor(((NTupla)cmbProveedor.getSelectedItem()).getId()));
    }//GEN-LAST:event_cmbProveedorActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        if(pestanias.getSelectedComponent() == panelFechaEmision)
                System.out.println("Elijiendo Fecha: "+jDateChooser1.getDate());
    }//GEN-LAST:event_jDateChooser1PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnMostrarDetalle;
    private javax.swing.JButton btnRecepcionParcial;
    private javax.swing.JButton btnRecepcionTotal;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbProveedor;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelFechaEmision;
    private javax.swing.JPanel panelNumeroOrden;
    private javax.swing.JPanel panelProveedor;
    private javax.swing.JTabbedPane pestanias;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTable tablaOrdenesCompra;
    private javax.swing.JTextField txtNumeroOrden;
    private javax.swing.JTextField txtTotalDetalle;
    // End of variables declaration//GEN-END:variables

}
