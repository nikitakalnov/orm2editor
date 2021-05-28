package view.core.actions.dnd;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;

public class OrmElementTransferHandler extends TransferHandler {
  private static final TransferHandler TRANSFER_HANDLER = new OrmElementTransferHandler();

  @Override
  public int getSourceActions(JComponent c) {
    return TransferHandler.COPY_OR_MOVE;
  }

  @Override
  protected Transferable createTransferable(JComponent c) {

    if (c instanceof OrmTransferableIcon) {
      OrmTransferableIcon icon = (OrmTransferableIcon)c;
      return new OrmTransferable(icon.getElement());
    } else
      throw new IllegalArgumentException("Can not transfer such a widget");
  }

  public static TransferHandler getTransferHandler() {
    return TRANSFER_HANDLER;
  }
}
