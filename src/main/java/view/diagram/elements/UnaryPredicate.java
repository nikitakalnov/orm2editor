package view.diagram.elements;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import view.diagram.actions.popup.RolePopupMenuProvider;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.elements.predicate.UniquenessConstraint;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.anchor.shape.OrmAnchorShapeFactory;

import java.awt.*;

public class UnaryPredicate extends Widget implements OrmWidget {

  private final static String DEFAULT_ROLE = "<role>";
  private final static WidgetAction EDIT_ROLE_ACTION = LabelEditor.withDefaultLabel(DEFAULT_ROLE);

  private final Role roleBox;
  private final LabelWidget roleLabel;
  private final OrmElement element;

  private boolean unique = false;
  private boolean mandatory = false;

  public UnaryPredicate(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    LookFeel lookFeel = scene.getLookFeel();
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, lookFeel.getMargin()));

    this.roleBox = new Role(scene, this);

    this.roleLabel = new LabelWidget(scene, DEFAULT_ROLE);
    roleLabel.setAlignment(LabelWidget.Alignment.CENTER);
    this.roleLabel.getActions().addAction(EDIT_ROLE_ACTION);

    addChild(roleBox);
    addChild(roleLabel);

    // Setting correct position for RoleBox when label is getting wider than RoleBox itself
    roleLabel.addDependency(this.roleBox);
  }

  public static class UniquenessConstraint extends Widget {
    private static final Dimension ROLE_DIMENSION = ShapeStrategyFactory.role().getShapeSize();
    private static final int SIDE_PADDING = ROLE_DIMENSION.width / 10;

    private final RoleBox role;
    private boolean enabled = false;

    public UniquenessConstraint(Scene scene, RoleBox role) {
      super(scene);

      this.role = role;
    }

    public RoleBox getRole() {
      return role;
    }

    @Override
    protected Rectangle calculateClientArea() {
      return new Rectangle(0, 0, ROLE_DIMENSION.width, 10);
    }

    @Override
    protected void paintWidget() {
      if(enabled) {
        Graphics2D g = getScene().getGraphics();
        Color previousColor = g.getColor();

        g.setColor(OrmColorFactory.getPurple());
        g.fillRect(SIDE_PADDING, SIDE_PADDING, ROLE_DIMENSION.width - SIDE_PADDING * 2, 3);
        g.setColor(previousColor);
      }
    }

    public void setUniquenessEnabled(boolean enabled) {
      this.enabled = enabled;
    }
  }

  public static class RoleBox extends ComponentWidget implements Dependency, OrmConnector {
    final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
    private final OrmWidget parent;

    private boolean mandatory = false;
    private boolean unique = false;
    private final boolean isUnaryPredicate;

    public RoleBox(Scene scene, OrmWidget parent) {
      super(scene, new SwingAbstractBox(SHAPE));
      this.parent = parent;
      isUnaryPredicate = parent.getElement().getType().equals(ElementType.UNARY_PREDICATE);

      this.getActions().addAction(ActionFactory.createPopupMenuAction(new RolePopupMenuProvider(this)));
    }

    @Override
    public void revalidateDependency() {
      this.revalidate();
    }

    public static ShapeStrategy getShape() {
      return SHAPE;
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

        BinaryPredicate parentWidget = (BinaryPredicate) (getParent().getWidget());
        parentWidget.setUniquenessConstraint(this, unique);
      }

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

                  if(source.equals(this) && target instanceof Entity) {
                    mandatory = !mandatory;
                    connection.setTargetAnchorShape(getAnchorShape());
                  }
                  else if(target.equals(this) && source instanceof Entity) {
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
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public Dimension getSize() {
    return Role.getShapeSize();
  }

  @Override
  public void attachConnectAction(WidgetAction action) {
    this.roleBox.getActions().addAction(action);
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
