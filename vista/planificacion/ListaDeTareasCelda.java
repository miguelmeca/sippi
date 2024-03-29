/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/**
 * http://www.java2s.com/Code/Java/Swing-JFC/JLabelDragSource.htm
 * @author Administrador
 */
public class ListaDeTareasCelda extends javax.swing.JPanel {

    private String component;
    private String id;
    
    /**
     * Creates new form ArbolDeTareasCelda
     */
    public ListaDeTareasCelda(String component,String id) {
        
        this.component = component;
        this.id = id;
        
        initComponents();
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTexto = new javax.swing.JLabel();

        lblTexto.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTexto;
    // End of variables declaration//GEN-END:variables

    void setSelected(boolean selected) {
        if (selected) {
            this.setBackground(Color.GRAY);
        } else {
            this.setBackground(Color.WHITE);
        }
    }
    
    void setItemData(String icono, String texto)
    {
        lblTexto.setText(texto);
        lblTexto.setIcon(new javax.swing.ImageIcon(getClass().getResource(icono)));
    }

    public JLabel getLabel()
    {
        return lblTexto;
    }

    @Override
    public String toString() {
        return component+";"+id+";"+lblTexto.getText();
    }

    public String getId() {
        return id;
    }
   
    
    
}
