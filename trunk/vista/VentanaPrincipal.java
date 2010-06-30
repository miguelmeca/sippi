/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VentanaPrincipal.java
 *
 * Created on 19/05/2010, 20:24:31
 */

package vista;

//import org.jfree.ui.RefineryUtilities;

import controlador.xml.XMLReader;
import controlador.xml.XMLReaderMenu;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import util.HibernateUtil;
import util.SwingPanel;
import vista.comer.pantallaConsultarEmpresaCliente;
import vista.comer.pantallaConsultarObra;
import vista.comer.pantallaRegistrarConfirmacionInicioObra;
import vista.comer.pantallaRegistrarEmpresaCliente;
import vista.comer.pantallaRegistrarNuevaPlanta;
import vista.comer.pantallaRegistrarPedido;
import vista.gui.sidebar.IconTreeModel;
import vista.gui.sidebar.IconTreeRenderer;
import vista.gui.sidebar.TreeEntry;
import vista.rrhh.pantallaConsultarCronogramaEmpleado;
import vista.rrhh.pantallaEmitirListadoDeAsistenciaATallerDeCapacitacion;
import vista.rrhh.pantallaGenerarListadoCompraIndumentaria;
import vista.rrhh.pantallaRegistrarAsistenciaTallerCapacitacion;
import vista.rrhh.pantallaRegistrarEmpleado;
import vista.rrhh.pantallaRegistrarPlanSeguridad;
import vista.rrhh.pantallaRegistrarTaller;
import vista.rrhh.pantallaRegistrarTallerCapacitacion;


/**
 *
 * @author iuga
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /** Creates new form VentanaPrincipal */
    public VentanaPrincipal() {
        initComponents();

        // Mando el Panel a un Singleton para poder accederlo de manera unica
        SwingPanel.getInstance().setPane(panel);
        SwingPanel.getInstance().setVentanaPrincipal(this);

        this.setExtendedState(MAXIMIZED_BOTH);

        cargarMenu();


    }

    private void cargarMenu()
    {
        treeMenu.setCellRenderer(new IconTreeRenderer());
        treeMenu.setOpaque(false);
        treeMenu.setRowHeight(26);
        treeMenu.setExpandsSelectedPaths(true);
        treeMenu.setBackground(null);

        XMLReaderMenu menuxml = new XMLReaderMenu(getClass().getResource("/config/menu.xml").getPath());
        IconTreeModel itm = new IconTreeModel();
        itm.RellenarArbol(menuxml.cargarMenu());
        treeMenu.setModel(itm);

        treeMenu.setRootVisible(false);

    }

    private void Salir()
    {
        int op = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?");
        if(op == JOptionPane.YES_OPTION)
        {
            HibernateUtil.closeSession();
            System.exit(0);
        }
    }

    private void NuevaPlanta()
    {
            pantallaRegistrarNuevaPlanta pre = new pantallaRegistrarNuevaPlanta();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnNuevoPedidoObra = new javax.swing.JButton();
        btnNuevoEmpleado = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeMenu = new javax.swing.JTree();
        jPanel5 = new javax.swing.JPanel();
        lblAyudaTitulo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblAyudaDesc = new javax.swing.JTextArea();
        panel = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mObra = new javax.swing.JMenu();
        miNuevo = new javax.swing.JMenuItem();
        miConsultar = new javax.swing.JMenuItem();
        miModificar = new javax.swing.JMenuItem();
        miCompraMateriales = new javax.swing.JMenuItem();
        mPlanificacion = new javax.swing.JMenu();
        miEPresupuesto = new javax.swing.JMenuItem();
        miNueva = new javax.swing.JMenuItem();
        miPModificar = new javax.swing.JMenuItem();
        miPlanSeguridad = new javax.swing.JMenuItem();
        mEjecucion = new javax.swing.JMenu();
        miLanzamientoyFin = new javax.swing.JMenuItem();
        miReLanz = new javax.swing.JMenuItem();
        miAvance = new javax.swing.JMenuItem();
        miOrdenesTrab = new javax.swing.JMenuItem();
        mEtapa = new javax.swing.JMenu();
        miEtapaConsultar = new javax.swing.JMenuItem();
        miEtapaModificar = new javax.swing.JMenuItem();
        miEtapaEliminar = new javax.swing.JMenuItem();
        mRRHH = new javax.swing.JMenu();
        miNuevoRH = new javax.swing.JMenuItem();
        miConsultarRH = new javax.swing.JMenuItem();
        miModificarRH = new javax.swing.JMenuItem();
        miDarDeBajaRH = new javax.swing.JMenuItem();
        miCronogramaRH = new javax.swing.JMenuItem();
        miLicencia = new javax.swing.JMenuItem();
        mTalleres = new javax.swing.JMenu();
        mNuevoTaller = new javax.swing.JMenu();
        miParaEmpleado = new javax.swing.JMenuItem();
        miParaObra = new javax.swing.JMenuItem();
        miCapacitadores = new javax.swing.JMenuItem();
        miRegistroDeAsistencia = new javax.swing.JMenuItem();
        miLugaresDeCapacitacion = new javax.swing.JMenuItem();
        mSistema = new javax.swing.JMenu();
        mEmpresa = new javax.swing.JMenu();
        miNuevaEmpresa = new javax.swing.JMenuItem();
        miConsultarEmpresa = new javax.swing.JMenuItem();
        miModificarEmpresa = new javax.swing.JMenuItem();
        mPlanta = new javax.swing.JMenu();
        miNuevaPlanta = new javax.swing.JMenuItem();
        miConsultarPlanta = new javax.swing.JMenuItem();
        miModificarPlanta = new javax.swing.JMenuItem();
        mContactos = new javax.swing.JMenu();
        miGestionContactos = new javax.swing.JMenuItem();
        mProveedor = new javax.swing.JMenu();
        miGestionProveedor = new javax.swing.JMenuItem();
        mHerramienta = new javax.swing.JMenu();
        miGestionHerramienta = new javax.swing.JMenuItem();
        mIndumentaria = new javax.swing.JMenu();
        miGestionIndumentaria = new javax.swing.JMenuItem();
        miOrdenDeCompra = new javax.swing.JMenuItem();
        mMateriales = new javax.swing.JMenu();
        miGestionMateriales = new javax.swing.JMenuItem();
        mUbicaciones = new javax.swing.JMenu();
        mLocalidad = new javax.swing.JMenu();
        miGestionLocalidad = new javax.swing.JMenuItem();
        mBarrio = new javax.swing.JMenu();
        miGestionBarrio = new javax.swing.JMenuItem();
        mProvincia = new javax.swing.JMenu();
        miGestionProvincia = new javax.swing.JMenuItem();
        mPais = new javax.swing.JMenu();
        miGestionPais = new javax.swing.JMenuItem();
        mUsuarios = new javax.swing.JMenu();
        miCerrarSesion = new javax.swing.JMenuItem();
        miCambiarUsuario = new javax.swing.JMenuItem();
        mAyuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema");

        jToolBar1.setRollover(true);

        btnNuevoPedidoObra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add_page.png"))); // NOI18N
        btnNuevoPedidoObra.setText("Nuevo Pedido de Obra");
        btnNuevoPedidoObra.setFocusable(false);
        btnNuevoPedidoObra.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevoPedidoObra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoPedidoObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPedidoObraActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevoPedidoObra);

        btnNuevoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/user.png"))); // NOI18N
        btnNuevoEmpleado.setText("Registrar Nuevo Empleado");
        btnNuevoEmpleado.setFocusable(false);
        btnNuevoEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevoEmpleado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEmpleadoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevoEmpleado);
        jToolBar1.add(jSeparator1);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setFocusable(false);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSalir);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jSplitPane2.setDoubleBuffered(true);

        jSplitPane1.setDividerLocation(600);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        treeMenu.setModel(new IconTreeModel());
        treeMenu.setRootVisible(false);
        treeMenu.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeMenuValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeMenu);

        jSplitPane1.setTopComponent(jScrollPane1);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblAyudaTitulo.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblAyudaTitulo.setText("Titulo de la Ayuda");

        jButton1.setFont(new java.awt.Font("Dialog", 2, 10));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/help.png"))); // NOI18N
        jButton1.setText("Más Ayuda ...");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        lblAyudaDesc.setColumns(20);
        lblAyudaDesc.setLineWrap(true);
        lblAyudaDesc.setRows(5);
        lblAyudaDesc.setEnabled(false);
        jScrollPane2.setViewportView(lblAyudaDesc);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addComponent(lblAyudaTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblAyudaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel5);

        jSplitPane2.setLeftComponent(jSplitPane1);

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setDoubleBuffered(true);
        jSplitPane2.setRightComponent(panel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1102, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        mObra.setText("Pedido Obra");

        miNuevo.setText("Nuevo");
        miNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevoActionPerformed(evt);
            }
        });
        mObra.add(miNuevo);

        miConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConsultarActionPerformed(evt);
            }
        });
        mObra.add(miConsultar);

        miModificar.setText("Modificar");
        mObra.add(miModificar);

        miCompraMateriales.setText("Compra de Materiales");
        mObra.add(miCompraMateriales);

        mPlanificacion.setText("Planificación");

        miEPresupuesto.setText("Emisión de Presupuesto");
        mPlanificacion.add(miEPresupuesto);

        miNueva.setText("Nueva");
        mPlanificacion.add(miNueva);

        miPModificar.setText("Modificar");
        mPlanificacion.add(miPModificar);

        miPlanSeguridad.setText("Plan de Seguridad");
        mPlanificacion.add(miPlanSeguridad);

        mObra.add(mPlanificacion);

        mEjecucion.setText("Ejecución");
        mEjecucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mEjecucionActionPerformed(evt);
            }
        });

        miLanzamientoyFin.setText("Lanzamiento y Fin");
        mEjecucion.add(miLanzamientoyFin);

        miReLanz.setText("Re-Lanzamiento");
        mEjecucion.add(miReLanz);

        miAvance.setText("Avance");
        mEjecucion.add(miAvance);

        miOrdenesTrab.setText("Órdenes de Trabajo");
        mEjecucion.add(miOrdenesTrab);

        mEtapa.setText("Etapa");

        miEtapaConsultar.setText("Consultar");
        mEtapa.add(miEtapaConsultar);

        miEtapaModificar.setText("Modificar");
        mEtapa.add(miEtapaModificar);

        miEtapaEliminar.setText("Eliminar");
        mEtapa.add(miEtapaEliminar);

        mEjecucion.add(mEtapa);

        mObra.add(mEjecucion);

        jMenuBar1.add(mObra);

        mRRHH.setText("Recursos Humanos");

        miNuevoRH.setText("Nuevo");
        miNuevoRH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevoRHActionPerformed(evt);
            }
        });
        mRRHH.add(miNuevoRH);

        miConsultarRH.setText("Consultar");
        mRRHH.add(miConsultarRH);

        miModificarRH.setText("Modificar");
        mRRHH.add(miModificarRH);

        miDarDeBajaRH.setText("Dar de Baja");
        mRRHH.add(miDarDeBajaRH);

        miCronogramaRH.setText("Cronograma");
        miCronogramaRH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCronogramaRHActionPerformed(evt);
            }
        });
        mRRHH.add(miCronogramaRH);

        miLicencia.setText("Licencias");
        mRRHH.add(miLicencia);

        jMenuBar1.add(mRRHH);

        mTalleres.setText("Talleres de Capacitación");

        mNuevoTaller.setText("Nuevo Taller");

        miParaEmpleado.setText("Para Empleados");
        mNuevoTaller.add(miParaEmpleado);

        miParaObra.setText("Para una Obra");
        mNuevoTaller.add(miParaObra);

        mTalleres.add(mNuevoTaller);

        miCapacitadores.setText("Capacitadores");
        mTalleres.add(miCapacitadores);

        miRegistroDeAsistencia.setText("Registro de Asistencias");
        mTalleres.add(miRegistroDeAsistencia);

        miLugaresDeCapacitacion.setText("Lugares de Capacitacion");
        mTalleres.add(miLugaresDeCapacitacion);

        jMenuBar1.add(mTalleres);

        mSistema.setText("Cargas Básicas");

        mEmpresa.setText("Empresa");

        miNuevaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        miNuevaEmpresa.setText("Nueva");
        miNuevaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevaEmpresaActionPerformed(evt);
            }
        });
        mEmpresa.add(miNuevaEmpresa);

        miConsultarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        miConsultarEmpresa.setText("Consultar");
        miConsultarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConsultarEmpresaActionPerformed(evt);
            }
        });
        mEmpresa.add(miConsultarEmpresa);

        miModificarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add_page.png"))); // NOI18N
        miModificarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miModificarEmpresaActionPerformed(evt);
            }
        });
        mEmpresa.add(miModificarEmpresa);

        mSistema.add(mEmpresa);

        mPlanta.setText("Planta");

        miNuevaPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        miNuevaPlanta.setText("Nueva");
        miNuevaPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevaPlantaActionPerformed(evt);
            }
        });
        mPlanta.add(miNuevaPlanta);

        miConsultarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        miConsultarPlanta.setText("Consultar");
        mPlanta.add(miConsultarPlanta);

        miModificarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add_page.png"))); // NOI18N
        miModificarPlanta.setText("Modificar");
        mPlanta.add(miModificarPlanta);

        mSistema.add(mPlanta);

        mContactos.setText("Contactos");

        miGestionContactos.setText("Gestión");
        mContactos.add(miGestionContactos);

        mSistema.add(mContactos);

        mProveedor.setText("Proveedor");

        miGestionProveedor.setText("Gestión");
        mProveedor.add(miGestionProveedor);

        mSistema.add(mProveedor);

        mHerramienta.setText("Herramienta");

        miGestionHerramienta.setText("Gestión");
        mHerramienta.add(miGestionHerramienta);

        mSistema.add(mHerramienta);

        mIndumentaria.setText("Indumentaria");

        miGestionIndumentaria.setText("Gestión");
        mIndumentaria.add(miGestionIndumentaria);

        miOrdenDeCompra.setText("Orden de Compra");
        miOrdenDeCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOrdenDeCompraActionPerformed(evt);
            }
        });
        mIndumentaria.add(miOrdenDeCompra);

        mSistema.add(mIndumentaria);

        mMateriales.setText("Materiales");

        miGestionMateriales.setText("Gestión");
        mMateriales.add(miGestionMateriales);

        mSistema.add(mMateriales);

        mLocalidad.setText("Localidad");

        miGestionLocalidad.setText("Gestión");
        mLocalidad.add(miGestionLocalidad);

        mUbicaciones.add(mLocalidad);

        mBarrio.setText("Barrio");

        miGestionBarrio.setText("Gestión");
        miGestionBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGestionBarrioActionPerformed(evt);
            }
        });
        mBarrio.add(miGestionBarrio);

        mUbicaciones.add(mBarrio);

        mProvincia.setText("Provincia");

        miGestionProvincia.setText("Gestión");
        mProvincia.add(miGestionProvincia);

        mUbicaciones.add(mProvincia);

        mPais.setText("País");

        miGestionPais.setText("Gestión");
        mPais.add(miGestionPais);

        mUbicaciones.add(mPais);

        mSistema.add(mUbicaciones);

        jMenuBar1.add(mSistema);

        mUsuarios.setText("Usuario");

        miCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/lock.png"))); // NOI18N
        miCerrarSesion.setText("Cerrar Sesión");
        mUsuarios.add(miCerrarSesion);

        miCambiarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/users.png"))); // NOI18N
        miCambiarUsuario.setText("Cambiar de Usuario");
        mUsuarios.add(miCambiarUsuario);

        jMenuBar1.add(mUsuarios);

        mAyuda.setText("Ayuda");
        jMenuBar1.add(mAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEmpleadoActionPerformed
            pantallaRegistrarEmpleado pre = new pantallaRegistrarEmpleado();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            pre.opcionRegistrarEmpleado();
    }//GEN-LAST:event_btnNuevoEmpleadoActionPerformed

    private void treeMenuValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeMenuValueChanged


        TreeEntry node = (TreeEntry)evt.getPath().getLastPathComponent();

        if(node==null) return;
        //
        if(node.getTitulo().equals("Nuevo Empleado"))
        {
            pantallaRegistrarEmpleado pre = new pantallaRegistrarEmpleado();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            pre.opcionRegistrarEmpleado();
            return;
        }
        // Nuevo Pedido de Obra
        if(node.getTitulo().equals("Nuevo Pedido de Obra"))
        {
            pantallaRegistrarPedido pre = new pantallaRegistrarPedido();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        //Nueva Empresa Cliente
        if(node.getTitulo().equals("Nueva Empresa Cliente"))
        {
            pantallaRegistrarEmpresaCliente pre = new pantallaRegistrarEmpresaCliente();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        //Consultar Empresas Cliente
         if(node.getTitulo().equals("Consultar Empresas Cliente"))
        {
            pantallaConsultarEmpresaCliente pre = new pantallaConsultarEmpresaCliente();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Nueva Planta
        if(node.getTitulo().equals("Nueva Planta"))
        {
            NuevaPlanta();
            return;
        }
        // Consultar Obras
        if(node.getTitulo().equals("Consultar Obras"))
        {
            pantallaConsultarObra pre = new pantallaConsultarObra();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Dar Inicio a una Obra
        if(node.getTitulo().equals("Dar Inicio a una Obra"))
        {
            pantallaRegistrarConfirmacionInicioObra pre = new pantallaRegistrarConfirmacionInicioObra();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Cronogramas de Trabajo
        if(node.getTitulo().equals("Cronogramas de Trabajo"))
        {
            pantallaConsultarCronogramaEmpleado pre = new pantallaConsultarCronogramaEmpleado();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Nuevo Listado de Compra de Indumentaria
        if(node.getTitulo().equals("Nuevo Listado de Compra de Indumentaria"))
        {
            pantallaGenerarListadoCompraIndumentaria pre = new pantallaGenerarListadoCompraIndumentaria();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Nuevo Plan de Seguridad
        if(node.getTitulo().equals("Nuevo Plan de Seguridad"))
        {
            pantallaRegistrarPlanSeguridad pre = new pantallaRegistrarPlanSeguridad();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Nuevo Taller de CapcitaciÃ³n
        if(node.getTitulo().equals("Nuevo Taller de CapcitaciÃ³n"))
        {
            pantallaRegistrarTaller pre = new pantallaRegistrarTaller();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Nuevo Plan de Seguridad
        if(node.getTitulo().equals("Registrar Asistencias"))
        {
            pantallaRegistrarAsistenciaTallerCapacitacion pre = new pantallaRegistrarAsistenciaTallerCapacitacion();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
        // Emitir Listado de Asistencia
        if(node.getTitulo().equals("Emitir Listado de Asistencia"))
        {
            pantallaEmitirListadoDeAsistenciaATallerDeCapacitacion pre = new pantallaEmitirListadoDeAsistenciaATallerDeCapacitacion();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
            return;
        }
    }//GEN-LAST:event_treeMenuValueChanged

    private void btnNuevoPedidoObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPedidoObraActionPerformed

            pantallaRegistrarPedido pre = new pantallaRegistrarPedido();
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);

    }//GEN-LAST:event_btnNuevoPedidoObraActionPerformed

    private void miNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevoActionPerformed
        // TODO add your handling code here:
        pantallaRegistrarPedido pre = new pantallaRegistrarPedido();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_miNuevoActionPerformed

    private void miConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConsultarActionPerformed
        // TODO add your handling code here:
        pantallaConsultarObra pre = new pantallaConsultarObra();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
}//GEN-LAST:event_miConsultarActionPerformed

    private void mEjecucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mEjecucionActionPerformed
        // TODO add your handling code here:
        pantallaRegistrarConfirmacionInicioObra pre = new pantallaRegistrarConfirmacionInicioObra();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_mEjecucionActionPerformed

    private void miNuevoRHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevoRHActionPerformed
        // TODO add your handling code here:
        pantallaRegistrarEmpleado pre = new pantallaRegistrarEmpleado();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        pre.opcionRegistrarEmpleado();
        return;
}//GEN-LAST:event_miNuevoRHActionPerformed

    private void miCronogramaRHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCronogramaRHActionPerformed
        // TODO add your handling code here:
        pantallaConsultarCronogramaEmpleado pre = new pantallaConsultarCronogramaEmpleado();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_miCronogramaRHActionPerformed

    private void miNuevaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevaEmpresaActionPerformed
        // TODO add your handling code here:
        pantallaRegistrarEmpresaCliente pre = new pantallaRegistrarEmpresaCliente();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_miNuevaEmpresaActionPerformed

    private void miConsultarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConsultarEmpresaActionPerformed

        //new Agregar().setVisible(true);
        // TODO add your handling code here:
}//GEN-LAST:event_miConsultarEmpresaActionPerformed

    private void miModificarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miModificarEmpresaActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_miModificarEmpresaActionPerformed

    private void miNuevaPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevaPlantaActionPerformed
        // TODO add your handling code here:
        pantallaRegistrarNuevaPlanta pre = new pantallaRegistrarNuevaPlanta();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_miNuevaPlantaActionPerformed

    private void miOrdenDeCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOrdenDeCompraActionPerformed
        // TODO add your handling code here:
        pantallaGenerarListadoCompraIndumentaria pre = new pantallaGenerarListadoCompraIndumentaria();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
        return;
}//GEN-LAST:event_miOrdenDeCompraActionPerformed

    private void miGestionBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGestionBarrioActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_miGestionBarrioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        Salir();

    }//GEN-LAST:event_btnSalirActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoEmpleado;
    private javax.swing.JButton btnNuevoPedidoObra;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea lblAyudaDesc;
    private javax.swing.JLabel lblAyudaTitulo;
    private javax.swing.JMenu mAyuda;
    private javax.swing.JMenu mBarrio;
    private javax.swing.JMenu mContactos;
    private javax.swing.JMenu mEjecucion;
    private javax.swing.JMenu mEmpresa;
    private javax.swing.JMenu mEtapa;
    private javax.swing.JMenu mHerramienta;
    private javax.swing.JMenu mIndumentaria;
    private javax.swing.JMenu mLocalidad;
    private javax.swing.JMenu mMateriales;
    private javax.swing.JMenu mNuevoTaller;
    private javax.swing.JMenu mObra;
    private javax.swing.JMenu mPais;
    private javax.swing.JMenu mPlanificacion;
    private javax.swing.JMenu mPlanta;
    private javax.swing.JMenu mProveedor;
    private javax.swing.JMenu mProvincia;
    private javax.swing.JMenu mRRHH;
    private javax.swing.JMenu mSistema;
    private javax.swing.JMenu mTalleres;
    private javax.swing.JMenu mUbicaciones;
    private javax.swing.JMenu mUsuarios;
    private javax.swing.JMenuItem miAvance;
    private javax.swing.JMenuItem miCambiarUsuario;
    private javax.swing.JMenuItem miCapacitadores;
    private javax.swing.JMenuItem miCerrarSesion;
    private javax.swing.JMenuItem miCompraMateriales;
    private javax.swing.JMenuItem miConsultar;
    private javax.swing.JMenuItem miConsultarEmpresa;
    private javax.swing.JMenuItem miConsultarPlanta;
    private javax.swing.JMenuItem miConsultarRH;
    private javax.swing.JMenuItem miCronogramaRH;
    private javax.swing.JMenuItem miDarDeBajaRH;
    private javax.swing.JMenuItem miEPresupuesto;
    private javax.swing.JMenuItem miEtapaConsultar;
    private javax.swing.JMenuItem miEtapaEliminar;
    private javax.swing.JMenuItem miEtapaModificar;
    private javax.swing.JMenuItem miGestionBarrio;
    private javax.swing.JMenuItem miGestionContactos;
    private javax.swing.JMenuItem miGestionHerramienta;
    private javax.swing.JMenuItem miGestionIndumentaria;
    private javax.swing.JMenuItem miGestionLocalidad;
    private javax.swing.JMenuItem miGestionMateriales;
    private javax.swing.JMenuItem miGestionPais;
    private javax.swing.JMenuItem miGestionProveedor;
    private javax.swing.JMenuItem miGestionProvincia;
    private javax.swing.JMenuItem miLanzamientoyFin;
    private javax.swing.JMenuItem miLicencia;
    private javax.swing.JMenuItem miLugaresDeCapacitacion;
    private javax.swing.JMenuItem miModificar;
    private javax.swing.JMenuItem miModificarEmpresa;
    private javax.swing.JMenuItem miModificarPlanta;
    private javax.swing.JMenuItem miModificarRH;
    private javax.swing.JMenuItem miNueva;
    private javax.swing.JMenuItem miNuevaEmpresa;
    private javax.swing.JMenuItem miNuevaPlanta;
    private javax.swing.JMenuItem miNuevo;
    private javax.swing.JMenuItem miNuevoRH;
    private javax.swing.JMenuItem miOrdenDeCompra;
    private javax.swing.JMenuItem miOrdenesTrab;
    private javax.swing.JMenuItem miPModificar;
    private javax.swing.JMenuItem miParaEmpleado;
    private javax.swing.JMenuItem miParaObra;
    private javax.swing.JMenuItem miPlanSeguridad;
    private javax.swing.JMenuItem miReLanz;
    private javax.swing.JMenuItem miRegistroDeAsistencia;
    private javax.swing.JDesktopPane panel;
    private javax.swing.JTree treeMenu;
    // End of variables declaration//GEN-END:variables

    public void mostrarAyuda(String titulo, String desc, int id)
    {
        lblAyudaTitulo.setText(titulo);
        lblAyudaDesc.setText(desc);
    }

}
