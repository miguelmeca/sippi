package vista.ejecucion.lanzamiento;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import vista.util.MyComboBoxEditor;

/**
 *
 * @author Iuga
 */
public class PanelHerramientas extends javax.swing.JPanel {

    public static final int TABLA_HERRAMIENTAS_COLUMNA_NOMBRE = 0;
    public static final int TABLA_HERRAMIENTAS_COLUMNA_NOTAS = 1;
    public static final int TABLA_HERRAMIENTAS_COLUMNA_ESTADO = 2;
    public static final int TABLA_HERRAMIENTAS_COLUMNA_SELECCION = 3;
    
    private static final int TABLA_DEFAULT_ALTO = 25;    
    
    /**
     * Creates new form PanelHerramientas
     */
    public PanelHerramientas() {
        initComponents();
        initTabla();
        mock();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHerramientas = new javax.swing.JTable();
        btnGenerarOrdenDeCompra = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        tblHerramientas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Notas", "Estado", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHerramientas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblHerramientas);

        btnGenerarOrdenDeCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/List.png"))); // NOI18N
        btnGenerarOrdenDeCompra.setText("Generar Orden de Compra");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnGenerarOrdenDeCompra)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarOrdenDeCompra))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarOrdenDeCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHerramientas;
    // End of variables declaration//GEN-END:variables

    private void initTabla() {
        tblHerramientas.setRowHeight(TABLA_DEFAULT_ALTO);
        DefaultTableModel modelo = (DefaultTableModel) tblHerramientas.getModel();

        TableColumn col = tblHerramientas.getColumnModel().getColumn(TABLA_HERRAMIENTAS_COLUMNA_ESTADO);
        String[] values = new String[]{"<HTML><span color='#009900'>Disponible</span>", 
                                       "<HTML><span color='#CC7A00'>En Reparación</span>", 
                                       "<HTML><span color='#CC0000'>No Disponible</span>"};
        col.setCellEditor(new MyComboBoxEditor(values));
        col.setCellRenderer(new PanelHerramientasCellRenderer(values));

        // Ancho de Columnas
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tblHerramientas.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < tblHerramientas.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch (i) {
                case TABLA_HERRAMIENTAS_COLUMNA_ESTADO:
                    anchoColumna = 300;
                    break;
                case TABLA_HERRAMIENTAS_COLUMNA_NOMBRE:
                    anchoColumna = 500;
                    break;
                case TABLA_HERRAMIENTAS_COLUMNA_NOTAS:
                    anchoColumna = 500;
                    break;
                case TABLA_HERRAMIENTAS_COLUMNA_SELECCION:
                    anchoColumna = 50;
                    break;
            }
            columnaTabla.setPreferredWidth(anchoColumna);
            columnaTabla.setWidth(anchoColumna);
        }
    }

    private void mock() {
        // TODO REMOVE
        DefaultTableModel modelo = (DefaultTableModel) tblHerramientas.getModel();
        
        Object row[] = new Object[4];
        row[0] = "Autoelevadora Electrica";
        row[1] = "<HTML><span color='#CC0000'>Herramienta Rota</span>";
        row[2] = new JComboBox();
        row[3] = false;
        modelo.addRow(row);
        
        Object row2[] = new Object[4];
        row2[0] = "Fresaadora CNC";
        row2[1] = "No está en la empresa";
        row2[2] = new JComboBox();
        row2[3] = false;
        modelo.addRow(row2);
        
        Object row3[] = new Object[4];
        row3[0] = "Torno Jhon Deer";
        row3[1] = "";
        row3[2] = new JComboBox();
        row3[3] = false;
        modelo.addRow(row3);
    }
}
