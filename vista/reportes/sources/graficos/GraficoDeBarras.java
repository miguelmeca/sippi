/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources.graficos;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author Administrador
 */
public class GraficoDeBarras {

    private String title;
    private CategoryItemRenderer categoryRenderer;
    private CategoryDataset dataset;
    private String categoryName;
    private String valueName;
    
    
    public GraficoDeBarras(String title, CategoryDataset dataset,String categoryName,String valueName) {
        this.title = title;
        this.dataset = dataset;
        this.categoryName = categoryName;
        this.valueName = valueName;
    }

    public JFreeChart createGraph() {

        final JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                categoryName, // domain axis label
                valueName, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // the plot orientation
                false, // include legend
                true,
                false);
        
        chart.setBackgroundPaint(Color.WHITE);

        final CategoryPlot plot = chart.getCategoryPlot();
        if(categoryRenderer!=null){
            // SEt
            plot.setRenderer(categoryRenderer);
        }
        
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);        
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        
        return chart;
    }

    public CategoryItemRenderer getCategoryRenderer() {
        return categoryRenderer;
    }

    public void setCategoryRenderer(CategoryItemRenderer categoryRenderer) {
        this.categoryRenderer = categoryRenderer;
    }
    
    
}
