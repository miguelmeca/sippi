/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.GestorEditarTareaDetalles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.DetalleTareaPlanificacion;
import modelo.Especialidad;
import modelo.TareaPlanificacion;
import modelo.TipoEspecialidad;
import util.NTupla;
import util.Tupla;
import vista.interfaces.ICallBack_v3;
import vista.util.Validaciones;

/**
 *
 * @author Fran
 */
public class EditarTareaDetallesABM extends javax.swing.JInternalFrame {

    private GestorEditarTareaDetalles gestor;
    private ICallBack_v3 pantallaPrincipal;
    private boolean modificacion; //modificacion = false entonces es detalle nuevo
    DetalleTareaPlanificacion detalleSuperiorSeleccionado;
    int disponiblesPersonasEnDetalleSuperior;
    int disponiblesPersonasAsignadasEnDetalleSuperior;
    double disponiblesHsNormalesEnDetalleSuperior;
    double disponiblesHs100EnDetalleSuperior;
    double disponiblesHs50EnDetalleSuperior;
    
    double hsNormalesEnTareaCotizadaOriginal;
    double hs100EnTareaCotizadaOriginal;
    double hs50EnTareaCotizadaOriginal;
    double costoEnTareaCotizadaOriginal;
    
    double hsNormalesEnTareaCotizadaModif;
    double hs100EnTareaCotizadaModif;
    double hs50EnTareaCotizadaModif;
    double costoEnTareaCotizadaModif;
    
    int cantPersonas;
    double cantHsNormales;
    double cantHs100;
    double cantHs50;
    Especialidad especialidad;
    double costoDetalle;
    
    public EditarTareaDetallesABM(ICallBack_v3 pantalla, GestorEditarTareaDetalles gestor, boolean modificacion) {
        initComponents();        
        this.gestor = gestor;
        gestor.setPantallaABM(this);
        inicializarVentana();
        pantallaPrincipal=pantalla;
        this.modificacion=modificacion;
        if(gestor.getCopiaDetallePadre()!=null)
        {
            costoDetalle=gestor.getCopiaDetallePadre().getCostoXHoraNormal();
            especialidad=gestor.getCopiaDetallePadre().getEspecialidad();
        }
    }

    
    private void inicializarVentana()
    {
      tblDetallesTareaSuperior.getTableHeader().setPreferredSize(new Dimension(tblDetallesTareaSuperior.getColumnModel().getTotalColumnWidth(), 34));
      if(!modificacion)
      {
          cargarCboTareasSuperiores(); 
      }
      else
      {
          habilitarDespuesDeClickEnTabla(true);
      }
      cambiarTamCabeceraTablas();
      
      SpinnerModel modelPersonas =
        new SpinnerNumberModel(1, //initial value
                               0, //min
                               500, //max
                               1);//step
      spnPersonas.setModel(modelPersonas);
      
      SpinnerModel modelHoras =
        new SpinnerNumberModel(0.0, //initial value
                               0.0, //min
                               999, //max
                               0.05);//step
      spnPersonas.setModel(modelPersonas);
      spnHsNormales.setModel(modelHoras);
      spnHs50.setModel(modelHoras);
      spnHs100.setModel(modelHoras);
      
      setearEscuchasSpinners();
      
      
      vaciarDatosDetalle();      
      setearValoresTareaCotizadaOriginal();
      setearValoresTareaCotizadaActual();
    }
    private void cambiarTamCabeceraTablas()
    {
        Font fuente = new Font("Verdana", Font.PLAIN, 9);
        JTableHeader th1;
        th1 = tblDetallesTareaSuperior.getTableHeader();
        th1.setFont(fuente); 
        
              
    }
    
    public void cargarCboTareasSuperiores()
    {
        ArrayList<NTupla> listaTareasSuperiores = gestor.mostrarTareasSuperiores();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaTareasSuperiores!=null && !listaTareasSuperiores.isEmpty())
        for (NTupla ts : listaTareasSuperiores)
        {
            model.addElement(ts);
        }
        cmbTareaSuperior.setModel(model);        
        
        if(!listaTareasSuperiores.isEmpty())        
        {
            NTupla t0 = new NTupla(-1);
            if(listaTareasSuperiores.get(0).getId()!=-1)
            t0.setNombre("Seleccione una tarea..."); 
            cmbTareaSuperior.insertItemAt(t0, 0);
        }
        
        
        cmbTareaSuperior.setSelectedIndex(0); 
        
        vaciarTablaTareaSuperior();
    }
    
    public void cargarCboRangos(TipoEspecialidad te)
    {
        ArrayList<NTupla> listaRangos = gestor.mostrarRangos(te);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaRangos!=null && !listaRangos.isEmpty())
        for (NTupla rango : listaRangos)
        {
            model.addElement(rango);
        }
        cboRango.setModel(model);
        NTupla t0 = new NTupla(-1);
        t0.setNombre("Seleccione un rango..."); 
        cboRango.insertItemAt(t0, 0);
        cboRango.setSelectedIndex(0);   
    }
    public void cargarCboTipoEspecialidad()
    {
        ArrayList<NTupla> listaTipoEspecialidad = gestor.mostrarTiposEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaTipoEspecialidad!=null && !listaTipoEspecialidad.isEmpty())
        for (NTupla te : listaTipoEspecialidad)
        {
            model.addElement(te);
        }
        cboTipoEspecialidad.setModel(model);
        NTupla t0 = new NTupla(-1);
        t0.setNombre("Seleccione una especialidad...");
        cboTipoEspecialidad.insertItemAt(t0, 0);
        cboTipoEspecialidad.setSelectedIndex(0);  
        
        cboRango.setModel(new DefaultComboBoxModel());        
    }
    
    private void setearValoresTareaCotizadaOriginal()
    {
        
        hsNormalesEnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasNormales();
        hs50EnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasAl50();
        hs100EnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasAl100();        
        costoEnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().calcularSubtotal();
        /*
        hsNormalesEnTareaCotizadaOriginal=gestor.getCopiaTareaCotizadaOriginal().obtenerTotalDeHorasNormales();
        hs50EnTareaCotizadaOriginal=gestor.getCopiaTareaCotizadaOriginal().obtenerTotalDeHorasAl50();
        hs100EnTareaCotizadaOriginal=gestor.getCopiaTareaCotizadaOriginal().obtenerTotalDeHorasAl100();        
        costoEnTareaCotizadaOriginal=gestor.getCopiaTareaCotizadaOriginal().calcularSubtotal();
        */
    
        lblHsNormalesTCotizada.setText(String.valueOf(hsNormalesEnTareaCotizadaOriginal));
        lblHs50TCotizada.setText(String.valueOf(hs50EnTareaCotizadaOriginal));
        lblHs100TCotizada.setText(String.valueOf(hs100EnTareaCotizadaOriginal));
        lblCostoTCotizada.setText(String.valueOf(costoEnTareaCotizadaOriginal));
    }
    
    private void setearValoresTareaCotizadaActual()
    {
        hsNormalesEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasNormales();
        hs50EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl50();
        hs100EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl100();        
        costoEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().calcularSubtotal();
    
    
        setearValoresTareaCotizadaActualEnPantalla();
    }
    
    private void setearValoresTareaCotizadaActualEnPantalla()
    {
        lblHsNormalesTCotizadaActual.setText(String.valueOf(hsNormalesEnTareaCotizadaModif));
        lblHs50TCotizadaActual.setText(String.valueOf(hs50EnTareaCotizadaModif));
        lblHs100TCotizadaActual.setText(String.valueOf(hs100EnTareaCotizadaModif));
        lblCostoTCotizadaActual.setText(String.valueOf(costoEnTareaCotizadaModif));
        
        
        
        if(hsNormalesEnTareaCotizadaOriginal<hsNormalesEnTareaCotizadaModif)
        {lblHsNormalesTCotizadaActual.setForeground(Color.red); } 
        else
        {lblHsNormalesTCotizadaActual.setForeground(Color.black);}
        
        if(hs50EnTareaCotizadaOriginal<hs50EnTareaCotizadaModif)
        {lblHs50TCotizadaActual.setForeground(Color.red); } 
        else
        {lblHs50TCotizadaActual.setForeground(Color.black);}
        
        if(hs100EnTareaCotizadaOriginal<hs100EnTareaCotizadaModif)
        {lblHs100TCotizadaActual.setForeground(Color.red); } 
        else
        {lblHs100TCotizadaActual.setForeground(Color.black);}
        
        if(costoEnTareaCotizadaOriginal<costoEnTareaCotizadaModif)
        {lblCostoTCotizadaActual.setForeground(Color.red); } 
        else
        {lblCostoTCotizadaActual.setForeground(Color.black);}
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        tabAsignacion = new javax.swing.JTabbedPane();
        PanelEsfuerzo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbTareaSuperior = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetallesTareaSuperior = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        lblPersonasDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHsNormalesDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHs50DetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHs100DetalleTPadreDisponibles = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        spnPersonas = new javax.swing.JSpinner();
        spnHsNormales = new javax.swing.JSpinner();
        spnHs50 = new javax.swing.JSpinner();
        spnHs100 = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cboTipoEspecialidad = new javax.swing.JComboBox();
        cboRango = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblHs100TCotizada = new javax.swing.JLabel();
        lblHs100TCotizadaActual = new javax.swing.JLabel();
        lblCostoTCotizadaActual = new javax.swing.JLabel();
        lblCostoTCotizada = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblHs50TCotizada = new javax.swing.JLabel();
        lblHs50TCotizadaActual = new javax.swing.JLabel();
        lblHsNormalesTCotizada = new javax.swing.JLabel();
        lblHsNormalesTCotizadaActual = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelAsignaciones = new javax.swing.JPanel();

        setOpaque(true);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setEnabled(false);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Tarea de nivel superior de la que usar horas:");

        cmbTareaSuperior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTareaSuperiorActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas disponibles en la tarea de nivel superior. Seleccione un detalle:"));
        jPanel1.setEnabled(false);

        tblDetallesTareaSuperior.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        tblDetallesTareaSuperior.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Especialidad", "Rango", "<html>Personas<br> Asignadas</html>", "Hs Normales", "Hs 50%", "Hs 100%"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetallesTareaSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetallesTareaSuperiorMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDetallesTareaSuperiorMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblDetallesTareaSuperiorMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetallesTareaSuperior);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel17.setText("Disponibles");

        lblPersonasDetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPersonasDetalleTPadreDisponibles.setText("---");

        lblHsNormalesDetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHsNormalesDetalleTPadreDisponibles.setText("---");

        lblHs50DetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs50DetalleTPadreDisponibles.setText("---");

        lblHs100DetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs100DetalleTPadreDisponibles.setText("---");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Hs Normales");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Personas");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Hs al 100%");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Hs al 50%");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblPersonasDetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesDetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50DetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs100DetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)))
                            .addComponent(jLabel7))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPersonasDetalleTPadreDisponibles)
                            .addComponent(lblHsNormalesDetalleTPadreDisponibles)
                            .addComponent(lblHs50DetalleTPadreDisponibles)
                            .addComponent(lblHs100DetalleTPadreDisponibles))))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas a usar de la tarea superior"));
        jPanel2.setEnabled(false);

        jLabel16.setText("Especialidad:");

        jLabel18.setText("Rango:");

        jLabel20.setText("Hs 100%");

        jLabel21.setText("Hs 50%");

        jLabel22.setText("Hs Normales");

        jLabel23.setText("Personas");

        cboTipoEspecialidad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTipoEspecialidadItemStateChanged(evt);
            }
        });
        cboTipoEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoEspecialidadActionPerformed(evt);
            }
        });

        cboRango.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRangoItemStateChanged(evt);
            }
        });
        cboRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRangoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(spnHs50, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnHs100))
                    .addComponent(cboRango, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel18)
                        .addComponent(cboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHs50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHs100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Implicaciones en la tarea cotizada"));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel15.setText("Actuales");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel14.setText("<html>Originalmente<br>cotizadas</html>");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("  Costo  ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hs al 50%");

        lblHs100TCotizada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs100TCotizada.setText("---");

        lblHs100TCotizadaActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs100TCotizadaActual.setText("---");

        lblCostoTCotizadaActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCostoTCotizadaActual.setText("---");

        lblCostoTCotizada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCostoTCotizada.setText("---");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hs al 100%");

        lblHs50TCotizada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs50TCotizada.setText("---");

        lblHs50TCotizadaActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs50TCotizadaActual.setText("---");

        lblHsNormalesTCotizada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHsNormalesTCotizada.setText("---");

        lblHsNormalesTCotizadaActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHsNormalesTCotizadaActual.setText("---");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hs Normales");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesTCotizada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesTCotizadaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(65, 65, 65)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50TCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50TCotizadaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(69, 69, 69)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHs100TCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                        .addComponent(lblHs100TCotizadaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3))
                .addGap(60, 60, 60)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCostoTCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCostoTCotizadaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHsNormalesTCotizada)
                    .addComponent(lblHs50TCotizada)
                    .addComponent(lblHs100TCotizada)
                    .addComponent(lblCostoTCotizada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHs50TCotizadaActual)
                    .addComponent(lblHsNormalesTCotizadaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHs100TCotizadaActual)
                    .addComponent(lblCostoTCotizadaActual)
                    .addComponent(jLabel15)))
        );

        javax.swing.GroupLayout PanelEsfuerzoLayout = new javax.swing.GroupLayout(PanelEsfuerzo);
        PanelEsfuerzo.setLayout(PanelEsfuerzoLayout);
        PanelEsfuerzoLayout.setHorizontalGroup(
            PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbTareaSuperior, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelEsfuerzoLayout.setVerticalGroup(
            PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTareaSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        tabAsignacion.addTab("Esfuerzo", PanelEsfuerzo);

        javax.swing.GroupLayout PanelAsignacionesLayout = new javax.swing.GroupLayout(PanelAsignaciones);
        PanelAsignaciones.setLayout(PanelAsignacionesLayout);
        PanelAsignacionesLayout.setHorizontalGroup(
            PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );
        PanelAsignacionesLayout.setVerticalGroup(
            PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        tabAsignacion.addTab("Asignaciones", PanelAsignaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(22, 22, 22))
                    .addComponent(tabAsignacion)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTareaSuperiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTareaSuperiorActionPerformed
        
        vaciarTablaTareaSuperior();
        if (((NTupla) cmbTareaSuperior.getSelectedItem()).getId() != -1) 
        {
            if (((NTupla) cmbTareaSuperior.getItemAt(0)).getId() == -1) {
                cmbTareaSuperior.removeItemAt(0);
            }
            NTupla tarea = (NTupla) cmbTareaSuperior.getModel().getSelectedItem();
            llenarTablaTareaSuperior((TareaPlanificacion) tarea.getData());
            
        }
    }//GEN-LAST:event_cmbTareaSuperiorActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        /*gestor.guardarCambios();
        this.actualizarPantallas();

        // Si tiene callback lo activo
        if (this.tieneCallback != null) {
            this.tieneCallback.actualizar(gestor.getTareaActual().hashCode(), PlanificacionSubTareas.CALLBACK_NUEVASUBTAREA, TareaPlanificacion.class);
        }

        this.setVisible(false);*/
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblDetallesTareaSuperiorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesTareaSuperiorMouseClicked
        clicEnTablaDetallesTareaSuperior(evt);
    }//GEN-LAST:event_tblDetallesTareaSuperiorMouseClicked

    private void tblDetallesTareaSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesTareaSuperiorMousePressed
        clicEnTablaDetallesTareaSuperior(evt);
    }//GEN-LAST:event_tblDetallesTareaSuperiorMousePressed

    private void tblDetallesTareaSuperiorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesTareaSuperiorMouseReleased
        clicEnTablaDetallesTareaSuperior(evt);
    }//GEN-LAST:event_tblDetallesTareaSuperiorMouseReleased

    private void cboTipoEspecialidadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipoEspecialidadItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoEspecialidadItemStateChanged

    private void cboTipoEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoEspecialidadActionPerformed
        if (((NTupla) cboTipoEspecialidad.getSelectedItem()).getId() != -1) {
            if (((NTupla) cboTipoEspecialidad.getItemAt(0)).getId() < 0) {
               cboTipoEspecialidad.removeItemAt(0);
            }
            if (((NTupla) cboTipoEspecialidad.getItemAt(0)).getId() > 0) {
               cargarCboRangos((TipoEspecialidad)((NTupla) cboTipoEspecialidad.getSelectedItem()).getData());
            }
        }
        
    }//GEN-LAST:event_cboTipoEspecialidadActionPerformed

    private void cboRangoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRangoItemStateChanged

   }//GEN-LAST:event_cboRangoItemStateChanged

    private void cboRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRangoActionPerformed
        if (((NTupla) cboRango.getSelectedItem()).getId() != -1) {
            if (((NTupla) cboRango.getItemAt(0)).getId() < 0) {
                cboRango.removeItemAt(0);
            }
        }
    }//GEN-LAST:event_cboRangoActionPerformed
           
    private void llenarTablaTareaSuperior(TareaPlanificacion tareaSuperior)
    {       
       habilitarDespuesDeClickEnComboTareas(true);
       for (DetalleTareaPlanificacion detalle:tareaSuperior.getDetalles()) 
       {
            agregarDetalleTareaATabla(detalle);
       }       
        
        habilitarDespuesDeClickEnTabla(false);
    }
    
    private void vaciarTablaTareaSuperior()
    {
         habilitarDespuesDeClickEnComboTareas(false);
        ((DefaultTableModel)tblDetallesTareaSuperior.getModel()).setRowCount(0);
         habilitarDespuesDeClickEnTabla(false);
    }
    private void habilitarDespuesDeClickEnComboTareas(boolean habilitar)
    {
        tblDetallesTareaSuperior.setEnabled(habilitar);
        if(!habilitar)
        {
            /*lblHsNormalesTCotizada.setText("---");
            lblHs50TCotizada.setText("---");
            lblHs100TCotizada.setText("---");*/
                                    
            habilitarDespuesDeClickEnTabla(false); 
        }
        
    } 
    private void clicEnTablaDetallesTareaSuperior(java.awt.event.MouseEvent evt)
    {
             
       int selectedRow=tblDetallesTareaSuperior.getSelectedRow();
   
       boolean clickEnFila=false;
       if(selectedRow!=-1)
       {
            clickEnFila=true;
            
            detalleSuperiorSeleccionado=(DetalleTareaPlanificacion)((NTupla)tblDetallesTareaSuperior.getModel().getValueAt(selectedRow, 0)).getData();
            costoDetalle=detalleSuperiorSeleccionado.getCostoXHoraNormal();
            especialidad=detalleSuperiorSeleccionado.getEspecialidad();
            gestor.crearNuevoDetalleAcutal(detalleSuperiorSeleccionado);
            SpinnerModel modelPersonas =
            new SpinnerNumberModel(1, //initial value
                                0, //min
                                detalleSuperiorSeleccionado.getCantidadPersonas(), //max
                                1);//step
            spnPersonas.setModel(modelPersonas);
            spnPersonas.setValue(detalleSuperiorSeleccionado.getCantidadPersonas());
            spnHsNormales.setValue(0.0);
            spnHs50.setValue(0.0);
            spnHs100.setValue(0.0);
            disponiblesPersonasEnDetalleSuperior=detalleSuperiorSeleccionado.getCantidadPersonas();
            disponiblesPersonasAsignadasEnDetalleSuperior=detalleSuperiorSeleccionado.getCantidadPersonasAsignadas();
            disponiblesHsNormalesEnDetalleSuperior=detalleSuperiorSeleccionado.getCantHorasNormales();
            disponiblesHs100EnDetalleSuperior=detalleSuperiorSeleccionado.getCantHorasAl50();
            disponiblesHs50EnDetalleSuperior=detalleSuperiorSeleccionado.getCantHorasAl100();
            
            setearDatosDetalleSuperiorEnPantalla();
            //TODO:    
           /* lblPersonasDetalleTPadreCotizadas.setText("---");
            lblHsNormalesDetalleTPadreCotizadas.setText("---");
            lblHs50DetalleTPadreCotizadas.setText("---");
            lblHs100DetalleTPadreCotizadas.setText("---");
            
            lblPersonasDetalleTPadreTotales.setText("---");
            lblHsNormalesDetalleTPadreTotales.setText("---");
            lblHs50DetalleTPadreTotales.setText("---");
            lblHs100DetalleTPadreTotales.setText("---");
            
            lblPersonasDetalleTPadreDisponibles.setText("---");
            lblHsNormalesDetalleTPadreDisponibles.setText("---");
            lblHs50DetalleTPadreDisponibles.setText("---");
            lblHs100DetalleTPadreDisponibles.setText("---"); */   
                
                
       }
            
       habilitarDespuesDeClickEnTabla(clickEnFila);
       evt.consume();
    }
    
    private void setearDatosDetalleSuperiorEnPantalla()
    {
        
        lblPersonasDetalleTPadreDisponibles.setText(disponiblesPersonasEnDetalleSuperior+"/"+disponiblesPersonasAsignadasEnDetalleSuperior);
        lblHsNormalesDetalleTPadreDisponibles.setText(String.valueOf(disponiblesHsNormalesEnDetalleSuperior));
        lblHs50DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs50EnDetalleSuperior));
        lblHs100DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs100EnDetalleSuperior));
        
        if(disponiblesPersonasEnDetalleSuperior<0)
        {lblPersonasDetalleTPadreDisponibles.setForeground(Color.red); } 
        else
        {lblPersonasDetalleTPadreDisponibles.setForeground(Color.black);}
        
        if(disponiblesHsNormalesEnDetalleSuperior<0)
        {lblHsNormalesDetalleTPadreDisponibles.setForeground(Color.red); } 
        else
        {lblHsNormalesDetalleTPadreDisponibles.setForeground(Color.black);}
        
        if(disponiblesHs50EnDetalleSuperior<0)
        {lblHs50DetalleTPadreDisponibles.setForeground(Color.red); } 
        else
        {lblHs50DetalleTPadreDisponibles.setForeground(Color.black);}
        
        if(disponiblesHs100EnDetalleSuperior<0)
        {lblHs100DetalleTPadreDisponibles.setForeground(Color.red); } 
        else
        {lblHs100DetalleTPadreDisponibles.setForeground(Color.black);}       
        
    }
    
    private void vaciarDatosDetalle()
    {
        disponiblesPersonasEnDetalleSuperior=1;
        disponiblesPersonasAsignadasEnDetalleSuperior=0;
        disponiblesHsNormalesEnDetalleSuperior=0;
        disponiblesHs100EnDetalleSuperior=0;
        disponiblesHs50EnDetalleSuperior=0;
        detalleSuperiorSeleccionado=null;
        
        limpiarDatosDetalleEnPantalla();
        
    }
    
    private void limpiarDatosDetalleEnPantalla()
    {
        lblPersonasDetalleTPadreDisponibles.setText("---");
        lblHsNormalesDetalleTPadreDisponibles.setText("---");
        lblHs50DetalleTPadreDisponibles.setText("---");
        lblHs100DetalleTPadreDisponibles.setText("---");
        
        cargarCboTipoEspecialidad();
        spnPersonas.setValue(1);
        spnHsNormales.setValue(0.0);
        spnHs50.setValue(0.0);
        spnHs100.setValue(0.0);
    }
    
    private void habilitarDespuesDeClickEnTabla(boolean habilitar)
    {
        vaciarDatosDetalle();
        spnPersonas.setEnabled(habilitar);
        spnHsNormales.setEnabled(habilitar);
        spnHs50.setEnabled(habilitar);
        spnHs100.setEnabled(habilitar);
        cboRango.setEnabled(habilitar);
        cboTipoEspecialidad.setEnabled(habilitar);
        //if(!habilitar)
        {
           // cargarCboTipoEspecialidad();
        }
        
    }
    
    private void agregarDetalleTareaATabla(DetalleTareaPlanificacion detalleTarea) //throws Exception
    {
       Object[] datos=new Object[6];   
       NTupla nombreTipoEspecialidad=new NTupla();
       nombreTipoEspecialidad.setNombre(detalleTarea.getEspecialidad().getTipo().getNombre());
       nombreTipoEspecialidad.setData(detalleTarea);
       datos[0]=nombreTipoEspecialidad; 
       Tupla detalleYNombreRango=new Tupla();
       detalleYNombreRango.setNombre(detalleTarea.getEspecialidad().getRango().getNombre());       
       datos[1]=detalleYNombreRango;      
       datos[2]=detalleTarea.getCantidadPersonasAsignadas()+"/"+detalleTarea.getCantidadPersonas();
       datos[3]=detalleTarea.getCantHorasNormales();
       datos[4]=detalleTarea.getCantHorasAl50();
       datos[5]=detalleTarea.getCantHorasAl100();
       
       DefaultTableModel modelo = (DefaultTableModel) tblDetallesTareaSuperior.getModel();
       
           modelo.addRow(datos);
       
    }
    
    private void setearEscuchasSpinners()
    {
        ChangeListener listenerSpiners = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(e.getSource().equals(spnPersonas))
                {
                    cantPersonas=(Integer)spnPersonas.getModel().getValue();
                }
                if(e.getSource().equals(spnHsNormales))
                {  
                    cantHsNormales=(Double)spnHsNormales.getModel().getValue();
                }
                if(e.getSource().equals(spnHs50))
                {                    
                    cantHs50=(Double)spnHs50.getModel().getValue();
                }
                if(e.getSource().equals(spnHs100))
                {                    
                    cantHs100= (Double)spnHs100.getModel().getValue();
                }
                
                
                
            }
       };

       spnPersonas.addChangeListener(listenerSpiners);
       spnHs50.addChangeListener(listenerSpiners);
       spnHs100.addChangeListener(listenerSpiners);
    }
    
    public void mandarCambios()
    {
        gestor.tomarCambios(cantPersonas, cantHsNormales,  cantHs50,  cantHs100,  costoDetalle, especialidad);
    }
    
    public void actualizar()
    {
        disponiblesPersonasEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantidadPersonas();
        disponiblesPersonasAsignadasEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantidadPersonasAsignadas();
        disponiblesHsNormalesEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasNormales();
        disponiblesHs100EnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasAl50();
        disponiblesHs50EnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasAl100();
       
            
        hsNormalesEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasNormales();
        hs50EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl50();
        hs100EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl100();
        costoEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().calcularSubtotal();
        
        
        setearValoresTareaCotizadaActualEnPantalla();
        setearDatosDetalleSuperiorEnPantalla();
    }
    
    
    
    public void MostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAsignaciones;
    private javax.swing.JPanel PanelEsfuerzo;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cboRango;
    private javax.swing.JComboBox cboTipoEspecialidad;
    private javax.swing.JComboBox cmbTareaSuperior;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCostoTCotizada;
    private javax.swing.JLabel lblCostoTCotizadaActual;
    private javax.swing.JLabel lblHs100DetalleTPadreDisponibles;
    private javax.swing.JLabel lblHs100TCotizada;
    private javax.swing.JLabel lblHs100TCotizadaActual;
    private javax.swing.JLabel lblHs50DetalleTPadreDisponibles;
    private javax.swing.JLabel lblHs50TCotizada;
    private javax.swing.JLabel lblHs50TCotizadaActual;
    private javax.swing.JLabel lblHsNormalesDetalleTPadreDisponibles;
    private javax.swing.JLabel lblHsNormalesTCotizada;
    private javax.swing.JLabel lblHsNormalesTCotizadaActual;
    private javax.swing.JLabel lblPersonasDetalleTPadreDisponibles;
    private javax.swing.JSpinner spnHs100;
    private javax.swing.JSpinner spnHs50;
    private javax.swing.JSpinner spnHsNormales;
    private javax.swing.JSpinner spnPersonas;
    private javax.swing.JTabbedPane tabAsignacion;
    private javax.swing.JTable tblDetallesTareaSuperior;
    // End of variables declaration//GEN-END:variables
}
