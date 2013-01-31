package vista.control.graficos;

import controlador.ejecucion.EjecucionUtils;
import controlador.planificacion.PlanificacionUtils;
import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Cotizacion;
import modelo.Ejecucion;
import modelo.EjecucionXHerramienta;
import modelo.Herramienta;
import modelo.HerramientaDeEmpresa;
import modelo.PedidoObra;
import modelo.PlanificacionXHerramienta;
import modelo.SubObra;
import modelo.SubObraXHerramienta;
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
public class GraficoHerramientasControlObra {
    
    private List<PedidoObra> pedidosDeOras;
    private HashMap<HerramientaDeEmpresa,Double> herramientasCotizadas;
    private HashMap<HerramientaDeEmpresa,Double> herramientasPlanificadas;
    private HashMap<HerramientaDeEmpresa,Double> herramientasEjecutadas;

    public GraficoHerramientasControlObra(List<PedidoObra> pedidosDeOras) {
        this.pedidosDeOras = pedidosDeOras;
        this.herramientasCotizadas = new HashMap<HerramientaDeEmpresa, Double>();
        this.herramientasPlanificadas = new HashMap<HerramientaDeEmpresa, Double>();
        this.herramientasEjecutadas = new HashMap<HerramientaDeEmpresa, Double>();
    }
    
    public JFreeChart createGraph() {
        // Analizo los datos antes de crear el Gráfico
        for (int i = 0; i < pedidosDeOras.size(); i++) {
            PedidoObra pedidoObra = pedidosDeOras.get(i);
            
            getUsoTotalCotizado(pedidoObra);
            getUsoTotalPlanificado(pedidoObra);
            getUsoTotalEjecutado(pedidoObra);
            
        }
        
        // Creo el Gráfico:
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
            // Agrego las herramientas cotizados
            Iterator it = herramientasCotizadas.entrySet().iterator();
            double totalHerramientaCotizada = 0;
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)e.getKey();
                double cantidad = (Double)e.getValue();
                totalHerramientaCotizada += cantidad;
            }
            dataset.addValue(totalHerramientaCotizada,"Horas","Hs. Cotizadas de Herramientas");
        
            // Agrego las herramientas planificadas
            double totalHerramientaPlanificada = 0;
            Iterator itp = herramientasPlanificadas.entrySet().iterator();
            while (itp.hasNext()) {
                Map.Entry e = (Map.Entry) itp.next();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)e.getKey();
                double cantidad = (Double)e.getValue();
                totalHerramientaPlanificada += cantidad;
            }
            dataset.addValue(totalHerramientaPlanificada,"Horas","Hs. Planificadas de Herramientas");
            
            // Agrego las herramientas ejecutadas
            double totalHerramientaEjecutadas = 0;
            Iterator ite = herramientasEjecutadas.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry e = (Map.Entry) ite.next();
                HerramientaDeEmpresa hde = (HerramientaDeEmpresa)e.getKey();
                double cantidad = (Double)e.getValue();
                totalHerramientaEjecutadas += cantidad;
            }
            dataset.addValue(totalHerramientaEjecutadas,"Horas","Hs. Ejecutadas de Herramientas");
        
        final GraficoDeBarrasCustomRenderer renderer;        
        renderer = new GraficoDeBarrasCustomRenderer(
            new Paint[] {new Color(124,232,201), 
            new Color(149,232,124),
            new Color(131,124,232),
            new Color(232,124,163),
            new Color(232,229,124),
            new Color(232,164,124)}        
            ); 
        
        GraficoDeBarras gdb = new GraficoDeBarras("", dataset,"Etapas de la Obra","Horas de Uso");
        gdb.setCategoryRenderer(renderer);
        gdb.setMostrarLabels(true);
        final JFreeChart chart = gdb.createGraph();
        
        return chart;        

    }

    private void getUsoTotalCotizado(PedidoObra pedidoObra) {
        if(pedidoObra!=null){
            if(pedidoObra.getPlanificacion()!=null){
                if(pedidoObra.getPlanificacion().getCotizacion()!=null && 
                        pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal()!=null){
                 
                    Cotizacion cot = pedidoObra.getPlanificacion().getCotizacion().getCotizacionOriginal();
                    // Veo para todas las subobras
                    for (int i = 0; i < cot.getSubObras().size(); i++) {
                        SubObra so = cot.getSubObras().get(i);
                        if(so!=null){
                            for (int j = 0; j < so.getHerramientas().size(); j++) {
                                SubObraXHerramienta sohde = so.getHerramientas().get(j);
                                if(sohde.getHerramienta()!=null){
                                    double horas = sohde.getCantHoras();
                                    // Si no existe la agrego, sino actualizo
                                    if(herramientasCotizadas.containsKey(sohde.getHerramienta())){
                                        // Existe
                                        double cantHoras = herramientasCotizadas.get(sohde.getHerramienta());
                                        cantHoras += horas;
                                        herramientasCotizadas.put(sohde.getHerramienta(), cantHoras);
                                    }else{
                                        // No existe
                                        herramientasCotizadas.put(sohde.getHerramienta(), horas);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }        
    }

    private void getUsoTotalPlanificado(PedidoObra pedidoObra) {
        
        if(pedidoObra!=null){
            if(pedidoObra.getPlanificacion()!=null){
                List<TareaPlanificacion> tareas = PlanificacionUtils.getTodasTareasPlanificacion(pedidoObra.getPlanificacion());
                for (int i = 0; i < tareas.size(); i++) {
                    TareaPlanificacion tarea = tareas.get(i);
                     List<PlanificacionXHerramienta> listaHerramientas = tarea.getHerramientas();
                     for (int j = 0; j < listaHerramientas.size(); j++) {
                        PlanificacionXHerramienta herramienta = listaHerramientas.get(j);
                        if(herramienta!=null){
                            HerramientaDeEmpresa hde = herramienta.getHerramienta();
                            double horas = herramienta.getHorasAsignadas();
                            if(hde!=null){
                                
                                // Si no existe la agrego, sino actualizo
                                if(herramientasPlanificadas.containsKey(hde)){
                                    // Existe
                                    double cantHoras = herramientasPlanificadas.get(hde);
                                    cantHoras += horas;
                                    herramientasPlanificadas.put(hde, cantHoras);
                                }else{
                                    // No existe
                                    herramientasPlanificadas.put(hde, horas);
                                }
                            }
                        }  
                    }
                }
            }
        }
    }

    private void getUsoTotalEjecutado(PedidoObra pedidoObra) {
        if(pedidoObra!=null){
            if(pedidoObra.getEjecucion()!=null){
                List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(pedidoObra.getEjecucion());
                for (int i = 0; i < tareas.size(); i++) {
                    TareaEjecucion tarea = tareas.get(i);
                     List<PlanificacionXHerramienta> listaHerramientas = tarea.getHerramientas();
                     for (int j = 0; j < listaHerramientas.size(); j++) {
                        EjecucionXHerramienta herramienta = (EjecucionXHerramienta)listaHerramientas.get(j);
                        if(herramienta!=null){
                            HerramientaDeEmpresa hde = herramienta.getHerramienta();
                            double horas = herramienta.getHorasUtilizadas();
                            if(hde!=null){
                                
                                // Si no existe la agrego, sino actualizo
                                if(herramientasEjecutadas.containsKey(hde)){
                                    // Existe
                                    double cantHoras = herramientasEjecutadas.get(hde);
                                    cantHoras += horas;
                                    herramientasEjecutadas.put(hde, cantHoras);
                                }else{
                                    // No existe
                                    herramientasEjecutadas.put(hde, horas);
                                }
                            }
                        }  
                    }
                }
            }
        }        
    }
    
}
