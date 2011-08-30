/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.xml;

import java.net.URL;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
/**
 *
 * @author iuga
 */
public abstract class XMLReader {

     SAXBuilder builder;
     Document doc;

    // BugFix: No anda en los jars directamente si es String, paso a URL:
    public XMLReader(URL urlXml) {

         try {
               builder=new SAXBuilder(false);
               doc=(Document) builder.build(urlXml);
             }
             catch (Exception e)
             {
               System.out.println("URL XML:"+urlXml.getPath());
               e.printStackTrace();
             }
    }



}
