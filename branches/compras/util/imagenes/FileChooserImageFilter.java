
package util.imagenes;

import java.io.File;
import javax.swing.filechooser.FileFilter;


public class FileChooserImageFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) 
        {
            return true;
        }

        String extension = FileFilterUtil.getExtension(f);
        if (extension != null) 
        {
            if (extension.equals("tiff") ||extension.equals("tif") ||extension.equals("gif") || extension.equals("jpeg") || extension.equals("jpg") ||extension.equals("png")) 
            {
                    return true;
            } else 
            {
                return false;
            }
        }
        return false;
    }
        
    
    
    @Override
    public String getDescription() 
    {
        return "Imagenes";
    } 
}