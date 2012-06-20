/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaConsultarPrecioXProveedor.java
 *
 * Created on 05-sep-2010, 16:30:34
 */

package vista.compras;

import controlador.Compras.GestorConsultarPrecioXProveedor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import util.LogUtil;
import util.NTupla;
import util.StringUtil;
import util.TablaUtil;
import util.Tupla;

/**
 *
 * @author Administrador
 */
public class pantallaConsultarPrecioXProveedor extends javax.swing.JInternalFrame {

    private GestorConsultarPrecioXProveedor gestor;

    /** Creates new form pantallaConsultarPrecioXProveedor */
    public pantallaConsultarPrecioXProveedor() {
        initComponents();

        gestor = new GestorConsultarPrecioXProveedor(this);

        habilitarVentana();

        tablaListado.setRowHeight(30);

    }

    public void habilitarVentana()
    {
        initRubros();
        initProveedores();
    }

    private void initProveedores()
    {
        ArrayList<Tupla> lista = gestor.mostrarProveedores();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No Proveedores Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbProveedores.setModel(valores);
    }

    private void initRubros()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        valores.addElement(new Tupla(0,"Seleccione un Proveedor..."));

        cmbRubro.setModel(valores);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbRubro = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbProveedores = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar Precios por Proveedor");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("2. Seleccione el Rubro"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Rubro");

        cmbRubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbRubro, javax.swing.GroupLayout.Alignment.LEADING, 0, 204, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("1. Seleccione el proveedor"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Proveedor");

        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProveedoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbProveedores, javax.swing.GroupLayout.Alignment.LEADING, 0, 277, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripción", "Último Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaListado);
        tablaListado.getColumnModel().getColumn(2).setMinWidth(150);
        tablaListado.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaListado.getColumnModel().getColumn(2).setMaxWidth(150);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        jLabel3.setText("Filtrar");

        txtFiltro.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroKeyReleased(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cmbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRubroActionPerformed

        Tupla tpr = (Tupla)cmbRubro.getSelectedItem();
        Tupla tpp = (Tupla)cmbProveedores.getSelectedItem();
        if(tpr.getId()!=0 && tpp.getId()!=0)
        {
            mostrarPreciosPorProveedor(tpp.getId(),tpr.getId());
        }

    }//GEN-LAST:event_cmbRubroActionPerformed

    private void mostrarPreciosPorProveedor(int idProv, int idRubro)
    {
        ArrayList<NTupla> lista = gestor.mostrarPreciosPorProveedor(idProv, idRubro);
        DefaultTableModel modelo = (DefaultTableModel)tablaListado.getModel();

        // LIMPIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        int numFila = 0;

        Iterator<NTupla> it = lista.iterator();
        while (it.hasNext())
        {
            NTupla ntp = it.next();
            Object[] fila = new Object[3];
            fila[0] = ntp;
            String[] datos = (String[])ntp.getData();
            fila[1] = datos[0];
            fila[2] = datos[1];

            modelo.addRow(fila);

                // REDIMENSIONO LA FILA !!! -----------------------------------
                String item = datos[1];
                int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                if(cantItems!=0)
                {
                    tablaListado.setRowHeight(numFila,16*cantItems);
                }
                LogUtil.addDebug("ConsultarPreciosXProveedor: Cantidad de Repeticiones: "+cantItems);
                // REDIMENSIONO LA FILA !!! -----------------------------------

            numFila++;
        }
        tablaListado.setModel(modelo);
        tablaListado.repaint();
    }


    private void cmbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedoresActionPerformed

        Tupla tp = (Tupla)cmbProveedores.getSelectedItem();
        if(tp.getId()!=0)
        {
            mostrarRubrosPorProveedor(tp.getId());
        }

        Tupla tpr = (Tupla)cmbRubro.getSelectedItem();
        Tupla tpp = (Tupla)cmbProveedores.getSelectedItem();
        if(tpr.getId()!=0 && tpp.getId()!=0)
        {
            mostrarPreciosPorProveedor(tpp.getId(),tpr.getId());
        }


    }//GEN-LAST:event_cmbProveedoresActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed



    }//GEN-LAST:event_txtFiltroActionPerformed

    private void txtFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyReleased

        FiltrarTabla();

    }//GEN-LAST:event_txtFiltroKeyReleased

    private void FiltrarTabla()
    {
       TableRowSorter<TableModel> modeloOrdenado;
       modeloOrdenado = new TableRowSorter<TableModel>(tablaListado.getModel());
       tablaListado.setRowSorter(modeloOrdenado);

           String[] cadena=txtFiltro.getText().trim().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
           modeloOrdenado.setRowFilter(cadenaFilter);

           // CAMBIO LOS TAMAÑOS DE LAS FILAS
           DefaultTableModel modelo = (DefaultTableModel) tablaListado.getModel();
           for (int i = 0; i < modelo.getRowCount(); i++)
           {
            // REDIMENSIONO LA FILA !!! -----------------------------------
                int index = modeloOrdenado.convertRowIndexToView(i);
                if(index>-1)
                {
                    // ESTA
                    String item = (String) modelo.getValueAt(i,2);
                    int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                    if(cantItems!=0)
                    {
                        tablaListado.setRowHeight(index,16*cantItems);
                    }
                    LogUtil.addDebug("ConsultarPreciosXProveedor: Cantidad de Repeticiones: "+cantItems);
                }

                // REDIMENSIONO LA FILA !!! -----------------------------------
           }




    }

    private void mostrarRubrosPorProveedor(int id)
    {
        ArrayList<Tupla> lista = gestor.mostrarRubrosPorProveedor(id);

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No Rubros Cargados para este proveedor"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbRubro.setModel(valores);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox cmbProveedores;
    private javax.swing.JComboBox cmbRubro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables

    /**
     * MUESTRA LOS MENSAJES EN LA GUI
     * @param cod
     */
    public void MostrarMensaje(String cod)
    {
        if(cod.equals("ME-0001"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Proveedores","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("ME-0002"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Rubros","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        // ME-0003
        if(cod.equals("ME-0003"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Precios","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
    }

}
