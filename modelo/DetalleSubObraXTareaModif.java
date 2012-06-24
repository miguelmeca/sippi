package modelo;

/**
 *
 * @author Emmanuel
 */
public class DetalleSubObraXTareaModif extends DetalleSubObraXTarea {
    private int id;
    private DetalleSubObraXTarea original;

    public DetalleSubObraXTareaModif() {
    }
    
    public DetalleSubObraXTareaModif(DetalleSubObraXTareaModif aCopiar) 
    {
        
        id=-1;
        super.setCantidadPersonas(aCopiar.getCantidadPersonas());
        super.setCantHorasNormales(aCopiar.getCantHorasNormales());
        super.setCantHorasAl50(aCopiar.getCantHorasAl50());
        super.setCantHorasAl100(aCopiar.getCantHorasAl100());
        super.setEspecialidad(aCopiar.getEspecialidad());
        this.original=aCopiar.original;
        
    }

    public DetalleSubObraXTarea getOriginal() {
        return original;
    }

    public void setOriginal(DetalleSubObraXTarea original) {
        this.original = original;
    }
}
