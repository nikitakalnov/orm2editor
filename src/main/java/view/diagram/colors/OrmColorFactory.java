package view.diagram.colors;

import java.awt.*;

public class OrmColorFactory {
  private static Color PURPLE = new Color(166, 68, 173);

  public static Color getPurple() {
    return PURPLE;
  }

  public static Color getError() {
    return Color.RED;
  }
}
