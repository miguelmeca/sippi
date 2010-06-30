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
/**
 *
 * @author Administrador
 */
public class pantallaRegistrarEmpleado extends javax.swing.JInternalFrame implements IAyuda {

    GestorRegistrarNuevoEmpleado gestor;
    private JComponent cmbfechaNacimiento;
    private JComponent cmbfechaVencimiento;
    private ArrayList<String> listaNroTel;
    private ArrayList<Tupla> listaTipoTel;
    private ArrayList<Tupla> listaTipoEspecialidad;
    private   ArrayList<Tupla> listaRangoEspecialidad;
    private ArrayList<Tupla> listaTipoCapacitacion;
    private ArrayList<Date> listaVencimientoCapacitacion;
    //private Date fechaVencimientoCapActual;
    /** Creates new form frmRegistrarEmpleado */
    public pantallaRegistrarEmpleado() {
        initComponents();
        gestor = new GestorRegistrarNuevoEmpleado(this);
        this.habilitarVentana();
        listaNroTel= new ArrayList<String>();
        listaTipoTel= new ArrayList<Tupla>();

      


        
    }

   

   public void opcionRegistrarEmpleado()
    {


    }
   private void habilitarVentana()
    {

        mostrarTiposDeDocumento();
        mostrarPaises();
        mostrarProvincias();
        mostrarLocalidades();
        mostrarBarrios();
        mostrarTiposDeTelefono();
        mostrarTiposEspecialidad();
        mostrarTiposCapacitacion();
        mostrarRangosEspecialidad();
        // Fecha de Nacimiento
        cmbfechaNacimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        cmbfechaNacimiento.setBounds(130,120,100,22); // x y ancho alto
        jpDatosPersonales.add(cmbfechaNacimiento);
        cmbfechaVencimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        cmbfechaVencimiento.setBounds(110,135,100,22); // x y ancho alto
        jpCapacitaciones.add(cmbfechaVencimiento);
        

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


        String legajo=""+gestor.generarLegajoEmpleado();
        txtLegajo.setText(legajo);



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
            listaVencimientoCapacitacion.add((Date)fila.get(1));

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
        btnConfirmar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        pnRegEmpleado = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtLegajo = new javax.swing.JTextField();
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
        txtNroDomicilio = new javax.swing.JTextField();
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
        jLabel6 = new javax.swing.JLabel();
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Legajo"));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel18.setText("Legajo:");

        txtLegajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLegajoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(276, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel18)
                .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("<html>Número de<br> Documento:</html>");

        txtNroDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroDocumentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDatosPersonalesLayout = new javax.swing.GroupLayout(jpDatosPersonales);
        jpDatosPersonales.setLayout(jpDatosPersonalesLayout);
        jpDatosPersonalesLayout.setHorizontalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCUIL)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(21, 21, 21)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)))
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5))
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

        tablaTelefonos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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
                .addContainerGap(29, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCPDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtCalleDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtPisoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtDeptoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtNroDomicilio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
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
                    .addComponent(txtPisoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
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
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
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

        tablaCapacitaciones.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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
        tablaCapacitaciones.setComponentPopupMenu(menuEspecialidades);
        jScrollPane5.setViewportView(tablaCapacitaciones);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel24.setText("Disponibles: ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText("Agregadas: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("<html>Fecha de<br> Vencimiento:</html>");

        javax.swing.GroupLayout jpCapacitacionesLayout = new javax.swing.GroupLayout(jpCapacitaciones);
        jpCapacitaciones.setLayout(jpCapacitacionesLayout);
        jpCapacitacionesLayout.setHorizontalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarCapacitacion, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(btnQuitarCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24))
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel26)
                        .addContainerGap(216, Short.MAX_VALUE))
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jpCapacitacionesLayout.setVerticalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                        .addGroup(jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                                .addComponent(btnAgregarCapacitacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitarCapacitacion))
                            .addComponent(jScrollPane4, 0, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
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

        tablaEspecialidades.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnQuitarEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lstRangosEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAgregarEspecialidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarEspecialidad))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lstRangosEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnRegEmpleado.addTab("Otros Datos", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnRegEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(507, Short.MAX_VALUE)
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
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        // TODO add your handling code here:
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
            cargarTelefonos();
            cargarEspecialidades();
            cargarCapacitaciones();
            Date fechaNac = ((JDateChooser) cmbfechaNacimiento).getDate();
            Tupla td=new Tupla();
            td=(Tupla)cmbTipoDocumento.getItemAt(cmbTipoDocumento.getSelectedIndex());
            int legajo=Integer.parseInt(txtLegajo.getText());
            gestor.datosPersonalesEmpleado(legajo, txtCUIL.getText() ,txtNroDocumento.getText(),td, txtNombre.getText(),txtApellido.getText(), fechaNac,txtEmail.getText());
            gestor.telefonosEmpleado(listaNroTel, listaTipoTel);
            Tupla barrio=new Tupla();
            barrio=(Tupla)cmbBarrios.getItemAt(cmbBarrios.getSelectedIndex());
            gestor.datosDomicilioEmpleado(txtCalleDomicilio.getText(), txtNroDomicilio.getText(),txtDeptoDomicilio.getText(),txtPisoDomicilio.getText(), txtCPDomicilio.getText(), barrio );
           gestor.tipoEspecialidadYRango(listaTipoEspecialidad ,listaRangoEspecialidad);
           gestor.capacitaciones(listaTipoCapacitacion ,listaVencimientoCapacitacion);
            if(gestor.empleadoConfirmado())
            {
                JOptionPane.showMessageDialog(this.getParent(),"Empleado Registrado correctamente","Empleado Registrado",JOptionPane.INFORMATION_MESSAGE);
                vaciarCampos();
            }
            else
            {
               JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante el registro del nuevo empleado","ERROR",JOptionPane.ERROR_MESSAGE);
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
        
        txtNombre.setText("");
        txtNroDocumento.setText("");
        txtNroDomicilio.setText("");
        txtPisoDomicilio.setText("");
        txtTelefono.setText("");
        ((DefaultTableModel)tablaEspecialidades.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaCapacitaciones.getModel()).setNumRows(0);
        
        String legajo=""+gestor.generarLegajoEmpleado();
        txtLegajo.setText(legajo);
        mostrarTiposEspecialidad();
        mostrarTiposCapacitacion();

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
                JOptionPane.showMessageDialog(this.getParent(),"Debe seleccionar una fecha de nacimiento valida","ERROR",JOptionPane.ERROR_MESSAGE);
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
            if(!gestor.ValidarDocumento(txtNroDocumento.getText()))
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
    private void emAgregarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emAgregarTelefonoActionPerformed
        agregarTelefonoTabla((Tupla)cmbTiposTelefono.getSelectedItem(), txtTelefono.getText());
    }//GEN-LAST:event_emAgregarTelefonoActionPerformed

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
                item[0] = tipo;
                item[1] = fechaVen;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCapacitacion;
    private javax.swing.JButton btnAgregarEspecialidad;
    private javax.swing.JButton btnAgregarTelefono;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnQuitarCapacitacion;
    private javax.swing.JButton btnQuitarEspecialidad;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel jpCapacitaciones;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JComboBox lstRangosEspecialidad;
    private javax.swing.JList lstTiposCapacitacion;
    private javax.swing.JList lstTiposEspecialidad;
    private javax.swing.JPopupMenu menuEspecialidades;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JTabbedPane pnRegEmpleado;
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
}
