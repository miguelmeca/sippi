/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaConsultar.java
 *
 * Created on 06-ago-2010, 15:44:11
 */

package vista.compras;
import vista.interfaces.ICallBack;
import controlador.Compras.GestorConsultarPrecioXRecurso;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import vista.interfaces.IAyuda;
import javax.swing.DefaultComboBoxModel;
import util.Tupla;
import util.NTupla;

/**
 *
 * @author Fran
 */
public class pantallaConsultarPrecioXRecurso extends javax.swing.JInternalFrame implements ICallBack,   IAyuda {

    private GestorConsultarPrecioXRecurso gestor;
    /** Creates new form pantallaGenerarOrdenCompra */
    public pantallaConsultarPrecioXRecurso() {
        gestor = new GestorConsultarPrecioXRecurso(this);
        initComponents();
        habilitarVentana();


    }
    public void habilitarVentana()
    {
        mostrarTipoRecurso();

}
    /** Creates new form pantallaConsultar */
    private void mostrarTipoRecurso()
    {
        
        ArrayList<Tupla> listaNombresTipoDeRecurso = gestor.mostrarRubros();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombresTipoDeRecurso)
        {
            model.addElement(nombre);
        }
        cmbTiposRecurso.setModel(model);
        cmbTiposRecurso.setSelectedIndex(-1);
        cmbRecursos.setModel(new DefaultComboBoxModel());
        cmbRecursosEspecificos.setModel(new DefaultComboBoxModel());
        DefaultTableModel modeloTablaPro=(DefaultTableModel)tablaProveedores.getModel();
        modeloTablaPro.setRowCount(0);
        DefaultTableModel modeloTablaPre=(DefaultTableModel)tablaPrecios.getModel();
        modeloTablaPre.setRowCount(0);
        cmbRecursos.setEnabled(false);
        cmbRecursosEspecificos.setEnabled(false);
        tablaProveedores.setEnabled(false);


    }

    private void mostrarRecursos()
    {
        
        if(cmbTiposRecurso.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbTiposRecurso.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarRecursos(t.getId());
        for (Tupla nombre : lista)
        {
            valores.addElement(nombre);
        }

        cmbRecursos.setModel(valores);
        cmbRecursos.setSelectedIndex(-1);
        cmbRecursosEspecificos.setModel(new DefaultComboBoxModel());
        DefaultTableModel modeloTablaPro=(DefaultTableModel)tablaProveedores.getModel();
        modeloTablaPro.setRowCount(0);
        DefaultTableModel modeloTablaPre=(DefaultTableModel)tablaPrecios.getModel();
        modeloTablaPre.setRowCount(0);
        cmbRecursos.setEnabled(true);
        cmbRecursosEspecificos.setEnabled(false);
        tablaProveedores.setEnabled(false);

        }
    }

    private void mostrarRecursosEspecificos()
    {
        
        if(cmbRecursos.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbRecursos.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarRecursosEspecificos(t.getId());
        for (Tupla nombre : lista)
        {
            valores.addElement(nombre);
        }

        cmbRecursosEspecificos.setModel(valores);
        cmbRecursosEspecificos.setSelectedIndex(-1);
        DefaultTableModel modeloTablaPro=(DefaultTableModel)tablaProveedores.getModel();
        modeloTablaPro.setRowCount(0);
        DefaultTableModel modeloTablaPre=(DefaultTableModel)tablaPrecios.getModel();
        modeloTablaPre.setRowCount(0);
        cmbRecursos.setEnabled(true);
        cmbRecursosEspecificos.setEnabled(true);
        tablaProveedores.setEnabled(false);

        }

    }
    
    private void mostrarProveedores()
    {
       
        if(cmbRecursosEspecificos.getSelectedIndex()!=-1)
       {

        DefaultTableModel valores = (DefaultTableModel)tablaProveedores.getModel();
        valores.setRowCount(0);
        /*int fil=valores.getRowCount();
        for (int i = 0; i < fil; i++) {
            valores.removeRow(0);
        }*/
        Tupla t = (Tupla) cmbRecursosEspecificos.getSelectedItem() ;
        ArrayList<NTupla> lista = gestor.mostrarProveedores(t.getId());

        for (NTupla nTuplaP : lista)
        {
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            double conf=(Double)nTuplaP.getData();
            //obj[0]=nTuplaEmpleado.getId();
            Tupla tup=new Tupla();
            tup.setId(nTuplaP.getId());
            tup.setNombre(nTuplaP.getNombre());
            Object[] obj=new Object[2];
            obj[0]=tup;
            obj[1]=conf;

            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            //System.arraycopy((Object[]) nTuplaP.getData(), 0, obj, 1, ((Object[]) nTuplaP.getData()).length);
            valores.addRow( obj );
        }
        DefaultTableModel modeloTablaPre=(DefaultTableModel)tablaPrecios.getModel();
        modeloTablaPre.setRowCount(0);
        tablaProveedores.setEnabled(true);
        }


    }

    private void mostrarPrecios()
    {
        if(tablaProveedores.getSelectedRow()!=-1 && cmbRecursosEspecificos.getSelectedIndex()!=-1)
       {

        DefaultTableModel valores = (DefaultTableModel)tablaPrecios.getModel();
        valores.setRowCount(0);
        /*int fil=valores.getRowCount();
        for (int i = 0; i < fil; i++) {
            valores.removeRow(0);
        }*/
        int idProv=((Tupla)(tablaProveedores.getModel().getValueAt(tablaProveedores.getSelectedRow(), 0))).getId();
        int idRE= ((Tupla) cmbRecursosEspecificos.getSelectedItem()).getId();
        ArrayList<NTupla> lista = gestor.mostrarPrecios(idProv, idRE);

        for (NTupla nTuplaP : lista)
        {
           
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            Object[] obj=new Object[((Object[])nTuplaP.getData()).length+1];
            //obj[0]=nTuplaEmpleado.getId();
            Tupla tup=new Tupla();
            tup.setId(nTuplaP.getId());
            tup.setNombre(nTuplaP.getNombre());
            obj[0]=tup;

            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            System.arraycopy((Object[]) nTuplaP.getData(), 0, obj, 1, ((Object[]) nTuplaP.getData()).length);
            valores.addRow( obj );
        }
        
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

        cmbTiposRecurso = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbRecursos = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbRecursosEspecificos = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPrecios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar Precios por Recurso");

        cmbTiposRecurso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Indumentaria", "Material", "Herramienta" }));
        cmbTiposRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTiposRecursoActionPerformed(evt);
            }
        });

        jLabel2.setText("Rubro:");

        jLabel4.setText("Nombre:");

        cmbRecursos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guantes" }));
        cmbRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosActionPerformed(evt);
            }
        });

        jLabel3.setText("Especificación: ");

        cmbRecursosEspecificos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4mm de Goma Azul" }));
        cmbRecursosEspecificos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosEspecificosActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Precios"));

        tablaPrecios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Cantidad", "Precio", "Vigencia hasta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPrecios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaPrecios.setEnabled(false);
        tablaPrecios.setRowSelectionAllowed(false);
        jScrollPane4.setViewportView(tablaPrecios);
        tablaPrecios.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaPrecios.getColumnModel().getColumn(1).setPreferredWidth(50);
        tablaPrecios.getColumnModel().getColumn(2).setPreferredWidth(90);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores"));

        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Proveedor", "Confianza"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProveedores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaProveedoresMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablaProveedores);
        tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(202);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTiposRecurso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(442, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTiposRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTiposRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTiposRecursoActionPerformed
        mostrarRecursos();
}//GEN-LAST:event_cmbTiposRecursoActionPerformed

    private void cmbRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursosActionPerformed
        mostrarRecursosEspecificos();
}//GEN-LAST:event_cmbRecursosActionPerformed

    private void cmbRecursosEspecificosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursosEspecificosActionPerformed
        mostrarProveedores();
}//GEN-LAST:event_cmbRecursosEspecificosActionPerformed

    private void tablaProveedoresMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedoresMouseReleased
        mostrarPrecios();
}//GEN-LAST:event_tablaProveedoresMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea salir?","Cancelar",JOptionPane.YES_NO_OPTION);
       // if(resp==JOptionPane.YES_OPTION)
        {       this.dispose();}

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbRecursos;
    private javax.swing.JComboBox cmbRecursosEspecificos;
    private javax.swing.JComboBox cmbTiposRecurso;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaPrecios;
    private javax.swing.JTable tablaProveedores;
    // End of variables declaration//GEN-END:variables
public void actualizar(int flag,boolean exito) {
        
        habilitarVentana();
    }
public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Registre un nuevo empleado o seleccione uno existente y consulte o modifique sus datos";
    }

    public String getTituloAyuda()
    {
        return "Opción: Consultar Empleados";

    }
}
