/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import java.io.FileNotFoundException;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class CotizacionInterna extends ReportDesigner{

    private int cotizacion_id;
    
    public CotizacionInterna(int cot_id) throws FileNotFoundException, DocumentException
    {
       this.cotizacion_id = cot_id;
    }

    @Override
    protected void makeCuerpo() throws DocumentException
    {
        
//         Edit
        super.doc.add(new Paragraph("Hello World!"));
              
    }
    
    
    
    
    
}
