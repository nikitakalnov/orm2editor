package view.diagram.graph.connect.anchor;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Subtyping;

import java.awt.*;
import java.awt.geom.Point2D;

public class SubtypingAnchor extends Anchor {
  protected SubtypingAnchor(Widget relatedWidget) {
    super(relatedWidget);
  }

  @Override
  public Result compute(Entry entry) {
    ConnectionWidget connection = (ConnectionWidget) getRelatedWidget();

    Point target = connection.getFirstControlPoint();
    Point source = connection.getLastControlPoint();

    Point midpoint = new Point((source.x + target.x) / 2, (source.y + target.y) / 2);

    return new Result(midpoint, Anchor.DIRECTION_ANY);
  }
}
