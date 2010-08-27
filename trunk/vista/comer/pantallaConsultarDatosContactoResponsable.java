/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarEmpleado.java
 *
 * Created on 09-may-2010, 18:52:12
 */

package vista.comer;
import vista.comer.*;
import java.util.ArrayList;
//import java.util.List;
import javax.swing.DefaultComboBoxModel;
//import modelo.TipoDocumento;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import util.HibernateUtil;
import javax.swing.JOptionPane;
import util.Tupla;
import util.NTupla;
import util.FechaUtil;
import java.util.Iterator;
import vista.interfaces.IAyuda;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComponent;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import vista.interfaces.ICallBack;
import controlador.comer.GestorConsultarDatosContactoResponsable;


import util.SwingPanel;
/**
 *
 * @author Fran
 */
public class pantallaConsultarDatosContactoResponsable extends javax.swing.JInternalFrame implements IAyuda, ICallBack {


    //El gestor a utilizar sera gestorRegistrar o gestorModificar
    //segun sea el caso.
    //El atributo gestor hace referencia al mismo gestor único nombrado
    //anteriormente, y se usa para acceder a metodos comunes que estan en
    //los 2 gestores
    private GestorConsultarDatosContactoResponsable gestor;
    
    //
    /*private JComponent cmbfechaNacimiento;
    private JComponent cmbfechaVencimiento;
    private ArrayList<String> listaNroTel;
    private ArrayList<Tupla> listaTipoTel;
    private ArrayList<Tupla> listaTipoEspecialidad;
    private   ArrayList<Tupla> listaRangoEspecialidad;
    private ArrayList<Tupla> listaTipoCapacitacion;
    private ArrayList<Date> listaVencimientoCapacitacion;*/
    //private Date fechaVencimientoCapActual;
    /** Creates new form frmRegistrarEmpleado */
    //private int legajoEmpModificar;
    private ICallBack pantallaConsultar;
    //private boolean instanciadaDesdeCU;
    private String nombre;
    private String apellido;
    private String cuil;
    private int id;


    
    

   public pantallaConsultarDatosContactoResponsable(int id, ICallBack pantallaConsu)
    {
        initComponents();
        //modificar=true;
        //legajoEmpModificar=legajo;
        pantallaConsultar=pantallaConsu;
        gestor = new GestorConsultarDatosContactoResponsable(this);
        //gestorModificar = new GestorModificarEmpleado(this);
        //gestor = new GestorModificarEmpleado(this);
        this.id=id;
        this.habilitarVentana();
        //listaNroTel= new ArrayList<String>();
        //listaTipoTel= new ArrayList<Tupla>();
        //boolean r= gestorModificar.levantarEmpleado(legajo);
        boolean r= gestor.levantarContactoResponsable(id);
        if (!r)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Error levantando el Contacto de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        this.setTitle("Consultar datos de Contacto Asignado - "+ nombre+" "+apellido);

    }
   
   public void opcionConsultarContactoResponsable()
    {


    }
   private void habilitarVentana()
    {
      

   }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuTelefonos = new javax.swing.JPopupMenu();
        emQuitarTelefono = new javax.swing.JMenuItem();
        menuEspecialidades = new javax.swing.JPopupMenu();
        emQuitarEspecialidad = new javax.swing.JMenuItem();
        menuCapacitaciones = new javax.swing.JPopupMenu();
        emQuitarCapacitacion = new javax.swing.JMenuItem();
        btnAceptar = new javax.swing.JButton();
        jpDatosPersonales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCuil = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblFechaRegistro = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        btnModificarEmpleado = new javax.swing.JButton();

        emQuitarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        emQuitarTelefono.setText("Quitar");
        emQuitarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emQuitarTelefonoActionPerformed(evt);
            }
        });
        menuTelefonos.add(emQuitarTelefono);

        emQuitarEspecialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        emQuitarEspecialidad.setText("Quitar");
        emQuitarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emQuitarEspecialidadActionPerformed(evt);
            }
        });
        menuEspecialidades.add(emQuitarEspecialidad);

        emQuitarCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        emQuitarCapacitacion.setText("Quitar");
        emQuitarCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emQuitarCapacitacionActionPerformed(evt);
            }
        });
        menuCapacitaciones.add(emQuitarCapacitacion);

        setClosable(true);
        setIconifiable(true);
        setTitle("Consultar datos de Contacto Asignado");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Cerrar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jpDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Apellido:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Email:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel23.setText("CUIL:");

        lblNombre.setText("-");

        lblCuil.setText("-");

        lblApellido.setText("-");

        lblEmail.setText("-");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Fecha de Registro:");

        lblFechaRegistro.setText("-");

        javax.swing.GroupLayout jpDatosPersonalesLayout = new javax.swing.GroupLayout(jpDatosPersonales);
        jpDatosPersonales.setLayout(jpDatosPersonalesLayout);
        jpDatosPersonalesLayout.setHorizontalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(27, 27, 27)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCuil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(139, 139, 139))
                    .addComponent(jLabel4)
                    .addComponent(jLabel23)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(27, 27, 27)
                                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                                .addGap(139, 139, 139)))
                        .addContainerGap())))
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(lblCuil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblFechaRegistro)))
        );

        jLabel23.getAccessibleContext().setAccessibleName("");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Números de Telefono"));

        tablaTelefonos.setFont(new java.awt.Font("Tahoma", 0, 10));
        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaTelefonos.setToolTipText("Telefonos agregados del empleado");
        tablaTelefonos.setComponentPopupMenu(menuTelefonos);
        tablaTelefonos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(tablaTelefonos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnModificarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnModificarEmpleado.setText("Modificar");
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnModificarEmpleado)
                        .addGap(34, 34, 34)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificarEmpleado)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
         /* int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea salir?","Cerrar",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        {       
            this.dispose();
        }  */
        this.dispose();
           
    }//GEN-LAST:event_btnAceptarActionPerformed
    
    private void vaciarCampos()
    {
        
        lblApellido.setText("");        
        lblCuil.setText("");        
        lblEmail.setText("");
        //lblFechaIngreso.setText("");
        lblNombre.setText("");
        lblFechaRegistro.setText("");
        
        
        
       
        //((DefaultTableModel)tablaEspecialidades.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        
        
    }
        
    private void emQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarTelefonoActionPerformed
      
    }//GEN-LAST:event_emQuitarTelefonoActionPerformed

    private void emQuitarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarEspecialidadActionPerformed
        
    }//GEN-LAST:event_emQuitarEspecialidadActionPerformed

    private void emQuitarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarCapacitacionActionPerformed
       
    }//GEN-LAST:event_emQuitarCapacitacionActionPerformed

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed

            
            pantallaRegistrarContactoResponsable pre = new pantallaRegistrarContactoResponsable(id, this);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            //pre.opcionRegistrarEmpleado();

}//GEN-LAST:event_btnModificarEmpleadoActionPerformed
 
    /**
    * @param args the command line arguments
    */
   /* public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaConsultarDatosEmpleado().setVisible(true);
            }
        });
    }*/

    /////////////////////////////////////////////////////
    //Modificar Contacto Responsable
   
	
        
        public void telefonosContactoResponsable(ArrayList<String> numero,ArrayList<Tupla> tipo)
        {

            DefaultTableModel tabTel= (DefaultTableModel)tablaTelefonos.getModel();

            for(int i=0; i<numero.size();i++)
            {

               Object[] obj=new Object[2];
               obj[0]=numero.get(i);
               obj[1]=(tipo.get(i));
              tabTel.addRow(obj);
            }
            tablaTelefonos.setModel(tabTel);

	}

        public void datosPersonalesContactoResponsable(String cuil, String nombre, String apellido,  Date fechaReg,String email)
        {
            //lblLegajo.setText(leg);
            this.nombre=nombre;
            this.apellido=apellido;
            lblCuil.setText(cuil);
            
            lblNombre.setText(nombre);
            lblApellido.setText(apellido);
            lblEmail.setText(email);
            
                        
            lblFechaRegistro.setText(FechaUtil.getFecha(fechaReg));
            //lblFechaRegistro.setText(fechaReg.toString());
           /* if(fechaIng!=null)
            {lblFechaIngreso.setText(FechaUtil.getFecha(fechaIng));}
            else
            {lblFechaIngreso.setText("-");}*/

	}

        
        //Fin Metodos Modificar Contacto Responsable
    /////////////////////////////////////////////////////

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JMenuItem emQuitarCapacitacion;
    private javax.swing.JMenuItem emQuitarEspecialidad;
    private javax.swing.JMenuItem emQuitarTelefono;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCuil;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFechaRegistro;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPopupMenu menuCapacitaciones;
    private javax.swing.JPopupMenu menuEspecialidades;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JTable tablaTelefonos;
    // End of variables declaration//GEN-END:variables

    public void actualizar(int n, boolean exito)
    {   //Uso el int n para mandar el legajo en vez del error, necesito algo que comunique las ventanas
       vaciarCampos();
        if(exito)
        { boolean r= gestor.levantarContactoResponsable(n);
            if (!r)
            {
                JOptionPane.showMessageDialog(this.getParent(),"Error levantando el contacto de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        pantallaConsultar.actualizar(5, exito);
    }
    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Seleccione Modificar si desea cambiar los datos del Contacto Asignado";
    }

    public String getTituloAyuda() {
        
        return "Opción: Consultar datos del Contacto Asignado";
       
    }
}
