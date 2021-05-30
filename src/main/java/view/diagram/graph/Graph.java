package view.diagram.graph;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.vstu.nodelinkdiagram.*;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import view.diagram.actions.dnd.DragAndDropAcceptProvider;
import view.diagram.actions.move.DiagramNodeMoveProvider;
import view.diagram.actions.popup.EdgePopupMenuProvider;
import view.diagram.actions.popup.WidgetPopupMenuProvider;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.factory.OrmWidgetFactory;
import view.diagram.graph.connect.Connection;
import view.diagram.graph.connect.ConnectionUtils;
import view.diagram.graph.connect.providers.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Graph extends GraphScene<OrmElement, Connection> {

  private final ClientDiagramModel model;

  private LayerWidget mainLayer;
  private LayerWidget connectionLayer;
  private LayerWidget interactionLayer;

  private OrmWidgetFactory widgetFactory = new OrmWidgetFactory(this);
  private ConnectProviderFactory connectProviderFactory = new ConnectProviderFactory(this);

  private final MoveProvider NODE_MOVE_PROVIDER = new DiagramNodeMoveProvider(this);
  private WidgetAction moveAction = ActionFactory.createMoveAction(
          ActionFactory.createFreeMoveStrategy(),
          NODE_MOVE_PROVIDER
  );
  private ConnectDecorator DEFAULT_CONNECT_DECORATOR = new DefaultConnectDecorator();

  public Graph(ClientDiagramModel model) {
    this.model = model;

    mainLayer = new LayerWidget(this);
    connectionLayer = new LayerWidget(this);
    interactionLayer = new LayerWidget(this);

    addChild(interactionLayer);
    addChild(mainLayer);
    addChild(connectionLayer);

    getActions().addAction(ActionFactory.createAcceptAction(new DragAndDropAcceptProvider(this)));
  }

  @Override
  protected Widget attachNodeWidget(OrmElement element) {
    OrmWidget elementWidget = widgetFactory.forElement(element);

    elementWidget.attachConnectAction(ActionFactory.createExtendedConnectAction(
            DEFAULT_CONNECT_DECORATOR,
            interactionLayer,
            connectProviderFactory.getFor(element.getType())
    ));
    elementWidget.attachAction(moveAction);
    elementWidget.attachAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenuProvider(elementWidget)));

    mainLayer.addChild(elementWidget.getWidget());
    return elementWidget.getWidget();
  }

  @Override
  protected Widget attachEdgeWidget(Connection c) {
    ConnectionWidget edge = c.getWidget();

    WidgetAction.Chain actions = edge.getActions();
    actions.addAction(ActionFactory.createPopupMenuAction(new EdgePopupMenuProvider(c, this)));

    for(OrmConnectProvider provider : c.getConnectProviders()) {
      actions.addAction(ActionFactory.createExtendedConnectAction(interactionLayer, provider));
    }

    connectionLayer.addChild(edge);

    return edge;
  }

  @Override
  protected void attachEdgeSourceAnchor(Connection s, OrmElement oldSource, OrmElement newSource) {

  }

  @Override
  protected void attachEdgeTargetAnchor(Connection s, OrmElement oldTarget, OrmElement newTarget) {
  }

  public void addConnection(Connection connection) {
    if(!getObjects().contains(connection)) {
      OrmElement sourceElement = connection.getSource();
      OrmElement targetElement = connection.getTarget();

      DiagramNode source = sourceElement.getNode();
      DiagramNode target = targetElement.getNode();

      Class<? extends DiagramEdge> edgeType = ConnectionUtils.getType(sourceElement, targetElement);

      ValidateStatus modelStatus = updateModel(() -> model.connectBy(source, target, edgeType));
      if(modelStatus.equals(ValidateStatus.Invalid))
        model.rollback();
      else {
        addEdge(connection);
        setEdgeSource(connection, connection.getSource());
        setEdgeTarget(connection, connection.getTarget());

        repaint();
        validate();
      }
    }
    else
      JOptionPane.showMessageDialog(null, "Such relation already exists");
  }

  public List<ConnectionWidget> getConnections() {
    List<ConnectionWidget> connections = connectionLayer
            .getChildren()
            .stream()
            .map(c -> (ConnectionWidget)c)
            .collect(Collectors.toList());

    return Collections.unmodifiableList(connections);
  }

  public Widget addOrmNode(ElementType type) {
    model.beginUpdate();

    Class<? extends DiagramElement> elementClass = type.getElementClass();
    DiagramNode node = model.createNode((Class<? extends DiagramNode>)elementClass);
    model.commit();

    OrmElement element = new OrmElement(node);

    ValidateStatus modelStatus = model.getValidateStatus();
    if(modelStatus.equals(ValidateStatus.Invalid)) {
      model.rollback();
      return null;
    }
    else
      return addNode(element);
  }

  public void moveNode(Widget widget, Point location) {
    DiagramNode node = ((OrmElement) findObject(widget)).getNode();

    updateModel(() -> {
      node.setPosition(new org.vstu.nodelinkdiagram.util.Point(location.x, location.y));
    });

    widget.setPreferredLocation(location);
  }

  /**
   *
   * @param updateCode исполняемый код, который модифицирует модель
   * @return статус валидности модели после выполнения updateCode
   */
  public ValidateStatus updateModel(Runnable updateCode) {
    model.beginUpdate();
    updateCode.run();
    model.commit();

    return model.getValidateStatus();
  }
}
