package modelo;

import java.util.List;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Proveedor extends Empresa {

    private List precios;

    public List getPrecios() {
        return precios;
    }

    public void setPrecios(List precios) {
        this.precios = precios;
    }

    public String[] buscarTipoDeRecursosDisponibles()
    {
        return null;
    }


}
