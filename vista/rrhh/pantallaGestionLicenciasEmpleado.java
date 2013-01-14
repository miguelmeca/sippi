/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaGestionLicenciasEmpleado.java
 *
 * Created on 13-ago-2010, 10:24:42
 */

package vista.rrhh;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import controlador.rrhh.gestorGestionarLicenciasEmpleado;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import util.FechaUtil;
import util.NTupla;
import util.Tupla;
import vista.interfaces.ICallBack;

/**
 *
 * @author Administrador
 */
public class pantallaGestionLicenciasEmpleado extends javax.swing.JInternalFrame {

    private gestorGestionarLicenciasEmpleado gestor;
    private int SELECCION_oid = 0;
    private ICallBack winLlamada;
    private boolean modificar;

    /** Creates new form pantallaGestionLicenciasEmpleado */
    public pantallaGestionLicenciasEmpleado() {
        initComponents();
        modificar=true;
        gestor = new gestorGestionarLicenciasEmpleado(this);

        habilitarVentana();

    }

    private void habilitarVentana()
    {
        mostrarEmpleados();
        mostrarTiposLicencia();
    }

    public void modificarLicencia(int oid)
    {
        modificar=true;
        this.SELECCION_oid = oid;
        btnGuardarMofidificar.setText("Guardar Modificaciones");

        //mostrarEmpleados();
        //mostrarTiposLicencia();
        // CARGO LA LICENCIA Y MUESTRO LOS DATOS
        gestor.mostrarLicencia(this.SELECCION_oid);
        //mostrarLicencia();
    }
   

    public void mostrarLicencia(int oid)
    {
        modificar=false;
        this.SELECCION_oid = oid;
        btnGuardarMofidificar.setText("Aceptar");
        btnCancelar.setVisible(false);
        
        (txtFechaInicio.getCalendarButton()).setEnabled(false);
        (txtFechaFin.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)txtFechaInicio.getDateEditor()).setEditable(false);
        ((JTextFieldDateEditor)txtFechaFin.getDateEditor()).setEditable(false);        
        cmbTipoLicencia.setEnabled(false);
        cmbEmpleado.setEnabled(false);
        txtMotivo.setEditable(false);
        gestor.mostrarLicencia(this.SELECCION_oid);
    }

    public void mostrarFechas(Date inicio, Date fin)
    {
            txtFechaInicio.setDate(inicio);
            txtFechaFin.setDate(fin);
    }

    public void mostrarMotivo(String motivo)
    {
        txtMotivo.setText(motivo);
    }

    private void mostrarEmpleados()
    {
        ArrayList<Tupla> lista = gestor.mostrarEmpleados();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.isEmpty())
        {
            valores.addElement(new Tupla(0,"No hay Empleados Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbEmpleado.setModel(valores);
    }

    private void mostrarTiposLicencia()
    {
        ArrayList<Tupla> lista = gestor.mostrarTiposLicencia();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        Iterator<Tupla> it = lista.iterator();

        if(lista.size()==0)
        {
            valores.addElement(new Tupla(0,"No hay Tipos de Licencia Cargados"));
        }

        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbTipoLicencia.setModel(valores);
    }

    /**
     * EG-0007 : Error al cargar la lista de Tipos de Licencia
     * EG-0008 : No se pudo cargar la lista de empleados
     * MI-0003 : La fecha de fin debe ser mayor o igual a la de inicio
     * EG-0009 : No se pudo setear el Empleado
     * EG-0010 : No se pudo setear el Tipo de Licencia
     * EG-0011 : Faltan Datos Requeridos
     * EG-0012 : Error en la conexion a la BD
     * MI-0004 : Se registro correctamente la licencia
     * MI-0005 : Ya tiene licencia en esa fecha
     * MI-0012 : Error en la carga de datos
     * @param cod
     */
    public void MostrarMensaje(String cod)
    {
        System.out.println("Mensaje "+cod+" detectado !!!");
        if(cod.equals("EG-0007"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Tipos de Licencia","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("EG-0008"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Empleados","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("MI-0003"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"La fecha de Fin de licencia debe ser mayor o igual a la de inicio","Datos No validos",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("EG-0009"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar el empleado elegido\n Intentelo nuevamente más tarde","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("EG-0010"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar el Tipo de Licencia elegido\n Intentelo nuevamente más tarde","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("EG-0011"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"Faltan datos requeridos","Error",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("EG-0012"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo conectar con la base de datos","Error",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("MI-0004"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"Se registro correctamente la licencia","Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(this.winLlamada!=null)
            {
                winLlamada.actualizar(1,true);
            }
            this.dispose();
        }
        if(cod.equals("MI-0013"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"Se modificó correctamente la licencia","Modificación Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(this.winLlamada!=null)
            {
                winLlamada.actualizar(1,true);
            }
            this.dispose();
        }
        if(cod.equals("MI-0005"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"El empleado seleccionado ya tiene una licencia en esa fecha","Atención",JOptionPane.INFORMATION_MESSAGE);
        }
        if(cod.equals("MI-0012"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"Error en la carga de datos","Atención",JOptionPane.INFORMATION_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        cmbEmpleado = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbTipoLicencia = new javax.swing.JComboBox();
        txtFechaInicio = new com.toedter.calendar.JDateChooser();
        txtFechaFin = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        btnGuardarMofidificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Gestionar Licencias y Vacaciones");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Licencia"));

        cmbEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpleadoActionPerformed(evt);
            }
        });

        jLabel1.setText("Empleado:");

        jLabel2.setText("Fecha Inicio:");

        jLabel3.setText("Fecha de Fin:");

        jLabel4.setText("Motivo:");

        jLabel5.setText("Tipo de Licencia:");

        cmbTipoLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoLicenciaActionPerformed(evt);
            }
        });

        txtFechaInicio.setDateFormatString("dd-MM-yyyy");
        txtFechaInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtFechaInicioMouseReleased(evt);
            }
        });
        txtFechaInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaInicioKeyReleased(evt);
            }
        });

        txtFechaFin.setDateFormatString("dd-MM-yyyy");
        txtFechaFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtFechaFinMouseReleased(evt);
            }
        });
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaFinKeyReleased(evt);
            }
        });

        txtMotivo.setColumns(20);
        txtMotivo.setRows(5);
        jScrollPane1.setViewportView(txtMotivo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(12, 12, 12)
                                .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbEmpleado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoLicencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbTipoLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        btnGuardarMofidificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnGuardarMofidificar.setText("Guardar");
        btnGuardarMofidificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMofidificarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardarMofidificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardarMofidificar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarMofidificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMofidificarActionPerformed

        // Mínimo 1 día de licencia
        /*if(FechaUtil.diasDiferencia(txtFechaInicio.getDate(),txtFechaFin.getDate())>=0)
        {*/
        if(modificar==true)
        {this.dispose();}
        
        if(validarDatos() && modificar==true){
            if(SELECCION_oid==0)
            {
                // GUARDO UNO NUEVO
                gestor.setEmpleado((Tupla)cmbEmpleado.getSelectedItem());
                gestor.setFechaInicio(txtFechaInicio.getDate());
                gestor.setFechaFin(txtFechaFin.getDate());
                gestor.setTipoLicencia((Tupla)cmbTipoLicencia.getSelectedItem());
                gestor.setMotivo(txtMotivo.getText());

                gestor.confirmarNuevaLicencia();
            }
            else
            {
                // MODIFICO
                gestor.setEmpleado((Tupla)cmbEmpleado.getSelectedItem());
                gestor.setFechaInicio(txtFechaInicio.getDate());
                gestor.setFechaFin(txtFechaFin.getDate());
                gestor.setTipoLicencia((Tupla)cmbTipoLicencia.getSelectedItem());
                gestor.setMotivo(txtMotivo.getText());

                gestor.modificarLicencia(SELECCION_oid);
            }
        }
       

    }//GEN-LAST:event_btnGuardarMofidificarActionPerformed

    private void cmbEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpleadoActionPerformed
      
    }//GEN-LAST:event_cmbEmpleadoActionPerformed

    private void cmbTipoLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoLicenciaActionPerformed
     
    }//GEN-LAST:event_cmbTipoLicenciaActionPerformed

    private void txtFechaInicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInicioKeyReleased
      
    }//GEN-LAST:event_txtFechaInicioKeyReleased

    private void txtFechaFinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaFinKeyReleased
      
    }//GEN-LAST:event_txtFechaFinKeyReleased

    private void txtFechaInicioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFechaInicioMouseReleased
       
    }//GEN-LAST:event_txtFechaInicioMouseReleased

    private void txtFechaFinMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFechaFinMouseReleased
        
    }//GEN-LAST:event_txtFechaFinMouseReleased

    public void setCallBack(ICallBack win)
    {
        this.winLlamada = win;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarMofidificar;
    private javax.swing.JComboBox cmbEmpleado;
    private javax.swing.JComboBox cmbTipoLicencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private com.toedter.calendar.JDateChooser txtFechaInicio;
    private javax.swing.JTextArea txtMotivo;
    // End of variables declaration//GEN-END:variables

    public void seleccionarEmpleadoLicencia(int oId)
    {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cmbEmpleado.getModel();
        for (int i = 0; i < modelo.getSize(); i++)
        {
            Tupla tup = (Tupla)modelo.getElementAt(i);
            if(tup.getId()==oId)
            {
                cmbEmpleado.setSelectedIndex(i);
                return;
            }
        }
    }

    public void seleccionarTipoLicencia(int oid) {

        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cmbTipoLicencia.getModel();
        for (int i = 0; i < modelo.getSize(); i++)
        {
            Tupla tup = (Tupla)modelo.getElementAt(i);
            if(tup.getId()==oid)
            {
                cmbTipoLicencia.setSelectedIndex(i);
                return;
            }
        }
    }
     
    
    private boolean validarDatos()
    {
        
        boolean ban=true;
            Date fechaAct=new Date();
            
            //El siguiente codigo comentado es para validar que el usuario ingrese una fecha de nacimiento
            if(  (((JDateChooser) txtFechaInicio).getDate()== null ) )
           {
                JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de inicio valida","ERROR",JOptionPane.ERROR_MESSAGE);
              return false;
           }
           else
            {
                  if(  (((JDateChooser) txtFechaFin).getDate()== null ) )
                  {
                         JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de fin valida","ERROR",JOptionPane.ERROR_MESSAGE);
                       return false;
                  }
                  else if(( ((JDateChooser) txtFechaInicio).getDate().compareTo(txtFechaFin.getDate()) )>0 )
                  {
                       JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de inicio menor a la fecha de fin","ERROR",JOptionPane.ERROR_MESSAGE);
                      return false;          
                   
                  }
            }    
            Tupla td=new Tupla();
            try{
                td=(Tupla)cmbEmpleado.getItemAt(cmbEmpleado.getSelectedIndex());
                if(td.getId()<=0)
                {
                  ban=false;
                  JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un empleado","ERROR",JOptionPane.ERROR_MESSAGE);
                  return ban;
                }
            }
            catch(Exception e)
            {
               ban=false;
                  JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un empleado","ERROR",JOptionPane.ERROR_MESSAGE);
                  return ban; 
            }
            
            try{
                td=(Tupla)cmbTipoLicencia.getItemAt(cmbTipoLicencia.getSelectedIndex());
                if(td.getId()<=0)
                {
                  ban=false;
                  JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un tipo de licencia","ERROR",JOptionPane.ERROR_MESSAGE);
                  return ban;
                }
            }
            catch(Exception e)
            {
               ban=false;
                  JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar un tipo de licencia","ERROR",JOptionPane.ERROR_MESSAGE);
                  return ban; 
            }
       
        

        return ban;
        
    }
    
    
}
