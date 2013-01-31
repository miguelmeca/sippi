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
import java.util.HashMap;
import java.util.List;
import modelo.PedidoObra;
import org.jfree.chart.JFreeChart;
import vista.control.graficos.GraficoAlquileresComprasControlObra;
import vista.control.graficos.GraficoGastosVariosControlObra;
import vista.control.graficos.GraficoHerramientasControlObra;
import vista.control.graficos.GraficoMontosControlObra;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Administrador
 */
public class InformeCompletoControl extends ReportDesigner{

    private List<PedidoObra> pedidosDeObra;
    private int yearsHistorico;

    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.pedidosDeObra = (List<PedidoObra>) params.get("LISTA_PEDIDOS_OBRA");
        this.yearsHistorico = (Integer) params.get("YEARS_HISTORICO");
        
        mostrarObrasInvolucradas();
        
        try{
                mostrarContinuaEnPaginaSiguiente();
                super.insertarSaltoDePagina();
            
            mostrarComparativaDeCostos();
            mostrarComparativaHerramientas();
            mostrarComparativaGastosVarios();
                
                mostrarContinuaEnPaginaSiguiente();
                super.insertarSaltoDePagina();
                
            mostrarComparativaAlquileresCompras();
        }catch(Exception e){
            throw new DocumentException(e);
        }

    }

    private void mostrarComparativaDeCostos() throws DocumentException, Exception {
        super.insertarTitulo("Montos Cotizados Vs. Planificados Vs. Ejecutados");
        
        GraficoMontosControlObra graficoComparativaCostos = new GraficoMontosControlObra(this.pedidosDeObra);
        JFreeChart chart = graficoComparativaCostos.createGraph();
        
        super.insertarGraficoCentrado(chart,500,180);
    }

    private void mostrarComparativaHerramientas() throws DocumentException, Exception {
        super.insertarTitulo("Horas de Uso de Herramientas Cotizados Vs. Planificados Vs. Ejecutados");
        
        GraficoHerramientasControlObra grafico = new GraficoHerramientasControlObra(this.pedidosDeObra);
        JFreeChart chart = grafico.createGraph();
        
         super.insertarGraficoCentrado(chart,500,180);   
    }

    private void mostrarComparativaGastosVarios() throws DocumentException, Exception {
        super.insertarTitulo("Gastos Varios Cotizados Vs. Ejecutados");
        
        GraficoGastosVariosControlObra grafico = new GraficoGastosVariosControlObra(this.pedidosDeObra);
        JFreeChart chart = grafico.createGraph();
        
         super.insertarGraficoCentrado(chart,500,180); 
    }

    private void mostrarComparativaAlquileresCompras() throws DocumentException, Exception {
        super.insertarTitulo("Alquileres/Compras Cotizados Vs. Planificados Vs. Ejecutados");
        
        GraficoAlquileresComprasControlObra grafico = new GraficoAlquileresComprasControlObra(this.pedidosDeObra);
        JFreeChart chart = grafico.createGraph();
        
        super.insertarGraficoCentrado(chart,500,180);         
    }

    private void mostrarObrasInvolucradas() throws DocumentException {
       super.insertarTitulo("Obras involucradas en éste informe");
       
       PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingBefore(5f);
        tabla.setSpacingAfter(5f);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(90);
        float[] anchos = {1f};
        tabla.setWidths(anchos);
       
        insertarCeldaHeaderEnTabla(tabla,"Nombre de la Obra",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
        
        for (int i = 0; i < pedidosDeObra.size(); i++) {
            PedidoObra po = pedidosDeObra.get(i);
            
            Paragraph paMontoCotizado = new Paragraph();
            paMontoCotizado.add(new Phrase(po.getNombre(),ReportDesigner.FUENTE_INFORMES_NORMAL_B));
            paMontoCotizado.add(new Phrase("\n"+po.getDescripcion(),ReportDesigner.FUENTE_INFORMES_NORMAL_I));
            PdfPCell celdaMontoCotizado = new PdfPCell(paMontoCotizado);
            celdaMontoCotizado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            tabla.addCell(celdaMontoCotizado);

        }
        
        super.doc.add(tabla);
    }

    private void mostrarContinuaEnPaginaSiguiente() throws DocumentException {
        Paragraph paContinua = new Paragraph();
            paContinua.add(new Phrase("Continua en la siguiente página ...",ReportDesigner.FUENTE_INFORMES_NORMAL_I));
        super.doc.add(paContinua);
    }
    
}
