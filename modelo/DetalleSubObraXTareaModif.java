package modelo;

/**
 *
 * @author Emmanuel
 */
public class DetalleSubObraXTareaModif extends DetalleSubObraXTarea {
    private int id;
    private DetalleSubObraXTarea original;
    
    private transient DetalleSubObraXTareaModif detalleCopia;

    public DetalleSubObraXTareaModif() {
    }
    
    public DetalleSubObraXTareaModif(DetalleSubObraXTareaModif aCopiar) 
    {
        aCopiar.detalleCopia=this;
        id=-1;
        super.setCantidadPersonas(aCopiar.getCantidadPersonas());
        super.setCantHorasNormales(aCopiar.getCantHorasNormales());
        super.setCantHorasAl50(aCopiar.getCantHorasAl50());
        super.setCantHorasAl100(aCopiar.getCantHorasAl100());
        super.setEspecialidad(aCopiar.getEspecialidad());
        super.setCostoXHoraNormal(aCopiar.getCostoXHoraNormal());
        this.original=aCopiar.original;
        
    }

    public DetalleSubObraXTarea getOriginal() {
        return original;
    }

    public void setOriginal(DetalleSubObraXTarea original) {
        this.original = original;
    }

    /**
     * @return the detalleCopia
     */
    public DetalleSubObraXTareaModif getDetalleCopia() {
        return detalleCopia;
    }
    
    public void setDetalleCopia(DetalleSubObraXTareaModif e) {
        detalleCopia=e;
    }
}
