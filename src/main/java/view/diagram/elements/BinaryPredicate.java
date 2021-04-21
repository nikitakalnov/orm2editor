package view.diagram.elements;


import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.modules.visual.action.ConnectAction;
import view.diagram.elements.actions.edit.LabelEditor;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BinaryPredicate extends Widget implements OrmWidget {

  private final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmElement element;
  private final LinkedList<Widget> roles = new LinkedList<>();
  private final String DEFAULT_ROLE_LABEL =  "<role>";

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;

    LookFeel lookFeel = scene.getLookFeel();
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, lookFeel.getMargin()));

    RolesBox box = new RolesBox(scene, roles);

    addChild(box);

    LabelWidget label = new LabelWidget(scene, DEFAULT_ROLE_LABEL);
    label.getActions().addAction(LabelEditor.withDefaultLabel(DEFAULT_ROLE_LABEL));
    label.addDependency(box);

    addChild(label);
  }

  public static class RolesBox extends Widget implements Dependency {

    public RolesBox(Scene scene, List<Widget> roleBoxes) {
      super(scene);
      setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, -2));

      Widget left = new Role.RoleBox(scene);
      Widget right = new Role.RoleBox(scene);

      roleBoxes.add(left);
      roleBoxes.add(right);

      addChild(left);
      addChild(right);
    }

    @Override
    public void revalidateDependency() {

    }
  }

  @Override
  public Dimension getSize() {
    Dimension roleSize = SHAPE.getShapeSize();
    return new Dimension(roleSize.width * 2, roleSize.height * 2);
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
