
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
//import org.jfree.ui.RefineryUtilities;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.ui.RefineryUtilities;
import test.DBExamplesLoader;
import util.HibernateUtil;
import vista.VentanaPrincipal;
import vista.users.UserLogin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Loading.java
 *
 * Created on 19/05/2010, 20:16:25
 */

/**
 *
 * @author iuga
 */
public class Loading extends javax.swing.JFrame {

    /** Creates new form Loading */
    public Loading() {

        initComponents();
        habilitarVentana();

        // Tienen que sumar 100
        CargarLibrerias(10,"Cargando Librerías...");
        CargarHibernate(50,"Cargando Hibernate...");
        CargarEjemplos(40,"Cargando Datos iniciales...");

        // Termino
        LanzarSistema();
    }

    private void CargarNetDaemon(int i, String txt)
    {
//        lblMsg.setText(txt);
//
//        // LANZO UN HILO QUE VIGILA EL ESTADO DE LA CONEXION AL SERVER
//        daemonNet demon = new daemonNet();
//        Thread daemonNet = new Thread(demon,"net");
//        daemonNet.setDaemon(true);
//        daemonNet.start(); 
//        // INESTABLE E INFUNCIONAL
//
//        setProgress(jpb.getValue()+i);
    }

    private void CargarEjemplos(int i, String txt)
    {
       int seleccion = -1;
        
       String conn = HibernateUtil.getCadenaConexion();
       
       // Si la base de datos es en memoria, siempre tengo que cargar los datos
       // asi que ni pregunto, los cargo de una
       if(conn.equals("jdbc:hsqldb:mem:testdb"))
       {
           seleccion = 0;
       }
       else
       {
            seleccion = JOptionPane.showOptionDialog(
                       this,
                       "¿Desea cargar los datos de prueba / Inicialización?",
                       "Seleccione una opción",
                       JOptionPane.YES_NO_CANCEL_OPTION,
                       JOptionPane.QUESTION_MESSAGE,
                       null,    // null para icono por defecto.
                       new Object[] { "Si", "No"},   // null para YES, NO y CANCEL
                       "Si");
       }
        if (seleccion != -1)
        {
            if((seleccion + 1)==1)
            {
                lblMsg.setText(txt);
                this.update(this.getGraphics());

                test.DBExamplesLoader load = new DBExamplesLoader();
                load.cargarEjemplos(); // AHORA CARGA TODOS LOS EJEMPLOS
                jpb.setValue(jpb.getValue()+i);

                setProgress(jpb.getValue()+i);
                this.update(this.getGraphics());
            }
            else
            {
                // PRESIONO NO
            }
        }


    }

    private void habilitarVentana()
    {
        this.setSize(400, 267);
        this.setVisible(true);
        this.update(this.getGraphics());
        setProgress(1);
        setProgress(0);
        lblMsg.setText("Inicializando...");
        RefineryUtilities.centerFrameOnScreen(this);
    }

    public void setProgress(final int progress)
    {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            jpb.setValue(progress);
          }
        });
    }

    private void LanzarSistema()
    {
        UserLogin ul = new UserLogin();
        ul.setVisible(true);
        RefineryUtilities.centerFrameOnScreen(ul);
        
        this.dispose();
    }

    // Llama punto por punto cargando las librerias
    private void CargarLibrerias(int x,String txt)
    {
        lblMsg.setText(txt);
        this.update(this.getGraphics());

        CargarLookAndFeel();
        jpb.setValue(20);

        setProgress(jpb.getValue()+x);
        this.update(this.getGraphics());
    }

    // Hace la carga inicial de Hibernate
    private void CargarHibernate(int x, String txt)
    {
        lblMsg.setText(txt);
        this.update(this.getGraphics());

            try{
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession(); // Pruebo la conexion !!
            }
            catch(ExceptionInInitializerError e)
            {
                JOptionPane.showMessageDialog(this,"Error de Hibernate \n "+e.getException().getMessage(),"Error de Conexion", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
            catch(NoClassDefFoundError e)
            {
                JOptionPane.showMessageDialog(this,"No se cargaron las librerías de Hibernate \n "+e.getMessage(),"Error de Conexion", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this,"Error en la conexion a la base de datos \n "+e.getMessage(),"Error de Conexion", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        
        setProgress(jpb.getValue()+x);
        this.update(this.getGraphics());
    }

    private void CargarLookAndFeel()
    {
                // YEAH YA CRAKEE LA LLAVE Y ME DIO ESTO :
                // C1410294-61B64AAC-4B7D3039-834A82A1-37E5D695
                String[] li = { "Licensee=AppWork UG", "LicenseRegistrationNumber=289416475", "Product=Synthetica", "LicenseType=Small Business License", "ExpireDate=--.--.----", "MaxVersion=2.999.999" };
	        UIManager.put("Synthetica.license.info", li);
                UIManager.put("Synthetica.license.key","C1410294-61B64AAC-4B7D3039-834A82A1-37E5D695");
                try
                {
                    UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");    
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
                    //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");    
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                }
                catch(Exception e)
                {}
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpb = new javax.swing.JProgressBar();
        lblMsg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        jpb.setDoubleBuffered(true);
        jpb.setStringPainted(true);
        getContentPane().add(jpb);
        jpb.setBounds(10, 225, 380, 17);

        lblMsg.setFont(new java.awt.Font("Dialog", 2, 12));
        lblMsg.setForeground(new java.awt.Color(255, 255, 255));
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMsg.setText("Mensajes de Carga ...");
        getContentPane().add(lblMsg);
        lblMsg.setBounds(10, 240, 380, 16);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/imagenes/Splash.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 270);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jpb;
    private javax.swing.JLabel lblMsg;
    // End of variables declaration//GEN-END:variables

}
