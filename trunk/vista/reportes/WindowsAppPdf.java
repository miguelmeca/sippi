/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import java.io.File;
import java.io.IOException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Iuga
 */
public class WindowsAppPdf implements IAppPdf {
    
    public void open(File f) throws IOException, InterruptedException
    {
        if (f.exists()) 
        {
        	Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+f.getCanonicalPath());
		p.waitFor();
 
	} 
        else 
        {
                JOptionPane.showMessageDialog(new JInternalFrame(),"El archivo no existe","Error!",JOptionPane.WARNING_MESSAGE);
	}

    }
    
}
