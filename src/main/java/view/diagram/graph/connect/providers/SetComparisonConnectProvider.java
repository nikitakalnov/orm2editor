package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;

import java.awt.*;
import java.util.List;

public class SetComparisonConnectProvider extends OrmConnectProvider {

  private final ElementType constraintType;

  public SetComparisonConnectProvider(
          Scene scene,
          LayerWidget layer,
          ElementType constraintType,
          List<Class<? extends Widget>> targets) {
    super(scene, layer);

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
