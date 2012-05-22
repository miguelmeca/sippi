/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.*;
import util.RecursosUtil;
import vista.interfaces.ICallBack_v3;

/**
 * @author Administrador
 */
public class AsignacionMaterialesCantidad extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "CALLBACK_FLAG_ASIGNACION_HERRAMIENTAS";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tarea;
    private RecursoXProveedor recurso;
    private RecursoEspecifico re;
    private Material material;
    private SubObraXMaterialModif gastos;
    private PlanificacionXXX plan;
    
    public AsignacionMaterialesCantidad(ICallBack_v3 callback,PlanificacionXXX plan, TareaPlanificacion tarea,SubObraXMaterialModif gastos, RecursoXProveedor recurso) {
        initComponents();
        this.tarea = tarea;
        this.recurso = recurso;
        this.re = RecursosUtil.getRecursoEspecifico(recurso);
        this.material = RecursosUtil.getMaterial(re);
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
        lblHerramientaNombre = new javax.swing.JLabel();
        lblTareaNombre = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblCantidadDisponible = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblCantidadTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCantidadCotizada = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblCantidadYaAsignada = new javax.swing.JLabel();
        btnAsignar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Asignar Material");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Está asignando");

        txtCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        lblHerramientaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHerramientaNombre.setText("...");

        lblTareaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTareaNombre.setText("...");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        jLabel5.setText("Para este material quedan");

        lblCantidadDisponible.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCantidadDisponible.setForeground(new java.awt.Color(0, 153, 0));

        jLabel7.setText("de un Total disponible de :");

        lblCantidadTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel9.setText("Cotizados Originalmente:");

        lblCantidadCotizada.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel11.setText("Anteriormente se asignaron");

        lblCantidadYaAsignada.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(23, 23, 23)
                        .addComponent(lblCantidadYaAsignada, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lblCantidadTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCantidadDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCantidadCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblCantidadYaAsignada, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(lblCantidadDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCantidadTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCantidadCotizada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel9)))
                .addContainerGap())
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblHerramientaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUnidadDeMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsignar)))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addComponent(lblTareaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUnidadDeMedida, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHerramientaNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTareaNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        int cantidadYaAsignada = PlanificacionUtils.getCantidadAsignadaAMaterial(this.plan,this.gastos);
        int cantidadTotal = this.gastos.getCantidadDisponible();
        int cantidadCotizadas = this.gastos.getOriginal().getCantidad();
        int cantidadQueQueda = cantidadTotal - cantidadYaAsignada;
        
        asignarCantidad(value,cantidadQueQueda,cantidadCotizadas,cantidadTotal);
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCantidadCotizada;
    private javax.swing.JLabel lblCantidadDisponible;
    private javax.swing.JLabel lblCantidadTotal;
    private javax.swing.JLabel lblCantidadYaAsignada;
    private javax.swing.JLabel lblHerramientaNombre;
    private javax.swing.JLabel lblTareaNombre;
    private javax.swing.JLabel lblUnidadDeMedida;
    private javax.swing.JSpinner txtCantidad;
    // End of variables declaration//GEN-END:variables

    private void init() {
        String unidadMedida = material.getUnidadDeMedida().getAbreviatura();
        lblUnidadDeMedida.setText(unidadMedida + " a la");
        lblHerramientaNombre.setText("<HTML>Material: <b>"+material.getNombre()+"</b> ("+re.getNombre()+")");
        lblTareaNombre.setText("<HTML>a la tarea: <b>"+this.tarea.getNombre()+"</b>");
        
        int cantidadYaAsignada = PlanificacionUtils.getCantidadAsignadaAMaterial(this.plan,this.gastos);
        lblCantidadYaAsignada.setText(cantidadYaAsignada+" "+unidadMedida);
        
        int cantidadTotal = this.gastos.getCantidadDisponible();
        lblCantidadTotal.setText(cantidadTotal+" "+unidadMedida);
        
        int cantidadDisponible = cantidadTotal - cantidadYaAsignada;
        lblCantidadDisponible.setText(cantidadDisponible+" "+unidadMedida);
        
        int cotaMenor = (int) cantidadTotal * 1/10; 
        
        if(cantidadDisponible==0)
        {
            lblCantidadDisponible.setForeground(Color.RED);
        }
        else if(cantidadDisponible>= 1 && cantidadDisponible <=cotaMenor)
        {
            lblCantidadDisponible.setForeground(Color.ORANGE);
        }
        else
        {
            lblCantidadDisponible.setForeground(Color.GREEN);
        }   
        
        if(this.gastos.getOriginal()!=null)
        {
            int cantidadCotizada = this.gastos.getOriginal().getCantidad();
            lblCantidadCotizada.setText(cantidadCotizada+" "+unidadMedida);
        }
    }

    private void asignarCantidad(int value , int cantidadQueQueda, int cantidadCotizada, int cantidadTotalDisponible) {
  
        int sePasoDeCantidad = cantidadQueQueda - value;
        
        if(sePasoDeCantidad<0)
        {
            // Hoas que me pase de la cotizacion original
            int cuantoSePasoDeCotizacionOriginal = cantidadTotalDisponible - cantidadCotizada + Math.abs(sePasoDeCantidad);
            // Me pase de horas
            StringBuilder msg = new StringBuilder("<HTML>");
            msg.append("Está asignando ");
            msg.append("<b><span color=\"#FF0000\">");
            msg.append(Math.abs(sePasoDeCantidad));
            String unidadMedida = material.getUnidadDeMedida().getAbreviatura();
            msg.append(" ").append(unidadMedida);
            msg.append("</span></b>");
            msg.append(" más de los que tienes disponibles");
            msg.append("<br>");
            msg.append("La cotización original (enviada al cliente) se excedera por ");
            msg.append("<span color=\"#FF0000\"><b>");
            msg.append(cuantoSePasoDeCotizacionOriginal);
            msg.append(" ").append(unidadMedida);
            msg.append("</b></span>");
            msg.append("<br>");
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
     * [0] SubObraXHerramientaMod
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
        this.callback.actualizar(value,AsignacionMaterialesCantidad.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
