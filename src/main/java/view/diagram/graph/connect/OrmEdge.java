package view.diagram.graph.connect;

import org.netbeans.api.visual.widget.ConnectionWidget;
import org.vstu.nodelinkdiagram.DiagramEdge;

public class OrmEdge {
  private final DiagramEdge edge;
  private final ConnectionWidget widget;

  public OrmEdge(DiagramEdge edge, ConnectionWidget widget) {
    this.edge = edge;
    this.widget = widget;
  }

  public DiagramEdge getEdge() {
    return edge;
  }

  public ConnectionWidget getWidget() {
    return widget;
  }
}
