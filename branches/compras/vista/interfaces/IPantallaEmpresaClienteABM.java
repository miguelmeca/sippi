/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

import java.util.ArrayList;
import util.NTupla;
import util.Tupla;

/**
 *
 * @author Emmanuel
 */
public interface IPantallaEmpresaClienteABM {
    public void mostrarEstadoEmpresa(String estado);

    public void mostrarRZEmpresa(String razonSocial);

    public void mostrarCUITEmpresa(String cuit);

    public void mostrarEmailEmpresa(String email);

    public void mostrarPaginaWebEmpresa(String paginaWeb);

    public void mostrarDomicilioEmpresa(String calle,String nro,String piso,String dpto,String cp);

    public void mostrarBarrioEmpresa(Tupla barrio);

    public void mostrarLocalidadEmpresa(Tupla localidad);

    public void mostrarProvinciaEmpresa(Tupla provincia);

    public void mostrarPaisEmpresa(Tupla pais);

    public void mostrarDatosTelefono(ArrayList<NTupla> listaTelefonos);

    public void mostrarDatosPlantas(ArrayList<NTupla> listaPlantas);

    public void plantaAgregada();
}
