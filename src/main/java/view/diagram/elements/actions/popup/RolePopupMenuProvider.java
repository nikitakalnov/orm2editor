package view.diagram.elements.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Role;
import view.diagram.elements.core.OrmElement;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.anchor.shape.OrmAnchorShapeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RolePopupMenuProvider implements PopupMenuProvider {
  private final Role roleWidget;

  private JPopupMenu menu;
  private JMenuItem makeMandatory;
  private JMenuItem makeUnique;

  public RolePopupMenuProvider(Role roleWidget) {
    this.roleWidget = roleWidget;

    menu = new JPopupMenu("Role menu");

    makeMandatory = new JMenuItem("Make mandatory");
    makeMandatory.addActionListener(makeMandatoryListener);
    menu.add(makeMandatory);

    makeUnique = new JMenuItem("Make unique");
    makeUnique.addActionListener(makeUniqueListener);
    menu.add(makeUnique);
  }

  ActionListener makeMandatoryListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      roleWidget.toggleMandatory();
    }
  };

  ActionListener makeUniqueListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      roleWidget.toggleUnique();
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }
}
