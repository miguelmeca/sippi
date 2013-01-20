package vista.reportes.sources.graficos;

import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author Iuga
 */
public class GraficoDeTorta {

    private String title;
    private PieDataset dataset;

    public GraficoDeTorta(String title, PieDataset dataset) {
        this.title = title;
        this.dataset = dataset;
    }

    public JFreeChart createGraph() {
        JFreeChart chart = ChartFactory.createPieChart(
            this.title,  // chart title
            dataset,             // data
            true,               // include legend
            true,
            false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        plot.setNoDataMessage("Datos Insuficientes");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;
    }
}
