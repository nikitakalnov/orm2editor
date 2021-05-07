package view.diagram.elements.factory;

import view.diagram.elements.core.ElementCategory;
import view.diagram.elements.core.ElementType;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class OrmElementIconFactory {
  private static final Map<ElementType, Image> ICONS = new HashMap<>();
  private static final String DEFAULT_ICON_PATH = "/icons/default_icon.png";

  private static final String EQUALITY_ICON_PATH = "/icons/equality.png";
  private static final String SUBSET_ICON_PATH = "/icons/subset.png";
  private static final String XOR_ICON_PATH = "/icons/xor.png";
  private static final String EXCLUSION_ICON_PATH = "/icons/exclusion.png";

  private static final String ENTITY_ICON_PATH = "/icons/entity.png";

  static {
    ICONS.put(ElementType.EQUALITY_CONSTRAINT, getIconForPath(EQUALITY_ICON_PATH));
    ICONS.put(ElementType.ENTITY, getIconForPath(ENTITY_ICON_PATH));
    ICONS.put(ElementType.EXCLUSION_CONSTRAINT, getIconForPath(EXCLUSION_ICON_PATH));
    ICONS.put(ElementType.SUBSET_CONSTRAINT, getIconForPath(SUBSET_ICON_PATH));
    ICONS.put(ElementType.XOR_CONSTRAINT, getIconForPath(XOR_ICON_PATH));
  }

  private static Image getIconForPath(String path) {
    return new ImageIcon(OrmElementIconFactory.class.getResource(path)).getImage();
  }

  public static Image getForType(ElementType type) {
    Image icon = ICONS.get(type);
    return icon != null ? icon : getIconForPath(DEFAULT_ICON_PATH);
  }

  private static java.util.List<Image> getElements(ElementCategory category) {
    java.util.List<Image> icons = new LinkedList<>();

    for(Map.Entry<ElementType, Image> elementIcon : ICONS.entrySet()) {
      if(elementIcon.getKey().getCategory() == category)
        icons.add(elementIcon.getValue());
    }

    return icons;
  }
}
