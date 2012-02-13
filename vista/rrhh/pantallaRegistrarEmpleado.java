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
import controlador.rrhh.GestorRegistrarNuevoEmpleado;
import controlador.rrhh.GestorModificarEmpleado;
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
import util.LimitadorCaracteres;
import vista.interfaces.ICallBack;
import vista.interfaces.ICallBack_v2;
import controlador.rrhh.IGestorEmpleado;


import util.imagenes.GestorImagenes;
import util.SwingPanel;
/**
 *
 * @author Fran
 */
public class pantallaRegistrarEmpleado extends javax.swing.JInternalFrame implements IAyuda,  ICallBack_v2 {


    //El gestor a utilizar sera gestorRegistrar o gestorModificar
    //segun sea el caso.
    //El atributo gestor hace referencia al mismo gestor único nombrado
    //anteriormente, y se usa para acceder a metodos comunes que estan en
    //los 2 gestores
    private IGestorEmpleado gestor;
    private GestorRegistrarNuevoEmpleado gestorRegistrar;
    private GestorModificarEmpleado gestorModificar;
    //
    private JComponent cmbfechaNacimiento;
    private JComponent cmbfechaIngreso;
    private JComponent cmbfechaVencimiento;
    private ArrayList<String> listaNroTel;
    private ArrayList<Tupla> listaTipoTel;
    private ArrayList<Tupla> listaTipoEspecialidad;
    private   ArrayList<Tupla> listaRangoEspecialidad;
    private ArrayList<Tupla> listaTipoCapacitacion;
    private ArrayList<Date> listaVencimientoCapacitacion;
    //private Date fechaVencimientoCapActual;
    /** Creates new form frmRegistrarEmpleado */
    //private int legajoEmpModificar;
    private boolean modificar;
    private ICallBack pantallaConsultar;
    private boolean instanciadaDesdeCU;
    private int legajo;
    private String nombre;
    private String apellido;
    private int idEmp;
    GestorImagenes gestorImagenes;
    
    public pantallaRegistrarEmpleado()
    {
        initComponents();
        modificar=false;
        gestorRegistrar = new GestorRegistrarNuevoEmpleado(this);
        //gestor = gestorReg;
        setGestor(gestorRegistrar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();        
        instanciadaDesdeCU=false;
        gestorImagenes=new GestorImagenes();
    }
    public pantallaRegistrarEmpleado(ICallBack pantallaConsu)
    {
        initComponents();
        modificar=false;
        gestorRegistrar = new GestorRegistrarNuevoEmpleado(this);
        //gestor = gestorReg;
        setGestor(gestorRegistrar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        instanciadaDesdeCU=true;
        pantallaConsultar=pantallaConsu;
        this.setTitle("Registrar nuevo empleado");
    }

   public pantallaRegistrarEmpleado(int id, ICallBack pantallaConsu)
    {
        initComponents();
        modificar=true;
        idEmp=id;
        //legajoEmpModificar=legajo;
        pantallaConsultar=pantallaConsu;
        gestorModificar = new GestorModificarEmpleado(this);
        //gestorModificar = new GestorModificarEmpleado(this);
        //gestor = new GestorModificarEmpleado(this);
        setGestor(gestorModificar);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();
        //boolean r= gestorModificar.levantarEmpleado(legajo);
        //panelFotografia.setSize(200, 200);
        boolean r= gestorModificar.levantarEmpleado(id);
        if (!r)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Error levantando el empleado de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        this.setTitle("Modificar datos de empleado - Legajo Nº"+legajo+" - "+ nombre+" "+apellido);
    }
   private void setGestor(IGestorEmpleado gest)
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
        mostrarTiposEspecialidad();
        mostrarTiposCapacitacion();
        mostrarRangosEspecialidad();
        // Fecha de Nacimiento
        cmbfechaNacimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        //cmbfechaNacimiento.setBounds(130,jblFechaNacimiento.getY(),100,22); // x y ancho alto
        //System.out.println("Fecha Nacimiento Y:"+jblFechaNacimiento.getY());
        cmbfechaNacimiento.setBounds(130,120,100,22); // x y ancho alto
        jpDatosPersonales.add(cmbfechaNacimiento);
        cmbfechaVencimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        ///cmbfechaVencimiento.setBounds(160,jblFechaVencimiento.getY(),100,22); // x y ancho alto
        cmbfechaVencimiento.setBounds(160,135,100,22); // x y ancho alto
        jpCapacitaciones.add(cmbfechaVencimiento);
        cmbfechaIngreso = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        //cmbfechaIngreso.setBounds(325, jblFechaIngreso.getBounds().y ,100,22); // x y ancho alto
        //System.out.println("Fecha Ingreso Y:"+jblFechaIngreso.getBounds().y);
        cmbfechaIngreso.setBounds(325,25,100,22); // x y ancho alto
        jPanel2.add(cmbfechaIngreso);        
        
        

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
        txtLegajo.addKeyListener(kaNuemros);
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
        txtLegajo.setDocument(new LimitadorCaracteres(txtApellido,10));
        txtNombre.setDocument(new LimitadorCaracteres(txtApellido,50));
        txtNroDocumento.setDocument(new LimitadorCaracteres(txtApellido,9));
        txtNroDomicilio.setDocument(new LimitadorCaracteres(txtApellido,6));
        txtPisoDomicilio.setDocument(new LimitadorCaracteres(txtApellido,3));
        txtTelefono.setDocument(new LimitadorCaracteres(txtApellido,15));

        if(!modificar)
        {String legajo=""+gestorRegistrar.generarLegajoEmpleado();
        txtLegajo.setText(legajo);}



   }

   public void mostrarRangosEspecialidad()
    {
        ArrayList<Tupla> listaNombreRangosEspecialidad = gestor.mostrarRangoEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreRangosEspecialidad)
        {
            model.addElement(nombre);
        }
        lstRangosEspecialidad.setModel(model);
    }
   public void mostrarTiposEspecialidad()
    {
        ArrayList<Tupla> listaNombreTiposEspecialidad = gestor.mostrarTipoEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreTiposEspecialidad)
        {
            model.addElement(nombre);
        }
        lstTiposEspecialidad.setModel(model);
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
        //cmbLocalidades.setSelectedIndex(-1);

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
        //cmbLocalidades.setModel(new DefaultComboBoxModel());
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
    private void cargarEspecialidades()
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaEspecialidades.getModel();
        Iterator it = modelo.getDataVector().iterator();
        listaTipoEspecialidad= new ArrayList<Tupla>();
        listaRangoEspecialidad= new ArrayList<Tupla>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
           // listaNroTel= new ArrayList<String>();

            //System.out.println("HOLA");
            listaTipoEspecialidad.add((Tupla)fila.get(0));
            listaRangoEspecialidad.add((Tupla)fila.get(1));
        }
    }

    private void cargarCapacitaciones()
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaCapacitaciones.getModel();
        Iterator it = modelo.getDataVector().iterator();
        listaTipoCapacitacion= new ArrayList<Tupla>();
        listaVencimientoCapacitacion= new ArrayList<Date>();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
           // listaNroTel= new ArrayList<String>();

            //System.out.println("HOLA");
            listaTipoCapacitacion.add((Tupla)fila.get(0));
            listaVencimientoCapacitacion.add((Date)((NTupla)fila.get(1)).getData());

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
        emQuitarTelefono = new javax.swing.JMenuItem();
        menuEspecialidades = new javax.swing.JPopupMenu();
        emQuitarEspecialidad = new javax.swing.JMenuItem();
        menuCapacitaciones = new javax.swing.JPopupMenu();
        emQuitarCapacitacion = new javax.swing.JMenuItem();
        buttonGroupAjustar = new javax.swing.ButtonGroup();
        btnConfirmar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        pnRegEmpleado = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtLegajo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jblFechaIngreso = new javax.swing.JLabel();
        jpDatosPersonales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jblFechaNacimiento = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCUIL = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtNroDocumento = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbTiposTelefono = new javax.swing.JComboBox();
        btnAgregarTelefono = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        btnQuitarTelefono = new javax.swing.JButton();
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
        jPanel6 = new javax.swing.JPanel();
        jpCapacitaciones = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstTiposCapacitacion = new javax.swing.JList();
        btnAgregarCapacitacion = new javax.swing.JButton();
        btnQuitarCapacitacion = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCapacitaciones = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jblFechaVencimiento = new javax.swing.JLabel();
        btnGestionCapacitaciones = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTiposEspecialidad = new javax.swing.JList();
        btnAgregarEspecialidad = new javax.swing.JButton();
        btnQuitarEspecialidad = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEspecialidades = new javax.swing.JTable();
        lstRangosEspecialidad = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnGestionEspecialidades = new javax.swing.JButton();
        seccionFotografia = new javax.swing.JPanel();
        panelFotografia = new javax.swing.JPanel();
        btnSeleccionarArchivo = new javax.swing.JButton();
        btnAjustarAltura = new javax.swing.JRadioButton();
        btnAjustarAncho = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();

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
        setTitle("Registrar un nuevo empleado");

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnConfirmar.setText("Aceptar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        pnRegEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnRegEmpleadoMousePressed(evt);
            }
        });
        pnRegEmpleado.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnRegEmpleadoStateChanged(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Legajo"));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel18.setText("Legajo:");

        txtLegajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLegajoActionPerformed(evt);
            }
        });

        jLabel25.setText("*");

        jblFechaIngreso.setFont(new java.awt.Font("Tahoma", 1, 11));
        jblFechaIngreso.setText("Fecha de Ingreso:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jblFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel18)
                .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel25)
                .addComponent(jblFechaIngreso))
        );

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

        jblFechaNacimiento.setFont(new java.awt.Font("Tahoma", 1, 11));
        jblFechaNacimiento.setText("Fecha de Nacimiento:");

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

        jLabel27.setText("*");

        jLabel28.setText("*");

        jLabel29.setText("*");

        jLabel30.setText("*");

        jLabel31.setText("*");

        jLabel33.setText("*");

        javax.swing.GroupLayout jpDatosPersonalesLayout = new javax.swing.GroupLayout(jpDatosPersonales);
        jpDatosPersonales.setLayout(jpDatosPersonalesLayout);
        jpDatosPersonalesLayout.setHorizontalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(txtCUIL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jblFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)))
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(1, 1, 1)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30)))
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29)))
                .addGap(7, 7, 7)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)))
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jblFechaNacimiento)
                    .addComponent(jLabel33)))
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel4)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel23.getAccessibleContext().setAccessibleName("");

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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnQuitarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarTelefono))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmbTiposTelefono, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTiposTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnQuitarTelefono)
                            .addComponent(btnAgregarTelefono)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        cmbProvincias.setEnabled(false);
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

        cmbBarrios.setEnabled(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        cmbLocalidades.setEnabled(false);
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
                            .addComponent(txtNroDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtPisoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtCPDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtCalleDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtDeptoDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(33, 33, 33)
                                .addComponent(cmbBarrios, 0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbLocalidades, 0, 116, Short.MAX_VALUE)))
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
                    .addComponent(jLabel16))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.getAccessibleContext().setAccessibleName("");

        pnRegEmpleado.addTab("Datos Básicos", jPanel1);

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
                "Capacitación", "Fecha de Vencimiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCapacitaciones.setComponentPopupMenu(menuCapacitaciones);
        jScrollPane5.setViewportView(tablaCapacitaciones);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel24.setText("Disponibles: ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText("Agregadas: ");

        jblFechaVencimiento.setFont(new java.awt.Font("Tahoma", 1, 11));
        jblFechaVencimiento.setText("<html>Fecha de<br> Vencimiento:</html>");

        btnGestionCapacitaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        btnGestionCapacitaciones.setText("<html>Modificar<br> Disponibles</html>");
        btnGestionCapacitaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCapacitacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCapacitacionesLayout = new javax.swing.GroupLayout(jpCapacitaciones);
        jpCapacitaciones.setLayout(jpCapacitacionesLayout);
        jpCapacitacionesLayout.setHorizontalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addComponent(btnGestionCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jblFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnQuitarCapacitacion, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(btnAgregarCapacitacion, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jpCapacitacionesLayout.setVerticalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGestionCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                            .addComponent(btnAgregarCapacitacion)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnQuitarCapacitacion))
                        .addComponent(jScrollPane4, 0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jblFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Especialidades"));

        jScrollPane1.setViewportView(lstTiposEspecialidad);

        btnAgregarEspecialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        btnAgregarEspecialidad.setText("Agregar");
        btnAgregarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEspecialidadActionPerformed(evt);
            }
        });

        btnQuitarEspecialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        btnQuitarEspecialidad.setText("Quitar");
        btnQuitarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarEspecialidadActionPerformed(evt);
            }
        });

        tablaEspecialidades.setFont(new java.awt.Font("Tahoma", 0, 10));
        tablaEspecialidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especialidad", "Rango"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEspecialidades.setComponentPopupMenu(menuEspecialidades);
        jScrollPane2.setViewportView(tablaEspecialidades);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel20.setText("Disponibles: ");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel21.setText("Rango: ");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel22.setText("Agregadas: ");

        btnGestionEspecialidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        btnGestionEspecialidades.setText("<html>Modificar<br> Disponibles</html>");
        btnGestionEspecialidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionEspecialidadesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnGestionEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(lstRangosEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnQuitarEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(btnAgregarEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGestionEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAgregarEspecialidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarEspecialidad))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lstRangosEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jpCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pnRegEmpleado.addTab("Otros Datos", jPanel6);

        panelFotografia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelFotografia.setPreferredSize(new java.awt.Dimension(260, 260));
        panelFotografia.setRequestFocusEnabled(false);

        javax.swing.GroupLayout panelFotografiaLayout = new javax.swing.GroupLayout(panelFotografia);
        panelFotografia.setLayout(panelFotografiaLayout);
        panelFotografiaLayout.setHorizontalGroup(
            panelFotografiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        panelFotografiaLayout.setVerticalGroup(
            panelFotografiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        btnSeleccionarArchivo.setText("Seleccionar archivo");
        btnSeleccionarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarArchivoActionPerformed(evt);
            }
        });

        buttonGroupAjustar.add(btnAjustarAltura);
        btnAjustarAltura.setText("Ajustar a altura");
        btnAjustarAltura.setEnabled(false);
        btnAjustarAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjustarAlturaActionPerformed(evt);
            }
        });

        buttonGroupAjustar.add(btnAjustarAncho);
        btnAjustarAncho.setText("Ajustar a ancho");
        btnAjustarAncho.setEnabled(false);
        btnAjustarAncho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjustarAnchoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout seccionFotografiaLayout = new javax.swing.GroupLayout(seccionFotografia);
        seccionFotografia.setLayout(seccionFotografiaLayout);
        seccionFotografiaLayout.setHorizontalGroup(
            seccionFotografiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seccionFotografiaLayout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addComponent(btnSeleccionarArchivo)
                .addGap(309, 309, 309))
            .addGroup(seccionFotografiaLayout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(panelFotografia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(seccionFotografiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAjustarAltura)
                    .addComponent(btnAjustarAncho))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        seccionFotografiaLayout.setVerticalGroup(
            seccionFotografiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seccionFotografiaLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(panelFotografia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSeleccionarArchivo)
                .addGap(28, 28, 28))
            .addGroup(seccionFotografiaLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btnAjustarAltura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAjustarAncho)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        pnRegEmpleado.addTab("Fotografia", seccionFotografia);

        jLabel2.setText("* Campos requeridos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnRegEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 372, Short.MAX_VALUE)
                .addComponent(btnConfirmar)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnRegEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(jButton6)
                    .addComponent(jLabel2))
                .addContainerGap(79, Short.MAX_VALUE))
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea cancelar?","Cancelar",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        {       this.dispose();}
    }//GEN-LAST:event_jButton6ActionPerformed

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
            gestor.vaciarDatos();
            cargarTelefonos();
            cargarEspecialidades();
            cargarCapacitaciones();
            Date fechaNac = ((JDateChooser) cmbfechaNacimiento).getDate();
            Date fechaIng = ((JDateChooser) cmbfechaIngreso).getDate();
            Tupla td=new Tupla();
            td=(Tupla)cmbTipoDocumento.getItemAt(cmbTipoDocumento.getSelectedIndex());
            int legajo=Integer.parseInt(txtLegajo.getText());
            gestor.datosPersonalesEmpleado(legajo, txtCUIL.getText() ,txtNroDocumento.getText(),td, txtNombre.getText(),txtApellido.getText(), fechaNac,fechaIng,txtEmail.getText());
            gestor.telefonosEmpleado(listaNroTel, listaTipoTel);
            gestor.imagenEmpleado(gestorImagenes);
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
            gestor.datosDomicilioEmpleado(txtCalleDomicilio.getText(), txtNroDomicilio.getText(),txtDeptoDomicilio.getText(),txtPisoDomicilio.getText(), txtCPDomicilio.getText(), tupBarrio );
            //////////////
            gestor.tipoEspecialidadYRango(listaTipoEspecialidad ,listaRangoEspecialidad);
            gestor.capacitaciones(listaTipoCapacitacion ,listaVencimientoCapacitacion);
            if(!modificar)//¿Se esta creando un empleado?
            {   
                 
                 if(gestorRegistrar.empleadoConfirmado())
                 {
                        JOptionPane.showMessageDialog(this.getParent(),"Empleado Registrado correctamente","Empleado Registrado",JOptionPane.INFORMATION_MESSAGE);
                        if(pantallaConsultar!=null)
                        {pantallaConsultar.actualizar(3, true);}
                        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Desea registrar otro emplado?","Cancelar",JOptionPane.YES_NO_OPTION);
                        if(resp==JOptionPane.NO_OPTION)
                        {       
                            this.dispose();
                        }
                        else
                        {vaciarCampos();}
                 }
                 else
                 {
                        JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante el registro del nuevo empleado","ERROR",JOptionPane.ERROR_MESSAGE);
                        if(pantallaConsultar!=null)
                        {pantallaConsultar.actualizar(2, false);}
                 }
            }                
            else//...O se esta modificando un empleado existente?
            {
                   
                    if(gestorModificar.empleadoModificado())
                    {
                        JOptionPane.showMessageDialog(this.getParent(),"Empleado modificado correctamente","Empleado Registrado",JOptionPane.INFORMATION_MESSAGE);
                        //Uso el metodo actualizar para mandar el legajo en vez del error, necesito algo que comunique las ventanas
                        pantallaConsultar.actualizar(idEmp, true);
                        this.dispose();
                    }
                    else
                    {
                       JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante la modificación del empleado","ERROR",JOptionPane.ERROR_MESSAGE);
                       pantallaConsultar.actualizar(3, false);
                       this.dispose();
                    }
             }
            
            }
            
           
    }//GEN-LAST:event_btnConfirmarActionPerformed
    
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
        ((JDateChooser) cmbfechaVencimiento).setDate(null);
        ((JDateChooser) cmbfechaIngreso).setDate(null);
        txtNombre.setText("");
        txtNroDocumento.setText("");
        txtNroDomicilio.setText("");
        txtPisoDomicilio.setText("");
        txtTelefono.setText("");
       // tablaEspecialidades.setModel(new DefaultTableModel());
       // tablaTelefonos.setModel(new DefaultTableModel());
       // tablaCapacitaciones.setModel(new DefaultTableModel());
        ((DefaultTableModel)tablaEspecialidades.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaCapacitaciones.getModel()).setNumRows(0);
        if(!modificar)
        {String legajo=""+gestorRegistrar.generarLegajoEmpleado();
        txtLegajo.setText(legajo);}
        mostrarTiposEspecialidad();
        mostrarTiposCapacitacion();
        listaNroTel=new ArrayList<String>();
        listaTipoTel=new ArrayList<Tupla>();
        listaTipoEspecialidad=new ArrayList<Tupla>();
        listaRangoEspecialidad=new ArrayList<Tupla>();
        listaTipoCapacitacion=new ArrayList<Tupla>();
        listaVencimientoCapacitacion=new ArrayList<Date>();
        cmbPaises.setSelectedIndex(-1);
        cmbProvincias.setModel(new DefaultComboBoxModel());
        cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(false);
        cmbLocalidades.setEnabled(false);
        cmbBarrios.setEnabled(false);
        panelFotografia.removeAll();
        panelFotografia.repaint();
        gestorImagenes=new GestorImagenes();
        btnAjustarAltura.setEnabled(false);
        btnAjustarAltura.setSelected(false);
        btnAjustarAncho.setEnabled(false);
        btnAjustarAncho.setSelected(false);

    }
        private boolean ValidarDatos()
    {
        
        //Validar nro doc unico :TODO:  EN GESTOR
        //Validar cuil doc unico :TODO:  EN GESTOR
        //validar legajo unico :TODO: EN GESTOR
        //validar campos not null: TODO: EN PANTALLA
        boolean ban=true;
            Date fechaAct=new Date();
            
            //El siguiente codigo comentado es para validar que el usuario ingrese una fecha de nacimiento
           /* if(  (((JDateChooser) cmbfechaNacimiento).getDate()== null ) )
           {
                JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de nacimiento valida","ERROR",JOptionPane.ERROR_MESSAGE);
              return false;
           }
            else
            {*/
               if(  (((JDateChooser) cmbfechaNacimiento).getDate()!= null ) )
               {
                   if(( ((JDateChooser) cmbfechaNacimiento).getDate().compareTo(fechaAct) )>0 )
                   {
                       JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de nacimiento menor a la fecha actual","ERROR",JOptionPane.ERROR_MESSAGE);
                      return false;          
                   }                   
               }               
          // } 
               
        if((((JDateChooser) cmbfechaNacimiento).getDate()!= null )&& (((JDateChooser) cmbfechaIngreso).getDate()!= null ) &&     ( ((JDateChooser) cmbfechaNacimiento).getDate().compareTo(((JDateChooser) cmbfechaIngreso).getDate()) )>0 )
        {
           JOptionPane.showMessageDialog(this.getParent(),"La fecha de nacimiento no puede ser posterior a la fecha de ingreso","ERROR",JOptionPane.ERROR_MESSAGE);
           return false;
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
        if(txtLegajo.getText().equals(""))
        {JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Legajo'","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
         ban=false;
         return ban;
        }
        else
        {
            if(!gestor.ValidarLegajo(txtLegajo.getText()))
            {ban=false;
             JOptionPane.showMessageDialog(this.getParent(),"El numero de legajo ingresado ya existe para un empleado registrado","ERROR",JOptionPane.ERROR_MESSAGE);
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
             JOptionPane.showMessageDialog(this.getParent(),"El numero de cuil ingresado ya existe para un empleado registrado","ERROR",JOptionPane.ERROR_MESSAGE);
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
                 JOptionPane.showMessageDialog(this.getParent(),"Debe completarse el campo 'Nº' en Domicilio","ERROR,Faltan campos requeridos",JOptionPane.ERROR_MESSAGE);
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

    private void txtLegajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLegajoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLegajoActionPerformed

    private void btnAgregarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEspecialidadActionPerformed
       /*Tupla rango=new Tupla();
       rango.setId(lstRangosEspecialidad.getSelectedIndex());
       rango.setNombre(lstRangosEspecialidad.getSelectedValue());
       Tupla tipo=new Tupla();
       tipo.setId(lstTiposEspecialidad.getSelectedIndex());
       tipo.setNombre(lstTiposEspecialidad.getSelectedValue());*/
       // if(!lstTiposEspecialidad.isSelectionEmpty())
       // {
       // Tupla rango=(Tupla)lstRangosEspecialidad.getSelectedItem();
       // Tupla tipo=(Tupla)lstTiposEspecialidad.getSelectedValue();
        //agregarEspecialidad(tipo, rango);
       // ((DefaultComboBoxModel)lstTiposEspecialidad.getModel()).removeElementAt(lstTiposEspecialidad.getSelectedIndex());//TODO:
       // }
        agregarEspecialidad();
        
    }//GEN-LAST:event_btnAgregarEspecialidadActionPerformed

    private void txtCUILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCUILActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCUILActionPerformed

    private void btnQuitarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarEspecialidadActionPerformed

       quitarEspecialidad();

    }//GEN-LAST:event_btnQuitarEspecialidadActionPerformed
    private void quitarEspecialidad()
    {
         if((tablaEspecialidades.getSelectedRowCount())==1)
        {
        DefaultTableModel modelo = (DefaultTableModel) tablaEspecialidades.getModel();
        Tupla tipo=(Tupla)modelo.getValueAt(tablaEspecialidades.getSelectedRow(), 0) ;
        ((DefaultComboBoxModel)lstTiposEspecialidad.getModel()).addElement(tipo);
        //agregarEspecialidad(tipo, rango);
        //((DefaultComboBoxModel)lstTiposEspecialidad.getModel()).removeElementAt(lstTiposEspecialidad.getSelectedIndex());//TODO:
        modelo.removeRow(tablaEspecialidades.getSelectedRow());
        }
    }

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
    private void emQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarTelefonoActionPerformed
       quitarTelefono();
    }//GEN-LAST:event_emQuitarTelefonoActionPerformed

    private void emQuitarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarEspecialidadActionPerformed
        quitarEspecialidad();
    }//GEN-LAST:event_emQuitarEspecialidadActionPerformed

    private void btnAgregarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCapacitacionActionPerformed
        agregarCapacitacion();
    }//GEN-LAST:event_btnAgregarCapacitacionActionPerformed

    private void btnQuitarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCapacitacionActionPerformed
        quitarCapacitacion();
    }//GEN-LAST:event_btnQuitarCapacitacionActionPerformed

    private void emQuitarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarCapacitacionActionPerformed
        quitarCapacitacion();
    }//GEN-LAST:event_emQuitarCapacitacionActionPerformed

    private void cmbLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLocalidadesActionPerformed
        mostrarBarrios();
        
    }//GEN-LAST:event_cmbLocalidadesActionPerformed

    private void btnGestionEspecialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionEspecialidadesActionPerformed
        pantallaGestionarTipoEspecialidad win = new pantallaGestionarTipoEspecialidad();
        SwingPanel.getInstance().addWindow(win);
        win.setPantalla(this);
        win.setVisible(true);
    }//GEN-LAST:event_btnGestionEspecialidadesActionPerformed

    private void btnGestionCapacitacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCapacitacionesActionPerformed
        /*pantallaGestionarTipoCapacitacion win = new pantallaGestionarTipoCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setPantalla(this);
        win.setVisible(true);*/
    }//GEN-LAST:event_btnGestionCapacitacionesActionPerformed

private void btnSeleccionarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarArchivoActionPerformed

int cargo=gestorImagenes.cargarImagenDeArchivo(panelFotografia, this);
if(cargo!=GestorImagenes.noCargoImagen)
{
    btnAjustarAncho.setEnabled(true);
    btnAjustarAltura.setEnabled(true);
    if(cargo==GestorImagenes.cargoImagenAjustadaEnAltura)
    {btnAjustarAltura.setSelected(true); }
    else if(cargo==GestorImagenes.cargoImagenAjustadaEnAncho)
    {btnAjustarAncho.setSelected(true);}
 }

}//GEN-LAST:event_btnSeleccionarArchivoActionPerformed

private void pnRegEmpleadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnRegEmpleadoMousePressed
   
     
    
}//GEN-LAST:event_pnRegEmpleadoMousePressed

private void pnRegEmpleadoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnRegEmpleadoStateChanged

    if(seccionFotografia==pnRegEmpleado.getSelectedComponent())
    {
       gestorImagenes.cargarImagenEnPanel(panelFotografia);
                
    }
}//GEN-LAST:event_pnRegEmpleadoStateChanged

private void btnAjustarAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjustarAlturaActionPerformed
if(btnAjustarAltura.isSelected())
{
    gestorImagenes.cargarImagenEnPanelAjustandoTamaño(panelFotografia,true);
}
}//GEN-LAST:event_btnAjustarAlturaActionPerformed

private void btnAjustarAnchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjustarAnchoActionPerformed
if(btnAjustarAncho.isSelected()&&!btnAjustarAltura.isSelected())
{
    gestorImagenes.cargarImagenEnPanelAjustandoTamaño(panelFotografia,false);
}
}//GEN-LAST:event_btnAjustarAnchoActionPerformed
 private void agregarEspecialidad()
    {
        if(!lstTiposEspecialidad.isSelectionEmpty())
        {
        Tupla rango=(Tupla)lstRangosEspecialidad.getSelectedItem();
        Tupla tipo=(Tupla)lstTiposEspecialidad.getSelectedValue();
        /////////
        DefaultTableModel modelo = (DefaultTableModel) tablaEspecialidades.getModel();
        Object[] item = new Object[2];
        item[0] = tipo;
        item[1] = rango;
        modelo.addRow(item);
        ///////////
        ((DefaultComboBoxModel)lstTiposEspecialidad.getModel()).removeElementAt(lstTiposEspecialidad.getSelectedIndex());
        }
        
    }
 private void agregarCapacitacion()
    {
        
        if(!lstTiposCapacitacion.isSelectionEmpty())
        {
          Date fechaAct=new Date();
          if( ! (((JDateChooser) cmbfechaVencimiento).getDate()== null ) )
          {
               if(( ((JDateChooser) cmbfechaVencimiento).getDate().compareTo(fechaAct) )>0 )
               {
                  Date fechaVen = ((JDateChooser) cmbfechaVencimiento).getDate();
                Tupla tipo=(Tupla)lstTiposCapacitacion.getSelectedValue();
                /////////
                DefaultTableModel modelo = (DefaultTableModel) tablaCapacitaciones.getModel();
                Object[] item = new Object[2];
                NTupla nt=new NTupla();
                nt.setId(1);
                nt.setNombre(FechaUtil.getFecha(fechaVen));
                nt.setData(fechaVen);
                FechaUtil fe=new FechaUtil();
                item[0] = tipo;                 
                //item[1] = FechaUtil.getFecha(fechaVen);
                item[1] = nt;
                
                modelo.addRow(item);
                ///////////
                ((DefaultComboBoxModel)lstTiposCapacitacion.getModel()).removeElementAt(lstTiposCapacitacion.getSelectedIndex());
               }
               else
               {
                  JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de vencimiento mayor a la fecha actual","ERROR",JOptionPane.ERROR_MESSAGE);
               }
           }
           else
           {
              JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de vencimiento valida","ERROR",JOptionPane.ERROR_MESSAGE);
           }
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
                new pantallaRegistrarEmpleado().setVisible(true);
            }
        });
    }
    /////////////////////////////////////////////////////
    //Modificar Empleado
    public void especialidadesEmpleado(ArrayList<Tupla> lstTipoEspecialidad, ArrayList<Tupla> lstRangoEspecialidad)
        {
            DefaultTableModel tabEsp= (DefaultTableModel)tablaEspecialidades.getModel();
            DefaultComboBoxModel cboEsp =(DefaultComboBoxModel)lstTiposEspecialidad.getModel();
            for(int i=0; i<lstTipoEspecialidad.size();i++)
            {
               for (int j= 0; j < cboEsp.getSize(); j++)
                {

                    if(lstTipoEspecialidad.get(i).getId()==((Tupla)cboEsp.getElementAt(j)).getId())
                    {cboEsp.removeElementAt(j);}
                }
               Tupla[] obj=new Tupla[2];
               obj[0]=lstTipoEspecialidad.get(i);
               obj[1]=lstRangoEspecialidad.get(i);
                      tabEsp.addRow(obj);
            }
           tablaEspecialidades.setModel(tabEsp);
            lstTiposEspecialidad.setModel(cboEsp);
	
	}
	
        public void capacitacionesEmpleado(ArrayList<Tupla> lstTipoCapacitaciones, ArrayList<Date> lstVencimientosCapacitaciones)
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
               Object[] obj=new Object[2];
               obj[0]=lstTipoCapacitaciones.get(i);
               obj[1]=FechaUtil.getFecha(lstVencimientosCapacitaciones.get(i));
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
        public void telefonosEmpleado(ArrayList<String> numero,ArrayList<Tupla> tipo)
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

        public void datosPersonalesEmpleado(String leg,String cuil, String nmroDoc, int idTipoDocumento, String nombre, String apellido, Date fechaNac,Date fechaIng, String email)
        {
            txtLegajo.setText(leg);
            txtCUIL.setText(cuil);
            this.nombre=nombre;
            this.apellido=apellido;
            this.legajo=Integer.parseInt(leg);
            txtNroDocumento.setText(nmroDoc);
            txtNombre.setText(nombre);
            txtApellido.setText(apellido);
            txtEmail.setText(email);
            ((JDateChooser) cmbfechaNacimiento).setDate(fechaNac);
            ((JDateChooser) cmbfechaIngreso).setDate(fechaIng);
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

        public void datosDomicilioEmpleado(String calle, String nro, String depto, String piso, String cp, int pais, int provincia, int localidad, int barrio)
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
        public void datosDomicilioEmpleado(String calle, String nro, String depto, String piso, String cp)
        {
            txtCalleDomicilio.setText(calle);
            if(!nro.equals("0"))
            {txtNroDomicilio.setText(nro);}
            txtDeptoDomicilio.setText(depto);
            if(!piso.equals("0"))
            {txtPisoDomicilio.setText(piso);}
            txtCPDomicilio.setText(cp);

	}
        
        public void mostrarImagenEmpleado(GestorImagenes ge)
        {            
            gestorImagenes=ge;
             panelFotografia.removeAll();
             //El renderizado se hace en el evento cuando se selecciona la pestaña.
            
        }
        //Fin Metodos Modificar Empleado
    /////////////////////////////////////////////////////

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCapacitacion;
    private javax.swing.JButton btnAgregarEspecialidad;
    private javax.swing.JButton btnAgregarTelefono;
    private javax.swing.JRadioButton btnAjustarAltura;
    private javax.swing.JRadioButton btnAjustarAncho;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnGestionCapacitaciones;
    private javax.swing.JButton btnGestionEspecialidades;
    private javax.swing.JButton btnQuitarCapacitacion;
    private javax.swing.JButton btnQuitarEspecialidad;
    private javax.swing.JButton btnQuitarTelefono;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.ButtonGroup buttonGroupAjustar;
    private javax.swing.JComboBox cmbBarrios;
    private javax.swing.JComboBox cmbLocalidades;
    private javax.swing.JComboBox cmbPaises;
    private javax.swing.JComboBox cmbProvincias;
    private javax.swing.JComboBox cmbTipoDocumento;
    private javax.swing.JComboBox cmbTiposTelefono;
    private javax.swing.JMenuItem emQuitarCapacitacion;
    private javax.swing.JMenuItem emQuitarEspecialidad;
    private javax.swing.JMenuItem emQuitarTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel jblFechaIngreso;
    private javax.swing.JLabel jblFechaNacimiento;
    private javax.swing.JLabel jblFechaVencimiento;
    private javax.swing.JPanel jpCapacitaciones;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JComboBox lstRangosEspecialidad;
    private javax.swing.JList lstTiposCapacitacion;
    private javax.swing.JList lstTiposEspecialidad;
    private javax.swing.JPopupMenu menuCapacitaciones;
    private javax.swing.JPopupMenu menuEspecialidades;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JPanel panelFotografia;
    private javax.swing.JTabbedPane pnRegEmpleado;
    private javax.swing.JPanel seccionFotografia;
    private javax.swing.JTable tablaCapacitaciones;
    private javax.swing.JTable tablaEspecialidades;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCPDomicilio;
    private javax.swing.JTextField txtCUIL;
    private javax.swing.JTextField txtCalleDomicilio;
    private javax.swing.JTextField txtDeptoDomicilio;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLegajo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNroDocumento;
    private javax.swing.JTextField txtNroDomicilio;
    private javax.swing.JTextField txtPisoDomicilio;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
    
    public void actualizar(int id, String flag, boolean exito)
    {
        if(flag.equals("FLAG_TIPO_CAPACITACION"))
        {
            mostrarTiposCapacitacion();
        }
        if(flag.equals(pantallaGestionarTipoEspecialidad.getFlagPantalla()))
        {
            mostrarTiposEspecialidad();
        }

    }

    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Ingrese los datos del empleado";
    }

    public String getTituloAyuda() {
        if(modificar)
        {return "Opción: Modificar Empleado";}
        else
        {return "Opción: Nuevo Empleado";}
    }
    
    
}
