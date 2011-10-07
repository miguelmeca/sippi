/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * generarCotizacionExterna.java
 *
 * Created on 30-jun-2011, 9:35:41
 */

package vista.cotizacion;

import controlador.reportes.GestorReportesCotizacion;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import modelo.Cotizacion;
import util.HibernateUtil;
import util.ReporteUtil;

/**
 *
 * @author Administrador
 */
public class GenerarCotizacion extends javax.swing.JInternalFrame {
    private int cotizacionId;
    /** Creates new form generarCotizacionExterna */
    public GenerarCotizacion(int cotizacionId) 
    {
        initComponents();
        lblLoad.setVisible(false);
        this.cotizacionId = cotizacionId;
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
        btnPresupuestoSinDetalle = new javax.swing.JButton();
        btnPresupuestoExternoSubObras = new javax.swing.JButton();
        btnPresupuestoExternoRecursos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        lblLoad = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Generar Presupuesto");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Externa ( Entregar al Cliente )"));

        btnPresupuestoSinDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/empty.png"))); // NOI18N
        btnPresupuestoSinDetalle.setText("Presupuesto: Sin Detalle");
        btnPresupuestoSinDetalle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPresupuestoSinDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresupuestoSinDetalleActionPerformed(evt);
            }
        });

        btnPresupuestoExternoSubObras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/x-dia-diagram.png"))); // NOI18N
        btnPresupuestoExternoSubObras.setText("Presupuesto: Discriminado por Sub-Obras");
        btnPresupuestoExternoSubObras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPresupuestoExternoSubObras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresupuestoExternoSubObrasActionPerformed(evt);
            }
        });

        btnPresupuestoExternoRecursos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/exec.png"))); // NOI18N
        btnPresupuestoExternoRecursos.setText("Presupuesto: Discriminado por Recursos");
        btnPresupuestoExternoRecursos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPresupuestoExternoRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresupuestoExternoRecursosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPresupuestoExternoRecursos, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(btnPresupuestoExternoSubObras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPresupuestoSinDetalle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPresupuestoSinDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPresupuestoExternoSubObras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPresupuestoExternoRecursos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Interna ( Uso de la Empresa )"));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/plantillas/exec.png"))); // NOI18N
        jButton4.setText("Presupuesto: Discriminado por Sub-Obras");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        lblLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/imagenes/loader.gif"))); // NOI18N
        lblLoad.setText("Generando, Espere por favor ...");
        lblLoad.setDoubleBuffered(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblLoad, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCerrar)))
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
                    .addComponent(btnCerrar)
                    .addComponent(lblLoad))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
    hideLoading();
    this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

private void btnPresupuestoSinDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPresupuestoSinDetalleActionPerformed
    showLoading();
    if(cotizacionId>0) {
        String urlReporte = "/vista/reportes/CotizacionExternaGeneral.jasper";
        try {
            // CUANDO TERMINES, RECORDA QUE NO VA ACA, ESTO ES VISTA, PONE EL METODO EN 
            // controlador/reportes/GestorReportesCotizacion.java
            Cotizacion cot = (Cotizacion) HibernateUtil.getSession().load(Cotizacion.class, cotizacionId);

            Map params = new HashMap();
            params.put("idCot",cotizacionId);
            params.put("precioTotal", cot.CalcularTotal());
            params.put("Contacto", "Armando Paredes");

            ReporteUtil ru = new ReporteUtil();
            ru.mostrarReporte(urlReporte,params);
        } catch (Exception ex) {
            Logger.getLogger(GenerarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    hideLoading();
}//GEN-LAST:event_btnPresupuestoSinDetalleActionPerformed

private void btnPresupuestoExternoSubObrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPresupuestoExternoSubObrasActionPerformed
    showLoading();
    // Acá tu código ...
    if(cotizacionId>0) 
    { 
       GestorReportesCotizacion gestor = new GestorReportesCotizacion();
       gestor.emitirPresupuestoExternoSubObras(cotizacionId);
    }
    // ...
    hideLoading();
}//GEN-LAST:event_btnPresupuestoExternoSubObrasActionPerformed

private void btnPresupuestoExternoRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPresupuestoExternoRecursosActionPerformed
    showLoading();
    // Acá tu código ...
    // ...
    hideLoading();
}//GEN-LAST:event_btnPresupuestoExternoRecursosActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    
    showLoading();
    jButton4.setEnabled(false);

    // Acá tu código ...
    if(cotizacionId>0) 
    { 
       GestorReportesCotizacion gestor = new GestorReportesCotizacion();
       gestor.emitirPresupuestoInterno(cotizacionId);
    }
    // ...
    hideLoading();
    jButton4.setEnabled(true);
}//GEN-LAST:event_jButton4ActionPerformed


private void showLoading()
{
    lblLoad.setVisible(true);
    this.repaint();
    lblLoad.repaint();
    this.pack();
    lblLoad.repaint();
    
}

private void hideLoading()
{
    lblLoad.setVisible(false);
    this.repaint();
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnPresupuestoExternoRecursos;
    private javax.swing.JButton btnPresupuestoExternoSubObras;
    private javax.swing.JButton btnPresupuestoSinDetalle;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblLoad;
    // End of variables declaration//GEN-END:variables

}
