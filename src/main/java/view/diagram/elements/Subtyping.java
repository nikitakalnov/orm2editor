package view.diagram.elements;

import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import view.diagram.colors.OrmColorFactory;

/**
 * Subtyping connection widget.
 * Usage: distinguish subtyping from other connection widgets in order to create constraints for subtyping connections.
 */
public class Subtyping extends ConnectionWidget {
  public Subtyping(Scene scene) {
    super(scene);

    super.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
    setLineColor(OrmColorFactory.getPurple());
  }

  @Override
  public void setTargetAnchorShape(AnchorShape targetAnchorShape) {
    // You can not change target anchor shape. It's being set in constructor and should be only triangle.
  }
}
