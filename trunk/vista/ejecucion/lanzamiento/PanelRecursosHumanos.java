/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ejecucion.lanzamiento;

import controlador.ejecucion.lanzamiento.GestorVentanaLanzamiento;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.NTupla;
import util.SwingPanel;
import util.TablaUtil;
import util.Tupla;
import vista.interfaces.ICallBack;
import vista.rrhh.pantallaConsultarDatosEmpleado;
import vista.rrhh.pantallaConsultarLicenciasEmpleado;
import vista.rrhh.pantallaRegistrarAltaEmpleado;
import vista.rrhh.pantallaRegistrarBajaEmpleado;
import vista.rrhh.pantallaRegistrarEmpleado;
import vista.util.MyComboBoxEditor;

/**
 *
 * @author Administrador
 */
public class PanelRecursosHumanos extends javax.swing.JPanel implements ICallBack {

    public static final int TABLA_RRHH_COLUMNA_NOMBRE = 0;
    public static final int TABLA_RRHH_COLUMNA_HSN = 1;
    public static final int TABLA_RRHH_COLUMNA_HS50 = 2;
    public static final int TABLA_RRHH_COLUMNA_HS100 = 3;
    public static final int TABLA_RRHH_COLUMNA_ESTADO = 4;
    public static final int TABLA_RRHH_COLUMNA_SELECCION = 5;
    
    private static final int TABLA_DEFAULT_ALTO = 25;    
        
    private GestorVentanaLanzamiento gestor;
    
    /**
     * Creates new form PanelRecursosHumanos
     */
    public PanelRecursosHumanos(GestorVentanaLanzamiento gestor) {
        this.gestor = gestor;
        initComponents();
        initTabla();
        cargarDatosTablaParaObra();
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
        jPanel1 = new javax.swing.JPanel();
        btnConsultarEmpleado = new javax.swing.JButton();
        btnLicencias = new javax.swing.JButton();
        btnBajaEmpleado = new javax.swing.JButton();
        btnAltaEmpleado = new javax.swing.JButton();

        tblRRHH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre del empleado", "Horas Normales", "Horas 50%", "Horas 100%", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRRHH.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRRHH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblRRHHMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblRRHH);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones sobre el Empleado:"));

        btnConsultarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        btnConsultarEmpleado.setText("ConsultarEmpleado");
        btnConsultarEmpleado.setEnabled(false);
        btnConsultarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEmpleadoActionPerformed(evt);
            }
        });

        btnLicencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnLicencias.setText("Licencias");
        btnLicencias.setEnabled(false);
        btnLicencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLicenciasActionPerformed(evt);
            }
        });

        btnBajaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete_page.png"))); // NOI18N
        btnBajaEmpleado.setText("Dar de Baja");
        btnBajaEmpleado.setEnabled(false);
        btnBajaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaEmpleadoActionPerformed(evt);
            }
        });

        btnAltaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept_page.png"))); // NOI18N
        btnAltaEmpleado.setText("Dar de alta");
        btnAltaEmpleado.setEnabled(false);
        btnAltaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnConsultarEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLicencias, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAltaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBajaEmpleado))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnConsultarEmpleado)
                .addComponent(btnLicencias)
                .addComponent(btnBajaEmpleado)
                .addComponent(btnAltaEmpleado))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBajaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaEmpleadoActionPerformed
        if(tblRRHH.getSelectedRow()!=-1)
        {
            int id;
            id=((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getId();
            pantallaRegistrarBajaEmpleado pre = new pantallaRegistrarBajaEmpleado(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
        }
    }//GEN-LAST:event_btnBajaEmpleadoActionPerformed

    private void btnAltaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaEmpleadoActionPerformed
        if(tblRRHH.getSelectedRow()!=-1)
        {
            int id;
            id=((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getId();
            pantallaRegistrarAltaEmpleado pre = new pantallaRegistrarAltaEmpleado(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);

        }
    }//GEN-LAST:event_btnAltaEmpleadoActionPerformed

    private void btnLicenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLicenciasActionPerformed
        if(tblRRHH.getSelectedRow()!=-1)
        {
            int id;
            id=((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getId();

            pantallaConsultarLicenciasEmpleado pcle = new pantallaConsultarLicenciasEmpleado();
            SwingPanel.getInstance().addWindow(pcle);
            pcle.filtrarPorEmpleado(id);
            pcle.setVisible(true);
        }
    }//GEN-LAST:event_btnLicenciasActionPerformed

    private void tblRRHHMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRRHHMouseReleased
       if(tblRRHH.getSelectedRow()!=-1)
       {
           int id;
           id=((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getId();
           btnConsultarEmpleado.setEnabled(true);
           btnLicencias.setEnabled(true);
           if(!gestor.esEmpleadoEnBaja(id))
           {btnBajaEmpleado.setEnabled(true);}
           else
           {btnBajaEmpleado.setEnabled(false);}

           if(gestor.esEmpleadoEnBaja(id))
           {btnAltaEmpleado.setEnabled(true);}
           else
           {btnAltaEmpleado.setEnabled(false);}
           if (evt.getClickCount() == 2)
            {

            pantallaConsultarDatosEmpleado pre = new pantallaConsultarDatosEmpleado(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
               
            }
        }
       else
       {
           btnConsultarEmpleado.setEnabled(false);
           btnLicencias.setEnabled(false);
           btnBajaEmpleado.setEnabled(false);
           btnAltaEmpleado.setEnabled(false);
       }
    }//GEN-LAST:event_tblRRHHMouseReleased

    private void btnConsultarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEmpleadoActionPerformed
        if(tblRRHH.getSelectedRow()!=-1)
        {
            int id;
            id=((NTupla)(tblRRHH.getModel().getValueAt(tblRRHH.getSelectedRow(), 0))).getId();
            pantallaConsultarDatosEmpleado pre = new pantallaConsultarDatosEmpleado(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);

        }
    }//GEN-LAST:event_btnConsultarEmpleadoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltaEmpleado;
    private javax.swing.JButton btnBajaEmpleado;
    private javax.swing.JButton btnConsultarEmpleado;
    private javax.swing.JButton btnLicencias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRRHH;
    // End of variables declaration//GEN-END:variables

    private void initTabla() {
        tblRRHH.setRowHeight(TABLA_DEFAULT_ALTO);
        DefaultTableModel modelo = (DefaultTableModel) tblRRHH.getModel();

        // Ancho de Columnas
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tblRRHH.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < tblRRHH.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch (i) {
                case TABLA_RRHH_COLUMNA_NOMBRE:
                    anchoColumna = 300;
                    break;
                case TABLA_RRHH_COLUMNA_HSN:
                    anchoColumna = 100;
                    break;
                case TABLA_RRHH_COLUMNA_HS50:
                    anchoColumna = 100;
                    break;
                case TABLA_RRHH_COLUMNA_HS100:
                    anchoColumna = 100;
                    break; 
                case TABLA_RRHH_COLUMNA_ESTADO:
                    anchoColumna = 200;
                    break;                        
                case TABLA_RRHH_COLUMNA_SELECCION:
                    anchoColumna = 50;
                    break;
            }
            columnaTabla.setPreferredWidth(anchoColumna);
            columnaTabla.setWidth(anchoColumna);
        } 
    }
    
    /**
     * Pide al gestor que busque todos los datos de los empleados que van a 
     * trabajar en esta obra y su estado.
     */
    private void cargarDatosTablaParaObra() {
        List<NTupla> tuplas = gestor.llenarTablaPanelRRHH();
        DefaultTableModel modelo = (DefaultTableModel)tblRRHH.getModel();
        TablaUtil.vaciarDefaultTableModel(modelo);
        
        for (int i = 0; i < tuplas.size(); i++) {
            NTupla nTupla = tuplas.get(i);
            Object[] fila = new Object[6];
            fila[TABLA_RRHH_COLUMNA_NOMBRE] = nTupla;
            
                String[] data = (String[]) nTupla.getData();
            
                fila[TABLA_RRHH_COLUMNA_HSN] = data[0];
                fila[TABLA_RRHH_COLUMNA_HS50] = data[1];
                fila[TABLA_RRHH_COLUMNA_HS100] = data[2];
                fila[TABLA_RRHH_COLUMNA_ESTADO] = data[3];

                fila[TABLA_RRHH_COLUMNA_SELECCION] = false;
                
            modelo.addRow(fila);
        }
    }    

    @Override
    public void actualizar(int flag, boolean exito) {
        cargarDatosTablaParaObra();
    }
}
