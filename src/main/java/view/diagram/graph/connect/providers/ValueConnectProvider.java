package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.BinaryPredicate;
import view.diagram.elements.RoleBox;
import view.diagram.elements.Value;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmWidget;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ValueConnectProvider extends OrmConnectProvider {

  protected ValueConnectProvider(Graph scene) {
    super(scene);
  }

  @Override
  protected List<Class<? extends Widget>> initTargets() {
    return Arrays.asList(RoleBox.class, BinaryPredicate.RolesBox.class);
  }

  @Override
  public ElementType getSourceType() {
    return ElementType.VALUE;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Value;
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
