package view.diagram.colors;

import org.vstu.nodelinkdiagram.statuses.ValidateStatus;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OrmColorFactory {
  private static Color PURPLE = new Color(166, 68, 173);
  private static Color ERROR = new Color(242, 62, 62);
  private static Color SUCCESS = new Color(50, 168, 82);
  private static Color PROBLEMS = new Color(168, 168, 50);

  private final static Map<ValidateStatus, Color> STATUS_COLORS = new HashMap<>();
  static {
    STATUS_COLORS.put(ValidateStatus.Invalid, OrmColorFactory.getError());
    STATUS_COLORS.put(ValidateStatus.Acceptable, OrmColorFactory.getSuccess());
    STATUS_COLORS.put(ValidateStatus.Intermediate, OrmColorFactory.getProblems());
    STATUS_COLORS.put(ValidateStatus.Incoherence, Color.BLUE);
    STATUS_COLORS.put(ValidateStatus.Unknown, Color.GRAY);
  }

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

  public static Color getValidateStatusColor(ValidateStatus status) {
    return STATUS_COLORS.get(status);
  }
}
