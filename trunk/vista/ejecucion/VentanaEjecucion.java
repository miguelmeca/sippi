package vista.ejecucion;

import controlador.ejecucion.GestorVentanaEjecucion;
import javax.swing.JOptionPane;
import util.SwingPanel;
import vista.ejecucion.lanzamiento.VentanaLanzamiento;

/**
 * Ventana Principal de Ejecucion !!
 * @author Iuga
 */
public class VentanaEjecucion extends javax.swing.JInternalFrame {

    private GestorVentanaEjecucion gestor;
    
    /**
     * Ventana Principal de Ejecucion !!
     */
    public VentanaEjecucion(int idObra) {
        // Inicializo Ventana
        initComponents();
        // Inicializo el Gestor de a Ejecucion
        this.gestor = new GestorVentanaEjecucion(this,idObra);
        // Verifico si la obra esta en ejecucion
        checkSiObraTieneEjecución(idObra);
        // Segun el estado de la ejecucion, cambio el comportamiento
        cambiarSegunEstadoEjecucion();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEmitirInformes = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnLanzamiento = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Editar Ejecución");

        btnEmitirInformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/List.png"))); // NOI18N
        btnEmitirInformes.setText("Emitir Informes");

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cancelar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Línea de Tiempo", jPanel1);

        jPanel2.setToolTipText("Datos Generales");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Generales", jPanel2);

        jPanel3.setToolTipText("Observaciones");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("A continuación escriba todas la observaciones que crea necesarias:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Observaciones", jPanel3);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Ayuda sobre la utilización de esta ventana:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(380, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("<HTML><span color='002EB8'><b>Ayuda?</b></span>", jPanel4);

        btnLanzamiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept_page.png"))); // NOI18N
        btnLanzamiento.setText("Lanzamiento");
        btnLanzamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLanzamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEmitirInformes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLanzamiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEmitirInformes)
                    .addComponent(btnCerrar)
                    .addComponent(btnGuardar)
                    .addComponent(btnLanzamiento))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // llamo al comportamiento Cancelar de la ventana 
        cancelar();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Llamo al guardar
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLanzamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLanzamientoActionPerformed
        VentanaLanzamiento win = new VentanaLanzamiento(gestor.getIdObraActual());
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_btnLanzamientoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEmitirInformes;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLanzamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    /**
     * Verifica si la obra tiene ejecucion,si no la tiene esta ventana
     * debería advertirlo y cerrarse.
     * @param idObra 
     */
    private void checkSiObraTieneEjecución(int idObra) {
        
    }
    
    /**
     * Segun el estado de la ejecucion, cambio el comportamiento.
     * Si está en alta, o en definicion, dejo todo editable
     * Si esta en baja o cancelada, no dejo editar nada.
     */
    private void cambiarSegunEstadoEjecucion() {

    }
    
    /**
     * Cancela los cambios y cierra la ventana.
     * Antes pregunta si desea guardar los cambios.
     */
    private void cancelar() {
        int seleccion = JOptionPane.showOptionDialog(
                this, // Componente padre
                "<HTML>Si <b>cancela</b> todos los cambios realizados se <b><span color='#FF0000'>pederán</span></b> \n"
                + "¿Desea guardar los cambios antes de cerrar?", //Mensaje
                "Seleccione una opción", // Título
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, // null para icono por defecto.
                new Object[]{"Guardar y Cerrar","Cerrar", "Cancelar"}, // null para YES, NO y CANCEL
                "Guardar y Cerrar");
        
        if (seleccion != -1) {
            switch(seleccion+1){
                case 1:
                    // PRESIONO Guardar y Cerrar
                    System.out.println("Guardar y Cerrar:"+(seleccion+1));
                    if(guardar()){
                        this.dispose();
                    }
                    break;
                case 2:
                    // PRESIONO Cerrar
                    System.out.println("Cerrar:"+(seleccion+1));
                    this.dispose();
                    break;
                case 3:
                default:
                    System.out.println("Cancelar:"+(seleccion+1));
                    // Cancelar - Nadaremos
                    break;
            }
        }
    }
    
    /**
     * Metodo a llamar cuando se quieren guardar los cambios !!
     * @return 
     */
    private boolean guardar() {
        return true;
    }    
}
