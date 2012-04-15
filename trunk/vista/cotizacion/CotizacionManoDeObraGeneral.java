/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaManoDeObra.java
 *
 * Created on 06/05/2011, 21:56:48
 */

package vista.cotizacion;

import java.util.Calendar;
import java.util.Date;
import util.NTupla;
import util.Tupla;
import vista.interfaces.ICallBack_v2;
import controlador.cotizacion.GestorCotizacionManoDeObra;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import java.text.DecimalFormat;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import javax.swing.table.DefaultTableModel;
import modelo.SubObraXTarea;
import util.FechaUtil;
import util.SwingPanel;


/**
 *
 * @author Fran
 */
public class CotizacionManoDeObraGeneral extends javax.swing.JPanel implements ICallBack_v2 {
    
    private GestorCotizacionManoDeObra gestor;
    private  IntervalCategoryDataset dataset;

    public CotizacionManoDeObraGeneral(GestorCotizacionManoDeObra gestor)
    {
        initComponents();
        dataset=null;
        this.gestor=gestor;
        this.gestor.setPantalla(this);
        limpiarTabla();
        gestor.getTareasDeSubObra();
       // initGrafico();
        mostrarTotal();
        
    }
    /*private void initGrafico()
    {
        panelGantt.removeAll();
        dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(471,211);
        if(panelGantt.getWidth()!=0)
        {chartPanel.setSize(panelGantt.getWidth(),panelGantt.getHeight());}        
        panelGantt.add( chartPanel );        
        //panelGantt.getParent().validate();
    }*/
    private void limpiarTabla()
    {
        int fil=((DefaultTableModel) tblTareas.getModel()).getRowCount();
        for (int i = 0; i < fil; i++) {
            ((DefaultTableModel) tblTareas.getModel()).removeRow(0);
        }
    }
    
    public void agregarTareaTabla(SubObraXTarea tareaActual, boolean nueva, boolean modificada)//una tarea puede no ser ni nueva ni modificada (cuando se cargan los datos en la tabla de las tareas existentes en el sistema desde el gestor)
    {      
       if(nueva || modificada )//Si es una tarea nueva o una modificada y no una existente.
       {
           boolean exito;
           exito=gestor.agregarTarea(tareaActual);
           if( (exito==false))
           { 
                   JOptionPane.showMessageDialog(this.getParent(), "Ocurrió un error guardando tarea", "Eror",JOptionPane.ERROR_MESSAGE);
                   return;
           }
       }  
       
           
       NTupla tar=new NTupla(tareaActual.hashCode());
       tar.setNombre(tareaActual.getNombre());
       tar.setData(tareaActual);      
             
       DefaultTableModel modelo = (DefaultTableModel) tblTareas.getModel();
       int indiceFila=-1;
       if(!modificada)
       {
           indiceFila=modelo.getRowCount();
           Object[] x=new Object[modelo.getColumnCount()];
           modelo.addRow(x);
       }
       else
       {          
           for (int j = 0; j < modelo.getRowCount() ; j++) 
           {
               if(((NTupla)modelo.getValueAt(j,0)).getData().hashCode()==tareaActual.hashCode())
               {
                   indiceFila=j;
               }               
           }
       }
       
       modelo.setValueAt(tar, indiceFila, 0); 
       modelo.setValueAt(tareaActual.getTipoTarea().getNombre(), indiceFila, 1); 
       modelo.setValueAt(tareaActual.obtenerTotalDePersonas(), indiceFila, 2);
       modelo.setValueAt(tareaActual.obtenerTotalDeHorasNormales(), indiceFila, 3); 
       modelo.setValueAt(tareaActual.obtenerTotalDeHorasAl50(), indiceFila, 4); 
       modelo.setValueAt(tareaActual.obtenerTotalDeHorasAl100(), indiceFila, 5); 
       modelo.setValueAt(tareaActual.calcularSubtotal(), indiceFila, 6); 
       
       if(nueva|| modificada)
       {
        mostrarTotal();
       }
    }
    private void mostrarTotal()
    {
        DecimalFormat df =  new DecimalFormat("0.00");
        txtSubtotal.setText(df.format(gestor.calcularSubtotal()).replace(".",","));        
        //txtSubtotal.setText(Double.toString(gestor.calcularSubtotal()));
    }

     /**
     * Creates a sample dataset for a Gantt chart.
     *
     * @return The dataset.
     */
   /* public IntervalCategoryDataset createDataset() {

        TaskSeries s1 = new TaskSeries("Tareas");
        for (int i = 0; i < ((DefaultTableModel)tblTareas.getModel()).getRowCount(); i++) 
        {
               s1.add(new Task(  (i+1)+"- "+((NTupla)(tblTareas.getModel().getValueAt(i, 0))).getNombre()  ,
               new SimpleTimePeriod(((Date)((NTupla)(tblTareas.getModel().getValueAt(i, 4))).getData()),
                                    FechaUtil.fechaMas(((Date)((NTupla)(tblTareas.getModel().getValueAt(i, 5))).getData()),1) )));
              
        } 

        

        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);

        return collection;
    }*/

    /**
     * Utility method for creating <code>Date</code> objects.
     *
     * @param day  the date.
     * @param month  the month.
     * @param year  the year.
     *
     * @return a date.
     */
   /* private static Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;

    }*/
        
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
   /* private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "",  // chart title
            "",              // domain axis label
            "",              // range axis label
            dataset,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );    
//        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
        return chart;    
    }
    */

      public void actualizar(int id, String flag, boolean exito)
      {}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTareas = new javax.swing.JTable();
        txtSubtotal = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();

        jLabel9.setText("Plano");

        jLabel10.setText("Posición");

        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitar.setText("Quitar");
        btnQuitar.setEnabled(false);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jLabel11.setText("Subtotal Recursos Humanos   $");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tareas"));
        jPanel4.setPreferredSize(new java.awt.Dimension(503, 380));

        tblTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomre de Tarea", "Tipo de Tarea", "Personas", "Horas Normales", "Horas al 50%", "Horas al 100%", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTareas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTareasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTareas);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Notes.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAgregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnModificar)
                        .addComponent(btnAgregar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
      CotizacionManoDeObraAgregarMO at = new CotizacionManoDeObraAgregarMO(this, gestor);
        SwingPanel.getInstance().addWindow(at);
       at.setVisible(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtSubtotalActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
       if(tblTareas.getSelectedRow()!=-1)
        {
            if(JOptionPane.showConfirmDialog(this.getParent(), "¿Está seguro de eliminar la tarea "+((NTupla)((DefaultTableModel)tblTareas.getModel()).getValueAt(tblTareas.getSelectedRow(), 0)).getNombre()+"?", "Eliminar tarea", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
              int idT=((NTupla)((DefaultTableModel)tblTareas.getModel()).getValueAt(tblTareas.getSelectedRow(), 0)).getId();
            gestor.eliminarTarea(idT);
            ((DefaultTableModel)tblTareas.getModel()).removeRow(tblTareas.getSelectedRow());
            //initGrafico();
            mostrarTotal();
            }
            
        //id=((Tupla)(tblTareas.getModel().getValueAt(tblTareas.getSelectedRow(), 0))).getId(); 
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
     modificarTarea();
    
}//GEN-LAST:event_btnModificarActionPerformed
private void modificarTarea()
{
    int selectedRow=tblTareas.getSelectedRow();
    if(selectedRow!=-1)
        {
           CotizacionManoDeObraAgregarMO at = new CotizacionManoDeObraAgregarMO(this, gestor);
           SwingPanel.getInstance().addWindow(at);       
           at.setVisible(true);
           //////////////////////////////
           /*Object[] datos=new Object[6];       
           for (int i = 0; i < datos.length; i++) 
           {
             datos[i]=(tblTareas.getModel().getValueAt(selectedRow, i));   
           }*/
           SubObraXTarea tarea=(SubObraXTarea)((NTupla)tblTareas.getModel().getValueAt(selectedRow, 0)).getData();
           at.tomarValoresDeDatos(tarea, tblTareas.getSelectedRow());
        }  
}        
        
private void tblTareasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTareasMouseClicked

    int selectedRow=tblTareas.getSelectedRow();
   
           if(evt.getClickCount()==1)
           {
               if(selectedRow!=-1)
             {
                btnModificar.setEnabled(true);
                btnQuitar.setEnabled(true);
             }
           }
           if(evt.getClickCount()==2)
           {
            modificarTarea();
           }
           
        
}//GEN-LAST:event_tblTareasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTable tblTareas;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables

}
