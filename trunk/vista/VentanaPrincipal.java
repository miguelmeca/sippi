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
import modelo.HerramientaDeEmpresa;
import net.sf.cglib.proxy.Factory;
import test.TestABM;
import test.TestCallBackListadoGenerico;
import vista.ayuda.VisorDeAyuda;
import vista.comer.pantallaListadoEmpresaCliente;
import vista.comer.pantallaListadoProveedores;
import vista.compras.*;
import vista.cotizacion.ListadoCotizaciones;
import vista.gen.FactoryABM;
import vista.gen.PantallaABMGenerica;
import vista.planificacion.EditarPlanificacion;
import vista.planificacion.PantallaConsultarPlanificaciones;
import vista.rrhh.PantallaConsultarEmpleado;
import vista.rrhh.pantallaConsultarLicenciasEmpleado;
import vista.rrhh.pantallaRegistrarEmpleado;



/**
 *
 * @author iuga
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private static final int PANEL_DERECHO_SIZE = 250;

    /** Creates new form VentanaPrincipal */
    public VentanaPrincipal() {
        initComponents();

        this.jpbCargando.setVisible(false);

        // Mando el Panel a un Singleton para poder accederlo de manera unica
        SwingPanel.getInstance().setPane(panel);
        SwingPanel.getInstance().setVentanaPrincipal(this);

        this.setExtendedState(MAXIMIZED_BOTH);

        jSplitPane2.setDividerLocation(PANEL_DERECHO_SIZE);
        this.setTitle(SConfig.getInstance().getNombreSistema() + " | "+ SConfig.getInstance().getNombreEmpresa() + " - " + SConfig.getInstance().getDireccionEmpresa());
        
        cargarMenu();
        
        cargarFavoritosGuardados();
        
        //cargarHomeScreen();
    }

    public void mostrarCargando(boolean flag)
    {
        if(flag==true)
        {
                jpbCargando.setVisible(true);
                setProgress(10);
                jpbCargando.setString("Cargando...");
                jpbCargando.setIndeterminate(true);
                jPanelCargando.update(jPanelCargando.getGraphics());

          setProgress(20);
          jPanelCargando.update(jPanelCargando.getGraphics());
          setProgress(30);
          jPanelCargando.update(jPanelCargando.getGraphics());

        }
        else
        {
            jPanelCargando.setVisible(false);
        }
    }

    public void setProgress(final int progress)
    {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            jpbCargando.setValue(progress);
          }
        });
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
    
    private void Salir()
    {
        int op = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?");
        if(op == JOptionPane.YES_OPTION)
        {
            // Guardo los datos del Usuario
            UserSession.getInstance().updateUser();
            HibernateUtil.closeSession();
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
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSiderbarAutoHide = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSalir = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jPanelCargando = new javax.swing.JPanel();
        jpbCargando = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        panel = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeMenu = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
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
        jMenu2 = new javax.swing.JMenu();
        btnMenuListadoEmpresasCliente = new javax.swing.JMenuItem();
        btnMenuListadoProveedores = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        btnMenuListHerr = new javax.swing.JMenuItem();
        btnMenuListadoOrdenesCompra = new javax.swing.JMenuItem();
        btnMenuListadoPlanificaciones = new javax.swing.JMenuItem();
        btnMenuListadoCotizaciones = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btnMenuAyuda = new javax.swing.JMenuItem();
        btnMenuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Acá va el nombre del sistema");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSiderbarAutoHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/fullscreen.png"))); // NOI18N
        btnSiderbarAutoHide.setFocusable(false);
        btnSiderbarAutoHide.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSiderbarAutoHide.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSiderbarAutoHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiderbarAutoHideActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSiderbarAutoHide);
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

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/warning.png"))); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.setFocusable(false);
        btnInicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnInicio);
        jToolBar1.add(jSeparator2);

        jpbCargando.setDoubleBuffered(true);
        jpbCargando.setString("Cargando...");
        jpbCargando.setStringPainted(true);

        javax.swing.GroupLayout jPanelCargandoLayout = new javax.swing.GroupLayout(jPanelCargando);
        jPanelCargando.setLayout(jPanelCargandoLayout);
        jPanelCargandoLayout.setHorizontalGroup(
            jPanelCargandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCargandoLayout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(jpbCargando, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelCargandoLayout.setVerticalGroup(
            jPanelCargandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpbCargando, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jToolBar1.add(jPanelCargando);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        jSplitPane2.setDividerLocation(200);
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

        jSplitPane2.setTopComponent(jScrollPane1);

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
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
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

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Favourites.png"))); // NOI18N
        jMenu3.setText("Prototipos");

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

        btnMenuListHerr.setText("Herramientas");
        btnMenuListHerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuListHerrActionPerformed(evt);
            }
        });
        jMenu2.add(btnMenuListHerr);

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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        Salir();

    }//GEN-LAST:event_btnSalirActionPerformed

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

        if(btnSiderbarAutoHide.isSelected())
        {
            jSplitPane2.setDividerLocation(0);
        }
        
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

private void btnSiderbarAutoHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiderbarAutoHideActionPerformed

    if(btnSiderbarAutoHide.isSelected())
    {
        jSplitPane2.setDividerLocation(0);
    }
    else
    {
        jSplitPane2.setDividerLocation(PANEL_DERECHO_SIZE);
    }
    
}//GEN-LAST:event_btnSiderbarAutoHideActionPerformed

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
        pantallaConsultarOC win = new pantallaConsultarOC();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);        
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void btnMenuListadoOrdenesCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuListadoOrdenesCompraActionPerformed
        PantallaConsultarOrdenesDeCompra win = new PantallaConsultarOrdenesDeCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);      
    }//GEN-LAST:event_btnMenuListadoOrdenesCompraActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        pantallaGenerarOrdenCompra win = new pantallaGenerarOrdenCompra();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);          
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        pantallaRegistrarRecepcionOrdenCompra win = new pantallaRegistrarRecepcionOrdenCompra();
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

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        VentanaHome win = new VentanaHome();
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);         
    }//GEN-LAST:event_btnInicioActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicio;
    private javax.swing.JMenuItem btnMenuAcercaDe;
    private javax.swing.JMenuItem btnMenuAyuda;
    private javax.swing.JMenuItem btnMenuListHerr;
    private javax.swing.JMenuItem btnMenuListadoCotizaciones;
    private javax.swing.JMenuItem btnMenuListadoEmpresasCliente;
    private javax.swing.JMenuItem btnMenuListadoOrdenesCompra;
    private javax.swing.JMenuItem btnMenuListadoPlanificaciones;
    private javax.swing.JMenuItem btnMenuListadoProveedores;
    private javax.swing.JButton btnSalir;
    private javax.swing.JToggleButton btnSiderbarAutoHide;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JPanel jPanelCargando;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JProgressBar jpbCargando;
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

}
