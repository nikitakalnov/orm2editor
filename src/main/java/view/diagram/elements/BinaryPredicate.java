package view.diagram.elements;


import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.modules.visual.action.ConnectAction;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.actions.popup.BinaryPredicateMenuProvider;
import view.diagram.actions.popup.PredicatePopupMenuProvider;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.elements.predicate.Predicate;
import view.diagram.elements.predicate.UniquenessConstraint;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryPredicate extends Widget implements OrmWidget, Predicate {

  private final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmElement element;
  private final LinkedList<Role> roles = new LinkedList<>();
  private final static String DEFAULT_ROLE_LABEL =  "<role>";
  private final RolesBox rolesBox;
  private boolean unique = false;
  private final UniquenessConstraint uniquenessConstraint;
  private final Widget uniquenessConstraintsBox;
  private final PropertyChangeSupport pcs;
  private final Map<ActionTarget, Set<Widget>> widgets = new HashMap<>();

  private enum ActionTarget {
    ALL,
    EXTERNAL,
    INTERNAL
  }

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    this.pcs = new PropertyChangeSupport(this);
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 2));

    rolesBox = new RolesBox(scene, this, roles);
    uniquenessConstraint = new UniquenessConstraint(scene, this);

    uniquenessConstraintsBox = new Widget(scene);
    uniquenessConstraintsBox.setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 0));

    uniquenessConstraintsBox.addChild(new UniquenessConstraint(scene, roles.getFirst()));
    uniquenessConstraintsBox.addChild(new UniquenessConstraint(scene, roles.getLast()));

    addChild(uniquenessConstraint);
    addChild(uniquenessConstraintsBox);
    addChild(rolesBox);

    LabelWidget label = new LabelWidget(scene, DEFAULT_ROLE_LABEL);
    label.getActions().addAction(LabelEditor.withDefaultLabel(DEFAULT_ROLE_LABEL));
    label.addDependency(rolesBox);

    addChild(label);
    getActions().addAction(ActionFactory.createPopupMenuAction(new BinaryPredicateMenuProvider(this)));

    initWidgetsMap();
  }

  private void initWidgetsMap() {
    Set<Widget> internal = new HashSet<>(roles);

    Set<Widget> external = new HashSet<>();
    external.add(this);

    Set<Widget> all = new HashSet<>(internal);
    all.add(this);

    widgets.put(ActionTarget.ALL, all);
    widgets.put(ActionTarget.EXTERNAL, external);
    widgets.put(ActionTarget.INTERNAL, internal);
  }

  @Override
  public boolean isUnique() {
    return unique;
  }

  @Override
  public boolean toggleUnique() {
    unique = !unique;
    uniquenessConstraint.setUniquenessEnabled(unique);

    return true;
  }

  @Override
  public boolean canToggleConstraints() {
    return true;
  }

  @Override
  public int getArity() {
    return 2;
  }

  public static class RolesBox extends Widget implements Dependency, OrmConnector {

    private static final int INTERNAL_ROLE_GAP = -2;
    private final OrmWidget parent;

    public RolesBox(Scene scene, OrmWidget parent, List<Role> roleBoxes) {
      super(scene);
      this.parent = parent;

      setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, INTERNAL_ROLE_GAP));

      Role left = new Role(scene, parent);
      Role right = new Role(scene, parent);

      roleBoxes.add(left);
      roleBoxes.add(right);

      addChild(left);
      addChild(right);

      setBorder(BorderFactory.createEmptyBorder(6));
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
    for(Widget w : getWidgetsForAction(action))
      w.getActions().addAction(action);
  }

  @Override
  public void removeAction(WidgetAction action) {
    for(Widget w : getWidgetsForAction(action)) {
      w.getActions().addAction(action);
    }
  }

  private Set<Widget> getWidgetsForAction(WidgetAction action) {
    if(action instanceof ConnectAction || action instanceof SelectAction || action instanceof MouseHoverAction)
      return widgets.get(ActionTarget.ALL);

    return widgets.get(ActionTarget.EXTERNAL);
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public Widget getWidget() {
    return this;
  }

  @Override
  public void addUniquenessChangeListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("unique", l);
  }
}
