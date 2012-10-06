package controlador.ejecucion.lanzamiento;

import controlador.Compras.StockUtils;
import controlador.ejecucion.EjecucionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.Ejecucion;
import modelo.EjecucionXHerramienta;
import modelo.EjecucionXMaterial;
import modelo.HerramientaDeEmpresa;
import modelo.PedidoObra;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.TareaEjecucion;
import org.hibernate.HibernateException;
import util.HibernateUtil;
import util.NTupla;
import util.RecursosUtil;

/**
 * Este gestor no conoce a su venatana, ya que pueden ser varias: Todas los
 * paneles de lanzamiento usan este gestor... Para simplificar un poco el
 * diseño..
 *
 * @author Iuga
 */
public class GestorVentanaLanzamiento {

    private PedidoObra pedidoDeObra;

    public GestorVentanaLanzamiento(int idObra) {
        cargarObra(idObra);
    }

    /**
     * Panel Herramientas. (Sobre la Ejecución). Llena la tabla con las
     * herramientas necesarias: 1- Busco las herramientas que me hacen falta
     * para la obra y las horas. 2- Retorno ?
     *
     */
    public List<NTupla> llenarTablaPanelHerramientas() {
        //1- Busco las herramientas que me hacen falta para la obra. 
        Map<HerramientaDeEmpresa, Integer> allHerramientas = new HashMap<HerramientaDeEmpresa, Integer>(); // <Id Herramienta,CantidadHoras>
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                List<TareaEjecucion> todasTareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = todasTareas.get(i);
                    List<EjecucionXHerramienta> listaHerramientas = tarea.getListaHerramientas();
                    if (listaHerramientas != null) {
                        for (int j = 0; j < listaHerramientas.size(); j++) {
                            EjecucionXHerramienta herramienta = listaHerramientas.get(j);
                            HerramientaDeEmpresa hde = herramienta.getHerramientaPlanificada().getHerramientaCotizacion().getHerramienta();
                            if (hde != null) {
                                if (allHerramientas.containsKey(hde)) {
                                    // Sumo las horas solamente
                                    int horas = allHerramientas.get(hde);
                                    horas += herramienta.getHerramientaPlanificada().getHorasAsignadas();
                                    allHerramientas.put(hde, horas);
                                } else {
                                    // Agrego la herramietna y seteo las horas
                                    allHerramientas.put(hde, herramienta.getHerramientaPlanificada().getHorasAsignadas());
                                }
                            }
                        }
                    }
                }
            }
        }
        // 2- Retorno 
        List<NTupla> ntlst = new ArrayList<NTupla>();
        Iterator it = allHerramientas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            //System.out.println(e.getKey() + " " + e.getValue());
            HerramientaDeEmpresa hde = (HerramientaDeEmpresa) e.getKey();
            NTupla nt = new NTupla(hde.getId());
            nt.setNombre(hde.getNombre());
                String[] data = new String[2];
                data[0] = String.valueOf(e.getValue());
                data[1] = hde.getEstadoColoreado();
            nt.setData(data);
            ntlst.add(nt);
        }
        return ntlst;
    }

    /**
     * Carga de DB la Obra ...
     */
    private void cargarObra(int idObra) {

        try {
            HibernateUtil.beginTransaction();
            this.pedidoDeObra = (PedidoObra) HibernateUtil.getSession().load(PedidoObra.class, idObra);
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error: Se produjo un error al cargar la Obra:\n" + e.getMessage());
            throw new HibernateException("Error: Se produjo un error al cargar la Obra");
        }

    }

    /**
     * Panel Materiales.
     * Llena la tabla 
     * @return 
     */
    public List<NTupla> llenarTablaPanelMateriales() {
        //1- Busco los materiales que me hacen falta para la obra. 
        Map<RecursoEspecifico, Integer> allMateriales = new HashMap<RecursoEspecifico, Integer>(); // <Id Herramienta,CantidadHoras>
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                List<TareaEjecucion> todasTareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = todasTareas.get(i);
                    List<EjecucionXMaterial> listaMateriales = tarea.getListaMateriales();
                    if (listaMateriales != null) {
                        for (int j = 0; j < listaMateriales.size(); j++) {
                            EjecucionXMaterial material = listaMateriales.get(j);
                            RecursoXProveedor rxp = material.getMaterialPlanificado().getMaterialCotizacion().getMaterial();
                            RecursoEspecifico recesp = RecursosUtil.getRecursoEspecifico(rxp);
                            if (recesp != null) {
                                if (allMateriales.containsKey(recesp)) {
                                    // Sumo las horas solamente
                                    int cantidad = allMateriales.get(recesp);
                                    cantidad += material.getMaterialPlanificado().getCantidad();
                                    allMateriales.put(recesp, cantidad);
                                } else {
                                    // Agrego la herramietna y seteo las horas
                                    allMateriales.put(recesp, material.getMaterialPlanificado().getCantidad());
                                }
                            }
                        }
                    }
                }
            }
        }
                // 2- Retorno 
        List<NTupla> ntlst = new ArrayList<NTupla>();
        Iterator it = allMateriales.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            //System.out.println(e.getKey() + " " + e.getValue());
            RecursoEspecifico rec = (RecursoEspecifico) e.getKey();
            NTupla nt = new NTupla(rec.getId());
            nt.setNombre(rec.getNombre());
            
                int necesarios = (Integer) e.getValue();
                double enstock = calcularStockDeRecursoespecifico(rec);
            
                String[] data = new String[3];
                data[0] = String.valueOf(necesarios)+" "+rec.getRecurso().mostrarUnidadDeMedida();
                data[1] = String.valueOf(enstock)+" "+rec.getRecurso().mostrarUnidadDeMedida();
                
                if(necesarios<enstock){
                    data[2] = "<HTML><span color='#009900'>Todo Disponible</span>";
                }else{
                    data[2] = "<HTML><span color='#FF0000'>Faltante de "+(necesarios-enstock)+"</span>";
                }
                
            nt.setData(data);
            ntlst.add(nt);
        }
        return ntlst;
    }

    /**
     * Veo cuanto Stock tengo de un material
     * @param rec
     * @return 
     */
    private double calcularStockDeRecursoespecifico(RecursoEspecifico rec) {
        StockUtils su = new StockUtils();
        return su.calcularStockDeRecursoespecifico(RecursoEspecifico.class,rec.getId());
    }
}
