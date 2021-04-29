package view.diagram.graph.connect.anchor.shape;

import org.netbeans.api.visual.anchor.AnchorShape;
import view.diagram.colors.OrmColorFactory;

import java.awt.*;

class MandatoryAnchorShape implements AnchorShape {
  private static int RADIUS = 10;

  @Override
  public boolean isLineOriented() {
    return false;
  }

  @Override
  public int getRadius() {
    return RADIUS;
  }

  @Override
  public double getCutDistance() {
    return RADIUS / 2.0;
  }

  @Override
  public void paint(Graphics2D graphics2D, boolean source) {
    Color prevColor = graphics2D.getColor();

    graphics2D.setColor(OrmColorFactory.getPurple());
    graphics2D.fillOval(- RADIUS / 2, - RADIUS / 2, RADIUS, RADIUS);

    graphics2D.setColor(prevColor);
  }
}
