package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.OrmEdge;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SetComparisonConnectProvider extends OrmConnectProvider {
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
  public ConnectorState isTargetWidget(Widget source, Widget target) {
    boolean isTargetClass = super.isTargetWidget(source, target).equals(ConnectorState.ACCEPT);
    if(!isTargetClass)
      return ConnectorState.REJECT;
    else {
      boolean result = true;

      SetComparisonConstraint constraint = (SetComparisonConstraint)source;
      Collection<OrmEdge> edges = scene.findNodeEdges(constraint.getElement(), true, true);

      Iterator<OrmEdge> edgeIterator = edges.iterator();
      while(edgeIterator.hasNext() && result) {
        OrmEdge edge = edgeIterator.next();
        Widget edgeSource = edge.getWidget().getSourceAnchor().getRelatedWidget();
        Widget edgeTarget = edge.getWidget().getTargetAnchor().getRelatedWidget();

        Widget predicate;
        if(edgeSource instanceof SetComparisonConstraint)
          predicate = edgeTarget;
        else
          predicate = edgeSource;

        result = predicate.getClass().isInstance(target);
      }

      return result ? ConnectorState.ACCEPT : ConnectorState.REJECT;
    }

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
