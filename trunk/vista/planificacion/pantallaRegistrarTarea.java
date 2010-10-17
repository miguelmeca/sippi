/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaRegistrarEtapa.java
 *
 * Created on 06-sep-2010, 22:16:38
 */

package vista.planificacion;

import controlador.planificacion.GestorRegistrarAsignacionMateriales;
import controlador.planificacion.GestorRegistrarAsignacionHerramientas;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import util.NTupla;
import util.StringUtil;
import util.SwingPanel;
import util.TablaUtil;
import util.Tupla;
import vista.comer.pantallaBuscarEmpresaCliente;
import vista.interfaces.ICallBack_v2;

/**
 *
 * @author Administrador
 */
public class pantallaRegistrarTarea extends javax.swing.JInternalFrame implements ICallBack_v2 {
    private GestorRegistrarAsignacionMateriales gestorRAM;
    private GestorRegistrarAsignacionHerramientas gestorRAH;
    /** Creates new form pantallaRegistrarEtapa */
    public pantallaRegistrarTarea() {
        initComponents();
        //gestorRAM = new GestorRegistrarAsignacionMateriales(this,0); COMO JORACA MANEJAMOS LAS TAREAS??
        gestorRAM = new GestorRegistrarAsignacionMateriales(this);
        gestorRAH = new GestorRegistrarAsignacionHerramientas(this);
        gestorRAM.crearTareaPrueba();
        gestorRAH.setIdTarea(gestorRAM.getIdTarea());
    }

    private void cargarTabMateriales() {
        ArrayList<Tupla> materiales = gestorRAM.getMaterialesDisponibles();

        DefaultTableModel modelo = (DefaultTableModel)tbMateriales.getModel();

        // VACIO LA TABLA MATERIALES
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();

        int i = 0;
        while (it.hasNext())
        {
            Tupla ntp = (Tupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        DefaultTableModel modelo2 = (DefaultTableModel)tbMaterialEspecifico.getModel();

        // VACIO LA TABLA MATERIAL ESPECIFICO
        TablaUtil.vaciarDefaultTableModel(modelo2);
    }


    private void mostrarEspecificacionMaterial(int id) {
        ArrayList<Tupla> esps = gestorRAM.getEspecificacionMaterial(id);

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialEspecifico.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = esps.iterator();

        while (it.hasNext())
        {
            Tupla ntp = (Tupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
    }

    private void mostrarMaterialesAUtilizar() {
        ArrayList<NTupla> materiales = gestorRAM.getMaterialesAUtilizar();

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialesAUsar.getModel();

        // VACIO LA TABLA HERRAMIENTAS
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();

        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[3];
            fila[0] = ntp;
            Object[] o = (Object[]) ntp.getData();
            fila[1] = o[0];
            fila[2] = o[1];

            modelo.addRow(fila);
        }
        // ACA FALTA PONER LAS HERRAMIENTAS YA SELECCIONADAS
    }

    private void mostrarHerramientasAUtilizar() {
        ArrayList<NTupla> herramientas = gestorRAH.getHerramientasAUtilizar();

        DefaultTableModel modelo = (DefaultTableModel)tbHerramientasSeleccionadas.getModel();

        // VACIO LA TABLA HERRAMIENTAS
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = herramientas.iterator();

        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        // ACA FALTA PONER LAS HERRAMIENTAS YA SELECCIONADAS
    }
    private void cargarHerramientas() {
        ArrayList<NTupla> herramientas = gestorRAH.getHerramientasDeEmpresaDisponibles();

        DefaultTableModel modelo = (DefaultTableModel)tbHerramientasDisponibles.getModel();

        // VACIO LA TABLA HERRAMIENTAS
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = herramientas.iterator();

        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        // ACA FALTA PONER LAS HERRAMIENTAS YA SELECCIONADAS
    }

    private void FiltrarTabla(JTable table,JTextField field){
       TableRowSorter<TableModel> modeloOrdenado;
       modeloOrdenado = new TableRowSorter<TableModel>(table.getModel());
       table.setRowSorter(modeloOrdenado);

           String[] cadena=field.getText().trim().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
           modeloOrdenado.setRowFilter(cadenaFilter);

           // CAMBIO LOS TAMAÃ‘OS DE LAS FILAS
           DefaultTableModel modelo = (DefaultTableModel) table.getModel();
           for (int i = 0; i < modelo.getRowCount(); i++)
           {
            // REDIMENSIONO LA FILA !!! -----------------------------------
                int index = modeloOrdenado.convertRowIndexToView(i);
                if(index>-1)
                {
                    // ESTA
                    String item = (String) modelo.getValueAt(i,0);
                    int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                    if(cantItems!=0)
                    {
                        table.setRowHeight(index,16*cantItems);
                    }
                    //LogUtil.addDebug("ConsultarPreciosXProveedor: Cantidad de Repeticiones: "+cantItems);
                }
                // REDIMENSIONO LA FILA !!! -----------------------------------
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

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMaterialesAUsar = new javax.swing.JTable();
        btnAgregarMaterial = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbMateriales = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbMaterialEspecifico = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarMaterial = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnQuitarMaterial = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscarHerramientas = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbHerramientasDisponibles = new javax.swing.JTable();
        btnAgregarHerramienta = new javax.swing.JButton();
        btnQuitarHerramienta = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbHerramientasSeleccionadas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setClosable(true);
        setTitle("Nueva Tarea");

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Nombre:");

        jLabel2.setText("Nivel de Criticidad:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Crítico", "Importante", "Media", "Baja", "Muy Baja" }));

        jLabel5.setText("Observaciones:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Duración de la Tarea (en Hs/Hombre)"));

        jTextField8.setText("0");

        jTextField7.setEditable(false);
        jTextField7.setText("1");

        jTextField2.setText("5");

        jLabel6.setText("Duración Normal + Duración 50% + Duración 100%");

        jLabel18.setText("Hs. +");

        jLabel19.setText("Hs. +");

        jLabel20.setText("Hs. =");

        jLabel21.setText("Horas Totales");

        jTextField9.setEditable(false);
        jTextField9.setText("6");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Ubicacion:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(482, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addGap(308, 308, 308))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Materiales a Utilizar"));

        tbMaterialesAUsar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Nombre", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(tbMaterialesAUsar);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setMinWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setPreferredWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setMaxWidth(80);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
        );

        btnAgregarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarMaterial.setText("Agregar");
        btnAgregarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMaterialActionPerformed(evt);
            }
        });

        tbMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ));
        tbMateriales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMaterialesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbMateriales);

        tbMaterialEspecifico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especificación"
            }
        ));
        jScrollPane7.setViewportView(tbMaterialEspecifico);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtBuscarMaterial.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscarMaterial.setForeground(java.awt.Color.gray);
        txtBuscarMaterial.setText("Buscar...");
        txtBuscarMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarMaterialFocusGained(evt);
            }
        });
        txtBuscarMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMaterialKeyReleased(evt);
            }
        });

        jLabel8.setText("Seleccione un Material:");

        btnQuitarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/up.png"))); // NOI18N
        btnQuitarMaterial.setText("Quitar");
        btnQuitarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarMaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAgregarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMaterial)
                    .addComponent(btnQuitarMaterial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Utilización de Materiales", jPanel2);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Grupos Creados en otras tareas:"));

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Grupo de Soldadores" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(jList2);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle del grupo seleccionado"));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tornero", "1"}
            },
            new String [] {
                "Rol Asignado al Grupo", "Cantidad Necesaria"
            }
        ));
        jScrollPane4.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Crear un Grupo de Trabajo"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Nombre:");

        jTextField5.setText("Grupo de Soldadores");

        jTextField10.setText("5");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Cantidad de Personas:");

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tornero", "1"}
            },
            new String [] {
                "Rol Asignado al Grupo", "Cantidad Necesaria"
            }
        ));
        jScrollPane11.setViewportView(jTable8);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        jButton8.setText("Asignar");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel22.setText("Asignar Rol a un Grupo");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Soldador", "Tornero" }));

        jLabel23.setText("X");

        jTextField11.setText("3");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton9.setText("Quitar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addComponent(jLabel11)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel23)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addComponent(jButton9)
                                .addGap(13, 13, 13)))
                        .addGap(13, 13, 13))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Grupos de Trabajo Asignados a Esta Tarea"));

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Grupo de Soldadores" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane12.setViewportView(jList3);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle del grupo seleccionado"));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tornero", "1"}
            },
            new String [] {
                "Rol Asignado al Grupo", "Cantidad Necesaria"
            }
        ));
        jScrollPane13.setViewportView(jTable6);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton10.setText("Quitar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10))
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        jButton4.setText("Agregar Grupo Existente");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        jButton3.setText("Crear Grupo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116)
                                .addComponent(jButton4)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jTabbedPane1.addTab("Grupos de Trabajo", jPanel4);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione una Herramienta a utilizar en la Tarea"));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtBuscarHerramientas.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscarHerramientas.setForeground(java.awt.Color.gray);
        txtBuscarHerramientas.setText("Buscar...");
        txtBuscarHerramientas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarHerramientasFocusGained(evt);
            }
        });
        txtBuscarHerramientas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarHerramientasKeyReleased(evt);
            }
        });

        tbHerramientasDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la Herramienta"
            }
        ));
        jScrollPane8.setViewportView(tbHerramientasDisponibles);

        btnAgregarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarHerramienta.setText("Agregar");
        btnAgregarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHerramientaActionPerformed(evt);
            }
        });

        btnQuitarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/up.png"))); // NOI18N
        btnQuitarHerramienta.setText("Quitar");
        btnQuitarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarHerramientaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnAgregarHerramienta, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarHerramienta, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscarHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarHerramienta)
                    .addComponent(btnQuitarHerramienta)))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Herramientas a utilizar en la Tarea"));

        tbHerramientasSeleccionadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la Herramienta"
            }
        ));
        jScrollPane9.setViewportView(tbHerramientasSeleccionadas);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Herramientas Necesarias", jPanel10);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        jButton1.setText("Guardar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(505, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMaterialActionPerformed
        if(tbMaterialEspecifico.getSelectedRow()>=0){
            Tupla re = (Tupla)(tbMaterialEspecifico.getModel()).getValueAt(tbMaterialEspecifico.getSelectedRow(), 0);
            Tupla r = (Tupla)(tbMateriales.getModel()).getValueAt(tbMateriales.getSelectedRow(), 0);
            pantallaSeleccionarProveedorPresupuesto psp = new pantallaSeleccionarProveedorPresupuesto(this.gestorRAM,r.getId(),re.getId());
            SwingPanel.getInstance().addWindow(psp);
            psp.setVisible(true);
        }
        
    }//GEN-LAST:event_btnAgregarMaterialActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        //System.out.print("INDICE: "+jTabbedPane1.getSelectedIndex());
        if(jTabbedPane1.getSelectedIndex() == 1){
            cargarTabMateriales();
            this.mostrarMaterialesAUtilizar();
        }
        if(jTabbedPane1.getSelectedIndex() == 3){
            cargarHerramientas();
            this.mostrarHerramientasAUtilizar();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tbMaterialesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaterialesMouseReleased
        if(tbMateriales.getSelectedRow()!=-1 && tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0) instanceof Tupla)
        {
            Tupla t = (Tupla)tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0);
            mostrarEspecificacionMaterial(t.getId());
        }
    }//GEN-LAST:event_tbMaterialesMouseReleased

    private void btnAgregarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHerramientaActionPerformed
        if(tbHerramientasDisponibles.getSelectedRow()>=0){
            NTupla hd = (NTupla)(tbHerramientasDisponibles.getModel()).getValueAt(tbHerramientasDisponibles.getSelectedRow(), 0);
            gestorRAH.agregarHerramienta(hd.getId());
            this.mostrarHerramientasAUtilizar();
        }
    }//GEN-LAST:event_btnAgregarHerramientaActionPerformed

    private void btnQuitarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarMaterialActionPerformed
        if(tbMaterialesAUsar.getSelectedRow()>=0){
            NTupla nt = (NTupla)tbMaterialesAUsar.getModel().getValueAt(tbMaterialesAUsar.getSelectedRow(), 0);
            if(gestorRAM.quitarMaterial(nt.getId())){
                DefaultTableModel dtm = (DefaultTableModel)tbMaterialesAUsar.getModel();
                dtm.removeRow(tbMaterialesAUsar.getSelectedRow());
                this.mostrarMaterialesAUtilizar();
                this.cargarTabMateriales();
            }
        }
    }//GEN-LAST:event_btnQuitarMaterialActionPerformed

    private void btnQuitarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarHerramientaActionPerformed
        if(tbHerramientasSeleccionadas.getSelectedRow()>=0){
            NTupla nt = (NTupla)tbHerramientasSeleccionadas.getModel().getValueAt(tbHerramientasSeleccionadas.getSelectedRow(), 0);
            if(gestorRAH.quitarHerramienta(nt.getId())){
                DefaultTableModel dtm = (DefaultTableModel)tbHerramientasSeleccionadas.getModel();
                dtm.removeRow(tbHerramientasSeleccionadas.getSelectedRow());
                this.mostrarHerramientasAUtilizar();
            }
        }
    }//GEN-LAST:event_btnQuitarHerramientaActionPerformed

    private void txtBuscarMaterialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMaterialKeyReleased
        this.FiltrarTabla(this.tbMateriales, txtBuscarMaterial);
    }//GEN-LAST:event_txtBuscarMaterialKeyReleased

    private void txtBuscarMaterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarMaterialFocusGained
        txtBuscarMaterial.setText("");
    }//GEN-LAST:event_txtBuscarMaterialFocusGained

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void txtBuscarHerramientasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarHerramientasKeyReleased
        this.FiltrarTabla(this.tbHerramientasDisponibles, this.txtBuscarHerramientas);
    }//GEN-LAST:event_txtBuscarHerramientasKeyReleased

    private void txtBuscarHerramientasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarHerramientasFocusGained
        this.txtBuscarHerramientas.setText("");
    }//GEN-LAST:event_txtBuscarHerramientasFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarHerramienta;
    private javax.swing.JButton btnAgregarMaterial;
    private javax.swing.JButton btnQuitarHerramienta;
    private javax.swing.JButton btnQuitarMaterial;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tbHerramientasDisponibles;
    private javax.swing.JTable tbHerramientasSeleccionadas;
    private javax.swing.JTable tbMaterialEspecifico;
    private javax.swing.JTable tbMateriales;
    private javax.swing.JTable tbMaterialesAUsar;
    private javax.swing.JTextField txtBuscarHerramientas;
    private javax.swing.JTextField txtBuscarMaterial;
    // End of variables declaration//GEN-END:variables


        public void MostrarMensaje(String cod)
    {
        if(cod.equals("AM-0001"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Materiales","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0002"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de EspecificaciÃ³n del material seleccionado","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0003"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Proveedores del Recurso Especifico","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0004"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo obtener el nombre del Recurso Especifico","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0005"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo calcular el subtotal","Error de CÃ¡lculo",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0006"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo agregar el material a la lista de materiales a utilizar","Error en el AÃ±adir",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0007"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar los materiales a utilizar","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0008"))
        {
            // HAY Q BORRARLA CUANDO DEFINAMOS DE DONDE SACO EL ID DE TAREA
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo crear la tarea de prueba","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AH-0001"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Herramientas","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AH-0002"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo agregar la Herramienta a la lista de Herramientas a utilizar","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizar(int id, String flag, boolean exito) {
        if(jTabbedPane1.getSelectedIndex()==1){
        this.mostrarMaterialesAUtilizar();
        this.cargarTabMateriales();
        }
    }

    public class TablaColoreada extends JTable{
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int colIndex) {
            Component component = super.prepareRenderer(renderer, rowIndex, colIndex);
            if((rowIndex%2) == 0){
                ; component.setBackground(Color.GREEN);
            } else {
                ; component.setBackground(Color.YELLOW);
            }
            return component;
        }
    }
}
