/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources.graficos;

import java.awt.Color;
import java.text.SimpleDateFormat;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Administrador
 */
public class GraficoDeBarrasMultiplesTemporal {

    private String title;
    private XYDataset data;

    public GraficoDeBarrasMultiplesTemporal(String title, XYDataset data) {
        this.title = title;
        this.data = data;
    }
    
    public JFreeChart createGraph() {
        
        final XYItemRenderer renderer1 = new XYBarRenderer();
        
        final DateAxis domainAxis = new DateAxis("Fechas");
        domainAxis.setVerticalTickLabels(true);
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
        domainAxis.setLowerMargin(0.01);
        domainAxis.setUpperMargin(0.01);
        final ValueAxis rangeAxis = new NumberAxis("Esfuerzo realizado");
        
        final XYPlot plot = new XYPlot(data, domainAxis, rangeAxis, renderer1);
        
        final JFreeChart chart = new JFreeChart(title, plot);
        
        // Setting the chart properties 
        chart.setBackgroundPaint(Color.white);
        // Setting the plot properties 
        plot.setBackgroundPaint(Color.lightGray); 
        plot.setDomainGridlinePaint(Color.white); 
        plot.setRangeGridlinePaint(Color.white); 
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0)); 
        plot.setDomainCrosshairVisible(true); 
        plot.setRangeCrosshairVisible(true); 
        DateAxis axis = (DateAxis) plot.getDomainAxis();axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy")); 
        return (chart);
    }
    
   
    
    
    
}
