package vista.control.graficos;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;
import modelo.Cotizacion;
import modelo.EjecucionXAdicional;
import modelo.PedidoObra;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.reportes.sources.graficos.GraficoDeBarras;
import vista.reportes.sources.graficos.GraficoDeBarrasCustomRenderer;

/**
 *
 * @author Iuga
 */
public class GraficoGastosVariosControlObra {
    
    private List<PedidoObra> pedidosDeOras;

    private double totalGastosVariosCotizados;
    private double totalGastosVariosEjecutados;
    
    public GraficoGastosVariosControlObra(List<PedidoObra> pedidosDeOras) {
        this.pedidosDeOras = pedidosDeOras;
    }
    
    public JFreeChart createGraph() {
       // Analizo los datos antes de crear el Gráfico
        for (int i = 0; i < pedidosDeOras.size(); i++) {
            PedidoObra pedidoObra = pedidosDeOras.get(i);
            totalGastosVariosCotizados += getTotalCotizado(pedidoObra);
            totalGastosVariosEjecutados += getTotalEjecutado(pedidoObra);
            
        }        
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(totalGastosVariosCotizados,"Cotizado","Gastos Varios Cotizados");
        dataset.addValue(totalGastosVariosEjecutados,"Cotizado","Gastos Varios Ejecutados");
        
        final GraficoDeBarrasCustomRenderer renderer;        
        renderer = new GraficoDeBarrasCustomRenderer(
            new Paint[] {new Color(124,232,201), 
            new Color(149,232,124),
            new Color(232,164,124)}        
            ); 
        
        GraficoDeBarras gdb = new GraficoDeBarras("", dataset,"Etapas de la Obra","Gastos Varios ($)");
        gdb.setCategoryRenderer(renderer);
        final JFreeChart chart = gdb.createGraph();
        
        return chart;  
    }
    
    private double getTotalCotizado(PedidoObra pedidoObra){
        double subTotal = 0;
        if(pedidoObra!=null){
            if(pedidoObra.getPlanificacion()!=null){
                if(pedidoObra.getPlanificacion().getCotizacion()!=null && 
                        pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal()!=null){
                 
                    Cotizacion cot = pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal();
                     // Veo para todas las subobras
                    for (int i = 0; i < cot.getSubObras().size(); i++) {
                        SubObra so = cot.getSubObras().get(i);
                        if(so!=null){
                            for (int j = 0; j < so.getAdicionales().size(); j++) {
                                SubObraXAdicional soxa = so.getAdicionales().get(j);
                                subTotal += soxa.calcularSubtotal();
                            }
                        }
                    }
                }
            }
        }
        return subTotal;
    }
    
    private double getTotalEjecutado(PedidoObra pedidoObra){
        double subTotal = 0;
        if(pedidoObra!=null){
            if(pedidoObra.getEjecucion()!=null){
                if(pedidoObra.getEjecucion().getAdicionales()!=null){
                    for (int i = 0; i < pedidoObra.getEjecucion().getAdicionales().size(); i++) {
                        EjecucionXAdicional eja = pedidoObra.getEjecucion().getAdicionales().get(i);
                        subTotal += eja.calcularSubtotal();
                    }
                }
            }
        }
        return subTotal;
    }
    
}
