package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Role;
import view.diagram.elements.UnaryPredicate;
import view.diagram.elements.predicate.Predicate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RolePopupMenuProvider implements PopupMenuProvider {
  private final Role role;

  private JPopupMenu menu;
  private JMenuItem makeMandatory;
  private JMenuItem makeUnique;
  private JMenuItem editName;

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

  public RolePopupMenuProvider(Role role) {
    this.role = role;

    menu = new JPopupMenu("Role menu");

    mandatoryPrefix = getMenuItemPrefix(role.isMandatory());
    makeMandatory = new JMenuItem(mandatoryPrefix + MANDATORY);
    makeMandatory.addActionListener(makeMandatoryListener);
    menu.add(makeMandatory);

    uniquePrefix = getMenuItemPrefix(role.isUnique());
    makeUnique = new JMenuItem(uniquePrefix + UNIQUE);
    makeUnique.addActionListener(makeUniqueListener);
    menu.add(makeUnique);

    boolean canToggle = role.canToggleConstraints();
    makeUnique.setEnabled(canToggle);
    makeMandatory.setEnabled(canToggle);

    editName = new JMenuItem("Edit role name");
    editName.addActionListener(editNameListener);
    menu.add(editName);
  }

  ActionListener makeMandatoryListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(role.toggleMandatory()) {
        mandatoryPrefix = revertPrefix(mandatoryPrefix);
        makeMandatory.setText(mandatoryPrefix + MANDATORY);
      }
    }
  };

  ActionListener makeUniqueListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if(role.toggleUnique()) {
        uniquePrefix = revertPrefix(uniquePrefix);
        makeUnique.setText(uniquePrefix + UNIQUE);
      }
    }
  };

  ActionListener editNameListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      String newName = JOptionPane.showInputDialog(null, "Input new role name", role.getName());
      if(Objects.nonNull(newName))
        role.setName(newName);
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }
}
