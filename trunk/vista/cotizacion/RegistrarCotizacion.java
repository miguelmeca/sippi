/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * registrarCotizacion.java
 *
 * Created on 02-jun-2011, 9:43:15
 */

package vista.cotizacion;

import util.SwingPanel;
import controlador.cotizacion.GestorRegistrarCotizacion;
import javax.swing.JOptionPane;
import vista.interfaces.ICallBack_v2;

/**
 *
 * @author Administrador
 */
public class RegistrarCotizacion extends javax.swing.JInternalFrame implements ICallBack_v2
{

    /** Creates new form registrarCotizacion */
    private GestorRegistrarCotizacion gestor;
    private int idCotizacionSeleccionadaParaCopia;
    public RegistrarCotizacion(int po_id) 
    {
        initComponents();
        gestor = new GestorRegistrarCotizacion(this, po_id);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rbtnCotizacionBlanco = new javax.swing.JRadioButton();
        lblCotizacionBlanco = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbtnCotizacionExistente = new javax.swing.JRadioButton();
        lblCotizacionExistente = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCotizacionSeleccionda = new javax.swing.JTextField();
        btnExplorarCotizaciones = new javax.swing.JButton();
        bntNuevaCotizacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Registrar Cotizacion");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rbtnCotizacionBlanco);

        lblCotizacionBlanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/vacio.png"))); // NOI18N
        lblCotizacionBlanco.setText("Cotización en Blanco");
        lblCotizacionBlanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCotizacionBlancoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnCotizacionBlanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCotizacionBlanco)
                .addContainerGap(284, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCotizacionBlanco))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(rbtnCotizacionBlanco)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rbtnCotizacionExistente);
        rbtnCotizacionExistente.setSelected(true);

        lblCotizacionExistente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/sistema.png"))); // NOI18N
        lblCotizacionExistente.setText("Cotización a partir de una ya existente");
        lblCotizacionExistente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCotizacionExistenteMouseClicked(evt);
            }
        });

        jLabel4.setText("Cotización:");

        txtCotizacionSeleccionda.setEditable(false);
        txtCotizacionSeleccionda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCotizacionSelecciondaActionPerformed(evt);
            }
        });

        btnExplorarCotizaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        btnExplorarCotizaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExplorarCotizacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnCotizacionExistente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCotizacionExistente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCotizacionSeleccionda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExplorarCotizaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(rbtnCotizacionExistente))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCotizacionExistente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCotizacionSeleccionda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExplorarCotizaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bntNuevaCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        bntNuevaCotizacion.setText("Crear");
        bntNuevaCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNuevaCotizacionActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bntNuevaCotizacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntNuevaCotizacion)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void bntNuevaCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNuevaCotizacionActionPerformed

        int nuevoId=-1;
        if(rbtnCotizacionBlanco.isSelected())
        {
            nuevoId=gestor.crearCotizacionNueva();
            
        }
        else
        {
           if(idCotizacionSeleccionadaParaCopia>0)
           {   
                nuevoId=gestor.crearCotizacionAPartirExistente(idCotizacionSeleccionadaParaCopia);               
           }
           else
           {
               JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una cotizacion existnte","Error",JOptionPane.ERROR_MESSAGE);
               return;
           }
        }
        if(nuevoId<0)
        {
         JOptionPane.showMessageDialog(this.getParent(),"Ocurrió un error en el registro de la cotización","Error",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          ExplorarSubObras mod =  new ExplorarSubObras(nuevoId);
          SwingPanel.getInstance().addWindow(mod);
          mod.setVisible(true);
        }
        
        

    }//GEN-LAST:event_bntNuevaCotizacionActionPerformed

    private void btnExplorarCotizacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExplorarCotizacionesActionPerformed

        ExplorarCotizaciones ec = new ExplorarCotizaciones(this);
        ec.setTipo(ExplorarCotizaciones.TIPO_EXPLORAR_ONLY);
        SwingPanel.getInstance().addWindow(ec);
        ec.setVisible(true);

    }//GEN-LAST:event_btnExplorarCotizacionesActionPerformed

    private void txtCotizacionSelecciondaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotizacionSelecciondaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCotizacionSelecciondaActionPerformed

private void lblCotizacionExistenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCotizacionExistenteMouseClicked
   rbtnCotizacionExistente.setSelected(true);
}//GEN-LAST:event_lblCotizacionExistenteMouseClicked

private void lblCotizacionBlancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCotizacionBlancoMouseClicked
    rbtnCotizacionBlanco.setSelected(true);
}//GEN-LAST:event_lblCotizacionBlancoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntNuevaCotizacion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExplorarCotizaciones;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCotizacionBlanco;
    private javax.swing.JLabel lblCotizacionExistente;
    private javax.swing.JRadioButton rbtnCotizacionBlanco;
    private javax.swing.JRadioButton rbtnCotizacionExistente;
    private javax.swing.JTextField txtCotizacionSeleccionda;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actualizar(int id, String CU, boolean exito)
    {
        idCotizacionSeleccionadaParaCopia=id;
        txtCotizacionSeleccionda.setText(gestor.getNombreCotizacion(id));
    }
}
