package vista.compras;

import controlador.Compras.gestorRegistrarPrecioRecurso;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import util.Tupla;

/**
 * @author Iuga
 */
public class pantallaRegistrarPrecioRecurso extends javax.swing.JInternalFrame {

    private gestorRegistrarPrecioRecurso gestor;

    /** Creates new form pantallaRegistrarPrecioRecurso */
    public pantallaRegistrarPrecioRecurso() {
        initComponents();

        gestor = new gestorRegistrarPrecioRecurso(this);

        habilitarVentana();

    }

    private void habilitarVentana()
    {
        initListaRecurso();
        initListaTipoRecurso();
        initListaRecursosEspecificos();
        buscarTipoRecursoPorProveedor();
    }

    private void initListaRecursosEspecificos()
    {
        DefaultListModel valores = new DefaultListModel();
        lstRecursosEspecificos.setModel(valores);
    }

    private void initListaRecurso()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        valores.addElement(new Tupla(0,"Seleccione un Tipo..."));

        cmbRecurso.setModel(valores);
    }

    private void initListaTipoRecurso()
    {
        ArrayList<Tupla> lista = gestor.mostrarTipoRecursos();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No hay Tipos de Recursos Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbTipoRecurso.setModel(valores);
    }

    private void cargarRecursos(int id)
    {
        ArrayList<Tupla> lista = gestor.mostrarRecursosPorTipoRecurso(id);

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No hay Recursos Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbRecurso.setModel(valores);
    }

    private void cargarProveedores()
    {
//        ArrayList<Tupla> lista = gestor.mostrarProveedores();
//
//        DefaultComboBoxModel valores = new DefaultComboBoxModel();
//        Iterator<Tupla> it = lista.iterator();
//
//        if(lista.size()==0)
//        {
//            valores.addElement(new Tupla(0,"No hay Proveedores cargados"));
//        }
//
//        while(it.hasNext()){
//            Tupla tu = it.next();
//            valores.addElement(tu);
//        }
//
//        cmbProveedores.setModel(valores);
    }

    private void buscarTipoRecursoPorProveedor()
    {
//        ArrayList<Tupla> lista = gestor.mostrarTipoDeRecursos();
//
//        DefaultComboBoxModel valores = new DefaultComboBoxModel();
//        Iterator<Tupla> it = lista.iterator();
//
//        if(lista.size()==0)
//        {
//            valores.addElement(new Tupla(0,"No hay Tipo de Recursos cargados"));
//        }
//
//        while(it.hasNext()){
//            Tupla tu = it.next();
//            valores.addElement(tu);
//        }
//
//        cmbTipoRecurso.setModel(valores);
    }

//    private void cargarRecursos(int idTipoRec)
//    {
//        ArrayList<Tupla> lista = gestor.mostrarRecursos(idTipoRec);
//
//        DefaultComboBoxModel valores = new DefaultComboBoxModel();
//        Iterator<Tupla> it = lista.iterator();
//
//        if(lista.size()==0)
//        {
//            valores.addElement(new Tupla(0,"No hay recursos"));
//        }
//
//        while(it.hasNext())
//        {
//            Tupla tu = it.next();
//            valores.addElement(tu);
//        }
//
//        cmbRecurso.setModel(valores);
//    }

    private void cargarListaDeRecursosEspecificos(int idRecurso)
    {
        ArrayList<Tupla> lista = gestor.mostrarRecursosEspecificos(idRecurso);

        DefaultListModel valores = new DefaultListModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No Hay Recursos"));
        }

        while(it.hasNext())
        {
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        lstRecursosEspecificos.setModel(valores);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbRecurso = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstRecursosEspecificos = new javax.swing.JList();
        cmbTipoRecurso = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbProveedores = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbLEP = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Precio de Recurso");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Recursos Disponibles"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("1. Seleccione el Recurso"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Tipo:");

        cmbRecurso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Torno" }));
        cmbRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursoActionPerformed(evt);
            }
        });

        lstRecursosEspecificos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lstRecursosEspecificosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstRecursosEspecificos);

        cmbTipoRecurso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Herramienta" }));
        cmbTipoRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoRecursoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Recurso:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoRecurso, 0, 208, Short.MAX_VALUE)
                            .addComponent(cmbRecurso, 0, 208, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipoRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("2. Datos del Recurso Seleccionado"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Descripción");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Último precio conocido");

        jTextField2.setEditable(false);
        jTextField2.setText("$");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Proveedor:");

        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProveedoresActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setEditable(false);
        txtDescripcion.setRows(5);
        jScrollPane4.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(cmbProveedores, 0, 136, Short.MAX_VALUE))
                        .addGap(66, 66, 66))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("3. Actualizar los Precios"));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nuevo Precio");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Fecha de Vigencia");

        cmbLEP.setDateFormatString("dd/MMM/yyyy");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        jButton1.setText("Actualizar Precio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField4.setText("1");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Cantidad");

        jLabel11.setText("Kg. ");

        jLabel12.setText("$");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbLEP, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addGap(3, 3, 3)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbLEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("3. Listado de Actualizaciones por Realizar"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Nombre", "Proveedor", "Vigencia", "Precio de Venta"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton2.setText("Quitar");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        jButton3.setText("Guardar Lista de Actualizaciones");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedoresActionPerformed

        

    }//GEN-LAST:event_cmbProveedoresActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbTipoRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoRecursoActionPerformed

        Tupla tp = (Tupla)cmbTipoRecurso.getSelectedItem();
        if(tp.getId()!=0)
        {
            initListaRecursosEspecificos(); // VACIO LA LISTA DE RECURSOS ESPECIFICOS
            cargarRecursos(tp.getId());
        }


    }//GEN-LAST:event_cmbTipoRecursoActionPerformed

    private void cmbRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursoActionPerformed

        Tupla tpR = (Tupla)cmbRecurso.getSelectedItem();
        if(tpR.getId()!=0)
        {
            cargarListaDeRecursosEspecificos(tpR.getId());
        }

    }//GEN-LAST:event_cmbRecursoActionPerformed

    private void lstRecursosEspecificosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRecursosEspecificosMouseReleased

        Tupla tp = (Tupla) lstRecursosEspecificos.getSelectedValue();
        gestor.mostrarInfoDeRecursoEspecifico(tp.getId());

    }//GEN-LAST:event_lstRecursosEspecificosMouseReleased

    public void mostrarDescipcionRecursoEspecifico(String desc)
    {
        txtDescripcion.setText(desc);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser cmbLEP;
    private javax.swing.JComboBox cmbProveedores;
    private javax.swing.JComboBox cmbRecurso;
    private javax.swing.JComboBox cmbTipoRecurso;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JList lstRecursosEspecificos;
    private javax.swing.JTextArea txtDescripcion;
    // End of variables declaration//GEN-END:variables


    /**
     * ME-0020 : No se pudo cargar la lista de Recursos
     * ME-0021 : No se pudo cargar los recursos especificos
     * @param cod
     */
    public void MostrarMensaje(String cod)
    {
        if(cod.equals("ME-0020"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Recursos","Error en la Carga",JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        if(cod.equals("ME-0021"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo el listado de Recursos","Error en la Carga",JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

}
