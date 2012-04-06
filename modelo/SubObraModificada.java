package modelo;

import java.util.List;

public class SubObraModificada {
    
    private int subObraModificadaId;
    private SubObra subObraOriginal; // Agregado por Iuga
    private List<SubObraXAlquilerCompraModif> alquileres;
    private List<SubObraXHerramientaModif> herramientas;
    private List<SubObraXTareaModif> tareas;
    private List<SubObraXAdicionalModif> adicionales;
    private List<SubObraXMaterialModif> materiales;
    
    // Falta la relacion a la subObra Original de presupuestacion

    public SubObraModificada() {
    }

    public int getSubObraModificadaId() {
        return subObraModificadaId;
    }

    public void setSubObraModificadaId(int subObraModificadaId) {
        this.subObraModificadaId = subObraModificadaId;
    }  
    
    public int getId() {
        return subObraModificadaId;
    }

    public void setId(int subObraModificadaId) {
        this.subObraModificadaId = subObraModificadaId;
    }      

    public List<SubObraXAdicionalModif> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<SubObraXAdicionalModif> adicionales) {
        this.adicionales = adicionales;
    }

    public List<SubObraXAlquilerCompraModif> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<SubObraXAlquilerCompraModif> alquileres) {
        this.alquileres = alquileres;
    }

    public List<SubObraXHerramientaModif> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<SubObraXHerramientaModif> herramientas) {
        this.herramientas = herramientas;
    }

    public List<SubObraXMaterialModif> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<SubObraXMaterialModif> materiales) {
        this.materiales = materiales;
    }

    public List<SubObraXTareaModif> getTareas() {
        return tareas;
    }

    public void setTareas(List<SubObraXTareaModif> tareas) {
        this.tareas = tareas;
    }

    public SubObra getSubObraOriginal() {
        return subObraOriginal;
    }

    public void setSubObraOriginal(SubObra subObraOriginal) {
        this.subObraOriginal = subObraOriginal;
    }
    
    public SubObra getSubObra() {
        return subObraOriginal;
    }

    public void setSubObra(SubObra subObraOriginal) {
        this.subObraOriginal = subObraOriginal;
    }
}
