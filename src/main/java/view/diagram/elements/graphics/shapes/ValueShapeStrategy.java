package view.diagram.elements.graphics.shapes;

import java.awt.*;

public class ValueShapeStrategy extends SoftRectangular {

  public ValueShapeStrategy() {
    super(new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f, new float[]{10.0f}, 0.0f));
  }

  @Override
  public void draw(Graphics2D g2d) {
    super.draw(g2d);
  }

  @Override
  public Dimension getShapeSize() {
    return super.getShapeSize();
  }
}
