/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ejecucion.lanzamiento;

import controlador.ejecucion.lanzamiento.GestorVentanaLanzamiento;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.NTupla;
import util.TablaUtil;
import vista.util.MyComboBoxEditor;

/**
 *
 * @author Administrador
 */
public class PanelAdicionales extends javax.swing.JPanel {

    public static final int TABLA_ADICIONAL_COLUMNA_NOMBRE = 0;
    public static final int TABLA_ADICIONAL_COLUMNA_OPERARIOS = 1;
    public static final int TABLA_ADICIONAL_COLUMNA_DIAS = 2;
    public static final int TABLA_ADICIONAL_COLUMNA_SUBTOTAL = 3;
    
    private GestorVentanaLanzamiento gestor;
    
    private static final int TABLA_DEFAULT_ALTO = 25;        
    
    /**
     * Creates new form PanelAlquileresCompras
     */
    public PanelAdicionales(GestorVentanaLanzamiento gestor) {
        this.gestor = gestor;
        initComponents();
        initTabla();
        cargarDatosTablaParaObra();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlquilerCompra = new javax.swing.JTable();
        txtTotal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        tblAlquilerCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre y Descripcion", "Operarios", "Días", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAlquilerCompra);

        txtTotal.setBackground(new java.awt.Color(204, 204, 255));
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Total de Efectivo Necesario:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAlquilerCompra;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private void initTabla() {
        tblAlquilerCompra.setRowHeight(TABLA_DEFAULT_ALTO);
        DefaultTableModel modelo = (DefaultTableModel) tblAlquilerCompra.getModel();
        
        // Ancho de Columnas
        int anchoColumna = 0;
        TableColumnModel modeloColumna = tblAlquilerCompra.getColumnModel();
        TableColumn columnaTabla;
        for (int i = 0; i < tblAlquilerCompra.getColumnCount(); i++) {
            columnaTabla = modeloColumna.getColumn(i);
            switch (i) {
                case TABLA_ADICIONAL_COLUMNA_NOMBRE:
                    anchoColumna = 300;
                    break;
                case TABLA_ADICIONAL_COLUMNA_DIAS:
                    anchoColumna = 100;
                    break;
                case TABLA_ADICIONAL_COLUMNA_OPERARIOS:
                    anchoColumna = 100;
                    break;
                case TABLA_ADICIONAL_COLUMNA_SUBTOTAL:
                    anchoColumna = 100;
                    break;
            }
            columnaTabla.setPreferredWidth(anchoColumna);
            columnaTabla.setWidth(anchoColumna);
        }
    }

    /**
     * Pide al gestor que busque todos los datos de las adicionales de la 
     * Ejecucion que se van a usar en esta obra.
     */
    private void cargarDatosTablaParaObra() {
        
        double total = 0;
        
        List<NTupla> tuplas = gestor.llenarTablaPanelAdicionales();
        DefaultTableModel modelo = (DefaultTableModel)tblAlquilerCompra.getModel();
        TablaUtil.vaciarDefaultTableModel(modelo);
        
        for (int i = 0; i < tuplas.size(); i++) {
            NTupla nTupla = tuplas.get(i);
            Object[] fila = new Object[4];
            fila[TABLA_ADICIONAL_COLUMNA_NOMBRE] = nTupla;
            
                String[] data = (String[]) nTupla.getData();
            
            fila[TABLA_ADICIONAL_COLUMNA_OPERARIOS] = data[0];
            
            fila[TABLA_ADICIONAL_COLUMNA_DIAS] = data[1];
                       
            fila[TABLA_ADICIONAL_COLUMNA_SUBTOTAL] = "$ " + data[2];
            
            total += Double.valueOf(data[2]);
            
            modelo.addRow(fila);
        }
        
        txtTotal.setText("$"+total);
    }

    void actualizarDatos() {
        cargarDatosTablaParaObra();
    }
}
