package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Entity;
import view.diagram.graph.connect.ConnectionType;

import java.awt.*;

public class EntityBinaryRoleConnectProvider extends OrmConnectProvider {
  public EntityBinaryRoleConnectProvider(Scene scene, LayerWidget layer) {
    super(scene, layer);
  }

  @Override
  public ConnectionType getConnectionType() {
    return ConnectionType.ENTITY_BINARY_ROLE;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Entity || widget instanceof BinaryPredicate;
  }

  @Override
  public ConnectorState isTargetWidget(Widget source, Widget target) {
    boolean correctTarget =
            source instanceof Entity && target instanceof BinaryPredicate
                    || source instanceof BinaryPredicate && target instanceof Entity;

    return correctTarget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
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
