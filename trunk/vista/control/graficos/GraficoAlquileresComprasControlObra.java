package vista.control.graficos;

import controlador.ejecucion.EjecucionUtils;
import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import java.awt.Paint;
import java.util.List;
import modelo.Cotizacion;
import modelo.EjecucionXAdicional;
import modelo.EjecucionXAlquilerCompra;
import modelo.PedidoObra;
import modelo.PlanificacionXAlquilerCompra;
import modelo.SubObra;
import modelo.SubObraXAlquilerCompra;
import modelo.TareaEjecucion;
import modelo.TareaPlanificacion;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.reportes.sources.graficos.GraficoDeBarras;
import vista.reportes.sources.graficos.GraficoDeBarrasCustomRenderer;

/**
 *
 * @author Iuga
 */
public class GraficoAlquileresComprasControlObra {
    
    private List<PedidoObra> pedidosDeOras;

    private double totalAlquileresComprasCotizados;
    private double totalAlquileresComprasPlanificados;
    private double totalAlquileresComprasEjecutados;
    
    public GraficoAlquileresComprasControlObra(List<PedidoObra> pedidosDeOras) {
        this.pedidosDeOras = pedidosDeOras;
    }
    
    public JFreeChart createGraph() {
       // Analizo los datos antes de crear el Gráfico
        for (int i = 0; i < pedidosDeOras.size(); i++) {
            PedidoObra pedidoObra = pedidosDeOras.get(i);
            totalAlquileresComprasCotizados += getTotalCotizado(pedidoObra);
            totalAlquileresComprasPlanificados += getTotalPlanificado(pedidoObra);
            totalAlquileresComprasEjecutados += getTotalEjecutado(pedidoObra);
        }        
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(totalAlquileresComprasCotizados,"Cotizado","Cotizados");
        dataset.addValue(totalAlquileresComprasPlanificados,"Cotizado","Planificados");
        dataset.addValue(totalAlquileresComprasEjecutados,"Cotizado","Ejecutados");
        
        final GraficoDeBarrasCustomRenderer renderer;        
        renderer = new GraficoDeBarrasCustomRenderer(
            new Paint[] {new Color(124,232,201), 
            new Color(149,232,124),
            new Color(232,164,124)}        
            ); 
        
        GraficoDeBarras gdb = new GraficoDeBarras("", dataset,"Etapas de la Obra","Alquileres/Compras ($)");
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
                            for (int j = 0; j < so.getAlquileresCompras().size(); j++) {
                                SubObraXAlquilerCompra soxac = so.getAlquileresCompras().get(j);
                                subTotal += soxac.calcularSubtotal();
                            }
                        }
                    }
                }
            }
        }
        return subTotal;
    }
    
    private double getTotalPlanificado(PedidoObra pedidoObra){
        double subTotal = 0;
        if(pedidoObra!=null){
            if(pedidoObra.getPlanificacion()!=null){
               List<TareaPlanificacion> tareas = PlanificacionUtils.getTodasTareasPlanificacion(pedidoObra.getPlanificacion());
                for (int i = 0; i < tareas.size(); i++) {
                    TareaPlanificacion tarea = tareas.get(i);
                    if(tarea.getAlquilerCompras()!=null){
                        for (int j = 0; j < tarea.getAlquilerCompras().size(); j++) {
                            PlanificacionXAlquilerCompra exac = (PlanificacionXAlquilerCompra)tarea.getAlquilerCompras().get(j);
                            subTotal += exac.calcularSubtotal();
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
               List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(pedidoObra.getEjecucion());
                for (int i = 0; i < tareas.size(); i++) {
                    TareaEjecucion tarea = tareas.get(i);
                    if(tarea.getAlquilerCompras()!=null){
                        for (int j = 0; j < tarea.getAlquilerCompras().size(); j++) {
                            EjecucionXAlquilerCompra exac = (EjecucionXAlquilerCompra)tarea.getAlquilerCompras().get(j);
                            subTotal += exac.calcularSubtotal();
                        }
                    }
                }
            }
        }
        return subTotal;
    }
    
}
