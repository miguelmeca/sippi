
package vista.planificacion;


import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Planificacion;
import modelo.TareaPlanificacion;
import util.FechaUtil;
import vista.interfaces.ICallBack_v3;

/**
 * @author Fran
 */
public class NuevaTareaPlanificacion extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "Callback_NuevaSubTareaPlanificacion";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tareaPadre;
    private Planificacion planificacion;
    private String nombreTareaNueva;
    private Date inicioTarea;
    private Date finTarea;
    
    private Date inicioPadre;
    private Date finPadre;
    
    public NuevaTareaPlanificacion(ICallBack_v3 callback, TareaPlanificacion tareaPadre, Planificacion planificacion) {
        initComponents();
        this.tareaPadre = tareaPadre;
        this.callback = callback;
        this.planificacion=planificacion;
        
        if(tareaPadre!=null && tareaPadre.getFechaInicio()!=null && tareaPadre.getFechaFin()!=null)
        {
            inicioPadre = new Date(tareaPadre.getFechaInicio().getTime());
            finPadre = new Date(tareaPadre.getFechaFin().getTime());            
        }
        else if(planificacion.getFechaInicio()!=null && planificacion.getFechaFin()!=null)
        {
            inicioPadre = new Date(planificacion.getFechaInicio().getTime());
            finPadre = new Date(planificacion.getFechaFin().getTime());
            
        }
        jdcFechaInicio.setDate(inicioPadre);
        jdcFechaFin.setDate(finPadre);
            
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtNombreTarea = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        btnCopyDates = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jdcFechaFin = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setTitle("Nueva Tarea");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jLabel3.setText("Nombre de la tarea:");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha de inicio:");

        btnCopyDates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Forward.png"))); // NOI18N
        btnCopyDates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyDatesActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha de fin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreTarea)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnCancelar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAceptar))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCopyDates)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(txtNombreTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnAceptar)))
                    .addComponent(btnCopyDates)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
       if(validarDatos())
        {
            nombreTareaNueva=txtNombreTarea.getText();
            inicioTarea=jdcFechaInicio.getDate();
            finTarea=jdcFechaFin.getDate();
            actualizar();
            this.dispose();
        }
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCopyDatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyDatesActionPerformed
        Date fini = jdcFechaInicio.getDate();
        if(fini!=null)
        {
            jdcFechaFin.setDate(fini);
        }
    }//GEN-LAST:event_btnCopyDatesActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCopyDates;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private com.toedter.calendar.JDateChooser jdcFechaFin;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    private javax.swing.JTextField txtNombreTarea;
    // End of variables declaration//GEN-END:variables

    private boolean validarDatos()
    {
        if(txtNombreTarea.getText().equals(""))
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Debe ingresar un nombre para la tarea");
            return false;
        }
        if(jdcFechaInicio.getDate()==null)
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Debe ingresar una fecha de inicio de la tarea");
            return false;
        }
        if(jdcFechaFin.getDate()==null)
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Debe ingresar una fecha de fin de la tarea");
            return false;
        }
        if(jdcFechaFin.getDate().before(jdcFechaInicio.getDate()))
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de fin no puede ser mayor a la inicio");
            return false;
        }
        if(tareaPadre!=null)
        {
            for (int i = 0; i < tareaPadre.getSubtareas().size(); i++) 
            {
                if(txtNombreTarea.getText().equals(tareaPadre.getSubtareas().get(i).getNombre()))
                {
                    mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Ya existe otra subtarea con el mismo nombre");
                    return false;
                }
            }
        }
        
        String finalMsje="";
        if(tareaPadre==null)
        {finalMsje="la planificación";}
        else
        finalMsje="la tarea contenedora";
        if(FechaUtil.diasDiferencia(finPadre, jdcFechaFin.getDate())>0)
        //if(jdcFechaFin.getDate().after(tareaPadre.getFechaFin()))
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de fin no puede ser mayor a la fecha de fin de "+finalMsje);
            return false;
        }
        if(FechaUtil.diasDiferencia(jdcFechaInicio.getDate(), inicioPadre)>0)
        //if(jdcFechaFin.getDate().before(tareaPadre.getFechaInicio()))
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de inicio no puede ser menor a la fecha de inicio de "+finalMsje);
            return false;
        }
        return true;
        
    }
    
    public void mostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this,mensaje,titulo,tipo);
    } 
   
    
    /**
     * Manda el mensaje callback con los datos asociados
     * data[0] = tareaPadre;
     * data[1] = nombreTareaNueva;
     * data[2] = inicioTarea;
     * data[3] = finTarea; 
     * 
     */
    private void actualizar()
    {
        Calendar fInicio = Calendar.getInstance();
        fInicio.setTime(this.inicioTarea);
        fInicio.set(Calendar.HOUR,21);
        fInicio.set(Calendar.MINUTE,0);
        fInicio.set(Calendar.SECOND,0);
        fInicio.set(Calendar.MILLISECOND,0);
        
        Calendar fFin = Calendar.getInstance();
        fFin.setTime(this.finTarea);
        fFin.set(Calendar.HOUR,21);
        fFin.set(Calendar.MINUTE,0);
        fFin.set(Calendar.SECOND,0);
        fFin.set(Calendar.MILLISECOND,0);        
            
        
        //WORKARROUND - NO FUNCA !!
        Date p1 = new Date(this.inicioTarea.getYear(),this.inicioTarea.getMonth(),this.inicioTarea.getDay(),0,0,0);
        Date p2 = new Date(this.finTarea.getYear(),this.finTarea.getMonth(),this.finTarea.getDay(),0,0,0);
        Object[] data = new Object[4];
        data[0] = this.tareaPadre;
        data[1] = this.nombreTareaNueva;
        data[2] = fInicio.getTime();
        data[3] = fFin.getTime();        
        this.callback.actualizar(0,NuevaTareaPlanificacion.CALLBACK_FLAG,true,data);
        this.dispose();        
        
//        Object[] data = new Object[4];
//        data[0] = this.tareaPadre;
//        data[1] = this.nombreTareaNueva;
//        data[2] = this.inicioTarea;
//        data[3] = this.finTarea;        
//        this.callback.actualizar(0,NuevaTareaPlanificacion.CALLBACK_FLAG,true,data);
//        this.dispose();
    }
    
}
