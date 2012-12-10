/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.util.HashMap;
import modelo.Planificacion;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Administrador
 */
public class InformeDeEjecucion extends ReportDesigner {
    
    public static final String PARAM_PLAN_NRO     = "PLANIFICAICON_NRO";
    public static final String PARAM_PLAN_EMPRESA = "OBRA_EMPRESA";
    public static final String PARAM_PLAN_PLANTA  = "OBRA_PLANTA";
    public static final String PARAM_PLAN_OBRA    = "PLANIFICAICON_OBRA";
    public static final String PARAM_PLAN_FINICIO = "PLANIFICAICON_FECHA_INICIO";
    public static final String PARAM_PLAN_FFIN    = "PLANIFICAICON_FECHA_FIN";
    
    protected Planificacion planificacion;

    public InformeDeEjecucion(Planificacion planificacion) {
        this.planificacion = planificacion;
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
            Phrase nPre = new Phrase("Datos de la Obra",ReportDesigner.FUENTE_TITULO_2);
            PTitulo.add(nPre);    
            
            Phrase nEM = new Phrase("\nEmpresa: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEM);  
                Phrase nEMD = new Phrase((String)params.get(PARAM_PLAN_EMPRESA),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEMD);  
            Phrase nEPP = new Phrase("\nPlanta: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEPP);  
                Phrase nEPP2 = new Phrase((String)params.get(PARAM_PLAN_PLANTA),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEPP2);                  
            Phrase nOB = new Phrase("\nObra: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nOB); 
                Phrase nEM2 = new Phrase((String)params.get(PARAM_PLAN_OBRA),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEM2);  
            Phrase nFI = new Phrase("\nFecha de Inicio: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nFI);    
                Phrase nEM3 = new Phrase((String)params.get(PARAM_PLAN_FINICIO),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEM3);              
            Phrase nFF = new Phrase("\nFecha de Fin: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nFF);  
                Phrase nEM4 = new Phrase((String)params.get(PARAM_PLAN_FFIN),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEM4);             
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
