/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import modelo.Herramienta;
import modelo.Material;
import modelo.TipoAdicional;
import modelo.TipoAlquilerCompra;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeLineasSerieDeTiempo;

/**
 *
 * @author Administrador
 */
public class InformeComprasPorMes extends ReportDesigner{
    
    HashMap<String,HashMap<String,Double>> compras;
    
    public InformeComprasPorMes()
    {
        super();
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.compras = (HashMap<String,HashMap<String,Double>>) params.get("COMPRAS");
              
        mostrarObjetivoDelInforme();
        super.doc.add(new Paragraph("\n"));
        try{
            mostarGraficoComprasTotales();
            super.doc.add(new Paragraph("\n"));
            Class clase = Material.class;
            mostarGraficoComprasRecurso("Compra de materiales mensuales", clase.getName());
            super.doc.add(new Paragraph("\n"));
            clase = Herramienta.class;
            mostarGraficoComprasRecurso("Compra de herramientas mensuales", clase.getName());
            super.doc.add(new Paragraph("\n"));
            clase = TipoAlquilerCompra.class;
            mostarGraficoComprasRecurso("Alquileres y Compras mensuales", clase.getName());
            super.doc.add(new Paragraph("\n"));
            clase = TipoAdicional.class;
            mostarGraficoComprasRecurso("Gastos Varios mensuales", clase.getName());
            
        }catch(Exception e){
            throw new DocumentException(e);
        }
    }    
    
    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Gráfico comparativo de las compras "
                + "realizadas por mes.\n",ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }    

    private void mostarGraficoComprasTotales() throws Exception {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Iterator<String> itCompras = compras.keySet().iterator();
        TimeSeries timeSerie = new TimeSeries("Compras totales realizadas por mes");
        while(itCompras.hasNext())
        {
            String key = itCompras.next();
            String[] fechaAsig = key.split("-");
            Month month = new Month(
                Integer.parseInt(fechaAsig[0]),
                Integer.parseInt(fechaAsig[1]));
            Double auxPrecio = 0d;
            HashMap<String,Double> compra = compras.get(key);
            Iterator<Double> itPrecios = compra.values().iterator();
            while(itPrecios.hasNext())
            {
                Double precio = itPrecios.next();
                auxPrecio += precio;
                
            }
            timeSerie.add(month, auxPrecio);
        }
        dataset.addSeries(timeSerie);
        
        GraficoDeLineasSerieDeTiempo gl =
                new GraficoDeLineasSerieDeTiempo(
                "Gráfico de compras totales por mes",
                dataset,
                GraficoDeLineasSerieDeTiempo.ETIQUETA_DINERO);
        JFreeChart chart = gl.createGraph();

        super.insertarGraficoCentrado(chart,500,180);
    }   
    
    private void mostarGraficoComprasRecurso(String titulo, String tipo) throws Exception {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Iterator<String> itCompras = compras.keySet().iterator();
        TimeSeries timeSerie = new TimeSeries(titulo);
        while(itCompras.hasNext())
        {
            String key = itCompras.next();
            String[] fechaAsig = key.split("-");
            Month month = new Month(
                Integer.parseInt(fechaAsig[0]),
                Integer.parseInt(fechaAsig[1]));
            Double auxPrecio = 0d;
            HashMap<String,Double> compra = compras.get(key);
            Iterator<String> itPrecios = compra.keySet().iterator();
            while(itPrecios.hasNext())
            {
                String keyPrecios = itPrecios.next();
                if(keyPrecios.equals(tipo))
                {
                    auxPrecio += compra.get(keyPrecios);
                }
                
            }
            timeSerie.add(month, auxPrecio);
        }
        dataset.addSeries(timeSerie);
        
        GraficoDeLineasSerieDeTiempo gl =
                new GraficoDeLineasSerieDeTiempo(
                titulo,
                dataset,
                GraficoDeLineasSerieDeTiempo.ETIQUETA_DINERO);
        JFreeChart chart = gl.createGraph();

        super.insertarGraficoCentrado(chart,500,180);
    }
}
