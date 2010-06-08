/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.xml;

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

    public XMLReader(String urlXml) {

         try {
               builder=new SAXBuilder(false);
               doc=(Document) builder.build(urlXml);

             }
             catch (Exception e)
             {
               e.printStackTrace();
             }
    }



}
