/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class EstadoEmpresaAlta extends EstadoEmpresa{

    public EstadoEmpresaAlta() {
        this.setNombre("Alta");
    }

    @Override
    public boolean esAlta() {
        return true;
    }

    @Override
    public boolean esBaja() {
        return false;
    }



    void setBaja(Empresa aThis) {
        aThis.setHib_flag_estado("modelo.EstadoEmpresaBaja");
        aThis.setEstado(new EstadoEmpresaBaja());
    }

}
