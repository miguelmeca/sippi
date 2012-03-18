/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import modelo.HerramientaDeEmpresa;

/**
 * Clase encargada de Crear los ABM genericos
 * @author Administrador
 */
public class FactoryABM {
    
    private Class[] intArgsClass = new Class[] { Class.class, int.class };
    private static HashMap<Class, String> storage = new HashMap<Class, String>();
    private static HashMap<Class, String> storageHbm = new HashMap<Class, String>();
    
    static 
    {
        FactoryABM.storage.put(HerramientaDeEmpresa.class,"test.TestABM");
        FactoryABM.storageHbm.put(HerramientaDeEmpresa.class,"HerramientaDeEmpresa.xml");
    }
    
    public PantallaABMGenerica create(Class clase, int comportamiento)
    {
        Object[] params = new Object[2];
        params[0] = clase;
        params[1] = comportamiento;
        
        String claseAgenerar = "";
        if(FactoryABM.storage.containsKey(clase))
        {
            claseAgenerar = FactoryABM.storage.get(clase);
        }
        if(claseAgenerar.isEmpty())
        {
            return null;
        }
        
        PantallaABMGenerica pantalla = null;
        //Class instancia;
        Constructor instancia=null;
        try
        {
            instancia = Class.forName( claseAgenerar ).getConstructor(intArgsClass);
            pantalla = (PantallaABMGenerica) createObject(instancia, params);
            System.out.println("[DEBUG] Se Reflexionó una clase: "+claseAgenerar);
        }
        catch(Exception e)
        {
            System.err.println("[ERROR] No se pudo generar el ABM, no se encontro la clase buscada");
        }
        return pantalla;
    }
    
    public boolean hasABM(Class clase)
    {
        return storage.containsKey(clase);
    }
    
    public static String getHibernateHbm(Class clase)
    {
        String hbm = storageHbm.get(clase);
        if(hbm==null  || hbm.isEmpty())
        {
            return "";
        }
        return hbm;
    }
    
  public static Object createObject(Constructor constructor,Object[] arguments) 
  {
        System.out.println("[DEBUG] Constructor: " + constructor.toString());
        Object object = null;
        try {
        object = constructor.newInstance(arguments);
        System.out.println("[DEBUG] Objeto Generado: " + object.toString());
        return object;
        } catch (InstantiationException e) {
        System.err.println("[ERROR] "+e);
        } catch (IllegalAccessException e) {
        System.err.println("[ERROR] "+e);
        } catch (IllegalArgumentException e) {
        System.err.println("[ERROR] "+e);
        } catch (InvocationTargetException e) {
        System.err.println("[ERROR] "+e);
        }
        return object;
  }    
    
}
