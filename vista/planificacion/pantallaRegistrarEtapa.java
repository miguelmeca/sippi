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
        cargarCombosEmpresas();
        gestorRegistrarEtapa.mostrarDatosEtapa();
        cargarTareas();
        cargarEtapas();
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



    }
    private void cargarCombosTransportYAlojamiento()
    {
        cmbTransporteMHOrigenProvincias.setEnabled(false);
        cmbTransporteMHOrigenLocalidades.setEnabled(false);
        cmbTransporteMHOrigenBarrios.setEnabled(false);
        cmbTransporteMHDestinoProvincias.setEnabled(false);
        cmbTransporteMHDestinoLocalidades.setEnabled(false);
        cmbTransporteMHDestinoBarrios.setEnabled(false);
        cmbTransportePasajerosOrigenProvincias.setEnabled(false);
        cmbTransportePasajerosOrigenLocalidades.setEnabled(false);
        cmbTransportePasajerosDestinoProvincias.setEnabled(false);
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
    private void mostrarHerramientasDisponibles()
    {

        DefaultComboBoxModel valores = new DefaultComboBoxModel();
        ArrayList<Tupla> lista = gestorOtrosDatosEtapa.mostrarHerramientasDisponibles();
        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        lstTransporteMHHerramientasDisponibles.setModel(valores);


    }

    private void cargarCombosEmpresas()
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

    }

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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        btnTareasNueva = new javax.swing.JButton();
        btnTareasEliminar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        cboAlojamientoCantPersonas = new javax.swing.JTextField();
        cboAlojamientoCantDias = new javax.swing.JTextField();
        cmbEmpresasAlojamiento = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtPrecioAlojamiento = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        cmbEmpresasTransportePasajeros = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cmbTransportePasajerosOrigenPaises = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        cmbTransportePasajerosOrigenProvincias = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        cmbTransportePasajerosOrigenLocalidades = new javax.swing.JComboBox();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        cmbTransportePasajerosDestinoPaises = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        cmbTransportePasajerosDestinoProvincias = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        cmbTransportePasajerosDestinoLocalidades = new javax.swing.JComboBox();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        txtTransportePasajerosCantidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtPrecioTransporteDePasajeros = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cmbEmpresasTransporteMH = new javax.swing.JComboBox();
        jButton6 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtTransporteMHOrigenCalle = new javax.swing.JTextField();
        txtTransporteMHOrigenNro = new javax.swing.JTextField();
        txtTransporteMHOrigenPiso = new javax.swing.JTextField();
        txtTransporteMHOrigenDepto = new javax.swing.JTextField();
        txtTransporteMHOrigenCP = new javax.swing.JTextField();
        cmbTransporteMHOrigenPaises = new javax.swing.JComboBox();
        cmbTransporteMHOrigenProvincias = new javax.swing.JComboBox();
        cmbTransporteMHOrigenLocalidades = new javax.swing.JComboBox();
        cmbTransporteMHOrigenBarrios = new javax.swing.JComboBox();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txtTransporteMHDestinoCalle = new javax.swing.JTextField();
        txtTransporteMHDestinoNro = new javax.swing.JTextField();
        txtTransporteMHDestinoPiso = new javax.swing.JTextField();
        txtTransporteMHDestinoDepto = new javax.swing.JTextField();
        txtTransporteMHDestinoCP = new javax.swing.JTextField();
        cmbTransporteMHDestinoPaises = new javax.swing.JComboBox();
        cmbTransporteMHDestinoProvincias = new javax.swing.JComboBox();
        cmbTransporteMHDestinoLocalidades = new javax.swing.JComboBox();
        cmbTransporteMHDestinoBarrios = new javax.swing.JComboBox();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        btnAgregarHerramienta = new javax.swing.JButton();
        btnQuitarHerramienta = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstTransporteMHHerramientasTrasnportar = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstTransporteMHHerramientasDisponibles = new javax.swing.JList();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txaTransporteMHMateriales = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txaTransporteMHMaterialesObs = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtPrecioTransporteMyH = new javax.swing.JTextField();
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
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
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
        jScrollPane1.setViewportView(tablaTareas);
        tablaTareas.getColumnModel().getColumn(0).setMinWidth(220);
        tablaTareas.getColumnModel().getColumn(0).setPreferredWidth(220);
        tablaTareas.getColumnModel().getColumn(0).setMaxWidth(220);

        txtCostoTareas.setEditable(false);
        txtCostoTareas.setText("$520");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setLabelFor(txtCostoTareas);
        jLabel3.setText("Costo Total de las Tareas:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/arrow_large_up.png"))); // NOI18N
        jButton1.setText("Al Principio");

        jButton2.setText("Subir");

        jButton7.setText("Bajar");

        jButton18.setText("Al final");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCostoTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton7)
                    .addComponent(jButton18))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCostoTareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
        );

        btnTareasNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnTareasNueva.setText("Nueva Tarea");
        btnTareasNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTareasNuevaActionPerformed(evt);
            }
        });

        btnTareasEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnTareasEliminar.setText("Eliminar Tarea");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnTareasEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTareasNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTareasNueva)
                    .addComponent(btnTareasEliminar))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tareas", jPanel3);

        cmbEmpresasAlojamiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmpresasAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpresasAlojamientoActionPerformed(evt);
            }
        });

        jLabel9.setText("Empresa:");

        jLabel10.setText("Cantidad de personas:");

        jLabel19.setText("Cantidad de dias:");

        jLabel25.setText("Precio Noche:");

        txtPrecioAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioAlojamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbEmpresasAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel19)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboAlojamientoCantDias)
                            .addComponent(cboAlojamientoCantPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioAlojamiento, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbEmpresasAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboAlojamientoCantPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cboAlojamientoCantDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(267, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Alojamiento", jPanel5);

        cmbEmpresasTransportePasajeros.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Empresa de Transporte:");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Origen"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel16.setText("Pais: ");

        cmbTransportePasajerosOrigenPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosOrigenPaisesActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Provincia:");

        cmbTransportePasajerosOrigenProvincias.setEnabled(false);
        cmbTransportePasajerosOrigenProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosOrigenProvinciasActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel18.setText("Localidad: ");

        cmbTransportePasajerosOrigenLocalidades.setEnabled(false);
        cmbTransportePasajerosOrigenLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosOrigenLocalidadesActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTransportePasajerosOrigenPaises, 0, 216, Short.MAX_VALUE)
                            .addComponent(cmbTransportePasajerosOrigenProvincias, 0, 216, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTransportePasajerosOrigenLocalidades, 0, 217, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTransportePasajerosOrigenPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbTransportePasajerosOrigenProvincias, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel17)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTransportePasajerosOrigenLocalidades)
                        .addComponent(jLabel18)))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino"));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel21.setText("Pais: ");

        cmbTransportePasajerosDestinoPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosDestinoPaisesActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel22.setText("Provincia:");

        cmbTransportePasajerosDestinoProvincias.setEnabled(false);
        cmbTransportePasajerosDestinoProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosDestinoProvinciasActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel23.setText("Localidad: ");

        cmbTransportePasajerosDestinoLocalidades.setEnabled(false);
        cmbTransportePasajerosDestinoLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransportePasajerosDestinoLocalidadesActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(35, 35, 35)
                        .addComponent(cmbTransportePasajerosDestinoPaises, 0, 216, Short.MAX_VALUE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTransportePasajerosDestinoProvincias, 0, 216, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTransportePasajerosDestinoLocalidades, 0, 217, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton10, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTransportePasajerosDestinoPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbTransportePasajerosDestinoProvincias, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTransportePasajerosDestinoLocalidades)
                        .addComponent(jLabel23)))
                .addContainerGap())
        );

        txtTransportePasajerosCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransportePasajerosCantidadActionPerformed(evt);
            }
        });

        jLabel11.setText("Cantidad de pasajeros:");

        jLabel20.setText("Precio Unitario:");

        txtPrecioTransporteDePasajeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioTransporteDePasajerosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecioTransporteDePasajeros, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTransportePasajerosCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbEmpresasTransportePasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbEmpresasTransportePasajeros, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTransportePasajerosCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(txtPrecioTransporteDePasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("Transoporte de pasajeros", jPanel6);

        jLabel12.setText("Empresa de Transporte:");

        cmbEmpresasTransporteMH.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Origen"));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel41.setText("Calle: ");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel42.setText("Nº:");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel43.setText("Piso:");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel44.setText("Departamento:");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel45.setText("Código Postal:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel46.setText("Pais: ");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel47.setText("Provincia:");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel48.setText("Localidad: ");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel49.setText("Barrio:");

        txtTransporteMHOrigenCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransporteMHOrigenCalleActionPerformed(evt);
            }
        });

        cmbTransporteMHOrigenPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHOrigenPaisesActionPerformed(evt);
            }
        });

        cmbTransporteMHOrigenProvincias.setEnabled(false);
        cmbTransporteMHOrigenProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHOrigenProvinciasActionPerformed(evt);
            }
        });

        cmbTransporteMHOrigenLocalidades.setEnabled(false);
        cmbTransporteMHOrigenLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHOrigenLocalidadesActionPerformed(evt);
            }
        });

        cmbTransporteMHOrigenBarrios.setEnabled(false);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTransporteMHOrigenCalle, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(20, 20, 20)
                        .addComponent(txtTransporteMHOrigenNro, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(10, 10, 10)
                        .addComponent(txtTransporteMHOrigenPiso, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(10, 10, 10)
                        .addComponent(txtTransporteMHOrigenDepto, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(17, 17, 17)
                        .addComponent(txtTransporteMHOrigenCP, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(41, 41, 41)
                                .addComponent(cmbTransporteMHOrigenPaises, 0, 184, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTransporteMHOrigenProvincias, 0, 184, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbTransporteMHOrigenLocalidades, 0, 185, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(33, 33, 33)
                                .addComponent(cmbTransporteMHOrigenBarrios, 0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton14, 0, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtTransporteMHOrigenCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtTransporteMHOrigenNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtTransporteMHOrigenPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtTransporteMHOrigenDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTransporteMHOrigenCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTransporteMHOrigenPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton12, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbTransporteMHOrigenProvincias)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton13, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbTransporteMHOrigenLocalidades)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTransporteMHOrigenBarrios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49))
                    .addComponent(jButton14, 0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Origen", jPanel12);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino"));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel53.setText("Calle: ");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel54.setText("Nº:");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel55.setText("Piso:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel56.setText("Departamento:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel57.setText("Código Postal:");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel58.setText("Pais: ");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel59.setText("Provincia:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel60.setText("Localidad: ");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel61.setText("Barrio:");

        txtTransporteMHDestinoCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransporteMHDestinoCalleActionPerformed(evt);
            }
        });

        cmbTransporteMHDestinoPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHDestinoPaisesActionPerformed(evt);
            }
        });

        cmbTransporteMHDestinoProvincias.setEnabled(false);
        cmbTransporteMHDestinoProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHDestinoProvinciasActionPerformed(evt);
            }
        });

        cmbTransporteMHDestinoLocalidades.setEnabled(false);
        cmbTransporteMHDestinoLocalidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTransporteMHDestinoLocalidadesActionPerformed(evt);
            }
        });

        cmbTransporteMHDestinoBarrios.setEnabled(false);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTransporteMHDestinoCalle, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(20, 20, 20)
                        .addComponent(txtTransporteMHDestinoNro, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(10, 10, 10)
                        .addComponent(txtTransporteMHDestinoPiso, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(10, 10, 10)
                        .addComponent(txtTransporteMHDestinoDepto, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(17, 17, 17)
                        .addComponent(txtTransporteMHDestinoCP, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(41, 41, 41)
                                .addComponent(cmbTransporteMHDestinoPaises, 0, 184, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTransporteMHDestinoProvincias, 0, 184, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbTransporteMHDestinoLocalidades, 0, 185, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(33, 33, 33)
                                .addComponent(cmbTransporteMHDestinoBarrios, 0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton17, 0, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txtTransporteMHDestinoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtTransporteMHDestinoNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtTransporteMHDestinoPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txtTransporteMHDestinoDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTransporteMHDestinoCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTransporteMHDestinoPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton15, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbTransporteMHDestinoProvincias)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton16, 0, 0, Short.MAX_VALUE)
                    .addComponent(cmbTransporteMHDestinoLocalidades)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTransporteMHDestinoBarrios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel61))
                    .addComponent(jButton17, 0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Destino", jPanel15);

        btnAgregarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/down.png"))); // NOI18N
        btnAgregarHerramienta.setText("Agregar");
        btnAgregarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHerramientaActionPerformed(evt);
            }
        });

        btnQuitarHerramienta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnQuitarHerramienta.setText("Quitar");
        btnQuitarHerramienta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarHerramientaActionPerformed(evt);
            }
        });

        lstTransporteMHHerramientasTrasnportar.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstTransporteMHHerramientasTrasnportar);

        lstTransporteMHHerramientasDisponibles.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lstTransporteMHHerramientasDisponibles);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Disponibles:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel14.setText("A transportar:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(btnAgregarHerramienta, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnQuitarHerramienta, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(31, Short.MAX_VALUE)))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel13)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarHerramienta)
                    .addComponent(btnQuitarHerramienta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Herramientas", jPanel11);

        txaTransporteMHMateriales.setColumns(20);
        txaTransporteMHMateriales.setRows(5);
        jScrollPane6.setViewportView(txaTransporteMHMateriales);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel15.setText("Materiales:");

        txaTransporteMHMaterialesObs.setColumns(20);
        txaTransporteMHMaterialesObs.setRows(5);
        jScrollPane7.setViewportView(txaTransporteMHMaterialesObs);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText("Observaciones:");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Materiales", jPanel18);

        jLabel24.setText("Precio:");

        txtPrecioTransporteMyH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioTransporteMyHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbEmpresasTransporteMH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPrecioTransporteMyH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbEmpresasTransporteMH, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(jButton6, 0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtPrecioTransporteMyH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Transporte de materiales y herramientas", jPanel8);

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
                .addContainerGap(153, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTransportePasajerosOrigenPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosOrigenPaisesActionPerformed
        mostrarProvincias(cmbTransportePasajerosOrigenPaises,cmbTransportePasajerosOrigenProvincias,cmbTransportePasajerosOrigenLocalidades,null);
    }//GEN-LAST:event_cmbTransportePasajerosOrigenPaisesActionPerformed

    private void cmbTransportePasajerosOrigenProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosOrigenProvinciasActionPerformed
        mostrarLocalidades(cmbTransportePasajerosOrigenProvincias,cmbTransportePasajerosOrigenLocalidades,null);
    }//GEN-LAST:event_cmbTransportePasajerosOrigenProvinciasActionPerformed

    private void cmbTransportePasajerosOrigenLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosOrigenLocalidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTransportePasajerosOrigenLocalidadesActionPerformed

    private void cmbTransportePasajerosDestinoPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosDestinoPaisesActionPerformed
        mostrarProvincias(cmbTransportePasajerosDestinoPaises,cmbTransportePasajerosDestinoProvincias,cmbTransportePasajerosDestinoLocalidades,null);
    }//GEN-LAST:event_cmbTransportePasajerosDestinoPaisesActionPerformed

    private void cmbTransportePasajerosDestinoProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosDestinoProvinciasActionPerformed
      mostrarLocalidades(cmbTransportePasajerosDestinoProvincias,cmbTransportePasajerosDestinoLocalidades,null);
    }//GEN-LAST:event_cmbTransportePasajerosDestinoProvinciasActionPerformed

    private void cmbTransportePasajerosDestinoLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransportePasajerosDestinoLocalidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTransportePasajerosDestinoLocalidadesActionPerformed

    private void txtTransportePasajerosCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransportePasajerosCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransportePasajerosCantidadActionPerformed

    private void txtTransporteMHOrigenCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransporteMHOrigenCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransporteMHOrigenCalleActionPerformed

    private void cmbTransporteMHOrigenPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHOrigenPaisesActionPerformed
        mostrarProvincias(cmbTransporteMHOrigenPaises,cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades,cmbTransporteMHOrigenBarrios);
    }//GEN-LAST:event_cmbTransporteMHOrigenPaisesActionPerformed

    private void cmbTransporteMHOrigenProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHOrigenProvinciasActionPerformed
        mostrarLocalidades(cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades,cmbTransporteMHOrigenBarrios);
    }//GEN-LAST:event_cmbTransporteMHOrigenProvinciasActionPerformed

    private void cmbTransporteMHOrigenLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHOrigenLocalidadesActionPerformed
       mostrarBarrios(cmbTransporteMHOrigenProvincias,cmbTransporteMHOrigenLocalidades,cmbTransporteMHOrigenBarrios);
    }//GEN-LAST:event_cmbTransporteMHOrigenLocalidadesActionPerformed

    private void txtTransporteMHDestinoCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransporteMHDestinoCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransporteMHDestinoCalleActionPerformed

    private void cmbTransporteMHDestinoPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHDestinoPaisesActionPerformed
         mostrarProvincias(cmbTransporteMHDestinoPaises,cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades,cmbTransporteMHDestinoBarrios);
    }//GEN-LAST:event_cmbTransporteMHDestinoPaisesActionPerformed

    private void cmbTransporteMHDestinoProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHDestinoProvinciasActionPerformed
        mostrarLocalidades(cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades,cmbTransporteMHDestinoBarrios);
    }//GEN-LAST:event_cmbTransporteMHDestinoProvinciasActionPerformed

    private void cmbTransporteMHDestinoLocalidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTransporteMHDestinoLocalidadesActionPerformed
         mostrarBarrios(cmbTransporteMHDestinoProvincias,cmbTransporteMHDestinoLocalidades,cmbTransporteMHDestinoBarrios);
    }//GEN-LAST:event_cmbTransporteMHDestinoLocalidadesActionPerformed

    private void btnAgregarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHerramientaActionPerformed

}//GEN-LAST:event_btnAgregarHerramientaActionPerformed

    private void btnQuitarHerramientaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarHerramientaActionPerformed

}//GEN-LAST:event_btnQuitarHerramientaActionPerformed

    private void cmbEmpresasAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpresasAlojamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEmpresasAlojamientoActionPerformed

    private void txtPrecioTransporteDePasajerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioTransporteDePasajerosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioTransporteDePasajerosActionPerformed

    private void txtPrecioTransporteMyHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioTransporteMyHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioTransporteMyHActionPerformed

    private void txtPrecioAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioAlojamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioAlojamientoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // PRIMERO GUARDO LOS DATOS BASE DE LA ETAPA
        String nombre = txtNombre.getText();
        Date fechaInicio = cmbFechaInicio.getDate();
        Date fechaFin    = cmbFechaFin.getDate();
        String obs = txtObservaciones.getText();

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
        pantallaRegistrarTarea pre = new pantallaRegistrarTarea(this, idPresupuesto, idEtapa);
            SwingPanel.getInstance().addWindow(pre);
            pre.setVisible(true);
    }//GEN-LAST:event_btnTareasNuevaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarHerramienta;
    private javax.swing.JButton btnQuitarHerramienta;
    private javax.swing.JButton btnTareasEliminar;
    private javax.swing.JButton btnTareasNueva;
    private javax.swing.JTextField cboAlojamientoCantDias;
    private javax.swing.JTextField cboAlojamientoCantPersonas;
    private javax.swing.JComboBox cmbEmpresasAlojamiento;
    private javax.swing.JComboBox cmbEmpresasTransporteMH;
    private javax.swing.JComboBox cmbEmpresasTransportePasajeros;
    private com.toedter.calendar.JDateChooser cmbFechaFin;
    private com.toedter.calendar.JDateChooser cmbFechaInicio;
    private javax.swing.JComboBox cmbTransporteMHDestinoBarrios;
    private javax.swing.JComboBox cmbTransporteMHDestinoLocalidades;
    private javax.swing.JComboBox cmbTransporteMHDestinoPaises;
    private javax.swing.JComboBox cmbTransporteMHDestinoProvincias;
    private javax.swing.JComboBox cmbTransporteMHOrigenBarrios;
    private javax.swing.JComboBox cmbTransporteMHOrigenLocalidades;
    private javax.swing.JComboBox cmbTransporteMHOrigenPaises;
    private javax.swing.JComboBox cmbTransporteMHOrigenProvincias;
    private javax.swing.JComboBox cmbTransportePasajerosDestinoLocalidades;
    private javax.swing.JComboBox cmbTransportePasajerosDestinoPaises;
    private javax.swing.JComboBox cmbTransportePasajerosDestinoProvincias;
    private javax.swing.JComboBox cmbTransportePasajerosOrigenLocalidades;
    private javax.swing.JComboBox cmbTransportePasajerosOrigenPaises;
    private javax.swing.JComboBox cmbTransportePasajerosOrigenProvincias;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JList lstTransporteMHHerramientasDisponibles;
    private javax.swing.JList lstTransporteMHHerramientasTrasnportar;
    private javax.swing.JTable tablaEtapas;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JTextArea txaTransporteMHMateriales;
    private javax.swing.JTextArea txaTransporteMHMaterialesObs;
    private javax.swing.JTextField txtCostoTareas;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtPrecioAlojamiento;
    private javax.swing.JTextField txtPrecioTransporteDePasajeros;
    private javax.swing.JTextField txtPrecioTransporteMyH;
    private javax.swing.JTextField txtTransporteMHDestinoCP;
    private javax.swing.JTextField txtTransporteMHDestinoCalle;
    private javax.swing.JTextField txtTransporteMHDestinoDepto;
    private javax.swing.JTextField txtTransporteMHDestinoNro;
    private javax.swing.JTextField txtTransporteMHDestinoPiso;
    private javax.swing.JTextField txtTransporteMHOrigenCP;
    private javax.swing.JTextField txtTransporteMHOrigenCalle;
    private javax.swing.JTextField txtTransporteMHOrigenDepto;
    private javax.swing.JTextField txtTransporteMHOrigenNro;
    private javax.swing.JTextField txtTransporteMHOrigenPiso;
    private javax.swing.JTextField txtTransportePasajerosCantidad;
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
