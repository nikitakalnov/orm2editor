package view.diagram.elements.graphics.shapes;

public class ShapeStrategyFactory {
  private static final ShapeStrategy ENTITY = new EntityShapeStrategy();
  private static final ShapeStrategy VALUE = new ValueShapeStrategy();
  private static final ShapeStrategy ROLE = new RoleShapeStrategy();

  public static ShapeStrategy entity() {
    return ENTITY;
  }

  public static ShapeStrategy value() {
    return VALUE;
  }

  public static ShapeStrategy role() {
    return ROLE;
  }
}
