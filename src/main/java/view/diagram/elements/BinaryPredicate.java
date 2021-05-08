package view.diagram.elements;


import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.modules.visual.action.ConnectAction;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BinaryPredicate extends Widget implements OrmWidget {

  private final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmElement element;
  private final LinkedList<Role.RoleBox> roles = new LinkedList<>();
  private final static String DEFAULT_ROLE_LABEL =  "<role>";
  private final RolesBox rolesBox;
  private final Widget uniquenessConstraintsBox;

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, -4));

    rolesBox = new RolesBox(scene, this, roles);
    uniquenessConstraintsBox = new Widget(scene);
    uniquenessConstraintsBox.setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 0));
    uniquenessConstraintsBox.addChild(new Role.UniquenessConstraint(scene, roles.getFirst()));
    uniquenessConstraintsBox.addChild(new Role.UniquenessConstraint(scene, roles.getLast()));

    addChild(uniquenessConstraintsBox);
    addChild(rolesBox);

    LabelWidget label = new LabelWidget(scene, DEFAULT_ROLE_LABEL);
    label.getActions().addAction(LabelEditor.withDefaultLabel(DEFAULT_ROLE_LABEL));
    label.addDependency(rolesBox);

    addChild(label);
  }

  public void setUniquenessConstraint(Role.RoleBox role, boolean uniquenessEnabled) {
    Role.UniquenessConstraint uniquenessConstraint = (Role.UniquenessConstraint) uniquenessConstraintsBox
            .getChildren()
            .stream()
            .filter(u -> ((Role.UniquenessConstraint)u).getRole().equals(role))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("There is no uniqueness constraint box for " + role.toString()));

    uniquenessConstraint.setUniquenessEnabled(uniquenessEnabled);
    uniquenessConstraint.repaint();
  }

  public static class RolesBox extends Widget implements Dependency, OrmConnector {

    private final OrmWidget parent;

    public RolesBox(Scene scene, OrmWidget parent, List<Role.RoleBox> roleBoxes) {
      super(scene);
      this.parent = parent;

      setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, -2));

      Role.RoleBox left = new Role.RoleBox(scene, parent);
      Role.RoleBox right = new Role.RoleBox(scene, parent);

      roleBoxes.add(left);
      roleBoxes.add(right);

      addChild(left);
      addChild(right);

      setBorder(BorderFactory.createEmptyBorder(10));
    }

    @Override
    public void revalidateDependency() {
      this.revalidate();
    }

    @Override
    public OrmWidget getParent() {
      return parent;
    }
  }

  @Override
  public Dimension getSize() {
    Dimension roleSize = SHAPE.getShapeSize();
    return new Dimension(roleSize.width * 2, roleSize.height * 2);
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

  public Widget getRolesBox() {
    return rolesBox;
  }
}
