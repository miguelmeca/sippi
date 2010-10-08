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

    private void setId(int id) {
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
    
    public ArrayList<PrecioSegunCantidad> getListaUltimosPrecios() {
        ArrayList<PrecioSegunCantidad> ultimosPrecios=new ArrayList<PrecioSegunCantidad>();
        if(listaPrecios.size()>0)
        {
            ultimosPrecios.add(listaPrecios.get(0));
        }
        boolean cant=false;
        for (int i = 1; i < listaPrecios.size(); i++) 
        {
            for (int j= 0; j < ultimosPrecios.size(); j++)
            {
               if(cant==false && listaPrecios.get(i).getCantidad()==ultimosPrecios.get(j).getCantidad())
               {cant=true;
                if(listaPrecios.get(i).getFecha().after(ultimosPrecios.get(j).getFecha()))
                {
                    ultimosPrecios.remove(j);
                    ultimosPrecios.add(listaPrecios.get(i));
                }
               }
                   
            }
            if(cant==false)
            {ultimosPrecios.add(listaPrecios.get(i));}
            
        }
        
        
        
        return ultimosPrecios;
    }


}
