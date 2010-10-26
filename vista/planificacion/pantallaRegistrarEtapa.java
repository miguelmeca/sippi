/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pantallaRegistrarEtapa.java
 *
 * Created on 13-sep-2010, 17:41:19
 */

package vista.planificacion;

import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import util.Tupla;
import util.NTupla;
import java.util.List;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import controlador.planificacion.GestorOtrosDatosEtapa;
import controlador.planificacion.GestorRegistrarEtapa;
import javax.swing.JOptionPane;
import vista.interfaces.ICallBack_v2;
import util.SwingPanel;
import vista.gui.graphProxy.SystemEventProxy;

/**
 *
 * @author Administrador
 */
public class pantallaRegistrarEtapa extends javax.swing.JInternalFrame implements ICallBack_v2{


    private int idEtapa;
    private int idPresupuesto;
    private GestorOtrosDatosEtapa gestorOtrosDatosEtapa;
    private GestorRegistrarEtapa gestorRegistrarEtapa;
    private List<NTupla> listaTareas;

    /** Creates new form pantallaRegistrarEtapa */
    public pantallaRegistrarEtapa(int idEtapa,int idPresupuesto)
    {
        this.idEtapa = idEtapa;
        this.idPresupuesto = idPresupuesto;

        initComponents();
        gestorOtrosDatosEtapa = new GestorOtrosDatosEtapa(this);
        
        gestorRegistrarEtapa = new GestorRegistrarEtapa(this);
        gestorRegistrarEtapa.cargarEtapa(idEtapa);

        habilitarVentana();

    }

    private void habilitarVentana()
    {
        cmbFechaFin.setDate(new Date());
        cmbFechaInicio.setDate(new Date());
        cargarCombosTransportYAlojamiento();
       // cargarCombosEmpresas();
        gestorRegistrarEtapa.mostrarDatosEtapa();
        cargarTareas();
        cargarEtapas();
        habilitarBotonesTarea();
    }

    public void mostrarDatosEtapa(String nombre, Date fechaInicio, Date fechaFin, String obs)
    {
      txtNombre.setText(nombre);
      cmbFechaInicio.setDate(fechaInicio);
      cmbFechaFin.setDate(fechaFin);
      txtObservaciones.setText(obs);
    }

    private void cargarEtapas()
    {
        DefaultTableModel model = (DefaultTableModel) tablaEtapas.getModel();
        Iterator<NTupla> it = gestorRegistrarEtapa.getListaEtapasRelacionadas(idPresupuesto,idEtapa).iterator();
        while (it.hasNext())
        {
            NTupla nt = it.next();
            Object[] fila = new Object[2];
            fila[1] = nt;
            fila[0] = (Boolean)nt.getData();
            model.addRow(fila);
        }
    }

    private void cargarTareas()
    {
        listaTareas=gestorOtrosDatosEtapa.listaTareas(idEtapa);
        if(listaTareas!=null)
        {
            DefaultTableModel model = (DefaultTableModel) tablaTareas.getModel();
        int fil=model.getRowCount();
        for (int i = 0; i < fil; i++) {
            model.removeRow(0);
        }
        for (NTupla nTuplaTarea : listaTareas)
        {
            //Creo un nuevo array con una unidad mas d largo que el devuelto en el Data de la NTupla(Para agregar el id)
            Object[] obj=new Object[((Object[])nTuplaTarea.getData()).length+1];
            Tupla tup=new Tupla();
            tup.setId(nTuplaTarea.getId());
            tup.setNombre(nTuplaTarea.getNombre());
            obj[0]=tup;

            //Este metodo d aca abajo copia el contenido del array de Data al nuevo array obj, poniendo los datos a partir d la posicion 1
            System.arraycopy((Object[]) nTuplaTarea.getData(), 0, obj, 1, ((Object[]) nTuplaTarea.getData()).length);
            model.addRow( obj );
        }
        tablaTareas.setModel(model);
        }
        txtCostoTareas.setText(String.valueOf( gestorOtrosDatosEtapa.costoTareas(idEtapa)));


    }
    private void cargarCombosTransportYAlojamiento()
    {
       // cmbTransporteMHOrigenProvincias.setEnabled(false);
        //cmbTransporteMHOrigenLocalidades.setEnabled(false);
        //cmbTransporteMHOrigenBarrios.setEnabled(false);
        //cmbTransporteMHDestinoProvincias.setEnabled(false);
        //cmbTransporteMHDestinoLocalidades.setEnabled(false);
        //cmbTransporteMHDestinoBarrios.setEnabled(false);
       // cmbTransportePasajerosOrigenProvincias.setEnabled(false);
       // cmbTransportePasajerosOrigenLocalidades.setEnabled(false);
       /* cmbTransportePasajerosDestinoProvincias.setEnabled(false);
        cmbTransportePasajerosDestinoLocalidades.setEnabled(false);
        mostrarPaises(cmbTransporteMHOrigenPaises, cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades, cmbTransporteMHOrigenBarrios);
        mostrarProvincias(cmbTransporteMHOrigenPaises, cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades, cmbTransporteMHOrigenBarrios);
        mostrarLocalidades( cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades, cmbTransporteMHOrigenBarrios);
        mostrarBarrios( cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades, cmbTransporteMHOrigenBarrios);

        mostrarPaises(cmbTransporteMHDestinoPaises, cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades, cmbTransporteMHDestinoBarrios);
        mostrarProvincias(cmbTransporteMHDestinoPaises, cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades, cmbTransporteMHDestinoBarrios);
        mostrarLocalidades( cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades, cmbTransporteMHDestinoBarrios);
        mostrarBarrios( cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades, cmbTransporteMHDestinoBarrios);

        mostrarPaises(cmbTransportePasajerosOrigenPaises, cmbTransportePasajerosOrigenProvincias,cmbTransportePasajerosOrigenLocalidades, null);
        mostrarProvincias(cmbTransportePasajerosOrigenPaises, cmbTransportePasajerosOrigenProvincias,cmbTransportePasajerosOrigenLocalidades, null);
        mostrarLocalidades( cmbTransportePasajerosOrigenProvincias,cmbTransportePasajerosOrigenLocalidades, null);


        mostrarPaises(cmbTransportePasajerosDestinoPaises, cmbTransportePasajerosDestinoProvincias,cmbTransportePasajerosDestinoLocalidades, null);
        mostrarProvincias(cmbTransportePasajerosDestinoPaises, cmbTransportePasajerosDestinoProvincias,cmbTransportePasajerosDestinoLocalidades, null);
        mostrarLocalidades( cmbTransportePasajerosDestinoProvincias,cmbTransportePasajerosDestinoLocalidades, null);
*/
        
    }

    private void mostrarPaises(JComboBox cmbPaises, JComboBox cmbProvincias, JComboBox cmbLocalidades, JComboBox cmbBarrios)
    {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarPaises();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }

        cmbPaises.setModel(valores);
        cmbPaises.setSelectedIndex(-1);
        cmbProvincias.setModel(new DefaultComboBoxModel());
        
        cmbLocalidades.setModel(new DefaultComboBoxModel());
        cmbProvincias.setEnabled(false);
       cmbLocalidades.setEnabled(false);
       if(cmbBarrios!=null)
       {cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbBarrios.setEnabled(false);}
        //cmbLocalidades.setSelectedIndex(-1);

    }

    private void mostrarProvincias(JComboBox cmbPaises, JComboBox cmbProvincias, JComboBox cmbLocalidades, JComboBox cmbBarrios)
    {
       if(cmbPaises.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbPaises.getSelectedItem() ;
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarProvincias(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbProvincias.setModel(valores);
        cmbProvincias.setSelectedIndex(-1);
        cmbLocalidades.setModel(new DefaultComboBoxModel());        
        cmbProvincias.setEnabled(true);
        cmbLocalidades.setEnabled(false);
        if(cmbBarrios!=null)
       {cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbBarrios.setEnabled(false);}
        //cmbLocalidades.setModel(new DefaultComboBoxModel());
       }
    }

    private void mostrarLocalidades(JComboBox cmbProvincias, JComboBox cmbLocalidades, JComboBox cmbBarrios)
    {
        if(cmbProvincias.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbProvincias.getSelectedItem() ;
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarLocalidades(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbLocalidades.setModel(valores);
        cmbLocalidades.setSelectedIndex(-1);        
        cmbProvincias.setEnabled(true);
       cmbLocalidades.setEnabled(true);
        if(cmbBarrios!=null)
       {cmbBarrios.setModel(new DefaultComboBoxModel());
        cmbBarrios.setEnabled(false);}
        }
    }

    private void mostrarBarrios(JComboBox cmbProvincias,JComboBox cmbLocalidades, JComboBox cmbBarrios)
    {
        if(cmbLocalidades.getSelectedIndex()!=-1)
       {
        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Tupla t = (Tupla) cmbLocalidades.getSelectedItem() ;
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarBarrios(t.getId());
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        cmbBarrios.setModel(valores);
        cmbBarrios.setSelectedIndex(-1);
        cmbProvincias.setEnabled(true);
       cmbLocalidades.setEnabled(true);
        cmbBarrios.setEnabled(true);
      }
    }
  /*  private void mostrarHerramientasDisponibles()
    {

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarHerramientasDisponibles();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        lstTransporteMHHerramientasDisponibles.setModel(valores);


    }*/

   /** private void cargarCombosEmpresas()
    {
        DefaultComboBoxModel valoresEmpresasTransporteMH = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarEmpresasTransporteMH();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valoresEmpresasTransporteMH.addElement(tu);
        }
        cmbEmpresasTransporteMH.setModel(valoresEmpresasTransporteMH);
        cmbEmpresasTransporteMH.setSelectedIndex(-1);

        DefaultComboBoxModel valoresEmpresasTransportePasajeros = new DefaultComboBoxModel();
        lista = gestorOtrosDatosEtapa.mostrarEmpresasTransportePasajeros();
        it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valoresEmpresasTransportePasajeros.addElement(tu);
        }
        cmbEmpresasTransportePasajeros.setModel(valoresEmpresasTransportePasajeros);
        cmbEmpresasTransportePasajeros.setSelectedIndex(-1);

        DefaultComboBoxModel valoresEmpresasAlojamiento = new DefaultComboBoxModel();
        lista = gestorOtrosDatosEtapa.mostrarEmpresasAlojamiento();
        it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valoresEmpresasAlojamiento.addElement(tu);
        }
        cmbEmpresasAlojamiento.setModel(valoresEmpresasAlojamiento);
        cmbEmpresasAlojamiento.setSelectedIndex(-1);

    }*/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cmbFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        cmbFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEtapas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTareas = new javax.swing.JTable();
        txtCostoTareas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTareaArriba = new javax.swing.JButton();
        btnTareaSubir = new javax.swing.JButton();
        btnTareaBajar = new javax.swing.JButton();
        btnTareaAlFinal = new javax.swing.JButton();
        lblMoneda = new javax.swing.JLabel();
        btnTareasNueva = new javax.swing.JButton();
        btnTareasEliminar = new javax.swing.JButton();
        btnTareasModificar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        jLabel6.setText("jLabel6");

        setClosable(true);
        setTitle("Nueva Etapa");

        jLabel4.setText("Fecha de Inicio:");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane5.setViewportView(txtObservaciones);

        jLabel7.setText("Fecha de Fin:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Nombre:");

        jLabel5.setText("Observaciones:");

        txtNombre.setText("Preparar lugar de trabajo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addGap(172, 172, 172)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos de la Etapa", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Marque las etapas precedentes:"));

        tablaEtapas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Nombre de la Etapa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaEtapas);
        tablaEtapas.getColumnModel().getColumn(0).setMinWidth(50);
        tablaEtapas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaEtapas.getColumnModel().getColumn(0).setMaxWidth(50);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Relaciones entre Etapas", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Tareas a realizar en la Etapa"));

        tablaTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Cortar planchas de Metal para la Base", "500"},
                {"Soldar Base", "20"}
            },
            new String [] {
                "Nombre de la Tarea", "Costo"
            }
        ));
        tablaTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaTareasMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTareas);
        tablaTareas.getColumnModel().getColumn(0).setMinWidth(220);
        tablaTareas.getColumnModel().getColumn(0).setPreferredWidth(220);
        tablaTareas.getColumnModel().getColumn(0).setMaxWidth(220);

        txtCostoTareas.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setLabelFor(txtCostoTareas);
        jLabel3.setText("Costo Total de las Tareas:");

        btnTareaArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/arrow_large_up.png"))); // NOI18N
        btnTareaArriba.setText("Al Principio");
        btnTareaArriba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareaArribaActionPerformed(evt);
            }
        });

        btnTareaSubir.setText("Subir");
        btnTareaSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareaSubirActionPerformed(evt);
            }
        });

        btnTareaBajar.setText("Bajar");
        btnTareaBajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareaBajarActionPerformed(evt);
            }
        });

        btnTareaAlFinal.setText("Al final");
        btnTareaAlFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareaAlFinalActionPerformed(evt);
            }
        });

        lblMoneda.setText("$");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnTareaArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTareaSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTareaBajar, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTareaAlFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMoneda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCostoTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTareaArriba)
                    .addComponent(btnTareaSubir)
                    .addComponent(btnTareaBajar)
                    .addComponent(btnTareaAlFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCostoTareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lblMoneda)))
        );

        btnTareasNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnTareasNueva.setText("Nueva Tarea");
        btnTareasNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareasNuevaActionPerformed(evt);
            }
        });

        btnTareasEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnTareasEliminar.setText("Eliminar");

        btnTareasModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/text_page.png"))); // NOI18N
        btnTareasModificar.setText("Modificar");
        btnTareasModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareasModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnTareasModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(btnTareasEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnTareasNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTareasModificar)
                    .addComponent(btnTareasNueva)
                    .addComponent(btnTareasEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tareas", jPanel3);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/save_upload.png"))); // NOI18N
        jButton3.setText("Guardar Cambios");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // PRIMERO GUARDO LOS DATOS BASE DE LA ETAPA
        String nombre = txtNombre.getText();
        Date fechaInicio = cmbFechaInicio.getDate();
        Date fechaFin    = cmbFechaFin.getDate();
        String obs = txtObservaciones.getText();
        DefaultTableModel modelOT = (DefaultTableModel) tablaTareas.getModel();
        ArrayList<NTupla> listaOrdenTareas=new ArrayList<NTupla>();
        for (int i = 0; i < modelOT.getRowCount(); i++)
        {
            NTupla nt =new NTupla();
            nt.setId(((Tupla) modelOT.getValueAt(i, 0)).getId());
            nt.setData((Integer)(i+1));
            listaOrdenTareas.add(nt);
        }
        gestorOtrosDatosEtapa.guardarTareas(listaOrdenTareas);
        gestorRegistrarEtapa.guardarCambiosBaseEtapa(nombre,fechaInicio,fechaFin,obs);

        // AHORA GUARDO LOS DATOS DE LAS ETPAAS PREDECESORAS
        DefaultTableModel model = (DefaultTableModel) tablaEtapas.getModel();
        for (int i = 0; i < model.getRowCount(); i++)
        {
            NTupla nt =(NTupla) model.getValueAt(i, 1);
            int idEtapaCheck = nt.getId();
            Boolean relacion = (Boolean) model.getValueAt(i, 0);

            gestorRegistrarEtapa.modificarRelacionEtapa(this.idEtapa,idEtapaCheck,relacion);

        }
        SystemEventProxy.getInstance().getPantalla().refescarGrafico();
        this.dispose();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnTareasNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareasNuevaActionPerformed
       /* pantallaRegistrarTarea pre = new pantallaRegistrarTarea(this, idPresupuesto, idEtapa);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);*/
         pantallaSeleccionTipoTarea pre = new pantallaSeleccionTipoTarea(this, idPresupuesto, idEtapa);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
    }//GEN-LAST:event_btnTareasNuevaActionPerformed

    private void btnTareaSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareaSubirActionPerformed
        if(tablaTareas.getSelectedRow()!=-1 &&tablaTareas.getSelectedRow()!=0)
        {
           
           DefaultTableModel modeloT = (DefaultTableModel) tablaTareas.getModel();
           modeloT.moveRow(tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow()-1);
           tablaTareas.changeSelection( tablaTareas.getSelectedRow()-1,0, false, false);
        }
    }//GEN-LAST:event_btnTareaSubirActionPerformed

    private void btnTareaBajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareaBajarActionPerformed
        if(tablaTareas.getSelectedRow()!=-1 &&tablaTareas.getSelectedRow()!=tablaTareas.getRowCount()-1)
        {
           DefaultTableModel modeloT = (DefaultTableModel) tablaTareas.getModel();
           modeloT.moveRow(tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow()+1);
           tablaTareas.changeSelection( tablaTareas.getSelectedRow()+1,0, false, false);
        }
    }//GEN-LAST:event_btnTareaBajarActionPerformed

    private void btnTareaArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareaArribaActionPerformed
      if(tablaTareas.getSelectedRow()!=-1 &&tablaTareas.getSelectedRow()!=0)
        {
          DefaultTableModel modeloT = (DefaultTableModel) tablaTareas.getModel();
           modeloT.moveRow(tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow(), 0);
           tablaTareas.changeSelection( 0,0, false, false);
        }
    }//GEN-LAST:event_btnTareaArribaActionPerformed

    private void btnTareaAlFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareaAlFinalActionPerformed
      if(tablaTareas.getSelectedRow()!=-1 &&tablaTareas.getSelectedRow()!=tablaTareas.getRowCount()-1)
        {
           DefaultTableModel modeloT = (DefaultTableModel) tablaTareas.getModel();
           modeloT.moveRow(tablaTareas.getSelectedRow(), tablaTareas.getSelectedRow(), tablaTareas.getRowCount()-1);
           tablaTareas.changeSelection( tablaTareas.getRowCount()-1,0, false, false);
        }
    }//GEN-LAST:event_btnTareaAlFinalActionPerformed

    private void btnTareasModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTareasModificarActionPerformed
       modificarTarea();
    }//GEN-LAST:event_btnTareasModificarActionPerformed

    private void tablaTareasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTareasMouseReleased
        habilitarBotonesTarea();
        
         if (evt.getClickCount() == 2)
            {
             modificarTarea();
            }
       
    }//GEN-LAST:event_tablaTareasMouseReleased

    private void modificarTarea()
    {
       if(tablaTareas.getSelectedRow()!=-1)
       { 
         int idTarea=((Tupla)tablaTareas.getValueAt(tablaTareas.getSelectedRow(), 0)).getId();
         pantallaRegistrarTarea pre = new pantallaRegistrarTarea(this, idPresupuesto, idEtapa, idTarea);
         SwingPanel.getInstance().addWindow(pre);
         pre.setVisible(true);
         this.dispose();
       }
    }
    private void habilitarBotonesTarea()
    {
        if(tablaTareas.getSelectedRow()!=-1)
       {
            btnTareasModificar.setEnabled(true);
            btnTareasEliminar.setEnabled(true);
            btnTareaBajar.setEnabled(true);
            btnTareaSubir.setEnabled(true);
            btnTareaArriba.setEnabled(true);
            btnTareaAlFinal.setEnabled(true);
       }
       else
       {
        btnTareasModificar.setEnabled(false);
            btnTareasEliminar.setEnabled(false);
            btnTareaBajar.setEnabled(false);
            btnTareaSubir.setEnabled(false);
            btnTareaArriba.setEnabled(false);
            btnTareaAlFinal.setEnabled(false);    
       }
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JButton btnModificarEmpleado1;
    private javax.swing.JButton btnModificarEmpleado2;
    private javax.swing.JButton btnModificarEmpleado3;
    private javax.swing.JButton btnModificarEmpleado4;
    private javax.swing.JButton btnTareaAlFinal;
    private javax.swing.JButton btnTareaArriba;
    private javax.swing.JButton btnTareaBajar;
    private javax.swing.JButton btnTareaSubir;
    private javax.swing.JButton btnTareasEliminar;
    private javax.swing.JButton btnTareasModificar;
    private javax.swing.JButton btnTareasNueva;
    private com.toedter.calendar.JDateChooser cmbFechaFin;
    private com.toedter.calendar.JDateChooser cmbFechaInicio;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMoneda;
    private javax.swing.JTable tablaEtapas;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JTextField txtCostoTareas;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    // End of variables declaration//GEN-END:variables

    public void MostrarMensaje(String cod)
    {
        if(cod.equals("ME-0001"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar la Etapa","Error en la Carga",JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        if(cod.equals("ME-0002"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo guardar los cambios en la Etapa","Error",JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        if(cod.equals("ME-0003"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo cargar el Presupuesto","Error",JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        if(cod.equals("ME-0004"))
        {
            JOptionPane.showMessageDialog(this.getParent(),"No se pudo guardar las relaciones entre Etapas","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void actualizar(int id, String flag, boolean exito)
    {
       if(flag.equals("RegistrarTarea"))
        {cargarTareas();}
    }

}
