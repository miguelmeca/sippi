/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 * Descripción: Clase para simplificar la emisión de Reportes
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class ReporteUtil {

    private Connection conn;

    public ReporteUtil()
    {
       String cadena = HibernateUtil.getCadenaConexion();
       cadena = cadena.replaceAll("//","");
       cadena = cadena.replaceAll("/",":");
       
        try
        {
            conn = DriverManager.getConnection(cadena, HibernateUtil.getUsuarioConexion(), HibernateUtil.getPasswordConexion());
        }
        catch (SQLException ex)
        {
            System.out.println("No se pudo conectar con la DB para hacer el reporte");
        }
    }



    public void mostrarReporte(String urlReporte, Map parametros)
    {

        if(parametros==null)
        {
            parametros = new HashMap();
        }

        InputStream logo = getClass().getResourceAsStream("/vista/reportes/logoMetar.png");
        parametros.put("imagenLogo",logo);

        try
        {
           URL url = this.getClass().getResource(urlReporte);
           String jrxml = url.getPath();

           JasperReport jr = JasperCompileManager.compileReport(jrxml);

           JasperPrint jp = JasperFillManager.fillReport(jr,parametros,conn);

           JasperViewer jv = new JasperViewer(jp,false);
           jv.setTitle("MetAr");
           jv.setVisible(true);

        }catch(Exception e){
           e.printStackTrace();
        }
    }

}
