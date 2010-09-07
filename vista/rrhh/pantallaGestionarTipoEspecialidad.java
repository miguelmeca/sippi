/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaRegistrarNuevoTipoCapacitacion.java
 *
 * Created on 24-jun-2010, 15:45:55
 */

package vista.rrhh;

import controlador.rrhh.GestorABMTipoEspecialidad;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import modelo.TipoCapacitacion;
import util.NTupla;
import util.Tupla;
import vista.interfaces.ICallBack_v2;

/**
 *
 * @author Administrador
 */
public class pantallaGestionarTipoEspecialidad extends javax.swing.JInternalFrame {

    private GestorABMTipoEspecialidad gestor;
    private ICallBack_v2 pantalla;
    private int oidEdit; // 0: No estoy en el modo EDICION

    /** Creates new form pantallaRegistrarNuevoTipoCapacitacion */
    public pantallaGestionarTipoEspecialidad() {
        initComponents();
        btnCancelEdit.setVisible(false);
        oidEdit = 0;

        gestor = new GestorABMTipoEspecialidad();

        llenarTabla();


    }
    public static String getFlagPantalla()
    {return "FLAG_TIPO_ESPECIALIDAD";}
    public void setPantalla(ICallBack_v2 win)
    {
        this.pantalla = win;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTipos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnCancelEdit = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Gestionar Tipos de Especialidad");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        tablaTipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaTipos.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(tablaTipos);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Tipo de Especialidad"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Nombre:");

        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Descripción:");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevo.setText("Agregar Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnCancelEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelEdit.setText("Cancelar Modificación");
        btnCancelEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelEdit)
                    .addComponent(btnNuevo))
                .addContainerGap())
        );

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, 0, 155, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

     if(!txtNombre.getText().isEmpty())
     {
         if(this.oidEdit == 0)
         {
                boolean exito=gestor.crear(txtNombre.getText(),txtDesc.getText());

                if(exito)
                {
                    // CARGA CORRECTA
                    txtNombre.setText("");
                    txtDesc.setText("");
                    JOptionPane.showMessageDialog(this,"Carga Exitosa","Se completo correctamente la carga del tipo de especialidad ",JOptionPane.INFORMATION_MESSAGE);
                    llenarTabla();
                }
                else
                {
                    // CARGA INCORRECTA
                    JOptionPane.showMessageDialog(this,"Error","Se produjo un error al intentar crear el Tipo de Especialidad",JOptionPane.INFORMATION_MESSAGE);
                }
         }
         else
         {
                if(gestor.modificar(oidEdit,txtNombre.getText(),txtDesc.getText()))
                {
                    // CARGA CORRECTA
                    txtNombre.setText("");
                    txtDesc.setText("");
                    this.oidEdit = 0;
                    btnCancelEdit.setVisible(false);
                    btnNuevo.setText("Agregar Nuevo");

                    JOptionPane.showMessageDialog(this,"Modificación Exitosa","Se completo correctamente la modificación",JOptionPane.INFORMATION_MESSAGE);
                    llenarTabla();
                }
                else
                {
                    // CARGA INCORRECTA
                    JOptionPane.showMessageDialog(this,"Error","Se produjo un error al modificar el Tipo de Especialidad",JOptionPane.INFORMATION_MESSAGE);
                }
         }
     }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed

        // LLAMO AL METODO DE CALLBACK DE LA PANTALLA DE LA QUE EXTENDIO
        this.pantalla.actualizar(0,"FLAG_TIPO_ESPECIALIDAD",true);

    }//GEN-LAST:event_formInternalFrameClosed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

       NTupla dato = (NTupla) tablaTipos.getModel().getValueAt(tablaTipos.getSelectedRow(),0);

       txtNombre.setText(dato.getNombre());
       txtDesc.setText((String)dato.getData());
       this.oidEdit = dato.getId();
       btnNuevo.setText("Guardar Cambios");

       btnCancelEdit.setVisible(true);

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEditActionPerformed

        txtNombre.setText("");
        txtDesc.setText("");
        this.oidEdit = 0;
        btnNuevo.setText("Agregar Nuevo");
        btnCancelEdit.setVisible(false);

    }//GEN-LAST:event_btnCancelEditActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        NTupla dato = (NTupla) tablaTipos.getModel().getValueAt(tablaTipos.getSelectedRow(),0);
        int ret = gestor.eliminar(dato.getId());

        switch(ret)
        {
            case 1:
            JOptionPane.showMessageDialog(this,"Eliminación Exitosa","Se eliminó correctamente el Tipo de Especialidad",JOptionPane.INFORMATION_MESSAGE);
            break;

            case 0:
            JOptionPane.showMessageDialog(this,"Eliminación Fallida","Se produjo un error desconocido al eliminar el Tipo de Especialidad",JOptionPane.ERROR_MESSAGE);
            break;

            case 2:
            JOptionPane.showMessageDialog(this,"Eliminación Fallida","El Tipo de Especialidad seleccionado está en uso, por lo que no puede eliminarse",JOptionPane.ERROR_MESSAGE);

        }

        llenarTabla();


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost

        if(gestor.existeTipo(txtNombre.getText()))
        {
            JOptionPane.showMessageDialog(this,"Ya existe un Tipo de Especialidad con ese nombre","Ingrese otro nombre",JOptionPane.ERROR_MESSAGE);
            txtNombre.setText("");
        }

    }//GEN-LAST:event_txtNombreFocusLost

    public void llenarTabla()
    {

        Object[] nombreColumnas = {"Nombre","Descripción"};
        DefaultTableModel modelo = new DefaultTableModel();
        for (int i = 0; i < nombreColumnas.length; i++)
        {
           modelo.addColumn(nombreColumnas[i]);
        }
        tablaTipos.setModel(modelo);

        modelo = (DefaultTableModel) tablaTipos.getModel();

        ArrayList<NTupla> lista = gestor.mostrarNombreTiposEspecialidad();
        Iterator it = lista.iterator();

        Object[] fila = new Object[2];

        while (it.hasNext())
        {
            NTupla t = (NTupla)it.next();
            fila[0] = t;
            fila[1] = t.getData();
            modelo.addRow(fila);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelEdit;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTipos;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

}
