package view.diagram.elements;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import view.diagram.actions.popup.RolePopupMenuProvider;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.actions.edit.LabelEditor;
import view.diagram.elements.graphics.SwingAbstractBox;
import view.diagram.elements.graphics.shapes.ShapeStrategy;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.anchor.shape.OrmAnchorShapeFactory;

import java.awt.*;
import java.util.List;

public class Role extends Widget implements OrmWidget {

  private final static String DEFAULT_ROLE = "<role>";
  private final static WidgetAction EDIT_ROLE_ACTION = LabelEditor.withDefaultLabel(DEFAULT_ROLE);

  private final RoleBox roleBox;
  private final LabelWidget roleLabel;
  private final OrmElement element;

  private boolean unique = false;
  private boolean mandatory = false;

  public Role(OrmElement element, Scene scene) {
    super(scene);

    this.element = element;
    LookFeel lookFeel = scene.getLookFeel();
    setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, lookFeel.getMargin()));

    this.roleBox = new RoleBox(scene, this);

    this.roleLabel = new LabelWidget(scene, DEFAULT_ROLE);
    roleLabel.setAlignment(LabelWidget.Alignment.CENTER);
    this.roleLabel.getActions().addAction(EDIT_ROLE_ACTION);

    addChild(this.roleBox);
    addChild(this.roleLabel);

    // Setting right position for RoleBox when label is getting wider than RoleBox itself
    roleLabel.addDependency(this.roleBox);

    this.getActions().addAction(ActionFactory.createPopupMenuAction(new RolePopupMenuProvider(this)));
  }

  public static class UniquenessConstraint extends Widget implements Dependency {
    private static final Dimension ROLE_DIMENSION = ShapeStrategyFactory.role().getShapeSize();
    private static final int SIDE_PADDING = ROLE_DIMENSION.width / 10;

    public UniquenessConstraint(Scene scene) {
      super(scene);
    }

    @Override
    protected Rectangle calculateClientArea() {
      return new Rectangle(0, 0, ROLE_DIMENSION.width, 10);
    }

    @Override
    protected void paintWidget() {
      Graphics2D g = getScene().getGraphics();
      Color previousColor = g.getColor();

      g.setColor(OrmColorFactory.getPurple());
      g.fillRect(SIDE_PADDING, SIDE_PADDING, ROLE_DIMENSION.width - SIDE_PADDING * 2, 3);
      g.setColor(previousColor);
    }

    @Override
    public void revalidateDependency() {
      this.repaint();
    }
  }

  public static class RoleBox extends ComponentWidget implements Dependency, OrmConnector {
    final static ShapeStrategy SHAPE = ShapeStrategyFactory.role();
    private final OrmWidget parent;

    public RoleBox(Scene scene, OrmWidget parent) {
      super(scene, new SwingAbstractBox(SHAPE));
      this.parent = parent;
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
  }

  @Override
  public OrmElement getElement() {
    return element;
  }

  @Override
  public Dimension getSize() {
    return RoleBox.getShape().getShapeSize();
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

  public void toggleMandatory() {
    mandatory = !mandatory;
    Graph graph = (Graph)getScene();
    graph.getConnections()
            .stream()
            .forEach(widget -> {
              ConnectionWidget connection = (ConnectionWidget) widget;
              Widget source, target;
              source = connection.getSourceAnchor().getRelatedWidget();
              target = connection.getTargetAnchor().getRelatedWidget();

              AnchorShape shape = getAnchorShape();

              if(source.equals(this.roleBox))
                connection.setTargetAnchorShape(shape);
              else if(target.equals(this.roleBox))
                connection.setSourceAnchorShape(shape);
            });
  }

  private AnchorShape getAnchorShape() {
    if(mandatory)
      return OrmAnchorShapeFactory.getMandatory();
    else
      return AnchorShape.NONE;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public boolean isUnique() {
    return unique;
  }

  public void toggleUnique() {
    unique = !unique;

    if(unique)
      addChild(0, new UniquenessConstraint(this.getScene()));
    else {
      Widget uniquenessConstraint = getChildren().get(0);
      removeChild(uniquenessConstraint);
    }
    this.repaint();
  }
}
