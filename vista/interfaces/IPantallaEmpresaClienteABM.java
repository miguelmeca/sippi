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

    public void mostrarDomicilioEmpresa(String direccion);

    public void mostrarBarrioEmpresa(String barrio);

    public void mostrarLocalidadEmpresa(String localidad);

    public void mostrarProvinciaEmpresa(String provincia);

    public void mostrarPaisEmpresa(String pais);

    public void mostrarDatosTelefono(ArrayList<NTupla> listaTelefonos);

    public void mostrarDatosPlantas(ArrayList<NTupla> listaPlantas);
}
