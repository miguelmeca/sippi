/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionMateriales;
import java.util.ArrayList;
import modelo.Cotizacion;
import modelo.SubObra;
import util.NTupla;
import util.Tupla;

/**
 *
 * @author Administrador
 */
public class GestorMaterialesCotizacionIntermedia extends GestorCotizacionMateriales{

    @Override
    public void agregarMaterial(int idRXP, int cantidad, String desc, double precio) {
        super.agregarMaterial(idRXP, cantidad, desc, precio);
    }

    @Override
    public Cotizacion getCotizacion() {
        return super.getCotizacion();
    }

    @Override
    public ArrayList<Tupla> getEspecificacionMaterial(int id) {
        return super.getEspecificacionMaterial(id);
    }

    @Override
    public ArrayList<NTupla> getMaterialesAUtilizar() {
        return super.getMaterialesAUtilizar();
    }

    @Override
    public ArrayList<NTupla> getMaterialesDisponibles() {
        return super.getMaterialesDisponibles();
    }

    @Override
    public String getNombreRecurso(int idR, int idRE) {
        return super.getNombreRecurso(idR, idRE);
    }

    @Override
    public double getPrecioMaterial(int idRXP, int cantidad) {
        return super.getPrecioMaterial(idRXP, cantidad);
    }

    @Override
    public SubObra getSubObraActual() {
        return super.getSubObraActual();
    }

    @Override
    public double getSubtotal(int idRXP, int cantidad) {
        return super.getSubtotal(idRXP, cantidad);
    }

    @Override
    public String mostrarMaterial(int idR) {
        return super.mostrarMaterial(idR);
    }

    @Override
    public ArrayList<NTupla> mostrarPreciosVigentes(int idProv, int idRe) {
        return super.mostrarPreciosVigentes(idProv, idRe);
    }

    @Override
    public ArrayList<Tupla> mostrarProveedores() {
        return super.mostrarProveedores();
    }

    @Override
    public ArrayList<Tupla> mostrarProveedoresMaterial(int idRe) {
        return super.mostrarProveedoresMaterial(idRe);
    }

    @Override
    public String mostrarRE(int idRe) {
        return super.mostrarRE(idRe);
    }

    @Override
    public ArrayList<NTupla> mostrarRecursosEspecificos(int idProveedor) {
        return super.mostrarRecursosEspecificos(idProveedor);
    }

    @Override
    public ArrayList<NTupla> mostrarRecursosEspecificosXProveedor(int idR, int idRE) {
        return super.mostrarRecursosEspecificosXProveedor(idR, idRE);
    }

    @Override
    public String mostrarUnidadDeMedida(int idRecEsp) {
        return super.mostrarUnidadDeMedida(idRecEsp);
    }

    @Override
    public boolean quitarMaterial(int idDM) {
        return super.quitarMaterial(idDM);
    }

    @Override
    public boolean quitarPrecioVigente(int idPrecio) {
        return super.quitarPrecioVigente(idPrecio);
    }

    @Override
    public void refrescarPantallas() {
        super.refrescarPantallas();
    }
    
    
    
}
