package view.diagram.elements.core;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

public interface OrmWidget {
  default void attachConnectAction(WidgetAction action) {
    attachAction(action);
  }
  void attachAction(WidgetAction action);
  OrmElement getElement();
  Widget getWidget();
}
