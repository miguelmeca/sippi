/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gui.dnd;


import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import javax.swing.JComponent;


public class PanelDropTarget implements DropTargetListener {
    
  protected IDropEvent dropEventHandler;
  protected JComponent pane;
  protected DropTarget dropTarget;
  protected boolean acceptableType; // Indicates whether data is acceptable
  protected DataFlavor targetFlavor; // Flavor to use for transfer  
    
    
  public PanelDropTarget(JComponent pane,IDropEvent eventHandler) {
    this.pane = pane;
    dropEventHandler = eventHandler;
    // Create the DropTarget and register
    // it with the JPanel.
    dropTarget = new DropTarget(pane, DnDConstants.ACTION_COPY_OR_MOVE,
        this, true, null);
  }

  // Implementation of the DropTargetListener interface
  public void dragEnter(DropTargetDragEvent dtde) {
    DnDUtils.debugPrintln("dragEnter, drop action = "
        + DnDUtils.showActions(dtde.getDropAction()));

    // Get the type of object being transferred and determine
    // whether it is appropriate.
    checkTransferType(dtde);

    // Accept or reject the drag.
    acceptOrRejectDrag(dtde);
  }

  public void dragExit(DropTargetEvent dte) {
    DnDUtils.debugPrintln("DropTarget dragExit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    DnDUtils.debugPrintln("DropTarget dragOver, drop action = "
        + DnDUtils.showActions(dtde.getDropAction()));

    // Accept or reject the drag
    acceptOrRejectDrag(dtde);
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    DnDUtils.debugPrintln("DropTarget dropActionChanged, drop action = "
        + DnDUtils.showActions(dtde.getDropAction()));

    // Accept or reject the drag
    acceptOrRejectDrag(dtde);
  }

  public void drop(DropTargetDropEvent dtde) {
    DnDUtils.debugPrintln("DropTarget drop, drop action = "
        + DnDUtils.showActions(dtde.getDropAction()));

    // Check the drop action
    if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
      // Accept the drop and get the transfer data
      dtde.acceptDrop(dtde.getDropAction());
      Transferable transferable = dtde.getTransferable();

      try {
        boolean result = dropComponent(transferable,dtde.getLocation());

        dtde.dropComplete(result);
        DnDUtils.debugPrintln("Drop completed, success: " + result);
      } catch (Exception e) {
        DnDUtils.debugPrintln("Exception while handling drop " + e);
        dtde.dropComplete(false);
      }
    } else {
      DnDUtils.debugPrintln("Drop target rejected drop");
      dtde.rejectDrop();
    }
  }

  // Internal methods start here

  protected boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
    int dropAction = dtde.getDropAction();
    int sourceActions = dtde.getSourceActions();
    boolean acceptedDrag = false;

    DnDUtils.debugPrintln("\tSource actions are "
        + DnDUtils.showActions(sourceActions) + ", drop action is "
        + DnDUtils.showActions(dropAction));

    // Reject if the object being transferred
    // or the operations available are not acceptable.
    if (!acceptableType
        || (sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
      DnDUtils.debugPrintln("Drop target rejecting drag");
      dtde.rejectDrag();
    } else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
      // Not offering copy or move - suggest a copy
      DnDUtils.debugPrintln("Drop target offering COPY");
      dtde.acceptDrag(DnDConstants.ACTION_COPY);
      acceptedDrag = true;
    } else {
      // Offering an acceptable operation: accept
      DnDUtils.debugPrintln("Drop target accepting drag");
      dtde.acceptDrag(dropAction);
      acceptedDrag = true;
    }

    return acceptedDrag;
  }

  protected void checkTransferType(DropTargetDragEvent dtde) {
    // Only accept a flavor that returns a Component
    acceptableType = true;
    acceptableType = false;
    DataFlavor[] fl = dtde.getCurrentDataFlavors();
    
    targetFlavor = fl[6];
    acceptableType = true;
    
//    for (int i = 0; i < fl.length; i++) {
//      Class dataClass = fl[i].getRepresentationClass();
//
//      if (Component.class.isAssignableFrom(dataClass)) {
//        // This flavor returns a Component - accept it.
//        targetFlavor = fl[i];
//        acceptableType = true;
//        break;
//      }
//    }

    DnDUtils.debugPrintln("File type acceptable - " + acceptableType);
  }

  protected boolean dropComponent(Transferable transferable, Point location)
      throws IOException, UnsupportedFlavorException {
      
    Object o = transferable.getTransferData(targetFlavor);
//    if (o instanceof Component) {
      DnDUtils.debugPrintln("Dragged component class is "
          + o.getClass().getName());

      System.out.println("Drop Target Point: "+location.x+" | "+location.y);
      
      
      if (o instanceof String) 
      {
          //ArbolDeTareasCelda celda = (ArbolDeTareasCelda)o;
          System.out.println("DROPEO : "+o);
          this.dropEventHandler.dropEvent((String)o,location);
      }
      
      return true;
  }


}