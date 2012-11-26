/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cotizacion;
import modelo.CotizacionModificada;
import modelo.DetalleSubObraXTareaModif;
import modelo.DetalleTareaPlanificacion;
import modelo.Planificacion;
import modelo.SubObraXTareaModif;
import modelo.TareaPlanificacion;
import org.junit.*;
import static org.junit.Assert.*;
import test.DBExamplesLoader;
import util.HibernateUtil;

/**
 *
 * @author Emmanuel
 */
public class TestCopiaTareas {
    
    Cotizacion cot;
    Planificacion plan;
    CotizacionModificada cotMod;
    SubObraXTareaModif tareaCotizada1;
    DetalleSubObraXTareaModif detalle1_tareaCotizada1;
    DetalleSubObraXTareaModif detalle2_tareaCotizada1;
    
    SubObraXTareaModif copiaTareaCotizada1;
    DetalleSubObraXTareaModif copiaDetalle1_tareaCotizada1;
    DetalleSubObraXTareaModif copiaDetalle2_tareaCotizada1;
    
    
    
    TareaPlanificacion tareaPlan1;
    TareaPlanificacion tareaPlan2;
    DetalleTareaPlanificacion detalle1_tarea1;
    DetalleTareaPlanificacion detalle2_tarea1;
    
    TareaPlanificacion tareaS1;
    DetalleTareaPlanificacion detalle1_tareaS1;
    DetalleTareaPlanificacion detalle2_tareaS1;
    
    TareaPlanificacion tareaS1_S1;
    DetalleTareaPlanificacion detalle1_tareaS1_S1;
    DetalleTareaPlanificacion detalle2_tareaS1_S1;
    
    TareaPlanificacion tareaS1_S1_S1;
    DetalleTareaPlanificacion detalle1_tareaS1_S1_S1;
    DetalleTareaPlanificacion detalle2_tareaS1_S1_S1;
    
    
    
    TareaPlanificacion copiaTareaPlan1;
    DetalleTareaPlanificacion copiaDetalle1_tarea1;
    DetalleTareaPlanificacion copiaDetalle2_tarea1;
    TareaPlanificacion copiaTareaPlan2;
    TareaPlanificacion copiaTareaS1;
    DetalleTareaPlanificacion copiaDetalle1_tareaS1;
    DetalleTareaPlanificacion copiaDetalle2_tareaS1;
    
    TareaPlanificacion copiaTareaS1_S1;
    DetalleTareaPlanificacion copiaDetalle1_tareaS1_S1;
    DetalleTareaPlanificacion copiaDetalle2_tareaS1_S1;
    
    TareaPlanificacion copiaTareaS1_S1_S1;
    DetalleTareaPlanificacion copiaDetalle1_tareaS1_S1_S1;
    DetalleTareaPlanificacion copiaDetalle2_tareaS1_S1_S1;

    public TestCopiaTareas() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        DBExamplesLoader el = new DBExamplesLoader();
        el.cargarEjemplos();
        preprararEstructura();
    }

    @After
    public void tearDown() {
    }
    
    /*
     tarea1
     --tareaS1
     ----tareaS1_S1
     ------tareaS1_S1_S1
     --tareaS2
    
     tarea2
     
      
     */

    /**
     * Test of copiarCotizacionACotizacionModificada method, of class Trazabilidad.
     */
    @Test
    public void testCopiar() {
        copiaTareaPlan1=new TareaPlanificacion(tareaPlan1);
        
        copiaDetalle1_tarea1=detalle1_tarea1.getDetalleCopia();
        copiaDetalle2_tarea1=detalle2_tarea1.getDetalleCopia();
        
        copiaTareaS1=tareaS1.getTareaCopia();
                
        
        copiaDetalle1_tareaS1=detalle1_tareaS1.getDetalleCopia();
        copiaDetalle2_tareaS1=detalle2_tareaS1.getDetalleCopia();
        
        copiaTareaS1_S1=tareaS1_S1.getTareaCopia();
       
        
        copiaDetalle1_tareaS1_S1=detalle1_tareaS1_S1.getDetalleCopia();
        copiaDetalle2_tareaS1_S1=detalle2_tareaS1_S1.getDetalleCopia();
        
        
        copiaTareaS1_S1_S1=tareaS1_S1_S1.getTareaCopia();
        copiaDetalle1_tareaS1_S1_S1=detalle1_tareaS1_S1_S1.getDetalleCopia();
        copiaDetalle2_tareaS1_S1_S1=detalle2_tareaS1_S1_S1.getDetalleCopia();
        
        
        copiaTareaCotizada1=tareaCotizada1.getTareaCopia();
        copiaDetalle1_tareaCotizada1=detalle1_tareaCotizada1.getDetalleCopia();
        copiaDetalle2_tareaCotizada1=detalle2_tareaCotizada1.getDetalleCopia();
        
        
        //Relaciones tarea cotizada y detalles tarea cotizada
        assertEquals(copiaTareaPlan1.getTareaCotizada(),copiaTareaCotizada1);
        assertEquals(copiaDetalle1_tarea1.buscarCotizado(),copiaDetalle1_tareaCotizada1);
        assertEquals(copiaDetalle2_tarea1.buscarCotizado(),copiaDetalle2_tareaCotizada1);
        
        
        //Relaciones subtarea
        assertEquals(copiaTareaPlan1.getSubtareas().get(0),copiaTareaS1);
        assertEquals(copiaTareaS1.getSubtareas().get(0),copiaTareaS1_S1);
        assertEquals(copiaTareaS1_S1.getSubtareas().get(0),copiaTareaS1_S1_S1);
        
        //Relaciones tarea-detalle
        assertEquals(copiaTareaS1.getDetalleParticular(0),copiaDetalle1_tareaS1);
        assertEquals(copiaTareaS1.getDetalleParticular(1),copiaDetalle2_tareaS1);
        
        assertEquals(copiaTareaS1_S1.getDetalleParticular(0),copiaDetalle1_tareaS1_S1);
        assertEquals(copiaTareaS1_S1.getDetalleParticular(1),copiaDetalle2_tareaS1_S1);
        
        assertEquals(copiaTareaS1_S1_S1.getDetalleParticular(0),copiaDetalle1_tareaS1_S1_S1);
        assertEquals(copiaTareaS1_S1_S1.getDetalleParticular(1),copiaDetalle2_tareaS1_S1_S1);
        
         //Relaciones detalle padre
        
        assertEquals(copiaDetalle1_tareaS1.getPadre(),copiaDetalle1_tarea1);
        assertEquals(copiaDetalle2_tareaS1.getPadre(),copiaDetalle2_tarea1);
                        
        assertEquals(copiaDetalle1_tareaS1_S1.getPadre(),copiaDetalle1_tareaS1);
        assertEquals(copiaDetalle2_tareaS1_S1.getPadre(),copiaDetalle2_tareaS1);
        
        assertEquals(copiaDetalle1_tareaS1_S1_S1.getPadre(),copiaDetalle1_tareaS1_S1);
        assertEquals(copiaDetalle2_tareaS1_S1_S1.getPadre(),copiaDetalle2_tareaS1_S1);
        
        assertNotSame(copiaTareaPlan1, tareaPlan1);
        
        assertNotSame(copiaDetalle1_tarea1, detalle1_tarea1);
        assertNotSame(copiaDetalle2_tarea1, detalle2_tarea1);
        
        assertNotSame(copiaTareaS1, tareaS1);
                
        
        assertNotSame(copiaDetalle1_tareaS1, detalle1_tareaS1);
        assertNotSame(copiaDetalle2_tareaS1, detalle2_tareaS1);
        
        assertNotSame(copiaTareaS1_S1, tareaS1_S1);
       
        
        assertNotSame(copiaDetalle1_tareaS1_S1, detalle1_tareaS1_S1);
        assertNotSame(copiaDetalle2_tareaS1_S1, detalle2_tareaS1_S1);
        
        
        assertNotSame(copiaTareaS1_S1_S1, tareaS1_S1_S1);
        assertNotSame(copiaDetalle1_tareaS1_S1_S1, detalle1_tareaS1_S1_S1);
        assertNotSame(copiaDetalle2_tareaS1_S1_S1, detalle2_tareaS1_S1_S1);
        
        
        assertNotSame(copiaTareaCotizada1, tareaCotizada1);
        assertNotSame(detalle1_tareaCotizada1, copiaDetalle1_tareaCotizada1);
        assertNotSame(copiaDetalle2_tareaCotizada1, detalle2_tareaCotizada1);
     
        
        
    }
    
    private void preprararEstructura() {
        
        try {
            HibernateUtil.beginTransaction();
            cot = (Cotizacion) HibernateUtil.getSession().load(Cotizacion.class, 1);
            plan = (Planificacion) HibernateUtil.getSession().load(Planificacion.class, 1);
            cotMod=plan.getCotizacion();
            tareaCotizada1=(SubObraXTareaModif)cotMod.getSubObras().get(0).getTareas().get(0);
            detalle1_tareaCotizada1=(DetalleSubObraXTareaModif)tareaCotizada1.getDetalleParticular(0);
            detalle2_tareaCotizada1=(DetalleSubObraXTareaModif)tareaCotizada1.getDetalleParticular(1);
            tareaPlan1=new TareaPlanificacion(tareaCotizada1);
            tareaPlan2=new TareaPlanificacion((SubObraXTareaModif)(cotMod.getSubObras().get(0).getTareas().get(1)));
            detalle1_tarea1=tareaPlan1.getDetalleParticular(0);
            detalle2_tarea1=tareaPlan1.getDetalleParticular(1);
            plan.addTarea(tareaPlan1);
            plan.addTarea(tareaPlan2);
            tareaS1=new TareaPlanificacion();
            tareaS1.setNombre("tareaS1");
            tareaPlan1.addSubTarea(tareaS1);
            
                detalle1_tareaS1=new DetalleTareaPlanificacion();
                detalle1_tareaS1.setCantHorasNormales(8.0);
                detalle1_tareaS1.setEspecialidad(tareaPlan1.getDetalleParticular(0).getEspecialidad());
                detalle1_tareaS1.setearPadre(tareaPlan1.getDetalleParticular(0));           

                detalle2_tareaS1=new DetalleTareaPlanificacion();
                detalle2_tareaS1.setCantHorasNormales(2.0);
                detalle2_tareaS1.setCantHorasAl50(2.0);
                detalle2_tareaS1.setEspecialidad(tareaPlan1.getDetalleParticular(1).getEspecialidad());
                detalle2_tareaS1.setearPadre(tareaPlan1.getDetalleParticular(1));
                
                tareaS1.addDetalle(detalle1_tareaS1);
                tareaS1.addDetalle(detalle2_tareaS1);
            
            tareaS1_S1=new TareaPlanificacion();
            tareaS1_S1.setNombre("tareaS1_S1");
            tareaS1.addSubTarea(tareaS1_S1);
            
                detalle1_tareaS1_S1=new DetalleTareaPlanificacion();
                detalle1_tareaS1_S1.setCantHorasNormales(6.0);
                detalle1_tareaS1_S1.setEspecialidad(tareaS1.getDetalleParticular(0).getEspecialidad());
                detalle1_tareaS1_S1.setearPadre(tareaS1.getDetalleParticular(0));           

                detalle2_tareaS1_S1=new DetalleTareaPlanificacion();
                detalle2_tareaS1_S1.setCantHorasNormales(2.0);
                detalle2_tareaS1_S1.setCantHorasAl50(2.0);
                detalle2_tareaS1_S1.setEspecialidad(tareaS1.getDetalleParticular(1).getEspecialidad());
                detalle2_tareaS1_S1.setearPadre(tareaS1.getDetalleParticular(1));
                
                tareaS1_S1.addDetalle(detalle1_tareaS1_S1);
                tareaS1_S1.addDetalle(detalle2_tareaS1_S1);
            
            tareaS1_S1_S1=new TareaPlanificacion();
            tareaS1_S1_S1.setNombre("tareaS1_S1_S1");
            tareaS1_S1.addSubTarea(tareaS1_S1_S1);
            
            
                detalle1_tareaS1_S1_S1=new DetalleTareaPlanificacion();
                detalle1_tareaS1_S1_S1.setCantHorasNormales(4.0);
                detalle1_tareaS1_S1_S1.setEspecialidad(tareaS1_S1.getDetalleParticular(0).getEspecialidad());
                detalle1_tareaS1_S1_S1.setearPadre(tareaS1_S1.getDetalleParticular(0));           

                detalle2_tareaS1_S1_S1=new DetalleTareaPlanificacion();
                detalle2_tareaS1_S1_S1.setCantHorasNormales(2.0);
                detalle2_tareaS1_S1_S1.setCantHorasAl50(2.0);
                detalle2_tareaS1_S1_S1.setEspecialidad(tareaS1_S1.getDetalleParticular(1).getEspecialidad());
                detalle2_tareaS1_S1_S1.setearPadre(tareaS1_S1.getDetalleParticular(1));
                
                tareaS1_S1_S1.addDetalle(detalle1_tareaS1_S1_S1);
                tareaS1_S1_S1.addDetalle(detalle2_tareaS1_S1_S1);
                
            
            
            
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            System.out.println("No funciona");
            Logger.getLogger(TestCopiaTareas.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
        }
    }
    
    
   
}

