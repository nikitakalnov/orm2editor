package view.diagram.elements.constraints;

import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import view.diagram.elements.core.OrmWidget;

import java.awt.*;

public abstract class Constraint extends ImageWidget implements OrmWidget {

  public Constraint(Scene scene, Image image) {
    super(scene, image);
  }

  @Override
  public Dimension getSize() {
    return super.getPreferredSize();
  }

  @Override
  public Widget getWidget() {
    return this;
  }

  public abstract Image getImage();
}
