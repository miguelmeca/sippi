/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import org.hibernate.HibernateException;
import util.HibernateUtil;
import util.SwingPanel;
import util.Tupla;
import vista.comer.pantallaListadoEmpresaCliente;
import vista.comer.pantallaListadoProveedores;
import vista.interfaces.ICallBackGen;

/**
 *
 * @author Administrador
 */
public class PantallaGestionarRecursos extends javax.swing.JInternalFrame  implements ICallBackGen {    
    
    private Class claseBase;
    private RecursoEspecifico recursoEspecifico;
    private Recurso           recurso;
    private int id = -1;
    
    private static final String QUERY_LIST_RECURSOS_MATERIAL = "FROM Material";
    private static final String QUERY_LIST_RECURSOS_HERRAMIENTA = "FROM Herramienta";
    
    public static final String CALLBACK_NUEVO_RECURSO = "NuevoRecurso";
    
    private static final String FLAG_SELECCIONAR_PROVEEDOR = "Seleccionar_Proveedor";

    public PantallaGestionarRecursos(Class claseBase) {
        this.claseBase = claseBase;
        initComponents();
        initTituloVentana();
        initNombres();
        initComboRecursos();
    }    
    
    public PantallaGestionarRecursos(Class claseBase,int id) {
        this.claseBase = claseBase;
        this.id = id;
        initComponents();
        initTituloVentana();
        initComboRecursos();
        initNombres();
        initLoadComponent();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmbRecursos = new javax.swing.JComboBox();
        btnAddRecurso = new javax.swing.JButton();
        btnModificarRecurso = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreEspec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescEspec = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        btnAgregarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevoModificar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestionar los Recursos de la Empresa");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Recurso"));

        cmbRecursos.setEditable(true);
        cmbRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosActionPerformed(evt);
            }
        });

        btnAddRecurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAddRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRecursoActionPerformed(evt);
            }
        });

        btnModificarRecurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png"))); // NOI18N
        btnModificarRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarRecursoActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 102)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Ayuda: ");

        jLabel3.setText("Seleccione el Nombre General del recurso que desee agregar, algunos ejemplos:");

        jLabel6.setText("Materiales: Chapas, Hierros, Tornillos, Pintura, Electrodos, etc");

        jLabel9.setText("Herramientas: Tornos, Fresadora, Autoelevadora, Moladora, etc");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbRecursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbRecursos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(btnAddRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnModificarRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificaciones"));

        jLabel4.setText("Nombre:");

        jLabel5.setText("Descripción:");

        txtDescEspec.setColumns(20);
        txtDescEspec.setRows(5);
        jScrollPane1.setViewportView(txtDescEspec);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores para este Recurso"));

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Razon Social", "Cuit / Cuil"
            }
        ));
        jScrollPane2.setViewportView(tblProveedores);

        btnAgregarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarProveedor.setToolTipText("Agregar Proveedor para este Recurso");
        btnAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnEliminarProveedor.setToolTipText("Eliminar un proveedor para este recurso");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarProveedor))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarProveedor)
                    .addComponent(btnAgregarProveedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreEspec)
                            .addComponent(jScrollPane1)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombreEspec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnNuevoModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoModificar.setText("Nuevo / Modificar");
        btnNuevoModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNuevoModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
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
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevoModificar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRecursosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProveedorActionPerformed
        
        pantallaListadoProveedores listado = new pantallaListadoProveedores();
        listado.setSeleccionarEnabled(this,PantallaGestionarRecursos.FLAG_SELECCIONAR_PROVEEDOR);
        SwingPanel.getInstance().addWindow(listado);
        listado.setVisible(true);  
        
    }//GEN-LAST:event_btnAgregarProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if(tblProveedores.getSelectedRow()!=-1)
        {
            int fila = tblProveedores.getSelectedRow();
            DefaultTableModel modelo = (DefaultTableModel)tblProveedores.getModel();
            Tupla t = (Tupla)modelo.getValueAt(tblProveedores.getSelectedRow(),0);

            int n = JOptionPane.showConfirmDialog(this,"¿Realmente desea eliminar el proveedor de la lista?","Está Seguro?",JOptionPane.YES_NO_OPTION);

            if(n==JOptionPane.YES_OPTION)
            {
                modelo.removeRow(fila);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Seleccione un Proveedor para eliminar de la lista","Atención!",JOptionPane.QUESTION_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoModificarActionPerformed

        if(txtNombreEspec.getText().isEmpty())
        {
            mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,"Atencion!","Ingrese un nombre para el Recurso");
            return;
        }
        
        if(this.id==-1)
        {
            // Creo uno nuevo
            this.recursoEspecifico = new RecursoEspecifico();
            this.recursoEspecifico.setDescipcion(txtDescEspec.getText());
            this.recursoEspecifico.setNombre(txtNombreEspec.getText());
        }
        else
        {
            // Modifico
            this.recursoEspecifico.setDescipcion(txtDescEspec.getText());
            this.recursoEspecifico.setNombre(txtNombreEspec.getText());
        }
                
        // UN recursoEspecifico tiene una Lista de RecursoXProveedor
        List<RecursoXProveedor> proveedores = new ArrayList<RecursoXProveedor>();
        DefaultTableModel modelo = (DefaultTableModel)tblProveedores.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Tupla fila = (Tupla)modelo.getValueAt(i, 0);

            Proveedor p = getProveedor(fila.getId());
            if(p!=null)
            {
                RecursoXProveedor rxp = new RecursoXProveedor();
                rxp.setProveedor(p);
                proveedores.add(rxp);

                // A su vez ese proveedor tiene la lista de RecursosEspecificos
                p.getListaArticulos().add(this.recursoEspecifico);
            }
            else
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar el Proveedor a asociar");
                return;
            }
        }
        this.recursoEspecifico.setProveedores(proveedores);
            
            // Ahora creo cargo el RECURSO y le asocio el recurso Especifico
            Tupla tp = (Tupla) cmbRecursos.getSelectedItem();
            Recurso r = getRecurso(tp.getId());
            if(r!=null)
            {
                if(!r.yaTieneRecursoEspecifico(this.recursoEspecifico.getId()))
                {
                    r.getRecursosEspecificos().add(this.recursoEspecifico);
                }
            }
            else
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar el Recurso a asociar");
                return;
            }
            
            // Si cambie el recurso, tengo que borrar la relacion vieja a Recurso Especifico
            // Solo si no estoy creando uno nuevo
            if(this.id!=-1 && r.getId()!=this.recurso.getId())
            {
                // r = recurso nuevo
                // this.recurso = recurso viejo
                List lrecesp = this.recurso.getRecursosEspecificos();
                for (int i = 0; i < lrecesp.size(); i++) {
                    RecursoEspecifico rec = (RecursoEspecifico)lrecesp.get(i);
                    if(rec.getId()==this.recursoEspecifico.getId())
                    {
                        lrecesp.remove(i);
                    }
                }
            }
            else
            {
                this.recurso = r;
            }
            
            try
            {
                HibernateUtil.beginTransaction();
                    HibernateUtil.getSession().save(this.recursoEspecifico);

                    for (int i = 0; i < this.recursoEspecifico.getRecursosXProveedor().size(); i++) {

                        RecursoXProveedor rxp = this.recursoEspecifico.getRecursosXProveedor().get(i);
                        HibernateUtil.getSession().saveOrUpdate(rxp);

                        Proveedor p = rxp.getProveedor();
                        HibernateUtil.getSession().update(p);
                    }

                    HibernateUtil.getSession().update(this.recursoEspecifico);
                    HibernateUtil.getSession().update(r);
                    HibernateUtil.getSession().saveOrUpdate(this.recurso);

                HibernateUtil.commitTransaction();

            }catch(Exception e)
            {
                HibernateUtil.rollbackTransaction();
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al Crear el nuevo Recurso Especifico\n"+e.getMessage());
                e.printStackTrace();
                return;
            }        
        
            if(this.id==-1)
            {
                mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,"Atencion!","El nuevo Recurso se creo Exitosamente !!");
            }
            else
            {
                mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,"Atencion!","El Recurso se modificó Exitosamente !!");
            }
            
        this.dispose();
    }//GEN-LAST:event_btnNuevoModificarActionPerformed

    private void btnAddRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRecursoActionPerformed
       
        PantallaGestionarRecurso win = new PantallaGestionarRecurso(this.claseBase);
        SwingPanel.getInstance().addWindow(win);
        win.setCallback(this);
        win.setVisible(true); 
        
    }//GEN-LAST:event_btnAddRecursoActionPerformed

    private void btnModificarRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarRecursoActionPerformed

        Tupla tp = (Tupla)cmbRecursos.getSelectedItem();
        if(tp!=null)
        {
            PantallaGestionarRecurso win = new PantallaGestionarRecurso(this.claseBase,tp.getId());
            SwingPanel.getInstance().addWindow(win);
            win.setCallback(this);
            win.setVisible(true); 
        }
    }//GEN-LAST:event_btnModificarRecursoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRecurso;
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnModificarRecurso;
    private javax.swing.JButton btnNuevoModificar;
    private javax.swing.JComboBox cmbRecursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTextArea txtDescEspec;
    private javax.swing.JTextField txtNombreEspec;
    // End of variables declaration//GEN-END:variables

    private void initLoadComponent() {
        
        try
        {
            HibernateUtil.beginTransaction();
            recursoEspecifico = (RecursoEspecifico) HibernateUtil.getSession().load(RecursoEspecifico.class,this.id);
            HibernateUtil.commitTransaction();
        }catch(Exception e)
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar el Recurso indicado");
            this.dispose();
        }
        
        if(recursoEspecifico!=null)
        {
            txtNombreEspec.setText(recursoEspecifico.getNombre());
            
            if(recursoEspecifico.getDescipcion()!=null)
            {
                txtDescEspec.setText(recursoEspecifico.getDescipcion());
            }
            
            // Get Proveedores
            DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel();
            for (int i = 0; i < recursoEspecifico.getProveedores().size(); i++) {
                RecursoXProveedor rxp = recursoEspecifico.getProveedores().get(i);
                if(rxp.getProveedor()!=null)
                {
                    Tupla tp = new Tupla(rxp.getProveedor().getId(),rxp.getProveedor().getRazonSocial());

                    Object[] fila = new Object[2];
                        fila[0] = tp;
                        fila[1] = rxp.getProveedor().getCuit();

                    modelo.addRow(fila);
                }
            }
        }
        
        // Get Recurso
        this.recurso = recursoEspecifico.getRecurso();
        if(this.recurso!=null)
        {   
            for (int i = 0; i < cmbRecursos.getItemCount(); i++) {
                Tupla tp = (Tupla) cmbRecursos.getItemAt(i);
                if(tp.getId()==this.recurso.getId())
                {
                    cmbRecursos.setSelectedIndex(i);
                }
            }
        }
        else
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar el Recurso indicado");
        }

        
    }
    
    
    /**
     * Muestra un mensaje
     * @param tipo
     * @param titulo
     * @param mensaje 
     */
    public void mostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }

    /**
     * Inicializa el combo de recursos segun con la clase que se esté trabajando
     */
    private void initComboRecursos() {
        
        cmbRecursos.removeAllItems();
        try {
                HibernateUtil.beginTransaction();
                List<Recurso> listaRec = HibernateUtil.getSession().createQuery(getRecursosListQuery()).list();
                HibernateUtil.commitTransaction();
                
                for (int i = 0; i < listaRec.size(); i++) {
                    Recurso rec1 = listaRec.get(i);
                    Tupla tp = new Tupla(rec1.getId(),rec1.getNombre()+" ["+rec1.getUnidadDeMedida().getAbreviatura()+"]");
                    cmbRecursos.addItem(tp);
                }
                
        }catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
    }

    private void initTituloVentana() {
        if(claseBase == Material.class){
            this.setTitle("Gestión de los Materiales de la Empresa");
        }
        else if(claseBase == Herramienta.class){
            this.setTitle("Gestión de las Herramientas de la Empresa");
        }
        else{
            this.setTitle("Gestión de los Recursos de la Empresa");
        }
        
    }
    
    private String getRecursosListQuery()
    {
        if(claseBase == Material.class){
            return PantallaGestionarRecursos.QUERY_LIST_RECURSOS_MATERIAL;
        }
        else{
            return PantallaGestionarRecursos.QUERY_LIST_RECURSOS_HERRAMIENTA;
        }
    }

    @Override
    public void actualizar(int id, String flag, Class tipo) {
        
        // Agrego un nuevo Recurso, lo selecciono
        if(flag.equals(PantallaGestionarRecursos.CALLBACK_NUEVO_RECURSO))
        {
            initComboRecursos();
            for (int i = 0; i < cmbRecursos.getItemCount(); i++) {
                Tupla tp  = (Tupla) cmbRecursos.getItemAt(i);
                if(tp.getId()==id)
                {
                    cmbRecursos.setSelectedItem(tp);
                }
            }
            // Cargo el Recurso
        }
        
        // Selecciono un Proveedor, lo agergo a la lista
        if(flag.equals(PantallaGestionarRecursos.FLAG_SELECCIONAR_PROVEEDOR))
        {
            // Veo si no esta ya agregado
            if(elProveedorYaEstaAgregado(id))
            {
                return;
            }
            
            try
            {
                HibernateUtil.beginTransaction();
                Proveedor p = (Proveedor) HibernateUtil.getSession().load(tipo,id);
                HibernateUtil.commitTransaction();
                
                DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel();
                
                Tupla tp = new Tupla(p.getId(),p.getRazonSocial());
                
                Object[] fila = new Object[2];
                    fila[0] = tp;
                    fila[1] = p.getCuit();
                    
                modelo.addRow(fila);
                
            }catch(Exception e)
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar el Proveedor Seleccionado");
            }
        }
    }

    private void initNombres() {
        if(id==-1)
        {
            // No cargo, creo uno nuevo
            btnNuevoModificar.setText("Crear Nuevo");
        }
        else
        {
            // Voy a cargar y modificar
            btnNuevoModificar.setText("Modificar");
        }
    }
    
    private Proveedor getProveedor(int id)
    {
            try
            {
                HibernateUtil.beginTransaction();
                Proveedor p = (Proveedor) HibernateUtil.getSession().load(Proveedor.class,id);
                HibernateUtil.commitTransaction();
                
                return p;
                
            }catch(Exception e)
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar el Proveedor");
            }
        return null;
    }
    
    private Recurso getRecurso(int id)
    {
        try
        {
            HibernateUtil.beginTransaction();
            Recurso r = (Recurso) HibernateUtil.getSession().load(Recurso.class,id);
            HibernateUtil.commitTransaction();

            return r;

        }catch(Exception e)
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo cargar el Recurso");
        }
        return null;
    }    
    
    private boolean elProveedorYaEstaAgregado(int id)
    {
        DefaultTableModel modelo = (DefaultTableModel)tblProveedores.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Tupla fila = (Tupla)modelo.getValueAt(i, 0);

            Proveedor p = getProveedor(fila.getId());
            if(p!=null)
            {
                if(p.getId()==id)
                {
                    return true;
                }
            }
        }
        return false;
    }
}