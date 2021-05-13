package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.edit.constraints.ConstraintEditor;
import view.diagram.elements.constraints.SetComparisonConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetComparisonConstraintPopupMenuProvider implements PopupMenuProvider {

  private final JPopupMenu menu;
  private final SetComparisonConstraint constraint;

  public SetComparisonConstraintPopupMenuProvider(SetComparisonConstraint constraint) {
    this.constraint = constraint;

    WidgetPopupMenuProvider provider = new WidgetPopupMenuProvider(constraint);

    //JMenuItem addPredicatesItem = new JMenuItem("Add predicates");
    //provider.addItem(addPredicatesItem, attachPredicatesSelectAction);

    JMenuItem removePredicatesItem = new JMenuItem("Remove predicates");
    provider.addItem(removePredicatesItem, attachPredicatesSelectAction);

    menu = provider.getPopupMenu(constraint, new Point());
  }

  ActionListener attachPredicatesSelectAction = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      new ConstraintEditor(constraint);
      JOptionPane.showMessageDialog(null, "Click on connected predicate to select it, click it again to unselect");
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }
}
