/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaModificarEmpresaCliente.java
 *
 * Created on 17/08/2010, 20:00:59
 */

package vista.comer;

import controlador.comer.GestorABMEmpresaCliente;
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
import vista.interfaces.IPantallaEmpresaClienteABM;

/**
 *
 * @author Emmanuel
 */
public class pantallaModificarEmpresaCliente extends javax.swing.JInternalFrame implements IAyuda, IPantallaEmpresaClienteABM, ICallBack{
    private GestorABMEmpresaCliente gestor;
    private DefaultTableModel moldeTabla;
    private pantallaBuscarEmpresaCliente pBuscar=null;
    private int idEmpresa;
    private ArrayList<Integer> telsBorrados;
    private ArrayList<Integer> plantasBorradas;
    /** Creates new form pantallaModificarEmpresaCliente */

    pantallaModificarEmpresaCliente(pantallaBuscarEmpresaCliente p) {
        initComponents();
        this.pBuscar = p;
        this.idEmpresa = p.getIdEmpresaClienteSeleccionado();
        this.gestor = new GestorABMEmpresaCliente(this);
        mostrarTiposTelefono();
        cargarDatosEmpresaClienteAModificar();
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
        if(txtPiso.getText().equals("")){
            mensaje+="- Piso\n";
            ban=false;
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
        if(!ban){
            JOptionPane.showMessageDialog(this.getParent(),mensaje,"ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
        }
        return ban;
    }

    private void quitarTelefono()
    {   if((tablaTelefonos.getSelectedRowCount())==1)
        {
            DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
            Tupla t = (Tupla)tablaTelefonos.getValueAt(tablaTelefonos.getSelectedRow(), 0);
            modelo.removeRow(tablaTelefonos.getSelectedRow());
            this.gestor.borrarTelefono(t.getId());
        }
    }

    private ArrayList<NTupla> cargarTelefonos(){
        DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        Iterator it = modelo.getDataVector().iterator();

        ArrayList<NTupla> listaTelefonos = new ArrayList<NTupla>();
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

//    public void telefonoAgregado(){
//        ArrayList<NTupla> tels = gestor.getTelefonos();
//        for(NTupla nt : tels){
//            DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
//            Object[] item = new Object[2];
//            item[0] = new Tupla(nt.getId(),nt.getNombre());// GUARDO EL INDICE DEL ARRAYLIST DEL GESTOR DE LAS PLANTAS
//            item[1] = (String)nt.getData();
//            modelo.addRow(item);
//        }
//    }

    public void plantaAgregada(){
        btnNuevaEmpresa.setEnabled(true);
        ArrayList<NTupla> pls = gestor.getPlantas();
        for(NTupla nt : pls){
            DefaultTableModel modelo = (DefaultTableModel) tablaPlantas.getModel();
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
    
    public void mostrarEstadoEmpresa(String estado) {
        this.txtEstado.setText(estado);
    }

    public void mostrarRZEmpresa(String razonSocial) {
        this.txtRazonSocial.setText(razonSocial);
    }

    public void mostrarCUITEmpresa(String cuit) {
        this.txtCuit.setText(cuit);
    }

    public void mostrarEmailEmpresa(String email) {
        this.txtEmail.setText(email);
    }

    public void mostrarPaginaWebEmpresa(String paginaWeb) {
        this.txtPaginaWeb.setText(paginaWeb);
    }

    public void mostrarDomicilioEmpresa(String calle, String nro, String piso, String dpto, String cp) {
       this.txtCalle.setText(calle);
       this.txtAltura.setText(nro);
       this.txtPiso.setText(piso);
       this.txtDpto.setText(dpto);
       this.txtCP.setText(cp);
    }

    public void mostrarBarrioEmpresa(Tupla barrio) {
        mostrarBarrios();
        Tupla t=null;
        for(int i=0;i<cmbBarrio.getModel().getSize();i++){
            t = (Tupla)cmbBarrio.getModel().getElementAt(i);
            if(barrio.getId()==t.getId()){
                cmbBarrio.setSelectedItem(t);
                break;
            }
        }
    }

    public void mostrarLocalidadEmpresa(Tupla localidad) {
        mostrarLocalidades();
        Tupla t=null;
        for(int i=0;i<cmbLocalidades.getModel().getSize();i++){
            t = (Tupla)cmbLocalidades.getModel().getElementAt(i);
            if(localidad.getId()==t.getId()){
                cmbLocalidades.setSelectedItem(t);
                break;
            }
        }
    }

    public void mostrarProvinciaEmpresa(Tupla provincia) {
        mostrarProvincias();
        Tupla t=null;
        for(int i=0;i<cmbProvincias.getModel().getSize();i++){
            t = (Tupla)cmbProvincias.getModel().getElementAt(i);
            if(provincia.getId()==t.getId()){
                cmbProvincias.setSelectedItem(t);
                break;
            }
        }
    }

    public void mostrarPaisEmpresa(Tupla pais) {
        mostrarPaises();
        Tupla t=null;
        for(int i=0;i<cmbPais.getModel().getSize();i++){
            t = (Tupla)cmbPais.getModel().getElementAt(i);
            if(pais.getId()==t.getId()){
                cmbPais.setSelectedItem(t);
                break;
            }
        }
    }

    public void mostrarDatosTelefono(ArrayList<NTupla> listaTelefonos) {
        formatearTablaTelefonos();
        DefaultTableModel dtm = (DefaultTableModel)this.tablaTelefonos.getModel();
        Iterator it = listaTelefonos.iterator();
        NTupla nt = null;
        Tupla t = null;
        while(it.hasNext()){
            nt = (NTupla)it.next();
            Tupla tu = new Tupla(nt.getId(),nt.getNombre());
            this.agregarTelefonoTabla(tu, (String)nt.getData());
        }
    }

    public void mostrarDatosPlantas(ArrayList<NTupla> listaPlantas) {
        DefaultTableModel tm = (DefaultTableModel)this.tablaPlantas.getModel();
        for (NTupla planta : listaPlantas) {
            Object[] item = new Object[2];
            item[0] = planta;
            item[1] = (String)planta.getData();
            tm.addRow(item);
        }
    }

    public void actualizar(int flag, boolean exito) {
        plantaAgregada();
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
        jLabel1 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtCuit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPaginaWeb = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        txtNumeroTelefono = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbTipoTelefono = new javax.swing.JComboBox();
        btnNuevoTelefono = new javax.swing.JButton();
        btnQuitarTelefono = new javax.swing.JButton();
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
        btnNuevaEmpresa = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setTitle("Modificar Empresa Cliente");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Empresa"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Razon Social: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Email:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("CUIT");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Página Web:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Estado:");

        txtEstado.setEditable(false);

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
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaginaWeb, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCuit, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaginaWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Números de Telefono"));

        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaTelefonos);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
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
                    .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(cmbTipoTelefono, javax.swing.GroupLayout.Alignment.TRAILING, 0, 158, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnQuitarTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Domicilio"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Calle: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Nº:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel10.setText("Piso:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Dpto:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("CP:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Pais: ");

        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel14.setText("Provincia:");

        btnAgregarProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProvinciaActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11));
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

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11));
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
                        .addComponent(txtCP, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProvincias, 0, 134, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarProvincia))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarLocalidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbBarrio, 0, 110, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarBarrio)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
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
                        .addComponent(jLabel14)
                        .addComponent(cmbProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(cmbLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarLocalidad)
                    .addComponent(jLabel16)
                    .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarBarrio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                "Razón Social", "Dirección"
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNuevaPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrarPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnNuevaPlanta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrarPlanta))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnNuevaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnNuevaEmpresa.setText("Aceptar");
        btnNuevaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaEmpresaActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(294, Short.MAX_VALUE)
                .addComponent(btnNuevaEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevaEmpresa))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            //gestor.telefono(this.cargarTelefonos());
            gestor.paginaWeb(this.txtPaginaWeb.getText());
            int id = gestor.confirmacionModificacionRegistro(this.idEmpresa);
            JOptionPane.showMessageDialog(this.getParent(),"Se registro con éxito la nueva Empresa.\n Número de Empresa: "+id,"Registración Exitosa",JOptionPane.INFORMATION_MESSAGE);
            if(pBuscar!=null){
                pBuscar.actualizar(id, true);
            }
            this.dispose();
        }
}//GEN-LAST:event_btnNuevaEmpresaActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        if(!txtNumeroTelefono.getText().isEmpty()) {
            agregarTelefonoTabla((Tupla)cmbTipoTelefono.getSelectedItem(), txtNumeroTelefono.getText());
        }
        txtNumeroTelefono.setText(""); // lo vacio para q cargue otro
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarTelefonoActionPerformed
        this.quitarTelefono();
}//GEN-LAST:event_btnQuitarTelefonoActionPerformed

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed
        cmbProvincias.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbBarrio.setModel(new DefaultComboBoxModel());
        mostrarProvincias();
}//GEN-LAST:event_cmbPaisActionPerformed

    private void btnAgregarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProvinciaActionPerformed
        SwingPanel.getInstance().mensajeEnConstruccion();
}//GEN-LAST:event_btnAgregarProvinciaActionPerformed

    private void cmbLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLocalidadesActionPerformed
        cmbBarrio.setModel(new DefaultComboBoxModel());
        mostrarBarrios();
}//GEN-LAST:event_cmbLocalidadesActionPerformed

    private void btnAgregarLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarLocalidadActionPerformed
        SwingPanel.getInstance().mensajeEnConstruccion();
}//GEN-LAST:event_btnAgregarLocalidadActionPerformed

    private void btnAgregarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarBarrioActionPerformed
        SwingPanel.getInstance().mensajeEnConstruccion();
}//GEN-LAST:event_btnAgregarBarrioActionPerformed

    private void cmbProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciasActionPerformed
        cmbBarrio.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        mostrarLocalidades();
}//GEN-LAST:event_cmbProvinciasActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevaPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPlantaActionPerformed
        gestor.registrarNuevaPlanta(txtRazonSocial.getText());
    }//GEN-LAST:event_btnNuevaPlantaActionPerformed

    private void btnBorrarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarPlantaActionPerformed
        // TODO add your handling code here:
        Tupla fila = null;
        if((tablaPlantas.getSelectedRowCount())==1) {
            DefaultTableModel modelo = (DefaultTableModel) tablaPlantas.getModel();
            modelo.removeRow(tablaPlantas.getSelectedRow());
            fila = (Tupla)modelo.getValueAt(tablaPlantas.getSelectedRow(),0);
            gestor.borrarPlanta(fila.getId());
        }
}//GEN-LAST:event_btnBorrarPlantaActionPerformed

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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaPlantas;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtAltura;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCuit;
    private javax.swing.JTextField txtDpto;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNumeroTelefono;
    private javax.swing.JTextField txtPaginaWeb;
    private javax.swing.JTextField txtPiso;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables

    private void cargarDatosEmpresaClienteAModificar() {
        gestor.seleccionEmpresaCliente(this.idEmpresa);
    }

    public String getTituloAyuda() {
        return "Modificar Datos Empresa Cliente";
    }

    public String getResumenAyuda() {
        return  "Esta ventana le permitirá modificar los datos de la empresa cliente y sus plantas asociadas";
    }

    public int getIdAyuda() {
        return 0;
    }
}
