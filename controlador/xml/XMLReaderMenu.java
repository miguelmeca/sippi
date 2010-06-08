/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.xml;

import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import vista.gui.sidebar.TreeEntry;

/**
 *
 * @author iuga
 */
public class XMLReaderMenu extends XMLReader {

    private TreeEntry root;

    public XMLReaderMenu(String urlXml) {
        super(urlXml);
        this.root = new TreeEntry("asd","/res/iconos/var/16x16/heart.png");
    }

    public TreeEntry cargarMenu()
    {

        try
        {
             Element raiz=doc.getRootElement();
             List equipos=raiz.getChildren("itemcat");
             if(equipos.size()!=0)
              {
                Iterator it = equipos.iterator();
                while (it.hasNext())
                {
                    Element e = (Element)it.next();
                    TreeEntry nuevo = new TreeEntry(e.getAttributeValue("nombre"),e.getAttributeValue("icono"));
                    root.add(nuevo);

                        List items=e.getChildren("item");
                        if(items.size()!=0)
                          {
                             Iterator i = items.iterator();
                            while (i.hasNext())
                            {
                                Element ex = (Element)i.next();
                                nuevo.add(new TreeEntry(ex.getAttributeValue("nombre"),ex.getAttributeValue("icono")));
                            }
                           }

                }
              }



        }catch (Exception e){
               e.printStackTrace();
            }

        return root;
    }


}
