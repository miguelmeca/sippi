package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CotizacionModificada extends Cotizacion{
    private Cotizacion cotizacionOriginal;
    
    private final static String nombreSubObraGeneral= "Gastos generales a la obra creados en planificación";
    private final static String descripcionSubObraGeneral= "Esta subobra ha sido generada automáticamente por el sistema para almacenar los gastos generales de la obra generados durante la planificación";
    public final static String obsevacionSubObraXTareaDeSubObraGeneral= "Esta tarea ha sido generada automáticamente por el sistema para almacenar los gastos de una tarea creada durante la planificación";
    
    public CotizacionModificada (Cotizacion cot) {
        cotizacionOriginal=cot;
        this.setDescripcion(cot.getDescripcion());
            this.setEstado(cot.getEstado());
            this.setFechaCreacion(cot.getFechaCreacion());
            this.setFechaLimiteEntrega(cot.getFechaLimiteEntrega());
            this.setLugarEntrega(cot.getLugarEntrega());
            this.setNroCotizacion(cot.getNroCotizacion());
            this.setNroRevision(cot.getNroRevision());
            this.setPlazoEntrega(cot.getPlazoEntrega());
            this.setValidezOferta(cot.getValidezOferta());
            Iterator<SubObra> itSubObras = cot.getSubObras().iterator();
            while(itSubObras.hasNext()){
                SubObra subObra = itSubObras.next();

                SubObraModificada subObraMod = new SubObraModificada();
                List<SubObraXAdicional> subObraXAdicionalModifs = new ArrayList<SubObraXAdicional>();
                List<SubObraXAlquilerCompra> subObraXAlquilerCompraModifs = new ArrayList<SubObraXAlquilerCompra>();
                List<SubObraXHerramienta> subObraXHerramientaModifs = new ArrayList<SubObraXHerramienta>();
                List<SubObraXMaterial> subObraXMaterialModifs = new ArrayList<SubObraXMaterial>();
                List<SubObraXTarea> subObraXTareaModifs = new ArrayList<SubObraXTarea>();

                Iterator<SubObraXAdicional> itSubObraXAdcional = subObra.getAdicionales().iterator();
                
                while(itSubObraXAdcional.hasNext()){
                    SubObraXAdicional subObraXAdicional = itSubObraXAdcional.next();
                    SubObraXAdicionalModif soXAdicionalModif = new SubObraXAdicionalModif();
                    soXAdicionalModif.setOriginal(subObraXAdicional);
                    int aleaCantDias = (int) (Math.random()*subObraXAdicional.getCantDias() + 2);
                    soXAdicionalModif.setCantDias(aleaCantDias);
                    int aleaCantOperarios = (int) (Math.random()*subObraXAdicional.getCantOperarios() + 3);
                    soXAdicionalModif.setCantOperarios(aleaCantOperarios);
                    soXAdicionalModif.setDescripcion(subObraXAdicional.getDescripcion());
                    double aleaPrecioUnit = (int) Math.random()*subObraXAdicional.getPrecioUnitario() + 1;
                    soXAdicionalModif.setPrecioUnitario(aleaPrecioUnit);
                    soXAdicionalModif.setTipoAdicional(subObraXAdicional.getTipoAdicional());
                    subObraXAdicionalModifs.add(soXAdicionalModif);
                }
                subObraMod.setAdicionales(subObraXAdicionalModifs);


                Iterator<SubObraXAlquilerCompra> itSubObraXAlquilerCompra = subObra.getAlquileresCompras().iterator();
                while(itSubObraXAlquilerCompra.hasNext()){
                    SubObraXAlquilerCompra subObraXAlquilerCompra = itSubObraXAlquilerCompra.next();
                    SubObraXAlquilerCompraModif subObraXAlquilerCompraModif = new SubObraXAlquilerCompraModif();
                    subObraXAlquilerCompraModif.setOriginal(subObraXAlquilerCompra);
                    int aleaCant = (int) Math.random()*subObraXAlquilerCompraModif.getCantidad() + 10;
                    subObraXAlquilerCompraModif.setCantidad(aleaCant);
                    subObraXAlquilerCompraModif.setDescripcion(subObraXAlquilerCompra.getDescripcion());
                    double aleaPU = (int) Math.random()*subObraXAlquilerCompraModif.getPrecioUnitario() + 10;
                    subObraXAlquilerCompraModif.setPrecioUnitario(aleaPU);
                    subObraXAlquilerCompraModif.setTipoAlquilerCompra(subObraXAlquilerCompra.getTipoAlquilerCompra());
                    subObraXAlquilerCompraModifs.add(subObraXAlquilerCompraModif);
                }
                subObraMod.setAlquileresCompras(subObraXAlquilerCompraModifs);

                Iterator<SubObraXHerramienta> itSubObraXHerramienta = subObra.getHerramientas().iterator();
                while(itSubObraXHerramienta.hasNext()){
                    SubObraXHerramienta subObraXHerramienta = itSubObraXHerramienta.next();
                    SubObraXHerramientaModif subObraXHerramientaModif = new SubObraXHerramientaModif();
                    subObraXHerramientaModif.setOriginal(subObraXHerramienta);
//                    int aleaCantDias = (int) (Math.random()*subObraXHerramienta.getCantDias() + 4);
//                    subObraXHerramientaModif.setCantDias(aleaCantDias);
                    int aleaCantHoras = (int) (Math.random()*subObraXHerramienta.getCantHoras() + 10);
                    subObraXHerramientaModif.setCantHoras(aleaCantHoras);
                    subObraXHerramientaModif.setHerramienta(subObraXHerramienta.getHerramienta());
                    subObraXHerramientaModif.setObservaciones(subObraXHerramienta.getObservaciones());
                    subObraXHerramientaModifs.add(subObraXHerramientaModif);
                }
                subObraMod.setHerramientas(subObraXHerramientaModifs);

                Iterator<SubObraXMaterial> itSubObraXMaterial = subObra.getMateriales().iterator();
                while(itSubObraXMaterial.hasNext()){
                    SubObraXMaterial subObraXMaterial = itSubObraXMaterial.next();
                    SubObraXMaterialModif subObraXMaterialModif = new SubObraXMaterialModif();
                    subObraXMaterialModif.setOriginal(subObraXMaterial);
                    int aleaCant = (int) (Math.random()*subObraXMaterial.getCantidad() + 15);
                    subObraXMaterialModif.setCantidad(aleaCant);
                    subObraXMaterialModif.setMaterial(subObraXMaterial.getMaterial());
                    double aleaPrecioUnit = (int) Math.random()*subObraXMaterial.getPrecioUnitario() + 3;
                    subObraXMaterialModif.setPrecioUnitario(aleaPrecioUnit);
                     subObraXMaterialModifs.add(subObraXMaterialModif);
                }
                subObraMod.setMateriales(subObraXMaterialModifs);

                Iterator<SubObraXTarea> itSubObraXTarea = subObra.getTareas().iterator();
                while(itSubObraXTarea.hasNext()){
                    SubObraXTarea subObraXTarea = itSubObraXTarea.next();
                    SubObraXTareaModif subObraXTareaModif = new SubObraXTareaModif();
                    subObraXTareaModif.setOriginal(subObraXTarea);
                    subObraXTareaModif.setObservaciones(subObraXTarea.getObservaciones());
                    subObraXTareaModif.setNombre(subObraXTarea.getNombre());
                    subObraXTareaModif.setTipoTarea(subObraXTarea.getTipoTarea());
                    List<DetalleSubObraXTareaModif> detalleMods = new ArrayList<DetalleSubObraXTareaModif>();
                    Iterator<DetalleSubObraXTarea> itDetalleSOXT = subObraXTarea.getDetalles().iterator();
                    while(itDetalleSOXT.hasNext()){
                        DetalleSubObraXTarea detalle = itDetalleSOXT.next();
                        DetalleSubObraXTareaModif detalleMod = new DetalleSubObraXTareaModif();
                        detalleMod.setOriginal(detalle);
                        double aleaCantHoras100 = (int) Math.random()*detalle.getCantHorasAl100() + 10;
                        detalleMod.setCantHorasAl100(aleaCantHoras100);
                        double aleaCantHoras50 = (int) Math.random()*detalle.getCantHorasAl50() + 10;
                        detalleMod.setCantHorasAl50(aleaCantHoras50);
                        double aleaCantHorasNormales = (int) Math.random()*detalle.getCantHorasNormales() + 12;
                        detalleMod.setCantHorasNormales(aleaCantHorasNormales);
                        int aleaCantPersona = (int) Math.random()*detalle.getCantidadPersonas() + 12;
                        detalleMod.setCantidadPersonas(aleaCantPersona);
                        double aleaCostoXHoraNormal = (int) Math.random()*detalle.getCostoXHoraNormal() + 12;
                        detalleMod.setCostoXHoraNormal(aleaCostoXHoraNormal);
                        detalleMod.setEspecialidad(detalle.getEspecialidad());
                        //detalleMod.setRangoEmpleado(detalle.getRangoEmpleado());
                        //detalleMod.setTipoEspecialidad(detalle.getTipoEspecialidad());
                         detalleMods.add(detalleMod);
                    }
                    subObraXTareaModif.setDetallesMod(detalleMods);
                    subObraXTareaModifs.add(subObraXTareaModif);
                }
                subObraMod.setTareas(subObraXTareaModifs);
                
                subObraMod.setSubObraOriginal(subObra);

                this.getSubObras().add(subObraMod);
        }
    }
            
    public CotizacionModificada () {
    }

    public Cotizacion getCotizacionOriginal() {
        return cotizacionOriginal;
    }

    public void setCotizacionOriginal(Cotizacion cotizacionOriginal) {
        this.cotizacionOriginal = cotizacionOriginal;
    }
    
    public SubObra getSubObraGeneral() {
        //Si la subOtra existe la devuelvo
        for(int i=0;i<subObras.size();i++)
        {
            if(subObras.get(i).esSubObraGeneralALaPlanificacion())
            {   return subObras.get(i);}
        }
        
        //Si la subobra todavia no existe la creo y la devuelvo
        SubObraModificada general=new SubObraModificada();
        general.setDescripcion(descripcionSubObraGeneral);
        general.setNombre(nombreSubObraGeneral);
        general.setSubObraGeneralALaPlanificacion(true);
        general.setGananciaMonto(0.0);
        
        subObras.add(0, general);
        return general;
    }

    
    
}
