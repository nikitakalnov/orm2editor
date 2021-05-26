package view.core.actions.dnd;

import org.vstu.nodelinkdiagram.DiagramNode;
import view.diagram.elements.core.OrmElement;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class OrmTransferable implements Transferable {
  public static final DataFlavor ORM_FLAVOR = new DataFlavor(DiagramNode.class, "ORM Element");
  private static final DataFlavor[] FLAVORS = new DataFlavor[]{ ORM_FLAVOR };

  private final DiagramNode element;

  public OrmTransferable(DiagramNode element) {
    this.element = element;
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return FLAVORS;
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    boolean isSupported = false;
    for(DataFlavor f : FLAVORS)
      isSupported = isSupported || f.equals(flavor);

    return isSupported;
  }

  @Override
  public Object getTransferData(DataFlavor flavor) {
    return element;
  }
}
