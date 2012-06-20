
package vista.gen;

import controlador.xml.XMLReader;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import vista.gen.renders.RenderAbstracto;
import vista.gen.renders.RenderString;
import vista.gui.sidebar.TreeEntry;

/**
 * @author Iuga
 */
public class PantallaABMGenericaParseHibXml extends XMLReader{

    public PantallaABMGenericaParseHibXml(URL urlXml) {
        super(urlXml);
    }

    public ArrayList<RenderAbstracto> parseEntidad(ArrayList<String[]> campos)
    {
        ArrayList<RenderAbstracto> listaRenders = new ArrayList<RenderAbstracto>();
        
        try
        {
             Element raiz=doc.getRootElement();
             List clases =raiz.getChildren("class");
             
             if(!clases.isEmpty())
              {
                Iterator it = clases.iterator();
                while (it.hasNext())
                {
                    Element e = (Element)it.next();
                    
                    // PARSE PROPERTIES ========================================
                    List properties = e.getChildren("property");
                    if(!properties.isEmpty())
                    {
                        Iterator itpr = properties.iterator();
                        while (itpr.hasNext())
                        {
                            Element ep = (Element)itpr.next();
                            listaRenders.add(createBasicRender(ep.getAttributeValue("type"), ep,campos));
                        }
                    }
                }
              }
        }catch (Exception e){
               e.printStackTrace();
               System.err.println("[ERROR]"+e.getMessage());
        }
        return listaRenders;
    }
 
    private RenderAbstracto createBasicRender(String type, Element e,ArrayList<String[]> campos)
    {       
        String nombre = "";
        for (int i = 0; i < campos.size(); i++) {
            String[] s = campos.get(i);
            if(s[0].equals(e.getAttribute("name").getValue()))
            {
                nombre = s[1];
                continue;
            }
        }
        
        RenderAbstracto render = null;
        if(type.equals("string"))
        {
            render = new RenderString(e,nombre);
        }
        return render;
    }
    
}
