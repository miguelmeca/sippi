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
import controlador.Compras.GestorConsultarPrecioXRecurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;

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

    public pantallaConsultarPrecioXRecurso(ItemComprable itemComprable, Proveedor proveedor) {
        gestor = new GestorConsultarPrecioXRecurso(this);
        initComponents();
        habilitarVentana();
        Object item = itemComprable.getItem();
        try
        {
            HibernateUtil.beginTransaction();
            if(item instanceof RecursoEspecifico)
            {
                RecursoEspecifico recursoEsp = (RecursoEspecifico) item;
                Recurso recurso = (Recurso) HibernateUtil.getSession().createQuery("FROM Recurso AS r WHERE :re in elements(r.recursos)").setParameter("re", recursoEsp).uniqueResult();
                Tupla tRecurso = null;
                if(recurso instanceof Herramienta)
                {
                    Herramienta herramienta = (Herramienta) recurso;
                    cmbTiposRecurso.setSelectedIndex(1);
                    tRecurso = new Tupla(herramienta.getId(),herramienta.getNombre());
                }
                
                if(recurso instanceof Material)
                {
                    Material material = (Material) recurso;
                    cmbTiposRecurso.setSelectedIndex(0);
                    tRecurso = new Tupla(material.getId(),material.getNombre());
                }
                seleccionarItemComboBox(tRecurso.getId(),cmbRecursos);
                Tupla tRecursoEspecifico = new Tupla(recursoEsp.getId(),recursoEsp.getNombre());
                seleccionarItemComboBox(tRecursoEspecifico.getId(), cmbRecursosEspecificos);
                
            }
            else if (item instanceof TipoAlquilerCompra)
            {
                TipoAlquilerCompra tipoAC = (TipoAlquilerCompra) item;
                cmbTiposRecurso.setSelectedIndex(2);
                seleccionarItemComboBox(tipoAC.getId(), cmbRecursos);
            }
            else if(item instanceof TipoAdicional)
            {
                TipoAdicional tipoAdi = (TipoAdicional) item;
                cmbTiposRecurso.setSelectedIndex(3);
                seleccionarItemComboBox(tipoAdi.getId(), cmbRecursos);
            }
            seleccionarItemTable(proveedor.getId(), tablaProveedores);
            mostrarPrecios();
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
        }
    }
    
    private void seleccionarItemComboBox(int id, JComboBox cb)
    {
        DefaultComboBoxModel cmb = (DefaultComboBoxModel)cb.getModel();
        for(int i=0; i<cmb.getSize();i++)
        {
            Tupla tupla = (Tupla)cmb.getElementAt(i);
            if(tupla.getId() == id)
            {
                cb.setSelectedIndex(i);
                
            }
        }
    }
    
    private void seleccionarItemTable(int id, JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            Tupla tupla = (Tupla) dtm.getValueAt(i, 0);
            if (tupla.getId() == id) {
                table.setRowSelectionInterval(i, i);

            }
        }
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

    private void mostrarRecursos() {

        if (cmbTiposRecurso.getSelectedIndex() != -1) {
            DefaultComboBoxModel valores = new DefaultComboBoxModel();

            Tupla t = (Tupla) cmbTiposRecurso.getSelectedItem();
            ArrayList<Tupla> lista = gestor.mostrarRecursos(t.getId());
            for (Tupla nombre : lista) {
                valores.addElement(nombre);
            }

            cmbRecursos.setModel(valores);
            cmbRecursos.setSelectedIndex(-1);
            cmbRecursosEspecificos.setModel(new DefaultComboBoxModel());
            DefaultTableModel modeloTablaPro = (DefaultTableModel) tablaProveedores.getModel();
            modeloTablaPro.setRowCount(0);
            DefaultTableModel modeloTablaPre = (DefaultTableModel) tablaPrecios.getModel();
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

            Tupla tRubro = (Tupla) cmbTiposRecurso.getSelectedItem();
            Tupla t = (Tupla) cmbRecursos.getSelectedItem();
            
            if(tRubro.getId()< 2) // Sólo Materiales y Herramientas
            {
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
            else
            {
                mostrarProveedores();
            }

        }

    }
    
    /**
     * Método que se encarga de cargar los proveedores en el caso de que:
     * - Si seleccionaste como tipo de recurso "Material" o "Herramienta" y hayas seleccionado un recurso específico
     * - SI seleccionaste "Alquileres y Compras" o "Adicionales" y hayas tan solo seleccionado el recurso.
     */
    private void mostrarProveedores()
    {
       
        if(cmbRecursosEspecificos.getSelectedIndex()!=-1)
       {
            Tupla t = (Tupla) cmbRecursosEspecificos.getSelectedItem() ;
            List<NTupla> lista = gestor.mostrarProveedores(t.getId()); // Para el caso de Materiales y Herramientas
            mostrarProveedores(lista);
        }
        else
        {
            Tupla tRubro = (Tupla) cmbTiposRecurso.getSelectedItem();
            if(tRubro.getId() > 1)
            {
                List<NTupla> lista = gestor.mostrarProveedores(tRubro.getNombre()); // Para el caso de Alquileres/Compras y Adicionales
                mostrarProveedores(lista);
            }
        }
    }
    
    /**
     * Sobre carga del método mostrarProveedores(). Permite mostrar los proveedores en base a una lista que se
     * pasa por parámetros
     * @param listaProveedores 
     */
    public void mostrarProveedores(List<NTupla> listaProveedores)
    {
        DefaultTableModel valores = (DefaultTableModel)tablaProveedores.getModel();
        valores.setRowCount(0);
        for (NTupla nTuplaP : listaProveedores)
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

    private void mostrarPrecios()
    {
       DefaultTableModel valores = (DefaultTableModel)tablaPrecios.getModel();
       valores.setRowCount(0);
       if(tablaProveedores.getSelectedRow()!=-1 && cmbRecursosEspecificos.getSelectedIndex()!=-1)
       {
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
       else // Alquileres/Compra y Adicionales
        { 
            Tupla tRubro = (Tupla) cmbTiposRecurso.getSelectedItem();
            if(tablaProveedores.getSelectedRow()!=-1 && tRubro.getId() > 1)
            {
                if(tRubro.getId() > 1)
                {
                    JOptionPane.showInternalMessageDialog(this, "No se encuentran precios registrados para este recurso" , "Precio no encontrado", JOptionPane.INFORMATION_MESSAGE);
                }
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

        jPanel1 = new javax.swing.JPanel();
        cmbRecursosEspecificos = new javax.swing.JComboBox();
        cmbRecursos = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbTiposRecurso = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPrecios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar Precios por Recurso");

        cmbRecursosEspecificos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4mm de Goma Azul" }));
        cmbRecursosEspecificos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosEspecificosActionPerformed(evt);
            }
        });

        cmbRecursos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guantes" }));
        cmbRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosActionPerformed(evt);
            }
        });

        jLabel3.setText("Especificación: ");

        jLabel2.setText("Rubro:");

        jLabel4.setText("Nombre:");

        cmbTiposRecurso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Material", "Herramienta" }));
        cmbTiposRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTiposRecursoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTiposRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTiposRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores"));

        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
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
        tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(270);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Precios"));

        tablaPrecios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Cantidad", "Precio", "Actualizado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPrecios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbRecursos;
    private javax.swing.JComboBox cmbRecursosEspecificos;
    private javax.swing.JComboBox cmbTiposRecurso;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
