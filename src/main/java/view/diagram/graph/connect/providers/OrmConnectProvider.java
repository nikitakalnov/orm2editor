package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.connect.ConnectionType;
import view.diagram.graph.connect.anchor.OrmAnchorFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class OrmConnectProvider implements ConnectProvider {
  protected final Scene scene;

  /**
   * Слой, в котором должна отрисовываться окончательно созданная дуга
   */
  protected final LayerWidget layer;
  protected List<Class<? extends Widget>> targets;

  protected OrmConnectProvider(Scene scene, LayerWidget layer) {
    this.scene = scene;
    this.layer = layer;

    targets = initTargets();
  }

  protected abstract List<Class<? extends Widget>> initTargets();

  @Override
  public void createConnection(Widget source, Widget target) {
    ConnectionWidget connection = new ConnectionWidget(scene);
    connection.setTargetAnchor(OrmAnchorFactory.forWidget(target));
    connection.setSourceAnchor(OrmAnchorFactory.forWidget(source));

    addConnectionActions(connection);

    layer.addChild(connection);
  }

  protected void addConnectionActions(Widget connectionWidget) {

  }

  @Override
  public ConnectorState isTargetWidget(Widget source, Widget target) {
    boolean isSource = isSourceWidget(source);
    boolean isTarget = false;

    for(Class<? extends Widget> targetClass : targets) {
      isTarget = isTarget || targetClass.isInstance(target);
    }

    return isSource && isTarget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
  }

  public abstract ElementType getSourceType();
}
