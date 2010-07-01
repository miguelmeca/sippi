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

import controlador.comer.GestorRegistrarNuevoContactoResponsable;
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
import java.util.Iterator;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComponent;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import util.LimitadorCaracteres;
/**
 *
 * @author Fran
 */
public class pantallaRegistrarContactoResponsable extends javax.swing.JInternalFrame implements IAyuda
{

    GestorRegistrarNuevoContactoResponsable gestor;
    
    private ArrayList<String> listaNroTel;
    private ArrayList<Tupla> listaTipoTel;
    private ICallBack pantallaCUSolicitante;
    //private Date fechaVencimientoCapActual;
   
    public pantallaRegistrarContactoResponsable() {
        initComponents();
        gestor = new GestorRegistrarNuevoContactoResponsable(this);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
            
    }
   
    public pantallaRegistrarContactoResponsable(ICallBack cuSoli) {
        initComponents();
        pantallaCUSolicitante=cuSoli;
        gestor = new GestorRegistrarNuevoContactoResponsable(this);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();        
        if (pantallaCUSolicitante!= null)
        {cmbEmpresas.setEnabled(false);
        cmbPlantas.setEnabled(false);}
    }
   

   public void opcionRegistrarEmpleado()
    {


    }
   private void habilitarVentana()
    {

        mostrarTiposDeTelefono();
        if (pantallaCUSolicitante== null)
        {
        mostrarEmpresas();
        mostrarPlantas();}
       
KeyAdapter kaNuemros=(new KeyAdapter()
{

            @Override
   public  void keyTyped(KeyEvent e)
   {
      char caracter = e.getKeyChar();

      // Verificar si la tecla pulsada no es un digito
      if(((caracter < '0') ||
         (caracter > '9')) &&
         (caracter != KeyEvent.VK_BACK_SPACE))
      {
         e.consume();  // ignorar el evento de teclado
      }
   }
});
        //-----------------------
        txtCUIL.addKeyListener(kaNuemros);
        
        //txtTelefono.addKeyListener(kaNuemros);

        txtApellido.setDocument(new LimitadorCaracteres(txtApellido,50));
        
        txtCUIL.setDocument(new LimitadorCaracteres(txtApellido,15));
        txtEmail.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtNombre.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtTelefono.setDocument(new LimitadorCaracteres(txtApellido,15));
        txtCargo.setDocument(new LimitadorCaracteres(txtApellido,50));



   }



   private void mostrarEmpresas()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        ArrayList<Tupla> lista = gestor.mostrarEmpresas();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbEmpresas.setModel(valores);

    }

    private void mostrarPlantas()
    {
       if(cmbEmpresas.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbEmpresas.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarPlantas(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbPlantas.setModel(valores);
       }
    }
    
    public void mostrarTiposDeTelefono()
    {
        ArrayList<Tupla> listaNombresTipoDeTelefono = gestor.mostrarTiposDeTelefono();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombresTipoDeTelefono)
        {
            model.addElement(nombre);
        }
        cmbTiposTelefono.setModel(model);
    }

    
    private void cargarTelefonos()
    {
        
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Iterator it = modelo.getDataVector().iterator();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
           // 
            
            //System.out.println("HOLA");
            listaTipoTel.add((Tupla)fila.get(0));
            listaNroTel.add((String)fila.get(1));
            
        }
       
    }
    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuTelefonos = new javax.swing.JPopupMenu();
        emAgregarTelefono = new javax.swing.JMenuItem();
        emQuitarTelefono = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jpDatosPersonales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbTiposTelefono = new javax.swing.JComboBox();
        btnAgregarTelefono = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        btnQuitarTelefono = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCUIL = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cmbEmpresas = new javax.swing.JComboBox();
        cmbPlantas = new javax.swing.JComboBox();

        emAgregarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        emAgregarTelefono.setText("Agregar");
        emAgregarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emAgregarTelefonoActionPerformed(evt);
            }
        });
        menuTelefonos.add(emAgregarTelefono);

        emQuitarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        emQuitarTelefono.setText("Quitar");
        emQuitarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emQuitarTelefonoActionPerformed(evt);
            }
        });
        menuTelefonos.add(emQuitarTelefono);

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar un nuevo Contacto Responsable");

        jpDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Nombre:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Apellido: ");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Email: ");

        javax.swing.GroupLayout jpDatosPersonalesLayout = new javax.swing.GroupLayout(jpDatosPersonales);
        jpDatosPersonales.setLayout(jpDatosPersonalesLayout);
        jpDatosPersonalesLayout.setHorizontalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                .addGap(47, 47, 47)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Números de Telefono"));

        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Tipo de Teléfono y Número:");

        cmbTiposTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTiposTelefonoActionPerformed(evt);
            }
        });

        btnAgregarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnAgregarTelefono.setText("Agregar");
        btnAgregarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTelefonoActionPerformed(evt);
            }
        });

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

        btnQuitarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(btnQuitarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAgregarTelefono))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbTiposTelefono, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTiposTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnQuitarTelefono)
                            .addComponent(btnAgregarTelefono)))))
        );

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnConfirmar.setText("Aceptar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Laborales"));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Cargo: ");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel19.setText("Empresa: ");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel20.setText("Planta: ");

        txtCUIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCUILActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel23.setText("CUIL: ");

        cmbEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpresasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbPlantas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbEmpresas, 0, 185, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCUIL)
                    .addComponent(txtCargo, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(cmbEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel23.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(btnConfirmar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea cancelar?","Cancelar",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        { if(pantallaCUSolicitante !=null)
                {
                   pantallaCUSolicitante.actualizar(1, true);
                    
                }
       this.dispose();      }       

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void btnAgregarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTelefonoActionPerformed
        
        agregarTelefonoTabla((Tupla)cmbTiposTelefono.getSelectedItem(), txtTelefono.getText());
    
    
    }//GEN-LAST:event_btnAgregarTelefonoActionPerformed

    private void agregarTelefonoTabla(Tupla tipo, String numero)
    {
        if(!txtTelefono.getText().isEmpty())
     {
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Object[] item = new Object[2];
        item[0] = tipo;
        item[1] = numero;
        modelo.addRow(item);
        txtTelefono.setText(""); 
        }
    }
    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
               
        if(ValidarDatos())
        {
            cargarTelefonos();


            if (pantallaCUSolicitante== null)
            {gestor.datosPersonalesContactoResponsable(txtCUIL.getText(), txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCargo.getText(), (Tupla)cmbEmpresas.getSelectedItem(),(Tupla)cmbPlantas.getSelectedItem());
            }
            else
            {
            gestor.datosPersonalesContactoResponsable(txtCUIL.getText(), txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCargo.getText());
            }

            gestor.telefonosContactoResponsable(listaNroTel, listaTipoTel);
            if(!gestor.validarPlantaSinContacto())
            {
                 JOptionPane.showMessageDialog(this.getParent(),"La planta seleccionada ya posee un contato registrado. Si lo desea puede modificarlo modificando la planta","ERROR",JOptionPane.ERROR_MESSAGE);

            }
            else
            {
                if(gestor.capacitadorConfirmado())
                {
                    JOptionPane.showMessageDialog(this.getParent(),"Contacto Responsable de Empresa Registrado correctamente","Contacto Responsable Registrado",JOptionPane.INFORMATION_MESSAGE);
                    vaciarCampos();
                    if(pantallaCUSolicitante !=null)
                    {
                       pantallaCUSolicitante.actualizar(1, true);
                        this.dispose();
                    }
                }
                else
                {
                   JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante el registro del nuevo Contacto Responsable de empresa","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
    }//GEN-LAST:event_btnConfirmarActionPerformed
    }
    private void vaciarCampos()
    {
        
        txtApellido.setText("");
        txtCUIL.setText("");
        txtEmail.setText("");        
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCargo.setText("");
        //tablaTelefonos.setModel(new DefaultTableModel()); ERROR
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        //((DefaultTableModel)tablaCapacitaciones.getModel()).setNumRows(0);
        
        
        
        listaNroTel=new ArrayList<String>();
        listaTipoTel=new ArrayList<Tupla>();
        
    }
        private boolean ValidarDatos()
    {
        
        //Validar nro doc unico :TODO:  EN GESTOR
        //Validar cuil doc unico :TODO:  EN GESTOR
        //validar legajo unico :TODO: EN GESTOR
        //validar campos not null: TODO: EN PANTALLA
        boolean ban=true;
            
           
        if(txtCUIL.getText().equals(""))
        {JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'CUIL'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
         ban=false;
         return ban;
        }
        else
        {
            if(!gestor.ValidarCuil(txtCUIL.getText()))
            {ban=false;
             JOptionPane.showMessageDialog(this.getParent(),"El numero de cuil ingresado ya existe para un contacto registrado","ERROR",JOptionPane.ERROR_MESSAGE);
         return ban;}

        }
        /////////////////////////
        if(txtNombre.getText().equals(""))
        {
                JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Nombre'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                ban=false;
                return ban;
        }
        if(txtApellido.getText().equals(""))
        {
                 JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Apellido'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                 ban=false;
                 return ban;
         }
        if (pantallaCUSolicitante== null)
        {
        if(cmbPlantas.getSelectedIndex()==-1)
        {
                 JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionarse una Planta","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                 ban=false;
                 return ban;
         }
        if(cmbEmpresas.getSelectedIndex()==-1)//Al pedo... pero nunca se sabe...
        {
                 JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionarse una Empresa","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                 ban=false;
                 return ban;
         }

        }

        return ban;
        
    }
    private void cmbTiposTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTiposTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTiposTelefonoActionPerformed

    private void txtCUILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCUILActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCUILActionPerformed
    

    private void btnQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarTelefonoActionPerformed
     quitarTelefono();
    }//GEN-LAST:event_btnQuitarTelefonoActionPerformed
    private void quitarTelefono()
    {   if((tablaTelefonos.getSelectedRowCount())==1)
        {   
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        modelo.removeRow(tablaTelefonos.getSelectedRow());
        }
    }
    private void emAgregarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emAgregarTelefonoActionPerformed
        agregarTelefonoTabla((Tupla)cmbTiposTelefono.getSelectedItem(), txtTelefono.getText());
    }//GEN-LAST:event_emAgregarTelefonoActionPerformed

    private void emQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarTelefonoActionPerformed
       quitarTelefono();
    }//GEN-LAST:event_emQuitarTelefonoActionPerformed

    private void cmbEmpresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpresasActionPerformed
        mostrarPlantas();
    }//GEN-LAST:event_cmbEmpresasActionPerformed
 
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaRegistrarContactoResponsable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarTelefono;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnQuitarTelefono;
    private javax.swing.JComboBox cmbEmpresas;
    private javax.swing.JComboBox cmbPlantas;
    private javax.swing.JComboBox cmbTiposTelefono;
    private javax.swing.JMenuItem emAgregarTelefono;
    private javax.swing.JMenuItem emQuitarTelefono;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCUIL;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables


    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Ingrese los datos del nuevo contacto de planta a cargar.";
    }

    public String getTituloAyuda() {
        return "Opción: Agruegar Nuevo Contacto Responsable De Empresa";
    }
}
