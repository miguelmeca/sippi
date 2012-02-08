/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.imagenes;

import java.io.File;

/**
 *
 * @author Fran
 */
public class FileFilterUtil 
{
    public static String getExtension(File f) 
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }     
}
