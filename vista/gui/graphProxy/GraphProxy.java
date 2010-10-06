package vista.gui.graphProxy;

import java.util.Date;
import vista.gui.graph.TaskGraphComponent;
import vista.gui.graphModel.TaskModelImpl;
import vista.gui.graphModel.TaskPainterImpl;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GraphProxy {

   private TaskModelImpl _taskModel;
   private TaskGraphComponent _graph;

   public GraphProxy()
   {
        _taskModel = TaskModelImpl.createEmptyModel();
   }

   public TaskGraphComponent buildGraph()
   {
       _graph = new TaskGraphComponent(_taskModel, new TaskPainterImpl());
       _graph.setVisible(true);
       return _graph;
   }

    public void addEtapa(int id, String nombre, Date fechaInicio, Date FechaFin)
    {
            EtapaProxy ep = new EtapaProxy(nombre);
            ep.setFechas(fechaInicio, FechaFin);
            ep.setId(id);
            ep.addToModel(_taskModel);
    }

    public TaskGraphComponent refescarGrafico()
    {
        return buildGraph();
    }

    public void setEtapaPredecesora(int idEtapaAntes, int idEtapaDespues)
    {
        ModelProxy mp = new ModelProxy(_taskModel);

        EtapaProxy eta = mp.getEtapa(idEtapaAntes);
        EtapaProxy etd = mp.getEtapa(idEtapaDespues);

        mp.addPredecesor(eta, etd);
    }

}
