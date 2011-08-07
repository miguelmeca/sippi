/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.gui.sidebar;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import util.SwingPanel;

/**
 * @author iuga
 */
public class TreeEntry
{

    private String titulo;
    private ImageIcon icono;
    private ArrayList<TreeEntry> hijos;
    private String _instance;


    public TreeEntry(String titulo, String urlIcono) {
        this();
        setTitle(titulo);
        setIcon(urlIcono);
    }

    private TreeEntry() {
        this.hijos = new ArrayList<TreeEntry>();
    }

    private void setTitle(String titulo) {
        this.titulo = titulo;
    }

    private void setIcon(String urlIcono) {
        if (urlIcono != null)
	{
             this.icono =  new javax.swing.ImageIcon(getClass().getResource(urlIcono));
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public ImageIcon getIcono() {
        return icono;
    }

   public ArrayList<TreeEntry> getEntries() {
        return hijos;
    }

    public int indexOf(Object child) {
        return hijos.indexOf(child);
    }

    public int size() {
        return hijos.size();
    }

    public Object get(int index) {
        return hijos.get(index);
    }

    public void add(TreeEntry treeEntry) {
        hijos.add(treeEntry);
    }

    public String getClassInstance() {
        return _instance;
    }

    public void setClassInstance(String _instance) {
        this._instance = _instance;
    }

    public void clickEvent()
    {
        if(this._instance!=null && !this._instance.isEmpty())
        {
            try
            {
                Class win = Class.forName(_instance);
                JInternalFrame frame = (JInternalFrame)win.newInstance();
                SwingPanel.getInstance().addWindow(frame);
                frame.setVisible(true);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
            
    
}
