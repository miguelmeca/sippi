/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CotizacionManoDeObraAgregar.java
 *
 * Created on 28/01/2012, 18:46:51
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
    private int hashCodeTarea;
    //private List<DetalleSubObraXTarea> listaDetallesTarea;
    private SubObraXTarea tareaActual;
    
    /** Creates new form CotizacionManoDeObraAgregarMOXXX */
    public CotizacionManoDeObraAgregarMO(CotizacionManoDeObraGeneral pant, GestorCotizacionManoDeObra gestorX) 
    {
        pantallaPadre=pant;
        gestor=gestorX;
        initComponents();
        indiceFilaModificada=-1;
        idTarea=-1;
        hashCodeTarea=-1;
        habiliarVentana();   
        tareaActual=new SubObraXTarea();
        this.setTitle("Nueva Tarea");
        //listaDetallesTarea=new ArrayList<DetalleSubObraXTarea>();
        habilitarCamposDatosDetalleTarea(false);
    }
    public void habiliarVentana()
    {
        cargarCboTareas();
        cargarCboRangos();   
        cargarCboTipoEspecialidad();
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
    public void cargarCboTipoEspecialidad()
    {
        ArrayList<Tupla> listaTipoEspecialidad = gestor.mostrarTiposEspecialidad();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listaTipoEspecialidad!=null && !listaTipoEspecialidad.isEmpty())
        for (Tupla te : listaTipoEspecialidad)
        {
            model.addElement(te);
        }
        cboTipoEspecialidad.setModel(model);
        Tupla t0 = new Tupla(-1,"Seleccione una especialidad...");
        cboTipoEspecialidad.insertItemAt(t0, 0);
        cboTipoEspecialidad.setSelectedIndex(0);   
    }
    
    
    public void tomarValoresDeDatos(SubObraXTarea tarea, int indiceFila)
    {
        this.setTitle("Modificar Tarea");
        tareaActual=tarea;
        indiceFilaModificada=indiceFila;
        hashCodeTarea=tarea.hashCode();
        idTarea=tarea.getId();
        for (int i = 0; i < cboTareas.getItemCount(); i++) 
        {
            if( (((NTupla)cboTareas.getItemAt(i)).getId()) == tarea.getTipoTarea().getId())
            {
                cboTareas.setSelectedIndex(i);
                break;
            }            
        }
        
       txtNombre.setText(tarea.getNombre());
       txaObservaciones.setText(tarea.getObservaciones());
        for (DetalleSubObraXTarea detalle:tarea.getDetalles()) {
            agregarDetalleTareaATabla(detalle);
        }       
       habilitarCamposDatosDetalleTarea(true);
      
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
           DecimalFormat df =  new DecimalFormat("0.00");
           txtSubtotalDetalle.setText(df.format(subT).replace(".",","));
       }
       else
       {
           txtSubtotalDetalle.setText("");
       }
          
    }
    
    public boolean validarDatosDetalle(boolean mostrarErrores)
    {
      
      if(((NTupla)cboRango.getSelectedItem()).getId()<0)
       {  
         if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Seleccione un rango", "Error",JOptionPane.ERROR_MESSAGE);
          cboRango.requestFocusInWindow();}
          return false;          
       }
      if(mostrarErrores)
      {
        if(((Tupla)cboTipoEspecialidad.getSelectedItem()).getId()<0)
        {  
          JOptionPane.showMessageDialog(this.getParent(), "Seleccione un especialidad", "Error",JOptionPane.ERROR_MESSAGE);
          cboTipoEspecialidad.requestFocusInWindow();
          return false;          }
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
       if(txtHorasNormales.getText()=="")
       {
           txtHorasNormales.setText("0");
       }
       if(txtHoras50.getText()=="")
       {
           txtHoras50.setText("0");
       }
       if(txtHoras100.getText()=="")
       {
           txtHoras100.setText("0");
       }
       
       if(!Validaciones.validarNumeroPositivoOCero(txtHoras100.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas al 100% por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHoras100.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivoOCero(txtHorasNormales.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas normales por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHorasNormales.requestFocusInWindow();}
          return false;          
       }
       if(!Validaciones.validarNumeroPositivoOCero(txtHoras50.getText().replace( ',','.' )))
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las horas al 50% por persona ingresadas no son válidas", "Error",JOptionPane.ERROR_MESSAGE);
          txtHoras50.requestFocusInWindow();}
          return false;          
       }
       double sumaHoras=Double.parseDouble(txtHorasNormales.getText())+Double.parseDouble(txtHoras50.getText())+Double.parseDouble(txtHoras100.getText());
       if(sumaHoras<=0)
       {  
           if(mostrarErrores)
           {JOptionPane.showMessageDialog(this.getParent(), "Las suma de horas debe ser mayor a cero", "Error",JOptionPane.ERROR_MESSAGE);
          txtHorasNormales.requestFocusInWindow();}
          return false;          
       }
        
       return true;
        
    }

   
    public boolean validarDatos()
    {
      if(txtNombre.getText().isEmpty())
       {  
        // if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Ingrese un nombre para la tarea", "Error",JOptionPane.ERROR_MESSAGE);
          txtNombre.requestFocusInWindow();}
          return false;          
       }
        if(((NTupla)cboTareas.getSelectedItem()).getId()<0)
       {  
        // if(mostrarErrores)
         { JOptionPane.showMessageDialog(this.getParent(), "Seleccione una tarea", "Error",JOptionPane.ERROR_MESSAGE);
          cboTareas.requestFocusInWindow();}
          return false;          
       }      
       
       return true;
        
    }
    
private void limpiarCamposDetalle()
{
    txtPersonas.setText("");
    txtCosto.setText("");
    txtHorasNormales.setText("");
    txtHoras50.setText("");
    txtHoras100.setText("");
    txtSubtotalDetalle.setText("");
    btnSetearCostoRango.setEnabled(false);
    txtCosto.setEnabled(false);
    cargarCboRangos();
    cargarCboTipoEspecialidad();
}
    
private DetalleSubObraXTarea pedirAlGestorNuevoDetalleSegunDatosNuevos()
{
       double hsNormales=Double.parseDouble(txtHorasNormales.getText().replace(",", "."));
       double hs50=Double.parseDouble(txtHoras50.getText().replace(",", "."));
       double hs100 =Double.parseDouble(txtHoras100.getText().replace(",", "."));
       int cantidadPersonas=Integer.parseInt(txtPersonas.getText());
       double costoNormal =Double.parseDouble(txtCosto.getText().replace(",", "."));
       int rangoEmpleado =((NTupla)cboRango.getSelectedItem()).getId();
       int tipoEspecialidad =((Tupla)cboTipoEspecialidad.getSelectedItem()).getId();
       DetalleSubObraXTarea detalleNuevo=gestor.crearDetalleTarea(hsNormales, hs50, hs100, cantidadPersonas, costoNormal, rangoEmpleado, tipoEspecialidad);
       return  detalleNuevo;
}

private void agregarDetalleTareaATabla(DetalleSubObraXTarea detalleTarea) //throws Exception
{
    
       Object[] datos=new Object[8];     
       NTupla detalleYNombreRango=new NTupla();
       detalleYNombreRango.setNombre(detalleTarea.getRangoEmpleado().getNombre());
       detalleYNombreRango.setData(detalleTarea);
       datos[0]=detalleYNombreRango;
       Tupla nombreTipoEspecialidad=new Tupla();
       nombreTipoEspecialidad.setNombre(detalleTarea.getTipoEspecialidad().getNombre());
       datos[1]=nombreTipoEspecialidad;
       datos[2]=detalleTarea.getCantidadPersonas();
       datos[3]=detalleTarea.getCostoXHoraNormal();
       datos[4]=detalleTarea.getCantHorasNormales();
       datos[5]=detalleTarea.getCantHorasAl50();
       datos[6]=detalleTarea.getCantHorasAl100();
       datos[7]=detalleTarea.calcularSubtotal();
       
       DefaultTableModel modelo = (DefaultTableModel) tblDetallesTarea.getModel();
       
           modelo.addRow(datos);
       
}

private void mostrarTotalTarea()
{
  DecimalFormat df =  new DecimalFormat("0.00");
  txtSubtotal.setText(df.format(calcularTotalTarea()).replace(".",","));
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        cboTareas = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        btnAgregarTipoDeTarea = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jpDatosDetalleTarea = new javax.swing.JPanel();
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
        jLabel11 = new javax.swing.JLabel();
        cboTipoEspecialidad = new javax.swing.JComboBox();
        btnAgregarDetalle = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetallesTarea = new javax.swing.JTable();
        btnQuitarDetalle = new javax.swing.JButton();
        btnEditarDetalle = new javax.swing.JButton();

        setTitle("Tarea");

        jLabel8.setText("Total de la tarea   $");

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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Tarea"));

        jLabel6.setText("Nombre:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        cboTareas.setName(""); // NOI18N
        cboTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTareasActionPerformed(evt);
            }
        });

        jLabel10.setText("Tipo:");

        txaObservaciones.setColumns(20);
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        jScrollPane1.setViewportView(txaObservaciones);

        btnAgregarTipoDeTarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregarTipoDeTarea.setToolTipText("Agreagar un nuevo tipo de tarea");
        btnAgregarTipoDeTarea.setPreferredSize(new java.awt.Dimension(49, 49));
        btnAgregarTipoDeTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTipoDeTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cboTareas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAgregarTipoDeTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombre))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTareas)
                        .addComponent(jLabel10))
                    .addComponent(btnAgregarTipoDeTarea, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de la tarea"));

        jpDatosDetalleTarea.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Cantidad de Personas");

        jLabel4.setText("<html>Cantidad de horas<br>al 100% por persona</html>");

        jLabel7.setText("Costo/Hora");

        jLabel12.setText("<html>Cantidad de horas<br>normales por persona</html>");

        jLabel13.setText("<html>Cantidad de horas<br>al 50% por persona</html>");

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

        txtHoras100.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoras100FocusLost(evt);
            }
        });

        txtPersonas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPersonasFocusLost(evt);
            }
        });

        txtHoras50.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoras50FocusLost(evt);
            }
        });

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

        jLabel11.setText("Especialidad de empleados");

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

        javax.swing.GroupLayout jpDatosDetalleTareaLayout = new javax.swing.GroupLayout(jpDatosDetalleTarea);
        jpDatosDetalleTarea.setLayout(jpDatosDetalleTareaLayout);
        jpDatosDetalleTareaLayout.setHorizontalGroup(
            jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHorasNormales, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoras50, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoras100))
                .addGap(43, 43, 43)
                .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSubtotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboRango, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboTipoEspecialidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSetearCostoRango, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jpDatosDetalleTareaLayout.setVerticalGroup(
            jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboRango))
                            .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSetearCostoRango, 0, 0, Short.MAX_VALUE)
                                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHorasNormales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoras50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoras100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosDetalleTareaLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jpDatosDetalleTareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtSubtotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        btnAgregarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        tblDetallesTarea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rango", "Especialidad", "Personas", "Costo Hora", "Hs Normales", "Hs 50%", "Hs 100%", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetallesTarea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDetallesTarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDetallesTareaMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetallesTarea);

        btnQuitarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.setEnabled(false);
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        btnEditarDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/arrow_large_up.png"))); // NOI18N
        btnEditarDetalle.setText("Editar");
        btnEditarDetalle.setEnabled(false);
        btnEditarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnAgregarDetalle)
                        .addGap(46, 46, 46)
                        .addComponent(btnEditarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(btnQuitarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 57, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpDatosDetalleTarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jpDatosDetalleTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitarDetalle)
                    .addComponent(btnAgregarDetalle)
                    .addComponent(btnEditarDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancelar)
                        .addComponent(btnAceptar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtSubtotalActionPerformed

private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
   if(validarDatos())
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
       if(hashCodeTarea>0)//Nunca seteo el valor hashCodeTarea
       {modificada=true;}
       
       //en este caso es o nueva o modificada (opciones mutuamente excluyentes. Pero se da el caso donde no es ni nueva ni modificada, falso en ambos casos, cuando se agrega una tarea vieja a la lista)
       pantallaPadre.agregarTareaTabla(tareaActual, !modificada, modificada);
       this.dispose();
   }
}//GEN-LAST:event_btnAceptarActionPerformed

private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtNombreActionPerformed

private void cboTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTareasActionPerformed
       if(((NTupla)cboTareas.getSelectedItem()).getId()!=-1)
       { 
          if(((NTupla)cboTareas.getItemAt(0)).getId()==-1)
           {
            cboTareas.removeItemAt(0);
           }
        NTupla tarea=(NTupla)cboTareas.getModel().getSelectedItem();
        
        
       habilitarCamposDatosDetalleTarea(true);
        }
       else
       {habilitarCamposDatosDetalleTarea(false);}
       
}//GEN-LAST:event_cboTareasActionPerformed

private void habilitarCamposDatosDetalleTarea(boolean habilitar)
{
    txtPersonas.setEnabled(habilitar);
       cboRango.setEnabled(habilitar);
       cboTipoEspecialidad.setEnabled(habilitar);
       txtHorasNormales.setEnabled(habilitar);
       txtHoras50.setEnabled(habilitar);
       txtHoras100.setEnabled(habilitar);  
       if(!habilitar)//no habilitar si es true. Solo deshabilitar si es false.
       {btnSetearCostoRango.setEnabled(habilitar);
       txtCosto.setEnabled(habilitar);}
}

private void btnAgregarTipoDeTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTipoDeTareaActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_btnAgregarTipoDeTareaActionPerformed

private void cboRangoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRangoItemStateChanged

}//GEN-LAST:event_cboRangoItemStateChanged

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
    calcularSubtotalDetalle(validarDatosDetalle(false));
}    
else
{txtCosto.setText("");}
}//GEN-LAST:event_cboRangoActionPerformed

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

private void txtCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostoFocusLost
calcularSubtotalDetalle(validarDatosDetalle(false));

    
}//GEN-LAST:event_txtCostoFocusLost

private void txtHoras100FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoras100FocusLost
calcularSubtotalDetalle(validarDatosDetalle(false));
}//GEN-LAST:event_txtHoras100FocusLost

private void txtPersonasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPersonasFocusLost
calcularSubtotalDetalle(validarDatosDetalle(false));
}//GEN-LAST:event_txtPersonasFocusLost

private void txtHoras50FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoras50FocusLost
calcularSubtotalDetalle(validarDatosDetalle(false));
}//GEN-LAST:event_txtHoras50FocusLost

private void txtHorasNormalesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorasNormalesFocusLost
calcularSubtotalDetalle(validarDatosDetalle(false));
}//GEN-LAST:event_txtHorasNormalesFocusLost

private void txtSubtotalDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalDetalleActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtSubtotalDetalleActionPerformed

private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed

    if(validarDatosDetalle(true))
    {    
        DetalleSubObraXTarea detalleNuevo;
        detalleNuevo=pedirAlGestorNuevoDetalleSegunDatosNuevos();
        agregarDetalleTareaATabla(detalleNuevo);
        mostrarTotalTarea();
        limpiarCamposDetalle();
    }       
}//GEN-LAST:event_btnAgregarDetalleActionPerformed

private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
    if(tblDetallesTarea.getSelectedRow()!=-1)
    {
        if(JOptionPane.showConfirmDialog(this.getParent(), "¿Está seguro de eliminar este fila?", "Eliminar fila", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
          ((DefaultTableModel)tblDetallesTarea.getModel()).removeRow(tblDetallesTarea.getSelectedRow());
            mostrarTotalTarea();
            habilitarBotonesParaTablaDetalle(false);
        }
            
      
    }
}//GEN-LAST:event_btnQuitarDetalleActionPerformed

private void btnEditarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDetalleActionPerformed
if(tblDetallesTarea.getSelectedRow()!=-1)
    {
       DefaultTableModel modelo=(DefaultTableModel)tblDetallesTarea.getModel();
       DetalleSubObraXTarea detalleTarea=(DetalleSubObraXTarea)((NTupla)modelo.getValueAt(tblDetallesTarea.getSelectedRow(), 0)).getData();
       txtPersonas.setText(String.valueOf(detalleTarea.getCantidadPersonas()));
       txtCosto.setText(String.valueOf(detalleTarea.getCostoXHoraNormal()));
       
       for (int i = 0; i < cboRango.getItemCount(); i++) 
        {
            if( (((NTupla)cboRango.getItemAt(i)).getId()) == detalleTarea.getRangoEmpleado().getId())
            {
                cboRango.setSelectedIndex(i);
                break;
            }            
        }
       for (int i = 0; i < cboTipoEspecialidad.getItemCount(); i++) 
        {
            if( (((Tupla)cboTipoEspecialidad.getItemAt(i)).getId()) == detalleTarea.getTipoEspecialidad().getId())
            {
                cboTipoEspecialidad.setSelectedIndex(i);
                break;
            }            
        }       
       txtHorasNormales.setText(String.valueOf(detalleTarea.getCantHorasNormales()));
       txtHoras50.setText(String.valueOf(detalleTarea.getCantHorasAl50()));
       txtHoras100.setText(String.valueOf(detalleTarea.getCantHorasAl100())); 
       calcularSubtotalDetalle(true);
       ((DefaultTableModel)tblDetallesTarea.getModel()).removeRow(tblDetallesTarea.getSelectedRow());
        mostrarTotalTarea();
        habilitarBotonesParaTablaDetalle(false);
        
    }
}//GEN-LAST:event_btnEditarDetalleActionPerformed

private void tblDetallesTareaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesTareaMousePressed
habilitarBotonesParaTablaDetalle(true);
}//GEN-LAST:event_tblDetallesTareaMousePressed

    private void cboTipoEspecialidadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipoEspecialidadItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoEspecialidadItemStateChanged

    private void cboTipoEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoEspecialidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoEspecialidadActionPerformed

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
        if(Validaciones.validarNumeroPositivo(txtCosto.getText().replace( ',','.' )))
        {   String costo=Double.toString( (Double)((NTupla)cboRango.getModel().getSelectedItem()).getData()).replace(".",",");
            if(txtCosto.getText().equals(costo))
            {
                btnSetearCostoRango.setEnabled(false);
            }
            else
            {
                btnSetearCostoRango.setEnabled(true);
            }
        }
        else
        {
            btnSetearCostoRango.setEnabled(false);
        }
    }//GEN-LAST:event_txtCostoKeyReleased
private void habilitarBotonesParaTablaDetalle(boolean habilitar)
{
    if(tblDetallesTarea.getSelectedRow()!=-1)
    {
        btnQuitarDetalle.setEnabled(habilitar);
        btnEditarDetalle.setEnabled(habilitar);
    }
else
    {
        btnQuitarDetalle.setEnabled(false);
        btnEditarDetalle.setEnabled(false);
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnAgregarTipoDeTarea;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditarDetalle;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JButton btnSetearCostoRango;
    private javax.swing.JComboBox cboRango;
    private javax.swing.JComboBox cboTareas;
    private javax.swing.JComboBox cboTipoEspecialidad;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpDatosDetalleTarea;
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
