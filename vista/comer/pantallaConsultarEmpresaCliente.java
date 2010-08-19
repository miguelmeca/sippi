/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmConsultarEmpresaCliente.java
 *
 * Created on 11-may-2010, 1:23:46
 */

package vista.comer;

import controlador.comer.GestorConsultarEmpresaCliente;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import util.NTupla;
import util.SwingPanel;
import util.Tupla;
import vista.interfaces.IAyuda;
import vista.interfaces.IPantallaEmpresaClienteABM;

/**
 *
 * @author Administrador
 */
public class pantallaConsultarEmpresaCliente extends javax.swing.JInternalFrame implements IAyuda, IPantallaEmpresaClienteABM{
    private GestorConsultarEmpresaCliente gestor;
    /** Creates new form frmConsultarEmpresaCliente */
    public pantallaConsultarEmpresaCliente() {
        initComponents();
        gestor = new GestorConsultarEmpresaCliente(this);
        //gestor.buscarEmpresasClientes();
    }

    pantallaConsultarEmpresaCliente(int idEmpresaClienteSeleccionado) {
        initComponents();
        gestor = new GestorConsultarEmpresaCliente(this);
        //gestor.buscarEmpresasClientes();
        gestor.seleccionEmpresaCliente(idEmpresaClienteSeleccionado);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pnlDatosCliente = new javax.swing.JPanel();
        lblCUIT = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblLocalidad = new javax.swing.JLabel();
        txtCUIT = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JLabel();
        txtLocalidad = new javax.swing.JLabel();
        pnlTelefonosContacto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        srcTelefonos = new javax.swing.JScrollPane();
        tblTelefonos = new javax.swing.JTable();
        pnlDatosPlanta = new javax.swing.JPanel();
        btnConsultarPlanta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        srcPlantas = new javax.swing.JScrollPane();
        tblPlantas = new javax.swing.JTable();
        lblEmailEmp = new javax.swing.JLabel();
        txtEmailEmp = new javax.swing.JLabel();
        lblProvincia = new javax.swing.JLabel();
        lblPais = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JLabel();
        txtPais = new javax.swing.JLabel();
        txtPaginaWeb = new javax.swing.JLabel();
        lblPaginaWeb = new javax.swing.JLabel();
        txtBarrio = new javax.swing.JLabel();
        lblLocalidad1 = new javax.swing.JLabel();
        lblCUIT1 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        txtEmpresaCliente = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Consultar Datos de una Empresa Cliente");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Empresa Cliente:");

        pnlDatosCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Cliente"));

        lblCUIT.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblCUIT.setText("CUIT : ");

        lblDireccion.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDireccion.setText("Dirección:");

        lblLocalidad.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLocalidad.setText("Localidad: ");

        pnlTelefonosContacto.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefonos de Contacto"));

        srcTelefonos.setAutoscrolls(true);

        tblTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Fijo", "Fax", "Celular "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        srcTelefonos.setViewportView(tblTelefonos);

        jScrollPane1.setViewportView(srcTelefonos);

        javax.swing.GroupLayout pnlTelefonosContactoLayout = new javax.swing.GroupLayout(pnlTelefonosContacto);
        pnlTelefonosContacto.setLayout(pnlTelefonosContactoLayout);
        pnlTelefonosContactoLayout.setHorizontalGroup(
            pnlTelefonosContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefonosContactoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlTelefonosContactoLayout.setVerticalGroup(
            pnlTelefonosContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefonosContactoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDatosPlanta.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de las plantas"));

        btnConsultarPlanta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/search.png"))); // NOI18N
        btnConsultarPlanta.setText("Consultar Datos");
        btnConsultarPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarPlantaActionPerformed(evt);
            }
        });

        tblPlantas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Razón Social", "Localidad", "Provincia"
            }
        ));
        tblPlantas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tblPlantas.setAutoscrolls(false);
        srcPlantas.setViewportView(tblPlantas);

        jScrollPane2.setViewportView(srcPlantas);

        javax.swing.GroupLayout pnlDatosPlantaLayout = new javax.swing.GroupLayout(pnlDatosPlanta);
        pnlDatosPlanta.setLayout(pnlDatosPlantaLayout);
        pnlDatosPlantaLayout.setHorizontalGroup(
            pnlDatosPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPlantaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConsultarPlanta, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)))
        );
        pnlDatosPlantaLayout.setVerticalGroup(
            pnlDatosPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosPlantaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConsultarPlanta))
        );

        lblEmailEmp.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblEmailEmp.setText("e-mail :");

        lblProvincia.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblProvincia.setText("Provincia:");

        lblPais.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPais.setText("País:");

        lblPaginaWeb.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPaginaWeb.setText("Página Web:");

        lblLocalidad1.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLocalidad1.setText("Barrio:");

        lblCUIT1.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblCUIT1.setText("Estado:");

        javax.swing.GroupLayout pnlDatosClienteLayout = new javax.swing.GroupLayout(pnlDatosCliente);
        pnlDatosCliente.setLayout(pnlDatosClienteLayout);
        pnlDatosClienteLayout.setHorizontalGroup(
            pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblEmailEmp)
                                .addGap(10, 10, 10)
                                .addComponent(txtEmailEmp, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDatosClienteLayout.createSequentialGroup()
                                .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProvincia)
                                    .addComponent(lblDireccion))
                                .addGap(6, 6, 6)
                                .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblLocalidad1)
                                .addGap(2, 2, 2)
                                .addComponent(txtBarrio, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblPais)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPais, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblPaginaWeb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPaginaWeb, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblLocalidad)
                                .addGap(2, 2, 2)
                                .addComponent(txtLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                                .addComponent(lblCUIT1)
                                .addGap(30, 30, 30))
                            .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlDatosPlanta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlTelefonosContacto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                        .addComponent(lblCUIT)
                        .addGap(30, 30, 30))
                    .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(txtCUIT, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                .addGap(282, 282, 282))
        );
        pnlDatosClienteLayout.setVerticalGroup(
            pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCUIT)
                            .addComponent(txtCUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLocalidad1)
                            .addComponent(txtBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProvincia)
                            .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmailEmp)
                            .addComponent(txtEmailEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDatosClienteLayout.createSequentialGroup()
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCUIT1)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLocalidad)
                            .addComponent(txtLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPais)
                            .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPaginaWeb)
                            .addComponent(txtPaginaWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(pnlTelefonosContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDatosPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/iconos/var/16x16/delete.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        txtEmpresaCliente.setEnabled(false);
        txtEmpresaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpresaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(418, 418, 418)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEmpresaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarPlantaActionPerformed
        //HAY QUE PASARLE EL ID DE LA PLANTA, lo Busco
        DefaultTableModel modelo = (DefaultTableModel) tblPlantas.getModel();
        int fila = tblPlantas.getSelectedRow();
        if(fila!=1)
        {
            NTupla nt=(NTupla) tblPlantas.getValueAt(fila,0);
            if(nt.getId() != 0)
            {
                pantallaConsultarPlantas pco = new pantallaConsultarPlantas(nt.getId());
                SwingPanel.getInstance().addWindow(pco);
                pco.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(this.getParent(),"El identificador de la planta no es valido","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }



    }//GEN-LAST:event_btnConsultarPlantaActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtEmpresaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpresaClienteActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantallaConsultarEmpresaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnConsultarPlanta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCUIT;
    private javax.swing.JLabel lblCUIT1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmailEmp;
    private javax.swing.JLabel lblLocalidad;
    private javax.swing.JLabel lblLocalidad1;
    private javax.swing.JLabel lblPaginaWeb;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JPanel pnlDatosCliente;
    private javax.swing.JPanel pnlDatosPlanta;
    private javax.swing.JPanel pnlTelefonosContacto;
    private javax.swing.JScrollPane srcPlantas;
    private javax.swing.JScrollPane srcTelefonos;
    private javax.swing.JTable tblPlantas;
    private javax.swing.JTable tblTelefonos;
    private javax.swing.JLabel txtBarrio;
    private javax.swing.JLabel txtCUIT;
    private javax.swing.JLabel txtDireccion;
    private javax.swing.JLabel txtEmailEmp;
    private javax.swing.JTextField txtEmpresaCliente;
    private javax.swing.JLabel txtEstado;
    private javax.swing.JLabel txtLocalidad;
    private javax.swing.JLabel txtPaginaWeb;
    private javax.swing.JLabel txtPais;
    private javax.swing.JLabel txtProvincia;
    // End of variables declaration//GEN-END:variables
/**
    public void mostrarRazonSocial(ArrayList<Tupla> tuplas) {

        ArrayList<Tupla> lista = tuplas;

        DefaultComboBoxModel valores = new DefaultComboBoxModel();

        Iterator<Tupla> it = lista.iterator();
        while(it.hasNext()){
            Tupla tu = it.next();
            valores.addElement(tu);
        }
        //cmbEmpresas.setModel(valores);
    }
**/
    public void mostrarEstadoEmpresa(String estado) {
        this.txtEstado.setText(estado);
    }

    public void mostrarRZEmpresa(String razonSocial) {
        this.txtEmpresaCliente.setText(razonSocial);
    }

    public void mostrarCUITEmpresa(String cuit) {
        this.txtCUIT.setText(cuit);
    }

    public void mostrarEmailEmpresa(String email) {
        this.txtEmailEmp.setText(email);
    }

    public void mostrarPaginaWebEmpresa(String paginaWeb) {
        this.txtPaginaWeb.setText(paginaWeb);
    }

    public void mostrarDomicilioEmpresa(String direccion) {
        this.txtDireccion.setText(direccion);
    }

    public void mostrarBarrioEmpresa(String barrio) {
        this.txtBarrio.setText(barrio);
    }

    public void mostrarLocalidadEmpresa(String localidad) {
        this.txtLocalidad.setText(localidad);
    }

    public void mostrarProvinciaEmpresa(String provincia) {
        this.txtProvincia.setText(provincia);
    }

    public void mostrarPaisEmpresa(String pais) {
        this.txtPais.setText(pais);
    }

    public void mostrarDatosTelefono(ArrayList<NTupla> listaTelefonos) {
        DefaultTableModel tm = new DefaultTableModel();
        int i = 0;
        Object[] item = new Object[listaTelefonos.size()];
        for (NTupla tel : listaTelefonos) {
            tm.addColumn(tel.getNombre());
            item[i] = (String)tel.getData();
            i++;
        }
        tm.addRow(item);
        tblTelefonos.setModel(tm);
    }

    public void mostrarDatosPlantas(ArrayList<NTupla> listaPlantas) {
        DefaultTableModel tm = new DefaultTableModel();
        tm.addColumn("Planta");
        tm.addColumn("Dirección");
        for (NTupla planta : listaPlantas) {
            Object[] item = new Object[2];
            item[0] = planta;
            item[1] = (String)planta.getData();
            tm.addRow(item);
        }
        tblPlantas.setModel(tm);
    }

    public String getTituloAyuda() {
        return "Opción: Consultar Empresa Cliente";
    }

    public String getResumenAyuda() {
        return "Podrá consultar los datos de las empresas cliente.";
    }

    public int getIdAyuda() {
        return 0;
    }
}
