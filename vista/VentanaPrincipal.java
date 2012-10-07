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

import config.SConfig;
import controlador.users.UserSession;
import controlador.xml.XMLReaderMenu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import util.HibernateUtil;
import util.SwingPanel;
import vista.comer.pantallaRegistrarPedido;
import vista.gui.sidebar.IconTreeModel;
import vista.gui.sidebar.IconTreeRenderer;
import vista.gui.sidebar.TreeEntry;
import vista.cotizacion.ExplorarCotizacionObra;
import vista.cotizacion.ExplorarCotizaciones;
import modelo.FavoritoBean;
import modelo.Herramienta;
import modelo.HerramientaDeEmpresa;
import modelo.Material;
import org.jfree.ui.RefineryUtilities;
import test.TestABM;
import test.TestCallBackListadoGenerico;
import vista.abms.*;
import vista.ayuda.VisorDeAyuda;
import vista.comer.*;
import vista.compras.*;
import vista.cotizacion.ListadoCotizaciones;
import vista.ejecucion.VentanaEjecucion;
import vista.ejecucion.lanzamiento.VentanaLanzamiento;
import vista.gen.FactoryABM;
import vista.gen.PantallaABMGenerica;
import vista.planificacion.EditarPlanificacion;
import vista.planificacion.PantallaConsultarPlanificaciones;
import vista.rrhh.PantallaConsultarEmpleado;
import vista.rrhh.pantallaConsultarLicenciasEmpleado;
import vista.rrhh.pantallaRegistrarEmpleado;
import vista.users.ABMUsers;
import vista.users.ListadoUsuarios;
import vista.users.UserLogin;



/**
 *
 * @author iuga
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private static final int PANEL_DERECHO_SIZE = 250;

    /** Creates new form VentanaPrincipal */
    public VentanaPrincipal() {
        initComponents();

        initWindowClosing();
        
        // Mando el Panel a un Singleton para poder accederlo de manera unica
        SwingPanel.getInstance().setPane(panel);
        SwingPanel.getInstance().setVentanaPrincipal(this);

        this.setExtendedState(MAXIMIZED_BOTH);

        jSplitPane2.setDividerLocation(PANEL_DERECHO_SIZE);
        this.setTitle(SConfig.getInstance().getNombreSistema() + " | "+ SConfig.getInstance().getNombreEmpresa() + " - " + SConfig.getInstance().getDireccionEmpresa());
        
        cargarMenu();
        
        cargarBarraHoy();
        
        cargarFavoritosGuardados();

    }

    private void cargarMenu()
    {
        treeMenu.setCellRenderer(new IconTreeRenderer());
        treeMenu.setOpaque(false);
        treeMenu.setRowHeight(26);
        treeMenu.setExpandsSelectedPaths(true);
        treeMenu.setBackground(null);

        XMLReaderMenu menuxml = new XMLReaderMenu(getClass().getResource("/config/menu.xml"));
        IconTreeModel itm = new IconTreeModel();
        itm.RellenarArbol(menuxml.cargarMenu());
        treeMenu.setModel(itm);

        treeMenu.setRootVisible(false);
        
        treeMenu.setSelectionRow(0);

    }

    public void updateMenu()
    {
        try
        {
            FavoritoBean[] listaFavs = SwingPanel.getInstance().getFavoritos();
            TreeEntry favMenu = new TreeEntry("Favoritos","/res/iconos/var/16x16/Favourites.png");
            TreeEntry leafFavoritos = (TreeEntry)treeMenu.getModel().getChild(treeMenu.getModel().getRoot(),0);
                // Limpio los viejos
                leafFavoritos.getEntries().clear();
                // Agrego los nuevos
                for (int i = 0; i < listaFavs.length; i++) 
                {
                    FavoritoBean fb = listaFavs[i];
                    if(fb!=null)
                    {
                        TreeEntry nuevo = new TreeEntry(fb.getNombre(),fb.getIcono());
                        nuevo.setClassInstance(fb.getInstance());
                        leafFavoritos.add(nuevo);
                    }
                }

            treeMenu.setSelectionRow(0);        
            treeMenu.updateUI();
            
        }catch(NullPointerException e)
        {
            e.printStackTrace(); /* do nothing ... */ }
    }
  
    private void CambiarDeUsuario()
    {
        int op = JOptionPane.showConfirmDialog(new JInternalFrame(), "<HTML>¿Está seguro que desea <b>Cerrar su Sesión</b> y <b>Cambiar de Usuario</b>?");
        if(op == JOptionPane.YES_OPTION)
        {
            // Guardo los datos del Usuario
            UserSession.getInstance().updateUser();
            // Cierro Sesion
            UserSession.getInstance().setUsuarioLogeado(null);
            // Cierro Conexion al a DB
            HibernateUtil.closeSession();
            // Abro ventana de Login
            UserLogin win = new UserLogin();
            win.setVisible(true);
            RefineryUtilities.centerFrameOnScreen(win);
            // Cierro esta ventana
            this.dispose();
        }
    }    
    
    private void CerrarSesion()
    {
        int op = JOptionPane.showConfirmDialog(new JInternalFrame(), "<HTML>¿Está seguro que desea <b>Cerrar la Sesión</b> y <b>Salir</b> del Sistema?");
        if(op == JOptionPane.YES_OPTION)
        {
            // Guardo los datos del Usuario
            UserSession.getInstance().updateUser();
             // Cierro Sesion
            UserSession.getInstance().setUsuarioLogeado(null);
            // Cierro Conexion al a DB
            HibernateUtil.closeSession();
            // Cierro el Sistema
            System.exit(0);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        panel = new javax.swing.JDesktopPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeMenu = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblFechaHoy = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        cmbUsuarios = new javax.swing.JMenu();
        cmbNuevoUsuario = new javax.swing.JMenuItem();
        cmbListadoUsuarios = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        cmbSalir = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        btnMenuListHerr = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnMenuListadoEmpresasCliente = new javax.swing.JMenuItem();
        btnMenuListadoProveedores = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        btnMenuListadoOrdenesCompra = new javax.swing.JMenuItem();
        btnMenuListadoPlanificaciones = new javax.swing.JMenuItem();
        btnMenuListadoCotizaciones = new javax.swing.JMenuItem();
        btnMnuRecursos = new javax.swing.JMenuItem();
        btnMnuObras = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btnMenuAyuda = new javax.swing.JMenuItem();
        btnMenuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Acá va el nombre del sistema");

        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setDoubleBuffered(true);
        jSplitPane2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSplitPane2MouseMoved(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setDoubleBuffered(true);
        panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelMouseMoved(evt);
            }
        });
        jSplitPane2.setRightComponent(panel);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jScrollPane1.setBorder(null);
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        treeMenu.setModel(new IconTreeModel());
        treeMenu.setExpandsSelectedPaths(false);
        treeMenu.setRootVisible(false);
        treeMenu.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeMenuValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeMenu);

        jTabbedPane1.addTab("Menú", jScrollPane1);

        lblFechaHoy.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblFechaHoy.setForeground(new java.awt.Color(57, 105, 138));
        lblFechaHoy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaHoy.setText("Junio 11");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFechaHoy, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFechaHoy)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 629, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inicio", jPanel2);

        jSplitPane2.setLeftComponent(jTabbedPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1592, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/favorite.png"))); // NOI18N
        jMenu8.setText("Sistema");

        cmbUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/users.png"))); // NOI18N
        cmbUsuarios.setText("Usuarios");

        cmbNuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/user.png"))); // NOI18N
        cmbNuevoUsuario.setText("Nuevo Usuario");
        cmbNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNuevoUsuarioActionPerformed(evt);
            }
        });
        cmbUsuarios.add(cmbNuevoUsuario);

        cmbListadoUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/users.png"))); // NOI18N
        cmbListadoUsuarios.setText("Listado de Usuarios");
        cmbListadoUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbListadoUsuariosActionPerformed(evt);
            }
        });
        cmbUsuarios.add(cmbListadoUsuarios);

        jMenu8.add(cmbUsuarios);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/warning.png"))); // NOI18N
        jMenuItem19.setText("Inicio");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem19);

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/lock.png"))); // NOI18N
        jMenuItem25.setText("Cambiar de Usuario");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem25);

        cmbSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cmbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        cmbSalir.setMnemonic('X');
        cmbSalir.setText("Cerrar Sesion y Salir");
        cmbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSalirActionPerformed(evt);
            }
        });
        jMenu8.add(cmbSalir);

        jMenuBar1.add(jMenu8);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Favourites.png"))); // NOI18N
        jMenu3.setText("Prototipos");

        jMenuItem21.setText("Ejecución");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem21);

        jMenuItem20.setText("Lanzamiento");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem20);

        jMenuItem4.setText("Registrar Empleado");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem3.setText("Editar Planificacion");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem5.setText("Cotizaciones");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem2.setText("Explorar Cotizaciones");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem1.setText("Registrar Pedido de Obra");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem7.setText("Ver Orden de Compra");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setText("Registrar Orden De Compra");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setText("Recepcion Orden De Compra");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Test Listados gen Callback");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenu4.setText("ABMs");

        jMenuItem11.setText("TestABM ALTA");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("TestABM BAJA");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem13.setText("TestABM MODIFICACION");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem14.setText("TestABM VER DETALLES");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenu3.add(jMenu4);

        jMenuBar1.add(jMenu3);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delivery.png"))); // NOI18N
        jMenu5.setText("Gestión");

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Equipment.png"))); // NOI18N
        jMenu6.setText("Herramientas");

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem15.setText("Listado");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem15);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem17.setText("Nueva");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem17);

        jMenu5.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Application.png"))); // NOI18N
        jMenu7.setText("Materiales");

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem16.setText("Listado");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem16);

        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem18.setText("Nuevo");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem18);

        jMenu5.add(jMenu7);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Wrench.png"))); // NOI18N
        jMenu9.setText("Herramienta de Empresa");

        btnMenuListHerr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        btnMenuListHerr.setText("Listado");
        btnMenuListHerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListHerrActionPerformed(evt);
            }
        });
        jMenu9.add(btnMenuListHerr);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem22.setText("Nueva");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem22);

        jMenu5.add(jMenu9);

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Boss.png"))); // NOI18N
        jMenu10.setText("Empresa Cliente");

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem23.setText("Listado");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem23);

        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem24.setText("Nuevo");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem24);

        jMenu5.add(jMenu10);

        jMenuBar1.add(jMenu5);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/List.png"))); // NOI18N
        jMenu2.setText("Listados");

        btnMenuListadoEmpresasCliente.setText("Empresas Cliente");
        btnMenuListadoEmpresasCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListadoEmpresasClienteActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListadoEmpresasCliente);

        btnMenuListadoProveedores.setText("Proveedores");
        btnMenuListadoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListadoProveedoresActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListadoProveedores);

        jMenuItem6.setText("Empleados");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        btnMenuListadoOrdenesCompra.setText("Ordenes de Compra");
        btnMenuListadoOrdenesCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListadoOrdenesCompraActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListadoOrdenesCompra);

        btnMenuListadoPlanificaciones.setText("Planificaciones");
        btnMenuListadoPlanificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListadoPlanificacionesActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListadoPlanificaciones);

        btnMenuListadoCotizaciones.setText("Cotizaciones");
        btnMenuListadoCotizaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListadoCotizacionesActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListadoCotizaciones);

        btnMnuRecursos.setText("Recursos");
        btnMnuRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMnuRecursosActionPerformed(evt);
            }
        });
        jMenu2.add(btnMnuRecursos);

        btnMnuObras.setText("Obras");
        btnMnuObras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMnuObrasActionPerformed(evt);
            }
        });
        jMenu2.add(btnMnuObras);

        jMenuBar1.add(jMenu2);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/help.png"))); // NOI18N
        jMenu1.setText("Ayuda");

        btnMenuAyuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        btnMenuAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/help.png"))); // NOI18N
        btnMenuAyuda.setMnemonic('F');
        btnMenuAyuda.setText("Tabla de Contenidos");
        btnMenuAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuAyudaActionPerformed(evt);
            }
        });
        jMenu1.add(btnMenuAyuda);

        btnMenuAcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/users.png"))); // NOI18N
        btnMenuAcercaDe.setText("Acerca De");
        jMenu1.add(btnMenuAcercaDe);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeMenuValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeMenuValueChanged
     
        try
        {
        
        TreeEntry node = (TreeEntry)evt.getPath().getLastPathComponent();
        
        if(node!=null)
        {
            node.clickEvent();
        }
        
        treeMenu.setSelectionRow(0);
               
        }catch(NullPointerException e){ e.printStackTrace();/* do nothing ... */ }
        
    }//GEN-LAST:event_treeMenuValueChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        pantallaConsultarLicenciasEmpleado pcle = new pantallaConsultarLicenciasEmpleado();
        SwingPanel.getInstance().addWindow(pcle);
        pcle.filtrarPorEmpleado(1); 
        pcle.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

        ExplorarCotizacionObra mod =  new ExplorarCotizacionObra();
        SwingPanel.getInstance().addWindow(mod);
        mod.setVisible(true);
}//GEN-LAST:event_jMenuItem5ActionPerformed

    private void panelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseMoved

      
    }//GEN-LAST:event_panelMouseMoved

    private void jScrollPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseMoved


    }//GEN-LAST:event_jScrollPane1MouseMoved

    private void jSplitPane2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSplitPane2MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jSplitPane2MouseMoved

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        
       /* if(btnSiderbarAutoHide.isSelected())
        {
            jSplitPane2.setDividerLocation(PANEL_DERECHO_SIZE);
        }*/
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        pantallaRegistrarPedido prp = new pantallaRegistrarPedido();
        SwingPanel.getInstance().addWindow(prp);
        prp.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    
        ExplorarCotizaciones ec = new ExplorarCotizaciones();
        SwingPanel.getInstance().addWindow(ec);
        ec.setVisible(true);
}//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       EditarPlanificacion ep = new EditarPlanificacion(1,1);
       SwingPanel.getInstance().addWindow(ep);
       ep.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void btnMenuAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuAyudaActionPerformed
    
        VisorDeAyuda.getInstance().mostrarAyuda();
        
    }//GEN-LAST:event_btnMenuAyudaActionPerformed

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
    pantallaRegistrarEmpleado ep = new pantallaRegistrarEmpleado();
       SwingPanel.getInstance().addWindow(ep);
       ep.setVisible(true);
}//GEN-LAST:event_jMenuItem4ActionPerformed

private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
    PantallaConsultarEmpleado ep = new PantallaConsultarEmpleado();
       SwingPanel.getInstance().addWindow(ep);
       ep.setVisible(true);
}//GEN-LAST:event_jMenuItem6ActionPerformed

    private void btnMenuListHerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListHerrActionPerformed
        pantallaConsultarHerramientas pcherr = new pantallaConsultarHerramientas();
        SwingPanel.getInstance().addWindow(pcherr);
        pcherr.setVisible(true);
    }//GEN-LAST:event_btnMenuListHerrActionPerformed

    private void btnMenuListadoEmpresasClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoEmpresasClienteActionPerformed
        pantallaListadoEmpresaCliente win = new pantallaListadoEmpresaCliente();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_btnMenuListadoEmpresasClienteActionPerformed

    private void btnMenuListadoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoProveedoresActionPerformed
        pantallaListadoProveedores win = new pantallaListadoProveedores();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_btnMenuListadoProveedoresActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        OLD_pantallaConsultarOC win = new OLD_pantallaConsultarOC();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);        
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void btnMenuListadoOrdenesCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoOrdenesCompraActionPerformed
        PantallaConsultarOrdenesDeCompra win = new PantallaConsultarOrdenesDeCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);      
    }//GEN-LAST:event_btnMenuListadoOrdenesCompraActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        OLD_pantallaGenerarOrdenCompra win = new OLD_pantallaGenerarOrdenCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);          
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        OLD_pantallaRegistrarRecepcionOrdenCompra win = new OLD_pantallaRegistrarRecepcionOrdenCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);  
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        TestCallBackListadoGenerico win = new TestCallBackListadoGenerico();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void btnMenuListadoPlanificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoPlanificacionesActionPerformed
        PantallaConsultarPlanificaciones win = new PantallaConsultarPlanificaciones();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_btnMenuListadoPlanificacionesActionPerformed

    private void btnMenuListadoCotizacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoCotizacionesActionPerformed
        ListadoCotizaciones win = new ListadoCotizaciones();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_btnMenuListadoCotizacionesActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        FactoryABM factory = new FactoryABM();
        PantallaABMGenerica win = factory.create(HerramientaDeEmpresa.class,PantallaABMGenerica.COMPORTAMIENTO_ALTA);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);         
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        FactoryABM factory = new FactoryABM();
        PantallaABMGenerica win = factory.create(HerramientaDeEmpresa.class,PantallaABMGenerica.COMPORTAMIENTO_BAJA);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);     
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        FactoryABM factory = new FactoryABM();
        PantallaABMGenerica win = factory.create(HerramientaDeEmpresa.class,PantallaABMGenerica.COMPORTAMIENTO_VER);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);     
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        FactoryABM factory = new FactoryABM();
        PantallaABMGenerica win = factory.create(HerramientaDeEmpresa.class,PantallaABMGenerica.COMPORTAMIENTO_MODIFICACION,1);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);     
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void btnMnuRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMnuRecursosActionPerformed
        ListadoRecursosEmpresa win = new ListadoRecursosEmpresa();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);         
    }//GEN-LAST:event_btnMnuRecursosActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        ListadoHerramientas win = new ListadoHerramientas(new FiltroPasivoHerramientas(null));
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);    
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        ListadoMateriales win = new ListadoMateriales(new FiltroPasivoMateriales(null));
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);   
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        PantallaGestionarRecursos win = new PantallaGestionarRecursos(Herramienta.class);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        PantallaGestionarRecursos win = new PantallaGestionarRecursos(Material.class);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void cmbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSalirActionPerformed
        CerrarSesion();
    }//GEN-LAST:event_cmbSalirActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        VentanaHome win = new VentanaHome();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true); 
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void btnMnuObrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMnuObrasActionPerformed
        ListadoPedidoDeObras win = new ListadoPedidoDeObras();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);         
    }//GEN-LAST:event_btnMnuObrasActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        VentanaLanzamiento win = new VentanaLanzamiento(1);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);   
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        ABMHerramientaDeEmpresa win = new ABMHerramientaDeEmpresa();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        VentanaEjecucion win = new VentanaEjecucion(1);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void cmbListadoUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbListadoUsuariosActionPerformed
        ListadoUsuarios win = new ListadoUsuarios();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_cmbListadoUsuariosActionPerformed

    private void cmbNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNuevoUsuarioActionPerformed
        ABMUsers win = new ABMUsers();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_cmbNuevoUsuarioActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        pantallaListadoEmpresaCliente win = new pantallaListadoEmpresaCliente();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        pantallaRegistrarEmpresaCliente pantalla = new pantallaRegistrarEmpresaCliente();
        SwingPanel.getInstance().addWindow(pantalla);
        pantalla.setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        CambiarDeUsuario();
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnMenuAcercaDe;
    private javax.swing.JMenuItem btnMenuAyuda;
    private javax.swing.JMenuItem btnMenuListHerr;
    private javax.swing.JMenuItem btnMenuListadoCotizaciones;
    private javax.swing.JMenuItem btnMenuListadoEmpresasCliente;
    private javax.swing.JMenuItem btnMenuListadoOrdenesCompra;
    private javax.swing.JMenuItem btnMenuListadoPlanificaciones;
    private javax.swing.JMenuItem btnMenuListadoProveedores;
    private javax.swing.JMenuItem btnMnuObras;
    private javax.swing.JMenuItem btnMnuRecursos;
    private javax.swing.JMenuItem cmbListadoUsuarios;
    private javax.swing.JMenuItem cmbNuevoUsuario;
    private javax.swing.JMenuItem cmbSalir;
    private javax.swing.JMenu cmbUsuarios;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFechaHoy;
    private javax.swing.JDesktopPane panel;
    private javax.swing.JTree treeMenu;
    // End of variables declaration//GEN-END:variables

    public void mostrarAyuda(String titulo, String desc, int id)
    {

    }

    private void cargarFavoritosGuardados() 
    {
        if(UserSession.getInstance().isLogeado())
        {
            SwingPanel.getInstance().fillFavoritos(UserSession.getInstance().getUsuarioLogeado().getListaFavoritos());
            updateMenu();
        }
    }

    private void cargarHomeScreen() {
        
        VentanaHome win = new VentanaHome();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
        
    }

    /**
     * Arma y setea los datos de la barra lateral de Hoy !!
     */
    private void cargarBarraHoy() {
        
        // Seteo la Fecha !!
        String msg = "Mayo 23";
        lblFechaHoy.setText(msg);
        
    }

    private void initWindowClosing() {
        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        CerrarSesion();
                    }
                });
    }

}
