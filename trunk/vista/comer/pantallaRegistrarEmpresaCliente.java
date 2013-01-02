/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarEmpresaCliente.java
 *
 * Created on 10/05/2010, 09:51:46
 */

package vista.comer;

import controlador.comer.GestorABMEmpresaCliente;
import controlador.comer.GestorABMPedido;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Barrio;
import modelo.Localidad;
import modelo.Provincia;
import util.ComboUtil;
import util.NTupla;
import util.SwingPanel;
import util.TablaUtil;
import util.Tupla;
import vista.abms.ABMDomicilio;
import vista.interfaces.IAyuda;
import vista.interfaces.ICallBack_v2;
import vista.interfaces.IPantallaEmpresaClienteABM;

/**
 *
 * @author iuga
 */
public class pantallaRegistrarEmpresaCliente extends javax.swing.JInternalFrame  implements IAyuda, IPantallaEmpresaClienteABM, ICallBack_v2{
    private GestorABMEmpresaCliente gestor;
    private DefaultTableModel moldeTabla;
    private pantallaBuscarEmpresaCliente pBuscar=null;
    private GestorABMPedido grp;
    
    private int idPaisSeleccionado;
    private int idProvinciaSeleccionada;
    private int idLocalidadSeleccionada;
    private int idBarrioSeleccionado;

    /** Creates new form frmRegistrarEmpresaCliente */
    public pantallaRegistrarEmpresaCliente() {
        gestor = new GestorABMEmpresaCliente(this);
        initComponents();
        habilitarVentana();
        formatearTablaTelefonos();
        grp = null;
        mostrarTiposTelefono();
    }

    public pantallaRegistrarEmpresaCliente(pantallaBuscarEmpresaCliente p) {
        gestor = new GestorABMEmpresaCliente(this);
        this.pBuscar = p;
        initComponents();
        habilitarVentana();
    }


    public pantallaRegistrarEmpresaCliente(GestorABMPedido aThis) {
        gestor = new GestorABMEmpresaCliente(this);
        initComponents();
        habilitarVentana();
        this.grp = aThis;
    }

    private void habilitarVentana(){
        txtCuit.setBackground(Color.white);
        mostrarPaises();
        formatearTablaTelefonos();
        grp = null;
        mostrarTiposTelefono();
    }

    private void mostrarTiposTelefono(){
        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestor.mostrarTiposTelefono();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbTipoTelefono.setModel(valores);
    }

    @Override
    public void plantaAgregada(){
        btnNuevaEmpresa.setEnabled(true);
        DefaultTableModel modelo = (DefaultTableModel) tablaPlantas.getModel();
        TablaUtil.vaciarDefaultTableModel(modelo);
        ArrayList<NTupla> pls = gestor.getPlantas();
        for(NTupla nt : pls){
            Object[] item = new Object[2];
            item[0] = new Tupla(nt.getId(),nt.getNombre());// GUARDO EL INDICE DEL ARRAYLIST DEL GESTOR DE LAS PLANTAS
            item[1] = (String)nt.getData();
            modelo.addRow(item);
        }
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

        ArrayList<Tupla> lista = gestor.mostrarNombrePaises();
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
        ArrayList<Tupla> lista = gestor.mostrarProvincias(t);
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
        ArrayList<Tupla> lista = gestor.mostrarLocalidades(t);
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
        ArrayList<Tupla> lista = gestor.mostrarBarrios(t);
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbBarrio.setModel(valores);
    }

    private void agregarTelefonoTabla(Tupla tipo, String numero){
        int indice = gestor.agregarTelefono(tipo,numero);
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Object[] item = new Object[2];
        item[0] = new Tupla(indice,tipo.getNombre());
        item[1] = numero;
        modelo.addRow(item);
    }

    private HashSet<NTupla> cargarTelefonos()
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Iterator it = modelo.getDataVector().iterator();

        HashSet<NTupla> listaTelefonos = new HashSet<NTupla>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();

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

    private boolean ValidarDatos()
    {
        boolean ban=true;
        String mensaje="Faltan ingresar/seleccionar los siguientes campos:\n";
        if(txtRazonSocial.getText().equals("")){
            mensaje+="- Razón Social\n";
            ban=false;
        }
        if(txtCuit.getText().equals("")){
            mensaje+="- CUIT\n";
            ban=false;
        }
        if(txtEmail.getText().equals("")){
            mensaje+="- Email\n";
            ban=false;
        }
        if(txtPaginaWeb.getText().equals("")){
            mensaje+="- Página Web\n";
            ban=false;
        }
        if(tablaTelefonos.getRowCount() == 0){
            mensaje+="- Un teléfono al menos\n";
            ban=false;
        }
        if(txtCalle.getText().equals("")){
            mensaje+="- Calle\n";
            ban=false;
        }
        if(txtAltura.getText().equals("")){
            mensaje+="- Altura\n";
            ban=false;
        }
        try{
            Integer.parseInt(txtAltura.getText());
        }
        catch(NumberFormatException e){
            mensaje+="- Altura (debe ser un número válido)\n";
        }
        if(txtPiso.getText().equals("")){
            mensaje+="- Piso\n";
            ban=false;
        }
        try{
            Integer.parseInt(txtPiso.getText());
        }
        catch(NumberFormatException e){
            mensaje+="- Piso (debe ser un número válido)\n";
        }
        if(txtPaginaWeb.getText().equals("")){
            mensaje+="- Departamento\n";
            ban=false;
        }
        if(cmbBarrio.getSelectedIndex() == -1){
            mensaje+="- Barrio\n";
            ban=false;
        }
        if(cmbLocalidades.getSelectedIndex() == -1){
            mensaje+="- Localidad\n";
            ban=false;
        }
        if(cmbProvincias.getSelectedIndex() == -1){
            mensaje+="- Provincia\n";
            ban=false;
        }
        if(cmbPais.getSelectedIndex() == -1){
            mensaje+="- Pais\n";
            ban=false;
        }
        if(gestor.validarExistenciaCUIT(txtCuit.getText())){
            mensaje+="- CUIT duplicado\n";
            ban = false;
        }
        if(!ban){
            JOptionPane.showMessageDialog(this.getParent(),mensaje,"ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        }
        return ban;
    }

    private void quitarTelefono()
    {   if((tablaTelefonos.getSelectedRowCount())==1)
        {
            DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
            modelo.removeRow(tablaTelefonos.getSelectedRow());
            Tupla t = (Tupla)tablaTelefonos.getValueAt(tablaTelefonos.getSelectedRow(), 0);
            this.gestor.borrarTelefono(t.getId());
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

        btnCancelar = new javax.swing.JButton();
        btnNuevaEmpresa = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtCuit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPaginaWeb = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnValidarCUIT = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAltura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPiso = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDpto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCP = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        btnAgregarProvincia = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cmbLocalidades = new javax.swing.JComboBox();
        btnAgregarLocalidad = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cmbBarrio = new javax.swing.JComboBox();
        btnAgregarBarrio = new javax.swing.JButton();
        cmbProvincias = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnNuevaPlanta = new javax.swing.JButton();
        btnBorrarPlanta = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPlantas = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        txtNumeroTelefono = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbTipoTelefono = new javax.swing.JComboBox();
        btnNuevoTelefono = new javax.swing.JButton();
        btnQuitarTelefono = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Registrar Nueva Empresa Cliente");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnNuevaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        btnNuevaEmpresa.setText("Guardar");
        btnNuevaEmpresa.setEnabled(false);
        btnNuevaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaEmpresaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Empresa"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Razon Social: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Email:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("CUIT");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Página Web:");

        btnValidarCUIT.setText("Validar");
        btnValidarCUIT.setToolTipText("<html><br>Validar CUIT</br></html>");
        btnValidarCUIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarCUITActionPerformed(evt);
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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonSocial))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaginaWeb))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCuit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnValidarCUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnValidarCUIT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaginaWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Domicilio"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Calle: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Nº:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Piso:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Dpto:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("CP:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Pais: ");

        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Provincia:");

        btnAgregarProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProvinciaActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Localidad: ");

        cmbLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLocalidadesActionPerformed(evt);
            }
        });

        btnAgregarLocalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarLocalidadActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Barrio:");

        btnAgregarBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarBarrioActionPerformed(evt);
            }
        });

        cmbProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDpto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCP, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProvincias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarProvincia))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbBarrio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarBarrio)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDpto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addComponent(btnAgregarProvincia)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(cmbLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarLocalidad)
                    .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarBarrio)
                    .addComponent(jLabel16)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Plantas"));

        btnNuevaPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevaPlanta.setText("Agregar Planta");
        btnNuevaPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaPlantaActionPerformed(evt);
            }
        });

        btnBorrarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnBorrarPlanta.setText("Quitar Planta");
        btnBorrarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarPlantaActionPerformed(evt);
            }
        });

        tablaPlantas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Razón Social", "CUIT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablaPlantas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNuevaPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrarPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevaPlanta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrarPlanta)
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("General", jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Números de Telefono"));

        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaTelefonos);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Tipo y Número de Teléfono :");

        btnNuevoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnNuevoTelefono.setText("Agregar");
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.Alignment.TRAILING, 0, 222, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnQuitarTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoTelefono)
                            .addComponent(btnQuitarTelefono)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Teléfonos", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevaEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevaEmpresa))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
    if(!txtNumeroTelefono.getText().isEmpty())
    {
        agregarTelefonoTabla((Tupla)cmbTipoTelefono.getSelectedItem(), txtNumeroTelefono.getText());
    }
    txtNumeroTelefono.setText(""); // lo vacio para q cargue otro
}//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed
        cmbProvincias.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbBarrio.setModel(new DefaultComboBoxModel());
        mostrarProvincias();
    }//GEN-LAST:event_cmbPaisActionPerformed

    private void cmbLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLocalidadesActionPerformed
        cmbBarrio.setModel(new DefaultComboBoxModel());
        mostrarBarrios();
    }//GEN-LAST:event_cmbLocalidadesActionPerformed

    private void btnNuevaPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPlantaActionPerformed
        gestor.registrarNuevaPlanta(txtRazonSocial.getText());
    }//GEN-LAST:event_btnNuevaPlantaActionPerformed

    private void btnAgregarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProvinciaActionPerformed
        this.actualizarIdSeleccionados();
        Tupla tupla = (Tupla) cmbPais.getSelectedItem();   
        if(tupla != null)
        {
            ABMDomicilio win = new ABMDomicilio(this, tupla.getId(), Provincia.class);
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }
        else
        {
            JOptionPane.showInternalMessageDialog(this, "Debe seleccionar un país", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarProvinciaActionPerformed

    private void btnAgregarLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarLocalidadActionPerformed
        this.actualizarIdSeleccionados();
        Tupla tupla = (Tupla) cmbProvincias.getSelectedItem();   
        if(tupla != null)
        {
            ABMDomicilio win = new ABMDomicilio(this, tupla.getId(), Localidad.class);
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }
        else
        {
            JOptionPane.showInternalMessageDialog(this, "Debe seleccionar una provincia", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }         
    }//GEN-LAST:event_btnAgregarLocalidadActionPerformed

    private void btnAgregarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarBarrioActionPerformed
        this.actualizarIdSeleccionados();
        Tupla tupla = (Tupla) cmbLocalidades.getSelectedItem();
        if(tupla != null)
        {
            ABMDomicilio win = new ABMDomicilio(this, tupla.getId(), Barrio.class);
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }
        else
        {
            JOptionPane.showInternalMessageDialog(this, "Debe seleccionar una localidad", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarBarrioActionPerformed

    private void cmbProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciasActionPerformed
        cmbBarrio.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        mostrarLocalidades();
    }//GEN-LAST:event_cmbProvinciasActionPerformed

    private void btnNuevaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaEmpresaActionPerformed
        if(ValidarDatos()){
            gestor.nombreEmpresa(txtRazonSocial.getText());
            gestor.CUIT(txtCuit.getText());
            gestor.EMail(txtEmail.getText());
            gestor.datosDomicilio(txtCalle.getText(), txtAltura.getText(), txtPiso.getText(), txtDpto.getText(), txtCP.getText());
            gestor.seleccionBarrio(((Tupla)cmbBarrio.getSelectedItem()).getId());
            gestor.seleccionLocalidad(((Tupla)cmbLocalidades.getSelectedItem()).getId());
            gestor.seleccionProvincia(((Tupla)cmbProvincias.getSelectedItem()).getId());
            gestor.seleccionPais(((Tupla)cmbPais.getSelectedItem()).getId());
//            gestor.telefono(this.cargarTelefonos());
            gestor.paginaWeb(this.txtPaginaWeb.getText());
            int id = gestor.confirmacionRegistro();
            JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito la nueva Empresa.\n Número de Empresa: "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(grp !=null){
                grp.recargarComboBox();
            }
            if(pBuscar!=null){
                pBuscar.actualizar(id, true);
            }
            this.dispose();
        }
    }//GEN-LAST:event_btnNuevaEmpresaActionPerformed

    private void btnQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarTelefonoActionPerformed
        this.quitarTelefono();
    }//GEN-LAST:event_btnQuitarTelefonoActionPerformed

    private void btnBorrarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarPlantaActionPerformed
        // TODO add your handling code here:
        Tupla fila = null;
        if((tablaPlantas.getSelectedRowCount())==1){
            DefaultTableModel modelo = (DefaultTableModel) tablaPlantas.getModel();
            modelo.removeRow(tablaPlantas.getSelectedRow());
            fila = (Tupla)modelo.getValueAt(tablaPlantas.getSelectedRow(),0);
            gestor.borrarPlanta(fila.getId());
        }
    }//GEN-LAST:event_btnBorrarPlantaActionPerformed

    private void btnValidarCUITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarCUITActionPerformed
        if(!txtCuit.getText().equals("")){
            if(gestor.validarExistenciaCUIT(txtCuit.getText())){
                JOptionPane.showMessageDialog(this.getParent(),"CUIT duplicado: "+txtCuit.getText(),"CUIT no válido",JOptionPane.WARNING_MESSAGE);
                txtCuit.setBackground(Color.red);
            }
            else{
                JOptionPane.showMessageDialog(this.getParent(),"No existe ninguna empresa con el cuit ingresado( "+txtCuit.getText()+")","CUIT Válido",JOptionPane.INFORMATION_MESSAGE);
                txtCuit.setBackground(Color.green);
            }
        }else{
            JOptionPane.showMessageDialog(this.getParent(),"Debe ingresar un número de cuit para validar ","Ingrese un número de CUIT Válido",JOptionPane.WARNING_MESSAGE);
            txtCuit.setBackground(Color.green);
        }
}//GEN-LAST:event_btnValidarCUITActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaRegistrarEmpresaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarBarrio;
    private javax.swing.JButton btnAgregarLocalidad;
    private javax.swing.JButton btnAgregarProvincia;
    private javax.swing.JButton btnBorrarPlanta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNuevaEmpresa;
    private javax.swing.JButton btnNuevaPlanta;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btnQuitarTelefono;
    private javax.swing.JButton btnValidarCUIT;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbLocalidades;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JComboBox cmbProvincias;
    private javax.swing.JComboBox cmbTipoTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaPlantas;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtAltura;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCuit;
    private javax.swing.JTextField txtDpto;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNumeroTelefono;
    private javax.swing.JTextField txtPaginaWeb;
    private javax.swing.JTextField txtPiso;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables

    public String getTituloAyuda() {
        return "Opción: Nueva Empresa Cliente";
    }

    public String getResumenAyuda() {
        return "Ingrese los datos de la nueva Empresa Cliente a cargar.";
    }

    public int getIdAyuda() {
        return 0;
    }

    public void mostrarEstadoEmpresa(String estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarRZEmpresa(String razonSocial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarCUITEmpresa(String cuit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarEmailEmpresa(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarPaginaWebEmpresa(String paginaWeb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarDomicilioEmpresa(String calle, String nro, String piso, String dpto, String cp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarBarrioEmpresa(Tupla barrio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarLocalidadEmpresa(Tupla localidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarProvinciaEmpresa(Tupla provincia) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarPaisEmpresa(Tupla pais) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarDatosTelefono(ArrayList<NTupla> listaTelefonos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarDatosPlantas(ArrayList<NTupla> listaPlantas) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizar(int id, String flag, boolean exito) {
        plantaAgregada();
        if(idPaisSeleccionado > -1) { ComboUtil.seleccionarEnCombo(cmbPais, idPaisSeleccionado);}
        if(idProvinciaSeleccionada > -1) { ComboUtil.seleccionarEnCombo(cmbProvincias, idProvinciaSeleccionada);}
        if(idLocalidadSeleccionada > -1) { ComboUtil.seleccionarEnCombo(cmbLocalidades, idLocalidadSeleccionada);}
        if(idBarrioSeleccionado > -1) { ComboUtil.seleccionarEnCombo(cmbBarrio, idBarrioSeleccionado);}
    }

    private void actualizarIdSeleccionados()
    {   
        Tupla tBarrio = (Tupla) cmbBarrio.getSelectedItem();
        if(tBarrio != null) { idBarrioSeleccionado = tBarrio.getId(); }
        Tupla tLocalidad = (Tupla) cmbLocalidades.getSelectedItem();
        if(tLocalidad != null) { idLocalidadSeleccionada = tLocalidad.getId(); }
        Tupla tProvincia = (Tupla) cmbProvincias.getSelectedItem();
        if(tProvincia != null) { idProvinciaSeleccionada = tProvincia.getId(); }
        Tupla tPais = (Tupla) cmbPais.getSelectedItem();
        if(tPais != null) { idPaisSeleccionado = tPais.getId(); }
    }
}
