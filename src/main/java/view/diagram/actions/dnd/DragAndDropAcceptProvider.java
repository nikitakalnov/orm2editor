package view.diagram.actions.dnd;

import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import view.core.actions.dnd.OrmTransferable;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DragAndDropAcceptProvider implements AcceptProvider {

  private final GraphScene<OrmElement, String> scene;

  public DragAndDropAcceptProvider(GraphScene<OrmElement, String> scene) {
    this.scene = scene;
  }

  @Override
  public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
    boolean isOrmElement = false;
    for(DataFlavor f : transferable.getTransferDataFlavors())
      isOrmElement = isOrmElement || f.equals(OrmTransferable.ORM_FLAVOR);

    return isOrmElement ? ConnectorState.ACCEPT : ConnectorState.REJECT;
  }

  @Override
  public void accept(Widget widget, Point point, Transferable transferable) {
    try {
      Object data = transferable.getTransferData(OrmTransferable.ORM_FLAVOR);
      OrmElement element = (OrmElement)data;
      Widget elementWidget = scene.addNode(element);
      elementWidget.setPreferredLocation(widget.convertLocalToScene(point));
    } catch (UnsupportedFlavorException | IOException e) {
      e.printStackTrace();
    }

    scene.repaint();
    scene.revalidate();
  }
}
