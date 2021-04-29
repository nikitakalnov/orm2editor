package view.diagram.actions;

import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.colors.OrmColorFactory;

import javax.swing.*;
import java.awt.*;

public class DefaultSelectProvider implements SelectProvider {
  @Override
  public boolean isAimingAllowed(Widget widget, Point point, boolean b) {
    return true;
  }

  @Override
  public boolean isSelectionAllowed(Widget widget, Point point, boolean b) {
    return true;
  }

  @Override
  public void select(Widget widget, Point point, boolean b) {
    widget.setBorder(BorderFactory.createLineBorder(OrmColorFactory.getPurple(), 2));
  }
}
