package view.diagram.actions.move;

import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmElement;
import view.diagram.graph.Graph;

import java.awt.*;

public class DiagramNodeMoveProvider implements MoveProvider {
  private final Graph graph;

  public DiagramNodeMoveProvider(Graph graph) {
    this.graph = graph;
  }

  @Override
  public void movementStarted(Widget widget) {

  }

  @Override
  public void movementFinished(Widget widget) {

  }

  @Override
  public Point getOriginalLocation(Widget widget) {
    return widget.getPreferredLocation();
  }

  @Override
  public void setNewLocation(Widget widget, Point point) {
    graph.moveNode(widget, point);
  }
}
