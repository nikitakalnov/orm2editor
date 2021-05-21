package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.edit.constraints.ConstraintEditor;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetComparisonConstraintPopupMenuProvider implements PopupMenuProvider {

  private final JPopupMenu menu;
  private final JMenuItem removePredicatesItem;
  private final SetComparisonConstraint constraint;

  public SetComparisonConstraintPopupMenuProvider(SetComparisonConstraint constraint) {
    this.constraint = constraint;

    WidgetPopupMenuProvider provider = new WidgetPopupMenuProvider(constraint);

    //JMenuItem addPredicatesItem = new JMenuItem("Add predicates");
    //provider.addItem(addPredicatesItem, attachPredicatesSelectAction);

    removePredicatesItem = new JMenuItem("Remove predicates");
    provider.addItem(removePredicatesItem, attachPredicatesSelectAction);

    menu = provider.getPopupMenu(constraint, new Point());
  }

  ActionListener attachPredicatesSelectAction = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      ConstraintEditor constraintEditor = new ConstraintEditor(constraint);
      constraintEditor.start();
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    Graph graph = (Graph)constraint.getScene();
    if(graph.findNodeEdges(constraint.getElement(), true, true).isEmpty())
      removePredicatesItem.setEnabled(false);
    else
      removePredicatesItem.setEnabled(true);

    return menu;
  }
}
