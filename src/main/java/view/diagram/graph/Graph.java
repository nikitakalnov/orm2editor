package view.diagram.graph;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.actions.dnd.DragAndDropAcceptProvider;
import view.diagram.actions.popup.WidgetPopupMenuProvider;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;
import view.diagram.elements.core.OrmWidget;
import view.diagram.elements.factory.OrmWidgetFactory;
import view.diagram.graph.connect.Connection;
import view.diagram.graph.connect.providers.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class Graph extends GraphScene<OrmElement, Connection> {

  private LayerWidget mainLayer;
  private LayerWidget connectionLayer;
  private LayerWidget interactionLayer;

  private OrmWidgetFactory widgetFactory = new OrmWidgetFactory(this);
  private ConnectProviderFactory connectProviderFactory = new ConnectProviderFactory(this);

  private WidgetAction moveAction = ActionFactory.createMoveAction();
  private ConnectDecorator DEFAULT_CONNECT_DECORATOR = new DefaultConnectDecorator();

  public Graph() {
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
    elementWidget.attachAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenuProvider(elementWidget.getWidget())));

    mainLayer.addChild(elementWidget.getWidget());
    return elementWidget.getWidget();
  }

  @Override
  protected Widget attachEdgeWidget(Connection c) {
    ConnectionWidget edge = c.getWidget();

    WidgetAction.Chain actions = edge.getActions();
    actions.addAction(createSelectAction());

    for(OrmConnectProvider provider : c.getConnectProviders()) {
      actions.addAction(ActionFactory.createExtendedConnectAction(interactionLayer, provider));
    }

    connectionLayer.addChild(edge);

    return edge;
  }

  @Override
  protected void attachEdgeSourceAnchor(Connection s, OrmElement oldSource, OrmElement newSource) {
    ConnectionWidget edge = (ConnectionWidget)findWidget(s);

    Widget source = findWidget(newSource);
    Anchor sourceAnchor = AnchorFactory.createCircularAnchor(source, 6);
    edge.setSourceAnchor(sourceAnchor);
  }

  @Override
  protected void attachEdgeTargetAnchor(Connection s, OrmElement oldTarget, OrmElement newTarget) {
    ConnectionWidget edge = (ConnectionWidget)findWidget(s);

    Widget target = findWidget(newTarget);
    Anchor targetAnchor = AnchorFactory.createCircularAnchor(target, 6);
    edge.setSourceAnchor(targetAnchor);
  }

  public void addConnection(Connection connection) {
    if(!getObjects().contains(connection)) {
      addEdge(connection);

      repaint();
      validate();
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
}
