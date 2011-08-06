/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vista.VentanaPrincipal;
import vista.gui.FavoritoBean;
import vista.gui.IFavorito;
import vista.interfaces.IAyuda;

/**
 *
 * @author Iuga
 */
public class SwingPanel {

   private static SwingPanel instance = null;

   private JDesktopPane panel;
   private VentanaPrincipal ventanaPrincipal;
   
   // MENU FAVORITOS
   private HashMap<String,FavoritoBean> FAV_ALLOWED_WINS;

   protected SwingPanel() {
      // Exists only to defeat instantiation.
      FAV_ALLOWED_WINS = new HashMap<String, FavoritoBean>();
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

    public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

   
   private void mostrarAyuda(IAyuda win)
   {
        if(win instanceof IAyuda)
        {
            this.ventanaPrincipal.mostrarAyuda(win.getTituloAyuda(),win.getResumenAyuda(),win.getIdAyuda());
        }
   }

   public void addWindow(JInternalFrame win)
   {
       fillFavoritosAllowedWins(win);
       win.setLocation(panel.getWidth()/2 - win.getWidth()/2 ,panel.getHeight()/2 - win.getHeight()/2 + 25);
       // WORKARROUND !!
       if(win instanceof IAyuda)
       {
            mostrarAyuda((IAyuda) win);
       }
       panel.add(win);
   }

   public void mensajeEnConstruccion()
    {
        JOptionPane.showMessageDialog(panel,"Esta funcionalidad se encuentra bajo construcción\n Espere a versiones posteriores para su utilización","En Construcción",JOptionPane.INFORMATION_MESSAGE);
    }

    public void setCargando(boolean flag)
    {
        ventanaPrincipal.mostrarCargando(flag);
    }

    public void anularGUI()
    {
        if(ventanaPrincipal!=null)
        {
            this.ventanaPrincipal.setExtendedState(JFrame.ICONIFIED);
        }
    }

    private void fillFavoritosAllowedWins(JInternalFrame _win)
    {
        if(_win instanceof IFavorito)
        {
            IFavorito ifav = (IFavorito)_win;
            if(ifav.isFavorito())
            {
                // LO AGREGO/UPDATE EN LA TABLA
                updateFavoritos(_win);
            }
        }
    }
    
    private void updateFavoritos(JInternalFrame _win)
    {
        if(FAV_ALLOWED_WINS.containsKey(_win.getClass().getName()))
        {
            // UPDATE
            FavoritoBean fbu = FAV_ALLOWED_WINS.get(_win.getClass().getName());
            fbu.addCount();
        }
        else
        {
            // ADD
            IFavorito ifav = (IFavorito)_win;
            FavoritoBean fb = new FavoritoBean(_win.getClass().getName(),_win.getTitle(),1,ifav.getIconoFavorito());
            FAV_ALLOWED_WINS.put(_win.getClass().getName(), fb);
        }
        updateTree();
    }
    
    private void updateTree()
    {
        this.ventanaPrincipal.updateMenu();
    }
    
    public FavoritoBean[] getFavoritos()
    {
        int flagLlenos = 0;
        FavoritoBean[] lista = new FavoritoBean[5];
        Iterator it = FAV_ALLOWED_WINS.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry e = (Map.Entry)it.next();
            FavoritoBean aux = (FavoritoBean)e.getValue();
            
//            for (int i = 0; i < lista.length; i++) 
//            {
//                FavoritoBean fb = lista[i];
                putFavoritosSort(lista, aux);
//                if(flagLlenos<lista.length)
//                {
//                    if(fb==null)
//                    {
//                        putFavoritosSort(lista, fb);
//                        flagLlenos++;
//                        break;
//                    }
//                }
//                else
//                {
//                    putFavoritosSort(lista,fb);
//                }
//            }
        }  
        return lista;

    }

    private void putFavoritosSort(FavoritoBean[] lista, FavoritoBean fb) 
    {
        for (int i = 0; i < lista.length; i++) 
        {
            FavoritoBean fbi = lista[i];
            if(fb==null)
            {
                return;
            }            
            if(fbi==null)
            {
                lista[i] = fb;
                return;
            }
            if(fb.getCounts()>fbi.getCounts())
            {
                FavoritoBean fbaux = lista[i];
                lista[i] = fb;
                putFavoritosSort(lista, fbaux);
                return;
            }
        }
    }
    


  
    
}
