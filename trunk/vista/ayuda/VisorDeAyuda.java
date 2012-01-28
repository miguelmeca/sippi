/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ayuda;

import com.hackelare.helpeame.HelpView;
import java.io.File;

/**
 * @author Iuga
 */
public class VisorDeAyuda {
 
   private static VisorDeAyuda instance = null;
   private File indiceFile;
   private File helpFile;
   private HelpView hv;
   
   public static VisorDeAyuda getInstance()
   {
      if(instance == null) {
         instance = new VisorDeAyuda();
      }
      return instance;
   }

    private VisorDeAyuda() {
        try
        {      
            indiceFile = new File(getClass().getResource("/config/menuAyuda.xml").toURI());
            helpFile   = new File(getClass().getResource("/config/GuiadeUsuario.pdf").toURI());
        }
        catch(Exception e)
        {
            System.err.println("No se pudo cargar las configuraciones de la Ayuda");
        }
    }
   
   public void mostrarAyuda()
   {
       if(hv==null)
       {
            HelpView hv = new HelpView(indiceFile,helpFile);
       }
       
       if(hv==null)
       {
            hv.view(1);       
       }
       else
       {
           System.err.println("La libreria de ayuda anda para el orto, arreglala antes de seguir perdiendo tiempo");
       }
   }
   
    
}
