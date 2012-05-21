
package vista.planificacion;


import java.util.Date;
import javax.swing.JOptionPane;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import vista.interfaces.ICallBack_v3;

/**
 * @author Fran
 */
public class NuevaTareaPlanificacion extends javax.swing.JInternalFrame {

    public static final String CALLBACK_FLAG = "Callback_NuevaSubTareaPlanificacion";
    
    private ICallBack_v3 callback;
    
    private TareaPlanificacion tareaPadre;
    private PlanificacionXXX planificacion;
    private String nombreTareaNueva;
    private Date inicioTarea;
    private Date finTarea;
    
    public NuevaTareaPlanificacion(ICallBack_v3 callback, TareaPlanificacion tareaPadre) {
        initComponents();
        this.tareaPadre = tareaPadre;
        this.callback = callback;
        inicioTarea.setTime(tareaPadre.getFechaInicio().getTime());
        finTarea.setTime(tareaPadre.getFechaFin().getTime());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNombreTarea = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        jdcFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Nueva Tarea");

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

        jLabel3.setText("Nombre de la tarea:");

        jLabel4.setText("Fecha de inicio:");

        jLabel6.setText("Fecha de fin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addGap(55, 55, 55))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNombreTarea)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(txtNombreTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
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
        
        for (int i = 0; i < planificacion.getTareas().size(); i++) 
        {
            if(txtNombreTarea.getText().equals(planificacion.getTareas().get(i).getNombre()))
            {
                mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Ya existe otra subtarea con el mismo nombre");
                return false;
            }
        }
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
        data[1] = this.nombreTareaNueva;
        data[2] = this.inicioTarea;
        data[3] = this.finTarea;        
        this.callback.actualizar(0,NuevaTareaPlanificacion.CALLBACK_FLAG,true,data);
        this.dispose();
    }
    
}
