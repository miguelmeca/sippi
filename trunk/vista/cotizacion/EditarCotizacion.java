/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * modificarPresupuesto.java
 *
 * Created on 24/04/2011, 15:49:18
 */

package vista.cotizacion;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author iuga
 */
public class EditarCotizacion extends javax.swing.JInternalFrame {

    private boolean necesita_guardar = true;

    private static final int OPTN_RRHH                  = 0;
    private static final int OPTN_HERRAMIENTAS          = 1;
    private static final int OPTN_MATERIALES            = 2;
    private static final int OPTN_ALQUILERES_COMPRAS    = 3;
    private static final int OPTN_BENEFICIOS            = 4;
    private static final int OPTN_ADICIONALES           = 5;
    private static final int OPTN_DESCRIPCION           = 6;

    /** Creates new form modificarPresupuesto */
    public EditarCotizacion() {

        initComponents();

        // POR DEFAULT VA DESCRIPCION DEL ITEM
        DefaultTableModel modelo = (DefaultTableModel)tblMenu.getModel();

        setNombrePanel(modelo.getValueAt(OPTN_DESCRIPCION, 0).toString());
        CotizacionDescripcion ecd = new CotizacionDescripcion();
        panel.setViewportView(ecd);
        ecd.setVisible(true);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGeneral = new javax.swing.JPanel();
        panel = new javax.swing.JScrollPane();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cotización");

        panelGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));

        panel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
        );
        panelGeneralLayout.setVerticalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        btnGuardar.setText("Guardar Cambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("SubTotal"));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 300));

        jLabel3.setText("Monto Total:");

        jLabel4.setBackground(new java.awt.Color(251, 250, 241));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("$2500");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(252, 239, 161)));
        jLabel4.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Menú de Recursos"));
        jPanel3.setMaximumSize(new java.awt.Dimension(50, 50));

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Recursos Humanos"},
                {"Herramientas"},
                {"Materiales"},
                {"Alquileres/Compras"},
                {"Beneficios"},
                {"Adicionales"},
                {"Descripción"}
            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMenu.setIntercellSpacing(new java.awt.Dimension(10, 5));
        tblMenu.setMaximumSize(new java.awt.Dimension(300, 300));
        tblMenu.setRowHeight(25);
        tblMenu.setTableHeader(null);
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMenuMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        cerrarVentana();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMousePressed

        DefaultTableModel modelo = (DefaultTableModel)tblMenu.getModel();
        switch(tblMenu.getSelectedRow())
        {
            case OPTN_BENEFICIOS:
                setNombrePanel(modelo.getValueAt(OPTN_BENEFICIOS,0).toString());
                CotizacionBeneficios ecb = new CotizacionBeneficios();
                panel.setViewportView(ecb);
                ecb.setVisible(true);
                break;
            case OPTN_MATERIALES:
                setNombrePanel(modelo.getValueAt(OPTN_MATERIALES,0).toString());
                CotizacionMateriales cm = new CotizacionMateriales();
                panel.setViewportView(cm);
                cm.setVisible(true);
                break;
            case OPTN_ALQUILERES_COMPRAS:
                setNombrePanel(modelo.getValueAt(OPTN_ALQUILERES_COMPRAS,0).toString());
                CotizacionAlquileresCompras cc = new CotizacionAlquileresCompras();
                panel.setViewportView(cc);
                cc.setVisible(true);
                break;
            case OPTN_ADICIONALES:
                setNombrePanel(modelo.getValueAt(OPTN_ADICIONALES,0).toString());
                CotizacionAdicionales ca = new CotizacionAdicionales();
                panel.setViewportView(ca);
                ca.setVisible(true);
                break;
            case OPTN_RRHH:
                setNombrePanel(modelo.getValueAt(OPTN_RRHH,0).toString());
                CotizacionManoDeObraGeneral mo = new CotizacionManoDeObraGeneral();
                panel.setViewportView(mo);
                mo.setVisible(true);
                break;
            case OPTN_HERRAMIENTAS:
                setNombrePanel(modelo.getValueAt(OPTN_HERRAMIENTAS, 0).toString());
                CotizacionHerramientas h = new CotizacionHerramientas();
                panel.setViewportView(h);
                h.setVisible(true);
                break;
            case OPTN_DESCRIPCION:
                setNombrePanel(modelo.getValueAt(OPTN_DESCRIPCION, 0).toString());
                CotizacionDescripcion ecd = new CotizacionDescripcion();
                panel.setViewportView(ecd);
                ecd.setVisible(true);
                break;
            default:
                setNombrePanel(modelo.getValueAt(OPTN_DESCRIPCION, 0).toString());
                CotizacionDescripcion ecd2 = new CotizacionDescripcion();
                panel.setViewportView(ecd2);
                ecd2.setVisible(true);
        }


    }//GEN-LAST:event_tblMenuMousePressed


    private void setNombrePanel(String nombre)
    {
         panelGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(nombre));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane panel;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JTable tblMenu;
    // End of variables declaration//GEN-END:variables


    private void cerrarVentana()
    {
        if(necesita_guardar)
        {
            int btn = JOptionPane.showConfirmDialog(this,"¿Está seguro que desea cerrar sin guardar los cambios?","Atención",JOptionPane.YES_NO_OPTION);
            switch(btn)
            {
                case JOptionPane.YES_OPTION:
                    this.dispose();
                    break;
            }
        }
        else
        {
            this.dispose();
        }
    }

    private void guardar()
    {
        int btn = JOptionPane.showConfirmDialog(this,"¿Está seguro que desea guardar los cambios?","Atención",JOptionPane.YES_NO_OPTION);
        if(btn==JOptionPane.YES_OPTION)
        {
            // GUARDO
        }
    }

}
