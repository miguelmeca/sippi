/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaEmitirOrdenDeCompra1.java
 *
 * Created on 07/09/2010, 05:18:35
 */

package vista.compras;

import controlador.Compras.GestorRegistrarRecepcionOC;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.NTupla;
import util.ReporteUtil;
import util.Tupla;
import vista.interfaces.IPantallaOrdenDeCompra;

/**
 *
 * @author Emmanuel
 */
public class pantallaEmitirOrdenDeCompra1 extends javax.swing.JInternalFrame implements IPantallaOrdenDeCompra {
    private GestorRegistrarRecepcionOC gestor;
    private boolean banCerrando;

    /** Creates new form pantallaEmitirOrdenDeCompra1 */
    public pantallaEmitirOrdenDeCompra1() {
        initComponents();
        banCerrando = false;
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
        lista = this.mostrarProveedores();
        if(lista != null){
            Iterator it = lista.iterator();
            while(it.hasNext()){
                NTupla tu = (NTupla)it.next();
                valores.addElement(tu);
            }
            cmbProveedor.setModel(valores);
        }
    }

    public void llenarTablaOC(ArrayList<NTupla> ordenes){
        DefaultTableModel modelo = null;
        if(!banCerrando){
            int filas = tablaOrdenesCompra.getModel().getRowCount();
            for(int i=0; i<filas;i++){
                ((DefaultTableModel)tablaOrdenesCompra.getModel()).removeRow(i);
            }
            filas = tablaDetalle.getModel().getRowCount();
            for(int i=0; i<filas;i++){
                ((DefaultTableModel)tablaDetalle.getModel()).removeRow(i);
            }
        }
        txtEstadoOC.setText("");
        if(ordenes != null  && ordenes.size() > 0){
            ArrayList<NTupla> pls = ordenes;
            modelo = (DefaultTableModel) tablaOrdenesCompra.getModel();
            for(NTupla nt : pls){
                Object[] item = new Object[3];
                item[0] = nt.getId();
                item[1] = nt.getNombre();
                item[2] = nt.getData();
                modelo.addRow(item);
            }
        }
    }

    public void llenarTablaDOC(ArrayList<NTupla> docs){
        DefaultTableModel modelo = null;
        int filas = tablaDetalle.getModel().getRowCount();
        for(int i=0; i<filas;i++){
            ((DefaultTableModel)tablaDetalle.getModel()).removeRow(i);
        }
        double total = 0, subtotal=0;
        if(docs != null  && docs.size() > 0){
            ArrayList<NTupla> pls = docs;
            modelo = (DefaultTableModel) tablaDetalle.getModel();
            for(NTupla nt : pls){
                Object[] item = new Object[6];
                item[0] = Boolean.parseBoolean(((String[])nt.getData())[3]); // SI ESTA RECIBIDO O NO
                item[1] = ((String[])nt.getData())[0]; // CANTIDAD
                item[2] = nt;                          // NOMBRE - IMPORTANTE PARA OBTENER EL ID
                item[3] = ((String[])nt.getData())[1]; // DESCRIPCION
                item[4] = ((String[])nt.getData())[2]; // P.U.
                subtotal = Double.valueOf((String)item[1])*Double.valueOf((String)item[4]); // SUBTOTAL
                total +=subtotal;
                item[5] = subtotal;
                modelo.addRow(item);
            }
        }
        txtTotalDetalle.setText(String.valueOf(total));
        tablaDetalle.getGraphicsConfiguration();
//                tablaDetalle.getModel().
    }

  protected void setAnchoColumnas(){
//        JViewport scroll =  (JViewport) tablaDetalle.getParent();
        //HARDCORE!!!
        int ancho = 460;
        // NO FUNCIONA:
        //int ancho = (int)tablaDetalle.getSize().getWidth();

//        FormatoTabla ft = new FormatoTabla();
//        tablaDetalle.setDefaultRenderer(Double.class, ft);
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tablaDetalle.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < tablaDetalle.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch(i){
                case 0: anchoColumna = (15*ancho)/100;
                        break;
                case 1: anchoColumna = (30*ancho)/100;
                        break;
                case 2: anchoColumna = (25*ancho)/100;
                        break;
                case 3: anchoColumna = (15*ancho)/100;
                        break;
                case 4: anchoColumna = (15*ancho)/100;
                        break;
            }
            columnaTabla.setPreferredWidth(anchoColumna);
        }
    }

    protected void ocultarBotonesRecepcion(){
//        this.btnRecepcionParcial.setVisible(false);
//        this.btnRecepcionTotal.setVisible(false);
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
         *  mos los mÃƒÂ©todos:
         *       tablaDetalle.getColumnCount() o
         *       tableDetalle.getColumnModel().getColumnCount()
         *
         *  estos nos devuelven la cantidad de COLUMNAS VISIBLES.
         *
         *  FUENTE: http://200.80.28.188/jforum/posts/list/24.page
         */
        tcm.removeColumn( columna );
        setAnchoColumnas();

    }

    protected void visualizarBotonImprimir() {
        this.btnImprimir.setVisible(true);
    }

    public class MiModelo extends DefaultTableModel
    {
        public boolean isCellEditable (int row, int column)
        {
            // AquÃ­ devolvemos true o false segÃºn queramos que una celda
            // identificada por fila,columna (row,column), sea o no editable
            boolean respuesta=false;
            if(column == 0){
                if(!(Boolean)this.getValueAt(row, column)){
                    respuesta = true;
                }
            }
            return respuesta;
        }
    }
    public class FormatoTabla extends JFormattedTextField implements TableCellRenderer{
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
            JFormattedTextField campoTexto = new JFormattedTextField();
            campoTexto.setBorder(BorderFactory.createEmptyBorder());
            if(value instanceof Double){
                campoTexto.setText("$ "+(Double)value);
                campoTexto.setHorizontalAlignment(SwingConstants.RIGHT);
            }
            return campoTexto;
        }
    }

    protected ArrayList<NTupla> buscarOCPorProveedor(int id){
        return gestor.buscarOCGeneradasProveedor(id);
    }

    protected ArrayList<NTupla> buscarOCPorFechaEmision(Date fecha){
        return gestor.buscarOCGeneradasPorFechaEmision(fecha);
    }

    protected ArrayList<NTupla> buscarOCPorNro(int nro){
        return gestor.buscarOCGeneradasPorNro(nro);
    }

    protected ArrayList<NTupla> mostrarProveedores(){
        return gestor.mostrarProveedoresConOCGeneradas();
    }

    protected GestorRegistrarRecepcionOC getGestor(){
        return this.gestor;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        txtTotalDetalle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnMostrarDetalle = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtEstadoOC = new javax.swing.JTextField();
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
        dchFechaEmision = new com.toedter.calendar.JDateChooser();
        btnImprimir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaOrdenesCompra = new javax.swing.JTable();

        setTitle("Emitir Orden De Compra");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Detalle"));

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Cant.", "Nombre", "Descripción", "P. U.", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tablaDetalle);

        txtTotalDetalle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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

        btnMostrarDetalle.setText("Mostrar Detalle");
        btnMostrarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDetalleActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Estado:");

        txtEstadoOC.setEditable(false);
        txtEstadoOC.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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
                .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
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
                .addContainerGap(158, Short.MAX_VALUE))
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

        dchFechaEmision.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dchFechaEmisionPropertyChange(evt);
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
                .addComponent(dchFechaEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        panelFechaEmisionLayout.setVerticalGroup(
            panelFechaEmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFechaEmisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFechaEmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dchFechaEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pestanias.addTab("Fecha de Emisión", panelFechaEmision);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenes de Compra"));

        tablaOrdenesCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Orden", "Proveedor", "Fecha de Emisión"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaOrdenesCompra);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnMostrarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstadoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnImprimir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pestanias, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pestanias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMostrarDetalle)
                    .addComponent(jLabel1)
                    .addComponent(txtEstadoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnImprimir))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarDetalleActionPerformed
        // TODO add your handling code here:
        if(tablaOrdenesCompra.getSelectedRow() >= 0){
            int id = (Integer) tablaOrdenesCompra.getModel().getValueAt(tablaOrdenesCompra.getSelectedRow(), 0);
            llenarTablaDOC(gestor.mostrarDetalleOC(id));
            Tupla t = gestor.getEstadoOCSeleccionada(id);
            txtEstadoOC.setText(t.getNombre());
            btnImprimir.setEnabled(true);
//            btnRecepcionParcial.setEnabled(true);
//            btnRecepcionTotal.setEnabled(true);
//            if(gestor.verificarRecibidaParcial(t.getId())){
//                btnRecepcionTotal.setEnabled(false);
//            }
        }
}//GEN-LAST:event_btnMostrarDetalleActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedorActionPerformed
        btnImprimir.setEnabled(false);
//        btnRecepcionParcial.setEnabled(false);
//        btnRecepcionTotal.setEnabled(false);
        llenarTablaOC(buscarOCPorProveedor(((NTupla)cmbProveedor.getSelectedItem()).getId()));
}//GEN-LAST:event_cmbProveedorActionPerformed

    private void txtNumeroOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroOrdenActionPerformed
        btnImprimir.setEnabled(false);
//        btnRecepcionParcial.setEnabled(false);
//        btnRecepcionTotal.setEnabled(false);
        llenarTablaOC(buscarOCPorNro(Integer.parseInt(txtNumeroOrden.getText())));
}//GEN-LAST:event_txtNumeroOrdenActionPerformed

    private void dchFechaEmisionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dchFechaEmisionPropertyChange
        if(pestanias.getSelectedComponent() == panelFechaEmision)
            btnImprimir.setEnabled(false);
//        btnRecepcionParcial.setEnabled(false);
//        btnRecepcionTotal.setEnabled(false);
        llenarTablaOC(buscarOCPorFechaEmision(dchFechaEmision.getDate()));
}//GEN-LAST:event_dchFechaEmisionPropertyChange

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        int id = (Integer) tablaOrdenesCompra.getModel().getValueAt(tablaOrdenesCompra.getSelectedRow(), 0);
        if(id>0) {
            //           SwingPanel.getInstance().setCargando(true);
            String urlReporte = "/vista/reportes/OrdenDeCompra.jrxml";

            //           Map params = new HashMap();

            Map params = gestor.parametrosAImprimir(id);
            //           params.put("idOC",id);
            //           params.put("PROVEEDOR", "EXPRESO BRIOS");
            //           params.put("CUIT", "12233");
            //           params.put("DIRECCION", "Algun LADO");

            ReporteUtil ru = new ReporteUtil();
            ru.mostrarReporte(urlReporte,params);
            //SwingPanel.getInstance().setCargando(false);
            gestor.emitirOrdenDeCompra(id);
        }
        banCerrando = true;
        this.dispose();
}//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMostrarDetalle;
    private javax.swing.JComboBox cmbProveedor;
    private com.toedter.calendar.JDateChooser dchFechaEmision;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField txtEstadoOC;
    private javax.swing.JTextField txtNumeroOrden;
    private javax.swing.JTextField txtTotalDetalle;
    // End of variables declaration//GEN-END:variables

}
