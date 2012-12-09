package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import controlador.utiles.gestorBDvarios;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import util.FechaUtil;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class InformeCantidadCotizacionesRechazadas extends ReportDesigner{

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
        mostrarFechasFiltro();
        mostrarTablaObas();
        mostrarCantidadDeRechazadas();
    }

    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Cantidad de Cotizaciones que fueron Rechazadas por un cliente.",ReportDesigner.FUENTE_NORMAL));
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
    
    private void mostrarCantidadDeRechazadas() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("\nCantidad Total de Cotizaciones rechazadas: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(""+calcularTotalCotizacionesRechazadas()+"\n",ReportDesigner.FUENTE_NORMAL));
        super.doc.add(PTitulo);
    }    

    private void mostrarTablaObas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(15f);
        tabla.setWidthPercentage(100);
        float[] anchos = {4f,4f,2f};
        tabla.setWidths(anchos);
        
        insertarCeldaHeaderEnTabla(tabla,"Nombre de la Obra",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Razón Social del Cliente",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_LEFT);
        insertarCeldaHeaderEnTabla(tabla,"Cantidad de Cotizaciones Rechazadas",ReportDesigner.FUENTE_NORMAL_B,PdfPCell.ALIGN_CENTER);
       
        for (int i = 0; i < obras.size(); i++) {
            PedidoObra po = obras.get(i);
            
                // Nombre
                insertarCeldaEnTabla(tabla,po.getNombre(),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_LEFT);
                // Cliente
                gestorBDvarios gbd = new gestorBDvarios();
                EmpresaCliente ec = gbd.buscarEmpresaCliente(po.getPlanta());
                String cliente = "";
                if(ec!=null){
                    cliente = ec.getRazonSocial();
                }
                insertarCeldaEnTabla(tabla,cliente,ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_LEFT);
                
                // Monto SubTotal
                insertarCeldaEnTabla(tabla,""+calcularCantidadCotizacionesRechazadas(po),ReportDesigner.FUENTE_NORMAL,PdfPCell.ALIGN_CENTER);

        }
        
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






    
    
    
}
