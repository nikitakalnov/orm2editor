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
}
