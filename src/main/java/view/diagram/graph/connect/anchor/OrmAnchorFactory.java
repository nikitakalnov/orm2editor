package view.diagram.graph.connect.anchor;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.elements.Value;
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
      //anchor = AnchorFactory.createDirectionalAnchor(widget, AnchorFactory.DirectionalAnchorKind.HORIZONTAL);
      anchor = new RoleBoxAnchor(widget, false);
    }
    else if(widget instanceof BinaryPredicate.RolesBox)
      anchor = new RoleBoxAnchor(widget, false);

    return anchor;
  }
}
