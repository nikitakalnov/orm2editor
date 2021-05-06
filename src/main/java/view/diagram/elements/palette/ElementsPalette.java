package view.diagram.elements.palette;

import view.core.actions.dnd.OrmTransferableIcon;
import view.diagram.elements.core.ElementType;

import javax.swing.*;
import java.awt.*;

public class ElementsPalette extends JPanel {

  private static final Color BACKGROUND = new Color(219, 207, 217);

  public ElementsPalette() {
    setBackground(BACKGROUND);
    setLayout(new FlowLayout(FlowLayout.LEFT, 20, 12));

    initElements();
  }

  private void initElements() {
    JLabel paletteName = new JLabel("ORM Elements");
    paletteName.setFont(new Font(null, Font.BOLD, 20));
    add(paletteName);

    ElementType[] elements = ElementType.values();
    for(ElementType e : elements) {
      add(new OrmTransferableIcon(() -> e));
    }

    JTextField field = new JTextField("Something lol");

    add(field);
  }

}
