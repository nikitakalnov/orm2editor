package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.predicate.Predicate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PredicatePopupMenuProvider {

  protected final JPopupMenu menu;
  protected final JMenuItem makeUnique;
  private final Predicate predicate;

  private final static String MAKE_PREFIX = "Make ";
  private final static String REMOVE_PREFIX = "Remove ";
  private final static String UNIQUE = "unique";

  protected static String getMenuItemPrefix(boolean status) {
    if(status)
      return REMOVE_PREFIX;
    else
      return MAKE_PREFIX;
  }

  protected static String revertPrefix(String prefix) {
    switch(prefix) {
      case MAKE_PREFIX:
        return REMOVE_PREFIX;
      case REMOVE_PREFIX:
        return MAKE_PREFIX;
      default:
        throw new IllegalArgumentException("No such menu item prefix");
    }
  }

  private String uniquePrefix;

  public PredicatePopupMenuProvider(Predicate predicate) {
    this.predicate = predicate;
    this.menu = new JPopupMenu("Predicate menu");

    uniquePrefix = getMenuItemPrefix(predicate.isUnique());
    makeUnique = new JMenuItem(uniquePrefix + UNIQUE);
    makeUnique.addActionListener(makeUniqueListener);
    menu.add(makeUnique);
  }

  ActionListener makeUniqueListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(predicate.toggleUnique()) {
        uniquePrefix = revertPrefix(uniquePrefix);
        makeUnique.setText(uniquePrefix + UNIQUE);
      }
    }
  };

  public java.util.List<JMenuItem> getItems() {
    return Arrays.asList(makeUnique);
  }
}
