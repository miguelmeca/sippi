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

import controlador.Compras.GestorGenerarOrdenCompra;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView.TableRow;
import util.NTupla;
import util.ReporteUtil;
import util.SwingPanel;
import util.Tupla;

/**
 *
 * @author Fran
 */
public class pantallaVistaPreviaGenerarOrdenCompra extends javax.swing.JInternalFrame {
    private GestorGenerarOrdenCompra gestor;
    boolean definitivo;
    ArrayList<Object[]> daktos;
    /** Creates new form pantallaRegistrarRecepcionOrdenCompra */
    public pantallaVistaPreviaGenerarOrdenCompra(boolean definitiv, GestorGenerarOrdenCompra ges, ArrayList<Object[]> dakt)
    {
        definitivo=definitiv;
        daktos=dakt;
        gestor=ges;
        initComponents();
       // gestor = new GestorRegistrarRecepcionOC(this);
        habilitarVentana();
    }
    public void setDatos(ArrayList<Object[]> datos)
    {
        daktos=datos;
        levatarDatos();
    }

    private void habilitarVentana(){
        //this.setAnchoColumnas();
        //llenarComboProveedores();
        this.btnImprimir.setVisible(definitivo);
        levatarDatos();
    }

   private void levatarDatos()
   {
        DefaultTableModel modelo = (DefaultTableModel) tablaOrdenesCompra.getModel();
        for(int i=0; i<tablaOrdenesCompra.getModel().getRowCount();i++)
        {
            ((DefaultTableModel)tablaOrdenesCompra.getModel()).removeRow(i);
        }
        for(int i=0; i<tablaDetallesOC.getModel().getRowCount();i++){
            ((DefaultTableModel)tablaDetallesOC.getModel()).removeRow(i);
        }
       
       
       for (int i= 0; i < daktos.size(); i++) 
       {
          Object[] fila=new Object[4];
           fila[0]=daktos.get(i)[5];
           fila[1]=daktos.get(i)[3];
           fila[2]=daktos.get(i)[6];
           modelo.addRow(fila);
       }
        
        
   }

   /* public void llenarTablaOC(ArrayList<NTupla> ordenes){
        DefaultTableModel modelo = null;
        int filas = tablaOrdenesCompra.getModel().getRowCount();
        for(int i=0; i<filas;i++){
            ((DefaultTableModel)tablaOrdenesCompra.getModel()).removeRow(i);
        }
        filas = tablaDetalle.getModel().getRowCount();
        for(int i=0; i<filas;i++){
            ((DefaultTableModel)tablaDetalle.getModel()).removeRow(i);
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
*/
   /* protected void setAnchoColumnas(){
//        JViewport scroll =  (JViewport) tablaDetalle.getParent();
        //HARDCORE!!!
        int ancho = 535;
        // NO FUNCIONA:
        //int ancho = (int)tablaDetalle.getSize().getWidth();

//        FormatoTabla ft = new FormatoTabla();
//        tablaDetalle.setDefaultRenderer(Double.class, ft);
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tablaDetalle.getColumnModel();
        TableColumn columnaTabla;
        if(tablaDetalle.getColumnCount()>5){
            for (int i = 0; i < tablaDetalle.getColumnCount(); i++) {
                columnaTabla = modeloColumna.getColumn(i);
                switch(i){
                    case 0: anchoColumna = (3*ancho)/100;
                            break;
                    case 1: anchoColumna = (15*ancho)/100;
                            break;
                    case 2: anchoColumna = (30*ancho)/100;
                            break;
                    case 3: anchoColumna = (22*ancho)/100;
                            break;
                    case 4: anchoColumna = (15*ancho)/100;
                            break;
                    case 5: anchoColumna = (15*ancho)/100;
                            break;
                }
                columnaTabla.setPreferredWidth(anchoColumna);
            }
        }
        else{
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
    }

 */
   

    protected void visualizarBotonImprimir() {
        this.btnImprimir.setVisible(true);
    }

   /* public class MiModelo extends DefaultTableModel
    {
        public boolean isCellEditable (int row, int column)
        {
            // Aquí devolvemos true o false según queramos que una celda
            // identificada por fila,columna (row,column), sea o no editable
            boolean respuesta=false;
            if(column == 0){
                if(!(Boolean)this.getValueAt(row, column)){
                    respuesta = true;
                }
            }
            return respuesta;
        }
    }*/
   /* public class FormatoTabla extends JFormattedTextField implements TableCellRenderer{
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
            JFormattedTextField campoTexto = new JFormattedTextField();
            campoTexto.setBorder(BorderFactory.createEmptyBorder());
            if(value instanceof Double){
                campoTexto.setText("$ "+(Double)value);
                campoTexto.setHorizontalAlignment(SwingConstants.RIGHT);
            }
            return campoTexto;
        }
    }*/

    

    

   /* protected GestorRegistrarRecepcionOC getGestor(){
        return this.gestor;
    }*/

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
        tablaDetallesOC = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaOrdenesCompra = new javax.swing.JTable();
        btnMostrarDetalle = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtEstadoOC = new javax.swing.JTextField();
        btnImprimir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Ordenes de compra generadas");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Detalle"));

        tablaDetallesOC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "Nombre", "Descripción", "P.U", "P.P"
            }
        ));
        tablaDetallesOC.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tablaDetallesOC);
        tablaDetallesOC.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaDetallesOC.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaDetallesOC.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaDetallesOC.getColumnModel().getColumn(3).setPreferredWidth(50);
        tablaDetallesOC.getColumnModel().getColumn(4).setPreferredWidth(50);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnCancelar.setText("Aceptar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenes de Compra"));

        tablaOrdenesCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Orden", "Proveedor", "Fecha de Generación", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaOrdenesCompra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaOrdenesCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaOrdenesCompraMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaOrdenesCompra);
        tablaOrdenesCompra.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaOrdenesCompra.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaOrdenesCompra.getColumnModel().getColumn(2).setPreferredWidth(114);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnMostrarDetalle.setText("Mostrar Detalle");
        btnMostrarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDetalleActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Estado:");

        txtEstadoOC.setEditable(false);
        txtEstadoOC.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMostrarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstadoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnImprimir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnMostrarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarDetalleActionPerformed
        /*
        if(tablaOrdenesCompra.getSelectedRow() >= 0){
            int id = (Integer) tablaOrdenesCompra.getModel().getValueAt(tablaOrdenesCompra.getSelectedRow(), 0);
            llenarTablaDOC(gestor.mostrarDetalleOC(id));
            Tupla t = gestor.getEstadoOCSeleccionada(id);
            txtEstadoOC.setText(t.getNombre());
            btnImprimir.setEnabled(true);
           
        }*/
    }//GEN-LAST:event_btnMostrarDetalleActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       /*
        int id = (Integer) tablaOrdenesCompra.getModel().getValueAt(tablaOrdenesCompra.getSelectedRow(), 0);

      if(id>0)
      {
           SwingPanel.getInstance().setCargando(true);
           String urlReporte = "/vista/reportes/OrdenDeCompra.jrxml";

           Map params = gestor.parametrosAImprimir(id);


           ReporteUtil ru = new ReporteUtil();
           ru.mostrarReporte(urlReporte,params);
           gestor.emitirOrdenDeCompra(id);
      }
        this.dispose();*/
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void tablaOrdenesCompraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaOrdenesCompraMousePressed
      for(int i=0; i<tablaDetallesOC.getModel().getRowCount();i++){
            ((DefaultTableModel)tablaDetallesOC.getModel()).removeRow(i);
        }
      DefaultTableModel modelo = (DefaultTableModel) tablaDetallesOC.getModel();
        if(tablaOrdenesCompra.getSelectedRow()!=-1)
       {
           int id;
            //String sleg;
            //sleg=(String)(tablaEmpleados.getModel().getValueAt(tablaEmpleados.getSelectedRow(), 0) );
            id=((Integer)(tablaOrdenesCompra.getModel().getValueAt(tablaOrdenesCompra.getSelectedRow(), 0)));
            
            Object[] datos=new Object[1];//inicializo solo para q no joda dsps
            for (int i= 0; i < daktos.size(); i++) 
            {
              if(((Integer) daktos.get(i)[5])==id)
              {
                  datos=daktos.get(i);
                  break;
              }
           }
            
           for (int i = 0; i < ((ArrayList<NTupla>)datos[0]).size(); i++) 
           {
            Object[] fila=new Object[5];
            fila[0]=((ArrayList<NTupla>)datos[0]).get(i);
            fila[1]=((ArrayList<Tupla>)datos[1]).get(i);
            fila[2]=((ArrayList<String>)datos[2]).get(i);
            fila[3]=((ArrayList<Double>)datos[4]).get(i);
            fila[4]=(((ArrayList<Double>)datos[4]).get(i))*((Double)((ArrayList<NTupla>)datos[0]).get(i).getData());
            modelo.addRow(fila);
           } 
            
          
      }
      
        
        
    }//GEN-LAST:event_tablaOrdenesCompraMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMostrarDetalle;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDetallesOC;
    private javax.swing.JTable tablaOrdenesCompra;
    private javax.swing.JTextField txtEstadoOC;
    // End of variables declaration//GEN-END:variables

}
