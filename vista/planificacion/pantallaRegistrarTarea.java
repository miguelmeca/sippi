/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaRegistrarEtapa.java
 *
 * Created on 06-sep-2010, 22:16:38
 */

package vista.planificacion;

import controlador.planificacion.GestorRegistrarAsignacionMateriales;
import controlador.planificacion.GestorRegistrarAsignacionHerramientas;
import controlador.planificacion.GestorRegistrarTarea;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import util.NTupla;
import util.StringUtil;
import util.SwingPanel;
import util.TablaUtil;
import util.Tupla;
import java.util.Vector;
import com.toedter.calendar.JDateChooser;
//import vista.comer.pantallaBuscarEmpresaCliente;
import java.util.HashMap;
import java.util.Map;
import vista.interfaces.ICallBack_v2;

/**
 *
 * @author Administrador
 */
public class pantallaRegistrarTarea extends javax.swing.JInternalFrame implements ICallBack_v2 {
    private GestorRegistrarAsignacionMateriales gestorRAM;
    private GestorRegistrarAsignacionHerramientas gestorRAH;
    private GestorRegistrarTarea gestorTarea;
    private ArrayList<Tupla> listaRolNombre;
    private ArrayList<Tupla> listaRolTipoEspecialidadRol;
    private ArrayList<Tupla> listaRolRangoEspecialidad;
    private ArrayList<Double> listaRolHsNormales;
    private ArrayList<Double> listaRolHs50;
    private ArrayList<Double> listaRolHs100;
    private ICallBack_v2 pantallaEtapa;
    private int idTarea;
    private int idEtapa;
    private int idPresupuesto;
    //private int idP;
    /** Creates new form pantallaRegistrarEtapa */
    public pantallaRegistrarTarea() {
        initComponents();
        this.tbHerramientasDisponibles.setDefaultRenderer(Object.class, new MiRender());
        idPresupuesto=-1;
        idTarea=-1;
        this.idEtapa= -1;
        this.idPresupuesto=-1;
        gestorTarea=new GestorRegistrarTarea(this);
        gestorRAM = new GestorRegistrarAsignacionMateriales(this);
        gestorRAH = new GestorRegistrarAsignacionHerramientas(this);
//        gestorRAM.crearTareaPrueba();
        habilitarVentana();
        gestorRAH.setIdTarea(this.idTarea);
        gestorRAM.setIdTarea(this.idTarea);
    }
    public pantallaRegistrarTarea(ICallBack_v2 pantallaEt, int idPresupuesto, int idEtapa ) {
        initComponents();
        pantallaEtapa=pantallaEt;
        this.idEtapa= idEtapa;
        this.idPresupuesto=idPresupuesto;
        this.tbHerramientasDisponibles.setDefaultRenderer(Object.class, new MiRender());
        //idP=1;
        idTarea=-1;
        gestorTarea=new GestorRegistrarTarea(this);
        gestorRAM = new GestorRegistrarAsignacionMateriales(this);
        gestorRAH = new GestorRegistrarAsignacionHerramientas(this);
//        gestorRAM.crearTareaPrueba();
        habilitarVentana();
        gestorRAH.setIdTarea(this.idTarea);
        gestorRAM.setIdTarea(this.idTarea);
    }
    public pantallaRegistrarTarea(ICallBack_v2 pantallaEt,int idPresupuesto, int idEtapa, int idTare) {
        idTarea=idTare;
        this.idEtapa= idEtapa;
        this.idPresupuesto=idPresupuesto;
        //idP=1;
        initComponents();
        pantallaEtapa=pantallaEt;
        this.tbHerramientasDisponibles.setDefaultRenderer(Object.class, new MiRender());
        gestorTarea=new GestorRegistrarTarea(this);
        gestorRAM = new GestorRegistrarAsignacionMateriales(this);
        gestorRAH = new GestorRegistrarAsignacionHerramientas(this);
//        gestorRAM.crearTareaPrueba();
        gestorRAH.setIdTarea(gestorRAM.getIdTarea());
        habilitarVentana();

    }

    private void habilitarVentana()
    {
        listaRolNombre= new ArrayList<Tupla>();
        listaRolTipoEspecialidadRol= new  ArrayList<Tupla>();
        listaRolRangoEspecialidad = new ArrayList<Tupla>();
        listaRolHsNormales = new ArrayList<Double>();
        listaRolHs50 = new ArrayList<Double>();
        listaRolHs100 = new ArrayList<Double>();
        mostrarTiposEspecialidad();
        mostrarRangosEspecialidad();
        mostrarCriticidad();
        vaciarCampos();
        if(idTarea==-1)
        { crearTarea(idEtapa);}
        cargarDatosTarea();
        
        cargarRolesCreadosAnteriormente();
    }
    private void cargarRolesCreadosAnteriormente()
    {
        ArrayList<NTupla> listaNombreRangosEspecialidad = gestorTarea.mostrarRolesCreados(idPresupuesto);
        //DefaultComboBoxModel model = new DefaultComboBoxModel();
        DefaultTableModel model = (DefaultTableModel) tablaRolesCreadosEnOtrasTareas.getModel();
        int fil=model.getRowCount();
        for (int i = 0; i < fil; i++) {
            model.removeRow(0);
        }

        for (NTupla nTuplaEmpleado : listaNombreRangosEspecialidad)
        {
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            Object[] obj=new Object[((Object[])nTuplaEmpleado.getData()).length+1];
            //obj[0]=nTuplaEmpleado.getId();
            Tupla tup=new Tupla();
            tup.setId(nTuplaEmpleado.getId());
            tup.setNombre(nTuplaEmpleado.getNombre());
            obj[0]=tup;

            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            System.arraycopy((Object[]) nTuplaEmpleado.getData(), 0, obj, 1, ((Object[]) nTuplaEmpleado.getData()).length);
            model.addRow( obj );
        }
        tablaRolesCreadosEnOtrasTareas.setModel(model);
        //lstRolesCreados.setModel(model);

    }


    private void vaciarCampos()
    {//TODO:

    }
    
    private void crearTarea(int idEtapa)
    {
        idTarea=gestorTarea.crearTarea(idEtapa);
     if(idTarea<0)
     {JOptionPane.showInternalMessageDialog(this.getParent(),"Ocurrio un error creando al Tarea","ERROR",JOptionPane.ERROR_MESSAGE);
       this.dispose();}
    }

    private void cargarDatosTarea()
    {//TODO:

    }

    public void mostrarRangosEspecialidad()
    {
        ArrayList<Tupla> listaNombreRangosEspecialidad = gestorTarea.mostrarRangosEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreRangosEspecialidad)
        {
            model.addElement(nombre);
        }
        cmbRangos.setModel(model);
    }
    public void mostrarTiposEspecialidad()
    {
        ArrayList<Tupla> listaNombreTiposEspecialidad = gestorTarea.mostrarTiposEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla nombre : listaNombreTiposEspecialidad)
        {
            model.addElement(nombre);
        }
        cmbEspecialidades.setModel(model);
    }
    public void mostrarCriticidad()
    {
        ArrayList<Tupla> listaNombreCriticidad = gestorTarea.mostrarCriticidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Tupla criticidad : listaNombreCriticidad)
        {
            model.addElement(criticidad);
        }

        cmbCriticidad.setModel(model);
    }
    private void cargarTabMateriales() {
        ArrayList<NTupla> materiales = gestorRAM.getMaterialesDisponibles();

        DefaultTableModel modelo = (DefaultTableModel)tbMateriales.getModel();

        // VACIO LA TABLA MATERIALES
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();

        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        DefaultTableModel modelo2 = (DefaultTableModel)tbMaterialEspecifico.getModel();

        // VACIO LA TABLA MATERIAL ESPECIFICO
        TablaUtil.vaciarDefaultTableModel(modelo2);
    }


    private void mostrarEspecificacionMaterial(int id) {
        ArrayList<Tupla> esps = gestorRAM.getEspecificacionMaterial(id);

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialEspecifico.getModel();

        // VACIO LA TABLA
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = esps.iterator();

        while (it.hasNext())
        {
            Tupla ntp = (Tupla)it.next();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
    }

    private void mostrarMaterialesAUtilizar() {
        ArrayList<NTupla> materiales = gestorRAM.getMaterialesAUtilizar();

        DefaultTableModel modelo = (DefaultTableModel)tbMaterialesAUsar.getModel();

        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = materiales.iterator();

        int cantidad = 0;
        double precio = 0;
        double total = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)it.next();
            Object[] fila = new Object[4];
            fila[0] = ntp;
            Object[] o = (Object[]) ntp.getData();
            fila[1] = o[0];
            fila[2] = o[1];
            cantidad = (Integer)o[1];
            precio = (Double)o[2];
            double subtotal = cantidad*precio;
            fila[3] = subtotal;
            total+=subtotal;
            modelo.addRow(fila);
        }
        txtSubtotalMateriales.setText("$ "+total);
    }

    private void mostrarHerramientasAUtilizar() {
//        ArrayList<NTupla> herramientas = gestorRAH.getHerramientasAUtilizar();
        HashMap<Integer,NTupla> herramientas = gestorRAH.getHerramientasAUtilizar();

        DefaultTableModel modelo = (DefaultTableModel)tbHerramientasSeleccionadas.getModel();

        // VACIO LA TABLA HERRAMIENTAS
        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = herramientas.entrySet().iterator();

        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)((Map.Entry)it.next()).getValue();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        // ACA FALTA PONER LAS HERRAMIENTAS YA SELECCIONADAS
    }

//    private void cargarHerramientas(ArrayList<NTupla> herramientas) {
    private void cargarHerramientas(HashMap<Integer,NTupla> herramientas) {

        DefaultTableModel modelo = (DefaultTableModel)tbHerramientasDisponibles.getModel();
//
//        // VACIO LA TABLA HERRAMIENTAS
//        TablaUtil.vaciarDefaultTableModel(modelo);

        Iterator it = herramientas.entrySet().iterator();

        int i = 0;
        while (it.hasNext())
        {
            NTupla ntp = (NTupla)((Map.Entry)it.next()).getValue();
            Object[] fila = new Object[1];
            fila[0] = ntp;

            modelo.addRow(fila);
        }
        // ACA FALTA PONER LAS HERRAMIENTAS YA SELECCIONADAS
    }

    private void FiltrarTabla(JTable table,JTextField field){
       TableRowSorter<TableModel> modeloOrdenado;
       modeloOrdenado = new TableRowSorter<TableModel>(table.getModel());
       table.setRowSorter(modeloOrdenado);

           String[] cadena=field.getText().trim().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);
           modeloOrdenado.setRowFilter(cadenaFilter);

           // CAMBIO LOS TAMAÃƒâ€˜OS DE LAS FILAS
           DefaultTableModel modelo = (DefaultTableModel) table.getModel();
           for (int i = 0; i < modelo.getRowCount(); i++)
           {
            // REDIMENSIONO LA FILA !!! -----------------------------------
                int index = modeloOrdenado.convertRowIndexToView(i);
                if(index>-1)
                {
                    // ESTA
//                    String item = (String) modelo.getValueAt(i,0);
                    String item = ((NTupla) modelo.getValueAt(i,0)).getNombre() ;
                    int cantItems = StringUtil.cantidadOcurrencias(item,"<b>x</b>");
                    if(cantItems!=0)
                    {
                        table.setRowHeight(index,16*cantItems);
                    }
                    //LogUtil.addDebug("ConsultarPreciosXProveedor: Cantidad de Repeticiones: "+cantItems);
                }
                // REDIMENSIONO LA FILA !!! -----------------------------------
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

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreTarea = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbCriticidad = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtUbicacionTarea = new javax.swing.JTextField();
        cmbFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbFechaFin = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMaterialesAUsar = new javax.swing.JTable();
        txtSubtotalMateriales = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnAgregarMaterialesOtrasTareas = new javax.swing.JButton();
        btnAgregarMaterial = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbMateriales = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbMaterialEspecifico = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarMaterial = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnQuitarMaterial = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNombreRol = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbEspecialidades = new javax.swing.JComboBox();
        cmbRangos = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablaRolesCreadosEnOtrasTareas = new javax.swing.JTable();
        btnCrearRol = new javax.swing.JButton();
        btnAgregarRol = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtHs100 = new javax.swing.JTextField();
        txtHs50 = new javax.swing.JTextField();
        txtHsNormales = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaRolesAsignados = new javax.swing.JTable();
        btnQuitarRol = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscarHerramientas = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbHerramientasDisponibles = new javax.swing.JTable();
        btnAgregarHerramienta = new javax.swing.JButton();
        btnQuitarHerramienta = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbHerramientasSeleccionadas = new javax.swing.JTable();
        btnAceptar = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setClosable(true);
        setTitle("Nueva Tarea");

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel1.setText("Nombre:");

        jLabel2.setText("Nivel de Criticidad:");

        cmbCriticidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Crítico", "Importante", "Media", "Baja", "Muy Baja" }));

        jLabel5.setText("Observaciones:");

        txaObservaciones.setColumns(20);
        txaObservaciones.setRows(5);
        jScrollPane5.setViewportView(txaObservaciones);

        jLabel4.setText("Ubicacion:");

        jLabel11.setText("Fecha de Inicio:");

        jLabel13.setText("Fecha de Fin:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreTarea, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(txtUbicacionTarea, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbCriticidad, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addGap(308, 308, 308))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUbicacionTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCriticidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Materiales a Utilizar"));

        tbMaterialesAUsar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Nombre", "Cantidad", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tbMaterialesAUsar);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setMinWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setPreferredWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(2).setMaxWidth(80);
        tbMaterialesAUsar.getColumnModel().getColumn(3).setMinWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(3).setPreferredWidth(70);
        tbMaterialesAUsar.getColumnModel().getColumn(3).setMaxWidth(80);

        txtSubtotalMateriales.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Subtotal Materiales:");

        btnAgregarMaterialesOtrasTareas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/promotion.png"))); // NOI18N
        btnAgregarMaterialesOtrasTareas.setText("Agregar Materiales de Otras Tareas");
        btnAgregarMaterialesOtrasTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMaterialesOtrasTareasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnAgregarMaterialesOtrasTareas)
                        .addGap(119, 119, 119)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(txtSubtotalMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMaterialesOtrasTareas)
                    .addComponent(jLabel6)
                    .addComponent(txtSubtotalMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnAgregarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarMaterial.setText("Agregar");
        btnAgregarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMaterialActionPerformed(evt);
            }
        });

        tbMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ));
        tbMateriales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMaterialesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbMateriales);

        tbMaterialEspecifico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especificación"
            }
        ));
        jScrollPane7.setViewportView(tbMaterialEspecifico);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtBuscarMaterial.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscarMaterial.setForeground(java.awt.Color.gray);
        txtBuscarMaterial.setText("Buscar...");
        txtBuscarMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarMaterialFocusGained(evt);
            }
        });
        txtBuscarMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMaterialKeyReleased(evt);
            }
        });

        jLabel8.setText("Seleccione un Material:");

        btnQuitarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/up.png"))); // NOI18N
        btnQuitarMaterial.setText("Quitar");
        btnQuitarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarMaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAgregarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(txtBuscarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMaterial)
                    .addComponent(btnQuitarMaterial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Utilización de Materiales", jPanel2);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Crear un Rol"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Nombre:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel22.setText("Especialidad");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel24.setText("Rango");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombreRol, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel22)
                            .addComponent(cmbEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(cmbRangos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRangos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Roles creados en otras tareas:"));

        tablaRolesCreadosEnOtrasTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre del Rol", "Especialidad", "Rango"
            }
        ));
        jScrollPane14.setViewportView(tablaRolesCreadosEnOtrasTareas);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCrearRol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnCrearRol.setText("Crear Rol");
        btnCrearRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearRolActionPerformed(evt);
            }
        });

        btnAgregarRol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarRol.setText("Agregar Rol Existente");
        btnAgregarRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRolActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cantidad de trabajo a realizar en esta tarea"));

        jLabel7.setText("Horas Normales      Horas 50%         Horas 100%");

        jLabel23.setText("Hs.");

        jLabel25.setText("Hs. ");

        jLabel26.setText("Hs.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHs50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHs100, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtHs50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtHs100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Grupo de Trabajo Asignado a Esta Tarea"));

        tablaRolesAsignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre del Rol", "Especialidad", "Rango", "Hs Normales", "Hs 50", "Hs 100"
            }
        ));
        jScrollPane13.setViewportView(tablaRolesAsignados);

        btnQuitarRol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarRol.setText("Quitar seleccionado");
        btnQuitarRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarRolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnQuitarRol)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnQuitarRol, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnCrearRol, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116)
                        .addComponent(btnAgregarRol))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearRol)
                    .addComponent(btnAgregarRol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(242, 242, 242))
        );

        jTabbedPane1.addTab("Grupo de Trabajo", jPanel4);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione una Herramienta a utilizar en la Tarea"));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        txtBuscarHerramientas.setFont(new java.awt.Font("Tahoma", 2, 11));
        txtBuscarHerramientas.setForeground(java.awt.Color.gray);
        txtBuscarHerramientas.setText("Buscar...");
        txtBuscarHerramientas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarHerramientasFocusGained(evt);
            }
        });
        txtBuscarHerramientas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarHerramientasKeyReleased(evt);
            }
        });

        tbHerramientasDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la Herramienta"
            }
        ));
        jScrollPane8.setViewportView(tbHerramientasDisponibles);

        btnAgregarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAgregarHerramienta.setText("Agregar");
        btnAgregarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHerramientaActionPerformed(evt);
            }
        });

        btnQuitarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/up.png"))); // NOI18N
        btnQuitarHerramienta.setText("Quitar");
        btnQuitarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarHerramientaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnAgregarHerramienta, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarHerramienta, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscarHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarHerramienta)
                    .addComponent(btnQuitarHerramienta)))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Herramientas a utilizar en la Tarea"));

        tbHerramientasSeleccionadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la Herramienta"
            }
        ));
        jScrollPane9.setViewportView(tbHerramientasSeleccionadas);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Herramientas Necesarias", jPanel10);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Guardar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAceptar)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptar)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMaterialActionPerformed
        if(tbMaterialEspecifico.getSelectedRow()>=0){
            Tupla re = (Tupla)(tbMaterialEspecifico.getModel()).getValueAt(tbMaterialEspecifico.getSelectedRow(), 0);
            NTupla r = (NTupla)(tbMateriales.getModel()).getValueAt(tbMateriales.getSelectedRow(), 0);
            pantallaSeleccionarProveedorPresupuesto psp = new pantallaSeleccionarProveedorPresupuesto(this.gestorRAM,r.getId(),re.getId());
            SwingPanel.getInstance().addWindow(psp);
            psp.setVisible(true);
        }
        
    }//GEN-LAST:event_btnAgregarMaterialActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        //System.out.print("INDICE: "+jTabbedPane1.getSelectedIndex());
        if(jTabbedPane1.getSelectedIndex() == 1){
            cargarTabMateriales();
            this.mostrarMaterialesAUtilizar();
        }
        if(jTabbedPane1.getSelectedIndex() == 3){
            TablaUtil.vaciarDefaultTableModel((DefaultTableModel) tbHerramientasDisponibles.getModel());
//            cargarHerramientas(gestorRAH.getHerramientasPresupuesto(idP));
//            cargarHerramientas(gestorRAH.getHerramientasDeEmpresaDisponibles());
            cargarHerramientas(gestorRAH.getHerramientasDeEmpresa(idPresupuesto));
            this.mostrarHerramientasAUtilizar();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tbMaterialesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaterialesMouseReleased
        if(tbMateriales.getSelectedRow()!=-1 && tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0) instanceof NTupla)
        {
            NTupla t = (NTupla)tbMateriales.getValueAt(tbMateriales.getSelectedRow(),0);
            mostrarEspecificacionMaterial(t.getId());
        }
    }//GEN-LAST:event_tbMaterialesMouseReleased

    private void btnAgregarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHerramientaActionPerformed
        if(tbHerramientasDisponibles.getSelectedRow()>=0){
            NTupla hd = (NTupla)(tbHerramientasDisponibles.getModel()).getValueAt(tbHerramientasDisponibles.getSelectedRow(), 0);
            gestorRAH.agregarHerramienta(hd.getId());
            this.mostrarHerramientasAUtilizar();
        }
    }//GEN-LAST:event_btnAgregarHerramientaActionPerformed

    private void btnQuitarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarMaterialActionPerformed
        if(tbMaterialesAUsar.getSelectedRow()>=0){
            NTupla nt = (NTupla)tbMaterialesAUsar.getModel().getValueAt(tbMaterialesAUsar.getSelectedRow(), 0);
            if(gestorRAM.quitarMaterial(nt.getId())){
                DefaultTableModel dtm = (DefaultTableModel)tbMaterialesAUsar.getModel();
                dtm.removeRow(tbMaterialesAUsar.getSelectedRow());
                this.mostrarMaterialesAUtilizar();
                this.cargarTabMateriales();
            }
        }
    }//GEN-LAST:event_btnQuitarMaterialActionPerformed

    private void btnQuitarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarHerramientaActionPerformed
        if(tbHerramientasSeleccionadas.getSelectedRow()>=0){
            NTupla nt = (NTupla)tbHerramientasSeleccionadas.getModel().getValueAt(tbHerramientasSeleccionadas.getSelectedRow(), 0);
            if(gestorRAH.quitarHerramienta(nt.getId())){
                DefaultTableModel dtm = (DefaultTableModel)tbHerramientasSeleccionadas.getModel();
                dtm.removeRow(tbHerramientasSeleccionadas.getSelectedRow());
                this.mostrarHerramientasAUtilizar();
            }
        }
    }//GEN-LAST:event_btnQuitarHerramientaActionPerformed

    private void txtBuscarMaterialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMaterialKeyReleased
        this.FiltrarTabla(this.tbMateriales, txtBuscarMaterial);
    }//GEN-LAST:event_txtBuscarMaterialKeyReleased

    private void txtBuscarMaterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarMaterialFocusGained
        txtBuscarMaterial.setText("");
    }//GEN-LAST:event_txtBuscarMaterialFocusGained

    private void txtBuscarHerramientasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarHerramientasKeyReleased
        this.FiltrarTabla(this.tbHerramientasDisponibles, this.txtBuscarHerramientas);
    }//GEN-LAST:event_txtBuscarHerramientasKeyReleased

    private void txtBuscarHerramientasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarHerramientasFocusGained
        this.txtBuscarHerramientas.setText("");
    }//GEN-LAST:event_txtBuscarHerramientasFocusGained

    private void btnQuitarRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarRolActionPerformed
        quitarRol();
}//GEN-LAST:event_btnQuitarRolActionPerformed
    private void quitarRol()
    {
         if((tablaRolesAsignados.getSelectedRowCount())==1)
        {
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesAsignados.getModel();
        /////////////////////////
        if(  ((Tupla)modelo.getValueAt(tablaRolesAsignados.getSelectedRow(), 0)).getId()!=-1)
        {
        DefaultTableModel modeloRCOT = (DefaultTableModel) tablaRolesCreadosEnOtrasTareas.getModel();
        ;
        Object[] item = new Object[3];
        item[0] = ((Tupla)(tablaRolesAsignados.getModel().getValueAt(tablaRolesAsignados.getSelectedRow(), 0)));
        item[1] = ((Tupla)(tablaRolesAsignados.getModel().getValueAt(tablaRolesAsignados.getSelectedRow(), 1)));
        item[2] = ((Tupla)(tablaRolesAsignados.getModel().getValueAt(tablaRolesAsignados.getSelectedRow(), 2)));

        modeloRCOT.addRow(item);
        }
        ///////////////////////////
        modelo.removeRow(tablaRolesAsignados.getSelectedRow());
        }
    }
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(validarDatos())
        {
            gestorTarea.vaciarDatos();
            cargarGrupoDeTrabajo();
            
            Date fechaInicio = ((JDateChooser) cmbFechaInicio).getDate();
            Date fechaFin = ((JDateChooser) cmbFechaFin).getDate();
            Tupla tCriticidad=new Tupla();
            tCriticidad=(Tupla)cmbCriticidad.getItemAt(cmbCriticidad.getSelectedIndex());
            
            gestorTarea.datosGenerales(txtNombreTarea.getText(), txtUbicacionTarea.getText() ,txaObservaciones.getText(),tCriticidad, fechaInicio,fechaFin );
            gestorTarea.datosGrupoRoles(listaRolNombre,listaRolTipoEspecialidadRol,listaRolRangoEspecialidad,listaRolHsNormales,listaRolHs50,listaRolHs100);
            
            if(gestorTarea.tareaModificada())
            {
                        JOptionPane.showMessageDialog(this.getParent(),"Tarea registrada correctamente","Tarea Registrada",JOptionPane.INFORMATION_MESSAGE);
                        //Uso el metodo actualizar para mandar el legajo en vez del error, necesito algo que comunique las ventanas
                        pantallaEtapa.actualizar(idTarea,"RegistrarTarea", true);
                        this.dispose();
            }
            else
            {
             JOptionPane.showMessageDialog(this.getParent(),"Ocurrio un error durante el registro de la tarea","ERROR",JOptionPane.ERROR_MESSAGE);
             //pantallaConsultar.actualizar(3, false);
             this.dispose();
            }
             
            
            }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCrearRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearRolActionPerformed
        crearRol();
    }//GEN-LAST:event_btnCrearRolActionPerformed

    private void btnAgregarRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRolActionPerformed
        if(tablaRolesCreadosEnOtrasTareas.getSelectedRow()!=-1)
        {
        /*int idRC;
        idRC=((Tupla)(tablaRolesCreadosEnOtrasTareas.getModel().getValueAt(tablaRolesCreadosEnOtrasTareas.getSelectedRow(), 0))).getId();
        if(!validarRolViejoNoAsignado(idRC))
        {JOptionPane.showInternalMessageDialog(this.getParent(),"No puede asignar el mismo rol dos veces a la misma tarea","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}*/
        double hn=0;
        double h50=0;
        double h100=0;
        try{
        if(txtHsNormales.getText().equals(""))
        {hn=0;}
        else
        {hn= Double.parseDouble(txtHsNormales.getText()) ;}

        if(txtHs50.getText().equals(""))
        {h50=0;}
        else
        {h50= Double.parseDouble(txtHs50.getText()) ;}

        if(txtHs100.getText().equals(""))
        {h100=0;}
        else
        {h100= Double.parseDouble(txtHs100.getText()) ;}

        if(hn<0.0 || h50<0.0 || h100<0.0)
        {
        JOptionPane.showInternalMessageDialog(this.getParent(),"La cantidad de horas de trabajo ingresadas es invalida","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }
        if(hn==0.0 && h50==0.0 && h100==0.0)
        {
        JOptionPane.showInternalMessageDialog(this.getParent(),"Debe ingresar la cantidad de horas de trabajo en esta tarea","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }

        }catch(Exception e)
        {JOptionPane.showInternalMessageDialog(this.getParent(),"La cantidad de horas de trabajo ingresadas es invalida","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }
        /////////////////
        DefaultTableModel modeloTRA = (DefaultTableModel) tablaRolesAsignados.getModel();
        ;
        Object[] item = new Object[6];
        item[0] = ((Tupla)(tablaRolesCreadosEnOtrasTareas.getModel().getValueAt(tablaRolesCreadosEnOtrasTareas.getSelectedRow(), 0)));
        item[1] = ((Tupla)(tablaRolesCreadosEnOtrasTareas.getModel().getValueAt(tablaRolesCreadosEnOtrasTareas.getSelectedRow(), 1)));
        item[2] = ((Tupla)(tablaRolesCreadosEnOtrasTareas.getModel().getValueAt(tablaRolesCreadosEnOtrasTareas.getSelectedRow(), 2)));
        item[3] = hn;
        item[4] = h50;
        item[5] = h100;
        modeloTRA.addRow(item);
        txtHsNormales.setText("");
        txtHs50.setText("");
        txtHs100.setText("");
        ((DefaultTableModel)tablaRolesCreadosEnOtrasTareas.getModel()).removeRow(tablaRolesCreadosEnOtrasTareas.getSelectedRow());
       
        }

    }//GEN-LAST:event_btnAgregarRolActionPerformed

    private void btnAgregarMaterialesOtrasTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMaterialesOtrasTareasActionPerformed
        pantallaSeleccionarMaterialesEtapa psme = new pantallaSeleccionarMaterialesEtapa(gestorRAM, idEtapa,this.idTarea);
        SwingPanel.getInstance().addWindow(psme);
        psme.setVisible(true);
    }//GEN-LAST:event_btnAgregarMaterialesOtrasTareasActionPerformed
private void crearRol()
    {
        if(txtNombreRol.getText().equals(""))
        {JOptionPane.showInternalMessageDialog(this.getParent(),"Debe ingresar el nombre del rol a crear","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}
        if(cmbEspecialidades.getSelectedIndex()==-1)
        {JOptionPane.showInternalMessageDialog(this.getParent(),"Debe seleccionar una especialidad","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}
        if(cmbRangos.getSelectedIndex()==-1)
        {JOptionPane.showInternalMessageDialog(this.getParent(),"Debe seleccionar un rango de la especialidad","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}
        if(!validarNombreRolNuevoOtrasTareas(txtNombreRol.getText()))
        {JOptionPane.showInternalMessageDialog(this.getParent(),"Para crear un rol nuevo, debe ingresar un nombre diferente al de los roles creados anteriormente en otra tarea. O puede seleccionar un rol creado anteriormente","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}
        if(!validarNombreRolNuevoEstaTarea(txtNombreRol.getText()))
        {JOptionPane.showInternalMessageDialog(this.getParent(),"No puede crear dos roles con el mismo nombre","ERROR",JOptionPane.ERROR_MESSAGE);
        return;}
        //TODO:Comparar con nombres de roles creados en otras tareas)
        //if(txtNombreRol.getText(){}
        //TODO: Agregar boton  en la ventana para poder modificar nombres de roles creados en otras tareas
        
        double hn=0;
        double h50=0;
        double h100=0;
        try{
        if(txtHsNormales.getText().equals(""))
        {hn=0;}
        else
        {hn= Double.parseDouble(txtHsNormales.getText()) ;}

        if(txtHs50.getText().equals(""))
        {h50=0;}
        else
        {h50= Double.parseDouble(txtHs50.getText()) ;}

        if(txtHs100.getText().equals(""))
        {h100=0;}
        else
        {h100= Double.parseDouble(txtHs100.getText()) ;}

        if(hn<0.0 || h50<0.0 || h100<0.0)
        {
        JOptionPane.showInternalMessageDialog(this.getParent(),"La cantidad de horas de trabajo ingresadas es invalida","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }
        if(hn==0.0 && h50==0.0 && h100==0.0)
        {
        JOptionPane.showInternalMessageDialog(this.getParent(),"Debe ingresar la cantidad de horas de trabajo en esta tarea","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }

        }catch(Exception e)
        {JOptionPane.showInternalMessageDialog(this.getParent(),"La cantidad de horas de trabajo ingresadas es invalida","ERROR",JOptionPane.ERROR_MESSAGE);
        return;
        }
        Tupla tNombre=new Tupla();
        tNombre.setId(-1);
        tNombre.setNombre(txtNombreRol.getText());
        Tupla rango=(Tupla)cmbRangos.getSelectedItem();
        Tupla tipo=(Tupla)cmbEspecialidades.getSelectedItem();
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesAsignados.getModel();
        Object[] item = new Object[6];
        item[0] = tNombre;
        item[1] = tipo;
        item[2] = rango;
        item[3] = hn;
        item[4] = h50;
        item[5] = h100;
        modelo.addRow(item);
        txtHsNormales.setText("");
        txtHs50.setText("");
        txtHs100.setText("");
    }
    private boolean validarNombreRolNuevoEstaTarea(String nom)
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesAsignados.getModel();
        Iterator it = modelo.getDataVector().iterator();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
            if(((Tupla)fila.get(0)).getNombre().equals(nom) )
            {return false;}
        }
        return true;
    }

    private boolean validarNombreRolNuevoOtrasTareas(String nom)
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesCreadosEnOtrasTareas.getModel();
        Iterator it = modelo.getDataVector().iterator();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
            if(((Tupla)fila.get(0)).getNombre().equals(nom) )
            {return false;}
        }
        return true;
    }
   /* private boolean validarRolViejoNoAsignado(int idRolViejo)
    {   
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesAsignados.getModel();
        Iterator it = modelo.getDataVector().iterator();
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
            if(((Tupla)fila.get(0)).getId()==idRolViejo )
            {return false;}
        }
        return true;
    }*/

    private boolean validarDatos()
    {//TODO: Si usamos fechas, validar las fechas y las cantidades de horas d 
        //cada tipo, segun el rango d dias y dependiendo de q dias d la semana sean
        if(txtNombreTarea.getText().equals(""))
        {JOptionPane.showInternalMessageDialog(this.getParent(),"Se debe ingresar un nombre para la tarea","ERROR",JOptionPane.ERROR_MESSAGE);
        return false;
        }
        return true;
    }
    private void cargarGrupoDeTrabajo()
    {
        
        DefaultTableModel modelo = (DefaultTableModel) tablaRolesAsignados.getModel();
        Iterator it = modelo.getDataVector().iterator();
        listaRolNombre= new ArrayList<Tupla>();
        listaRolTipoEspecialidadRol= new  ArrayList<Tupla>();
        listaRolRangoEspecialidad = new ArrayList<Tupla>();
        listaRolHsNormales = new ArrayList<Double>();
        listaRolHs50 = new ArrayList<Double>();
        listaRolHs100 = new ArrayList<Double>();
        
        while (it.hasNext())
        {
            Vector fila = (Vector)it.next();
           // 
            
            listaRolNombre.add((Tupla)fila.get(0));
            listaRolTipoEspecialidadRol.add((Tupla)fila.get(1));
            listaRolRangoEspecialidad.add((Tupla)fila.get(2));
            listaRolHsNormales.add((Double)fila.get(3));
            listaRolHs50.add((Double)fila.get(4));
            listaRolHs100.add((Double)fila.get(5));
            
        }
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarHerramienta;
    private javax.swing.JButton btnAgregarMaterial;
    private javax.swing.JButton btnAgregarMaterialesOtrasTareas;
    private javax.swing.JButton btnAgregarRol;
    private javax.swing.JButton btnCrearRol;
    private javax.swing.JButton btnQuitarHerramienta;
    private javax.swing.JButton btnQuitarMaterial;
    private javax.swing.JButton btnQuitarRol;
    private javax.swing.JComboBox cmbCriticidad;
    private javax.swing.JComboBox cmbEspecialidades;
    private com.toedter.calendar.JDateChooser cmbFechaFin;
    private com.toedter.calendar.JDateChooser cmbFechaInicio;
    private javax.swing.JComboBox cmbRangos;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaRolesAsignados;
    private javax.swing.JTable tablaRolesCreadosEnOtrasTareas;
    private javax.swing.JTable tbHerramientasDisponibles;
    private javax.swing.JTable tbHerramientasSeleccionadas;
    private javax.swing.JTable tbMaterialEspecifico;
    private javax.swing.JTable tbMateriales;
    private javax.swing.JTable tbMaterialesAUsar;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtBuscarHerramientas;
    private javax.swing.JTextField txtBuscarMaterial;
    private javax.swing.JTextField txtHs100;
    private javax.swing.JTextField txtHs50;
    private javax.swing.JTextField txtHsNormales;
    private javax.swing.JTextField txtNombreRol;
    private javax.swing.JTextField txtNombreTarea;
    private javax.swing.JTextField txtSubtotalMateriales;
    private javax.swing.JTextField txtUbicacionTarea;
    // End of variables declaration//GEN-END:variables


        public void MostrarMensaje(String cod)
    {
        if(cod.equals("AM-0001"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Materiales","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0002"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de EspecificaciÃƒÂ³n del material seleccionado","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0003"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Proveedores del Recurso EspecÃ­fico","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0004"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo obtener el nombre del Recurso EspecÃ­fico","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0005"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo calcular el subtotal","Error de CÃ¡lculo",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0006"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo agregar el material a la lista de materiales a utilizar","Error al Agregar",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0007"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar los materiales a utilizar","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0008"))
        {
            // HAY Q BORRARLA CUANDO DEFINAMOS DE DONDE SACO EL ID DE TAREA
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo crear la tarea de prueba","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AM-0009"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar los materiales de otras tareas","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AH-0001"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la lista de Herramientas","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
        if(cod.equals("AH-0002"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo agregar la Herramienta a la lista de Herramientas a utilizar","Error en la Carga",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizar(int id, String flag, boolean exito) {
        if(jTabbedPane1.getSelectedIndex()==1){
        this.mostrarMaterialesAUtilizar();
        this.cargarTabMateriales();
        }
    }

    public class TablaColoreada extends JTable{
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int colIndex) {
            Component component = super.prepareRenderer(renderer, rowIndex, colIndex);
            if((rowIndex%2) == 0){
                ; component.setBackground(Color.GREEN);
            } else {
                ; component.setBackground(Color.YELLOW);
            }
            return component;
        }
    }

    public class MiRender extends DefaultTableCellRenderer
    {
       public Component getTableCellRendererComponent(JTable table,
          Object value,
          boolean isSelected,
          boolean hasFocus,
          int row,
          int column)
       {
          super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
          DefaultTableModel dtm = (DefaultTableModel)table.getModel();
          NTupla nt = (NTupla)dtm.getValueAt(row, 0);
          int idT = (Integer)nt.getData();
          if (idT!=0)
          {
             this.setOpaque(true);
             this.setBackground(Color.BLUE);
             this.setForeground(Color.LIGHT_GRAY);
          }
          else{
             this.setOpaque(true);
             this.setBackground(Color.WHITE);
             this.setForeground(Color.BLACK);
          }
          return this;
       }
    }
}
