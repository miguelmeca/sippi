/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.rrhh;

import javax.swing.RowFilter;

/**
 *
 * @author Emmanuel
 */
public class EmpleadoActivoRowFilter extends RowFilter{

    @Override
    public boolean include(Entry entry) {
        boolean incluir = false;
        for (int i = entry.getValueCount() - 1; i >= 0; i--) {
            if (entry.getValue(i) instanceof ExplorarEmpleados_celdaDatos)
            {
                ExplorarEmpleados_celdaDatos datos = (ExplorarEmpleados_celdaDatos) entry.getValue(i);
                if(datos.getEstado().contains("Activo"))
                    incluir = true;
            }
        }
        
        return incluir;
    }
    
}
