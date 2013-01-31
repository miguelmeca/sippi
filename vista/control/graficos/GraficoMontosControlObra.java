package vista.control.graficos;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;
import modelo.PedidoObra;
import modelo.TareaEjecucion;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.ejecucion.GraficoCantidadDeTareasPorEstado;
import vista.reportes.sources.graficos.GraficoDeBarras;
import vista.reportes.sources.graficos.GraficoDeBarrasCustomRenderer;

/**
 * Grafico de Barras de 3 entradas.
 * Monto Total Cotizado
 * Monto Total Planificado
 * Monto Total Ejecutado
 * @author Iuga
 */
public class GraficoMontosControlObra {
    
    private List<PedidoObra> pedidosDeOras;
    private double montoTotalCotizado;
    private double montoTotalPlanificado;
    private double montoTotalEjecutado;
    
    public GraficoMontosControlObra(List<PedidoObra> pedidosDeOras) {
        this.pedidosDeOras = pedidosDeOras;
    }
    
    public JFreeChart createGraph() {
        // Analizo los datos antes de crear el Gráfico
        for (int i = 0; i < pedidosDeOras.size(); i++) {
            PedidoObra pedidoObra = pedidosDeOras.get(i);
            
            montoTotalCotizado += getMontoTotalCotizado(pedidoObra);
            montoTotalPlanificado += getMontoTotalPlanificado(pedidoObra);
            montoTotalEjecutado += getMontoTotalEjecutado(pedidoObra);
            
        }
        
        // Creo el Gráfico:
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(montoTotalCotizado,"Row1","Montos Cotizados");
        dataset.addValue(montoTotalPlanificado,"Row1","Montos Planificados");
        dataset.addValue(montoTotalEjecutado,"Row1","Montos Ejecutados");
        
        final GraficoDeBarrasCustomRenderer renderer;        
        renderer = new GraficoDeBarrasCustomRenderer(
            new Paint[] {new Color(124,232,201), 
            new Color(149,232,124),
            new Color(232,164,124)}        
            ); 
        
        GraficoDeBarras gdb = new GraficoDeBarras("", dataset,"Etapas de la Obra","Montos (En Pesos $)");
        gdb.setCategoryRenderer(renderer);
        final JFreeChart chart = gdb.createGraph();
        
        return chart;
    }
    
    private double getMontoTotalCotizado(PedidoObra po){
        if(po!=null){
            if(po.getPlanificacion()!=null){
                if(po.getPlanificacion().getCotizacion()!=null &&
                        po.getPlanificacion().getCotizacion().getCotizacionOriginal()!=null){
                    return po.getPlanificacion().getCotizacion().getCotizacionOriginal().CalcularMontoBase();
                }
            }
        }
        return 0;
    }
    
    private double getMontoTotalPlanificado(PedidoObra po){
         if(po!=null){
            if(po.getPlanificacion()!=null){
                return po.getPlanificacion().calcularMontoTotal();
            }
         }
         return 0;
    }

    private double getMontoTotalEjecutado(PedidoObra po){
         if(po!=null){
            if(po.getEjecucion()!=null){
                return po.getEjecucion().calcularMontoTotal();
            }
         }
         return 0;
    }    
    
}
