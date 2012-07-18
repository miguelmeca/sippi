/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import util.NTupla;
import util.RecursosUtil;
import vista.interfaces.ICallBack_v3;

/**
 * @author Administrador
 */
public class AsignacionAlquileresCompraCantidad extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "CALLBACK_FLAG_ASIGNACION_ALQUILER_COMPRA";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tarea;
    private TipoAlquilerCompra tipoAlquilerCompra;
    private SubObraXAlquilerCompraModif gastos;
    private PlanificacionXXX plan;
    
    public AsignacionAlquileresCompraCantidad(ICallBack_v3 callback,PlanificacionXXX plan, TareaPlanificacion tarea,SubObraXAlquilerCompraModif gastos, TipoAlquilerCompra tipoAlquilerCompra) {
        initComponents();
        this.tarea = tarea;
        this.tipoAlquilerCompra = tipoAlquilerCompra;
        this.gastos = gastos;
        this.plan = plan;
        this.callback = callback;
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JSpinner();
        lblUnidadDeMedida = new javax.swing.JLabel();
        lblAlquilerCompraNombre = new javax.swing.JLabel();
        lblTareaNombre = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblCantidadCotizada = new javax.swing.JLabel();
        lblCantidadDisponible = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnAsignar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTareas = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Tareas que lo comparten"));
        setClosable(true);
        setTitle("Asignar Alquiler/Compra");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Está asignando");

        txtCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        lblAlquilerCompraNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAlquilerCompraNombre.setText("...");

        lblTareaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTareaNombre.setText("...");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        jLabel9.setText("Cotizados Originalmente:");

        lblCantidadCotizada.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblCantidadDisponible.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel10.setText("Cantidad Planificada:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cada Alquiler/Compra puede ser utilizado por más de una tarea. Por tal motivo, la ");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("cantidad planificada contabiliza la cantidad de este recurso en el baúl de recursos.");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Esto implica que no se descuenta en base a las tareas en las que ya se lo asignó.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(lblCantidadDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCantidadCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblCantidadCotizada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCantidadDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tareas que lo comparten"));

        tablaTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tarea", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaTareas);
        tablaTareas.getColumnModel().getColumn(0).setResizable(false);
        tablaTareas.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaTareas.getColumnModel().getColumn(1).setResizable(false);
        tablaTareas.getColumnModel().getColumn(1).setPreferredWidth(50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblAlquilerCompraNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTareaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUnidadDeMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblUnidadDeMedida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAlquilerCompraNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTareaNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAsignar))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        
        SpinnerNumberModel model = (SpinnerNumberModel)txtCantidad.getModel();
        int value = model.getNumber().intValue();
        
        int cantidadDisponible = this.gastos.getCantidad();
        int cantidadCotizadas = this.gastos.getOriginal().getCantidad();
        
        asignarCantidad(value, cantidadDisponible);
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAlquilerCompraNombre;
    private javax.swing.JLabel lblCantidadCotizada;
    private javax.swing.JLabel lblCantidadDisponible;
    private javax.swing.JLabel lblTareaNombre;
    private javax.swing.JLabel lblUnidadDeMedida;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JSpinner txtCantidad;
    // End of variables declaration//GEN-END:variables

    private void init() {
        lblUnidadDeMedida.setText("unidades de");
        lblAlquilerCompraNombre.setText("<HTML><b>"+tipoAlquilerCompra.getNombre()+"</b>");
        lblTareaNombre.setText("<HTML>a la tarea: <b>"+this.tarea.getNombre()+"</b>");
               
        int cantidadDisponible = this.gastos.getCantidad();
        lblCantidadDisponible.setText(cantidadDisponible+" ");
        
        if(this.gastos.getOriginal()!=null)
        {
            int cantidadCotizada = this.gastos.getOriginal().getCantidad();
            lblCantidadCotizada.setText(cantidadCotizada+" ");
        }
        
        List<NTupla> tareas = PlanificacionUtils.getTareasQuePoseanAlquilerCompraAsignado(this.plan, this.gastos);
        DefaultTableModel dtm = (DefaultTableModel) tablaTareas.getModel();
        Iterator<NTupla> itTareas = tareas.iterator();
        while(itTareas.hasNext()){
            NTupla nt = itTareas.next();
            Object[] fila = new Object[2];
            fila[0] = nt;
            fila[1] = nt.getData();
            dtm.addRow(fila);
        }
    }

    private void asignarCantidad(int value , int cantidadDisponible) {
  
        int sePasoDeCantidad = cantidadDisponible - value;
        
        if(sePasoDeCantidad<0)
        {
            // Hoas que me pase de la cotizacion original
//            int cuantoSePasoDeCotizacionOriginal = cantidadTotalDisponible - cantidadCotizada + Math.abs(sePasoDeCantidad);
            // Me pase de horas
            StringBuilder msg = new StringBuilder("<HTML>");
            msg.append("Está asignando ");
            msg.append("<b><span color=\"#FF0000\">");
            msg.append(Math.abs(sePasoDeCantidad));
            msg.append("</span></b>");
            msg.append(" más de los que tienes disponibles");
            msg.append("<br>");
            msg.append("La cotización original (enviada al cliente) se verá excedida por este valor.");
            msg.append("¿Desea realizar la asignación a esta tarea de todas formas?");
            msg.append("<br>");
            msg.append("<br>");
            msg.append("<i><b>Nota:</b></i>");
            msg.append("<br>");
            msg.append("<i>");
            msg.append("El excedente se agregará automaticamente a los gastos de la obra");
            msg.append("</i>");

            Object[] options = {"Cancelar","Aumentar y asignar"};
            int n = JOptionPane.showInternalOptionDialog(this,msg.toString(),"Atencion!",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[1]);            
            System.out.println("SELECCIONO: "+n);
            
            if(n==1)
            {
                // Aumentar y Asignar
                actualizar(value);
            }
            else
            {
                // Cancelar ... No hago nada ...
            }            
        }
        else
        {
            // No me pase en las horas, Aumentar y asignar
            actualizar(value);
        }
    }
    
    /**
     * Manda el mensaje callback con los datos asociados
     * [0] SubObraXMaterialMod
     * [1] TareaPlanificacion
     * [2] cantida de horas
     * @param value 
     */
    private void actualizar(int value)
    {
        Object[] data = new Object[3];
        data[0] = this.gastos;
        data[1] = this.tarea;
        data[2] = value;
        this.callback.actualizar(value,AsignacionAlquileresCompraCantidad.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
