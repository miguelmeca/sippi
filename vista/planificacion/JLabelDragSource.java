/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.Iterator;
import javax.swing.JLabel;

/**
 *
 * @author Administrador
 */
public class JLabelDragSource implements DragGestureListener,
    DragSourceListener {
    
    protected JLabel label; 
    
  public JLabelDragSource(JLabel label) {
    this.label = label;

    // Use the default DragSource
    DragSource dragSource = DragSource.getDefaultDragSource();

    // Create a DragGestureRecognizer and
    // register as the listener
    dragSource.createDefaultDragGestureRecognizer(label,
        DnDConstants.ACTION_COPY_OR_MOVE, this);
  }

  // Implementation of DragGestureListener interface.
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
    Transferable transferable = new JLabelTransferable(label);
    dge.startDrag(null, transferable, this);
  }

  // Implementation of DragSourceListener interface
  public void dragEnter(DragSourceDragEvent dsde) {
    DnDUtils.debugPrintln("Drag Source: dragEnter, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
  }

  public void dragOver(DragSourceDragEvent dsde) {
    DnDUtils.debugPrintln("Drag Source: dragOver, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
  }

  public void dragExit(DragSourceEvent dse) {
    DnDUtils.debugPrintln("Drag Source: dragExit");
  }

  public void dropActionChanged(DragSourceDragEvent dsde) {
    DnDUtils.debugPrintln("Drag Source: dropActionChanged, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()));
  }

  public void dragDropEnd(DragSourceDropEvent dsde) {
    DnDUtils.debugPrintln("Drag Source: drop completed, drop action = "
        + DnDUtils.showActions(dsde.getDropAction()) + ", success: "
        + dsde.getDropSuccess());
  }
    
}
