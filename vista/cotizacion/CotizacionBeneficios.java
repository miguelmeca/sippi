/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * editarCotizacion_Beneficios.java
 *
 * Created on 24/04/2011, 17:37:14
 */

package vista.cotizacion;

import controlador.cotizacion.GestorCotizacionBeneficios;
import java.lang.NumberFormatException;
import vista.util.Validaciones;

/**
 *
 * @author iuga
 */
public class CotizacionBeneficios extends javax.swing.JPanel {

    private GestorCotizacionBeneficios gestor;
    private boolean flagGananciaPorcentaje;
    private double gananciaMonto;
    private double gananciaPorcentaje;

    public CotizacionBeneficios(GestorCotizacionBeneficios gestor) {
        initComponents();
        this.gestor = gestor;
        gestor.setPantalla(this);
        habilitarVentana();
        actualizarDatos();
    }
    private void habilitarVentana()
    {
        ftxtPorcentaje.addKeyListener(Validaciones.getKaNumeros());
        ftxtMonto.addKeyListener(Validaciones.getKaNumeros());
    }
    
    public void actualizarDatos()
    {
        flagGananciaPorcentaje=gestor.isFlagGananciaPorcentaje();
        gananciaMonto=gestor.getGananciaMonto();
        gananciaPorcentaje=gestor.getGananciaPorcentaje();
        rbtnPorcentajeGanancia.setSelected(flagGananciaPorcentaje);
        rbtnMontoGanancia.setSelected(!flagGananciaPorcentaje);
        ftxtPorcentaje.setEnabled(flagGananciaPorcentaje);
        ftxtMonto.setEnabled(!flagGananciaPorcentaje);
        //ftxtPorcentaje.setText(Double.toString(gananciaPorcentaje));  
        ftxtPorcentaje.setText(Double.toString(gananciaPorcentaje).replace(".", ","));
        //ftxtMonto.setText(Double.toString(gananciaMonto));
        ftxtMonto.setText(Double.toString(gananciaMonto).replace(".", ","));
        
        actualizarMonto();
    }
    public void actualizarMonto()
    {
        String StringGanancia;
        double ganancia=0.0;
        if(rbtnPorcentajeGanancia.isSelected())
        {
            flagGananciaPorcentaje=true;
            StringGanancia= ftxtPorcentaje.getText();
           
        }
        else
        {
            flagGananciaPorcentaje=false;
            StringGanancia= ftxtMonto.getText();
            
        } 
        try
        {
            //ganancia=Double.parseDouble(StringGanancia);
            ganancia=Double.parseDouble(StringGanancia.replace(",", "."));
        }
        catch(NumberFormatException pe)
        {
         lblGanancia.setText("-"); 
        }
        //lblGanancia.setText("Ganancia por el Item de Obra: $"+Double.toString(gestor.calcularGananciaSubObra(flagGananciaPorcentaje,ganancia)));
        lblGanancia.setText("Ganancia por el Item de Obra: $"+Double.toString(gestor.calcularGananciaSubObra(flagGananciaPorcentaje,ganancia)).replace(".", ","));
        if(flagGananciaPorcentaje)
        {
            ftxtMonto.setText(Double.toString(gestor.getGananciaMonto()).replace(".", ","));           
        }
        else
        {
           ftxtPorcentaje.setText(Double.toString(gestor.getGananciaPorcentaje()).replace(".", ","));            
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        ftxtPorcentaje = new javax.swing.JFormattedTextField();
        ftxtMonto = new javax.swing.JFormattedTextField();
        lblGanancia = new javax.swing.JLabel();
        rbtnMontoGanancia = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        rbtnPorcentajeGanancia = new javax.swing.JRadioButton();

        ftxtPorcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        ftxtPorcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftxtPorcentajeFocusLost(evt);
            }
        });
        ftxtPorcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftxtPorcentajeKeyReleased(evt);
            }
        });

        ftxtMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        ftxtMonto.setEnabled(false);
        ftxtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtMontoActionPerformed(evt);
            }
        });
        ftxtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftxtMontoKeyReleased(evt);
            }
        });

        lblGanancia.setBackground(new java.awt.Color(251, 250, 241));
        lblGanancia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGanancia.setText("Ganancia por el Item de Obra: $1350");
        lblGanancia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(252, 239, 161)));
        lblGanancia.setOpaque(true);

        buttonGroup1.add(rbtnMontoGanancia);
        rbtnMontoGanancia.setText("Monto de Ganancias Fijo para la Sub-obra($)");
        rbtnMontoGanancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMontoGananciaActionPerformed(evt);
            }
        });

        jRadioButton2.setText("jRadioButton2");

        buttonGroup1.add(rbtnPorcentajeGanancia);
        rbtnPorcentajeGanancia.setSelected(true);
        rbtnPorcentajeGanancia.setText("Porcentaje de Ganancia para la sub-obra(%)");
        rbtnPorcentajeGanancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPorcentajeGananciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGanancia, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnMontoGanancia, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                            .addComponent(rbtnPorcentajeGanancia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ftxtPorcentaje, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ftxtMonto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnMontoGanancia))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnPorcentajeGanancia))
                .addGap(18, 18, 18)
                .addComponent(lblGanancia)
                .addContainerGap(173, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnMontoGananciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMontoGananciaActionPerformed
        if(rbtnMontoGanancia.isSelected())
        {
            ftxtMonto.setEnabled(true);
            ftxtPorcentaje.setEnabled(false);
            actualizarMonto();
        }
    }//GEN-LAST:event_rbtnMontoGananciaActionPerformed

private void rbtnPorcentajeGananciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPorcentajeGananciaActionPerformed
    if(rbtnPorcentajeGanancia.isSelected())
    {
        ftxtMonto.setEnabled(false);
        ftxtPorcentaje.setEnabled(true);
        actualizarMonto();
    }
}//GEN-LAST:event_rbtnPorcentajeGananciaActionPerformed

private void ftxtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtMontoActionPerformed

}//GEN-LAST:event_ftxtMontoActionPerformed

private void ftxtPorcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtPorcentajeFocusLost
   
}//GEN-LAST:event_ftxtPorcentajeFocusLost

private void ftxtMontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtMontoKeyReleased
 actualizarMonto();
}//GEN-LAST:event_ftxtMontoKeyReleased

private void ftxtPorcentajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtPorcentajeKeyReleased
 actualizarMonto();
}//GEN-LAST:event_ftxtPorcentajeKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField ftxtMonto;
    private javax.swing.JFormattedTextField ftxtPorcentaje;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JLabel lblGanancia;
    private javax.swing.JRadioButton rbtnMontoGanancia;
    private javax.swing.JRadioButton rbtnPorcentajeGanancia;
    // End of variables declaration//GEN-END:variables

}
