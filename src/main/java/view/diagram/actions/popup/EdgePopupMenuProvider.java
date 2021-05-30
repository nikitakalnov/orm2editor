package view.diagram.actions.popup;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.OrmEdge;
import view.diagram.graph.connect.TemporaryConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EdgePopupMenuProvider implements PopupMenuProvider {

  private final JPopupMenu menu;
  private final Graph graph;
  private final OrmEdge edge;

  public EdgePopupMenuProvider(OrmEdge connection, Graph graph) {
    this.graph = graph;
    this.edge = connection;
    menu = new JPopupMenu("Connection actions");

    JMenuItem removeEdgeItem = new JMenuItem("Remove connection");
    removeEdgeItem.addActionListener(removeEdge);

    menu.add(removeEdgeItem);
  }

  ActionListener removeEdge = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      graph.removeOrmEdge(edge);
    }
  };

  @Override
  public JPopupMenu getPopupMenu(Widget widget, Point point) {
    return menu;
  }
}
