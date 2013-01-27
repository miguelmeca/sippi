/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import modelo.Empleado;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import util.FechaUtil;
import util.HibernateUtil;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.graficos.GraficoDeBarrasMultiplesTemporal;
import vista.reportes.sources.graficos.GraficoDeLineasSerieDeTiempo;

/**
 *
 * @author Administrador
 */
public class InformeHorasEjecutadasPorEmpleado extends ReportDesigner{
    
    private HashMap<Integer,HashMap<String,Integer>> empleados;
    public static int INFORME_MENSUAL = 1;
    public static int INFORME_DIARIO = 2;
    private int tipoInforme;
    
    public InformeHorasEjecutadasPorEmpleado(int tipoInforme)
    {
        super();
        this.tipoInforme = tipoInforme;
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        this.empleados = (HashMap<Integer,HashMap<String,Integer>>) params.get("EMPLEADOS");
              
        mostrarObjetivoDelInforme();
        try{
            mostarGraficoEsfuerzo();
        }catch(Exception e){
            throw new DocumentException(e);
        }
        
//        mostrarTablaDeDatos();
    }    
    
    private void mostrarObjetivoDelInforme() throws DocumentException {
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Gráfico comparativo de las horas "
                + "trabajadas por los empleados por mes.",ReportDesigner.FUENTE_INFORMES_NORMAL));
        super.doc.add(PTitulo);
    }    

    private void mostarGraficoEsfuerzo() throws Exception {
        
        if(tipoInforme == INFORME_DIARIO) // TODO: Nunca se terminó
        {
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            Iterator<Entry<Integer, HashMap<String, Integer>>> itEmpleados = empleados.entrySet().iterator();
            while(itEmpleados.hasNext())
            {
                // Obtengo los datos del empleado
                Entry<Integer, HashMap<String, Integer>> empleado = itEmpleados.next();
                HibernateUtil.beginTransaction();
                Empleado emp = (Empleado)HibernateUtil.getSession().get(Empleado.class,empleado.getKey());
                HibernateUtil.commitTransaction();


                // Creando la línea del empleado iterado
                TimeSeries timeSerie = new TimeSeries(emp.getNombreEmpleado());
                HashMap<String, Integer> dias = empleado.getValue();
                Iterator<Entry<String, Integer>> itDiasEmpleado = dias.entrySet().iterator();
                while(itDiasEmpleado.hasNext())
                {
                    Entry<String, Integer> diaEmpleado = itDiasEmpleado.next();
                    Date fechaAsig = FechaUtil.getDate(diaEmpleado.getKey());
                    Day day = new Day(
                            Integer.parseInt(FechaUtil.getDay(fechaAsig)),
                            Integer.parseInt(FechaUtil.getMonth(fechaAsig)),
                            Integer.parseInt(FechaUtil.getYear(fechaAsig)));
                    timeSerie.add(day, diaEmpleado.getValue());
                }
                dataset.addSeries(timeSerie);
            }

    //        GraficoDeLineasSerieDeTiempo gl = new GraficoDeLineasSerieDeTiempo("Esfuerzo diario por Empleado", dataset);
    //        JFreeChart chart = gl.createGraph();

            GraficoDeBarrasMultiplesTemporal gbt = new GraficoDeBarrasMultiplesTemporal("", dataset);
            JFreeChart chart = gbt.createGraph();

            super.insertarGraficoCentrado(chart,500,180);
        }
        else
        {
            if(tipoInforme == INFORME_MENSUAL)
            {
                TimeSeriesCollection dataset = new TimeSeriesCollection();
                Iterator<Entry<Integer, HashMap<String, Integer>>> itEmpleados = empleados.entrySet().iterator();
                while(itEmpleados.hasNext())
                {
                    // Obtengo los datos del empleado
                    Entry<Integer, HashMap<String, Integer>> empleado = itEmpleados.next();
                    HibernateUtil.beginTransaction();
                    Empleado emp = (Empleado)HibernateUtil.getSession().get(Empleado.class,empleado.getKey());
                    HibernateUtil.commitTransaction();


                    // Creando la línea del empleado iterado
                    TimeSeries timeSerie = new TimeSeries(emp.getNombreEmpleado());
                    HashMap<String, Integer> dias = empleado.getValue();
                    Iterator<Entry<String, Integer>> itDiasEmpleado = dias.entrySet().iterator();
                    while(itDiasEmpleado.hasNext())
                    {
                        Entry<String, Integer> diaEmpleado = itDiasEmpleado.next();
                        String[] fechaAsig = diaEmpleado.getKey().split("-");
                        Month month = new Month(
                                Integer.parseInt(fechaAsig[0]),
                                Integer.parseInt(fechaAsig[1]));
                        timeSerie.add(month, diaEmpleado.getValue());
                    }
                    dataset.addSeries(timeSerie);
                }

                GraficoDeLineasSerieDeTiempo gl =
                        new GraficoDeLineasSerieDeTiempo(
                        "",
                        dataset,
                        GraficoDeLineasSerieDeTiempo.ETIQUETA_HORAS);
                JFreeChart chart = gl.createGraph();

                super.insertarGraficoCentrado(chart,500,180);
            }
            else
            {
                throw new Exception("Tipo de Informe equivocado");
            }
        }
            
    }

//    private void mostrarTablaDeDatos() throws DocumentException {
//        PdfPTable tabla = new PdfPTable(2);
//        tabla.setSpacingBefore(15f);
//        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
//        tabla.setWidthPercentage(50);
//        float[] anchos = {4f,4f};
//        tabla.setWidths(anchos);
//        
//        insertarCeldaHeaderEnTabla(tabla,"Año de Referencia",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
//        insertarCeldaHeaderEnTabla(tabla,"Cantidad de Obras Finalizadas",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
//       
//        // Sort Map
//        List<String> yearsToSort = new ArrayList<String>(listaObrasHistorico.keySet());
//        Collections.sort(yearsToSort, new Comparator<String>() {
//            @Override
//            public int compare(String t1, String t2) {
//                int year1 = Integer.valueOf(t1);
//                int year2 = Integer.valueOf(t2);
//                return year2-year1;
//            }
//        });
//        
//        int total = 0;
//        for (int i = 0; i < yearsToSort.size(); i++) {
//            String year = yearsToSort.get(i);
//            int cantidad = listaObrasHistorico.get(year);
//            
//            insertarCeldaEnTabla(tabla,year,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
//            insertarCeldaEnTabla(tabla,""+cantidad,ReportDesigner.FUENTE_INFORMES_NORMAL,PdfPCell.ALIGN_CENTER);
//     
//            total += cantidad;
//        }
//        
//        insertarCeldaEnTabla(tabla,"Total",ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
//        insertarCeldaEnTabla(tabla,""+total,ReportDesigner.FUENTE_INFORMES_NORMAL_B,PdfPCell.ALIGN_CENTER);
//        
//        super.doc.add(tabla);
//    }
    
}
