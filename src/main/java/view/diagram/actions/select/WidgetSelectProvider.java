package view.diagram.actions.select;

import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;
import java.util.function.Consumer;

public class WidgetSelectProvider implements SelectProvider {

  private final Consumer<Widget> selectWidgetCallback;

  public WidgetSelectProvider(Consumer<Widget> selectWidgetCallback) {
    this.selectWidgetCallback = selectWidgetCallback;
  }

  @Override
  public boolean isAimingAllowed(Widget widget, Point point, boolean b) {
    return false;
  }

  @Override
  public boolean isSelectionAllowed(Widget widget, Point point, boolean b) {
    boolean widgetContainsPoint = false;
    Rectangle widgetBounds = widget.getBounds();
    if(widgetBounds != null)
      widgetContainsPoint = widgetBounds.contains(point);

    return widgetContainsPoint;
  }

  @Override
  public void select(Widget widget, Point point, boolean b) {
    selectWidgetCallback.accept(widget);
  }
}
