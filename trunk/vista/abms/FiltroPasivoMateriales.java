/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;
import modelo.RecursoEspecifico;
import vista.gen.FiltroPasivo;

/**
 * @author Iuga
 */
public class FiltroPasivoMateriales extends FiltroPasivo{

    private Proveedor proveedor;

    public FiltroPasivoMateriales(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    @Override
    public List Excecute(List lista) {
        
        List listaFinal = new ArrayList();
        
        System.out.println("Initial Size:"+lista.size());
        
        for (int i = 0; i < lista.size(); i++) {
            
            System.out.print("*");
            
            Object object = lista.get(i);
            if(object instanceof RecursoEspecifico)
            {
                RecursoEspecifico re = (RecursoEspecifico)object;
                // Si el recuro especifico es un Material, la agrego de la lista
                if(re.getTipoRecursoespecifico().equals(RecursoEspecifico.tiposDeRecurso[0]) && loTieneElProveedor(re))
                {
                    listaFinal.add(object);
                }
            }
        }
        
        System.out.println("Final Size:"+listaFinal.size());
        
        return listaFinal;
    }  
    
    /**
     * Verifica que este material sea vendido por el proveedor
     * Si el proveedor es null, retorno todo!
     * @param re
     * @return 
     */
    private boolean loTieneElProveedor(RecursoEspecifico re)
    {
        if(this.proveedor==null)
        {
            return true;
        }        
        for (int i = 0; i < this.proveedor.getListaArticulos().size(); i++) {
            RecursoEspecifico vendido = this.proveedor.getListaArticulos().get(i);
            if(vendido.getId()==re.getId())
            {
                return true;
            }
        }
        return false;
    }
    
}

