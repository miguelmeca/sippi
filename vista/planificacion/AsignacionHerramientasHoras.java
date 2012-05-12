/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import modelo.HerramientaDeEmpresa;
import modelo.PlanificacionXXX;
import modelo.SubObraXHerramientaModif;
import modelo.TareaPlanificacion;

/**
 * @author Administrador
 */
public class AsignacionHerramientasHoras extends javax.swing.JInternalFrame {

    private TareaPlanificacion tarea;
    private HerramientaDeEmpresa herramienta;
    private SubObraXHerramientaModif gastos;
    private PlanificacionXXX plan;
    
    public AsignacionHerramientasHoras(PlanificacionXXX plan, TareaPlanificacion tarea,SubObraXHerramientaModif gastos, HerramientaDeEmpresa herramienta) {
        initComponents();
        this.tarea = tarea;
        this.herramienta = herramienta;
        this.gastos = gastos;
        this.plan = plan;
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblHerramientaNombre = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTareaNombre = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblHorasDisponibles = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblHorrasTotales = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblHorasCotizadas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblHorasYaAsignadas = new javax.swing.JLabel();
        btnAsignar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Asignar Herramientas");

        jLabel1.setText("Está asignando");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel2.setText("Horas de uso a la");

        jLabel3.setText("Herramienta:");

        lblHerramientaNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHerramientaNombre.setText("...");

        jLabel4.setText("a la Tarea:");

        lblTareaNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTareaNombre.setText("...");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        jLabel5.setText("Para ésta herramienta quedan");

        lblHorasDisponibles.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasDisponibles.setForeground(new java.awt.Color(0, 153, 0));
        lblHorasDisponibles.setText("- Hs.");

        jLabel7.setText("sin asignar del Total de:");

        lblHorrasTotales.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorrasTotales.setText("- Hs.");

        jLabel9.setText("Cotizadas Originalmente:");

        lblHorasCotizadas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasCotizadas.setText("- Hs.");

        jLabel11.setText("Anteriormente se asignaron");

        lblHorasYaAsignadas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHorasYaAsignadas.setText("- Hs.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(23, 23, 23)
                        .addComponent(lblHorasYaAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(42, 42, 42)
                        .addComponent(lblHorrasTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHorasDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHorasCotizadas, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblHorasYaAsignadas)
                    .addComponent(jLabel5)
                    .addComponent(lblHorasDisponibles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblHorrasTotales)
                    .addComponent(jLabel9)
                    .addComponent(lblHorasCotizadas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAsignar.setText("Asignar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHerramientaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lblTareaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsignar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblHerramientaNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTareaNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAsignar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel lblHerramientaNombre;
    private javax.swing.JLabel lblHorasCotizadas;
    private javax.swing.JLabel lblHorasDisponibles;
    private javax.swing.JLabel lblHorasYaAsignadas;
    private javax.swing.JLabel lblHorrasTotales;
    private javax.swing.JLabel lblTareaNombre;
    // End of variables declaration//GEN-END:variables

    private void init() {
        lblHerramientaNombre.setText(this.herramienta.getNombre()+" ("+this.herramienta.getNroSerie()+")");
        lblTareaNombre.setText(this.tarea.getNombre());
        
        int horasYaAsignadas = PlanificacionUtils.getHorasTotalesAsignadasAHerramienta(this.plan,this.gastos);
        lblHorasYaAsignadas.setText(horasYaAsignadas+" Hs.");
        
        int horasTotal = this.gastos.getHorasDisponibles();
        lblHorrasTotales.setText(horasTotal+" Hs.");
        
        int horasDisponibles = horasTotal - horasYaAsignadas;
        lblHorasDisponibles.setText(horasDisponibles+" Hs.");
        
        if(horasDisponibles==0)
        {
            lblHorasDisponibles.setForeground(Color.RED);
        }
        else if(horasDisponibles>= 1 && horasDisponibles <=5)
        {
            lblHorasDisponibles.setForeground(Color.ORANGE);
        }
        else
        {
            lblHorasDisponibles.setForeground(Color.GREEN);
        }   
        
        if(this.gastos.getOriginal()!=null)
        {
            int horasCotizadas = this.gastos.getOriginal().getHorasDisponibles();
            lblHorasCotizadas.setText(horasCotizadas+" Hs.");
        }
        
    }
}
