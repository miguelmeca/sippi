/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ejecucion;

import com.toedter.calendar.JDateChooser;
import controlador.ejecucion.GestorEjecucion;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.DetalleTareaEjecucion;
import modelo.DetalleTareaEjecucionXDia;
import sun.rmi.runtime.Log;
import util.NTupla;
import util.SwingPanel;
import vista.interfaces.ICallBack_v3;
import vista.util.EditableCellTableRenderer;
import vista.util.TableCellListener;

/**
 *
 * @author Administrador
 */
public class PanelRecursosHumanos extends javax.swing.JPanel implements ICallBack_v3{

    public static final int TABLA_RRHH_COLUMNA_EMPLEADO = 0;
    public static final int TABLA_RRHH_COLUMNA_TAREA = 1;
    public static final int TABLA_RRHH_COLUMNA_FECHA = 2;
    public static final int TABLA_RRHH_COLUMNA_HS_NORMALES = 3;
    public static final int TABLA_RRHH_COLUMNA_HS_50 = 4;
    public static final int TABLA_RRHH_COLUMNA_HS_100 = 5;    
    private static final int TABLA_DEFAULT_ALTO = 25;  
    
    private List<Object> listaRRHH;
    private DefaultTableModel model;
    private boolean primeraVez;
    private boolean filtroBuscarActivado;
    boolean filtroFechaDesdeActivado;
    boolean filtroFechaHastaActivado;
    
    private GestorEjecucion gestor;
        
    public PanelRecursosHumanos(GestorEjecucion gestor) {
        this.gestor=gestor;
        initComponents();
        seterListenerPropertyChangedAJDateChooser(dcFechaInicio);
        seterListenerPropertyChangedAJDateChooser(dcFechaFin);
        initTabla();
        habilitarVentana();
    }
    
    
    
  private void habilitarVentana()
    {
        filtroBuscarActivado=false;
        filtroFechaDesdeActivado=false;
        filtroFechaHastaActivado=false;
        cargarRRHH();
        activarFiltrosTabla();  
    }
  
  
    public void activarFiltrosTabla()
    {
         TableRowSorter<TableModel> modeloOrdenado;
            modeloOrdenado = new TableRowSorter<TableModel>(model);
            tblRRHH.setRowSorter(modeloOrdenado);
        

        if(filtroBuscarActivado)
        {
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);

        }
       


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
        tblRRHH = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        dcFechaInicio = new com.toedter.calendar.JDateChooser();
        dcFechaFin = new com.toedter.calendar.JDateChooser();
        btnHoy = new javax.swing.JButton();
        btnAsignacion = new javax.swing.JButton();

        tblRRHH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empleado", "Tarea", "Fecha", "Hs Normales", "Hs al 50%", "Hs al 100%"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRRHH.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRRHH);

        jLabel5.setText("Desde:");

        jLabel6.setText("Hasta:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        btnHoy.setText("Hoy");
        btnHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoyActionPerformed(evt);
            }
        });

        btnAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png"))); // NOI18N
        btnAsignacion.setText("Cambiar Asingacion de empleado");
        btnAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHoy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnAsignacion)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscar)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(dcFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsignacion))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked

        if(txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained

        if(txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
            filtroBuscarActivado=true;
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if(txtBuscar.getText().equals("")) {
            txtBuscar.setText("Buscar...");
            txtBuscar.setForeground(Color.GRAY);
            filtroBuscarActivado=false;
        } else {
            filtroBuscarActivado=true;}
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        activarFiltrosTabla();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoyActionPerformed
        dcFechaInicio.setDate(new Date());
        dcFechaFin.setDate(new Date());
    }//GEN-LAST:event_btnHoyActionPerformed

    private void btnAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignacionActionPerformed
        if(tblRRHH.getSelectedRow()!=-1)
        {
            DetalleTareaEjecucionXDia detalleXDia=(DetalleTareaEjecucionXDia)((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getData();
            DetalleTareaEjecucion detalle=(DetalleTareaEjecucion)((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 1))).getData();
            gestor.setDetalleRRHHSeleccionado(detalle);
            gestor.setDetalleRRHHXDiaSeleccionado(detalleXDia);
            EdicionDetalleRRHH edrh = new EdicionDetalleRRHH(this, gestor);
            SwingPanel.getInstance().addWindow(edrh);
            edrh.setVisible(true);

        }
    }//GEN-LAST:event_btnAsignacionActionPerformed

    
    //Sale una bendicion al tipo q implemento la clase q solciono muchos problemas
    Action accionSobreCelda = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableCellListener tcl = (TableCellListener)e.getSource();
            int fila = tcl.getRow();
            int columna = tcl.getColumn();
            Float valor = (Float) tcl.getNewValue();
            DefaultTableModel modeloTabla = (DefaultTableModel)tblRRHH.getModel();
            DetalleTareaEjecucionXDia detalle = (DetalleTareaEjecucionXDia)((NTupla)modeloTabla.getValueAt(fila, 0)).getData();
            
            
            switch(columna)
            {
                case TABLA_RRHH_COLUMNA_HS_NORMALES:
                    detalle.setCantHorasNormales(Double.valueOf(valor));
                    break;
                case TABLA_RRHH_COLUMNA_HS_50:
                    detalle.setCantHorasAl50(Double.valueOf(valor));
                    break;
                case TABLA_RRHH_COLUMNA_HS_100:
                    detalle.setCantHorasAl100(Double.valueOf(valor));
                    break;
                default:
                    Logger.getLogger(PanelRecursosHumanos.class.getName()).log(Level.SEVERE, "ERROR EN LOS INDICES DE LAS COLUMAS DE LA TABLA");
                    mostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error", "ALGO SALIO MAL");
                    break;
                        
            }  
        }
    };

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignacion;
    private javax.swing.JButton btnHoy;
    private com.toedter.calendar.JDateChooser dcFechaFin;
    private com.toedter.calendar.JDateChooser dcFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRRHH;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    private void initTabla() {
        tblRRHH.setRowHeight(TABLA_DEFAULT_ALTO);
       tblRRHH.setDefaultRenderer(Object.class,new EditableCellTableRenderer());
        // Ancho de Columnas
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tblRRHH.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < tblRRHH.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch (i) {
                case TABLA_RRHH_COLUMNA_EMPLEADO:
                    anchoColumna = 230;
                    break;
                case TABLA_RRHH_COLUMNA_TAREA:
                    anchoColumna = 230;
                    break;
                case TABLA_RRHH_COLUMNA_FECHA:
                    anchoColumna = 90;
                    break;
                case TABLA_RRHH_COLUMNA_HS_NORMALES:
                    anchoColumna = 110;
                    break;
                case TABLA_RRHH_COLUMNA_HS_50:
                    anchoColumna = 110;
                    break;
                case TABLA_RRHH_COLUMNA_HS_100:
                    anchoColumna = 110;
                    break;    
            }
            columnaTabla.setPreferredWidth(anchoColumna);
            columnaTabla.setWidth(anchoColumna);
        }
        cambiarTamCabeceraTablas();
        TableCellListener tcl = new TableCellListener(tblRRHH, accionSobreCelda);
    }
    
    
    private void cargarRRHH()
    {
        Date fechaDesde;
        Date fechaHasta;
        fechaDesde= dcFechaInicio.getDate();
        fechaHasta= dcFechaFin.getDate();
        
        
        listaRRHH=gestor.getListaRRHH( fechaDesde,fechaHasta);
       
        model = (DefaultTableModel) tblRRHH.getModel();
        
        model.setRowCount(0);
        for (Object detalle : listaRRHH)
        {
            Object[] obj=(Object[])detalle;
            model.addRow( obj );
        }
       
        tblRRHH.setModel(model);
    

    }
    
    public void actualizar() {
        cargarRRHH();
    }
    
    private void cambiarTamCabeceraTablas()
    {
        Font fuente = new Font("Verdana", Font.PLAIN, 9);
        JTableHeader th1;
        th1 = tblRRHH.getTableHeader();
        th1.setFont(fuente); 
        
              
    }
    
    private void seterListenerPropertyChangedAJDateChooser(JDateChooser chooser){
    
        chooser.getDateEditor().addPropertyChangeListener(
        new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("date".equals(e.getPropertyName())) {
                    actualizar();
                }
            }
        });
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

    @Override
    public void actualizar(int id, String flag, boolean exito, Object[] data) {
        actualizar();
    }

}
