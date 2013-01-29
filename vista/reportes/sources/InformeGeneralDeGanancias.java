package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Cotizacion;
import modelo.PedidoObra;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import util.FechaUtil;
import util.StringUtil;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeAreas;
import vista.reportes.sources.graficos.GraficoDeBarras;
import vista.reportes.sources.graficos.GraficoDeLineas;

/**
 *
 * @author Iuga
 */
public class InformeGeneralDeGanancias extends ReportDesigner{

    private List<PedidoObra> obras;
    private Date fechaInicio;
    private Date fechaFin;
    private DefaultCategoryDataset gananciasHistoricas;
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.obras = (List<PedidoObra>) params.get("LISTA_PEDIDOS_OBRA");
        
        fechaInicio = (Date) params.get("INFORME_FECHA_INICIO");
        fechaFin = (Date) params.get("INFORME_FECHA_FIN");
        gananciasHistoricas = (DefaultCategoryDataset)params.get("GANANCIAS_HISTORICAS");
        
        mostrarObjetivoDelInforme();
        mostrarFechasFiltro();
        mostrarTablaObas();
        try{
            mostrarHistoricoDeGanancias();
        }catch(Exception e){
            throw new DocumentException(e);
        }
    }

    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Ingresos de la empresa por obras concluidas en el rango de tiempo seleccionado:",ReportDesigner.FUENTE_NORMAL));
        super.doc.add(PTitulo);
    }
    
    private void mostrarFechasFiltro() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Fecha de Inicio: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(fechaInicio),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase(" - Fecha de Fin: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(fechaFin),ReportDesigner.FUENTE_NORMAL));
        super.doc.add(PTitulo);
    }      

    private void mostrarTablaObas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setSpacingBefore(5f);
        tabla.setSpacingAfter(5f);
        tabla.setWidthPercentage(100);
        float[] anchos = {6f,2f,2f,2f};
        tabla.setWidths(anchos);
        
        insertarCeldaHeaderEnTabla(tabla,"Nombre de la Obra",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Monto Cotizado",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
        insertarCeldaHeaderEnTabla(tabla,"Monto Ejecutado",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
        insertarCeldaHeaderEnTabla(tabla,"SubTotal",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
       
        double subTotal = 0;
        for (int i = 0; i < obras.size(); i++) {
            PedidoObra po = obras.get(i);
            
            if(FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)){
                // Nombre
                insertarCeldaEnTabla(tabla,po.getNombre(),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_LEFT);
                // Monto Cotizado
                double montoCotizado = buscarMontoCotizado(po);
                insertarCeldaEnTabla(tabla,"$ "+StringUtil.roundAmount(montoCotizado),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                // Monto Ejecutado
                double montoEjecutado = buscarMontoEjecutado(po);
                insertarCeldaEnTabla(tabla,"$ "+StringUtil.roundAmount(montoEjecutado),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                // Monto SubTotal
                insertarCeldaEnTabla(tabla,"$ "+StringUtil.roundAmount((montoCotizado-montoEjecutado)),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                subTotal += (montoCotizado-montoEjecutado);
            }
        }
        
        // Fila de Totales
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"Total:\n$ "+StringUtil.roundAmount(subTotal),ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
        
        super.doc.add(tabla);
        
    }

    private double buscarMontoCotizado(PedidoObra po) {
        if(po!=null){
            if(po.getPlanificacion()!=null){
                if(po.getPlanificacion().getCotizacion()!=null){
                    if(po.getPlanificacion().getCotizacion().getCotizacionOriginal()!=null){
                        Cotizacion cotOrigin = po.getPlanificacion().getCotizacion().getCotizacionOriginal();
                        return cotOrigin.CalcularTotal();
                    }
                }
            }
        }
        return 0D;
    }

    private double buscarMontoEjecutado(PedidoObra po) {
        if(po!=null){
            if(po.getEjecucion()!=null){
                return po.getEjecucion().calcularMontoTotal();
            }
        }
        return 0D;
    }

    private void mostrarHistoricoDeGanancias() throws DocumentException, Exception {
        
        insertarTitulo("Historico de Montos Anuales");
        GraficoDeAreas gd = new GraficoDeAreas("", this.gananciasHistoricas);
        JFreeChart chart = gd.createGraph();
        super.insertarGraficoCentrado(chart,500,180);
        
    }


    
    
    
}
