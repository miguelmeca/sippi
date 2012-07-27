/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaHerramientas.java
 *
 * Created on 06/05/2011, 22:58:50
 */

package vista.cotizacion;

import controlador.cotizacion.GestorCotizacionHerramientas;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.SubObraXHerramienta;
import util.NTupla;
import util.TablaUtil;
import util.Tupla;

/**
 *
 * @author Fran
 */
public class CotizacionHerramientas extends javax.swing.JPanel {

    private static final String TEXTO_BTN_AGREGAR = "Agregar";
    private static final String TEXTO_BTN_MODIFICAR = "Modificar";
    private static final String TEXTO_BTN_CANCELAR = "Cancelar";
    private static final String TEXTO_BTN_EDITAR = "Editar";
    
    private GestorCotizacionHerramientas gestor;
    private SubObraXHerramienta herramientaEditando;
    
    private boolean modificando = false;
    
    /** Creates new form pantallaHerramientas */
    public CotizacionHerramientas(GestorCotizacionHerramientas gestor) {
        initComponents();
        this.gestor = gestor;
        this.gestor.setPantalla(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        cmbHerramienta = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSubTotalConcepto = new javax.swing.JTextField();
        txtCostoHora = new javax.swing.JTextField();
        txtHorasDia = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHeramientas = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Herramienta a Utilizar (*)/ Observaciones"));

        cmbHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbHerramientaActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setEnabled(false);
        jScrollPane3.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addComponent(cmbHerramienta, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cmbHerramienta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel14.setText("Costo x Hora (*)");

        jLabel15.setText("=");

        jLabel16.setText("Subtotal");

        jLabel17.setText("X");

        jLabel4.setText("Horas Necesarias (*)");

        txtSubTotalConcepto.setBackground(new java.awt.Color(204, 204, 255));
        txtSubTotalConcepto.setEditable(false);

        txtCostoHora.setEnabled(false);
        txtCostoHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoHoraActionPerformed(evt);
            }
        });
        txtCostoHora.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCostoHoraFocusLost(evt);
            }
        });

        txtHorasDia.setEnabled(false);
        txtHorasDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorasDiaActionPerformed(evt);
            }
        });
        txtHorasDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorasDiaFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHorasDia)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtCostoHora, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubTotalConcepto)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(txtSubTotalConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCostoHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHorasDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Herramientas a utilizar"));

        tblHeramientas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Herramienta", "Horas", "Costo x Hora", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblHeramientas);

        jLabel20.setText("Subtotal Herramientas   $");

        txtSubTotal.setEditable(false);
        txtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar)
                    .addComponent(btnEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtSubTotalActionPerformed

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Tupla tpitem = (Tupla)cmbHerramienta.getSelectedItem();
        if(tpitem.getId()!=0)
        {         
            if(!txtHorasDia.getText().isEmpty() && !txtCostoHora.getText().isEmpty())
            {
                int cantHoras = 0;
                try
                {
                    cantHoras = Integer.parseInt(txtHorasDia.getText());
                }
                catch(Exception e)
                {
                    MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","La cantidad de horas ingresada es incorrecta");
                    return;
                }
                if(cantHoras<=0)
                {
                    MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","La cantidad de horas ingresada debe ser positiva");
                    return;
                }

                double costoHora = 0;
                try
                {
                    costoHora = Double.parseDouble(txtCostoHora.getText().replaceAll(",","."));
                }
                catch(Exception e)
                {
                    MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","El costo ingresado es incorrecto");
                    return;
                }   

                if(costoHora<0)
                {
                    MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","El costo ingresado debe ser positivo");
                    return;            
                }
                if(this.herramientaEditando==null)
                {
                    gestor.AgregarHerramienta(tpitem,cantHoras, costoHora,txtDescripcion.getText());
                }
                else
                {
                    gestor.AgregarHerramienta(this.herramientaEditando,tpitem,cantHoras, costoHora,txtDescripcion.getText());
                }
                cargarModificacionHerramienta(null);
                modificando= false;
            }
        }
        else
        {
            MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Debe seleccionar una Herramienta");
        }
    
}//GEN-LAST:event_btnAgregarActionPerformed

private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        if(tblHeramientas.getSelectedRow()!= -1)
        {
            if(!modificando)
            {
                DefaultTableModel dtm = (DefaultTableModel)tblHeramientas.getModel();
                NTupla ntp = (NTupla) dtm.getValueAt(tblHeramientas.getSelectedRow(), 0);
                gestor.quitarHerramienta(ntp);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Debe terminar la edición actual antes de realizar esta acción.","Advertencia",JOptionPane.WARNING_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Herramienta","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_btnQuitarActionPerformed

    private void cmbHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbHerramientaActionPerformed
        Tupla tp = (Tupla)cmbHerramienta.getSelectedItem();
        if(tp.getId()!=0)
        {
            txtHorasDia.setEnabled(true);
            txtCostoHora.setEnabled(true);
            txtDescripcion.setEnabled(true);
        }
        else
        {
            txtHorasDia.setEnabled(false);
            txtCostoHora.setEnabled(false);
            txtDescripcion.setEnabled(false);
        }
            
    }//GEN-LAST:event_cmbHerramientaActionPerformed

    private void txtHorasDiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorasDiaFocusLost
        CalcularSubTotalConcepto();
    }//GEN-LAST:event_txtHorasDiaFocusLost

    private void txtCostoHoraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostoHoraFocusLost
        CalcularSubTotalConcepto();
    }//GEN-LAST:event_txtCostoHoraFocusLost

    private void txtHorasDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorasDiaActionPerformed
        CalcularSubTotalConcepto();
    }//GEN-LAST:event_txtHorasDiaActionPerformed

    private void txtCostoHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoHoraActionPerformed
        CalcularSubTotalConcepto();
    }//GEN-LAST:event_txtCostoHoraActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if(btnEditar.getText().equals(TEXTO_BTN_EDITAR))
        {
            if(tblHeramientas.getSelectedRow()!= -1)
            {
                DefaultTableModel dtm = (DefaultTableModel)tblHeramientas.getModel();
                NTupla ntp = (NTupla) dtm.getValueAt(tblHeramientas.getSelectedRow(), 0);

                SubObraXHerramienta h = gestor.getHerramientaAgregada(ntp);
                cargarModificacionHerramienta(h);
                modificando = true;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una Herramienta agregada para Editarla","Advertencia",JOptionPane.WARNING_MESSAGE);
            }
        }else if(btnEditar.getText().equals(TEXTO_BTN_CANCELAR))
        {
            // No modifico más
            cargarModificacionHerramienta(null);
            modificando = false;
        }
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox cmbHerramienta;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblHeramientas;
    private javax.swing.JTextField txtCostoHora;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtHorasDia;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtSubTotalConcepto;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra un mensaje
     * @param tipo
     * @param titulo
     * @param mensaje 
     */
    public void MostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }    
    
    public void llenarComboHerramientas(ArrayList<Tupla> listaTipos) 
    {
        Tupla tp0 = new Tupla(0,"Seleccione una Herramienta...");
        
        cmbHerramienta.removeAllItems();
        cmbHerramienta.addItem(tp0);
        for (int i = 0; i < listaTipos.size(); i++) 
        {
            Tupla tp = listaTipos.get(i);
            cmbHerramienta.addItem(tp);
        }
    }    
    
    public void CalcularSubTotalConcepto()
    {
        if(!txtHorasDia.getText().isEmpty() && !txtCostoHora.getText().isEmpty())
        {
//            double cantDias = 0;
            double cantHoras = 0;
            double costoHora = 0;
            try
            {
//                cantDias = Double.parseDouble(txtCantDias.getText());
                cantHoras = Double.parseDouble(txtHorasDia.getText());
                costoHora = Double.parseDouble(txtCostoHora.getText().replaceAll(",","."));
            }
            catch(Exception e)
            {
                return;
            }
//            txtSubTotalConcepto.setText("$"+Math.abs(cantDias)*Math.abs(cantHoras)*Math.abs(costoHora));
            txtSubTotalConcepto.setText("$"+Math.abs(cantHoras)*Math.abs(costoHora));
        }
    }

    public void llenarTabla(ArrayList<NTupla> listaFilas) 
    {
        double total = 0;
        
        DefaultTableModel modelo = (DefaultTableModel)tblHeramientas.getModel();
        // Vacio la tabla por si ya tiene filas de otra llamada
        TablaUtil.vaciarDefaultTableModel(modelo);
        // lleno con los nuevos datos
        for (int i = 0; i < listaFilas.size(); i++) 
        {
            NTupla nt = listaFilas.get(i);
            
            String[] data = (String[])nt.getData();
            
            Object fila[] = new Object[4];
                fila[0] = nt;
                fila[1] = data[0];
                fila[2] = data[1];
                fila[3] = data[2];
//                fila[4] = data[3];
                
                total += Double.valueOf(data[2]);
                
            modelo.addRow(fila);
        }
        
        // muestro el total calculado
        txtSubTotal.setText(String.valueOf(total));
    
    }

    private void cargarModificacionHerramienta(SubObraXHerramienta h) {
        this.herramientaEditando = h;
        if(this.herramientaEditando!=null)
        {
            btnAgregar.setText(TEXTO_BTN_MODIFICAR);
            btnEditar.setText(TEXTO_BTN_CANCELAR);
            btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png")));
 
            // Selecciona la herramienta
            for (int i = 0; i < cmbHerramienta.getItemCount(); i++) {
                Tupla tp = (Tupla)cmbHerramienta.getItemAt(i);
                if(tp.getId()==h.getHerramienta().getId()){
                    cmbHerramienta.setSelectedIndex(i);
                    continue;
                }
            }
            // Concepto
            txtDescripcion.setText(h.getObservaciones());
            txtDescripcion.setEnabled(true);
            txtDescripcion.setEditable(true);
            
            // Costos
            txtHorasDia.setText(String.valueOf(this.herramientaEditando.getCantHoras()));
            txtHorasDia.setEnabled(true);
            txtHorasDia.setEditable(true);
            txtCostoHora.setText(String.valueOf(this.herramientaEditando.getCostoXHora()));
            txtCostoHora.setEnabled(true);
            txtCostoHora.setEditable(true);
            CalcularSubTotalConcepto();
        }
        else{
            btnAgregar.setText(TEXTO_BTN_AGREGAR);
            btnEditar.setText(TEXTO_BTN_EDITAR);
            btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png")));
            txtDescripcion.setText("");
            txtHorasDia.setText("");
            txtCostoHora.setText("");
            txtSubTotalConcepto.setText("");
            cmbHerramienta.setSelectedIndex(0);
        }
    }
    
}
