/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import com.itextpdf.text.Font;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 *
 * @author Administrador
 */
public class ArbolDeTareasCeldaDragable_old extends JLabel implements DragGestureListener,DragSourceListener{

    
    public ArbolDeTareasCeldaDragable_old() {
        
         DragSource dragSource = DragSource.getDefaultDragSource();
         dragSource.createDefaultDragGestureRecognizer(this,DnDConstants.ACTION_COPY_OR_MOVE, this);
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        if (DnDUtils.isDebugEnabled()) {
        DnDUtils.debugPrintln("Initiating event is "
            + dge.getTriggerEvent());
        DnDUtils.debugPrintln("Complete event set is:");
        Iterator iter = dge.iterator();
        while (iter.hasNext()) {
            DnDUtils.debugPrintln("\t" + iter.next());
        }
        }
        Transferable transferable = new JLabelTransferable(this);
        dge.startDrag(null, transferable, this);
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {
          DnDUtils.debugPrintln("Drag Source: dragEnter, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {
       DnDUtils.debugPrintln("Drag Source: dragOver, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {
       DnDUtils.debugPrintln("Drag Source: dropActionChanged, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
    }

    @Override
    public void dragExit(DragSourceEvent dse) {
         DnDUtils.debugPrintln("Drag Source: dragExit");
    }

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
         DnDUtils.debugPrintln("Drag Source: drop completed, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()) + ", success: "
        + dsde.getDropSuccess());
    }
    
    
    
}
