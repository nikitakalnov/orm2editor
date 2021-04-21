package view.diagram.elements;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.RoleShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategy;

import java.util.List;

public class Role extends Widget implements OrmWidget {

  private final static String DEFAULT_ROLE = "<role>";
  private final static WidgetAction EDIT_ROLE_ACTION = LabelEditor.withDefaultLabel(DEFAULT_ROLE);

  private final Widget roleBox;
  private final LabelWidget roleLabel;
  private final OrmElement element;

  public Role(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    LookFeel lookFeel = scene.getLookFeel();
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, lookFeel.getMargin()));

    this.roleBox = new RoleBox(scene);

    this.roleLabel = new LabelWidget(scene, DEFAULT_ROLE);
    roleLabel.setAlignment(LabelWidget.Alignment.CENTER);
    this.roleLabel.getActions().addAction(EDIT_ROLE_ACTION);

    addChild(this.roleBox);
    addChild(this.roleLabel);

    roleLabel.addDependency((Dependency)this.roleBox);
  }

  public static class RoleBox extends ComponentWidget implements Dependency {
    final static ShapeStrategy SHAPE = new RoleShapeStrategy();

    public RoleBox(Scene scene) {
      super(scene, new SwingAbstractBox(SHAPE));
    }

    @Override
    public void revalidateDependency() {
      this.revalidate();
    }

    public static ShapeStrategy getShape() {
      return SHAPE;
    }
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public ShapeStrategy getShape() {
    return RoleBox.getShape();
  }

  @Override
  public void attachActions(List<WidgetAction> actions) {
    WidgetAction.Chain widgetActions = this.roleBox.getActions();

    for(WidgetAction action : actions) {
      widgetActions.addAction(action);
    }
  }

  @Override
  public void attachAction(WidgetAction action) {
    getActions().addAction(action);
  }

  @Override
  public Widget getWidget() {
    return this;
  }
}
