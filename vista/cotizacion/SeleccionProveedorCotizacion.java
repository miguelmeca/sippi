/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SeleccionProveedorCotizacion.java
 *
 * Created on 10-sep-2010, 15:39:00
 */

package vista.cotizacion;

import controlador.cotizacion.GestorCotizacionMateriales;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import modelo.SubObraXMaterial;
import util.LogUtil;
import util.NTupla;
import util.StringUtil;
import util.TablaUtil;

/**
 *
 * @author Administrador
 */
public class SeleccionProveedorCotizacion extends javax.swing.JInternalFrame {
    private GestorCotizacionMateriales gestor;
    private int idR;
    private int idRE;
    private boolean banHayPreciosMaterial;
    private SubObraXMaterial soxm;
    
    /** Creates new form SeleccionProveedorCotizacion */
    public SeleccionProveedorCotizacion() {
        initComponents();
    }

    SeleccionProveedorCotizacion(GestorCotizacionMateriales gestor,int idR, int idRE) {
        initComponents();
        this.gestor = gestor;
        this.idR=idR;
        this.idRE=idRE;
        this.banHayPreciosMaterial = true;
        mostrarRecursosEspecificosXProveedor();
    }
    
    // Constructor para cuando se pretende editar un material
    SeleccionProveedorCotizacion(GestorCotizacionMateriales gestor,int idR, int idRE, SubObraXMaterial soxm) {
        initComponents();
        this.gestor = gestor;
        this.idR=idR;
        this.idRE=idRE;
        this.banHayPreciosMaterial = true;
        this.soxm = soxm;
        mostrarRecursosEspecificosXProveedor();
        this.txtCantidad.setText(String.valueOf(soxm.getCantidad()));
        double subtotal = Math.rint((soxm.getCantidad()* soxm.getPrecioUnitario())*100)/100;
        this.txtSubtotal.setText(String.valueOf(subtotal));
        this.taDescripcion.setText(soxm.getDescripcion());
//        soxm.getMaterial().getProveedor()
        DefaultTableModel modelo = (DefaultTableModel)tbProveedores.getModel();
        for(int i=0; i< modelo.getRowCount();i++)
        {
            NTupla nt = (NTupla)modelo.getValueAt(i, 0);
            if(nt.getId() == soxm.getMaterial().getId())
            {
                tbProveedores.getSelectionModel().setSelectionInterval(0, i);
                break;
            }
        }
    }

    public void mostrarRecursosEspecificosXProveedor()
    {

        DefaultTableModel modelo = (DefaultTableModel)tbProveedores.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        ArrayList<NTupla> listaRecEsp = gestor.mostrarRecursosEspecificosXProveedor(idR,idRE);
        ((TitledBorder)this.panelProveedor.getBorder()).setTitle("Seleccione un Proveedor para ( "+gestor.getNombreRecurso(idR,idRE)+" )");
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
                    fila[2] = "<HTML><BODY>"+datos[1];

                modelo.addRow(fila);

                // REDIMENSIONO LAS FILAS !!! -----------------------------------
                String item = datos[0];
                int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                if(cantItems!=0)
                {
                    if(cantItems>2){
                    tbProveedores.setRowHeight(i,16*cantItems);
                    }else{
                        tbProveedores.setRowHeight(i,16*2);
                    }
                }
                LogUtil.addDebug("Registrar Asignacion de Materiales: Cantidad de Repeticiones: "+cantItems);
                // REDIMENSIONO LA FILA !!! -----------------------------------
                i++;
            }
        }else{
            this.banHayPreciosMaterial=false;
        }
    }

    public boolean isBanHayPreciosMaterial() {
        return banHayPreciosMaterial;
    }

    public void setBanHayPreciosMaterial(boolean banHayPreciosMaterial) {
        this.banHayPreciosMaterial = banHayPreciosMaterial;
    }

    public void actualizarSubTotal(){
        int cantidad = 0;
        boolean banCantidad=true;
        try{
            cantidad=Integer.parseInt(this.txtCantidad.getText());
        }catch(NumberFormatException ex)
        {
            banCantidad=false;
        }
        if(tbProveedores.getSelectedRow()>=0 && !txtCantidad.getText().equals("") && banCantidad){
            NTupla nt = (NTupla)((DefaultTableModel)this.tbProveedores.getModel()).getValueAt(tbProveedores.getSelectedRow(), 0);
            double subtotal = gestor.getSubtotal(nt.getId(),cantidad);
            DecimalFormat df =  new DecimalFormat("0.00");
            txtSubtotal.setText(df.format(subtotal));
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
        txtCantidad = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("Seleccione un Proveedor para el Material");

        panelProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione un Proveedor para ( Chapa 4NT 3x4m. )"));

        tbProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proveedor", "Precios Por Cantidad", "Vigencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProveedoresMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbProveedoresMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbProveedores);

        javax.swing.GroupLayout panelProveedorLayout = new javax.swing.GroupLayout(panelProveedor);
        panelProveedor.setLayout(panelProveedorLayout);
        panelProveedorLayout.setHorizontalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelProveedorLayout.setVerticalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione la Cantidad a Utilizar"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cantidad:");

        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("SubTotal:");

        txtSubtotal.setEditable(false);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel3.setText("$ ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAgregar)
                .addComponent(jLabel3))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        taDescripcion.setColumns(20);
        taDescripcion.setLineWrap(true);
        taDescripcion.setRows(5);
        jScrollPane2.setViewportView(taDescripcion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        Double cant = 0d;
        String msg = "";
        try{
            cant = Double.parseDouble(txtCantidad.getText());
        }catch(Exception ex){
            msg = "Debe ingresar un número válido";
        }
        if(cant < 0){
            msg += "Debe ingresar una cifra positiva";
        }
        if(msg.equals("")){
            actualizarSubTotal();
        }else{
            JOptionPane.showMessageDialog(this.getParent(),msg,"Advertencia",JOptionPane.WARNING_MESSAGE);
            txtCantidad.setText("");
        }
    }//GEN-LAST:event_txtCantidadFocusLost

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int cantidad = 0;
        String msg = "";
        boolean ban=true;
        try{
            cantidad=Integer.parseInt(this.txtCantidad.getText());
        }catch(NumberFormatException ex)
        {
            msg+="\n- Debe ingresar una cantidad válida";
            ban=false;
        }
        if(tbProveedores.getSelectedRow()<0){
            msg+="\n- Debe seleccionar un proveedor para este material";
            ban=false;
        }
        int subtotal = 0;
        try{
            subtotal=Integer.parseInt(this.txtSubtotal.getText());
            if(subtotal == 0)
            {
                
            }
        }catch(NumberFormatException ex){}
        if(ban){
            NTupla nt = (NTupla)this.tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 0);
            double precio = gestor.getPrecioMaterial(nt.getId(),cantidad);
            if(soxm == null) // Una nueva SubObraXMaterial
            {
                // Si hay precio registrado para este recurso
                if(gestor.agregarMaterial(nt.getId(),cantidad,this.taDescripcion.getText(),precio))
                {
                    this.dispose();
                }
            }
            else // Se trata de una edición de la SubObraXMaterial
            {
                // Si hay precio registrado para este recurso
                if(gestor.modificarMaterial(nt.getId(),cantidad,this.taDescripcion.getText(),precio,soxm))
                {
                    this.dispose();
                }
            }
            
        }else{
            JOptionPane.showMessageDialog(this.getParent(),"No se ha podido agregar el material debido a: "+msg,"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void tbProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProveedoresMouseClicked
        actualizarSubTotal();
    }//GEN-LAST:event_tbProveedoresMouseClicked

    private void tbProveedoresMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProveedoresMouseReleased
        actualizarSubTotal();
    }//GEN-LAST:event_tbProveedoresMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelProveedor;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTable tbProveedores;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables

}
