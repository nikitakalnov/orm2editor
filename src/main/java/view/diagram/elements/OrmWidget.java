package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.util.List;

public interface OrmWidget {
  ShapeStrategy getShape();
  void attachActions(List<WidgetAction> actions);
  void attachAction(WidgetAction action);
  OrmElement getElement();
  Widget getWidget();
}
