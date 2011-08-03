/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.cotizacion;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;
import org.jfree.util.UnitType;
import vista.gui.TortaRotator;

/**
 *
 * @author iuga
 */
public class CotizacionGraficoTortaSubObras extends JPanel {

    public CotizacionGraficoTortaSubObras()
    {
        setLayout(new java.awt.BorderLayout());

        final DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Construcción [$2000]", new Double(80));
        data.setValue("Desmontaje [$500]", new Double(20));


        final JFreeChart chart = ChartFactory.createPieChart3D(
            "",  // chart title
            data,                   // data
            false,                   // include legend
            true,
            false
        );

        chart.setBackgroundPaint(new Color(242,241,240));
        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setInsets(new RectangleInsets(0, 0, 0, 0));
        plot.setStartAngle(270);
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setForegroundAlpha(0.60f);
        //plot.setInteriorGap(0.50); // NO DESCOMENTAR ESTA LINEA, PERDI CASI 2 HORAS POR ESTA PUTA DE MIERDA

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(242,241,240));
        chartPanel.setPreferredSize(new java.awt.Dimension(100, 250));
        chartPanel.setMouseZoomable(true);
        chartPanel.setBounds(0,0,chartPanel.getWidth(),chartPanel.getHeight());
        add(chartPanel,BorderLayout.CENTER);

//        final TortaRotator rotator = new TortaRotator(plot);
//        rotator.start();

    }



}
