package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ValueShapeStrategy;

import java.util.List;

public class Value extends ComponentWidget implements OrmWidget {
  private static final String DEFAULT_VALUE = "<value>";
  private static final ShapeStrategy SHAPE = new ValueShapeStrategy();

  private final OrmElement element;

  public Value(OrmElement element, Scene scene) {
    super(scene, new SwingAbstractBox(SHAPE));
    this.element = element;
  }

  @Override
  public ShapeStrategy getShape() {
    return SHAPE;
  }

  @Override
  public void attachActions(List<WidgetAction> actions) {
    for(WidgetAction a : actions) {

    }

  }

  @Override
  public void attachAction(WidgetAction action) {

  }

  @Override
  public OrmElement getElement() {
    return null;
  }

  @Override
  public Widget getWidget() {
    return null;
  }
}
