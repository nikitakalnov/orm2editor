package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.ElementType;
import view.diagram.graph.Graph;

import java.util.List;

public class SubsetConnectProvider extends SetComparisonConnectProvider {

  public SubsetConnectProvider(Graph scene, List<Class<? extends Widget>> targets) {
    super(scene, ElementType.SUBSET_CONSTRAINT, targets);
  }

  @Override
  protected ConnectionWidget createWidget(Widget source, Widget target) {
    ConnectionWidget connectionWidget = super.createWidget(source, target);

    // TODO: устанавливать стрелку только в том случае, если пользователь коннектит ограничение ко второй роли
    connectionWidget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);

    return connectionWidget;
  }
}
