/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * editarCotizacion_ManoDeObra_OpcionB_parte2Fix.java
 *
 * Created on 11-jul-2011, 11:44:54
 */
package vista.cotizacion;
import controlador.cotizacion.GestorCotizacionManoDeObra;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import util.Tupla;
import util.NTupla;
import util.FechaUtil;
import javax.swing.JOptionPane;
import vista.util.Validaciones;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.DetalleSubObraXTarea;
import modelo.SubObraXTarea;

/**
 *
 * @author Fran
 */
public class CotizacionManoDeObraAgregarMO extends javax.swing.JInternalFrame {
    private GestorCotizacionManoDeObra gestor;
    private CotizacionManoDeObraGeneral pantallaPadre;
    private int indiceFilaModificada;
    private int idTarea;
    //private List<DetalleSubObraXTarea> listaDetallesTarea;
    private SubObraXTarea tareaActual;

    /////////////////////////
    ///////////******
    /** Creates new form editarCotizacion_ManoDeObra_OpcionB_parte2Fix */
    public CotizacionManoDeObraAgregarMO(CotizacionManoDeObraGeneral pant, GestorCotizacionManoDeObra gestorX) 
    {
        pantallaPadre=pant;
        gestor=gestorX;
        initComponents();
        indiceFilaModificada=-1;
        idTarea=-1;
        habiliarVentana();    
        //listaDetallesTarea=new ArrayList<DetalleSubObraXTarea>();
    }
    ///////************
    ///////////////////////////// 
    
    public void habiliarVentana()
    {
        cargarCboTareas();
        cargarCboRangos();
        /*jdcFInicio.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFInicioFocusLost(evt);
            }
        });
        jdcFFin.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFFinFocusLost(evt);
            }
        });*/
        txtCosto.addKeyListener(Validaciones.getKaNumeros());
        txtHorasNormales.addKeyListener(Validaciones.getKaNumeros());
        txtHoras50.addKeyListener(Validaciones.getKaNumeros());
        txtHoras100.addKeyListener(Validaciones.getKaNumeros());
        txtPersonas.addKeyListener(Validaciones.getKaNumerosEnteros());
    }
    
    public void cargarCboTareas()
    {
        ArrayList<NTupla> listaNombresTareas = gestor.mostrarNombresTareas();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaNombresTareas!=null && !listaNombresTareas.isEmpty())
        for (NTupla nombre : listaNombresTareas)
        {
            model.addElement(nombre);
        }
        cboTareas.setModel(model);        
        NTupla t0 = new NTupla(-1);
        t0.setNombre("Seleccione una tarea..."); 
        cboTareas.insertItemAt(t0, 0);
        cboTareas.setSelectedIndex(0);   
    }
    
    public void cargarCboRangos()
    {
        ArrayList<NTupla> listaRangos = gestor.mostrarRangos();
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
    
    
    public void tomarValoresDeDatos(SubObraXTarea tarea, int indiceFila)
    {
        //tareaActual;
        indiceFilaModificada=indiceFila;
        idTarea=tarea.getId();
        for (int i = 0; i < cboTareas.getItemCount(); i++) 
        {
            if(((NTupla)cboTareas.getItemAt(i)).getId()==tarea.getTipoTarea().getId());
            {
                cboTareas.setSelectedIndex(i);
            }            
        }
       txtNombre.setText(tarea.getNombre());
       txaObservaciones.setText(tarea.getObservaciones());
        for (DetalleSubObraXTarea detalle:tarea.getDetalles()) {
            agregarDetalleTareaATabla(detalle);
        }
       
       //txtPersonas.setText(((Integer)datos[1]).toString());
       /* Para modificar el detalle
        * for (int i = 0; i < cboRango.getItemCount(); i++) 
        {
            if(((NTupla)cboRango.getItemAt(i)).getId()==tarea. )
            {
                cboRango.setSelectedIndex(i);
            }            
        }
       txtCosto.setText(((Double)((NTupla)datos[2]).getData()).toString().replace(".", ","));
       txtHoras100.setText(((Double)datos[3]).toString().replace(".", ","));
       txtHoras50.setText(((Double)datos[3]).toString().replace(".", ","));
       txtHorasNormales.setText(((Double)datos[3]).toString().replace(".", ","));*/
       mostrarTotalTarea();
    }
    
    
    public void calcularSubtotalDetalle(boolean ok)
    {
       if(ok)
       {  
           double cantPersonas=0.0;
           double costo=0.0;
           double horasNormales=0.0;
           double horas50=0.0;
           double horas100=0.0;
           cantPersonas=Double.parseDouble(txtPersonas.getText());
           costo=Double.parseDouble(txtCosto.getText().replace(",", "."));
           horasNormales=Double.parseDouble(txtHorasNormales.getText().replace(",", "."));
           horas50=Double.parseDouble(txtHoras50.getText().replace(",", "."));
           horas100=Double.parseDouble(txtHoras100.getText().replace(",", "."));
           double subT =cantPersonas*((costo*horasNormales)+(1.5*costo*horas50)+(2*costo*horas100) );
           //double subT=Double.parseDouble(txtPersonas.getText())*( (Double.parseDouble(txtCosto.getText().replace(",", "."))*Double.parseDouble(txtHorasNormales.getText().replace(",", ".")))  +   (1.5*Double.parseDouble(txtCosto.getText().replace(",", "."))*Double.parseDouble(txtHoras50.getText().replace(",", ".")))    +(Double.parseDouble(txtCosto.getText().replace(",", "."))*Double.parseDouble(txtHorasNormales.getText().replace(",", ".")))    );
           //txtSubtotal.setText(Double.toString(subT));
           DecimalFormat df =  new DecimalFormat("0.00");
           txtSubtotal.setText(df.format(subT).replace(".",","));
       }
       else
       {
           txtSubtotal.setText("");
       }
          
    }
    
    public boolean validarDatos(boolean mostrarErrores)
    {
      if(txtNombre.getText().isEmpty())
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Ingrese un nombre para la tarea", "Error",JOptionPane.ERROR_MESSAGE);
          txtNombre.requestFocusInWindow();}
          return false;          
       }
        if(((NTupla)cboTareas.getSelectedItem()).getId()<0)
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Seleccione una tarea", "Error",JOptionPane.ERROR_MESSAGE);
          cboTareas.requestFocusInWindow();}
          return false;          
       }
      if(((NTupla)cboRango.getSelectedItem()).getId()<0)
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Seleccione un rango", "Error",JOptionPane.ERROR_MESSAGE);
          cboRango.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivo(txtPersonas.getText().replace( ',','.' )))
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "La cantidad de personas ingresadas no es válida", "Error",JOptionPane.ERROR_MESSAGE);
          txtPersonas.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivo(txtCosto.getText().replace( ',','.' )))
       {  
          if(mostrarErrores)
          {JOptionPane.showMessageDialog(this.getParent(), "El costo ingresado no es válido", "Error",JOptionPane.ERROR_MESSAGE);
          txtCosto.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivo(txtHoras100.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas al 100% por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHoras100.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivo(txtHorasNormales.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas normales por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHorasNormales.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivo(txtHoras50.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas al 50% por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHoras50.requestFocusInWindow();}
          return false;          
       }
       
       /* if(jdcFInicio.getDate()==null)        
       {
          if(mostrarErrores)
           {    JOptionPane.showMessageDialog(this.getParent(), "Error en la fecha inicio ingresada", "Error",JOptionPane.ERROR_MESSAGE);
                jdcFInicio.requestFocusInWindow();
            }
       
           return false ;
       }
       //if(FechaUtil.getFecha(jdcFFin.getDate()).equals(""))
        if(jdcFFin.getDate()==null)
       {
          if(mostrarErrores)
           {    JOptionPane.showMessageDialog(this.getParent(), "Error en la fecha fin ingresada", "Error",JOptionPane.ERROR_MESSAGE);
                jdcFFin.requestFocusInWindow();
            }
       
           return false ;
       }*/
       
       if(mostrarErrores)
       { 
           
          /* if(jdcFInicio.getDate().before(FechaUtil.getToday(new Date())))        
           {
              //if(mostrarErrores)
               {    JOptionPane.showMessageDialog(this.getParent(), "La fecha inicio ingresada es anterior al día actual", "Error",JOptionPane.ERROR_MESSAGE);
                    jdcFInicio.requestFocusInWindow();
                }

               return false ;
           }
           if(jdcFFin.getDate().before(FechaUtil.getToday(new Date())))        
           {
             // if(mostrarErrores)
               {    JOptionPane.showMessageDialog(this.getParent(), "La fecha fin ingresada es anterior al día actual", "Error",JOptionPane.ERROR_MESSAGE);
                    jdcFInicio.requestFocusInWindow();
                }

               return false ;
           }*/
       }
       
       /*if(FechaUtil.diasDiferencia(jdcFInicio.getDate(), jdcFFin.getDate())<0)
       {    if(mostrarErrores)
           {    JOptionPane.showMessageDialog(this.getParent(), "La fecha de finalización fin de la tarea no puede ser mayor a la fecha de inicio", "Error",JOptionPane.ERROR_MESSAGE);
                jdcFFin.requestFocusInWindow();
            }
       
           return false ;
       }*/
       return true;
        
    }

   
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboRango = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        btnSetearCostoRango = new javax.swing.JButton();
        txtCosto = new javax.swing.JFormattedTextField();
        txtHoras100 = new javax.swing.JFormattedTextField();
        txtPersonas = new javax.swing.JFormattedTextField();
        txtHoras50 = new javax.swing.JFormattedTextField();
        txtHorasNormales = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSubtotalDetalle = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cboTareas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        btnAgregarTipoDeTarea = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetallesTarea = new javax.swing.JTable();
        btnAgregarDetalle = new javax.swing.JButton();
        btnQuitarDetalle = new javax.swing.JButton();

        setClosable(true);
        setTitle("Agregar Mano de Obra");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Cantidad de Personas");

        jLabel4.setText("<html>Cantidad de horas<br>al 100% por persona</html>");

        jLabel7.setText("Costo/Hora");

        jLabel12.setText("<html>Cantidad de horas<br>normales por persona</html>");

        jLabel13.setText("<html>Cantidad de horas<br>al 50% por persona</html>");

        cboRango.setEnabled(false);
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

        jLabel5.setText("Rango de empleados");

        btnSetearCostoRango.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        btnSetearCostoRango.setToolTipText("Setear este costo como costo por defecto del rando de empleado seleccionado");
        btnSetearCostoRango.setEnabled(false);
        btnSetearCostoRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetearCostoRangoActionPerformed(evt);
            }
        });

        txtCosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        txtCosto.setEnabled(false);
        txtCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCostoFocusLost(evt);
            }
        });

        txtHoras100.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        txtHoras100.setEnabled(false);
        txtHoras100.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoras100FocusLost(evt);
            }
        });

        txtPersonas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        txtPersonas.setEnabled(false);
        txtPersonas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPersonasFocusLost(evt);
            }
        });

        txtHoras50.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        txtHoras50.setEnabled(false);
        txtHoras50.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoras50FocusLost(evt);
            }
        });

        txtHorasNormales.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        txtHorasNormales.setEnabled(false);
        txtHorasNormales.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorasNormalesFocusLost(evt);
            }
        });

        jLabel9.setText("Subtotal   $");

        txtSubtotalDetalle.setEditable(false);
        txtSubtotalDetalle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotalDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cboRango, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSetearCostoRango, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(291, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSubtotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHorasNormales, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtHoras50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoras100, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboRango))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnSetearCostoRango, 0, 0, Short.MAX_VALUE)
                                .addComponent(txtCosto)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtHoras100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtHoras50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHorasNormales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSubtotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalActionPerformed(evt);
            }
        });

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down2.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jLabel8.setText("Total de la tarea   $");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarea/ Observaciones"));

        cboTareas.setName(""); // NOI18N
        cboTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTareasActionPerformed(evt);
            }
        });

        txaObservaciones.setColumns(20);
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        jScrollPane1.setViewportView(txaObservaciones);

        btnAgregarTipoDeTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarTipoDeTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTipoDeTareaActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre:");

        jLabel10.setText("Tipo:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 364, Short.MAX_VALUE)
                                .addComponent(btnAgregarTipoDeTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboTareas, javax.swing.GroupLayout.Alignment.TRAILING, 0, 384, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTareas)
                        .addComponent(jLabel10))
                    .addComponent(btnAgregarTipoDeTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
        );

        tblDetallesTarea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rango", "Personas", "Costo Hora", "Hs Normales", "Hs 50%", "Hs 100%", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblDetallesTarea);

        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(btnAgregarDetalle)
                        .addGap(60, 60, 60)
                        .addComponent(btnQuitarDetalle))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarDetalle)
                    .addComponent(btnQuitarDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSetearCostoRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetearCostoRangoActionPerformed
    //if(!txtCosto.getText().equals(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData())))
    if(!txtCosto.getText().replace(",", ".").equals(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData())))
    {
        int resp=JOptionPane.showConfirmDialog(this.getParent(),"¿Seguro que desea cambiar el costo por defecto del rango de empleado'"+((NTupla)cboRango.getModel().getSelectedItem()).getNombre()+"' a "+txtCosto.getText()+"?","ConfirmaciÃ³n",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION)
        {   
            if(Validaciones.validarNumeroPositivo(txtCosto.getText().replace( ',','.' )))
            {   
                double nuevoCosto=Double.parseDouble(txtCosto.getText().replace( ',','.' ));
                //double nuevoCosto=Double.parseDouble(txtCosto.getText());
                if(gestor.setearNuevoCostoPorDefectoEnRolEmpleado(((NTupla)cboRango.getModel().getSelectedItem()).getId(),nuevoCosto))
                {
                    JOptionPane.showMessageDialog(this.getParent(), "Nuevo costo por defecto modificado exitosamente", "Exito",JOptionPane.OK_OPTION);
                }
                else
                {
                    JOptionPane.showMessageDialog(this.getParent(), "No se pudo guardar el nuevo costo por defecto. Ocurrió un error en el proceso", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this.getParent(), "El costo ingresado no es válido", "Error",JOptionPane.ERROR_MESSAGE);
                txtCosto.requestFocusInWindow();
            }
        }
    }
}//GEN-LAST:event_btnSetearCostoRangoActionPerformed

    private void txtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtSubtotalActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

////////////////////////////////////////////////
///********
    private void cboTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTareasActionPerformed
       if(((NTupla)cboTareas.getSelectedItem()).getId()!=-1)
       { 
          if(((NTupla)cboTareas.getItemAt(0)).getId()==-1)
           {
            cboTareas.removeItemAt(0);
           }
        NTupla tarea=(NTupla)cboTareas.getModel().getSelectedItem();
        //txtHoras.setText((String)((Object[])tarea.getData())[0]);
       //txtPersonas.setText((String)((Object[])tarea.getData())[1]);
        txtHoras100.setText(((String)((Object[])tarea.getData())[0]).replace(".", ","));
        txtPersonas.setText(((String)((Object[])tarea.getData())[1]).replace(".", ",")); 
        
       /* if(((NTupla)cboRango.getItemAt(0)).getId() <0)
        {  
         cboRango.removeItemAt(0);
        } */ 
        DefaultComboBoxModel modeloRango=(DefaultComboBoxModel) cboRango.getModel();
            for (int i= 0; i < modeloRango.getSize(); i++)
            {
                if(((NTupla)modeloRango.getElementAt(i)).getId()==((Integer)((Object[])tarea.getData())[2]))
                {
                    cboRango.setSelectedIndex(i);
                    break;
                }
            }
           
        txtPersonas.setEnabled(true);
       cboRango.setEnabled(true);
       txtHoras100.setEnabled(true);
       txtHorasNormales.setEnabled(true);
       txtHoras50.setEnabled(true);
       
        }
       else
       {txtPersonas.setEnabled(false);
       cboRango.setEnabled(false);
       txtHorasNormales.setEnabled(false);
       txtHoras50.setEnabled(false);
       txtHoras100.setEnabled(false);       
       btnSetearCostoRango.setEnabled(false);}
       
    }//GEN-LAST:event_cboTareasActionPerformed

    private void btnAgregarTipoDeTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTipoDeTareaActionPerformed
        // TODO:
    }//GEN-LAST:event_btnAgregarTipoDeTareaActionPerformed

private void cboRangoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRangoItemStateChanged

}//GEN-LAST:event_cboRangoItemStateChanged
///********
////////////////////////////////////////////////

private void cboRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRangoActionPerformed
if(((NTupla)cboRango.getSelectedItem()).getId()!=-1)
{ 
        if(((NTupla)cboRango.getItemAt(0)).getId() <0)
        {  
         cboRango.removeItemAt(0);
        }  

    //txtCosto.setText(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData()));
    txtCosto.setText(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData()).replace(".",","));
    txtCosto.setEnabled(true);
}    
else
{txtCosto.setText("");}
}//GEN-LAST:event_cboRangoActionPerformed



////////////////////////////////////////////////
///********
private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
   if(validarDatos(true))
   {       
       tareaActual.setObservaciones(txaObservaciones.getText());
       tareaActual.setNombre(txtNombre.getText());
       int idTipoTarea=((NTupla)cboTareas.getSelectedItem()).getId();
       tareaActual.setTipoTarea(gestor.levantarTipoTarea(idTipoTarea));
       DefaultTableModel modelo = (DefaultTableModel) tblDetallesTarea.getModel();
       ArrayList<DetalleSubObraXTarea> listaDetallesTarea=new ArrayList<DetalleSubObraXTarea>();
       
        for (int indiceDetalle = 0; indiceDetalle < modelo.getRowCount(); indiceDetalle++) 
        {
           NTupla detalleYNombreRango=(NTupla)modelo.getValueAt(indiceDetalle, 0);
           DetalleSubObraXTarea dsoxt=(DetalleSubObraXTarea) detalleYNombreRango.getData();
           listaDetallesTarea.add(dsoxt);
        }
       tareaActual.setDetalles(listaDetallesTarea);
              
       boolean modificada=false;
       if(idTarea>0)
       {modificada=true;}
       
       //en este caso es o nueva o modificada (opciones mutuamente excluyentes. Pero se da el caso donde no es ni nueva ni modificada, falso en ambos casos, cuando se agrega una tarea vieja a la lista)
       pantallaPadre.agregarTareaTabla(tareaActual, !modificada, modificada);
       this.dispose();
   }
}//GEN-LAST:event_btnAceptarActionPerformed
///********
////////////////////////////////////////////////


private void txtCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostoFocusLost
calcularSubtotalDetalle(validarDatos(false));
}//GEN-LAST:event_txtCostoFocusLost

private void txtHoras100FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoras100FocusLost
calcularSubtotalDetalle(validarDatos(false));
}//GEN-LAST:event_txtHoras100FocusLost

private void txtPersonasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPersonasFocusLost
calcularSubtotalDetalle(validarDatos(false));
}//GEN-LAST:event_txtPersonasFocusLost

private void txtHoras50FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoras50FocusLost
calcularSubtotalDetalle(validarDatos(false));
}//GEN-LAST:event_txtHoras50FocusLost

private void txtHorasNormalesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorasNormalesFocusLost
calcularSubtotalDetalle(validarDatos(false));
}//GEN-LAST:event_txtHorasNormalesFocusLost

private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed

    if(validarDatos(true))
    {    
        DetalleSubObraXTarea detalleNuevo;
        detalleNuevo=pedirAlGestorNuevoDetalleSegunDatosNuevos();
        agregarDetalleTareaATabla(detalleNuevo);
        mostrarTotalTarea();
    }       
}//GEN-LAST:event_btnAgregarDetalleActionPerformed

private DetalleSubObraXTarea pedirAlGestorNuevoDetalleSegunDatosNuevos()
{
       double hsNormales=Double.parseDouble(txtHorasNormales.getText().replace(",", "."));
       double hs50=Double.parseDouble(txtHoras50.getText().replace(",", "."));
       double hs100 =Double.parseDouble(txtHoras100.getText().replace(",", "."));
       int cantidadPersonas=Integer.parseInt(txtPersonas.getText());
       double costoNormal =Double.parseDouble(txtCosto.getText().replace(",", "."));
       int rangoEmpleado =((NTupla)cboRango.getSelectedItem()).getId();
       DetalleSubObraXTarea detalleNuevo=gestor.crearDetalleTarea(hsNormales, hs50, hs100, cantidadPersonas, costoNormal, rangoEmpleado);
       return  detalleNuevo;
}

private void agregarDetalleTareaATabla(DetalleSubObraXTarea detalleTarea) //throws Exception
{
    
       Object[] datos=new Object[7];     
       NTupla detalleYNombreRango=new NTupla();
       detalleYNombreRango.setNombre(detalleTarea.getRangoEmpleado().getNombre());
       detalleYNombreRango.setData(detalleTarea);
       datos[0]=detalleYNombreRango;
       datos[1]=detalleTarea.getCantidadPersonas();
       datos[2]=detalleTarea.getCantHorasNormales();
       datos[3]=detalleTarea.getCantHorasAl50();
       datos[4]=detalleTarea.getCantHorasAl100();
       datos[5]=detalleTarea.calcularSubtotal();
       
       DefaultTableModel modelo = (DefaultTableModel) tblDetallesTarea.getModel();
       /* if(!detalleModificado)
       {*/
           modelo.addRow(datos);
       /*}*/
       /*else
       {         
           int indiceFila=-1;
           for (int j = 0; j < modelo.getRowCount() ; j++) 
           {
               if(((NTupla)modelo.getValueAt(j,0)).getId()==detalleTarea.getId())
               {
                   indiceFila=j;
               }               
           }
           if((modelo.getColumnCount()==datos.length))
           {
               for (int i = 0; i < modelo.getColumnCount() ; i++) 
               {
                modelo.setValueAt(datos[i], indiceFila, i);  
               }
           }
           else
           {
               Exception wtf=new Exception("Excepcion imposible!!!");
               throw wtf; 
           }
       }       
       if(detalleNueva|| detallemodificado)
       {
        mostrarTotalTarea();
       }
       */
}

private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
    if(tblDetallesTarea.getSelectedRow()!=-1)
    {
        if(JOptionPane.showConfirmDialog(this.getParent(), "¿Está seguro de eliminar este fila?", "Eliminar fila", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
           //int idT=((NTupla)((DefaultTableModel)tblDetallesTarea.getModel()).getValueAt(tblDetallesTarea.getSelectedRow(), 0)).getId();
           ((DefaultTableModel)tblDetallesTarea.getModel()).removeRow(tblDetallesTarea.getSelectedRow());
            mostrarTotalTarea();
        }
            
        //id=((Tupla)(tblTareas.getModel().getValueAt(tblTareas.getSelectedRow(), 0))).getId(); 
    }
}//GEN-LAST:event_btnQuitarDetalleActionPerformed

private void mostrarTotalTarea()
{
  DecimalFormat df =  new DecimalFormat("0.00");
  String total=String.valueOf(calcularTotalTarea());
  txtSubtotal.setText(df.format(total).replace(".",","));
}

private double calcularTotalTarea()
{
    double totalTarea=0.0;
    DefaultTableModel modelo = (DefaultTableModel) tblDetallesTarea.getModel();
    for (int i = 0; i < modelo.getRowCount(); i++) 
    {
       
       NTupla detalleYNombreRango=(NTupla)modelo.getValueAt(i, 0);
       totalTarea+=((DetalleSubObraXTarea)detalleYNombreRango.getData()).calcularSubtotal();
    }
    return totalTarea;
}


private void txtSubtotalDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalDetalleActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtSubtotalDetalleActionPerformed

private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtNombreActionPerformed
      


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnAgregarTipoDeTarea;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JButton btnSetearCostoRango;
    private javax.swing.JComboBox cboRango;
    private javax.swing.JComboBox cboTareas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTable tblDetallesTarea;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JFormattedTextField txtCosto;
    private javax.swing.JFormattedTextField txtHoras100;
    private javax.swing.JFormattedTextField txtHoras50;
    private javax.swing.JFormattedTextField txtHorasNormales;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtPersonas;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtSubtotalDetalle;
    // End of variables declaration//GEN-END:variables

    
}
