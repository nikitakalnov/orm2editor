package view.diagram.graph.connect.factory;

import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.orm2diagram.model.ORM_ConstraintAssociation;
import org.vstu.orm2diagram.model.ORM_SetComparisonConstraint;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.Entity;
import view.diagram.elements.Subtyping;
import view.diagram.elements.constraints.SetComparisonConstraint;
import view.diagram.elements.core.ElementType;
import view.diagram.elements.core.OrmElement;
import view.diagram.graph.connect.providers.SubsetConnectProvider;

import java.awt.*;

public class ConnectionWidgetDesigner {
  private static final Stroke CONSTRAINT_CONNECTION_STROKE = new BasicStroke(2.0f,
          BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_MITER,
          10.0f, new float[]{8.0f}, 0.0f);

  public static ConnectionWidget decorate(ConnectionWidget connectionWidget) {
    Widget source = connectionWidget.getSourceAnchor().getRelatedWidget();
    Widget target = connectionWidget.getTargetAnchor().getRelatedWidget();

    if(source instanceof SetComparisonConstraint) {
      decorateSetComparisonConnection(connectionWidget);

      OrmElement constraint = ((SetComparisonConstraint)source).getElement();
      if(constraint.getType().equals(ElementType.SUBSET_CONSTRAINT)) {
        ORM_SetComparisonConstraint constraintModel = (ORM_SetComparisonConstraint)constraint.getNode();
        // Subset is already connected
        if(constraintModel.getIncidenceElements(ORM_ConstraintAssociation.class).count() == 1)
          connectionWidget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
      }
    }

    else if(source instanceof Entity && target instanceof Entity) {
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
