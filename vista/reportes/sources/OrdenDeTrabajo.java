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
import controlador.ejecucion.EjecucionUtils;
import java.util.HashMap;
import java.util.List;
import modelo.DetalleTareaEjecucion;
import modelo.DetalleTareaPlanificacion;
import modelo.Empleado;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.RecursoEspecifico;
import modelo.TareaEjecucion;
import util.FechaUtil;
import util.HibernateUtil;
import util.RecursosUtil;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class OrdenDeTrabajo extends ReportDesigner {
    
    private static final BaseColor TABLE_HEADER_BASE_COLOR = new BaseColor(230,230,230);
    private static final BaseColor TABLE_SUBHEADER_BASE_COLOR = new BaseColor(240,240,240);
    
    private PedidoObra pedido;
    private TareaEjecucion tarea;

    public OrdenDeTrabajo(PedidoObra pedido, TareaEjecucion tarea) {
        this.pedido = pedido;
        this.tarea = tarea;
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        mostrarNumeroDeOFT();
        mostrarFechaEmision();
        mostrarDatosGenerales();
        mostrarTareaARealizar();
        mostrarPersonalDesignado();
        mostrarRecursosNecesarios();
        mostrarObservacionesGenerales();
        mostrarFirmas();
    }

    private String getNroODT() {
        return EjecucionUtils.getNumeroOrdenDeTrabajo(this.pedido.getNumero(),this.tarea.getId());
    }

    private void mostrarNumeroDeOFT() throws DocumentException {
        Paragraph PNroODT = new Paragraph();
        PNroODT.setAlignment(Paragraph.ALIGN_RIGHT);
        Phrase nroODT = new Phrase("Orden de Trabajo Nº: "+getNroODT(),FUENTE_NORMAL_B);
        PNroODT.add(nroODT);    
        super.doc.add(PNroODT);
    }

    private String getEmision() {
        return FechaUtil.getFechaYHoraActual();
    }

    private void mostrarFechaEmision() throws DocumentException {
        Paragraph PNroODT = new Paragraph();
        PNroODT.setAlignment(Paragraph.ALIGN_RIGHT);
        Phrase nroODT = new Phrase("Emisión: "+getEmision()+"\n",FUENTE_NORMAL);
        PNroODT.add(nroODT);    
        super.doc.add(PNroODT);
    }

    private void mostrarDatosGenerales() throws DocumentException {
        
        mostrarTitulo("DATOS GENERALES");
                
        String nombreCliente = "";
        try {
            EmpresaCliente empresa = (EmpresaCliente)HibernateUtil.getSession().createQuery("from EmpresaCliente EC where :cID in elements(EC.plantas)").setParameter("cID", this.pedido.getPlanta()).uniqueResult();
            nombreCliente = empresa.getRazonSocial();
        } catch (Exception ex) {
            System.err.println("No se pudo encontrar el nombre del Cliente");
        }
        
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Empresa Cliente: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(nombreCliente,ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nPlanta: ",ReportDesigner.FUENTE_NORMAL_B));
        String planta = this.pedido.getPlanta().getRazonSocial()+"("+this.pedido.getPlanta().getDomicilio().toString()+")";
        PTitulo.add( new Phrase(planta,ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nNombre de la Obra: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(this.pedido.getNombre(),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nFecha de Inicio de la Obra: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(this.pedido.getFechaInicio()),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase(" - Fecha de Fin de la Obra: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(this.pedido.getFechaFin()),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nCotización N°: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(this.pedido.getPlanificacion().getNroCotizacionPlanificada()+" Rev:"+this.pedido.getPlanificacion().getCotizacion().getNroRevision(),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase(" - Fecha de Cotización: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(this.pedido.getPlanificacion().getCotizacion().getFechaCreacion()),ReportDesigner.FUENTE_NORMAL));
        super.doc.add(PTitulo);
    }

    private void mostrarTitulo(String titulo) throws DocumentException {
        super.insertarSubTitulo(titulo);
    }

    private void mostrarSaltoDeLinea() throws DocumentException {
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
    }

    private void mostrarTareaARealizar() throws DocumentException {
        
        mostrarTitulo("TAREA A REALIZAR");
        
        Paragraph PTitulo = new Paragraph();
        PTitulo.setAlignment(Paragraph.ALIGN_LEFT);      
        PTitulo.add( new Phrase("Nombre: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(tarea.getNombre(),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nDescripción: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(tarea.getObservaciones(),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase("\nFecha de Inicio: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(tarea.getFechaInicio()),ReportDesigner.FUENTE_NORMAL));
        PTitulo.add( new Phrase(" - Fecha de Fin: ",ReportDesigner.FUENTE_NORMAL_B));
        PTitulo.add( new Phrase(FechaUtil.getFecha(tarea.getFechaFin()),ReportDesigner.FUENTE_NORMAL));        
        super.doc.add(PTitulo);
        
    }

    private void mostrarPersonalDesignado() throws DocumentException {
        mostrarTitulo("PERSONAL DESIGNADO");
        
        PdfPTable tabla = new PdfPTable(7);
        tabla.setSpacingBefore(5f);
        tabla.setWidthPercentage(100);
        float[] anchos = {9f,1f,1f,1f,3f,3f,3f};
        tabla.setWidths(anchos);
        
        Paragraph paNumero = new Paragraph();
        paNumero.add(new Phrase("Nombre del Empelado", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaEmpleado = new PdfPCell(paNumero);
        celdaEmpleado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaEmpleado.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        tabla.addCell(celdaEmpleado);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase("Horas Estimadas", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaDescripcion.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        celdaDescripcion.setColspan(3);
        tabla.addCell(celdaDescripcion);

        Paragraph paUtilizadas = new Paragraph();
        paUtilizadas.add(new Phrase("Utilizadas (Normales)", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaUtilizadas = new PdfPCell(paUtilizadas);
        celdaUtilizadas.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaUtilizadas.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        tabla.addCell(celdaUtilizadas);     
        
        Paragraph paUtilizadas50 = new Paragraph();
        paUtilizadas50.add(new Phrase("Utilizadas (50%)", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaUtilizadas50 = new PdfPCell(paUtilizadas50);
        celdaUtilizadas50.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaUtilizadas50.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        tabla.addCell(celdaUtilizadas50); 
        
        Paragraph paUtilizadas100 = new Paragraph();
        paUtilizadas100.add(new Phrase("Utilizadas (100%)", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaUtilizadas100 = new PdfPCell(paUtilizadas100);
        celdaUtilizadas100.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaUtilizadas100.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        tabla.addCell(celdaUtilizadas100);       
        
        List<DetalleTareaPlanificacion> lista = tarea.getDetalles();
        for (int i = 0; i < lista.size(); i++) {
            DetalleTareaEjecucion detalleTareaPlanificacion = (DetalleTareaEjecucion)lista.get(i);
            List<Empleado> empleados = detalleTareaPlanificacion.getEmpleados();
            for (int j = 0; j < empleados.size(); j++) {
                Empleado empleado = empleados.get(j);
                
                String nombreCompleto = empleado.getNombreEmpleado();
                if(detalleTareaPlanificacion.getEspecialidad()!=null && detalleTareaPlanificacion.getEspecialidad().getTipo()!=null
                        && detalleTareaPlanificacion.getEspecialidad().getRango()!=null){
                    nombreCompleto += " ["+detalleTareaPlanificacion.getEspecialidad().getTipo().getNombre()+" "+detalleTareaPlanificacion.getEspecialidad().getRango().getNombre()+"]";
                }
                PdfPCell celdaC1 = new PdfPCell(new Phrase(nombreCompleto,FUENTE_NORMAL_CHICA));
                
                PdfPCell celdaC2 = new PdfPCell(new Phrase(PdfPCell.ALIGN_CENTER,String.valueOf(detalleTareaPlanificacion.getDetalleTareaPlanificado().getCantHorasNormales()),FUENTE_NORMAL_CHICA));
                    celdaC2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                PdfPCell celdaC3 = new PdfPCell(new Phrase(PdfPCell.ALIGN_CENTER,String.valueOf(detalleTareaPlanificacion.getDetalleTareaPlanificado().getCantHorasAl50()),FUENTE_NORMAL_CHICA));
                    celdaC3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                PdfPCell celdaC4 = new PdfPCell(new Phrase(PdfPCell.ALIGN_CENTER,String.valueOf(detalleTareaPlanificacion.getDetalleTareaPlanificado().getCantHorasAl100()),FUENTE_NORMAL_CHICA));
                    celdaC4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                
                PdfPCell celdaC5 = new PdfPCell(new Phrase(" ",FUENTE_NORMAL_CHICA));
                PdfPCell celdaC6 = new PdfPCell(new Phrase(" ",FUENTE_NORMAL_CHICA));
                PdfPCell celdaC7 = new PdfPCell(new Phrase(" ",FUENTE_NORMAL_CHICA));

                tabla.addCell(celdaC1);
                tabla.addCell(celdaC2);
                tabla.addCell(celdaC3);
                tabla.addCell(celdaC4);
                tabla.addCell(celdaC5);
                tabla.addCell(celdaC6);
                tabla.addCell(celdaC7);
                
            }
        }

        super.doc.add(tabla);
    }
    private void mostrarRecursosNecesarios() throws DocumentException {
        mostrarTitulo("RECURSOS NECESARIOS");
        mostrarTablaMateriales();
        mostrarTablaHerramientas();
    }

    private void mostrarTablaMateriales() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(5f);
        tabla.setWidthPercentage(100);
        float[] anchos = {9f,4f,4f};
        tabla.setWidths(anchos);
        
        Paragraph paHeader = new Paragraph();
        paHeader.add(new Phrase("Materiales", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaHeader = new PdfPCell(paHeader);
        celdaHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaHeader.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        celdaHeader.setColspan(3);
        tabla.addCell(celdaHeader);
        
        Paragraph paNumero = new Paragraph();
        paNumero.add(new Phrase("Nombre", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaEmpleado = new PdfPCell(paNumero);
        celdaEmpleado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaEmpleado.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaEmpleado);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase("A Utilizar", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaDescripcion.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaDescripcion);

        Paragraph paUtilizadas = new Paragraph();
        paUtilizadas.add(new Phrase("Utilizados", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaUtilizadas = new PdfPCell(paUtilizadas);
        celdaUtilizadas.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaUtilizadas.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaUtilizadas);         
        
          List<PlanificacionXMaterial> lista = tarea.getMateriales();
          for (int i = 0; i < lista.size(); i++) {
            PlanificacionXMaterial mat = lista.get(i);
            
            Paragraph paC1 = new Paragraph();
            RecursoEspecifico recesp = RecursosUtil.getRecursoEspecifico(mat.getMaterial());
            
            if(recesp!=null){
                paC1.add(new Phrase(recesp.getNombre(), ReportDesigner.FUENTE_NORMAL_CHICA));
                PdfPCell celdaC1 = new PdfPCell(paC1);
                celdaC1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                tabla.addCell(celdaC1);

                Paragraph paC2 = new Paragraph();
                paC2.add(new Phrase(String.valueOf(mat.getCantidad()), ReportDesigner.FUENTE_NORMAL_CHICA));
                PdfPCell celdaC2 = new PdfPCell(paC2);
                celdaC2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tabla.addCell(celdaC2);

                Paragraph paC3 = new Paragraph();
                paC3.add(new Phrase(" ", ReportDesigner.FUENTE_NORMAL_CHICA));
                PdfPCell celdaC3 = new PdfPCell(paC3);
                celdaC3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tabla.addCell(celdaC3); 
            }

          }
        
        super.doc.add(tabla);
    }
    
    private void mostrarTablaHerramientas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(5f);
        tabla.setWidthPercentage(100);
        float[] anchos = {9f,4f,4f};
        tabla.setWidths(anchos);
        
        Paragraph paHeader = new Paragraph();
        paHeader.add(new Phrase("Herramientas", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaHeader = new PdfPCell(paHeader);
        celdaHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaHeader.setBackgroundColor(TABLE_HEADER_BASE_COLOR);
        celdaHeader.setColspan(3);
        tabla.addCell(celdaHeader);
        
        Paragraph paNumero = new Paragraph();
        paNumero.add(new Phrase("Nombre", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaEmpleado = new PdfPCell(paNumero);
        celdaEmpleado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaEmpleado.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaEmpleado);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase("Horas Estimadas", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaDescripcion.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaDescripcion);

        Paragraph paUtilizadas = new Paragraph();
        paUtilizadas.add(new Phrase("Utilizadas", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaUtilizadas = new PdfPCell(paUtilizadas);
        celdaUtilizadas.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaUtilizadas.setBackgroundColor(TABLE_SUBHEADER_BASE_COLOR);
        tabla.addCell(celdaUtilizadas);         
        
          List<PlanificacionXHerramienta> lista = tarea.getHerramientas();
          for (int i = 0; i < lista.size(); i++) {
            PlanificacionXHerramienta herr = lista.get(i);
            
            Paragraph paC1 = new Paragraph();
            paC1.add(new Phrase(herr.getHerramienta().getNombre(), ReportDesigner.FUENTE_NORMAL_CHICA));
            PdfPCell celdaC1 = new PdfPCell(paC1);
            celdaC1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            tabla.addCell(celdaC1);

            Paragraph paC2 = new Paragraph();
            paC2.add(new Phrase(String.valueOf(herr.getHorasAsignadas()), ReportDesigner.FUENTE_NORMAL_CHICA));
            PdfPCell celdaC2 = new PdfPCell(paC2);
            celdaC2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(celdaC2);

            Paragraph paC3 = new Paragraph();
            paC3.add(new Phrase(" ", ReportDesigner.FUENTE_NORMAL_CHICA));
            PdfPCell celdaC3 = new PdfPCell(paC3);
            celdaC3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(celdaC3);    
            
          }
        
        super.doc.add(tabla);
    }

    private void mostrarObservacionesGenerales() throws DocumentException {
        mostrarTitulo("OBSERVACIONES");
        
        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingBefore(5f);
        tabla.setWidthPercentage(100);
        
            for (int i = 0; i < 5; i++) {
                PdfPCell celdaC2 = new PdfPCell(new Phrase(" "));
                celdaC2.setBorderColorLeft(BaseColor.WHITE);
                celdaC2.setBorderColorRight(BaseColor.WHITE);
                celdaC2.setBorderColorTop(BaseColor.WHITE);
                tabla.addCell(celdaC2);
            }
        
        super.doc.add(tabla);
    }

    private void mostrarFirmas() throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(5f);
        tabla.setWidthPercentage(100);
        
                PdfPCell celdaC1 = new PdfPCell(new Phrase("Firma:\n\n\n ",FUENTE_NORMAL_B));
                tabla.addCell(celdaC1);
                PdfPCell celdaC2 = new PdfPCell(new Phrase("Aclaración:\n\n\n ",FUENTE_NORMAL_B));
                tabla.addCell(celdaC2);
                PdfPCell celdaC3 = new PdfPCell(new Phrase("Fecha:\n\n\n ",FUENTE_NORMAL_B));
                tabla.addCell(celdaC3);
        
        super.doc.add(tabla);
    }
}
