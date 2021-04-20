package view.diagram.graph.connect.anchor;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class SoftRectangularAnchor extends Anchor {

  private final int rectRadius;

  public SoftRectangularAnchor(Widget anchoredWidget, int rectRadius) {
    super(anchoredWidget);

    this.rectRadius = rectRadius;
  }

  @Override
  public Result compute(Entry entry) {
    ConnectionWidget fcw = entry.getAttachedConnectionWidget();

    assert fcw != null;

    Point relatedLocation = this.getRelatedSceneLocation();
    Widget widget = this.getRelatedWidget();
    List<Point> fcwControlPoints = fcw.getControlPoints();
    Point oppositeLocation;
    if (fcwControlPoints.size() < 2) {
      oppositeLocation = this.getOppositeSceneLocation(entry);
    } else if (entry.isAttachedToConnectionSource()) {
      oppositeLocation = (Point)fcwControlPoints.get(1);
    } else {
      oppositeLocation = (Point)fcwControlPoints.get(fcwControlPoints.size() - 2);
    }

    Rectangle bounds = getRelatedWidget().convertLocalToScene(getRelatedWidget().getBounds());
    RoundRectangle2D.Double roundBounds = new RoundRectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height, rectRadius, rectRadius);

    float dx = (float)(oppositeLocation.x - relatedLocation.x);
    float dy = (float)(oppositeLocation.y - relatedLocation.y);

    // Определение направления движения

    double deltaX = 0;
    double deltaY = 0;

    if(dx > 0.0F)
      deltaX = 0.01;
    else if(dx < 0.0F)
      deltaX = -0.01;

    if(dy > 0.0F)
      deltaY = 0.01;
    else if(dy < 0.0F)
      deltaY = -0.01;

    double a = oppositeLocation.getY() - relatedLocation.getY();
    double b = relatedLocation.getX() - oppositeLocation.getX();

    // Если линия не вертикальная и не горизонтальная, то определяем коэффициент для y
    if(a != 0 && b != 0) {
      a = a / b;
      deltaY = deltaY * Math.abs(a);
    }

    Point2D.Double point = new Point2D.Double(relatedLocation.getX(), relatedLocation.getY());

    // Двигаем точку в заданном направлении, пока она находится внутри прямоугольника
    do {
      point.setLocation(point.x + deltaX, point.y + deltaY);
    } while(roundBounds.contains(point));

    // Округление координат полученной точки в сторону, противоположную направлению движения
    double connectionX = point.x, connectionY = point.y;
    if(dx > 0.0F)
      connectionX = Math.floor(point.x - 0.5);
    else if(dx < 0.0F)
      connectionX = Math.ceil(point.x + 0.5);

    if(dy > 0.0F)
      connectionY = (int)Math.floor(point.y - 0.5);
    else if(dy < 0.0F)
      connectionY = (int)Math.ceil(point.y + 0.5);

    Point connectionPoint = new Point((int)connectionX, (int)connectionY);

    return new Result(connectionPoint, Anchor.DIRECTION_ANY);
  }
}
