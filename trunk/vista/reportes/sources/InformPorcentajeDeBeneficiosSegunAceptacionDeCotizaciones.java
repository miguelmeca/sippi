package vista.reportes.sources;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import controlador.utiles.gestorBDvarios;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Cotizacion;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import util.FechaUtil;
import util.NTupla;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeAreas;
import vista.reportes.sources.graficos.GraficoDePareto;

/**
 *
 * @author Iuga
 */
public class InformPorcentajeDeBeneficiosSegunAceptacionDeCotizaciones extends ReportDesigner{

    private List<PedidoObra> obras;
    private Date fechaInicio;
    private Date fechaFin;
    private HashMap<String,Integer> listaObrasHistorico;
    private HashMap<String,Integer> historicoXcliente;
    
    private NTupla empreasaCliente;
    private double porcentAceptadasMaximo;
    private double porcentAceptadasMinimo;
    private double porcentAceptadasPromedio;
    
    private double porcentRechazadasMaximo;
    private double porcentRechazadasMinimo;
    private double porcentRechazadasPromedio;
    boolean tieneAceptadas;
        boolean tieneRechazadas;
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        tieneAceptadas = true;
        tieneRechazadas = true;
        this.empreasaCliente = (NTupla)params.get("Cliente");
        
        
        try{
        porcentAceptadasMaximo = (Double)params.get("porcentajeAceptadasMaximo");
        porcentAceptadasMinimo = (Double)params.get("porcentajeAceptadasMinimo");
        porcentAceptadasPromedio = (Double)params.get("porcentajeAceptadasPromedio");
        }
        catch(NullPointerException npe){
            tieneAceptadas=false;
        }
        
        try{
        porcentRechazadasMaximo = (Double)params.get("porcentajeRechazadasMaximo");
        porcentRechazadasMinimo = (Double)params.get("porcentajeRechazadasMinimo");
        porcentRechazadasPromedio = (Double)params.get("porcentajeRechazadasPromedio");
        }
        catch(NullPointerException npe){
            tieneRechazadas = false;
        }
        
        
        fechaInicio = (Date) params.get("INFORME_FECHA_INICIO");         
        fechaFin = (Date) params.get("INFORME_FECHA_FIN");
        
        
        mostrarObjetivoDelInforme();
        mostrarCliente();
        mostrarFechasFiltro();
        mostrarTablaResultados();
    }

    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Beneficios de obras anteriores segun aceptacion o rechazo de cotizaciones.",ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }
    
    private void mostrarCliente() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Cliente: ",ReportDesigner.FUENTE_INFORMES_NORMAL_B));
        String cliente;
        if(empreasaCliente.getId()==0){
            cliente="Todos";
        }
        else{
            cliente=empreasaCliente.getNombre();
        }
        PTitulo.add( new Phrase(cliente,ReportDesigner.FUENTE_INFORMES_NORMAL_B));
        super.doc.add(PTitulo);
    }  
    
    private void mostrarFechasFiltro() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Fecha de Inicio: ",ReportDesigner.FUENTE_INFORMES_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(fechaInicio),ReportDesigner.FUENTE_INFORMES_NORMAL));
        PTitulo.add( new Phrase(" - Fecha de Fin: ",ReportDesigner.FUENTE_INFORMES_NORMAL_B));        
        PTitulo.add( new Phrase(FechaUtil.getFecha(fechaFin),ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }   
      
    
    private void mostrarTablaResultados() throws DocumentException {
        
        
        float[] anchos = {5f,5f, 5f};
        super.insertarSubTitulo("Beneficio en cotizaciones Aceptadas");
        
        if(tieneAceptadas) {
            PdfPTable tablaA = new PdfPTable(3);
            tablaA.setWidthPercentage(100);
            tablaA.setSpacingBefore(5f);

            tablaA.setWidths(anchos);

            insertarCeldaHeaderEnTabla(tablaA,"Beneficio Minimo",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
            insertarCeldaHeaderEnTabla(tablaA,"Beneficio Promedio",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
            insertarCeldaHeaderEnTabla(tablaA,"Beneficio Máximo",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);

            insertarCeldaEnTabla(tablaA,porcentAceptadasMinimo+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
            insertarCeldaEnTabla(tablaA,porcentAceptadasPromedio+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
            insertarCeldaEnTabla(tablaA,porcentAceptadasMaximo+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);        

            super.doc.add(tablaA);    
        }
        else{
            Paragraph result = new Paragraph();
            result.setAlignment(Paragraph.ALIGN_LEFT);      
            result.add( new Phrase("No se encontraron cotizaciones aceptadas", ReportDesigner.FUENTE_INFORMES_NORMAL));
            super.doc.add(result);
        }
        
        super.insertarSubTitulo("Beneficio en cotizaciones Rechazadas"); 
        
        
        if(tieneRechazadas) {
            PdfPTable tablaR = new PdfPTable(3);
            tablaR.setWidthPercentage(100);
            tablaR.setSpacingBefore(5f);
            tablaR.setWidths(anchos);

            insertarCeldaHeaderEnTabla(tablaR,"Beneficio Minimo",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
            insertarCeldaHeaderEnTabla(tablaR,"Beneficio Promedio",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
            insertarCeldaHeaderEnTabla(tablaR,"Beneficio Máximo",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);

            insertarCeldaEnTabla(tablaR,porcentRechazadasMinimo+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
            insertarCeldaEnTabla(tablaR,porcentRechazadasPromedio+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
            insertarCeldaEnTabla(tablaR,porcentRechazadasMaximo+" %",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);        

            super.doc.add(tablaR); 
        }
        else{
            Paragraph result = new Paragraph();
            result.setAlignment(Paragraph.ALIGN_LEFT);      
            result.add( new Phrase("No se encontraron cotizaciones rechazadas", ReportDesigner.FUENTE_INFORMES_NORMAL));
            super.doc.add(result);
        }
        
    }







    
    
    
}
