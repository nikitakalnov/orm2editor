package view.core.actions.dnd;

import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.factory.OrmElementIconFactory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrmTransferableIcon extends JLabel {
  private final ElementType type;

  public OrmTransferableIcon(ElementType type) {
    super(new ImageIcon(OrmElementIconFactory.getForType(type)));

    this.type = type;
    setToolTipText(type.toString().replace('_', ' ').toLowerCase());

    setTransferHandler(OrmElementTransferHandler.getTransferHandler());
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        JComponent component = (JComponent)e.getSource();
        getTransferHandler().exportAsDrag(component, e, TransferHandler.COPY);
      }
    });
  }

  public ElementType getElement() {
    return this.type;
  }
}
