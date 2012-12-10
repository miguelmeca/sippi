/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import controlador.planificacion.PlanificacionUtils;
import java.util.*;
import java.util.List;
import modelo.DetalleTareaPlanificacion;
import modelo.Empleado;
import modelo.Planificacion;
import modelo.TareaPlanificacion;
import vista.reportes.ReportDesigner;

/**
 * Muestra un listado de empleados que fueron asignados a una Obra !
 * @author Iuga
 */
public class InformeDePlanificaiconEmpleadosEnObra extends InformeDePlanificacion{

    private Planificacion plan;
    private HashMap<Integer,AssetInformeEmpleado> stash;
    
    public InformeDePlanificaiconEmpleadosEnObra(Planificacion planificacion) {
        super(planificacion);
        this.plan = planificacion;
        stash = new HashMap<Integer, AssetInformeEmpleado>();
    }
  
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        // Armo la cabecera
        makeCabecera(params);
        
        // Intro
        Paragraph pintro = new Paragraph();
            pintro.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nroCot = new Phrase("A continuación se listan los empleados asignados a la obra:");
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
            // getAsignacionEmpleadoPlanificacion() y para cada una de estas preguntar:
             List<DetalleTareaPlanificacion> listadoDetalles = tareaPlanificacion.getDetalles();
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
                         // Acumulo las horas N,50,100 a las que estaban agregadas
                         AssetInformeEmpleado aie = stash.get(empleado.hashCode());
                         aie.addHorasN(detalleTareaPlanificacion.getCantHorasNormales());
                         aie.addHoras50(detalleTareaPlanificacion.getCantHorasAl50());
                         aie.addHoras100(detalleTareaPlanificacion.getCantHorasAl100());
                     }
                     else
                     {
                         // No esta agregado
                         // Agrego el empleado al hash y seteo las horas asignadas N,50,100
                         AssetInformeEmpleado aie = new AssetInformeEmpleado(empleado,detalleTareaPlanificacion.getCantHorasNormales(),detalleTareaPlanificacion.getCantHorasAl50(),detalleTareaPlanificacion.getCantHorasAl100());
                         stash.put(empleado.hashCode(), aie);
                     }
                 }
            }
        }
        // Fin Calculo de datos para el informe
        // Armo y muestro el informe
        
        // Armo la tabla
        PdfPTable tabla = new PdfPTable(6);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(99);
        
            // Encabezado
            tabla.addCell(createHeadCell("Nombre del Empleado",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("DNI / CUIL",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Hs. Normales Totales",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Hs. 50% Totales",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Hs. 100% Totales",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
            tabla.addCell(createHeadCell("Teléfono",ReportDesigner.COLOR_HEADINGS, PdfPCell.ALIGN_CENTER));
        
        // Por cada Asset, armo su fila
        Iterator it = stash.entrySet().iterator();      
        
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            
            AssetInformeEmpleado aie = (AssetInformeEmpleado)e.getValue();
            String nombreEmpleado = aie.getEmpleado().getNombreEmpleado();
            if(nombreEmpleado!=null)
            {
                tabla.addCell(createNormalCell(nombreEmpleado));
            }
            else
            {
                tabla.addCell("");
            }
            
            String dni = aie.getEmpleado().getCuil();
            if(dni!=null)
            {
                tabla.addCell(createNormalCell(dni));
            }
            else
            {
                tabla.addCell("");
            }
            
            tabla.addCell(createNormalCell(String.valueOf(aie.getHorasN())));
            tabla.addCell(createNormalCell(String.valueOf(aie.getHoras50())));
            tabla.addCell(createNormalCell(String.valueOf(aie.getHoras100())));
                        
            String telefonos = aie.getEmpleado().mostrarTelefonos();
            if(telefonos!=null)
            {
                tabla.addCell(createNormalCell(telefonos));
            }
            else
            {
                tabla.addCell("");
            }
        }
        
        if(stash.isEmpty())
        {
           PdfPCell nada = new PdfPCell(new Paragraph("No hay asignaciones a Empleados para esta Obra",ReportDesigner.FUENTE_NORMAL));
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
     * Clase interna que solo sirve para ordenar y almacenar la información de los assets
     * para el informe.
     */
    public class AssetInformeEmpleado{
        
        private Empleado empleado;
        private double   horasN;
        private double   horas50;
        private double   horas100;

        public AssetInformeEmpleado(Empleado empleado, Double horasN, Double horas50, Double horas100) {
            this.empleado = empleado;
            this.horasN = horasN;
            this.horas50 = horas50;
            this.horas100 = horas100;
        }
        
        public void addHorasN(double cant)
        {
            this.horasN = this.horasN+cant;
        }

        public void addHoras50(double cant)
        {
            this.horas50 = this.horas50+cant;
        }
        
        public void addHoras100(double cant)
        {
            this.horas100 = this.horas100+cant;
        }

        public Empleado getEmpleado() {
            return empleado;
        }

        public double getHoras100() {
            return horas100;
        }

        public double getHoras50() {
            return horas50;
        }

        public double getHorasN() {
            return horasN;
        }
        
    }
}
