package view.diagram.elements.graphics.shapes;

import java.awt.*;

public class RoleShapeStrategy implements ShapeStrategy {
  private static int WIDTH = 50;
  private static int HEIGHT = 30;

  private static int STROKE_WIDTH = 2;

  @Override
  public void draw(Graphics2D g2d) {
    Color prevColor = g2d.getColor();

    g2d.setColor(Color.BLACK);
    g2d.fillRect(0, 0, WIDTH, HEIGHT);

    g2d.setColor(Color.WHITE);
    g2d.fillRect(STROKE_WIDTH, STROKE_WIDTH, WIDTH - STROKE_WIDTH * 2, HEIGHT - STROKE_WIDTH * 2);

    g2d.setColor(prevColor);
  }

  @Override
  public Dimension getShapeSize() {
    return new Dimension(WIDTH, HEIGHT);
  }
}
