
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FechaUtil;
import util.HibernateUtil;
import util.SwingPanel;
import vista.testConnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class daemonNet implements Runnable
{

    private testConnection testFrame = new testConnection();
    private boolean conectado;

    public void run()
    {

       String cadena = HibernateUtil.getCadenaConexion();
       cadena = cadena.replaceAll("//","");
       cadena = cadena.replaceAll("/",":");

       Connection conn;

        while(true)
        {
            try {
                try {

                     try
                        {
                            System.out.println("["+FechaUtil.getFechaYHoraActual()+"] "+"NETDAEMON: Probando conexión con la Base de Datos");
                            conn = DriverManager.getConnection(cadena, HibernateUtil.getUsuarioConexion(), HibernateUtil.getPasswordConexion());
                            conectado = true;
                            testFrame.setVisible(false);
                        }
                        catch (SQLException ex)
                        {
                            conectado = false;
                            SwingPanel.getInstance().anularGUI();
                            System.out.println("["+FechaUtil.getFechaYHoraActual()+"] "+"NETDAEMON: No se pudo conectar con la DB");
                            testFrame.setVisible(true);
                            testFrame.addText("["+FechaUtil.getFechaYHoraActual()+"] "+"NETDAEMON: Se perdio la conexión con la BD");


                            while(conectado==false)
                            {
                                 testFrame.addText("["+FechaUtil.getFechaYHoraActual()+"] "+"Reintantando ...");
                                 try
                                    {
                                        System.out.println("["+FechaUtil.getFechaYHoraActual()+"] "+"NETDAEMON: Probando conexión con la Base de Datos");
                                        conn = DriverManager.getConnection(cadena, HibernateUtil.getUsuarioConexion(), HibernateUtil.getPasswordConexion());
                                        conectado = true;
                                        testFrame.setVisible(false);
                                    }
                                    catch (SQLException exo)
                                    {
                                        SwingPanel.getInstance().anularGUI();
                                        testFrame.addText("["+FechaUtil.getFechaYHoraActual()+"] "+"NETDAEMON: Reintantando en 30 segundos...");
                                        Thread.sleep(10000);
                                    }
                            }

                        }

                } catch (Exception ex) {
                  
                }
                
                Thread.sleep(10000);
                
            } catch (InterruptedException ex) {
                
            }
        }

    }

    public boolean getEstadoConexion()
    {
        return conectado;
    }

}
