package view.diagram.elements.palette;

import view.core.actions.dnd.OrmTransferableIcon;
import view.diagram.elements.core.ElementCategory;
import view.diagram.elements.core.ElementType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    java.util.List<ElementType> elements = new ArrayList<>();
    elements.addAll(ElementType.getInCategory(ElementCategory.OBJECT));
    elements.addAll(ElementType.getInCategory(ElementCategory.SET_COMPARISON_CONSTRAINT));

    for(ElementType e : elements) {
      add(new OrmTransferableIcon(() -> e));
    }
  }

}
