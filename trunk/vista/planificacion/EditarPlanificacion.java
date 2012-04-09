/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditarPlanificacion.java
 *
 * Created on 08-ene-2012, 17:13:17
 */
package vista.planificacion;

import vista.planificacion.cotizacion.EditarCotizacionModificada;
import vista.gui.dnd.PanelDropTarget;
import com.hackelare.coolgantt.*;
import com.hackelare.coolgantt.demo.demoEvents;
import com.hackelare.coolgantt.demo.demoTypes;
import com.hackelare.coolgantt.legacy.model.ColorLabel;
import config.Iconos;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import controlador.planificacion.GestorEditarPlanificacion;
import controlador.planificacion.GestorEditarTarea;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import modelo.TareaPlanificacion;
import util.NTupla;
import util.SwingPanel;
import util.TablaUtil;
import vista.cotizacion.ExplorarSubObras;
import vista.gui.dnd.IDropEvent;
import vista.gui.sidebar.IconTreeRenderer;
import vista.interfaces.ICallBack_v2;
import vista.planificacion.arbolTareas.ArbolIconoNodo;
import vista.planificacion.arbolTareas.ArbolIconoRenderer;

/**
 *
 * @author Administrador
 */
public class EditarPlanificacion extends javax.swing.JInternalFrame implements ICallBack_v2{

    public static int TEMPLATE_MODIFICACION = 0;
    public static int TEMPLATE_SOLOLECTURA  = 1;
    
    private static final String iconoSubObra = "/res/iconos/var/16x16/Application.png";
    private static final String iconoTarea = "/res/iconos/var/16x16/calendar.png";
    private final String CADENA_SUB_TAREA = "@";
    private int idObra;
    private int template;
    private GestorEditarPlanificacion _gestor;
    ICoolGantt graph;
    JComponent grafico;
    ArbolTareasGestor arbolTareasGestor;
    private HashMap<Integer,PantallaEditarTarea> ventanasEditarTareasAbiertas;
    
    private int hashSubObraSeleccionada = -1;
   
 public EditarPlanificacion( int idObra) {
        this.idObra = idObra;
        
        ventanasEditarTareasAbiertas = new HashMap<Integer, PantallaEditarTarea>();
        
        _gestor = new GestorEditarPlanificacion(this,idObra);
        initComponents(); 
        
        tblSubObras.setDefaultRenderer(Object.class, new ListaDeTareasRender());
        tblTareas.setDefaultRenderer(Object.class, new ListaDeTareasRender());        
        treeRecursos.setCellRenderer(new IconTreeRenderer());
        treeRecursos.setRootVisible(false);

        initArbolRecursos();
        initDatosGenerales(idObra);
        arbolTareasGestor=new ArbolTareasGestor(arbolTareas);
        inicializarArbolDeTareas();
        initGraph();

        //TODO: Sacar esto, test de DnD
        PanelDropTarget target = new PanelDropTarget(jPanel2, new GanttDropEvent());    
    }    
    
    /**
     * No usea, el template tiene que setearse segun el estado de la planificacion
     * @param template
     * @param idObra
     * @deprecated
     */
    @Deprecated
    public EditarPlanificacion(int template, int idObra) {
        this.template = template;
        this.idObra = idObra;
        
        ventanasEditarTareasAbiertas = new HashMap<Integer, PantallaEditarTarea>();
        
        _gestor = new GestorEditarPlanificacion(this,idObra);
        initComponents(); 
        
        tblSubObras.setDefaultRenderer(Object.class, new ListaDeTareasRender());
        tblTareas.setDefaultRenderer(Object.class, new ListaDeTareasRender());        
        treeRecursos.setCellRenderer(new IconTreeRenderer());
        treeRecursos.setRootVisible(false);

        initArbolRecursos();
        initDatosGenerales(idObra);
        arbolTareasGestor=new ArbolTareasGestor(arbolTareas);
        inicializarArbolDeTareas();
        initGraph();

        //TODO: Sacar esto, test de DnD
        PanelDropTarget target = new PanelDropTarget(jPanel2, new GanttDropEvent());    
    }

    
    
    private void initDatosGenerales(int idObra) {
        _gestor.mostrarDatosGenerales(idObra);
    }    
    
    private void initArbolRecursos() {
        initTablaSubObras();
    }

    private void initGraph() {
        graph = CoolGanttFactory.create();
        graph.setEventHandler(new demoEvents());

        initTypeModel();
        initModel();

        grafico = graph.getComponent();
        
        // Soporte para Drop
        PanelDropTarget target = new PanelDropTarget(grafico, new GanttDropEvent());
        
        panelLineaDeTiempo.add(grafico, BorderLayout.CENTER);
        pack();
        setVisible(true);
        
        // Agrego el menu del boton derecho
        grafico.setComponentPopupMenu(jpm);
        
        // Le doy el zoom que quiero
         graph.setZoomOut();
          graph.setZoomOut();
           graph.setZoomOut();
            graph.setZoomOut();
             graph.setZoomOut();
         
        graph.setEventHandler(new GanttEventMouse());
    }

    private void updateGantt()
    {
        panelLineaDeTiempo.remove(grafico);
        pack();
        grafico = graph.getComponent();
        panelLineaDeTiempo.add(grafico, BorderLayout.CENTER);
        pack();
        PanelDropTarget target = new PanelDropTarget(grafico, new GanttDropEvent());
    }
    
    private void initModel() {

        int n = 0;
        // TODO: Llenar el Gantt
        List<TareaPlanificacion> lista = _gestor.getListaTareasPlanificadas();
        for (int i = 0; i < lista.size(); i++) {
            TareaPlanificacion tplan = lista.get(i);
            cargarTareasRecursivas(tplan,n);
        }
        
        graph.refreshModel();
    }

    private void cargarTareasRecursivas(TareaPlanificacion tplan, int n) {

        CoolGanttPhase p1 = new CoolGanttPhase();
        p1.setEditable(true);
        p1.setId(tplan.getIdTareaGantt());
        p1.setNombre(tplan.getNombre());
        p1.setType(demoTypes.TYPE_NORMAL);
        p1.setStartDate(tplan.getFechaInicio());
        p1.setEndDate(tplan.getFechaFin());

        // Agrego el nivel de tarea al gantt
        for (int j = 0; j < n; j++) {
            p1.setNombre(p1.getNombre()+" @");
        }
        
        graph.addPhase(p1);

//        tplan.getSubtareas();
        if (tplan.getSubtareas() != null) {
            for (int i = 0; i < tplan.getSubtareas().size(); i++) {
                TareaPlanificacion tp = tplan.getSubtareas().get(i);
                cargarTareasRecursivas(tp,n++);
            }
        }
    }

    public void refreshGanttAndData()
    {
        // Limpio las tareas anteriores
        graph.getModel().getPhaseList().clear();
        graph.refreshModel();
        
        // Cargo de nuevo todas las tareas
        int n = 0;
        // TODO: Llenar el Gantt
        List<TareaPlanificacion> lista = _gestor.getListaTareasPlanificadas();
        for (int i = 0; i < lista.size(); i++) {
            TareaPlanificacion tplan = lista.get(i);
            cargarTareasRecursivas(tplan,n);
        }
        
        // refresco el modelo
        graph.refreshModel();
        updateGantt();
    }
    
    private void initTypeModel() {
        CoolGanttTypeModel typeModel = new CoolGanttTypeModel();
        typeModel.addType(demoTypes.TYPE_NORMAL, ColorLabel.COLOR_LABELS[0]);
        typeModel.addType(demoTypes.TYPE_TRANSPORTE, ColorLabel.COLOR_LABELS[1]);
        typeModel.addType(demoTypes.TYPE_MONTAJE, ColorLabel.COLOR_LABELS[2]);
        graph.getModel().setTypeModel(typeModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpm = new javax.swing.JPopupMenu();
        menuNuevaEtapa = new javax.swing.JMenuItem();
        menuZoomMas = new javax.swing.JMenuItem();
        menuZoomMenos = new javax.swing.JMenuItem();
        menuDerecha = new javax.swing.JMenuItem();
        menuIzquierda = new javax.swing.JMenuItem();
        menuFechaActual = new javax.swing.JMenuItem();
        menuVistaAnual = new javax.swing.JMenuItem();
        menuVistaSemanal = new javax.swing.JMenuItem();
        jTextField3 = new javax.swing.JTextField();
        menuArbolTareas = new javax.swing.JPopupMenu();
        panelBarraIzquierda = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTareas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        treeRecursos = new javax.swing.JTree();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSubObras = new javax.swing.JTable();
        btnEmitirInforme = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panelCentral = new javax.swing.JTabbedPane();
        panelArbolTareas = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        arbolTareas = new javax.swing.JTree();
        panelLineaDeTiempo = new javax.swing.JPanel();
        panelDatosGenerales = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtFechaFin = new com.toedter.calendar.JDateChooser();
        txtFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblObraNombre = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblObraLugar = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblObraPlanta = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblObraFechaInicio = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblObraFechaFin = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtNroCotizacion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        lblCotMontoTotal = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtDescripcionGeneral = new javax.swing.JTextArea();

        menuNuevaEtapa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        menuNuevaEtapa.setText("Nueva Tarea");
        menuNuevaEtapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevaEtapaActionPerformed(evt);
            }
        });
        jpm.add(menuNuevaEtapa);

        menuZoomMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        menuZoomMas.setText("Zoom +");
        menuZoomMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomMasActionPerformed(evt);
            }
        });
        jpm.add(menuZoomMas);

        menuZoomMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        menuZoomMenos.setText("Zoom -");
        menuZoomMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomMenosActionPerformed(evt);
            }
        });
        jpm.add(menuZoomMenos);

        menuDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/next.png"))); // NOI18N
        menuDerecha.setText("Mover a la Derecha");
        menuDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDerechaActionPerformed(evt);
            }
        });
        jpm.add(menuDerecha);

        menuIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/back.png"))); // NOI18N
        menuIzquierda.setText("Mover a la Izquierda");
        menuIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIzquierdaActionPerformed(evt);
            }
        });
        jpm.add(menuIzquierda);

        menuFechaActual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuFechaActual.setText("Ir a Fecha Actual");
        menuFechaActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFechaActualActionPerformed(evt);
            }
        });
        jpm.add(menuFechaActual);

        menuVistaAnual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuVistaAnual.setText("Vista Anual");
        menuVistaAnual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVistaAnualActionPerformed(evt);
            }
        });
        jpm.add(menuVistaAnual);

        menuVistaSemanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/calendar.png"))); // NOI18N
        menuVistaSemanal.setText("Vista Semanal");
        menuVistaSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVistaSemanalActionPerformed(evt);
            }
        });
        jpm.add(menuVistaSemanal);

        jTextField3.setText("jTextField3");

        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Editar Planificación");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        panelBarraIzquierda.setBorder(javax.swing.BorderFactory.createTitledBorder("Sub Obras Cotizadas"));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles de la SubObra"));

        tblTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tareas Cotizadas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTareas.setCellSelectionEnabled(true);
        tblTareas.setDragEnabled(true);
        tblTareas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblTareas);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeRecursos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeRecursos.setDragEnabled(true);
        treeRecursos.setRootVisible(false);
        jScrollPane3.setViewportView(treeRecursos);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Modify.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 204, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar))
        );

        tblSubObras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de la SubObra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSubObras.setCellSelectionEnabled(true);
        tblSubObras.setDragEnabled(true);
        tblSubObras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSubObras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSubObrasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSubObras);

        javax.swing.GroupLayout panelBarraIzquierdaLayout = new javax.swing.GroupLayout(panelBarraIzquierda);
        panelBarraIzquierda.setLayout(panelBarraIzquierdaLayout);
        panelBarraIzquierdaLayout.setHorizontalGroup(
            panelBarraIzquierdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBarraIzquierdaLayout.setVerticalGroup(
            panelBarraIzquierdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarraIzquierdaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEmitirInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/List.png"))); // NOI18N
        btnEmitirInforme.setText("Emitir Informes");
        btnEmitirInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmitirInformeActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Save.png"))); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles Generales"));

        panelCentral.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panelCentralStateChanged(evt);
            }
        });

        arbolTareas.setModel(null);
        arbolTareas.setComponentPopupMenu(menuArbolTareas);
        jScrollPane5.setViewportView(arbolTareas);

        javax.swing.GroupLayout panelArbolTareasLayout = new javax.swing.GroupLayout(panelArbolTareas);
        panelArbolTareas.setLayout(panelArbolTareasLayout);
        panelArbolTareasLayout.setHorizontalGroup(
            panelArbolTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
        );
        panelArbolTareasLayout.setVerticalGroup(
            panelArbolTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );

        panelCentral.addTab("Arbol de Tareas", panelArbolTareas);

        panelLineaDeTiempo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                panelLineaDeTiempoFocusGained(evt);
            }
        });
        panelLineaDeTiempo.setLayout(new java.awt.BorderLayout());
        panelCentral.addTab("Línea de Tiempo", panelLineaDeTiempo);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDatosGeneralesLayout = new javax.swing.GroupLayout(panelDatosGenerales);
        panelDatosGenerales.setLayout(panelDatosGeneralesLayout);
        panelDatosGeneralesLayout.setHorizontalGroup(
            panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDatosGeneralesLayout.setVerticalGroup(
            panelDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        panelCentral.addTab("Test de Drag&Drop DnD", panelDatosGenerales);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Planificación"));

        txtFechaFin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaFinFocusLost(evt);
            }
        });

        txtFechaInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaInicioFocusLost(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha de Inicio: ");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Fecha de Fin: ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido de Obra"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Nombre:");

        lblObraNombre.setText("---");
        lblObraNombre.setPreferredSize(new java.awt.Dimension(1, 1));
        lblObraNombre.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Planta: ");

        lblObraLugar.setText("---");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Lugar de la Planta:");

        lblObraPlanta.setText("---");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Fecha de Inicio:");

        lblObraFechaInicio.setText("---");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Fecha de Fin:");

        lblObraFechaFin.setText("---");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObraFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(lblObraFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(lblObraNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(lblObraPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(lblObraLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(lblObraNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblObraPlanta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblObraLugar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblObraFechaInicio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblObraFechaFin))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Cotización"));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Cotización sobre la que se planifico: ");

        txtNroCotizacion.setEditable(false);
        txtNroCotizacion.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jButton1.setText("Abrir Cotización");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Monto Total Cotizado: ");

        lblCotMontoTotal.setText("---");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtNroCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                    .addComponent(lblCotMontoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNroCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblCotMontoTotal))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        panelCentral.addTab("Datos Generales", jPanel3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Descripción General de la Planificación");

        txtDescripcionGeneral.setColumns(20);
        txtDescripcionGeneral.setRows(5);
        txtDescripcionGeneral.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionGeneralFocusLost(evt);
            }
        });
        jScrollPane6.setViewportView(txtDescripcionGeneral);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCentral.addTab("Descripción", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBarraIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEmitirInforme)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelBarraIzquierda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnSave)
                            .addComponent(btnEmitirInforme))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmitirInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirInformeActionPerformed
       EmitirInformes informesWin = new EmitirInformes(_gestor.getPlanificacion().getId());
       SwingPanel.getInstance().addWindow(informesWin);
       informesWin.setVisible(true);
    }//GEN-LAST:event_btnEmitirInformeActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    }//GEN-LAST:event_btnSaveActionPerformed

    private void menuNuevaEtapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevaEtapaActionPerformed
       
    }//GEN-LAST:event_menuNuevaEtapaActionPerformed

    private void menuZoomMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomMasActionPerformed
        graph.setZoomIn();
    }//GEN-LAST:event_menuZoomMasActionPerformed

    private void menuZoomMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomMenosActionPerformed
        graph.setZoomOut();
    }//GEN-LAST:event_menuZoomMenosActionPerformed

    private void menuDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDerechaActionPerformed
        graph.moveRight();
    }//GEN-LAST:event_menuDerechaActionPerformed

    private void menuIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIzquierdaActionPerformed
        graph.moveLeft();
    }//GEN-LAST:event_menuIzquierdaActionPerformed

    private void menuFechaActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFechaActualActionPerformed
        graph.setFocusToday();
    }//GEN-LAST:event_menuFechaActualActionPerformed

    private void menuVistaAnualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVistaAnualActionPerformed
        graph.setYearView();
    }//GEN-LAST:event_menuVistaAnualActionPerformed

    private void menuVistaSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVistaSemanalActionPerformed
        graph.setWeeklyView();
    }//GEN-LAST:event_menuVistaSemanalActionPerformed

    private void tblSubObrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSubObrasMouseClicked
        if(evt.getButton()==MouseEvent.BUTTON1)
        {
            ListaDeTareasCelda tpSelected = (ListaDeTareasCelda) tblSubObras.getModel().getValueAt(tblSubObras.getSelectedRow(),0);
            if(tpSelected!=null)
            {
                this.hashSubObraSeleccionada = Integer.parseInt(tpSelected.getId());
                initTablaTareas(this.hashSubObraSeleccionada);
                initArbolRecursosCotizados(Integer.parseInt(tpSelected.getId()));
            }
        }
    }//GEN-LAST:event_tblSubObrasMouseClicked

    private void txtFechaFinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaFinFocusLost

    }//GEN-LAST:event_txtFechaFinFocusLost

    private void txtFechaInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInicioFocusLost

    }//GEN-LAST:event_txtFechaInicioFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        ExplorarSubObras eso = new ExplorarSubObras(_gestor.getCotizacionPlanificada());
        SwingPanel.getInstance().addWindow(eso);
        eso.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void panelLineaDeTiempoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelLineaDeTiempoFocusGained
    }//GEN-LAST:event_panelLineaDeTiempoFocusGained

    private void panelCentralStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panelCentralStateChanged
        System.out.println("Gano foco");
        JTabbedPane pan = (JTabbedPane)evt.getSource();
        switch(pan.getSelectedIndex())
        {
            case 0: 
                // Se mostro la ventana del arbol de tareas
                System.out.println("[DEBUG] Foco en el Ã¡rbol de Tareas");
                break;
            case 1:
                // Se mostro el Gantt
                System.out.println("[DEBUG] Foco en el Gantt");
                refreshGanttAndData();
                break;
        }
    }//GEN-LAST:event_panelCentralStateChanged

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        
        GestorEditarCotizacionModificada gestor = new GestorEditarCotizacionModificada(null,_gestor.getPlanificacion(),this.hashSubObraSeleccionada);
        EditarCotizacionModificada win = new EditarCotizacionModificada(gestor, this);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtDescripcionGeneralFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionGeneralFocusLost
        System.out.println("[DEBUG] La descripción de la Planificación perdió el foco");
    }//GEN-LAST:event_txtDescripcionGeneralFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        System.out.println("[DEBUG] La ventana de Editar Planificacion tomo foco");
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbolTareas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEmitirInforme;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPopupMenu jpm;
    private javax.swing.JLabel lblCotMontoTotal;
    private javax.swing.JLabel lblObraFechaFin;
    private javax.swing.JLabel lblObraFechaInicio;
    private javax.swing.JLabel lblObraLugar;
    private javax.swing.JLabel lblObraNombre;
    private javax.swing.JLabel lblObraPlanta;
    private javax.swing.JPopupMenu menuArbolTareas;
    private javax.swing.JMenuItem menuDerecha;
    private javax.swing.JMenuItem menuFechaActual;
    private javax.swing.JMenuItem menuIzquierda;
    private javax.swing.JMenuItem menuNuevaEtapa;
    private javax.swing.JMenuItem menuVistaAnual;
    private javax.swing.JMenuItem menuVistaSemanal;
    private javax.swing.JMenuItem menuZoomMas;
    private javax.swing.JMenuItem menuZoomMenos;
    private javax.swing.JPanel panelArbolTareas;
    private javax.swing.JPanel panelBarraIzquierda;
    private javax.swing.JTabbedPane panelCentral;
    private javax.swing.JPanel panelDatosGenerales;
    private javax.swing.JPanel panelLineaDeTiempo;
    private javax.swing.JTable tblSubObras;
    private javax.swing.JTable tblTareas;
    private javax.swing.JTree treeRecursos;
    private javax.swing.JTextArea txtDescripcionGeneral;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private com.toedter.calendar.JDateChooser txtFechaInicio;
    private javax.swing.JTextField txtNroCotizacion;
    // End of variables declaration//GEN-END:variables

    private void initTablaSubObras() {

        ArrayList<NTupla> lista = (ArrayList<NTupla>) _gestor.getListaSubObras();

        for (int i = 0; i < lista.size(); i++) {
            NTupla nt = lista.get(i);
                   
            ListaDeTareasCelda item = new ListaDeTareasCelda(ArbolDeTareasTipos.TIPO_SUBOBRA,String.valueOf(nt.getId()));
            item.setItemData(iconoSubObra, nt.getNombre());

            DefaultTableModel modelo = (DefaultTableModel) tblSubObras.getModel();
            Object[] fila = new Object[1];
            fila[0] = item;
            modelo.addRow(fila);
        }
    }

    private void initTablaTareas(int hashSubObra) {
        
        ArrayList<NTupla> lista = (ArrayList<NTupla>) _gestor.getListaTareasXSubObra(hashSubObra);
        
        DefaultTableModel modelo = (DefaultTableModel)tblTareas.getModel();
        modelo = TablaUtil.vaciarDefaultTableModel(modelo);

        for (int i = 0; i < lista.size(); i++) {
            NTupla nt = lista.get(i);
            ListaDeTareasCelda item = new ListaDeTareasCelda(ArbolDeTareasTipos.TIPO_TAREA,String.valueOf(nt.getId()));
            item.setItemData(iconoTarea, nt.getNombre());

            Object[] fila = new Object[1];
            fila[0] = item;
            modelo.addRow(fila);
        }
        
    }

    private void initArbolRecursosCotizados(int idSubObra) {
        _gestor.cargarArbolRecursos(idSubObra,treeRecursos);
    }
     
    @Deprecated
    public void agregarNuevaTareaGantt(int id,String nombre,int idTareaPadre)
    { 
//        IUGA: Comente este mÃ©todo, no se deberÃ­a usar MAS        
//        // Create a new Phrase
//        CoolGanttPhase p5 = new CoolGanttPhase();
//        p5.setEditable(true);
//        p5.setId(id);
//        p5.setNombre(nombre);
//        p5.setStartDate(fechaMas(new Date(), 10));
//        p5.setEndDate(fechaMas(new Date(), 20));
//        
//        graph.addPhase(p5);
//
//        graph.refreshModel();     
//        updateGantt();
//        inicializarArbolDeTareas();
    }
    
    public void agregarNuevaTareaArbol(int id,String nombre,int idTareaPadre)
    { 
        //idTareaPadre sera siempre o una tarea, subtarea o la obra. Nada mas. En caso de ser la obra, el id sera 0 (cero)
        
        //Arbol
        ArbolIconoNodo nodoTarea = new ArbolIconoNodo(id,ArbolDeTareasTipos.TIPO_TAREA,nombre,Iconos.ICONO_TAREA);
        ArbolIconoNodo padre=arbolTareasGestor.getNodoArbolTareasPorId(arbolTareas,idTareaPadre);        
        if(padre!=null)
        {
            ((DefaultTreeModel)(arbolTareas.getModel())).insertNodeInto(nodoTarea, padre, padre.getChildCount());
            arbolTareas.expandPath(arbolTareasGestor.getTreeNodePathDeNodo(padre));
        }
        else
        {
             JOptionPane.showMessageDialog(new JFrame(),"Se ha producido un error interno. Razon: Nodo padre inexistente.");
        }
//        graph.refreshModel();     
//        updateGantt();
        
    }
    public void asociarRecursoATareaArbol(int id,String nombre,int idTareaPadre, String tipo)
    { 
        DefaultTreeModel modelo=(DefaultTreeModel)(arbolTareas.getModel());
        ArbolIconoNodo padre=arbolTareasGestor.getNodoArbolTareasPorId(arbolTareas,idTareaPadre);        
        if(padre!=null)
        {
            String tipoGrupo=ArbolDeTareasTipos.getTipoColectivo(tipo); 
            //Ejemplo
            //tipo="MATERIAL"
            //tipoGrupo="MATERIALES"

            //El nodo puede ser un nodo de grupo, pero no necesariamente el que necesito
            //En este caso, obtengo la tarea padre
            String[] grupos=ArbolDeTareasTipos.getGruposRecursos();
            for (int i = 0; i < grupos.length; i++) 
            {
               if(grupos[i].equals(padre.getTipo()))
                {
                    padre=(ArbolIconoNodo)padre.getParent();
                    break;
                }  
            }                
            //Si el nodo padre es una tarea, obtengo grupo (Materiales, Herramientas, AlquileresCompras) como padre
            if(padre.getTipo().equals(ArbolDeTareasTipos.TIPO_TAREA))
            {  //obtener Nodo Grupo De Tarea
             ArbolIconoNodo  nodoGrupo=arbolTareasGestor.obtenerNodoGrupoDeTarea(modelo,padre,tipoGrupo);
             padre=nodoGrupo;    
            }
             //Creo el nodo
            ArbolIconoNodo nodo = new ArbolIconoNodo(id,tipo,nombre,ArbolDeTareasTipos.getIcono(tipo));
            modelo.insertNodeInto(nodo, padre, padre.getChildCount());
            arbolTareas.expandPath(arbolTareasGestor.getTreeNodePathDeNodo(padre));
        }
        else
        {
             JOptionPane.showMessageDialog(new JFrame(),"Se ha producido un error interno. Razon: Nodo padre inexistente.");
        }
        graph.refreshModel();     
        updateGantt();
        
    }
    

    public void setLblObraFechaFin(String lblObraFechaFin) {
        this.lblObraFechaFin.setText(lblObraFechaFin);
    }

    public void setLblObraFechaInicio(String lblObraFechaInicio) {
        this.lblObraFechaInicio.setText(lblObraFechaInicio);
    }

    public void setLblObraLugar(String lblObraLugar) {
        this.lblObraLugar.setText(lblObraLugar);
    }

    public void setLblObraNombre(String lblObraNombre) {
        this.lblObraNombre.setText(lblObraNombre);
    }

    public void setLblObraPlanta(String lblObraPlanta) {
        this.lblObraPlanta.setText(lblObraPlanta);
    }
    
    public void setTxtNroCotizacion(String nro) {
        this.txtNroCotizacion.setText(nro);
    }    
    
    public void setCotizacionMontoTotal(String monto) {
        this.lblCotMontoTotal.setText("$ "+monto);
    }     
       
    public void setFechaInicioPlanif(Date fecha) {
        this.txtFechaInicio.setDate(fecha);
    }    
    
    public void setFechaFinPlanif(Date fecha) {
        this.txtFechaFin.setDate(fecha);
    }

    public void setDescripcionPlanificacion(String descripcion) {
        txtDescripcionGeneral.setText(descripcion);
    }

    /**
     * InnerClass para manerja el disparo de evento Drag&Drop en el Gantt
     */
    public class GanttDropEvent implements IDropEvent{

        @Override
        public void dropEvent(String data,Point location) {

            // Veo a que apunta location !!!
            CoolGanttPhase p = graph.getPhase(location);
            // p==NULL : El Drop no callo sobre ninguna Tarea
            // p!=NULL :  El Drop callo sobre una Tarea en el Gantt (p)

            
            // Desencadeno la accion
            if(data!=null && !data.isEmpty())
            {
                String[] dataTrigger = data.split(";");
                if(dataTrigger.length==3)
                {
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_SUBOBRA))
                    {
                        // Por ahora no hago nada
                        JOptionPane.showMessageDialog(new JFrame(),"ESTO ES UNA SUBOBRA IDIOTA \n Disparador del Drop > Tipo:"+ dataTrigger[0]+"  ID: "+dataTrigger[1]);
                    }
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_TAREA))
                    {
                        if(p==null)
                        {
                            // Agrego una nueva Tarea
                            //No tiene tarea padre, paso un cero como IdTareaGantt
                            _gestor.agregarNuevaTareaGantt(Integer.parseInt(dataTrigger[1]),dataTrigger[2],0,0);
                        }
                        else
                        {
                            // Agrego una nueva Sub Tarea
                            int nivel = calcularNivel(p);
                            nivel++;
                            _gestor.agregarNuevaTareaGantt(Integer.parseInt(dataTrigger[1]),dataTrigger[2],p.getId(),nivel);
                        }        
                    }
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_HERRAMIENTA))
                    {
                        if(p==null)
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_HERRAMIENTA+"\nPero no se lo estsa asignando a ninguna Tarea");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_HERRAMIENTA+"\nA la Tarea "+p.getNombre());
                        }
                        
                    }
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_MATERIAL))
                    {
                        if(p==null)
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_MATERIAL+"\nPero no se lo estsa asignando a ninguna Tarea");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_MATERIAL+"\nA la Tarea "+p.getNombre());
                        }
                                    
                    }
                    if(dataTrigger[0].equals(ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA))
                    {
                        if(p==null)
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA+"\nPero no se lo estsa asignando a ninguna Tarea");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(new JFrame(),"En Contruccion!\nEstas intentando agregar un "+ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA+"\nA la Tarea "+p.getNombre());
                        }
                    }                    
                }
            }   
        }

        private int calcularNivel(CoolGanttPhase p) {
            String taskNameSearch = p.getNombre();
            int subNivel = 0;
            while (taskNameSearch.indexOf(CADENA_SUB_TAREA) > -1) 
            {
                taskNameSearch = taskNameSearch.substring(taskNameSearch.indexOf(CADENA_SUB_TAREA)+CADENA_SUB_TAREA.length(),taskNameSearch.length());
                subNivel++;
                System.out.println("[DEBUG] Task Level ++");
            }
            return subNivel;
        }
    }
    
    /**
     * Muestra un mensaje
     * @param tipo
     * @param titulo
     * @param mensaje 
     */
    public void mostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    } 
    
    
    /**
     * Arbol de tareas
     */
    public void inicializarArbolDeTareas()
    {
        PanelDropTarget target = new PanelDropTarget(arbolTareas, new ArbolDropEvent());
        DefaultTreeModel modelo=_gestor.getModeloArbolTareas();
        arbolTareas.setModel(modelo);
        arbolTareas.setCellRenderer(new ArbolIconoRenderer());
        inicializarEventosArbol();
        arbolTareas.repaint();        
        //arbolTareas.setRootVisible(false);
    }
    
    private void inicializarEventosArbol()
    {
        arbolTareas.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    setearItemsMenuArbol(me);
                }
            });
    }
    
    private void setearItemsMenuArbol(final MouseEvent mouseEvent)
    {
        menuArbolTareas.removeAll();
        TreePath tp = arbolTareas.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
        final ArbolIconoNodo nodo  = ArbolTareasGestor.getNodoDeTreeNodePath(tp);
        String tipo=nodo.getTipo();
        /////////////////////////////////////
        //Si es un nodo al q no se le puede hacer acciones, retorno
        String[] tiposSinHijos=ArbolDeTareasTipos.getSinHijos();
        boolean valido=false;
        for (int i = 0; i < tiposSinHijos.length; i++) {
            if(tiposSinHijos[i].equals(tipo))
            {valido=true;}
        }        
        if(valido==false && !tipo.equals(ArbolDeTareasTipos.TIPO_TAREA))
        {return;}
        //////////////////////////////
        //Agrego el listener para quitar
        /*JMenuItem quitar=new JMenuItem();
        quitar.setText("Quitar "+tipo.toLowerCase());
        quitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png")));
        menuArbolTareas.add(quitar); 
        
        ActionListener alQuitarTarea=new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) 
             {
               if(tipo.equals(ArbolDeTareasTipos.TIPO_TAREA))
                {  
                    eventoQuitarTareaArbol( nodo); 
                    quitar.addActionListener(alQuitarTarea);
                } 
             }
        };*/
         
        /////////////////////////
       /* if(tipo.equals(ArbolDeTareasTipos.TIPO_TAREA))
        {
            JMenuItem quitarTarea=new JMenuItem();
            quitarTarea.setText("Quitar tarea");
            quitarTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png")));
            menuArbolTareas.add(quitarTarea); 
        
            quitarTarea.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    evento_arbol_click(arbolTareas,  mouseEvent, nodo);
                }
            });
        }*/
    }
    
    private void eventoQuitarTareaArbol(ArbolIconoNodo nodo) 
    {        
       int resp=JOptionPane.showConfirmDialog(this.getParent(), "Esta seguro que desea quitar la tarea '"+nodo.getTitulo()+"'", "Quitar tarea", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       if(resp==JOptionPane.YES_OPTION)
       {
           boolean eliminada=_gestor.quitarTarea(nodo.getId(), nodo.getTitulo(),((ArbolIconoNodo)nodo.getParent()).getId());
           if(eliminada)
           {
              DefaultTreeModel modelo=(DefaultTreeModel)(arbolTareas.getModel());
              modelo.removeNodeFromParent(nodo);
           }
           else
           {mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","Ocurrio un error quitando la tarea");}
       }
    
    }
    
    /**
     * InnerClass para manerja el disparo de evento Drag&Drop en el arbol
     */
    public class ArbolDropEvent implements IDropEvent
    {

        @Override
        public void dropEvent(String data,Point location) 
        {

          TreePath path = arbolTareas.getPathForLocation(location.x, location.y);
          ArbolIconoNodo padre;
          padre=ArbolTareasGestor.getNodoDeTreeNodePath(path);
          if(padre != null)
          {
            padre= (ArbolIconoNodo) path.getLastPathComponent();
            System.out.println("Receptor:"+padre);
            for (int i = 0; i < ArbolDeTareasTipos.getSinHijos().length; i++) 
            {
                //if el nodo receptor es un recurso
                String[] sinHijos=ArbolDeTareasTipos.getSinHijos();
                if(sinHijos[i].equals(padre.getTipo()))
                {
                    return;
                }  
            }            
          }
          else
          {
              return;
          }
          
          if(data!=null && !data.isEmpty() && padre!=null)
          {
              String[] dataTrigger = data.split(";");
              if(dataTrigger.length==3)
              {
                String tipo=dataTrigger[0];
                int id= Integer.parseInt(dataTrigger[1]);
                String nombre=dataTrigger[2]; 
                int idTareaPadre=padre.getId(); 
                
                //Si agregamos una tarea
                if(tipo.equals(ArbolDeTareasTipos.TIPO_TAREA))
                {
                    //Es una tarea, pero no es una subtarea
                    if(padre.getTipo().equals(ArbolDeTareasTipos.TIPO_OBRA))
                    {
                        idTareaPadre=0;
                    }
                    else
                    {
                      //Si el nodo padre es un recurso
                      if(!padre.getTipo().equals(ArbolDeTareasTipos.TIPO_TAREA))
                        {
                            return;
                        }  
                    }
                    _gestor.agregarNuevaTareaArbol(id, nombre, idTareaPadre);                    
                }
                else //Si agregamos un recurso
                {  
                    _gestor.asociarRecurso(id, nombre, idTareaPadre,tipo);
                }                
            }
          }//Fin de if(data!=null && !data.isEmpty() && padre!=null)            
        }
    }
    
    public class GanttEventMouse implements ICoolGanttEvent{

        @Override
        public Date inGetProjectStartDate() {
            return _gestor.getFechaInicioObra();
        }

        @Override
        public Date inGetProjectEndDate() {
            return _gestor.getFechaFinObra();
        }

        @Override
        public void outClickPhase(int i) {
            try{
                // Mantenemos un registro unificado de q ventanas ya estan abiertas
                // Antes busco q tarea es!
                TareaPlanificacion tarea = _gestor.getTareaFromIDGantt(i);
                if(tarea!=null)
                {
                    abrirEditarTarea(tarea);
                }
                
            }catch(Exception ex)
            {
                System.err.println("Error al abrir una tarea !");
                JOptionPane.showMessageDialog(new JFrame(),"Se ha producido un error interno. Razon: Error al abrir una tarea","Error!",JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void outMovePhase(int i, Date date) {
              _gestor.tareaCambioFecha(i,date);
        }

        @Override
        public void outExtendPhaseBackward(int i, Date date) {
              _gestor.tareaCambioFechaInicio(i,date);
        }

        @Override
        public void outExtendPhaseForward(int i, Date date) {
             _gestor.tareaCambioFechaFin(i,date);
        }

    }
    
    /**
     * Este metodo mantiene un registro de que ventanas de "Editar tarea" estÃ¡n abiertas
     * esto es para que no puedan abrir mas de un "Editar Tarea" con la misma Tarea.
     * Manenemos un registro de q ventanas ya estan abiertas !
     * NOTA: Se usa desde el Gantt y desde el Arbol.
     * @param tareaGantt: tarea a Editar
     */
    private void abrirEditarTarea(TareaPlanificacion tarea)
    {
        // Si no esta abierta, la creo, registro y Muestro
        if(!ventanasEditarTareasAbiertas.containsKey(tarea.hashCode()))
        {
            GestorEditarTarea gestorEditarTarea = new GestorEditarTarea(_gestor);
            gestorEditarTarea.seleccionarTarea(tarea);
            PantallaEditarTarea editarTarea = new PantallaEditarTarea(gestorEditarTarea);
            SwingPanel.getInstance().addWindow(editarTarea);
            
            // Registro
            ventanasEditarTareasAbiertas.put(tarea.hashCode(),editarTarea);
            
            // Muestro
            editarTarea.setVisible(true);        
        }
        else
        {
            PantallaEditarTarea editarTarea = ventanasEditarTareasAbiertas.get(tarea.hashCode());
            if(editarTarea!=null)
            {
                if(editarTarea.isVisible())
                {
                    editarTarea.requestFocus();
                    editarTarea.requestFocusInWindow();
                }
                else
                {
                    SwingPanel.getInstance().addWindow(editarTarea);
                    editarTarea.setVisible(true);
                    editarTarea.requestFocus();
                }
            }        
            else
            {
                GestorEditarTarea gestorEditarTarea = new GestorEditarTarea(_gestor);
                gestorEditarTarea.seleccionarTarea(tarea);
                editarTarea = new PantallaEditarTarea(gestorEditarTarea);
                SwingPanel.getInstance().addWindow(editarTarea);

                // Registro
                ventanasEditarTareasAbiertas.put(tarea.hashCode(),editarTarea);

                // Muestro
                editarTarea.setVisible(true);                    
            }
        }
        
        
        
    }
    
    
    @Override
    public void actualizar(int id, String flag, boolean exito) {
        DefaultTableModel modelo = (DefaultTableModel)tblTareas.getModel();
        modelo = TablaUtil.vaciarDefaultTableModel(modelo);        
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeRecursos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        initTablaSubObras();
    }
    

}
