package vista.ejecucion.lanzamiento;

import controlador.ejecucion.lanzamiento.GestorVentanaLanzamiento;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Iuga
 */
public class VentanaLanzamiento extends javax.swing.JInternalFrame {

    protected static final int OPTN_HERRAMIENTAS    = 0;
    protected static final int OPTN_MATERIALES      = 1;    
    protected static final int OPTN_ALQUILERCOMPRA  = 2;    
    protected static final int OPTN_RRHH            = 3;    
    protected static final int OPTN_ADICIONALES     = 4;    
    protected static final int OPTN_ORDENESDETRABAJO= 5;    
    
    private PanelHerramientas panelHerramientas;
    private PanelMateriales   panelMateriales;
    private PanelAlquileresCompras panelAlquileresCompras;
    private PanelRecursosHumanos panelRecursosHumanos;
    private PanelAdicionales panelAdicionales;
    private PanelOrdenesDeTrabajo panelOrdenesDeTrabajo;
    
    private GestorVentanaLanzamiento gestor;
    
    /**
     * Crea una nueva ventana de Lanzamiento.
     * @param idObra id de la obra en cuestion
     */
    public VentanaLanzamiento(int idObra) {
        // Inicializo Ventana
        initComponents();
        // Todas las solapas comparten el mismo gestor, así simplificamos !!
        this.gestor = new GestorVentanaLanzamiento(idObra);
        // Verifico si la obra esta en ejecucion
        checkSiObraTieneEjecución(idObra);
        // Segun el estado de la ejecucion, cambio el comportamiento
        cambiarSegunEstadoEjecucion();
        // Comportamiento del menu lateral
        clickMenuLateral();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        pnlCentral = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Lanzamiento de la Obra");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Menú de Recursos"));

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Herramientas"},
                {"Materiales"},
                {"Alquileres/Compras"},
                {"Recursos Humanos"},
                {"Adicionales"},
                {"Ordenes de Trabajo"}
            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMenu.setIntercellSpacing(new java.awt.Dimension(10, 5));
        tblMenu.setMaximumSize(new java.awt.Dimension(300, 300));
        tblMenu.setRowHeight(25);
        tblMenu.setTableHeader(null);
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMenuMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );

        pnlCentral.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));
        pnlCentral.setLayout(new java.awt.BorderLayout());

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(619, 619, 619)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMousePressed
        clickMenuLateral();
    }//GEN-LAST:event_tblMenuMousePressed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
       // llamo al comportamiento Cancelar de la ventana 
        cerrar();       
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained

    }//GEN-LAST:event_formFocusGained

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlCentral;
    private javax.swing.JTable tblMenu;
    // End of variables declaration//GEN-END:variables

    private void clickMenuLateral() {
        DefaultTableModel modelo = (DefaultTableModel) tblMenu.getModel();
        switch(tblMenu.getSelectedRow())
        {
            case OPTN_HERRAMIENTAS:
            default:
                setNombrePanel(modelo.getValueAt(OPTN_HERRAMIENTAS, 0).toString());
                if(this.panelHerramientas==null){
                    this.panelHerramientas = new PanelHerramientas(this.gestor);
                }
                this.panelHerramientas.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelHerramientas, BorderLayout.CENTER);
                this.panelHerramientas.setVisible(true);  
                this.updateUI();
                break;
            case OPTN_MATERIALES:
                setNombrePanel(modelo.getValueAt(OPTN_MATERIALES,0).toString());
                if(this.panelMateriales==null){
                    this.panelMateriales =  new PanelMateriales(this.gestor);
                }
                this.panelMateriales.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelMateriales, BorderLayout.CENTER);
                this.panelMateriales.setVisible(true);  
                this.updateUI();
                break;
            case OPTN_ALQUILERCOMPRA:
                setNombrePanel(modelo.getValueAt(OPTN_ALQUILERCOMPRA,0).toString());
                if(this.panelAlquileresCompras==null){
                    this.panelAlquileresCompras =  new PanelAlquileresCompras(this.gestor);
                }
                this.panelAlquileresCompras.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelAlquileresCompras, BorderLayout.CENTER);
                this.panelAlquileresCompras.setVisible(true);  
                this.updateUI();
                break;
            case OPTN_RRHH:
                setNombrePanel(modelo.getValueAt(OPTN_RRHH,0).toString());
                if(this.panelRecursosHumanos==null){
                    this.panelRecursosHumanos =  new PanelRecursosHumanos(this.gestor);
                }
                //this.panelRecursosHumanos.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelRecursosHumanos, BorderLayout.CENTER);
                this.panelRecursosHumanos.setVisible(true);  
                this.updateUI();
                break;
            case OPTN_ADICIONALES:
                setNombrePanel(modelo.getValueAt(OPTN_ADICIONALES,0).toString());
                if(this.panelAdicionales==null){
                    this.panelAdicionales =  new PanelAdicionales(this.gestor);
                }
                this.panelAdicionales.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelAdicionales, BorderLayout.CENTER);
                this.panelAdicionales.setVisible(true);  
                this.updateUI();
                break;
            case OPTN_ORDENESDETRABAJO:
                setNombrePanel(modelo.getValueAt(OPTN_ORDENESDETRABAJO,0).toString());
                if(this.panelOrdenesDeTrabajo==null){
                    this.panelOrdenesDeTrabajo =  new PanelOrdenesDeTrabajo(this.gestor);
                }
                this.panelOrdenesDeTrabajo.actualizarDatos();
                pnlCentral.removeAll();
                pnlCentral.add(this.panelOrdenesDeTrabajo, BorderLayout.CENTER);
                this.panelOrdenesDeTrabajo.setVisible(true);  
                this.updateUI();
                break;                
        } 
    }
    
    protected void setNombrePanel(String nombre){
         pnlCentral.setBorder(javax.swing.BorderFactory.createTitledBorder(nombre));
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
     * Verifica si la obra tiene ejecucion,si no la tiene esta ventana
     * debería advertirlo y cerrarse.
     */
    private void checkSiObraTieneEjecución(int idObra) {
        
    }

    /**
     * Segun el estado de la ejecucion, cambio el comportamiento.
     * Si está en alta, o en definicion, dejo todo editable
     * Si esta en baja o cancelada, no dejo editar nada.
     */
    private void cambiarSegunEstadoEjecucion() {
        
    }

    /**
     * Metodo a llamar cuando se quieren guardar los cambios !!
     * @return 
     */
    private boolean guardar() {
        return true;
    }

    /**
     * Cancela los cambios y cierra la ventana.
     * Antes pregunta si desea guardar los cambios.
     */
    private void cerrar() {
        this.dispose();
    }

    private void actualizarPaneles() {
        if(this.panelHerramientas!=null){
            this.panelHerramientas.actualizarDatos();
        }
        if(this.panelMateriales!=null){
            this.panelMateriales.actualizarDatos();
        }
        if(this.panelAdicionales!=null){
            this.panelAdicionales.actualizarDatos();
        }
        if(this.panelAlquileresCompras!=null){
            this.panelAlquileresCompras.actualizarDatos();
        }
        if(this.panelOrdenesDeTrabajo!=null){
            this.panelOrdenesDeTrabajo.actualizarDatos();
        }
    }
    
}
