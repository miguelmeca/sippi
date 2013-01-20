package vista.control.graficos;

import java.util.List;
import modelo.Cotizacion;
import modelo.PedidoObra;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXHerramienta;
import modelo.SubObraXMaterial;
import modelo.SubObraXTarea;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import vista.reportes.sources.graficos.GraficoDeTorta;

/**
 *
 * @author Iuga
 */
public class GraficoTortaRecursosCotizacion {

    private PedidoObra obra;

    public GraficoTortaRecursosCotizacion(PedidoObra obra) {
        this.obra = obra;
    }

    /**
     * Herramientas Mano de Obra Materiales Compras Gastos Varios
     *
     * @return
     */
    public JFreeChart createGraph() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Cotizacion cot = null;
        if (obra != null && obra.getPlanificacion() != null) {

            if (obra.getPlanificacion().getCotizacion() != null && obra.getPlanificacion().getCotizacion().getCotizacionOriginal() != null) {
                cot = obra.getPlanificacion().getCotizacion().getCotizacionOriginal();
            }
            
            if(cot!=null){
                // Herramientas
                double montoHerramientas = calcularMontoHerramientas(cot);
                dataset.setValue("Herramientas [$" + montoHerramientas + "]", montoHerramientas);
                // Mano de Obra
                double montoManoObra = calcularMontoManoObra(cot);
                dataset.setValue("Mano de Obra [$" + montoManoObra + "]", montoManoObra);
                // Materiales
                double montoMateriales = calcularMontoMateriales(cot);
                dataset.setValue("Materiales [$" + montoMateriales + "]", montoMateriales);
                // Alquileres/Compras
                double montoAlqComp = calcularMontoAlqComp(cot);
                dataset.setValue("Alquileres/Compras [$" + montoAlqComp + "]", montoAlqComp);
                // Gastos Varios
                double montoGastosVarios = calcularMontoGastosVarios(cot);
                dataset.setValue("Gastos Varios [$" + montoGastosVarios + "]", montoGastosVarios);
            }
        }
        return createGraphObject(dataset);
    }

    protected double calcularMontoHerramientas(Cotizacion cot) {
        double subTotal = 0;
        List<SubObra> subObras = cot.getSubObras();
        for (int i = 0; i < subObras.size(); i++) {
            SubObra so = subObras.get(i);
             List<SubObraXHerramienta> herrs = so.getHerramientas();
             for (int j = 0; j < herrs.size(); j++) {
                SubObraXHerramienta soxh = herrs.get(j);
                subTotal += soxh.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected double calcularMontoManoObra(Cotizacion cot) {
        double subTotal = 0;
        List<SubObra> subObras = cot.getSubObras();
        for (int i = 0; i < subObras.size(); i++) {
            SubObra so = subObras.get(i);
             List<SubObraXTarea> herrs = so.getTareas();
             for (int j = 0; j < herrs.size(); j++) {
                SubObraXTarea subObraXTarea = herrs.get(j);
                subTotal += subObraXTarea.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected double calcularMontoMateriales(Cotizacion cot) {
        double subTotal = 0;
        List<SubObra> subObras = cot.getSubObras();
        for (int i = 0; i < subObras.size(); i++) {
            SubObra so = subObras.get(i);
             List<SubObraXMaterial> mats = so.getMateriales();
             for (int j = 0; j < mats.size(); j++) {
                SubObraXMaterial soi = mats.get(j);
                subTotal += soi.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected double calcularMontoAlqComp(Cotizacion cot) {
        double subTotal = 0;
        List<SubObra> subObras = cot.getSubObras();
        for (int i = 0; i < subObras.size(); i++) {
            SubObra so = subObras.get(i);
             List<SubObraXAlquilerCompra> mats = so.getAlquileresCompras();
             for (int j = 0; j < mats.size(); j++) {
                SubObraXAlquilerCompra soi = mats.get(j);
                subTotal += soi.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected double calcularMontoGastosVarios(Cotizacion cot) {
        double subTotal = 0;
        List<SubObra> subObras = cot.getSubObras();
        for (int i = 0; i < subObras.size(); i++) {
            SubObra so = subObras.get(i);
             List<SubObraXAdicional> adis = so.getAdicionales();
             for (int j = 0; j < adis.size(); j++) {
                SubObraXAdicional soi = adis.get(j);
                subTotal += soi.calcularSubtotal();
            }
        }
        return subTotal;
    }

    protected JFreeChart createGraphObject(DefaultPieDataset dataset) {
        GraficoDeTorta graph = new GraficoDeTorta("", dataset);
        return graph.createGraph();
    }
}
