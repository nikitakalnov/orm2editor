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

import java.awt.*;
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
  public Dimension getSize() {
    return SHAPE.getShapeSize();
  }

  @Override
  public void attachAction(WidgetAction action) {
    getActions().addAction(action);
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
