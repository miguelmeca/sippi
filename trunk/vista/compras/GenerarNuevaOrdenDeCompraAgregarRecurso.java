/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import javax.swing.JOptionPane;
import modelo.*;
import util.HibernateUtil;
import util.SwingPanel;
import util.Tupla;
import vista.abms.*;
import vista.interfaces.ICallBackGen;
import vista.interfaces.ICallBackObject;

/**
 *
 * @author Administrador
 */
public class GenerarNuevaOrdenDeCompraAgregarRecurso extends javax.swing.JInternalFrame implements ICallBackGen {

    public static final String CALLBACK_SELECCION_MATERIAL     = "SeleccionDeMaterial";
    public static final String CALLBACK_SELECCION_HERRAMIENTA  = "SeleccionDeHerramietna";
    public static final String CALLBACK_SELECCION_ALQUILERCOMPRA  = "SeleccionAlquilerCompra";
    public static final String CALLBACK_SELECCION_ADICIONAL  = "SeleccionAdicional";
    private ICallBackObject callback;
    
    // NO CAMBIAR EL ORDEN !!
    private static final String[] conceptos = {"Materiales","Herramientas", "Alquileres y Compras", "Adicionales"};
    
    private ItemComprable itemSeleccionado;
    private Proveedor proveedor;
    
    /**
     * Creates new form GenerarNuevaOrdenDeCompraAgregarRecurso
     */
    public GenerarNuevaOrdenDeCompraAgregarRecurso(ICallBackObject callback,Proveedor p) {
        this.callback = callback;
        this.proveedor = p;
        initComponents();
        initComboConceptos();
    }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        cmbConcepto = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        btnExplorarItems = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDescripción = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblUnidadDeMedida = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnConsultarPrecios = new javax.swing.JButton();

        setClosable(true);
        setTitle("Agregar Un Item a la Orden de Compra");

        jLabel2.setText("Concepto:");

        cmbConcepto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Materiales", "Herramientas", "Alquileres y Compras", "Adicionales" }));
        cmbConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConceptoActionPerformed(evt);
            }
        });

        jLabel1.setText("Item a agregar en la Orden de Compra:");

        txtItem.setBackground(new java.awt.Color(204, 204, 204));
        txtItem.setEditable(false);

        btnExplorarItems.setText("...");
        btnExplorarItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExplorarItemsActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setText("Descripción:");

        jLabel4.setText("Precio Unitario:");

        jLabel3.setText("Cantidad:");

        lblUnidadDeMedida.setText("[UN.]");

        jLabel5.setText("$");

        btnConsultarPrecios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        btnConsultarPrecios.setToolTipText("Consultar Precios");
        btnConsultarPrecios.setPreferredSize(new java.awt.Dimension(49, 23));
        btnConsultarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarPreciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUnidadDeMedida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConsultarPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUnidadDeMedida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultarPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbConcepto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExplorarItems))
                    .addComponent(txtDescripción)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExplorarItems))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripción, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnAgregar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExplorarItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExplorarItemsActionPerformed
        // OJO, SOLO PUEDE MOSTRAR RECURSOS COMPRABLES !! USAR UN FILTRO PASIVO !!
        Tupla tp = (Tupla) cmbConcepto.getSelectedItem();
        
        switch(tp.getId())
        {
            case 0: 
                 ListadoMateriales win = new ListadoMateriales(new FiltroPasivoMateriales(this.proveedor));
                 win.setSeleccionarEnabled(this,GenerarNuevaOrdenDeCompraAgregarRecurso.CALLBACK_SELECCION_MATERIAL);
                 SwingPanel.getInstance().addWindow(win);
                 win.setVisible(true);
                break;
            case 1: 
                 ListadoHerramientas winh = new ListadoHerramientas(new FiltroPasivoHerramientas(this.proveedor));   
                 winh.setSeleccionarEnabled(this,GenerarNuevaOrdenDeCompraAgregarRecurso.CALLBACK_SELECCION_HERRAMIENTA);
                 SwingPanel.getInstance().addWindow(winh);
                 winh.setVisible(true);
                break;
            case 2: 
                ListadoAlquileresCompras winac = new ListadoAlquileresCompras();
                winac.setSeleccionarEnabled(this,GenerarNuevaOrdenDeCompraAgregarRecurso.CALLBACK_SELECCION_ALQUILERCOMPRA);
                SwingPanel.getInstance().addWindow(winac);
                winac.setVisible(true);    
                break;
            case 3: 
                ListadoAdicionales winad = new ListadoAdicionales();
                winad.setSeleccionarEnabled(this,GenerarNuevaOrdenDeCompraAgregarRecurso.CALLBACK_SELECCION_ADICIONAL);
                SwingPanel.getInstance().addWindow(winad);
                winad.setVisible(true);  
                break;
        }
    }//GEN-LAST:event_btnExplorarItemsActionPerformed

    private void btnConsultarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarPreciosActionPerformed
        if(this.itemSeleccionado!=null && this.proveedor!=null){
            pantallaConsultarPrecioXRecurso win = new pantallaConsultarPrecioXRecurso(itemSeleccionado, this.proveedor);
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true); 
        }else{
            pantallaConsultarPrecioXRecurso win = new pantallaConsultarPrecioXRecurso();
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }
    }//GEN-LAST:event_btnConsultarPreciosActionPerformed

    private void cmbConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConceptoActionPerformed
        txtItem.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        lblUnidadDeMedida.setText("[UN.]");
    }//GEN-LAST:event_cmbConceptoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Validar Ventana
        if(validar())
        {       
            DetalleOrdenDeCompra id = new DetalleOrdenDeCompra();
            id.setCantidad(Float.parseFloat(txtCantidad.getText()));
            id.setItem(itemSeleccionado);
            id.setPrecioUnitario(Double.parseDouble(txtPrecio.getText()));
            id.setDescripcion(txtDescripción.getText());

            Object[] retorno = new Object[1];
            retorno[0] = id;
            this.callback.actualizarConObjeto(GenerarNuevaOrdenDeCompra.CALLBACK_SELECCION_ITEMDETALLE,DetalleOrdenDeCompra.class,retorno);

            this.dispose();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsultarPrecios;
    private javax.swing.JButton btnExplorarItems;
    private javax.swing.JComboBox cmbConcepto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblUnidadDeMedida;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripción;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables

    private void initComboConceptos() {
        cmbConcepto.removeAllItems();
        for (int i = 0; i < conceptos.length; i++) {
            String c = conceptos[i];
            Tupla tp = new Tupla(i,conceptos[i]);
            cmbConcepto.addItem(tp);
        }
    }

    @Override
    public void actualizar(int id, String flag, Class tipo) {
        // Ahora tengo que generar el Item Comprable
        if(CALLBACK_SELECCION_HERRAMIENTA.equals(flag) || CALLBACK_SELECCION_MATERIAL.equals(flag))
        {
             ItemComprable ic = new ItemComprable(tipo, id);
             this.itemSeleccionado = ic;
             
             RecursoEspecifico re = getRecursoEspecifico(id);
             if(re!=null)
             {
                 txtItem.setText(re.getNombreRecurso()+" - "+re.getNombre());
                 lblUnidadDeMedida.setText("["+re.getRecurso().getUnidadDeMedida().getAbreviatura() +"]");
             }
        }
        if(CALLBACK_SELECCION_ALQUILERCOMPRA.equals(flag))
        {
            ItemComprable ic = new ItemComprable(tipo, id);
            this.itemSeleccionado = ic;
            
            TipoAlquilerCompra tac = getTipoAlquilerCompra(id);
            if(tac!=null)
            {
                txtItem.setText(tac.getNombre());
                lblUnidadDeMedida.setText("["+UnidadDeMedida.UNIDAD_BASE_ABREVIATURA+"]");
            }
        }
        if(CALLBACK_SELECCION_ADICIONAL.equals(flag))
        {
            ItemComprable ic = new ItemComprable(tipo, id);
            this.itemSeleccionado = ic;
            
            TipoAdicional ta = getTipoAdicional(id);
            if(ta!=null)
            {
                txtItem.setText(ta.getNombre());
                lblUnidadDeMedida.setText("["+UnidadDeMedida.UNIDAD_BASE_ABREVIATURA+"]");
            }            
        }
    }
    
    private RecursoEspecifico getRecursoEspecifico(int id)
    {
        RecursoEspecifico re = null;
        try
        {
            HibernateUtil.beginTransaction();
            re = (RecursoEspecifico)HibernateUtil.getSession().load(RecursoEspecifico.class, id);
            HibernateUtil.commitTransaction();
            return re;

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar la Orden De Compra\n"+e.getMessage());
            e.printStackTrace();
        }         
        return null;
    }
    
    /**
     * Muestra un mensaje
     * @param tipo
     * @param titulo
     * @param mensaje 
     */
    public void mostrarMensaje(int tipo,String titulo,String mensaje)
    {
         JOptionPane.showMessageDialog(this.getParent(),mensaje,titulo,tipo);
    }

    private TipoAlquilerCompra getTipoAlquilerCompra(int id) {
        TipoAlquilerCompra tac = null;
        try
        {
            HibernateUtil.beginTransaction();
            tac = (TipoAlquilerCompra)HibernateUtil.getSession().load(TipoAlquilerCompra.class, id);
            HibernateUtil.commitTransaction();
            return tac;

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar el Aquiler o Compra\n"+e.getMessage());
            e.printStackTrace();
        }         
        return null;
    }

    private TipoAdicional getTipoAdicional(int id) {
        TipoAdicional tac = null;
        try
        {
            HibernateUtil.beginTransaction();
            tac = (TipoAdicional)HibernateUtil.getSession().load(TipoAdicional.class, id);
            HibernateUtil.commitTransaction();
            return tac;

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","Se produjo un error al cargar el Adicional\n"+e.getMessage());
            e.printStackTrace();
        }         
        return null;
    }

    /**
     * Valido que todos los datos sean correctos antes de regresar
     * @return 
     */
    private boolean validar() {
        boolean validado = true;
        StringBuilder msg = new StringBuilder("<HTML>Para continuar, asegurese que:");
        
        // Fecha de la Orden de Compra
        if(this.itemSeleccionado==null || this.txtItem.getText().isEmpty())
        {
            msg.append("<br>- Elegir un <b>Item</b> para el detalle de la orden de compra");
            validado = false;
        }
        // Cantidad no nula
        if(txtCantidad.getText().isEmpty())
        {
            msg.append("<br>- Ingrese la <b>Cantidad</b> del item seleccionado que desee ingresar");
            validado = false;
        }
        // Cantidad Numerica
        try
        {
            double cantidad = Double.parseDouble(txtCantidad.getText());
        }catch(NumberFormatException ex)
        {
            msg.append("<br>- La <b>Cantidad</b> ingresada debe tener un valor numérico");
            validado = false;
        }
        // Precio no nulo
        if(txtPrecio.getText().isEmpty())
        {
            msg.append("<br>- Ingrese el <b>Precio Unitario</b> del item seleccionado");
            validado = false;
        }
        // Precio Numerico
        try
        {
            double precio = Double.parseDouble(txtPrecio.getText());
        }catch(NumberFormatException ex)
        {
            msg.append("<br>- El <b>Precio</b> ingresado debe tener un valor numérico");
            validado = false;
        }        
        
        
        if(!validado)
        {
            mostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error de Validación!",msg.toString());
        }
        return validado;
    }
}
