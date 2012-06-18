/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.util;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cotizacion;
import modelo.CotizacionModificada;
import modelo.SubObra;
import static org.junit.Assert.*;
import org.junit.*;
import test.DBExamplesLoader;
import util.HibernateUtil;
import util.Trazabilidad;

/**
 *
 * @author Emmanuel
 */
public class TrazabilidadTest {

    public TrazabilidadTest() {
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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of copiarCotizacionACotizacionModificada method, of class Trazabilidad.
     */
    @Test
    public void testCopiarCotizacionACotizacionModificada() {
        System.out.println("copiarCotizacionACotizacionModificada");
        Cotizacion cot = null;
        try {
            HibernateUtil.beginTransaction();
            cot = (Cotizacion) HibernateUtil.getSession().load(Cotizacion.class, 1);
            Trazabilidad instance = new Trazabilidad();
            CotizacionModificada expResult = null;
            CotizacionModificada cotMod = instance.copiarCotizacionACotizacionModificada(cot);
            if(cotMod != null)
            {
                assertEquals(cot, cotMod.getCotizacionOriginal());
                List<SubObra> subObras = cot.getSubObras();
                List<SubObra> subObraModifs = cotMod.getSubObras();
                assertEquals(subObras.size(),subObraModifs.size());
            }
            else
            {
                fail("No se creó el objeto");
            }
            CotizacionModificada cm = (CotizacionModificada) HibernateUtil.getSession().load(CotizacionModificada.class, cotMod.getId());
            assertEquals(cotMod, cm);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            System.out.println("No funciona");
            Logger.getLogger(TrazabilidadTest.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTransaction();
        }
    }
}