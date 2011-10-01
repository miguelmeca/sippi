/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import java.io.File;
import java.io.IOException;

/**
 * Interface to open pdf document
 * @author Iuga
 */
public interface IAppPdf {
    
    public void open(File f) throws IOException, InterruptedException;
            
}
