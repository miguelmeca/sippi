package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class RecursoXProveedor {

    private int id;
    private List<PrecioSegunCantidad> listaPrecios;
    private Proveedor proveedor;


    public RecursoXProveedor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PrecioSegunCantidad> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(List<PrecioSegunCantidad> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void addPrecioSegunCantidad(PrecioSegunCantidad psc)
    {
        if(listaPrecios==null)
        {
            listaPrecios = new ArrayList<PrecioSegunCantidad>();
            listaPrecios.add(psc);
        }
        else
        {
            listaPrecios.add(psc);
        }
    }


}
