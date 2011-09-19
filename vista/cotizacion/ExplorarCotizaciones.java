/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * explorarCotizaciones.java
 *
 * Created on 23-jun-2011, 8:59:42
 */

package vista.cotizacion;

import vista.gui.IFavorito;
import vista.interfaces.ICallBack_v2;
import java.awt.Color;
import controlador.cotizacion.GestorExplorarCotizaciones;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import util.NTupla;
import util.Tupla;
import util.SwingPanel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;




/**
 *
 * @author Administrador
 */
public class ExplorarCotizaciones extends javax.swing.JInternalFrame implements IFavorito, ICallBack_v2{

    public static final int TIPO_EXPLORAR_ONLY = 0;
    public static final int TIPO_EXPLORAR_FULL = 1;    
    private int TIPO;
    private boolean desdeRegistrarNuevaCotizacion;
    private GestorExplorarCotizaciones gestor;
    private List<NTupla> listaCotizaciones;
    private DefaultTableModel model;
    private ICallBack_v2 pantallaPadre;
    private boolean filtroBuscarActivado;

    /** Creates new form explorarCotizaciones */
    public ExplorarCotizaciones(ICallBack_v2 padre)
    {
        initComponents();
        pantallaPadre=padre;
        gestor = new GestorExplorarCotizaciones(this);
        this.habilitarVentana();
        desdeRegistrarNuevaCotizacion=true;
        btnSeleccionar.setText("Seleccionar");
    }
    public ExplorarCotizaciones()
    {
        initComponents();
        gestor = new GestorExplorarCotizaciones(this);
        this.habilitarVentana();
        desdeRegistrarNuevaCotizacion=false;
        btnSeleccionar.setText("Editar");
    }
    public void setTipo(int TIPO) 
    {
        this.TIPO = TIPO;
    }
    
    private void habilitarVentana()
    {
        filtroBuscarActivado=false;
        cargarCotizaciones();
         if(tablaCotizaciones.getSelectedRow()!=-1)
        {
           btnSeleccionar.setEnabled(true);
        }
        else
        {
          btnSeleccionar.setEnabled(false);
        }

    }

     private void cargarCotizaciones()
    {
        listaCotizaciones=gestor.listaCotizaciones();
        /*for (int i= 0; i < listaEmpleados.size(); i++)
        {

           tablaEmpleados.getModel().. listaEmpleados.get(0)
        }
        */
        model = (DefaultTableModel) tablaCotizaciones.getModel();
        int fil=model.getRowCount();
        for (int i = 0; i < fil; i++) {
            model.removeRow(0);
        }
        /*for (NTupla nTuplaEmpleado : listaEmpleados)
        {
            model.addRow( (Object[]) nTuplaEmpleado.getData() );
        
        }
        modeloTablaCompleto=model;
        tablaEmpleados.setModel(model);*/
        for (NTupla nTuplaCotizacion : listaCotizaciones)
        {
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            Object[] obj=new Object[((Object[])nTuplaCotizacion.getData()).length+1];
            //obj[0]=nTuplaEmpleado.getId();
            Tupla tup=new Tupla();
            tup.setId(nTuplaCotizacion.getId());
            tup.setNombre(nTuplaCotizacion.getNombre());
            obj[0]=tup;

            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            System.arraycopy((Object[]) nTuplaCotizacion.getData(), 0, obj, 1, ((Object[]) nTuplaCotizacion.getData()).length);
            model.addRow( obj );
        }
        //modeloTablaCompleto=model;
        tablaCotizaciones.setModel(model);
        
         //TODO: Esconder primera fila  // tablaEmpleados.getColumnModel().removeColumn(tablaEmpleados.getColumnModel().getColumn(0));
         

    }
    
     public void llamarEditarCotizacion()
     {
         int id;
         //if(tablaCotizaciones.getSelectedRow()!=-1)
         // {
            id=((Tupla)(tablaCotizaciones.getModel().getValueAt(tablaCotizaciones.getSelectedRow(), 0))).getId();
            ExplorarSubObras pre = new ExplorarSubObras(id);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
         // }
            
     }
     public void activarFiltrosTabla()
    {
         TableRowSorter<TableModel> modeloOrdenado;
           // model.setRowFilter(RowFilter.regexFilter("2", 1));
            modeloOrdenado = new TableRowSorter<TableModel>(model);
            tablaCotizaciones.setRowSorter(modeloOrdenado);
        

        
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);


    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSeleccionar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCotizaciones = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Explorar Cotizaciones");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
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
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        tablaCotizaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pedido de Obra", "Cotización", "Revisión", "Fecha Creación", "Fecha Última Modoficación", "Monto Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCotizaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaCotizacionesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCotizaciones);

        jButton1.setText("PruebaReporteSubObras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(42, 42, 42)
                        .addComponent(btnSeleccionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSeleccionar)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
   
       if(tablaCotizaciones.getSelectedRow()!=-1)
       {
           if(desdeRegistrarNuevaCotizacion)
            {   
            int id;
            
                id=((Tupla)(tablaCotizaciones.getModel().getValueAt(tablaCotizaciones.getSelectedRow(), 0))).getId();
                pantallaPadre.actualizar(id, "ExplorarCotizaciones", true);
                this.dispose();
            }
           else
            {
                llamarEditarCotizacion();
            }
        }
        
        
}//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
       if(txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
            filtroBuscarActivado=false;
        } else {
            filtroBuscarActivado=true;}
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
         if(txtBuscar.getText().equals("")) 
         {
            txtBuscar.setText("Buscar...");
            txtBuscar.setForeground(Color.GRAY); 
            filtroBuscarActivado=false;
        } 
    }//GEN-LAST:event_txtBuscarFocusLost

    private void tablaCotizacionesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCotizacionesMouseReleased
        if(tablaCotizaciones.getSelectedRow()!=-1)
       {
           //id=((Tupla)(tablaCotizaciones.getModel().getValueAt(tablaCotizaciones.getSelectedRow(), 0))).getId();
           btnSeleccionar.setEnabled(true);
           if (evt.getClickCount() == 2)
            {
             llamarEditarCotizacion();
            }
        }
       else
       {
           btnSeleccionar.setEnabled(false);
       }
    }//GEN-LAST:event_tablaCotizacionesMouseReleased

private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
    activarFiltrosTabla();
}//GEN-LAST:event_txtBuscarKeyReleased

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
    if(tablaCotizaciones.getSelectedRow()!=-1)
       {
        int id = (Integer) tablaCotizaciones.getModel().getValueAt(tablaCotizaciones.getSelectedRow(), 0);
        //if(id>0) 
        //{
           gestor.reporteCotizacionExternaPorSubObra(id); 
        //}
       }
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCotizaciones;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isFavorito() {
       return true;
    }

    @Override
    public String getIconoFavorito() {
        return "/res/iconos/var/16x16/search.png";
    }
    
    @Override
    public void actualizar(int id, String flag, boolean exito)
    {
        habilitarVentana();
    }

}
