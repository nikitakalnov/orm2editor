package view.core.actions.dnd;

import view.diagram.elements.core.OrmElement;
import view.diagram.elements.factory.OrmElementIconFactory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrmTransferableIcon extends JLabel {
  private final OrmElement element;

  public OrmTransferableIcon(OrmElement element) {
    super(new ImageIcon(OrmElementIconFactory.getForType(element.getType())));

    this.element = element;
    setToolTipText(element.getType().toString().replace('_', ' ').toLowerCase());

    setTransferHandler(OrmElementTransferHandler.getTransferHandler());
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        JComponent component = (JComponent)e.getSource();
        getTransferHandler().exportAsDrag(component, e, TransferHandler.COPY);
      }
    });
  }

  public OrmElement getElement() {
    return this.element;
  }
}
