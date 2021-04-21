package view.diagram.elements;


import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.*;
import org.netbeans.modules.visual.action.ConnectAction;
import view.diagram.elements.actions.edit.LabelEditor;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.RoleShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;

import java.util.LinkedList;
import java.util.List;

public class BinaryPredicate extends Widget implements OrmWidget {

  private final static ShapeStrategy SHAPE = new RoleShapeStrategy();
  private final OrmElement element;
  private final LinkedList<Widget> roles = new LinkedList<>();
  private final String DEFAULT_ROLE_LABEL =  "<role>";

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;

    Widget left = new ComponentWidget(scene, new SwingAbstractBox(SHAPE));
    Widget right = new ComponentWidget(scene, new SwingAbstractBox(SHAPE));

    LabelWidget label = new LabelWidget(scene, DEFAULT_ROLE_LABEL);
    label.getActions().addAction(LabelEditor.withDefaultLabel(DEFAULT_ROLE_LABEL));

    addChild(left);
    addChild(right);
    addChild(label);

    roles.addLast(left);
    roles.addLast(right);
  }

  @Override
  public ShapeStrategy getShape() {
    return SHAPE;
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
