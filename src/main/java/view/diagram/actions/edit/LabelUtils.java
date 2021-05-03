package view.diagram.actions.edit;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LabelUtils {
  private static final Toolkit toolkit = Toolkit.getDefaultToolkit();

  public static int getLabelWidth(Graphics g, Font font, String label) {
    Rectangle2D labelBounds = toolkit.getFontMetrics(font).getStringBounds(label, g);
    return (int)Math.ceil(labelBounds.getWidth());
  }
}
