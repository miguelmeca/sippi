package modelo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Iuga Modified for I4S1
 * @author Iuga
 */
public class HerramientaDeEmpresa {
    
    private int id;
    private String nroSerie;
    private RecursoEspecifico recursoEsp;
    private String estado;
    private OrdenDeCompra ordenDeCompra;
    
    public static final String ESTADO_DISPONIBLE = "Disponible";
    public static final String ESTADO_EN_REPARACION = "En Reparación";
    public static final String ESTADO_NECESITA_REPARACION = "Necesita Reparación";
    public static final String ESTADO_DE_BAJA = "De Baja";
    public static final String ESTADO_NO_DISPONIBLE = "No Disponible";

    public HerramientaDeEmpresa() {
       estado = "Disponible";
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public RecursoEspecifico getRecursoEsp() {
        return recursoEsp;
    }

    public void setRecursoEsp(RecursoEspecifico recursoEsp) {
        this.recursoEsp = recursoEsp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNombre()
    {
        if(this.recursoEsp!=null && this.recursoEsp.getRecurso()!=null)
        {
            return this.recursoEsp.getRecurso().getNombre()+" "+this.recursoEsp.getNombre();
        }
        return "";
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }
    
    public static List<String> getEstados()
    {
        Class c;
        try {
            c = Class.forName("modelo.HerramientaDeEmpresa");
            List<String> estados = new ArrayList<String>();
            for(int i=0; i< c.getFields().length; i++)
            {
                Field field = c.getFields()[i];
                if(field.getName().contains("ESTADO_"))
                {
                    estados.add((String) field.get(null));
                }
            }
            return estados;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Herramienta.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<String>();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger(Herramienta.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<String>();
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(Herramienta.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<String>();
        }
    }
}
