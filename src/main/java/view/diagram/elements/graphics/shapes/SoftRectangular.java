package view.diagram.elements.graphics.shapes;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public abstract class SoftRectangular implements ShapeStrategy {

  private final Stroke stroke;

  protected SoftRectangular(Stroke stroke) {
    this.stroke = stroke;
  }

  private int width = 120;
  private static final int HEIGHT = 52;
  protected static final int ARC_RADIUS =  26;
  protected static final int SIDE_PADDING = 25;

  @Override
  public void draw(Graphics2D g2d) {
    Stroke prevStroke = g2d.getStroke();

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setStroke(this.stroke);
    g2d.setColor(Color.BLACK);
    g2d.draw(
            new RoundRectangle2D.Double(2, 2, width - 4, getHeight() - 4, ARC_RADIUS, ARC_RADIUS)
    );

    g2d.setStroke(prevStroke);

    g2d.setColor(Color.WHITE);
    // Somehow Java draws RoundRectangle border badly when drawing from x = 0 and y = 0
    g2d.fillRoundRect(
            4,
            4,
            width - 7,
            getHeight() - 7,
            ARC_RADIUS, ARC_RADIUS
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
