package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.*;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EntityConnectProvider extends OrmConnectProvider {

  public EntityConnectProvider(Graph scene) {
    super(scene);
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
    return Arrays.asList(RoleBox.class, Entity.class);
  }
}
