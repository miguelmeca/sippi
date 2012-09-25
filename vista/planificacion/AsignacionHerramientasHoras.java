/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.HerramientaDeEmpresa;
import modelo.PlanificacionXXX;
import modelo.SubObraXHerramientaModif;
import modelo.TareaPlanificacion;
import vista.interfaces.ICallBack_v3;

/**
 * @author Administrador
 */
public class AsignacionHerramientasHoras extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "CALLBACK_FLAG_ASIGNACION_HERRAMIENTAS";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tarea;
    private HerramientaDeEmpresa herramienta;
    private SubObraXHerramientaModif gastos;
    private PlanificacionXXX plan;
    
    public AsignacionHerramientasHoras(ICallBack_v3 callback,PlanificacionXXX plan, TareaPlanificacion tarea,SubObraXHerramientaModif gastos, HerramientaDeEmpresa herramienta) {
        initComponents();
        this.tarea = tarea;
        this.herramienta = herramienta;
        this.gastos = gastos;
        this.plan = plan;
        this.callback = callback;
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtHoras = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        lblHerramientaNombre = new javax.swing.JLabel();
        lblTareaNombre = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblHorasDisponibles = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblHorrasTotales = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblHorasCotizadas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblHorasYaAsignadas = new javax.swing.JLabel();
        btnAsignar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Asignar Herramientas");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Está asignando");

        txtHoras.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel2.setText("Horas de uso a la");

        lblHerramientaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHerramientaNombre.setText("...");

        lblTareaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTareaNombre.setText("...");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        jLabel5.setText("Para ésta herramienta quedan");

        lblHorasDisponibles.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasDisponibles.setForeground(new java.awt.Color(0, 153, 0));
        lblHorasDisponibles.setText("- Hs.");

        jLabel7.setText("de un Total disponible de :");

        lblHorrasTotales.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorrasTotales.setText("- Hs.");

        jLabel9.setText("Cotizadas Originalmente:");

        lblHorasCotizadas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasCotizadas.setText("- Hs.");

        jLabel11.setText("Anteriormente se asignaron");

        lblHorasYaAsignadas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasYaAsignadas.setText("- Hs.");

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
                        .addComponent(lblHorasYaAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lblHorrasTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHorasDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHorasCotizadas, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblHorasYaAsignadas)
                    .addComponent(jLabel5)
                    .addComponent(lblHorasDisponibles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblHorrasTotales)
                    .addComponent(jLabel9)
                    .addComponent(lblHorasCotizadas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsignar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblTareaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        
        SpinnerNumberModel model = (SpinnerNumberModel)txtHoras.getModel();
        int value = model.getNumber().intValue();
        
        int horasYaAsignadas = PlanificacionUtils.getHorasTotalesAsignadasAHerramienta(this.plan,this.gastos);
        int horasTotal = this.gastos.getHorasDisponibles();
        int horasCotizadas=0;
        if(this.gastos.getOriginal()!=null)
        {horasCotizadas = this.gastos.getOriginal().getHorasDisponibles();}
        int horasQueQuedan = horasTotal - horasYaAsignadas;
        
        asignarHoras(value,horasQueQuedan,horasCotizadas,horasTotal);
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHerramientaNombre;
    private javax.swing.JLabel lblHorasCotizadas;
    private javax.swing.JLabel lblHorasDisponibles;
    private javax.swing.JLabel lblHorasYaAsignadas;
    private javax.swing.JLabel lblHorrasTotales;
    private javax.swing.JLabel lblTareaNombre;
    private javax.swing.JSpinner txtHoras;
    // End of variables declaration//GEN-END:variables

    private void init() {
        lblHerramientaNombre.setText("<HTML>herramienta: <b>"+this.herramienta.getNombre()+"</b> ("+this.herramienta.getNroSerie()+")");
        lblTareaNombre.setText("<HTML>a la tarea: <b>"+this.tarea.getNombre()+"</b>");
        
        int horasYaAsignadas = PlanificacionUtils.getHorasTotalesAsignadasAHerramienta(this.plan,this.gastos);
        lblHorasYaAsignadas.setText(horasYaAsignadas+" Hs.");
        
        int horasTotal = this.gastos.getHorasDisponibles();
        lblHorrasTotales.setText(horasTotal+" Hs.");
        
        int horasDisponibles = horasTotal - horasYaAsignadas;
        lblHorasDisponibles.setText(horasDisponibles+" Hs.");
        
        if(horasDisponibles==0)
        {
            lblHorasDisponibles.setForeground(Color.RED);
        }
        else if(horasDisponibles>= 1 && horasDisponibles <=5)
        {
            lblHorasDisponibles.setForeground(Color.ORANGE);
        }
        else
        {
            lblHorasDisponibles.setForeground(Color.GREEN);
        }   
        
        if(this.gastos.getOriginal()!=null)
        {
            int horasCotizadas = this.gastos.getOriginal().getHorasDisponibles();
            lblHorasCotizadas.setText(horasCotizadas+" Hs.");
        }
        
    }

    private void asignarHoras(int value , int horasQueQuedan, int horasCotizadas, int horasTotalesDisponibles) {
  
        int sePasoDeHoras = horasQueQuedan - value;
        
        if(sePasoDeHoras<0)
        {
            // Hoas que me pase de la cotizacion original
            int cuantoSePasoDeCotizacionOriginal = horasTotalesDisponibles - horasCotizadas + Math.abs(sePasoDeHoras);
            // Me pase de horas
            StringBuilder msg = new StringBuilder("<HTML>");
            msg.append("Está asignando ");
            msg.append("<b><span color=\"#FF0000\">");
            msg.append(Math.abs(sePasoDeHoras));
                if(Math.abs(sePasoDeHoras)==1)
                {
                    msg.append(" Hora");
                }
                else
                {
                    msg.append(" Horas");
                }
            msg.append("</span></b>");
            msg.append(" más de las que tienes disponibles");
            msg.append("<br>");
            msg.append("La cotización original (enviada al cliente) se excedera por ");
            msg.append("<span color=\"#FF0000\"><b>");
            msg.append(cuantoSePasoDeCotizacionOriginal);
                if(cuantoSePasoDeCotizacionOriginal==1)
                {
                    msg.append(" Hora");
                }
                else
                {
                    msg.append(" Horas");
                }
            msg.append("</b></span>");
            msg.append("<br>");
            msg.append("¿Deseas asignar las horas a esta tarea de todas formas?");
            msg.append("<br>");
            msg.append("<br>");
            msg.append("<i><b>Nota:</b></i>");
            msg.append("<br>");
            msg.append("<i>");
            msg.append("Las horas asignadas a la planificación se agregarán automaticamente a los gastos de la obra");
            msg.append("</i>");

            Object[] options = {"Cancelar","Aumentar las horas y asignar"};
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
        this.callback.actualizar(value,AsignacionHerramientasHoras.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
