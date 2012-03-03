/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import com.hackelare.coolgantt.ICoolGanttEvent;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class GanttEventMouse implements ICoolGanttEvent{

    @Override
    public Date inGetProjectStartDate() {
        return new Date();
    }

    @Override
    public Date inGetProjectEndDate() {
        return new Date();
    }

    @Override
    public void outClickPhase(int i) {
        //JOptionPane.showMessageDialog(new JFrame(),"Se hizo click en la tarea: "+i);
    }

    @Override
    public void outMovePhase(int i, Date date) {
          //JOptionPane.showMessageDialog(new JFrame(),"Se movio en la tarea: "+i);
    }

    @Override
    public void outExtendPhaseBackward(int i, Date date) {
          //JOptionPane.showMessageDialog(new JFrame(),"Se agrando a la izquierda la tarea: "+i);
    }

    @Override
    public void outExtendPhaseForward(int i, Date date) {
         //JOptionPane.showMessageDialog(new JFrame(),"Se agrando a la derecha la tarea: "+i);
    }
    
}
