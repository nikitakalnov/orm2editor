package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WidgetPopupMenuProvider implements PopupMenuProvider {
  private final OrmWidget widget;

  private final JPopupMenu menu = new JPopupMenu("Widget actions");
  private final JMenuItem deleteWidgetItem = new JMenuItem("Remove widget");

  public WidgetPopupMenuProvider(OrmWidget widget) {
    this.widget = widget;

    addItem(deleteWidgetItem, deleteWidget);
  }

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }

  ActionListener deleteWidget = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

      Graph ormGraph = (Graph)(widget.getWidget().getScene());
      ormGraph.removeNodeWithEdges(widget.getElement());
    }
  };

  protected void addItem(JMenuItem item, ActionListener listener) {
    item.addActionListener(listener);

    menu.add(item);
  }
}
