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
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import vista.reportes.ReportDesigner;

/**
 * Muestra un listado de empleados que fueron asignados a una Obra !
 * @author Iuga
 */
public class InformeDePlanificacionTareasXEmpleado extends InformeDePlanificacion{

    private PlanificacionXXX plan;
    private HashMap<Integer,EmpleadoInforme> stash;
    
    public InformeDePlanificacionTareasXEmpleado(PlanificacionXXX planificacion) {
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
                         DetalleTareasEmpleado detalle = new DetalleTareasEmpleado();
                         detalle.setTarea(tareaPlanificacion.getNombre());
                         detalle.setFechaInicio(tareaPlanificacion.getFechaInicio());
                         detalle.setFechaFin(tareaPlanificacion.getFechaFin());
                         empInf.getTareas().add(detalle);
                     }
                     else
                     {
                         // No esta agregado
                         EmpleadoInforme emp = new EmpleadoInforme(empleado,new ArrayList<DetalleTareasEmpleado>());
                         stash.put(empleado.hashCode(), emp);
                     }
                 }
            }
        }
        // Fin Calculo de datos para el informe
        // Armo y muestro el informe
        
        // Armo la tabla
        PdfPTable tabla = new PdfPTable(4);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(99);
        
        // Encabezado
        tabla.addCell(createHeadCell("Empleado",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Tarea",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Fecha de Inicio",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        tabla.addCell(createHeadCell("Fecha e Fin",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        
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
            Iterator<DetalleTareasEmpleado> itDetalles =  emp.getTareas().iterator();
            while(itDetalles.hasNext())
            {
                DetalleTareasEmpleado dte = itDetalles.next();
                tareas.concat(dte.getTarea()+"\n");
                fechaInicio.concat(dte.getFechaInicio()+"\n");
                fechaFin.concat(dte.getFechaFin()+"\n");
            }
            
            tabla.addCell(createNormalCell(String.valueOf(tareas)));
            tabla.addCell(createNormalCell(String.valueOf(fechaInicio)));
            tabla.addCell(createNormalCell(String.valueOf(fechaFin)));
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
    
    
    public class EmpleadoInforme
    {
        private Empleado empleado;
        private List<DetalleTareasEmpleado> tareas;

        public EmpleadoInforme(Empleado empleado, List<DetalleTareasEmpleado> tareas) {
            this.empleado = empleado;
            this.tareas = tareas;
        }
        
        public Empleado getEmpleado() {
            return empleado;
        }

        public void setEmpleado(Empleado empleado) {
            this.empleado = empleado;
        }

        public List<DetalleTareasEmpleado> getTareas() {
            return tareas;
        }

        public void setTareas(List<DetalleTareasEmpleado> tareas) {
            this.tareas = tareas;
        }
    }
    
    public class DetalleTareasEmpleado
    {
        private String tarea;
        private Date fechaInicio;
        private Date fechaFin;

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
    }
}
