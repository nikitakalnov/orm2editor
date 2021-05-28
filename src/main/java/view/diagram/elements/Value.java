package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.SoftRectangular;
import view.diagram.elements.graphics.shapes.ValueShapeStrategy;

import java.awt.*;
import java.util.List;

public class Value extends ObjectType {
  public Value(OrmElement element, Scene scene) {
    super(element, scene, new ValueShapeStrategy());
  }
}
