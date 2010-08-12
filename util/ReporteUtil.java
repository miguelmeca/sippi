/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class ReporteUtil {

    public void mostrarReporte()
    {

        try{

            String cadena = HibernateUtil.getCadenaConexion();
            cadena = cadena.replaceAll("//","");
            cadena = cadena.replaceAll("/",":");
            Connection conn = DriverManager.getConnection(cadena,HibernateUtil.getUsuarioConexion(), HibernateUtil.getPasswordConexion());

           Map parameters = new HashMap();
           parameters.put("TITULO", "PAISES");
           parameters.put("FECHA", new java.util.Date());

           URL url = this.getClass().getResource("/vista/reportes/ListaAsistencia.jrxml");
           String jrxml = url.getPath();

           JasperReport jr = JasperCompileManager.compileReport(jrxml);

           JasperPrint jp = JasperFillManager.fillReport(jr,parameters,conn);

           JasperViewer jv = new JasperViewer(jp,false);
           jv.setVisible(true);

        }catch(Exception e){
           e.printStackTrace();
        }
    }

}
