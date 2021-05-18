package view.diagram.actions.popup;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class PopupMenuUtils {
  public static JPopupMenu combine(JPopupMenu current, JPopupMenu additional) {
    JPopupMenu combinedMenu = new JPopupMenu();

    List<JMenuItem> currentItems = getItems(current);
    List<JMenuItem> additionalItems = getItems(additional);

    currentItems.forEach(combinedMenu::add);
    combinedMenu.addSeparator();
    additionalItems.forEach(combinedMenu::add);

    return combinedMenu;
  }

  private static List<JMenuItem> getItems(JPopupMenu menu) {
    List<JMenuItem> items = new LinkedList<>();

    Component[] components = menu.getComponents();
    for(Component item : components) {
      items.add((JMenuItem)item);
    }

    return items;
  }
}
