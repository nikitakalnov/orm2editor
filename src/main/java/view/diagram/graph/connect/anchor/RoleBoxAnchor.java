package view.diagram.graph.connect.anchor;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.util.GeomUtil;

import java.awt.*;

public class RoleBoxAnchor extends Anchor {
  private final boolean includeBorders;

  public RoleBoxAnchor(Widget relatedWidget, boolean includeBorders) {
    super(relatedWidget);

    this.includeBorders = includeBorders;
  }

  @Override
  public Result compute(Entry entry) {
    Point oppositeLocation = this.getOppositeSceneLocation(entry);
    Widget widget = this.getRelatedWidget();

    Rectangle bounds = widget.convertLocalToScene(widget.getBounds());
    if (!this.includeBorders) {
      Insets insets = widget.getBorder().getInsets();
      bounds.x += insets.left;
      bounds.y += insets.top;
      bounds.width -= insets.left + insets.right;
      bounds.height -= insets.top + insets.bottom;
    }

    Point center = GeomUtil.center(bounds);

    int topLeft = bounds.x;
    int topRight = bounds.x + bounds.width;

    if(oppositeLocation.x < topLeft) {
      return new Result(new Point(topLeft, center.y), Direction.LEFT);
    }
    else if(oppositeLocation.x > topRight)
      return new Result(new Point(topRight, center.y), Direction.RIGHT);
    else {
      if(oppositeLocation.y < bounds.y)
        return new Result(new Point(center.x, bounds.y), Direction.TOP);
      else
        return new Result(new Point(center.x, bounds.y + bounds.height), Direction.BOTTOM);
    }
  }
}
