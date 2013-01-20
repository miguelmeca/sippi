package vista.gen;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cotizacion;
import modelo.CotizacionModificada;
import modelo.Ejecucion;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import vista.interfaces.ICallBackGen;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Administrador
 */
public abstract class PantallaConsultarGenerica extends javax.swing.JInternalFrame {

    private Class entidad;
    private HashMap<String,String> columnas;
    
    private String nombreOrigenCallback = "";
    private ICallBackGen origenCallback = null;
    
    private String filtroActivo;
    private FiltroPasivo filtoPasivo;
    
    /** Creates new form pantallaConsultar */
    public PantallaConsultarGenerica(Class entidad) {
        
        this.entidad = entidad;
                
        filtroActivo = new String();
        
        initComponents();
        initConfig();
        habilitarVentana();
    }

    public PantallaConsultarGenerica(Class entidad,String filtoActivo) {
        this.entidad = entidad;                
        this.filtroActivo = filtoActivo;
        initComponents();
        initConfig();
        habilitarVentana();
    }   
    
    public PantallaConsultarGenerica(Class entidad,FiltroPasivo filtoPasivo) {
        this.entidad = entidad;                
        this.filtoPasivo = filtoPasivo;
        filtroActivo = new String();
        initComponents();
        initConfig();
        habilitarVentana();
    }        
    
    private void habilitarVentana()
    {
        cargarDatosIniciales();
        cmbColumna.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXX");
        cmbColumnavalor.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXX");
        if(getColumnasFiltro()!=null && getColumnasFiltro().length!=0)
        {
            mostrarFiltrosColumna(true);   
            cargarFiltrosColumna();
        }
        else
        {
            mostrarFiltrosColumna(false);
        }
    }

    private void cargarDatosIniciales()
    {
        // Limpio el modelo Anterior
        ArrayList<ArrayList<Tupla>> data = new ArrayList<ArrayList<Tupla>>();
        
        // Cargo los datos iniciales de la tabla
        try{
            Session sesion= HibernateUtil.getSession();
            sesion.beginTransaction();
                List lista=null;
                // CREO LA CONSULTA, CON O SIN FILTROS
                if(getFiltrosActivos().isEmpty())
                {
                    if(!this.filtroActivo.isEmpty())
                    {
                        lista = sesion.createQuery("FROM "+this.entidad.getSimpleName()+" As SC WHERE "+this.filtroActivo).list();
                    }
                    else
                    {
                        lista = sesion.createQuery("FROM "+this.entidad.getSimpleName()).list();
                    }
                }
                else
                {
                    lista = sesion.createQuery("FROM "+this.entidad.getSimpleName()+" As SC WHERE "+getFiltrosActivos()).list();
                }
                
                if(this.filtoPasivo!=null)
                {
                    lista = this.filtoPasivo.Excecute(lista);
                }
                
                
                // INICIO DEL WORKARROUND ================================================================
                boolean aplicoWorkArround = false;
                List filtroWorkArround = new ArrayList<Object>();
                // WORKARROUND COTIZACION
                if(this.entidad.getSimpleName().equals("Cotizacion")){
                    aplicoWorkArround = true;
                    System.out.println("Aplicando el workarround para Cotizacion");
                    for (int i = 0; i < lista.size(); i++) {
                        if(lista.get(i) instanceof CotizacionModificada){
                            System.out.println("Este item NO es una Cotizacion, lo filtro");
                        }else{
                            System.out.println("Este item es es una Cotizacion");
                            filtroWorkArround.add(lista.get(i));
                        }                        
                    }
                }
                // WORKARROUND PLANIFICACION
                if(this.entidad.getSimpleName().equals("Planificacion")){
                    System.out.println("Aplicando el workarround para Planificacion");
                    aplicoWorkArround = true;
                    for (int i = 0; i < lista.size(); i++) {
                        if(lista.get(i) instanceof Ejecucion){
                            System.out.println("Este item NO es una Planificacion, lo filtro");
                        }else{
                            System.out.println("Este item es es una Planificacion");
                            filtroWorkArround.add(lista.get(i));
                        }                        
                    } 
                }
                if(aplicoWorkArround){
                    lista = filtroWorkArround;
                }
                // FIN DEL WORKARROUND ===================================================================
                
                for (int i = 0; i < lista.size(); i++) 
                {
                    Object obj = lista.get(i);
                    // Reflexiono un poco
                    Iterator it = getColumnas().iterator();
                    
                    ArrayList<Tupla> fila = new ArrayList<Tupla>();
                    
                    while (it.hasNext()) {
                        String e[] = (String[])it.next();
                        System.out.println(e[0] + " " + e[1]);
                        
                            java.lang.reflect.Method method; 
                            try 
                            { 
                                method = obj.getClass().getMethod((String)e[0]);
                                try { 
                                    String result = (String) String.valueOf(method.invoke(obj));
                                    System.out.println("Resultado Reflection: "+result);
                                    
                                    java.lang.reflect.Method methodGetId = obj.getClass().getMethod("getId");
                                    String id = (String) String.valueOf(methodGetId.invoke(obj));
                                    
                                    String content = "";
                                    if(content!=null){
                                        content = result;
                                    }
                                    
                                    String idContent = "0";
                                    if(id!=null){
                                        idContent = id;
                                    }
                                    
                                    fila.add(new Tupla(Integer.parseInt(idContent), content ));
                                    
                                }  catch (Exception ex) 
                                {
                                    System.out.println("No se pudo ejecutar el método");
                                    fila.add(new Tupla(-1,"---"));
                                } 
                                
                            } 
                            catch (Exception ep) { 
                                System.out.println("No se encontro el metodo");
                            }
                    }
                    data.add(fila);

                }
            sesion.getTransaction().commit();
        } catch (Exception ex)
        {
            System.out.println("No se pudo abrir la sesion");
        }
        
        // Armo el modelo
        
        DefaultTableModel modelo = new DefaultTableModel(parseData(data), parseColumNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };
        tblLista.setModel(modelo);
        
        // Muestro la cantidad de Ocurrencias
        mostrarCantidadFilas();
        
    }

    private void setCantidadResultados(int cant)
    {
        lblCantResultados.setText("Cantidad de Resultados: "+cant);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();
        lblCantResultados = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JButton();
        btnVerDetalles = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnRefrescar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        txtFiltro = new javax.swing.JLabel();
        txtFiltroColumna = new javax.swing.JLabel();
        cmbColumna = new javax.swing.JComboBox();
        txtFiltroValor = new javax.swing.JLabel();
        cmbColumnavalor = new javax.swing.JComboBox();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar ...");

        tblLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Legajo", "Nombre", "Apellido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLista.setSelectionBackground(new java.awt.Color(153, 153, 255));
        tblLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblListaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblLista);

        lblCantResultados.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblCantResultados.setText("Cantidad: ");

        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnSeleccionar.setText("Cerrar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        btnVerDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N
        btnVerDetalles.setText("Ver Detalles / Modificar");
        btnVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetallesActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jToolBar1.setRollover(true);

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/refresh.png"))); // NOI18N
        btnRefrescar.setText("Actualizar");
        btnRefrescar.setToolTipText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefrescar);
        jToolBar1.add(jSeparator3);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        jToolBar1.add(jLabel1);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
        txtBuscar.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        txtBuscar.setMinimumSize(new java.awt.Dimension(300, 20));
        txtBuscar.setPreferredSize(new java.awt.Dimension(200, 20));
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });
        jToolBar1.add(txtBuscar);

        jToolBar2.setRollover(true);

        txtFiltro.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFiltro.setText("  Fltro:");
        txtFiltro.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar2.add(txtFiltro);

        txtFiltroColumna.setText("  Columna:  ");
        jToolBar2.add(txtFiltroColumna);

        cmbColumna.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbColumna.setPreferredSize(new java.awt.Dimension(28, 25));
        cmbColumna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColumnaActionPerformed(evt);
            }
        });
        jToolBar2.add(cmbColumna);

        txtFiltroValor.setText("  Valor:  ");
        jToolBar2.add(txtFiltroValor);

        cmbColumnavalor.setMinimumSize(new java.awt.Dimension(150, 25));
        cmbColumnavalor.setPreferredSize(new java.awt.Dimension(28, 25));
        cmbColumnavalor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColumnavalorActionPerformed(evt);
            }
        });
        jToolBar2.add(cmbColumnavalor);

        jToolBar1.add(jToolBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblCantResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                        .addGap(133, 133, 133)
                        .addComponent(btnImprimir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerDetalles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccionar))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantResultados)
                    .addComponent(btnSeleccionar)
                    .addComponent(btnVerDetalles)
                    .addComponent(btnImprimir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked

        if(txtBuscar.getText().equals("Buscar..."))
        {
            txtBuscar.setText("");
        }

    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        activarFiltrosTabla();
//        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
//        {
//            // LANZO EL BUSCAR
//            activarFiltrosTabla();
//        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        cargarDatosIniciales();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void tblListaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaMouseReleased
       // Si seleccione una Fila...
       if(tblLista.getSelectedRow()!=-1)
       {
           // y fue con doble click...
           if (evt.getClickCount() == 2)
           {
                int id = getIDfromFila(tblLista.getSelectedRow());
                // Si tiene callback, llamo al seleccionar!
                if(!this.nombreOrigenCallback.isEmpty() && this.origenCallback!=null)
                {
                    seleccionarEntidad(id);
                }
                else
                {
                    // Llamo al abrir!
                    abrirEntidad(id);
                }
           }
        }
    }//GEN-LAST:event_tblListaMouseReleased

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed

        // Veo si hay que seleccionar una fila
        if(!this.nombreOrigenCallback.isEmpty() && this.origenCallback!=null)
        {
            // Busco algun ID en la fila != -1
            if(tblLista.getSelectedRow()!=-1)
            {
                int id = getIDfromFila(tblLista.getSelectedRow());
                seleccionarEntidad(id);
            }
            else
            {
                JOptionPane.showMessageDialog(new JInternalFrame(),"Debe seleccionar al menos una fila!","Atención!",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            this.dispose();
        }
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
       if(tblLista.getSelectedRow()!=-1)
       {
          int id = getIDfromFila(tblLista.getSelectedRow());
          abrirEntidad(id);
       }
       else
       {
            JOptionPane.showMessageDialog(new JInternalFrame(),"Debe seleccionar al menos una Fila!","Atención!",JOptionPane.INFORMATION_MESSAGE);                   
       }
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        
        // Recopilo los datos
        DefaultTableModel modelo = (DefaultTableModel)tblLista.getModel();
        
        String [][] datos = new String[modelo.getRowCount()][modelo.getColumnCount()];
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            for (int j = 0; j < modelo.getRowCount(); j++) {
                
                String dato = String.valueOf(modelo.getValueAt(j, i));
                datos[j][i] = dato;
            }
        }
        
        String[] columnas = new String[getColumnas().size()];
        for (int i = 0; i < getColumnas().size(); i++) {
            String[] data = getColumnas().get(i);
            columnas[i] = data[1];
        }
        
        imprimir(getNombreVentana(),columnas,datos);
        
        JOptionPane.showMessageDialog(new JInternalFrame(),"Generando el Reporte\nPor favor aguarde...!","Atención!",JOptionPane.INFORMATION_MESSAGE,new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/32x32/promotion.png")));                   
        
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void cmbColumnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColumnaActionPerformed
        // Saco el valor del elemento seleccionado
        String valor = (String) cmbColumna.getItemAt(cmbColumna.getSelectedIndex());
        if(valor!=null && !valor.isEmpty())
        {
            cargarFiltrosValorColumna(valor);
        }
        else
        {
            // Limpio el otro combo
           cmbColumnavalor.removeAllItems();
           cmbColumnavalor.addItem("");
           cmbColumnavalor.setMinimumSize(new Dimension(150,18));
        }
    }//GEN-LAST:event_cmbColumnaActionPerformed

    private void cmbColumnavalorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColumnavalorActionPerformed
        // Saco el valor del elemento seleccionado
        String valor = (String) cmbColumnavalor.getItemAt(cmbColumnavalor.getSelectedIndex());
        if(valor!=null && !valor.isEmpty())
        {
            // Filtro !
           TableRowSorter<TableModel> modeloOrdenado;

           modeloOrdenado = new TableRowSorter<TableModel>(tblLista.getModel());
           tblLista.setRowSorter(modeloOrdenado);
        
           String[] cadena= new String[1];
           cadena[0] = valor;
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);
           
           mostrarCantidadFilas();            
        } 
        else if(valor!=null && valor.isEmpty())
        {
            TableRowSorter<TableModel> modeloOrdenVacio = new TableRowSorter<TableModel>(tblLista.getModel());
            tblLista.setRowSorter(modeloOrdenVacio);
        }
    }//GEN-LAST:event_cmbColumnavalorActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    /**
     * Setea el nombre de las columnas y el correspondiente getter de la entidad
     * que los va a llenar, el formato es:
     * [getter][nombre de la columna]
     * @return 
     */
    protected ArrayList<String[]> getColumnas()
    {
        return new ArrayList<String[]>();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JComboBox cmbColumna;
    private javax.swing.JComboBox cmbColumnavalor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblCantResultados;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtFiltro;
    private javax.swing.JLabel txtFiltroColumna;
    private javax.swing.JLabel txtFiltroValor;
    // End of variables declaration//GEN-END:variables

    private void initConfig() {
        // Pongo el nombre a la ventana
        this.setTitle(getNombreVentana());
        // Pinto y seteo el estado de los componentes y botones
        setEstadoInicial();
        // Si hay pintado segun criterio lo hago
        initColorCriteria();
    }
    
    protected String getNombreVentana()
    {
        return generarNombreVentana();
    }
    
    private String generarNombreVentana()
    {
        // Seteo el nombre de la ventana
        String nombre = this.entidad.getSimpleName();
        // WORKARROUND para este asco nombre
        if(nombre.equals("PlanificacionXXX"))
        {
            nombre = "Planificación";
        }
        return ("Listado: "+nombre);        
    }
    
    protected Object[] parseColumNames()
    {
        ArrayList<String> lista = new ArrayList<String>();
        
        for (int i = 0; i < getColumnas().size(); i++) {
            String[] col = (String[])getColumnas().get(i);
            System.out.println("[DEBUG] Agrego a la columnas: " + col[1]);
            lista.add((String)col[1]);
        }
        
        Object[] columns = new Object[lista.size()];
        for (int i = 0; i < lista.size(); i++) 
        {
            String s = lista.get(i);
            columns[i] = s;
        }
        
        return columns;
    }

    private Object[][] parseData(ArrayList<ArrayList<Tupla>> data) {

        int a = 0;
        
        if(data!=null)
            a = data.size();
        
        int b = 0;
        
        if(data.size()>0 && data.get(0)!=null)
            b=data.get(0).size();
        
        System.out.println("[DEBUG] Cantidad de Filas: "+a);    
        System.out.println("[DEBUG] Cantidad de Columbas: "+b);
        
        Object[][] fila = new Object[a][b];
        
        for (int i = 0; i < data.size(); i++) {
            ArrayList<Tupla> arrayList = data.get(i);
            for (int j = 0; j < arrayList.size(); j++) {
                Tupla d = arrayList.get(j);
                fila[i][j] = d;
            }
        }
       
        return fila;
    }

    private void mostrarCantidadFilas() {
        lblCantResultados.setText("Cantidad: "+tblLista.getRowCount());
    }

    protected void activarFiltrosTabla()
    {
           TableRowSorter<TableModel> modeloOrdenado;

           modeloOrdenado = new TableRowSorter<TableModel>(tblLista.getModel());
           tblLista.setRowSorter(modeloOrdenado);
        
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i]));
           }
            
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);
           
           mostrarCantidadFilas();
    }
    
    public void setSeleccionarEnabled(ICallBackGen origen, String nombre)
    {
        this.nombreOrigenCallback = nombre;
        this.origenCallback = origen;
        
        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.setEnabled(true);
        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png")));
    }

    private void setEstadoInicial() {
        btnSeleccionar.setEnabled(true);
        btnSeleccionar.setText("Cerrar");
    }

    /**
     * Comportamiento cuando Abrimos una endidad.
     * (Ver detalles o Editarlos)
     * Por defecto llama al call
     * @param id 
     */
    protected void abrirEntidad(int id) {
        // Veo si hay que seleccionar una fila
        if(id!=-1)
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se definió un Editor por defecto para esta entidad","Error!",JOptionPane.ERROR_MESSAGE);
            //this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se pudo encontrar el ID de la Fila","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comportamiento cuando seleccionamos una entidad.
     * Llama a su callback de origen
     * @param id 
     */
    protected void seleccionarEntidad(int id) {
        if(id!=-1)
        {
            this.origenCallback.actualizar(id,this.nombreOrigenCallback,this.entidad);
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se pudo encontrar el ID de la Fila","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    protected String getColumnaId()
    {
        return "";
    }
    
    private int getIDfromFila(int fila)
    {
        
        fila = tblLista.convertRowIndexToModel(fila);
        
        DefaultTableModel modelo = (DefaultTableModel)tblLista.getModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            int id=((Tupla)(tblLista.getModel().getValueAt(fila, 0))).getId();      
            if(id!=-1)
            {
                return id;
            }
        }
        return -1;
    }

    /**
     * Activa el Coloreado de Celdas segun los datos que esta contenga, el filtro
     * el del tipo:
     * [Nombre de Columna][Dato][Color]
     * @return 
     */
    protected ArrayList<String[]> getColumnColorCriteria()
    {
        return new ArrayList<String[]>();
    }

    private void initColorCriteria() {
        tblLista.setDefaultRenderer(Object.class,new PantallaConsultarGenericaCellRenderer(getColumnColorCriteria(),getColumnas()));
    }
    
    private int getIdColumnFromColumnName(String name)
    {
        DefaultTableModel modelo = (DefaultTableModel)tblLista.getModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            String actualName = modelo.getColumnName(i);
            if(actualName.equals(name))
            {
                return i;
            }
        }        
        return -1;
    }
    
    /**
     * Comportamiento del botón Imprimir
     * @param nombre
     * @param columnas
     * @param datos 
     */
    protected void imprimir(String nombre,String[]columnas, String[][] datos)
    {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("NOMBRE",nombre);
        params.put("DATOS",datos);
        params.put("NOMBRE_COLUMNAS",columnas);
        
        try {
            PantallaConsultarGenericaImprimir ci = new PantallaConsultarGenericaImprimir();
            ci.setNombreReporte(nombre);
            ci.setNombreArchivo("Listado",ReportDesigner.REPORTE_TIPO_LISTADOS);
            ci.makeAndShow(params);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JInternalFrame(),"Se produjo un error al generar el Reporte\nIntentelo nuevamente.","Error!",JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    /**
     * Retorna una condicion WHERE de HSQL para filtrar desde el vamos los datos
     * que serán mostrados en el listado.
     * @return 
     */
    protected String getFiltrosActivos()
    {
        return "";
    }

    protected void mostrarFiltrosColumna(boolean flag)
    {
        txtFiltro.setVisible(flag);
        txtFiltroColumna.setVisible(flag);
        txtFiltroValor.setVisible(flag);
        cmbColumna.setVisible(flag);
        cmbColumnavalor.setVisible(flag);
    }
    
    /**
     * Activa el filtrado por columna / valor
     * @return 
     */
    protected String[] getColumnasFiltro()
    {
        return new String[0];
    }

    protected void cargarFiltrosColumna() {
        // COLUMNAS !!
        // Borro todo lo que tenga esta combo
        cmbColumna.removeAllItems();
        // Agrego el elemento vacio
        // cmbColumna.addItem("");
        // Lo lleno con los filtros activos
        if(getColumnasFiltro()!=null && getColumnasFiltro().length>0)
        {
            for (int i = 0; i < getColumnasFiltro().length; i++) {
                String item = getColumnasFiltro()[i];
                cmbColumna.addItem(item);
            }
        }  
        cmbColumnavalor.setMinimumSize(new Dimension(150,18));
    }
    
    protected void cargarFiltrosValorColumna(String columna){
        
        // VALOR DE COLUMNAS !!
        // Borro todo lo que tenga esta combo
        cmbColumnavalor.removeAllItems();
        // Agrego el elemento vacio
        cmbColumnavalor.addItem("");        
        // Lo lleno con los valores actuales
        if(columna!=null && !columna.isEmpty())
        {
            // Armo un hashmap con todos los valores posibles
            HashMap<String,String> valores = new HashMap<String, String>();
            int orden = getIdColumnFromColumnName(columna);
            if(orden!=-1)
            {
               // Recorro todas las filas de la columna orden
                DefaultTableModel modelo = (DefaultTableModel)tblLista.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String valor =((Tupla)(tblLista.getModel().getValueAt(i,orden))).getNombre();      
                    if(valor!=null && !valor.isEmpty())
                    {
                        valores.put(valor, valor);
                    }
                }                
            }
            // Recorro todos los valores y los cargo al combo
            Iterator it = valores.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry)it.next();
                cmbColumnavalor.addItem(e.getValue());
            }
        }   
        cmbColumnavalor.setMinimumSize(new Dimension(150,18));
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
    
}
