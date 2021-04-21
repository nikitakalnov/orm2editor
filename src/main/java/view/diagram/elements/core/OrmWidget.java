package view.diagram.elements.core;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;
import java.util.List;

public interface OrmWidget {
  Dimension getSize();
  void attachActions(List<WidgetAction> actions);
  void attachAction(WidgetAction action);
  OrmElement getElement();
  Widget getWidget();
}
