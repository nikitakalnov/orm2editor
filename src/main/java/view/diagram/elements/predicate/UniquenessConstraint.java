package view.diagram.elements.predicate;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.colors.OrmColorFactory;
import view.diagram.elements.graphics.shapes.ShapeStrategyFactory;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UniquenessConstraint extends Widget implements PropertyChangeListener  {
  private static final int SIDE_PADDING = 6;

  private final Predicate predicate;
  private final int width;
  private boolean enabled;

  public UniquenessConstraint(Scene scene, Predicate predicate) {
    super(scene);

    enabled = predicate.isUnique();

    this.predicate = predicate;
    int constraintWidth = ShapeStrategyFactory.role().getShapeSize().width;
    constraintWidth *= predicate.getArity();

    predicate.addUniquenessChangeListener(this);

    this.width = constraintWidth;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    setUniquenessEnabled(predicate.isUnique());
  }

  @Override
  protected Rectangle calculateClientArea() {
    return new Rectangle(0, 0, width, 3);
  }

  @Override
  protected void paintWidget() {
    if(enabled) {
      Graphics2D g = getScene().getGraphics();
      Color previousColor = g.getColor();

      g.setColor(OrmColorFactory.getPurple());
      g.fillRect(SIDE_PADDING, 4, width - SIDE_PADDING * 2, 3);
      g.setColor(previousColor);
    }
  }

  public void setUniquenessEnabled(boolean enabled) {
    this.enabled = enabled;
    repaint();
  }
}
