package vista.gui.graphProxy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import vista.gui.graph.Task;
import vista.gui.graph.TaskModel;
import vista.gui.graphModel.ColorLabel;
import vista.gui.graphModel.ManImpl;
import vista.gui.graphModel.TaskImpl;
import vista.gui.graphModel.TaskModelImpl;
import vista.gui.graphUtils.Utils;

/**
 * Hago un proxy para que sirva de Gantt
 * @author iuga
 */
public class EtapaProxy
{
    private ManImpl worker;
    private TaskImpl tarea;

    public EtapaProxy(String nombre)
    {
            worker = new ManImpl();
            worker.setName(nombre);

            tarea = new TaskImpl();
            tarea.setMan(worker);
            tarea.setName(nombre);
            tarea.setDuration(1);
            Date hoy = new Date();
            tarea.setStartTime(hoy.getTime());

            tarea.setColorLabel(ColorLabel.COLOR_LABELS[8]);
    }

    public void addToModel(TaskModelImpl modelo)
    {
        modelo.addMan(worker);
        modelo.addTask(tarea);
    }

    public void setFechas(Date fechaInicio, Date fechaFin)
    {
        tarea.setStartTime(fechaInicio.getTime()/Utils.MILLISECONDS_PER_DAY);
        tarea.setDuration(fechaFin.getTime()-fechaInicio.getTime());
    }
   
    public String getNombre()
    {
        return worker.getName();
    }

    public void setNombre(String nombre)
    {
        worker.setName(nombre);
    }

    public long getFechaInicio()
    {
        return tarea.getStartTime();
    }

    public long getFechaFin()
    {
        return tarea.getStartTime()+tarea.getDuration();
    }

    public EtapaProxy[] getPredecesor()
    {
        // FALTA IMPLEMENTAR
        return null;
    }

    public void setId(int id)
    {
        tarea._id = String.valueOf(id);
    }

    public int getId()
    {
        return Integer.parseInt(tarea._id);
    }

    public void setEtapaTrasporte()
    {
        tarea.setColorLabel(ColorLabel.COLOR_LABELS[7]);
    }

    private TaskImpl getTarea()
    {
        return this.tarea;
    }

    public void addPredecesor(EtapaProxy etapa)
    {
        // Tomo los anteriores
        TaskImpl[] pred = tarea.getPredecessors();
        ArrayList<TaskImpl> lista = new ArrayList<TaskImpl>();
        if(pred!=null)
        {
            for (int i = 0; i < pred.length; i++)
            {
                TaskImpl ti = pred[i];
                lista.add(ti);
            }
        }

        // Agrego la actual
        lista.add(etapa.getTarea());

        // PEDAZO DE WORKARROUND xq no me funciona el casteo
        Iterator<TaskImpl> it = lista.iterator();
        int siz = lista.size();
        TaskImpl[] listado = new TaskImpl[siz];

        int i = 0;
        while (it.hasNext())
        {
            TaskImpl ti = it.next();
            listado[i] = ti;
            i++;
        }

        tarea.setPredecessors(listado);
    }

    public TaskImpl getTask()
    {
        return tarea;
    }


}
