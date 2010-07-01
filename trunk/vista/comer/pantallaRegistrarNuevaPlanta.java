/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarNuevaPlanta.java
 *
 * Created on 10-may-2010, 17:41:26
 */

package vista.comer;

import controlador.comer.GestorRegistrarNuevaEmpresaCliente;
import controlador.comer.GestorRegistrarNuevaPlanta;
import controlador.comer.GestorRegistrarPedido;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.NTupla;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack;

/**
 *
 * @author Administrador
 */
public class pantallaRegistrarNuevaPlanta extends javax.swing.JInternalFrame implements IAyuda, ICallBack {

    GestorRegistrarNuevaPlanta gestor;
    GestorRegistrarNuevaEmpresaCliente gestorEmpresaCliente;
    private GestorRegistrarPedido gestorRegistrarPedido;

    /** Creates new form frmRegistrarNuevaPlanta */
    public pantallaRegistrarNuevaPlanta() {
        initComponents();

        gestor = new GestorRegistrarNuevaPlanta(this);

        habilitarVentana();

    }

    /**
     * VIENE DEL INCLUDE DE REGISTRAR EMPRESA CLIENTE
     */
    public pantallaRegistrarNuevaPlanta(GestorRegistrarNuevaEmpresaCliente gestor,String Empresa)
    {
        initComponents();
        this.gestor = new GestorRegistrarNuevaPlanta(this);
        habilitarVentana();

        // VAMOS POR EL INCLUDE
        this.gestorEmpresaCliente = gestor;
        // LA EMPRESA YA ESTÁ ELEGIDA
        cmbEmpresa.setSelectedItem(Empresa);
        cmbEmpresa.setEnabled(false);
        // No puedo cancelar
        btnCancelar.setEnabled(false);
        this.setClosable(false);
        
    }

    public pantallaRegistrarNuevaPlanta(GestorRegistrarPedido aThis, Tupla empresa) {
        initComponents();
        this.gestor = new GestorRegistrarNuevaPlanta(this);
        habilitarVentana();

        // VAMOS POR EL INCLUDE
        this.gestorRegistrarPedido = aThis;
        // LA EMPRESA YA ESTÁ ELEGIDA
        cmbEmpresa.setSelectedItem(empresa);
        cmbEmpresa.setEnabled(false);
        // No puedo cancelar
        btnCancelar.setEnabled(false);
        this.setClosable(false);
    }

    private void habilitarVentana()
    {
        txtPiso.setValue(new Integer(0));
        txtAltura.setValue(new Integer(100));
        mostrarEmpresasCliente();
        mostrarPaises();
        formatearTablaTelefonos();
        mostrarTiposTelefono();
        mostrarContactos();
    }

    private void mostrarContactos()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestor.mostrarContactos();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbContactos.setModel(valores);
    }

    private void mostrarTiposTelefono()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestor.mostrarTiposTelefono();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbTipoTelefono.setModel(valores);
    }

    private void formatearTablaTelefonos()
    {
        Object[] nombreColumnas = {"Tipo","Número"};
        DefaultTableModel modelo = new DefaultTableModel();
        for (int i = 0; i < nombreColumnas.length; i++)
        {
           modelo.addColumn(nombreColumnas[i]);
        }
        tablaTelefonos.setModel(modelo);
    }

    private void mostrarPaises()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        ArrayList<Tupla> lista = gestor.mostrarPaises();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbPais.setModel(valores);
    }

    private void mostrarProvincias()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbPais.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarProvincias(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbProvincias.setModel(valores);
    }

    private void mostrarLocalidades()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbProvincias.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarLocalidades(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbLocalidades.setModel(valores);
    }

    private void mostrarBarrios()
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbLocalidades.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarBarrios(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbBarrio.setModel(valores);
    }

    private void mostrarEmpresasCliente()
    {
        ArrayList<Tupla> lista = gestor.mostrarEmpresasCliente();

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbEmpresa.setModel(valores);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuTablaTelefonos = new javax.swing.JPopupMenu();
        menuQuitar = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtNombrePlanta = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCodPostal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cmbProvincias = new javax.swing.JComboBox();
        btnNuevaProvincia = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmbLocalidades = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cmbBarrio = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDepto = new javax.swing.JTextField();
        txtPiso = new javax.swing.JFormattedTextField();
        txtAltura = new javax.swing.JFormattedTextField();
        btnCancelar = new javax.swing.JButton();
        btnNuevaPlanta = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnNuevoContacto = new javax.swing.JButton();
        cmbContactos = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        txtNumeroTelefono = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbTipoTelefono = new javax.swing.JComboBox();
        btnNuevoTelefono = new javax.swing.JButton();

        menuQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        menuQuitar.setText("Quitar");
        menuQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuitarActionPerformed(evt);
            }
        });
        menuTablaTelefonos.add(menuQuitar);

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar Nueva Planta");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Empresa: ");

        cmbEmpresa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cargue una Empresa..." }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Nombre:  ");

        txtNombrePlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePlantaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Domicilio"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Calle:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Código Postal:");

        txtCodPostal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPostalActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("País: ");

        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Provincia:");

        cmbProvincias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un Pais..." }));
        cmbProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciasActionPerformed(evt);
            }
        });

        btnNuevaProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevaProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaProvinciaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Localidad: ");

        cmbLocalidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione una Provincia..." }));
        cmbLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLocalidadesActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Barrio:");

        cmbBarrio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione una Localidad..." }));
        cmbBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBarrioActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel10.setText("Altura:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Piso:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Depto.:");

        txtDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeptoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbProvincias, 0, 285, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevaProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbBarrio, 0, 285, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbLocalidades, 0, 285, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbPais, 0, 319, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCalle, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                    .addComponent(txtPiso, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtAltura, 0, 0, Short.MAX_VALUE))))
                            .addComponent(txtCodPostal, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbPais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbProvincias, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addComponent(btnNuevaProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbLocalidades)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(cmbBarrio)
                    .addComponent(jButton5, 0, 0, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnNuevaPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnNuevaPlanta.setText("Aceptar");
        btnNuevaPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaPlantaActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto de la Empresa"));

        btnNuevoContacto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevoContacto.setText("Agregar Contacto");
        btnNuevoContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoContactoActionPerformed(evt);
            }
        });

        cmbContactos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cargue un Contacto..." }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbContactos, 0, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevoContacto)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoContacto)
                    .addComponent(cmbContactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Números de Telefono"));

        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaTelefonos.setComponentPopupMenu(menuTablaTelefonos);
        tablaTelefonos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTelefonosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaTelefonos);

        txtNumeroTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelefonoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Tipo y Número de Teléfono :");

        btnNuevoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnNuevoTelefono.setText("Agregar");
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(cmbTipoTelefono, 0, 161, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoTelefono))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevaPlanta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombrePlanta)
                            .addComponent(cmbEmpresa, 0, 336, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombrePlanta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevaPlanta))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        SwingPanel.getInstance().mensajeEnConstruccion();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLocalidadesActionPerformed

        mostrarBarrios();

    }//GEN-LAST:event_cmbLocalidadesActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCodPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPostalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodPostalActionPerformed

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed

        // Selecciono un Pais, Muestro sus Provincias
        mostrarProvincias();

    }//GEN-LAST:event_cmbPaisActionPerformed

    private void cmbProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciasActionPerformed

        mostrarLocalidades();

    }//GEN-LAST:event_cmbProvinciasActionPerformed

    private void btnNuevaProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaProvinciaActionPerformed

       SwingPanel.getInstance().mensajeEnConstruccion();
        

    }//GEN-LAST:event_btnNuevaProvinciaActionPerformed

    private void cmbBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBarrioActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        SwingPanel.getInstance().mensajeEnConstruccion();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptoActionPerformed

    private void btnNuevoContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoContactoActionPerformed

       // pantallaRegistrarContactoResponsable rcr = new pantallaRegistrarContactoResponsable(this);
       // SwingPanel.getInstance().addWindow(rcr);
       // rcr.setVisible(true);
        //SwingPanel.getInstance().mensajeEnConstruccion();

    }//GEN-LAST:event_btnNuevoContactoActionPerformed

    private HashSet<NTupla> cargarTelefonos()
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Iterator it = modelo.getDataVector().iterator();
        
        HashSet<NTupla> listaTelefonos = new HashSet<NTupla>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();

            System.out.println("HOLA");
            Tupla tipo = (Tupla)fila.get(0);

            NTupla nt = new NTupla();
            nt.setNombre((String)fila.get(1));
            
            Tupla ttt = new Tupla();
            ttt.setId(tipo.getId());
            ttt.setNombre(tipo.getNombre());

            nt.setData(ttt);

            listaTelefonos.add(nt);
        }
        return listaTelefonos;
    }

    private void btnNuevaPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPlantaActionPerformed

        boolean validado = true;

        if(txtNombrePlanta.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getParent(),"Por favor ingrese el Nombre de la Planta","Faltan Datos",JOptionPane.INFORMATION_MESSAGE);
            validado = false;
        }

        if(txtCalle.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getParent(),"Por favor ingrese la Calle de la Planta","Faltan Datos",JOptionPane.INFORMATION_MESSAGE);
            validado = false;
        }

        if(txtCodPostal.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getParent(),"Por favor ingrese el Código Postal","Faltan Datos",JOptionPane.INFORMATION_MESSAGE);
            validado = false;
        }

        if(validado==true)
        {
            // PASO EL NOMBRE
            gestor.nombrePlanta(txtNombrePlanta.getText());

            // PASO LOS TELEFONOS
            gestor.telefonoPlanta(cargarTelefonos());

            // PASO LOS DATOS DEL DOMICILIO
            gestor.DomicilioPlanta(txtCalle.getText(),Integer.parseInt(txtAltura.getText()),Integer.parseInt(txtPiso.getText()),txtDepto.getText(),txtCodPostal.getText());

            // PASO LA GEOLOCALIZACION ;)
            // MIERDA AL PEDO !!! SOLO NECESITO EL BARRIO
            Tupla aux;
            aux = (Tupla)cmbPais.getSelectedItem();
            gestor.paisPlanta(aux.getId());
            aux = (Tupla)cmbProvincias.getSelectedItem();
            gestor.provinciaPlanta(aux.getId());
            aux = (Tupla)cmbLocalidades.getSelectedItem();
            gestor.LocalidadPlanta(aux.getId());
            aux = (Tupla)cmbBarrio.getSelectedItem();
            gestor.barrioPlanta(aux.getId());

            // SI ES NULO VENGO DESDE EL ACTOR
            if(gestorEmpresaCliente == null && gestorRegistrarPedido == null)
            {
                // YA TENGO LA PLANTA !! ... corcholis ... y ahora?
                gestor.empresaCliente((Tupla)cmbEmpresa.getSelectedItem());
                //TODO: PASO LA EMPRESA
                int id = gestor.PlantaConfirmada();
                JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito la nueva Planta\n Número de Planta: "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
            // SI NO ES NULO, VENGO DEL INCLUDE DE REGISTRAR EMPRESA CLIENTE O DEL EXTEND REGISTRAR PEDIDO
            else
            {
                if(gestorEmpresaCliente != null){
                    // MANDO A GUARDAR LA PLANTA
                    int id = gestor.PlantaConfirmada();
                    gestorEmpresaCliente.setNuevaPlanta(id);
                    JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito la nueva Planta\n Número de Planta: "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
                else
                {
                    gestor.empresaCliente((Tupla)cmbEmpresa.getSelectedItem());
                    int id = gestor.PlantaConfirmada();
                    gestorRegistrarPedido.setNuevaPlanta(id);
                    JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito la nueva Planta\n Número de Planta: "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_btnNuevaPlantaActionPerformed

    private void txtNumeroTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelefonoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtNumeroTelefonoActionPerformed

    private void agregarTelefonoTabla(Tupla tipo, String numero)
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Object[] item = new Object[2];
        item[0] = tipo;
        item[1] = numero;
        modelo.addRow(item);
    }

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed

        if(!txtNumeroTelefono.getText().isEmpty()) {
            agregarTelefonoTabla((Tupla)cmbTipoTelefono.getSelectedItem(), txtNumeroTelefono.getText());
        }
        txtNumeroTelefono.setText(""); // lo vacio para q cargue otro
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void txtNombrePlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePlantaActionPerformed

    private void tablaTelefonosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTelefonosMouseClicked
    }//GEN-LAST:event_tablaTelefonosMouseClicked

    private void menuQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuitarActionPerformed

        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        modelo.removeRow(tablaTelefonos.getSelectedRow());

    }//GEN-LAST:event_menuQuitarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNuevaPlanta;
    private javax.swing.JButton btnNuevaProvincia;
    private javax.swing.JButton btnNuevoContacto;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbContactos;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbLocalidades;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JComboBox cmbProvincias;
    private javax.swing.JComboBox cmbTipoTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem menuQuitar;
    private javax.swing.JPopupMenu menuTablaTelefonos;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JFormattedTextField txtAltura;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCodPostal;
    private javax.swing.JTextField txtDepto;
    private javax.swing.JTextField txtNombrePlanta;
    private javax.swing.JTextField txtNumeroTelefono;
    private javax.swing.JFormattedTextField txtPiso;
    // End of variables declaration//GEN-END:variables

    // ----------------------------------------------------------------------
    //                              AYUDA
    // ----------------------------------------------------------------------
    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Ingrese los datos de la nueva planta a cargar. También puede asignar la persona que será contacto";
    }

    public String getTituloAyuda() {
        return "Opción: Nueva Planta";
    }

    public void actualizar(int flag,boolean exito) {

        // 1: VIENE DEL UC Registrar Contacto Responsable
        switch(flag)
        {
            case 1:
                if(exito==true)
                {
                    mostrarContactos();
                }
                else
                {
                    JOptionPane.showMessageDialog(this.getParent(),"No se registro correctamente el Contacto Responsable","Error",JOptionPane.INFORMATION_MESSAGE);
                }
                break;
        }
    }

    // ----------------------------------------------------------------------




}
