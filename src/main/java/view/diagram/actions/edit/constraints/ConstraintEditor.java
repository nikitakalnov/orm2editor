package view.diagram.actions.edit.constraints;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.confirm.ConfirmListener;
import view.diagram.actions.edit.ConfirmAction;
import view.diagram.actions.select.WidgetSelectProvider;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.OrmConnector;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;
import view.diagram.graph.connect.OrmEdge;
import view.diagram.graph.connect.TemporaryConnection;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConstraintEditor implements ConfirmListener {

  private final Set<OrmEdge> selectedConnections = new LinkedHashSet<>();
  private final SetComparisonConstraint constraint;
  private WidgetAction predicateSelectAction;
  private WidgetAction sceneConfirmAction;
  private final Set<Widget> connectedPredicates;
  private final Graph graph;
  private Widget previouslyFocusedWidget;
  private final Map<Widget, Color> previousColors = new HashMap<>();
  private boolean started = false;

  public ConstraintEditor(SetComparisonConstraint constraint) {
    this.constraint = constraint;
    this.graph = (Graph)constraint.getScene();
    connectedPredicates = getPredicates();
  }

  public void start() {
    predicateSelectAction = ActionFactory.createSelectAction(new WidgetSelectProvider(this::selectPredicate));
    connectedPredicates.forEach(p -> {
      p.getActions().addAction(predicateSelectAction);
    });

    sceneConfirmAction = new ConfirmAction(this);
    graph.getActions().addAction(sceneConfirmAction);

    graph.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
    previouslyFocusedWidget = graph.getFocusedWidget();
    graph.setFocusedWidget(graph);

    JOptionPane.showMessageDialog(
            null,
            "Click on connected predicate to select it, click it again to remove selection.\nClick Enter to remove selected predicates."
    );

    started = true;
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
    OrmEdge connection = getPredicateConnection(widget);
    ConnectionWidget connectionWidget = (ConnectionWidget) graph.findWidget(connection);

    if(previousColors.containsKey(connectionWidget))
      unselectPredicate(widget);
    else {
      previousColors.put(connectionWidget, connectionWidget.getLineColor());

      connectionWidget.setLineColor(Color.RED);
      selectedConnections.add(connection);
    }
  }

  protected void unselectPredicate(Widget widget) {
    OrmEdge connection = getPredicateConnection(widget);

    ConnectionWidget connectionWidget = (ConnectionWidget) graph.findWidget(connection);
    Color previousColor = previousColors.get(connectionWidget);
    connectionWidget.setLineColor(previousColor);

    selectedConnections.remove(connection);
    previousColors.remove(connectionWidget);
  }

  OrmEdge getPredicateConnection(Widget widget) {
    OrmElement predicate = null;
    if(widget instanceof OrmWidget)
      predicate = ((OrmWidget)widget).getElement();
    else if(widget instanceof OrmConnector)
      predicate = ((OrmConnector)widget).getParent().getElement();

    List<OrmEdge> connections = new ArrayList<>();
    connections.addAll(graph.findEdgesBetween(predicate, constraint.getElement()));
    connections.addAll(graph.findEdgesBetween(constraint.getElement(), predicate));

    return connections.get(0);
  }

  @Override
  public void confirmed() {
    if(started) {
      selectedConnections.forEach(graph::removeEdge);

      constraint.getScene().getActions().removeAction(sceneConfirmAction);
      connectedPredicates.forEach(p -> p.getActions().removeAction(predicateSelectAction));
      graph.setFocusedWidget(previouslyFocusedWidget);

      started = false;
    }
  }
}
