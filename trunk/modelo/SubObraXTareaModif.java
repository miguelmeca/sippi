package modelo;

import java.util.ArrayList;
import java.util.List;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos
//  @ File Name : SubObraXTareaModif.java
//  @ Date : 14/01/2012
//  @ Author : 
//
//

public class SubObraXTareaModif extends SubObraXTarea {
    private int id;
	private List<DetalleSubObraXTareaModif> detallesMod;
	private SubObraXTarea original;

    public SubObraXTareaModif() {
    }

    public SubObraXTareaModif(SubObraXTareaModif aCopiar) 
    {
        id=-1;
        super.setNombre(aCopiar.getNombre());
        super.setObservaciones(aCopiar.getObservaciones());
        super.setTipoTarea(aCopiar.getTipoTarea());
        List<DetalleSubObraXTareaModif> detallesAux=aCopiar.getDetallesMod();
        
        detallesMod=new ArrayList<DetalleSubObraXTareaModif>();
        for (DetalleSubObraXTareaModif detalleMod:detallesAux) 
        {
           DetalleSubObraXTareaModif nuevoDetalle=new DetalleSubObraXTareaModif(detalleMod);
           detallesMod.add(nuevoDetalle);
        }
        this.original=aCopiar.original;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
   
    public SubObraXTarea getOriginal() {
        return original;
    }

    public void setOriginal(SubObraXTarea original) {
        this.original = original;
    }
    
    @Deprecated
    @Override
    public List<DetalleSubObraXTarea> getDetalles() {
        List<DetalleSubObraXTarea> aux = new ArrayList<DetalleSubObraXTarea>();
        try {
            for (int i = 0; i < detallesMod.size(); i++) {
                aux.add(detallesMod.get(i));
            }
        } catch (Exception e) {
            System.err.println("Se produjo un error grave en SubObraXTareaModif:getDetalles()");
        }
        return aux;
    }
    
    public List<DetalleSubObraXTareaModif> getDetallesMod() {
        return detallesMod;
    }

    @Deprecated
    @Override
    public void setDetalles(List<DetalleSubObraXTarea> detalles) {
        try {
            List<DetalleSubObraXTareaModif> aux = new ArrayList<DetalleSubObraXTareaModif>();
            for (int i = 0; i < detalles.size(); i++) {
                aux.add((DetalleSubObraXTareaModif) detalles.get(i));
            }
            this.detallesMod = aux;
        } catch (Exception e) {
            System.err.println("Se produjo un error grave en SubObraXTareaModif:setDetalles()");
        }
    }
    
    public void setDetallesMod(List<DetalleSubObraXTareaModif> detalles) {        
        this.detallesMod = detalles;
    }
    
   
    @Override
    public void agreagarDetalle(DetalleSubObraXTarea detalle) {
        this.detallesMod.add((DetalleSubObraXTareaModif)detalle);
    }
    
    @Override
    public void agreagarDetalle(DetalleSubObraXTarea detalle, int indice) {
        this.detallesMod.add(indice, (DetalleSubObraXTareaModif)detalle);
    }
    
    @Override
    public DetalleSubObraXTarea getDetalleParticular(int i) {
        return detallesMod.get(i);
    }
    
   /* @Override
    public void setDetalles(List<DetalleSubObraXTarea> detalles) {
        for (int i = 0; i < detalles.size(); i++) {
            if(!DetalleSubObraXTareaModif.class.isInstance(detalles.get(i)))
            {
               ClassCastException cce=new ClassCastException("ERROR: Los detalles de SubObraTareaModif deben ser instancias de DetalleSubObraTareaModif"); 
            }
        }
        super.setDetalles(detalles);
    }
    
    @Override
    public void agreagarDetalle(DetalleSubObraXTarea detalle) {
        if(!DetalleSubObraXTareaModif.class.isInstance(detalle))
            {
               ClassCastException cce=new ClassCastException("ERROR: Los detalles de SubObraTareaModif deben ser instancias de DetalleSubObraTareaModif"); 
            }
        super.agreagarDetalle((DetalleSubObraXTareaModif)detalle);
    }
    
    @Override
    public DetalleSubObraXTarea getDetalleParticular(int i) {
        return (DetalleSubObraXTarea)super.getDetalleParticular(i);
    }*/
    
     public double obtenerTotalDeHorasNormales()
    {
        double totalHorasNormales=0;
        for (DetalleSubObraXTareaModif detalle: detallesMod) 
        {
            totalHorasNormales+=(detalle.getCantHorasNormales()*detalle.getCantidadPersonas());
        }
        return totalHorasNormales;
    }
    public double obtenerTotalDeHorasAl50()
    {
        double totalHoras50=0;
        for (DetalleSubObraXTareaModif detalle: detallesMod) 
        {
            totalHoras50+=(detalle.getCantHorasAl50()*detalle.getCantidadPersonas());
        }
        return totalHoras50;
    }
    public double obtenerTotalDeHorasAl100()
    {
        double totalHoras100=0;
        for (DetalleSubObraXTareaModif detalle: detallesMod) 
        {
            totalHoras100+=(detalle.getCantHorasAl100()*detalle.getCantidadPersonas());
        }
        return totalHoras100;
    }
    
    @Override
    public double calcularSubtotal() 
    {        
        double subT=0.0;
        for (int i = 0; i < detallesMod.size(); i++) 
        {
            subT+=detallesMod.get(i).calcularSubtotal();
            
        }               
       return subT; 
    }
    
}
