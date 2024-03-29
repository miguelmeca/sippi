/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.*;
import modelo.FormaDePago;
import modelo.ItemStock;
import sun.swing.SwingUtilities2;
import util.*;

/**
 *
 * @author Administrador
 */
public class GestionarStock extends javax.swing.JInternalFrame {

    /**
     * Creates new form GestionarStock
     */
    public GestionarStock() {
        initComponents();
        initAnchoColumnasTabla();
        initTableData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();
        btnAjusteManual = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Stock");

        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Nombre y Descripción", "Unidad", "Cantidad", "Modificado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblStock);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnAjusteManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/comment.png"))); // NOI18N
        btnAjusteManual.setText("Ajuste Manual de Stock");
        btnAjusteManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjusteManualActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/refresh.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFiltroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtFiltro)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAjusteManual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAjusteManual)
                    .addComponent(btnCerrar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        initTableData();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyPressed
        activarFiltrosTabla();
    }//GEN-LAST:event_txtFiltroKeyPressed

    private void btnAjusteManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjusteManualActionPerformed
       int cantidadSeleccionados = TablaUtil.getCantidadSeleccionados((DefaultTableModel)tblStock.getModel(),0,true);
       if(cantidadSeleccionados>0)
       {
            ajusteManualStock();
       }
       else
       {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Seleccione al menos un item para actualizar su Stock");   
       }
    }//GEN-LAST:event_btnAjusteManualActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAjusteManual;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblStock;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables

    private void initAnchoColumnasTabla() {
        int anchoColumna = 0; 
        TableColumnModel modeloColumna = tblStock.getColumnModel(); 
        TableColumn columnaTabla; 
        for (int i = 0; i < tblStock.getColumnCount(); i++) { 
            columnaTabla = modeloColumna.getColumn(i); 
            switch(i){ 
                case 0: anchoColumna = 30; 
                        break; 
                case 1: anchoColumna = 500; 
                        break; 
                case 2: anchoColumna = 100; 
                        break; 
                case 3: anchoColumna = 100; 
                        break; 
                case 4: anchoColumna = 150; 
                        break;                     
            }                      
            columnaTabla.setPreferredWidth(anchoColumna); 
            columnaTabla.setWidth(anchoColumna);
        } 
    }

    private void initTableData() {
        DefaultTableModel modelo = (DefaultTableModel)tblStock.getModel();
        // Vacio la Tabla
        TablaUtil.vaciarDefaultTableModel(modelo);
        // Hago la consulta y lleno la tabla
        try
        {
            HibernateUtil.beginTransaction();
            List<ItemStock> listado = HibernateUtil.getSession().createQuery("from ItemStock").list();
            HibernateUtil.commitTransaction();

            for (int i = 0; i < listado.size(); i++) {
                ItemStock is = listado.get(i);
                
                if(is.getItem()!=null)
                {
                    Tupla tp = new Tupla(is.getId(),is.getItem().getNombre());

                    Object[] fila = new Object[5];
                        fila[0] = false;
                        fila[1] = tp;
                        fila[2] = is.getItem().getUnidadDeMedida();
                        fila[3] = is.getCantidad();
                        fila[4] = FechaUtil.getFecha(is.getFechaModificacion());

                    modelo.addRow(fila);
                }
            }

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar el Stock\n"+e.getMessage());
            e.printStackTrace();
            return;
        } 
    }
    
    /**
     * Muestra un mensaje
     * @param tipo
     * @param titulo
     * @param mensaje 
     */
    public void mostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }

    private void activarFiltrosTabla() {
           
           TableRowSorter<TableModel> modeloOrdenado;

           modeloOrdenado = new TableRowSorter<TableModel>(tblStock.getModel());
           tblStock.setRowSorter(modeloOrdenado);
        
           String[] cadena=txtFiltro.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);
    }

    private void ajusteManualStock() {
        int seleccion = JOptionPane.showOptionDialog(
            this, // Componente padre
            "<HTML>Está por hacer un ajuste manual al stock de<br> la empresa, <u>se recomienda</u> que utilize el procedimiento normal<br> de compras y genere nuevas <b>'Ordenes de Compra'</b>", //Mensaje
            "Seleccione una opción", // Título
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,    // null para icono por defecto.
            new Object[] { "Generar Orden de Compra", "Ajuste Manual"},    // null para YES, NO y CANCEL
            "Generar Orden de Compra");

        if (seleccion != -1)
        {
            if((seleccion + 1)==1)
            {
                // PRESIONO "Generar Orden de Compra"
                generarNuevaOrdenDeCompra();
            }
            else
            {
                // PRESIONO "Ajuste Manual"
                ajusteManual();
            }
        }
        
    }

    private void generarNuevaOrdenDeCompra() {
        GenerarNuevaOrdenDeCompra goc = new GenerarNuevaOrdenDeCompra();
        SwingPanel.getInstance().addWindow(goc);
        goc.setVisible(true);
        this.dispose();
    }

    private void ajusteManual() {
        AjustarNivelesDeStock goc = new AjustarNivelesDeStock(getItemsAActualizar());
        SwingPanel.getInstance().addWindow(goc);
        goc.setVisible(true);
        this.dispose();        
    }
    
    private List<ItemStock> getItemsAActualizar()
    {
        List<ItemStock> items = new ArrayList<ItemStock>();
        DefaultTableModel modelo = (DefaultTableModel)tblStock.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Tupla tp = (Tupla) modelo.getValueAt(i,1);
            boolean isSelected = (Boolean)modelo.getValueAt(i,0);
            if(isSelected)
            {
                ItemStock itemStock = loadItemStock(tp.getId());
                if(itemStock!=null)
                {
                    items.add(itemStock);
                }
            }
        }
        return items;
    }
    
    private ItemStock loadItemStock(int id)
    {
        ItemStock itemStock = null;
        try
        {
            HibernateUtil.beginTransaction();
            itemStock = (ItemStock) HibernateUtil.getSession().load(ItemStock.class, id);
            HibernateUtil.commitTransaction();

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            e.printStackTrace();
            return null;
        } 
        return itemStock;
    }
}
