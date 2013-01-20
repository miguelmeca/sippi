package vista.control.graficos;

import controlador.ejecucion.EjecucionUtils;
import java.util.List;
import modelo.DetalleTareaPlanificacion;
import modelo.Ejecucion;
import modelo.EjecucionXAdicional;
import modelo.EjecucionXAlquilerCompra;
import modelo.EjecucionXHerramienta;
import modelo.PedidoObra;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.TareaEjecucion;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import vista.reportes.sources.graficos.GraficoDeTorta;

/**
 *
 * @author Iuga
 */
public class GraficoTortaRecursosEjecucion {
    
    private PedidoObra obra;

    public GraficoTortaRecursosEjecucion(PedidoObra obra) {
        this.obra = obra;
    }
    
    /**
     * Herramientas
     * Mano de Obra
     * Materiales
     * Compras
     * Gastos Varios
     * @return 
     */
    public JFreeChart createGraph() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        if(obra!=null && obra.getEjecucion()!=null){
            Ejecucion ejec = obra.getEjecucion();
            // Herramientas
            double montoHerramientas = calcularMontoHerramientas(ejec);
            dataset.setValue("Herramientas [$"+montoHerramientas+"]", montoHerramientas);
            // Mano de Obra
            double montoManoObra = calcularMontoManoObra(ejec);
            dataset.setValue("Mano de Obra [$"+montoManoObra+"]", montoManoObra);
            // Materiales
            double montoMateriales = calcularMontoMateriales(ejec);
            dataset.setValue("Materiales [$"+montoMateriales+"]", montoMateriales);
            // Alquileres/Compras
            double montoAlqComp = calcularMontoAlqComp(ejec);
            dataset.setValue("Alquileres/Compras [$"+montoAlqComp+"]", montoAlqComp);
            // Gastos Varios
            double montoGastosVarios = calcularMontoGastosVarios(ejec);
            dataset.setValue("Gastos Varios [$"+montoGastosVarios+"]", montoGastosVarios);
        }
        return createGraphObject(dataset);
    }
    
    

    protected double calcularMontoHerramientas(Ejecucion ejec) {
        double subTotal = 0;
        List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(ejec);
        for (int i = 0; i < tareas.size(); i++) {
            TareaEjecucion tareaEjecucion = tareas.get(i);
            List<PlanificacionXHerramienta> herras = tareaEjecucion.getHerramientas();
            for (int j = 0; j < herras.size(); j++) {
                EjecucionXHerramienta h = (EjecucionXHerramienta) herras.get(j);
                subTotal += h.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected double calcularMontoManoObra(Ejecucion ejec) {
        double subTotal = 0;
            List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(ejec);
            for (int i = 0; i < tareas.size(); i++) {
                TareaEjecucion tareaEjecucion = tareas.get(i);
                List<DetalleTareaPlanificacion> mos = tareaEjecucion.getDetalles();
                for (int j = 0; j < mos.size(); j++) {
                    DetalleTareaPlanificacion mo = mos.get(j);
                    subTotal += mo.calcularSubtotal();
                }
            }
        return subTotal;
    }

    protected double calcularMontoMateriales(Ejecucion ejec) {
        double subTotal = 0;
            List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(ejec);
            for (int i = 0; i < tareas.size(); i++) {
                TareaEjecucion tareaEjecucion = tareas.get(i);
                List<PlanificacionXMaterial> mats = tareaEjecucion.getMateriales();
                for (int j = 0; j < mats.size(); j++) {
                    PlanificacionXMaterial mat = mats.get(j);
                    subTotal += mat.calcularSubtotal();
                }
            }
        return subTotal;        
    }

    protected double calcularMontoAlqComp(Ejecucion ejec) {
        double subTotal = 0;
            List<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(ejec);
            for (int i = 0; i < tareas.size(); i++) {
                TareaEjecucion tareaEjecucion = tareas.get(i);
                List<PlanificacionXAlquilerCompra> compras = tareaEjecucion.getAlquilerCompras();
                for (int j = 0; j < compras.size(); j++) {
                    EjecucionXAlquilerCompra compra = (EjecucionXAlquilerCompra) compras.get(j);
                    subTotal += compra.calcularSubtotal();
                }
            }
        return subTotal; 
    }

    protected double calcularMontoGastosVarios(Ejecucion ejec) {
        double subTotal = 0; 
        List<EjecucionXAdicional> adicionales = ejec.getAdicionales();
        for (int i = 0; i < adicionales.size(); i++) {
            EjecucionXAdicional exa = adicionales.get(i);
            subTotal += exa.calcularSubtotal();
        }
        return subTotal; 
    }

    protected JFreeChart createGraphObject(DefaultPieDataset dataset) {
        GraficoDeTorta graph = new GraficoDeTorta("", dataset);
        return graph.createGraph();
    }
    
}
