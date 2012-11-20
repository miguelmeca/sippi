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
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import modelo.FavoritoBean;
import modelo.Herramienta;
import modelo.HerramientaDeEmpresa;
import modelo.Material;
import org.jfree.ui.RefineryUtilities;
import test.TestCallBackListadoGenerico;
import util.HibernateUtil;
import util.NTupla;
import util.SwingPanel;
import util.Tupla;
import vista.abms.*;
import vista.ayuda.VentanaAcercaDe;
import vista.ayuda.VisorDeAyuda;
import vista.comer.ListadoPedidoDeObras;
import vista.comer.pantallaListadoEmpresaCliente;
import vista.comer.pantallaListadoProveedores;
import vista.comer.pantallaRegistrarEmpresaCliente;
import vista.compras.*;
import vista.control.VentanaControl;
import vista.cotizacion.ExplorarCotizacionObra;
import vista.cotizacion.ExplorarCotizaciones;
import vista.cotizacion.ListadoCotizaciones;
import vista.ejecucion.VentanaEjecucion;
import vista.ejecucion.lanzamiento.VentanaLanzamiento;
import vista.gen.FactoryABM;
import vista.gen.PantallaABMGenerica;
import vista.gui.sidebar.IconTreeModel;
import vista.gui.sidebar.IconTreeRenderer;
import vista.gui.sidebar.TreeEntry;
import vista.planificacion.EditarPlanificacion;
import vista.planificacion.PantallaConsultarPlanificaciones;
import vista.rrhh.*;
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
        pnlToday = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblFechaHoy = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstTareas = new JList() {
            // This method is called as the cursor moves within the list.
            public String getToolTipText(MouseEvent evt) {
                // Get item index
                int index = locationToIndex(evt.getPoint());

                // Get item
                NTupla item = (NTupla)getModel().getElementAt(index);

                // Return the tool tip text
                return getToolTipFromTarea(item);
            }
        };
        jLabel1 = new javax.swing.JLabel();
        tareasDateChooser = new com.toedter.calendar.JDateChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        cmbUsuarios = new javax.swing.JMenu();
        cmbNuevoUsuario = new javax.swing.JMenuItem();
        cmbListadoUsuarios = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        cmbSalir = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
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
        jMenu11 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
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
            .addComponent(lblFechaHoy, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFechaHoy)
        );

        lstTareas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "-" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstTareas);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Tareas creadas para la fecha:");

        javax.swing.GroupLayout pnlTodayLayout = new javax.swing.GroupLayout(pnlToday);
        pnlToday.setLayout(pnlTodayLayout);
        pnlTodayLayout.setHorizontalGroup(
            pnlTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(tareasDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTodayLayout.setVerticalGroup(
            pnlTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTodayLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tareasDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hoy", pnlToday);

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

        jMenuItem3.setText("Planificacion");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

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

        jMenuItem4.setText("Control");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

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

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delivery.png"))); // NOI18N
        jMenu11.setText("Proveedores");

        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem26.setText("Listado");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem26);

        jMenuItem27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem27.setText("Nuevo");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem27);

        jMenu5.add(jMenu11);

        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/users.png"))); // NOI18N
        jMenu12.setText("Empleados");

        jMenuItem28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem28.setText("Listado");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem28);

        jMenuItem29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem29.setText("Nuevo");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem29);

        jMenu5.add(jMenu12);

        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Yellow tag.png"))); // NOI18N
        jMenu13.setText("Taller de Capacitación");

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        jMenuItem7.setText("Listado");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jMenuItem8.setText("Nuevo");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept_page.png"))); // NOI18N
        jMenuItem9.setText("Registrar Asistencia");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem9);

        jMenuItem31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/People.png"))); // NOI18N
        jMenuItem31.setText("Registrar Capacitador");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem31);

        jMenu5.add(jMenu13);

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
        btnMenuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuAcercaDeActionPerformed(evt);
            }
        });
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

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       EditarPlanificacion ep = new EditarPlanificacion(1,1);
       SwingPanel.getInstance().addWindow(ep);
       ep.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void btnMenuAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuAyudaActionPerformed
    
        VisorDeAyuda.getInstance().mostrarAyuda();
        
    }//GEN-LAST:event_btnMenuAyudaActionPerformed

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       VentanaControl ep = new VentanaControl(1);
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

    private void btnMenuListadoOrdenesCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoOrdenesCompraActionPerformed
        PantallaConsultarOrdenesDeCompra win = new PantallaConsultarOrdenesDeCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);      
    }//GEN-LAST:event_btnMenuListadoOrdenesCompraActionPerformed

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

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        pantallaListadoProveedores win = new pantallaListadoProveedores();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        ABMProveedor win = new ABMProveedor();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
       PantallaConsultarEmpleado ep = new PantallaConsultarEmpleado();
       SwingPanel.getInstance().addWindow(ep);
       ep.setVisible(true);
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        pantallaRegistrarEmpleado pre = new pantallaRegistrarEmpleado();
        SwingPanel.getInstance().addWindow(pre);
        pre.setVisible(true);
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        pantallaConsultarTallerCapacitacion win = new pantallaConsultarTallerCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        pantallaRegistrarTallerCapacitacion prtc = new pantallaRegistrarTallerCapacitacion();
        SwingPanel.getInstance().addWindow(prtc);
        prtc.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        pantallaRegistrarAsistenciaTallerCapacitacion win = new pantallaRegistrarAsistenciaTallerCapacitacion();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);        
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        pantallaRegistrarCapacitador prc = new pantallaRegistrarCapacitador();
        SwingPanel.getInstance().addWindow(prc);
        prc.setVisible(true);         // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void btnMenuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuAcercaDeActionPerformed
        VentanaAcercaDe ventana = new VentanaAcercaDe();
        SwingPanel.getInstance().addWindow(ventana);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnMenuAcercaDeActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFechaHoy;
    private javax.swing.JList lstTareas;
    private javax.swing.JDesktopPane panel;
    private javax.swing.JPanel pnlToday;
    private com.toedter.calendar.JDateChooser tareasDateChooser;
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
        // Seteo la Fecha de Hoy !!
        String msg = VentanaPrincipalUtils.getFechaActual();
        lblFechaHoy.setText(msg);
        
        // Setear Date change listerner
        tareasDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("date".equals(e.getPropertyName())) {
                    System.out.println(e.getPropertyName() + ": " + (Date) e.getNewValue());
                    cargarTareasParaHoy((Date) e.getNewValue());
                }
            }
        });
 
        // Activar listener
        tareasDateChooser.setDate(new Date());
    }

    /**
     * Recibe una fecha y carga todas las tareas para hoy.
     */
    private void cargarTareasParaHoy(Date date) {
        if(date!=null){
            List<NTupla> tpTareas = VentanaPrincipalUtils.getTareasParaFecha(date);
            
            lstTareas.removeAll();
            
            DefaultListModel modelo = new DefaultListModel();  
            
            if(tpTareas.isEmpty()){
                NTupla empty = new NTupla(-1);
                empty.setNombre("No hay tareas asignadas para Hoy");
                modelo.addElement(empty);
            }
            else{
                for (int i = 0; i < tpTareas.size(); i++) {
                    NTupla tupla = tpTareas.get(i);
                    modelo.addElement(tupla);
                }    
            }
            
            lstTareas.setModel(modelo);
        }
    }

    /**
     * Hagarra la NTupla de la lista de tareas para hoy y arma un ToolTip.
     * @param item
     * @return 
     */
    public String getToolTipFromTarea(NTupla item){
        String msg = "<HTML>";
        
            if(item!=null){
                msg += "<b>Tarea: </b>"+item.getNombre();
                
                // Metadata
                String[] data = (String[])item.getData();
                if(data!=null){
                    
                    if(data[3]!=null){
                        msg += "<br>";
                        msg += "<b>Fecha de Inicio: </b><i>"+data[3]+"</i>";
                    }
                    
                    if(data[4]!=null){
                        msg += "<br>";
                        msg += "<b>Fecha de Fin: </b><i>"+data[4]+"</i>";
                    }
                    
                    if(data[2]!=null){
                        msg += "<br>";
                        msg += "<span color='#00F53D'><b>Obra: </b><i>"+data[2]+"</i></span>";
                    }
                }
            }
        
        return msg;
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
