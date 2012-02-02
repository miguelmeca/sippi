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

    public DetalleSubObraXTarea getOriginal() {
        return original;
    }

    public void setOriginal(DetalleSubObraXTarea original) {
        this.original = original;
    }
}
