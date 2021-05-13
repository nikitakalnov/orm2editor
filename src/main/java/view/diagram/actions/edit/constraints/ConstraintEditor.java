package view.diagram.actions.edit.constraints;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.SelectAction;
import view.diagram.actions.confirm.ConfirmListener;
import view.diagram.actions.edit.ConfirmAction;
import view.diagram.actions.select.WidgetSelectProvider;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.Connection;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class ConstraintEditor implements ConfirmListener {

  private final Set<Connection> selectedConnections = new LinkedHashSet<>();
  private final SetComparisonConstraint constraint;
  private final WidgetAction predicateSelectAction;
  private final WidgetAction sceneConfirmAction;
  private final Set<Widget> connectedPredicates;
  private final Graph graph;
  private final Widget previouslyFocusedWidget;
  private final Map<Widget, Border> previousBorders = new HashMap<>();

  public ConstraintEditor(SetComparisonConstraint constraint) {
    this.constraint = constraint;
    this.graph = (Graph)constraint.getScene();
    connectedPredicates = getPredicates();

    predicateSelectAction = new SelectAction(new WidgetSelectProvider(this::selectPredicate));
    connectedPredicates.forEach(p -> {
      p.getActions().addAction(predicateSelectAction);
    });

    sceneConfirmAction = new ConfirmAction(this);
    graph.getActions().addAction(sceneConfirmAction);

    graph.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
    previouslyFocusedWidget = graph.getFocusedWidget();
    graph.setFocusedWidget(graph);
  }

  private Set<Widget> getPredicates() {
    return graph.getConnections()
            .stream()
            .filter(c -> constraint.equals(c.getSourceAnchor().getRelatedWidget()) || constraint.equals(c.getTargetAnchor().getRelatedWidget()))
            .map(c -> {
              Widget widget = null;
              if(c.getSourceAnchor().getRelatedWidget().equals(constraint))
                widget = c.getTargetAnchor().getRelatedWidget();
              else if(c.getTargetAnchor().getRelatedWidget().equals(constraint))
                widget = c.getSourceAnchor().getRelatedWidget();

              return widget;
            })
            .collect(Collectors.toSet());
  }

  protected void selectPredicate(Widget widget) {
    if(previousBorders.containsKey(widget))
      unselectPredicate(widget);
    else {
      previousBorders.put(widget, widget.getBorder());
      widget.setBorder(BorderFactory.createLineBorder(2, Color.RED));

      selectedConnections.addAll(getConnectionsForPredicate(widget));
    }
  }

  protected void unselectPredicate(Widget widget) {
    Border previousBorder = previousBorders.get(widget);
    if(Objects.nonNull(previousBorder))
      widget.setBorder(previousBorder);

    previousBorders.remove(widget);
    selectedConnections.removeAll(getConnectionsForPredicate(widget));
  }

  Collection<Connection> getConnectionsForPredicate(Widget widget) {
    OrmElement predicate = null;
    if(widget instanceof OrmWidget)
      predicate = ((OrmWidget)widget).getElement();
    else if(widget instanceof OrmConnector)
      predicate = ((OrmConnector)widget).getParent().getElement();

    Set<Connection> connections = new HashSet<>();
    connections.addAll(graph.findEdgesBetween(predicate, constraint.getElement()));
    connections.addAll(graph.findEdgesBetween(constraint.getElement(), predicate));

    return connections;
  }

  @Override
  public void confirmed() {
    selectedConnections.forEach(graph::removeEdge);

    constraint.getScene().getActions().removeAction(sceneConfirmAction);
    connectedPredicates.forEach(p -> p.getActions().removeAction(predicateSelectAction));
    graph.setFocusedWidget(previouslyFocusedWidget);

    for(Map.Entry<Widget, Border> widgetBorderEntry : previousBorders.entrySet()) {
      widgetBorderEntry.getKey().setBorder(widgetBorderEntry.getValue());
    }
  }
}
