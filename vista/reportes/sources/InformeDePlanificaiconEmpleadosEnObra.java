/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.DocumentException;
import java.util.HashMap;
import modelo.PlanificacionXXX;

/**
 *
 * @author Administrador
 */
public class InformeDePlanificaiconEmpleadosEnObra extends InformeDePlanificacion{

    public InformeDePlanificaiconEmpleadosEnObra(PlanificacionXXX planificacion) {
        super(planificacion);
    }
  
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        makeCabecera(params);
    }    
}
