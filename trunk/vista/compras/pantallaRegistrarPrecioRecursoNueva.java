package vista.compras;

import controlador.Compras.gestorRegistrarPrecioRecurso;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.UnidadDeMedida;
import util.LogUtil;
import util.NTupla;
import util.StringUtil;
import util.TablaUtil;
import util.Tupla;

/**
 * @author Iuga
 */
public class pantallaRegistrarPrecioRecursoNueva extends javax.swing.JInternalFrame {

    private gestorRegistrarPrecioRecurso gestor;
    private DefaultTableModel modeloFinal;
    private String unidadMedida;

    /** Creates new form pantallaRegistrarPrecioRecurso */
    public pantallaRegistrarPrecioRecursoNueva() {
        initComponents();

        gestor = new gestorRegistrarPrecioRecurso(this);

        habilitarVentana();

    }

    private void habilitarVentana()
    {
        initListaProveedores();
    }

    private void initListaProveedores()
    {
        ArrayList<Tupla> lista = gestor.mostrarProveedores();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No hay Proveedores Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbProveedores.setModel(valores);
    }


    private void cargarRecursos(int idProveedor)
    {

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
        cmbProveedores = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRecursos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbLEP = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        lblUnidadMedida = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JFormattedTextField();
        txtPrecio = new javax.swing.JFormattedTextField();
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("1. Seleccione el Recurso al que le registrará el Precio"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        jLabel3.setText("Buscar:");

        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Herramienta" }));
        cmbProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProveedoresActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Proveedor:");

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroKeyReleased(evt);
            }
        });

        tablaRecursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rubro", "Nombre", "Especificación", "Precios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaRecursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaRecursosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRecursos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("2. Actualizar el Precio para el Recuso Seleccionado"));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nuevo Precio");

        jLabel8.setText("Fecha de Vigencia de la cotización");

        cmbLEP.setToolTipText("Dejar en Blanco si no se tiene");
        cmbLEP.setDateFormatString("dd/MM/yyyy");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Cantidad");

        lblUnidadMedida.setText("UN.");

        jLabel12.setText("$");

        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCantidad.setText("1");

        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        jButton3.setText("Actualizar Precio");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 590, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbLEP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(546, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblUnidadMedida)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(cmbLEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedoresActionPerformed

        Tupla tp = (Tupla) cmbProveedores.getSelectedItem();
        if(tp.getId()!=0)
        {
            txtFiltro.setText("");
            txtPrecio.setText("");
            cmbLEP.cleanup();
            mostrarRecursosEspecificos(tp.getId());
        }


    }//GEN-LAST:event_cmbProveedoresActionPerformed

    private void mostrarRecursosEspecificos(int idProveedor)
    {
        DefaultTableModel modelo = (DefaultTableModel)tablaRecursos.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        ArrayList<NTupla> listaRecEsp = gestor.mostrarRecursosEspecificos(idProveedor);
        Iterator<NTupla> itp = listaRecEsp.iterator();

        int i = 0;
        while (itp.hasNext())
        {
            NTupla ntp = itp.next();
            Object[] fila = new Object[4];
            fila[0] = ntp;
                String[] datos = (String[])ntp.getData();
                fila[1] = datos[0];
                fila[2] = datos[1];

                if(datos[2].isEmpty())
                {
                    fila[3] = "No hay Precios Cargados";
                }
                else
                {
                    fila[3] = datos[2];
                }
                
            modelo.addRow(fila);

                // REDIMENSIONO LAS FILAS !!! -----------------------------------
                String item = datos[2];
                int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                if(cantItems!=0)
                {
                    tablaRecursos.setRowHeight(i,16*cantItems);
                }
                LogUtil.addDebug("Registrar Precios: Cantidad de Repeticiones: "+cantItems);
                // REDIMENSIONO LA FILA !!! -----------------------------------
                i++;
        }



    }


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

           // VALIDO TODOS LOS FUCKING DATOS
        boolean valido = true;
        // RECURSO ESPECIFICO
        if(tablaRecursos.getSelectedRow()==-1)
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Seleccione un recurso","Faltan Datos",JOptionPane.ERROR_MESSAGE);
        }
        // PROVEEDOR
        Tupla tp_pr = (Tupla) cmbProveedores.getSelectedItem();
        if(tp_pr.getId()==0)
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un Proveedor\nEl precio Actualizado se asignara a él","Faltan Datos",JOptionPane.ERROR_MESSAGE);
        }
        // CANTIDAD
        if(txtCantidad.getText().isEmpty())
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Ingrese una cantidad","Faltan Datos",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            double cant = Double.valueOf(txtCantidad.getText());
            if(cant<=0)
            {
                valido = false;
                JOptionPane.showMessageDialog(this.getParent(),"La Cantidad debe ser mayor a Cero","Faltan Datos",JOptionPane.ERROR_MESSAGE);
            }
        }
        // PRECIO
        if(txtPrecio.getText().isEmpty())
        {
            valido = false;
            JOptionPane.showMessageDialog(this.getParent(),"Ingrese un Precio","Faltan Datos",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            double cant = Double.valueOf(txtPrecio.getText());
            if(cant<=0)
            {
                valido = false;
                JOptionPane.showMessageDialog(this.getParent(),"La Precio debe ser mayor a Cero","Faltan Datos",JOptionPane.ERROR_MESSAGE);
            }
        }
        // LISTOOO
        if(valido)
        {
            // DATOS VALIDOS, GUARDO
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyReleased
         FiltrarTabla();
    }//GEN-LAST:event_txtFiltroKeyReleased

    private void tablaRecursosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRecursosMouseReleased

        if(tablaRecursos.getSelectedRow()!=-1)
        {
            NTupla ntp = (NTupla)tablaRecursos.getValueAt(tablaRecursos.getSelectedRow(),0);
            mostrarUnidadMedida(ntp.getId());
        }

    }//GEN-LAST:event_tablaRecursosMouseReleased

    private void mostrarUnidadMedida(int idRecEsp)
    {
        lblUnidadMedida.setText(gestor.mostrarUnidadDeMedida(idRecEsp));
    }

    private void FiltrarTabla()
    {
       TableRowSorter<TableModel> modeloOrdenado;
       modeloOrdenado = new TableRowSorter<TableModel>(tablaRecursos.getModel());
       tablaRecursos.setRowSorter(modeloOrdenado);

           String[] cadena=txtFiltro.getText().trim().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
           modeloOrdenado.setRowFilter(cadenaFilter);

           // CAMBIO LOS TAMAÑOS DE LAS FILAS
           DefaultTableModel modelo = (DefaultTableModel) tablaRecursos.getModel();
           for (int i = 0; i < modelo.getRowCount(); i++)
           {
            // REDIMENSIONO LA FILA !!! -----------------------------------
                int index = modeloOrdenado.convertRowIndexToView(i);
                if(index>-1)
                {
                    // ESTA
                    String item = (String) modelo.getValueAt(i,3);
                    int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                    if(cantItems!=0)
                    {
                        tablaRecursos.setRowHeight(index,16*cantItems);
                    }
                    LogUtil.addDebug("Registrar Precios: Cantidad de Repeticiones: "+cantItems);
                }

                // REDIMENSIONO LA FILA !!! -----------------------------------
           }




    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser cmbLEP;
    private javax.swing.JComboBox cmbProveedores;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblUnidadMedida;
    private javax.swing.JTable tablaRecursos;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JFormattedTextField txtPrecio;
    // End of variables declaration//GEN-END:variables


    /**
     * ME-0020 : No se pudo cargar la lista de Recursos
     * ME-0021 : No se pudo cargar los recursos especificos
     * ME-0021 : No se pudo cargar la lista de proveedores
     * @param cod
     */
    public void MostrarMensaje(String cod)
    {
        if(cod.equals("ME-0020"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Recursos","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("ME-0021"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo el listado de Recursos","Error en la Carga",JOptionPane.ERROR_MESSAGE);

        }
        if(cod.equals("ME-0022"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Proveedores","Error en la Carga",JOptionPane.ERROR_MESSAGE);

        }
        if(cod.equals("ME-0023"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Ultimos Precios","Error en la Carga",JOptionPane.ERROR_MESSAGE);

        }
        // MI-0001
        if(cod.equals("MI-0001"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"Se registro correctamente toda la lista de actualizaciones de precios","Exito",JOptionPane.INFORMATION_MESSAGE);

        }
        if(cod.equals("ME-0024"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"Se detecto un error en la registración de alguno de los items\n Inténtelo nuevamente en un instante","Error",JOptionPane.ERROR_MESSAGE);

        }
        if(cod.equals("ME-0025"))
        {
            // Se guardó en orden
            JOptionPane.showMessageDialog(this.getParent(),"Se produjo un error al registrar el precio","Error",JOptionPane.ERROR_MESSAGE);
}
    }

}
