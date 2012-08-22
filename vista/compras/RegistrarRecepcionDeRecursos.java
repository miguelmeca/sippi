/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modelo.*;
import sun.swing.SwingUtilities2;
import util.*;
import vista.comer.pantallaListadoProveedores;
import vista.interfaces.ICallBackGen;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.EmitirOrdenDeCompra;
import vista.reportes.sources.EmitirRecepcionOrdenDeCompra;

/**
 *
 * @author Administrador
 */
public class RegistrarRecepcionDeRecursos extends javax.swing.JInternalFrame implements ICallBackGen {

    public static final String CALLBACK_SELECCION_ORDENDECOMPRA = "SelecciÃƒÂ³nOrdenDeCompra";
    
    private OrdenDeCompra ordenDeCompra;
    
    /**
     * Creates new form RegistrarRecepcionDeRecursos
     */
    public RegistrarRecepcionDeRecursos() {
        initComponents();
        initCellListener();
        initAnchoColumnasTablaDetalles();
    }

    /**
     * Creates new form RegistrarRecepcionDeRecursos
     */
    public RegistrarRecepcionDeRecursos(int idOrdenDeCompra) {
        initComponents();
        initAnchoColumnasTablaDetalles();
        initCellListener();
        cargarOrdenDeCompra(idOrdenDeCompra);
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtOrdenDeCompra = new javax.swing.JTextField();
        btnSeleccionarOrdenDeCompra = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblFechaEmision = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblFormaDePago = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblProveedor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblFormaDeEntrega = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblEstadoRecepcion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        btnCerrar = new javax.swing.JButton();
        btnRegistrarRecepcion = new javax.swing.JButton();
        btnEmitir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Recepción de Recursos");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Orden de Compra:");

        txtOrdenDeCompra.setBackground(new java.awt.Color(204, 204, 204));
        txtOrdenDeCompra.setEditable(false);

        btnSeleccionarOrdenDeCompra.setText("...");
        btnSeleccionarOrdenDeCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarOrdenDeCompraActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha de Emisión:");

        lblFechaEmision.setText("...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Fecha de Modificación:");

        lblFechaModificacion.setText("...");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Forma de Pago:");

        lblFormaDePago.setText("...");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Estado de la Orden:");

        lblEstado.setText("...");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Proveedor:");

        lblProveedor.setText("...");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Forma de Entrega:");

        lblFormaDeEntrega.setText("...");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Estado de la Recepción:");

        lblEstadoRecepcion.setText("...");
        lblEstadoRecepcion.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblFormaDeEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFechaEmision, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFormaDePago, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaModificacion, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEstadoRecepcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblFechaEmision)
                    .addComponent(jLabel4)
                    .addComponent(lblFechaModificacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblFormaDePago)
                    .addComponent(jLabel8)
                    .addComponent(lblEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblFormaDeEntrega)
                    .addComponent(jLabel5)
                    .addComponent(lblEstadoRecepcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblProveedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de la compra:"));

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Total Ordenados", "Total Recibidos", "Recibidos", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDetalle);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Ingrese la cantidad \"Recibida\" en este envío (no la total)");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Observaciones:");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        txtObservaciones.setText("Recibido según lo acordado!");
        jScrollPane2.setViewportView(txtObservaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnRegistrarRecepcion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnRegistrarRecepcion.setText("Registrar Recepción");
        btnRegistrarRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarRecepcionActionPerformed(evt);
            }
        });

        btnEmitir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/print.png"))); // NOI18N
        btnEmitir.setText("Emitir");
        btnEmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmitirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOrdenDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnSeleccionarOrdenDeCompra))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRegistrarRecepcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmitir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOrdenDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccionarOrdenDeCompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnRegistrarRecepcion)
                    .addComponent(btnEmitir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarOrdenDeCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarOrdenDeCompraActionPerformed
        
        String filtro = "SC.estado = '"+OrdenDeCompra.ESTADO_EMITIDA+"'";
        PantallaConsultarOrdenesDeCompra win = new PantallaConsultarOrdenesDeCompra(filtro);
        win.setSeleccionarEnabled(this,RegistrarRecepcionDeRecursos.CALLBACK_SELECCION_ORDENDECOMPRA);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_btnSeleccionarOrdenDeCompraActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnRegistrarRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarRecepcionActionPerformed
        
        if(this.ordenDeCompra!=null)
        {
            if(aceptaRegistracion())
            {
                RecepcionOrdenDeCompra roc = registrarNuevaRecepcion();
                if(roc!=null)
                {
                    ajustarNivelesDeStock(roc);
                }
            }
        }
        else
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Para registrar un recepciÃ³n, debe cargar una Orden De Compra previamente");
        }
    }//GEN-LAST:event_btnRegistrarRecepcionActionPerformed

    private void btnEmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirActionPerformed
        if(!this.ordenDeCompra.getRecepciones().isEmpty())
        {
            emitirRecepcionesOrdenDeCompra();
        }
        else
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Noy hay recepciones registradas");
        }
    }//GEN-LAST:event_btnEmitirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEmitir;
    private javax.swing.JButton btnRegistrarRecepcion;
    private javax.swing.JButton btnSeleccionarOrdenDeCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoRecepcion;
    private javax.swing.JLabel lblFechaEmision;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblFormaDeEntrega;
    private javax.swing.JLabel lblFormaDePago;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtOrdenDeCompra;
    // End of variables declaration//GEN-END:variables

    private void initAnchoColumnasTablaDetalles() {
        int anchoColumna = 0; 
        TableColumnModel modeloColumna = tblDetalle.getColumnModel(); 
        TableColumn columnaTabla; 
        for (int i = 0; i < tblDetalle.getColumnCount(); i++) { 
            columnaTabla = modeloColumna.getColumn(i); 
            switch(i){ 
                case 0: anchoColumna = 500; 
                        break; 
                case 1: anchoColumna = 150; 
                        break; 
                case 2: anchoColumna = 150; 
                        break; 
                case 3: anchoColumna = 150; 
                        break; 
                case 4: anchoColumna = 50; 
                        break;                     
            }                      
            columnaTabla.setPreferredWidth(anchoColumna); 
            columnaTabla.setWidth(anchoColumna);
        } 
    }

    @Override
    public void actualizar(int id, String flag, Class tipo) {
        if(CALLBACK_SELECCION_ORDENDECOMPRA.equals(flag))
        {
            if(tipo == OrdenDeCompra.class)
            {
                cargarOrdenDeCompra(id);
            }
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

    private void mostrarDatosOrdenDeCompra() {
        if(this.ordenDeCompra!=null)
        {
            txtOrdenDeCompra.setText(""+this.ordenDeCompra.getId());
            lblFechaEmision.setText(FechaUtil.getFecha(this.ordenDeCompra.getFechaDeGeneracion()));
            lblFechaModificacion.setText(FechaUtil.getFecha(this.ordenDeCompra.getFechaUltimaModificacion()));
            
            if(this.ordenDeCompra.getFormaDePago()!=null)
            {
                lblFormaDePago.setText(this.ordenDeCompra.getFormaDePago().getNombre());
            }
            else
            {
                lblFormaDePago.setText("");
            }
            
            lblEstado.setText(this.ordenDeCompra.getEstado());
            if(this.ordenDeCompra.getProveedor()!=null)
            {
                lblProveedor.setText(this.ordenDeCompra.getProveedor().toString());
            }
            else
            {
                lblProveedor.setText("");
            }
            lblFormaDeEntrega.setText(this.ordenDeCompra.getFormaDeEntrega());
        }
        
        actualizarEstadoRecepcion();
        
    }

    private void cargarOrdenDeCompra(int idOrdenDeCompra) {
        try
        {
            HibernateUtil.beginTransaction();
            this.ordenDeCompra = (OrdenDeCompra)HibernateUtil.getSession().load(OrdenDeCompra.class, idOrdenDeCompra);
            HibernateUtil.commitTransaction();

            if(this.ordenDeCompra!=null)
            {
                // Lleno los datos Generales
                mostrarDatosOrdenDeCompra();
                // Lleno la tabla de Detalles
                mostrarDetalleDeCompra();
            }
            
        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la Orden De Compra seleccionada\n"+e.getMessage());
            e.printStackTrace();
            return;
        }  
    }

    private void mostrarDetalleDeCompra() {
       // Vacio la Tabla
       DefaultTableModel modelo = (DefaultTableModel)tblDetalle.getModel();
       TablaUtil.vaciarDefaultTableModel(modelo); 
        
       // La lleno de nuevo
        if(this.ordenDeCompra!=null && this.ordenDeCompra.getDetalle()!=null){
         
            for (int i = 0; i < this.ordenDeCompra.getDetalle().size(); i++) {
                DetalleOrdenDeCompra doc = this.ordenDeCompra.getDetalle().get(i);
                Object[] fila = new Object[5];
                
                    Tupla tp = new Tupla(doc.getId(),doc.getItem().getNombre()+"-"+doc.getDescripcion());
                
                    fila[0] = tp;
                    fila[1] = doc.getCantidad();
                    fila[2] = this.ordenDeCompra.getCantidadTotalRecibida(doc);
                    fila[3] = 0D;
                    fila[4] = false;
                    
                modelo.addRow(fila);
            }
        }
    }

    /**
     * Registrar Recepcion Parcial/Total:.
     * Crea un nuevo objeto de recepcion
     * Lo agrega a la lista
     * Actualiza el detalle
     * Actualiza el estado de la orden de compra (si es necesario)
     */
    private RecepcionOrdenDeCompra registrarNuevaRecepcion() {
        
        // Creo el objeto Recepcion
        RecepcionOrdenDeCompra roc = new RecepcionOrdenDeCompra();
        roc.setFechaRecepcion(new Date());
        roc.setObservaciones(txtObservaciones.getText());
        
        // lleno el Detalle
        DefaultTableModel modelo = (DefaultTableModel)tblDetalle.getModel();
        for (int i = 0; i <  modelo.getRowCount(); i++) {
            Double cantidadRecibida = (Double) modelo.getValueAt(i,3);
            
            // Si la cantidad recibida es mayor a cero
            if(cantidadRecibida>0)
            {
                // Busco su correspondiente DetalleDeOrdenDeCompra
                Tupla tp = (Tupla) modelo.getValueAt(i,0);
                DetalleOrdenDeCompra doc = getDetalleOrdenDeCompraFromId(tp.getId());
                if(doc!=null)
                {
                    DetalleRecepcionOrdenDeCompra droc = new DetalleRecepcionOrdenDeCompra();
                    droc.setCantidad(cantidadRecibida);
                    droc.setDetalleOrdenDeCompra(doc);
                    roc.getRecepcionesParciales().add(droc);
                }
                else
                {
                    mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar uno de los detalles de la Orden de compra");
                    return null;
                }
            }
        }
        
        // Si no recibi nada, aviso y retorno
        if(roc.getRecepcionesParciales().isEmpty())
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","<HTML>Debe ingresar las <b>Cantidades Recibidas</b> de cada material <br>en la columna <b>'Recibidos'</b> o bien seleccionar la totalidad <br>de la entrega en la primer columna de cada detalle ");
            return null;
        }
        
        // Veo el Estado que tengo que asignarle
        if(esRecepcionTotal())
        {
            // Se recibiÃƒÂ³ todo o mas (TOTAL)
            roc.setEstado(RecepcionOrdenDeCompra.ESTADO_RECIBIDA_TOTALMENTE);
        }
        else
        {
            // Faltan cosas por recibir (PARCIAL)
            roc.setEstado(RecepcionOrdenDeCompra.ESTADO_RECIBIDA_PARCIALMENTE);
        }
        
        // Actualizo la orden de compra
        this.ordenDeCompra.getRecepciones().add(roc);
        
        // Guardo !!!
        try
        {
            HibernateUtil.beginTransaction();
                HibernateUtil.getSession().saveOrUpdate(roc);
                for (int i = 0; i < roc.getRecepcionesParciales().size(); i++) {
                    DetalleRecepcionOrdenDeCompra droc = roc.getRecepcionesParciales().get(i);
                    HibernateUtil.getSession().saveOrUpdate(droc);
                }
                HibernateUtil.getSession().saveOrUpdate(this.ordenDeCompra);
            HibernateUtil.commitTransaction();
        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al Registrar la RecepciÃƒÂ³n Parcial del Pedido\n"+e.getMessage());
            e.printStackTrace();
            return null;
        } 
        
        // Todo fue excelente !!!
        mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,"Exito!","La recepciÃƒÂ³n de recursos se registro exitosamente!");
        // Actualizo la tabla de detalles
        mostrarDetalleDeCompra();
        // Actualizo el estado de la Recepcion
        actualizarEstadoRecepcion();
        // Se guardo exitosamente!
        return roc;
    }

    private DetalleOrdenDeCompra getDetalleOrdenDeCompraFromId(int id) {
        for (int i = 0; i < this.ordenDeCompra.getDetalle().size(); i++) {
            DetalleOrdenDeCompra doc = this.ordenDeCompra.getDetalle().get(i);
            if(doc.getId()==id)
            {
                return doc;
            }
        }
        return null;
    }
    
    /**
     * Analiza la tabla para ver si la recepcion es Parcial o Total.
     * Para cada una de sus filas, el:
     * (Total + Recibidos ) >= ordenadas
     * @return 
     */
    private boolean esRecepcionTotal() {
        DefaultTableModel modelo = (DefaultTableModel)tblDetalle.getModel();
        for (int i = 0; i <  modelo.getRowCount(); i++) {
            double cantidadRecibida = (Double) modelo.getValueAt(i,3);
            double totalRecibida    = (Double) modelo.getValueAt(i,2);
            double ordenadas        = (Double) modelo.getValueAt(i,1);
            if((totalRecibida+cantidadRecibida)<ordenadas)
            {
                return false;
            }    
        }
        return true; // Si termina es Total
    }

    /**
     * TODO, el check solo suma si faltan items en la compra !!!
     */
    private void initCellListener() {

        tblDetalle.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                
                System.out.println("Table Event: Value changed!!");
                System.out.println("E.column"+e.getColumn());
                System.out.println("E.type"+e.getType());
                System.out.println("E.frow"+e.getFirstRow());
                System.out.println("E.lrow"+e.getLastRow());
                // Si cambio la primer fila, altero entre recibido totalmente y cero
                if(e.getColumn()==4)
                {
                    // Del modelo, saco el calor actual !!
                    DefaultTableModel modelo = (DefaultTableModel)tblDetalle.getModel();
                    boolean selected = (Boolean)modelo.getValueAt(e.getFirstRow(),4);
                    if(selected)
                    {
                        System.out.println("SE SELECCIONO");
                        double odenado = (Double)modelo.getValueAt(e.getFirstRow(),1);
                        double recibido = (Double)modelo.getValueAt(e.getFirstRow(),2);
                        modelo.setValueAt((odenado-recibido),e.getFirstRow(),3);
                    }
                    else
                    {
                        System.out.println("SE DES-SELECCIONO");
                        modelo.setValueAt(0D,e.getFirstRow(),3);
                    }
                }
            }
        });
    }

    /**
     * Actualiza el combo de Estado de la Recepcion.
     * Tambien pinta con los colores caracteristicos
     * 
     */
    private void actualizarEstadoRecepcion() {
        // Pongo el Estado
        if(this.ordenDeCompra.getRecepciones()!=null)
        {
            String estado =  this.ordenDeCompra.getEstadoRecepciones();
            lblEstadoRecepcion.setText(estado);
            
            // Switch por color
            if(RecepcionOrdenDeCompra.ESTADO_PENDIENTE.equals(estado))
            {
                lblEstadoRecepcion.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_PENDIENTE);
            }
            if(RecepcionOrdenDeCompra.ESTADO_RECIBIDA_PARCIALMENTE.equals(estado))
            {
                lblEstadoRecepcion.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_RECIBIDA_PARCIALMENTE);
            }
            if(RecepcionOrdenDeCompra.ESTADO_RECIBIDA_TOTALMENTE.equals(estado))
            {
                lblEstadoRecepcion.setBackground(RecepcionOrdenDeCompra.ESTADO_COLOR_RECIBIDA_TOTALMENTE);
            }            
        }
    }

    /**
     * Un dialogo muy informativo para saber si desea aceptar o no la registracion
     * @return 
     */
    private boolean aceptaRegistracion() {
        
        StringBuilder msg = new StringBuilder("<HTML>");
        msg.append("Esta a punto de registrar la recepción de:<br>");
        
            DefaultTableModel modelo = (DefaultTableModel)tblDetalle.getModel();
            for (int i = 0; i <  modelo.getRowCount(); i++) {
                double total = (Double) modelo.getValueAt(i,1);
                double recibida = (Double) modelo.getValueAt(i,3);
                Tupla nombre  = (Tupla)modelo.getValueAt(i,0);
                
                if(recibida>0)
                {
                    msg.append("- ");
                    msg.append("<b>");
                    msg.append(recibida);
                    msg.append("</b> / ");
                    msg.append(total);
                    msg.append(" de ");
                    msg.append(nombre.getNombre());
                    msg.append("<br>");
                }
            }
        
        msg.append("¿Está seguro que desea continuar?");
        
        int seleccion = JOptionPane.showOptionDialog(
            this, // Componente padre
            msg.toString(), //Mensaje
            "Seleccione una opción", // Título
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,    // null para icono por defecto.
            new Object[] { "Si", "No"},    // null para YES, NO y CANCEL
            "Si");

            if (seleccion != -1)
            {
                if((seleccion + 1)==1)
                {
                    // PRESIONO SI
                    return true;
                }
                else
                {
                    // PRESIONO NO
                    return false;
                }
            }
            return false;
    }

    /**
     * Con lo que se recibió, ajusta los niveles de Stock de la empresa.
     */
    private void ajustarNivelesDeStock(RecepcionOrdenDeCompra roc) {
        
        AjustarNivelesDeStock win = new AjustarNivelesDeStock(roc);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
        
    }
    
    
    /**
     * Emite la recepción de orden de compra en curso
     */
    private void emitirRecepcionesOrdenDeCompra() {
        
        Iterator<RecepcionOrdenDeCompra> itRecepciones = this.ordenDeCompra.getRecepciones().iterator();
        while(itRecepciones.hasNext())
        {
            RecepcionOrdenDeCompra recepcion = itRecepciones.next();
            EmitirRecepcionOrdenDeCompra informe = new EmitirRecepcionOrdenDeCompra(ordenDeCompra,recepcion);
            informe.setNombreReporte("Recepción Orden de Compra : "+recepcion.getId());
            informe.setNombreArchivo("Recepción Compras-"+recepcion.getId(),ReportDesigner.REPORTE_TIPO_COMPRAS);

            try 
            {
                informe.makeAndShow(new HashMap<String,Object>());
            } catch (DocumentException ex) {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo crear el informe\nVerifique los datos e intentelo nuevamente");
            } catch (FileNotFoundException ex) {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo crear el archivo donde guardar el informe");
            }                   
        }
    }
    
}