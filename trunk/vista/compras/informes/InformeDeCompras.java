/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras.informes;

import vista.reportes.sources.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Planificacion;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Administrador
 */
public class InformeDeCompras extends ReportDesigner {
    
    public static final String PARAM_FILTROS     = "FILTROS";
    public static final String PARAM_FILTRO_FECHAINICIO = "Fecha de Inicio";
    public static final String PARAM_FILTRO_FECHAFIN = "Fecha de Fin";

    public InformeDeCompras() {
    }
    
    /**
     * Hace la cabecera comun a todos los informes de planificacion
     * @param params
     * @throws DocumentException 
     */
    protected void makeCabecera(HashMap<String,Object> params)throws DocumentException
    {
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
            PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nPre = new Phrase("Filtros Aplicados",ReportDesigner.FUENTE_TITULO_2);
            PTitulo.add(nPre); 
            
        Iterator itFiltros = params.entrySet().iterator();
        while(itFiltros.hasNext())
        {
            Map.Entry filtro = (Map.Entry)itFiltros.next();
            String nombreFiltro = filtro.getKey().toString();
            String valorFiltro = filtro.getValue().toString();
            
            Phrase nEM = new Phrase("\n"+nombreFiltro+": ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEM);  
            Phrase nEMD = new Phrase(valorFiltro+".",ReportDesigner.FUENTE_NORMAL);
            PTitulo.add(nEMD); 
        }
        
        super.doc.add(PTitulo);
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        makeCabecera(params);
    }
    
}
