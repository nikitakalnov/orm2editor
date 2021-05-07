package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Entity;
import view.diagram.elements.Role;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class UnaryPredicateConnectProvider extends OrmConnectProvider {

  public UnaryPredicateConnectProvider(Graph scene) {
    super(scene);
  }

  @Override
  public ElementType getSourceType() {
    return ElementType.ROLE;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Role.RoleBox;
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
  protected List<Class<? extends Widget>> initTargets() {
    return Arrays.asList(Entity.class);
  }
}
