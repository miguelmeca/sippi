/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaSeleccionarProveedorPresupuesto.java
 *
 * Created on 10-sep-2010, 15:39:00
 */

package vista.planificacion;

import controlador.planificacion.GestorRegistrarAsignacionMateriales;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import util.LogUtil;
import util.NTupla;
import util.StringUtil;
import util.TablaUtil;

/**
 *
 * @author Administrador
 */
public class pantallaSeleccionarProveedorPresupuesto extends javax.swing.JInternalFrame {
    private GestorRegistrarAsignacionMateriales gestorRAM;
    private int idR;
    private int idRE;
    /** Creates new form pantallaSeleccionarProveedorPresupuesto */
    public pantallaSeleccionarProveedorPresupuesto() {
        initComponents();
    }

    pantallaSeleccionarProveedorPresupuesto(GestorRegistrarAsignacionMateriales gestorRAM,int idR, int idRE) {
        initComponents();
        this.gestorRAM = gestorRAM;
        this.idR=idR;
        this.idRE=idRE;
        mostrarRecursosEspecificosXProveedor();
    }

    private void mostrarRecursosEspecificosXProveedor()
    {
        DefaultTableModel modelo = (DefaultTableModel)tbProveedores.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        ArrayList<NTupla> listaRecEsp = gestorRAM.mostrarRecursosEspecificosXProveedor(idR,idRE);
        ((TitledBorder)this.panelProveedor.getBorder()).setTitle("Seleccione un Proveedor para ( "+gestorRAM.getNombreRecurso(idR,idRE)+" )");
        Iterator<NTupla> itp = listaRecEsp.iterator();

        if(listaRecEsp.size()>0){
            int i = 0;
            while (itp.hasNext())
            {
                NTupla ntp = itp.next();
                Object[] fila = new Object[3];
                fila[0] = ntp;
                    String[] datos = (String[])ntp.getData();
                    fila[0] = ntp;
                    fila[1] = datos[0];
                    fila[2] = datos[1];

                modelo.addRow(fila);

                // REDIMENSIONO LAS FILAS !!! -----------------------------------
                String item = datos[0];
                int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                if(cantItems!=0)
                {
                    tbProveedores.setRowHeight(i,16*cantItems);
                }
                LogUtil.addDebug("Registrar Asignacion de Materiales: Cantidad de Repeticiones: "+cantItems);
                // REDIMENSIONO LA FILA !!! -----------------------------------
                i++;
            }
        }else{
            JOptionPane.showMessageDialog(this.getParent(),"No se encontraron precios de este material","Material",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
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

        panelProveedor = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProveedores = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fxtCantidad = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        btnAniadir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("Seleccione un Proveedor para el Material");

        panelProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione un Proveedor para ( Chapa 4NT 3x4m. )"));

        tbProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proveedor", "Precios Por Cantidad", "Fecha de Vigencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProveedores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbProveedoresFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(tbProveedores);

        javax.swing.GroupLayout panelProveedorLayout = new javax.swing.GroupLayout(panelProveedor);
        panelProveedor.setLayout(panelProveedorLayout);
        panelProveedorLayout.setHorizontalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProveedorLayout.setVerticalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione la Cantidad a Utilizar"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Cantidad:");

        fxtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        fxtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fxtCantidadFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("SubTotal:");

        txtSubtotal.setEditable(false);
        txtSubtotal.setText("$600");

        btnAniadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAniadir.setText("Agregar");
        btnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAniadir, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(fxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAniadir))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        taDescripcion.setColumns(20);
        taDescripcion.setRows(5);
        jScrollPane2.setViewportView(taDescripcion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addComponent(panelProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbProveedoresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbProveedoresFocusLost
        if(tbProveedores.getSelectedRow()>=0 && !fxtCantidad.getText().equals("")){
            NTupla nt = (NTupla)((DefaultTableModel)this.tbProveedores.getModel()).getValueAt(tbProveedores.getSelectedRow(), 0);
            int cantidad = Integer.parseInt(fxtCantidad.getText());
            txtSubtotal.setText(gestorRAM.getSubtotal(nt.getId(),cantidad));
        }
    }//GEN-LAST:event_tbProveedoresFocusLost

    private void fxtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fxtCantidadFocusLost
        int cantidad = 0;
        boolean banCantidad=true;
        try{
            cantidad=Integer.parseInt(this.fxtCantidad.getText());
        }catch(NumberFormatException ex)
        {
            banCantidad=false;
        }
        if(tbProveedores.getSelectedRow()>=0 && !fxtCantidad.getText().equals("") && banCantidad){
            NTupla nt = (NTupla)((DefaultTableModel)this.tbProveedores.getModel()).getValueAt(tbProveedores.getSelectedRow(), 0);
            txtSubtotal.setText(gestorRAM.getSubtotal(nt.getId(),cantidad));
        }
    }//GEN-LAST:event_fxtCantidadFocusLost

    private void btnAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirActionPerformed
        int cantidad = 0;
        boolean banCantidad=true, banProveedor=true;
        try{
            cantidad=Integer.parseInt(this.fxtCantidad.getText());
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Debe ingresar una cantidad válida","Cantidad",JOptionPane.WARNING_MESSAGE);
            banCantidad=false;
        }
        if(tbProveedores.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un proveedor para este material","Proveedor",JOptionPane.WARNING_MESSAGE);
            banProveedor=false;
        }
        if(banCantidad && banProveedor){
            NTupla nt = (NTupla)this.tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 0);
            gestorRAM.agregarMaterial(nt.getId(),cantidad,this.taDescripcion.getText());
            this.dispose();
        }
    }//GEN-LAST:event_btnAniadirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniadir;
    private javax.swing.JFormattedTextField fxtCantidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelProveedor;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTable tbProveedores;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables

}
