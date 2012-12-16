/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.PedidoObra;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeAreas;

/**
 *
 * @author Administrador
 */
public class InformeObrasPorAnio extends ReportDesigner{
    
    private HashMap<String,Integer> listaObrasHistorico;
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.listaObrasHistorico = (HashMap<String,Integer>) params.get("DATOS_HISTORICOS");
              
        mostrarObjetivoDelInforme();
        try{
            mostarGraficoObras();
        }catch(Exception e){
            throw new DocumentException(e);
        }
        
        mostrarTablaDeDatos();
    }    
    
    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Cantidad de Obras que fueron Finalizadas por año.",ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }    

    private void mostarGraficoObras() throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
        
        // Sort Map
        List<String> yearsToSort = new ArrayList<String>(listaObrasHistorico.keySet());
        Collections.sort(yearsToSort, new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                int year1 = Integer.valueOf(t1);
                int year2 = Integer.valueOf(t2);
                return year1-year2;
            }
        });
        
        for (int i = 0; i < yearsToSort.size(); i++) {
            int cantidad = listaObrasHistorico.get(yearsToSort.get(i));
            dataset.addValue(cantidad,"Cantidad de Obras",yearsToSort.get(i));
        }
          
        GraficoDeAreas gd = new GraficoDeAreas("", dataset);
        JFreeChart chart = gd.createGraph();

        super.insertarGraficoCentrado(chart,500,180);
    }

    private void mostrarTablaDeDatos() throws DocumentException {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setSpacingBefore(15f);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(50);
        float[] anchos = {4f,4f};
        tabla.setWidths(anchos);
        
        insertarCeldaHeaderEnTabla(tabla,"Año de Referencia",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
        insertarCeldaHeaderEnTabla(tabla,"Cantidad de Obras Finalizadas",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
       
        // Sort Map
        List<String> yearsToSort = new ArrayList<String>(listaObrasHistorico.keySet());
        Collections.sort(yearsToSort, new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                int year1 = Integer.valueOf(t1);
                int year2 = Integer.valueOf(t2);
                return year2-year1;
            }
        });
        
        int total = 0;
        for (int i = 0; i < yearsToSort.size(); i++) {
            String year = yearsToSort.get(i);
            int cantidad = listaObrasHistorico.get(year);
            
            insertarCeldaEnTabla(tabla,year,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
            insertarCeldaEnTabla(tabla,""+cantidad,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
     
            total += cantidad;
        }
        
        insertarCeldaEnTabla(tabla,"Total",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,""+total,ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
        
        super.doc.add(tabla);
    }
    
}
