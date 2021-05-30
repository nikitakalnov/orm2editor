package view.diagram.colors;

import java.awt.*;

public class OrmColorFactory {
  private static Color PURPLE = new Color(166, 68, 173);
  private static Color ERROR = new Color(168, 50, 50);
  private static Color SUCCESS = new Color(50, 168, 82);
  private static Color PROBLEMS = new Color(168, 168, 50);

  public static Color getPurple() {
    return PURPLE;
  }

  public static Color getError() {
    return ERROR;
  }

  public static Color getSuccess() {
    return SUCCESS;
  }

  public static Color getProblems() {
    return PROBLEMS;
  }
}
