/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * explorarCotizaciones.java
 *
 * Created on 01-jun-2011, 21:51:09
 */

package vista.cotizacion;

import controlador.cotizacion.GestorExplorarCotizacionesObra;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.NTupla;
import util.SwingPanel;
import vista.gui.IFavorito;
import vista.interfaces.IAyuda;

/**
 *
 * @author Administrador
 */
public class ExplorarCotizacionObra extends javax.swing.JInternalFrame implements IFavorito,IAyuda{

    private GestorExplorarCotizacionesObra gestor;
    
    /** Creates new form explorarCotizaciones */
    public ExplorarCotizacionObra() {
        initComponents();

        gestor = new GestorExplorarCotizacionesObra(this);
        
        initTables();

        llenarTablaObras();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaObras = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCotizaciones = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();
        btnModificarCotizacion = new javax.swing.JButton();
        btnNuevaCotizacion = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setTitle("Explorar Cotizaciones de Obra");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search_page.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Obras"));

        tablaObras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaObras.setTableHeader(null);
        tablaObras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaObrasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaObras);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cotizaciones"));

        tablaCotizaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCotizaciones.setTableHeader(null);
        jScrollPane2.setViewportView(tablaCotizaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnModificarCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/folder.png"))); // NOI18N
        btnModificarCotizacion.setText("Modificar Cotización");
        btnModificarCotizacion.setEnabled(false);
        btnModificarCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCotizacionActionPerformed(evt);
            }
        });

        btnNuevaCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnNuevaCotizacion.setText("Nueva Cotización");
        btnNuevaCotizacion.setEnabled(false);
        btnNuevaCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCotizacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(btnNuevaCotizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarCotizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnModificarCotizacion)
                    .addComponent(btnNuevaCotizacion))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCotizacionActionPerformed

        if(tablaCotizaciones.getSelectedRow()!=-1 && tablaCotizaciones.getValueAt(tablaCotizaciones.getSelectedRow(),0) instanceof ExplorarCotizacionObra_celda)
        {
            ExplorarCotizacionObra_celda fila = (ExplorarCotizacionObra_celda)tablaCotizaciones.getValueAt(tablaCotizaciones.getSelectedRow(),0);
            int cot_id = fila.getId();

            ExplorarSubObras mod =  new ExplorarSubObras(cot_id);
            SwingPanel.getInstance().addWindow(mod);
            mod.setVisible(true);
            
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione una cotzación para modificarla","Atencion!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarCotizacionActionPerformed

    private void btnNuevaCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCotizacionActionPerformed

        if(tablaObras.getSelectedRow()!=-1 && tablaObras.getValueAt(tablaObras.getSelectedRow(),0) instanceof ExplorarCotizacionObra_celdaObras)
        {
            ExplorarCotizacionObra_celdaObras fila = (ExplorarCotizacionObra_celdaObras)tablaObras.getValueAt(tablaObras.getSelectedRow(),0);
            int po_id = fila.getId();        
         
            RegistrarCotizacion regcot = new RegistrarCotizacion(po_id);
            SwingPanel.getInstance().addWindow(regcot);
            regcot.setVisible(true);
            
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione un Pedido de Obra para crear una nueva Cotización","Atencion!",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnNuevaCotizacionActionPerformed

private void tablaObrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaObrasMouseClicked

    if(tablaObras.getSelectedRow()!=-1 && tablaObras.getValueAt(tablaObras.getSelectedRow(),0) instanceof ExplorarCotizacionObra_celdaObras)
    {
        ExplorarCotizacionObra_celdaObras t = (ExplorarCotizacionObra_celdaObras)tablaObras.getValueAt(tablaObras.getSelectedRow(),0);
        llenarTablaCotizaciones(gestor.getCotizaciones(t.getId()));
    }
    
}//GEN-LAST:event_tablaObrasMouseClicked

private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
    
    this.dispose();
    
}//GEN-LAST:event_btnCerrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnModificarCotizacion;
    private javax.swing.JButton btnNuevaCotizacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaCotizaciones;
    private javax.swing.JTable tablaObras;
    // End of variables declaration//GEN-END:variables

    private void initTables()
    {
         tablaCotizaciones.setDefaultRenderer(Object.class,new ExplorarCotizacionObra_Render());
         tablaCotizaciones.setRowHeight(76);
         
         tablaObras.setDefaultRenderer(Object.class,new ExplorarCotizacionObra_Render());
         tablaObras.setRowHeight(76);
    }

    @Override
    public boolean isFavorito() {
        return true;
    }

    @Override
    public String getIconoFavorito() {
        return "/res/iconos/var/16x16/search_page.png";
    }

    private void llenarTablaObras() {
        
        Object filaTabla[] = new Object[1];
        
        DefaultTableModel modelo = (DefaultTableModel) tablaObras.getModel();
     
        ArrayList<NTupla> obras = gestor.getObras();
        
        // Activo - Desactivo el boton modificar
        if(obras.isEmpty())
        {
            btnNuevaCotizacion.setEnabled(false);
        }
        else
        {
            btnNuevaCotizacion.setEnabled(true);
        }
        
        for (int i = 0; i < obras.size(); i++) 
        {
            NTupla nt = obras.get(i);
            
            ExplorarCotizacionObra_celdaObras fila = new ExplorarCotizacionObra_celdaObras();
            
            String[] data = (String[]) nt.getData();
            
            fila.setId(nt.getId());
            fila.setDatosObra(nt.getId(),data[0]);
            fila.setCliente(data[1]);
            fila.setFechaInicioyFin(data[2],data[3]);
            
            
            filaTabla[0] = fila;
            modelo.addRow(filaTabla);
        }
        
        
    }

    private void llenarTablaCotizaciones(ArrayList<NTupla> lista) 
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaCotizaciones.getModel();
        // Vacio la tabla
        for (int i = 0; i < modelo.getRowCount(); i++) 
        {
            modelo.removeRow(i);
        }
        
        // Activo - Desactivo el boton modificar
        if(lista.isEmpty())
        {
            btnModificarCotizacion.setEnabled(false);
            Object filaVacia[] = new Object[1];
            filaVacia[0] = "No se encontraron cotizaciones. ¿Desea agregar una?";
            modelo.addRow(filaVacia);
        }
        else
        {
            btnModificarCotizacion.setEnabled(true);
            Object filaTabla[] = new Object[1];
        
            for (int i = 0; i < lista.size(); i++) 
            {
                NTupla nt = lista.get(i);

                ExplorarCotizacionObra_celda fila = new ExplorarCotizacionObra_celda();

                String[] data = (String[]) nt.getData();

                fila.setId(nt.getId());
                fila.setNumeroCotizacion(data[0]);
                fila.setRevision(Integer.parseInt(data[1]));
                fila.setFechaCreacion(data[3]);
                fila.setUltimaModificacion(data[2]);
                fila.setObraId(Integer.parseInt(data[4]));
                fila.setEstado(data[5]);

                filaTabla[0] = fila;
                modelo.addRow(filaTabla);
            }
        }
        

                
    }

    @Override
    public String getTituloAyuda() {
        return this.getTitle();
    }

    @Override
    public String getResumenAyuda() {
        return "Permite explorar las Obras y ver o modificar las cotizaciónes que tiene o crear nuevas";
    }

    @Override
    public int getIdAyuda() {
        return 0;
    }

}
