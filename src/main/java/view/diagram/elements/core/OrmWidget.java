package view.diagram.elements.core;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.awt.*;

public interface OrmWidget {
  default void attachConnectAction(WidgetAction action) {
    attachAction(action);
  }
  void attachAction(WidgetAction action);
  default void removeAction(WidgetAction action) {
    getWidget().getActions().removeAction(action);
  }
  OrmElement getElement();
  Widget getWidget();
}
