/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * editarCotizacion_Materiales.java
 *
 * Created on 02/05/2011, 16:58:38
 */

package vista.cotizacion;

import controlador.cotizacion.GestorCotizacionMateriales;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import util.*;

/**
 *
 * @author Emmanuel
 */
public class CotizacionMateriales extends javax.swing.JPanel {

    private GestorCotizacionMateriales gestor;
    
    /** Creates new form editarCotizacion_Materiales */
    public CotizacionMateriales(GestorCotizacionMateriales gestor) 
    {
        initComponents();
        
        this.gestor = gestor;
        this.gestor.setPantalla(this);
        cargarTabMateriales();
        mostrarMaterialesAUtilizar();
    }

    private void FiltrarTabla(JTable table,JTextField field){
//       if(!field.getText().matches("[\[\*\(\)\?]")){
           TableRowSorter<TableModel> modeloOrdenado;
           modeloOrdenado = new TableRowSorter<TableModel>(table.getModel());
           table.setRowSorter(modeloOrdenado);

               String[] cadena=field.getText().trim().split(" ");
               List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
               for (int i= 0; i < cadena.length; i++)
               {
                 filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
               }
               RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
               modeloOrdenado.setRowFilter(cadenaFilter);

               // CAMBIO LOS TAMAÃƒÆ’Ã¢â‚¬ËœOS DE LAS FILAS
               DefaultTableModel modelo = (DefaultTableModel) table.getModel();
               for (int i = 0; i < modelo.getRowCount(); i++)
               {
                // REDIMENSIONO LA FILA !!! -----------------------------------
                    int index = modeloOrdenado.convertRowIndexToView(i);
                    if(index>-1)
                    {
                        // ESTA
    //                    String item = (String) modelo.getValueAt(i,0);
                        String item = ((NTupla) modelo.getValueAt(i,0)).getNombre() ;
                        int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                        if(cantItems!=0)
                        {
                            table.setRowHeight(index,16*cantItems);
                        }
                        //LogUtil.addDebug("ConsultarPreciosXProveedor: Cantidad de Repeticiones: "+cantItems);
                    }
                    // REDIMENSIONO LA FILA !!! -----------------------------------
               }
//        }
    }

    private void mostrarEspecificacionMaterial(int id) {
        ArrayList<Tupla> esps = gestor.getEspecificacionMaterial(id);

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialEspecifico.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = esps.iterator();

        while (it.hasNext())
        {
            Tupla ntp = (Tupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
    }

    private void mostrarMaterialesAUtilizar() {
        ArrayList<NTupla> materiales = gestor.getMaterialesAUtilizar();

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialesAUsar.getModel();

        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();

        int cantidad = 0;
        double precio = 0;
        double total = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[5];
            Object[] o = (Object[]) ntp.getData();
            fila[0] = o[1];
            fila[1] = ntp;
            fila[2] = o[0];
            cantidad = (Integer)o[1];
            precio = (Double)o[2];
            fila[3] = precio;
            double subtotal = cantidad*precio;
            fila[4] = subtotal;
            total+=subtotal;
            modelo.addRow(fila);
        }
        txtSubtotalMateriales.setText(""+total);
    }

    private void cargarTabMateriales() {
        ArrayList<NTupla> materiales = gestor.getMaterialesDisponibles();

        DefaultTableModel modelo = (DefaultTableModel)tbMateriales.getModel();

        // VACIO LA TABLA MATERIALES
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();
        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }

        // VACIO LA TABLA MATERIAL ESPECIFICO
        DefaultTableModel modelo2 = (DefaultTableModel)tbMaterialEspecifico.getModel();
        TablaUtil.vaciarDefaultTableModel(modelo2);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarMaterial = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbMateriales = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbMaterialEspecifico = new javax.swing.JTable();
        btnAgregarMaterial = new javax.swing.JButton();
        btnQuitarMaterial = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMaterialesAUsar = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtSubtotalMateriales = new javax.swing.JTextField();
        btnAgregarNuevoPrecio = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(440, 380));

        txtBuscarMaterial.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscarMaterial.setForeground(java.awt.Color.gray);
        txtBuscarMaterial.setText("Buscar...");
        txtBuscarMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarMaterialFocusGained(evt);
            }
        });
        txtBuscarMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMaterialKeyReleased(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        jLabel8.setText("Seleccione un Material:");

        tbMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ));
        tbMateriales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMaterialesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbMateriales);

        tbMaterialEspecifico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especificación"
            }
        ));
        tbMaterialEspecifico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMaterialEspecificoMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tbMaterialEspecifico);

        btnAgregarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarMaterial.setText("Agregar");
        btnAgregarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMaterialActionPerformed(evt);
            }
        });

        btnQuitarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/up.png"))); // NOI18N
        btnQuitarMaterial.setText("Quitar");
        btnQuitarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarMaterialActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Materiales a Utilizar"));

        tbMaterialesAUsar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cantidad", "Tipo", "Nombre", "Precio Unitario", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbMaterialesAUsar);

        jLabel3.setText("Subtotal Materiales $");

        txtSubtotalMateriales.setEditable(false);
        txtSubtotalMateriales.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnAgregarNuevoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarNuevoPrecio.setText("Agregar Precio");
        btnAgregarNuevoPrecio.setEnabled(false);
        btnAgregarNuevoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarNuevoPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubtotalMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.Alignment.LEADING))
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarNuevoPrecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMaterial)
                    .addComponent(btnQuitarMaterial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubtotalMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarMaterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarMaterialFocusGained
        txtBuscarMaterial.setText("");
}//GEN-LAST:event_txtBuscarMaterialFocusGained

    private void txtBuscarMaterialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMaterialKeyReleased
        DefaultTableModel dtm = (DefaultTableModel)tbMaterialEspecifico.getModel();
        TablaUtil.vaciarDefaultTableModel(dtm);
        this.FiltrarTabla(this.tbMateriales, txtBuscarMaterial);
}//GEN-LAST:event_txtBuscarMaterialKeyReleased

    private void tbMaterialesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaterialesMouseReleased
        if(tbMateriales.getSelectedRow()!=-1 && tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0) instanceof NTupla) {
            NTupla t = (NTupla)tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0);
            mostrarEspecificacionMaterial(t.getId());
            btnAgregarNuevoPrecio.setEnabled(false);
        }
}//GEN-LAST:event_tbMaterialesMouseReleased

    private void btnAgregarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMaterialActionPerformed
        if(tbMateriales.getSelectedRow() >= 0){
            if(tbMaterialEspecifico.getSelectedRow()>=0){
                Tupla re = (Tupla)(tbMaterialEspecifico.getModel()).getValueAt(tbMaterialEspecifico.getSelectedRow(), 0);
                NTupla r = (NTupla)(tbMateriales.getModel()).getValueAt(tbMateriales.getSelectedRow(), 0);
                SeleccionProveedorCotizacion psp = new SeleccionProveedorCotizacion(this.gestor,r.getId(),re.getId());
                if(psp.isBanHayPreciosMaterial()){
                    SwingPanel.getInstance().addWindow(psp);
                    psp.setVisible(true);
                    btnAgregarNuevoPrecio.setEnabled(false);
                } else{
                    if(JOptionPane.showConfirmDialog(this.getParent(), "No se encontraron precios registrados para este material. Â¿Desea agregarle uno?", "Material", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        AgregarNuevoPrecio anp = new AgregarNuevoPrecio(this.gestor,r.getId(),re.getId());
                        SwingPanel.getInstance().addWindow(anp);
                        anp.setVisible(true);
                    }
                    psp.dispose();
                }
            }
            else{
                JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un material","Material",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarMaterialActionPerformed

    private void btnQuitarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarMaterialActionPerformed
        if(tbMaterialesAUsar.getSelectedRow()>=0){
            NTupla nt = (NTupla)tbMaterialesAUsar.getModel().getValueAt(tbMaterialesAUsar.getSelectedRow(), 1);
            if(gestor.quitarMaterial(nt.getId())){
                this.mostrarMaterialesAUtilizar();
                this.cargarTabMateriales();
            }
        }
}//GEN-LAST:event_btnQuitarMaterialActionPerformed

    private void btnAgregarNuevoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoPrecioActionPerformed
        String msg = "";
        if(tbMateriales.getSelectedRow() >= 0){
            if(tbMaterialEspecifico.getSelectedRow()>=0){
                Tupla re = (Tupla)(tbMaterialEspecifico.getModel()).getValueAt(tbMaterialEspecifico.getSelectedRow(), 0);
                NTupla r = (NTupla)(tbMateriales.getModel()).getValueAt(tbMateriales.getSelectedRow(), 0);
                AgregarNuevoPrecio anp = new AgregarNuevoPrecio(this.gestor,r.getId(),re.getId());
                SwingPanel.getInstance().addWindow(anp);
                anp.setVisible(true);
            }else{
                msg= "- Especificación\n";
            }
        }
        else{
            msg= "- Material";
        }
        if(!msg.equals("")){
            JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar los siguientes elementos: \n"+msg,"Agregar Precio",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarNuevoPrecioActionPerformed

    private void tbMaterialEspecificoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaterialEspecificoMouseReleased
        btnAgregarNuevoPrecio.setEnabled(true);
    }//GEN-LAST:event_tbMaterialEspecificoMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMaterial;
    private javax.swing.JButton btnAgregarNuevoPrecio;
    private javax.swing.JButton btnQuitarMaterial;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable tbMaterialEspecifico;
    private javax.swing.JTable tbMateriales;
    private javax.swing.JTable tbMaterialesAUsar;
    private javax.swing.JTextField txtBuscarMaterial;
    private javax.swing.JTextField txtSubtotalMateriales;
    // End of variables declaration//GEN-END:variables

    public void actualizar(int id, String msg, boolean pass){
        this.cargarTabMateriales();
        this.mostrarMaterialesAUtilizar();
    }
}
