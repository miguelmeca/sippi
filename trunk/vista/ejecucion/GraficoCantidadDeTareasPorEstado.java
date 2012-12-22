package vista.ejecucion;

import java.awt.Dimension;
import java.awt.Paint;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import modelo.TareaEjecucion;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RefineryUtilities;
import util.SwingPanel;
import vista.reportes.sources.graficos.GraficoDeBarras;

/**
 *
 * @author Iuga
 */
public class GraficoCantidadDeTareasPorEstado{
    
    public void showGraph(double[][] data){
        
        final CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {TareaEjecucion.ESTADO_COLOR_NUEVA, 
                         TareaEjecucion.ESTADO_COLOR_ENPROGRESO,
                         TareaEjecucion.ESTADO_COLOR_COMPLETA,
                         TareaEjecucion.ESTADO_COLOR_CANCELADA, 
                         TareaEjecucion.ESTADO_COLOR_ENESPERA,
                         TareaEjecucion.ESTADO_COLOR_IMPEDIMENTO}
        );
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(data[0][0],"Row1",TareaEjecucion.ESTADO_NUEVA);
        dataset.addValue(data[0][1],"Row1",TareaEjecucion.ESTADO_ENPROGRESO);
        dataset.addValue(data[0][2],"Row1",TareaEjecucion.ESTADO_COMPLETA);
        dataset.addValue(data[0][3],"Row1",TareaEjecucion.ESTADO_CANCELADA);
        dataset.addValue(data[0][4],"Row1",TareaEjecucion.ESTADO_ENESPERA);
        dataset.addValue(data[0][5],"Row1",TareaEjecucion.ESTADO_IMPEDIMENTO);

        
        
        GraficoDeBarras gdb = new GraficoDeBarras("", dataset,"Estados","Cantidad");
        gdb.setCategoryRenderer(renderer);
        final JFreeChart chart = gdb.createGraph();
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(200, 100));
        final JInternalFrame frame = new JInternalFrame();
        frame.setSize(500,400);
        frame.getContentPane().add(chartPanel);   
        frame.setTitle("Cantidad de Tareas por Estado");
        frame.setClosable(true);
        frame.setMaximizable(true);
        frame.setIconifiable(true);
        //RefineryUtilities.centerFrameOnScreen(frame);
        SwingPanel.getInstance().addWindow(frame);
        frame.setVisible(true);
    }
    
    public class CustomRenderer extends BarRenderer {

        /** The colors. */
        private Paint[] colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour inherited from
         * AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }
    
}
