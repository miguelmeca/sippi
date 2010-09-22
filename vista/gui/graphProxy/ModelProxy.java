package vista.gui.graphProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import vista.gui.graphModel.TaskImpl;
import vista.gui.graphModel.TaskModelImpl;

/**
 *
 * @author iuga
 */
public class ModelProxy {

    private ArrayList<EtapaProxy> etapas;
    private TaskModelImpl modelo;

    public ModelProxy(TaskModelImpl modelo)
    {
        this.modelo = modelo;
        etapas = new ArrayList<EtapaProxy>();

        Object[] tareas = modelo.getTasks();
        for (int i = 0; i < tareas.length; i++)
        {
            TaskImpl ti = (TaskImpl)tareas[i];

            EtapaProxy ep = new EtapaProxy(ti.getName());
            Date ini = new Date(ti.getStartTime());
            Date fin = new Date(ti.getStartTime()+ti.getDuration());
            ep.setFechas(ini,fin);
            ep.setId(Integer.parseInt(ti._id));

            etapas.add(ep);
        }
        
    }

    public ArrayList<EtapaProxy> getEtapas()
    {
        return null;
    }

    public Iterator<EtapaProxy> iteratorEtapas()
    {
        return etapas.iterator();
    }

    public EtapaProxy getEtapa(int id)
    {
        Iterator<EtapaProxy> it = iteratorEtapas();
        while (it.hasNext())
        {
            EtapaProxy ep = it.next();
            if(ep.getId()==id)
            {
                return ep;
            }
        }
        return null;
    }



}
