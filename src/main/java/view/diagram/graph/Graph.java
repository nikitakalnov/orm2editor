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
import view.diagram.elements.OrmElement;
import view.diagram.elements.OrmWidget;
import view.diagram.elements.OrmWidgetFactory;
import view.diagram.elements.actions.DefaultSelectProvider;
import view.diagram.graph.connect.providers.DefaultConnectDecorator;
import view.diagram.graph.connect.providers.EntityRoleConnectProvider;
import view.diagram.graph.connect.providers.OrmConnectProvider;

import java.util.*;
import java.util.stream.Collectors;

public class Graph extends GraphScene<OrmElement, String> {

  private LayerWidget mainLayer;
  private LayerWidget connectionLayer;
  private LayerWidget interactionLayer;

  private OrmWidgetFactory widgetFactory = new OrmWidgetFactory(this);
  private Set<OrmConnectProvider> connectProviders = new HashSet<>();

  private WidgetAction moveAction = ActionFactory.createMoveAction();
  private ConnectDecorator DEFAULT_CONNECT_DECORATOR = new DefaultConnectDecorator();

  public Graph() {
    mainLayer = new LayerWidget(this);
    connectionLayer = new LayerWidget(this);
    interactionLayer = new LayerWidget(this);

    addChild(interactionLayer);
    addChild(mainLayer);
    addChild(connectionLayer);

    connectProviders.add(new EntityRoleConnectProvider(this, connectionLayer));
  }

  protected java.util.List<WidgetAction> getConnectActions(OrmElement element) {
    java.util.List<OrmConnectProvider> connectors = new ArrayList<>();

    for(OrmConnectProvider provider : connectProviders) {
      if(provider.getConnectionType().contains(element.getType()))
        connectors.add(provider);
    }

    return connectors
            .stream()
            .map(connector -> ActionFactory.createExtendedConnectAction(DEFAULT_CONNECT_DECORATOR, interactionLayer, connector))
            .collect(Collectors.toList());
  }

  @Override
  protected Widget attachNodeWidget(OrmElement element) {
    OrmWidget elementWidget = widgetFactory.forElement(element);

    elementWidget.attachActions(getConnectActions(element));
    elementWidget.attachAction(moveAction);
    //elementWidget.attachAction(ActionFactory.createSelectAction(new DefaultSelectProvider()));

    mainLayer.addChild(elementWidget.getWidget());
    return elementWidget.getWidget();
  }

  @Override
  protected Widget attachEdgeWidget(String s) {
    ConnectionWidget edge = new ConnectionWidget(this);
    edge.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);

    WidgetAction.Chain actions = edge.getActions();
    actions.addAction(createSelectAction());

    connectionLayer.addChild(edge);

    return edge;
  }

  @Override
  protected void attachEdgeSourceAnchor(String s, OrmElement oldSource, OrmElement newSource) {
    ConnectionWidget edge = (ConnectionWidget)findWidget(s);

    Widget source = findWidget(newSource);
    Anchor sourceAnchor = AnchorFactory.createCircularAnchor(source, 6);
    edge.setSourceAnchor(sourceAnchor);
  }

  @Override
  protected void attachEdgeTargetAnchor(String s, OrmElement oldTarget, OrmElement newTarget) {
    ConnectionWidget edge = (ConnectionWidget)findWidget(s);

    Widget target = findWidget(newTarget);
    Anchor targetAnchor = AnchorFactory.createCircularAnchor(target, 6);
    edge.setSourceAnchor(targetAnchor);
  }
}
