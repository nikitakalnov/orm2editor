package view.diagram.elements;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.vstu.nodelinkdiagram.DiagramNode;
import org.vstu.orm2diagram.model.ORM_Role;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.graph.Graph;

public class RoleBox extends Widget implements OrmConnector {
  private final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmWidget predicate;
  private final OrmElement element;

  public RoleBox(Scene scene, OrmWidget predicate, ORM_Role role) {
    super(scene);

    setPreferredSize(SHAPE.getShapeSize());
    this.predicate = predicate;
    this.element = new OrmElement(role);

    Graph graph = (Graph)scene;
    graph.addNode(element);
  }

  @Override
  protected void paintWidget() {
    SHAPE.draw(getGraphics());
  }

  @Override
  public OrmWidget getParent() {
    return predicate;
  }

  @Override
  public OrmElement getElement() {
    return element;
  }
}
