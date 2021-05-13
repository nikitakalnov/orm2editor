package view.diagram.elements.core;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;

public interface OrmWidget {
  Dimension getSize();
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
