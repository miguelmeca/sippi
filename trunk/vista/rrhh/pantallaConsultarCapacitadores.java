/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaConsultar.java
 *
 * Created on 06-ago-2010, 15:44:11
 */

package vista.rrhh;
import vista.interfaces.ICallBack;
import controlador.rrhh.GestorConsultarCapacitadores;
import java.awt.Color;
import util.NTupla;
import util.Tupla;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import util.SwingPanel;
import vista.interfaces.IAyuda;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Administrador
 */
public class pantallaConsultarCapacitadores extends javax.swing.JInternalFrame implements ICallBack,   IAyuda {

    private GestorConsultarCapacitadores gestor;
    private List<NTupla> listaCapacitadores;
    private DefaultTableModel model;
    private boolean filtroBuscarActivado;

    /** Creates new form pantallaConsultar */
    public pantallaConsultarCapacitadores()
    {
        initComponents();
        gestor = new GestorConsultarCapacitadores(this);
        this.habilitarVentana();

        //listaNroTel= new ArrayList<String>();
        //listaTipoTel= new ArrayList<Tupla>();

    }

    private void habilitarVentana()
    {
        filtroBuscarActivado=false;
        rbFiltroTodos.setSelected(true);
        rbFiltroActivos.setSelected(false);
        cargarCapacitadores();
        activarFiltrosTabla();
        if(tablaCapacitadores.getSelectedRow()!=-1)
       {
           int id;
            id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
           btnModificarEmpleado.setEnabled(true);
           btnConsultarEmpleado.setEnabled(true);
           if(!gestor.esBaja(id))
           {btnBajaEmpleado.setEnabled(true);}
           else
           {btnBajaEmpleado.setEnabled(false);}

           if(gestor.esBaja(id))
           {btnAltaEmpleado.setEnabled(true);}
           else
           {btnAltaEmpleado.setEnabled(false);}

        }
        else
        {
            btnModificarEmpleado.setEnabled(false);
           btnConsultarEmpleado.setEnabled(false);

           btnBajaEmpleado.setEnabled(false);

            btnAltaEmpleado.setEnabled(false);
        }

    }

    private void cargarCapacitadores()
    {
        listaCapacitadores=gestor.listaCapacitadores();
        /*for (int i= 0; i < listaEmpleados.size(); i++)
        {

           tablaEmpleados.getModel().. listaEmpleados.get(0)
        }
        */
        model = (DefaultTableModel) tablaCapacitadores.getModel();
        int fil=model.getRowCount();
        for (int i = 0; i < fil; i++) {
            model.removeRow(0);
        }
        /*for (NTupla nTuplaEmpleado : listaCapacitadores)
        {
            model.addRow( (Object[]) nTuplaEmpleado.getData() );
        
        }
        modeloTablaCompleto=model;
        tablaCapacitadores.setModel(model);*/
        for (NTupla nTuplaEmpleado : listaCapacitadores)
        {
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            Object[] obj=new Object[((Object[])nTuplaEmpleado.getData()).length+1];
            //obj[0]=nTuplaEmpleado.getId();
            Tupla tup=new Tupla();
            tup.setId(nTuplaEmpleado.getId());
            tup.setNombre(nTuplaEmpleado.getNombre());
            obj[0]=tup;
            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            System.arraycopy((Object[]) nTuplaEmpleado.getData(), 0, obj, 1, ((Object[]) nTuplaEmpleado.getData()).length);
            model.addRow( obj );
        }
        //model=model;
        tablaCapacitadores.setModel(model);
        /////////////////////////////////////////////////////       
        

    }

    public void activarFiltrosTabla()
    {
         TableRowSorter<TableModel> modeloOrdenado;
           // model.setRowFilter(RowFilter.regexFilter("2", 1));
            modeloOrdenado = new TableRowSorter<TableModel>(model);
            tablaCapacitadores.setRowSorter(modeloOrdenado);


        if(filtroBuscarActivado)
        {
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            if(rbFiltroActivos.isSelected())
           {
              filters.add(RowFilter.regexFilter(gestor.nombreEstadoCapacitadorActivo(), 3));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
           modeloOrdenado.setRowFilter(cadenaFilter);

        }
            else
        {
             if(rbFiltroActivos.isSelected())
           {
              modeloOrdenado.setRowFilter(RowFilter.regexFilter(gestor.nombreEstadoCapacitadorActivo(), 3));
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

        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCapacitadores = new javax.swing.JTable();
        btnBajaEmpleado = new javax.swing.JButton();
        btnModificarEmpleado = new javax.swing.JButton();
        btnNuevoEmpleado = new javax.swing.JButton();
        btnConsultarEmpleado = new javax.swing.JButton();
        rbFiltroTodos = new javax.swing.JRadioButton();
        rbFiltroActivos = new javax.swing.JRadioButton();
        btnAltaEmpleado = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar Capacitadores");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        tablaCapacitadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUIL", "Nombre", "Apellido", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCapacitadores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaCapacitadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCapacitadoresMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaCapacitadoresMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCapacitadores);

        btnBajaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete_page.png"))); // NOI18N
        btnBajaEmpleado.setText("Dar de Baja");
        btnBajaEmpleado.setEnabled(false);
        btnBajaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaEmpleadoActionPerformed(evt);
            }
        });

        btnModificarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnModificarEmpleado.setText("Modificar");
        btnModificarEmpleado.setEnabled(false);
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });

        btnNuevoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add_page.png"))); // NOI18N
        btnNuevoEmpleado.setText("Nuevo");
        btnNuevoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEmpleadoActionPerformed(evt);
            }
        });

        btnConsultarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        btnConsultarEmpleado.setText("Ver Detalles");
        btnConsultarEmpleado.setEnabled(false);
        btnConsultarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEmpleadoActionPerformed(evt);
            }
        });

        rbFiltroTodos.setText("Mostrar Todos");
        rbFiltroTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroTodosActionPerformed(evt);
            }
        });

        rbFiltroActivos.setText("Mostrar sólo capacitadores en estado \"Activo\"");
        rbFiltroActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroActivosActionPerformed(evt);
            }
        });

        btnAltaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept_page.png"))); // NOI18N
        btnAltaEmpleado.setText("Dar de Alta");
        btnAltaEmpleado.setEnabled(false);
        btnAltaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rbFiltroTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbFiltroActivos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConsultarEmpleado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajaEmpleado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAltaEmpleado)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbFiltroTodos)
                        .addComponent(rbFiltroActivos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultarEmpleado)
                    .addComponent(btnBajaEmpleado)
                    .addComponent(btnModificarEmpleado)
                    .addComponent(btnNuevoEmpleado)
                    .addComponent(btnAltaEmpleado))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked

        if(txtBuscar.getText().equals("Buscar..."))
        {
            txtBuscar.setText("");
        }

    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
       
        
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        if(tablaCapacitadores.getSelectedRow()!=-1)
        {
        int id;
        //String scuil;
        //id=(Integer)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0) );
        id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
        //cuil=Integer.parseInt(scuil);
        pantallaRegistrarCapacitador pre = new pantallaRegistrarCapacitador(id, this);
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
        }
    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void btnNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEmpleadoActionPerformed
        pantallaRegistrarCapacitador pre = new pantallaRegistrarCapacitador(this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
    }//GEN-LAST:event_btnNuevoEmpleadoActionPerformed

    private void btnConsultarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEmpleadoActionPerformed
        if(tablaCapacitadores.getSelectedRow()!=-1)
        {
        int id;
        //String sleg;
        //id=(Integer)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0) );
        id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
        pantallaConsultarDatosCapacitador pre = new pantallaConsultarDatosCapacitador(id, this);
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
        }
    }//GEN-LAST:event_btnConsultarEmpleadoActionPerformed

    private void tablaCapacitadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCapacitadoresMouseClicked
       
    }//GEN-LAST:event_tablaCapacitadoresMouseClicked

    private void btnAltaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaEmpleadoActionPerformed
        if(tablaCapacitadores.getSelectedRow()!=-1) {
            int id;
            //String sleg;
            //sleg=(String)(tablaEmpleados.getModel().getValueAt(tablaEmpleados.getSelectedRow(), 0) );
            id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
            //id=Integer.parseInt(sleg);
            pantallaRegistrarAltaCapacitador pre = new pantallaRegistrarAltaCapacitador(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
        }
}//GEN-LAST:event_btnAltaEmpleadoActionPerformed

    private void btnBajaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaEmpleadoActionPerformed
        if(tablaCapacitadores.getSelectedRow()!=-1)
        {
        int id;
        //String sleg;
        //sleg=(String)(tablaEmpleados.getModel().getValueAt(tablaEmpleados.getSelectedRow(), 0) );
        id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
        //id=Integer.parseInt(sleg);
        pantallaRegistrarBajaCapacitador pre = new pantallaRegistrarBajaCapacitador(id, this);
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();
        }
    }//GEN-LAST:event_btnBajaEmpleadoActionPerformed

    private void rbFiltroTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroTodosActionPerformed
        rbFiltroTodos.setSelected(true);
        rbFiltroActivos.setSelected(false);
        activarFiltrosTabla();

    }//GEN-LAST:event_rbFiltroTodosActionPerformed

    private void rbFiltroActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroActivosActionPerformed
       rbFiltroTodos.setSelected(false);
        rbFiltroActivos.setSelected(true);
        activarFiltrosTabla();
    }//GEN-LAST:event_rbFiltroActivosActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained

        if(txtBuscar.getText().equals("Buscar..."))
        {
        txtBuscar.setText("");
        txtBuscar.setForeground(Color.BLACK);
        filtroBuscarActivado=true;
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if(txtBuscar.getText().equals(""))
        {
        txtBuscar.setText("Buscar...");
        txtBuscar.setForeground(Color.GRAY);
        filtroBuscarActivado=false;
        }
        else
        {filtroBuscarActivado=true;}

    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
       activarFiltrosTabla();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaCapacitadoresMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCapacitadoresMouseReleased
       if(tablaCapacitadores.getSelectedRow()!=-1)
       {
           int id;
            id=((Tupla)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0))).getId();
           btnModificarEmpleado.setEnabled(true);
           btnConsultarEmpleado.setEnabled(true);
           if(!gestor.esBaja(id))
           {btnBajaEmpleado.setEnabled(true);}
           else
           {btnBajaEmpleado.setEnabled(false);}

           if(gestor.esBaja(id))
           {btnAltaEmpleado.setEnabled(true);}
           else
           {btnAltaEmpleado.setEnabled(false);}
           if (evt.getClickCount() == 2)
            {

            //String sleg;
            //sleg=(String)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0) );
            //id=(Integer)(tablaCapacitadores.getModel().getValueAt(tablaCapacitadores.getSelectedRow(), 0) );

            //leg=Integer.parseInt(sleg);
            pantallaConsultarDatosCapacitador pre = new pantallaConsultarDatosCapacitador(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
                //pre.opcionRegistrarEmpleado();
            }
        }
       else
       {
           btnModificarEmpleado.setEnabled(false);
           btnConsultarEmpleado.setEnabled(false);
           btnBajaEmpleado.setEnabled(false);
           btnAltaEmpleado.setEnabled(false);
       }
    }//GEN-LAST:event_tablaCapacitadoresMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltaEmpleado;
    private javax.swing.JButton btnBajaEmpleado;
    private javax.swing.JButton btnConsultarEmpleado;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JButton btnNuevoEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbFiltroActivos;
    private javax.swing.JRadioButton rbFiltroTodos;
    private javax.swing.JTable tablaCapacitadores;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
public void actualizar(int flag,boolean exito) {
        
        habilitarVentana();
    }
public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Registre un nuevo capacitador o seleccione uno existente y consulte o modifique sus datos";
    }

    public String getTituloAyuda()
    {
        return "Opción: Consultar Capacitadores";

    }
}
