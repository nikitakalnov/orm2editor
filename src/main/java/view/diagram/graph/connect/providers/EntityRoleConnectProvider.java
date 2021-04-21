package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.graph.connect.ConnectionType;
import view.diagram.graph.connect.anchor.OrmAnchorFactory;

import java.awt.*;

public class EntityRoleConnectProvider implements OrmConnectProvider {
  private final Scene scene;
  private final LayerWidget layer;
  private static EntityRoleReconnectProvider RECONNECT_PROVIDER = new EntityRoleReconnectProvider();

  public EntityRoleConnectProvider(Scene scene, LayerWidget layer) {
    this.scene = scene;
    this.layer = layer;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Role.RoleBox || widget instanceof Entity;
  }

  @Override
  public ConnectorState isTargetWidget(Widget source, Widget target) {
    // TODO обращение к методу модели для валидации: source не должен быть уже соединён с target
    // TODO: подсвечивать виджеты, к которым можно присоединить создаваемую дугу
    return source instanceof Entity && target instanceof Role.RoleBox
            || source instanceof Role.RoleBox && target instanceof Entity ? ConnectorState.ACCEPT : ConnectorState.REJECT;
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
  public void createConnection(Widget source, Widget target) {

    ConnectionWidget connection = new ConnectionWidget(scene);
    connection.setTargetAnchor(OrmAnchorFactory.forWidget(source));
    connection.setSourceAnchor(OrmAnchorFactory.forWidget(target));

    connection.getActions().addAction(
            ActionFactory.createReconnectAction(RECONNECT_PROVIDER)
    );
    //connection.getActions().addAction(ActionFactory.createSelectAction(new ConnectionSelectProvider()));

    layer.addChild(connection);
  }

  @Override
  public ConnectionType getConnectionType() {
    return ConnectionType.ENTITY_ROLE;
  }
}
