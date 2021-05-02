package view.diagram.elements.palette;

import org.netbeans.api.visual.widget.general.IconNodeWidget;
import view.diagram.elements.core.ElementType;

import javax.swing.*;
import java.awt.*;

public class ElementsPalette extends JPanel {
  public ElementsPalette() {
    JLabel label = new JLabel("ORM Elements");
    label.setFont(new Font(null, Font.BOLD, 20));

    add(label);
    add(initElements());
  }

  private JPanel initElements() {
    ElementType[] elements = ElementType.values();
    JPanel elementsPanel = new JPanel();

    for(ElementType type : elements) {
      // TODO: get icon for element
      add(elementsPanel);
    }

    return elementsPanel;
  }
}
