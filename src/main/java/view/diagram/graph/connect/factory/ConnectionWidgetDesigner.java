package view.diagram.graph.connect.factory;

import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.Entity;
import view.diagram.elements.Subtyping;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;

import java.awt.*;

public class ConnectionWidgetDesigner {
  private static final Stroke CONSTRAINT_CONNECTION_STROKE = new BasicStroke(2.0f,
          BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_MITER,
          10.0f, new float[]{8.0f}, 0.0f);

  public static ConnectionWidget decorate(ConnectionWidget connectionWidget) {
    Widget source = connectionWidget.getSourceAnchor().getRelatedWidget();
    Widget target = connectionWidget.getTargetAnchor().getRelatedWidget();

    if(source instanceof SetComparisonConstraint || target instanceof SetComparisonConstraint) {
      decorateSetComparisonConnection(connectionWidget);

      if(source instanceof SetComparisonConstraint) {
        if(((SetComparisonConstraint) source).getElement().getType().equals(ElementType.SUBSET_CONSTRAINT))
          connectionWidget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
      }
      else if(((SetComparisonConstraint) target).getElement().getType().equals(ElementType.SUBSET_CONSTRAINT))
        connectionWidget.setSourceAnchorShape(AnchorShape.TRIANGLE_FILLED);

      return connectionWidget;
    }

    if(source instanceof Entity && target instanceof Entity) {
      ConnectionWidget subtypingConnectionWidget = new Subtyping(connectionWidget.getScene());
      subtypingConnectionWidget.setTargetAnchor(connectionWidget.getTargetAnchor());
      subtypingConnectionWidget.setSourceAnchor(connectionWidget.getSourceAnchor());

      return subtypingConnectionWidget;
    }

    return connectionWidget;
  }

  protected static void decorateSetComparisonConnection(ConnectionWidget connectionWidget) {
    connectionWidget.setStroke(CONSTRAINT_CONNECTION_STROKE);
    connectionWidget.setLineColor(OrmColorFactory.getPurple());
  }
}
