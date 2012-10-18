/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import controlador.planificacion.PlanificacionUtils;
import java.util.*;
import modelo.DetalleTareaPlanificacion;
import modelo.Empleado;
import modelo.Planificacion;
import modelo.TareaPlanificacion;
import util.FechaUtil;
import vista.reportes.ReportDesigner;

/**
 * Muestra un listado de empleados que fueron asignados a una Obra !
 * @author Iuga
 */
public class InformeDePlanificacionTareasXEmpleado extends InformeDePlanificacion{

    private Planificacion plan;
    private HashMap<Integer,EmpleadoInforme> stash;
    
    /**
     * Segun este campo, apareceran o no en el informe las cantidades de horas 
     * normales, al 50% y al 100%
     */
    private boolean conHoras=false;
    
    public InformeDePlanificacionTareasXEmpleado(Planificacion planificacion) {
        super(planificacion);
        this.plan = planificacion;
        stash = new HashMap<Integer, EmpleadoInforme>();
    }
  
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        // Armo la cabecera
        makeCabecera(params);
        
        // Intro
        Paragraph pintro = new Paragraph();
            pintro.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nroCot = new Phrase("A continuación se listan los empleados y sus asignaciones a la obra:");
            pintro.add(nroCot);    
        super.doc.add(pintro);
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
        
        // Inicio de Calculo de datos para el informe
        // Para la planificacion en curso hacer:
        // Recorrer todas sus tareas y subtareas y para cada una hacer:
        List<TareaPlanificacion> listado = PlanificacionUtils.getTodasTareasPlanificacion(this.plan);
        for (int i = 0; i < listado.size(); i++) {
            TareaPlanificacion tareaPlanificacion = listado.get(i);
            
            // Recorrer cada detalle (Fran)
             List<DetalleTareaPlanificacion> listadoDetalles = tareaPlanificacion.getDetallesSinDetallesVacios();
             for (int j = 0; j < listadoDetalles.size(); j++) {
                DetalleTareaPlanificacion detalleTareaPlanificacion = listadoDetalles.get(j);
                
                //Los getEmpleado() esta ya agregado al Hash??
                 List<Empleado> listadoEmpelados = detalleTareaPlanificacion.getEmpleados();
                 for (int k = 0; k < listadoEmpelados.size(); k++) {
                     Empleado empleado = listadoEmpelados.get(k);
                     
                     // Esta agregado al Hash??
                     if(stash.containsKey(empleado.hashCode()))
                     {
                         // Ya esta agregado
                         EmpleadoInforme empInf = stash.get(empleado.hashCode());
                         boolean tareaYaAgregada=false;
                         List<TareaDeEmpleado> tareas=empInf.getTareas();
                         int indiceTarea=-1;
                         for (int l = 0; l < tareas.size(); l++) {
                             if(tareas.get(l).getHash()==tareaPlanificacion.hashCode()){
                                 tareaYaAgregada=true;
                                 indiceTarea=l;
                             }
                             
                         }
                         if(!tareaYaAgregada) {
                             TareaDeEmpleado detalle = new TareaDeEmpleado();
                            detalle.setTarea(tareaPlanificacion.getNombre());
                            detalle.setFechaInicio(tareaPlanificacion.getFechaInicio());
                            detalle.setFechaFin(tareaPlanificacion.getFechaFin());
                            detalle.setHash(tareaPlanificacion.hashCode());
                            detalle.setCantHorasNormales(tareaPlanificacion.obtenerTotalDeHorasNormalesSinSubtareas());
                            detalle.setCantHorasAl50(tareaPlanificacion.obtenerTotalDeHorasAl50SinSubtareas());
                            detalle.setCantHorasAl100(tareaPlanificacion.obtenerTotalDeHorasAl100SinSubtareas());
                            tareas.add(detalle);
                         }
                         else {
                             tareas.get(indiceTarea).setCantHorasNormales(tareas.get(indiceTarea).getCantHorasNormales()+tareaPlanificacion.obtenerTotalDeHorasNormalesSinSubtareas());
                            tareas.get(indiceTarea).setCantHorasAl50(tareas.get(indiceTarea).getCantHorasAl50()+tareaPlanificacion.obtenerTotalDeHorasAl50SinSubtareas());
                            tareas.get(indiceTarea).setCantHorasAl100(tareas.get(indiceTarea).getCantHorasAl100()+tareaPlanificacion.obtenerTotalDeHorasAl100SinSubtareas());
                         }
                     }
                     else
                     {
                         // No esta agregado
                         EmpleadoInforme emp = new EmpleadoInforme(empleado,new ArrayList<TareaDeEmpleado>());
                         TareaDeEmpleado detalle = new TareaDeEmpleado();
                         detalle.setTarea(tareaPlanificacion.getNombre());
                         detalle.setFechaInicio(tareaPlanificacion.getFechaInicio());
                         detalle.setFechaFin(tareaPlanificacion.getFechaFin());
                         detalle.setHash(tareaPlanificacion.hashCode());
                         detalle.setCantHorasNormales(tareaPlanificacion.obtenerTotalDeHorasNormalesSinSubtareas());
                         detalle.setCantHorasAl50(tareaPlanificacion.obtenerTotalDeHorasAl50SinSubtareas());
                         detalle.setCantHorasAl100(tareaPlanificacion.obtenerTotalDeHorasAl100SinSubtareas());
                         emp.getTareas().add(detalle);
                         stash.put(empleado.hashCode(), emp);
                     }
                 }
            }
        }
        // Fin Calculo de datos para el informe
        // Armo y muestro el informe
        
        // Armo la tabla
        PdfPTable tabla;
        if(isConHoras()) {
           tabla = new PdfPTable(7);}
        else {
           tabla = new PdfPTable(4); 
        }
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(99);
        
        // Encabezado
        tabla.addCell(createHeadCell("Empleado",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Tarea",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Fecha de Inicio",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Fecha e Fin",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        if(isConHoras()) {
            tabla.addCell(createHeadCell("Cantidad de horas normales",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Cantidad de horas al 50%",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Cantidad de horas al 100%",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        }
        // Por cada Asset, armo su fila
        Iterator it = stash.entrySet().iterator();      
        
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            
            EmpleadoInforme emp = (EmpleadoInforme)e.getValue();
            String nombreEmpleado = emp.getEmpleado().getNombreEmpleado();
            if(nombreEmpleado!=null)
            {
                tabla.addCell(createNormalCell(nombreEmpleado));
            }
            else
            {
                tabla.addCell("");
            }
            String tareas = "", fechaInicio = "", fechaFin = "";
            String cantHorasNormales = "", cantHoras50="", cantHoras100="";
            Iterator<TareaDeEmpleado> itDetalles =  emp.getTareas().iterator();
            while(itDetalles.hasNext())
            {
                TareaDeEmpleado dte = itDetalles.next();
                tareas=tareas.concat(dte.getTarea()+"\n");
                fechaInicio=fechaInicio.concat(FechaUtil.getFecha(dte.getFechaInicio())+"\n");
                fechaFin=fechaFin.concat(FechaUtil.getFecha(dte.getFechaFin())+"\n");
                cantHorasNormales=cantHorasNormales.concat(dte.getCantHorasNormales()+"\n");
                cantHoras50=cantHoras50.concat(dte.getCantHorasAl50()+"\n");
                cantHoras100=cantHoras100.concat(dte.getCantHorasAl100()+"\n");
            }
            
            tabla.addCell(createNormalCell(String.valueOf(tareas)));
            tabla.addCell(createNormalCell(String.valueOf(fechaInicio)));
            tabla.addCell(createNormalCell(String.valueOf(fechaFin)));
            if(isConHoras()) {
                tabla.addCell(createNormalCell(String.valueOf(cantHorasNormales)));
                tabla.addCell(createNormalCell(String.valueOf(cantHoras50)));
                tabla.addCell(createNormalCell(String.valueOf(cantHoras100)));
            }
        }
        
        if(stash.isEmpty())
        {
           PdfPCell nada = new PdfPCell(new Paragraph("No hay empleados asignados a esta Obra",ReportDesigner.FUENTE_NORMAL));
           nada.setColspan(6);
           nada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           nada.setPadding(5);
           tabla.addCell(nada);
        }
        
        // Agrego la tabla
        super.doc.add(tabla);
    }
    
    private PdfPCell createHeadCell(String nombre, BaseColor color, int align)
    {
        PdfPCell celdaHead1 = new PdfPCell(new Paragraph(nombre,ReportDesigner.FUENTE_NORMAL_BK));
            celdaHead1.setPaddingLeft(0);
            celdaHead1.setHorizontalAlignment(align);
            celdaHead1.setBackgroundColor(color);
        return celdaHead1;
    }
    
    private PdfPCell createNormalCell(String data)
    {
        return new PdfPCell(new Paragraph(data,ReportDesigner.FUENTE_NORMAL));
    }

    /**
     * Segun este campo, apareceran o no en el informe las cantidades de horas 
     * normales, al 50% y al 100%
     */
    public boolean isConHoras() {
        return conHoras;
    }

    /**
     * Segun este campo, apareceran o no en el informe las cantidades de horas 
     * normales, al 50% y al 100%
     */
    public void setConHoras(boolean conHoras) {
        this.conHoras = conHoras;
    }
    
    
    public class EmpleadoInforme
    {
        private Empleado empleado;
        private List<TareaDeEmpleado> tareas;

        public EmpleadoInforme(Empleado empleado, List<TareaDeEmpleado> tareas) {
            this.empleado = empleado;
            this.tareas = tareas;
        }
        
        public Empleado getEmpleado() {
            return empleado;
        }

        public void setEmpleado(Empleado empleado) {
            this.empleado = empleado;
        }

        public List<TareaDeEmpleado> getTareas() {
            return tareas;
        }

        public void setTareas(List<TareaDeEmpleado> tareas) {
            this.tareas = tareas;
        }
    }
    
    public class TareaDeEmpleado
    {
        private String tarea;
        private Date fechaInicio;
        private Date fechaFin;
        private int hash;
        private double cantHorasNormales;
        private double cantHorasAl50;
        private double cantHorasAl100;
        
        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }

        public Date getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(Date fechaFin) {
            this.fechaFin = fechaFin;
        }

        public Date getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(Date fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getTarea() {
            return tarea;
        }

        public void setTarea(String tarea) {
            this.tarea = tarea;
        }               

        /**
         * @return the cantHorasNormales
         */
        public double getCantHorasNormales() {
            return cantHorasNormales;
        }

        /**
         * @param cantHorasNormales the cantHorasNormales to set
         */
        public void setCantHorasNormales(double cantHorasNormales) {
            this.cantHorasNormales = cantHorasNormales;
        }

        /**
         * @return the cantHorasAl50
         */
        public double getCantHorasAl50() {
            return cantHorasAl50;
        }

        /**
         * @param cantHorasAl50 the cantHorasAl50 to set
         */
        public void setCantHorasAl50(double cantHorasAl50) {
            this.cantHorasAl50 = cantHorasAl50;
        }

        /**
         * @return the cantHorasAl100
         */
        public double getCantHorasAl100() {
            return cantHorasAl100;
        }

        /**
         * @param cantHorasAl100 the cantHorasAl100 to set
         */
        public void setCantHorasAl100(double cantHorasAl100) {
            this.cantHorasAl100 = cantHorasAl100;
        }
    }
}
