
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.HerramientaDeEmpresa;
import modelo.Planificacion;
import modelo.SubObraXHerramientaModif;
import modelo.TareaPlanificacion;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import util.FechaUtil;
import vista.interfaces.ICallBack_v3;

/**
 * @author Fran
 */
public class AsignacionTareaAPlanificacion extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "Callback_AsignacionTareaAPlanificacion";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tareaPadre;
    private int hashTareaCotizacion;
    private Date inicioTarea;
    private Date finTarea;
    
    private Date inicioPadre;
    private Date finPadre;
    
    public AsignacionTareaAPlanificacion(ICallBack_v3 callback, int hashTareaCotizacion, TareaPlanificacion tareaPadre, Planificacion planificacion) {
        initComponents();
        this.tareaPadre = tareaPadre;
        this.callback = callback;
        this.hashTareaCotizacion=hashTareaCotizacion;
        
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

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        jdcFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        btnCopy = new javax.swing.JButton();

        setClosable(true);
        setTitle("Fechas de la tarea");

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

        jLabel6.setText("Fecha de fin:");

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Forward.png"))); // NOI18N
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCopy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jdcFechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jdcFechaFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCopy)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAceptar)
                            .addComponent(btnCancelar))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
       if(validarDatos())
        {
            inicioTarea=jdcFechaInicio.getDate();
            finTarea=jdcFechaFin.getDate();
            actualizar();
            this.dispose();
        }
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        Date fini = jdcFechaInicio.getDate();
        if(fini!=null)
        {
            jdcFechaFin.setDate(fini);
        }
    }//GEN-LAST:event_btnCopyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCopy;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private com.toedter.calendar.JDateChooser jdcFechaFin;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    // End of variables declaration//GEN-END:variables

    private boolean validarDatos()
    {
        
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
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de fin no puede ser menor a la inicio");
            return false;
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
        
        
        Object[] data = new Object[4];
        data[0] = this.tareaPadre;
        data[1] = this.hashTareaCotizacion;
        data[2] = fInicio.getTime();
        data[3] = fFin.getTime();     
        this.callback.actualizar(0,AsignacionTareaAPlanificacion.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
