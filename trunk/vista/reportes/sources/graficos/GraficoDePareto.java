/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources.graficos;

import com.itextpdf.text.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.util.SortOrder;

/**
 *
 * @author Administrador
 */
public class GraficoDePareto {
    
    private String title;
    private HashMap<String,Integer> datos;

    public GraficoDePareto(String title, HashMap<String, Integer> datos) {
        this.title = title;
        this.datos = datos;
    }

    public JFreeChart createGraph() {
        final DefaultKeyedValues data = new DefaultKeyedValues();
        
        Iterator it = datos.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry)it.next();
                data.addValue(""+e.getKey(),(Integer)e.getValue());
            }
            
        data.sortByValues(SortOrder.DESCENDING);
        
        final KeyedValues cumulative = DataUtilities.getCumulativePercentages(data);
        final CategoryDataset dataset = DatasetUtilities.createCategoryDataset("A", data);        
        
         // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            title,  // chart title
            "",  // domain axis label
            "",  // range axis label
            dataset,                        // data
            PlotOrientation.VERTICAL,
            false,                           // include legend
            false,
            false
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        //chart.addSubtitle(new TextTitle("By Programming Language"));
        //chart.addSubtitle(new TextTitle("As at 5 March 2003"));

        // set the background color for the chart...
        chart.setBackgroundPaint(new Color(0xFFFFFF));
        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();

        final CategoryDataset dataset2 = DatasetUtilities.createCategoryDataset(
            "Acumulador", cumulative
        );
        final NumberAxis axis2 = new NumberAxis("%");
        axis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);
        plot.mapDatasetToRangeAxis(1, 1);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        // OPTIONAL CUSTOMISATION COMPLETED.     
        
        return chart;
    }
    
    
    
}
