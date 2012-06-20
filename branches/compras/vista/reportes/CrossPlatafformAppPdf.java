/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Iuga
 */
public class CrossPlatafformAppPdf implements IAppPdf{

    @Override
    public void open(File f) throws IOException, InterruptedException 
    {
        if (f.exists()) 
        {
            if (Desktop.isDesktopSupported()) 
            {
                    Desktop.getDesktop().open(f);
            } 
            else 
            {
                JOptionPane.showMessageDialog(new JInternalFrame(),"Awt Desktop is not supported!","Error!",JOptionPane.WARNING_MESSAGE);
            }
        } 
        else 
        {
           JOptionPane.showMessageDialog(new JInternalFrame(),"El archivo no existe","Error!",JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
