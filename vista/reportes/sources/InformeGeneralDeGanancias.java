package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.PedidoObra;
import util.FechaUtil;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class InformeGeneralDeGanancias extends ReportDesigner{

    private List<PedidoObra> obras;
    private Date fechaInicio;
    private Date fechaFin;
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.obras = (List<PedidoObra>) params.get("LISTA_PEDIDOS_OBRA");
        
        fechaInicio = (Date) params.get("INFORME_FECHA_INICIO");
        fechaFin = (Date) params.get("INFORME_FECHA_FIN");
        
        mostrarObjetivoDelInforme();
        mostrarTablaObas();
    }

    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Ingresos de la empresa por obras concluidas en el rango de tiempo seleccionado:",ReportDesigner.FUENTE_NORMAL));
        super.doc.add(PTitulo);
    }

    private void mostrarTablaObas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setSpacingBefore(5f);
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
                insertarCeldaEnTabla(tabla,"$ "+montoCotizado,ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                // Monto Ejecutado
                double montoEjecutado = buscarMontoEjecutado(po);
                insertarCeldaEnTabla(tabla,"$ "+montoEjecutado,ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                // Monto SubTotal
                insertarCeldaEnTabla(tabla,"$ "+(montoCotizado-montoEjecutado),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
                subTotal += (montoCotizado-montoEjecutado);
            }
        }
        
        // Fila de Totales
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"",ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);
        insertarCeldaEnTabla(tabla,"Total:\n$ "+subTotal,ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
        
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


    
    
    
}
