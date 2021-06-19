package view.diagram.elements;


import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.modules.visual.action.ConnectAction;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;
import org.vstu.nodelinkdiagram.ClientDiagramModelListener;
import org.vstu.nodelinkdiagram.DiagramNode;
import org.vstu.nodelinkdiagram.ModelUpdateEvent;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.orm2diagram.model.*;
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
import view.diagram.graph.Graph;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryPredicate extends Widget implements OrmWidget, Predicate, ClientDiagramModelListener {
  private final OrmElement element;
  private final ORM_BinaryPredicate predicate;
  private final LinkedList<Role> roles = new LinkedList<>();
  private final static String DEFAULT_ROLE_LABEL =  "<role>";
  private final RolesBox rolesBox;
  private final UniquenessConstraint uniquenessConstraint;
  private final Widget uniquenessConstraintsBox;
  private final PropertyChangeSupport pcs;
  private final Map<ActionTarget, Set<Widget>> widgets = new HashMap<>();
  private final LabelWidget roleName;
  private ORM_SequenceFromTwoRoles binaryRoleSequence;

  private enum ActionTarget {
    ALL,
    EXTERNAL,
    INTERNAL
  }

  public BinaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    this.predicate = (ORM_BinaryPredicate)element.getNode();
    Graph graph = (Graph)scene;
    graph.updateModel(model -> {
      binaryRoleSequence = model.createNode(ORM_SequenceFromTwoRoles.class);
      binaryRoleSequence.addItem(predicate.getItem(0));
      binaryRoleSequence.addItem(predicate.getItem(1));

      ORM_RoleSequence leftRoleSequence = model.createNode(ORM_SequenceFromOneRole.class);
      leftRoleSequence.addItem(predicate.getItem(0));

      ORM_RoleSequence rightRoleSequence = model.createNode(ORM_SequenceFromOneRole.class);
      rightRoleSequence.addItem(predicate.getItem(1));
    });

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

    roleName = new LabelWidget(scene, getRoleNames());
    //label.getActions().addAction(LabelEditor.withDefaultLabel(DEFAULT_ROLE_LABEL));
    roleName.addDependency(rolesBox);

    addChild(roleName);
    getActions().addAction(ActionFactory.createPopupMenuAction(new BinaryPredicateMenuProvider(this)));

    initWidgetsMap();
    graph.addModelListener(this);
  }

  protected String getRoleNames() {
    String names = predicate.getItem(0).getName() + " / " + predicate.getItem(1).getName();
    return names
            .replaceFirst("/\\s{0,}$", "")
            .replaceFirst("^\\s{0,}/", "<- ");
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
    return binaryRoleSequence.isUnique();
  }

  @Override
  public boolean toggleUnique() {
    Graph graph = (Graph)getScene();

    boolean previousUnique = binaryRoleSequence.isUnique();
    graph.updateModel(model -> {
      binaryRoleSequence.setUnique(!previousUnique);
    });

    uniquenessConstraint.setUniquenessEnabled(!previousUnique);

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

      ORM_BinaryPredicate predicate = (ORM_BinaryPredicate) parent.getElement().getNode();
      Role left = new Role(scene, parent, predicate.getItem(0));
      Role right = new Role(scene, parent, predicate.getItem(1));

      roleBoxes.add(left);
      roleBoxes.add(right);

      addChild(left);
      addChild(right);

      setBorder(BorderFactory.createEmptyBorder(6));
    }

    @Override
    public OrmElement getElement() {
      return parent.getElement();
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
  public void attachAction(WidgetAction action) {
    for(Widget w : getWidgetsForAction(action))
      w.getActions().addAction(action);
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

  @Override
  public void isUpdated(ModelUpdateEvent modelUpdateEvent) {
    roleName.setLabel(getRoleNames());
    repaint();
  }
}
