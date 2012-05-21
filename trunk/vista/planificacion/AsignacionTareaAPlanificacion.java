
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.HerramientaDeEmpresa;
import modelo.PlanificacionXXX;
import modelo.SubObraXHerramientaModif;
import modelo.TareaPlanificacion;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
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
    
    public AsignacionTareaAPlanificacion(ICallBack_v3 callback, int hashTareaCotizacion, TareaPlanificacion tareaPadre) {
        initComponents();
        this.tareaPadre = tareaPadre;
        this.callback = callback;
        this.hashTareaCotizacion=hashTareaCotizacion;
        
       Date fechaSugeridaInicio;
       Date fechaSugeridaFin;
       if(tareaPadre!=null)
       {
         inicioTarea.setTime(tareaPadre.getFechaInicio().getTime());
         finTarea.setTime(tareaPadre.getFechaFin().getTime());
       }
       
        
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

        setClosable(true);
        setTitle("Fechas de la tarea");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha de inicio:");

        jLabel6.setText("Fecha de fin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnCancelar)
                        .addGap(67, 67, 67)
                        .addComponent(btnAceptar)))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
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
        
        if(tareaPadre!=null)
        {
            if(jdcFechaFin.getDate().after(tareaPadre.getFechaFin()))
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de fin no puede ser mayor a la fecha de fin de la tarea contenedora");
                return false;
            }
            if(jdcFechaFin.getDate().before(tareaPadre.getFechaInicio()))
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La fecha de inicio no puede ser menor a la fecha de inicio de la tarea contenedora");
                return false;
            }
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
        Object[] data = new Object[4];
        data[0] = this.tareaPadre;
        data[1] = this.hashTareaCotizacion;
        data[2] = this.inicioTarea;
        data[3] = this.finTarea;        
        this.callback.actualizar(0,AsignacionTareaAPlanificacion.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
