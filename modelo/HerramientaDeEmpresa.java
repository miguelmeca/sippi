package modelo;


/**
 * Iuga Modified for I4S1
 * @author Iuga
 */
public class HerramientaDeEmpresa {
    
    private int id;
    private String nroSerie;
    private RecursoEspecifico recursoEsp;
    private String estado;

    public HerramientaDeEmpresa() {
       estado = "Alta";
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

    
}
