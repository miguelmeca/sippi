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
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeAreas;
import vista.reportes.sources.graficos.GraficoDePareto;

/**
 *
 * @author Iuga
 */
public class InformeCantidadCotizacionesRechazadas extends ReportDesigner{

    private List<PedidoObra> obras;
    private Date fechaInicio;
    private Date fechaFin;
    private HashMap<String,Integer> listaObrasHistorico;
    private HashMap<String,Integer> historicoXcliente;
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.obras = (List<PedidoObra>) params.get("LISTA_PEDIDOS_OBRA");
        
        fechaInicio = (Date) params.get("INFORME_FECHA_INICIO"); 
        
        fechaFin = (Date) params.get("INFORME_FECHA_FIN");
        listaObrasHistorico = (HashMap<String,Integer>)params.get("OBRAS_HISTORICO");
        
        this.historicoXcliente = (HashMap<String,Integer>) params.get("HISTORICO_X_CLIENTE");
        
        mostrarObjetivoDelInforme();
        mostrarFechasFiltro();
        mostrarTablaObas();
        mostrarHistoricoDeRechazadas();
        try{
            mostarGraficoPareto();
            mostarGraficoClientes();
        }catch(Exception e){
            throw new DocumentException(e);
        }
    }

    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Cantidad de Cotizaciones que fueron Rechazadas por un cliente.",ReportDesigner.FUENTE_INFORMES_NORMAL));
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
    
    private void mostrarCantidadDeRechazadas() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("\nCantidad Total de Cotizaciones rechazadas: ",ReportDesigner.FUENTE_INFORMES_NORMAL_B));
        PTitulo.add( new Phrase(""+calcularTotalCotizacionesRechazadas()+"\n",ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }    

    private void mostrarTablaObas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(15f);
        tabla.setWidthPercentage(100);
        float[] anchos = {4f,4f,2f};
        tabla.setWidths(anchos);
        
        insertarCeldaHeaderEnTabla(tabla,"Nombre de la Obra",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Razón Social del Cliente",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Cantidad de Cotizaciones Rechazadas",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
       
        for (int i = 0; i < obras.size(); i++) {
            PedidoObra po = obras.get(i);
            
                int cantidadRechazados = calcularCantidadCotizacionesRechazadas(po);
            
                if(cantidadRechazados>0){
                    
                    // Nombre
                    insertarCeldaEnTabla(tabla,po.getNombre(),ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
                    // Cliente
                    gestorBDvarios gbd = new gestorBDvarios();
                    EmpresaCliente ec = gbd.buscarEmpresaCliente(po.getPlanta());
                    String cliente = "";
                    if(ec!=null){
                        cliente = ec.getRazonSocial();
                    }
                    insertarCeldaEnTabla(tabla,cliente,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);

                    // Monto SubTotal
                    insertarCeldaEnTabla(tabla,""+cantidadRechazados,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
                }
        }
        
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,""+calcularTotalCotizacionesRechazadas(),ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
        
        super.doc.add(tabla);
        
    }

    private int calcularTotalCotizacionesRechazadas() {
        int cantidadTotal = 0;
        for (int i = 0; i < obras.size(); i++) {
            PedidoObra po = obras.get(i);
            if(po.getCotizaciones()!=null){
                for(int j = 0; j < po.getCotizaciones().size(); j++) {
                    Cotizacion cot = po.getCotizaciones().get(j);
                    if(cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)){
                        cantidadTotal++;
                    }
                }
            }
        }
        return cantidadTotal;
    }

    private int calcularCantidadCotizacionesRechazadas(PedidoObra po) {
        int cantidadTotal = 0;
        if(po.getCotizaciones()!=null){
            for(int j = 0; j < po.getCotizaciones().size(); j++) {
                Cotizacion cot = po.getCotizaciones().get(j);
                if(cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)){
                    cantidadTotal++;
                }
            }
        }
        return cantidadTotal;
    }

    private void mostrarHistoricoDeRechazadas() throws DocumentException {
        super.insertarSubTitulo("Historico de Cotizaciones Rechazadas");      
        
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(5f);
        float[] anchos = {5f,5f};
        tabla.setWidths(anchos);

        insertarCeldaHeaderEnTabla(tabla,"Año",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Cantidad",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);

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
        
        for (int i = 0; i < yearsToSort.size(); i++) {
            String year = yearsToSort.get(i);
            insertarCeldaEnTabla(tabla,""+year,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_LEFT);
            insertarCeldaEnTabla(tabla,""+listaObrasHistorico.get(year),ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_LEFT);
        }

        super.doc.add(tabla);     
        
    }
    
    private void mostarGraficoPareto() throws BadElementException, IOException, DocumentException, Exception{
        
        super.insertarSubTitulo("Pareto Historico de Cotizaciones Rechazadas");      
        
        GraficoDePareto gdp = new GraficoDePareto("", listaObrasHistorico);
        JFreeChart chart = gdp.createGraph();

        super.insertarGraficoCentrado(chart,500,180);
            
    }

    private void mostarGraficoClientes() throws Exception {
        
        super.insertarSubTitulo("Historico de Cotizaciones Rechazadas por Cliente");     
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
        
        Iterator it = historicoXcliente.entrySet().iterator();
            while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            dataset.addValue((Double)e.getValue(),"Cantidad de Cotizaciones Rechazadas",(String)e.getKey());   
        }
  
        GraficoDeAreas gd = new GraficoDeAreas("", dataset);
        JFreeChart chart = gd.createGraph();

        super.insertarGraficoCentrado(chart,500,180);

    }






    
    
    
}
