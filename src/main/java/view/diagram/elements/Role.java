package view.diagram.elements;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.vstu.nodelinkdiagram.DiagramNode;
import org.vstu.orm2diagram.model.ORM_Role;
import view.diagram.actions.popup.RolePopupMenuProvider;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.elements.predicate.Predicate;
import view.diagram.elements.predicate.UniquenessConstraint;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.anchor.shape.OrmAnchorShapeFactory;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Role extends Widget implements Widget.Dependency, Predicate {
  final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmWidget parent;
  private final ORM_Role role;

  private final boolean isUnaryPredicate;
  private final PropertyChangeSupport pcs;

  private final RoleBox roleBox;

  public Role(Scene scene, OrmWidget parent, ORM_Role role) {
    super(scene);

    this.parent = parent;
    this.role = role;
    isUnaryPredicate = parent.getElement().getType().equals(ElementType.UNARY_PREDICATE);

    this.getActions().addAction(ActionFactory.createPopupMenuAction(new RolePopupMenuProvider(this)));
    this.roleBox = new RoleBox(scene, parent, role);
    addChild(roleBox);

    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 4));

    this.pcs = new PropertyChangeSupport(this);
  }

  public void addUniquenessChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener("unique", listener);
  }

  public static Dimension getShapeSize() {
    return SHAPE.getShapeSize();
  }

  @Override
  public void revalidateDependency() {
    this.revalidate();
  }

  public OrmWidget getParent() {
    return parent;
  }

  public boolean isMandatory() {
    return role.isMandatory();
  }

  public boolean isUnique() {
    return role.isUnique();
  }

  /**
   * Добавляет или удаляет ограничение уникальности
   * @return было ли изменено ограничение уникальности
   */
  public boolean toggleUnique() {
    boolean previousUnique = role.isUnique();

    Graph graph = (Graph)getScene();
    if(!isUnaryPredicate) {
      graph.updateModel(model -> {
        role.setUnique(!previousUnique);
      });
    }

    pcs.firePropertyChange("unique", previousUnique, role.isUnique());

    return previousUnique != role.isUnique();
  }

  /**
   * Добавляет или удаляет ограничение обязательности
   * @return было ли изменено ограничение обязательности
   */
  public boolean toggleMandatory() {
    boolean previousMandatory = role.isMandatory();

    if(!isUnaryPredicate) {
      Graph graph = (Graph)getScene();
      graph.getConnections()
              .forEach(connection -> {
                Widget source, target;
                source = connection.getSourceAnchor().getRelatedWidget();
                target = connection.getTargetAnchor().getRelatedWidget();

                if(source.equals(roleBox) && target instanceof Entity) {
                  graph.updateModel(model -> {
                    role.setMandatory(!previousMandatory);
                  });
                  connection.setTargetAnchorShape(getAnchorShape());
                }
                else if(target.equals(roleBox) && source instanceof Entity) {
                  graph.updateModel(model -> {
                    role.setMandatory(!previousMandatory);
                  });
                  connection.setSourceAnchorShape(getAnchorShape());
                }
              });
    }

    return role.isMandatory() != previousMandatory;
  }

  public boolean canToggleConstraints() {
    return !isUnaryPredicate;
  }

  private AnchorShape getAnchorShape() {
    if(role.isMandatory())
      return OrmAnchorShapeFactory.getMandatory();
    else
      return AnchorShape.NONE;
  }

  @Override
  public int getArity() {
    return 1;
  }

  public String getName() {
    return role.getName();
  }

  public void setName(String newName) {
    Graph graph = (Graph)getScene();
    graph.updateModel(model -> {
      role.setName(newName);
    });
  }
}
