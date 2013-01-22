/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources.graficos;

import java.awt.Color;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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

    public GraficoDeLineasSerieDeTiempo(String title, XYDataset data) {
        this.title = title;
        this.data = data;
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
        XYItemRenderer r = plot.getRenderer();

        DateAxis axis = (DateAxis) plot.getDomainAxis();axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy")); 
        return (chart);
    }
    
   
    
    
    
}
