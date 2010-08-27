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
import controlador.rrhh.GestorConsultarDatosCapacitador;


import util.SwingPanel;
/**
 *
 * @author Fran
 */
public class pantallaConsultarDatosCapacitador extends javax.swing.JInternalFrame implements IAyuda, ICallBack {


    //El gestor a utilizar sera gestorRegistrar o gestorModificar
    //segun sea el caso.
    //El atributo gestor hace referencia al mismo gestor único nombrado
    //anteriormente, y se usa para acceder a metodos comunes que estan en
    //los 2 gestores
    private GestorConsultarDatosCapacitador gestor;
    
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


    
    

   public pantallaConsultarDatosCapacitador(int id, ICallBack pantallaConsu)
    {
        initComponents();
        //modificar=true;
        //legajoEmpModificar=legajo;
        pantallaConsultar=pantallaConsu;
        gestor = new GestorConsultarDatosCapacitador(this);
        //gestorModificar = new GestorModificarEmpleado(this);
        //gestor = new GestorModificarEmpleado(this);
        this.id=id;
        this.habilitarVentana();
        //listaNroTel= new ArrayList<String>();
        //listaTipoTel= new ArrayList<Tupla>();
        //boolean r= gestorModificar.levantarEmpleado(legajo);
        boolean r= gestor.levantarCapacitador(id);
        if (!r)
        {
            JOptionPane.showMessageDialog(this.getParent(),"Error levantando el capacitador de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        this.setTitle("Consultar datos de capacitador - "+ nombre+" "+apellido);

    }
   
   public void opcionConsultarCapacitador()
    {


    }
   private void habilitarVentana()
    {
       //lblProvincias.setEnabled(false);
       //lblLocalidades.setEnabled(false);
       // lblBarrios.setEnabled(false);
        //mostrarTiposDeDocumento();
        //mostrarPaises();
        //mostrarProvincias();
        //mostrarLocalidades();
        //mostrarBarrios();
        //mostrarTiposDeTelefono();
       // mostrarTiposEspecialidad();
       // mostrarTiposCapacitacion();
        
       // mostrarRangosEspecialidad();
        // Fecha de Nacimiento
       /* cmbfechaNacimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        cmbfechaNacimiento.setBounds(130,120,100,22); // x y ancho alto
        jpDatosPersonales.add(cmbfechaNacimiento);
        cmbfechaVencimiento = new JDateChooser("dd/MM/yyyy", "####/##/##", '_');
        cmbfechaVencimiento.setBounds(110,135,100,22); // x y ancho alto
        jpCapacitaciones.add(cmbfechaVencimiento);*/

        

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
      


   }

   /*public void mostrarRangosEspecialidad()
    {
        ArrayList<Tupla> listaNombreRangosEspecialidad = gestor.mostrarRangoEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreRangosEspecialidad)
        {
            model.addElement(nombre);
        }
        lstRangosEspecialidad.setModel(model);
    }*/
  /* public void mostrarTiposEspecialidad()
    {
        ArrayList<Tupla> listaNombreTiposEspecialidad = gestor.mostrarTipoEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreTiposEspecialidad)
        {
            model.addElement(nombre);
        }
        lstTiposEspecialidad.setModel(model);
    }*/
    /*public void mostrarTiposCapacitacion()
    {
        ArrayList<Tupla> listaNombreTiposCapacitacion = gestor.mostrarTipoCapacitacion();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreTiposCapacitacion)
        {
            model.addElement(nombre);
        }
        lstTiposCapacitacion.setModel(model);
    }*/
    /*public void mostrarTiposDeTelefono()
    {
        ArrayList<Tupla> listaNombresTipoDeTelefono = gestor.mostrarTiposDeTelefono();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombresTipoDeTelefono)
        {
            model.addElement(nombre);
        }
        cmbTiposTelefono.setModel(model);
    }
*/
    /*public void mostrarTiposDeDocumento()
    {
        ArrayList<Tupla> listaNombresTipoDocumento = gestor.mostrarTiposDeDocumento();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla td : listaNombresTipoDocumento)
        {
            model.addElement(td);
        }
        cmbTipoDocumento.setModel(model);
    }*/
      /*  private void mostrarPaises()
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

    }*/

   /* private void mostrarProvincias()
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
    }*/

    /*private void mostrarLocalidades()
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
    }*/

   /* private void mostrarBarrios()
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
    }*/
   /* private void cargarTelefonos()
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
            listaTipoTel.add((Tupla)fila.get(1));
            listaNroTel.add((String)fila.get(0));
            
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
*/
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
        btnAceptar = new javax.swing.JButton();
        jpCapacitaciones = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCapacitaciones = new javax.swing.JTable();
        jpDatosPersonales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCuil = new javax.swing.JLabel();
        lblTipoDocumento = new javax.swing.JLabel();
        lblNroDocumento = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblFechaRegistro = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblCalleDomicilio = new javax.swing.JLabel();
        lblNroDomicilio = new javax.swing.JLabel();
        lblPisoDomicilio = new javax.swing.JLabel();
        lblDeptoDomicilio = new javax.swing.JLabel();
        lblCPDomicilio = new javax.swing.JLabel();
        lblPais = new javax.swing.JLabel();
        lblProvincia = new javax.swing.JLabel();
        lblLocalidad = new javax.swing.JLabel();
        lblBarrio = new javax.swing.JLabel();
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
        setTitle("Consultar datos de capacitador");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Cerrar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jpCapacitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Capacitaciones"));

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
        tablaCapacitaciones.setComponentPopupMenu(menuCapacitaciones);
        jScrollPane5.setViewportView(tablaCapacitaciones);

        javax.swing.GroupLayout jpCapacitacionesLayout = new javax.swing.GroupLayout(jpCapacitaciones);
        jpCapacitaciones.setLayout(jpCapacitacionesLayout);
        jpCapacitacionesLayout.setHorizontalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );
        jpCapacitacionesLayout.setVerticalGroup(
            jpCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCapacitacionesLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Apellido:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Fecha de Nacimiento:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Email:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel23.setText("CUIL:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel19.setText("Documento:");

        lblNombre.setText("-");

        lblCuil.setText("-");

        lblTipoDocumento.setText("-");

        lblNroDocumento.setText("-");

        lblApellido.setText("-");

        lblEmail.setText("-");

        lblFechaNacimiento.setText("-");

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
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTipoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel23))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(22, 22, 22)
                                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addGap(74, 74, 74)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(lblCuil, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpDatosPersonalesLayout.setVerticalGroup(
            jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblTipoDocumento)
                            .addComponent(lblNroDocumento)))
                    .addGroup(jpDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblApellido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCuil)
                            .addComponent(jLabel23))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lblFechaNacimiento))
                    .addGroup(jpDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(lblEmail)))
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Domicilio"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Calle: ");

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

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel14.setText("Provincia:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel15.setText("Localidad: ");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel16.setText("Barrio:");

        lblCalleDomicilio.setText("-");

        lblNroDomicilio.setText("-");

        lblPisoDomicilio.setText("-");

        lblDeptoDomicilio.setText("-");

        lblCPDomicilio.setText("-");

        lblPais.setText("-");

        lblProvincia.setText("-");

        lblLocalidad.setText("-");

        lblBarrio.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(59, 59, 59)
                        .addComponent(lblCalleDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(76, 76, 76)
                        .addComponent(lblNroDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(66, 66, 66)
                        .addComponent(lblPisoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBarrio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lblLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lblProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lblCPDomicilio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lblDeptoDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lblPais, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCalleDomicilio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNroDomicilio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPisoDomicilio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDeptoDomicilio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblCPDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblPais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(lblProvincia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(lblLocalidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(lblBarrio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnModificarEmpleado)
                        .addGap(34, 34, 34)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        lblNroDocumento.setText("");
        lblApellido.setText("");
        lblCPDomicilio.setText("");
        lblCuil.setText("");
        lblCalleDomicilio.setText("");
        lblDeptoDomicilio.setText("");
        lblEmail.setText("");
        lblFechaNacimiento.setText("");
        //lblFechaIngreso.setText("");
        lblNombre.setText("");
        lblFechaRegistro.setText("");
        lblNroDomicilio.setText("");
        lblPisoDomicilio.setText("");
        
       
        //((DefaultTableModel)tablaEspecialidades.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaTelefonos.getModel()).setNumRows(0);
        ((DefaultTableModel)tablaCapacitaciones.getModel()).setNumRows(0);
        
        //lblLegajo.setText("-");
        
        lblPais.setText("");
        lblProvincia.setText("");
        lblBarrio.setText("");
        lblLocalidad.setText("");
        
    }
        
    private void emQuitarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarTelefonoActionPerformed
      
    }//GEN-LAST:event_emQuitarTelefonoActionPerformed

    private void emQuitarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarEspecialidadActionPerformed
        
    }//GEN-LAST:event_emQuitarEspecialidadActionPerformed

    private void emQuitarCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emQuitarCapacitacionActionPerformed
       
    }//GEN-LAST:event_emQuitarCapacitacionActionPerformed

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed

            
            pantallaRegistrarCapacitador pre = new pantallaRegistrarCapacitador(id, this);
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
    //Modificar Empleado
   
	
        public void tiposCapacitacionesCapacitador(ArrayList<Tupla> lstTipoCapacitaciones)
        {
           
            DefaultTableModel tabCap= (DefaultTableModel)tablaCapacitaciones.getModel();
               //DefaultComboBoxModel cboCap =(DefaultComboBoxModel)lstTiposCapacitacion.getModel();
            for(int i=0; i<lstTipoCapacitaciones.size();i++)
            {               
               /* for (int j= 0; j < cboCap.getSize(); j++)
                {
                    
                    if(lstTipoCapacitaciones.get(i).getId()==((Tupla)cboCap.getElementAt(j)).getId())
                    {cboCap.removeElementAt(j);}
                }*/
               Object[] obj=new Object[2];
               obj[0]=lstTipoCapacitaciones.get(i);
               //obj[1]=FechaUtil.getFecha(lstVencimientosCapacitaciones.get(i));
              tabCap.addRow(obj);                      
            }
            tablaCapacitaciones.setModel(tabCap);
            //lstTiposCapacitacion.setModel(cboCap);
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
               obj[0]=numero.get(i);
               obj[1]=(tipo.get(i));
              tabTel.addRow(obj);
            }
            tablaTelefonos.setModel(tabTel);

	}

        public void datosPersonalesCapacitador(String cuil, String nmroDoc, String tipoDocumento, String nombre, String apellido, Date fechaNac, Date fechaReg,String email)
        {
            //lblLegajo.setText(leg);
            this.nombre=nombre;
            this.apellido=apellido;
            lblCuil.setText(cuil);
            lblNroDocumento.setText(nmroDoc);
            lblNombre.setText(nombre);
            lblApellido.setText(apellido);
            lblEmail.setText(email);
            lblTipoDocumento.setText(tipoDocumento);
            lblFechaNacimiento.setText(FechaUtil.getFecha(fechaNac));            
            lblFechaRegistro.setText(FechaUtil.getFecha(fechaReg));
            //lblFechaRegistro.setText(fechaReg.toString());
           /* if(fechaIng!=null)
            {lblFechaIngreso.setText(FechaUtil.getFecha(fechaIng));}
            else
            {lblFechaIngreso.setText("-");}*/

	}

        public void datosDomicilioCapacitador(String calle, String nro, String depto, String piso, String cp, String pais, String provincia, String localidad, String barrio)
        {
            lblCalleDomicilio.setText(calle);
            if(!nro.equals("0"))
            {lblNroDomicilio.setText(nro);}
            lblDeptoDomicilio.setText(depto);
            if(!piso.equals("0"))
            {lblPisoDomicilio.setText(piso);}
            lblCPDomicilio.setText(cp);
            lblPais.setText(pais);
            lblProvincia.setText(provincia);
            lblLocalidad.setText(localidad);
            lblBarrio.setText(barrio);
           
	}
        public void datosDomicilioCapacitador(String calle, String nro, String depto, String piso, String cp)
        {
            lblCalleDomicilio.setText(calle);
            if(!nro.equals("0"))
            {lblNroDomicilio.setText(nro);}
            else
            {lblNroDomicilio.setText("-");}
            lblDeptoDomicilio.setText(depto);
            if(!piso.equals("0"))
            {lblPisoDomicilio.setText(piso);}
            else
            {lblPisoDomicilio.setText("-");}
            lblCPDomicilio.setText(cp);

	}
        //Fin Metodos Modificar Empleado
    /////////////////////////////////////////////////////

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JMenuItem emQuitarCapacitacion;
    private javax.swing.JMenuItem emQuitarEspecialidad;
    private javax.swing.JMenuItem emQuitarTelefono;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel jpCapacitaciones;
    private javax.swing.JPanel jpDatosPersonales;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblBarrio;
    private javax.swing.JLabel lblCPDomicilio;
    private javax.swing.JLabel lblCalleDomicilio;
    private javax.swing.JLabel lblCuil;
    private javax.swing.JLabel lblDeptoDomicilio;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFechaRegistro;
    private javax.swing.JLabel lblLocalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNroDocumento;
    private javax.swing.JLabel lblNroDomicilio;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPisoDomicilio;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblTipoDocumento;
    private javax.swing.JPopupMenu menuCapacitaciones;
    private javax.swing.JPopupMenu menuEspecialidades;
    private javax.swing.JPopupMenu menuTelefonos;
    private javax.swing.JTable tablaCapacitaciones;
    private javax.swing.JTable tablaTelefonos;
    // End of variables declaration//GEN-END:variables

    public void actualizar(int n, boolean exito)
    {   //Uso el int n para mandar el legajo en vez del error, necesito algo que comunique las ventanas
       vaciarCampos();
        if(exito)
        { boolean r= gestor.levantarCapacitador(n);
            if (!r)
            {
                JOptionPane.showMessageDialog(this.getParent(),"Error levantando el capacitador de la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        pantallaConsultar.actualizar(5, exito);
    }
    public int getIdAyuda()
    {
        return 0;
    }

    public String getResumenAyuda() {
        return "Seleccione Modificar si desea cambiar los datos del capacitador";
    }

    public String getTituloAyuda() {
        
        return "Opción: Consultar datos del capacitador";
       
    }
}
