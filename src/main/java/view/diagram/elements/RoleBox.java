package view.diagram.elements;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;

public class RoleBox extends Widget implements OrmConnector {
  private final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmWidget predicate;

  public RoleBox(Scene scene, OrmWidget predicate) {
    super(scene);

    setPreferredSize(SHAPE.getShapeSize());
    this.predicate = predicate;
  }

  @Override
  protected void paintWidget() {
    SHAPE.draw(getGraphics());
  }

  @Override
  public OrmWidget getParent() {
    return predicate;
  }
}
