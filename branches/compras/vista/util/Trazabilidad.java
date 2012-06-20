/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Cotizacion;
import modelo.CotizacionModificada;
import modelo.DetalleSubObraXTarea;
import modelo.DetalleSubObraXTareaModif;
import modelo.SubObra;
import modelo.SubObraModificada;
import modelo.SubObraXAdicional;
import modelo.SubObraXAdicionalModif;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXAlquilerCompraModif;
import modelo.SubObraXHerramienta;
import modelo.SubObraXHerramientaModif;
import modelo.SubObraXMaterial;
import modelo.SubObraXMaterialModif;
import modelo.SubObraXTarea;
import modelo.SubObraXTareaModif;

/**
 *
 * @author Emmanuel
 */
public class Trazabilidad {
    
    /**
     * Este método permite copiar la cotización a una cotizacionModificada para ser tratada en Planificación.
     * @param cot La cotización original
     * @return La cotización modificada
     */
    public CotizacionModificada copiarCotizacionACotizacionModificada(Cotizacion cot){
        CotizacionModificada cotMod = new CotizacionModificada();
        cotMod.setCotizacionOriginal(cot);
        cotMod.setFechaCreacion(cot.getFechaCreacion());
        cotMod.setFechaLimiteEntrega(cot.getFechaLimiteEntrega());
        cotMod.setLugarEntrega(cot.getLugarEntrega());
        cotMod.setNroCotizacion(cot.getNroCotizacion());
        cotMod.setNroRevision(cot.getNroRevision());
        cotMod.setPlazoEntrega(cot.getPlazoEntrega());
        cotMod.setValidezOferta(cot.getValidezOferta());

        Iterator<SubObra> itSubObras = cot.getSubObras().iterator();
        while(itSubObras.hasNext()){
            SubObra subObra = itSubObras.next();
            
            SubObraModificada subObraMod = new SubObraModificada();
            List<SubObraXAdicional> subObraXAdicionalModifs = new ArrayList<SubObraXAdicional>();
            List<SubObraXAlquilerCompra> subObraXAlquilerCompraModifs = new ArrayList<SubObraXAlquilerCompra>();
            List<SubObraXHerramienta> subObraXHerramientaModifs = new ArrayList<SubObraXHerramienta>();
            List<SubObraXMaterial> subObraXMaterialModifs = new ArrayList<SubObraXMaterial>();
            List<SubObraXTarea> subObraXTareaModifs = new ArrayList<SubObraXTarea>();
            subObraMod.setDescripcion(subObra.getDescripcion());
            subObraMod.setGananciaMonto(subObra.getGananciaMonto());
            subObraMod.setGananciaPorcentaje(subObra.getGananciaPorcentaje());
            subObraMod.setNombre(subObra.getNombre());
                
            Iterator<SubObraXAdicional> itSubObraXAdcional = subObra.getAdicionales().iterator();
            while(itSubObraXAdcional.hasNext()){
                SubObraXAdicional subObraXAdicional = itSubObraXAdcional.next();
                SubObraXAdicionalModif soXAdicionalModif = new SubObraXAdicionalModif();
                soXAdicionalModif.setOriginal(subObraXAdicional);
                soXAdicionalModif.setCantDias(subObraXAdicional.getCantDias());
                soXAdicionalModif.setCantOperarios(subObraXAdicional.getCantOperarios());
                soXAdicionalModif.setDescripcion(subObraXAdicional.getDescripcion());
                soXAdicionalModif.setPrecioUnitario(subObraXAdicional.getPrecioUnitario());
                soXAdicionalModif.setTipoAdicional(subObraXAdicional.getTipoAdicional());
                subObraXAdicionalModifs.add(soXAdicionalModif);
            }
            subObraMod.setAdicionales(subObraXAdicionalModifs);

            Iterator<SubObraXAlquilerCompra> itSubObraXAlquilerCompra = subObra.getAlquileresCompras().iterator();
            while(itSubObraXAlquilerCompra.hasNext()){
                SubObraXAlquilerCompra subObraXAlquilerCompra = itSubObraXAlquilerCompra.next();
                SubObraXAlquilerCompraModif subObraXAlquilerCompraModif = new SubObraXAlquilerCompraModif();
                subObraXAlquilerCompraModif.setOriginal(subObraXAlquilerCompra);
                subObraXAlquilerCompraModif.setCantidad(subObraXAlquilerCompraModif.getCantidad());
                subObraXAlquilerCompraModif.setDescripcion(subObraXAlquilerCompra.getDescripcion());
                subObraXAlquilerCompraModif.setPrecioUnitario(subObraXAlquilerCompraModif.getPrecioUnitario());
                subObraXAlquilerCompraModif.setTipoAlquilerCompra(subObraXAlquilerCompra.getTipoAlquilerCompra());
                subObraXAlquilerCompraModifs.add(subObraXAlquilerCompraModif);
            }
            subObraMod.setAlquileresCompras(subObraXAlquilerCompraModifs);

            Iterator<SubObraXHerramienta> itSubObraXHerramienta = subObra.getHerramientas().iterator();
            while(itSubObraXHerramienta.hasNext()){
                SubObraXHerramienta subObraXHerramienta = itSubObraXHerramienta.next();
                SubObraXHerramientaModif subObraXHerramientaModif = new SubObraXHerramientaModif();
                subObraXHerramientaModif.setOriginal(subObraXHerramienta);
//                subObraXHerramientaModif.setCantDias(subObraXHerramienta.getCantDias());
                subObraXHerramientaModif.setCantHoras(subObraXHerramienta.getCantHoras());
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
                subObraXMaterialModif.setCantidad(subObraXMaterial.getCantidad());
                subObraXMaterialModif.setMaterial(subObraXMaterial.getMaterial());
                subObraXMaterialModif.setPrecioUnitario(subObraXMaterial.getPrecioUnitario());
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
                    detalleMod.setCantHorasAl100(detalle.getCantHorasAl100());
                    detalleMod.setCantHorasAl50(detalle.getCantHorasAl50());
                    detalleMod.setCantHorasNormales(detalle.getCantHorasNormales());
                    detalleMod.setCantidadPersonas(detalle.getCantidadPersonas());
                    detalleMod.setCostoXHoraNormal(detalle.getCostoXHoraNormal());
                    detalleMod.setEspecialidad(detalle.getEspecialidad());
                    //detalleMod.setRangoEmpleado(detalle.getRangoEmpleado());
                    detalleMods.add(detalleMod);
                }
                subObraXTareaModif.setDetallesMod(detalleMods);
                subObraXTareaModifs.add(subObraXTareaModif);
            }
            subObraMod.setTareas(subObraXTareaModifs);

            subObraMod.setSubObraOriginal(subObra);

            cotMod.getSubObras().add(subObraMod);
        }

        return cotMod;
    }

}
