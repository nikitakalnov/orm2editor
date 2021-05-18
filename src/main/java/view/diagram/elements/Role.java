package view.diagram.elements;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
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

public class Role extends Widget implements Widget.Dependency, OrmConnector, Predicate {
  final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
  private final OrmWidget parent;

  private boolean mandatory = false;
  private boolean unique = false;
  private final boolean isUnaryPredicate;
  private final PropertyChangeSupport pcs;

  private final RoleBox roleBox;

  public Role(Scene scene, OrmWidget parent) {
    super(scene);

    this.parent = parent;
    isUnaryPredicate = parent.getElement().getType().equals(ElementType.UNARY_PREDICATE);

    this.getActions().addAction(ActionFactory.createPopupMenuAction(new RolePopupMenuProvider(this)));
    this.roleBox = new RoleBox(scene, parent);
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
    return mandatory;
  }

  public boolean isUnique() {
    return unique;
  }

  /**
   * Добавляет или удаляет ограничение уникальности
   * @return было ли изменено ограничение уникальности
   */
  public boolean toggleUnique() {
    boolean previousUnique = unique;

    if(!isUnaryPredicate) {
      unique = !unique;
    }

    pcs.firePropertyChange("unique", previousUnique, unique);

    return previousUnique != unique;
  }

  /**
   * Добавляет или удаляет ограничение обязательности
   * @return было ли изменено ограничение обязательности
   */
  public boolean toggleMandatory() {
    boolean previousMandatory = mandatory;

    if(!isUnaryPredicate) {
      Graph graph = (Graph)getScene();
      graph.getConnections()
              .forEach(connection -> {
                Widget source, target;
                source = connection.getSourceAnchor().getRelatedWidget();
                target = connection.getTargetAnchor().getRelatedWidget();

                if(source.equals(roleBox) && target instanceof Entity) {
                  mandatory = !mandatory;
                  connection.setTargetAnchorShape(getAnchorShape());
                }
                else if(target.equals(roleBox) && source instanceof Entity) {
                  mandatory = !mandatory;
                  connection.setSourceAnchorShape(getAnchorShape());
                }
              });
    }

    return mandatory != previousMandatory;
  }

  public boolean canToggleConstraints() {
    return !isUnaryPredicate;
  }

  private AnchorShape getAnchorShape() {
    if(mandatory)
      return OrmAnchorShapeFactory.getMandatory();
    else
      return AnchorShape.NONE;
  }

  @Override
  public int getArity() {
    return 1;
  }
}
