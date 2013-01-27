/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources.graficos;

import java.awt.Color;
import java.awt.Paint;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Administrador
 */
public class GraficoDeLineasSerieDeTiempo {

    private String title;
    private XYDataset data;
    private String etiqueta;

    public final static String ETIQUETA_DINERO = "$ {2}";
    public final static String ETIQUETA_HORAS = "{2} hs.";
    public final static String ETIQUETA_UNIDADES = "{2} unid.";
    public final static String ETIQUETA_VACIA = "";
    
    /**
     * Permite crear un gráfico de líneas temporal
     * @param title El nombre del gráfico
     * @param data Los datos a mostrar
     * @param etiqueta La etiqueta que mostrará cada uno de los puntos
     */
    public GraficoDeLineasSerieDeTiempo(String title, XYDataset data, String etiqueta) {
        this.title = title;
        this.data = data;
        this.etiqueta = etiqueta;
    }
    
    public JFreeChart createGraph() {
        
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            title,             // chart title
            "",               // domain axis label
            "",                  // range axis label
            data,                  // data
            true,                     // include legend
            false,                     // tooltips
            false                     // urls
        );
        
        // Setting the chart properties 
        chart.setBackgroundPaint(Color.white);
        // Setting the plot properties 
        XYPlot plot = (XYPlot) chart.getPlot(); 
        plot.setBackgroundPaint(Color.lightGray); 
        plot.setDomainGridlinePaint(Color.white); 
        plot.setRangeGridlinePaint(Color.white); 
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0)); 
        plot.setDomainCrosshairVisible(true); 
        plot.setRangeCrosshairVisible(true); 
        XYItemRenderer renderer = plot.getRenderer();
        
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2); // etc.
        XYItemLabelGenerator generator = new StandardXYItemLabelGenerator(etiqueta, format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy")); 
        axis.setAutoRange(true);
        return (chart);
    }
    
   
    
    
    
}
