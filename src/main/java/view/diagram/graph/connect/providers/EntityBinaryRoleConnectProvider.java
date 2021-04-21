package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.graph.connect.ConnectionType;

import java.awt.*;

public class EntityBinaryRoleConnectProvider implements OrmConnectProvider {
  @Override
  public ConnectionType getConnectionType() {
    return ConnectionType.ENTITY_BINARY_ROLE;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return false;
  }

  @Override
  public ConnectorState isTargetWidget(Widget widget, Widget widget1) {
    return null;
  }

  @Override
  public boolean hasCustomTargetWidgetResolver(Scene scene) {
    return false;
  }

  @Override
  public Widget resolveTargetWidget(Scene scene, Point point) {
    return null;
  }

  @Override
  public void createConnection(Widget widget, Widget widget1) {

  }
}
