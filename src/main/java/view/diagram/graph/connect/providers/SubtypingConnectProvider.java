package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.Subtyping;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubtypingConnectProvider extends OrmConnectProvider {
  public SubtypingConnectProvider(Graph scene) {
    super(scene);
  }

  @Override
  protected List<Class<? extends Widget>> initTargets() {
    return Collections.singletonList(Subtyping.class);
  }

  @Override
  public ElementType getSourceType() {
    return ElementType.SUBTYPING;
  }

  @Override
  public boolean isSourceWidget(Widget widget) {
    return widget instanceof Subtyping;
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
