package view.diagram.elements.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RolePopupMenuProvider implements PopupMenuProvider {
  private final Role roleWidget;

  private JPopupMenu menu;
  private JMenuItem makeMandatory;
  private JMenuItem makeUnique;

  private final static String MAKE_PREFIX = "Make ";
  private final static String REMOVE_PREFIX = "Remove ";
  private final static String MANDATORY = "mandatory";
  private final static String UNIQUE = "unique";

  private String mandatoryPrefix;
  private String uniquePrefix;

  protected String getMenuItemPrefix(boolean status) {
    if(status)
      return REMOVE_PREFIX;
    else
      return MAKE_PREFIX;
  }

  protected String revertPrefix(String prefix) {
    switch(prefix) {
      case MAKE_PREFIX:
        return REMOVE_PREFIX;
      case REMOVE_PREFIX:
        return MAKE_PREFIX;
      default:
        throw new IllegalArgumentException("No such menu item prefix");
    }
  }

  public RolePopupMenuProvider(Role roleWidget) {
    this.roleWidget = roleWidget;

    menu = new JPopupMenu("Role menu");

    mandatoryPrefix = getMenuItemPrefix(roleWidget.isMandatory());
    makeMandatory = new JMenuItem(mandatoryPrefix + MANDATORY);
    makeMandatory.addActionListener(makeMandatoryListener);
    menu.add(makeMandatory);

    uniquePrefix = getMenuItemPrefix(roleWidget.isUnique());
    makeUnique = new JMenuItem(uniquePrefix + UNIQUE);
    makeUnique.addActionListener(makeUniqueListener);
    menu.add(makeUnique);
  }

  ActionListener makeMandatoryListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      roleWidget.toggleMandatory();
      mandatoryPrefix = revertPrefix(mandatoryPrefix);
      makeMandatory.setText(mandatoryPrefix + MANDATORY);
    }
  };

  ActionListener makeUniqueListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      roleWidget.toggleUnique();
      uniquePrefix = revertPrefix(uniquePrefix);
      makeUnique.setText(uniquePrefix + UNIQUE);
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }
}
