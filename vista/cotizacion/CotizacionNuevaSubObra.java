/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * nuevaSubObra.java
 *
 * Created on 27-jun-2011, 8:09:00
 */

package vista.cotizacion;

import util.Tupla;

/**
 *
 * @author Administrador
 */
public class CotizacionNuevaSubObra extends javax.swing.JInternalFrame {

    public static final int TIPO_CREAR = 0;
    public static final int TIPO_MODIFICAR = 1;
    private int TIPO;
    
    private Tupla tp;
    
    private ExplorarSubObras pantalla;

    /** Creates new form nuevaSubObra */
    public CotizacionNuevaSubObra(ExplorarSubObras pantalla, int TIPO)
    {
        this.pantalla = pantalla;
        this.TIPO = TIPO;
        initComponents();

        switch(TIPO)
        {
            case TIPO_CREAR:
                btnOK.setText("Crear");
                this.setTitle("Crear nueva Sub-Obra");
                break;
            case TIPO_MODIFICAR:
                btnOK.setText("Modificar");
                this.setTitle("Modificar Sub-Obra");
                break;
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNombra = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Nueva Sub-Obra");

        jLabel1.setText("Nombre de la Sub-Obra:");

        txtNombra.setText("Traslado");

        btnOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnOK.setText("Crear");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombra, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed

        switch(this.TIPO)
        {
            case TIPO_CREAR:
                pantalla.crearNuevaSubObra(txtNombra.getText());
                this.dispose();
                break;
            case TIPO_MODIFICAR:
                this.tp.setNombre(txtNombra.getText());
                pantalla.cambiarNombreSubObra(tp);
                this.dispose();
                break;
        }    
    
}//GEN-LAST:event_btnOKActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtNombra;
    // End of variables declaration//GEN-END:variables

    void setTuplaFila(Tupla t) 
    {
        this.tp=t;
        this.txtNombra.setText(t.getNombre());
    }

}
