package controlador.ejecucion.lanzamiento;

import controlador.Compras.StockUtils;
import controlador.ejecucion.EjecucionUtils;
import controlador.ejecucion.GestorGenerarOrdenesDeTrabajo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modelo.DetalleTareaEjecucion;
import modelo.DetalleTareaPlanificacion;
import modelo.Ejecucion;
import modelo.EjecucionXAdicional;
import modelo.EjecucionXAlquilerCompra;
import modelo.EjecucionXHerramienta;
import modelo.EjecucionXMaterial;
import modelo.Empleado;
import modelo.HerramientaDeEmpresa;
import modelo.PedidoObra;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.SubObraXAlquilerCompraModif;
import modelo.TareaEjecucion;
import modelo.TipoAlquilerCompra;
import org.hibernate.HibernateException;
import util.FechaUtil;
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
    private List<NTupla> ntlst;
    Map<Empleado, double[]> allEmpleados;

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
                    List<PlanificacionXHerramienta> listaHerramientas = tarea.getHerramientas();
                    if (listaHerramientas != null) {
                        for (int j = 0; j < listaHerramientas.size(); j++) {
                            if(listaHerramientas.get(j) instanceof EjecucionXHerramienta){
                                EjecucionXHerramienta herramienta = (EjecucionXHerramienta)listaHerramientas.get(j);
                                HerramientaDeEmpresa hde = herramienta.getHerramienta();
                                if (hde != null) {
                                    if (allHerramientas.containsKey(hde)) {
                                        // Sumo las horas solamente
                                        int horas = allHerramientas.get(hde);
                                        if(herramienta.getHerramientaPlanificada()!=null){
                                            horas += herramienta.getHerramientaPlanificada().getHorasAsignadas();
                                        }
                                        allHerramientas.put(hde, horas);
                                    } else {
                                        // Agrego la herramietna y seteo las horas
                                        if(herramienta.getHerramientaPlanificada()!=null){
                                            allHerramientas.put(hde, herramienta.getHerramientaPlanificada().getHorasAsignadas());
                                        }
                                    }
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
            NTupla nt = new NTupla(hde.hashCode());
            nt.setId(hde.getId());
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
     * Panel Materiales. Llena la tabla
     *
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
                    List<PlanificacionXMaterial> listaMateriales = tarea.getMateriales();
                    if (listaMateriales != null) {
                        for (int j = 0; j < listaMateriales.size(); j++) {
                            EjecucionXMaterial material = (EjecucionXMaterial)listaMateriales.get(j);
                            RecursoXProveedor rxp = material.getMaterial();
                            RecursoEspecifico recesp = RecursosUtil.getRecursoEspecifico(rxp);
                            if (recesp != null) {
                                if (allMateriales.containsKey(recesp)) {
                                    // Sumo las horas solamente
                                    int cantidad = allMateriales.get(recesp);
                                    if(material.getMaterialPlanificado()!=null){
                                        cantidad += material.getMaterialPlanificado().getCantidad();
                                    }
                                    allMateriales.put(recesp, cantidad);
                                } else {
                                    // Agrego la herramietna y seteo las horas
                                    if(material.getMaterialPlanificado()!=null){
                                        allMateriales.put(recesp, material.getMaterialPlanificado().getCantidad());
                                    }
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
            NTupla nt = new NTupla(rec.hashCode());
            nt.setNombre(rec.getNombre());

            int necesarios = (Integer) e.getValue();
            double enstock = calcularStockDeRecursoespecifico(rec);

            String[] data = new String[3];
            data[0] = String.valueOf(necesarios) + " " + rec.getRecurso().mostrarUnidadDeMedida();
            data[1] = String.valueOf(enstock) + " " + rec.getRecurso().mostrarUnidadDeMedida();

            if (necesarios <= enstock) {
                data[2] = "<HTML><span color='#009900'>Todo Disponible</span>";
            } else {
                data[2] = "<HTML><span color='#FF0000'>Faltante de " + (necesarios - enstock) + "</span>";
            }

            nt.setData(data);
            ntlst.add(nt);
        }
        return ntlst;
    }

    /**
     * Veo cuanto Stock tengo de un material
     *
     * @param rec
     * @return
     */
    private double calcularStockDeRecursoespecifico(RecursoEspecifico rec) {
        StockUtils su = new StockUtils();
        return su.calcularStockDeRecursoespecifico(RecursoEspecifico.class, rec.getId());
    }

    /**
     * Panel Alquileres/Compras. Retorna los datos para llenar la tabla.
     *
     * @return
     */
    public List<NTupla> llenarTablaPanelAlquileresCompras() {
        //1- Busco los Alq/Comp que me hacen falta para la obra. 
        Map<SubObraXAlquilerCompraModif, Integer> allAlqCompras = new HashMap<SubObraXAlquilerCompraModif, Integer>(); // <Id Herramienta,CantidadHoras>
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                List<TareaEjecucion> todasTareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = todasTareas.get(i);
                    List<PlanificacionXAlquilerCompra> listaAlqCompra = tarea.getAlquilerCompras();
                    if (listaAlqCompra != null) {
                        for (int j = 0; j < listaAlqCompra.size(); j++) {
                            EjecucionXAlquilerCompra alqCompra = (EjecucionXAlquilerCompra)listaAlqCompra.get(j);
                            SubObraXAlquilerCompraModif tac = alqCompra.getAlquilerCompraCotizacion();
                            if (tac != null) {
                                if (allAlqCompras.containsKey(tac)) {
                                    // Sumo las horas solamente
                                    int cantidad = allAlqCompras.get(tac);
                                    if(alqCompra.getAlquilerCompraPlanificado()!=null){
                                        cantidad +=alqCompra.getAlquilerCompraPlanificado().getCantidad();
                                    }
                                    allAlqCompras.put(tac, cantidad);
                                } else {
                                    // Agrego la herramietna y seteo las horas
                                    if(alqCompra.getAlquilerCompraPlanificado()!=null){
                                        allAlqCompras.put(tac, alqCompra.getAlquilerCompraPlanificado().getCantidad());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2- Retorno 
        List<NTupla> ntlst = new ArrayList<NTupla>();
        Iterator it = allAlqCompras.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();

            SubObraXAlquilerCompraModif tac = (SubObraXAlquilerCompraModif) e.getKey();
            NTupla nt = new NTupla(tac.hashCode());
            nt.setNombre(tac.getTipoAlquilerCompra().getNombre() + " - " + tac.getDescripcion());

            int necesarios = (Integer) e.getValue();
            double enstock = calcularStockDeAlquilerCompra(tac.getTipoAlquilerCompra());

            String[] data = new String[3];
            data[0] = String.valueOf(necesarios);

            if (enstock != 0) {
                if (enstock == 1) {
                    data[1] = "<HTML><span color='#009900'>Hay <b>" + enstock + "</b> '" + tac.getTipoAlquilerCompra().getNombre() + "' Disponible</span>";
                } else {
                    data[1] = "<HTML><span color='#009900'>Hay <b>" + enstock + "</b> '" + tac.getTipoAlquilerCompra().getNombre() + "' Disponibles</span>";
                }
            } else {
                data[1] = "<HTML><span color='#FF0000'>No Hay Disponibles</span>";
            }

            nt.setData(data);
            ntlst.add(nt);
        }
        return ntlst;

    }

    /**
     * Veo cuanto Stock tengo de un Tipo Alquiler/Compra
     *
     * @param rec
     * @return
     */
    private double calcularStockDeAlquilerCompra(TipoAlquilerCompra tac) {
        StockUtils su = new StockUtils();
        return su.calcularStockDeRecursoespecifico(TipoAlquilerCompra.class, tac.getId());
    }

    /**
     * Panel Adicionales. Retorna los datos para llenar la tabla.
     *
     * @return
     */    
    public List<NTupla> llenarTablaPanelAdicionales() {
        List<NTupla> listaFilas = new ArrayList<NTupla>();

        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                for (int i = 0; i < ejecucion.getAdicionales().size(); i++) {
                    EjecucionXAdicional ejecXadi = ejecucion.getAdicionales().get(i);
                    
                        NTupla fila = new NTupla(ejecXadi.hashCode());
                        fila.setNombre(ejecXadi.getAdicionalPlanificado().getTipoAdicional().getNombre()+" - "+ejecXadi.getAdicionalPlanificado().getDescripcion());
                            
                            String[] data = new String[3];
                            // Cantidad
                            data[0] = String.valueOf(ejecXadi.getAdicionalPlanificado().getCantidad());
                            // Precio
                            data[1] = String.valueOf(ejecXadi.getAdicionalPlanificado().getPrecioUnitario());
                            // SubTotal
                            data[2] = String.valueOf(ejecXadi.getAdicionalPlanificado().calcularSubtotal());
                            fila.setData(data);
                            
                        listaFilas.add(fila);
                }
            }
        }
        return listaFilas;
    }

    /**
     * Panel RRHH. Retorna los datos para llenar la tabla.
     * - Busco todos los empleados (unica instancia de cada uno)
     * - Calculo el total de sus 3 tipos de horas que tiene asignado(N,50 y 100)
     * - Busco el estado en el que se encuetra cada uno.
     *      - Tiene Licencias?
     *      - Esta en Alta?
     * @return
     */    
    public List<NTupla> llenarTablaPanelRRHH() {
        //1- Busco todos los empleados (unica instancia de cada uno)
        allEmpleados = new HashMap<Empleado, double[]>();
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                List<TareaEjecucion> todasTareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = todasTareas.get(i);
                    List<DetalleTareaPlanificacion> listaDetalle = tarea.getDetalles();
                    for (int j = 0; j < listaDetalle.size(); j++) {
                        DetalleTareaEjecucion detalleTareaEjecucion = (DetalleTareaEjecucion)listaDetalle.get(j);
                        List<Empleado> listaEmpleados = detalleTareaEjecucion.getEmpleados();
                        for (int k = 0; k < listaEmpleados.size(); k++) {
                            Empleado empleado = listaEmpleados.get(k);
                            
                            if(allEmpleados.containsKey(empleado))
                            {
                                // Ya lo tenia, actualizo
                                double[] horasYaAsignadas = allEmpleados.get(empleado);
                                horasYaAsignadas[0] += detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasNormales();
                                horasYaAsignadas[1] += detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasAl50();
                                horasYaAsignadas[2] += detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasAl100();
                                
                                allEmpleados.put(empleado, horasYaAsignadas);
                            }
                            else
                            {
                                // NO lo tenia, lo creo
                                double[] horasAsignadas = new double[3];
                                horasAsignadas[0] = detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasNormales();
                                horasAsignadas[1] = detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasAl50();
                                horasAsignadas[2] = detalleTareaEjecucion.getDetalleTareaPlanificado().getCantHorasAl100();
                                
                                allEmpleados.put(empleado, horasAsignadas);
                            }                                
                        }
                    }
                }
            }
        }
        
        ntlst = new ArrayList<NTupla>();
        Iterator it = allEmpleados.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            Empleado empleado = (Empleado) e.getKey();
            NTupla nt = new NTupla(empleado.getOID());
            nt.setNombre(empleado.getNombreEmpleado());
            
                double[] horas = (double[])e.getValue();
                
                String[] data = new String[4];
                data[0] = ""+horas[0];
                data[1] = ""+horas[1];
                data[2] = ""+horas[2];
                data[3] = empleado.getEstado().getNombre();
            
            nt.setData(data);
            ntlst.add(nt);
        }
        
        return ntlst;
    }
    
    
    /**
     * Panel Ordenes de Trabajo. Retorna los datos para llenar la tabla.
     * El ID es el HASH de la Tarea
     * @return
     */    
    public List<NTupla> llenarTablaPanelOrdenesDeTrabajo() {
        List<NTupla> ordenes = new ArrayList<NTupla>();
        
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                 ArrayList<TareaEjecucion> listaTareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
                 for (int i = 0; i < listaTareas.size(); i++) {
                    TareaEjecucion tarea = listaTareas.get(i);
                        NTupla ntp = new NTupla(tarea.hashCode());
                        
                            String numero = EjecucionUtils.getNumeroOrdenDeTrabajo(this.pedidoDeObra.getNumero(),tarea.getId());
                            ntp.setNombre(numero);
                            
                                String[] data = new String[3];
                                data[0] = tarea.getNombre();
                                data[1] = FechaUtil.getFecha(tarea.getFechaInicio());
                                data[2] = FechaUtil.getFecha(tarea.getFechaFin());
                                ntp.setData(data);
                                
                        ordenes.add(ntp);
                }
            }
        }
        
        return ordenes;
    }
    
    /**
     * Arma los datso necesarios para la emisión de una orden de trabajo.
     * Recibe el HASH de la Tarea a Emitir
     */
    public void emitirOrdenDeTrabajo(int tareaHash) throws Exception{       
        if (this.pedidoDeObra != null) {
            Ejecucion ejecucion = this.pedidoDeObra.getEjecucion();
            if (ejecucion != null) {
                
                TareaEjecucion te = EjecucionUtils.getTareaFromHash(ejecucion,tareaHash);
                
                try{
                    GestorGenerarOrdenesDeTrabajo emisor = new GestorGenerarOrdenesDeTrabajo();
                    emisor.emitirOrdenDeTrabajo(this.pedidoDeObra,te);
                }catch(Exception ex){
                    throw ex;
                }
            }
        }
    }
    
  
    public boolean esEmpleadoEnBaja(int id)
    {
        Iterator it = allEmpleados.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            Empleado empleado = (Empleado) e.getKey();
            if(empleado.getOID()==id)
            {return empleado.estaBaja();}
            
        }      
         return false;
    }
    
}
