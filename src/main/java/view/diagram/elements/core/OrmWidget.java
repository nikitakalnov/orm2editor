package view.diagram.elements.core;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.ConnectAction;

import java.awt.*;
import java.util.List;

public interface OrmWidget {
  Dimension getSize();
  default void attachConnectAction(WidgetAction action) {
    attachAction(action);
  }
  void attachAction(WidgetAction action);
  OrmElement getElement();
  Widget getWidget();
}
