package modelo;

import java.util.ArrayList;
import java.util.List;

public class CotizacionModificada {
    private int id;
    private Cotizacion cotizacionOriginal;
    private List<SubObraModificada> subObra;

    public CotizacionModificada() {
        
        subObra = new ArrayList<SubObraModificada>();
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cotizacion getCotizacionOriginal() {
        return cotizacionOriginal;
    }

    public void setCotizacionOriginal(Cotizacion cotizacionOriginal) {
        this.cotizacionOriginal = cotizacionOriginal;
    }

    public List<SubObraModificada> getSubObra() {
        return subObra;
    }

    public void setSubObra(List<SubObraModificada> subObra) {
        this.subObra = subObra;
    }

    public void addSubObra(SubObraModificada subObraMod) {
        this.subObra.add(subObraMod);
    }
    
    public SubObraModificada buscarSubObra(int idSubObra)
    {
        for (int i = 0; i < subObra.size(); i++) {
            SubObraModificada som = subObra.get(i);
            if(idSubObra == som.getId())
            {
                return som;
            }
        }
        return null;
    }
}
