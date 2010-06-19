/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Iuga
 */
public class SwingPanel {

   private static SwingPanel instance = null;

   private JDesktopPane panel;

   protected SwingPanel() {
      // Exists only to defeat instantiation.
   }
   public static SwingPanel getInstance() {
      if(instance == null) {
         instance = new SwingPanel();
      }
      return instance;
   }

   public void setPane(JDesktopPane pane)
   {
       panel = pane;
   }

   public void addWindow(JInternalFrame win)
   {
       win.setLocation(panel.getWidth()/2 - win.getWidth()/2 ,panel.getHeight()/2 - win.getHeight()/2 - 20);
       panel.add(win);
   }

   public void mensajeEnConstruccion()
    {
        JOptionPane.showMessageDialog(panel,"Esta funcionalidad se encuentra bajo construcción\n Espere a versiones posteriores para su utilización","En Construcción",JOptionPane.INFORMATION_MESSAGE);
    }





}
