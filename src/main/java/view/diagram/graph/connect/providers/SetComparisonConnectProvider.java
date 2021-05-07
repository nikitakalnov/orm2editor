package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.List;

public class SetComparisonConnectProvider extends OrmConnectProvider {

  private static final Stroke CONSTRAINT_CONNECTION_STROKE = new BasicStroke(2.0f,
          BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_MITER,
          10.0f, new float[]{8.0f}, 0.0f);
  private final ElementType constraintType;

  public SetComparisonConnectProvider(
          Graph scene,
          ElementType constraintType,
          List<Class<? extends Widget>> targets) {
    super(scene);

    this.targets = targets;
    this.constraintType = constraintType;

  }

  @Override
  protected List<Class<? extends Widget>> initTargets() {
    return targets;
  }

  @Override
  public ElementType getSourceType() {
    return constraintType;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    boolean isSource = false;

    if(widget instanceof SetComparisonConstraint) {
      SetComparisonConstraint constraint = (SetComparisonConstraint)widget;
      isSource = constraint.getElement().getType().equals(constraintType);
    }

    return isSource;
  }

  @Override
  public boolean hasCustomTargetWidgetResolver(Scene scene) {
    return false;
  }

  @Override
  public Widget resolveTargetWidget(Scene scene, Point point) {
    return null;
  }
}
