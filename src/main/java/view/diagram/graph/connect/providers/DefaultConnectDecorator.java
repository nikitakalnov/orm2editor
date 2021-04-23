package view.diagram.graph.connect.providers;

import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;

public class DefaultConnectDecorator implements ConnectDecorator {
  @Override
  public ConnectionWidget createConnectionWidget(Scene scene) {
    ConnectionWidget c = new ConnectionWidget(scene);
    c.setTargetAnchorShape(AnchorShape.NONE);
    return c;
  }

  @Override
  public Anchor createSourceAnchor(Widget widget) {
    return AnchorFactory.createFreeRectangularAnchor(widget, true);
  }

  @Override
  public Anchor createTargetAnchor(Widget widget) {
    return AnchorFactory.createFreeRectangularAnchor(widget, true);
  }

  @Override
  public Anchor createFloatAnchor(Point point) {
    return AnchorFactory.createFixedAnchor(point);
  }
}