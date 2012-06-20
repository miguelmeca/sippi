/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 * Descripción: Carga el HashMap de las propiedades
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class PropiedadLoader {

    private Session sesion;

    public PropiedadLoader() 
    {
        try
        {
            sesion = HibernateUtil.getSession();
        } catch (Exception ex) {
            
        }
    }

    /**
     * Cargo todas las configuraciones del Sistema
     * @param propiedades
     */
    public void cargar(HashMap<String, Object> propiedades)
    {
        Iterator<PropiedadBean> it = sesion.createQuery("from PropiedadBean pb").iterate();
        while (it.hasNext())
        {
            PropiedadBean prop = it.next();
            propiedades.put(prop.getKey(), prop.getValue());
        }
    }


}
