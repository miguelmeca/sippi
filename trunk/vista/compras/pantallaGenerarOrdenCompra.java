/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaGenerarOrdenCompra.java
 *
 * Created on 27-ago-2010, 11:30:49
 */

package vista.compras;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import util.Tupla;
import util.NTupla;
import controlador.Compras.GestorGenerarOrdenCompra;

/**
 *
 * @author Administrador
 */
public class pantallaGenerarOrdenCompra extends javax.swing.JInternalFrame {

    private GestorGenerarOrdenCompra gestor;
    private boolean mostroPrecios=false;
    /** Creates new form pantallaGenerarOrdenCompra */
    public pantallaGenerarOrdenCompra() {
        gestor = new GestorGenerarOrdenCompra(this);
        initComponents();
        habilitarVentana();


    }
    public void habilitarVentana()
    {
        mostrarTipoRecurso();


    }
    private void mostrarTipoRecurso()
    {
        mostroPrecios=false;
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
        mostroPrecios=false;
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
        mostroPrecios=false;
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
    private void mostrarUnidadMedida()
    {
        if(cmbRecursos.getSelectedIndex()!=-1)
       {
        //DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbRecursos.getSelectedItem() ;
        String um = gestor.mostrarUnidadMedida(t.getId());
        lblUnidadMedida.setText(um);
        }
    }
    private void mostrarProveedores()
    {
       mostroPrecios=false;
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
            mostroPrecios=true;//Pasa por aquí si es que se muestra por lo menos un precio
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
        setearPrecioXCantidad();
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

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbTiposRecurso = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbRecursosEspecificos = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cmbRecursos = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblUnidadMedida = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ftxtCantidad = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPrecios = new javax.swing.JTable();
        lblPrecioParcial = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblPrecioUnitario = new javax.swing.JLabel();
        btnAgregarDetalle = new javax.swing.JButton();
        btnQuitarDetalle = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetallesOC = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setTitle("Generar Orden de Compra");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Obra:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Recursos"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "", "Cantidad", "Proveedor", "P.U", "P.P"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Recursos para Obra", jPanel1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Tipo de Recurso:");

        cmbTiposRecurso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Indumentaria", "Material", "Herramienta" }));
        cmbTiposRecurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTiposRecursoActionPerformed(evt);
            }
        });

        jLabel3.setText("Especificación: ");

        cmbRecursosEspecificos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4mm de Goma Azul" }));
        cmbRecursosEspecificos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosEspecificosActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre:");

        cmbRecursos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guantes" }));
        cmbRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRecursosActionPerformed(evt);
            }
        });

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
        tablaProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaProveedoresMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablaProveedores);
        tablaProveedores.getColumnModel().getColumn(1).setPreferredWidth(30);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel5.setText("Precio Parcial:");

        jLabel9.setText("Descripción:");

        lblUnidadMedida.setText("Unidades.");

        jLabel7.setText("Cantidad:");

        ftxtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.###"))));
        ftxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtCantidadActionPerformed(evt);
            }
        });
        ftxtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftxtCantidadFocusLost(evt);
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
        tablaPrecios.setEnabled(false);
        tablaPrecios.setRowSelectionAllowed(false);
        jScrollPane4.setViewportView(tablaPrecios);
        tablaPrecios.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaPrecios.getColumnModel().getColumn(1).setPreferredWidth(40);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblPrecioParcial.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPrecioParcial.setText("$0.00");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("SubTotal:");

        jTextField2.setEditable(false);

        jLabel8.setText("Precio Unitariol:");

        lblPrecioUnitario.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPrecioUnitario.setText("$0.00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPrecioUnitario)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPrecioParcial))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ftxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUnidadMedida)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(4, 4, 4)
                                    .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(78, 78, 78))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(92, 92, 92)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, 0, 257, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTiposRecurso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTiposRecurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbRecursosEspecificos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ftxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(lblUnidadMedida)
                            .addComponent(jLabel9)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblPrecioUnitario)
                            .addComponent(jLabel5)
                            .addComponent(lblPrecioParcial))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Recursos Generales", jPanel3);

        btnAgregarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        btnQuitarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de Orden de Compra"));

        tablaDetallesOC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "Nombre", "Descripción", "Proveedor", "P.U", "P.P"
            }
        ));
        tablaDetallesOC.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tablaDetallesOC);
        tablaDetallesOC.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaDetallesOC.getColumnModel().getColumn(4).setPreferredWidth(20);
        tablaDetallesOC.getColumnModel().getColumn(5).setPreferredWidth(20);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jButton4.setText("Generar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btnAgregarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnQuitarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(21, 21, 21))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarDetalle)
                    .addComponent(btnQuitarDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(82, 82, 82))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed
        if(validarDatos())
        {
            
          DefaultTableModel modelo = (DefaultTableModel) tablaDetallesOC.getModel();
          Object[] det = new Object[6];
          
          double canti=Double.parseDouble(ftxtCantidad.getText());
          
          NTupla ntCant=new NTupla();
          //nt.setId(1);
          ntCant.setNombre(canti+" "+lblUnidadMedida.getText());
          ntCant.setData(canti);
          det[0]=ntCant;
          Tupla tRE=new Tupla();
          //tRE=((Tupla)cmbRecursosEspecificos.getSelectedItem());
          tRE.setId(((Tupla)cmbRecursosEspecificos.getSelectedItem()).getId());
          tRE.setNombre(((Tupla)cmbRecursosEspecificos.getSelectedItem()).getNombre());
          //ntRE.setId(cmbRecursosEspecificos.getSelectedItem());
          String nomR=((Tupla)cmbRecursos.getSelectedItem()).getNombre();
          tRE.setNombre(nomR+" "+tRE.getNombre());
          //ntRE.setData(canti);
          det[1]=tRE;
          det[2]=txtDescripcion.getText();
          Tupla tProv=(Tupla)tablaProveedores.getValueAt(tablaProveedores.getSelectedRow(), 0);
          det[3]=tProv;
          //double canti=Double.parseDouble(ftxtCantidad.getText());

          double[] precios =gestor.precioParcial(canti);
          det[4]=precios[0];
          det[5]=precios[1];
          boolean repetido=false;
          for (int i= 0; i < tablaDetallesOC.getRowCount(); i++)
          {
           if( (((Tupla)modelo.getValueAt(i, 1)).getId()==tRE.getId()) &&   (((Tupla)modelo.getValueAt(i, 3)).getId()==tProv.getId())  &&  (((String)modelo.getValueAt(i, 2)).equalsIgnoreCase((String)det[2]))  )
           {
              double d1=(Double)((NTupla)modelo.getValueAt(i, 0)).getData();
               double d2=(Double)(((NTupla)det[0]).getData());
               double can=d1+d2;
               //can=can+(Double)(((NTupla)det[0]).getData());
               //ntCant.setData(can);

               ((NTupla)modelo.getValueAt(i, 0)).setData(can);
               ((NTupla)modelo.getValueAt(i, 0)).setNombre(can+" "+lblUnidadMedida.getText());
               det[0]=(NTupla)modelo.getValueAt(i, 0);
               precios =gestor.precioParcial(can);
               det[4]=precios[0];
               det[5]=precios[1];
               repetido=true;
               modelo.removeRow(i);
               modelo.insertRow(i, det);


           }
          }
          if(!repetido)
          {modelo.addRow(det);}


        }
    }//GEN-LAST:event_btnAgregarDetalleActionPerformed

    private void cmbTiposRecursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTiposRecursoActionPerformed
       mostrarRecursos();
    }//GEN-LAST:event_cmbTiposRecursoActionPerformed

    private void cmbRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursosActionPerformed
        mostrarRecursosEspecificos();
        mostrarUnidadMedida();
    }//GEN-LAST:event_cmbRecursosActionPerformed

    private void cmbRecursosEspecificosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRecursosEspecificosActionPerformed
        mostrarProveedores();
    }//GEN-LAST:event_cmbRecursosEspecificosActionPerformed

    private void tablaProveedoresMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedoresMouseReleased
        mostrarPrecios();
    }//GEN-LAST:event_tablaProveedoresMouseReleased

    private void ftxtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtCantidadFocusLost
            double canti=Double.parseDouble(ftxtCantidad.getText());
            if (canti<0)
            {
                canti=canti-(2*canti);//Convierto a valor positivo si es q es negativo
                ftxtCantidad.setText(Double.toString(canti));
            }
        setearPrecioXCantidad();
}//GEN-LAST:event_ftxtCantidadFocusLost

    private void ftxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtCantidadActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ftxtCantidadActionPerformed

    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
       if((tablaDetallesOC.getSelectedRowCount())==1)
        {
        DefaultTableModel modelo = (DefaultTableModel) tablaDetallesOC.getModel();
        //Tupla tipo=(Tupla)modelo.getValueAt(tablaDetallesOC.getSelectedRow(), 0) ;
        //((DefaultComboBoxModel)lstTiposEspecialidad.getModel()).addElement(tipo);

        modelo.removeRow(tablaDetallesOC.getSelectedRow());
        }
    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea cancelar?","Cancelar",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        {       this.dispose();}
    }//GEN-LAST:event_jButton3ActionPerformed
private boolean validarCantidad()
{
    try{
            Double.parseDouble(ftxtCantidad.getText());
                    return true;
    }
    catch(Exception ex)
   {
      return false;

   }
}
private boolean validarDatos()
{
    if(!validarCantidad())
    {
        JOptionPane.showMessageDialog(this.getParent(),"Debe introducir una cantidad a comprar","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if(!(tablaProveedores.getSelectedRow()!=-1))
    {
        JOptionPane.showMessageDialog(this.getParent(),"Debe selecionar un proveedor","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if(!mostroPrecios)// Nunca deberia pasar por aca... pero nunca se sabe...
    {
        JOptionPane.showMessageDialog(this.getParent(),"Debe selecionar un proveedor que tenga precios registrados","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
}
private void setearPrecioXCantidad()
{
   
    if(validarCantidad() && tablaProveedores.getSelectedRow()!=-1 )
        {
           // int idProv=((Tupla)(tablaProveedores.getModel().getValueAt(tablaProveedores.getSelectedRow(), 0))).getId();
            double canti=Double.parseDouble(ftxtCantidad.getText());
            /*if (canti<0)
            {
                canti=canti-(2*canti);//Convierto a valor positivo si es q es negativo
                ftxtCantidad.setText(Double.toString(canti));
            }*/
            double[] precios =gestor.precioParcial(canti);
            lblPrecioUnitario.setText("$ "+precios[0]);
            lblPrecioParcial.setText("$ "+precios[1]);
        }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JComboBox cmbRecursos;
    private javax.swing.JComboBox cmbRecursosEspecificos;
    private javax.swing.JComboBox cmbTiposRecurso;
    private javax.swing.JFormattedTextField ftxtCantidad;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblPrecioParcial;
    private javax.swing.JLabel lblPrecioUnitario;
    private javax.swing.JLabel lblUnidadMedida;
    private javax.swing.JTable tablaDetallesOC;
    private javax.swing.JTable tablaPrecios;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables

}
