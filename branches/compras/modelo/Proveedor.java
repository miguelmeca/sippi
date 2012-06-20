package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.RubroUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Proveedor extends Empresa {

    private List<Rubro> rubros;
    private List<RecursoEspecifico> listaArticulos;
    private double confiabilidad;

    public Proveedor()
    {
        rubros=new ArrayList<Rubro>();;
        listaArticulos  = new ArrayList<RecursoEspecifico>();
    }

    public List<Rubro> getRubros() {
        return rubros;
    }

    public void setRubros(List<Rubro> rubros) {
        this.rubros = rubros;
    }

    public List<RecursoEspecifico> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<RecursoEspecifico> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public void setConfiabilidad(double c) {
        this.confiabilidad=c;
    }
    public double getConfiabilidad() {
        return this.confiabilidad;
    }

    /**
     * Ve si el proveedor vende articulos de este rubro
     * El objeto Rubro debe tener cargado el ID
     * Chequea solo en su lista de rubros, no en los RecursosEspecificos
     * @param r
     * @return
     */
    public boolean tieneRubro(Rubro r)
    {
        Iterator<Rubro> itr = rubros.iterator();
        while (itr.hasNext())
        {
            Rubro rubro = itr.next();
            if(rubro.getId()==r.getId())
            {
                return true;
            }
        }
        return false;
    }
    
    
    public String mostrarRubros()
    {
        if(this.rubros!=null && !this.rubros.isEmpty())
        {
            String buffer = "";
            for (int i = 0; i < rubros.size(); i++) {
                Rubro rubro = rubros.get(i);
                if(rubro!=null)
                {
                    buffer += rubro.getNombre()+" ";
                }
            }
            return buffer;
        }
        return null;
    }

   
}
