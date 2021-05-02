package view.diagram.elements.graphics.shapes;

import java.awt.*;

abstract class SoftRectangular implements ShapeStrategy {

  private final Stroke stroke;

  protected SoftRectangular(Stroke stroke) {
    this.stroke = stroke;
  }

  private static final int WIDTH = 120;
  private static final int HEIGHT = 50;
  private static final int BORDER_SIZE = 4;
  protected static final int ARC_RADIUS = WIDTH / 5;

  @Override
  public void draw(Graphics2D g2d) {
    Stroke prevStroke = g2d.getStroke();

    g2d.setStroke(this.stroke);
    g2d.setColor(Color.BLACK);
    g2d.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_RADIUS, ARC_RADIUS);

    g2d.setStroke(prevStroke);

    g2d.setColor(Color.WHITE);
    g2d.fillRoundRect(
            BORDER_SIZE / 2,
            BORDER_SIZE / 2,
            WIDTH - BORDER_SIZE,
            HEIGHT - BORDER_SIZE,
            ARC_RADIUS - BORDER_SIZE, ARC_RADIUS - BORDER_SIZE
    );

    g2d.setColor(Color.BLACK);
  }

  @Override
  public Dimension getShapeSize() {
    return new Dimension(WIDTH, HEIGHT);
  }

  public static int getArcRadius() {
    return ARC_RADIUS;
  }
}
