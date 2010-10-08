package config;

import java.util.HashMap;
import org.hibernate.Session;

/**
 * Maneja las propiedades del sistema
 * @author Iuga
 */
public class Propiedad {

   private HashMap<String, Object> propiedades;
   private PropiedadLoader loader;

   public Propiedad()
   {
      propiedades = new HashMap<String, Object>();
      loader = new PropiedadLoader();
      this.cargar();
   }

   /**
    * Carga Todas las configuraciones del Sistema
    */
   private void cargar()
   {
        loader.cargar(propiedades);
   }

    /**
     * Retorna un valor Booleano de la configuración.
     * @param key
     * @return
     */
    public Boolean getBoolean(final String key) 
    {
        return getBoolean(key, false);
    }

    /**
     * Retorna un valor booleano, sino se encuentra devuelve el def
     * @param key
     * @param def
     * @return
     */
    public Boolean getBoolean(final String key, final boolean def) 
    {
        try 
        {
            Object r = getPropiedad(key, def);
            if (!(r instanceof Boolean)) 
            {
                r = r + "";
                if (((String) r).equals("false")) 
                {
                    r = false;
                } 
                else 
                {
                    r = ((String) r).length() > 0;
                }
            }
            final Boolean ret = (Boolean) r;
            return ret;
        } 
        catch (final Exception e) 
        {
            return false;
        }
    }

    /**
     * Retorna un Double de la configuración
     * @param key
     * @return
     */
    public Double getDouble(final String key)
    {
        return getDouble(key, -1.0);
    }

    /**
     * Retorna un Double de la configuración sino, retorna def
     * @param key
     * @return
     */
    public Double getDouble(final String key, final Double def) {
        try {
            Object r = getPropiedad(key, def);
            if (r instanceof String)
            {
                r = Double.parseDouble((String) r);
            }
            final Double ret = (Double) r;
            return ret;
        }
        catch (final Exception e)
        {
            return def;
        }
    }

    /**
     * Retorna un Integer de la configuración, sino retorna def
     * @param key
     * @return
     */
    public int getInteger(final String key)
    {
        return getInteger(key, -1);
    }

    /**
     * Retorna un Integer de la configuración, sino retorna def
     * @param key
     * @return
     */
    public int getInteger(final String key, final int def)
    {
        try {
            Object r = getPropiedad(key, def);
            if (r instanceof String)
            {
                r = Integer.parseInt((String) r);
            }
            final Integer ret = (Integer) r;
            return ret;
        }
        catch (final Exception e)
        {
            return def;
        }
    }

    /**
     * Devuelve un String de la configuracion
     * @param key
     * @return
     */
    public String getString(final String key)
    {
        return getString(key, null);
    }

    /**
     * Devuelve un String de la configuracion, sino retorna def
     * @param key
     * @return
     */
    public String getString(final String key, final String def)
    {
        try
        {
            final Object r = getPropiedad(key, def);
            final String ret = (r == null) ? null : r.toString();
            return ret;
        }
        catch (final Exception e)
        {
            return def;
        }
    }

    /**
     * Devuelve el valos de una llave pero si no esta creado el
     * hashmap lo crea.
     * @param key
     * @return Value for key
     */
    public Object getPropiedad(final String key)
    {
        return getPropiedadNotNull().get(key);
    }

    /**
     * Cheuqea que no sea nulo el HashMap, si lo es, lo crea
     * @return
     */
    private HashMap<String, Object> getPropiedadNotNull()
    {
        if (propiedades == null)
        {
            propiedades = new HashMap<String, Object>();
        }
        return propiedades;
    }

    /**
     * Returns the value for key, and if none is set def
     *
     * @param key
     * @param def
     * @return value
     */
    public Object getPropiedad(final String key, final Object def) {
        if (getPropiedad(key) == null)
        {
            setPropiedad(key, def);
            return def;
        }
        return propiedades.get(key);
    }

    private void setPropiedad(String key, Object def)
    {

    }

}
