package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.graph.connect.ConnectionType;
import view.diagram.graph.connect.anchor.OrmAnchorFactory;

public abstract class OrmConnectProvider implements ConnectProvider {
  protected final Scene scene;
  protected final LayerWidget layer;

  protected OrmConnectProvider(Scene scene, LayerWidget layer) {
    this.scene = scene;
    this.layer = layer;
  }

  @Override
  public void createConnection(Widget source, Widget target) {
    ConnectionWidget connection = new ConnectionWidget(scene);
    connection.setTargetAnchor(OrmAnchorFactory.forWidget(source));
    connection.setSourceAnchor(OrmAnchorFactory.forWidget(target));

    addConnectionActions(connection);

    layer.addChild(connection);
  }

  protected void addConnectionActions(Widget connectionWidget) {

  }

  public abstract ConnectionType getConnectionType();
}
