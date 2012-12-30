/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.xml;

import controlador.users.UserSession;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import vista.gui.sidebar.TreeEntry;
import vista.users.UserLogin;

/**
 *
 * @author iuga
 */
public class XMLReaderMenu extends XMLReader {

    private TreeEntry root;

    public XMLReaderMenu(URL urlXml) {
        super(urlXml);
        this.root = new TreeEntry("_ROOT_", null);
    }

    public TreeEntry cargarMenu()
    {

        try
        {
             Element raiz=doc.getRootElement();
             List equipos=raiz.getChildren("itemcat");
             if(!equipos.isEmpty())
              {
                Iterator it = equipos.iterator();
                while (it.hasNext())
                {
                    Element e = (Element)it.next();
                    TreeEntry nuevo = new TreeEntry(e.getAttributeValue("nombre"),e.getAttributeValue("icono"));
                    root.add(nuevo);

                        List items=e.getChildren("item");
                        if(!items.isEmpty())
                          {
                            Iterator i = items.iterator();
                            while (i.hasNext())
                            {
                                Element ex = (Element)i.next();
                                TreeEntry item = new TreeEntry(ex.getAttributeValue("nombre"),ex.getAttributeValue("icono"));
                                item.setClassInstance(ex.getAttributeValue("instance"));
                                if(ex.getAttributeValue("permisos")!=null && !ex.getAttributeValue("permisos").isEmpty()){
                                    String permisosNecesarios = ex.getAttributeValue("permisos");
                                    
                                    if(UserSession.getInstance().tienePermisos(permisosNecesarios)){
                                        item.setPermiso(true);
                                    }else{
                                        item.setPermiso(false);
                                    }         
                                }
                                else{
                                    item.setPermiso(true);
                                }
                                nuevo.add(item);
                            }
                           }
                }
              }

        }catch (Exception e){
            System.err.println("Error al cargar el menú...");
            e.printStackTrace();
        }

        return root;
    }


}
