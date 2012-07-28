package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import util.RubroUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public abstract class Recurso {
    
    private int id;
    private String nombre;
    private List<RecursoEspecifico> recursos;
    private UnidadDeMedida unidadDeMedida;
    private String estado;
    
    // Este Recurso puede tener Stock
    private boolean esStockeable = false;
    // Este recurso puede agregarse a una orden de compra
    private boolean esComprable  = false;
    
    public static final String ESTADO_ALTA = "Alta";
    public static final String ESTADO_BAJA = "Baja";

    public Recurso() {
        recursos = new ArrayList<RecursoEspecifico>();
        estado = Recurso.ESTADO_ALTA;
    }

    public boolean esRubro(int idRubro)
    {
         Rubro r = RubroUtil.getRubro(idRubro);
         if(r.getNombreClase().equals(this.toString()))
         {
             return true;
         }
         return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getRecursos() {
        return recursos;
    }

    public List getRecursosEspecificos() {
        return recursos;
    }

    public void setRecursos(List recursos) {
        this.recursos = recursos;
    }

    public UnidadDeMedida getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }

    public boolean isEsComprable() {
        return esComprable;
    }

    public void setEsComprable(boolean esComprable) {
        this.esComprable = esComprable;
    }

    public boolean isEsStockeable() {
        return esStockeable;
    }

    public void setEsStockeable(boolean esStockeable) {
        this.esStockeable = esStockeable;
    }    
    
    public String mostrarUnidadDeMedida() {
        if(unidadDeMedida!=null)
        {
            return unidadDeMedida.getNombre()+" ["+unidadDeMedida.getAbreviatura()+"]";
        }
        return "";
        
    }

    public String getEstado() {
        if(this.estado==null)
        {
            return "";
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean yaTieneRecursoEspecifico(int id)
    {
        for (int i = 0; i < recursos.size(); i++) {
            RecursoEspecifico re = recursos.get(i);
            if(re.getId()==id)
            {
                return true;
            }
        }
        return false;
    }
    
}
