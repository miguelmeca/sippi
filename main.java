
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.EstadoTallerCapacitacion;
import modelo.TallerCapacitacion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */


public class main
{
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {                   
                    // LANZO LA INTERFAZ
                    new Loading();
            }
        });
    }
}
