package view.diagram.elements.graphics.shapes;

import java.awt.*;

public interface ShapeStrategy {
  void draw(Graphics2D g2d);
  Dimension getShapeSize();
}
