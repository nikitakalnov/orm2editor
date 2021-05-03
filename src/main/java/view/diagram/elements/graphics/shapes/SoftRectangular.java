package view.diagram.elements.graphics.shapes;

import java.awt.*;

abstract class SoftRectangular implements ShapeStrategy {

  private final Stroke stroke;

  protected SoftRectangular(Stroke stroke) {
    this.stroke = stroke;
  }

  private int width = 120;
  private static final int HEIGHT = 52;
  private static final int BORDER_SIZE = 4;
  protected static final int ARC_RADIUS =  26;
  protected static final int SIDE_PADDING = 25;

  @Override
  public void draw(Graphics2D g2d) {
    Stroke prevStroke = g2d.getStroke();

    g2d.setStroke(this.stroke);
    g2d.setColor(Color.BLACK);
    g2d.fillRoundRect(0, 0, width, getHeight(), ARC_RADIUS, ARC_RADIUS);

    g2d.setStroke(prevStroke);

    g2d.setColor(Color.WHITE);
    g2d.fillRoundRect(
            BORDER_SIZE / 2,
            BORDER_SIZE / 2,
            width - BORDER_SIZE,
            getHeight() - BORDER_SIZE,
            ARC_RADIUS - BORDER_SIZE, ARC_RADIUS - BORDER_SIZE
    );

    g2d.setColor(Color.BLACK);
  }

  @Override
  public Dimension getShapeSize() {
    return new Dimension(width, getHeight());
  }

  public static int getArcRadius() {
    return ARC_RADIUS;
  }

  protected int getHeight() {
    return HEIGHT;
  }

  public void setWidth(int contentWidth) {
    this.width = contentWidth + SIDE_PADDING * 2;
  }
}
