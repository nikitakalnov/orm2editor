package view.diagram.elements.graphics.shapes;

import view.diagram.elements.Role;

import java.awt.*;

public class BinaryPredicateShapeStrategy implements ShapeStrategy {
  @Override
  public void draw(Graphics2D g2d) {

  }

  @Override
  public Dimension getShapeSize() {
    return Role.RoleBox.getShape().getShapeSize();
  }
}
