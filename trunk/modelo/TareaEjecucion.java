package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iuga
 */
public class TareaEjecucion {
    
    private int id;
    private List<TareaEjecucion> subtareas;
    private TareaPlanificacion tareaPlanificada;
    private List<EjecucionXHerramienta> listaHerramientas;
    private List<EjecucionXMaterial> listaMateriales;
    private List<EjecucionXAlquilerCompra> listaAlquileresCompras;

    public TareaEjecucion() {
        subtareas = new ArrayList<TareaEjecucion>();
        listaHerramientas = new ArrayList<EjecucionXHerramienta>();
        listaAlquileresCompras = new ArrayList<EjecucionXAlquilerCompra>();
        listaMateriales = new ArrayList<EjecucionXMaterial>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public TareaPlanificacion getTareaPlanificada() {
        return tareaPlanificada;
    }

    public void setTareaPlanificada(TareaPlanificacion tareaPlanificada) {
        this.tareaPlanificada = tareaPlanificada;
    }

    public List<EjecucionXHerramienta> getListaHerramientas() {
        return listaHerramientas;
    }

    public void setListaHerramientas(List<EjecucionXHerramienta> listaHerramientas) {
        this.listaHerramientas = listaHerramientas;
    }

    public List<TareaEjecucion> getSubtareas() {
        return subtareas;
    }

    public void setSubtareas(List<TareaEjecucion> subtareas) {
        this.subtareas = subtareas;
    }

    @Override
    public String toString() {
        if(tareaPlanificada.getNombre()!=null){
            return tareaPlanificada.getNombre();
        }
        return "";
    }

    public List<EjecucionXMaterial> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<EjecucionXMaterial> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public List<EjecucionXAlquilerCompra> getListaAlquileresCompras() {
        return listaAlquileresCompras;
    }

    public void setListaAlquileresCompras(List<EjecucionXAlquilerCompra> listaAlquileresCompras) {
        this.listaAlquileresCompras = listaAlquileresCompras;
    }

}
