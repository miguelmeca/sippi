/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class EstadoEmpresaBaja extends EstadoEmpresa{

    public EstadoEmpresaBaja() {
        this.setNombre("Baja");
    }

    @Override
    public boolean esAlta() {
        return false;
    }

    @Override
    public boolean esBaja() {
        return true;
    }

    public void setAlta(Empresa aThis) {
        aThis.setHib_flag_estado("modelo.EstadoEmpresaAlta");
        aThis.setEstado(new EstadoEmpresaAlta());
    }
}
