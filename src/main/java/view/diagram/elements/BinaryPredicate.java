package view.diagram.elements;


import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.ConnectAction;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.RoleShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.util.LinkedList;
import java.util.List;

public class BinaryPredicate extends Widget implements OrmWidget {

  private final static ShapeStrategy SHAPE = new RoleShapeStrategy();
  private final OrmElement element;
  private final LinkedList<Widget> roles = new LinkedList<>();

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;

    Widget left = new ComponentWidget(scene, new SwingAbstractBox(SHAPE));
    Widget right = new ComponentWidget(scene, new SwingAbstractBox(SHAPE));

    addChild(left);
    addChild(right);

    roles.addLast(left);
    roles.addLast(right);
  }

  @Override
  public ShapeStrategy getShape() {
    return null;
  }

  @Override
  public void attachActions(List<WidgetAction> actions) {
    for(WidgetAction a : actions) {
      attachAction(a);
    }
  }

  @Override
  public void attachAction(WidgetAction action) {
    this.getActions().addAction(action);

    if(action instanceof ConnectAction) {
      this.roles.peekFirst().getActions().addAction(action);
      this.roles.peekLast().getActions().addAction(action);
    }
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public Widget getWidget() {
    return this;
  }
}
