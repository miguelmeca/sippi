/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import java.util.List;
import modelo.RecursoEspecifico;
import vista.gen.FiltroPasivo;

/**
 * @author Iuga
 */
public class FiltroPasivoHerramientas extends FiltroPasivo{
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
                // Si el recuro especifico es una Herramienta, la agrego de la lista
                if(re.getTipoRecursoespecifico().equals(RecursoEspecifico.tiposDeRecurso[1]))
                {
                    listaFinal.add(object);
                }
            }
        }
        
        System.out.println("Final Size:"+listaFinal.size());
        
        return listaFinal;
    }        
}

