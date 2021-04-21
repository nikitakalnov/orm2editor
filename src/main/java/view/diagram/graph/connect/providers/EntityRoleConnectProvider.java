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

public class EntityRoleConnectProvider extends OrmConnectProvider {
  private static EntityRoleReconnectProvider RECONNECT_PROVIDER = new EntityRoleReconnectProvider();

  public EntityRoleConnectProvider(Scene scene, LayerWidget layer) {
    super(scene, layer);
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
  protected void addConnectionActions(Widget connection) {
    connection.getActions().addAction(
            ActionFactory.createReconnectAction(RECONNECT_PROVIDER)
    );
  }

  @Override
  public ConnectionType getConnectionType() {
    return ConnectionType.ENTITY_ROLE;
  }
}
