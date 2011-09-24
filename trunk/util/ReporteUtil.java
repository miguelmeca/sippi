/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Descripción: Clase para simplificar la emisión de Reportes
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class ReporteUtil {

    private Connection conn;
    private Session sesion;

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
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        sesion = sf.openSession();        
        
        
    }



    public void mostrarReporte(String urlReporte, Map parametros) throws Exception
    {
        sesion.beginTransaction();
        
        if(parametros==null)
        {
            parametros = new HashMap();
        }
        
        // Put Conection as PARAMETER
        parametros.put("HIBERNATE_SESSION",sesion);

           InputStream isrep = getClass().getResourceAsStream(urlReporte);
           
           JasperPrint jp = JasperFillManager.fillReport(isrep,parametros);
           
           sesion.getTransaction().commit();
           
           JasperViewer jv = new JasperViewer(jp,false);
           jv.setTitle("MetAr");
           jv.setVisible(true);

           
    }

}
