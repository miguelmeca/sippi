/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import modelo.HerramientaDeEmpresa;
import vista.gen.PantallaABMGenerica;

/**
 *
 * @author Administrador
 */
public class TestABM extends PantallaABMGenerica {

    public TestABM(Class clase,int comportamiento) {
        super(clase,comportamiento);
    }

    @Override
    protected ArrayList<String[]> getNombresCampos() {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"nroSerie","Número de Serie"});
            columnas.add(new String[]{"recursoEsp","Recurso"});
        
        return columnas;
    }
    
    
    
}
