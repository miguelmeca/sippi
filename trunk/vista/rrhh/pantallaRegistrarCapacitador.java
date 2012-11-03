/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmRegistrarEmpleado.java
 *
 * Created on 09-may-2010, 18:52:12
 */

package vista.rrhh;

import controlador.rrhh.GestorRegistrarNuevoCapacitador;
import controlador.rrhh.GestorModificarCapacitador;
import controlador.rrhh.IGestorCapacitador;
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
import com.toedter.calendar.JDateChooser;
import javax.swing.JComponent;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import util.LimitadorCaracteres;
import vista.interfaces.ICallBack;

/**
 *
 * @author Fran
 */
public class pantallaRegistrarCapacitador extends javax.swing.JInternalFrame implements IAyuda {

    private IGestorCapacitador gestor;
    private GestorRegistrarNuevoCapacitador gestorRegistrar;
    private GestorModificarCapacitador gestorModificar;
    //GestorRegistrarNuevoCapacitador gestor;
    private JComponent cmbfechaNacimiento;
    
    private ArrayList<String> listaNroTel;
    private ArrayList<Tupla> listaTipoTel;
    private ArrayList<Tupla> listaTipoCapacitacion;
    //private Date fechaVencimientoCapActual;
    /** Creates new form frmRegistrarEmpleado */
    private boolean modificar;
    private ICallBack pantallaConsultar;
    private boolean instanciadaDesdeCU;
    private String nombre;
    private String apellido;
    private int idCap;


    public pantallaRegistrarCapacitador() {
        initComponents();
        modificar=false;
        gestorRegistrar = new GestorRegistrarNuevoCapacitador(this);
        setGestor(gestorRegistrar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        instanciadaDesdeCU=false;
        this.setTitle("Registrar nuevo capacitador");
        
    }

    public pantallaRegistrarCapacitador(ICallBack pantallaConsu)
    {
        initComponents();
        modificar=false;
        gestorRegistrar = new GestorRegistrarNuevoCapacitador(this);
        //gestor = gestorReg;
        setGestor(gestorRegistrar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        instanciadaDesdeCU=true;
        pantallaConsultar=pantallaConsu;
        this.setTitle("Registrar nuevo capacitador");
    }

   public pantallaRegistrarCapacitador(int id, ICallBack pantallaConsu)
    {
        initComponents();
        modificar=true;
        //legajoEmpModificar=legajo;
        pantallaConsultar=pantallaConsu;
        gestorModificar = new GestorModificarCapacitador(this);
        //gestorModificar = new GestorModificarEmpleado(this);
        //gestor = new GestorModificarEmpleado(this);
        setGestor(gestorModificar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        idCap=id;
        //boolean r= gestorModificar.levantarEmpleado(legajo);
        boolean r= gestorModificar.levantarCapacitador(id);
        if (!r)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Error levantando el capacitador de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        this.setTitle("Modificar datos de capacitador - "+ nombre+" "+apellido);

    }
   private void setGestor(IGestorCapacitador gest)
   {gestor=gest;}

   

   public void opcionRegistrarEmpleado()
    {


    }
   private void habilitarVentana()
    {
        cmbProvincias.setEnabled(false);
       cmbLocalidades.setEnabled(false);
        cmbBarrios.setEnabled(false);
        mostrarTiposDeDocumento();
        mostrarPaises();
        //mostrarProvincias();
        //mostrarLocalidades();
        //mostrarBarrios();
        mostrarTiposDeTelefono();
        mostrarTiposCapacitacion();
        // Fecha de Nacimiento
        cmbfechaNacimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        cmbfechaNacimiento.setBounds(130,120,100,22); // x y ancho alto
        jpDatosPersonales.add(cmbfechaNacimiento);
       
        

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
        txtNroDocumento.addKeyListener(kaNuemros);
        txtNroDomicilio.addKeyListener(kaNuemros);
        txtPisoDomicilio.addKeyListener(kaNuemros);
        //txtTelefono.addKeyListener(kaNuemros);

        txtApellido.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtCPDomicilio.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtCUIL.setDocument(new LimitadorCaracteres(txtApellido,15));
        txtCalleDomicilio.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtDeptoDomicilio.setDocument(new LimitadorCaracteres(txtApellido,10));
        txtEmail.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtNombre.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtNroDocumento.setDocument(new LimitadorCaracteres(txtApellido,9));
        txtNroDomicilio.setDocument(new LimitadorCaracteres(txtApellido,6));
        txtPisoDomicilio.setDocument(new LimitadorCaracteres(txtApellido,3));
        txtTelefono.setDocument(new LimitadorCaracteres(txtApellido,15));




   }

   
    public void mostrarTiposCapacitacion()
    {
        ArrayList<Tupla> listaNombreTiposCapacitacion = gestor.mostrarTipoCapacitacion();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreTiposCapacitacion)
        {
            model.addElement(nombre);
        }
        lstTiposCapacitacion.setModel(model);
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

    public void mostrarTiposDeDocumento()
    {
        ArrayList<Tupla> listaNombresTipoDocumento = gestor.mostrarTiposDeDocumento();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla td : listaNombresTipoDocumento)
        {
            model.addElement(td);
        }
        cmbTipoDocumento.setModel(model);
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

        cmbPaises.setModel(valores);
        cmbPaises.setSelectedIndex(-1);
        cmbProvincias.setModel(new DefaultComboBoxModel());
        cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(false);
       cmbLocalidades.setEnabled(false);
        cmbBarrios.setEnabled(false);

    }

    private void mostrarProvincias()
    {
       if(cmbPaises.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbPaises.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarProvincias(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbProvincias.setModel(valores);
        cmbProvincias.setSelectedIndex(-1);
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(true);
        cmbLocalidades.setEnabled(false);
        cmbBarrios.setEnabled(false);
       }
    }

    private void mostrarLocalidades()
    {
        if(cmbProvincias.getSelectedIndex()!=-1)
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
        cmbLocalidades.setSelectedIndex(-1);
        cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(true);
       cmbLocalidades.setEnabled(true);
        cmbBarrios.setEnabled(false);
        }
    }

    private void mostrarBarrios()
    {
        if(cmbLocalidades.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbLocalidades.getSelectedItem() ;
        ArrayList<Tupla> lista = gestor.mostrarBarrios(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbBarrios.setModel(valores);
        cmbBarrios.setSelectedIndex(-1);
        cmbProvincias.setEnabled(true);
       cmbLocalidades.setEnabled(true);
        cmbBarrios.setEnabled(true);
      }
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
    

    private void cargarTiposCapacitacion()
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaCapacitaciones.getModel();
        Iterator it = modelo.getDataVector().iterator();
        listaTipoCapacitacion= new ArrayList<Tupla>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
           // listaNroTel= new ArrayList<String>();

            //System.out.println("HOLA");
            listaTipoCapacitacion.add((Tupla)fila.get(0));

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

        menuTelefonos = new javax.swing.JPopupMenu();
        emAgregarTelefono = new javax.swing.JMenuItem();
        emQuitarTelefono = new javax.swing.JMenuItem();
        menuEspecialidades = new javax.swing.JPopupMenu();
        emQuitarEspecialidad = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jpDatosPersonales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCUIL = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtNroDocumento = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCalleDomicilio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPisoDomicilio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDeptoDomicilio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCPDomicilio = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbPaises = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        cmbProvincias = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cmbBarrios = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        cmbLocalidades = new javax.swing.JComboBox();
        txtNroDomicilio = new javax.swing.JTextField();
        jpCapacitaciones = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstTiposCapacitacion = new javax.swing.JList();
        btnAgregarCapacitacion = new javax.swing.JButton();
        btnQuitarCapacitacion = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCapacitaciones = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
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
        jLabel2 = new javax.swing.JLabel();

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

        emQuitarEspecialidad.setText("jMenuItem1");
        emQuitarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emQuitarEspecialidadActionPerformed(evt);
            }
        });
        menuEspecialidades.add(emQuitarEspecialidad);

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar un nuevo capacitador");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Fecha de Nacimiento:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Email: ");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel23.setText("CUIL:");

        txtCUIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCUILActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel19.setText("<html>Tipo de<br> Documento:</html> ");

        cmbTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "D.N.I", "L.C" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("<html>Número de<br> Documento:</html>");

        txtNroDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroDocumentoActionPerformed(evt);
            }
        });

        jLabel25.setText("*");

        jLabel30.setText("*");

        jLabel27.setText("*");

        jLabel33.setText("*");

        jLabel34.setText("*");

        jLabel35.setText("*");

        javax.swing.GroupLayout jpDatosPersonalesLayout = new javax.swing.GroupLayout(jpDatosPersonales);
        jpDatosPersonales.setLayout(jpDatosPersonalesLayout);
        jpDatosPersonalesLayout.setHorizontalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCUIL)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)))
                                .addGap(21, 21, 21)
                                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addContainerGap())
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)))
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel35))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel23.getAccessibleContext().setAccessibleName("");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Domicilio"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Calle: ");

        txtCalleDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalleDomicilioActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Nº:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel10.setText("Piso:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Departamento:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Código Postal:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Pais: ");

        cmbPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisesActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel14.setText("Provincia:");

        cmbProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciasActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel15.setText("Localidad: ");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel16.setText("Barrio:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        cmbLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLocalidadesActionPerformed(evt);
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPisoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(txtNroDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(txtCPDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(txtCalleDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(txtDeptoDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(33, 33, 33)
                                .addComponent(cmbBarrios, 0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbLocalidades, 0, 120, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(41, 41, 41)
                                .addComponent(cmbPaises, 0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbProvincias, 0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCalleDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNroDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPisoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDeptoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCPDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel15)
                    .addComponent(cmbLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBarrios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jLabel16)))
        );

        jpCapacitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Capacitaciones"));

        jScrollPane4.setViewportView(lstTiposCapacitacion);

        btnAgregarCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        btnAgregarCapacitacion.setText("Agregar");
        btnAgregarCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCapacitacionActionPerformed(evt);
            }
        });

        btnQuitarCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnQuitarCapacitacion.setText("Quitar");
        btnQuitarCapacitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCapacitacionActionPerformed(evt);
            }
        });

        tablaCapacitaciones.setFont(new java.awt.Font("Tahoma", 0, 10));
        tablaCapacitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Capacitación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCapacitaciones.setComponentPopupMenu(menuEspecialidades);
        jScrollPane5.setViewportView(tablaCapacitaciones);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel24.setText("Disponibles: ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText("Agregadas: ");

        javax.swing.GroupLayout jpCapacitacionesLayout = new javax.swing.GroupLayout(jpCapacitaciones);
        jpCapacitaciones.setLayout(jpCapacitacionesLayout);
        jpCapacitacionesLayout.setHorizontalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel24)))
                .addGap(39, 39, 39)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnQuitarCapacitacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jpCapacitacionesLayout.setVerticalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addComponent(btnAgregarCapacitacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarCapacitacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
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
                "Tipo", "Número"
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel2.setText("* Campos requeridos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                        .addComponent(btnConfirmar)
                        .addGap(37, 37, 37)
                        .addComponent(btnCancelar)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jpCapacitaciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jpDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConfirmar)
                        .addComponent(btnCancelar))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNroDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroDocumentoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtCalleDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalleDomicilioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCalleDomicilioActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea cancelar?","Cancelar",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        {       this.dispose();}
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
            cargarTiposCapacitacion();
            Date fechaNac = ((JDateChooser) cmbfechaNacimiento).getDate();
            Tupla td=new Tupla();
            int cuil=Integer.parseInt(txtCUIL.getText());
            td=(Tupla)cmbTipoDocumento.getItemAt(cmbTipoDocumento.getSelectedIndex());
            gestor.datosPersonalesCapacitador(txtCUIL.getText() ,txtNroDocumento.getText(),td, txtNombre.getText(),txtApellido.getText(), fechaNac,txtEmail.getText());
            gestor.telefonosCapacitador(listaNroTel, listaTipoTel);
            /*if(cmbBarrios.getSelectedIndex()!=-1)
            {
               Tupla tupBarrio=new Tupla();
               tupBarrio=(Tupla)cmbBarrios.getItemAt(cmbBarrios.getSelectedIndex());
               gestor.datosDomicilioEmpleado(txtCalleDomicilio.getText(), txtNroDomicilio.getText(),txtDeptoDomicilio.getText(),txtPisoDomicilio.getText(), txtCPDomicilio.getText(), tupBarrio );
            }*/
            Tupla tupBarrio=new Tupla();
            if(cmbBarrios.getSelectedIndex()!=-1)
            {
               tupBarrio=(Tupla)cmbBarrios.getItemAt(cmbBarrios.getSelectedIndex());
            }
            else
            {tupBarrio.setId(-1);}
            gestor.datosDomicilioCapacitador(txtCalleDomicilio.getText(), txtNroDomicilio.getText(),txtDeptoDomicilio.getText(),txtPisoDomicilio.getText(), txtCPDomicilio.getText(), tupBarrio );
           gestor.tiposCapacitacion(listaTipoCapacitacion );
            if(!modificar)//Â¿Se esta creando un empleado?
            {

                 if(gestorRegistrar.capacitadorConfirmado())
                 {
                        JOptionPane.showMessageDialog(this.getParent(),"Capacitador Registrado correctamente","Capacitador Registrado",JOptionPane.INFORMATION_MESSAGE);
                        if(pantallaConsultar!=null)
                        {pantallaConsultar.actualizar(3, true);}
                        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Desea registrar otro capacitador?","Cancelar",JOptionPane.YES_NO_OPTION);
                        if(resp==JOptionPane.NO_OPTION)
                        {
                            this.dispose();
                        }
                        else
                        {vaciarCampos();}
                 }
                 else
                 {
                        JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante el registro del nuevo capacitador","ERROR",JOptionPane.ERROR_MESSAGE);
                        if(pantallaConsultar!=null)
                        {pantallaConsultar.actualizar(2, false);}
                 }
            }
            else//...O se esta modificando un empleado existente?
            {

                    if(gestorModificar.capacitadorModificado())
                    {
                        JOptionPane.showMessageDialog(this.getParent(),"Capacitador modificado correctamente","Capacitador Registrado",JOptionPane.INFORMATION_MESSAGE);
                        //Uso el metodo actualizar para mandar el legajo en vez del error, necesito algo que comunique las ventanas
                        pantallaConsultar.actualizar(idCap, true);
                        this.dispose();
                    }
                    else
                    {
                       JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante la modificación del capacitador","ERROR",JOptionPane.ERROR_MESSAGE);
                       pantallaConsultar.actualizar(3, false);
                       this.dispose();
                    }
             }
    }//GEN-LAST:event_btnConfirmarActionPerformed
    }
    private void vaciarCampos()
    {
        txtNroDocumento.setText("");
        txtApellido.setText("");
        txtCPDomicilio.setText("");
        txtCUIL.setText("");
        txtCalleDomicilio.setText("");
        txtDeptoDomicilio.setText("");
        txtEmail.setText("");
        ((JDateChooser) cmbfechaNacimiento).setDate(null);
        //((JDateChooser) cmbfechaVencimiento).setDate(null);
        //((JDateChooser) cmbfechaIngreso).setDate(null);
        txtNombre.setText("");
        txtNroDocumento.setText("");
        txtNroDomicilio.setText("");
        txtPisoDomicilio.setText("");
        txtTelefono.setText("");
        //tablaTelefonos.setModel(new DefaultTableModel());
        //tablaCapacitaciones.setModel(new DefaultTableModel());
        
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaCapacitaciones.getModel()).setNumRows(0);
        cmbPaises.setSelectedIndex(-1);
        cmbProvincias.setModel(new DefaultComboBoxModel());
        cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(false);
        cmbLocalidades.setEnabled(false);
        cmbBarrios.setEnabled(false);
        
        mostrarTiposCapacitacion();
        listaNroTel=new ArrayList<String>();
        listaTipoTel=new ArrayList<Tupla>();
        listaTipoCapacitacion=new ArrayList<Tupla>();
    }
        private boolean ValidarDatos()
    {
        
        //Validar nro doc unico :TODO:  EN GESTOR
        //Validar cuil doc unico :TODO:  EN GESTOR
        //validar legajo unico :TODO: EN GESTOR
        //validar campos not null: TODO: EN PANTALLA
        boolean ban=true;
            Date fechaAct=new Date();
            if(  (((JDateChooser) cmbfechaNacimiento).getDate()== null ) )
           {
                JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de nacimiento válida","ERROR",JOptionPane.ERROR_MESSAGE);
              return false;
           }
            else
            {
               if(( ((JDateChooser) cmbfechaNacimiento).getDate().compareTo(fechaAct) )>0 )
               {
                   JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de nacimiento menor a la fecha actual","ERROR",JOptionPane.ERROR_MESSAGE);
                  return false;          
               }
               
           }
            
        
        if(txtNroDocumento.getText().equals(""))
        {JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Numero de documento'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
         ban=false;
         return ban;
        }
        else
        {
             Tupla td=new Tupla();
            td=(Tupla)cmbTipoDocumento.getItemAt(cmbTipoDocumento.getSelectedIndex());
            if(!gestor.ValidarDocumento(txtNroDocumento.getText(),td))
            {ban=false;
             JOptionPane.showMessageDialog(this.getParent(),"El numero de documento ingresado ya existe para un empleado registrado","ERROR",JOptionPane.ERROR_MESSAGE);
         return ban;}
            
        }
        
        
        ///////////////////////
        if(txtCUIL.getText().equals(""))
        {JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'CUIL'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
         ban=false;
         return ban;
        }
        else
        {
            if(!gestor.ValidarCuil(txtCUIL.getText()))
            {ban=false;
             JOptionPane.showMessageDialog(this.getParent(),"El numero de cuil ingresado ya existe para un capacitador registrado","ERROR",JOptionPane.ERROR_MESSAGE);
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
       /* if(txtCalleDomicilio.getText().equals(""))
        {
                 JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Calle' en Domicilio","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                 ban=false;
                 return ban;
         }
        if(txtNroDomicilio.getText().equals(""))
        {
                 JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'NÂº' en Domicilio","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
                 ban=false;
                 return ban;
         } */

        return ban;
        
    }

    private void cmbPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisesActionPerformed
        mostrarProvincias();
    }//GEN-LAST:event_cmbPaisesActionPerformed

    private void cmbProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciasActionPerformed
        mostrarLocalidades();
    }//GEN-LAST:event_cmbProvinciasActionPerformed

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

    private void emQuitarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarEspecialidadActionPerformed
        
    }//GEN-LAST:event_emQuitarEspecialidadActionPerformed

    private void btnAgregarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCapacitacionActionPerformed
        agregarCapacitacion();
    }//GEN-LAST:event_btnAgregarCapacitacionActionPerformed

    private void btnQuitarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCapacitacionActionPerformed
        quitarCapacitacion();
    }//GEN-LAST:event_btnQuitarCapacitacionActionPerformed

    private void cmbLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLocalidadesActionPerformed
        mostrarBarrios();
    }//GEN-LAST:event_cmbLocalidadesActionPerformed
 
 private void agregarCapacitacion()
    {
        
        if(!lstTiposCapacitacion.isSelectionEmpty())
        {
                Tupla tipo=(Tupla)lstTiposCapacitacion.getSelectedValue();
                /////////
                DefaultTableModel modelo = (DefaultTableModel) tablaCapacitaciones.getModel();
                Object[] item = new Object[1];
                item[0] = tipo;
                modelo.addRow(item);
                ///////////
                ((DefaultComboBoxModel)lstTiposCapacitacion.getModel()).removeElementAt(lstTiposCapacitacion.getSelectedIndex());
               
           
        }
    }
 private void quitarCapacitacion()
    {
         if((tablaCapacitaciones.getSelectedRowCount())==1)
        {
        DefaultTableModel modelo = (DefaultTableModel) tablaCapacitaciones.getModel();
        Tupla tipo=(Tupla)modelo.getValueAt(tablaCapacitaciones.getSelectedRow(), 0) ;
        ((DefaultComboBoxModel)lstTiposCapacitacion.getModel()).addElement(tipo);
        modelo.removeRow(tablaCapacitaciones.getSelectedRow());
        }
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaRegistrarCapacitador().setVisible(true);
            }
        });
    }

     //Modificar Capacitador


        public void capacitacionesCapacitador(ArrayList<Tupla> lstTipoCapacitaciones)
        {

            DefaultTableModel tabCap= (DefaultTableModel)tablaCapacitaciones.getModel();
               DefaultComboBoxModel cboCap =(DefaultComboBoxModel)lstTiposCapacitacion.getModel();
            for(int i=0; i<lstTipoCapacitaciones.size();i++)
            {
                for (int j= 0; j < cboCap.getSize(); j++)
                {

                    if(lstTipoCapacitaciones.get(i).getId()==((Tupla)cboCap.getElementAt(j)).getId())
                    {cboCap.removeElementAt(j);}
                }
               Object[] obj=new Object[1];
               obj[0]=lstTipoCapacitaciones.get(i);
               //obj[1]=FechaUtil.getFecha(lstVencimientosCapacitaciones.get(i));
              tabCap.addRow(obj);
            }
            tablaCapacitaciones.setModel(tabCap);
            lstTiposCapacitacion.setModel(cboCap);
            //TODO: Darle color a las filas si estan vencidas o cerca de la fecha d vencimiento (no se cmo choto hacer esto, creo q no se puede)
            /*Date fechaAct=new Date();
            for (int i= 0; i < tabCap.getRowCount(); i++)
            {if(lstVencimientosCapacitaciones.get(i)<=fechaAct)
             {tablaCapacitaciones.}
            }*/
	}
        public void telefonosCapacitador(ArrayList<String> numero,ArrayList<Tupla> tipo)
        {

            DefaultTableModel tabTel= (DefaultTableModel)tablaTelefonos.getModel();

            for(int i=0; i<numero.size();i++)
            {

               Object[] obj=new Object[2];
               obj[1]=numero.get(i);
               obj[0]=(tipo.get(i));
              tabTel.addRow(obj);
            }
            tablaTelefonos.setModel(tabTel);

	}

        public void datosPersonalesCapacitador(String cuil, String nmroDoc, int idTipoDocumento, String nombre, String apellido, Date fechaNac, String email)
        {
           // txtLegajo.setText(leg);
            this.nombre=nombre;
            this.apellido=apellido;
            txtCUIL.setText(cuil);
            txtNroDocumento.setText(nmroDoc);
            txtNombre.setText(nombre);
            txtApellido.setText(apellido);
            txtEmail.setText(email);
            ((JDateChooser) cmbfechaNacimiento).setDate(fechaNac);
           // ((JDateChooser) cmbfechaIngreso).setDate(fechaIng);
            DefaultComboBoxModel modeloTipoDoc=(DefaultComboBoxModel) cmbTipoDocumento.getModel();
            for (int i= 0; i < modeloTipoDoc.getSize(); i++)
            {
                if(((Tupla)modeloTipoDoc.getElementAt(i)).getId()==idTipoDocumento)
                {
                    cmbTipoDocumento.setSelectedIndex(i);
                    break;
                }
            }
	}

        public void datosDomicilioCapacitador(String calle, String nro, String depto, String piso, String cp, int pais, int provincia, int localidad, int barrio)
        {
            txtCalleDomicilio.setText(calle);
            if(!nro.equals("0"))
            {txtNroDomicilio.setText(nro);}
            txtDeptoDomicilio.setText(depto);
            if(!piso.equals("0"))
            {txtPisoDomicilio.setText(piso);}
            txtCPDomicilio.setText(cp);
            DefaultComboBoxModel modeloPaises=(DefaultComboBoxModel) cmbPaises.getModel();
            for (int i= 0; i < modeloPaises.getSize(); i++)
            {
                if(((Tupla)modeloPaises.getElementAt(i)).getId()==pais)
                {
                    cmbPaises.setSelectedIndex(i);
                    break;
                }
            }
            mostrarProvincias();
            DefaultComboBoxModel modeloProvincias=(DefaultComboBoxModel) cmbProvincias.getModel();
            for (int i= 0; i < modeloProvincias.getSize(); i++)
            {
                if(((Tupla)modeloProvincias.getElementAt(i)).getId()==provincia)
                {
                    cmbProvincias.setSelectedIndex(i);
                    break;
                }
            }
            mostrarLocalidades();
            DefaultComboBoxModel modeloLocalidades=(DefaultComboBoxModel) cmbLocalidades.getModel();
            for (int i= 0; i < modeloLocalidades.getSize(); i++)
            {
                if(((Tupla)modeloLocalidades.getElementAt(i)).getId()==localidad)
                {
                    cmbLocalidades.setSelectedIndex(i);
                    break;
                }
            }
            mostrarBarrios();
            DefaultComboBoxModel modeloBarrios=(DefaultComboBoxModel) cmbBarrios.getModel();
            for (int i= 0; i < modeloBarrios.getSize(); i++)
            {
                if(((Tupla)modeloBarrios.getElementAt(i)).getId()==barrio)
                {
                    cmbBarrios.setSelectedIndex(i);
                    break;
                }
            }
	}
        public void datosDomicilioCapacitador(String calle, String nro, String depto, String piso, String cp)
        {
            txtCalleDomicilio.setText(calle);
            if(!nro.equals("0"))
            {txtNroDomicilio.setText(nro);}
            txtDeptoDomicilio.setText(depto);
            if(!piso.equals("0"))
            {txtPisoDomicilio.setText(piso);}
            txtCPDomicilio.setText(cp);

	}
        //Fin Metodos Modificar Capacitador

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCapacitacion;
    private javax.swing.JButton btnAgregarTelefono;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnQuitarCapacitacion;
    private javax.swing.JButton btnQuitarTelefono;
    private javax.swing.JComboBox cmbBarrios;
    private javax.swing.JComboBox cmbLocalidades;
    private javax.swing.JComboBox cmbPaises;
    private javax.swing.JComboBox cmbProvincias;
    private javax.swing.JComboBox cmbTipoDocumento;
    private javax.swing.JComboBox cmbTiposTelefono;
    private javax.swing.JMenuItem emAgregarTelefono;
    private javax.swing.JMenuItem emQuitarEspecialidad;
    private javax.swing.JMenuItem emQuitarTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel jpCapacitaciones;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JList lstTiposCapacitacion;
    private javax.swing.JPopupMenu menuEspecialidades;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JTable tablaCapacitaciones;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCPDomicilio;
    private javax.swing.JTextField txtCUIL;
    private javax.swing.JTextField txtCalleDomicilio;
    private javax.swing.JTextField txtDeptoDomicilio;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNroDocumento;
    private javax.swing.JTextField txtNroDomicilio;
    private javax.swing.JTextField txtPisoDomicilio;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables


    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Ingrese los datos del nuevo capacitador.";
    }

    public String getTituloAyuda() {
        return "Opción: Registrar Nuevo capacitador";
    }
}
