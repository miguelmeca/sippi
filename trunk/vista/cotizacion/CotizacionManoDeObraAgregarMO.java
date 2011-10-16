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

/**
 *
 * @author Iuga
 */
public class CotizacionManoDeObraAgregarMO extends javax.swing.JInternalFrame {
    private GestorCotizacionManoDeObra gestor;
    private CotizacionManoDeObraGeneral pantallaPadre;
    private int indiceFilaModificada;
    private int idTarea;

    /** Creates new form editarCotizacion_ManoDeObra_OpcionB_parte2Fix */
    public CotizacionManoDeObraAgregarMO(CotizacionManoDeObraGeneral pant, GestorCotizacionManoDeObra gestorX) 
    {
        pantallaPadre=pant;
        gestor=gestorX;
        initComponents();
        indiceFilaModificada=-1;
        idTarea=-1;
        habiliarVentana();        
    }
     
    
    public void habiliarVentana()
    {
        cargarCboTareas();
        cargarCboRangos();
        jdcFInicio.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFInicioFocusLost(evt);
            }
        });
        jdcFFin.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFFinFocusLost(evt);
            }
        });
        txtCosto.addKeyListener(Validaciones.getKaNumeros());
        txtHoras.addKeyListener(Validaciones.getKaNumeros());
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
        cboTareas.setSelectedIndex(-1);   
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
        cboRango.setSelectedIndex(-1);   
    }
    public void tomarValoresDeDatos(Object[] datos, int indiceFila)
    {
        indiceFilaModificada=indiceFila;
        idTarea=((NTupla)datos[0]).getId();
        for (int i = 0; i < cboTareas.getItemCount(); i++) 
        {
            if(((NTupla)cboTareas.getItemAt(i)).getId()==(((Tupla)((Object[])((NTupla)datos[0]).getData())[0]).getId()))//Que Quilombo!
            {
                cboTareas.setSelectedIndex(i);
            }            
        }
       txaObservaciones.setText(((String)((Object[])((NTupla)datos[0]).getData())[1]));
       txtPersonas.setText(((Integer)datos[1]).toString());
       for (int i = 0; i < cboRango.getItemCount(); i++) 
        {
            if(((NTupla)cboRango.getItemAt(i)).getId()==(((NTupla)datos[2]).getId()) )
            {
                cboRango.setSelectedIndex(i);
            }            
        }
       txtCosto.setText(((Double)((NTupla)datos[2]).getData()).toString().replace(".", ","));
       txtHoras.setText(((Double)datos[3]).toString().replace(".", ","));
       jdcFInicio.setDate((Date)((NTupla)datos[4]).getData());
       jdcFFin.setDate((Date)((NTupla)datos[5]).getData());       
       calcularSubtotal(true);//calcularSubtotal(validarDatos(false));
    }
    
    public void calcularSubtotal(boolean ok)
    {
       if(ok)
       {  long cantDias=FechaUtil.diasDiferencia(jdcFInicio.getDate(), jdcFFin.getDate());//(jdcFFin.getDate().getTime()-jdcFInicio.getDate().getTime())/(1000*60*60*24);
           
           //double subT=Double.parseDouble(txtPersonas.getText())*(cantDias+1)*Double.parseDouble(txtCosto.getText())*Double.parseDouble(txtHoras.getText());
           double subT=Double.parseDouble(txtPersonas.getText())*(cantDias+1)*Double.parseDouble(txtCosto.getText().replace(",", "."))*Double.parseDouble(txtHoras.getText().replace(",", "."));
           //txtSubtotal.setText(Double.toString(subT));
           txtSubtotal.setText(Double.toString(subT).replace(".",","));
       }
       else
       {
           txtSubtotal.setText("");
       }
          
    }
    
    public boolean validarDatos(boolean mostrarErrores)
    {
       if(cboTareas.getSelectedIndex()<0)
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Seleccione una tarea", "Error",JOptionPane.ERROR_MESSAGE);
          cboTareas.requestFocusInWindow();}
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
       if(!Validaciones.validarNumeroPositivo(txtHoras.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas por día ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHoras.requestFocusInWindow();}
          return false;          
       }
       
        if(jdcFInicio.getDate()==null)        
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
       }
       
       if(mostrarErrores)
       { 
           
           if(jdcFInicio.getDate().before(FechaUtil.getToday(new Date())))        
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
           }
       }
       
       if(FechaUtil.diasDiferencia(jdcFInicio.getDate(), jdcFFin.getDate())<0)
       {    if(mostrarErrores)
           {    JOptionPane.showMessageDialog(this.getParent(), "La fecha de finalización fin de la tarea no puede ser mayor a la fecha de inicio", "Error",JOptionPane.ERROR_MESSAGE);
                jdcFFin.requestFocusInWindow();
            }
       
           return false ;
       }
       return true;
        
    }

    
   
    /////////////////////////////////////////////
    
    
    
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
        jdcFInicio = new com.toedter.calendar.JDateChooser();
        jdcFFin = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboRango = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        btnSetearCostoRango = new javax.swing.JButton();
        txtCosto = new javax.swing.JFormattedTextField();
        txtHoras = new javax.swing.JFormattedTextField();
        txtPersonas = new javax.swing.JFormattedTextField();
        txtSubtotal = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cboTareas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Agregar Mano de Obra");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Cantidad de Personas");

        jLabel4.setText("Hs/Día");

        jLabel7.setText("Costo/Hora");

        jdcFInicio.setEnabled(false);
        jdcFInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdcFInicioMouseClicked(evt);
            }
        });
        jdcFInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFInicioFocusLost(evt);
            }
        });
        jdcFInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcFInicioPropertyChange(evt);
            }
        });

        jdcFFin.setEnabled(false);
        jdcFFin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jdcFFinFocusLost(evt);
            }
        });
        jdcFFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcFFinPropertyChange(evt);
            }
        });

        jLabel12.setText("Fecha Inicio");

        jLabel13.setText("Fecha Fin");

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

        btnSetearCostoRango.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/tag_blue.png"))); // NOI18N
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

        txtHoras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.##"))));
        txtHoras.setEnabled(false);
        txtHoras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorasFocusLost(evt);
            }
        });

        txtPersonas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        txtPersonas.setEnabled(false);
        txtPersonas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPersonasFocusLost(evt);
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
                            .addComponent(jLabel3)
                            .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5))
                            .addComponent(cboRango, 0, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSetearCostoRango, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(40, 40, 40))
                            .addComponent(jdcFInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(64, 64, 64))
                            .addComponent(jdcFFin, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboRango)
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSetearCostoRango, 0, 0, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdcFFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel8.setText("Subtotal");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarea/ Observaciones"));

        cboTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTareasActionPerformed(evt);
            }
        });

        txaObservaciones.setColumns(20);
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        jScrollPane1.setViewportView(txaObservaciones);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cboTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(23, Short.MAX_VALUE))
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

    private void cboTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTareasActionPerformed
       if(cboTareas.getSelectedIndex()!=-1)
       { 
        NTupla tarea=(NTupla)cboTareas.getModel().getSelectedItem();
        //txtHoras.setText((String)((Object[])tarea.getData())[0]);
       //txtPersonas.setText((String)((Object[])tarea.getData())[1]);
        txtHoras.setText(((String)((Object[])tarea.getData())[0]).replace(".", ","));
        txtPersonas.setText(((String)((Object[])tarea.getData())[1]).replace(".", ",")); 
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
       txtHoras.setEnabled(true);
       jdcFInicio.setEnabled(true);
       jdcFFin.setEnabled(true);
       
        }
       else
       {txtPersonas.setEnabled(false);
       cboRango.setEnabled(false);
       txtHoras.setEnabled(false);
       jdcFInicio.setEnabled(false);
       jdcFFin.setEnabled(false);
       btnSetearCostoRango.setEnabled(false);}
       
    }//GEN-LAST:event_cboTareasActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

private void cboRangoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRangoItemStateChanged

}//GEN-LAST:event_cboRangoItemStateChanged

private void cboRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRangoActionPerformed
if(cboRango.getSelectedIndex()!=-1)
{
    //txtCosto.setText(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData()));
    txtCosto.setText(Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData()).replace(".",","));
    txtCosto.setEnabled(true);
}    
else
{txtCosto.setText("");}
}//GEN-LAST:event_cboRangoActionPerformed

private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
   if(validarDatos(true))
   {
       calcularSubtotal(true);
       Object[] datos=new Object[7];
       NTupla tar=new NTupla(idTarea); //Id de la tarea (SubObraXTarea) todavía no seteado
       tar.setNombre(((NTupla)cboTareas.getSelectedItem()).getNombre());
       Object[] datosTarea=new Object[2];       
           Tupla tipoTar=new Tupla(((NTupla)cboTareas.getSelectedItem()).getId(), ((NTupla)cboTareas.getSelectedItem()).getNombre());
           datosTarea[0]=tipoTar;
           datosTarea[1]=txaObservaciones.getText();//Mierda q quilombo!!!
       tar.setData(datosTarea);       
       datos[0]=(tar); 
       datos[1]=Integer.parseInt(txtPersonas.getText()); 
       NTupla nvoRango=new NTupla( ((NTupla)cboRango.getSelectedItem()).getId());
       nvoRango.setNombre(((NTupla)cboRango.getSelectedItem()).getNombre());
       //nvoRango.setData(Double.parseDouble(txtCosto.getText()));
       nvoRango.setData(Double.parseDouble(txtCosto.getText().replace(",", ".")));
       datos[2]=nvoRango;
       //datos[3]=Double.parseDouble(txtHoras.getText()); 
       datos[3]=Double.parseDouble(txtHoras.getText().replace(",", "."));
       NTupla tFI=new NTupla(0);
       tFI.setNombre(FechaUtil.getFecha(jdcFInicio.getDate()));
       tFI.setData(jdcFInicio.getDate());
       datos[4]=tFI;
       NTupla tFF=new NTupla(0);
       tFF.setNombre(FechaUtil.getFecha(jdcFFin.getDate()));
       tFF.setData(jdcFFin.getDate());
       datos[5]=tFF;
       //datos[6]=Double.parseDouble(txtSubtotal.getText());
       datos[6]=Double.parseDouble(txtSubtotal.getText().replace(",", "."));
       boolean modificada=false;
       //if(indiceFilaModificada>0)
       if(idTarea>0)
       {modificada=true;}
      // pantallaPadre.agregarTarea(datos, !modificada, modificada,indiceFilaModificada);
       pantallaPadre.agregarTarea(datos, !modificada, modificada);
       this.dispose();
   }
}//GEN-LAST:event_btnAceptarActionPerformed

private void jdcFInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jdcFInicioFocusLost
  calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_jdcFInicioFocusLost

private void jdcFFinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jdcFFinFocusLost
calcularSubtotal(validarDatos(false));   
}//GEN-LAST:event_jdcFFinFocusLost

private void jdcFInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdcFInicioMouseClicked

}//GEN-LAST:event_jdcFInicioMouseClicked

private void txtCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostoFocusLost
calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_txtCostoFocusLost

private void txtHorasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorasFocusLost
calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_txtHorasFocusLost

private void txtPersonasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPersonasFocusLost
calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_txtPersonasFocusLost

private void jdcFInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcFInicioPropertyChange
calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_jdcFInicioPropertyChange

private void jdcFFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcFFinPropertyChange
calcularSubtotal(validarDatos(false));
}//GEN-LAST:event_jdcFFinPropertyChange
      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSetearCostoRango;
    private javax.swing.JComboBox cboRango;
    private javax.swing.JComboBox cboTareas;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcFFin;
    private com.toedter.calendar.JDateChooser jdcFInicio;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JFormattedTextField txtCosto;
    private javax.swing.JFormattedTextField txtHoras;
    private javax.swing.JFormattedTextField txtPersonas;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
