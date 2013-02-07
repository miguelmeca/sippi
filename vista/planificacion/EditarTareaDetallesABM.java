/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.GestorEditarTareaDetalles;
import controlador.rrhh.GestorConsultarEmpleado;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import modelo.*;
import util.NTupla;
import vista.interfaces.ICallBack_v3;
import vista.rrhh.EmpleadoActivoRowFilter;
import vista.rrhh.ExplorarEmpleados_RenderCeldas;
import vista.rrhh.ExplorarEmpleados_celdaDatos;
import vista.rrhh.ExplorarEmpleados_celdaFoto;
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
    String especialidadDetalleTPadreDisponibles;
    double costoEnDetalleSuperior;
    boolean tareaHijaDePlanificacion=false; 
    
    int disponiblesPersonasEnDetalleSuperior_nva;
    int disponiblesPersonasAsignadasEnDetalleSuperior_nva;
    double disponiblesHsNormalesEnDetalleSuperior_nva;
    double disponiblesHs100EnDetalleSuperior_nva;
    double disponiblesHs50EnDetalleSuperior_nva;
    String especialidadDetalleTPadreDisponibles_nva;
    double costoEnDetalleSuperior_nva;
    
    double hsNormalesEnTareaCotizadaOriginal;
    double hs100EnTareaCotizadaOriginal;
    double hs50EnTareaCotizadaOriginal;
    double costoEnTareaCotizadaOriginal;
    
    double hsNormalesEnTareaCotizadaModif;
    double hs100EnTareaCotizadaModif;
    double hs50EnTareaCotizadaModif;
    double costoEnTareaCotizadaModif;
    
    int cantPersonasAsignadas;
    int cantPersonas;
    double cantHsNormales;
    double cantHs100;
    double cantHs50;
    Especialidad especialidadPadre;
    int idEspecialidad;
    double costoDetalle;
    
    boolean enProceso=false;
    private boolean filtroBuscarActivado;
    private GestorConsultarEmpleado gestorConsultarEmpleado;
    private List<Empleado> listaEmpleadosDisponibles;
    private List<Empleado> listaEmpleadosAsignados;
    private DefaultTableModel modeloTablaEmpleadosDisponibles;
    private DefaultTableModel modeloTablaEmpleadosAsignados;
    private boolean huboFocoEnAsignaciones=false;
    
    public EditarTareaDetallesABM(ICallBack_v3 pantalla, GestorEditarTareaDetalles gestor, boolean modificacion, boolean tareaHijaDePlanificacion) {
        gestorConsultarEmpleado = new GestorConsultarEmpleado();
        this.tareaHijaDePlanificacion=tareaHijaDePlanificacion;
        this.setTitle("Detalle de Recursos Humanos - Tarea "+gestor.getTareaActual().getNombre());        
        initComponents();        
        this.gestor = gestor;
        gestor.setPantallaABM(this);
        detalleSuperiorSeleccionado=null;
        detalleSuperiorSeleccionado=gestor.getDetallePadre();
        this.modificacion=modificacion;
        inicializarVentana();
        pantallaPrincipal=pantalla;
        
        if(gestor.getCopiaDetallePadre()!=null)
        {
            costoDetalle=gestor.getCopiaDetallePadre().getCostoXHoraNormal();
            especialidadPadre=gestor.getCopiaDetallePadre().getEspecialidad();
        }
         huboFocoEnAsignaciones=false;
        
        
        
    }
    
    
    /*public void tomarDatosDetalleModificado(TareaPlanificacion tareaDeDetallePadre, DetalleTareaPlanificacion detallePadre, DetalleTareaPlanificacion detalleAcutal)
    {
        tomarDatosDetalleModificado(detalleAcutal);
        detalleSuperiorSeleccionado=detallePadre;
        clicEnTablaDetallesTareaSuperior(false);
    }*/

    
    private void inicializarVentana()
    {
        enProceso=true;
     filtroBuscarActivado=false;
     rbFiltroTodos.setSelected(true);
     rbFiltroActivos.setSelected(false);
     ((TitledBorder)panelTareaCotizada.getBorder()).setTitle("Implicaciones en la tarea cotizada '"+gestor.getTareaConCotizacion().getNombre()+"'" );
     
        ////////////////////////////////////
        tblEmpleadosDisponibles.setDefaultRenderer(Object.class,new ExplorarEmpleados_RenderCeldas());
        tblEmpleadosDisponibles.setRowHeight(72);
        tblEmpleadosDisponibles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEmpleadosDisponibles.getColumnModel().getColumn(0).setPreferredWidth(72);
        tblEmpleadosDisponibles.getColumnModel().getColumn(1).setPreferredWidth(465);
        
        tblEmpleadosAsignados.setDefaultRenderer(Object.class,new ExplorarEmpleados_RenderCeldas());
        tblEmpleadosAsignados.setRowHeight(72);
        tblEmpleadosAsignados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEmpleadosAsignados.getColumnModel().getColumn(0).setPreferredWidth(72);
        tblEmpleadosAsignados.getColumnModel().getColumn(1).setPreferredWidth(465);
        
      habilitarDespuesDeClickEnTabla(true);
            
      SpinnerModel modelPersonas =
        new SpinnerNumberModel(1, //initial value
                               1, //min
                               500, //max
                               1);//step
      spnPersonas.setModel(modelPersonas);
      
      SpinnerModel modelHorasNormales =
        new SpinnerNumberModel(0.0, //initial value
                               0.0, //min
                               999, //max
                               0.25);//step
      SpinnerModel modelHoras50 = new SpinnerNumberModel(0.0, 0.0, 999, 0.25);
      SpinnerModel modelHoras100 = new SpinnerNumberModel(0.0, 0.0, 999, 0.25);
      spnPersonas.setModel(modelPersonas);
      spnHsNormales.setModel(modelHorasNormales);
      spnHs50.setModel(modelHoras50);
      spnHs100.setModel(modelHoras100);
      
      setearEscuchasSpinners();
      
      cargarCboTipoEspecialidad();
      vaciarDatosDetalle();      
      setearValoresTareaCotizadaOriginal();
      setearValoresTareaCotizadaActual();
      setearDatosDetalleActual();
      enProceso=false;
      cargarEmpleadosDisponibles();
      cargarEmpleadosAsignados();
      limpiarSobranteTablaEmpleadosDisponibles();
      
      seterarRadioButtonsHorasNuevas();
      this.setTitle("Detalle de Recursos Humanos en tarea '"+gestor.getTareaActual().getNombre()+"'");
      /*
      private boolean modificacion=false;
    private boolean tareaHijaDePlanificacion=false;
    private boolean detalleNoCotizado=false;
    private boolean devolverHorasATareaSuperior=true;*/
    }
    
    
    private void seterarRadioButtonsHorasNuevas()
    {
      if(!modificacion)
      {  
        rbDevolverHoras.setEnabled(false);
        rbEliminarHoras.setEnabled(false); 
        if(gestor.isDetalleNoCotizado() || gestor.isTareaHijaDePlanificacion())
        {
            rbDevolverHoras.setSelected(false);
            rbEliminarHoras.setSelected(true);

        }
        else
        {
            rbDevolverHoras.setSelected(true);
            rbEliminarHoras.setSelected(false);
        }
      }
      else
      {
         rbDevolverHoras.setEnabled(true);
         rbEliminarHoras.setEnabled(true);  
      }
      
      setearDevolverHoras();
      
     
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
        if(gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal()!=null)
        {
            hsNormalesEnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasNormales();
            hs50EnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasAl50();
            hs100EnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().obtenerTotalDeHorasAl100();        
            costoEnTareaCotizadaOriginal=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().getOriginal().calcularSubtotal();
        }
        else
        {
           hsNormalesEnTareaCotizadaOriginal=0.0;
           hs50EnTareaCotizadaOriginal=0.0;
           hs100EnTareaCotizadaOriginal=0.0;       
           costoEnTareaCotizadaOriginal=0.0;
        }
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
        seterarRadioButtonsHorasNuevas();
    }
    
   
    
    private void setearValoresTareaCotizadaActual()
    {   
        hsNormalesEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasNormales();
        hs50EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl50();
        hs100EnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().obtenerTotalDeHorasAl100();        
        costoEnTareaCotizadaModif=gestor.getCopiaTareaConConCotizacion().getTareaCotizada().calcularSubtotal();
        
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
        //seterarRadioButtonsHorasNuevas();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        tabAsignacion = new javax.swing.JTabbedPane();
        PanelEsfuerzo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblPersonasDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHsNormalesDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHs50DetalleTPadreDisponibles = new javax.swing.JLabel();
        lblHs100DetalleTPadreDisponibles = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblPersonasDetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
        lblHsNormalesDetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
        lblHs50DetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
        lblHs100DetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblEspecialidadDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblEspecialidadDetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblCostoDetalleTPadreDisponibles = new javax.swing.JLabel();
        lblCostoDetalleTPadreDisponibles_Nvo = new javax.swing.JLabel();
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
        txtCosto = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        btnSetearCostoRango = new javax.swing.JButton();
        panelTareaCotizada = new javax.swing.JPanel();
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
        rbDevolverHoras = new javax.swing.JRadioButton();
        rbEliminarHoras = new javax.swing.JRadioButton();
        PanelAsignaciones = new javax.swing.JPanel();
        rbFiltroTodos = new javax.swing.JRadioButton();
        rbFiltroActivos = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleadosDisponibles = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleadosAsignados = new javax.swing.JTable();
        btnAgregarEmpleado = new javax.swing.JButton();
        btnQuitarEmpleado = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setTitle("Detalle de Recursos Humanos");
        setOpaque(true);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

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

        tabAsignacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabAsignacionStateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Impacto en horas del detalle del mismo tipo en la tarea de nivel superior:"));
        jPanel1.setEnabled(false);

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

        lblPersonasDetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPersonasDetalleTPadreDisponibles_Nvo.setText("---");

        lblHsNormalesDetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHsNormalesDetalleTPadreDisponibles_Nvo.setText("---");

        lblHs50DetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs50DetalleTPadreDisponibles_Nvo.setText("---");

        lblHs100DetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHs100DetalleTPadreDisponibles_Nvo.setText("---");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Especialidad");

        lblEspecialidadDetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEspecialidadDetalleTPadreDisponibles.setText("---");

        lblEspecialidadDetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEspecialidadDetalleTPadreDisponibles_Nvo.setText("---");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Costo/Hora");

        lblCostoDetalleTPadreDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCostoDetalleTPadreDisponibles.setText("---");

        lblCostoDetalleTPadreDisponibles_Nvo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCostoDetalleTPadreDisponibles_Nvo.setText("---");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEspecialidadDetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEspecialidadDetalleTPadreDisponibles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPersonasDetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPersonasDetalleTPadreDisponibles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesDetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesDetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50DetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50DetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHs100DetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHs100DetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCostoDetalleTPadreDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCostoDetalleTPadreDisponibles_Nvo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel9)
                    .addGap(1, 1, 1)
                    .addComponent(lblHs50DetalleTPadreDisponibles)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblHs50DetalleTPadreDisponibles_Nvo))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEspecialidadDetalleTPadreDisponibles)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEspecialidadDetalleTPadreDisponibles_Nvo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(lblHsNormalesDetalleTPadreDisponibles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHsNormalesDetalleTPadreDisponibles_Nvo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(1, 1, 1)
                        .addComponent(lblPersonasDetalleTPadreDisponibles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPersonasDetalleTPadreDisponibles_Nvo))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(1, 1, 1)
                .addComponent(lblHs100DetalleTPadreDisponibles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHs100DetalleTPadreDisponibles_Nvo))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(lblCostoDetalleTPadreDisponibles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCostoDetalleTPadreDisponibles_Nvo))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Horas a usar en este detalle"));
        jPanel2.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel16.setText("Especialidad:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel18.setText("Rango:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel20.setText("Hs 100%");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel21.setText("Hs 50%");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel22.setText("Hs Normales");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
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

        txtCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCostoFocusLost(evt);
            }
        });
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("Costo/Hora");

        btnSetearCostoRango.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        btnSetearCostoRango.setToolTipText("Setear este costo como costo por defecto del rando de empleado seleccionado");
        btnSetearCostoRango.setEnabled(false);
        btnSetearCostoRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetearCostoRangoActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(cboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(cboRango, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSetearCostoRango, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnHs50, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnHs100, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnSetearCostoRango, 0, 0, Short.MAX_VALUE)
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHsNormales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHs50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHs100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelTareaCotizada.setBorder(javax.swing.BorderFactory.createTitledBorder("Implicaciones en la tarea cotizada"));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel15.setText("Actuales");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel14.setText("<html>Originalmente<br>cotizadas</html>");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("  Costo  ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hs al 100%");

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
        jLabel4.setText("Hs al 50%");

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

        javax.swing.GroupLayout panelTareaCotizadaLayout = new javax.swing.GroupLayout(panelTareaCotizada);
        panelTareaCotizada.setLayout(panelTareaCotizadaLayout);
        panelTareaCotizadaLayout.setHorizontalGroup(
            panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTareaCotizadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCostoTCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCostoTCotizadaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesTCotizada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHsNormalesTCotizadaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50TCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHs50TCotizadaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHs100TCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHs100TCotizadaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(39, 39, 39))
        );
        panelTareaCotizadaLayout.setVerticalGroup(
            panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTareaCotizadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelTareaCotizadaLayout.createSequentialGroup()
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHsNormalesTCotizada)
                    .addComponent(lblHs50TCotizada)
                    .addComponent(lblHs100TCotizada)
                    .addComponent(lblCostoTCotizada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTareaCotizadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHs50TCotizadaActual)
                    .addComponent(lblHsNormalesTCotizadaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHs100TCotizadaActual)
                    .addComponent(lblCostoTCotizadaActual)
                    .addComponent(jLabel15)))
        );

        buttonGroup1.add(rbDevolverHoras);
        rbDevolverHoras.setSelected(true);
        rbDevolverHoras.setText("Usar horas dela tarea superior");
        rbDevolverHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDevolverHorasActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbEliminarHoras);
        rbEliminarHoras.setText("Horas nuevas");
        rbEliminarHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEliminarHorasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelEsfuerzoLayout = new javax.swing.GroupLayout(PanelEsfuerzo);
        PanelEsfuerzo.setLayout(PanelEsfuerzoLayout);
        PanelEsfuerzoLayout.setHorizontalGroup(
            PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                .addGroup(PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelTareaCotizada, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(rbDevolverHoras)
                        .addGap(65, 65, 65)
                        .addComponent(rbEliminarHoras)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        PanelEsfuerzoLayout.setVerticalGroup(
            PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEsfuerzoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEsfuerzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDevolverHoras)
                    .addComponent(rbEliminarHoras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(panelTareaCotizada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabAsignacion.addTab("Esfuerzo", PanelEsfuerzo);

        PanelAsignaciones.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PanelAsignacionesFocusGained(evt);
            }
        });

        rbFiltroTodos.setText("Mostrar Todos");
        rbFiltroTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroTodosActionPerformed(evt);
            }
        });

        rbFiltroActivos.setText("Mostrar sólo empleados en estado \"Activo\"");
        rbFiltroActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFiltroActivosActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setText("Buscar...");
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
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N

        tblEmpleadosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Foto", "Datos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleadosDisponibles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleadosDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEmpleadosDisponiblesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleadosDisponibles);

        tblEmpleadosAsignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Foto", "Datos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleadosAsignados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleadosAsignados.setTableHeader(null);
        tblEmpleadosAsignados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEmpleadosAsignadosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmpleadosAsignados);

        btnAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarEmpleado.setText("Agregar");
        btnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpleadoActionPerformed(evt);
            }
        });

        btnQuitarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/Erase.png"))); // NOI18N
        btnQuitarEmpleado.setText("Quitar");
        btnQuitarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarEmpleadoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Empleados registrados:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Empleados asignados:");

        javax.swing.GroupLayout PanelAsignacionesLayout = new javax.swing.GroupLayout(PanelAsignaciones);
        PanelAsignaciones.setLayout(PanelAsignacionesLayout);
        PanelAsignacionesLayout.setHorizontalGroup(
            PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAsignacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(PanelAsignacionesLayout.createSequentialGroup()
                        .addGroup(PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAsignacionesLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(btnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)
                            .addGroup(PanelAsignacionesLayout.createSequentialGroup()
                                .addComponent(rbFiltroTodos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbFiltroActivos)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19))
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelAsignacionesLayout.setVerticalGroup(
            PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAsignacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbFiltroTodos)
                        .addComponent(rbFiltroActivos))
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelAsignacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarEmpleado)
                    .addComponent(btnQuitarEmpleado))
                .addGap(8, 8, 8)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        tabAsignacion.addTab("Asignaciones", PanelAsignaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabAsignacion)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
        //this.actualizarPantallas();
        //this.limpiarDatosDetalleEnPantalla();
        if(!huboFocoEnAsignaciones && !gestor.todosLosEmpleadosAsignados())
        {
            int resp = JOptionPane.showConfirmDialog(this.getParent(), "¿Desea asignar empleados a los detalles antes de finalizar?", "Asignar empleados", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) 
            {
                tabAsignacion.setSelectedComponent(PanelAsignaciones);
                return;
            } 
            
        }
        if(validar( true))
        {
            gestor.guardarCambios();
        }
        
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    public void actualizarPantallas()
    {
        pantallaPrincipal.actualizar(0, "", true, null);
    }
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
               cboRango.setEnabled(true);
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
            idEspecialidad=((NTupla) cboRango.getSelectedItem()).getId();
            costoDetalle=(Double)((NTupla) cboRango.getSelectedItem()).getData();
            txtCosto.setText(String.valueOf(costoDetalle));
            if(!enProceso)
            {
                mandarCambios(); 
                intentarActivarAceptar();   
            }
            
            
        }
    }//GEN-LAST:event_cboRangoActionPerformed

    private void txtCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostoFocusLost
       if(!enProceso)
       {        
           //costoDetalle=Double.parseDouble(txtCosto.getText());
                mandarCambios(); 
                intentarActivarAceptar();   
       }
    
}//GEN-LAST:event_txtCostoFocusLost

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
        if (Validaciones.validarNumeroPositivo(txtCosto.getText().replace(',', '.'))) 
        {
            String costo = Double.toString((Double) ((NTupla) cboRango.getModel().getSelectedItem()).getData()).replace(".", ",");
            if (txtCosto.getText().equals(costo)) {
                btnSetearCostoRango.setEnabled(false);
            } else {
                btnSetearCostoRango.setEnabled(true);
            }
            if(!enProceso && validar(false))
            {
                costoDetalle=Double.parseDouble(txtCosto.getText());
                mandarCambios(); 
                intentarActivarAceptar();   
            }
        } else {
            btnSetearCostoRango.setEnabled(false);
        }
    }//GEN-LAST:event_txtCostoKeyReleased

    private void btnSetearCostoRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetearCostoRangoActionPerformed
        //if(!txtCosto.getText().equals(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData())))
        if (!txtCosto.getText().replace(",", ".").equals(Double.toString((Double) ((NTupla) cboRango.getModel().getSelectedItem()).getData()))) {
            int resp = JOptionPane.showConfirmDialog(this.getParent(), "¿Seguro que desea cambiar el costo por defecto del rango de empleado'" + ((NTupla) cboRango.getModel().getSelectedItem()).getNombre() + "' a " + txtCosto.getText() + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                if (Validaciones.validarNumeroPositivo(txtCosto.getText().replace(',', '.'))) {
                    double nuevoCosto = Double.parseDouble(txtCosto.getText().replace(',', '.'));
                    //double nuevoCosto=Double.parseDouble(txtCosto.getText());
                    if (gestor.setearNuevoCostoPorDefectoEnRolEmpleado(((NTupla) cboRango.getModel().getSelectedItem()).getId(), nuevoCosto)) {
                        JOptionPane.showMessageDialog(this.getParent(), "Nuevo costo por defecto modificado exitosamente", "Exito", JOptionPane.OK_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(this.getParent(), "No se pudo guardar el nuevo costo por defecto. Ocurrió un error en el proceso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.getParent(), "El costo ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
                    txtCosto.requestFocusInWindow();
                }
            }
        }
    }//GEN-LAST:event_btnSetearCostoRangoActionPerformed

    private void rbFiltroTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroTodosActionPerformed
        rbFiltroTodos.setSelected(true);
        rbFiltroActivos.setSelected(false);
        activarFiltrosTabla();
    }//GEN-LAST:event_rbFiltroTodosActionPerformed

    private void rbFiltroActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFiltroActivosActionPerformed
        rbFiltroTodos.setSelected(false);
        rbFiltroActivos.setSelected(true);
        activarFiltrosTabla();
    }//GEN-LAST:event_rbFiltroActivosActionPerformed

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked

        if (txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained

        if (txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
            filtroBuscarActivado = true;
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (txtBuscar.getText().equals("")) {
            txtBuscar.setText("Buscar...");
            txtBuscar.setForeground(Color.GRAY);
            filtroBuscarActivado = false;
        } else {
            filtroBuscarActivado = true;
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        activarFiltrosTabla();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

   }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed
        int selectedRow=tblEmpleadosDisponibles.getSelectedRow();
       
       if(tblEmpleadosAsignados.getRowCount()<cantPersonas)
       {
           if(selectedRow!=-1 )
            {
		///////////////////////////////
		Empleado e=((ExplorarEmpleados_celdaDatos)tblEmpleadosDisponibles.getValueAt(selectedRow, 1)).getEmpleado();
                boolean asignarlo= true;
		String solapadas =  gestor.validarDisponibilidad(e);                           
		if(solapadas!=null){
                    String encabezado="Durante las fechas de la tarea, el empleado "+e.getNombreEmpleado()+" \n ya se encuentra asignado en las siguientes tareas:"                                   
                                + "\n ";
                            //+ "              ************************************************* \n"*;
                   // JOptionPane.showMessageDialog(this.getParent(), encabezado+solapadas+"\n ¿Desea asignarlo de todos modos?", "El empleado se encuentra asignado a otras tareas en esa fecha", JOptionPane.INFORMATION_MESSAGE);
                    /////////
                    int resp = JOptionPane.showConfirmDialog(this.getParent(), encabezado+solapadas+"\n ¿Desea asignarlo de todos modos?", "El empleado se encuentra asignado a otras tareas en esa fecha", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.NO_OPTION) {
                        asignarlo= true;
                        /*DefaultTableModel modeloDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();           
                        DefaultTableModel modeloAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
                        int altura = tblEmpleadosDisponibles.getRowHeight(tblEmpleadosDisponibles.getSelectedRow());
                        modeloAsignados.addRow((Vector)modeloDisponibles.getDataVector().elementAt(tblEmpleadosDisponibles.getSelectedRow()));
                        tblEmpleadosAsignados.setRowHeight(tblEmpleadosAsignados.getRowCount()-1, altura);
                        modeloDisponibles.removeRow(tblEmpleadosDisponibles.getSelectedRow());  */
                    }
                    ///////////
		}
		/////////////////
                if(asignarlo){
                    DefaultTableModel modeloDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();           
                    DefaultTableModel modeloAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
                    int altura = tblEmpleadosDisponibles.getRowHeight(tblEmpleadosDisponibles.getSelectedRow());
                    modeloAsignados.addRow((Vector)modeloDisponibles.getDataVector().elementAt(tblEmpleadosDisponibles.getSelectedRow()));
                    tblEmpleadosAsignados.setRowHeight(tblEmpleadosAsignados.getRowCount()-1, altura);
                    modeloDisponibles.removeRow(tblEmpleadosDisponibles.getSelectedRow());  
                }
            }
       }
       else {
            JOptionPane.showMessageDialog(this.getParent(), "Todos los empleados ya se encuentran asignados a este detalle", "Asignados todos los empelados", JOptionPane.INFORMATION_MESSAGE);          
       }
       btnAgregarEmpleado.setEnabled(false);
       calcularListaEmpleadosAsignados();
       intentarActivarAceptar();  
    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void btnQuitarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarEmpleadoActionPerformed
      int selectedRow=tblEmpleadosAsignados.getSelectedRow();
      if(selectedRow!=-1)
       {		   
           DefaultTableModel modeloDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();           
           DefaultTableModel modeloAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
           int altura = tblEmpleadosAsignados.getRowHeight(tblEmpleadosAsignados.getSelectedRow());
           modeloDisponibles.addRow((Vector)modeloAsignados.getDataVector().elementAt(tblEmpleadosAsignados.getSelectedRow()));
           tblEmpleadosDisponibles.setRowHeight(tblEmpleadosDisponibles.getRowCount()-1, altura);
           modeloAsignados.removeRow(tblEmpleadosAsignados.getSelectedRow()); 
           
       }
      btnQuitarEmpleado.setEnabled(false);
      calcularListaEmpleadosAsignados();
      intentarActivarAceptar();  
    }//GEN-LAST:event_btnQuitarEmpleadoActionPerformed

    private void tblEmpleadosDisponiblesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosDisponiblesMousePressed
        clicEnTablaEmpleadosDisponibles();
         evt.consume();
    }//GEN-LAST:event_tblEmpleadosDisponiblesMousePressed

    private void tblEmpleadosAsignadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosAsignadosMousePressed
        clicEnTablaEmpleadosAsignados();
        evt.consume();        
    }//GEN-LAST:event_tblEmpleadosAsignadosMousePressed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        inicializarVentana();
    }//GEN-LAST:event_formFocusGained

    private void rbDevolverHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDevolverHorasActionPerformed
        setearDevolverHoras();
    }//GEN-LAST:event_rbDevolverHorasActionPerformed

    private void setearDevolverHoras() {
        if(rbDevolverHoras.isSelected())
        {
            gestor.setDevolverHorasATareaSuperior(true);
        }
        else
        {
            gestor.setDevolverHorasATareaSuperior(false);
        }
    }
    
    private void rbEliminarHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEliminarHorasActionPerformed
        
        setearDevolverHoras();
    }//GEN-LAST:event_rbEliminarHorasActionPerformed

    private void PanelAsignacionesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PanelAsignacionesFocusGained
        
    }//GEN-LAST:event_PanelAsignacionesFocusGained

    private void tabAsignacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabAsignacionStateChanged
        
        if(tabAsignacion.getSelectedComponent()==PanelAsignaciones)
        {
           huboFocoEnAsignaciones=true; 
        }
    
    }//GEN-LAST:event_tabAsignacionStateChanged
           
    private void clicEnTablaEmpleadosDisponibles()
    {
       int selectedRow=tblEmpleadosDisponibles.getSelectedRow();
       if(selectedRow!=-1)
       {
              btnAgregarEmpleado.setEnabled(true);
              btnQuitarEmpleado.setEnabled(false);
              tblEmpleadosAsignados.clearSelection();
       }
       else
       {
           btnAgregarEmpleado.setEnabled(false);
       }
         
    }
    
     private void clicEnTablaEmpleadosAsignados()
    {
       int selectedRow=tblEmpleadosAsignados.getSelectedRow();
       if(selectedRow!=-1)
       {
              btnQuitarEmpleado.setEnabled(true);
              btnAgregarEmpleado.setEnabled(false);
              tblEmpleadosDisponibles.clearSelection();
              
       }
       else
       {
           btnQuitarEmpleado.setEnabled(false);
       }     
         
    }
    
    
   
    private void setearDatosDetalleActual()
    {
         enProceso=true;    
       
       
      
            habilitarDespuesDeClickEnTabla(true); 
           
            tomarDatosDetalleModificado(gestor.getDetalleActual());  
            if(detalleSuperiorSeleccionado!=null)
            { disponiblesPersonasEnDetalleSuperior = detalleSuperiorSeleccionado.getCantidadPersonas();
               disponiblesPersonasAsignadasEnDetalleSuperior = detalleSuperiorSeleccionado.getCantidadPersonasAsignadas();
               disponiblesHsNormalesEnDetalleSuperior = detalleSuperiorSeleccionado.getCantHorasNormales();
               disponiblesHs50EnDetalleSuperior = detalleSuperiorSeleccionado.getCantHorasAl50();
               disponiblesHs100EnDetalleSuperior = detalleSuperiorSeleccionado.getCantHorasAl100();
               especialidadDetalleTPadreDisponibles=detalleSuperiorSeleccionado.getEspecialidad().getTipo().getNombre().toString()+"-"+detalleSuperiorSeleccionado.getEspecialidad().getRango().getNombre().toString();
               costoEnDetalleSuperior=detalleSuperiorSeleccionado.getCostoXHoraNormal();
            }
            if(!tareaHijaDePlanificacion)
            {    
                setearDatosDetalleSuperiorEnPantalla();
            }
                  
       
      // mandarCambios();
        enProceso=false; 
    }
    
    private void setearDatosDetalleSuperiorEnPantalla()
    {
        
        if(disponiblesPersonasEnDetalleSuperior_nva!=0 || (disponiblesHsNormalesEnDetalleSuperior_nva!=0.0 && disponiblesHs50EnDetalleSuperior_nva!=0.0 && disponiblesHs100EnDetalleSuperior_nva!=0.0))
        {
            lblPersonasDetalleTPadreDisponibles.setText(String.valueOf(disponiblesPersonasEnDetalleSuperior_nva));
            lblHsNormalesDetalleTPadreDisponibles.setText(String.valueOf(disponiblesHsNormalesEnDetalleSuperior_nva));
            lblHs50DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs50EnDetalleSuperior_nva));
            lblHs100DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs100EnDetalleSuperior_nva)); 
            lblEspecialidadDetalleTPadreDisponibles.setText(especialidadDetalleTPadreDisponibles_nva);
            lblCostoDetalleTPadreDisponibles.setText(String.valueOf(costoEnDetalleSuperior_nva));
            
            
            lblPersonasDetalleTPadreDisponibles_Nvo.setText(String.valueOf(disponiblesPersonasEnDetalleSuperior));
            lblHsNormalesDetalleTPadreDisponibles_Nvo.setText(String.valueOf(disponiblesHsNormalesEnDetalleSuperior));
            lblHs50DetalleTPadreDisponibles_Nvo.setText(String.valueOf(disponiblesHs50EnDetalleSuperior));
            lblHs100DetalleTPadreDisponibles_Nvo.setText(String.valueOf(disponiblesHs100EnDetalleSuperior));
            lblEspecialidadDetalleTPadreDisponibles_Nvo.setText(especialidadDetalleTPadreDisponibles);
            lblCostoDetalleTPadreDisponibles_Nvo.setText(String.valueOf(costoEnDetalleSuperior));

        }
        else
        {
            lblPersonasDetalleTPadreDisponibles_Nvo.setText(" ");
            lblHsNormalesDetalleTPadreDisponibles_Nvo.setText(" ");
            lblHs50DetalleTPadreDisponibles_Nvo.setText(" ");
            lblHs100DetalleTPadreDisponibles_Nvo.setText(" ");
            lblEspecialidadDetalleTPadreDisponibles_Nvo.setText(" ");
            lblCostoDetalleTPadreDisponibles_Nvo.setText(" ");
            
            lblPersonasDetalleTPadreDisponibles.setText(String.valueOf(disponiblesPersonasEnDetalleSuperior));
            lblHsNormalesDetalleTPadreDisponibles.setText(String.valueOf(disponiblesHsNormalesEnDetalleSuperior));
            lblHs50DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs50EnDetalleSuperior));
            lblHs100DetalleTPadreDisponibles.setText(String.valueOf(disponiblesHs100EnDetalleSuperior));
            lblEspecialidadDetalleTPadreDisponibles.setText(especialidadDetalleTPadreDisponibles);
            lblCostoDetalleTPadreDisponibles.setText(String.valueOf(costoEnDetalleSuperior));
        }
        
        
        
    }
    
    private void vaciarDatosDetalle()
    {
        disponiblesPersonasEnDetalleSuperior=1;
        disponiblesPersonasAsignadasEnDetalleSuperior=0;
        disponiblesHsNormalesEnDetalleSuperior=0;
        disponiblesHs100EnDetalleSuperior=0;
        disponiblesHs50EnDetalleSuperior=0;
        //detalleSuperiorSeleccionado=null;
        especialidadDetalleTPadreDisponibles="---";
        disponiblesPersonasEnDetalleSuperior_nva=0;
        disponiblesPersonasAsignadasEnDetalleSuperior_nva=0;
        disponiblesHsNormalesEnDetalleSuperior_nva=0;
        disponiblesHs100EnDetalleSuperior_nva=0;
        disponiblesHs50EnDetalleSuperior_nva=0;
        especialidadDetalleTPadreDisponibles_nva="";
        costoEnDetalleSuperior=0.0;
        limpiarDatosDetalleEnPantalla();
        
    }
    
    private void limpiarDatosDetalleEnPantalla()
    {
        lblPersonasDetalleTPadreDisponibles.setText("---");
        lblHsNormalesDetalleTPadreDisponibles.setText("---");
        lblHs50DetalleTPadreDisponibles.setText("---");
        lblHs100DetalleTPadreDisponibles.setText("---");
        lblEspecialidadDetalleTPadreDisponibles.setText("---");
        lblCostoDetalleTPadreDisponibles.setText("---");
        
        lblPersonasDetalleTPadreDisponibles_Nvo.setText(" ");
        lblHsNormalesDetalleTPadreDisponibles_Nvo.setText(" ");
        lblHs50DetalleTPadreDisponibles_Nvo.setText(" ");
        lblHs100DetalleTPadreDisponibles_Nvo.setText(" ");
        lblEspecialidadDetalleTPadreDisponibles_Nvo.setText(" ");
        lblCostoDetalleTPadreDisponibles_Nvo.setText(" ");
        DefaultComboBoxModel model = new DefaultComboBoxModel();        
        cboRango.setModel(model);
        btnAceptar.setEnabled(false);
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
        cboRango.setEnabled(false);
        cboTipoEspecialidad.setEnabled(habilitar);
        //if(!habilitar)
        {
           // cargarCboTipoEspecialidad();
        }
        
    }
    
    
    private void setearEscuchasSpinners()
    {
        
        ChangeListener listenerSpiners = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               //System.out.println("cantPersonas A: " + cantPersonas);
               // System.out.println("COMBO PERSONAS A: " + spnPersonas.getModel().getValue());
                if(e.getSource().equals(spnPersonas) )
                {
                    int cantPersonasN=(Integer)spnPersonas.getModel().getValue();
                    if(cantPersonasN<cantPersonasAsignadas)
                    {
                        MostrarMensaje(JOptionPane.INFORMATION_MESSAGE,"Imposible reducir la cantidad","No se puede reducir la cantidad de personas ya que los puestos ya estan asignados. Por favor, elimine una asignacion de empleado antes de reducir la cantidad");
                        spnPersonas.getModel().setValue(cantPersonas);
                        return;
                    }
                    else
                    {
                        cantPersonas=cantPersonasN;
                    }
                //    System.out.println("cantPersonas B: " + cantPersonas);
                //    System.out.println("COMBO PERSONAS B: " + spnPersonas.getModel().getValue());
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
                
                if(!enProceso)
                {
                    mandarCambios();                  
                     if(cantHsNormales!=0.0 || cantHs50!=0.0 || cantHs100!=0.0)
                    {
                        intentarActivarAceptar();
                    }       
                }
                
               // System.out.println("cantPersonas C: " + cantPersonas);
               // System.out.println("COMBO PERSONAS C: " + spnPersonas.getModel().getValue());
               // System.out.println("--------------------");
                
            }
       };

       spnPersonas.addChangeListener(listenerSpiners);
       spnHsNormales.addChangeListener(listenerSpiners);
       spnHs50.addChangeListener(listenerSpiners);
       spnHs100.addChangeListener(listenerSpiners);
    }
    
    
    
    private void intentarActivarAceptar()
    {
        if((cantHsNormales!=0.0 || cantHs50!=0.0 || cantHs100!=0.0) && cboRango.getSelectedItem()!=null && (((NTupla) cboRango.getSelectedItem()).getId() != -1) && (costoDetalle!=0.0))
        {
            btnAceptar.setEnabled(true);
        }
        else
        {
            btnAceptar.setEnabled(false);
        }
    }
    
    public void mandarCambios()
    {
        if(idEspecialidad>0) {            
            gestor.tomarCambios(cantPersonas, cantHsNormales,  cantHs50,  cantHs100,  costoDetalle, idEspecialidad);
        }
    }
    
    public void actualizar()
    {
        if(!tareaHijaDePlanificacion)
        {
            if(gestor.getCopiaDetallePadre()!=null)
            {disponiblesPersonasEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantidadPersonas();
            disponiblesPersonasAsignadasEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantidadPersonasAsignadas();
            disponiblesHsNormalesEnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasNormales();
            disponiblesHs50EnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasAl50();
            disponiblesHs100EnDetalleSuperior=gestor.getCopiaDetallePadre().getCantHorasAl100();
            especialidadDetalleTPadreDisponibles=gestor.getCopiaDetallePadre().getEspecialidad().getTipo().getNombre().toString()+"-"+gestor.getCopiaDetallePadre().getEspecialidad().getRango().getNombre().toString();
            costoEnDetalleSuperior=gestor.getCopiaDetallePadre().getCostoXHoraNormal();}
        
        
            if(gestor.getCopiaDetallePadre_nvo()!=null)
            {disponiblesPersonasEnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCantidadPersonas();
            disponiblesPersonasAsignadasEnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCantidadPersonasAsignadas();
            disponiblesHsNormalesEnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCantHorasNormales();
            disponiblesHs50EnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCantHorasAl50();
            disponiblesHs100EnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCantHorasAl100(); 
            especialidadDetalleTPadreDisponibles_nva=gestor.getCopiaDetallePadre_nvo().getEspecialidad().getTipo().getNombre().toString()+"-"+gestor.getCopiaDetallePadre_nvo().getEspecialidad().getRango().getNombre().toString();
            costoEnDetalleSuperior_nva=gestor.getCopiaDetallePadre_nvo().getCostoXHoraNormal();} 

            else
            {disponiblesPersonasEnDetalleSuperior_nva=0;
            disponiblesPersonasAsignadasEnDetalleSuperior_nva=0;
            disponiblesHsNormalesEnDetalleSuperior_nva=0.0;
            disponiblesHs100EnDetalleSuperior_nva=0.0;
            disponiblesHs50EnDetalleSuperior_nva=0.0;
            especialidadDetalleTPadreDisponibles_nva="";
            costoEnDetalleSuperior_nva=0.0;
            }        
       }
        
        setearValoresTareaCotizadaActual();
       if(!tareaHijaDePlanificacion)
       {
            setearDatosDetalleSuperiorEnPantalla();
       }
    }
    
    
    
    public void MostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAsignaciones;
    private javax.swing.JPanel PanelEsfuerzo;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarEmpleado;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnQuitarEmpleado;
    private javax.swing.JButton btnSetearCostoRango;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboRango;
    private javax.swing.JComboBox cboTipoEspecialidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCostoDetalleTPadreDisponibles;
    private javax.swing.JLabel lblCostoDetalleTPadreDisponibles_Nvo;
    private javax.swing.JLabel lblCostoTCotizada;
    private javax.swing.JLabel lblCostoTCotizadaActual;
    private javax.swing.JLabel lblEspecialidadDetalleTPadreDisponibles;
    private javax.swing.JLabel lblEspecialidadDetalleTPadreDisponibles_Nvo;
    private javax.swing.JLabel lblHs100DetalleTPadreDisponibles;
    private javax.swing.JLabel lblHs100DetalleTPadreDisponibles_Nvo;
    private javax.swing.JLabel lblHs100TCotizada;
    private javax.swing.JLabel lblHs100TCotizadaActual;
    private javax.swing.JLabel lblHs50DetalleTPadreDisponibles;
    private javax.swing.JLabel lblHs50DetalleTPadreDisponibles_Nvo;
    private javax.swing.JLabel lblHs50TCotizada;
    private javax.swing.JLabel lblHs50TCotizadaActual;
    private javax.swing.JLabel lblHsNormalesDetalleTPadreDisponibles;
    private javax.swing.JLabel lblHsNormalesDetalleTPadreDisponibles_Nvo;
    private javax.swing.JLabel lblHsNormalesTCotizada;
    private javax.swing.JLabel lblHsNormalesTCotizadaActual;
    private javax.swing.JLabel lblPersonasDetalleTPadreDisponibles;
    private javax.swing.JLabel lblPersonasDetalleTPadreDisponibles_Nvo;
    private javax.swing.JPanel panelTareaCotizada;
    private javax.swing.JRadioButton rbDevolverHoras;
    private javax.swing.JRadioButton rbEliminarHoras;
    private javax.swing.JRadioButton rbFiltroActivos;
    private javax.swing.JRadioButton rbFiltroTodos;
    private javax.swing.JSpinner spnHs100;
    private javax.swing.JSpinner spnHs50;
    private javax.swing.JSpinner spnHsNormales;
    private javax.swing.JSpinner spnPersonas;
    private javax.swing.JTabbedPane tabAsignacion;
    private javax.swing.JTable tblEmpleadosAsignados;
    private javax.swing.JTable tblEmpleadosDisponibles;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JFormattedTextField txtCosto;
    // End of variables declaration//GEN-END:variables

     /*private void tomarDatosDetalleNuevo() {
        especialidadPadre = detalleSuperiorSeleccionado.getEspecialidad();
        //gestor.crearNuevoDetalleAcutal(detalleSuperiorSeleccionado);
        SpinnerModel modelPersonas =
                new SpinnerNumberModel(1, //initial value
                1, //min
                detalleSuperiorSeleccionado.getCantidadPersonas(), //max
                1);//step
        spnPersonas.setModel(modelPersonas);
        spnPersonas.setValue(detalleSuperiorSeleccionado.getCantidadPersonas());
        cantPersonas = detalleSuperiorSeleccionado.getCantidadPersonas();
        spnHsNormales.setValue(0.0);
        spnHs50.setValue(0.0);
        spnHs100.setValue(0.0);
        for (int i = 0; i < cboTipoEspecialidad.getItemCount(); i++) {
            if ((((NTupla) cboTipoEspecialidad.getItemAt(i)).getId()) == especialidadPadre.getTipo().getId()) {
                cboTipoEspecialidad.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cboRango.getItemCount(); i++) {
            if ((((NTupla) cboRango.getItemAt(i)).getId()) == especialidadPadre.getId()) {
                cboRango.setSelectedIndex(i);
                break;
            }
        }
        costoDetalle = detalleSuperiorSeleccionado.getCostoXHoraNormal();
        txtCosto.setText(String.valueOf(costoDetalle));
    }*/

    
    private void tomarDatosDetalleModificado(DetalleTareaPlanificacion detalleAcutal) {
        enProceso=true;
        cantPersonas = detalleAcutal.getCantidadPersonas();
        cantHsNormales = detalleAcutal.getCantHorasNormales();
        cantHs100 = detalleAcutal.getCantHorasAl100();
        cantHs50 = detalleAcutal.getCantHorasAl50();
        
        if(cantPersonas==0)
        {cantPersonas=1;}
        
        if(detalleAcutal.getEspecialidad()!=null)
        {idEspecialidad = detalleAcutal.getEspecialidad().getId();}
        
        int maximoPersonas=200;
        if(detalleSuperiorSeleccionado!=null)
        {
            maximoPersonas=detalleSuperiorSeleccionado.getCantidadPersonas();
        }
        
        SpinnerModel modelPersonas =
                new SpinnerNumberModel(1, //initial value
                1, //min
                maximoPersonas, //max
                1);//step
        spnPersonas.setModel(modelPersonas);
        
        spnPersonas.setValue(cantPersonas);
        spnHsNormales.setValue(cantHsNormales);
        spnHs50.setValue(cantHs50);
        spnHs100.setValue(cantHs100);

        if(detalleAcutal.getEspecialidad()!=null)
        {
            for (int i = 0; i < cboTipoEspecialidad.getItemCount(); i++) {
                if ((((NTupla) cboTipoEspecialidad.getItemAt(i)).getId()) == detalleAcutal.getEspecialidad().getTipo().getId()) {
                    cboTipoEspecialidad.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cboRango.getItemCount(); i++) {
                if ((((NTupla) cboRango.getItemAt(i)).getId()) == detalleAcutal.getEspecialidad().getId()) {
                    cboRango.setSelectedIndex(i);
                    break;
                }
            }
        }
        costoDetalle = detalleAcutal.getCostoXHoraNormal();
        txtCosto.setText(String.valueOf(costoDetalle));
        
        enProceso=false;
    }
    
    
    private boolean validar(boolean mostrarErrores)
    {
        if(!Validaciones.validarNumeroPositivo(txtCosto.getText().replace( ',','.' )))
       {  
          if(mostrarErrores)
          {JOptionPane.showMessageDialog(this.getParent(), "El costo ingresado no es válido", "Error",JOptionPane.ERROR_MESSAGE);
          txtCosto.requestFocusInWindow();}
          return false;          
       } 
        if(!Validaciones.validarNumeroPositivo(txtCosto.getText().replace( ',','.' )))
       {  
          if(mostrarErrores)
          {JOptionPane.showMessageDialog(this.getParent(), "El costo ingresado no es válido", "Error",JOptionPane.ERROR_MESSAGE);
          txtCosto.requestFocusInWindow();}
          return false;          
       }
        
        if (((NTupla) cboTipoEspecialidad.getSelectedItem()).getId() == -1) 
        {
            if(mostrarErrores)
            {JOptionPane.showMessageDialog(this.getParent(), "Debe seleccionar un tipo de especialidad", "Error",JOptionPane.ERROR_MESSAGE);
            txtCosto.requestFocusInWindow();}
            return false;          
        }
        if (((NTupla) cboRango.getSelectedItem()).getId() == -1) 
        {
            if(mostrarErrores)
            {JOptionPane.showMessageDialog(this.getParent(), "Debe seleccionar un rango de especialidad", "Error",JOptionPane.ERROR_MESSAGE);
            txtCosto.requestFocusInWindow();}
            return false;          
        }
        if (cantPersonas<cantPersonasAsignadas) 
        {
            if(mostrarErrores)
            {JOptionPane.showMessageDialog(this.getParent(), "La cantidad de personas asignadas no puede ser mayor a la cantidad de personas en el detalle", "Error",JOptionPane.ERROR_MESSAGE);
            spnPersonas.requestFocusInWindow();}
            return false;          
        }
        return true;
    }
    
    private void cargarEmpleadosDisponibles()
    {
        listaEmpleadosDisponibles=gestorConsultarEmpleado.listaEmpleados();
       
        modeloTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();
        int fil=modeloTablaEmpleadosDisponibles.getRowCount();
        for (int i = 0; i < fil; i++) {
            modeloTablaEmpleadosDisponibles.removeRow(0);
        }
        
        
        
        int rowNumber = 0;
        for (Empleado empleado : listaEmpleadosDisponibles)
        {
             Object[] filaTabla=new Object[2];
           
            ExplorarEmpleados_celdaDatos celdaDatos = new ExplorarEmpleados_celdaDatos();            
            celdaDatos.setEmpleado(empleado);  
            ExplorarEmpleados_celdaFoto celdaFoto = new ExplorarEmpleados_celdaFoto();
            celdaFoto.setEmpleado(empleado);
            
            filaTabla[0] = celdaFoto;
            filaTabla[1] = celdaDatos;
            modeloTablaEmpleadosDisponibles.addRow(filaTabla);
            tblEmpleadosDisponibles.setRowHeight(rowNumber, celdaDatos.getAltura());
            
            rowNumber++;
            ////////////////////////
        }
       
        tblEmpleadosDisponibles.setModel(modeloTablaEmpleadosDisponibles);
    }
    
    private void cargarEmpleadosAsignados()
    {
        listaEmpleadosAsignados=gestor.getListaEmpleadosAsignados();
       
        modeloTablaEmpleadosAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
        int fil=modeloTablaEmpleadosAsignados.getRowCount();
        for (int i = 0; i < fil; i++) {
            modeloTablaEmpleadosAsignados.removeRow(0);
        }
        
        int rowNumber = 0;
        for (Empleado empleado : listaEmpleadosAsignados)
        {
             Object[] filaTabla=new Object[2];
           
            ExplorarEmpleados_celdaDatos celdaDatos = new ExplorarEmpleados_celdaDatos();            
            celdaDatos.setEmpleado(empleado);  
            ExplorarEmpleados_celdaFoto celdaFoto = new ExplorarEmpleados_celdaFoto();
            celdaFoto.setEmpleado(empleado);
            
            filaTabla[0] = celdaFoto;
            filaTabla[1] = celdaDatos;
            modeloTablaEmpleadosAsignados.addRow(filaTabla);
            tblEmpleadosAsignados.setRowHeight(rowNumber, celdaDatos.getAltura());
            
            rowNumber++;
            ////////////////////////
        }
       
        tblEmpleadosAsignados.setModel(modeloTablaEmpleadosAsignados);
    }
    
    private void limpiarSobranteTablaEmpleadosDisponibles()
    {
        //DefaultTableModel modeloXTablaEmpleadosAsignados = (DefaultTableModel) tblEmpleadosAsignados.getModel();
        int filAsig=modeloTablaEmpleadosAsignados.getRowCount();
        
        //DefaultTableModel modeloXTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();
       
        for (int i = filAsig-1; i >=0 ; i--) {
            for (int j = modeloTablaEmpleadosDisponibles.getRowCount()-1; j >=0 ; j--) 
            {
                if(((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosAsignados.getValueAt(i, 1)).getEmpleado().getLegajo()  ==  ((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosDisponibles.getValueAt(j, 1)).getEmpleado().getLegajo())
                {modeloTablaEmpleadosDisponibles.removeRow(j);}
            }
            
        }
        
    }
    
    private void calcularListaEmpleadosAsignados()
    {
        listaEmpleadosAsignados.clear();
        int filAsig=modeloTablaEmpleadosAsignados.getRowCount();        
        //DefaultTableModel modeloXTablaEmpleadosDisponibles = (DefaultTableModel) tblEmpleadosDisponibles.getModel();       
        for (int i = filAsig-1; i >=0 ; i--) {
            
            listaEmpleadosAsignados.add(((ExplorarEmpleados_celdaDatos)modeloTablaEmpleadosAsignados.getValueAt(i, 1)).getEmpleado());
        }
        cantPersonasAsignadas=listaEmpleadosAsignados.size();
        gestor.setListaEmpleadosAsignados(listaEmpleadosAsignados);
    }
    
    private void activarFiltrosTabla()
    {
         TableRowSorter<TableModel> modeloOrdenado;
            modeloOrdenado = new TableRowSorter<TableModel>(modeloTablaEmpleadosDisponibles);
            tblEmpleadosDisponibles.setRowSorter(modeloOrdenado);
        

        if(filtroBuscarActivado)
        {
           String[] cadena=txtBuscar.getText().split(" ");
           List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
           for (int i= 0; i < cadena.length; i++)
           {
             filters.add(RowFilter.regexFilter("(?i)" + cadena[i],1));
           }
            if(rbFiltroActivos.isSelected())
           {
              filters.add(RowFilter.regexFilter(gestorConsultarEmpleado.nombreEstadoEmpleadoActivo(), 3));
           }
           RowFilter<Object,Object> cadenaFilter = RowFilter.andFilter(filters);           
           modeloOrdenado.setRowFilter(cadenaFilter);

        }
        else
        {
             if(rbFiltroActivos.isSelected())
           {
              modeloOrdenado.setRowFilter(new EmpleadoActivoRowFilter());
           }
        }


    }
}
