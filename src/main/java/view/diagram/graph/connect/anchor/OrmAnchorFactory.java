package view.diagram.graph.connect.anchor;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.anchor.CircularAnchor;
import view.diagram.elements.*;
import view.diagram.elements.constraints.RoundConstraint;
import view.diagram.elements.graphics.shapes.EntityShapeStrategy;
import view.diagram.elements.graphics.shapes.ValueShapeStrategy;

public class OrmAnchorFactory {
  public static Anchor forWidget(Widget widget) {
    Anchor anchor = null;

    if(widget instanceof Entity)
      anchor = new SoftRectangularAnchor(widget, EntityShapeStrategy.getArcRadius());
    else if(widget instanceof Value)
      anchor = new SoftRectangularAnchor(widget, ValueShapeStrategy.getArcRadius());
    else if(widget instanceof Role.RoleBox) {
      anchor = new RoleBoxAnchor(widget, false);
    }
    else if(widget instanceof BinaryPredicate.RolesBox)
      anchor = new RoleBoxAnchor(widget, false);
    else if(widget instanceof Subtyping)
      anchor = new SubtypingAnchor(widget);
    else if(widget instanceof RoundConstraint) {
      RoundConstraint constraintWidget = (RoundConstraint)widget;
      anchor = new CircularAnchor(constraintWidget, constraintWidget.getIcon().getWidth(null));
    }

    else
      throw new IllegalArgumentException("No anchor for " + widget.toString());

    return anchor;
  }
}
