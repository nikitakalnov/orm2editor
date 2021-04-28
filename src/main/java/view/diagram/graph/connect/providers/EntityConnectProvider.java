package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.elements.Subtyping;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.connect.ConnectionType;
import view.diagram.graph.connect.anchor.OrmAnchorFactory;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EntityConnectProvider extends OrmConnectProvider {
  private final LayerWidget interactionLayer;
  private final OrmConnectProvider subtypingConnectProvider;

  public EntityConnectProvider(Scene scene, LayerWidget connectionLayer, LayerWidget interactionLayer) {
    super(scene, connectionLayer);

    this.interactionLayer = interactionLayer;
    this.subtypingConnectProvider = new SubtypingConnectProvider(scene, connectionLayer);
  }

  @Override
  public ElementType getSourceType() {
    return ElementType.ENTITY;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Entity;
  }

  @Override
  public boolean hasCustomTargetWidgetResolver(Scene scene) {
    return false;
  }

  // TODO: resolve label widget for RolesBox class

  @Override
  public Widget resolveTargetWidget(Scene scene, Point point) {
    return null;
  }

  @Override
  protected List<Class<? extends Widget>> initTargets() {
    return Arrays.asList(Role.RoleBox.class, BinaryPredicate.RolesBox.class, Entity.class);
  }

  @Override
  protected void modifyConnection(ConnectionWidget connectionWidget) {
    if(connectionWidget instanceof Subtyping) {
      connectionWidget.getActions().addAction(
              ActionFactory.createExtendedConnectAction(interactionLayer, subtypingConnectProvider)
      );
    }
  }

  @Override
  protected ConnectionWidget createWidget(Widget source, Widget target) {
    ConnectionWidget widget;

    if(target instanceof Entity)
      widget = new Subtyping(scene);
    else
      widget = new ConnectionWidget(scene);

    return widget;
  }
}
