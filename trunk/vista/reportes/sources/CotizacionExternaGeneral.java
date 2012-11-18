/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXHerramienta;
import modelo.SubObraXMaterial;
import modelo.SubObraXTarea;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class CotizacionExternaGeneral extends ReportDesigner{

    private int cotizacion_id;
    
    public CotizacionExternaGeneral(int cot_id) throws FileNotFoundException, DocumentException
    {
       this.cotizacion_id = cot_id;
    }

    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        // Cotización Nro
        Paragraph PNroCotizacion = new Paragraph();
        PNroCotizacion.setAlignment(Paragraph.ALIGN_RIGHT);
        Phrase nroCot = new Phrase("Presupuesto Nº: "+(String)params.get("COTIZACION_NRO"),new Font(Font.FontFamily.HELVETICA,11,Font.BOLD));
        PNroCotizacion.add(nroCot);
        super.doc.add(PNroCotizacion);
        
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
        Phrase nPre = new Phrase("Presupuesto",ReportDesigner.FUENTE_TITULO_1);
        PTitulo.add(nPre);
            
        Phrase nMD = new Phrase("\nMemoria Descriptiva:\n",ReportDesigner.FUENTE_TITULO_2);
        PTitulo.add(nMD);

        Phrase nMemDesc = new Phrase((String)params.get("COTIZACION_MEMDESC"),ReportDesigner.FUENTE_NORMAL);
        PTitulo.add(nMemDesc);
            
        super.doc.add(PTitulo);

        // TOTAL
        Paragraph pTotal = new Paragraph();
        pTotal.setAlignment(Paragraph.ALIGN_RIGHT);
        pTotal.add(new Phrase("Monto Total: "+(String)params.get("COTIZACION_TOTAL"),ReportDesigner.FUENTE_NORMAL_B));
        //pTotal.add(new Phrase("\n",ReportDesigner.FUENTE_NORMAL_B)+"\n");
        super.doc.add(pTotal);

        // CUERPO
        Paragraph pFoot = new Paragraph();
        pFoot.setAlignment(Paragraph.ALIGN_LEFT);
        pFoot.add(new Phrase("\nForma de Pago: "));
        pFoot.add(new Phrase((String)params.get("FORMA_DE_PAGO"), ReportDesigner.FUENTE_NORMAL));
        pFoot.add(new Phrase("\nPlazo de Entrega: "));
        pFoot.add(new Phrase((String)params.get("PLAZO_ENTREGA"), ReportDesigner.FUENTE_NORMAL));
        pFoot.add(new Phrase("\nLugar de Entrega: "));
        pFoot.add(new Phrase((String)params.get("LUGAR_ENTREGA"), ReportDesigner.FUENTE_NORMAL));
        pFoot.add(new Phrase("\nValidez de la Oferta: "));
        pFoot.add(new Phrase((String)params.get("VALIDEZ_OFERTA"), ReportDesigner.FUENTE_NORMAL));

        pFoot.add(new Phrase("\nSin otro particular saludamos a Uds. muy atte. Y quedamos a su disposición.\n", ReportDesigner.FUENTE_NORMAL));

        pFoot.add(new Phrase("Nota: Los valores consignados no incluyen IVA.", ReportDesigner.FUENTE_NORMAL_B));

        pFoot.add(new Phrase("\n"));
        pFoot.add(new Phrase("\n"));
        pFoot.add(new Phrase("\n"));

        super.doc.add(pFoot);

        Paragraph pFoot2 = new Paragraph();
        pFoot2.setAlignment(Paragraph.ALIGN_RIGHT);
        pFoot2.add(new Phrase("Aldo Romero\n", ReportDesigner.FUENTE_NORMAL_B));

        pFoot2.add(new Phrase("MetAr S.R.L.", ReportDesigner.FUENTE_NORMAL_B));

        super.doc.add(pFoot2);
        
    }
}
